package cn.steateam.lets_team_server.entity;

import java.util.Date;
import lombok.Data;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/30 12:25 上午
 */
@Data
public class UserInfo {
    /**
     * 主键
     */
    private Integer userId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像url
     */
    private String avatarUrl;

    /**
     * 用户标签
     */
    private String tags;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 用户经验值
     */
    private Integer experience;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}