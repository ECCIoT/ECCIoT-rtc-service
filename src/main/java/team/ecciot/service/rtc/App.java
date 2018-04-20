package team.ecciot.service.rtc;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class App 
{
    //日志记录器
    private static Logger LOGGER = LogManager.getLogger(App.class);

    public static void main( String[] args )
    {
        //自动快速地使用缺省Log4j环境
        BasicConfigurator.configure();

        LOGGER.info("ECCIoT RTC service has started.");
    }
}
