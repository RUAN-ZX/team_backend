package cn.steateam.lets_team_server.constant;

import lombok.Getter;

/**
 * 组织角色枚举
 *
 * @author YaoYi
 * @date 2021/2/25 2:30 下午
 */
@Getter
public enum OrganizationRoleEnum {
    /**
     * 普通成员
     */
    NORMAL(1, "普通成员"),
    /**
     * 审核员
     */
    AUDITOR(2, "审核员"),
    /**
     * 编辑者
     */
    EDITOR(3, "编辑者"),
    /**
     * 管理员
     */
    ADMIN(4, "管理员");

    private final Integer value;
    private final String name;

    OrganizationRoleEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static OrganizationRoleEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (OrganizationRoleEnum organizationRoleEnum : OrganizationRoleEnum.values()) {
            if (organizationRoleEnum.value.equals(value)) {
                return organizationRoleEnum;
            }
        }
        return null;
    }
}
