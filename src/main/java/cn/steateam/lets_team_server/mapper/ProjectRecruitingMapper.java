package cn.steateam.lets_team_server.mapper;
import cn.steateam.lets_team_server.entity.ProjectRecruiting;
import cn.steateam.lets_team_server.vo.ProjectRecruitingDetailedVo;
import org.apache.ibatis.annotations.Param;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/17 12:09 上午
 */
public interface ProjectRecruitingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectRecruiting record);

    int insertSelective(ProjectRecruiting record);

    ProjectRecruiting selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProjectRecruiting record);

    int updateByPrimaryKey(ProjectRecruiting record);

    int updateStatusByProjectIdAndStatus(@Param("updatedStatus")Integer updatedStatus,@Param("projectId")Integer projectId,@Param("status")Integer status);

    ProjectRecruitingDetailedVo selectVoById(@Param("id")Integer id);
}