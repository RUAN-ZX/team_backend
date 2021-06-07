package cn.steateam.lets_team_server.constant;

import lombok.Getter;

/**
 * 项目招募需求招募状态枚举
 *
 * @author YaoYi
 * @date 2021/2/25 2:30 下午
 */
@Getter
public enum ProjectRecruitingRecruitStatusEnum {
    /**
     * 已终止
     */
    CANCELED(-1, "已终止"),
    /**
     * 已完成
     */
    FINISHED(0, "已完成"),
    /**
     * 进行中
     */
    OPEN(1, "进行中");

    private final Integer value;
    private final String name;

    ProjectRecruitingRecruitStatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static ProjectRecruitingRecruitStatusEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (ProjectRecruitingRecruitStatusEnum projectRecruitingStatusEnum : ProjectRecruitingRecruitStatusEnum.values()) {
            if (projectRecruitingStatusEnum.value.equals(value)) {
                return projectRecruitingStatusEnum;
            }
        }
        return null;
    }
}
