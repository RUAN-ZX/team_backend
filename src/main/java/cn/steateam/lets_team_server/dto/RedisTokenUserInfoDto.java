package cn.steateam.lets_team_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * redis里存放的Token信息DTO
 *
 * @author STEA_YY
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RedisTokenUserInfoDto {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 客户端id
     */
    private String platformId;
    /**
     * 角色id
     */
    private Integer roleId;
}
