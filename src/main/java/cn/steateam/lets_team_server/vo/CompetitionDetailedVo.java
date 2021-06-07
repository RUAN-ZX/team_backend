package cn.steateam.lets_team_server.vo;

import lombok.Data;

import java.util.Date;

/**
 * 竞赛详细信息VO
 *
 * @author YaoYi
 * @date 2021/3/21 11:00 下午
 */
@Data
public class CompetitionDetailedVo {

    /**
     * 竞赛主键
     */
    private Integer id;

    /**
     * 竞赛名称
     */
    private String name;

    /**
     * 竞赛头像url
     */
    private String avatarUrl;

    /**
     * 竞赛介绍文案
     */
    private String intro;

    /**
     * 竞赛标签
     */
    private String tags;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
