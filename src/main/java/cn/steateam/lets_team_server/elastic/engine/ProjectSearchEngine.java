package cn.steateam.lets_team_server.elastic.engine;

import cn.steateam.lets_team_server.elastic.document.ProjectDocument;
import cn.steateam.lets_team_server.vo.PageVo;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目搜索引擎
 *
 * @author YaoYi
 * @date 2021/4/6 9:44 下午
 */
@Service
public class ProjectSearchEngine {
    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public PageVo<List<ProjectDocument>> search(String keywords, int page, int pageSize) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder
                .withPageable(PageRequest.of(page,pageSize))
                .withQuery(QueryBuilders.multiMatchQuery(keywords, "name", "intro", "tags"));
        SearchHits<ProjectDocument> searchHits = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), ProjectDocument.class);
        List<ProjectDocument> projectDocuments = searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
        return new PageVo<>(page,projectDocuments.size(),projectDocuments);
    }
}
