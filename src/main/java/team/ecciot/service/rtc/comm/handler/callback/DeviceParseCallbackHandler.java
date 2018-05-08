package team.ecciot.service.rtc.comm.handler.callback;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import io.netty.channel.Channel;
import team.ecciot.lib.args.callback.IDevice2ServerEventCallback;
import team.ecciot.lib.args.model.impl.AlarmArgs;

public class DeviceParseCallbackHandler extends BaseParseCallbackHandler implements IDevice2ServerEventCallback{

	private static Logger LOGGER = LogManager.getLogger(DeviceParseCallbackHandler.class);
	
	public DeviceParseCallbackHandler(Channel channel) {
		super(channel);
	}

	public void InvalidActionInstruction(String action, String content) {
		LOGGER.info(String.format("未知的Action：%s，内容为：%s。", action, content));
	}

	@Override
	public void Device_Alarm(AlarmArgs args) {
		System.out.println(args);
	}
}
