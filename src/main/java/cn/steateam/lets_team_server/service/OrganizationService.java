package cn.steateam.lets_team_server.service;

import cn.steateam.lets_team_server.constant.OrganizationAuditingStatusEnum;
import cn.steateam.lets_team_server.constant.OrganizationLevelEnum;
import cn.steateam.lets_team_server.constant.OrganizationRoleEnum;
import cn.steateam.lets_team_server.constant.OrganizationStatusEnum;
import cn.steateam.lets_team_server.dto.*;
import cn.steateam.lets_team_server.entity.Organization;
import cn.steateam.lets_team_server.entity.OrganizationAuditingRequest;
import cn.steateam.lets_team_server.entity.OrganizationMember;
import cn.steateam.lets_team_server.entity.User;
import cn.steateam.lets_team_server.exception.DeleteException;
import cn.steateam.lets_team_server.exception.InsertException;
import cn.steateam.lets_team_server.exception.SelectException;
import cn.steateam.lets_team_server.exception.UpdateException;
import cn.steateam.lets_team_server.mapper.OrganizationAuditingRequestMapper;
import cn.steateam.lets_team_server.mapper.OrganizationMapper;
import cn.steateam.lets_team_server.mapper.OrganizationMemberMapper;
import cn.steateam.lets_team_server.vo.OrganizationDetailedVo;
import cn.steateam.lets_team_server.vo.OrganizationMemberVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 组织服务类
 *
 * @author STEA_YY
 **/
@Service
public class OrganizationService {
    @Resource
    private OrganizationMapper organizationMapper;
    @Resource
    private OrganizationMemberMapper organizationMemberMapper;
    @Resource
    private UserService userService;
    @Resource
    private OrganizationAuditingRequestMapper organizationAuditingRequestMapper;

    public OrganizationDetailedVo getVoById(Integer id) throws SelectException {
        Organization organization = getNotDeletedById(id);
        if (organization == null) {
            throw new SelectException("组织不存在！");
        }
        return organizationMapper.selectVoById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer save(OrganizationCreateDto organizationCreateDto, Integer userId) throws InsertException {
        boolean isPersonal = true;
        if (OrganizationLevelEnum.getByValue(organizationCreateDto.getLevel()) == null) {
            throw new InsertException("组织级别字段填写错误！");
        }
        Organization organization = new Organization();
        BeanUtils.copyProperties(organizationCreateDto, organization);
        organization.setCreatorUserId(userId);
        if (!organization.getLevel().equals(OrganizationLevelEnum.PERSONAL.getValue())) {
            organization.setStatus(OrganizationStatusEnum.WAITING_FOR_AUDITING.getValue());
            isPersonal = false;
            if (StringUtils.isEmpty(organizationCreateDto.getCertificateUrl())) {
                throw new InsertException("组织等级大于个人时，必须上传认证材料");
            }
        } else {
            organization.setStatus(OrganizationStatusEnum.NORMAL.getValue());
        }
        organizationMapper.insertSelective(organization);
        if (!isPersonal) {
            OrganizationAuditingRequest request = OrganizationAuditingRequest.builder()
                    .fileUrl(organizationCreateDto.getCertificateUrl())
                    .organizationId(organization.getId())
                    .level(organizationCreateDto.getLevel())
                    .userId(userId)
                    .status(OrganizationAuditingStatusEnum.WAITING_FOR_AUDITING.getValue())
                    .build();
            organizationAuditingRequestMapper.insertSelective(request);
        }
        return organization.getId();
    }

    public void update(OrganizationEditDto organizationEditDto, Integer id) throws UpdateException {
        Organization originalOrganization = getNotDeletedById(id);
        if (originalOrganization == null) {
            throw new UpdateException("组织不存在！");
        }
        Organization organization = new Organization();
        BeanUtils.copyProperties(organizationEditDto, organization);
        organizationMapper.updateByPrimaryKeySelective(organization);
    }

    public void delete(Integer id) throws DeleteException {
        Organization originalOrganization = getNotDeletedById(id);
        if (originalOrganization == null) {
            throw new DeleteException("组织不存在！");
        }
        Organization organization = new Organization();
        organization.setStatus(OrganizationStatusEnum.IS_DELETED.getValue());
        organizationMapper.updateByPrimaryKeySelective(organization);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveMemberByOrgId(OrganizationMemberCreateDto organizationMemberCreateDto, Integer orgId) throws InsertException {
        User user = userService.getUserById(organizationMemberCreateDto.getUserId());
        if (user == null) {
            throw new InsertException("用户不存在！");
        }
        Organization organization = getNotDeletedById(orgId);
        if (organization == null) {
            throw new InsertException("组织不存在！");
        }
        if (OrganizationRoleEnum.getByValue(organizationMemberCreateDto.getRoleId()) == null) {
            throw new InsertException("成员角色字段取值异常！");
        }
        OrganizationMember organizationMember = new OrganizationMember();
        BeanUtils.copyProperties(organizationMemberCreateDto, organizationMember);
        organizationMember.setOrgId(orgId);
        organizationMemberMapper.insertSelective(organizationMember);
        setUpdateTime(orgId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateMemberByOrgId(OrganizationMemberEditDto organizationMemberEditDto, Integer orgId, Integer userId) throws UpdateException {
        Organization organization = getNotDeletedById(orgId);
        if (organization == null) {
            throw new UpdateException("组织不存在！");
        }
        OrganizationMember originalOrganizationMember = organizationMemberMapper.selectByPrimaryKey(orgId, userId);
        if (originalOrganizationMember == null) {
            throw new UpdateException("组织成员不存在！");
        }
        if (organizationMemberEditDto.getRoleId() != null) {
            if (OrganizationRoleEnum.getByValue(organizationMemberEditDto.getRoleId()) == null) {
                throw new UpdateException("成员角色字段取值异常！");
            }
        }
        OrganizationMember organizationMember = new OrganizationMember();
        BeanUtils.copyProperties(organizationMemberEditDto, organizationMember);
        organizationMember.setOrgId(orgId);
        organizationMember.setUserId(userId);
        organizationMemberMapper.updateByPrimaryKeySelective(organizationMember);
        setUpdateTime(orgId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteMemberByOrgId(Integer orgId, Integer userId) throws DeleteException {
        Organization organization = getNotDeletedById(orgId);
        if (organization == null) {
            throw new DeleteException("组织不存在！");
        }
        OrganizationMember originalOrganizationMember = organizationMemberMapper.selectByPrimaryKey(orgId, userId);
        if (originalOrganizationMember == null) {
            throw new DeleteException("组织成员不存在！");
        }
        organizationMemberMapper.deleteByPrimaryKey(orgId, userId);
        setUpdateTime(orgId);
    }

    public List<OrganizationMemberVo> getAllMemberVoByOrgId(Integer orgId) throws SelectException {
        Organization organization = getNotDeletedById(orgId);
        if (organization == null) {
            throw new SelectException("组织不存在！");
        }
        return organizationMemberMapper.selectVoByOrgId(orgId);
    }

    public Integer saveAuditingRequestByOrgId(OrganizationLevelAuditingRequestDto organizationLevelAuditingRequestDto, Integer orgId, Integer userId) throws InsertException {
        Organization organization = getNotDeletedById(orgId);
        if (organization == null) {
            throw new InsertException("组织不存在！");
        }
        if (organization.getStatus() < OrganizationStatusEnum.NORMAL.getValue()) {
            throw new InsertException("组织当前处于异常情况，无法申请！");
        }
        if (OrganizationLevelEnum.getByValue(organizationLevelAuditingRequestDto.getLevel()) == null) {
            throw new InsertException("组织级别字段填写错误！");
        }
        if (!organizationAuditingRequestMapper.selectByOrganizationIdAndStatus(orgId, OrganizationAuditingStatusEnum.WAITING_FOR_AUDITING.getValue()).isEmpty()) {
            throw new InsertException("本组织当前有待审核的请求！");
        }
        OrganizationAuditingRequest request = OrganizationAuditingRequest.builder()
                .fileUrl(organizationLevelAuditingRequestDto.getCertificateUrl())
                .organizationId(organization.getId())
                .level(organizationLevelAuditingRequestDto.getLevel())
                .userId(userId)
                .status(OrganizationAuditingStatusEnum.WAITING_FOR_AUDITING.getValue())
                .build();
        organizationAuditingRequestMapper.insertSelective(request);
        return request.getId();
    }

    public boolean hasPermission(Integer userId, Integer orgId, Integer roleId) {
        Organization organization = getNotDeletedById(orgId);
        if (organization == null) {
            return false;
        }
        if (organization.getCreatorUserId().equals(userId)) {
            return true;
        }
        OrganizationMember organizationMember = organizationMemberMapper.selectByPrimaryKey(orgId, userId);
        if (organizationMember == null) {
            return false;
        }
        return organizationMember.getRoleId() >= roleId;
    }

    public Organization getNotDeletedById(Integer id) {
        Organization organization = organizationMapper.selectByPrimaryKey(id);
        if (organization == null) {
            return null;
        }
        if (organization.getStatus().equals(OrganizationStatusEnum.IS_DELETED.getValue())) {
            return null;
        }
        return organization;
    }

    private void setUpdateTime(Integer orgId) {
        Organization updatedOrganization = new Organization();
        updatedOrganization.setId(orgId);
        if (updatedOrganization.getStatus() < 0) {
            updatedOrganization.setStatus(OrganizationStatusEnum.WAITING_FOR_AUDITING.getValue());
        }
        organizationMapper.updateByPrimaryKeySelective(updatedOrganization);
    }
}
