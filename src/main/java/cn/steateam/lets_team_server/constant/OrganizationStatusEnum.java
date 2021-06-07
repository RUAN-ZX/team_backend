package cn.steateam.lets_team_server.constant;

import lombok.Getter;

/**
 * 组织状态枚举
 *
 * @author YaoYi
 * @date 2021/2/25 2:30 下午
 */
@Getter
public enum OrganizationStatusEnum {
    /**
     * 已删除
     */
    IS_DELETED(-65535, "已删除"),
    /**
     * 被冻结
     */
    IS_FROZEN(-2, "被冻结"),
    /**
     * 审核不通过
     */
    AUDITING_NOT_PASSED(-1, "审核不通过"),
    /**
     * 等待审核
     */
    WAITING_FOR_AUDITING(0, "等待审核"),
    /**
     * 正常访问
     */
    NORMAL(1, "正常访问");

    private final Integer value;
    private final String name;

    OrganizationStatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static OrganizationStatusEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (OrganizationStatusEnum organizationStatusEnum : OrganizationStatusEnum.values()) {
            if (organizationStatusEnum.value.equals(value)) {
                return organizationStatusEnum;
            }
        }
        return null;
    }
}
