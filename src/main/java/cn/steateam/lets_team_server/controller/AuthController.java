package cn.steateam.lets_team_server.controller;

import cn.steateam.lets_team_server.annotation.RequiresLogin;
import cn.steateam.lets_team_server.constant.AuthenticationConstants;
import cn.steateam.lets_team_server.dto.MailCaptchaUserDto;
import cn.steateam.lets_team_server.dto.MailLoginUserDto;
import cn.steateam.lets_team_server.dto.MailRegisterUserDto;
import cn.steateam.lets_team_server.exception.HeadersNotValidException;
import cn.steateam.lets_team_server.exception.MailSendingException;
import cn.steateam.lets_team_server.exception.AccountNotFoundException;
import cn.steateam.lets_team_server.exception.CodeVerificationException;
import cn.steateam.lets_team_server.exception.InvalidPasswordException;
import cn.steateam.lets_team_server.exception.TokenCheckException;
import cn.steateam.lets_team_server.exception.AccountExistedException;
import cn.steateam.lets_team_server.service.AuthService;
import cn.steateam.lets_team_server.util.ThreadLocalUtil;
import cn.steateam.lets_team_server.vo.ResponseBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * 授权鉴权类请求
 *
 * @author STEA_YY
 */
@RestController
public class AuthController {
    @Resource
    private AuthService authService;
    @Resource
    private HttpServletRequest httpServletRequest;
    @Resource
    private HttpServletResponse httpServletResponse;

    /**
     * 使用密码 邮箱方式 登录
     *
     * @param mailPwdLoginUserDto 邮箱密码 登录信息
     */
    @PostMapping("/loginByPwd/mail")
    public ResponseBean<Object> loginByPwdMail(@Validated @RequestBody MailLoginUserDto mailPwdLoginUserDto) throws AccountNotFoundException, InvalidPasswordException, HeadersNotValidException, TokenCheckException {
        String platformId = httpServletRequest.getHeader(AuthenticationConstants.HEADER_NAME_PLATFORM_ID);

        String refreshToken = authService.loginByMail(mailPwdLoginUserDto, platformId);

        httpServletResponse.addHeader(AuthenticationConstants.HEADER_NAME_REFRESH_TOKEN, refreshToken);

        String accessToken = authService.refreshAccessToken(refreshToken, platformId);
        httpServletResponse.addHeader(AuthenticationConstants.HEADER_NAME_ACCESS_TOKEN, accessToken);

        return ResponseBean.emptySuccessResponse();
    }


    /**
     * 固定密码 邮箱方式 注册
     *
     * @param mailPwdRegisterUserDto 邮箱密码 注册信息
     */
    @PostMapping("/registerByPwd/mail")
    public ResponseBean<Object> registerByPwdMail(@Validated @RequestBody MailRegisterUserDto mailPwdRegisterUserDto) throws CodeVerificationException, AccountExistedException {
        int userId = authService.registerByMail(mailPwdRegisterUserDto);
        return new ResponseBean<>(userId);
    }


    /**
     * 使用验证码 邮箱方式 登录或注册
     *
     * @param mailCaptchaUserDto 邮箱验证码 信息
     */
    @PostMapping("/loginRegByCaptcha/mail")
    public ResponseBean<Object> loginRegByCaptchaMail(@Validated @RequestBody MailCaptchaUserDto mailCaptchaUserDto) throws AccountNotFoundException, InvalidPasswordException, HeadersNotValidException, CodeVerificationException, TokenCheckException {
        String platformId = httpServletRequest.getHeader(AuthenticationConstants.HEADER_NAME_PLATFORM_ID);

        String refreshToken = authService.loginRegByCaptchaMail(mailCaptchaUserDto, platformId);

        httpServletResponse.addHeader(AuthenticationConstants.HEADER_NAME_REFRESH_TOKEN, refreshToken);

        String accessToken = authService.refreshAccessToken(refreshToken, platformId);
        httpServletResponse.addHeader(AuthenticationConstants.HEADER_NAME_ACCESS_TOKEN, accessToken);

        return ResponseBean.emptySuccessResponse();
    }



    /**
     * 使用 userId & refresh token登录
     * 这里不用返回userId 使用手动登录（输入验证码密码）的才会返回userId
     *
     */
    @GetMapping("/loginByToken")
    public ResponseBean<Object> registerByCaptchaMail(@Validated @RequestParam int userId) throws CodeVerificationException, AccountExistedException, HeadersNotValidException, TokenCheckException {
        String refreshToken = httpServletRequest.getHeader(AuthenticationConstants.HEADER_NAME_REFRESH_TOKEN);
        String platformId = httpServletRequest.getHeader(AuthenticationConstants.HEADER_NAME_PLATFORM_ID);
        ArrayList<String> token = authService.loginByToken(refreshToken,userId, platformId);

        httpServletResponse.addHeader(AuthenticationConstants.HEADER_NAME_REFRESH_TOKEN, token.get(1));
        httpServletResponse.addHeader(AuthenticationConstants.HEADER_NAME_ACCESS_TOKEN, token.get(0));


        return ResponseBean.emptySuccessResponse();
    }


    /**
     * 发送注册验证邮件(验证码）
     *
     * @param receiverAddress 注册邮箱
     */
    @GetMapping("/getCaptcha/mail")
    public ResponseBean<Object> sendRegisterVerificationMail(@RequestParam String receiverAddress) throws MailSendingException {
        authService.sendRegisterVerificationMail(receiverAddress);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 登出
     */
    @GetMapping("/logout")
    @RequiresLogin
    public ResponseBean<Object> logout() {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        authService.logout(userId);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 刷新Access Token
     */
    @PostMapping("/token/refresh")
    public ResponseBean<Object> refreshAccessToken() throws TokenCheckException, HeadersNotValidException {
        String refreshToken = httpServletRequest.getHeader(AuthenticationConstants.HEADER_NAME_REFRESH_TOKEN);
        String platformId = httpServletRequest.getHeader(AuthenticationConstants.HEADER_NAME_PLATFORM_ID);
        String accessToken = authService.refreshAccessToken(refreshToken, platformId);
        httpServletResponse.addHeader(AuthenticationConstants.HEADER_NAME_ACCESS_TOKEN, accessToken);
        return ResponseBean.emptySuccessResponse();
    }
}
