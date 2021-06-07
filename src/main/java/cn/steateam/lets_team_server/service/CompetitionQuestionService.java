package cn.steateam.lets_team_server.service;

import cn.steateam.lets_team_server.constant.CompetitionQuestionStatusEnum;
import cn.steateam.lets_team_server.dto.CompetitionAnswerDto;
import cn.steateam.lets_team_server.dto.CompetitionQuestionCreateDto;
import cn.steateam.lets_team_server.dto.CompetitionQuestionEditDto;
import cn.steateam.lets_team_server.entity.Competition;
import cn.steateam.lets_team_server.entity.CompetitionAnswer;
import cn.steateam.lets_team_server.entity.CompetitionQuestion;
import cn.steateam.lets_team_server.exception.DeleteException;
import cn.steateam.lets_team_server.exception.InsertException;
import cn.steateam.lets_team_server.exception.SelectException;
import cn.steateam.lets_team_server.exception.UpdateException;
import cn.steateam.lets_team_server.mapper.CompetitionAnswerMapper;
import cn.steateam.lets_team_server.mapper.CompetitionQuestionMapper;
import cn.steateam.lets_team_server.vo.CompetitionAnswerVo;
import cn.steateam.lets_team_server.vo.CompetitionQuestionVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 竞赛问答服务类
 *
 * @author YaoYi
 * @date 2021/3/18 12:14 上午
 */
@Service
public class CompetitionQuestionService {

    @Resource
    private CompetitionQuestionMapper competitionQuestionMapper;
    @Resource
    private CompetitionAnswerMapper competitionAnswerMapper;
    @Resource
    private CompetitionService competitionService;

    public CompetitionQuestionVo getQuestionVoByQuestionId(Integer questionId) throws SelectException {
        CompetitionQuestion competitionQuestion = getQuestionNotDeletedByQuestionId(questionId);
        if (competitionQuestion == null) {
            throw new SelectException("竞赛提问不存在！");
        }
        return competitionQuestionMapper.selectVoById(questionId);
    }

    public CompetitionAnswerVo getAnswerVoByAnswerId(Integer answerId) throws SelectException {
        CompetitionAnswer competitionAnswer = getAnswerNotDeletedByAnswerId(answerId);
        if (competitionAnswer == null) {
            throw new SelectException("竞赛回答不存在！");
        }
        return competitionAnswerMapper.selectVoById(answerId);
    }

    public List<CompetitionAnswerVo> getAllAnswerVoByQuestionIdAndStatusList(Integer questionId, List<Integer> statusList) throws SelectException {
        CompetitionQuestion competitionQuestion = getQuestionNotDeletedByQuestionId(questionId);
        if (competitionQuestion == null) {
            throw new SelectException("竞赛提问不存在！");
        }
        return competitionAnswerMapper.selectVoByQuestionIdAndStatusIn(questionId, statusList);
    }

    public Integer saveQuestion(CompetitionQuestionCreateDto competitionQuestionCreateDto, Integer userId) throws InsertException {
        Competition competition = competitionService.getNotDeletedById(competitionQuestionCreateDto.getCompId());
        if (competition==null){
            throw new InsertException("竞赛不存在！");
        }
        CompetitionQuestion competitionQuestion = new CompetitionQuestion();
        BeanUtils.copyProperties(competitionQuestionCreateDto, competitionQuestion);
        competitionQuestion.setAuthorUserId(userId);
        competitionQuestion.setStatus(CompetitionQuestionStatusEnum.NORMAL.getValue());
        competitionQuestionMapper.insertSelective(competitionQuestion);
        return competitionQuestion.getId();
    }

    public Integer saveAnswer(CompetitionAnswerDto competitionAnswerDto, Integer questionId, Integer userId) throws SelectException {
        CompetitionQuestion competitionQuestion = getQuestionNotDeletedByQuestionId(questionId);
        if (competitionQuestion == null) {
            throw new SelectException("竞赛提问不存在！");
        }
        if (!competitionQuestion.getStatus().equals(CompetitionQuestionStatusEnum.NORMAL.getValue())) {
            throw new SelectException("暂时无法回答本问题！");
        }
        CompetitionAnswer competitionAnswer = new CompetitionAnswer();
        BeanUtils.copyProperties(competitionAnswerDto, competitionAnswer);
        competitionAnswer.setQuestionId(questionId);
        competitionAnswer.setAuthorUserId(userId);
        competitionAnswer.setStatus(CompetitionQuestionStatusEnum.NORMAL.getValue());
        competitionAnswerMapper.insertSelective(competitionAnswer);
        return competitionAnswer.getId();
    }

    public void updateQuestion(CompetitionQuestionEditDto competitionQuestionEditDto, Integer questionId) throws UpdateException {
        CompetitionQuestion originalCompetitionQuestion = getQuestionNotDeletedByQuestionId(questionId);
        if (originalCompetitionQuestion == null) {
            throw new UpdateException("竞赛提问不存在！");
        }
        Integer answerCount = competitionAnswerMapper.countByQuestionIdAndStatusNotIn(questionId,
                new ArrayList<>(CompetitionQuestionStatusEnum.IS_DELETED.getValue()));
        if (answerCount > 0) {
            throw new UpdateException("已经有回答的提问无法更新！");
        }
        CompetitionQuestion competitionQuestion = new CompetitionQuestion();
        BeanUtils.copyProperties(competitionQuestionEditDto, competitionQuestion);
        competitionQuestion.setId(questionId);
        if (originalCompetitionQuestion.getStatus() < 0) {
            originalCompetitionQuestion.setStatus(CompetitionQuestionStatusEnum.WAITING_FOR_AUDITING.getValue());
        }
        competitionQuestionMapper.updateByPrimaryKeySelective(competitionQuestion);
    }

    public void updateAnswer(CompetitionAnswerDto competitionAnswerDto, Integer answerId) throws UpdateException {
        CompetitionAnswer originalCompetitionAnswer = getAnswerNotDeletedByAnswerId(answerId);
        if (originalCompetitionAnswer == null) {
            throw new UpdateException("竞赛回答不存在！");
        }
        CompetitionAnswer competitionAnswer = new CompetitionAnswer();
        BeanUtils.copyProperties(competitionAnswerDto, competitionAnswer);
        competitionAnswer.setId(answerId);
        if (originalCompetitionAnswer.getStatus() < 0) {
            originalCompetitionAnswer.setStatus(CompetitionQuestionStatusEnum.WAITING_FOR_AUDITING.getValue());
        }
        competitionAnswerMapper.updateByPrimaryKeySelective(competitionAnswer);
    }

    public void deleteQuestion(Integer questionId) throws DeleteException {
        CompetitionQuestion originalCompetitionQuestion = getQuestionNotDeletedByQuestionId(questionId);
        if (originalCompetitionQuestion == null) {
            throw new DeleteException("竞赛提问不存在！");
        }
        Integer answerCount = competitionAnswerMapper.countByQuestionIdAndStatusNotIn(questionId,
                new ArrayList<>(CompetitionQuestionStatusEnum.IS_DELETED.getValue()));
        if (answerCount > 0) {
            throw new DeleteException("已经有回答的提问无法被删除！");
        }
        CompetitionQuestion competitionQuestion = new CompetitionQuestion();
        competitionQuestion.setId(questionId);
        competitionQuestion.setStatus(CompetitionQuestionStatusEnum.IS_DELETED.getValue());
        competitionQuestionMapper.updateByPrimaryKeySelective(competitionQuestion);
    }

    public void deleteAnswer(Integer answerId) throws DeleteException {
        CompetitionAnswer originalCompetitionAnswer = getAnswerNotDeletedByAnswerId(answerId);
        if (originalCompetitionAnswer == null) {
            throw new DeleteException("竞赛回答不存在！");
        }
        CompetitionAnswer competitionAnswer = new CompetitionAnswer();
        competitionAnswer.setId(answerId);
        competitionAnswer.setStatus(CompetitionQuestionStatusEnum.IS_DELETED.getValue());
        competitionAnswerMapper.updateByPrimaryKeySelective(competitionAnswer);
    }

    public CompetitionQuestion getQuestionNotDeletedByQuestionId(Integer questionId) {
        CompetitionQuestion competitionQuestion = competitionQuestionMapper.selectByPrimaryKey(questionId);
        if (competitionQuestion.getStatus().equals(CompetitionQuestionStatusEnum.IS_DELETED.getValue())) {
            return null;
        }
        return competitionQuestion;
    }

    public CompetitionAnswer getAnswerNotDeletedByAnswerId(Integer answerId) {
        CompetitionAnswer competitionAnswer = competitionAnswerMapper.selectByPrimaryKey(answerId);
        if (competitionAnswer.getStatus().equals(CompetitionQuestionStatusEnum.IS_DELETED.getValue())) {
            return null;
        }
        return competitionAnswer;
    }

    public boolean isCreatorOfQuestion(Integer userId, Integer questionId) {
        CompetitionQuestion competitionQuestion = competitionQuestionMapper.selectByPrimaryKey(questionId);
        if (competitionQuestion == null) {
            return false;
        }
        return competitionQuestion.getAuthorUserId().equals(userId);
    }

    public boolean isCreatorOfAnswer(Integer userId, Integer answerId) {
        CompetitionAnswer competitionAnswer = competitionAnswerMapper.selectByPrimaryKey(answerId);
        if (competitionAnswer == null) {
            return false;
        }
        return competitionAnswer.getAuthorUserId().equals(userId);
    }
}
