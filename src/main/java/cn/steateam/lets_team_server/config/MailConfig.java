package cn.steateam.lets_team_server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 邮件配置类
 *
 * @author STEA_YY
 */
@Component
@ConfigurationProperties("mail-config")
@Data
public class MailConfig {
    private String fromAddress;
    private Long expireTime;
    private Long coolingTime;
    private Integer codeLength;
    private String subject;
}
