package synchro.task.outer.exportTask;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;
import synchro.service.SynchRecordService;
import synchro.util.Constant;
import synchro.util.DateUtils;

import common.constant.StaticVariables;

import extract.service.supplier.AutoExtractSupplierService;

//@Component("expertExtractResultExportTask")
public class SupplierExtractResultExport {

	/** 记录service  **/
    @Autowired
    private SynchRecordService  recordService;
    @Autowired
    private AutoExtractSupplierService autoExtractSupplierService;
    
    public void resultExport(){
		//外网
		if ("1".equals(StaticVariables.ipAddressType)) {
			DictionaryData extractResult = DictionaryDataUtil.get(Constant.DATE_SYNCH_SUPPLIER_EXTRACT_RESULT);
	        if(extractResult != null && StringUtils.isNotBlank(extractResult.getId())){
	        	 String startTime = recordService.getSynchTime(Constant.OPER_TYPE_EXPORT, extractResult.getId());
	        	 if (!StringUtils.isNotBlank(startTime)){
	                 startTime = DateUtils.getCurrentDate() + " 00:00:00";
	             }
	             startTime = DateUtils.getCalcelDate(startTime);
	             String endTime = DateUtils.getCurrentTime();
	             Date synchDate = DateUtils.stringToTime(endTime);
	             autoExtractSupplierService.exportSupplierExtractResult(startTime, endTime, synchDate);
	        }
		}
	}
}
