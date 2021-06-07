package cn.steateam.lets_team_server.mapper;

import cn.steateam.lets_team_server.entity.CompetitionExperienceSharingReply;
import cn.steateam.lets_team_server.vo.CompetitionExperienceSharingReplyVo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/23 10:08 下午
 */
public interface CompetitionExperienceSharingReplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompetitionExperienceSharingReply record);

    int insertSelective(CompetitionExperienceSharingReply record);

    CompetitionExperienceSharingReply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompetitionExperienceSharingReply record);

    int updateByPrimaryKey(CompetitionExperienceSharingReply record);

    int updateStatusBySharingIdAndStatus(@Param("updatedStatus") Integer updatedStatus, @Param("sharingId") Integer sharingId, @Param("status") Integer status);

    List<Integer> selectReplyUserIdByReplyId(@Param("replyId") Integer replyId);

    int updateStatusByReplyIdAndStatus(@Param("updatedStatus") Integer updatedStatus, @Param("replyId") Integer replyId, @Param("status") Integer status);

    List<CompetitionExperienceSharingReplyVo> selectVoBySharingIdAndTypeAndStatusIn(@Param("sharingId") Integer sharingId, @Param("type") Integer type, @Param("statusCollection") Collection<Integer> statusCollection);

    List<CompetitionExperienceSharingReplyVo> selectVoByReplyIdAndTypeAndStatusIn(@Param("replyId") Integer replyId, @Param("type") Integer type, @Param("statusCollection") Collection<Integer> statusCollection);

    CompetitionExperienceSharingReplyVo selectVoById(@Param("id") Integer id);
}