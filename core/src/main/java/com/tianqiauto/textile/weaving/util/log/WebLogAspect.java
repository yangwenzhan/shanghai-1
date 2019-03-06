package com.tianqiauto.textile.weaving.util.log;

import com.tianqiauto.textile.weaving.model.base.Sys_Log;
import com.tianqiauto.textile.weaving.model.base.User;
import com.tianqiauto.textile.weaving.repository.LogRepository;
import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Component
@Aspect
@Log
public class WebLogAspect {

    /**
     * 我们可能会对Web层做多个切面，校验用户，校验头信息等等，这个时候经常会碰到切面的处理顺序问题。
     * 所以，我们需要定义每个切面的优先级，我们需要@Order(i)注解来标识切面的优先级。i的值越小，优先级越高。
     */
    ThreadLocal<Long> startTime = new ThreadLocal<>();
    @Autowired
    private LogRepository logJpaRepository;
    // controller相关切面拦截
    @Pointcut("execution(public * com.tianqiauto.textile.weaving.controller..*.*(..))")
    private void controller() {
    }





    @Before("controller()&&@annotation(logger)")
    public void doBefore(JoinPoint joinPoint,Logger logger){
        startTime.set(System.currentTimeMillis());
    }


    @AfterThrowing(throwing="ex",pointcut = "controller()")
    public void doException(JoinPoint joinPoint,Throwable ex){  //JoinPoint必须放最前面
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        String URL = request.getRequestURL().toString();
        String HTTP_METHOD = request.getMethod();
        String IP = request.getRemoteAddr();

        HttpSession session = request.getSession();
        String userInfo = null;
        User user =  (User)session.getAttribute("current_user");
        if(user != null){
            userInfo = user.getId()+" "+user.getUsername();
        }

        Sys_Log  sys_log = new Sys_Log();
        sys_log.setUserInfo(userInfo);
        sys_log.setException(ex.toString());
        sys_log.setUrl(URL);
        sys_log.setHttp_method(HTTP_METHOD);
        sys_log.setIp(IP);
        sys_log.setClass_method(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        sys_log.setArgs(Arrays.toString(joinPoint.getArgs()));
        sys_log.setType("exception");
        logJpaRepository.save(sys_log);
    }





    @AfterReturning(returning = "ret", pointcut = "controller()&&@annotation(logger))")
    public void doAfterReturning(JoinPoint joinPoint,Object ret,Logger logger) {  //JoinPoint必须放最前面

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        String URL = request.getRequestURL().toString();
        String HTTP_METHOD = request.getMethod();
        String IP = request.getRemoteAddr();


        HttpSession session = request.getSession();
        String userInfo = null;
        User user =  (User)session.getAttribute("current_user");
        if(user != null){
            userInfo = user.getId()+" "+user.getUsername();
        }

        Sys_Log  sys_log = new Sys_Log();
        sys_log.setUserInfo(userInfo);
        sys_log.setUrl(URL);
        sys_log.setHttp_method(HTTP_METHOD);
        sys_log.setIp(IP);
        sys_log.setClass_method(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        sys_log.setArgs(Arrays.toString(joinPoint.getArgs()));
        sys_log.setType("normal");
        sys_log.setExecuteTime(System.currentTimeMillis() - startTime.get());
        sys_log.setResponse(ret.toString());
        sys_log.setMsg(logger.msg());
        logJpaRepository.save(sys_log);
    }



}
