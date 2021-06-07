package cn.steateam.lets_team_server.entity;

import lombok.Data;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/10 3:19 下午
 */
@Data
public class OrganizationRole {
    /**
     * 组织角色id
     */
    private Integer id;

    /**
     * 组织角色名称
     */
    private String name;
}