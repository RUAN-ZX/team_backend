package cn.steateam.lets_team_server.strategy.handler;

/**
 * web socket消息类型策略接口
 *
 * @author STEA_YY
 */
public interface WebSocketMessageHandler {
    void handleMessage(int senderUserId, String textMessage) throws Exception;
}
