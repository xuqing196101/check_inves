package synchro.task.inner.exportTask;

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
 * Description: 内网定时导出专家抽取信息
 * 
 * @date 2017年11月29日
 * @since JDK1.7
 */
@Component("extractInfoExportTask")
public class ExtractInfoExportTask {

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
	 * Description: 内网导出专家抽取结果
	 * 
	 * @data 2017年10月19日
	 * @param 
	 * @return
	 */
	public void resultExport(){
		//内网标志
		if("0".equals(StaticVariables.ipAddressType)){
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

	/**
	 * 
	 * 
	 * Description: 内网导出专家抽取项目信息
	 * 
	 * @data 2017年11月29日
	 * @param 
	 * @return void
	 */
	public void infoExport(){
		//内网标识
		if("0".equals(StaticVariables.ipAddressType)){
			DictionaryData info = DictionaryDataUtil.get(Constant.DATE_SYNCH_EXPERT_EXTRACT);
			if(info != null && StringUtils.isNotBlank(info.getId())){
				String startTime = recordService.getSynchTime(Constant.OPER_TYPE_EXPORT, info.getId());
				if (!StringUtils.isNotBlank(startTime)){
					startTime = DateUtils.getCurrentDate() + " 00:00:00";
				}
				startTime = DateUtils.getCalcelDate(startTime);
				String endTime = DateUtils.getCurrentTime();
				Date synchDate = DateUtils.stringToTime(endTime);
				expertExtractProjectService.exportListExpertInfo(startTime, endTime, synchDate);
			}
		}
	}

}
