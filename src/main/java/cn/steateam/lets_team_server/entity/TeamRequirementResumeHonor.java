package cn.steateam.lets_team_server.entity;

import java.util.Date;
import lombok.Data;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/30 12:24 上午
 */
@Data
public class TeamRequirementResumeHonor {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 组队需求id
     */
    private Integer requirementId;

    /**
     * 荣誉名称
     */
    private String name;

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
}