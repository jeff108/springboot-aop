package com.king.springbootaop.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * 日志切面
 */
@Component
@Aspect
public class LogAspect {

    @Pointcut("execution(public * com.king.springbootaop.controller.*.*(..))")
    public void webLog(){}

    /**
     * 前置通知
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{
        System.out.println("前置通知开始...");
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributs.getRequest();
        //记录下请求内容
        System.out.println("URL : " + request.getRequestURL().toString());
        System.out.println("HTTP METHOD : " + request.getMethod());
        System.out.println("IP : " + request.getRemoteAddr());
        System.out.println("CLASS METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "()");
        System.out.println("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        System.out.println("前置通知结束...");
    }

    /**
     * 后置通知
     * @param ret
     */
    @AfterReturning(pointcut = "webLog()",returning = "ret")
    public void doAfterReturning(Object ret){
        System.out.println("后置通知开始...");
        System.out.println("方法返回值 : " + ret);
        System.out.println("后置通知结束...");
    }

    /**
     * 异常通知
     * @param ex
     */
    @AfterThrowing(throwing = "ex",pointcut = "webLog()")
    public void doTrowss(Throwable  ex) throws ServletException, IOException {
        System.out.println("异常通知开始...");
        System.out.println("目标方法抛出的异常 : " + ex);
        ServletRequestAttributes attributs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributs.getRequest();
        HttpServletResponse response = attributs.getResponse();
        request.getRequestDispatcher("err").forward(request,response);
        System.out.println("异常通知结束...");
    }

    /**
     * 最终通知，final增强，不管是跑出异常还是正常退出都会执行
     * @param joinPoint
     */
    @After("webLog()")
    public void doAfter(JoinPoint joinPoint){
        System.out.println("最终通知开始...");
    }

    /**
     * 环绕增强
     * @param pj
     * @return
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint pj) throws Throwable {
        System.out.println("目标方法执行前相关操作...");
        // 执行目标方法
        Object object = pj.proceed();
        System.out.println("目标方法执行后相关操作...");
        return object;
    }

}
