package cn.steateam.lets_team_server.service;

import cn.steateam.lets_team_server.entity.User;
import cn.steateam.lets_team_server.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户服务类
 *
 * @author STEA_YY
 */
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public User getUserById(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public void saveUser(User user) {
        userMapper.insertSelective(user);
    }
}
