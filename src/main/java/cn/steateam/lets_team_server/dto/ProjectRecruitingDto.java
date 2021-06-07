package cn.steateam.lets_team_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 项目招募需求DTO
 *
 * @author YaoYi
 * @date 2021/3/16 5:40 下午
 */
@Data
public class ProjectRecruitingDto {

    /**
     * 对应项目id
     */
    @NotNull(message = "项目id不能为空！")
    private Integer projectId;

    /**
     * 招募详细需求
     */
    @NotEmpty(message = "招募详细需求不能为空！")
    private String content;

    /**
     * 招募人数
     */
    @NotNull(message = "招募人数不能为空！")
    private Integer number;

    /**
     * 招募标签
     */
    @NotEmpty(message = "招募标签不能为空！")
    private String tags;
}
