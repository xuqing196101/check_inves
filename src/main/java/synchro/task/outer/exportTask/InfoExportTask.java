package synchro.task.outer.exportTask;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bss.service.ob.OBProjectServer;

import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;
import synchro.outer.back.service.infos.OuterInfoExportService;
import synchro.service.SynchRecordService;
import synchro.util.Constant;
import synchro.util.DateUtils;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 外网信息导出任务
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Component("outerInfoExportTask")
public class InfoExportTask {

    /** 同步信息service **/
    @Autowired
    private OuterInfoExportService infoService;
    
    /** 记录service  **/
    @Autowired
    private SynchRecordService  recordService;
    /**竞价信息**/
    @Autowired
    private OBProjectServer OBProjectServer;
    /**
     * 
     *〈简述〉定时任务执行时间
     *〈详细描述〉
     * @author myc
     */
    public void outerInfoExportTask(){
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
        
        /**竞价结果导出  只能是外网导出内网**/
        DictionaryData bidding_result_code = DictionaryDataUtil.get(Constant.DATA_TYPE_BIDDING_RESULT_CODE);
        if(bidding_result_code!=null && StringUtils.isNotBlank(bidding_result_code.getId())){
        	 String startTime = recordService.getSynchTime(Constant.OPER_TYPE_EXPORT, bidding_result_code.getId());
        	 if (!StringUtils.isNotBlank(startTime)){
                 startTime = DateUtils.getCurrentDate() + " 00:00:00";
             }
             startTime = DateUtils.getCalcelDate(startTime);
             String endTime = DateUtils.getCurrentTime();
             Date synchDate = DateUtils.stringToTime(endTime);
             OBProjectServer.exportProjectResult(startTime, endTime, synchDate);
        }
    }
}
