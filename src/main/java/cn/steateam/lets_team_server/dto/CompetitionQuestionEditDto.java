package cn.steateam.lets_team_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 竞赛提问DTO
 *
 * @author YaoYi
 * @date 2021/3/18 10:56 下午
 */
@Data
public class CompetitionQuestionEditDto {
    /**
     * 标题
     */
    @NotEmpty(message = "标题不能为空！")
    private String title;

    /**
     * 提问正文
     */
    @NotEmpty(message = "提问正文不能为空！")
    private String content;
}
