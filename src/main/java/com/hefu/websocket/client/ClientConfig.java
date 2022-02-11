//package com.hefu.websocket.client;
//
//import lombok.extern.slf4j.Slf4j;
//import org.java_websocket.WebSocket;
//import org.java_websocket.client.WebSocketClient;
//import org.java_websocket.drafts.Draft_6455;
//import org.java_websocket.enums.ReadyState;
//import org.java_websocket.handshake.ServerHandshake;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author Administrator
// */
//@Slf4j
//@Component
//public class ClientConfig {
//
//    private static final String  WEBSOCKETCLIENT = "ws://localhost:80/webSocket/baili";
//
//    /**
//     * 获取WebSocket实例
//     * @return
//     */
//    @Bean
//    public WebSocketClient webSocketClient(){
//        try {
//            WebSocketClient client = new WebSocketClient(new URI(WEBSOCKETCLIENT), new Draft_6455()) {
//                @Override
//                public void onOpen(ServerHandshake serverhandshake) {
//                    log.info("握手成功");
//                }
//
//                @Override
//                public void onClose(int arg0, String arg1, boolean arg2) {
//                    log.info("链接已关闭");
////                    if(StringUtils.isNull(this)){
////                        this.close();
////                    }
//                }
//
//                @Override
//                public void onError(Exception e) {
//                    log.error("发生错误已关闭", e);
//                }
//
//                @Override
//                public void onMessage(String msg) {
//                    log.info("收到消息: " + msg);
//                }
//            };
//
//            client.connect();
//            while(client != null && !client.getReadyState().equals(ReadyState.OPEN)){
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e1) {}
//            }
//            log.info("websocketClien连接已打开");
//            return client;
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//            log.error("新建websocketClient连接失败。");
//        }
//        return null;
//    }
//}
