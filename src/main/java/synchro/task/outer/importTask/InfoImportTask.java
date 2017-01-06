package synchro.task.outer.importTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import synchro.outer.read.OuterFilesRepeater;

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
@Component("outerInfoImportTask")
public class InfoImportTask {
    
    /**
     * 外网文件导入
     */
    @Autowired
    private OuterFilesRepeater fileRepeater;
    
    /**
     * 
     *〈简述〉任务
     *〈详细描述〉
     * @author myc
     */
    public void outerInfoImportTask(){
        fileRepeater.initFiles();
    }
    
}
