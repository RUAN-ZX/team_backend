package cn.steateam.lets_team_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 登录信息DTO
 *
 * @author STEA_YY
 */
@Data
public class MailLoginUserDto {
    /**
     * 邮箱
     */
    @NotEmpty(message = "邮箱不能为空！")
    private String mail;
    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空！")
    private String password;
}
