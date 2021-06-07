package cn.steateam.lets_team_server.mapper;

import cn.steateam.lets_team_server.entity.ProjectHonor;import cn.steateam.lets_team_server.vo.ProjectHonorVo;import org.apache.ibatis.annotations.Param;import java.util.List;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/30 12:18 上午
 */
public interface ProjectHonorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectHonor record);

    int insertSelective(ProjectHonor record);

    ProjectHonor selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProjectHonor record);

    int updateByPrimaryKey(ProjectHonor record);

    List<ProjectHonor> selectByProjectId(@Param("projectId") Integer projectId);

    List<ProjectHonorVo> selectVoByProjectId(@Param("projectId") Integer projectId);
}