package cn.steateam.lets_team_server.exception;

import cn.steateam.lets_team_server.constant.ApiExceptionCodeEnum;

/**
 * 密码错误异常类
 *
 * @author STEA_YY
 **/
public class InvalidPasswordException extends AuthenticationException {

    public InvalidPasswordException(String message) {
        super(message, ApiExceptionCodeEnum.INVALID_USER_PASSWORD.getValue());
    }

    public InvalidPasswordException() {
        super(ApiExceptionCodeEnum.INVALID_USER_PASSWORD.getDesc(), ApiExceptionCodeEnum.INVALID_USER_PASSWORD.getValue());
    }
}
