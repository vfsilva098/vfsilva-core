//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package br.com.vfsilvacore.annotation;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class TargetMethodWrapper implements TargetMethod {
    private final Method targetMethod;
    private final Class<? extends Object> ownewClass;
    private final MethodInvocation invocation;

    public TargetMethodWrapper(MethodInvocation invocation) throws NoSuchMethodException, SecurityException {
        this.invocation = invocation;
        Method original = invocation.getMethod();
        this.ownewClass = invocation.getThis().getClass();
        this.targetMethod = this.ownewClass.getMethod(original.getName(), original.getParameterTypes());
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return this.targetMethod.getAnnotation(annotationClass);
    }

    public String getOwnerClassName() {
        return this.ownewClass.getSimpleName();
    }

    public String getName() {
        return this.targetMethod.getName();
    }

    public Annotation[][] getParameterAnnotations() {
        return this.targetMethod.getParameterAnnotations();
    }

    public Parameter[] getParameters() {
        return this.targetMethod.getParameters();
    }

    public Object[] getArguments() {
        return this.invocation.getArguments();
    }
}
