package cn.steateam.lets_team_server.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 组织成员创建DTO
 *
 * @author YaoYi
 * @date 2021/3/14 6:02 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrganizationMemberCreateDto extends OrganizationMemberEditDto{

    /**
     * 用户id
     */
    @NotNull(message = "项目成员用户id不能为空！")
    private Integer userId;
}
