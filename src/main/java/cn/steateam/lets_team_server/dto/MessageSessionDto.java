package cn.steateam.lets_team_server.dto;

import lombok.Data;

import java.util.Date;

/**
 * 会话DTO
 *
 * @author STEA_YY
 */
@Data
public class MessageSessionDto {
    /**
     * 会话id
     */
    private String sessionKey;

    /**
     * 最后通信时间
     */
    private Date lastMessageSendingTime;
}
