//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package br.com.vfsilvacore.annotation.profiler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleTimeTracker implements TimeTracker {
    private static final Logger logger = LoggerFactory.getLogger(SimpleTimeTracker.class);

    public SimpleTimeTracker() {
    }

    public Timer start(String tag) {
        return new SimpleTimer(tag, System.currentTimeMillis(), logger);
    }
}
