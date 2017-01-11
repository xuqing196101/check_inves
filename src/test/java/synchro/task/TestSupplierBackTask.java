package synchro.task;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import synchro.task.outer.exportTask.SupplierExportTask;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 任务测试
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath*:spring.xml","classpath*:spring-mvc.xml",
                                  "classpath*:spring-mybatis.xml"})

public class TestSupplierBackTask {
    
    @Resource
    private SupplierExportTask synchTask;
    
    @Test
    public void test(){
        synchTask.outerSupplierExportTask();
    }
    
}
