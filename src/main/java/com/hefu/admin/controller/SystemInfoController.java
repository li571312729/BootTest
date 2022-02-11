package com.hefu.admin.controller;

import com.hefu.admin.util.SystemInfoUtil;
import com.hefu.common.entity.Result;
import com.hefu.common.utils.Utils;
import com.hefu.websocket.server.WebSocket;
import io.netty.util.CharsetUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author 系统服务信息模块
 */
@Api(tags = "系统服务信息模块")
@RestController
@RequestMapping("systemInfo")
@Slf4j
public class SystemInfoController {

    @Value("${test.file}")
    private String TestFile;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private WebSocket webSocket;

    @ApiOperation(value = "获取系统服务信息", notes = "获取系统服务信息")
    @GetMapping("/")
    public Result view() {
        return Result.success(new SystemInfoUtil());
    }

    @ApiOperation(value = "国际化测试", notes = "国际化测试")
    @GetMapping("/locale")
    public Result locale(@RequestParam(required = false) String name) {
        String message = messageSource.getMessage("common.name",
                null, LocaleContextHolder.getLocale());
        return Result.success(message);
    }

    @ApiOperation(value = "获取系统服务信息", notes = "获取系统服务信息")
    @GetMapping("/file")
    public Result fileTest() {
        log.info("文件路径为：{}", TestFile);
        Resource resource = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            resource = new ClassPathResource(TestFile);
            is = resource.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String data = null;
            StringBuilder result = new StringBuilder();
            while((data = br.readLine()) != null) {
                result.append(data);
            }
            log.info("读取文件内容为：{}", result.toString());
            return Result.success(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(!Utils.isNull(br)){
                try {
                    br.close();
                } catch (IOException e) {}
            }
            if(!Utils.isNull(isr)){
                try {
                    isr.close();
                } catch (IOException e) {}
            }
            if(!Utils.isNull(is)){
                try {
                    is.close();
                } catch (IOException e) {}
            }
        }
        return Result.error("读取文件内容为空");
    }

    @ApiOperation(value = "获取系统服务信息", notes = "获取系统服务信息")
    @GetMapping("/file1")
    public Result fileTest1() {
        // windows下就是所在盘符下home文件夹
        // linux就是对应文件夹，如果是docker就是docker容器内对应文件夹，或者进行文件挂载
        String TestFile = "/home/TestFile.txt";
        log.info("文件路径为：{}", TestFile);
        FileInputStream fileInputStream = null;
        FileChannel channel = null;
        try {
            File file = new File(TestFile);
            fileInputStream = new FileInputStream(file);
            channel = fileInputStream.getChannel();

            ByteBuffer data = ByteBuffer.allocate(1024);
            int read = channel.read(data);
            StringBuffer msg = new StringBuffer();
            while (read > 0){
                msg.append(new String(data.array()));
                // 这里要重置一下标记位，因为下面读完后，position到达了5,而limit也是5 如果不重置一下position 那么下次读的就是0
                data.clear();
                read = channel.read(data);
            }
            String result = msg.toString().replaceAll("\u0000","");
            log.info("读取文件内容为：{}", result);
            return Result.success(result);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(!Utils.isNull(channel)){
                try {
                    channel.close();
                } catch (IOException e) {}
            }

            if(!Utils.isNull(fileInputStream)){
                try {
                    fileInputStream.close();
                } catch (IOException e) {}
            }
        }
        return Result.error("读取文件内容为空");
    }

    @ApiOperation(value = "获取系统服务信息", notes = "获取系统服务信息")
    @GetMapping("/file2")
    public Result fileTest2(@RequestParam String message) {
        String TestFile = "/home/TestFile.txt";
        log.info("文件路径为：{}", TestFile);
        Resource resource = null;
        FileOutputStream fileOutputStream = null;
        FileChannel channel = null;
        try {
            File f = new File(TestFile);
            fileOutputStream = new FileOutputStream(f);
            channel = fileOutputStream.getChannel();

            if(Utils.isNullOrEmpty(message)){
                message = "我想写点的东西进去";
            }
            channel.write(ByteBuffer.wrap(message.getBytes(CharsetUtil.UTF_8)));
            return Result.success("数据" + message + "写入成功");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(!Utils.isNull(channel)){
                try {
                    channel.close();
                } catch (IOException e) {}
            }
            if(!Utils.isNull(fileOutputStream)){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {}
            }
        }
        return Result.success("数据" + message + "写入失败");
    }

    //TODO 失败
    @ApiOperation(value = "获取系统服务信息", notes = "获取系统服务信息")
    @GetMapping("/file3")
    public Result fileTest3Result(@RequestParam String message) {
        log.info("文件路径为：{}", TestFile);
        Resource resource = null;
        InputStream is = null;
        FileOutputStream fileOutputStream = null;
        FileChannel channel = null;
        try {
            resource = new ClassPathResource(TestFile);
            log.info("resource.getURL().getPath():" + resource.getURL().getPath());
            log.info("resource.getURL().getFile():" + resource.getURL().getFile());
            log.info("resource.getURI().getPath():" + resource.getURI().getPath());
            fileOutputStream = new FileOutputStream(resource.getURL().getPath());
            channel = fileOutputStream.getChannel();

            if(Utils.isNullOrEmpty(message)){
                message = "我想写点的东西进去";
            }
            channel.write(ByteBuffer.wrap(message.getBytes(CharsetUtil.UTF_8)));
            return Result.success("数据" + message + "写入成功");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(!Utils.isNull(channel)){
                try {
                    channel.close();
                } catch (IOException e) {}
            }
            if(!Utils.isNull(fileOutputStream)){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {}
            }
        }
        return Result.success("数据" + message + "写入失败");
    }

}
