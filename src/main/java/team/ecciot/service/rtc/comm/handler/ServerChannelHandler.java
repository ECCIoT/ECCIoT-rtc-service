package team.ecciot.service.rtc.comm.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import team.ecciot.lib.args.model.cmd.CheckAPIKeyCmdArgs;
import team.ecciot.lib.args.model.cmd.ControlItemCmdArgs;
import team.ecciot.lib.args.parser.CmdParserCallback;
import team.ecciot.lib.args.parser.ContentParser;

public class ServerChannelHandler extends SimpleChannelInboundHandler<String> {

    /*
     *   思路：当服务端的通信接入时，首先验证身份，再判断应用管理器中是否已有来自相同应用API的服务通信
     */
    public static final ChannelGroup group = new DefaultChannelGroup(
            GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext arg0, String msg)
            throws Exception {
        Channel channel = arg0.channel();
//        for (Channel ch : group) {
//            if (ch == channel) {
//                ch.writeAndFlush("[you]：" + arg1 + "\n");
//            } else {
//                ch.writeAndFlush(
//                        "[" + channel.remoteAddress() + "]: " + arg1 + "\n");
//            }
//        }
        
        //将读取的指令转换为json格式
        JSONObject json = JSON.parseObject(msg);
        //读取指令中的Action
        String action = json.getString("action");
        //判断action是否存在
        if(ContentParser.checkActionValidity(action) && action.equals("EccCmd_CheckAPIKey")){
        	
        	//定义解析器
        	ContentParser parser = new ContentParser(new CmdParserCallback() {
    			@Override
    			public void InvalidActionInstruction(String action, String content) {}
    			@Override
    			public void EccCmd_ControlItem(ControlItemCmdArgs args) {}
    			@Override
    			public void EccCmd_CheckAPIKey(CheckAPIKeyCmdArgs args) {
    				//args.getApiKey();
    			}
    		});
        }
        
        
        

    }

    //当新连接接入
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        if(!group.contains(channel)){
        	group.add(channel);
        }
        
    }

    //当连接断开
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        //从group中移除对象
        if(!group.contains(channel)){
        	group.remove(channel);
        }
        
        //从ApplicationsManager中移除对象
        
        
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("[" + channel.remoteAddress() + "] " + "online");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("[" + channel.remoteAddress() + "] " + "offline");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        System.out.println(
                "[" + ctx.channel().remoteAddress() + "]" + "exit the room");
        ctx.close().sync();
    }

}