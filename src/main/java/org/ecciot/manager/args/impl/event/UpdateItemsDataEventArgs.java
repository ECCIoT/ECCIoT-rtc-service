package org.ecciot.manager.args.impl.event;

import org.ecciot.manager.args.thread.BaseEccEventArgs;

public class UpdateItemsDataEventArgs extends BaseEccEventArgs {
    public UpdateItemsDataEventArgs(String content) {
        super(content);
    }

    @Override
    protected void parse(String content) {

    }
}
