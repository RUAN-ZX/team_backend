package cn.steateam.lets_team_server.mapper;

import cn.steateam.lets_team_server.entity.AuthorizationInfo;import org.apache.ibatis.annotations.Param;import java.util.List;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/30 12:15 上午
 */
public interface AuthorizationInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AuthorizationInfo record);

    int insertSelective(AuthorizationInfo record);

    AuthorizationInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AuthorizationInfo record);

    int updateByPrimaryKey(AuthorizationInfo record);

    AuthorizationInfo selectByUserIdAndType(@Param("userId") Integer userId, @Param("type") Integer type);

    AuthorizationInfo selectByAccountAndType(@Param("account") String account, @Param("type") Integer type);

    List<AuthorizationInfo> selectByUserId(@Param("userId") Integer userId);
}