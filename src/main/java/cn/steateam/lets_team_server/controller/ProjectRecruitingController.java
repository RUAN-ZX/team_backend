package cn.steateam.lets_team_server.controller;

import cn.steateam.lets_team_server.constant.ProjectPermissionLevelEnum;
import cn.steateam.lets_team_server.constant.ProjectRecruitingStatusEnum;
import cn.steateam.lets_team_server.dto.ProjectRecruitingDto;
import cn.steateam.lets_team_server.exception.*;
import cn.steateam.lets_team_server.service.ProjectRecruitingService;
import cn.steateam.lets_team_server.service.ProjectService;
import cn.steateam.lets_team_server.util.ThreadLocalUtil;
import cn.steateam.lets_team_server.vo.ProjectRecruitingDetailedVo;
import cn.steateam.lets_team_server.vo.ResponseBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 项目招募类请求
 *
 * @author YaoYi
 * @date 2021/3/20 1:47 上午
 */
@RestController
@RequestMapping("/recruiting")
public class ProjectRecruitingController {
    @Resource
    private ProjectRecruitingService projectRecruitingService;
    @Resource
    private ProjectService projectService;

    /**
     * 根据id获取项目招募帖
     *
     * @param id 项目招募帖id
     */
    @GetMapping("/{id}")
    public ResponseBean<ProjectRecruitingDetailedVo> getVoById(@PathVariable int id) throws SelectException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        ProjectRecruitingDetailedVo recruitingDetailedVo = getDetailedVoWithPermission(userId, id);
        return new ResponseBean<>(recruitingDetailedVo);
    }

    /**
     * 创建项目招募贴
     *
     * @param projectRecruitingDto 项目招募贴DTO
     */
    @PostMapping
    public ResponseBean<Integer> save(@Validated @RequestBody ProjectRecruitingDto projectRecruitingDto) throws PermissionDeniedException, InsertException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean hasPermission = projectService.hasPermission(userId, projectRecruitingDto.getProjectId(), ProjectPermissionLevelEnum.NORMAL.getValue());
        if (!hasPermission) {
            throw new PermissionDeniedException("您无权限执行本操作！");
        }
        Integer insertedId = projectRecruitingService.save(projectRecruitingDto, userId);
        return new ResponseBean<>(insertedId);
    }

    /**
     * 关闭项目招募贴
     *
     * @param id          项目招募贴id
     * @param closeStatus 关闭状态
     */
    @PutMapping("/{id}/close")
    public ResponseBean<Object> close(@PathVariable int id, @RequestParam int closeStatus) throws UpdateException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean hasPermission = projectRecruitingService.hasPermission(userId, id);
        if (!hasPermission) {
            throw new PermissionDeniedException("您无权限执行本操作！");
        }
        projectRecruitingService.close(id, closeStatus);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 删除项目招募贴
     *
     * @param id 项目招募贴id
     */
    @DeleteMapping("/{id}")
    public ResponseBean<Object> delete(@PathVariable int id) throws DeleteException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean hasPermission = projectRecruitingService.hasPermission(userId, id);
        if (!hasPermission) {
            throw new PermissionDeniedException();
        }
        projectRecruitingService.delete(id);
        return ResponseBean.emptySuccessResponse();
    }

    private ProjectRecruitingDetailedVo getDetailedVoWithPermission(Integer userId, Integer id) throws SelectException, PermissionDeniedException {
        ProjectRecruitingDetailedVo projectRecruitingDetailedVo = projectRecruitingService.getVoById(id);
        if (projectRecruitingDetailedVo.getStatus() < ProjectRecruitingStatusEnum.NORMAL.getValue()) {
            boolean hasPermission = projectRecruitingService.hasPermission(userId, id);
            if (!hasPermission) {
                throw new PermissionDeniedException("项目招募需求暂时无法访问");
            }
        }
        return projectRecruitingDetailedVo;
    }
}
