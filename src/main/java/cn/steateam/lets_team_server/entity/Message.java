package cn.steateam.lets_team_server.entity;

import java.util.Date;
import lombok.Data;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/30 12:16 上午
 */
@Data
public class Message {
    /**
     * 站内信id
     */
    private Integer id;

    /**
     * 站内信会话标识
     */
    private String sessionKey;

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

    /**
     * 修改时间
     */
    private Date updateTime;
}