package cn.steateam.lets_team_server.vo;

import lombok.Data;

/**
 * 项目粗略信息VO
 *
 * @author YaoYi
 * @date 2021/3/10 3:52 下午
 */
@Data
public class ProjectRoughVo {
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
     * 项目状态
     */
    private Integer status;
}
