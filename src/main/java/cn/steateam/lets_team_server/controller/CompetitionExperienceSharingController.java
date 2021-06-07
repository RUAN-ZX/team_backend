package cn.steateam.lets_team_server.controller;

import cn.steateam.lets_team_server.constant.CompetitionExperienceSharingStatusEnum;
import cn.steateam.lets_team_server.dto.CompetitionExperienceSharingCreateDto;
import cn.steateam.lets_team_server.dto.CompetitionExperienceSharingEditDto;
import cn.steateam.lets_team_server.dto.CompetitionExperienceSharingReplyDto;
import cn.steateam.lets_team_server.exception.*;
import cn.steateam.lets_team_server.service.CompetitionExperienceSharingService;
import cn.steateam.lets_team_server.util.ThreadLocalUtil;
import cn.steateam.lets_team_server.vo.CompetitionExperienceSharingDetailedVo;
import cn.steateam.lets_team_server.vo.CompetitionExperienceSharingReplyVo;
import cn.steateam.lets_team_server.vo.ResponseBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目经验分享类请求
 *
 * @author YaoYi
 * @date 2021/3/25 12:22 上午
 */
@RestController
@RequestMapping("/sharing")
public class CompetitionExperienceSharingController {

    @Resource
    private CompetitionExperienceSharingService competitionExperienceSharingService;

    /**
     * 根据id获取竞赛经验分享
     *
     * @param id 竞赛经验分享id
     */
    @GetMapping("/{id}")
    public ResponseBean<CompetitionExperienceSharingDetailedVo> getVoById(@PathVariable int id) throws SelectException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        CompetitionExperienceSharingDetailedVo competitionExperienceSharingDetailedVo = getVoWithPermission(userId, id);
        return new ResponseBean<>(competitionExperienceSharingDetailedVo);
    }

    /**
     * 根据竞赛经验分享id获取全部回复
     *
     * @param id 竞赛经验分享id
     */
    @GetMapping("/{id}/replies")
    public ResponseBean<List<CompetitionExperienceSharingReplyVo>> getAllReplyVoById(@PathVariable int id) throws SelectException {
        List<CompetitionExperienceSharingReplyVo> replyVos = competitionExperienceSharingService.getDirectReplyVoBySharingIdAndStatusList(id,
                new ArrayList<>(CompetitionExperienceSharingStatusEnum.NORMAL.getValue()));
        return new ResponseBean<>(replyVos);
    }

    /**
     * 根据回复id获取回复
     *
     * @param replyId 回复id
     */
    @GetMapping("/reply/{replyId}")
    public ResponseBean<CompetitionExperienceSharingReplyVo> getReplyVoByReplyId(@PathVariable int replyId) throws SelectException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        CompetitionExperienceSharingReplyVo competitionExperienceSharingReplyVo = getReplyVoWithPermission(userId, replyId);
        return new ResponseBean<>(competitionExperienceSharingReplyVo);
    }

    /**
     * 根据回复id获取全部间接回复
     *
     * @param replyId 回复id
     * @return
     * @throws SelectException
     */
    @GetMapping("/reply/{replyId}/replies")
    public ResponseBean<List<CompetitionExperienceSharingReplyVo>> getAllIndirectReplyVoByReplyId(@PathVariable int replyId) throws SelectException {
        List<CompetitionExperienceSharingReplyVo> replyVos = competitionExperienceSharingService.getIndirectReplyVoByReplyIdAndStatusList(replyId,
                new ArrayList<>(CompetitionExperienceSharingStatusEnum.NORMAL.getValue()));
        return new ResponseBean<>(replyVos);
    }

    /**
     * 新建项目经验分享
     *
     * @param competitionExperienceSharingCreateDto 项目经验分享创建DTO
     */
    @PostMapping()
    public ResponseBean<Integer> save(@Validated @RequestBody CompetitionExperienceSharingCreateDto competitionExperienceSharingCreateDto) throws InsertException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        Integer insertedId = competitionExperienceSharingService.save(competitionExperienceSharingCreateDto, userId);
        return new ResponseBean<>(insertedId);
    }

    /**
     * 修改项目经验分享
     *
     * @param competitionExperienceSharingEditDto 项目经验分享修改DTO
     * @param id                                  项目经验分享id
     */
    @PutMapping("/{id}")
    public ResponseBean<Object> update(@RequestBody CompetitionExperienceSharingEditDto competitionExperienceSharingEditDto, @PathVariable int id) throws PermissionDeniedException, UpdateException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean isCreator = competitionExperienceSharingService.isCreator(userId, id);
        if (!isCreator) {
            throw new PermissionDeniedException();
        }
        competitionExperienceSharingService.update(competitionExperienceSharingEditDto, id);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 删除项目经验分享
     *
     * @param id 项目经验分享id
     */
    @DeleteMapping("/{id}")
    public ResponseBean<Object> delete(@PathVariable int id) throws PermissionDeniedException, DeleteException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean isCreator = competitionExperienceSharingService.isCreator(userId, id);
        if (!isCreator) {
            throw new PermissionDeniedException();
        }
        competitionExperienceSharingService.delete(id);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 回复项目经验分享(直接、间接回复通用)
     *
     * @param competitionExperienceSharingReplyDto 项目经验分享回复DTO
     * @param sharingId                            项目经验分享id
     */
    @PostMapping("/{sharingId}/reply")
    public ResponseBean<Integer> saveReply(@Validated @RequestBody CompetitionExperienceSharingReplyDto competitionExperienceSharingReplyDto, @PathVariable int sharingId) throws InsertException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        Integer insertedId = competitionExperienceSharingService.saveReply(competitionExperienceSharingReplyDto, sharingId, userId);
        return new ResponseBean<>(insertedId);
    }

    /**
     * 删除回复
     *
     * @param replyId 回复id
     */
    @DeleteMapping("/reply/{replyId}")
    public ResponseBean<Object> deleteReply(@PathVariable int replyId) throws PermissionDeniedException, DeleteException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean isCreator = competitionExperienceSharingService.isCreatorOfReply(userId, replyId);
        if (!isCreator) {
            throw new PermissionDeniedException();
        }
        competitionExperienceSharingService.deleteReply(replyId);
        return ResponseBean.emptySuccessResponse();
    }

    private CompetitionExperienceSharingDetailedVo getVoWithPermission(Integer userId, Integer id) throws SelectException, PermissionDeniedException {
        CompetitionExperienceSharingDetailedVo competitionExperienceSharingDetailedVo = competitionExperienceSharingService.getVoById(id);
        if (competitionExperienceSharingDetailedVo.getStatus() < CompetitionExperienceSharingStatusEnum.NORMAL.getValue()) {
            boolean isCreator = competitionExperienceSharingService.isCreator(userId, id);
            if (!isCreator) {
                throw new PermissionDeniedException("竞赛经验分享暂时无法访问");
            }
        }
        return competitionExperienceSharingDetailedVo;
    }

    private CompetitionExperienceSharingReplyVo getReplyVoWithPermission(Integer userId, Integer replyId) throws SelectException, PermissionDeniedException {
        CompetitionExperienceSharingReplyVo competitionExperienceSharingReplyVo = competitionExperienceSharingService.getReplyVoByReplyId(replyId);
        if (competitionExperienceSharingReplyVo.getStatus() < CompetitionExperienceSharingStatusEnum.NORMAL.getValue()) {
            boolean isCreator = competitionExperienceSharingService.isCreatorOfReply(userId, replyId);
            if (!isCreator) {
                throw new PermissionDeniedException("竞赛经验分享评论暂时无法访问");
            }
        }
        return competitionExperienceSharingReplyVo;
    }
}
