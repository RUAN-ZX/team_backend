package cn.steateam.lets_team_server.vo;

import cn.steateam.lets_team_server.entity.UserInfo;
import lombok.Data;

/**
 * 会话VO
 *
 * @author STEA_YY
 */
@Data
public class MessageSessionVo {
    /**
     * 对方用户信息
     */
    private UserInfo targetUser;
    /**
     * 未读数量
     */
    private Integer unreadCount;
    /**
     * 最后一条消息
     */
    private MessageVo latestMessage;
}