package synchro.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import synchro.inner.backup.service.infos.InnerInfoService;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>同步信息
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Component("InnerInfoTask")
public class InnerExportSynchInfoTask {

    /** 同步信息service **/
    @Autowired
    private InnerInfoService infoService;
    
    /**
     * 
     *〈简述〉定时任务执行时间
     *〈详细描述〉
     * @author myc
     */
    public void exportInfo(){
        infoService.backUpInfos();
    }
}
