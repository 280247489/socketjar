package com.memory.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Auther: cui.Memory
 * @Date: 2018/12/14 0014 13:50
 * @Description: https://www.cnblogs.com/applerosa/p/7141684.html
 */
public class NettyServer  {
    public void startup(Integer port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        System.out.println("准备运行端口：" + port);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b = b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer() {// (4)
                protected void initChannel(Channel channel) throws Exception {
                    channel.pipeline().addLast(new DiscardServerHandler());// demo1.discard
                    // ch.pipeline().addLast(new
                    // ResponseServerHandler());//demo2.echo
                    // ch.pipeline().addLast(new
                    // TimeServerHandler());//demo3.time
                }
            })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }
}
