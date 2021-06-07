package cn.steateam.lets_team_server.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 项目成员创建DTO
 *
 * @author STEA_YY
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectMemberCreateDto extends ProjectMemberEditDto {

    /**
     * 项目成员用户id
     */
    @NotNull(message = "项目成员用户id不能为空！")
    private Integer userId;
}
