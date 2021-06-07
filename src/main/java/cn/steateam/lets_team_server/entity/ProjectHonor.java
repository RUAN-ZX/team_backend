package cn.steateam.lets_team_server.entity;

import java.util.Date;
import lombok.Data;

/**
 * ${description}
 *
 * @author YaoYi
 * @date 2021/3/30 12:18 上午
 */
@Data
public class ProjectHonor {
    /**
     * 项目荣誉id
     */
    private Integer id;

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 荣誉名称
     */
    private String name;

    /**
     * 荣誉类型
     */
    private Integer type;

    /**
     * 取得时间
     */
    private Date acquiringTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}