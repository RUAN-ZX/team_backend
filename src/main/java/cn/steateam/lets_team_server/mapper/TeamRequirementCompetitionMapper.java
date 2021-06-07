package cn.steateam.lets_team_server.mapper;
import cn.steateam.lets_team_server.entity.TeamRequirementCompetition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ${description}
 * @author YaoYi
 * @date 2021/3/27 11:20 下午
 */
public interface TeamRequirementCompetitionMapper {
    int deleteByPrimaryKey(@Param("requirementId") Integer requirementId, @Param("compId") Integer compId);

    int insert(TeamRequirementCompetition record);

    int insertSelective(TeamRequirementCompetition record);

    int insertList(@Param("list")List<TeamRequirementCompetition> list);

    List<Integer> selectCompIdByRequirementId(@Param("requirementId")Integer requirementId);


}