//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package br.com.vfsilvacore.annotation;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class MethodInvocationInpector implements MethodInterceptor {
    private MethodInvocationHandler handler;

    public MethodInvocationInpector(MethodInvocationHandler handler) {
        this.handler = handler;
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        TargetMethod targetMethod = new TargetMethodWrapper(invocation);
        MethodInvocationObserver observer = this.handler.start(targetMethod);

        try {
            Object callResult = invocation.proceed();
            observer.success(callResult);
            return callResult;
        } catch (Throwable var6) {
            observer.error(var6);
            throw var6;
        }
    }
}
