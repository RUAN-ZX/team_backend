package cn.steateam.lets_team_server.entity;

import lombok.Data;

import java.util.Date;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/23 10:08 下午
 */
@Data
public class CompetitionExperienceSharingReply {
    /**
     * 项目经验分享回复id
     */
    private Integer id;

    /**
     * 作者id
     */
    private Integer authorUserId;

    /**
     * 分享文章id
     */
    private Integer sharingId;

    /**
     * 回复类型(直接回复or间接)
     */
    private Integer type;

    /**
     * 目标回复id
     */
    private Integer replyId;

    /**
     * 目标用户id
     */
    private Integer replyUserId;

    /**
     * 回复正文
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
     * 更新时间
     */
    private Date updateTime;
}