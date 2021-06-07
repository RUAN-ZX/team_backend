package cn.steateam.lets_team_server.dto;

import lombok.Data;

/**
 * 通知DTO
 *
 * @author STEA_YY
 */
@Data
public class NoticeDto {
    /**
     * 收信人用户id
     */
    private Integer receiverUserId;

    /**
     * 通知类型
     */
    private Integer type;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知正文
     */
    private String content;
}
