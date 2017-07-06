package synchro.util;

import org.apache.commons.lang3.StringUtils;

import ses.util.DictionaryDataUtil;
import synchro.service.SynchRecordService;

public class MultiTaskUril {
	//封装    获取 导出 时间
    public static String getSynchDate(String type,SynchRecordService recordService){
			String startTime=null;
			String bidding_code = DictionaryDataUtil.getId(type);
	        if(StringUtils.isNotBlank(bidding_code)){
	          startTime = recordService.getSynchTime(Constant.OPER_TYPE_EXPORT,bidding_code);
	          if (!StringUtils.isNotBlank(startTime)){
	                 startTime = DateUtils.getCurrentDate() + " 00:00:00";
	          }
	        }
	      return startTime;
	    }
}
