package cn.steateam.lets_team_server.exception;

import cn.steateam.lets_team_server.constant.ApiExceptionCodeEnum;

/**
 * token校验的异常类
 *
 * @author STEA_YY
 **/
public class TokenCheckException extends AuthenticationException {
    public TokenCheckException() {
        super(ApiExceptionCodeEnum.TOKEN_CHECK_FAIL.getDesc(), ApiExceptionCodeEnum.TOKEN_CHECK_FAIL.getValue());
    }

    public TokenCheckException(String message) {
        super(message, ApiExceptionCodeEnum.TOKEN_CHECK_FAIL.getValue());
    }
}
