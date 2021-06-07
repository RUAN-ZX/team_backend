package cn.steateam.lets_team_server.exception;

import cn.steateam.lets_team_server.constant.ApiExceptionCodeEnum;

/**
 * 数据库插入失败的异常类
 *
 * @author STEA_YY
 **/
public class SelectException extends DaoException {
    public SelectException(String message) {
        super(message, ApiExceptionCodeEnum.SELECT_ERROR.getValue());
    }

    public SelectException() {
        super(ApiExceptionCodeEnum.INSERT_ERROR.getDesc(), ApiExceptionCodeEnum.SELECT_ERROR.getValue());
    }

    public SelectException(String message, Integer code) {
        super(message, code);
    }
}
