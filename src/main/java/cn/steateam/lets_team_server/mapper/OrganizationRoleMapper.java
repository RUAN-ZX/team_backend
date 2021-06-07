package cn.steateam.lets_team_server.mapper;

import cn.steateam.lets_team_server.entity.OrganizationRole;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/10 3:19 下午
 */
public interface OrganizationRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrganizationRole record);

    int insertSelective(OrganizationRole record);

    OrganizationRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrganizationRole record);

    int updateByPrimaryKey(OrganizationRole record);
}