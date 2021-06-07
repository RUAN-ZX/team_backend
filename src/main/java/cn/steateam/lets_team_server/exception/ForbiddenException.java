package cn.steateam.lets_team_server.exception;

import cn.steateam.lets_team_server.constant.ApiExceptionCodeEnum;

/**
 * 鉴权错误异常类，对应HTTP403
 *
 * @author STEA_YY
 **/
public class ForbiddenException extends ApiException {
    public ForbiddenException() {
        super(ApiExceptionCodeEnum.ACCESS_FORBIDDEN.getDesc(), ApiExceptionCodeEnum.ACCESS_FORBIDDEN.getValue());
    }

    public ForbiddenException(String message) {
        super(message, ApiExceptionCodeEnum.ACCESS_FORBIDDEN.getValue());
    }
}
