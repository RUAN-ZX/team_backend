package cn.steateam.lets_team_server.config;

import cn.steateam.lets_team_server.interceptor.WebSocketHandShakeInterceptor;
import cn.steateam.lets_team_server.service.WebSocketService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * websocket配置类
 *
 * @author STEA_YY
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Resource
    private WebSocketService webSocketService;
    @Resource
    private WebSocketHandShakeInterceptor webSocketHandShakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketService, "/im")
                .addInterceptors(webSocketHandShakeInterceptor)
                .setAllowedOrigins("*");
    }
}
