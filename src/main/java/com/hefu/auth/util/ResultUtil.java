package com.hefu.auth.util;

import com.hefu.common.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 返回结果工具类
 * @Author
 */
@Slf4j
public class ResultUtil {

    /**
     * 私有化构造器
     */
    private ResultUtil(){}

    /**
     * 使用response输出JSON
     * @Return void
     */
    public static void responseJson(ServletResponse response, Map<String, Object> resultMap){
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JacksonUtils.obj2json(resultMap));
        } catch (Exception e) {
            log.error("【JSON输出异常】"+e);
        }finally{
            if(out!=null){
                out.flush();
                out.close();
            }
        }
    }
    /**
     * 返回成功示例
     * @Return Map<String,Object> 返回数据MAP
     */
    public static Map<String, Object> resultSuccess(Map<String, Object> resultMap){
        resultMap.put("message","操作成功");
        resultMap.put("code", 200);
        return resultMap;
    }
    /**
     * 返回失败示例
     * @Param  resultMap  返回数据MAP
     * @Return Map<String,Object> 返回数据MAP
     */
    public static Map<String, Object> resultError(Map<String, Object> resultMap){
        resultMap.put("message","操作失败");
        resultMap.put("code",500);
        return resultMap;
    }

    /**
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static Map<String, Object> resultCode(Integer code, String msg,Object data){
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("code",code);
        resultMap.put("message",msg);
        resultMap.put("data",data);
        return resultMap;
    }

}