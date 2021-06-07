package cn.steateam.lets_team_server.vo;

import lombok.Data;

import java.util.Date;

/**
 * 竞赛经验分享帖详细信息VO
 *
 * @author YaoYi
 * @date 2021/3/24 1:12 上午
 */
@Data
public class CompetitionExperienceSharingDetailedVo {
    /**
     * 竞赛经验分享id
     */
    private Integer id;

    /**
     * 竞赛id
     */
    private Integer compId;

    /**
     * 作者用户id
     */
    private Integer authorUserId;

    /**
     * 标签
     */
    private String tags;

    /**
     * 标题
     */
    private String title;

    /**
     * 正文
     */
    private String content;

    /**
     * 状态
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
