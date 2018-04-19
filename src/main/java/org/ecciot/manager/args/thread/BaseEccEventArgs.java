package org.ecciot.manager.args.thread;

import org.ecciot.manager.args.thread.BaseEccArgs;

public abstract class BaseEccEventArgs extends BaseEccArgs {
    public BaseEccEventArgs(String content){
        parse(content);
    }
    protected abstract void parse(String content);
}
