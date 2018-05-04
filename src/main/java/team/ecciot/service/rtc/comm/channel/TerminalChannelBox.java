package team.ecciot.service.rtc.comm.channel;

import io.netty.channel.Channel;
import team.ecciot.lib.args.model.impl.CheckTerminalIdentityArgs;

public class TerminalChannelBox extends BaseChannelBox {

	private CheckTerminalIdentityArgs identityArgs;
	
	public TerminalChannelBox(Channel channel,CheckTerminalIdentityArgs identityArgs) {
		super(channel);
		this.identityArgs = identityArgs;
	}
	
	public CheckTerminalIdentityArgs getIdentityArgs() {
		return identityArgs;
	}

}
