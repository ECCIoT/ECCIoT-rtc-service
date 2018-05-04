package team.ecciot.service.rtc.comm.channel;

import io.netty.channel.Channel;
import team.ecciot.service.rtc.manager.ApplicationGroup;

public class BaseChannelBox {
	
	private final Channel channel;
	private ApplicationGroup applicationGroup;
	
	public BaseChannelBox(Channel channel) {
		this.channel = channel;
	}
	
	public Channel getChannel() {
		return channel;
	}
	
	public ApplicationGroup getApplicationGroup() {
		return applicationGroup;
	}
	
	public void setApplicationGroup(ApplicationGroup applicationGroup) {
		this.applicationGroup = applicationGroup;
	}
}
