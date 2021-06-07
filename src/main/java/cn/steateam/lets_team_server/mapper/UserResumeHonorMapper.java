package cn.steateam.lets_team_server.mapper;

import cn.steateam.lets_team_server.entity.UserResumeHonor;import org.apache.ibatis.annotations.Param;import java.util.List;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/30 12:26 上午
 */
public interface UserResumeHonorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserResumeHonor record);

    int insertSelective(UserResumeHonor record);

    UserResumeHonor selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserResumeHonor record);

    int updateByPrimaryKey(UserResumeHonor record);

    List<UserResumeHonor> selectByUserId(@Param("userId") Integer userId);
}