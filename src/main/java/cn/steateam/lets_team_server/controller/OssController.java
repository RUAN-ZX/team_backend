package cn.steateam.lets_team_server.controller;

import cn.steateam.lets_team_server.annotation.RequiresLogin;
import cn.steateam.lets_team_server.config.AliyunConfig;
import cn.steateam.lets_team_server.constant.AliyunOssConstants;
import cn.steateam.lets_team_server.service.OssService;
import cn.steateam.lets_team_server.util.ThreadLocalUtil;
import cn.steateam.lets_team_server.vo.OssPolicyVo;
import cn.steateam.lets_team_server.vo.ResponseBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * OSS类请求
 *
 * @author YaoYi
 * @date 2021/2/26 2:01 下午
 */
@RestController
@RequestMapping("/oss")
public class OssController {
    @Resource
    private OssService ossService;
    @Resource
    private AliyunConfig aliyunConfig;

    /**
     * 获取通用公开文件上传签名
     */
    @RequiresLogin
    @GetMapping("/signature/upload/public")
    public ResponseBean<OssPolicyVo> signCommonUploadToPublicBucket() {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        OssPolicyVo ossPolicyVo = ossService.signUpload(AliyunOssConstants.DIR_PUBLIC_COMMON + userId, aliyunConfig.getOss().getBucket().getPublicBucket().getName());
        return new ResponseBean<>(ossPolicyVo);
    }
}
