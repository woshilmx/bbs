package com.bbs.project.commen;

import lombok.Data;

/**
 * 分页请求
 *
 * @author lmx
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private long current = 1;

    /**
     * 页面大小
     */
    private long pageSize = 10;


}
