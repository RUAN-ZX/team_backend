package cn.steateam.lets_team_server.service;

import cn.steateam.lets_team_server.constant.MessageConstants;
import cn.steateam.lets_team_server.constant.WebSocketConstants;
import cn.steateam.lets_team_server.criteria.MessageUserIdsAndReadStatusCriteria;
import cn.steateam.lets_team_server.dto.PrivateMessageDto;
import cn.steateam.lets_team_server.dto.MessageSessionDto;
import cn.steateam.lets_team_server.dto.WebSocketMessageDto;
import cn.steateam.lets_team_server.entity.Message;
import cn.steateam.lets_team_server.entity.User;
import cn.steateam.lets_team_server.exception.MessageSendingException;
import cn.steateam.lets_team_server.exception.SelectException;
import cn.steateam.lets_team_server.mapper.MessageMapper;
import cn.steateam.lets_team_server.util.JacksonUtil;
import cn.steateam.lets_team_server.vo.MessageVo;
import cn.steateam.lets_team_server.vo.PageVo;
import cn.steateam.lets_team_server.vo.MessageSessionVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageState;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 站内信服务类
 *
 * @author STEA_YY
 */
@Service
public class MessageService {
    @Resource
    private MessageMapper messageMapper;
    @Resource
    private UserService userService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private WebSocketService webSocketService;

    public void sendPrivateMessage(int senderUserId, PrivateMessageDto privateMessageDto) throws MessageSendingException {
        User receiver = userService.getUserById(privateMessageDto.getReceiverUserId());
        if (receiver == null) {
            throw new MessageSendingException("收件人不存在！");
        }
        if (senderUserId == privateMessageDto.getReceiverUserId()) {
            throw new MessageSendingException("不能给自己发送私信！");
        }
        String sessionKey;
        Integer receiverUserId = privateMessageDto.getReceiverUserId();
        if (receiverUserId <= senderUserId) {
            sessionKey = receiverUserId + ":" + senderUserId;
        } else {
            sessionKey = senderUserId + ":" + receiverUserId;
        }

        // 送完信以后 senderId 状态为 MessageConstants.STATUS_SOFT_DEL 的将恢复为已读状态
        changeLastMessageStatusByUserId(senderUserId,privateMessageDto.getReceiverUserId(),
                MessageConstants.STATUS_READ);

        Message message = new Message();
        BeanUtils.copyProperties(privateMessageDto, message);
        message.setSenderUserId(senderUserId);
        message.setSessionKey(sessionKey);
        message.setType(MessageConstants.TYPE_PRIVATE);
        message.setReadStatus(MessageConstants.STATUS_UNREAD);
        messageMapper.insertSelective(message);
        sendMessageToSocket(message);
    }

    public PageVo<List<MessageSessionVo>> getAllSessionPageableByUserId(int pageNum, int pageSize, int userId) throws SelectException {
        PageHelper.startPage(pageNum, pageSize);
        List<MessageSessionDto> messageSessionDtos = messageMapper.selectSessionByUserId(userId);
        PageInfo<MessageSessionDto> pageInfo = new PageInfo<>(messageSessionDtos);
        List<MessageSessionVo> messageSessionVos = new ArrayList<>();
        for (MessageSessionDto messageSessionDto : messageSessionDtos) {
            MessageSessionVo messageSessionVo = new MessageSessionVo();
            String[] userIds = messageSessionDto.getSessionKey().split(":");
            if (userIds.length == 2) {
                int firstUserId = Integer.parseInt(userIds[0]);
                int secondUserId = Integer.parseInt(userIds[1]);
                if (userId == firstUserId) {
                    messageSessionVo.setTargetUser(userInfoService.getByUserId(secondUserId));
                } else {
                    messageSessionVo.setTargetUser(userInfoService.getByUserId(firstUserId));
                }
            } else {
                throw new SelectException("服务器内部错误！");
            }

            MessageVo lastMessage = messageMapper.selectLastVoBySessionKey(messageSessionDto.getSessionKey());
            // 已经软删除的会话不再展示：）
            if(lastMessage.getReadStatus()== MessageConstants.STATUS_SOFT_DEL){
                messageSessionVo.setLatestMessage(lastMessage);
                MessageUserIdsAndReadStatusCriteria criteria = MessageUserIdsAndReadStatusCriteria.builder()
                        .senderUserId(messageSessionVo.getTargetUser().getUserId())
                        .receiverUserId(userId)
                        .readStatus(MessageConstants.STATUS_UNREAD).build();
                messageSessionVo.setUnreadCount(messageMapper.selectCountByCriteria(criteria));

                messageSessionVos.add(messageSessionVo);
            }
        }
        return new PageVo<>(pageInfo.getPageNum(), pageInfo.getSize(), messageSessionVos);
    }


    public List<MessageVo> getAllByUserIds(int firstUserId, int secondUserId) {
        String sessionKey;
        if (firstUserId <= secondUserId) {
            sessionKey = firstUserId + ":" + secondUserId;
        } else {
            sessionKey = secondUserId + ":" + firstUserId;
        }
        return messageMapper.selectVoBySessionKey(sessionKey);
    }
    // 把最近的消息status更改以下 主要服务软删除状态
    public void changeLastMessageStatusByUserId(int senderId, int receiverId, int statusToChange){
        MessageUserIdsAndReadStatusCriteria criteria = MessageUserIdsAndReadStatusCriteria.builder()
                .senderUserId(senderId)
                .receiverUserId(receiverId).build();

        messageMapper.updateLastMsgReadStatusAndReadTimeByCriteria(
                MessageConstants.STATUS_SOFT_DEL,new Date(), criteria);
    }

    public void readAllByUserIds(int senderId, int receiverId) {
        MessageUserIdsAndReadStatusCriteria criteria = MessageUserIdsAndReadStatusCriteria.builder()
                .senderUserId(senderId)
                .receiverUserId(receiverId)
                .readStatus(MessageConstants.STATUS_UNREAD).build();
        messageMapper.updateReadStatusAndReadTimeByCriteria(MessageConstants.STATUS_READ,new Date(), criteria);
        sendReceiveMessageToSocket(senderId, receiverId);
    }

    @Async
    void sendMessageToSocket(Message message) {
        Integer receiverUserId = message.getReceiverUserId();
        MessageVo messageVo = new MessageVo();
        BeanUtils.copyProperties(message, messageVo);
        WebSocketMessageDto<MessageVo> webSocketMessageDto = new WebSocketMessageDto<>(WebSocketConstants.TYPE_MESSAGE, messageVo);
        try {
            String textMessage = JacksonUtil.objToJson(webSocketMessageDto);
            webSocketService.sendMessage(receiverUserId, textMessage);
        } catch (Exception ignore) {

        }
    }

    @Async
    void sendReceiveMessageToSocket(int senderId, int receiverId) {
        WebSocketMessageDto<Integer> webSocketMessageDto = new WebSocketMessageDto<>(WebSocketConstants.TYPE_RECEIVE_MESSAGE, receiverId);
        try {
            String textMessage = JacksonUtil.objToJson(webSocketMessageDto);
            webSocketService.sendMessage(senderId, textMessage);
        } catch (Exception ignore) {

        }
    }
}