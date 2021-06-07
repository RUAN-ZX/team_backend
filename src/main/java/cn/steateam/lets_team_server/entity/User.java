package cn.steateam.lets_team_server.entity;

import java.util.Date;
import lombok.Data;

/**
* ${description}
*
* @author STEA_YY
*/
@Data
public class User {
    /**
    * 主键
    */
    private Integer id;

    /**
    * 散列后的用户密码
    */
    private String password;

    /**
    * 角色id
    */
    private Integer roleId;

    /**
    * 用户注册时间
    */
    private Date registerTime;

    /**
    * 用户最后一次登录时间
    */
    private Date lastLoginTime;
}