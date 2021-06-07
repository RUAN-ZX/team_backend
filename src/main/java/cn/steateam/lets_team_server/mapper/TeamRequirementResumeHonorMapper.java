package cn.steateam.lets_team_server.mapper;

import cn.steateam.lets_team_server.entity.TeamRequirementResumeHonor;import cn.steateam.lets_team_server.vo.TeamRequirementResumeHonorVo;import org.apache.ibatis.annotations.Param;import java.util.List;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/30 12:24 上午
 */
public interface TeamRequirementResumeHonorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TeamRequirementResumeHonor record);

    int insertSelective(TeamRequirementResumeHonor record);

    TeamRequirementResumeHonor selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TeamRequirementResumeHonor record);

    int updateByPrimaryKey(TeamRequirementResumeHonor record);

    int insertList(@Param("list") List<TeamRequirementResumeHonor> list);

    List<TeamRequirementResumeHonorVo> selectVoByRequirementId(@Param("requirementId") Integer requirementId);
}