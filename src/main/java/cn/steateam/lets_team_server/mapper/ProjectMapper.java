package cn.steateam.lets_team_server.mapper;

import cn.steateam.lets_team_server.entity.Project;import cn.steateam.lets_team_server.vo.ProjectDetailedVo;import org.apache.ibatis.annotations.Param;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/13 10:56 下午
 */
public interface ProjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);

    ProjectDetailedVo selectVoById(@Param("id") Integer id);

    int updateStatusById(@Param("updatedStatus")Integer updatedStatus,@Param("id")Integer id);


}