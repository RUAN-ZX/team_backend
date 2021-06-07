package cn.steateam.lets_team_server.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import cn.steateam.lets_team_server.entity.OrganizationAuditingRequest;

/**
 * ${description}
 * @author YaoYi
 * @date 2021/3/14 12:18 上午
 */
public interface OrganizationAuditingRequestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrganizationAuditingRequest record);

    int insertSelective(OrganizationAuditingRequest record);

    OrganizationAuditingRequest selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrganizationAuditingRequest record);

    int updateByPrimaryKey(OrganizationAuditingRequest record);

    List<OrganizationAuditingRequest> selectByOrganizationIdAndStatus(@Param("organizationId")Integer organizationId,@Param("status")Integer status);


}