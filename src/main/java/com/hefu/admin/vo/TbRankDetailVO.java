package com.hefu.admin.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hefu.common.DoubleSerialize;
import lombok.Data;

import java.io.Serializable;

@Data
public class TbRankDetailVO implements Serializable {
    //项目主键
    private Integer projectId;
    //项目名称
    private String projectName;
    //项目地址
    private String projectAd;
    //项目全景图
    private String figure;
    //监测天数
    private Integer monitorDays;
    //建设单位
    private String consUnit;
    //综合评分
    @JsonSerialize(using = DoubleSerialize.class)
    private Double comprehensiveScore;
    //行为识别评分
    @JsonSerialize(using = DoubleSerialize.class)
    private Double actionScore;
    //基坑评分
    @JsonSerialize(using = DoubleSerialize.class)
    private Double foundScore;
    //塔吊评分
    @JsonSerialize(using = DoubleSerialize.class)
    private Double towerScore;
    //支模评分
    @JsonSerialize(using = DoubleSerialize.class)
    private Double formworkScore;
    //建材评分
    @JsonSerialize(using = DoubleSerialize.class)
    private Double buildScore;
    //综合排名
    private Long rank;
}
