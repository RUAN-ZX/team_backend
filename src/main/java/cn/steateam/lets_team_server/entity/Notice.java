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
public class Notice {
    /**
     * 主键
     */
    private Integer id;

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

    /**
     * 发送时间
     */
    private Date sendingTime;

    /**
     * 阅读状态
     */
    private Integer readStatus;

    /**
     * 阅读时间
     */
    private Date readTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}