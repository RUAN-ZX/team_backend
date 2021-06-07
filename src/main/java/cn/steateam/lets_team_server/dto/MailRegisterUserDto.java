package cn.steateam.lets_team_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 邮件注册信息DTO
 *
 * @author STEA_YY
 */
@Data
public class MailRegisterUserDto {
    /**
     * 邮箱
     */
    @NotEmpty(message = "邮箱不能为空！")
    private String mail;
    /**
     * 昵称
     */
    @NotEmpty(message = "昵称不能为空！")
    private String nickname;
    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空！")
    private String password;
    /**
     * 验证码
     */
    @NotEmpty(message = "验证码不能为空！")
    private String code;
}
