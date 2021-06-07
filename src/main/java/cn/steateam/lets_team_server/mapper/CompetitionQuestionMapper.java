package cn.steateam.lets_team_server.mapper;
import cn.steateam.lets_team_server.vo.CompetitionQuestionVo;
import org.apache.ibatis.annotations.Param;

import cn.steateam.lets_team_server.entity.CompetitionQuestion;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/18 11:22 下午
 */
public interface CompetitionQuestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompetitionQuestion record);

    int insertSelective(CompetitionQuestion record);

    CompetitionQuestion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompetitionQuestion record);

    int updateByPrimaryKey(CompetitionQuestion record);

    CompetitionQuestionVo selectVoById(@Param("id")Integer id);
}