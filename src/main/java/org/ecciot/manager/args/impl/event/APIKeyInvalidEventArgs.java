package org.ecciot.manager.args.impl.event;

import org.ecciot.manager.args.thread.BaseEccEventArgs;

public class APIKeyInvalidEventArgs extends BaseEccEventArgs {
    public APIKeyInvalidEventArgs(String content) {
        super(content);
    }

    @Override
    protected void parse(String content) {

    }
}
