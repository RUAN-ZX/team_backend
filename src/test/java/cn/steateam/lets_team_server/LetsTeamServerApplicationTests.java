package cn.steateam.lets_team_server;

import cn.steateam.lets_team_server.config.AliyunConfig;
import cn.steateam.lets_team_server.constant.ProjectStatusEnum;
import cn.steateam.lets_team_server.dto.TeamRequirementDto;
import cn.steateam.lets_team_server.dto.TeamRequirementResumeDto;
import cn.steateam.lets_team_server.elastic.document.ProjectDocument;
import cn.steateam.lets_team_server.elastic.engine.ProjectSearchEngine;
import cn.steateam.lets_team_server.elastic.repository.ProjectRepository;
import cn.steateam.lets_team_server.exception.InsertException;
import cn.steateam.lets_team_server.exception.SelectException;
import cn.steateam.lets_team_server.service.OssService;
import cn.steateam.lets_team_server.service.TeamRequirementService;
import cn.steateam.lets_team_server.vo.OssPolicyVo;
import cn.steateam.lets_team_server.vo.PageVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class LetsTeamServerApplicationTests {

    @Resource
    private AliyunConfig aliyunConfig;
    @Resource
    private OssService ossService;
    @Resource
    private TeamRequirementService teamRequirementService;
    @Resource
    private ProjectRepository projectRepository;
    @Resource
    private ProjectSearchEngine projectSearchEngine;

    @Test
    void testConfig() {
        System.out.println(aliyunConfig);
    }

    @Test
    void testOss() {
        OssPolicyVo ossPolicyVo = ossService.signUpload("/test", aliyunConfig.getOss().getBucket().getPublicBucket().getName());
        System.out.println(ossPolicyVo);
    }

    @Test
    void testSaveTeamRequirement() throws InsertException {
        TeamRequirementDto teamRequirementDto = new TeamRequirementDto();
        ArrayList<Integer> compIds = new ArrayList<>();
        compIds.add(1);
        compIds.add(2);
        compIds.add(3);
        teamRequirementDto.setCompIds(compIds);
        teamRequirementDto.setIntro("测试组队需求");
        teamRequirementDto.setContent("测试介绍");
        teamRequirementDto.setTags("测试");
        TeamRequirementResumeDto teamRequirementResumeDto = new TeamRequirementResumeDto();
        teamRequirementDto.setResume(teamRequirementResumeDto);
        teamRequirementService.save(teamRequirementDto, 7);
    }

    @Test
    void testGetTeamRequirement() throws SelectException {
        System.out.println(teamRequirementService.getVoById(1));
        System.out.println(teamRequirementService.getResumeBasicVoById(1));
        System.out.println(teamRequirementService.getAllResumeHonorVoById(1));
        System.out.println(teamRequirementService.getAllCompetitionById(1));
    }

    @Test
    void testElastic() {
//        ProjectDocument projectDocument = new ProjectDocument();
//        projectDocument.setId(1);
//        projectDocument.setName("\"组队啦\"大学生竞赛服务平台");
//        projectDocument.setIntro("实现竞赛信息的及时交互，管理人员/竞赛组织人员可及时将竞赛的面向对象、比赛时间、比赛流程、赛事QQ群等相关信息公示在平台上。");
//        ArrayList<String> tags = new ArrayList<>();
//        tags.add("竞赛服务");
//        tags.add("微信小程序");
//        tags.add("创业项目");
//        projectDocument.setTags(tags);
//        projectDocument.setCreateTime(new Date());
//        projectDocument.setLeaderUserId(7);
//        projectDocument.setStatus(ProjectStatusEnum.NORMAL.getValue());
//        projectDocument.setType(1);
//        ProjectDocument projectDocument = new ProjectDocument();
//        projectDocument.setId(2);
//        projectDocument.setName("\"城市鹰眼\"交通时空大数据分析挖掘系统");
//        projectDocument.setIntro("交通时空大数据");
//        ArrayList<String> tags = new ArrayList<>();
//        tags.add("大数据");
//        tags.add("交通");
//        projectDocument.setTags(tags);
//        projectDocument.setCreateTime(new Date());
//        projectDocument.setLeaderUserId(7);
//        projectDocument.setStatus(ProjectStatusEnum.NORMAL.getValue());
//        projectDocument.setType(1);
        ProjectDocument projectDocument = new ProjectDocument();
        projectDocument.setId(3);
        projectDocument.setName("\"Aha创客空间\"大学生竞赛资源分享平台");
        projectDocument.setIntro("实现竞赛资源的付费分享");
        ArrayList<String> tags = new ArrayList<>();
        tags.add("竞赛服务");
        tags.add("资源共享");
        tags.add("微信小程序");
        tags.add("创业项目");
        projectDocument.setTags(tags);
        projectDocument.setCreateTime(new Date());
        projectDocument.setLeaderUserId(7);
        projectDocument.setStatus(ProjectStatusEnum.NORMAL.getValue());
        projectDocument.setType(1);
        projectRepository.save(projectDocument);
    }

    @Test
    void testSearch() {
        PageVo<List<ProjectDocument>> pageVo = projectSearchEngine.search("竞赛", 0, 10);
        System.out.println(pageVo.getPageData());
    }

    @Test
    void contextLoads() {
    }
}
