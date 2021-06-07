package cn.steateam.lets_team_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 竞赛DTO
 *
 * @author YaoYi
 * @date 2021/3/21 9:01 下午
 */
@Data
public class CompetitionDto {
    /**
     * 竞赛名称
     */
    @NotEmpty(message = "竞赛名称不能为空！")
    private String name;

    /**
     * 竞赛头像url
     */
    private String avatarUrl;

    /**
     * 竞赛介绍文案
     */
    @NotEmpty(message = "竞赛介绍文案不能为空！")
    private String intro;

    /**
     * 竞赛标签
     */
    @NotEmpty(message = "竞赛标签不能为空！")
    private String tags;
}
