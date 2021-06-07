package cn.steateam.lets_team_server.mapper;
import cn.steateam.lets_team_server.entity.CompetitionAnswer;
import cn.steateam.lets_team_server.vo.CompetitionAnswerVo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/19 12:35 上午
 */
public interface CompetitionAnswerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompetitionAnswer record);

    int insertSelective(CompetitionAnswer record);

    CompetitionAnswer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompetitionAnswer record);

    int updateByPrimaryKey(CompetitionAnswer record);

    Integer countByQuestionIdAndStatusNotIn(@Param("questionId")Integer questionId,@Param("statusCollection")Collection<Integer> statusCollection);

    List<CompetitionAnswerVo> selectVoByQuestionIdAndStatusIn(@Param("questionId")Integer questionId,@Param("statusCollection")Collection<Integer> statusCollection);

    CompetitionAnswerVo selectVoById(@Param("id") Integer id);
}