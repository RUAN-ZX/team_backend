package cn.steateam.lets_team_server.mapper;

import cn.steateam.lets_team_server.entity.ProjectMember;
import cn.steateam.lets_team_server.vo.ProjectMemberVo;import org.apache.ibatis.annotations.Param;import java.util.List;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/30 12:19 上午
 */
public interface ProjectMemberMapper {
    int deleteByPrimaryKey(@Param("projectId") Integer projectId, @Param("userId") Integer userId);

    int insert(ProjectMember record);

    int insertSelective(ProjectMember record);

    ProjectMember selectByPrimaryKey(@Param("projectId") Integer projectId, @Param("userId") Integer userId);

    int updateByPrimaryKeySelective(ProjectMember record);

    int updateByPrimaryKey(ProjectMember record);

    List<ProjectMember> selectByProjectId(@Param("projectId") Integer projectId);

    List<ProjectMemberVo> selectVoByProjectId(@Param("projectId") Integer projectId);
}