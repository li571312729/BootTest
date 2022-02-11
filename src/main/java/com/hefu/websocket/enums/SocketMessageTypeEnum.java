package com.hefu.websocket.enums;

/**
 * 0 : 心跳
 * 1 : 请求边缘性能数据
 * 2 : 接收边缘性能数据
 * 3 ：关闭边缘性能数据
 * 4 : 请求摄像头状态信息
 * 5 : 接收摄像头状态信息
 * 6 : 关闭摄像头状态信息
 * --------------------
 * 大屏演示
 * 20 : 行为识别，数据更新
 * 21 : 传感器数据更新
 *
 * @author lxq
 * @date 2020/10/9 14:30
 */
public enum SocketMessageTypeEnum {
    HEART(0, "心跳消息"),
    PERFORMANCEQ(1, "请求边缘性能数据"),
    PERFORMANCER(2, "接收边缘性能数据"),
    PERFORMANCEE(3, "关闭边缘性能数据"),
    CAMERAQ(4, "请求摄像头状态信息"),
    CAMERAR(5, "接收摄像头状态信息"),
    CAMERAE(6, "关闭摄像头状态信息"),
    ACCIDENT(20, "行为识别数据更新"),
    SENSOR(21, "传感器数据更新");

    private int value;
    private String desc;

    SocketMessageTypeEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static SocketMessageTypeEnum valueOf(int value) {
        for (SocketMessageTypeEnum socketMessageTypeEnum : values()) {
            if (socketMessageTypeEnum.value == value) {
                return socketMessageTypeEnum;
            }
        }
        throw new IllegalArgumentException("找不到匹配的枚举值 [" + value + "]");
    }

    public int getValue() {
        return value;
    }


    public String getDesc() {
        return desc;
    }
}
