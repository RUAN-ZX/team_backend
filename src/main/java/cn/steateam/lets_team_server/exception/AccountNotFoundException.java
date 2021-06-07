package cn.steateam.lets_team_server.exception;

import cn.steateam.lets_team_server.constant.ApiExceptionCodeEnum;

/**
 * 用户未找到异常类
 *
 * @author STEA_YY
 **/
public class AccountNotFoundException extends AuthenticationException {

    public AccountNotFoundException(String message) {
        super(message, ApiExceptionCodeEnum.USER_NOT_FOUND.getValue());
    }

    public AccountNotFoundException() {
        super(ApiExceptionCodeEnum.USER_NOT_FOUND.getDesc(), ApiExceptionCodeEnum.USER_NOT_FOUND.getValue());
    }
}
