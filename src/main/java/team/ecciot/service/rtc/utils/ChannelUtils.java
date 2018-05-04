package team.ecciot.service.rtc.utils;

import io.netty.channel.Channel;

public class ChannelUtils {
	public static void sendMessage(Channel channel,String msg){
		channel.write(msg);
		channel.writeAndFlush("\n");
	}
}
