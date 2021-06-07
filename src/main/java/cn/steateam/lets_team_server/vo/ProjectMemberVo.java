package cn.steateam.lets_team_server.vo;

import lombok.Data;

import java.util.Date;

/**
 * 项目成员VO
 *
 * @author YaoYi
 * @date 2021/3/10 4:48 下午
 */
@Data
public class ProjectMemberVo {
    /**
     * 项目表主键
     */
    private Integer projectId;

    /**
     * 项目成员用户id
     */
    private Integer userId;

    /**
     * 项目成员职责
     */
    private String duty;

    /**
     * 项目成员权限等级
     */
    private Integer permissionLevel;

    /**
     * 加入时间
     */
    private Date enteringTime;

    /**
     * 成员顺位
     */
    private Integer ranking;
}
