package cn.steateam.lets_team_server.dto;

import lombok.Data;

/**
 * 邮件DTO
 *
 * @author STEA_YY
 */
@Data
public class SimpleMailDto {
    private String receiverAddress;
    private String subject;
    private String content;
}
