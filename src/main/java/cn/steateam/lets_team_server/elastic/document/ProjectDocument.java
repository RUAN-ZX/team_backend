package cn.steateam.lets_team_server.elastic.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

/**
 * 项目文档类
 *
 * @author YaoYi
 * @date 2021/4/2 12:19 下午
 */
@Data
@Document(indexName = "projects")
public class ProjectDocument {
    /**
     * 项目id
     */
    @Id
    private Integer id;

    /**
     * 项目名称
     */
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String name;

    /**
     * 项目头像url
     */
    private String avatarUrl;

    /**
     * 项目负责人用户id
     */
    private Integer leaderUserId;

    /**
     * 项目所在机构id
     */
    private Integer organizationId;

    /**
     * 项目标签
     */
    @Field(type = FieldType.Keyword)
    private List<String> tags;

    /**
     * 项目类型
     */
    private Integer type;

    /**
     * 项目介绍
     */
    private String intro;

    /**
     * 项目状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最近更新时间
     */
    private Date updateTime;
}
