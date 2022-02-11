package com.hefu.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: qingcheng
 * @author: XIONG CHUAN
 * @create: 2019-04-28 19:42
 * @description: 客户端
 **/

@Slf4j
public class NettyClient{

    static final String HOST = "127.0.0.1";
    static final int PORT = 9002;

    private String content;

    public NettyClient(String content) {
        this.content = content;
    }

    /**
     *  下面是不加线程的
     */
    public static void main(String[] args) throws Exception {
        sendMessage("hhh你好？");
    }

    public static void sendMessage(String content) throws InterruptedException {
        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast("decoder", new StringDecoder());
                            p.addLast("encoder", new StringEncoder());
                            p.addLast(new NettyClientHandler());
                        }
                    });

            ChannelFuture future = b.connect(HOST, PORT).sync();
            future.channel().write(content + "\n");
            future.channel().flush();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

}