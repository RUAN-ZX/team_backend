package cn.steateam.lets_team_server.interceptor;

import cn.steateam.lets_team_server.annotation.RequiresLogin;
import cn.steateam.lets_team_server.constant.AuthenticationConstants;
import cn.steateam.lets_team_server.dto.RedisTokenUserInfoDto;
import cn.steateam.lets_team_server.exception.HeadersNotValidException;
import cn.steateam.lets_team_server.exception.PermissionDeniedException;
import cn.steateam.lets_team_server.exception.TokenCheckException;
import cn.steateam.lets_team_server.service.RedisService;
import cn.steateam.lets_team_server.util.IpUtil;
import cn.steateam.lets_team_server.util.ThreadLocalUtil;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 鉴权拦截器
 *
 * @author STEA_YY
 **/
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Resource
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws TokenCheckException, PermissionDeniedException, HeadersNotValidException {
        // 从 http 请求头中取出 token
        String accessToken = request.getHeader(AuthenticationConstants.HEADER_NAME_ACCESS_TOKEN);
        String platformId = request.getHeader(AuthenticationConstants.HEADER_NAME_PLATFORM_ID);
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(RequiresLogin.class)) {
            RequiresLogin requiresLogin = method.getAnnotation(RequiresLogin.class);
            if (requiresLogin.required()) {
                // 执行认证
                RedisTokenUserInfoDto redisTokenUserInfoDto = redisService.checkAccessToken(accessToken, platformId);
                if (requiresLogin.requiresRoles().length > 0) {
                    boolean hasPermission = false;
                    for (Integer roleId : requiresLogin.requiresRoles()) {
                        if (roleId.equals(redisTokenUserInfoDto.getRoleId())) {
                            hasPermission = true;
                            break;
                        }
                    }
                    if (!hasPermission) {
                        throw new PermissionDeniedException();
                    }
                }
                ThreadLocalUtil.setCurrentUser(redisTokenUserInfoDto.getUserId());
                MDC.put("userId", redisTokenUserInfoDto.getUserId().toString());
                return true;
            }
        }
        MDC.put("ip", IpUtil.getIpAddr(request));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ThreadLocalUtil.removeCurrentUser();
        MDC.clear();
    }
}
