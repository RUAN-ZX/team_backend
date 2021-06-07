package cn.steateam.lets_team_server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ${description}
 * @author YaoYi
 * @date 2021/3/27 11:20 下午
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamRequirementCompetition {
    /**
    * 组队需求id
    */
    private Integer requirementId;

    /**
    * 竞赛id
    */
    private Integer compId;
}