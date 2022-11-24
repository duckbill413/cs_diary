package com.example.springaop.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class ParameterAop {
    @Pointcut("execution(* com.example.springaop.controller..*.*(..))")
    private void pointCut(){

    }
    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println(method.getName());

        Object [] args = joinPoint.getArgs();

        for (Object obj : args){
            System.out.println("type: " + obj.getClass().getSimpleName());
            System.out.println("value: " + obj);
        }
    }

    @AfterReturning(value = "pointCut()", returning = "object")
    public void afterReturn(JoinPoint joinPoint, Object object){
        System.out.println("return Obj");
        System.out.println(object);
    }
}
