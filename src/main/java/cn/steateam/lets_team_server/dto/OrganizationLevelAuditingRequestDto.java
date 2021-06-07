package cn.steateam.lets_team_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 组织等级审核请求DTO
 *
 * @author YaoYi
 * @date 2021/3/14 6:41 下午
 */
@Data
public class OrganizationLevelAuditingRequestDto {

    /**
     * 证明文件URL
     */
    @NotEmpty(message = "证明文件URL不能为空！")
    private String certificateUrl;

    /**
     * 组织级别
     */
    @NotNull(message = "组织级别不能为空！")
    private Integer level;
}
