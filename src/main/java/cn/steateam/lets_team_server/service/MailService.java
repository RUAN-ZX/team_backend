package cn.steateam.lets_team_server.service;

import cn.steateam.lets_team_server.config.MailConfig;
import cn.steateam.lets_team_server.constant.RedisConstants;
import cn.steateam.lets_team_server.dto.SimpleMailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 邮件服务类
 *
 * @author STEA_YY
 */
@Service
@Slf4j
public class MailService {
    @Resource
    private RedisService redisService;
    @Resource
    private JavaMailSenderImpl mailSender;
    @Resource
    private MailConfig mailConfig;

    @Async
    public void sendHtmlMail(SimpleMailDto simpleMailDto) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setFrom(mailConfig.getFromAddress());
            mimeMessageHelper.setTo(simpleMailDto.getReceiverAddress());
            mimeMessageHelper.setSubject(simpleMailDto.getSubject());
            mimeMessageHelper.setText(simpleMailDto.getContent(), true);
            mailSender.send(message);
            redisService.set(RedisConstants.PREFIX_MAIL_ADDRESS_COOLING + simpleMailDto.getReceiverAddress(), 1, mailConfig.getCoolingTime());
        } catch (MessagingException ignored) {
        }
    }
}
