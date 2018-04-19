package org.ecciot.manager.args.impl.event;

import org.ecciot.manager.args.thread.BaseEccEventArgs;

public class AlarmEventArgs extends BaseEccEventArgs {

    public AlarmEventArgs(String content) {
        super(content);
    }

    @Override
    protected void parse(String content) {

    }
}
