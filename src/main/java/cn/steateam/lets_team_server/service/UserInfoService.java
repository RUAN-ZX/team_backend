package cn.steateam.lets_team_server.service;

import cn.steateam.lets_team_server.dto.UserInfoDto;
import cn.steateam.lets_team_server.entity.UserInfo;
import cn.steateam.lets_team_server.mapper.UserInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户信息服务类
 *
 * @author STEA_YY
 */
@Service
public class UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;

    public UserInfo getByUserId(int userId) {
        return userInfoMapper.selectByPrimaryKey(userId);
    }

    public void save(UserInfo userInfo) {
        userInfoMapper.insertSelective(userInfo);
    }

    public void updateByUserId(UserInfoDto userInfoDto, int userId) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoDto, userInfo);
        userInfo.setUserId(userId);
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }
}
