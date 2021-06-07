package cn.steateam.lets_team_server.dto;

import lombok.Data;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 组队需求简历DTO
 *
 * @author YaoYi
 * @date 2021/3/20 9:48 下午
 */
@Data
public class TeamRequirementResumeDto {
    /**
     * 用户真实姓名
     */
    private String trueName;

    /**
     * 用户学校id
     */
    private Integer schoolId;

    /**
     * 用户所学专业
     */
    private String major;

    /**
     * 用户入学时间
     */
    private Date admissionDate;

    /**
     * 用户标签
     */
    private String tags;

    /**
     * 用户自我介绍
     */
    private String intro;

    /**
     * 用户个人主页
     */
    private String homepageUrl;

    /**
     * 个人荣誉列表
     */
    @Valid
    private List<TeamRequirementResumeHonorDto> honors;
}
