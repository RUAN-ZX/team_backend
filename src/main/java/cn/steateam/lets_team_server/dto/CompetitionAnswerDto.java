package cn.steateam.lets_team_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 竞赛回答DTO
 *
 * @author YaoYi
 * @date 2021/3/18 11:42 下午
 */
@Data
public class CompetitionAnswerDto {

    /**
     * 回答正文
     */
    @NotEmpty(message = "回答正文不能为空！")
    private String content;
}