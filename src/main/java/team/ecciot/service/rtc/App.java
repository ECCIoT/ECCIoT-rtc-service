package team.ecciot.service.rtc;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import team.ecciot.lib.args.builder.CmdBuilder;
import team.ecciot.lib.args.model.impl.CheckServerIdentityArgs;
import team.ecciot.service.rtc.comm.listener.ServerListener;

public class App 
{
    //日志记录器
    private static Logger LOGGER = LogManager.getLogger(App.class);

    public static void main( String[] args )
    {
        //自动快速地使用缺省Log4j环境
        BasicConfigurator.configure();

        //产生一个用于测试的服务端身份校验指令
        CheckServerIdentityArgs csia = new CheckServerIdentityArgs();
        csia.setApikey("123456789abcdef");
        csia.setSecretkey("005117");
        JSONObject json = CmdBuilder.build(csia);
        System.out.println("产生一个用于测试的服务端身份校验指令:");
        System.out.println(json.toJSONString());
        System.out.println();
        
        new ServerListener(19941).run();
        
        LOGGER.info("ECCIoT RTC service has started.");
    }
}
