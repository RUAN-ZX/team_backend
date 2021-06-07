package cn.steateam.lets_team_server.service;

import cn.steateam.lets_team_server.constant.ProjectPermissionLevelEnum;
import cn.steateam.lets_team_server.constant.ProjectRecruitingStatusEnum;
import cn.steateam.lets_team_server.constant.ProjectStatusEnum;
import cn.steateam.lets_team_server.dto.ProjectDto;
import cn.steateam.lets_team_server.dto.ProjectHonorDto;
import cn.steateam.lets_team_server.dto.ProjectMemberCreateDto;
import cn.steateam.lets_team_server.dto.ProjectMemberEditDto;
import cn.steateam.lets_team_server.entity.Project;
import cn.steateam.lets_team_server.entity.ProjectHonor;
import cn.steateam.lets_team_server.entity.ProjectMember;
import cn.steateam.lets_team_server.entity.User;
import cn.steateam.lets_team_server.exception.DeleteException;
import cn.steateam.lets_team_server.exception.InsertException;
import cn.steateam.lets_team_server.exception.SelectException;
import cn.steateam.lets_team_server.exception.UpdateException;
import cn.steateam.lets_team_server.mapper.ProjectHonorMapper;
import cn.steateam.lets_team_server.mapper.ProjectMapper;
import cn.steateam.lets_team_server.mapper.ProjectMemberMapper;
import cn.steateam.lets_team_server.vo.ProjectDetailedVo;
import cn.steateam.lets_team_server.vo.ProjectHonorVo;
import cn.steateam.lets_team_server.vo.ProjectMemberVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目服务类
 *
 * @author YaoYi
 * @date 2021/3/10 3:39 下午
 */
@Service
public class ProjectService {
    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private ProjectHonorMapper projectHonorMapper;
    @Resource
    private ProjectMemberMapper projectMemberMapper;
    @Resource
    private UserService userService;
    @Resource
    private ProjectRecruitingService projectRecruitingService;

    public ProjectDetailedVo getVoById(Integer id) throws SelectException {
        Project project = getNotDeletedById(id);
        if (project == null) {
            throw new SelectException("项目不存在！");
        }
        return projectMapper.selectVoById(id);
    }

    /**
     * 根据id获取项目
     *
     * @param id 项目id
     * @return 项目PO
     */
    @SuppressWarnings("unused")
    public Project getById(Integer id) {
        return projectMapper.selectByPrimaryKey(id);
    }

    /**
     * 新建项目
     *
     * @param projectDto   项目DTO
     * @param leaderUserId 创建者用户id
     * @return 插入后的id
     */
    public Integer save(ProjectDto projectDto, Integer leaderUserId) {
        Project project = new Project();
        BeanUtils.copyProperties(project, projectDto);
        project.setLeaderUserId(leaderUserId);
        project.setStatus(ProjectStatusEnum.NORMAL.getValue());
        return projectMapper.insertSelective(project);
    }

    /**
     * 更新项目
     *
     * @param projectDto 项目DTO
     * @param projectId  项目id
     */
    public void update(ProjectDto projectDto, Integer projectId) throws UpdateException {
        Project originalProject = getNotDeletedById(projectId);
        if (originalProject == null) {
            throw new UpdateException("项目不存在！");
        }
        Project project = new Project();
        BeanUtils.copyProperties(project, projectDto);
        project.setId(projectId);
        if (originalProject.getStatus() < 0) {
            project.setStatus(ProjectStatusEnum.WAITING_FOR_AUDITING.getValue());
        }
        projectMapper.updateByPrimaryKeySelective(project);
    }

    /**
     * 根据id删除项目
     *
     * @param projectId 项目id
     * @throws DeleteException 删除异常
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer projectId) throws DeleteException {
        Project originalProject = getNotDeletedById(projectId);
        if (originalProject == null) {
            throw new DeleteException("项目不存在！");
        }
        Project project = new Project();
        project.setId(projectId);
        project.setStatus(ProjectStatusEnum.IS_DELETED.getValue());
        projectMapper.updateByPrimaryKeySelective(project);
        projectRecruitingService.updateStatusByProjectIdAndStatus(projectId, ProjectRecruitingStatusEnum.IS_DELETED.getValue(), null);
    }

    /**
     * 根据id新建项目成员
     *
     * @param projectMemberCreateDto 项目成员DTO
     * @param projectId              项目id
     * @throws InsertException 插入异常
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveMemberByProjectId(ProjectMemberCreateDto projectMemberCreateDto, Integer projectId) throws InsertException {
        User user = userService.getUserById(projectMemberCreateDto.getUserId());
        if (user == null) {
            throw new InsertException("用户不存在！");
        }
        Project project = getNotDeletedById(projectId);
        if (project == null) {
            throw new InsertException("项目不存在！");
        }
        if (ProjectPermissionLevelEnum.getByValue(projectMemberCreateDto.getPermissionLevel()) == null) {
            throw new InsertException("权限等级字段取值异常！");
        }
        ProjectMember projectMember = new ProjectMember();
        BeanUtils.copyProperties(projectMemberCreateDto, projectMember);
        projectMember.setProjectId(projectId);
        projectMemberMapper.insertSelective(projectMember);
        setUpdateTime(projectId);
    }

    /**
     * 根据id修改项目成员信息
     *
     * @param projectMemberEditDto 项目成员DTO
     * @param projectId            项目id
     * @param userId               用户id
     * @throws UpdateException 更新异常
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateMemberByProjectId(ProjectMemberEditDto projectMemberEditDto, Integer projectId, Integer userId) throws UpdateException {
        Project project = getNotDeletedById(projectId);
        if (project == null) {
            throw new UpdateException("项目不存在！");
        }
        ProjectMember originalProjectMember = projectMemberMapper.selectByPrimaryKey(projectId, userId);
        if (originalProjectMember == null) {
            throw new UpdateException("项目成员不存在！");
        }
        if (projectMemberEditDto.getPermissionLevel() != null) {
            if (ProjectPermissionLevelEnum.getByValue(projectMemberEditDto.getPermissionLevel()) == null) {
                throw new UpdateException("权限等级字段取值异常！");
            }
        }
        ProjectMember projectMember = new ProjectMember();
        BeanUtils.copyProperties(projectMemberEditDto, projectMember);
        projectMember.setProjectId(projectId);
        projectMember.setUserId(userId);
        projectMemberMapper.updateByPrimaryKeySelective(projectMember);
        setUpdateTime(projectId);
    }

    /**
     * 删除项目成员
     *
     * @param projectId 项目id
     * @param userId    成员用户id
     * @throws DeleteException 删除异常
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteMemberByProjectId(Integer projectId, Integer userId) throws DeleteException {
        Project project = getNotDeletedById(projectId);
        if (project == null) {
            throw new DeleteException("项目不存在！");
        }
        ProjectMember originalProjectMember = projectMemberMapper.selectByPrimaryKey(projectId, userId);
        if (originalProjectMember == null) {
            throw new DeleteException("项目成员不存在！");
        }
        projectMemberMapper.deleteByPrimaryKey(projectId, userId);
        setUpdateTime(projectId);
    }

    /**
     * 根据id新建项目荣誉id
     *
     * @param projectHonorDto 项目荣誉DTO
     * @param projectId       项目id
     * @return 插入后的id
     * @throws InsertException 插入异常
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer saveHonorByProjectId(ProjectHonorDto projectHonorDto, Integer projectId) throws InsertException {
        Project project = getNotDeletedById(projectId);
        if (project == null) {
            throw new InsertException("项目不存在！");
        }
        ProjectHonor projectHonor = new ProjectHonor();
        BeanUtils.copyProperties(projectHonorDto, projectHonor);
        projectHonor.setProjectId(projectId);
        projectHonorMapper.insertSelective(projectHonor);
        setUpdateTime(projectId);
        return projectHonor.getId();
    }

    /**
     * 更新项目荣誉
     *
     * @param projectHonorDto 项目荣誉DTO
     * @param projectId       项目id
     * @param honorId         荣誉id
     * @throws UpdateException 更新异常
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateHonorByProjectId(ProjectHonorDto projectHonorDto, Integer projectId, Integer honorId) throws UpdateException {
        Project project = getNotDeletedById(projectId);
        if (project == null) {
            throw new UpdateException("项目不存在！");
        }
        ProjectHonor originalProjectHonor = projectHonorMapper.selectByPrimaryKey(honorId);
        if (originalProjectHonor == null) {
            throw new UpdateException("项目荣誉不存在！");
        }
        if (!originalProjectHonor.getProjectId().equals(projectId)) {
            throw new UpdateException("项目id取值错误！");
        }
        ProjectHonor projectHonor = new ProjectHonor();
        BeanUtils.copyProperties(projectHonorDto, projectHonor);
        projectHonor.setId(honorId);
        projectHonorMapper.updateByPrimaryKeySelective(projectHonor);
        setUpdateTime(projectId);
    }

    /**
     * 删除项目荣誉
     *
     * @param projectId 项目id
     * @param honorId   荣誉id
     * @throws DeleteException 删除异常
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteHonorByProjectId(Integer projectId, Integer honorId) throws DeleteException {
        Project project = getNotDeletedById(projectId);
        if (project == null) {
            throw new DeleteException("项目不存在！");
        }
        ProjectHonor originalProjectHonor = projectHonorMapper.selectByPrimaryKey(honorId);
        if (originalProjectHonor == null) {
            throw new DeleteException("项目荣誉不存在！");
        }
        if (!originalProjectHonor.getProjectId().equals(projectId)) {
            throw new DeleteException("项目id取值错误！");
        }
        projectHonorMapper.deleteByPrimaryKey(honorId);
        setUpdateTime(projectId);
    }

    /**
     * 根据id获取项目成员VO列表
     *
     * @param projectId 项目id
     * @return 项目成员VO列表
     */
    public List<ProjectMemberVo> getAllMemberVoByProjectId(Integer projectId) throws SelectException {
        Project project = getNotDeletedById(projectId);
        if (project == null) {
            throw new SelectException("项目不存在！");
        }
        return projectMemberMapper.selectVoByProjectId(projectId);
    }

    /**
     * 根据id获取项目荣誉VO列表
     *
     * @param projectId 项目id
     * @return 项目成员荣誉列表
     */
    public List<ProjectHonorVo> getAllHonorVoByProjectId(Integer projectId) throws SelectException {
        Project project = getNotDeletedById(projectId);
        if (project == null) {
            throw new SelectException("项目不存在！");
        }
        return projectHonorMapper.selectVoByProjectId(projectId);
    }

    /**
     * 判断用户是否有权限完成相应操作
     *
     * @param userId          用户id
     * @param projectId       项目id
     * @param permissionLevel 权限等级
     * @return 是否拥有权限
     */
    public boolean hasPermission(Integer userId, Integer projectId, Integer permissionLevel) {
        Project project = getNotDeletedById(projectId);
        if (project == null) {
            return false;
        }
        if (project.getLeaderUserId().equals(userId)) {
            return true;
        }
        ProjectMember projectMember = projectMemberMapper.selectByPrimaryKey(projectId, userId);
        if (projectMember == null) {
            return false;
        }
        return projectMember.getPermissionLevel() >= permissionLevel;
    }

    public boolean hasPermissionToOperateMember(Integer projectId, Integer operatorUserId, Integer targetUserId, Integer targetLevel) {
        Project project = getNotDeletedById(projectId);
        if (project == null) {
            return false;
        }
        ProjectMember targetMember = projectMemberMapper.selectByPrimaryKey(projectId, targetUserId);
        if (project.getLeaderUserId().equals(operatorUserId)) {
            return true;
        }
        ProjectMember operator = projectMemberMapper.selectByPrimaryKey(projectId, operatorUserId);
        if (operator == null) {
            return false;
        }
        if (targetLevel != null) {
            if (targetLevel >= operator.getPermissionLevel()) {
                return false;
            }
        }
        if (targetUserId != null) {
            if (targetMember == null) {
                return false;
            }
            return targetMember.getPermissionLevel() < operator.getPermissionLevel();
        }
        return true;
    }

    public Project getNotDeletedById(Integer id) {
        Project project = projectMapper.selectByPrimaryKey(id);
        if (project == null) {
            return null;
        }
        if (project.getStatus().equals(ProjectStatusEnum.IS_DELETED.getValue())) {
            return null;
        }
        return project;
    }

    private void setUpdateTime(Integer projectId) {
        Project updatedProject = new Project();
        updatedProject.setId(projectId);
        if (updatedProject.getStatus() < 0) {
            updatedProject.setStatus(ProjectStatusEnum.WAITING_FOR_AUDITING.getValue());
        }
        projectMapper.updateByPrimaryKeySelective(updatedProject);
    }
}
