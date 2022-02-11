package com.hefu.websocket.server;


import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.hefu.common.utils.Utils;
import com.hefu.config.redis.RedisCache;
import com.hefu.websocket.entity.WebSocketMessage;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 大屏演示webSocket
 *
 * @author lxq
 */
@Component
@ServerEndpoint("/webSocket/{test}")
@Slf4j
public class WebSocket {

    // 原子类型标识连接数量
    private static AtomicInteger count = new AtomicInteger(0);
    private static volatile ConcurrentHashMap<String, List<WebSocket>> websocketMap = new ConcurrentHashMap<>(16);
    private static RedisCache redisCache;
    private String test;
    private Session session;

    @Autowired
    private void setRedisCache(RedisCache redisCache) {
        WebSocket.redisCache = redisCache;
    }

    @OnOpen
    public void onOpen(@PathParam("test") String test, Session session) {
        this.test = test;
        this.session = session;

        List<WebSocket> webSocketScreens = websocketMap.get(test);
        if (CollectionUtil.isEmpty(webSocketScreens)) {
            synchronized (WebSocket.class) {
                webSocketScreens = websocketMap.get(test);
                if (CollectionUtil.isEmpty(webSocketScreens)) {
                    webSocketScreens = new ArrayList<>(10);
                    websocketMap.put(test, webSocketScreens);
                }
            }
        }
        webSocketScreens.add(this);
        log.info("[webSocket大屏演示实时刷新-消息]有新的连接,总数:{}", count.incrementAndGet());
    }

    @OnClose
    public void onClose() {
        List<WebSocket> webSocketScreens = websocketMap.get(this.test);
        webSocketScreens.remove(this);
        log.info("[webSocket大屏演示实时刷新-消息]连接断开,总数:{}", count.decrementAndGet());
    }

    @OnMessage
    public void onMsg(String msg) {
        if (Utils.isNullOrEmpty(msg)) {
            return;
        }
        log.debug("【webSocket大屏演示-收到消息】{}", msg);
        WebSocketMessage webSocketMessage = JSON.parseObject(msg, WebSocketMessage.class);
        Integer type = webSocketMessage.getType();
        if (type != null) {
            switch (type) {
                case 0:
                    // 接收心跳消息
                    replyMessage(msg);
                    break;
                case 1:
                    replyMessage(msg);
                    break;
                default:
                    break;
            }
        }
        return;
    }

    @SneakyThrows
    private void sendMessage(String message) {
        this.session.getBasicRemote().sendText(message);
    }

    public void sendInfo(String message) {
        log.debug("【websocket大屏演示实时刷新-消息】广播消息, message={}", message);
        websocketMap.forEach((k, v) -> {
            v.forEach(o -> {
                try {
                    o.session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    log.error("会话已结束");
                }
            });
        });
    }

    /**
     * 解析token信息
     *
     * @param tokenKey
     * @return
     */
    public boolean validate(String tokenKey) {
        String token = redisCache.getCacheObject(tokenKey);
        if (null != token) {
            Claims claims = null;
            try {
                // 解析JWT
                claims = Jwts.parser()
                        .setSigningKey("hefu")
                        .parseClaimsJws(token)
                        .getBody();
                Long userId = Long.valueOf(claims.getId());
                if (null == userId) {
                    return false;
                }
            } catch (ExpiredJwtException e) {
                return false;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * 回复消息
     *
     * @param message
     */
    public void replyMessage(String message) {
        log.debug("【websocket大屏演示实时刷新-消息】回复消息, message={}", message);
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("【websocket大屏演示实时刷新-消息】回复消息失败, message={}", message);
            e.printStackTrace();
        }
    }

}