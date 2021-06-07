package cn.steateam.lets_team_server.vo;

import lombok.Data;

import java.util.Date;

/**
 * 组队需求简历基础信息VO
 *
 * @author YaoYi
 * @date 2021/3/20 10:28 下午
 */
@Data
public class TeamRequirementResumeBasicVo {
    /**
     * 组队需求id
     */
    private Integer requirementId;

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
}
