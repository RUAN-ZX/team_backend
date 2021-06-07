package cn.steateam.lets_team_server.service;

import cn.steateam.lets_team_server.constant.TeamRequirementConstants;
import cn.steateam.lets_team_server.constant.TeamRequirementRequireStatusEnum;
import cn.steateam.lets_team_server.constant.TeamRequirementStatusEnum;
import cn.steateam.lets_team_server.dto.TeamRequirementDto;
import cn.steateam.lets_team_server.dto.TeamRequirementResumeDto;
import cn.steateam.lets_team_server.dto.TeamRequirementResumeHonorDto;
import cn.steateam.lets_team_server.entity.*;
import cn.steateam.lets_team_server.exception.DeleteException;
import cn.steateam.lets_team_server.exception.InsertException;
import cn.steateam.lets_team_server.exception.SelectException;
import cn.steateam.lets_team_server.exception.UpdateException;
import cn.steateam.lets_team_server.mapper.TeamRequirementCompetitionMapper;
import cn.steateam.lets_team_server.mapper.TeamRequirementMapper;
import cn.steateam.lets_team_server.mapper.TeamRequirementResumeBasicMapper;
import cn.steateam.lets_team_server.mapper.TeamRequirementResumeHonorMapper;
import cn.steateam.lets_team_server.vo.TeamRequirementDetailedVo;
import cn.steateam.lets_team_server.vo.TeamRequirementResumeBasicVo;
import cn.steateam.lets_team_server.vo.TeamRequirementResumeHonorVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 组队需求服务类
 *
 * @author YaoYi
 * @date 2021/3/17 2:52 下午
 */
@Service
public class TeamRequirementService {

    @Resource
    private TeamRequirementMapper teamRequirementMapper;
    @Resource
    private TeamRequirementResumeBasicMapper teamRequirementResumeBasicMapper;
    @Resource
    private TeamRequirementResumeHonorMapper teamRequirementResumeHonorMapper;
    @Resource
    private TeamRequirementCompetitionMapper teamRequirementCompetitionMapper;
    @Resource
    private CompetitionService competitionService;

    public TeamRequirementDetailedVo getVoById(Integer id) throws SelectException {
        TeamRequirement teamRequirement = getNotDeletedById(id);
        if (teamRequirement == null) {
            throw new SelectException("组队需求不存在！");
        }
        return teamRequirementMapper.selectVoById(id);
    }

    public List<Competition> getAllCompetitionById(Integer id) throws SelectException {
        TeamRequirement teamRequirement = getNotDeletedById(id);
        if (teamRequirement == null) {
            throw new SelectException("组队需求不存在！");
        }
        List<Integer> compIds = teamRequirementCompetitionMapper.selectCompIdByRequirementId(id);
        return competitionService.getAllByIdList(compIds);
    }

    public TeamRequirementResumeBasicVo getResumeBasicVoById(Integer id) throws SelectException {
        TeamRequirement teamRequirement = getNotDeletedById(id);
        if (teamRequirement == null) {
            throw new SelectException("组队需求不存在！");
        }
        return teamRequirementResumeBasicMapper.selectVoByRequirementId(id);
    }

    public List<TeamRequirementResumeHonorVo> getAllResumeHonorVoById(Integer id) throws SelectException {
        TeamRequirement teamRequirement = getNotDeletedById(id);
        if (teamRequirement == null) {
            throw new SelectException("组队需求不存在！");
        }
        return teamRequirementResumeHonorMapper.selectVoByRequirementId(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer save(TeamRequirementDto teamRequirementDto, Integer userId) throws InsertException {
        List<Integer> compIds = teamRequirementDto.getCompIds();
        if (!CollectionUtils.isEmpty(compIds)) {
            verifyCompIds(compIds);
        }
        TeamRequirement teamRequirement = new TeamRequirement();
        BeanUtils.copyProperties(teamRequirementDto, teamRequirement);
        teamRequirement.setUserId(userId);
        teamRequirement.setRequireStatus(TeamRequirementRequireStatusEnum.OPEN.getValue());
        teamRequirement.setStatus(TeamRequirementStatusEnum.NORMAL.getValue());
        teamRequirementMapper.insertSelective(teamRequirement);
        saveResume(teamRequirementDto.getResume(), teamRequirement.getId());
        saveCompIds(compIds, teamRequirement.getId());
        return teamRequirement.getId();
    }

    private void verifyCompIds(List<Integer> compIds) throws InsertException {
        if (compIds.size() > TeamRequirementConstants.COMP_IDS_MAX_SIZE) {
            throw new InsertException("竞赛列表长度不符合要求！");
        }
        List<Competition> competitions = competitionService.getAllByIdList(compIds);
        if (competitions.size() < compIds.size()) {
            throw new InsertException("竞赛id参数取值有误！");
        }
    }

    private void saveResume(TeamRequirementResumeDto teamRequirementResumeDto, Integer requirementId) {
        TeamRequirementResumeBasic teamRequirementResumeBasic = new TeamRequirementResumeBasic();
        BeanUtils.copyProperties(teamRequirementResumeDto, teamRequirementResumeBasic);
        teamRequirementResumeBasic.setRequirementId(requirementId);
        teamRequirementResumeBasicMapper.insertSelective(teamRequirementResumeBasic);
        List<TeamRequirementResumeHonorDto> honorDtos = teamRequirementResumeDto.getHonors();
        if (honorDtos != null && !honorDtos.isEmpty()) {
            List<TeamRequirementResumeHonor> honors = honorDtos.stream().map(honorDto -> {
                TeamRequirementResumeHonor teamRequirementResumeHonor = new TeamRequirementResumeHonor();
                BeanUtils.copyProperties(honorDto, teamRequirementResumeHonor);
                teamRequirementResumeHonor.setRequirementId(requirementId);
                return teamRequirementResumeHonor;
            }).collect(Collectors.toList());
            teamRequirementResumeHonorMapper.insertList(honors);
        }
    }

    private void saveCompIds(List<Integer> compIds, Integer requirementId) {
        List<TeamRequirementCompetition> teamRequirementCompetitionList = compIds.stream().map(compId -> TeamRequirementCompetition.builder()
                .compId(compId)
                .requirementId(requirementId)
                .build()).collect(Collectors.toList());
        teamRequirementCompetitionMapper.insertList(teamRequirementCompetitionList);
    }

    public void delete(Integer id) throws DeleteException {
        TeamRequirement originalTeamRequirement = getNotDeletedById(id);
        if (originalTeamRequirement == null) {
            throw new DeleteException("组队需求不存在！");
        }
        TeamRequirement teamRequirement = new TeamRequirement();
        teamRequirement.setId(id);
        teamRequirement.setStatus(TeamRequirementStatusEnum.IS_DELETED.getValue());
        teamRequirementMapper.updateByPrimaryKeySelective(teamRequirement);
    }

    public void close(Integer id, Integer closeStatus) throws UpdateException {
        TeamRequirement originalTeamRequirement = getNotDeletedById(id);
        if (originalTeamRequirement == null) {
            throw new UpdateException("组队需求不存在！");
        }
        if (!originalTeamRequirement.getRequireStatus().equals(TeamRequirementRequireStatusEnum.OPEN.getValue())) {
            throw new UpdateException("组队需求已经关闭！");
        }
        TeamRequirementRequireStatusEnum statusEnum = TeamRequirementRequireStatusEnum.getByValue(closeStatus);
        if (statusEnum == null || statusEnum.equals(TeamRequirementRequireStatusEnum.OPEN)) {
            throw new UpdateException("关闭类型参数取值错误！");
        }
        TeamRequirement teamRequirement = new TeamRequirement();
        teamRequirement.setId(id);
        teamRequirement.setRequireStatus(closeStatus);
        teamRequirement.setClosedTime(new Date());
        teamRequirementMapper.updateByPrimaryKeySelective(teamRequirement);
    }

    public TeamRequirement getNotDeletedById(Integer id) {
        TeamRequirement teamRequirement = teamRequirementMapper.selectByPrimaryKey(id);
        if (teamRequirement == null) {
            return null;
        }
        if (teamRequirement.getStatus().equals(TeamRequirementStatusEnum.IS_DELETED.getValue())) {
            return null;
        }
        return teamRequirement;
    }

    public boolean isCreator(Integer userId, Integer id) {
        TeamRequirement teamRequirement = teamRequirementMapper.selectByPrimaryKey(id);
        if (teamRequirement == null) {
            return false;
        }
        return teamRequirement.getUserId().equals(userId);
    }
}
