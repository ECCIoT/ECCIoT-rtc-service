package org.ecciot.manager.args.impl.event;

import org.ecciot.manager.args.thread.BaseEccEventArgs;

public class APIKeyVerifiedEventArgs extends BaseEccEventArgs {
    public APIKeyVerifiedEventArgs(String content) {
        super(content);
    }

    @Override
    protected void parse(String content) {

    }
}
