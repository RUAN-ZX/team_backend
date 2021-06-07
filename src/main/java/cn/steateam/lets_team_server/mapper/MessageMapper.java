package cn.steateam.lets_team_server.mapper;

import cn.steateam.lets_team_server.criteria.MessageUserIdsAndReadStatusCriteria;import cn.steateam.lets_team_server.dto.MessageSessionDto;import cn.steateam.lets_team_server.entity.Message;import cn.steateam.lets_team_server.vo.MessageVo;import org.apache.ibatis.annotations.Param;import java.util.Date;import java.util.List;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/30 12:16 上午
 */
public interface MessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    List<MessageVo> selectVoBySessionKey(@Param("sessionKey") String sessionId);

    List<MessageSessionDto> selectSessionByUserId(@Param("userId") Integer userId);

    Message selectLastBySessionKey(@Param("sessionKey") String sessionId);

    MessageVo selectLastVoBySessionKey(@Param("sessionKey") String sessionId);

    Integer selectCountByCriteria(@Param("criteria") MessageUserIdsAndReadStatusCriteria criteria);

    int updateReadStatusAndReadTimeByCriteria(@Param("updatedReadStatus") Integer updatedReadStatus, @Param("updatedReadTime") Date updatedReadTime, @Param("criteria") MessageUserIdsAndReadStatusCriteria criteria);

    int updateLastMsgReadStatusAndReadTimeByCriteria(@Param("updatedReadStatus") Integer updatedReadStatus, @Param("updatedReadTime") Date updatedReadTime, @Param("criteria") MessageUserIdsAndReadStatusCriteria criteria);

}