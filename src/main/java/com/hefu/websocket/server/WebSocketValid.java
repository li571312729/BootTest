//package com.hefu.websocket.server;
//
//import cn.hutool.core.collection.CollectionUtil;
//import com.alibaba.fastjson.JSON;
//import com.hefu.common.entity.Result;
//import com.hefu.common.utils.SignInfoUtil;
//import com.hefu.common.utils.Utils;
//import com.hefu.websocket.entity.WebSocketMessage;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.websocket.OnClose;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @author lifenxing
// * @date 2020/9/4 13:19
// */
//@Component
//@ServerEndpoint("/webSocket/{noncestr}/{timestamp}/{signature}/{projectId}")
//@Slf4j
//public class WebSocketValid {
//
//
//    private Session session;
//    private Integer projectId;
//    private static ConcurrentHashMap<String, List<WebSocketValid>> webSocketMap = new ConcurrentHashMap<>(16);
//
//    @OnOpen
//    public void onOpen(@PathParam("noncestr") String noncestr,
//                       @PathParam("timestamp") String timestamp,
//                       @PathParam("signature") String signature,
//                       @PathParam("projectId") String projectId, Session session) {
//        this.session = session;
//        this.projectId = Integer.valueOf(projectId);
//        ProjectInformation project = projectService.getById(projectId);
//        if (project == null) {
//            log.info("项目不存在");
//            sessionClose(session);
//            return;
//        }
//        //时间相差五秒，视为无效
//        if ((System.currentTimeMillis() - Long.parseLong(timestamp)) / 1000 > 5) {
//            log.info("时间超时");
//            sessionClose(session);
//            return;
//        }
//        String macAd = project.getMacAd();
//        SignInfoUtil build = SignInfoUtil.builder()
//                .macAd(macAd)
//                .timestamp(timestamp)
//                .signature(signature)
//                .nonce_str(noncestr).build();
//        //判断标识是否有效
//        boolean check = build.check("/webSocket");
//        if (!check) return;
//
//        List<WebSocketValid> webSocketScreens = webSocketMap.get(token);
//        if (CollectionUtil.isEmpty(webSocketScreens)) {
//            webSocketScreens = new ArrayList<>(10);
//        }
//        webSocketScreens.add(this);
//        log.info("【webSocket消息】有新的连接, 总数:{}", clientCount());
//        replyMessage(JSON.toJSONString(Result.success("连接成功")));
//    }
//
//    private void sessionClose(Session session) {
//        try {
//            session.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @OnClose
//    public void onClose() {
//        List<WebSocketValid> webSocketScreens = webSocketMap.get(this.token);
//        try {
//            webSocketScreens.remove(this);
//        } catch (Exception e) {
//        }
//        log.info("[webSocket大屏演示实时刷新-消息]连接断开,总数:{}", clientCount());
//    }
//
//    @OnMessage
//    public void onMessage(String msg) {
//        if (Utils.isNullOrEmpty(msg)) {
//            return;
//        }
//        log.debug("【webSocket大屏演示-收到消息】{}", msg);
//        WebSocketMessage webSocketMessage = JSON.parseObject(msg, WebSocketMessage.class);
//        Integer type = webSocketMessage.getType();
//        if (type != null) {
//            switch (type) {
//                case 0:
//                    // 接收心跳消息
//                    replyMessage(msg);
//                    break;
//                default:
//                    break;
//            }
//        }
//        return;
//    }
//
//    /**
//     * 回复消息
//     *
//     * @param message
//     */
//    public void replyMessage(String message) {
//        log.info("【webSocket消息】回复消息, message={}", message);
//        try {
//            session.getBasicRemote().sendText(message);
//        } catch (IOException e) {
//            log.error("【webSocket消息】回复消息失败, message={}", message);
//            e.printStackTrace();
//        }
//    }
//
//    @SneakyThrows
//    private void sendMessage(String message) {
//        this.session.getBasicRemote().sendText(message);
//    }
//
//    public void sendInfo(String message) {
//        log.debug("【websocket大屏演示实时刷新-消息】广播消息, message={}", message);
//        webSocketMap.forEach((k, v) -> {
//            v.forEach(o -> {
//                try {
//                    o.session.getAsyncRemote().sendText(message);
//                } catch (Exception e) {
//                    log.error("会话已结束");
//                }
//            });
//        });
//    }
//
//    /**
//     * 连接数
//     *
//     * @return
//     */
//    private Integer clientCount() {
//        int count = 0;
//        for (List<WebSocketValid> value : webSocketMap.values()) {
//            if (CollectionUtil.isNotEmpty(value)) {
//                count += value.size();
//            }
//        }
//        return count;
//    }
//}
