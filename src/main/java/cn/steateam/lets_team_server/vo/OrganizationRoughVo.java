package cn.steateam.lets_team_server.vo;

import lombok.Data;

/**
 * 组织粗略信息VO
 *
 * @author YaoYi
 * @date 2021/3/15 11:04 下午
 */
@Data
public class OrganizationRoughVo {
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
     * 组织级别
     */
    private Integer level;

    /**
     * 组织状态
     */
    private Integer status;
}
