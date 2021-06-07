package cn.steateam.lets_team_server.elastic.repository;

import cn.steateam.lets_team_server.elastic.document.ProjectDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 项目仓库类
 *
 * @author YaoYi
 * @date 2021/4/6 11:28 下午
 */
public interface ProjectRepository extends ElasticsearchRepository<ProjectDocument,Integer> {
}
