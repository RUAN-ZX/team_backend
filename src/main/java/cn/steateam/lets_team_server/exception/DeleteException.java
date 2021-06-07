package cn.steateam.lets_team_server.exception;

import cn.steateam.lets_team_server.constant.ApiExceptionCodeEnum;

/**
 * 数据库删除失败的异常类
 *
 * @author STEA_YY
 **/
public class DeleteException extends DaoException {
    public DeleteException(String message) {
        super(message, ApiExceptionCodeEnum.DELETE_ERROR.getValue());
    }

    public DeleteException() {
        super(ApiExceptionCodeEnum.DELETE_ERROR.getDesc(), ApiExceptionCodeEnum.DELETE_ERROR.getValue());
    }
}
