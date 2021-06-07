package cn.steateam.lets_team_server.controller;

import cn.steateam.lets_team_server.annotation.RequiresLogin;
import cn.steateam.lets_team_server.constant.ProjectPermissionLevelEnum;
import cn.steateam.lets_team_server.constant.ProjectStatusEnum;
import cn.steateam.lets_team_server.dto.ProjectDto;
import cn.steateam.lets_team_server.dto.ProjectHonorDto;
import cn.steateam.lets_team_server.dto.ProjectMemberCreateDto;
import cn.steateam.lets_team_server.dto.ProjectMemberEditDto;
import cn.steateam.lets_team_server.exception.*;
import cn.steateam.lets_team_server.service.ProjectService;
import cn.steateam.lets_team_server.util.ThreadLocalUtil;
import cn.steateam.lets_team_server.vo.ProjectDetailedVo;
import cn.steateam.lets_team_server.vo.ProjectHonorVo;
import cn.steateam.lets_team_server.vo.ProjectMemberVo;
import cn.steateam.lets_team_server.vo.ResponseBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目类请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/project")
public class ProjectController {
    @Resource
    private ProjectService projectService;

    /**
     * 根据id获取项目
     *
     * @param id 项目id
     */
    @RequiresLogin
    @GetMapping("/{id}")
    public ResponseBean<ProjectDetailedVo> getVoById(@PathVariable int id) throws SelectException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        ProjectDetailedVo projectDetailedVo = getDetailedVoWithPermission(userId, id);
        return new ResponseBean<>(projectDetailedVo);
    }

    /**
     * 根据项目id获取所有项目成员
     *
     * @param id 项目id
     */
    @RequiresLogin
    @GetMapping("/{id}/members")
    public ResponseBean<List<ProjectMemberVo>> getMemberVosById(@PathVariable int id) throws SelectException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        getDetailedVoWithPermission(userId, id);
        List<ProjectMemberVo> projectMemberVos = projectService.getAllMemberVoByProjectId(id);
        return new ResponseBean<>(projectMemberVos);
    }

    /**
     * 根据项目id获取全部荣誉
     *
     * @param id 项目id
     */
    @RequiresLogin
    @GetMapping("/{id}/honors")
    public ResponseBean<List<ProjectHonorVo>> getHonorVosById(@PathVariable int id) throws SelectException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        getDetailedVoWithPermission(userId, id);
        List<ProjectHonorVo> projectHonorVos = projectService.getAllHonorVoByProjectId(id);
        return new ResponseBean<>(projectHonorVos);
    }

    /**
     * 创建项目
     *
     * @param projectDto 项目DTO
     */
    @RequiresLogin
    @PostMapping()
    public ResponseBean<Integer> save(@Validated @RequestBody ProjectDto projectDto) {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        Integer insertedId = projectService.save(projectDto, userId);
        return new ResponseBean<>(insertedId);
    }

    /**
     * 修改项目
     *
     * @param projectDto 项目DTO
     * @param id         项目id
     */
    @RequiresLogin
    @PutMapping("/{id}")
    public ResponseBean<Object> update(@RequestBody ProjectDto projectDto, @PathVariable int id) throws PermissionDeniedException, UpdateException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean hasPermission = projectService.hasPermission(userId, id, ProjectPermissionLevelEnum.EDIT.getValue());
        if (!hasPermission) {
            throw new PermissionDeniedException("您无权限进行此操作！");
        }
        projectService.update(projectDto, id);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 删除项目
     *
     * @param id 项目id
     */
    @RequiresLogin
    @DeleteMapping("/{id}")
    public ResponseBean<Object> delete(@PathVariable int id) throws PermissionDeniedException, DeleteException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean hasPermission = projectService.hasPermission(userId, id, ProjectPermissionLevelEnum.MANAGEMENT.getValue());
        if (!hasPermission) {
            throw new PermissionDeniedException("您无权限进行此操作！");
        }
        projectService.delete(id);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 添加项目成员
     *
     * @param projectMemberCreateDto 项目成员创建DTO
     * @param projectId              项目id
     */
    @RequiresLogin
    @PostMapping("/{projectId}/members")
    public ResponseBean<Object> saveMember(@Validated @RequestBody ProjectMemberCreateDto projectMemberCreateDto, @PathVariable int projectId) throws PermissionDeniedException, InsertException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean hasPermission = projectService.hasPermission(userId, projectId, ProjectPermissionLevelEnum.MEMBER_EDIT.getValue());
        if (!hasPermission) {
            throw new PermissionDeniedException("您无权限进行此操作！");
        }
        boolean hasPermissionToOperateMember = projectService.hasPermissionToOperateMember(projectId, userId, null, projectMemberCreateDto.getPermissionLevel());
        if (!hasPermissionToOperateMember) {
            throw new PermissionDeniedException("您无权限进行此操作！");
        }
        projectService.saveMemberByProjectId(projectMemberCreateDto, projectId);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 修改项目成员信息
     *
     * @param projectMemberEditDto 项目成员修改DTO
     * @param projectId            项目id
     * @param memberUserId         项目成员用户id
     */
    @RequiresLogin
    @PutMapping("/{projectId}/members/{memberUserId}")
    public ResponseBean<Object> updateMember(@RequestBody ProjectMemberEditDto projectMemberEditDto, @PathVariable int projectId, @PathVariable int memberUserId) throws PermissionDeniedException, UpdateException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean hasPermission = projectService.hasPermission(userId, projectId, ProjectPermissionLevelEnum.MEMBER_EDIT.getValue());
        if (!hasPermission) {
            throw new PermissionDeniedException("您无权限进行此操作！");
        }
        boolean hasPermissionToOperateMember = projectService.hasPermissionToOperateMember(projectId, userId, memberUserId, projectMemberEditDto.getPermissionLevel());
        if (!hasPermissionToOperateMember) {
            throw new PermissionDeniedException("您无权限进行此操作！");
        }
        projectService.updateMemberByProjectId(projectMemberEditDto, projectId, memberUserId);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 删除项目成员
     *
     * @param projectId    项目id
     * @param memberUserId 项目成员用户id
     */
    @RequiresLogin
    @DeleteMapping("/{projectId}/members/{memberUserId}")
    public ResponseBean<Object> deleteMember(@PathVariable int projectId, @PathVariable int memberUserId) throws PermissionDeniedException, DeleteException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean hasPermission = projectService.hasPermission(userId, projectId, ProjectPermissionLevelEnum.MEMBER_EDIT.getValue());
        if (!hasPermission) {
            throw new PermissionDeniedException("您无权限进行此操作！");
        }
        boolean hasPermissionToOperateMember = projectService.hasPermissionToOperateMember(projectId, userId, memberUserId, null);
        if (!hasPermissionToOperateMember) {
            throw new PermissionDeniedException("您无权限进行此操作！");
        }
        projectService.deleteMemberByProjectId(projectId, memberUserId);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 添加项目荣誉
     *
     * @param projectHonorDto 项目荣誉DTO
     * @param projectId       项目id
     */
    @RequiresLogin
    @PostMapping("/{projectId}/honors")
    public ResponseBean<Integer> saveHonor(@Validated @RequestBody ProjectHonorDto projectHonorDto, @PathVariable int projectId) throws PermissionDeniedException, InsertException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean hasPermission = projectService.hasPermission(userId, projectId, ProjectPermissionLevelEnum.EDIT.getValue());
        if (!hasPermission) {
            throw new PermissionDeniedException("您无权限进行此操作！");
        }
        Integer insertedId = projectService.saveHonorByProjectId(projectHonorDto, projectId);
        return new ResponseBean<>(insertedId);
    }

    /**
     * 修改项目荣誉
     *
     * @param projectHonorDto 项目荣誉DTO
     * @param projectId       项目id
     * @param honorId         项目荣誉id
     */
    @RequiresLogin
    @PutMapping("/{projectId}/honors/{honorId}")
    public ResponseBean<Object> updateHonor(@RequestBody ProjectHonorDto projectHonorDto, @PathVariable int projectId, @PathVariable int honorId) throws PermissionDeniedException, UpdateException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean hasPermission = projectService.hasPermission(userId, projectId, ProjectPermissionLevelEnum.EDIT.getValue());
        if (!hasPermission) {
            throw new PermissionDeniedException("您无权限进行此操作！");
        }
        projectService.updateHonorByProjectId(projectHonorDto, projectId, honorId);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 删除项目荣誉
     *
     * @param projectId 项目id
     * @param honorId   项目荣誉id
     */
    @RequiresLogin
    @DeleteMapping("/{projectId}/honors/{honorId}")
    public ResponseBean<Object> deleteHonor(@PathVariable int projectId, @PathVariable int honorId) throws PermissionDeniedException, DeleteException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean hasPermission = projectService.hasPermission(userId, projectId, ProjectPermissionLevelEnum.EDIT.getValue());
        if (!hasPermission) {
            throw new PermissionDeniedException("您无权限进行此操作！");
        }
        projectService.deleteHonorByProjectId(projectId, honorId);
        return ResponseBean.emptySuccessResponse();
    }

    private ProjectDetailedVo getDetailedVoWithPermission(Integer userId, Integer projectId) throws SelectException, PermissionDeniedException {
        ProjectDetailedVo projectDetailedVo = projectService.getVoById(projectId);
        if (projectDetailedVo.getStatus() < ProjectStatusEnum.NORMAL.getValue()) {
            boolean hasPermission = projectService.hasPermission(userId, projectId, ProjectPermissionLevelEnum.NORMAL.getValue());
            if (!hasPermission) {
                throw new PermissionDeniedException("项目暂时无法访问");
            }
        }
        return projectDetailedVo;
    }
}
