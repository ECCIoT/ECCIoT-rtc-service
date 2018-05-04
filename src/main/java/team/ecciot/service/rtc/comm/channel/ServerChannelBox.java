package team.ecciot.service.rtc.comm.channel;

import io.netty.channel.Channel;
import team.ecciot.lib.args.model.impl.CheckServerIdentityArgs;

public class ServerChannelBox extends BaseChannelBox {

	private CheckServerIdentityArgs identityArgs;
	
	public ServerChannelBox(Channel channel,CheckServerIdentityArgs identityArgs) {
		super(channel);
		this.identityArgs = identityArgs;
	}
	
	public CheckServerIdentityArgs getIdentityArgs() {
		return identityArgs;
	}

}
