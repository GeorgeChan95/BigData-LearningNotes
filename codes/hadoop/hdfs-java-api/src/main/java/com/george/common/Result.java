package com.george.common;

import lombok.Data;

/**
 * <p>
 *  返回结果
 * </p>
 *
 * @author GeorgeChan 2019/3/16 10:13
 * @version 1.0
 * @since jdk1.8
 */
@Data
public class Result {
    private boolean flag;
    private Integer code;
    private String message;
    private Object data;
    private Long count;

    public Result() {
    }

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(boolean flag, Integer code, String message, Object data, Long count) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
        this.count = count;
    }
}
