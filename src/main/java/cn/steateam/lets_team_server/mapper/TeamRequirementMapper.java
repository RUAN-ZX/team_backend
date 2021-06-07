package cn.steateam.lets_team_server.mapper;

import cn.steateam.lets_team_server.entity.TeamRequirement;import cn.steateam.lets_team_server.vo.TeamRequirementDetailedVo;import org.apache.ibatis.annotations.Param;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/27 10:22 下午
 */
public interface TeamRequirementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TeamRequirement record);

    int insertSelective(TeamRequirement record);

    TeamRequirement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TeamRequirement record);

    int updateByPrimaryKey(TeamRequirement record);

    int deleteByPrimaryKey(@Param("id") Integer id, @Param("userId") Integer userId);

    TeamRequirement selectByPrimaryKey(@Param("id") Integer id, @Param("userId") Integer userId);

    TeamRequirementDetailedVo selectVoById(@Param("id") Integer id);
}