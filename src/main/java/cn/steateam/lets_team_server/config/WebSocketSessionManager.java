package cn.steateam.lets_team_server.config;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * web socket会话管理器
 *
 * @author STEA_YY
 */
public class WebSocketSessionManager {
    private static final ConcurrentHashMap<Integer, WebSocketSession> SESSION_POOL = new ConcurrentHashMap<>();

    public static void add(int userId, WebSocketSession session) {
        SESSION_POOL.put(userId, session);
    }

    public static WebSocketSession remove(int userId) {
        return SESSION_POOL.remove(userId);
    }

    public static void removeAndClose(int userId) throws IOException {
        WebSocketSession session = remove(userId);
        if (session != null) {
            session.close();
        }
    }

    public static WebSocketSession get(int userId) {
        return SESSION_POOL.get(userId);
    }
}
