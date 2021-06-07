package cn.steateam.lets_team_server.constant;

import lombok.Getter;

/**
 * 竞赛状态枚举
 *
 * @author YaoYi
 * @date 2021/3/21 9:41 下午
 */
@Getter
public enum CompetitionStatusEnum {

    /**
     * 已删除
     */
    IS_DELETED(-65535, "已删除"),
    /**
     * 正常显示
     */
    NORMAL(1, "正常显示");

    private final Integer value;
    private final String name;

    CompetitionStatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static CompetitionStatusEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (CompetitionStatusEnum competitionStatusEnum : CompetitionStatusEnum.values()) {
            if (competitionStatusEnum.value.equals(value)) {
                return competitionStatusEnum;
            }
        }
        return null;
    }
}
