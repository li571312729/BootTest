package com.hefu.admin.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WeatherVo implements Serializable {
    private Object yesterday;
    private String city;
    private Object[] forecast;
    private String ganmao;
    private String wendu;
}