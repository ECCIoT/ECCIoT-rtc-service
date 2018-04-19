package org.ecciot.manager.args.thread;

import com.alibaba.fastjson.JSON;

public abstract class BaseEccArgs {
    public String toString()
    {
        return JSON.toJSONString(this);
    }
}
