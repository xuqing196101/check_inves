package synchro.task.outer.exportTask;

import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import bss.service.ob.OBProjectServer;
import ses.service.sms.SMSProductLibService;
import ses.util.DictionaryDataUtil;
import synchro.service.SynchRecordService;
import synchro.util.Constant;
import synchro.util.DateUtils;
import synchro.util.MultiTaskUril;

import common.constant.StaticVariables;
/**
 * 定时  外网  导出多个
 * @author YHL
 *
 */
@Component("outerMultiExportTask")
public class MultiExportTask {
    /**竞价信息**/
    @Autowired
    private OBProjectServer OBProjectServer;
    /** 记录service  **/
    @Autowired
    private SynchRecordService  recordService;
    /**产品库**/
    @Autowired
    private SMSProductLibService smsProductLibService;
  /***
   * 实现 定时导出数据
   */
	public void exprotTask(){
		//外网
	   if("1".equals(StaticVariables.ipAddressType)){
		    /**竞价结果导出  只能是外网导出内网**/
		   String startTime= MultiTaskUril.getSynchDate(Constant.DATA_TYPE_BIDDING_RESULT_CODE,recordService);
	        if(startTime!=null ){
	           startTime = DateUtils.getCalcelDate(startTime);
	           String endTime = DateUtils.getCurrentTime();
	           Date synchDate = DateUtils.stringToTime(endTime);
	           /**竞价结果导出  只能是外网导出内网**/
		       OBProjectServer.exportProjectResult(startTime, endTime, synchDate);
	        }
	        startTime= MultiTaskUril.getSynchDate(Constant.SYNCH_PRODUCT_LIBRARY,recordService);
	        if(startTime!=null ){
	           startTime = DateUtils.getCalcelDate(startTime);
	           String endTime = DateUtils.getCurrentTime();
	           Date synchDate = DateUtils.stringToTime(endTime);
	           /**产品库管理导出  只能是外网导出内网**/
	           smsProductLibService.exportAddProjectData(startTime, endTime, synchDate);
	        }
	   }
	}
}
