package cn.steateam.lets_team_server.controller;

import cn.steateam.lets_team_server.exception.SelectException;
import cn.steateam.lets_team_server.service.CompetitionService;
import cn.steateam.lets_team_server.vo.CompetitionDetailedVo;
import cn.steateam.lets_team_server.vo.ResponseBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 竞赛类请求
 *
 * @author YaoYi
 * @date 2021/3/21 11:22 下午
 */
@RestController
@RequestMapping("/competition")
public class CompetitionController {

    @Resource
    private CompetitionService competitionService;

    /**
     * 根据id获取竞赛信息
     *
     * @param id 竞赛id
     */
    @GetMapping("/{id}")
    public ResponseBean<CompetitionDetailedVo> getVoById(@PathVariable Integer id) throws SelectException {
        CompetitionDetailedVo competitionDetailedVo = competitionService.getVoById(id);
        return new ResponseBean<>(competitionDetailedVo);
    }
}
