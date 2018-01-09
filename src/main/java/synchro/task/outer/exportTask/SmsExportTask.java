package synchro.task.outer.exportTask;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import common.constant.StaticVariables;

import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;
import synchro.service.SynchRecordService;
import synchro.util.Constant;
import synchro.util.DateUtils;
import system.service.sms.SmsRecordService;

/**
 * 
 * Description: 外网导出短信发送记录
 * 
 * @version 2018年1月6日
 * @since JDK1.7
 */
@Component("smsRecordExport")
public class SmsExportTask {

    /** 记录service  **/
    @Autowired
    private SynchRecordService  recordService;
    
    @Autowired
    private SmsRecordService smsRecordService;
    
	/**
	 * 
	 * Description: 外网导出短信发送记录
	 * 
	 * @data 2018年1月6日
	 * @param 
	 * @return
	 */
	public void smsExport(){
		if ("1".equals(StaticVariables.ipAddressType)) {
			/** 外网导出 **/
			DictionaryData data = DictionaryDataUtil.get(Constant.DATE_SYNCH_SMS_RECORD);
			if(data != null && StringUtils.isNotBlank(data.getId())){
				String startTime = recordService.getSynchTime(Constant.OPER_TYPE_EXPORT, data.getId());
				if (!StringUtils.isNotBlank(startTime)){
					startTime = DateUtils.getCurrentDate() + " 00:00:00";
				}
				startTime = DateUtils.getCalcelDate(startTime);
				String endTime = DateUtils.getCurrentTime();
				Date synchDate = DateUtils.stringToTime(endTime);
				smsRecordService.exportSmsRecord(startTime, endTime, synchDate);
			}
		}
	}
}