package team.ecciot.service.rtc.comm.handler.callback;

import io.netty.channel.Channel;
import team.ecciot.lib.args.callback.ITerminal2ServerEventCallback;
import team.ecciot.lib.args.model.impl.BindDeviceArgs;
import team.ecciot.lib.args.model.impl.ControlItemArgs;

public class TerminalParseCallbackHandler extends BaseParseCallbackHandler implements ITerminal2ServerEventCallback{

	public TerminalParseCallbackHandler(Channel channel) {
		super(channel);
	}

	@Override
	public void InvalidActionInstruction(String action, String content) {
		System.out.println(String.format("未知的Action：%s，内容为：%s。", action, content));
	}

	@Override
	public void Terminal_ControlItem(ControlItemArgs args) {
		System.out.println(args);		
	}

	@Override
	public void Terminal_BindDevice(BindDeviceArgs args) {
		System.out.println(args);		
	}
}
