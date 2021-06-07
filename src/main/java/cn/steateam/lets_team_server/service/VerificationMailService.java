package cn.steateam.lets_team_server.service;

import cn.steateam.lets_team_server.config.MailConfig;
import cn.steateam.lets_team_server.constant.RedisConstants;
import cn.steateam.lets_team_server.dto.SimpleMailDto;
import cn.steateam.lets_team_server.exception.MailSendingException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 邮件验证码服务类
 *
 * @author STEA_YY
 */
@Service
public class VerificationMailService {
    @Resource
    private MailConfig mailConfig;
    @Resource
    private Configuration freeMarkerConfiguration;
    @Resource
    private RedisService redisService;
    @Resource
    private MailService mailService;

    public void sendVerificationMail(String receiverAddress) throws Exception {
        if (redisService.get(RedisConstants.PREFIX_MAIL_ADDRESS_COOLING + receiverAddress) != null) {
            throw new MailSendingException("邮件发送频率过高！");
        }
        String code = RandomStringUtils.randomNumeric(mailConfig.getCodeLength());
        Template template = freeMarkerConfiguration.getTemplate("verification-mail.ftlh");
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("code", code);
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, paramsMap);
        SimpleMailDto simpleMailDto = new SimpleMailDto();
        simpleMailDto.setReceiverAddress(receiverAddress);
        simpleMailDto.setSubject(mailConfig.getSubject());
        simpleMailDto.setContent(content);
        mailService.sendHtmlMail(simpleMailDto);
        redisService.set(RedisConstants.PREFIX_MAIL_VERIFICATION_CODE + receiverAddress, code, mailConfig.getExpireTime());
    }

    public boolean verify(String receiverAddress, String code) {
        String currentCode = (String) redisService.get(RedisConstants.PREFIX_MAIL_VERIFICATION_CODE + receiverAddress);
        if (currentCode == null) {
            return false;
        }
        if (currentCode.equals(code)) {
            redisService.del(RedisConstants.PREFIX_MAIL_VERIFICATION_CODE + receiverAddress);
            return true;
        } else {
            return false;
        }
    }
}
