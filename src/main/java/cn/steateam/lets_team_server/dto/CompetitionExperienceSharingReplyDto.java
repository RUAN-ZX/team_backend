package cn.steateam.lets_team_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 竞赛经验分享回复DTO
 *
 * @author YaoYi
 * @date 2021/3/23 11:46 下午
 */
@Data
public class CompetitionExperienceSharingReplyDto {

    /**
     * 回复类型(直接回复or间接)
     */
    @NotNull(message = "回复类型不能为空！")
    private Integer type;

    /**
     * 目标回复id
     */
    private Integer replyId;

    /**
     * 目标用户id
     */
    private Integer replyUserId;

    /**
     * 回复正文
     */
    @NotEmpty(message = "回复正文不能为空！")
    private String content;
}
