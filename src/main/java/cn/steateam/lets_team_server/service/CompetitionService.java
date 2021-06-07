package cn.steateam.lets_team_server.service;

import cn.steateam.lets_team_server.constant.CompetitionStatusEnum;
import cn.steateam.lets_team_server.dto.CompetitionDto;
import cn.steateam.lets_team_server.entity.Competition;
import cn.steateam.lets_team_server.exception.DeleteException;
import cn.steateam.lets_team_server.exception.SelectException;
import cn.steateam.lets_team_server.exception.UpdateException;
import cn.steateam.lets_team_server.mapper.CompetitionMapper;
import cn.steateam.lets_team_server.vo.CompetitionDetailedVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 竞赛服务类
 *
 * @author YaoYi
 * @date 2021/3/21 8:56 下午
 */
@Service
public class CompetitionService {

    @Resource
    private CompetitionMapper competitionMapper;

    public CompetitionDetailedVo getVoById(Integer id) throws SelectException {
        Competition competition = getNotDeletedById(id);
        if (competition==null){
            throw new SelectException("竞赛不存在！");
        }
        return competitionMapper.selectVoById(id);
    }

    public List<Competition> getAllByIdList(List<Integer> idList){
        return competitionMapper.selectByIdIn(idList);
    }

    public Integer save(CompetitionDto competitionDto) {
        Competition competition = new Competition();
        BeanUtils.copyProperties(competitionDto, competition);
        competition.setStatus(CompetitionStatusEnum.NORMAL.getValue());
        competitionMapper.insertSelective(competition);
        return competition.getId();
    }

    public void update(CompetitionDto competitionDto, Integer id) throws UpdateException {
        Competition originalCompetition = getNotDeletedById(id);
        if (originalCompetition==null){
            throw new UpdateException("竞赛不存在！");
        }
        Competition competition = new Competition();
        BeanUtils.copyProperties(competitionDto, competition);
        competition.setId(id);
        competitionMapper.updateByPrimaryKeySelective(competition);
    }

    public void delete(Integer id) throws DeleteException {
        Competition originalCompetition = getNotDeletedById(id);
        if (originalCompetition==null){
            throw new DeleteException("竞赛不存在！");
        }
        Competition competition = new Competition();
        competition.setId(id);
        competition.setStatus(CompetitionStatusEnum.IS_DELETED.getValue());
        competitionMapper.updateByPrimaryKeySelective(competition);
    }

    public Competition getNotDeletedById(Integer id) {
        Competition competition = competitionMapper.selectByPrimaryKey(id);
        if (competition.getStatus().equals(CompetitionStatusEnum.IS_DELETED.getValue())) {
            return null;
        }
        return competition;
    }
}
