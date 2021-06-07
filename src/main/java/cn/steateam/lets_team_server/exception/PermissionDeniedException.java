package cn.steateam.lets_team_server.exception;

import cn.steateam.lets_team_server.constant.ApiExceptionCodeEnum;

/**
 * 无权限异常
 *
 * @author STEA_YY
 **/
public class PermissionDeniedException extends AuthenticationException {
    public PermissionDeniedException() {
        super(ApiExceptionCodeEnum.PERMISSION_DENIED.getDesc(), ApiExceptionCodeEnum.PERMISSION_DENIED.getValue());
    }

    public PermissionDeniedException(String message) {
        super(message, ApiExceptionCodeEnum.PERMISSION_DENIED.getValue());
    }
}
