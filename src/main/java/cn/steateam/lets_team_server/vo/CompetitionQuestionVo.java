package cn.steateam.lets_team_server.vo;

import lombok.Data;

import java.util.Date;

/**
 * 竞赛提问VO
 *
 * @author YaoYi
 * @date 2021/3/20 1:19 上午
 */
@Data
public class CompetitionQuestionVo {
    /**
     * 竞赛提问id
     */
    private Integer id;

    /**
     * 竞赛id
     */
    private Integer compId;

    /**
     * 提问者用户id
     */
    private Integer authorUserId;

    /**
     * 标题
     */
    private String title;

    /**
     * 提问正文
     */
    private String content;

    /**
     * 发布时间
     */
    private Date postTime;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 最近修改时间
     */
    private Date updateTime;
}
