package cn.steateam.lets_team_server.exception;

import cn.steateam.lets_team_server.constant.ApiExceptionCodeEnum;

/**
 * 验证码校验异常
 *
 * @author STEA_YY
 */
public class CodeVerificationException extends AuthenticationException {
    public CodeVerificationException() {
        super(ApiExceptionCodeEnum.CODE_VERIFICATION_FAIL.getDesc(), ApiExceptionCodeEnum.CODE_VERIFICATION_FAIL.getValue());
    }

    public CodeVerificationException(String message) {
        super(message, ApiExceptionCodeEnum.CODE_VERIFICATION_FAIL.getValue());
    }
}
