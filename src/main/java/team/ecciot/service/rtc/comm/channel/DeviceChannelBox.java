package team.ecciot.service.rtc.comm.channel;

import io.netty.channel.Channel;
import team.ecciot.lib.args.model.impl.CheckDeviceIdentityArgs;

public class DeviceChannelBox extends BaseChannelBox {

	private CheckDeviceIdentityArgs identityArgs;
	
	public DeviceChannelBox(Channel channel,CheckDeviceIdentityArgs identityArgs) {
		super(channel);
		this.identityArgs = identityArgs;
	}
	
	public CheckDeviceIdentityArgs getIdentityArgs() {
		return identityArgs;
	}

}
