package synchro.task.inner.importTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import synchro.inner.read.InnerFilesRepeater;

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
@Component("innerInfoImportTask")
public class InfoImportTask {

    
    /**
     * 外网文件导入
     */
    @Autowired
    private InnerFilesRepeater fileRepeater;
    
    /**
     * 
     *〈简述〉任务
     *〈详细描述〉
     * @author myc
     */
    public void innerInfoImportTask(){
        fileRepeater.initFiles();
        /**内网导入**/
    }
    
}
