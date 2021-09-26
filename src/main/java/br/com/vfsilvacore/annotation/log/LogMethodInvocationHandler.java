//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package br.com.vfsilvacore.annotation.log;

import br.com.vfsilvacore.annotation.MethodInvocationHandler;
import br.com.vfsilvacore.annotation.MethodInvocationObserver;
import br.com.vfsilvacore.annotation.SimpleFormatter;
import br.com.vfsilvacore.annotation.TargetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogMethodInvocationHandler implements MethodInvocationHandler {
    private final String logPrefix = LogMethodInvocationHandler.class.getPackage().getName() + ".Log";

    public LogMethodInvocationHandler() {
    }

    public MethodInvocationObserver start(TargetMethod targetMethod) {
        String logTarget = this.logPrefix + targetMethod.getOwnerClassName();
        Logger log = LoggerFactory.getLogger(logTarget);
        return new LogMethodInvocationMessage(targetMethod, log, new SimpleFormatter(" "));
    }
}
