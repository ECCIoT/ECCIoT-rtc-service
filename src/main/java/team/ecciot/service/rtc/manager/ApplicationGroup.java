package team.ecciot.service.rtc.manager;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ApplicationGroup {

    private final String API_KEY;

    private Channel serverChannel;
	private ChannelGroup deviceChannelGroup;
    private ChannelGroup terminalChannelGroup;

    public ApplicationGroup(String api_key){
        API_KEY = api_key;
    }

    public String getApiKey() {
        return API_KEY;
    }
    
    public Channel getServerChannel() {
		return serverChannel;
	}
    
    public void setServerChannel(Channel serverChannel) {
		this.serverChannel = serverChannel;
	}

	public ChannelGroup getDeviceChannelGroup() {
		return deviceChannelGroup==null?(deviceChannelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE)):deviceChannelGroup;
	}

	public ChannelGroup getTerminalChannelGroup() {
		return terminalChannelGroup==null?(terminalChannelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE)):terminalChannelGroup;
	}

}
