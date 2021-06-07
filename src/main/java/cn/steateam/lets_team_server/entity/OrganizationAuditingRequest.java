package cn.steateam.lets_team_server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * ${description}
 * @author YaoYi
 * @date 2021/3/14 12:18 上午
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationAuditingRequest {
    /**
    * 组织审核请求id
    */
    private Integer id;

    /**
    * 组织id
    */
    private Integer organizationId;

    /**
    * 提请审核用户id
    */
    private Integer userId;

    /**
    * 组织级别
    */
    private Integer level;

    /**
    * 审核材料url
    */
    private String fileUrl;

    /**
    * 审核状态
    */
    private Integer status;

    /**
    * 请求创建时间
    */
    private Date createTime;

    /**
    * 审核人用户id
    */
    private Integer auditorUserId;

    /**
    * 审核时间
    */
    private Date auditingTime;
}