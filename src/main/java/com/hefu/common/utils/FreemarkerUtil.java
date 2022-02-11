package com.hefu.common.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * @author lxq
 */
@Slf4j
public class FreemarkerUtil {

    private static final String PATH = Objects.requireNonNull(FreemarkerUtil.class.getClassLoader().getResource("")).getPath();

    private static Template getTemplate(String templateFileName, String templateDirectory) throws IOException {
        Configuration configuration = new Configuration(Configuration.getVersion());
        configuration.setDefaultEncoding("UTF-8");
        configuration.setDirectoryForTemplateLoading(new File(PATH + templateDirectory));
        return configuration.getTemplate(templateFileName);
    }

    /**
     * @param templateFileName  模版名称
     * @param templateDirectory 模版文件夹路径(类路径下)
     * @param data
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String generateString(String templateFileName, String templateDirectory, Object data)
            throws IOException, TemplateException {
        Template template = getTemplate(templateFileName, templateDirectory);
        try (StringWriter stringWriter = new StringWriter()) {
            template.process(data, stringWriter);
            stringWriter.flush();
            return stringWriter.getBuffer().toString();
        }
    }

    /**
     * @param templateFileName  模版名称
     * @param templateDirectory 模版文件夹路径
     * @param data              填充数据
     * @param fileName          生成文件名
     * @param response          HttpServletResponse
     * @throws IOException
     * @throws TemplateException
     */
    public static void generateFile(String templateFileName,
                                    String templateDirectory,
                                    Map<String, Object> data,
                                    String fileName,
                                    HttpServletResponse response) throws IOException, TemplateException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/msword");
        response.setHeader("Content-disposition", "attachment;filename=" + Utils.toUtf8String(fileName));
        try (
                Writer w = new OutputStreamWriter(new BufferedOutputStream(response.getOutputStream()), StandardCharsets.UTF_8);
        ) {
            Template template = getTemplate(templateFileName, templateDirectory);
            template.process(data, w);
        } catch (cn.hutool.extra.template.TemplateException | IOException e) {
            log.error("导出监测点报告失败：{}", e);
            throw e;
        }
    }

}
