package cn.steateam.lets_team_server.interceptor;

import cn.steateam.lets_team_server.dto.RedisTokenUserInfoDto;
import cn.steateam.lets_team_server.service.RedisService;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * web socket握手请求拦截器
 *
 * @author STEA_YY
 */
@Component
public class WebSocketHandShakeInterceptor extends HttpSessionHandshakeInterceptor {
    @Resource
    private RedisService redisService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        HttpServletRequest httpServletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        String accessToken = httpServletRequest.getParameter("token");
        String platformId = httpServletRequest.getParameter("platformId");
        RedisTokenUserInfoDto redisTokenUserInfoDto = redisService.checkAccessToken(accessToken, platformId);
        attributes.put("userId", redisTokenUserInfoDto.getUserId());
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }
}
