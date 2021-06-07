package cn.steateam.lets_team_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 组队需求简历荣誉DTO
 *
 * @author YaoYi
 * @date 2021/3/20 9:54 下午
 */
@Data
public class TeamRequirementResumeHonorDto {
    /**
     * 荣誉名称
     */
    @NotEmpty(message = "荣誉名称不能为空！")
    private String name;

    /**
     * 获得时间
     */
    @NotNull(message = "荣誉获得时间不能为空！")
    private Date acquiringTime;

    /**
     * 证明文件url
     */
    private String supportingDocumentUrl;

    /**
     * 荣誉描述
     */
    @NotEmpty(message = "荣誉描述不能为空！")
    private String intro;
}
