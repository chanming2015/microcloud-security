package com.github.chanming2015.microcloud.security.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.github.chanming2015.utils.log.DefaultAopLogger;

@Aspect
@Component
public class ServiceAopLogger extends DefaultAopLogger{

    @Pointcut(value = "execution(public * com.github.chanming2015.microcloud.security.service.impl.*.*(..))")
    public void doServicePoint() {	}

    @Override
    @Around(value="doServicePoint()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        return super.aroundAdvice(pjp);
    }

}
