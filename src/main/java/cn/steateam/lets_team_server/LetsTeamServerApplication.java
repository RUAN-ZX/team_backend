package cn.steateam.lets_team_server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动类
 *
 * @author YaoYi
 */
@SpringBootApplication
@EnableAsync
@MapperScan(basePackages = "cn.steateam.lets_team_server.mapper")
public class LetsTeamServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LetsTeamServerApplication.class, args);
//        buildApiDoc();
    }

//    private static void buildApiDoc() {
//        DocsConfig config = new DocsConfig();
//        // 项目根目录
//        config.setProjectPath("/Users/bytedance/IdeaProjects/lets_team_server");
//        // 项目名称
//        config.setProjectName("lets_team");
//        // 声明该API的版本
//        config.setApiVersion("V1.0");
//        // 生成API 文档所在目录
//        config.setDocsPath("api_doc");
//        // 配置自动生成
//        config.setAutoGenerate(Boolean.TRUE);
//        //导出md格式文档
//        config.addPlugin(new MarkdownDocPlugin());
//        // 执行生成文档
//        Docs.buildHtmlDocs(config);
//    }
}
