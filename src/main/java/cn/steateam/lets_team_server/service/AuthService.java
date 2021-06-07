package cn.steateam.lets_team_server.service;

import cn.steateam.lets_team_server.config.AuthorizationConfig;
import cn.steateam.lets_team_server.constant.AuthorizationInfoConstants;
import cn.steateam.lets_team_server.constant.PlatformEnum;
import cn.steateam.lets_team_server.constant.RedisConstants;
import cn.steateam.lets_team_server.constant.UserConstants;
import cn.steateam.lets_team_server.dto.MailCaptchaUserDto;
import cn.steateam.lets_team_server.dto.MailLoginUserDto;
import cn.steateam.lets_team_server.dto.MailRegisterUserDto;
import cn.steateam.lets_team_server.dto.RedisTokenUserInfoDto;
import cn.steateam.lets_team_server.entity.AuthorizationInfo;
import cn.steateam.lets_team_server.entity.User;
import cn.steateam.lets_team_server.entity.UserInfo;
import cn.steateam.lets_team_server.entity.UserResumeBasic;
import cn.steateam.lets_team_server.exception.*;
import cn.steateam.lets_team_server.util.EncryptUtil;
import cn.steateam.lets_team_server.util.IpUtil;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 授权鉴权服务类
 *
 * @author STEA_YY
 */
@Service
public class AuthService {
    @Resource
    private UserService userService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private AuthorizationInfoService authorizationInfoService;
    @Resource
    private RedisService redisService;
    @Resource
    private VerificationMailService verificationMailService;
    @Resource
    private UserResumeService userResumeService;
    @Resource
    private AuthorizationConfig authorizationConfig;
    @Resource
    private HttpServletRequest request;

    public void sendRegisterVerificationMail(String receiverAddress) throws MailSendingException {
//        这里暂时不审核是否已注册邮箱 因为要支持邮箱的验证码登录 当然无需担心
//        已经注册过的邮箱不会再次注册 而是直接登录 详情可见AuthService 的 loginRegByCaptchaMail()
//        if (authorizationInfoService.getAuthorizationInfoByAccountAndType(receiverAddress, AuthorizationInfoConstants.TYPE_MAIL) != null) {
//            throw new MailSendingException("该邮箱已被注册！");
//        }
        try {
            verificationMailService.sendVerificationMail(receiverAddress);
        } catch (MailSendingException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("服务器内部错误！");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public int registerByMail(MailRegisterUserDto mailPwdRegisterUserDto) throws CodeVerificationException, AccountExistedException {
        boolean verify = verificationMailService.verify(mailPwdRegisterUserDto.getMail(), mailPwdRegisterUserDto.getCode());
        if (!verify) {
            throw new CodeVerificationException("邮箱验证码填写错误！");
        }
        if (authorizationInfoService.getAuthorizationInfoByAccountAndType(mailPwdRegisterUserDto.getMail(), AuthorizationInfoConstants.TYPE_MAIL) != null) {
            throw new AccountExistedException();
        }
        User user = new User();
        user.setPassword(EncryptUtil.getSha256(mailPwdRegisterUserDto.getPassword()));
        user.setRoleId(UserConstants.ROLE_NORMAL);
        userService.saveUser(user);
        AuthorizationInfo authorizationInfo = new AuthorizationInfo();
        authorizationInfo.setUserId(user.getId());
        authorizationInfo.setAccount(mailPwdRegisterUserDto.getMail());
        authorizationInfo.setType(AuthorizationInfoConstants.TYPE_MAIL);
        authorizationInfoService.saveAuthorizationInfo(authorizationInfo);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNickname(mailPwdRegisterUserDto.getNickname());
        userInfoService.save(userInfo);
        saveAdditionalRecords(user.getId());
        return user.getId();
    }

    public String loginByMail(MailLoginUserDto mailPwdLoginUserDto, String platformId) throws AccountNotFoundException, InvalidPasswordException, HeadersNotValidException {
        if (PlatformEnum.getByValue(platformId) == null) {
            throw new HeadersNotValidException();
        }
        AuthorizationInfo authorizationInfo = authorizationInfoService.getAuthorizationInfoByAccountAndType(mailPwdLoginUserDto.getMail(), AuthorizationInfoConstants.TYPE_MAIL);
        if (authorizationInfo == null) {
            throw new AccountNotFoundException("用户不存在！");
        }
        User user = userService.getUserById(authorizationInfo.getUserId());
        if (EncryptUtil.getSha256(mailPwdLoginUserDto.getPassword()).equals(user.getPassword())) {
            return executeLogin(user, platformId);
        } else {
            throw new InvalidPasswordException("用户名或密码错误！");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public String loginRegByCaptchaMail(MailCaptchaUserDto mailCaptchaUserDto, String platformId) throws AccountNotFoundException, InvalidPasswordException, HeadersNotValidException, CodeVerificationException {
        Map<String,String> result = new HashMap<>();

        if (PlatformEnum.getByValue(platformId) == null) {
            throw new HeadersNotValidException();
        }

        AuthorizationInfo authorizationInfo = authorizationInfoService.getAuthorizationInfoByAccountAndType(mailCaptchaUserDto.getMail(), AuthorizationInfoConstants.TYPE_MAIL);
        if (authorizationInfo == null) {
            boolean verify = verificationMailService.verify(mailCaptchaUserDto.getMail(), mailCaptchaUserDto.getCaptcha());
            if (!verify) {
                throw new CodeVerificationException("邮箱验证码填写错误！");
            }
            User user = new User();
            user.setRoleId(UserConstants.ROLE_NORMAL);
            userService.saveUser(user);

            Integer userId = user.getId();
            AuthorizationInfo authorizationInfo_ = new AuthorizationInfo();
            authorizationInfo_.setUserId(userId);
            authorizationInfo_.setAccount(mailCaptchaUserDto.getMail());
            authorizationInfo_.setType(AuthorizationInfoConstants.TYPE_MAIL);
            authorizationInfoService.saveAuthorizationInfo(authorizationInfo_);
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userId);
            userInfo.setNickname(mailCaptchaUserDto.getMail());
            userInfoService.save(userInfo);
            saveAdditionalRecords(userId);

            return executeLogin(user, platformId);
        }
        else{
            // 用户存在 检测验证码
            boolean verify = verificationMailService.verify(mailCaptchaUserDto.getMail(), mailCaptchaUserDto.getCaptcha());
            if (verify) {
                User user = userService.getUserById(authorizationInfo.getUserId());
                return executeLogin(user, platformId);
            }
            else{
                // 验证码有误
                throw new CodeVerificationException("邮箱验证码填写错误！");
            }
        }
    }



    public void logout(Integer userId) {
        redisService.hDel(RedisConstants.CURRENT_REFRESH_TOKEN_KEY, userId.toString());
        redisService.hDel(RedisConstants.CURRENT_ACCESS_TOKEN_KEY, userId.toString());
    }

    public ArrayList<String> loginByToken(String refreshToken, int userId, String platformId) throws HeadersNotValidException, TokenCheckException {
        User user = userService.getUserById(userId);
        ArrayList<String> result = new ArrayList<String>();
        result.add(refreshAccessToken(refreshToken, platformId));
        // 调用refreshAccessToken没报错 意味着可以使用 executeLogin 重置 refreshToken
        result.add(executeLogin(user, platformId));

        return result;
    }

    public String refreshAccessToken(String refreshToken, String platformId) throws TokenCheckException, HeadersNotValidException {
        if (refreshToken == null) {
            throw new TokenCheckException();
        }
        if (PlatformEnum.getByValue(platformId) == null) {
            throw new HeadersNotValidException();
        }
        RedisTokenUserInfoDto redisTokenUserInfoDto = (RedisTokenUserInfoDto) redisService.get(RedisConstants.PREFIX_REFRESH_TOKEN + refreshToken);
        if (redisTokenUserInfoDto == null) {
            throw new TokenCheckException();
        }
        if (!platformId.equals(redisTokenUserInfoDto.getPlatformId())) {
            throw new TokenCheckException();
        }
        String currentRefreshToken = (String) redisService.hGet(RedisConstants.CURRENT_REFRESH_TOKEN_KEY, redisTokenUserInfoDto.getUserId().toString() + ":" + platformId);
        if (!refreshToken.equals(currentRefreshToken)) {
            throw new TokenCheckException();
        }
        String accessToken = UUID.randomUUID().toString();
        redisService.set(RedisConstants.PREFIX_ACCESS_TOKEN + accessToken, redisTokenUserInfoDto, authorizationConfig.getAccessToken().getExpireTime());
        redisService.hPut(RedisConstants.CURRENT_ACCESS_TOKEN_KEY, redisTokenUserInfoDto.getUserId().toString() + ":" + platformId, accessToken);
        redisService.expire(RedisConstants.PREFIX_REFRESH_TOKEN + refreshToken, authorizationConfig.getRefreshToken().getExpireTime());
        return accessToken;
    }



    private String executeLogin(User user, String platformId) {
        MDC.put("userId", user.getId().toString());
        MDC.put("ip", IpUtil.getIpAddr(request));
        String refreshToken = UUID.randomUUID().toString();
        RedisTokenUserInfoDto redisTokenUserInfoDto = RedisTokenUserInfoDto
                .builder()
                .userId(user.getId())
                .roleId(user.getRoleId())
                .platformId(platformId)
                .build();
        redisService.set(RedisConstants.PREFIX_REFRESH_TOKEN + refreshToken, redisTokenUserInfoDto, authorizationConfig.getRefreshToken().getExpireTime());
        redisService.hPut(RedisConstants.CURRENT_REFRESH_TOKEN_KEY, user.getId().toString() + ":" + platformId, refreshToken);
        return refreshToken;
    }

    void saveAdditionalRecords(int userId) {
        UserResumeBasic userResumeBasic = new UserResumeBasic();
        userResumeBasic.setUserId(userId);
        userResumeService.saveBasic(userResumeBasic);
    }
}
