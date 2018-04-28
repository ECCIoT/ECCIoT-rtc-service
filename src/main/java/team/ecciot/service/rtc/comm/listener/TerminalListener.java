package team.ecciot.service.rtc.comm.listener;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import team.ecciot.service.rtc.comm.initializer.TerminalChannelInitializer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class TerminalListener {

    //日志记录器
    private static Logger LOGGER = LogManager.getLogger(TerminalListener.class);

    private int port;

    public TerminalListener(int port) {
        this.port = port;
    }

    public void run() {
        EventLoopGroup acceptor = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        bootstrap.group(acceptor, worker);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new TerminalChannelInitializer());
        try {
            Channel channel = bootstrap.bind(port).sync().channel();
            LOGGER.info("TerminalListener strart running in port:" + port);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            acceptor.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}