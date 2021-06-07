package cn.steateam.lets_team_server.exception;

import cn.steateam.lets_team_server.constant.ApiExceptionCodeEnum;

/**
 * 数据库修改失败的异常类
 *
 * @author STEA_YY
 **/
public class UpdateException extends DaoException {
    public UpdateException() {
        super(ApiExceptionCodeEnum.UPDATE_ERROR.getDesc(), ApiExceptionCodeEnum.UPDATE_ERROR.getValue());
    }

    public UpdateException(String message) {
        super(message, ApiExceptionCodeEnum.UPDATE_ERROR.getValue());
    }
}
