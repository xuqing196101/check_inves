package synchro.inner.read;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import synchro.util.FileUtils;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Component
public class FilesRepeater {
    
    /**
     * 线程池
     */
    @Autowired
    private TaskExecutor taskExecutor;
    
    /**
     * 
     *〈简述〉初始化
     *〈详细描述〉
     * @author myc
     */
    public void initFiles(){
        File file = FileUtils.getImportFile();
        if (file != null && file.exists()){
            File [] files = file.listFiles();
            for (File f : files){
                taskExecutor.execute(new FileRun(f));
            }
        }
    }
}
