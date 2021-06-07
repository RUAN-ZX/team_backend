package cn.steateam.lets_team_server.exception;

import cn.steateam.lets_team_server.constant.ApiExceptionCodeEnum;

/**
 * 请求header校验异常错误
 *
 * @author YaoYi
 * @date 2021/2/25 4:07 下午
 */
public class HeadersNotValidException extends ApiException {
    public HeadersNotValidException(String message) {
        super(message, ApiExceptionCodeEnum.HEADER_VALID_FAIL.getValue());
    }

    public HeadersNotValidException() {
        super(ApiExceptionCodeEnum.HEADER_VALID_FAIL.getDesc(), ApiExceptionCodeEnum.HEADER_VALID_FAIL.getValue());
    }
}
