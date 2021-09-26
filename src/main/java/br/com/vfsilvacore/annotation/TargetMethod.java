//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package br.com.vfsilvacore.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

public interface TargetMethod {
    <T extends Annotation> T getAnnotation(Class<T> var1);

    String getOwnerClassName();

    String getName();

    Annotation[][] getParameterAnnotations();

    Parameter[] getParameters();

    Object[] getArguments();
}
