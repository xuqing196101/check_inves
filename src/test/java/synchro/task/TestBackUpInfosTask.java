package synchro.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath*:spring.xml","classpath*:spring-mvc.xml",
                                  "classpath*:spring-mybatis.xml"})
public class TestBackUpInfosTask {

    @Value("#{sys[system.title]}")
    private String fileSize;
    
    @Test
    public void test(){
        System.out.println(fileSize);
    }
}
