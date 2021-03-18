package com.kickshaw.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description
 * @date 2021/3/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonData<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 状态码，0表示成功过，1表示处理中，-1表示失败
     */
    private Integer code;
    /**
     * 业务数据
     */
    private T data;
    /**
     * 信息描述
     */
    private String msg;

    public JsonData(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
