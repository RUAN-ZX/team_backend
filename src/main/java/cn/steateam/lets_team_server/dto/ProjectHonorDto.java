package cn.steateam.lets_team_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 项目荣誉DTO
 *
 * @author YaoYi
 * @date 2021/3/10 6:55 下午
 */
@Data
public class ProjectHonorDto {

    /**
     * 荣誉名称
     */
    @NotEmpty(message = "荣誉名称不能为空！")
    private String name;

    /**
     * 荣誉类型
     */
    @NotNull(message = "荣誉类型不能为空！")
    private Integer type;

    /**
     * 取得时间
     */
    @NotNull(message = "荣誉取得时间不能为空！")
    private Date acquiringTime;
}
