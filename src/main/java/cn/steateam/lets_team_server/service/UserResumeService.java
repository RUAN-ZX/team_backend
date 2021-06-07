package cn.steateam.lets_team_server.service;

import cn.steateam.lets_team_server.dto.UserResumeBasicDto;
import cn.steateam.lets_team_server.dto.UserResumeHonorDto;
import cn.steateam.lets_team_server.entity.UserResumeBasic;
import cn.steateam.lets_team_server.entity.UserResumeHonor;
import cn.steateam.lets_team_server.mapper.UserResumeBasicMapper;
import cn.steateam.lets_team_server.mapper.UserResumeHonorMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户简历服务类
 *
 * @author STEA_YY
 */
@Service
public class UserResumeService {
    @Resource
    private UserResumeBasicMapper userResumeBasicMapper;
    @Resource
    private UserResumeHonorMapper userResumeHonorMapper;

    public void saveBasic(UserResumeBasic userResumeBasic) {
        userResumeBasicMapper.insertSelective(userResumeBasic);
    }

    public UserResumeBasic getBasicByUserId(int userId) {
        return userResumeBasicMapper.selectByPrimaryKey(userId);
    }

    public List<UserResumeHonor> getAllHonorByUserId(int userId) {
        return userResumeHonorMapper.selectByUserId(userId);
    }

    public void updateBasicByUserId(UserResumeBasicDto userResumeBasicDto, int userId) {
        UserResumeBasic userResumeBasic = new UserResumeBasic();
        BeanUtils.copyProperties(userResumeBasicDto, userResumeBasic);
        userResumeBasic.setUserId(userId);
        userResumeBasicMapper.updateByPrimaryKeySelective(userResumeBasic);
    }

    public void saveHonorByUserId(UserResumeHonorDto userResumeHonorDto, int userId) {
        UserResumeHonor userResumeHonor = new UserResumeHonor();
        BeanUtils.copyProperties(userResumeHonorDto, userResumeHonor);
        userResumeHonor.setUserId(userId);
        userResumeHonorMapper.insertSelective(userResumeHonor);
    }

    public void updateHonorById(UserResumeHonorDto userResumeHonorDto, int id) {
        UserResumeHonor userResumeHonor = new UserResumeHonor();
        BeanUtils.copyProperties(userResumeHonorDto, userResumeHonor);
        userResumeHonor.setId(id);
        userResumeHonorMapper.updateByPrimaryKeySelective(userResumeHonor);
    }

    public void deleteHonorById(int id) {
        userResumeHonorMapper.deleteByPrimaryKey(id);
    }

    public boolean isOwnerOfHonor(int honorId, int userId) {
        UserResumeHonor userResumeHonor = userResumeHonorMapper.selectByPrimaryKey(honorId);
        if (userResumeHonor == null) {
            return false;
        }
        return userResumeHonor.getUserId() == userId;
    }
}
