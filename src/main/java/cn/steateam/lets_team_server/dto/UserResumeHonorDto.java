package cn.steateam.lets_team_server.dto;

import lombok.Data;

import java.util.Date;

/**
 * 用户简历荣誉DTO
 *
 * @author STEA_YY
 */
@Data
public class UserResumeHonorDto {

    /**
     * 荣誉名称
     */
    private String name;

    /**
     * 荣誉类型
     */
    private String type;

    /**
     * 获得时间
     */
    private Date acquiringTime;

    /**
     * 证明文件url
     */
    private String supportingDocumentUrl;

    /**
     * 荣誉描述
     */
    private String intro;
}
