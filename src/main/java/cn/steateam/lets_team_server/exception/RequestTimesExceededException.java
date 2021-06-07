package cn.steateam.lets_team_server.exception;

import cn.steateam.lets_team_server.constant.ApiExceptionCodeEnum;

/**
 * 访问次数限制异常类
 *
 * @author STEA_YY
 **/
public class RequestTimesExceededException extends RuntimeApiException {
    public RequestTimesExceededException() {
        super(ApiExceptionCodeEnum.REQUEST_TIMES_EXCEEDED.getDesc(), ApiExceptionCodeEnum.REQUEST_TIMES_EXCEEDED.getValue());
    }

    public RequestTimesExceededException(String message) {
        super(message, ApiExceptionCodeEnum.REQUEST_TIMES_EXCEEDED.getValue());
    }
}