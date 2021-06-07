package cn.steateam.lets_team_server.strategy.handler.impl;

import cn.steateam.lets_team_server.constant.StrategyConstants;
import cn.steateam.lets_team_server.constant.WebSocketConstants;
import cn.steateam.lets_team_server.dto.WebSocketMessageDto;
import cn.steateam.lets_team_server.service.MessageService;
import cn.steateam.lets_team_server.service.WebSocketService;
import cn.steateam.lets_team_server.strategy.handler.WebSocketMessageHandler;
import cn.steateam.lets_team_server.util.JacksonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 已读消息处理策略类
 *
 * @author STEA_YY
 */
@Component(value = StrategyConstants.PREFIX_BEAN_NAME_WEB_SOCKET_STRATEGY + WebSocketConstants.TYPE_RECEIVE_MESSAGE)
public class ReceiveMessageHandler implements WebSocketMessageHandler {
    @Resource
    private MessageService messageService;
    @Resource
    private WebSocketService webSocketService;

    @Override
    public void handleMessage(int senderUserId, String textMessage) throws IOException {
        WebSocketMessageDto<Integer> webSocketMessageDto = JacksonUtil.jsonToPojo(textMessage, new TypeReference<WebSocketMessageDto<Integer>>() {
        });
        messageService.readAllByUserIds(webSocketMessageDto.getData(), senderUserId);
        webSocketService.sendEmptySuccessResponse(senderUserId);
    }
}
