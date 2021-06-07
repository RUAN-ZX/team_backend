package cn.steateam.lets_team_server.mapper;

import cn.steateam.lets_team_server.entity.TeamRequirementResumeBasic;import cn.steateam.lets_team_server.vo.TeamRequirementResumeBasicVo;import org.apache.ibatis.annotations.Param;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/30 12:23 上午
 */
public interface TeamRequirementResumeBasicMapper {
    int deleteByPrimaryKey(Integer requirementId);

    int insert(TeamRequirementResumeBasic record);

    int insertSelective(TeamRequirementResumeBasic record);

    TeamRequirementResumeBasic selectByPrimaryKey(Integer requirementId);

    int updateByPrimaryKeySelective(TeamRequirementResumeBasic record);

    int updateByPrimaryKey(TeamRequirementResumeBasic record);

    TeamRequirementResumeBasicVo selectVoByRequirementId(@Param("requirementId") Integer requirementId);
}