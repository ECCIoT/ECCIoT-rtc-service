package team.ecciot.service.rtc.comm.handler.callback;

import java.util.LinkedList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import io.netty.channel.Channel;
import team.ecciot.lib.args.builder.CmdBuilder;
import team.ecciot.lib.args.callback.IServer2RtcEventCallback;
import team.ecciot.lib.args.model.impl.CheckDeviceIdentityArgs;
import team.ecciot.lib.args.model.impl.CheckTerminalIdentityArgs;
import team.ecciot.lib.args.model.impl.CloseDeviceArgs;
import team.ecciot.lib.args.model.impl.QueryOnlineDevicesArgs;
import team.ecciot.lib.args.model.impl.QueryOnlineTerminalsArgs;
import team.ecciot.lib.args.model.impl.ReturnDeviceQueryResultArgs;
import team.ecciot.lib.args.model.impl.ReturnDeviceQueryResultArgs.DeviceInfo;
import team.ecciot.lib.args.model.impl.ReturnTerminalQueryResultArgs;
import team.ecciot.lib.args.model.impl.ReturnTerminalQueryResultArgs.TerminalInfo;
import team.ecciot.service.rtc.comm.channel.DeviceChannelBox;
import team.ecciot.service.rtc.comm.channel.TerminalChannelBox;
import team.ecciot.service.rtc.comm.handler.DeviceChannelHandler;
import team.ecciot.service.rtc.comm.handler.ServerChannelHandler;
import team.ecciot.service.rtc.manager.ApplicationGroup;
import team.ecciot.service.rtc.manager.ApplicationsManager;
import team.ecciot.service.rtc.utils.ChannelUtils;

public class ServerParseCallbackHandler extends BaseParseCallbackHandler implements IServer2RtcEventCallback {

	private static Logger LOGGER = LogManager.getLogger(ServerParseCallbackHandler.class);

	public ServerParseCallbackHandler(Channel channel) {
		super(channel);
	}

	@Override
	public void InvalidActionInstruction(String action, String content) {
//		LOGGER.info(String.format("未知的Action：%s，内容为：%s。", action, content));
	}

	@Override
	public void Server_CloseDevice(CloseDeviceArgs args) {
		// 从参数对象中获取设备的唯一识别码
		String id = args.getItemID();
		// 获取当前Channel的apikey
		String apikey = ServerChannelHandler.hmChannalApikey.get(super.getChannel());
		// 通过apikey获取ApplicationGroup
		ApplicationGroup group = ApplicationsManager.getInstance().getApplicationGroupByApikey(apikey);
		// 通过唯一识别码从ApplicationGroup获取DeviceChannelBox
		DeviceChannelBox dcb = group.getDeviceChannelBoxByItemID(id);
		// 判断有效性
		if(dcb==null)return;
		// 删除设备存在的记录
		if (DeviceChannelHandler.hmChannalApikey.containsKey(dcb.getChannel())) {
			DeviceChannelHandler.hmChannalApikey.remove(dcb.getChannel());
		}
		group.getDeviceChannelList().remove(dcb);
		// 关闭Channel
		dcb.getChannel().close();

		LOGGER.info(String.format("ACTION:%s | Device[%s] from Group[%s] has been closed.",
				CmdBuilder.getActionNameByArgsObject(args), id, apikey));
	}

	@Override
	public void Server_QueryOnlineDevices(QueryOnlineDevicesArgs args) {
		// 获取参数中的apikey
		String apikey = args.getApikey();
		// 通过apikey获取ApplicationGroup
		ApplicationGroup group = ApplicationsManager.getInstance().getApplicationGroupByApikey(apikey);
		// 获取设备列表
		LinkedList<DeviceChannelBox> lst = group.getDeviceChannelList();
		// 定义设备信息的数组
		DeviceInfo[] devices = new ReturnDeviceQueryResultArgs.DeviceInfo[lst.size()];
		// 遍历设备列表将数据转存至设备信息列表中(TODO 多线程情况下这样做很不安全)
		for (int i = 0; i < devices.length; ++i) {
			CheckDeviceIdentityArgs ia = lst.get(i).getIdentityArgs();
			devices[i] = new DeviceInfo();
			devices[i].setItemID(ia.getItemID());
			devices[i].setModel(ia.getModel());
			devices[i].setVersion(ia.getVersion());
		}
		// 创建返回参数对象
		ReturnDeviceQueryResultArgs ra = new ReturnDeviceQueryResultArgs();
		ra.setApikey(apikey);
		ra.setDevices(devices);
		JSONObject json = CmdBuilder.build(ra);
		// 发送数据
		ChannelUtils.sendMessage(getChannel(), json.toJSONString());
	}

	@Override
	public void Server_QueryOnlineTerminals(QueryOnlineTerminalsArgs args) {
		// 获取参数中的apikey
		String apikey = args.getApikey();
		// 通过apikey获取ApplicationGroup
		ApplicationGroup group = ApplicationsManager.getInstance().getApplicationGroupByApikey(apikey);
		// 获取设备列表
		LinkedList<TerminalChannelBox> lst = group.getTerminalChannelList();
		// 定义设备信息的数组
		TerminalInfo[] terminals = new ReturnTerminalQueryResultArgs.TerminalInfo[lst.size()];
		// 遍历设备列表将数据转存至设备信息列表中(TODO 多线程情况下这样做很不安全)
		for (int i = 0; i < terminals.length; ++i) {
			CheckTerminalIdentityArgs ia = lst.get(i).getIdentityArgs();
			terminals[i] = new TerminalInfo();
			terminals[i].setPlatform(ia.getPlatform());
			terminals[i].setToken(ia.getToken());
			terminals[i].setVersion(ia.getVersion());
		}
		// 创建返回参数对象
		ReturnTerminalQueryResultArgs ra = new ReturnTerminalQueryResultArgs();
		ra.setApikey(apikey);
		ra.setTerminals(terminals);
		JSONObject json = CmdBuilder.build(ra);
		// 发送数据
		ChannelUtils.sendMessage(getChannel(), json.toJSONString());
	}

}
