package cn.steateam.lets_team_server.constant;

import lombok.Getter;

/**
 * 组织等级枚举
 *
 * @author YaoYi
 * @date 2021/2/25 2:30 下午
 */
@Getter
public enum OrganizationLevelEnum {
    /**
     * 个人组织/普通兴趣社团
     */
    PERSONAL(1, "个人组织/普通社团"),
    /**
     * 学生创业公司
     */
    COMPANY(2, "学生创业公司"),
    /**
     * 院级社团/普通实验室
     */
    COLLEGE(3, "院级社团/普通实验室"),
    /**
     * 校级社团/大型实验室
     */
    SCHOOL(4, "校级社团/大型实验室");

    private final Integer value;
    private final String name;

    OrganizationLevelEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static OrganizationLevelEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (OrganizationLevelEnum organizationLevelEnum : OrganizationLevelEnum.values()) {
            if (organizationLevelEnum.value.equals(value)) {
                return organizationLevelEnum;
            }
        }
        return null;
    }
}
