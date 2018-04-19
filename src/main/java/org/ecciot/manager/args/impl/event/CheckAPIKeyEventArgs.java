package org.ecciot.manager.args.impl.event;

import org.ecciot.manager.args.thread.BaseEccEventArgs;

public class CheckAPIKeyEventArgs extends BaseEccEventArgs {
    public CheckAPIKeyEventArgs(String content) {
        super(content);
    }

    @Override
    protected void parse(String content) {

    }
}
