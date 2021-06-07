package cn.steateam.lets_team_server.entity;

import java.util.Date;
import lombok.Data;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/27 10:22 下午
 */
@Data
public class TeamRequirement {
    /**
     * 组队需求id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户标签
     */
    private String tags;

    /**
     * 组队需求简介
     */
    private String intro;

    /**
     * 组队需求正文
     */
    private String content;

    /**
     * 发布时间
     */
    private Date postTime;

    /**
     * 需求状态
     */
    private Integer requireStatus;

    /**
     * 关闭时间
     */
    private Date closedTime;

    /**
     * 状态(是否关闭)
     */
    private Integer status;

    /**
     * 最近更新时间
     */
    private Date updateTime;
}