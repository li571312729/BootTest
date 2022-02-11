package com.hefu.admin.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVo implements Serializable {
    private WeatherVo data;
    private Integer status;
    private String desc;
}