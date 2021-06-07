package cn.steateam.lets_team_server.entity;

import java.util.Date;
import lombok.Data;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/30 12:26 上午
 */
@Data
public class UserResumeHonor {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 荣誉名称
     */
    private String name;

    /**
     * 荣誉类型
     */
    private Integer type;

    /**
     * 获得时间
     */
    private Date acquiringTime;

    /**
     * 证明文件url
     */
    private String supportingDocumentUrl;

    /**
     * 荣誉描述
     */
    private String intro;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}