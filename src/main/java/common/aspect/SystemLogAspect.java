package common.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

import common.annotation.SystemControllerLog;
import common.annotation.SystemServiceLog;
import common.model.SystemLog;
import common.service.SystemLogService;
import common.utils.DateUtils;
import ses.model.bms.User;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 系统日志AOP
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Component
@Aspect  
@Order(10)
public class SystemLogAspect {
    
    /** log4j **/
    private static final Logger log = Logger.getLogger(SystemLogAspect.class);
    
    /** log类型-controller **/
    private static final int  LOG_TYPE_CONTROLLER = 1;
    
    /** log类型-service **/
    private static final int LOG_TYPE_SERVICE = 2;
    
    /** 系统日志 **/
    @Autowired
    private SystemLogService systemLogService;
    
    /** 当前操作用户 **/
    private User currentUser;
    
    /** 开始时间 **/
    private long startTimeMillis;
    
    /**
     *〈简述〉controller 切面
     *〈详细描述〉
     * @author myc
     */
    @Pointcut("@annotation(common.annotation.SystemControllerLog)")
    public void controllerAspect(){}
    
    /**
     * 
     *〈简述〉service 切面
     *〈详细描述〉
     * @author myc
     */
    @Pointcut("@annotation(common.annotation.SystemServiceLog)")
    public void serviceAspect(){}
    
    /**
     * 
     *〈简述〉controller切面开始
     *〈详细描述〉
     * @author myc
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint){
        log.info("aspect before starting...");
        startTimeMillis = System.currentTimeMillis();
        HttpServletRequest request = getHttpServletRequest();
        currentUser = (User) request.getSession().getAttribute("loginUser");
    }
    
    /**
     * 
     *〈简述〉controller切面结束
     *〈详细描述〉异常 不执行
     * @author myc
     */
   // @After("controllerAspect()")
    @AfterReturning("controllerAspect()")
    public void doAfter(JoinPoint joinPoint){
        try {
            long endTimeMillis = System.currentTimeMillis();
            HttpServletRequest request = getHttpServletRequest();
            SystemLog systemLog = new SystemLog();
            systemLog.setLogType(LOG_TYPE_CONTROLLER);
            systemLog.setOperateIp(getIpAddress(request));
            systemLog.setOperatePersonId(currentUser.getId());
            systemLog.setOperatePersonName(currentUser.getRelName() == null? currentUser.getLoginName() : currentUser.getRelName());
            systemLog.setOperateTime(new Date());
            //systemLog.setOperateType((Integer)getAnnotaControllerParam(joinPoint)[1]);
            systemLog.setDescriptions((String)getAnnotaControllerParam(joinPoint)[0]);
            systemLog.setMethod(getOperMethod(joinPoint));
            systemLog.setOperateStartTime(DateUtils.longToDate(startTimeMillis));
            systemLog.setOperateEndTime(DateUtils.longToDate(endTimeMillis));
            systemLog.setResponseTime((endTimeMillis - startTimeMillis ) + "");
            systemLog.setParams(JSON.toJSONString(request.getParameterMap()));
            systemLogService.saveLog(systemLog);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 
     *〈简述〉异常处理
     *〈详细描述〉异常执行
     * @author myc
     * @param joinPoint
     * @param e
     */
   // @ AfterThrowing(pointcut ="serviceAspect()",throwing = "e")
    @AfterThrowing(pointcut="controllerAspect()",throwing ="e")
    public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {   
        try {
            long endTimeMillis = System.currentTimeMillis();
            HttpServletRequest request = getHttpServletRequest();
            //currentUser = (User) request.getSession().getAttribute("loginUser");
            SystemLog systemLog = new SystemLog();
            systemLog.setLogType(LOG_TYPE_SERVICE);
            systemLog.setOperateIp(getIpAddress(request));
            systemLog.setOperatePersonId(currentUser.getId());
            systemLog.setOperatePersonName(currentUser.getRelName() == null? currentUser.getLoginName() : currentUser.getRelName());
            systemLog.setOperateTime(new Date());
            /*systemLog.setOperateType((Integer)getAnnotaServiceParam(joinPoint)[1]);
            systemLog.setDescriptions((String)getAnnotaServiceParam(joinPoint)[0]);*/
            //systemLog.setOperateType((Integer)getAnnotaControllerParam(joinPoint)[1]);
            systemLog.setDescriptions((String)getAnnotaControllerParam(joinPoint)[0]);
            systemLog.setMethod(getOperMethod(joinPoint));
            systemLog.setOperateStartTime(DateUtils.longToDate(startTimeMillis));
            systemLog.setOperateEndTime(DateUtils.longToDate(endTimeMillis));
            systemLog.setExceptionDetail(e.getMessage());
            systemLog.setResponseTime((endTimeMillis - startTimeMillis ) + "");
            systemLog.setParams(JSON.toJSONString(request.getParameterMap()));
            systemLogService.saveLog(systemLog);
        } catch (Exception e1) {
            log.error(e1.getMessage());
            e1.printStackTrace();
        }
    }
    
    /**
     *〈简述〉获取操作方法
     *〈详细描述〉
     * @author myc
     * @param joinPoint 切入点
     * @return
     */
    private static String getOperMethod(JoinPoint joinPoint){
        
        return joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()";
    }
    
    /**
     * 
     *〈简述〉 获取注解的参数 controller
     *〈详细描述〉
     * @author myc
     * @param joinPoint 
     * @return
     * @throws Exception
     */
    private  static Object []  getAnnotaControllerParam(JoinPoint joinPoint)    
            throws Exception {    
       String targetName = joinPoint.getTarget().getClass().getName();    
       String methodName = joinPoint.getSignature().getName();    
       Object[] arguments = joinPoint.getArgs();  
       Class<?> targetClass = Class.forName(targetName);    
       Method [] methods = targetClass.getMethods();    
       Object[] params = new Object[2];    
        for (Method method : methods) {    
            if (method.getName().equals(methodName)) {    
               Class<?>[] clazzs = method.getParameterTypes();    
                if (clazzs.length == arguments.length) {  
                    String description = method.getAnnotation(SystemControllerLog.class).description();  
                    //int operateType = method.getAnnotation(SystemControllerLog.class).operType();
                    params[0] = description;
                    //params[1] = operateType;
                    break;    
               }    
           }    
       }    
        return params;    
   }    
    
    /**
     * 
     *〈简述〉 获取注解的参数 service
     *〈详细描述〉
     * @author myc
     * @param joinPoint 
     * @return
     * @throws Exception
     */
    private  static Object []  getAnnotaServiceParam(JoinPoint joinPoint)throws Exception {    
       String targetName = joinPoint.getTarget().getClass().getName();    
       String methodName = joinPoint.getSignature().getName();    
       Object[] arguments = joinPoint.getArgs();    
       Class<?> targetClass = Class.forName(targetName);    
       Method [] methods = targetClass.getMethods();    
       Object[] params = new Object[2];    
        for (Method method : methods) {    
            if (method.getName().equals(methodName)) {    
               Class<?>[] clazzs = method.getParameterTypes();    
                if (clazzs.length == arguments.length) {    
                    String description = method.getAnnotation(SystemServiceLog.class).description();  
                    int operateType = method.getAnnotation(SystemServiceLog.class).operType();
                    params[0] = description;
                    params[1] = operateType;
                    break;    
               }    
           }    
       }    
        return params;    
   }    
    
    /**
     * 
     *〈简述〉获取httpServletRequest
     *〈详细描述〉
     * @author myc
     * @return
     */
    private HttpServletRequest getHttpServletRequest(){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();  
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;  
        HttpServletRequest request = sra.getRequest();
        return request;
    }
    
    /**
     * 
     *〈简述〉获取真实的ip地址,防止使用代理
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     * @return
     */
    private static String getIpAddress(HttpServletRequest request) { 
        String ip = request.getHeader("x-forwarded-for"); 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
          ip = request.getHeader("Proxy-Client-IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
          ip = request.getHeader("WL-Proxy-Client-IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
          ip = request.getHeader("HTTP_CLIENT_IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
          ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
          ip = request.getRemoteAddr(); 
        } 
        return ip; 
      } 
}
