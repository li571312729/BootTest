package com.hefu.kafka.entity;

import java.util.Date;

public class KafkaMsg {
    private Integer id ;
    private String model ;
    private Integer type ;
    private String typeMsg ;
    private Date createTime ;

    public KafkaMsg(Integer id, String model, Integer type, String typeMsg, Date createTime) {
        this.id = id;
        this.model = model;
        this.type = type;
        this.typeMsg = typeMsg;
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeMsg() {
        return typeMsg;
    }

    public void setTypeMsg(String typeMsg) {
        this.typeMsg = typeMsg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}