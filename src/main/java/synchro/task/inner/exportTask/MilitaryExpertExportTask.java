package synchro.task.inner.exportTask;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import common.constant.StaticVariables;
import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;
import synchro.service.SynchMilitaryExpertService;
import synchro.service.SynchRecordService;
import synchro.util.Constant;
import synchro.util.DateUtils;

/**
 * 
 * Description: 内网 定时导出军队专家
 * 
 * @version 2016-9-7
 * @since JDK1.7
 */
@Component("militaryExpertExportTask")
public class MilitaryExpertExportTask {

	/**
	 * 专家同步
	 */
	@Autowired
	private SynchMilitaryExpertService synchMilitaryExpertService;
	
	/** 记录service  **/
    @Autowired
    private SynchRecordService  recordService;
    
	/**
	 * 
	 * Description: 军队专家导出
	 * 
	 * @data 2017年10月20日
	 * @param 
	 * @return
	 */
	public void militaryExpertExport(){
		//内网
		if ("0".equals(StaticVariables.ipAddressType)) {
			DictionaryData extractResult = DictionaryDataUtil.get(Constant.DATE_SYNCH_MILITARY_EXPERT);
	        if(extractResult != null && StringUtils.isNotBlank(extractResult.getId())){
	        	 String startTime = recordService.getSynchTime(Constant.OPER_TYPE_EXPORT, extractResult.getId());
	        	 if (!StringUtils.isNotBlank(startTime)){
	                 startTime = DateUtils.getCurrentDate() + " 00:00:00";
	             }
	             startTime = DateUtils.getCalcelDate(startTime);
	             String endTime = DateUtils.getCurrentTime();
	             Date synchDate = DateUtils.stringToTime(endTime);
	             synchMilitaryExpertService.militaryExpertExport(startTime, endTime, synchDate);
	        }
		}
	}
}
