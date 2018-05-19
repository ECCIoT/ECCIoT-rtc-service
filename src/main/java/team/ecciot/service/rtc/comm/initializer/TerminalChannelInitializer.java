package team.ecciot.service.rtc.comm.initializer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import team.ecciot.service.rtc.comm.handler.TerminalChannelHandler;

public class TerminalChannelInitializer extends  ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel arg0) throws Exception {
        ChannelPipeline pipeline = arg0.pipeline();
        pipeline.addLast("docode",new StringDecoder());
        pipeline.addLast("encode",new StringEncoder());
        pipeline.addLast("terminal",new TerminalChannelHandler());
    }

}