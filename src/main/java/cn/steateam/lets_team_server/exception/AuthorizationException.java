package cn.steateam.lets_team_server.exception;

import cn.steateam.lets_team_server.constant.ApiExceptionCodeEnum;

/**
 * 授权异常类
 *
 * @author STEA_YY
 **/
public class AuthorizationException extends ApiException {
    public AuthorizationException() {
        super(ApiExceptionCodeEnum.AUTHORIZATION_FAIL.getDesc(), ApiExceptionCodeEnum.AUTHORIZATION_FAIL.getValue());
    }

    public AuthorizationException(String message) {
        super(message, ApiExceptionCodeEnum.AUTHORIZATION_FAIL.getValue());
    }
}
