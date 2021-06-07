package cn.steateam.lets_team_server.exception;

import cn.steateam.lets_team_server.exception.ApiException;

/**
 * 鉴权相关的异常类
 *
 * @author STEA_YY
 **/
public abstract class AuthenticationException extends ApiException {

    public AuthenticationException() {
    }

    public AuthenticationException(String message, Integer code) {
        super(message, code);
    }
}