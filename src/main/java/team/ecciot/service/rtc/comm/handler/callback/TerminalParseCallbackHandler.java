package team.ecciot.service.rtc.comm.handler.callback;

import io.netty.channel.Channel;
import team.ecciot.lib.args.callback.IServerEventCallback;
import team.ecciot.lib.args.callback.ITerminalEventCallback;
import team.ecciot.lib.args.model.impl.AlarmArgs;
import team.ecciot.lib.args.model.impl.BindDeviceArgs;
import team.ecciot.lib.args.model.impl.ControlItemArgs;
import team.ecciot.lib.args.model.impl.DeviceStateChangedArgs;
import team.ecciot.lib.args.model.impl.UpdateItemsDataArgs;

public class TerminalParseCallbackHandler extends BaseParseCallbackHandler implements ITerminalEventCallback{

	public TerminalParseCallbackHandler(Channel channel) {
		super(channel);
	}

	@Override
	public void InvalidActionInstruction(String action, String content) {
		System.out.println(String.format("未知的Action：%s，内容为：%s。", action, content));
	}

	@Override
	public void Device_Alarm(AlarmArgs args) {
		System.out.println(args);
	}

	@Override
	public void Server_UpdateItemsData(UpdateItemsDataArgs args) {
		
	}
}
