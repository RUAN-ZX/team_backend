package cn.steateam.lets_team_server.constant;

import lombok.Getter;

/**
 * 项目权限等级枚举
 *
 * @author YaoYi
 * @date 2021/2/25 2:30 下午
 */
@Getter
public enum ProjectPermissionLevelEnum {
    /**
     * 普通
     */
    NORMAL(1, "普通"),
    /**
     * 成员管理
     */
    MEMBER_EDIT(2, "成员管理"),
    /**
     * 编辑
     */
    EDIT(3, "项目编辑"),
    /**
     * 管理权限
     */
    MANAGEMENT(4, "完全管理权限");

    private final Integer value;
    private final String name;

    ProjectPermissionLevelEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static ProjectPermissionLevelEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (ProjectPermissionLevelEnum projectPermissionLevelEnum : ProjectPermissionLevelEnum.values()) {
            if (projectPermissionLevelEnum.value.equals(value)) {
                return projectPermissionLevelEnum;
            }
        }
        return null;
    }
}
