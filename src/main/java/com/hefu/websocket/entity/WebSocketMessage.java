package com.hefu.websocket.entity;

import lombok.Data;


/**
 * @author Administrator
 */
@Data
public class WebSocketMessage {

    /**
     * 0 : 心跳
     * 1 : 请求边缘性能数据
     * 2 : 接收边缘性能数据
     * 3 ：关闭边缘性能数据
     * 4 : 请求摄像头状态信息
     * 5 : 接收摄像头状态信息
     * 6 : 关闭摄像头状态信息
     * <p>
     * --------------------
     * 大屏演示
     * 20 : 行为识别，数据更新
     * 21 : 传感器数据更新
     */
    private Integer type;

    /**
     * 数据
     */
    private String data;

    public WebSocketMessage(Integer type, String data) {
        this.type = type;
        this.data = data;
    }

    public WebSocketMessage(String data) {
        this.data = data;
    }
}
