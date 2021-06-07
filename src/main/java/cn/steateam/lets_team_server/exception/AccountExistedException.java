package cn.steateam.lets_team_server.exception;

import cn.steateam.lets_team_server.constant.ApiExceptionCodeEnum;

/**
 * 用户已存在异常类
 *
 * @author STEA_YY
 **/
public class AccountExistedException extends InsertException {
    public AccountExistedException(String message) {
        super(message, ApiExceptionCodeEnum.ACCOUNT_EXISTED.getValue());
    }

    public AccountExistedException() {
        super(ApiExceptionCodeEnum.ACCOUNT_EXISTED.getDesc(), ApiExceptionCodeEnum.ACCOUNT_EXISTED.getValue());
    }
}
