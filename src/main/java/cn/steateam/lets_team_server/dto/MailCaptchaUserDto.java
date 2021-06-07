package cn.steateam.lets_team_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @ClassName: MailCaptchaUserDto
 * @Description
 * @Author Ryan
 * @Date 2021.4.14 15:04
 * @Version 1.0.0-Beta
 **/
@Data
public class MailCaptchaUserDto {
    /**
     * 邮箱
     */
    @NotEmpty(message = "邮箱不能为空！")
    private String mail;
    /**
     * 验证码
     */
    @NotEmpty(message = "验证码不能为空！")
    private String captcha;
}
