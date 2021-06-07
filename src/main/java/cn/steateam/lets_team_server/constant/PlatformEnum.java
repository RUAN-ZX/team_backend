package cn.steateam.lets_team_server.constant;

import lombok.Getter;

/**
 * 平台编号枚举
 *
 * @author YaoYi
 * @date 2021/2/25 2:30 下午
 */
@Getter
public enum PlatformEnum {
    /**
     * 微信小程序
     */
    PLATFORM_WX_PROGRAM("1", "微信小程序"),
    /**
     * H5网页端
     */
    PLATFORM_H5("2", "H5网页端"),
    /**
     * APP端
     */
    PLATFORM_APP("3", "APP端");

    private final String value;
    private final String name;

    PlatformEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static PlatformEnum getByValue(String value) {
        if (value == null) {
            return null;
        }
        for (PlatformEnum platformEnum : PlatformEnum.values()) {
            if (platformEnum.value.equals(value)) {
                return platformEnum;
            }
        }
        return null;
    }
}
