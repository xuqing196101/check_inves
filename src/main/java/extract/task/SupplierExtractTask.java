package extract.task;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import synchro.inner.read.supplier.InnerSupplierService;
import synchro.outer.back.service.supplier.OuterSupplierService;
import synchro.service.SynchRecordService;
import synchro.util.Constant;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;
import extract.service.supplier.AutoExtractSupplierService;
import extract.util.DateUtils;


@Component("SupplierExtractTask")
public class SupplierExtractTask {

	/**
	 * 供应商自动抽取
	 */
	@Autowired
    private AutoExtractSupplierService autoExtractSupplierService;
	
	/**
     * 内网同步供应商数据service
     **/
	@Autowired
    private InnerSupplierService innerSupplierService;
	
	 /**
     * 外网同步供应商信息
     */
    @Autowired
    private OuterSupplierService outerSupplierService;
    
    /** 导入导出记录service  **/
    @Autowired
    private SynchRecordService  synchRecordService;
	
	 //获取是否内网标识 1外网 0内网
    private static final String ipAddressType = PropUtil.getProperty("ipAddressType");
	
	
	
	/**
	 * 
	 * 导出供应商等级 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-11-16上午11:46:19
	 */
	public void exportSupplierLevel() {
		if("0".equals(ipAddressType)){
			String prevOperateTime = getPrevOperateTime(1,Constant.DATE_SYNCH_SUPPLIER_LEVEL);
			innerSupplierService.selectSupplierLevelOfExport(prevOperateTime,common.utils.DateUtils.getCurrentTime());
		}
	}
	/**
	 * 
	 * 导入供应商等级 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-11-16上午11:46:19
	 */
	public void importSupplierLevel() {
		
		if("1".equals(ipAddressType)){
			File file = FileUtils.getImportFile();
			if (file != null && file.exists()) {
				File[] files = file.listFiles();
				for (File f : files) {
					if (f.getName().contains(Constant.SUPPLIER_LEVEL_FILE_NAME)) {
            			outerSupplierService.importSupplierLevel(f);
            		}
            		if (f.isDirectory()) {
            			if (f.getName().contains(Constant.SUPPLIER_LEVEL_FILE_NAME)) {
            				OperAttachment.moveFolder(f);
            			}
            		}
				}
			}
		}
		
	}
	/**
	 * 
	 * 导出供应商抽取结果
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-11-16上午11:46:19
	 */
	public void exportSupplierExtractResult() {
		if("1".equals(ipAddressType)){
			String prevOperateTime = getPrevOperateTime(1, Constant.DATE_SYNCH_SUPPLIER_EXTRACT_RESULT);
			autoExtractSupplierService.exportSupplierExtractResult(prevOperateTime,common.utils.DateUtils.getCurrentTime(), new Date());
		}
	}
	/**
	 * 
	 * 导入供应商抽取结果 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-11-16上午11:46:19
	 */
	public void importSupplierExtractResult() {
		if("0".equals(ipAddressType)){
			File file = FileUtils.getImportFile();
			if (file != null && file.exists()) {
				File[] files = file.listFiles();
				for (File f : files) {
					if (f.getName().contains(Constant.SUPPLIER_EXTRACT_RESULT_FILE_NAME)) {
						autoExtractSupplierService.importSupplierExtractResult(f);
					}
					if (f.isDirectory()) {
						if (f.getName().contains(Constant.SUPPLIER_EXTRACT_RESULT_FILE_NAME)) {
							OperAttachment.moveFolder(f);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * 导入供应商抽取信息
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-11-16上午11:46:19
	 */
	public void importSupplierExtractInfo() {
		File file = FileUtils.getImportFile();
		if (file != null && file.exists()) {
			File[] files = file.listFiles();
			for (File f : files) {
				if (f.getName().contains(Constant.SUPPLIER_EXTRACT_FILE_NAME)) {
		    		autoExtractSupplierService.importSupplierExtract(f);
		    	}
		    	if (f.isDirectory()) {
		    		if (f.getName().contains(Constant.SUPPLIER_EXTRACT_FILE_NAME)) {
		    			OperAttachment.moveFolder(f);
		    		}
		    	}
			}
		}
	}
	
	/**
	 * 
	 * 导出供应商抽取信息
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-11-16上午11:46:19
	 */
	public void exportSupplierExtractInfo() {
		if("1".equals(ipAddressType)){
			String prevOperateTime = getPrevOperateTime(1, Constant.DATE_SYNCH_SUPPLIER_EXTRACT_INFO);
			autoExtractSupplierService.exportExtractProjectInfo(prevOperateTime, common.utils.DateUtils.getCurrentTime(), new Date());
		}
	}
	
	
	/**
	 * 
	 * 定时任务，自动抽取
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-11-16上午11:46:19
	 */
	public void autoExtractSupplier() {
		if("1".equals(ipAddressType)){
			autoExtractSupplierService.selectAutoExtractProject(DateUtils.getYesterdayZeroTime(), new Date());
		}
	}
	
	private String getPrevOperateTime(int operaType,String code){
		//查询上次导入/出的时间
		String startTime = null;
		String id = DictionaryDataUtil.getId(code);
		if(StringUtils.isNotBlank(id)){
			String synchTime = synchRecordService.getSynchTime(operaType, id);
			if(StringUtils.isNotBlank(synchTime)){
				Date stringToTime = common.utils.DateUtils.stringToTime(synchTime);
				Date addDate = common.utils.DateUtils.getAddDate(stringToTime,-30);
				startTime = DateUtils.dateToString(addDate);
			}
			
		}
		return StringUtils.isNotBlank(startTime)?startTime:DateUtils.getTodayZeroTimeToString();
	}
	
}
