package cn.steateam.lets_team_server.constant;

import lombok.Getter;

/**
 * 竞赛经验分享状态枚举类
 *
 * @author YaoYi
 * @date 2021/3/19 12:09 上午
 */
@Getter
public enum CompetitionExperienceSharingStatusEnum {
    /**
     * 已删除
     */
    IS_DELETED(-65535, "已删除"),
    /**
     * 父回复被冻结
     */
    PARENT_REPLY_IS_FROZEN(-4, "父回复被冻结"),
    /**
     * 分享被冻结
     */
    PARENT_IS_FROZEN(-3, "分享被冻结"),
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

    CompetitionExperienceSharingStatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static CompetitionExperienceSharingStatusEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (CompetitionExperienceSharingStatusEnum competitionExperienceSharingStatusEnum : CompetitionExperienceSharingStatusEnum.values()) {
            if (competitionExperienceSharingStatusEnum.value.equals(value)) {
                return competitionExperienceSharingStatusEnum;
            }
        }
        return null;
    }
}
