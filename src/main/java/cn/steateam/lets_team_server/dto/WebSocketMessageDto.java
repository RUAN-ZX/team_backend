package cn.steateam.lets_team_server.dto;

import cn.steateam.lets_team_server.constant.WebSocketConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * web socket消息传输封装DTO
 *
 * @author STEA_YY
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketMessageDto<T> {
    /**
     * 消息类型
     */
    private Integer type;
    /**
     * 消息数据
     */
    private T data;
    /**
     * 时间戳
     */
    private Date time;

    public WebSocketMessageDto(Integer type, T data) {
        this.setTime(new Date());
        this.setType(type);
        this.setData(data);
    }

    public static WebSocketMessageDto<Object> emptySuccessResponse() {
        return WebSocketMessageDto
                .builder()
                .type(WebSocketConstants.TYPE_OK)
                .time(new Date())
                .build();
    }
}
