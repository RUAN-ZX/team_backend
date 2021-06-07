package cn.steateam.lets_team_server.exception;

import cn.steateam.lets_team_server.constant.ApiExceptionCodeEnum;

/**
 * 数据库插入失败的异常类
 *
 * @author STEA_YY
 **/
public class InsertException extends DaoException {
    public InsertException(String message) {
        super(message, ApiExceptionCodeEnum.INSERT_ERROR.getValue());
    }

    public InsertException() {
        super(ApiExceptionCodeEnum.INSERT_ERROR.getDesc(), ApiExceptionCodeEnum.INSERT_ERROR.getValue());
    }

    public InsertException(String message, Integer code) {
        super(message, code);
    }
}
