package cn.steateam.lets_team_server.entity;

import lombok.Data;

import java.util.Date;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/13 11:53 下午
 */
@Data
public class Organization {
    /**
     * 组织id
     */
    private Integer id;

    /**
     * 组织名称
     */
    private String name;

    /**
     * 组织头像url
     */
    private String avatarUrl;

    /**
     * 组织介绍文案
     */
    private String intro;

    /**
     * 组织级别
     */
    private Integer level;

    /**
     * 组织状态
     */
    private Integer status;

    /**
     * 组织创建时间
     */
    private Date createTime;

    /**
     * 组织创建人用户id
     */
    private Integer creatorUserId;

    /**
     * 组织更新时间
     */
    private Date updateTime;
}