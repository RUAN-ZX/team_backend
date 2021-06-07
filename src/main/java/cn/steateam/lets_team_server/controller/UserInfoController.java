package cn.steateam.lets_team_server.controller;

import cn.steateam.lets_team_server.annotation.RequiresLogin;
import cn.steateam.lets_team_server.dto.UserInfoDto;
import cn.steateam.lets_team_server.entity.UserInfo;
import cn.steateam.lets_team_server.service.UserInfoService;
import cn.steateam.lets_team_server.util.ThreadLocalUtil;
import cn.steateam.lets_team_server.vo.ResponseBean;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户公开信息类请求
 *
 * @author STEA_YY
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Resource
    private UserInfoService userInfoService;

    /**
     * 获取当前用户个人信息
     */
    @GetMapping("/me")
    @RequiresLogin
    public ResponseBean<UserInfo> getPersonalUserInfo() {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        UserInfo userInfo = userInfoService.getByUserId(userId);
        return new ResponseBean<>(userInfo);
    }

    /**
     * 获取指定用户个人信息
     *
     * @param userId 用户id
     */
    @GetMapping("/{userId}")
    @RequiresLogin
    public ResponseBean<UserInfo> getUserInfoByUserId(@PathVariable int userId) {
        UserInfo userInfo = userInfoService.getByUserId(userId);
        return new ResponseBean<>(userInfo);
    }

    /**
     * 修改用户公开信息
     *
     * @param userInfoDto 用户公开信息封装
     */
    @PutMapping("/me")
    @RequiresLogin
    public ResponseBean<Object> updatePersonalUserInfo(@RequestBody UserInfoDto userInfoDto) {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        userInfoService.updateByUserId(userInfoDto, userId);
        return ResponseBean.emptySuccessResponse();
    }
}
