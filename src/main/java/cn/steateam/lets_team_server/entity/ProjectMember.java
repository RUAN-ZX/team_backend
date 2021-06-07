package cn.steateam.lets_team_server.entity;

import java.util.Date;
import lombok.Data;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/30 12:19 上午
 */
@Data
public class ProjectMember {
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

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}