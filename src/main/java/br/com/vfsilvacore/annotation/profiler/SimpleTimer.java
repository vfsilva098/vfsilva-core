//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package br.com.vfsilvacore.annotation.profiler;

import org.slf4j.Logger;

public class SimpleTimer implements Timer {
    private final String tag;
    private final long startMilli;
    private final Logger logger;

    public SimpleTimer(String tag, long startMilli, Logger logger) {
        this.tag = tag;
        this.startMilli = startMilli;
        this.logger = logger;
    }

    public void success(Object callResult) {
        this.log("success");
    }

    public void error(Throwable ex) {
        this.log("error");
    }

    private void log(String message) {
        long duration = System.currentTimeMillis() - this.startMilli;
        if (this.logger.isInfoEnabled()) {
            this.logger.info("tag={}.{},start={},duration={}", new Object[]{this.tag, message, this.startMilli, duration});
        }

    }
}
