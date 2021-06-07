package cn.steateam.lets_team_server.dto;

import lombok.Data;

import java.util.Date;

/**
 * 用户简历基础信息DTO
 *
 * @author STEA_YY
 */
@Data
public class UserResumeBasicDto {

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
