package cn.steateam.lets_team_server.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 组队需求DTO
 *
 * @author YaoYi
 * @date 2021/3/17 2:35 下午
 */
@Data
public class TeamRequirementDto {

    /**
     * 竞赛id列表
     */
    private List<Integer> compIds;

    /**
     * 用户标签
     */
    @NotEmpty(message = "用户标签不能为空！")
    private String tags;

    /**
     * 组队需求简介
     */
    @NotEmpty(message = "组队需求简介不能为空！")
    private String intro;

    /**
     * 组队需求正文
     */
    @NotEmpty(message = "组队需求正文不能为空！")
    private String content;

    /**
     * 组队需求简历
     */
    @Valid
    @NotNull(message = "组队需求简历不能为空！")
    private TeamRequirementResumeDto resume;
}
