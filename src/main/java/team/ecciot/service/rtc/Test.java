package team.ecciot.service.rtc;

import com.alibaba.fastjson.JSONObject;

import team.ecciot.lib.args.builder.CmdBuilder;
import team.ecciot.lib.args.model.impl.CloseDeviceArgs;
import team.ecciot.lib.args.model.impl.ControlDeviceArgs;
import team.ecciot.lib.args.model.impl.QueryOnlineDevicesArgs;
import team.ecciot.lib.args.model.impl.CheckDeviceIdentityArgs;
import team.ecciot.lib.args.model.impl.CheckServerIdentityArgs;
import team.ecciot.lib.args.model.impl.CheckTerminalIdentityArgs;

public class Test {
	
	private final static String API_KEY = "123456789abcdef";
	private final static String DEVICE_ITEMID = "device_001";
	
	
	public static void main(String[] args) {
		System.out.println("产生用于测试的指令:");
		System.out.println("=======================================================================");
		CheckServerIdentityArgs csia = new CheckServerIdentityArgs();
		csia.setApikey(API_KEY);
		csia.setSecretkey("005117");
		JSONObject json1 = CmdBuilder.build(csia);
		System.out.println("服务端身份校验指令:");
		System.out.println(json1.toJSONString());
      
		System.out.println("=======================================================================");
		CheckTerminalIdentityArgs ctia = new CheckTerminalIdentityArgs();
		ctia.setApikey(API_KEY);
		ctia.setPlatform("Android 7.0");
		ctia.setToken("a1b2c3d4");
     	ctia.setVersion("SmartHome v1.1.2");
     	JSONObject json2 = CmdBuilder.build(ctia);
     	System.out.println("移动端身份校验指令:");
     	System.out.println(json2.toJSONString());
     	
     	System.out.println("=======================================================================");
     	CheckDeviceIdentityArgs cdia = new CheckDeviceIdentityArgs();
     	cdia.setApikey(API_KEY);
     	cdia.setItemID(DEVICE_ITEMID);
     	cdia.setModel("SWITCH");
     	cdia.setVersion("TyphaX v1.0.0");
     	JSONObject json3 = CmdBuilder.build(cdia);
     	System.out.println("设备端身份校验指令:");
     	System.out.println(json3.toJSONString());
     	
     	System.out.println("=======================================================================");
     	CloseDeviceArgs cda = new CloseDeviceArgs();
     	cda.setItemID(DEVICE_ITEMID);
     	cda.setState(false);
     	JSONObject json4 = CmdBuilder.build(cda);
      	System.out.println("关闭设备通信的指令:");
      	System.out.println(json4.toJSONString());
      	
      	System.out.println("=======================================================================");
     	QueryOnlineDevicesArgs qoda = new QueryOnlineDevicesArgs();
     	qoda.setApikey(API_KEY);
     	JSONObject json5 = CmdBuilder.build(qoda);
      	System.out.println("查询指定apikey下所有在线设备指令:");
      	System.out.println(json5.toJSONString());
      	
      	System.out.println("=======================================================================");
      	ControlDeviceArgs cia = new ControlDeviceArgs();
      	cia.setItemID(DEVICE_ITEMID);
      	cia.setAtCmd("AT+TEST=1,2,3,4");
      	JSONObject json6 = CmdBuilder.build(cia,"device",DEVICE_ITEMID);
      	System.out.println("向指定设备发送控制指令:");
      	System.out.println(json6.toJSONString());
	}
}
