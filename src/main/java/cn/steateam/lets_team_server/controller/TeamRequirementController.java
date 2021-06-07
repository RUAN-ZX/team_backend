package cn.steateam.lets_team_server.controller;

import cn.steateam.lets_team_server.constant.TeamRequirementStatusEnum;
import cn.steateam.lets_team_server.dto.TeamRequirementDto;
import cn.steateam.lets_team_server.exception.*;
import cn.steateam.lets_team_server.service.TeamRequirementService;
import cn.steateam.lets_team_server.util.ThreadLocalUtil;
import cn.steateam.lets_team_server.vo.ResponseBean;
import cn.steateam.lets_team_server.vo.TeamRequirementDetailedVo;
import cn.steateam.lets_team_server.vo.TeamRequirementResumeBasicVo;
import cn.steateam.lets_team_server.vo.TeamRequirementResumeHonorVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 组队需求类请求
 *
 * @author YaoYi
 * @date 2021/3/21 10:48 上午
 */
@RestController
@RequestMapping("/requirement")
public class TeamRequirementController {
    @Resource
    private TeamRequirementService teamRequirementService;

    /**
     * 根据id获取组队需求
     *
     * @param id 组队需求id
     */
    @GetMapping("/{id}")
    public ResponseBean<TeamRequirementDetailedVo> getVoById(@PathVariable int id) throws SelectException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        TeamRequirementDetailedVo teamRequirementDetailedVo = getDetailedVoWithPermission(userId, id);
        return new ResponseBean<>(teamRequirementDetailedVo);
    }

    /**
     * 根据id获取组队需求简历基础信息
     *
     * @param id 组队需求id
     */
    @GetMapping("/{id}/resume/basic")
    public ResponseBean<TeamRequirementResumeBasicVo> getResumeVoById(@PathVariable int id) throws SelectException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        getDetailedVoWithPermission(userId, id);
        TeamRequirementResumeBasicVo resumeBasicVo = teamRequirementService.getResumeBasicVoById(id);
        return new ResponseBean<>(resumeBasicVo);
    }

    /**
     * 根据id获取组队需求简历所有荣誉信息
     *
     * @param id 组队需求id
     */
    @GetMapping("/{id}/resume/honors")
    public ResponseBean<List<TeamRequirementResumeHonorVo>> getAllResumeHonorVoById(@PathVariable int id) throws SelectException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        getDetailedVoWithPermission(userId, id);
        List<TeamRequirementResumeHonorVo> resumeHonorVos = teamRequirementService.getAllResumeHonorVoById(id);
        return new ResponseBean<>(resumeHonorVos);
    }

    /**
     * 创建组队需求
     *
     * @param teamRequirementDto 组队需求DTO
     */
    @PostMapping
    public ResponseBean<Integer> save(@Validated @RequestBody TeamRequirementDto teamRequirementDto) throws InsertException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        Integer insertedId = teamRequirementService.save(teamRequirementDto, userId);
        return new ResponseBean<>(insertedId);
    }

    /**
     * 关闭组队需求
     *
     * @param id          组队需求id
     * @param closeStatus 关闭后状态
     */
    @PutMapping("/{id}/close")
    public ResponseBean<Object> close(@PathVariable int id, @RequestParam int closeStatus) throws UpdateException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean isCreator = teamRequirementService.isCreator(userId, id);
        if (!isCreator) {
            throw new PermissionDeniedException();
        }
        teamRequirementService.close(id, closeStatus);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 删除组队需求
     *
     * @param id 组队需求id
     */
    @DeleteMapping("/{id}")
    public ResponseBean<Object> delete(@PathVariable int id) throws DeleteException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean isCreator = teamRequirementService.isCreator(userId, id);
        if (!isCreator) {
            throw new PermissionDeniedException();
        }
        teamRequirementService.delete(id);
        return ResponseBean.emptySuccessResponse();
    }

    private TeamRequirementDetailedVo getDetailedVoWithPermission(Integer userId, Integer id) throws SelectException, PermissionDeniedException {
        TeamRequirementDetailedVo teamRequirementDetailedVo = teamRequirementService.getVoById(id);
        if (teamRequirementDetailedVo.getStatus() < TeamRequirementStatusEnum.NORMAL.getValue()) {
            boolean isCreator = teamRequirementService.isCreator(userId, id);
            if (!isCreator) {
                throw new PermissionDeniedException("组队需求暂时无法访问");
            }
        }
        return teamRequirementDetailedVo;
    }
}
