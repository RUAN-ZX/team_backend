package cn.steateam.lets_team_server.controller;

import cn.steateam.lets_team_server.constant.CompetitionQuestionStatusEnum;
import cn.steateam.lets_team_server.dto.CompetitionAnswerDto;
import cn.steateam.lets_team_server.dto.CompetitionQuestionCreateDto;
import cn.steateam.lets_team_server.dto.CompetitionQuestionEditDto;
import cn.steateam.lets_team_server.exception.*;
import cn.steateam.lets_team_server.service.CompetitionQuestionService;
import cn.steateam.lets_team_server.util.ThreadLocalUtil;
import cn.steateam.lets_team_server.vo.CompetitionAnswerVo;
import cn.steateam.lets_team_server.vo.CompetitionQuestionVo;
import cn.steateam.lets_team_server.vo.ResponseBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 竞赛问答类请求
 *
 * @author YaoYi
 * @date 2021/3/21 1:01 下午
 */
@RestController
@RequestMapping("/question")
public class CompetitionQuestionController {
    @Resource
    private CompetitionQuestionService competitionQuestionService;

    /**
     * 根据id获取竞赛提问
     *
     * @param questionId 竞赛提问id
     */
    @GetMapping("/{questionId}")
    public ResponseBean<CompetitionQuestionVo> getQuestionVoByQuestionId(@PathVariable Integer questionId) throws SelectException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        CompetitionQuestionVo competitionQuestionVo = getQuestionVoWithPermission(userId, questionId);
        return new ResponseBean<>(competitionQuestionVo);
    }

    /**
     * 根据提问id获取全部回答
     *
     * @param questionId 竞赛提问id
     */
    @GetMapping("/{questionId}/answers")
    public ResponseBean<List<CompetitionAnswerVo>> getAllAnswerVoByQuestionId(@PathVariable Integer questionId) throws SelectException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        getQuestionVoWithPermission(userId, questionId);
        List<CompetitionAnswerVo> competitionAnswerVos = competitionQuestionService.getAllAnswerVoByQuestionIdAndStatusList(questionId,
                new ArrayList<>(CompetitionQuestionStatusEnum.NORMAL.getValue()));
        return new ResponseBean<>(competitionAnswerVos);
    }

    /**
     * 根据回答id获取回答
     *
     * @param answerId 回答id
     */
    @GetMapping("/answer/{answerId}")
    public ResponseBean<CompetitionAnswerVo> getAnswerVoByAnswerId(@PathVariable Integer answerId) throws SelectException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        CompetitionAnswerVo competitionAnswerVo = getAnswerVoWithPermission(userId, answerId);
        return new ResponseBean<>(competitionAnswerVo);
    }

    /**
     * 创建竞赛提问
     *
     * @param competitionQuestionCreateDto 竞赛提问创建DTO
     */
    @PostMapping()
    public ResponseBean<Integer> saveQuestion(@Validated @RequestBody CompetitionQuestionCreateDto competitionQuestionCreateDto) throws InsertException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        Integer insertedId = competitionQuestionService.saveQuestion(competitionQuestionCreateDto, userId);
        return new ResponseBean<>(insertedId);
    }

    /**
     * 根据竞赛提问id回答竞赛提问
     *
     * @param questionId           竞赛提问id
     * @param competitionAnswerDto 竞赛提问回答DTO
     */
    @PostMapping("/{questionId}/answer")
    public ResponseBean<Integer> saveAnswer(@PathVariable Integer questionId, @Validated @RequestBody CompetitionAnswerDto competitionAnswerDto) throws SelectException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        Integer insertedId = competitionQuestionService.saveAnswer(competitionAnswerDto, questionId, userId);
        return new ResponseBean<>(insertedId);
    }

    /**
     * 修改竞赛提问
     *
     * @param questionId                 竞赛提问id
     * @param competitionQuestionEditDto 竞赛提问修改DTO
     */
    @PutMapping("/{questionId}")
    public ResponseBean<Object> updateQuestion(@PathVariable Integer questionId, @RequestBody CompetitionQuestionEditDto competitionQuestionEditDto) throws UpdateException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean isCreator = competitionQuestionService.isCreatorOfQuestion(userId, questionId);
        if (!isCreator) {
            throw new PermissionDeniedException();
        }
        competitionQuestionService.updateQuestion(competitionQuestionEditDto, questionId);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 修改竞赛提问回答
     *
     * @param answerId             竞赛提问回答id
     * @param competitionAnswerDto 竞赛提问回答DTO
     */
    @PutMapping("/answer/{answerId}")
    public ResponseBean<Object> updateAnswer(@PathVariable Integer answerId, @RequestBody CompetitionAnswerDto competitionAnswerDto) throws PermissionDeniedException, UpdateException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean isCreator = competitionQuestionService.isCreatorOfAnswer(userId, answerId);
        if (!isCreator) {
            throw new PermissionDeniedException();
        }
        competitionQuestionService.updateAnswer(competitionAnswerDto, answerId);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 删除竞赛提问
     *
     * @param questionId 竞赛提问id
     */
    @DeleteMapping("/{questionId}")
    public ResponseBean<Object> deleteQuestion(@PathVariable Integer questionId) throws PermissionDeniedException, DeleteException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean isCreator = competitionQuestionService.isCreatorOfQuestion(userId, questionId);
        if (!isCreator) {
            throw new PermissionDeniedException();
        }
        competitionQuestionService.deleteQuestion(questionId);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 删除回答
     *
     * @param answerId 回答id
     */
    @DeleteMapping("/answer/{answerId}")
    public ResponseBean<Object> deleteAnswer(@PathVariable Integer answerId) throws PermissionDeniedException, DeleteException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean isCreator = competitionQuestionService.isCreatorOfAnswer(userId, answerId);
        if (!isCreator) {
            throw new PermissionDeniedException();
        }
        competitionQuestionService.deleteAnswer(answerId);
        return ResponseBean.emptySuccessResponse();
    }

    private CompetitionQuestionVo getQuestionVoWithPermission(Integer userId, Integer id) throws SelectException, PermissionDeniedException {
        CompetitionQuestionVo competitionQuestionVo = competitionQuestionService.getQuestionVoByQuestionId(id);
        if (competitionQuestionVo.getStatus() < CompetitionQuestionStatusEnum.NORMAL.getValue()) {
            boolean isCreator = competitionQuestionService.isCreatorOfQuestion(userId, id);
            if (!isCreator) {
                throw new PermissionDeniedException("比赛提问暂时无法访问");
            }
        }
        return competitionQuestionVo;
    }

    private CompetitionAnswerVo getAnswerVoWithPermission(Integer userId, Integer id) throws SelectException, PermissionDeniedException {
        CompetitionAnswerVo competitionAnswerVo = competitionQuestionService.getAnswerVoByAnswerId(id);
        if (competitionAnswerVo.getStatus() < CompetitionQuestionStatusEnum.NORMAL.getValue()) {
            boolean isCreator = competitionQuestionService.isCreatorOfAnswer(userId, id);
            if (!isCreator) {
                throw new PermissionDeniedException("比赛提问暂时无法访问");
            }
        }
        return competitionAnswerVo;
    }
}
