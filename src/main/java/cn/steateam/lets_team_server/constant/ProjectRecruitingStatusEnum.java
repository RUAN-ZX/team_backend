package cn.steateam.lets_team_server.constant;

import lombok.Getter;

/**
 * 项目招募需求状态枚举
 *
 * @author YaoYi
 * @date 2021/2/25 2:30 下午
 */
@Getter
public enum ProjectRecruitingStatusEnum {
    /**
     * 已删除
     */
    IS_DELETED(-65535, "已删除"),
    /**
     * 项目被冻结
     */
    PARENT_IS_FROZEN(-3, "项目被冻结"),
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

    ProjectRecruitingStatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static ProjectRecruitingStatusEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (ProjectRecruitingStatusEnum projectRecruitingStatusEnum : ProjectRecruitingStatusEnum.values()) {
            if (projectRecruitingStatusEnum.value.equals(value)) {
                return projectRecruitingStatusEnum;
            }
        }
        return null;
    }
}
