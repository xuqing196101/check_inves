package synchro.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import synchro.inner.read.supplier.InnerSupplierService;
import synchro.util.FileUtils;

import javax.annotation.Resource;
import java.io.File;

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
public class TestImport {

    @Resource
    private InnerSupplierService innerSupplierService;

    @Test
    public void testSupplierNewSubmitImport() {
        // 获取本地文件
        File file = FileUtils.getImportFile();
        // 遍历文件
        if (file != null && file.exists()) {
            File[] files = file.listFiles();
            for (File f : files) {
                //　调用新提交供应商导入方法
                innerSupplierService.importSupplierInfo(f);
            }
        }
    }
}
