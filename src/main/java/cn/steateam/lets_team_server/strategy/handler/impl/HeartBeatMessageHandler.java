package cn.steateam.lets_team_server.strategy.handler.impl;

import cn.steateam.lets_team_server.constant.StrategyConstants;
import cn.steateam.lets_team_server.constant.WebSocketConstants;
import cn.steateam.lets_team_server.dto.WebSocketMessageDto;
import cn.steateam.lets_team_server.service.WebSocketService;
import cn.steateam.lets_team_server.strategy.handler.WebSocketMessageHandler;
import cn.steateam.lets_team_server.util.JacksonUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 心跳包处理策略类
 *
 * @author STEA_YY
 */
@Component(value = StrategyConstants.PREFIX_BEAN_NAME_WEB_SOCKET_STRATEGY + WebSocketConstants.TYPE_HEART_BEAT)
public class HeartBeatMessageHandler implements WebSocketMessageHandler {
    @Resource
    private WebSocketService webSocketService;

    @Override
    public void handleMessage(int senderUserId, String textMessage) throws Exception {
        WebSocketMessageDto<Object> webSocketMessageDto = new WebSocketMessageDto<>(WebSocketConstants.TYPE_HEART_BEAT,null);
        String responseTextMessage = JacksonUtil.objToJson(webSocketMessageDto);
        webSocketService.sendMessage(senderUserId, responseTextMessage);
    }
}
