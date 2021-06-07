package cn.steateam.lets_team_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 项目成员DTO
 *
 * @author YaoYi
 * @date 2021/3/10 5:22 下午
 */
@Data
public class ProjectMemberEditDto {

    /**
     * 项目成员职责
     */
    @NotEmpty(message = "项目成员职责不能为空！")
    private String duty;

    /**
     * 项目成员权限等级
     */
    @NotNull(message = "项目成员权限等级不能为空！")
    private Integer permissionLevel;

    /**
     * 加入时间
     */
    @NotNull(message = "加入时间不能为空！")
    private Date enteringTime;

    /**
     * 成员顺位
     */
    @NotNull(message = "成员顺位不能为空！")
    private Integer ranking;
}
