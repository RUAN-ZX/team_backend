package cn.steateam.lets_team_server.entity;

import java.util.Date;
import lombok.Data;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/13 10:56 下午
 */
@Data
public class Project {
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

    /**
     * 最近更新时间
     */
    private Date updateTime;
}