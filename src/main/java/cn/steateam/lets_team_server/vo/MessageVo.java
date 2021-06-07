package cn.steateam.lets_team_server.vo;

import lombok.Data;

import java.util.Date;

/**
 * 站内信VO
 *
 * @author STEA_YY
 */
@Data
public class MessageVo {

    /**
     * 发件人用户id
     */
    private Integer senderUserId;

    /**
     * 收件人用户id
     */
    private Integer receiverUserId;

    /**
     * 站内信类型
     */
    private Integer type;

    /**
     * 站内信正文类型
     */
    private Integer contentType;

    /**
     * 站内信正文
     */
    private String content;

    /**
     * 站内信发送时间
     */
    private Date sendingTime;

    /**
     * 站内信已读标识
     */
    private Integer readStatus;

    /**
     * 站内信被阅读时间
     */
    private Date readTime;
}
