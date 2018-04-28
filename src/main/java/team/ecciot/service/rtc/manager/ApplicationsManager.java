package team.ecciot.service.rtc.manager;

import java.util.HashMap;

public class ApplicationsManager {

    private static volatile ApplicationsManager instance = null;

    public static ApplicationsManager getInstance() {
        if (instance == null) {
            synchronized (ApplicationsManager.class) {
                if (instance == null) {
                    instance = new ApplicationsManager();
                }
            }
        }
        return instance;
    }

    private ApplicationsManager(){}

    //API_KEY,ApplicationGroup
    private HashMap<String,ApplicationGroup> hmApplication;
    
    public HashMap<String, ApplicationGroup> getHmApplication() {
		return hmApplication;
	}

}