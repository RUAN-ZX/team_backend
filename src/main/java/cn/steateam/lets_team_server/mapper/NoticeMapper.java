package cn.steateam.lets_team_server.mapper;

import cn.steateam.lets_team_server.entity.Notice;import cn.steateam.lets_team_server.vo.NoticeVo;import org.apache.ibatis.annotations.Param;import java.util.Date;import java.util.List;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/30 12:16 上午
 */
public interface NoticeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Notice record);

    int insertSelective(Notice record);

    Notice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Notice record);

    int updateByPrimaryKey(Notice record);

    NoticeVo selectVoById(@Param("id") Integer id);

    List<NoticeVo> selectVoByReceiverUserId(@Param("receiverUserId") Integer receiverUserId);

    int updateReadStatusAndReadTimeById(@Param("updatedReadStatus") Integer updatedReadStatus, @Param("updatedReadTime") Date updatedReadTime, @Param("id") Integer id);

    int updateReadStatusAndReadTimeByReceiverUserIdAndReadStatus(@Param("updatedReadStatus") Integer updatedReadStatus, @Param("updatedReadTime") Date updatedReadTime, @Param("receiverUserId") Integer receiverUserId, @Param("readStatus") Integer readStatus);
}