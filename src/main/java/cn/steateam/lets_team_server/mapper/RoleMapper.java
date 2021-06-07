package cn.steateam.lets_team_server.mapper;

import cn.steateam.lets_team_server.entity.Role;

/**
* ${description}
*
* @author STEA_YY
*/
public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}