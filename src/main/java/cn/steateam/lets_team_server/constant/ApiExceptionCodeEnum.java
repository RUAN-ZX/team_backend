package cn.steateam.lets_team_server.constant;

import lombok.Getter;

/**
 * 错误码枚举
 *
 * @author STEA_YY
 **/
@Getter
public enum ApiExceptionCodeEnum {
    /**
     * token校验失败
     */
    TOKEN_CHECK_FAIL(102, "token校验失败！"),
    /**
     * 无权限访问
     */
    ACCESS_FORBIDDEN(104, "无权限访问！"),
    /**
     * 无权限操作
     */
    PERMISSION_DENIED(105, "无权限操作！"),
    /**
     * 用户不存在
     */
    USER_NOT_FOUND(301, "用户不存在！"),
    /**
     * 用户名或密码错误
     */
    INVALID_USER_PASSWORD(302, "用户名或密码错误！"),
    /**
     * 验证码校验异常
     */
    CODE_VERIFICATION_FAIL(302, "验证码校验失败！"),
    /**
     * 该用户已经存在
     */
    ACCOUNT_EXISTED(303, "该用户已经存在！"),
    /**
     * 创建失败
     */
    INSERT_ERROR(401, "创建失败！"),
    /**
     * 修改失败
     */
    UPDATE_ERROR(402, "修改失败！"),
    /**
     * 删除失败
     */
    DELETE_ERROR(403, "删除失败"),
    /**
     * 未找到结果
     */
    SELECT_ERROR(404, "未找到结果！"),
    /**
     * 站内信发送异常
     */
    MESSAGE_SENDING_ERROR(501, "站内信发送异常！"),
    /**
     * 邮件发送异常
     */
    MAIL_SENDING_ERROR(502, "站内信发送异常！"),
    /**
     * 参数校验失败
     */
    ARGUMENTS_VALID_FAIL(601, "参数校验失败！"),
    /**
     * header校验失败
     */
    HEADER_VALID_FAIL(602, "header校验失败！"),
    /**
     * 访问次数超限
     */
    REQUEST_TIMES_EXCEEDED(701, "访问次数超限！"),
    /**
     * 授权失败
     */
    AUTHORIZATION_FAIL(801, "授权失败！");

    private final Integer value;
    private final String desc;

    ApiExceptionCodeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}
