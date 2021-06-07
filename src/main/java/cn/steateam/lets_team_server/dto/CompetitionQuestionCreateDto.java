package cn.steateam.lets_team_server.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 竞赛提问创建DTO
 *
 * @author YaoYi
 * @date 2021/3/22 12:47 上午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CompetitionQuestionCreateDto extends CompetitionQuestionEditDto{
    /**
     * 竞赛id
     */
    @NotNull(message = "竞赛id不能为空！")
    private Integer compId;
}
