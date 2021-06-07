package cn.steateam.lets_team_server.constant;

import lombok.Getter;

/**
 * 竞赛经验分享回复类型枚举
 *
 * @author YaoYi
 * @date 2021/3/23 11:51 下午
 */
@Getter
public enum CompetitionExperienceSharingReplyTypeEnum {
    /**
     * 直接回复
     */
    DIRECT_REPLY(0,"直接回复"),
    /**
     * 间接回复
     */
    INDIRECT_REPLY(1,"间接回复");

    private final Integer value;
    private final String name;

    CompetitionExperienceSharingReplyTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static CompetitionExperienceSharingReplyTypeEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (CompetitionExperienceSharingReplyTypeEnum competitionExperienceSharingReplyTypeEnum : CompetitionExperienceSharingReplyTypeEnum.values()) {
            if (competitionExperienceSharingReplyTypeEnum.value.equals(value)) {
                return competitionExperienceSharingReplyTypeEnum;
            }
        }
        return null;
    }
}
