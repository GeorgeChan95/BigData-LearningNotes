package com.george.common;

import lombok.Data;

import java.util.List;

/**
 * <p>
 *  返回分页信息
 * </p>
 *
 * @author GeorgeChan 2019/3/16 10:50
 * @version 1.0
 * @since jdk1.8
 */
@Data
public class PageResult<T> {
    /**
     * 数量
     */
    private long total;
    /**
     * 行项目集合
     */
    private List<T> rows;

    public PageResult() {
    }

    public PageResult(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }
}
