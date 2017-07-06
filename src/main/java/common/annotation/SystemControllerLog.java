package common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 系统日志controller层
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER, ElementType.METHOD})
@Documented
public @interface SystemControllerLog {

    /** 描述 **/
    String description() default "";
    
    /** 操作类型 **/
   /* int operType() default 3; */
}
