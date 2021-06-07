package cn.steateam.lets_team_server.vo;

import lombok.Data;

import java.util.Date;

/**
 * 项目荣誉VO
 *
 * @author YaoYi
 * @date 2021/3/10 3:59 下午
 */
@Data
public class ProjectHonorVo {
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
}
