package cn.steateam.lets_team_server.service;

import cn.steateam.lets_team_server.constant.NoticeConstants;
import cn.steateam.lets_team_server.dto.NoticeDto;
import cn.steateam.lets_team_server.entity.Notice;
import cn.steateam.lets_team_server.mapper.NoticeMapper;
import cn.steateam.lets_team_server.vo.NoticeVo;
import cn.steateam.lets_team_server.vo.PageVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 通知服务类
 *
 * @author STEA_YY
 */
@Service
public class NoticeService {
    @Resource
    private NoticeMapper noticeMapper;

    public PageVo<List<NoticeVo>> getAllVoPageableByUserId(int pageNum, int pageSize, int userId) {
        PageHelper.startPage(pageNum, pageSize);
        List<NoticeVo> noticeVos = noticeMapper.selectVoByReceiverUserId(userId);
        PageInfo<NoticeVo> pageInfo = new PageInfo<>(noticeVos);
        return new PageVo<>(pageInfo.getPageNum(), pageInfo.getSize(), pageInfo.getList());
    }

    public void readById(int id) {
        noticeMapper.updateReadStatusAndReadTimeById(NoticeConstants.STATUS_READ, new Date(), id);
    }

    public void readAllByReceiverUserId(int receiverUserId) {
        noticeMapper.updateReadStatusAndReadTimeByReceiverUserIdAndReadStatus(NoticeConstants.STATUS_READ, new Date(), receiverUserId, NoticeConstants.STATUS_UNREAD);
    }

    public void sendNotice(NoticeDto noticeDto) {
        Notice notice = new Notice();
        BeanUtils.copyProperties(noticeDto, notice);
        noticeMapper.insertSelective(notice);
    }

    public boolean isOwner(int id, int receiverUserId) {
        Notice notice = noticeMapper.selectByPrimaryKey(id);
        if (notice == null) {
            return false;
        }
        return notice.getReceiverUserId().equals(receiverUserId);
    }
}
