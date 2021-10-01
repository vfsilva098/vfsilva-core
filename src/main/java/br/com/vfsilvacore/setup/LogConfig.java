package br.com.vfsilvacore.setup;

import br.com.vfsilvacore.annotation.MethodInvocationInpector;
import br.com.vfsilvacore.annotation.log.LogInfo;
import br.com.vfsilvacore.annotation.log.LogMethodInvocationHandler;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Classe responsável pela configuração dos Logs da aplicação.
 */
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
public class LogConfig {

    @Bean
    public Advisor logInfoAdvisor() {
        final AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(
                "execution(public * *(..)) && @annotation(" + LogInfo.class.getName() + ")");
        final MethodInvocationInpector advice = new MethodInvocationInpector(
                new LogMethodInvocationHandler());
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}