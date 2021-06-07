package cn.steateam.lets_team_server.vo;

import lombok.Data;

import java.util.Date;

/**
 * 项目详细信息VO
 *
 * @author YaoYi
 * @date 2021/3/10 3:57 下午
 */
@Data
public class ProjectDetailedVo {
    /**
     * 项目id
     */
    private Integer id;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目头像url
     */
    private String avatarUrl;

    /**
     * 项目负责人用户id
     */
    private Integer leaderUserId;

    /**
     * 项目所在机构id
     */
    private Integer organizationId;

    /**
     * 项目标签
     */
    private String tags;

    /**
     * 项目类型
     */
    private Integer type;

    /**
     * 项目介绍
     */
    private String intro;

    /**
     * 项目状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;
}
