package cn.steateam.lets_team_server.mapper;

import cn.steateam.lets_team_server.entity.UserInfo;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/30 12:25 上午
 */
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
}