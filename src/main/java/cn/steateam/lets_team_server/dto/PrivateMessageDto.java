package cn.steateam.lets_team_server.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 站内信DTO
 *
 * @author STEA_YY
 */
@Data
public class PrivateMessageDto {
    /**
     * 收件人用户id
     */
    @NotNull(message = "收件人用户id不能为空！")
    private Integer receiverUserId;

    /**
     * 站内信正文类型
     */
    @NotNull(message = "站内信类型不能为空！")
    private Integer contentType;

    /**
     * 站内信正文
     */
    @NotNull(message = "站内信正文不能为空！")
    private String content;
}
