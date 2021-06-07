package cn.steateam.lets_team_server.mapper;

import cn.steateam.lets_team_server.entity.OrganizationMember;
import cn.steateam.lets_team_server.vo.OrganizationMemberVo;import org.apache.ibatis.annotations.Param;import java.util.List;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/30 12:17 上午
 */
public interface OrganizationMemberMapper {
    int deleteByPrimaryKey(@Param("orgId") Integer orgId, @Param("userId") Integer userId);

    int insert(OrganizationMember record);

    int insertSelective(OrganizationMember record);

    OrganizationMember selectByPrimaryKey(@Param("orgId") Integer orgId, @Param("userId") Integer userId);

    int updateByPrimaryKeySelective(OrganizationMember record);

    int updateByPrimaryKey(OrganizationMember record);

    int deleteByPrimaryKey(Integer orgId);

    List<OrganizationMemberVo> selectVoByOrgId(@Param("orgId") Integer orgId);
}