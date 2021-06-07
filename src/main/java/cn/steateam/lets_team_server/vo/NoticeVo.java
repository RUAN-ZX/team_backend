package cn.steateam.lets_team_server.vo;

import lombok.Data;

import java.util.Date;

/**
 * 通知VO
 *
 * @author STEA_YY
 */
@Data
public class NoticeVo {
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
}
