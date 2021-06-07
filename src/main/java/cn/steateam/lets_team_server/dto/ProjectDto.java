package cn.steateam.lets_team_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 项目DTO
 *
 * @author YaoYi
 * @date 2021/3/10 5:12 下午
 */
@Data
public class ProjectDto {

    /**
     * 项目名称
     */
    @NotEmpty(message = "项目名称不能为空！")
    private String name;

    /**
     * 项目头像url
     */
    @NotEmpty(message = "项目头像url不能为空！")
    private String avatarUrl;

    /**
     * 项目标签
     */
    @NotEmpty(message = "项目标签不能为空！")
    private String tags;

    /**
     * 项目类型
     */
    @NotNull(message = "项目类型不能为空！")
    private Integer type;

    /**
     * 项目介绍
     */
    @NotEmpty(message = "项目介绍不能为空！")
    private String intro;
}
