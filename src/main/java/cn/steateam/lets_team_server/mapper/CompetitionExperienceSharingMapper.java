package cn.steateam.lets_team_server.mapper;
import cn.steateam.lets_team_server.vo.CompetitionExperienceSharingDetailedVo;
import org.apache.ibatis.annotations.Param;

import cn.steateam.lets_team_server.entity.CompetitionExperienceSharing;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/22 9:24 上午
 */
public interface CompetitionExperienceSharingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompetitionExperienceSharing record);

    int insertSelective(CompetitionExperienceSharing record);

    CompetitionExperienceSharing selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompetitionExperienceSharing record);

    int updateByPrimaryKey(CompetitionExperienceSharing record);

    CompetitionExperienceSharingDetailedVo selectVoById(@Param("id")Integer id);
}