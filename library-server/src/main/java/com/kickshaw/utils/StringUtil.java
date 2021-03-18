package com.kickshaw.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @date 2021/3/18
 */
public class StringUtil {

    /**
     * 将字符串"s1,s2,.."转换为list<Integer>集合
     * @Param [src]
     * @Return java.util.List<Integer>
     */
    public static List<Integer> string2List(String src) {
        if(src == null || src.isEmpty()) {
            return null;
        }
        List<Integer> dest = new ArrayList<>();
        String[] split = src.split(",");
        for (String s : split) {
            dest.add(Integer.valueOf(s));
        }
        return dest;
    }
}
