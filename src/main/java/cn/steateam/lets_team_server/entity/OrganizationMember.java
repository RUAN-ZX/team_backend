package cn.steateam.lets_team_server.entity;

import java.util.Date;
import lombok.Data;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/30 12:17 上午
 */
@Data
public class OrganizationMember {
    /**
     * 组织id
     */
    private Integer orgId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 组织角色id
     */
    private Integer roleId;

    /**
     * 加入时间
     */
    private Date enteringTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}