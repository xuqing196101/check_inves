package synchro.task.inner.exportTask;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;
import synchro.service.SynchRecordService;
import synchro.util.Constant;
import synchro.util.DateUtils;
import system.service.sms.SmsRecordTempService;

import common.constant.StaticVariables;

/**
 * 
 * Description: 内网短信定时导出任务
 * 
 * @version 2018年1月6日
 * @since JDK1.7
 */
@Component("smsRecordTempExport")
public class SmsExportTask {

    /** 记录service  **/
    @Autowired
    private SynchRecordService  recordService;
    
    @Autowired
    private SmsRecordTempService smsRecordTempService;
    
	/**
	 * 内网导出待发送短信记录
	 */
	
	public void smsExport(){
		//内网标志
		if("0".equals(StaticVariables.ipAddressType)){
			DictionaryData data = DictionaryDataUtil.get(Constant.DATE_SYNCH_SMS_RECORD_TEMP);
			if(data != null && StringUtils.isNotBlank(data.getId())){
				String startTime = recordService.getSynchTime(Constant.OPER_TYPE_EXPORT, data.getId());
				if (!StringUtils.isNotBlank(startTime)){
					startTime = DateUtils.getCurrentDate() + " 00:00:00";
				}
				startTime = DateUtils.getCalcelDate(startTime);
				String endTime = DateUtils.getCurrentTime();
				Date synchDate = DateUtils.stringToTime(endTime);
				smsRecordTempService.exportSmsRecordTemp(startTime, endTime, synchDate);
			}
		}
	}
}
