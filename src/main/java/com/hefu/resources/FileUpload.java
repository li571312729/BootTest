package com.hefu.resources;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
@PropertySource("classpath:application.yml")
@Data
public class FileUpload {
    private String imageLocaltion;
    private String imgSqlLocation;
    private String excelIncTemplateUrl;
    private String excelInstrTemplateUrl;
    private String excelDownTemplateUrl;
    private String pdfLocaltion;
    private String excelStaff;
    private String appLocation;
}
