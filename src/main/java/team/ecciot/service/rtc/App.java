package team.ecciot.service.rtc;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import team.ecciot.service.rtc.comm.listener.DeviceListener;
import team.ecciot.service.rtc.comm.listener.ServerListener;
import team.ecciot.service.rtc.comm.listener.TerminalListener;

public class App 
{
    //日志记录器
    private static Logger LOGGER = LogManager.getLogger(App.class);

    public static void main( String[] args )
    {
        //自动快速地使用缺省Log4j环境
        BasicConfigurator.configure();

//        new ServerListener(19941).run();
//        new Thread(){
//        	public void run() {
//        		
//        	};
//        }.start();
        new Thread(){
        	public void run() {
        		new TerminalListener(19942).run();
        	};
        }.start();
        new Thread(){
        	public void run() {
        		new DeviceListener(19943).run();
        	};
        }.start();
        new ServerListener(19941).run();
        
        
        
        LOGGER.info("ECCIoT RTC service has started.");
    }
}
