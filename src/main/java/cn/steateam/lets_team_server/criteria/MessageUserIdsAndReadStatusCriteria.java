package cn.steateam.lets_team_server.criteria;

import lombok.Builder;
import lombok.Data;

/**
 * 站内信发件/收件人与阅读状态查询约束类
 *
 * @author YaoYi
 * @date 2021/3/10 4:19 下午
 */
@Data
@Builder
public class MessageUserIdsAndReadStatusCriteria {
    private Integer senderUserId;
    private Integer receiverUserId;
    private Integer readStatus;
}
