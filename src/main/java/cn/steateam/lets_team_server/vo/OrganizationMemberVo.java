package cn.steateam.lets_team_server.vo;

import lombok.Data;

import java.util.Date;

/**
 * 组织成员VO
 *
 * @author YaoYi
 * @date 2021/3/14 6:25 下午
 */
@Data
public class OrganizationMemberVo {

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
}
