package cn.steateam.lets_team_server.mapper;
import cn.steateam.lets_team_server.vo.OrganizationDetailedVo;
import org.apache.ibatis.annotations.Param;

import cn.steateam.lets_team_server.entity.Organization;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/13 11:53 下午
 */
public interface OrganizationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Organization record);

    int insertSelective(Organization record);

    Organization selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Organization record);

    int updateByPrimaryKey(Organization record);

    OrganizationDetailedVo selectVoById(@Param("id")Integer id);
}