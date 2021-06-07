package cn.steateam.lets_team_server.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 服务器抛出（接口）异常类
 *
 * @author STEA_YY
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class ApiException extends Exception {
    private Integer code;

    public ApiException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public ApiException() {
        super();
    }
}