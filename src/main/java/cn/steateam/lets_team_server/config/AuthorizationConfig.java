package cn.steateam.lets_team_server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 授权鉴权模块配置类
 *
 * @author STEA_YY
 */
@Component
@ConfigurationProperties("authorization-config")
@Data
public class AuthorizationConfig {
    private RefreshToken refreshToken = new RefreshToken();
    private AccessToken accessToken = new AccessToken();

    @Data
    public static class RefreshToken {
        private long expireTime;
    }

    @Data
    public static class AccessToken {
        private long expireTime;
    }
}
