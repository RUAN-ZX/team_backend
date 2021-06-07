package cn.steateam.lets_team_server.service;

import cn.steateam.lets_team_server.config.WebSocketSessionManager;
import cn.steateam.lets_team_server.constant.WebSocketConstants;
import cn.steateam.lets_team_server.dto.WebSocketMessageDto;
import cn.steateam.lets_team_server.strategy.factory.WebSocketStrategyFactory;
import cn.steateam.lets_team_server.util.JacksonUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * web socket服务类
 *
 * @author STEA_YY
 */
@Component
public class WebSocketService extends TextWebSocketHandler {
    @Resource
    private WebSocketStrategyFactory webSocketStrategyFactory;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Integer userId = (Integer) session.getAttributes().get("userId");
        WebSocketSessionManager.add(userId, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Integer userId = (Integer) session.getAttributes().get("userId");
        WebSocketSessionManager.remove(userId);
        super.afterConnectionClosed(session, status);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        Integer userId = (Integer) session.getAttributes().get("userId");
        String payload = message.getPayload();
        try {
        Map<String, Object> messageMap = JacksonUtil.jsonToMap(payload);
        Integer type = (Integer) messageMap.get("type");
            webSocketStrategyFactory.getStrategy(type).handleMessage(userId, payload);
        } catch (Exception e) {
            WebSocketMessageDto<Object> webSocketMessageDto = new WebSocketMessageDto<>(WebSocketConstants.TYPE_ERROR,null);
            String responseTextMessage = JacksonUtil.objToJson(webSocketMessageDto);
            sendMessage(userId, responseTextMessage);
        }
    }

    public void sendMessage(int userId, String message) throws IOException {
        WebSocketSession webSocketSession = WebSocketSessionManager.get(userId);
        if (webSocketSession != null && webSocketSession.isOpen()) {
            TextMessage textMessage = new TextMessage(message);
            webSocketSession.sendMessage(textMessage);
        }
    }

    public void sendEmptySuccessResponse(int userId) throws IOException {
        WebSocketSession webSocketSession = WebSocketSessionManager.get(userId);
        if (webSocketSession != null && webSocketSession.isOpen()) {
            WebSocketMessageDto<Object> webSocketMessageDto = WebSocketMessageDto.emptySuccessResponse();
            TextMessage textMessage = new TextMessage(JacksonUtil.objToJson(webSocketMessageDto));
            webSocketSession.sendMessage(textMessage);
        }
    }
}