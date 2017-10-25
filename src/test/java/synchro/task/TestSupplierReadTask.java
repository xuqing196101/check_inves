package synchro.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import synchro.inner.read.InnerFilesRepeater;
import synchro.inner.read.expert.InnerExpertService;
import synchro.util.FileUtils;

import javax.annotation.Resource;
import java.io.File;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>读取供应商信息
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath:spring.xml","classpath:spring-mvc.xml","classpath:spring-mybatis.xml","classpath:spring-redis.xml","classpath:spring-solr.xml"})
@WebAppConfiguration
public class TestSupplierReadTask {

    @Resource
    private InnerFilesRepeater fileRepeater;

    @Resource
    private InnerExpertService innerExpertService;
    
    @Test
    public void test(){
        fileRepeater.initFiles();
    }

    @Test
    public void testExpertPublicity(){
        File file = FileUtils.getImportFile();
        if (file != null && file.exists()) {
            File[] files = file.listFiles();
            for (File f : files) {
                for (File file2 : f.listFiles()) {
                    if (file2.getName().contains(FileUtils.C_SYNCH_PUBLICITY_EXPERT_FILENAME)) {
                        innerExpertService.importExpOfPublicity(file2);
                    }
                }
            }
        }
    }

}
