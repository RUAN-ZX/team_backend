package cn.steateam.lets_team_server.entity;

import lombok.Data;

import java.util.Date;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/17 12:09 上午
 */
@Data
public class ProjectRecruiting {
    /**
     * 项目招募需求主键
     */
    private Integer id;

    /**
     * 对应项目id
     */
    private Integer projectId;

    /**
     * 发布者用户id
     */
    private Integer creatorUserId;

    /**
     * 招募详细需求
     */
    private String content;

    /**
     * 招募人数
     */
    private Integer number;

    /**
     * 招募标签
     */
    private String tags;

    /**
     * 发布时间
     */
    private Date postTime;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 招募状态
     */
    private Integer recruitStatus;

    /**
     * 招募结束时间
     */
    private Date closedTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}