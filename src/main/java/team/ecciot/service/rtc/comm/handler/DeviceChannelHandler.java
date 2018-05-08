package team.ecciot.service.rtc.comm.handler;

import java.util.HashMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import team.ecciot.lib.args.builder.CmdBuilder;
import team.ecciot.lib.args.callback.IRtcCheckIdentityCallback;
import team.ecciot.lib.args.model.impl.APIKeyInvalidArgs;
import team.ecciot.lib.args.model.impl.APIKeyVerifiedArgs;
import team.ecciot.lib.args.model.impl.AskIdentityArgs;
import team.ecciot.lib.args.model.impl.CheckDeviceIdentityArgs;
import team.ecciot.lib.args.model.impl.CheckServerIdentityArgs;
import team.ecciot.lib.args.model.impl.CheckTerminalIdentityArgs;
import team.ecciot.lib.args.model.impl.DeviceStateChangedArgs;
import team.ecciot.lib.args.parser.ContentParser;
import team.ecciot.service.rtc.comm.channel.DeviceChannelBox;
import team.ecciot.service.rtc.manager.ApplicationGroup;
import team.ecciot.service.rtc.manager.ApplicationsManager;
import team.ecciot.service.rtc.utils.ChannelUtils;

public class DeviceChannelHandler extends SimpleChannelInboundHandler<String> {

	// 是否允许未知指令的执行
	public static boolean isEnableUnknowCmd = false;

	public static final HashMap<Channel, String> hmChannalApikey = new HashMap<Channel, String>();

	private static Logger LOGGER = LogManager.getLogger(DeviceChannelHandler.class);

	// 是否已完成身份验证
	boolean bCheckIdentityFinished = false;

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, String msg) throws Exception {
		final Channel channel = arg0.channel();

		// 将读取的指令转换为json格式
		JSONObject json = JSON.parseObject(msg);
		// 读取指令中的Action和Content
		String action = json.getString("action");
		String content = json.getString("content");

		// 判断action是否有效
		if (ContentParser.checkActionValidity(action) || isEnableUnknowCmd) {
			// 判断当前Channel的身份是否已经验证完成，如果未完成则需要进行身份验证，若完成则解析具体的功能指令
			if (!bCheckIdentityFinished) {
				// 进行身份验证
				// 设置指令内容配置解析器（通过实现匿名接口实现解析消息的过滤）
				ContentParser parser = new ContentParser(new IRtcCheckIdentityCallback() {

					@Override
					public void InvalidActionInstruction(String action, String content) {

					}

					@Override
					public void Terminal_CheckTerminalIdentity(CheckTerminalIdentityArgs args) {
						// 不接受，终端通信
						channel.close();
					}

					@Override
					public void Server_CheckServerIdentity(CheckServerIdentityArgs args) {
						// 不接受，终端通信
						// channel.closeFuture().sync();
						channel.close();
					}

					@Override
					public void Device_CheckDeviceIdentity(CheckDeviceIdentityArgs args) {
						String apikey = args.getApikey();
						String itemID = args.getItemID();

						// 查询数据库验证身份
						boolean b = true;
						if (b) {
							// 验证成功

							// 创建一个ApplicationGroup
							ApplicationGroup group = ApplicationsManager.getInstance()
									.getApplicationGroupByApikey(apikey);
							// 创建ServerChannelBox
							DeviceChannelBox dcb = new DeviceChannelBox(channel, args);
							// 将DeviceChannelBox加入ApplicationGroup
							group.getDeviceChannelList().add(dcb);

							// 将信道的apikey记录下来
							hmChannalApikey.put(channel, apikey);

							// 标记已完成身份验证
							bCheckIdentityFinished = true;

							// 产生回执消息
							APIKeyVerifiedArgs akva = new APIKeyVerifiedArgs();
							JSONObject json = CmdBuilder.build(akva);
							// 消息回执
							ChannelUtils.sendMessage(channel, json.toJSONString());

							/*----------将设备登录信息发送给服务端----------*/
							// 产生通知服务端新设备接入的消息
							DeviceStateChangedArgs dsca = new DeviceStateChangedArgs();
							dsca.setItemID(itemID);
							dsca.setState(true);
							JSONObject json2 = CmdBuilder.build(dsca);
							// 获取服务端的Channel
							Channel channelServer = group.getServerChannelBox().getChannel();
							// 将数据转发给服务端
							ChannelUtils.sendMessage(channelServer, json2.toJSONString());

						} else {
							// 验证失败

							// 产生回执消息
							APIKeyInvalidArgs akia = new APIKeyInvalidArgs();
							akia.setMessage("验证未通过.");
							JSONObject json = CmdBuilder.build(akia);
							// 消息回执
							ChannelUtils.sendMessage(channel, json.toJSONString());
						}
					}
				}, IRtcCheckIdentityCallback.class);
				// 解析指令
				if (parser.parse(action, content)) {
					// 执行到这里表示解析成功
				} else {
					// 对于服务端来说不存在消息转发的情况，因此代码执行到这里说明用户SDK版本与服务端不匹配(排除通信安全问题)。
				}
			} else {
				/**
				 * 将消息转发给服务器
				 */
				// 从hmChannalApikey中取得信道对应的Apikey
				String apikey = hmChannalApikey.get(channel);
				// 根据apikey从ApplicationsManager获取ApplicationGroup
				ApplicationGroup group = ApplicationsManager.getInstance().getApplicationGroupByApikey(apikey);
				// 获取服务端的Channel
				Channel channelServer = group.getServerChannelBox().getChannel();
				// 将数据转发给服务端
				ChannelUtils.sendMessage(channelServer, msg);

				/**
				 * (!)暂不使用解析器实现拦截功能 思路：通过解析器获取消息类型，并进行统计，存储记录
				 */
				// // 设置指令内容配置解析器（通过设置XParseCallbackHandler的接口实现解析消息的过滤）
				// ContentParser parser = new ContentParser(new
				// ServerParseCallbackHandler(channel),
				// ServerParseCallbackHandler.class);
				// // 解析指令
				// if (parser.parse(action, content)) {
				// // 执行到这里表示解析成功
				// } else {
				// // 对于服务端来说不存在消息转发的情况，因此代码执行到这里说明用户SDK版本与服务端不匹配(排除通信安全问题)。
				// }

			}
		} else {
			// 接收到无效的指令
		}
	}

	// 当新连接接入
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		// Channel channel = ctx.channel();
		// if (!group.contains(channel)) {
		// group.add(channel);
		// }
	}

	// 当连接断开
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 产生一个身份询问的参数对象
		AskIdentityArgs aia = new AskIdentityArgs();
		aia.setMessage("Welcome!");
		JSONObject json = CmdBuilder.build(aia);
		ChannelUtils.sendMessage(ctx.channel(), json.toJSONString());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		String apikey = null;
		// 从hmChannalApikey中取得信道对应的Apikey

		if (!hmChannalApikey.containsKey(channel)) {
			return;
		}
		apikey = hmChannalApikey.get(channel);
		// 从ApplicationsManager中移除对象
		ApplicationsManager.getInstance().getApplicationGroupByApikey(apikey).removeDeviceChannel(channel);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Channel channel = ctx.channel();
		String apikey = null;
		// 从hmChannalApikey中取得信道对应的Apikey

		if (!hmChannalApikey.containsKey(channel)) {
			return;
		}
		apikey = hmChannalApikey.get(channel);
		// 从ApplicationsManager中移除对象
		ApplicationsManager.getInstance().getApplicationGroupByApikey(apikey).removeDeviceChannel(channel);
		ctx.close().sync();
	}

}