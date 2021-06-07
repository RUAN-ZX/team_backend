package cn.steateam.lets_team_server.service;

import cn.steateam.lets_team_server.config.AliyunConfig;
import cn.steateam.lets_team_server.util.EncryptUtil;
import cn.steateam.lets_team_server.vo.OssPolicyVo;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URL;
import java.util.Date;

/**
 * oss服务类
 *
 * @author STEA_YY
 **/
@Service
public class OssService {
    @Resource
    private OSS oss;
    @Resource
    private AliyunConfig aliyunConfig;

    public OssPolicyVo signUpload(String dir, String bucketName) {
        String host = "http://" + bucketName + "." + aliyunConfig.getOss().getEndpoint();
        Date expiration = new Date(System.currentTimeMillis() + aliyunConfig.getOss().getSignature().getExpireTime() * 1000L);
        PolicyConditions policyConditions = new PolicyConditions();
        policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
        policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, aliyunConfig.getOss().getObject().getMaxSize());
        String policyStr = oss.generatePostPolicy(expiration, policyConditions);
        String policyBase64 = EncryptUtil.toBase64String(policyStr.getBytes());
        String signature = oss.calculatePostSignature(policyStr);
        OssPolicyVo ossPolicyVo = new OssPolicyVo();
        ossPolicyVo.setHost(host);
        ossPolicyVo.setAccessId(aliyunConfig.getOss().getAccessKeyId());
        ossPolicyVo.setPolicy(policyBase64);
        ossPolicyVo.setDir(dir);
        ossPolicyVo.setSignature(signature);
        ossPolicyVo.setExpire(expiration.getTime());
        return ossPolicyVo;
    }

    public URL generateDownloadUrl(String filename,String bucketName) {
        Date expiration = new Date(System.currentTimeMillis() + aliyunConfig.getOss().getSignature().getExpireTime() * 1000L);
        return oss.generatePresignedUrl(bucketName, filename, expiration);
    }
}