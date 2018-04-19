package org.ecciot.manager;

import java.util.HashMap;

public class ApplicationManager {

    private static volatile ApplicationManager instance = null;

    public static ApplicationManager getInstance() {
        if (instance == null) {
            synchronized (ApplicationManager.class) {
                if (instance == null) {
                    instance = new ApplicationManager();
                }
            }
        }
        return instance;
    }

    private ApplicationManager(){}

    private HashMap<String,ApplicationModel> hmApplication;

}