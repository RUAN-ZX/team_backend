package cn.steateam.lets_team_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 竞赛经验分享帖编辑DTO
 *
 * @author YaoYi
 * @date 2021/3/22 11:51 下午
 */
@Data
public class CompetitionExperienceSharingEditDto {


    /**
     * 标签
     */
    @NotEmpty(message = "标签不能为空！")
    private String tags;

    /**
     * 标题
     */
    @NotEmpty(message = "标题不能为空！")
    private String title;

    /**
     * 正文
     */
    @NotEmpty(message = "正文不能为空！")
    private String content;
}
