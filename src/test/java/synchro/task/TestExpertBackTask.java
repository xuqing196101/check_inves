package synchro.task;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 专家备份测试
 * <详细描述>
 * @author   WangHuijie
 * @version  
 * @since
 * @see
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath*:spring.xml","classpath*:spring-mvc.xml",
                                  "classpath*:spring-mybatis.xml"})

public class TestExpertBackTask {
    
    /*@Resource
    private SynchTask synchTask;
    
    @Test
    public void test(){
        synchTask.outerExpertTask();
    }*/
    
}
