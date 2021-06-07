package cn.steateam.lets_team_server.entity;

import lombok.Data;

import java.util.Date;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/19 12:35 上午
 */
@Data
public class CompetitionAnswer {
    /**
     * 回答id
     */
    private Integer id;

    /**
     * 回答者用户id
     */
    private Integer authorUserId;

    /**
     * 提问id
     */
    private Integer questionId;

    /**
     * 回答正文
     */
    private String content;

    /**
     * 回答状态
     */
    private Integer status;

    /**
     * 发布时间
     */
    private Date postTime;

    /**
     * 最近修改时间
     */
    private Date updateTime;
}