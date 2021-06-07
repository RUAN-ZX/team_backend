package cn.steateam.lets_team_server.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页封装VO
 *
 * @author STEA_YY
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVo<T> {
    /**
     * 页码
     */
    private int pageNum;

    /**
     * 分页大小
     */
    private int pageSize;

    /**
     * 内容
     */
    private T pageData;
}
