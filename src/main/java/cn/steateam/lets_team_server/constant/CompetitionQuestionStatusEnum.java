package cn.steateam.lets_team_server.constant;

import lombok.Getter;

/**
 * 竞赛提问/回答状态枚举类
 *
 * @author YaoYi
 * @date 2021/3/19 12:09 上午
 */
@Getter
public enum CompetitionQuestionStatusEnum {
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
     * 正常显示
     */
    NORMAL(1, "正常显示");

    private final Integer value;
    private final String name;

    CompetitionQuestionStatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static CompetitionQuestionStatusEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (CompetitionQuestionStatusEnum competitionQuestionStatusEnum : CompetitionQuestionStatusEnum.values()) {
            if (competitionQuestionStatusEnum.value.equals(value)) {
                return competitionQuestionStatusEnum;
            }
        }
        return null;
    }
}
