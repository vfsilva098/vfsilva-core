//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package br.com.vfsilvacore.annotation.profiler;

import br.com.vfsilvacore.annotation.MethodInvocationHandler;
import br.com.vfsilvacore.annotation.MethodInvocationObserver;
import br.com.vfsilvacore.annotation.TargetMethod;

import java.util.Objects;

public class LogProfilerAspect implements MethodInvocationHandler {
    private TimeTracker timeTracker;

    public LogProfilerAspect() {
        this(new SimpleTimeTracker());
    }

    public LogProfilerAspect(TimeTracker timeTracker) {
        this.timeTracker = (TimeTracker) Objects.requireNonNull(timeTracker);
    }

    public MethodInvocationObserver start(TargetMethod targetMethod) {
        String tag = this.buildTag(targetMethod);
        return this.timeTracker.start(tag);
    }

    private String buildTag(TargetMethod targetMethod) {
        LogProfiler logProfiler = (LogProfiler) targetMethod.getAnnotation(LogProfiler.class);
        String tag;
        if (logProfiler != null && !this.undefinedTagName(logProfiler)) {
            tag = logProfiler.tag();
        } else {
            tag = targetMethod.getOwnerClassName() + "." + targetMethod.getName();
        }

        return tag;
    }

    private boolean undefinedTagName(LogProfiler logProfiler) {
        if (logProfiler == null) {
            return true;
        } else {
            String tag = logProfiler.tag();
            return tag == null || tag.length() == 0 || this.allWhiteSpaces(tag);
        }
    }

    private boolean allWhiteSpaces(String tag) {
        for (int i = 0; i < tag.length(); ++i) {
            if (!Character.isWhitespace(tag.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}
