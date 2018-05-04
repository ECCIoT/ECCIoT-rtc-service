package team.ecciot.service.rtc.comm.handler.callback;

import io.netty.channel.Channel;

public class BaseParseCallbackHandler {
	
	private Channel channel;
	
	public BaseParseCallbackHandler(Channel channel){
		this.channel = channel;
	}
	
	public Channel getChannel() {
		return channel;
	}
}
