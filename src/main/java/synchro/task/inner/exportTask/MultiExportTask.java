package synchro.task.inner.exportTask;

import iss.service.ps.DataDownloadService;
import iss.service.ps.TemplateDownloadService;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import common.constant.StaticVariables;

import ses.service.bms.CategoryParameterService;
import ses.service.bms.CategoryService;
import ses.service.bms.QualificationService;
import ses.service.sms.SMSProductLibService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import synchro.service.SynchRecordService;
import synchro.util.Constant;
import synchro.util.DateUtils;
import synchro.util.MultiTaskUril;
import bss.service.ob.OBProductService;
import bss.service.ob.OBProjectServer;
import bss.service.ob.OBSupplierService;
/***
 * 定时处理多个内网需要导出的数据
 * @author YHL
 */
@Component("innerMultiExportTask")
public class MultiExportTask {
    /**同步 竞价定型产品**/
    @Autowired
    private OBProductService OBProductService;
    /**竞价供应商**/
    @Autowired
    private OBSupplierService OBSupplierService;
    /**竞价信息**/
    @Autowired
    private OBProjectServer OBProjectServer;
    /** 记录service  **/
    @Autowired
    private SynchRecordService  recordService;
    /**产品库**/
    @Autowired
    private SMSProductLibService smsProductLibService;
    /**产品目录**/
    @Autowired
    private CategoryService categoryService;
    /** 产品目录参数 **/
    @Autowired
    private CategoryParameterService categoryParameterService;
    /**资料 管理**/
    @Autowired
    private DataDownloadService dataDownloadService;
    /** 门户模板管理 **/
    @Autowired
    private TemplateDownloadService templateDownloadService;
    /**产品资质**/
    @Autowired
    private QualificationService qualificationService;
   /**
    * 内网定时处理方法
    */
	public void innerMultiExportTask(){
		//内网
		if("0".equals(StaticVariables.ipAddressType)){
		//网上竞价 导出
			 //**内网导出 竞价信息**//*
		        //**竞价定型产品导出   只能是内网导出外网**//*
		        String startTime= MultiTaskUril.getSynchDate(Constant.DATE_SYNCH_BIDDING_PRODUCT,recordService);
		        /*  if(startTime!=null ){
		        	  startTime = DateUtils.getCalcelDate(startTime);
		              String endTime = DateUtils.getCurrentTime();
		              Date synchDate = DateUtils.stringToTime(endTime);
		        	OBProductService.exportProduct(startTime, endTime, synchDate);
		        }
		      //竞价供应商导出  只能是内网导出外网//
		        startTime= getSynchDate(Constant.DATE_SYNCH_BIDDING_SUPPLIER);
		        if(startTime!=null){
		             startTime = DateUtils.getCalcelDate(startTime);
		             String endTime = DateUtils.getCurrentTime();
		             Date synchDate = DateUtils.stringToTime(endTime);
		         	OBSupplierService.exportSupplier(startTime, endTime, synchDate);
		        }
		      //竞价信息导出  只能是内网导出外网
		        startTime= getSynchDate(Constant.DATA_TYPE_BIDDING_CODE);
		        if(startTime!=null){
		        	 startTime = DateUtils.getCalcelDate(startTime);
		             String endTime = DateUtils.getCurrentTime();
		             Date synchDate = DateUtils.stringToTime(endTime);
		             OBProjectServer.exportProject(startTime, endTime, synchDate);
		        }*/		
		//产品库管理
			/*	startTime = MultiTaskUril.getSynchDate(Constant.SYNCH_PRODUCT_LIBRARY,recordService);
		        if(StringUtils.isNotBlank(startTime)){
		             startTime = DateUtils.getCalcelDate(startTime);
		             String endTime = DateUtils.getCurrentTime();
		             Date synchDate = DateUtils.stringToTime(endTime);
		        	*//**产品库管理导出 0内网**//*
		        	//内网时 导出 产品库审核的 相关数据	
		        	smsProductLibService.exportCheckProjectData(startTime, endTime, synchDate);
		        }*/
		//产品目录 导出 数据
				startTime  = MultiTaskUril.getSynchDate(Constant.SYNCH_CATEGORY,recordService);
		        if(StringUtils.isNotBlank(startTime)){
		        	
		             startTime = DateUtils.getCalcelDate(startTime);
		             String endTime = DateUtils.getCurrentTime();
		             Date synchDate = DateUtils.stringToTime(endTime);
		        	//产品目录 导出 数据
		        	categoryService.exportCategory(startTime, endTime, synchDate);
			   }
		//产品目录参数 导出数据
				 startTime = MultiTaskUril.getSynchDate(Constant.SYNCH_CATE_PARAMTER,recordService);
		        if(StringUtils.isNotBlank(startTime)){
		             startTime = DateUtils.getCalcelDate(startTime);
		             String endTime = DateUtils.getCurrentTime();
		             Date synchDate = DateUtils.stringToTime(endTime);
		        	//产品目录参数 导出数据
		        	categoryParameterService.exportCategoryParamter(startTime, endTime, synchDate);
		        }
		        //同步 目录资质关联表
		        startTime = MultiTaskUril.getSynchDate(Constant.DATA_SYNCH_CATEGORY_QUA,recordService);
		        if(StringUtils.isNotBlank(startTime)){
		        	 startTime = DateUtils.getCalcelDate(startTime);
		             String endTime = DateUtils.getCurrentTime();
		             Date synchDate = DateUtils.stringToTime(endTime);
		        	//门户模板管理 导出数据
		        	categoryService.exportCategoryQua(startTime, endTime, synchDate);
		        }
		        /**同步  产品资质表**/
		        startTime = MultiTaskUril.getSynchDate(Constant.DATA_SYNCH_QUALIFICATION,recordService);
		        if(StringUtils.isNotBlank(startTime)){
		        	 startTime = DateUtils.getCalcelDate(startTime);
		             String endTime = DateUtils.getCurrentTime();
		             Date synchDate = DateUtils.stringToTime(endTime);
		        	//门户模板管理 导出数据
		        	qualificationService.exportQualification(startTime, endTime, synchDate);
		        }
		 /* //资料 管理 导出
				 startTime = getSynchDate(Constant.SYNCH_DATA);
		        if(StringUtils.isNotBlank(startTime)){
		             startTime = DateUtils.getCalcelDate(startTime);
		             String endTime = DateUtils.getCurrentTime();
		             Date synchDate = DateUtils.stringToTime(endTime);
			        	//资料 管理 导出
			        	dataDownloadService.exportData(startTime, endTime, synchDate);
			        }*/
		/*//门户模板管理 导出数据
				 startTime = getSynchDate(Constant.SYNCH_TEMPLATE_DOWNLOAD);
		        if(StringUtils.isNotBlank(startTime)){
		             startTime = DateUtils.getCalcelDate(startTime);
		             String endTime = DateUtils.getCurrentTime();
		             Date synchDate = DateUtils.stringToTime(endTime);
		        	//门户模板管理 导出数据
		        	templateDownloadService.exportTemplateDownload(startTime, endTime, synchDate);
		        }*/
		}
	}
}
