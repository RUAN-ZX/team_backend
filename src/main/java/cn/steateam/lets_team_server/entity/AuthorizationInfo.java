package cn.steateam.lets_team_server.entity;

import lombok.Data;

import java.util.Date;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/30 12:15 上午
 */
@Data
public class AuthorizationInfo {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 授权类型
     */
    private Integer type;

    /**
     * 授权凭据
     */
    private String account;

    /**
     * 授权次要凭据
     */
    private String secondaryAccount;

    /**
     * 授权密钥
     */
    private String cipher;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}