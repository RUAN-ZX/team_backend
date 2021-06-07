package cn.steateam.lets_team_server.controller;

import cn.steateam.lets_team_server.annotation.RequiresLogin;
import cn.steateam.lets_team_server.exception.PermissionDeniedException;
import cn.steateam.lets_team_server.service.NoticeService;
import cn.steateam.lets_team_server.util.ThreadLocalUtil;
import cn.steateam.lets_team_server.vo.NoticeVo;
import cn.steateam.lets_team_server.vo.PageVo;
import cn.steateam.lets_team_server.vo.ResponseBean;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 通知类请求
 *
 * @author STEA_YY
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Resource
    private NoticeService noticeService;

    /**
     * 分页获取用户全部通知
     *
     * @param pageNum  页码
     * @param pageSize 分页大小
     */
    @GetMapping("/history")
    @RequiresLogin
    public ResponseBean<PageVo<List<NoticeVo>>> getAllPersonalNoticeVoPageable(@RequestParam int pageNum, @RequestParam int pageSize) {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        PageVo<List<NoticeVo>> noticeVos = noticeService.getAllVoPageableByUserId(pageNum, pageSize, userId);
        return new ResponseBean<>(noticeVos);
    }

    /**
     * 已读某条通知
     *
     * @param id 通知id
     */
    @PutMapping("/status/read/{id}")
    public ResponseBean<Object> readNoticeById(@PathVariable int id) throws PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        if (!noticeService.isOwner(id, userId)) {
            throw new PermissionDeniedException();
        }
        noticeService.readById(id);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 已读全部通知
     */
    @PutMapping("/status/read/all")
    public ResponseBean<Object> readAllPersonalNotice() {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        noticeService.readAllByReceiverUserId(userId);
        return ResponseBean.emptySuccessResponse();
    }
}
