package cn.steateam.lets_team_server.exception;

import cn.steateam.lets_team_server.constant.ApiExceptionCodeEnum;

/**
 * 站内信发送异常
 *
 * @author STEA_YY
 */
public class MessageSendingException extends ApiException {
    public MessageSendingException(String message) {
        super(message, ApiExceptionCodeEnum.MESSAGE_SENDING_ERROR.getValue());
    }

    public MessageSendingException() {
        super(ApiExceptionCodeEnum.MESSAGE_SENDING_ERROR.getDesc(), ApiExceptionCodeEnum.MESSAGE_SENDING_ERROR.getValue());
    }

    public MessageSendingException(String message, Integer code) {
        super(message, code);
    }
}
