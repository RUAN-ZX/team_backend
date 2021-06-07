package cn.steateam.lets_team_server.exception;

import cn.steateam.lets_team_server.exception.ApiException;

/**
 * 数据库操作异常类
 *
 * @author STEA_YY
 **/
public abstract class DaoException extends ApiException {
    public DaoException() {
    }

    public DaoException(String message, Integer code) {
        super(message, code);
    }
}
