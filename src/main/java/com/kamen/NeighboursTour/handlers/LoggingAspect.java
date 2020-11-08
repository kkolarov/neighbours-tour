package com.kamen.NeighboursTour.handlers;

import com.kamen.NeighboursTour.NeighboursTour;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {

    private final static Logger LOG = Logger.getLogger(LoggingAspect.class.getName());

    @Pointcut("execution(* com.kamen.NeighboursTour.NeighboursTourManager.*(..))")
    private void selectAllNeighboursTourManagerMethods(){}

    @AfterReturning(value = "selectAllNeighboursTourManagerMethods() && args(neighboursTour)", returning = "value")
    public void log(JoinPoint joinPoint, Object value, NeighboursTour neighboursTour){
        LOG.log(Level.INFO, "Method name: {0}, arguments: {1}, returned value: {2}", new Object[] {joinPoint.getSignature().getName(),neighboursTour.toString(), value});
    }

}
