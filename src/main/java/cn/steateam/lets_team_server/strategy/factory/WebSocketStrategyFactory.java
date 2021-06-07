package cn.steateam.lets_team_server.strategy.factory;

import cn.steateam.lets_team_server.constant.StrategyConstants;
import cn.steateam.lets_team_server.strategy.handler.WebSocketMessageHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * web socket 策略工厂类
 *
 * @author STEA_YY
 */
@Service
public class WebSocketStrategyFactory {
    @Resource
    private final Map<String, WebSocketMessageHandler> strategyMap = new ConcurrentHashMap<>();

    public WebSocketMessageHandler getStrategy(Integer type) {
        WebSocketMessageHandler strategy = strategyMap.get(StrategyConstants.PREFIX_BEAN_NAME_WEB_SOCKET_STRATEGY + type);
        if (strategy == null) {
            throw new IllegalArgumentException();
        }
        return strategy;
    }
}
