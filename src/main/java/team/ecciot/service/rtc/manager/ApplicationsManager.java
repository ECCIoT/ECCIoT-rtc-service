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
    private HashMap<String,ApplicationGroup> hmApplication = new HashMap<String,ApplicationGroup>();
    
    private HashMap<String, ApplicationGroup> getHmApplication() {
		return hmApplication;
	}
    
    /**
     * 通过apikey获取ApplicationGroup
     * @param apikey
     * @return
     */
    public ApplicationGroup getApplicationGroupByApikey(String apikey){
    	synchronized (hmApplication) {
    		return getHmApplication().get(apikey);
		}
    }
    
    /**
     * 添加一个ApplicationGroup
     * @param apikey
     * @param group
     * @return 若已存在相同apikey，则返回false
     */
    public boolean addApplicationGroup(String apikey,ApplicationGroup group){
    	if(getHmApplication().containsKey(apikey)){
    		return false;
    	}else{
    		getHmApplication().put(apikey, group);
    		return true;
    	}
    }

}