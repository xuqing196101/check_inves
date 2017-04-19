package synchro.task.inner.exportTask;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bss.service.ob.OBProductService;
import bss.service.ob.OBProjectServer;
import bss.service.ob.OBSpecialDateServer;
import bss.service.ob.OBSupplierService;

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
    /**同步 竞价定型产品**/
    @Autowired
    private OBProductService OBProductService;
    /**竞价供应商**/
    @Autowired
    private OBSupplierService OBSupplierService;
    /**竞价信息**/
    @Autowired
    private OBProjectServer OBProjectServer;
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
        /**内网导出 竞价信息**/
        /**竞价定型产品导出   只能是内网导出外网**/
        DictionaryData bidding_product = DictionaryDataUtil.get(Constant.DATE_SYNCH_BIDDING_PRODUCT);
        if(bidding_product!=null && StringUtils.isNotBlank(bidding_product.getId())){
        	 String startTime = recordService.getSynchTime(Constant.OPER_TYPE_EXPORT, bidding_product.getId());
        	 if (!StringUtils.isNotBlank(startTime)){
                 startTime = DateUtils.getCurrentDate() + " 00:00:00";
             }
             startTime = DateUtils.getCalcelDate(startTime);
             String endTime = DateUtils.getCurrentTime();
             Date synchDate = DateUtils.stringToTime(endTime);
        	OBProductService.exportProduct(startTime, endTime, synchDate);
        }
      //**竞价供应商导出  只能是内网导出外网**//*/
        DictionaryData bidding_supplier = DictionaryDataUtil.get(Constant.DATE_SYNCH_BIDDING_SUPPLIER);
        if(bidding_supplier!=null && StringUtils.isNotBlank(bidding_supplier.getId())){
        	 String startTime = recordService.getSynchTime(Constant.OPER_TYPE_EXPORT, bidding_supplier.getId());
        	 if (!StringUtils.isNotBlank(startTime)){
                 startTime = DateUtils.getCurrentDate() + " 00:00:00";
             }
             startTime = DateUtils.getCalcelDate(startTime);
             String endTime = DateUtils.getCurrentTime();
             Date synchDate = DateUtils.stringToTime(endTime);
         	OBSupplierService.exportSupplier(startTime, endTime, synchDate);
        }
      //**竞价信息导出  只能是内网导出外网**/
        DictionaryData bidding_code = DictionaryDataUtil.get(Constant.DATA_TYPE_BIDDING_CODE);
        if(bidding_code!=null && StringUtils.isNotBlank(bidding_code.getId())){
        	 String startTime = recordService.getSynchTime(Constant.OPER_TYPE_EXPORT, bidding_code.getId());
        	 if (!StringUtils.isNotBlank(startTime)){
                 startTime = DateUtils.getCurrentDate() + " 00:00:00";
             }
             startTime = DateUtils.getCalcelDate(startTime);
             String endTime = DateUtils.getCurrentTime();
             Date synchDate = DateUtils.stringToTime(endTime);
             OBProjectServer.exportProject(startTime, endTime, synchDate);
        }
    }
}
