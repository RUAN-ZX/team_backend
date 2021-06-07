package cn.steateam.lets_team_server.controller;

import cn.steateam.lets_team_server.annotation.RequiresLogin;
import cn.steateam.lets_team_server.constant.OrganizationRoleEnum;
import cn.steateam.lets_team_server.constant.OrganizationStatusEnum;
import cn.steateam.lets_team_server.dto.*;
import cn.steateam.lets_team_server.exception.*;
import cn.steateam.lets_team_server.service.OrganizationService;
import cn.steateam.lets_team_server.util.ThreadLocalUtil;
import cn.steateam.lets_team_server.vo.OrganizationDetailedVo;
import cn.steateam.lets_team_server.vo.OrganizationMemberVo;
import cn.steateam.lets_team_server.vo.ResponseBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 组织类请求
 *
 * @author YaoYi
 * @date 2021/3/15 10:55 下午
 */
@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @Resource
    private OrganizationService organizationService;

    /**
     * 根据id获取组织信息
     *
     * @param id 组织id
     */
    @RequiresLogin
    @GetMapping("/{id}")
    public ResponseBean<OrganizationDetailedVo> getVoById(@PathVariable int id) throws SelectException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        OrganizationDetailedVo organizationDetailedVo = getDetailedVoWithPermission(userId, id);
        return new ResponseBean<>(organizationDetailedVo);
    }

    /**
     * 根据id获取组织成员列表
     *
     * @param id 组织id
     */
    @RequiresLogin
    @GetMapping("/{id}/members")
    public ResponseBean<List<OrganizationMemberVo>> getMemberVosById(@PathVariable int id) throws SelectException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        getDetailedVoWithPermission(userId, id);
        List<OrganizationMemberVo> organizationMemberVos = organizationService.getAllMemberVoByOrgId(id);
        return new ResponseBean<>(organizationMemberVos);
    }

    /**
     * 创建组织
     *
     * @param organizationCreateDto 组织创建DTO
     */
    @RequiresLogin
    @PostMapping()
    public ResponseBean<Integer> save(@Validated @RequestBody OrganizationCreateDto organizationCreateDto) throws InsertException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        Integer insertedId = organizationService.save(organizationCreateDto, userId);
        return new ResponseBean<>(insertedId);
    }

    /**
     * 修改组织信息
     *
     * @param organizationEditDto 组织修改DTO
     * @param id                  组织id
     */
    @RequiresLogin
    @PutMapping("/{id}")
    public ResponseBean<Object> update(@RequestBody OrganizationEditDto organizationEditDto, @PathVariable int id) throws PermissionDeniedException, UpdateException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean hasPermission = organizationService.hasPermission(userId, id, OrganizationRoleEnum.EDITOR.getValue());
        if (!hasPermission) {
            throw new PermissionDeniedException("您无权限进行此操作！");
        }
        organizationService.update(organizationEditDto, id);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 删除组织
     *
     * @param id 组织id
     */
    @RequiresLogin
    @DeleteMapping("/{id}")
    public ResponseBean<Object> delete(@PathVariable int id) throws PermissionDeniedException, DeleteException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean hasPermission = organizationService.hasPermission(userId, id, OrganizationRoleEnum.ADMIN.getValue());
        if (!hasPermission) {
            throw new PermissionDeniedException("您无权限进行此操作！");
        }
        organizationService.delete(id);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 添加组织成员
     *
     * @param organizationMemberCreateDto 组织成员创建DTO
     * @param orgId                       组织id
     */
    @RequiresLogin
    @PostMapping("/{orgId}/members")
    public ResponseBean<Object> saveMember(@Validated @RequestBody OrganizationMemberCreateDto organizationMemberCreateDto, @PathVariable int orgId) throws PermissionDeniedException, InsertException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean hasPermission = organizationService.hasPermission(userId, orgId, OrganizationRoleEnum.AUDITOR.getValue());
        if (!hasPermission) {
            throw new PermissionDeniedException("您无权限进行此操作！");
        }
        organizationService.saveMemberByOrgId(organizationMemberCreateDto, orgId);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 修改组织成员信息
     *
     * @param organizationMemberEditDto 组织成员修改DTO
     * @param orgId                     组织id
     * @param memberUserId              组织成员用户id
     */
    @RequiresLogin
    @PutMapping("/{orgId}/members/{memberUserId}")
    public ResponseBean<Object> updateMember(@RequestBody OrganizationMemberEditDto organizationMemberEditDto, @PathVariable int orgId, @PathVariable int memberUserId) throws PermissionDeniedException, UpdateException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean hasPermission = organizationService.hasPermission(userId, orgId, OrganizationRoleEnum.AUDITOR.getValue());
        if (!hasPermission) {
            throw new PermissionDeniedException("您无权限进行此操作！");
        }
        organizationService.updateMemberByOrgId(organizationMemberEditDto, orgId, memberUserId);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 删除组织成员
     *
     * @param orgId        组织id
     * @param memberUserId 组织成员用户id
     */
    @RequiresLogin
    @DeleteMapping("/{orgId}/members/{memberUserId}")
    public ResponseBean<Object> deleteMember(@PathVariable int orgId, @PathVariable int memberUserId) throws PermissionDeniedException, DeleteException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean hasPermission = organizationService.hasPermission(userId, orgId, OrganizationRoleEnum.AUDITOR.getValue());
        if (!hasPermission) {
            throw new PermissionDeniedException("您无权限进行此操作！");
        }
        organizationService.deleteMemberByOrgId(orgId, memberUserId);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 请求变更组织等级
     *
     * @param organizationLevelAuditingRequestDto 组织等级审核请求DTO
     * @param orgId                               组织id
     */
    @RequiresLogin
    @PostMapping("/auditingRequest/{orgId}")
    public ResponseBean<Integer> saveAuditingRequestById(@Validated OrganizationLevelAuditingRequestDto organizationLevelAuditingRequestDto, @PathVariable int orgId) throws InsertException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean hasPermission = organizationService.hasPermission(userId, orgId, OrganizationRoleEnum.ADMIN.getValue());
        if (!hasPermission) {
            throw new PermissionDeniedException("您无权限进行此操作！");
        }
        Integer insertedId = organizationService.saveAuditingRequestByOrgId(organizationLevelAuditingRequestDto, orgId, userId);
        return new ResponseBean<>(insertedId);
    }

    private OrganizationDetailedVo getDetailedVoWithPermission(Integer userId, Integer orgId) throws SelectException, PermissionDeniedException {
        OrganizationDetailedVo organizationDetailedVo = organizationService.getVoById(orgId);
        if (organizationDetailedVo.getStatus() < OrganizationStatusEnum.NORMAL.getValue()) {
            boolean hasPermission = organizationService.hasPermission(userId, orgId, OrganizationRoleEnum.NORMAL.getValue());
            if (!hasPermission) {
                throw new PermissionDeniedException("组织暂时无法访问");
            }
        }
        return organizationDetailedVo;
    }
}
