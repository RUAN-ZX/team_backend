package cn.steateam.lets_team_server.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 竞赛经验分享帖创建DTO
 *
 * @author YaoYi
 * @date 2021/3/23 9:21 上午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CompetitionExperienceSharingCreateDto extends CompetitionExperienceSharingEditDto{
    /**
     * 竞赛id
     */
    @NotNull(message = "竞赛id不能为空！")
    private Integer compId;
}
