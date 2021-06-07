package cn.steateam.lets_team_server.constant;

/**
 * Redis常量类
 *
 * @author STEA_YY
 */
public class RedisConstants {
    public static final String PREFIX_ACCESS_TOKEN = "user:accessToken:";
    public static final String PREFIX_REFRESH_TOKEN = "user:refreshToken:";
    public static final String CURRENT_ACCESS_TOKEN_KEY = "current:accessToken";
    public static final String CURRENT_REFRESH_TOKEN_KEY = "current:refreshToken";
    public static final String PREFIX_MAIL_VERIFICATION_CODE = "mail:verification:code:";
    public static final String PREFIX_MAIL_ADDRESS_COOLING = "mail:address:cooling";
}
