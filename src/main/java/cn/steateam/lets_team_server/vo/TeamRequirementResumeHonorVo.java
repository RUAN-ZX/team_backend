package cn.steateam.lets_team_server.vo;

import lombok.Data;

import java.util.Date;

/**
 * 组队需求简历荣誉VO
 *
 * @author YaoYi
 * @date 2021/3/20 10:30 下午
 */
@Data
public class TeamRequirementResumeHonorVo {
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
}
