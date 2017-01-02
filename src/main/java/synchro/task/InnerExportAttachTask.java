package synchro.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import synchro.inner.backup.service.att.InnerAttachmentService;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>导出附件
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Component("innerAttachTask")
public class InnerExportAttachTask {
    
    /** 同步附件service **/
    @Autowired
    private InnerAttachmentService attachService;
    
    /**
     * 
     *〈简述〉定时任务执行时间
     *〈详细描述〉
     * @author myc
     */
    public void exportAttach(){
        attachService.backAttachment();
    }
}
