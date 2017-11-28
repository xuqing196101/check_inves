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
import extract.service.expert.ExpertExtractProjectService;

/**
 * 
 * Description: 外网定时导出抽取结果
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
@Component("expertExtractResultExportTask")
public class ExpertExtractResultExportTask {

	/**
	 * 专家抽取信息
	 */
	@Autowired
	private ExpertExtractProjectService expertExtractProjectService;
	
    /** 记录service  **/
    @Autowired
    private SynchRecordService  recordService;
	
	/**
	 * 
	 * Description: 外网导出抽取结果
	 * 
	 * @author zhang shubin
	 * @data 2017年10月19日
	 * @param 
	 * @return
	 */
	public void resultExport(){
		//外网
		if ("1".equals(StaticVariables.ipAddressType)) {
			DictionaryData extractResult = DictionaryDataUtil.get(Constant.DATE_SYNCH_EXPERT_EXTRACT_RESULT);
	        if(extractResult != null && StringUtils.isNotBlank(extractResult.getId())){
	        	 String startTime = recordService.getSynchTime(Constant.OPER_TYPE_EXPORT, extractResult.getId());
	        	 if (!StringUtils.isNotBlank(startTime)){
	                 startTime = DateUtils.getCurrentDate() + " 00:00:00";
	             }
	             startTime = DateUtils.getCalcelDate(startTime);
	             String endTime = DateUtils.getCurrentTime();
	             Date synchDate = DateUtils.stringToTime(endTime);
	             expertExtractProjectService.exportExpertExtractResult(startTime, endTime, synchDate);
	        }
		}
	}
}
