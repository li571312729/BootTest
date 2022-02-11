package com.hefu.admin.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SpectrumVO implements Serializable {
    private String phase;
    private String name;
    private String rir;
    private String unc;
    private String mu;
    private String xtal;
    private String xtal_a;
}
