package org.ecciot.comm.model;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ApplicationModel {

    private final String API_KEY;

    private Channel cServer;
    private ChannelGroup cgDevice;      // = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private ChannelGroup cgTerminal;    // = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public ApplicationModel(String api_key){
        API_KEY = api_key;
    }

    public String getApiKey() {
        return API_KEY;
    }
}
