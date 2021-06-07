package cn.steateam.lets_team_server.mapper;

import cn.steateam.lets_team_server.entity.User;

/**
* ${description}
*
* @author STEA_YY
*/
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}