package cn.steateam.lets_team_server.mapper;
import cn.steateam.lets_team_server.entity.Competition;
import cn.steateam.lets_team_server.vo.CompetitionDetailedVo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/21 9:50 下午
 */
public interface CompetitionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Competition record);

    int insertSelective(Competition record);

    Competition selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Competition record);

    int updateByPrimaryKey(Competition record);

    CompetitionDetailedVo selectVoById(@Param("id")Integer id);

    List<Competition> selectByIdIn(@Param("idCollection")Collection<Integer> idCollection);
}