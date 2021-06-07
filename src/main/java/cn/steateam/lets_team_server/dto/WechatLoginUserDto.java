package cn.steateam.lets_team_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 微信注册/登录DTO
 *
 * @author STEA_YY
 */
@Data
public class WechatLoginUserDto {

    /**
     * 昵称
     */
    @NotEmpty(message = "昵称不能为空")
    private String nickname;

    /**
     * 微信code
     */
    @NotEmpty(message = "code不能为空")
    private String code;

    /**
     * 头像URL
     */
    private String avatarUrl;
}
