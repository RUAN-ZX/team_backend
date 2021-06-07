package cn.steateam.lets_team_server.entity;

import java.util.Date;
import lombok.Data;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/30 12:25 上午
 */
@Data
public class UserResumeBasic {
    /**
     * 用户id
     */
    private Integer userId;

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}