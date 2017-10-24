package synchro.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import synchro.outer.back.service.supplier.OuterSupplierService;

import javax.annotation.Resource;

/**
 * Description: 编写导入测试类
 *
 * @param
 * @author Easong
 * @version 2017/10/11
 * @since JDK1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mvc.xml", "classpath:spring-mybatis.xml", "classpath:spring-redis.xml", "classpath:spring-solr.xml"})
@WebAppConfiguration
public class TestExport {

    @Resource
    private OuterSupplierService outerSupplierService;

    /**
     *
     * Description: 测试注销供应商导出
     *
     * @author Easong
     * @version 2017/10/16
     * @param 
     * @since JDK1.7
     */
    @Test
    public void testLogoutSupplierImport() {
        outerSupplierService.selectLogoutSupplierOfExport("2017-10-16 16:00:00","2017-10-16 16:50:00");
    }
}
