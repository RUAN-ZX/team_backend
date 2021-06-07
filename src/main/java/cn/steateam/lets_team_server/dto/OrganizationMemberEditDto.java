package cn.steateam.lets_team_server.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 组织成员编辑DTO
 *
 * @author YaoYi
 * @date 2021/3/14 5:59 下午
 */
@Data
public class OrganizationMemberEditDto {

    /**
     * 组织角色id
     */
    @NotNull(message = "组织角色id不能为空！")
    private Integer roleId;

    /**
     * 加入时间
     */
    @NotNull(message = "加入时间不能为空！")
    private Date enteringTime;
}
