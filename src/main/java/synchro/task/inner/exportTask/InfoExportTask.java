package synchro.task.inner.exportTask;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;
import synchro.inner.back.service.infos.InnerInfoExportService;
import synchro.service.SynchRecordService;
import synchro.util.Constant;
import synchro.util.DateUtils;

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
@Component("innerInfoExportTask")
public class InfoExportTask {

    /** 同步信息service **/
    @Autowired
    private InnerInfoExportService infoService;
    
    /** 记录service  **/
    @Autowired
    private SynchRecordService  recordService;
    
    /**
     * 
     *〈简述〉定时任务执行时间
     *〈详细描述〉
     * @author myc
     */
    public void innerInfoExportTask(){
        DictionaryData dd = DictionaryDataUtil.get(Constant.DATA_TYPE_INFOS_CODE);
        if (dd != null && StringUtils.isNotBlank(dd.getId())){
            String startTime = recordService.getSynchTime(Constant.OPER_TYPE_EXPORT, dd.getId());
            if (!StringUtils.isNotBlank(startTime)){
                startTime = DateUtils.getCurrentDate() + " 00:00:00";
            }
            startTime = DateUtils.getCalcelDate(startTime);
            String endTime = DateUtils.getCurrentTime();
            Date synchDate = DateUtils.stringToTime(endTime);
            infoService.backUpInfos(startTime, endTime, synchDate);
        }
        /**
         * 内网导出 竞价信息
         */
    }
}
