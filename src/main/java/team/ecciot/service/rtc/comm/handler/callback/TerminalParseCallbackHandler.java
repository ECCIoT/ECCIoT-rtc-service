package team.ecciot.service.rtc.comm.handler.callback;

import io.netty.channel.Channel;
import team.ecciot.lib.args.callback.ITerminal2RtcEventCallback;

public class TerminalParseCallbackHandler extends BaseParseCallbackHandler implements ITerminal2RtcEventCallback{

	public TerminalParseCallbackHandler(Channel channel) {
		super(channel);
	}

	@Override
	public void InvalidActionInstruction(String action, String content) {
//		System.out.println(String.format("未知的Action：%s，内容为：%s。", action, content));
	}
}
