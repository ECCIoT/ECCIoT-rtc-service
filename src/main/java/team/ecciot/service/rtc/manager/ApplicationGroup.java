package team.ecciot.service.rtc.manager;

import java.util.LinkedList;

import io.netty.channel.Channel;
import team.ecciot.service.rtc.comm.channel.BaseChannelBox;
import team.ecciot.service.rtc.comm.channel.DeviceChannelBox;
import team.ecciot.service.rtc.comm.channel.ServerChannelBox;
import team.ecciot.service.rtc.comm.channel.TerminalChannelBox;

public class ApplicationGroup {

    private final String api_key;

    private ServerChannelBox serverChannelBox;
	private LinkedList<DeviceChannelBox> deviceChannelList;
    private LinkedList<TerminalChannelBox> terminalChannelList;

    public ApplicationGroup(String api_key){
        this.api_key = api_key;
    }

    public String getApiKey() {
        return api_key;
    }
    
    public BaseChannelBox getServerChannel() {
		return serverChannelBox;
	}
    
    public ServerChannelBox getServerChannelBox() {
		return serverChannelBox;
	}
    
    public void setServerChannel(ServerChannelBox channelBox) {
    	synchronized (this) {
    		this.serverChannelBox = channelBox;
    	}
	}

	public LinkedList<DeviceChannelBox> getDeviceChannelList() {
		synchronized (this) {
			return deviceChannelList==null?(deviceChannelList=new LinkedList<DeviceChannelBox>()):deviceChannelList;
		}
	}
	
	public LinkedList<TerminalChannelBox> getTerminalChannelList() {
		synchronized (this) {
			return terminalChannelList==null?(terminalChannelList=new LinkedList<TerminalChannelBox>()):terminalChannelList;
		}
	}

	/**
	 * 移除指定Channel对应的DeviceChannelBox
	 * @param channel
	 * @return 被移除的DeviceChannelBox
	 */
	public DeviceChannelBox removeDeviceChannel(Channel channel){
		for(DeviceChannelBox dcb:getDeviceChannelList()){
			if(dcb.getChannel().equals(channel)){
				getDeviceChannelList().remove(dcb);
				return dcb;
			}
		}
		return null;
	}
	
	/**
	 * 移除指定Channel对应的TerminalChannelBox
	 * @param channel
	 * @return 被移除的TerminalChannelBox
	 */
	public TerminalChannelBox removeTerminalChannel(Channel channel){
		for(TerminalChannelBox tcb:getTerminalChannelList()){
			if(tcb.getChannel().equals(channel)){
				getTerminalChannelList().remove(tcb);
				return tcb;
			}
		}
		return null;
	}
}
