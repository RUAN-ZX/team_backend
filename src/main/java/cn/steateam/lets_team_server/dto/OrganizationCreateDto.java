package cn.steateam.lets_team_server.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 组织创建DTO
 *
 * @author YaoYi
 * @date 2021/3/13 11:58 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrganizationCreateDto extends OrganizationEditDto{

    /**
     * 证明文件URL
     */
    private String certificateUrl;

    /**
     * 组织级别
     */
    @NotNull(message = "组织级别不能为空！")
    private Integer level;
}
