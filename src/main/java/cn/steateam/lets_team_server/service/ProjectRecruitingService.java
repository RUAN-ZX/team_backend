package cn.steateam.lets_team_server.service;

import cn.steateam.lets_team_server.constant.ProjectPermissionLevelEnum;
import cn.steateam.lets_team_server.constant.ProjectRecruitingRecruitStatusEnum;
import cn.steateam.lets_team_server.constant.ProjectRecruitingStatusEnum;
import cn.steateam.lets_team_server.constant.ProjectStatusEnum;
import cn.steateam.lets_team_server.dto.ProjectRecruitingDto;
import cn.steateam.lets_team_server.entity.Project;
import cn.steateam.lets_team_server.entity.ProjectRecruiting;
import cn.steateam.lets_team_server.exception.DeleteException;
import cn.steateam.lets_team_server.exception.InsertException;
import cn.steateam.lets_team_server.exception.SelectException;
import cn.steateam.lets_team_server.exception.UpdateException;
import cn.steateam.lets_team_server.mapper.ProjectRecruitingMapper;
import cn.steateam.lets_team_server.vo.ProjectRecruitingDetailedVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 项目招募需求服务类
 *
 * @author YaoYi
 * @date 2021/3/16 5:34 下午
 */
@Service
public class ProjectRecruitingService {
    @Resource
    private ProjectRecruitingMapper projectRecruitingMapper;

    @Resource
    private ProjectService projectService;

    public ProjectRecruitingDetailedVo getVoById(Integer id) throws SelectException {
        ProjectRecruiting projectRecruiting = getNotDeletedById(id);
        if (projectRecruiting == null) {
            throw new SelectException("项目招募需求不存在！");
        }
        return projectRecruitingMapper.selectVoById(id);
    }

    public Integer save(ProjectRecruitingDto projectRecruitingDto, Integer creatorUserId) throws InsertException {
        Project project = projectService.getNotDeletedById(projectRecruitingDto.getProjectId());
        if (project == null) {
            throw new InsertException("项目不存在！");
        }
        if (project.getStatus() < ProjectStatusEnum.NORMAL.getValue()) {
            throw new InsertException("项目当前处于异常状态，无法发布招募贴！");
        }
        ProjectRecruiting projectRecruiting = new ProjectRecruiting();
        BeanUtils.copyProperties(projectRecruitingDto, projectRecruiting);
        projectRecruiting.setCreatorUserId(creatorUserId);
        projectRecruiting.setStatus(ProjectRecruitingStatusEnum.NORMAL.getValue());
        projectRecruiting.setRecruitStatus(ProjectRecruitingRecruitStatusEnum.OPEN.getValue());
        projectRecruitingMapper.insertSelective(projectRecruiting);
        return projectRecruiting.getId();
    }

    public void updateStatusByProjectIdAndStatus(Integer projectId, Integer updatedStatus, Integer originalStatus) {
        projectRecruitingMapper.updateStatusByProjectIdAndStatus(updatedStatus, projectId, originalStatus);
    }

    public void delete(Integer id) throws DeleteException {
        ProjectRecruiting originalProjectRecruiting = getNotDeletedById(id);
        if (originalProjectRecruiting == null) {
            throw new DeleteException("项目招募需求不存在！");
        }
        ProjectRecruiting projectRecruiting = new ProjectRecruiting();
        projectRecruiting.setId(id);
        projectRecruiting.setStatus(ProjectRecruitingStatusEnum.IS_DELETED.getValue());
        projectRecruitingMapper.updateByPrimaryKeySelective(projectRecruiting);
    }

    public void close(Integer id, Integer closedStatus) throws UpdateException {
        ProjectRecruiting originalProjectRecruiting = getNotDeletedById(id);
        if (originalProjectRecruiting == null) {
            throw new UpdateException("项目招募需求不存在！");
        }
        if (!originalProjectRecruiting.getRecruitStatus().equals(ProjectRecruitingRecruitStatusEnum.OPEN.getValue())) {
            throw new UpdateException("项目招募需求已经被关闭！");
        }
        ProjectRecruitingRecruitStatusEnum statusEnum = ProjectRecruitingRecruitStatusEnum.getByValue(closedStatus);
        if (statusEnum == null || statusEnum.equals(ProjectRecruitingRecruitStatusEnum.OPEN)) {
            throw new UpdateException("关闭状态参数取值错误！");
        }
        ProjectRecruiting projectRecruiting = new ProjectRecruiting();
        projectRecruiting.setId(id);
        projectRecruiting.setRecruitStatus(closedStatus);
        projectRecruiting.setClosedTime(new Date());
        projectRecruitingMapper.updateByPrimaryKeySelective(projectRecruiting);
    }

    public boolean hasPermission(Integer userId, Integer id) {
        ProjectRecruiting projectRecruiting = getNotDeletedById(id);
        if (projectRecruiting == null) {
            return false;
        }
        if (projectRecruiting.getCreatorUserId().equals(userId)) {
            return true;
        }
        return projectService.hasPermission(userId, projectRecruiting.getProjectId(), ProjectPermissionLevelEnum.EDIT.getValue());
    }

    public ProjectRecruiting getNotDeletedById(Integer id) {
        ProjectRecruiting projectRecruiting = projectRecruitingMapper.selectByPrimaryKey(id);
        if (projectRecruiting == null) {
            return null;
        }
        if (projectRecruiting.getStatus().equals(ProjectRecruitingStatusEnum.IS_DELETED.getValue())) {
            return null;
        }
        return projectRecruiting;
    }
}
