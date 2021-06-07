package cn.steateam.lets_team_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 组织编辑DTO
 *
 * @author YaoYi
 * @date 2021/3/14 4:02 下午
 */
@Data
public class OrganizationEditDto {

    /**
     * 组织名称
     */
    @NotEmpty(message = "组织名称不能为空！")
    private String name;

    /**
     * 组织头像url
     */
    private String avatarUrl;

    /**
     * 组织介绍文案
     */
    @NotEmpty(message = "组织介绍文案不能为空！")
    private String intro;
}
