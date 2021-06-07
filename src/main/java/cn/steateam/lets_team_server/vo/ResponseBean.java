package cn.steateam.lets_team_server.vo;

import lombok.Data;

import java.util.Date;

/**
 * 响应数据封装vo
 *
 * @author STEA_YY
 **/
@Data
public class ResponseBean<T> {
    /**
     * 响应状态码
     */
    private int code;
    /**
     * 响应消息
     */
    private String msg;
    /**
     * 响应数据
     */
    private T data;
    /**
     * 响应时间
     */
    private Date time;

    public ResponseBean(int code, String msg, T data, Date time) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.time = time;
    }

    public ResponseBean(int code, String msg, T data) {
        this(code, msg, data, new Date());
    }

    public ResponseBean(T data) {
        this(200, "succ", data);
    }

    public static ResponseBean<Object> emptySuccessResponse() {
        return new ResponseBean<>(200, "succ", null);
    }
}
