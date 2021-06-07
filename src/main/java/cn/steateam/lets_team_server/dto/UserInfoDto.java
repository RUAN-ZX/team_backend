package cn.steateam.lets_team_server.dto;

import lombok.Data;

/**
 * 用户公开信息DTO
 *
 * @author STEA_YY
 */
@Data
public class UserInfoDto {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像url
     */
    private String avatarUrl;

    /**
     * 用户标签
     */
    private String tags;

    /**
     * 个性签名
     */
    private String signature;
}
