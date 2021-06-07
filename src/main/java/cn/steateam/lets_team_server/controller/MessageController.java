package cn.steateam.lets_team_server.controller;

import cn.steateam.lets_team_server.annotation.RequiresLogin;
import cn.steateam.lets_team_server.constant.MessageConstants;
import cn.steateam.lets_team_server.dto.PrivateMessageDto;
import cn.steateam.lets_team_server.exception.MessageSendingException;
import cn.steateam.lets_team_server.exception.SelectException;
import cn.steateam.lets_team_server.service.MessageService;
import cn.steateam.lets_team_server.util.ThreadLocalUtil;
import cn.steateam.lets_team_server.vo.MessageVo;
import cn.steateam.lets_team_server.vo.PageVo;
import cn.steateam.lets_team_server.vo.ResponseBean;
import cn.steateam.lets_team_server.vo.MessageSessionVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 站内信相关请求
 *
 * @author STEA_YY
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    @Resource
    private MessageService messageService;

    /**
     * 获取全部最近会话
     *
     * @param pageNum  页码
     * @param pageSize 分页大小
     */
    @GetMapping("/session/history")
    @RequiresLogin
    public ResponseBean<PageVo<List<MessageSessionVo>>> getAllSessionPageable(@RequestParam int pageNum, @RequestParam int pageSize) throws SelectException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        PageVo<List<MessageSessionVo>> sessionVos = messageService.getAllSessionPageableByUserId(pageNum, pageSize, userId);
        return new ResponseBean<>(sessionVos);
    }

    /**
     * 获取全部私信(会话内)
     *
     * @param targetUserId 对方用户id
     */
    @GetMapping()
    @RequiresLogin
    public ResponseBean<List<MessageVo>> getAllMessageByUserId(@RequestParam int targetUserId) {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        List<MessageVo> messageVos = messageService.getAllByUserIds(userId, targetUserId);
        return new ResponseBean<>(messageVos);
    }

    /**
     * 标记所有会已经被软删除 方法是最新消息status==2
     * 如果有消息更新 则原status变为1（已读）
     * 此时最新消息为未读 可以被拉取
     *
     * @param targetUserId 对方(寄信人)用户id
     */
    @PutMapping("/status/softDelete")
    @RequiresLogin
    public ResponseBean<Object> softDeleteSessionByUserId(@RequestParam int targetUserId){
        Integer senderUserId = ThreadLocalUtil.getCurrentUser();
        messageService.changeLastMessageStatusByUserId(senderUserId,targetUserId,
                MessageConstants.STATUS_SOFT_DEL);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 标记所有私信已读
     *
     * @param targetUserId 对方用户id
     */
    @PutMapping("/status/read")
    @RequiresLogin
    public ResponseBean<Object> readAllMessageByTargetUserId(@RequestParam int targetUserId) {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        messageService.readAllByUserIds(targetUserId, userId);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 发送私信
     *
     * @param privateMessageDto 私信信息封装
     */
    @PostMapping()
    @RequiresLogin
    public ResponseBean<Object> sendPrivateMessage(@Validated @RequestBody PrivateMessageDto privateMessageDto) throws MessageSendingException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        messageService.sendPrivateMessage(userId, privateMessageDto);
        return ResponseBean.emptySuccessResponse();
    }
}
