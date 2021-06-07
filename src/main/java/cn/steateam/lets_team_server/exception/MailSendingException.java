package cn.steateam.lets_team_server.exception;

import cn.steateam.lets_team_server.constant.ApiExceptionCodeEnum;

/**
 * 邮件发送异常类
 *
 * @author STEA_YY
 */
public class MailSendingException extends ApiException {
    public MailSendingException(String message) {
        super(message, ApiExceptionCodeEnum.MAIL_SENDING_ERROR.getValue());
    }

    public MailSendingException() {
        super(ApiExceptionCodeEnum.MAIL_SENDING_ERROR.getDesc(), ApiExceptionCodeEnum.MAIL_SENDING_ERROR.getValue());
    }
}
