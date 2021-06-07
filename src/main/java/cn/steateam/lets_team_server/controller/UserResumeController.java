package cn.steateam.lets_team_server.controller;

import cn.steateam.lets_team_server.annotation.RequiresLogin;
import cn.steateam.lets_team_server.dto.UserResumeBasicDto;
import cn.steateam.lets_team_server.dto.UserResumeHonorDto;
import cn.steateam.lets_team_server.entity.UserResumeBasic;
import cn.steateam.lets_team_server.entity.UserResumeHonor;
import cn.steateam.lets_team_server.exception.PermissionDeniedException;
import cn.steateam.lets_team_server.service.UserResumeService;
import cn.steateam.lets_team_server.util.ThreadLocalUtil;
import cn.steateam.lets_team_server.vo.ResponseBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户简历类请求
 *
 * @author STEA_YY
 */
@RestController
@RequestMapping("/resume")
public class UserResumeController {
    @Resource
    private UserResumeService userResumeService;

    /**
     * 获取用户个人简历基础信息
     */
    @GetMapping("/basic/me")
    @RequiresLogin
    public ResponseBean<UserResumeBasic> getPersonalResumeBasic() {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        UserResumeBasic userResumeBasic = userResumeService.getBasicByUserId(userId);
        return new ResponseBean<>(userResumeBasic);
    }

    /**
     * 修改简历基础信息
     *
     * @param userResumeBasicDto 简历基础信息
     */
    @PutMapping("/basic/me")
    @RequiresLogin
    public ResponseBean<Object> updatePersonalResumeBasic(@RequestBody UserResumeBasicDto userResumeBasicDto) {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        userResumeService.updateBasicByUserId(userResumeBasicDto, userId);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 获取用户个人简历荣誉列表
     */
    @GetMapping("/honors/me")
    @RequiresLogin
    public ResponseBean<List<UserResumeHonor>> getPersonalResumeHonors() {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        List<UserResumeHonor> userResumeHonors = userResumeService.getAllHonorByUserId(userId);
        return new ResponseBean<>(userResumeHonors);
    }

    /**
     * 添加简历荣誉
     *
     * @param userResumeHonorDto 简历荣誉信息
     */
    @PostMapping("/honor")
    @RequiresLogin
    public ResponseBean<Object> saveHonor(@Validated @RequestBody UserResumeHonorDto userResumeHonorDto) {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        userResumeService.saveHonorByUserId(userResumeHonorDto, userId);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 修改简历荣誉
     *
     * @param userResumeHonorDto 简历荣誉
     * @param id                 简历荣誉id
     */
    @PutMapping("/honor/{id}")
    @RequiresLogin
    public ResponseBean<Object> updateHonor(@RequestBody UserResumeHonorDto userResumeHonorDto, @PathVariable int id) throws PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        if (!userResumeService.isOwnerOfHonor(id, userId)) {
            throw new PermissionDeniedException("只有本人可以修改简历荣誉！");
        }
        userResumeService.updateHonorById(userResumeHonorDto, id);
        return ResponseBean.emptySuccessResponse();
    }

    /**
     * 删除简历荣誉
     *
     * @param id 简历荣誉id
     */
    @DeleteMapping("/honor/{id}")
    @RequiresLogin
    public ResponseBean<Object> deleteHonor(@PathVariable int id) throws PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        if (!userResumeService.isOwnerOfHonor(id, userId)) {
            throw new PermissionDeniedException("只有本人可以删除简历荣誉！");
        }
        userResumeService.deleteHonorById(id);
        return ResponseBean.emptySuccessResponse();
    }
}
