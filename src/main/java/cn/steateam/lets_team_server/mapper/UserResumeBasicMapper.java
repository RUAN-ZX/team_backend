package cn.steateam.lets_team_server.mapper;

import cn.steateam.lets_team_server.entity.UserResumeBasic;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/30 12:25 上午
 */
public interface UserResumeBasicMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserResumeBasic record);

    int insertSelective(UserResumeBasic record);

    UserResumeBasic selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserResumeBasic record);

    int updateByPrimaryKey(UserResumeBasic record);
}