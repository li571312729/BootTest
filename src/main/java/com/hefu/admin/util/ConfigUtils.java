package com.hefu.admin.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 解析配置文件

 */
public class ConfigUtils {

    /**
     * 解析list配置文件
     * @param param
     * @return
     */
    public static List<String> parseParam(String param){

        List<String> list = new ArrayList<>();

        if(StringUtils.isBlank(param)){
            return list;
        }

        String[] split = param.split(",");
        list = Arrays.asList(split);
        return list;
    }
}
