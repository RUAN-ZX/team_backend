package cn.steateam.lets_team_server.constant;

import lombok.Getter;

/**
 * 组织审核状态枚举
 *
 * @author YaoYi
 * @date 2021/3/14 3:51 下午
 */
@Getter
public enum OrganizationAuditingStatusEnum {
    /**
     * 审核未通过
     */
    AUDITING_NOT_PASSED(-1, "审核未通过"),
    /**
     * 待审核
     */
    WAITING_FOR_AUDITING(0, "待审核"),
    /**
     * 审核中
     */
    AUDITING(1, "审核中"),
    /**
     * 审核通过
     */
    AUDITING_PASSED(2, "审核通过");


    private final Integer value;
    private final String name;

    OrganizationAuditingStatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static OrganizationAuditingStatusEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (OrganizationAuditingStatusEnum organizationAuditingStatusEnum : OrganizationAuditingStatusEnum.values()) {
            if (organizationAuditingStatusEnum.value.equals(value)) {
                return organizationAuditingStatusEnum;
            }
        }
        return null;
    }
}
