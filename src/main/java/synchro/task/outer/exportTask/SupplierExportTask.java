package synchro.task.outer.exportTask;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;
import synchro.outer.back.service.supplier.OuterSupplierService;
import synchro.service.SynchRecordService;
import synchro.util.Constant;
import synchro.util.DateUtils;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>供应商导出
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Component("outerSupplierExportTask")
public class SupplierExportTask {
    
    /** 供应商service **/
    @Autowired
    private OuterSupplierService supplierService;
    
    /** 记录service  **/
    @Autowired
    private SynchRecordService  recordService;
    
    
    /**
     * 
     *〈简述〉导出供应商信息
     *〈详细描述〉
     * @author myc
     */
    public void outerSupplierExportTask(){
        DictionaryData dd = DictionaryDataUtil.get(Constant.DATA_TYPE_SUPPLIER_CODE);
        if (dd != null && StringUtils.isNotBlank(dd.getId())){
            String startTime = recordService.getSynchTime(Constant.OPER_TYPE_EXPORT, dd.getId());
            if (!StringUtils.isNotBlank(startTime)){
                startTime = DateUtils.getCurrentDate() + " 00:00:00";
            }
            startTime = DateUtils.getCalcelDate(startTime);
            String endTime = DateUtils.getCurrentTime();
            Date synchDate = DateUtils.stringToTime(endTime);
            supplierService.exportCommitSupplier(startTime, endTime, synchDate);
        }
    }
}
