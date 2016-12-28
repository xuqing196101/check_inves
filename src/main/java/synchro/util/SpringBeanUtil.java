package synchro.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 获取bean的工具类
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {
    
    /** 上下文 **/
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtil.applicationContext = applicationContext;
    }
    
    /**
     * 
     *〈简述〉根据bean的名称获取
     *〈详细描述〉
     * @author myc
     * @param beanName bean名称
     * @return
     */
    public static Object getBeanByName(String beanName) {  
        if (applicationContext == null){  
            return null;
        }
        return applicationContext.getBean(beanName); 
    }
    
    /**
     * 
     *〈简述〉根据class获取
     *〈详细描述〉
     * @author myc
     * @param type 类型
     * @return
     */
    public static <T> T getBean(Class<T> type) {
        return applicationContext.getBean(type); 
    }

}
