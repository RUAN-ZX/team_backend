package cn.steateam.lets_team_server.service;

import cn.steateam.lets_team_server.entity.AuthorizationInfo;
import cn.steateam.lets_team_server.mapper.AuthorizationInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 授权信息服务类
 *
 * @author STEA_YY
 */
@Service
public class AuthorizationInfoService {
    @Resource
    private AuthorizationInfoMapper authorizationInfoMapper;

    public AuthorizationInfo getAuthorizationInfoByUserIdAndType(int userId, int type) {
        return authorizationInfoMapper.selectByUserIdAndType(userId, type);
    }

    public AuthorizationInfo getAuthorizationInfoByAccountAndType(String account, int type) {
        return authorizationInfoMapper.selectByAccountAndType(account, type);
    }

    public void saveAuthorizationInfo(AuthorizationInfo authorizationInfo) {
        authorizationInfoMapper.insertSelective(authorizationInfo);
    }
}
