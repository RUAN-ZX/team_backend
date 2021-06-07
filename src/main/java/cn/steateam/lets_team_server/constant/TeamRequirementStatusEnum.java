package cn.steateam.lets_team_server.constant;

import lombok.Getter;

/**
 * 组队需求状态枚举
 *
 * @author YaoYi
 * @date 2021/2/25 2:30 下午
 */
@Getter
public enum TeamRequirementStatusEnum {
    /**
     * 已删除
     */
    IS_DELETED(-65535, "已删除"),
    /**
     * 被冻结
     */
    IS_FROZEN(-2, "被冻结"),
    /**
     * 正常显示
     */
    NORMAL(1, "正常显示");

    private final Integer value;
    private final String name;

    TeamRequirementStatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static TeamRequirementStatusEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (TeamRequirementStatusEnum teamRequirementStatusEnum : TeamRequirementStatusEnum.values()) {
            if (teamRequirementStatusEnum.value.equals(value)) {
                return teamRequirementStatusEnum;
            }
        }
        return null;
    }
}
