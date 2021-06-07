package cn.steateam.lets_team_server.vo;

import lombok.Data;

import java.util.Date;

/**
 * 项目招募需求详细信息VO
 *
 * @author YaoYi
 * @date 2021/3/17 1:13 下午
 */
@Data
public class ProjectRecruitingDetailedVo {

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
}
