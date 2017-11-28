package scheduled.supplier;/**
 * Created by Easong on 2017/11/22.
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ses.service.sms.SupplierAuditService;

/**
 * 供应商定时任务测试
 *
 * @author Easong
 * @create 2017-11-22 17:30
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mvc.xml", "classpath:spring-mybatis.xml", "classpath:spring-redis.xml", "classpath:spring-solr.xml"})
@WebAppConfiguration
public class SupplierScheduledTest {

    @Autowired
    private SupplierAuditService supplierAuditService;

    /**
     *
     * Description:测试短信通知
     *
     * @author Easong
     * @version 2017/11/22
     * @param
     * @since JDK1.7
     */
    @Test
    public void testMessageAdvice(){
        supplierAuditService.updateHandlerPublictySup();
    }
}
