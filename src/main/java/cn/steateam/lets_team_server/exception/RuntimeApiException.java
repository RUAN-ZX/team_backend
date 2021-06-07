package cn.steateam.lets_team_server.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 运行期间API异常类
 *
 * @author STEA_YY
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class RuntimeApiException extends RuntimeException {
    private Integer code;

    public RuntimeApiException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public RuntimeApiException() {
        super();
    }
}
