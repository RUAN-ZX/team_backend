package cn.steateam.lets_team_server.service;

import cn.steateam.lets_team_server.constant.CompetitionExperienceSharingReplyTypeEnum;
import cn.steateam.lets_team_server.constant.CompetitionExperienceSharingStatusEnum;
import cn.steateam.lets_team_server.dto.CompetitionExperienceSharingCreateDto;
import cn.steateam.lets_team_server.dto.CompetitionExperienceSharingEditDto;
import cn.steateam.lets_team_server.dto.CompetitionExperienceSharingReplyDto;
import cn.steateam.lets_team_server.entity.Competition;
import cn.steateam.lets_team_server.entity.CompetitionExperienceSharing;
import cn.steateam.lets_team_server.entity.CompetitionExperienceSharingReply;
import cn.steateam.lets_team_server.exception.DeleteException;
import cn.steateam.lets_team_server.exception.InsertException;
import cn.steateam.lets_team_server.exception.SelectException;
import cn.steateam.lets_team_server.exception.UpdateException;
import cn.steateam.lets_team_server.mapper.CompetitionExperienceSharingMapper;
import cn.steateam.lets_team_server.mapper.CompetitionExperienceSharingReplyMapper;
import cn.steateam.lets_team_server.vo.CompetitionExperienceSharingDetailedVo;
import cn.steateam.lets_team_server.vo.CompetitionExperienceSharingReplyVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目经验分享服务类
 *
 * @author YaoYi
 * @date 2021/3/21 8:45 下午
 */
@Service
public class CompetitionExperienceSharingService {

    @Resource
    private CompetitionExperienceSharingMapper competitionExperienceSharingMapper;
    @Resource
    private CompetitionExperienceSharingReplyMapper competitionExperienceSharingReplyMapper;
    @Resource
    private CompetitionService competitionService;

    public CompetitionExperienceSharingDetailedVo getVoById(Integer id) throws SelectException {
        CompetitionExperienceSharing competitionExperienceSharing = getNotDeletedById(id);
        if (competitionExperienceSharing == null) {
            throw new SelectException("竞赛经验分享不存在！");
        }
        return competitionExperienceSharingMapper.selectVoById(id);
    }

    public CompetitionExperienceSharingReplyVo getReplyVoByReplyId(Integer replyId) throws SelectException {
        CompetitionExperienceSharingReply competitionExperienceSharingReply = getNotDeletedReplyById(replyId);
        if (competitionExperienceSharingReply == null) {
            throw new SelectException("竞赛经验分享评论不存在！");
        }
        return competitionExperienceSharingReplyMapper.selectVoById(replyId);
    }

    public List<CompetitionExperienceSharingReplyVo> getDirectReplyVoBySharingIdAndStatusList(Integer sharingId, List<Integer> statusList) throws SelectException {
        CompetitionExperienceSharing competitionExperienceSharing = getNotDeletedById(sharingId);
        if (competitionExperienceSharing == null) {
            throw new SelectException("竞赛经验分享不存在");
        }
        return competitionExperienceSharingReplyMapper.selectVoBySharingIdAndTypeAndStatusIn(sharingId, CompetitionExperienceSharingReplyTypeEnum.DIRECT_REPLY.getValue(), statusList);
    }

    public List<CompetitionExperienceSharingReplyVo> getIndirectReplyVoByReplyIdAndStatusList(Integer replyId, List<Integer> statusList) throws SelectException {
        CompetitionExperienceSharingReply targetSharingReply = getNotDeletedReplyById(replyId);
        if (targetSharingReply == null) {
            throw new SelectException("竞赛经验分享回复不存在！");
        }
        return competitionExperienceSharingReplyMapper.selectVoByReplyIdAndTypeAndStatusIn(replyId, CompetitionExperienceSharingReplyTypeEnum.INDIRECT_REPLY.getValue(), statusList);
    }

    public Integer save(CompetitionExperienceSharingCreateDto competitionExperienceSharingCreateDto, Integer userId) throws InsertException {
        Competition competition = competitionService.getNotDeletedById(competitionExperienceSharingCreateDto.getCompId());
        if (competition == null) {
            throw new InsertException("竞赛不存在！");
        }
        CompetitionExperienceSharing competitionExperienceSharing = new CompetitionExperienceSharing();
        BeanUtils.copyProperties(competitionExperienceSharingCreateDto, competitionExperienceSharing);
        competitionExperienceSharing.setAuthorUserId(userId);
        competitionExperienceSharing.setStatus(CompetitionExperienceSharingStatusEnum.NORMAL.getValue());
        competitionExperienceSharingMapper.insertSelective(competitionExperienceSharing);
        return competitionExperienceSharing.getId();
    }

    public void update(CompetitionExperienceSharingEditDto competitionExperienceSharingEditDto, Integer id) throws UpdateException {
        CompetitionExperienceSharing originalCompetitionExperienceSharing = getNotDeletedById(id);
        if (originalCompetitionExperienceSharing == null) {
            throw new UpdateException("比赛经验分享不存在！");
        }
        CompetitionExperienceSharing competitionExperienceSharing = new CompetitionExperienceSharing();
        BeanUtils.copyProperties(competitionExperienceSharingEditDto, competitionExperienceSharing);
        competitionExperienceSharing.setId(id);
        if (competitionExperienceSharing.getStatus() < 0) {
            competitionExperienceSharing.setStatus(CompetitionExperienceSharingStatusEnum.WAITING_FOR_AUDITING.getValue());
        }
        competitionExperienceSharingMapper.updateByPrimaryKeySelective(competitionExperienceSharing);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) throws DeleteException {
        CompetitionExperienceSharing originalCompetitionExperienceSharing = getNotDeletedById(id);
        if (originalCompetitionExperienceSharing == null) {
            throw new DeleteException("比赛经验分享不存在！");
        }
        CompetitionExperienceSharing competitionExperienceSharing = new CompetitionExperienceSharing();
        competitionExperienceSharing.setStatus(CompetitionExperienceSharingStatusEnum.IS_DELETED.getValue());
        competitionExperienceSharingMapper.updateByPrimaryKeySelective(competitionExperienceSharing);
        competitionExperienceSharingReplyMapper.updateStatusBySharingIdAndStatus(CompetitionExperienceSharingStatusEnum.IS_DELETED.getValue(), id, null);
    }

    public Integer saveReply(CompetitionExperienceSharingReplyDto competitionExperienceSharingReplyDto, Integer sharingId, Integer userId) throws InsertException {
        CompetitionExperienceSharing experienceSharing = getNotDeletedById(sharingId);
        if (experienceSharing == null) {
            throw new InsertException("竞赛经验分享不存在！");
        }
        if (experienceSharing.getStatus() < CompetitionExperienceSharingStatusEnum.NORMAL.getValue()) {
            throw new InsertException("项目经验分享当前处于异常状态，无法回复！");
        }
        verifyAndClearReplyDto(competitionExperienceSharingReplyDto);
        CompetitionExperienceSharingReply competitionExperienceSharingReply = new CompetitionExperienceSharingReply();
        BeanUtils.copyProperties(competitionExperienceSharingReplyDto, competitionExperienceSharingReply);
        competitionExperienceSharingReply.setStatus(CompetitionExperienceSharingStatusEnum.NORMAL.getValue());
        competitionExperienceSharingReply.setAuthorUserId(userId);
        competitionExperienceSharingReply.setSharingId(sharingId);
        competitionExperienceSharingReplyMapper.insertSelective(competitionExperienceSharingReply);
        return competitionExperienceSharingReply.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteReply(Integer replyId) throws DeleteException {
        CompetitionExperienceSharingReply originalCompetitionExperienceSharingReply = competitionExperienceSharingReplyMapper.selectByPrimaryKey(replyId);
        if (originalCompetitionExperienceSharingReply == null) {
            throw new DeleteException("竞赛经验分享回复不存在！");
        }
        CompetitionExperienceSharingReply competitionExperienceSharingReply = new CompetitionExperienceSharingReply();
        competitionExperienceSharingReply.setId(replyId);
        competitionExperienceSharingReply.setStatus(CompetitionExperienceSharingStatusEnum.IS_DELETED.getValue());
        competitionExperienceSharingReplyMapper.updateByPrimaryKeySelective(competitionExperienceSharingReply);
        if (originalCompetitionExperienceSharingReply.getType().equals(CompetitionExperienceSharingReplyTypeEnum.DIRECT_REPLY.getValue())) {
            competitionExperienceSharingReplyMapper.updateStatusByReplyIdAndStatus(CompetitionExperienceSharingStatusEnum.IS_DELETED.getValue(), replyId, null);
        }
    }

    public CompetitionExperienceSharing getNotDeletedById(Integer id) {
        CompetitionExperienceSharing competitionExperienceSharing = competitionExperienceSharingMapper.selectByPrimaryKey(id);
        if (competitionExperienceSharing.getStatus().equals(CompetitionExperienceSharingStatusEnum.IS_DELETED.getValue())) {
            return null;
        }
        return competitionExperienceSharing;
    }

    public CompetitionExperienceSharingReply getNotDeletedReplyById(Integer id) {
        CompetitionExperienceSharingReply competitionExperienceSharingReply = competitionExperienceSharingReplyMapper.selectByPrimaryKey(id);
        if (competitionExperienceSharingReply.getStatus().equals(CompetitionExperienceSharingStatusEnum.IS_DELETED.getValue())) {
            return null;
        }
        return competitionExperienceSharingReply;
    }

    public boolean isCreator(Integer userId, Integer id) {
        CompetitionExperienceSharing competitionExperienceSharing = competitionExperienceSharingMapper.selectByPrimaryKey(id);
        if (competitionExperienceSharing == null) {
            return false;
        }
        return competitionExperienceSharing.getAuthorUserId().equals(userId);
    }

    public boolean isCreatorOfReply(Integer userId, Integer replyId) {
        CompetitionExperienceSharingReply competitionExperienceSharingReply = competitionExperienceSharingReplyMapper.selectByPrimaryKey(replyId);
        if (competitionExperienceSharingReply == null) {
            return false;
        }
        return competitionExperienceSharingReply.getAuthorUserId().equals(userId);
    }

    private void verifyAndClearReplyDto(CompetitionExperienceSharingReplyDto competitionExperienceSharingReplyDto) throws InsertException {
        CompetitionExperienceSharingReplyTypeEnum typeEnum = CompetitionExperienceSharingReplyTypeEnum.getByValue(competitionExperienceSharingReplyDto.getType());
        if (typeEnum == null) {
            throw new InsertException("回复类型取值错误！");
        }
        switch (typeEnum) {
            case DIRECT_REPLY: {
                competitionExperienceSharingReplyDto.setReplyId(null);
                competitionExperienceSharingReplyDto.setReplyUserId(null);
                break;
            }
            case INDIRECT_REPLY: {
                Integer replyId = competitionExperienceSharingReplyDto.getReplyId();
                Integer replyUserId = competitionExperienceSharingReplyDto.getReplyUserId();
                if (replyId == null) {
                    throw new InsertException("目标回复id不能为空！");
                }
                if (replyUserId == null) {
                    throw new InsertException("目标回复用户id不能为空！");
                }
                CompetitionExperienceSharingReply targetReply = getNotDeletedReplyById(replyId);
                if (targetReply == null) {
                    throw new InsertException("目标回复不存在！");
                }
                if (targetReply.getStatus() < CompetitionExperienceSharingStatusEnum.NORMAL.getValue()) {
                    throw new InsertException("目标回复目前处于异常状态，无法回复！");
                }
                if (!targetReply.getType().equals(CompetitionExperienceSharingReplyTypeEnum.DIRECT_REPLY.getValue())) {
                    throw new InsertException("目标回复类型错误！");
                }
                if (!targetReply.getReplyUserId().equals(replyUserId)) {
                    List<Integer> userIds = competitionExperienceSharingReplyMapper.selectReplyUserIdByReplyId(targetReply.getId());
                    if (!userIds.contains(replyUserId)) {
                        throw new InsertException("回复目标用户id取值错误");
                    }
                }
                break;
            }
            default: {
                break;
            }
        }
    }
}
