//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package br.com.vfsilvacore.annotation.log;

import br.com.vfsilvacore.annotation.LogParameter;
import br.com.vfsilvacore.annotation.MethodInvocationObserver;
import br.com.vfsilvacore.annotation.TargetMethod;
import br.com.vfsilvacore.annotation.log.LogFormater.FormatField;
import org.slf4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.*;

import static br.com.vfsilvacore.annotation.log.LogFormater.MethodParameter;

public class LogMethodInvocationMessage implements MethodInvocationObserver {
    private TargetMethod targetMethod;
    private Logger logger;
    private LogInfo logInfo;
    private LogFormater formatter;

    public LogMethodInvocationMessage(TargetMethod targetMethod, Logger logger, LogFormater formatter) {
        this.targetMethod = targetMethod;
        this.formatter = formatter;
        this.logInfo = (LogInfo) targetMethod.getAnnotation(LogInfo.class);
        this.logger = logger;
        this.logStart();
    }

    private void logStart() {
        if (this.logger.isInfoEnabled()) {
            SortedMap<FormatField, Object> messageValues = this.baseMessage();
            if (this.logParameters(this.logInfo)) {
                this.appendLogParametersMessage(messageValues, this.targetMethod, this.targetMethod.getArguments());
            }

            this.logger.info(this.formatter.format(messageValues));
        }

    }

    private SortedMap<FormatField, Object> baseMessage() {
        SortedMap<FormatField, Object> messageValues = new TreeMap();
        messageValues.put(FormatField.ClassName, this.targetMethod.getOwnerClassName());
        messageValues.put(FormatField.MethodName, this.targetMethod.getName());
        return messageValues;
    }

    public void error(Throwable ex) {
        if (this.logError(this.logInfo) && this.logger.isErrorEnabled()) {
            SortedMap<FormatField, Object> messageValues = this.baseMessage();
            messageValues.put(FormatField.ExceptionClass, ex.getClass().getSimpleName());
            messageValues.put(FormatField.ExceptionMessage, ex.getMessage());
            this.logger.error(this.formatter.format(messageValues));
        }

    }

    public void success(Object callResult) {
        if (this.logResult(this.logInfo) && this.logger.isInfoEnabled()) {
            SortedMap<FormatField, Object> messageValues = this.baseMessage();
            messageValues.put(FormatField.RetunrValue, callResult);
            this.logger.info(this.formatter.format(messageValues));
        }

    }

    private boolean logResult(LogInfo logInfo) {
        return logInfo != null && logInfo.logResult();
    }

    private boolean logError(LogInfo logInfo) {
        return logInfo != null && logInfo.logError();
    }

    private boolean logParameters(LogInfo logInfo) {
        return logInfo != null && logInfo.logParameters();
    }

    private void appendLogParametersMessage(Map<FormatField, Object> messageValues, TargetMethod method, Object[] args) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Parameter[] parametros = method.getParameters();
        List<MethodParameter> parameters = new ArrayList(parametros.length);

        for (int argIndex = 0; argIndex < args.length; ++argIndex) {
            Annotation[] var8 = parameterAnnotations[argIndex];
            int var9 = var8.length;

            for (int var10 = 0; var10 < var9; ++var10) {
                Annotation annotation = var8[var10];
                if (annotation instanceof LogParameter) {
                    parameters.add(new MethodParameter(parametros[argIndex].getName(), args[argIndex]));
                }
            }
        }

        if (parameters.size() > 0) {
            messageValues.put(FormatField.MethodParameter, parameters);
        }

    }
}
