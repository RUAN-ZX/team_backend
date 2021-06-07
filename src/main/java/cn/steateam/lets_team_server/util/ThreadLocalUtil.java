package cn.steateam.lets_team_server.util;

/**
 * 线程隔离变量相关工具类
 *
 * @author STEA_YY
 **/
public class ThreadLocalUtil {
    private static final ThreadLocal<Integer> CURRENT_USER_THREAD_LOCAL = new ThreadLocal<>();

    public static void setCurrentUser(Integer userId) {
        CURRENT_USER_THREAD_LOCAL.set(userId);
    }

    public static Integer getCurrentUser() {
        return CURRENT_USER_THREAD_LOCAL.get();
    }

    public static void removeCurrentUser() {
        CURRENT_USER_THREAD_LOCAL.remove();
    }
}
