package synchro.controller;

import iss.service.hl.ServiceHotlineService;
import iss.service.ps.DataDownloadService;
import iss.service.ps.TemplateDownloadService;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.service.bms.CategoryParameterService;
import ses.service.bms.CategoryService;
import ses.service.bms.QualificationService;
import ses.service.ems.ExpertBlackListService;
import ses.service.sms.SMSProductLibService;
import ses.service.sms.SupplierBlacklistService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import sums.service.oc.ComplaintService;
import synchro.inner.back.service.infos.InnerInfoExportService;
import synchro.model.SynchRecord;
import synchro.outer.back.service.expert.OuterExpertService;
import synchro.outer.back.service.supplier.OuterSupplierService;
import synchro.service.SynchRecordService;
import synchro.service.SynchService;
import synchro.util.Constant;
import bss.service.ob.OBProductService;
import bss.service.ob.OBProjectServer;
import bss.service.ob.OBSupplierService;

import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;
import common.bean.ResponseBean;

import extract.service.expert.ExpertExtractProjectService;


/**
 * 版权：(C) 版权所有
 * <简述>数据导出
 * <详细描述>
 *
 * @author myc
 * @see
 */
@Controller
@RequestMapping("/synchExport")
public class SynchExportController {

    /**
     * 同步控制层service
     **/
    @Autowired
    private SynchService synchService;

    /**
     * 同步信息数据service
     **/
    @Autowired
    private InnerInfoExportService infoService;

    /**
     * 同步供应商数据service
     **/
    @Autowired
    private OuterSupplierService outerSupplierService;

    /**
     * 供应商名录
     **/
    @Autowired
    private SupplierService supplierService;
    /**
     * 记录service
     **/
    @Autowired
    private SynchRecordService recordService;
    /**
     * 同步 竞价定型产品
     **/
    @Autowired
    private OBProductService OBProductService;
    /**竞价特殊日期**/
   /* @Autowired
    private OBSpecialDateServer OBSpecialDateServer;*/
    /**
     * 竞价供应商
     **/
    @Autowired
    private OBSupplierService OBSupplierService;
    /**
     * 竞价信息
     **/
    @Autowired
    private OBProjectServer OBProjectServer;
    @Autowired
    private OuterExpertService outerExpertService;
    /**
     * 产品库
     **/
    @Autowired
    private SMSProductLibService smsProductLibService;
    /**
     * 设置数据类型
     **/
    private static final Integer DATA_TYPE_KIND = 29;
    /**
     * 产品目录
     **/
    @Autowired
    private CategoryService categoryService;
    /**
     * 产品目录参数
     **/
    @Autowired
    private CategoryParameterService categoryParameterService;
    /**
     * 资料 管理
     **/
    @Autowired
    private DataDownloadService dataDownloadService;
    /**
     * 门户模板管理
     **/
    @Autowired
    private TemplateDownloadService templateDownloadService;
    /**
     * 产品资质
     **/
    @Autowired
    private QualificationService qualificationService;

    /**
     * 网上投诉信息
     **/
    @Autowired
    private ComplaintService complaintService;

    /**
     * 供应商黑名单
     **/
    @Autowired
    private SupplierBlacklistService supplierBlacklistService;

    /**
     * 专家黑名单
     **/
    @Autowired
    private ExpertBlackListService expertBlackListService;

    /**
     * 服务热线
     **/
    @Autowired
	private ServiceHotlineService serviceHotlineService;

    /**
     * 〈简述〉初始化导出
     * 〈详细描述〉
     *
     * @param model
     * @param request {@link HttpServletRequest}
     * @return
     * @author myc
     */
    @RequestMapping("/initExport")
    public String initExport(@CurrentUser User user, Model model, HttpServletRequest request) {
        //声明标识是否是资源服务中心
        String authType = null;
        if (null != user && "4".equals(user.getTypeName())) {
            //判断是否 是资源服务中心 
            authType = "4";
            model.addAttribute("authType", authType);
	        model.addAttribute("operType", Constant.OPER_TYPE_EXPORT);
	        List<DictionaryData> list =DictionaryDataUtil.find(DATA_TYPE_KIND);
	        //获取是否内网标识 1外网 0内网
	        String ipAddressType= PropUtil.getProperty("ipAddressType");
	        //过滤附件类型
	        Iterator<DictionaryData> iter=list.iterator();
	        while (iter.hasNext()) {
	    	  DictionaryData dd=iter.next();
	    	  if (dd.getCode().equals(Constant.DATA_TYPE_ATTACH_CODE)){
	    		  iter.remove();
	              continue;
	          }
	          // 过滤专家抽取信息 定时任务自动导入导出
	          if (dd.getCode().equals(Constant.DATE_SYNCH_EXPERT_EXTRACT)) {
	              iter.remove();
	              continue;
	          }
	          if (dd.getCode().equals(Constant.DATE_SYNCH_EXPERT_EXTRACT_RESULT)) {
	              iter.remove();
	              continue;
	          }
	          // 过滤军队专家信息
	          if (dd.getCode().equals(Constant.DATE_SYNCH_MILITARY_EXPERT)) {
	              iter.remove();
	              continue;
	          }
	          //外网时
	          if(ipAddressType.equals("1")){
	       	   //过滤外网导出  	竞价定型产品导出  只能是内网导出外网
	       	   if (dd.getCode().equals(Constant.DATE_SYNCH_BIDDING_PRODUCT)){
	       		  iter.remove();
	       		   continue;
	              } 
	       	   //竞价供应商导出  只能是内网导出外网
	       	   if(dd.getCode().equals(Constant.DATE_SYNCH_BIDDING_SUPPLIER)){
	       		 iter.remove();
	       		   continue;
	              }
	              if(dd.getCode().equals(Constant.DATA_TYPE_BIDDING_CODE)){
	              	/**竞价信息导出  只能是内网导出外网**/
	            	iter.remove();
	           	   continue;
	              }
	              if(dd.getCode().equals(Constant.SYNCH_CATEGORY)){
	              	/**产品目录管理 数据导出  只能是内网导出外网**/
	               iter.remove();
	           	   continue;
	              }
	              if(dd.getCode().equals(Constant.SYNCH_CATE_PARAMTER)){
	            	  /**产品目录参数管理 数据导出  只能是内网导出外网**/
	            	  iter.remove();
	            	  continue;
	              }
	              if(dd.getCode().equals(Constant.SYNCH_DATA)){
	                	/**资料管理 数据导出  只能是内网导出外网**/
	                 iter.remove();
	             	   continue;
	                }
	              if(dd.getCode().equals(Constant.SYNCH_TEMPLATE_DOWNLOAD)){
	            	  /**门户模板管理 数据导出  只能是内网导出外网**/
	            	  iter.remove();
	            	  continue;
	              }
	              if(dd.getCode().equals(Constant.DATA_SYNCH_CATEGORY_QUA)){
	            	  /**目录资质关联表  只能是内网导出外网**/
	            	  iter.remove();
	            	  continue;
	              }
	              if(dd.getCode().equals(Constant.DATA_SYNCH_QUALIFICATION)){
	            	  /**产品资质表  只能是内网导出外网**/
	            	  iter.remove();
	            	  continue;
	              }
	          }
	          //内网时
	          if(ipAddressType.equals("0")){
	              if(dd.getCode().equals(Constant.DATA_TYPE_BIDDING_RESULT_CODE)){
	              	/**竞价结果导出  只能是外网导出内网**/
	               iter.remove();
	           	   continue;
	              }
	             
	            }
	     	}
	       
	       model.addAttribute("dataTypeList", list);
	       
	       //获取最近一次同步时间,作为手动同步的开始时间的默认值
	       DictionaryData dd = DictionaryDataUtil.get(Constant.DATA_TYPE_INFOS_CODE);
	       if (dd != null && StringUtils.isNotBlank(dd.getId())){
	           String startTime = recordService.getSynchTime(Constant.OPER_TYPE_EXPORT, dd.getId());
	           model.addAttribute("startTime", startTime);
	       }
        }
        return "/synch/export";
    }

    /**
     * 〈简述〉数据同步
     * 〈详细描述〉
     *
     * @param operType 操作类型
     * @return
     * @author myc
     */
    @ResponseBody
    @RequestMapping(value = "/list", produces = "application/json;charset=UTF-8")
    public ResponseBean list(@CurrentUser User user, Integer operType, Integer page, String searchType, String startTime, String endTime) {
        //声明标识是否是资源服务中心
        if (null != user && "4".equals(user.getTypeName())) {
            //判断是否 是资源服务中心 
            ResponseBean bean = new ResponseBean();
            if (operType != null) {
                bean.setSuccess(true);
                List<SynchRecord> list = synchService.getList(operType, page, searchType, startTime, endTime);
                PageInfo<SynchRecord> pageInfo = new PageInfo<SynchRecord>(list);
                if (pageInfo.getList() != null && pageInfo.getList().size() > 0) {
                    pageInfo.setList(packageSynchRecord(pageInfo.getList()));
                }
                bean.setObj(pageInfo);
            } else {
                bean.setSuccess(false);
            }
            return bean;
        }
        return new ResponseBean();
    }

    /**
     * 〈简述〉数据导出
     * 〈详细描述〉
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @author myc
     */
    @ResponseBody
    @RequestMapping(value = "/dataExport")
    public ResponseBean dataExport(@CurrentUser User user, String startTime, String endTime, String synchType) {
        //声明标识是否是资源服务中心
        if (null != user && "4".equals(user.getTypeName())) {
            //判断是否 是资源服务中心 
            ResponseBean bean = new ResponseBean();
            if (StringUtils.isEmpty(synchType)) {
                bean.setSuccess(false);
                return bean;
            }
            //获取是否内网标识 1外网 0内网
            String ipAddressType = PropUtil.getProperty("ipAddressType");
            Date date = new Date();
            if (synchType.contains(Constant.DATA_TYPE_INFOS_CODE)) {
                infoService.backUpInfos(startTime, endTime, date);
            }
            /**供应商提交*/
            if (synchType.contains(Constant.DATA_TYPE_SUPPLIER_CODE)) {
                outerSupplierService.exportCommitSupplier(startTime, endTime, date);
            }
            /**内网退回修改*/
            if (synchType.contains("inner_out")) {
                outerSupplierService.auditPass(startTime, endTime);
            }
            /**外网供应商退回修改重新提交*/
            if (synchType.contains("back_out")) {
                //            outerSupplierService.exportCommitSupplier(startTime, endTime,date);
            }
            /**临时供应商导入导出*/
            if (synchType.contains("temp_out")) {
                outerSupplierService.tempSupplier(startTime, endTime);
            }


            /**专家内网，外网数据导出*/
            if (synchType.contains(Constant.DATA_TYPE_EXPERT_CODE)) {
                outerExpertService.backupCreated(startTime, endTime);
            }
            /**内网专家退回修改*/
            if (synchType.contains("expert_out")) {
                outerExpertService.backModifyExpert(startTime, endTime);
            }


            if (synchType.contains(Constant.DATE_SYNCH_BIDDING_PRODUCT)) {
                /**竞价定型产品导出  只能是内网导出外网**/
                OBProductService.exportProduct(startTime, endTime, date);
            }
           /* if(synchType.contains(Constant.DATE_SYNCH_BIDDING_SPECIAL_DATE)){
	        	/**竞价特殊日期导出  只能是内网导出外网**//*
	        	OBSpecialDateServer.exportSpecialDate(startTime, endTime, date);
	        }*/
            if (synchType.contains(Constant.DATE_SYNCH_BIDDING_SUPPLIER)) {
                /**竞价供应商导出  只能是内网导出外网**/
                OBSupplierService.exportSupplier(startTime, endTime, date);
            }
            if (synchType.contains(Constant.DATA_TYPE_BIDDING_CODE)) {
                /**竞价信息导出  只能是内网导出外网**/
                OBProjectServer.exportProject(startTime, endTime, date);
            }
            if (synchType.contains(Constant.DATA_TYPE_BIDDING_RESULT_CODE)) {
                /**竞价结果导出  只能是外网导出内网**/
                OBProjectServer.exportProjectResult(startTime, endTime, date);
            }
            if (synchType.contains(Constant.SYNCH_PRODUCT_LIBRARY)) {
                /**产品库管理导出  只能是外网导出内网   1外网 0内网**/
                if (ipAddressType.equals("1")) {
                    //外网时 到出 产品库录入的未审核的数据
                    smsProductLibService.exportAddProjectData(startTime, endTime, date);
                } else {
                    //内网时 导出 产品库审核的 相关数据
                    smsProductLibService.exportCheckProjectData(startTime, endTime, date);
                }
            }
            if (synchType.contains(Constant.SYNCH_CATEGORY)) {
                //产品目录 导出 数据
                categoryService.exportCategory(startTime, endTime, date);
            }
            if (synchType.contains(Constant.SYNCH_CATE_PARAMTER)) {
                //产品目录参数 导出数据
                categoryParameterService.exportCategoryParamter(startTime, endTime, date);
            }
            if (synchType.contains(Constant.SYNCH_DATA)) {
                //资料 管理 导出
                dataDownloadService.exportData(startTime, endTime, date);
            }
            if (synchType.contains(Constant.SYNCH_TEMPLATE_DOWNLOAD)) {
                //门户模板管理 导出数据
                templateDownloadService.exportTemplateDownload(startTime, endTime, date);
            }
            //图片导出
            if (synchType.contains("img_out")) {
                synchService.imageHandler();
            }
            /**同步 目录资质关联表**/
            if (synchType.contains(Constant.DATA_SYNCH_CATEGORY_QUA)) {
                //门户模板管理 导出数据
                categoryService.exportCategoryQua(startTime, endTime, date);
            }
            /**同步  产品资质表**/
            if (synchType.contains(Constant.DATA_SYNCH_QUALIFICATION)) {
                //门户模板管理 导出数据
                qualificationService.exportQualification(startTime, endTime, date);
            }
            /**内网公示供应商导出*/
            if (synchType.contains(Constant.SYNCH_PUBLICITY_SUPPLIER)) {
                outerSupplierService.selectSupByPublictyOfExport(startTime, endTime);
            }

            /**内网供应商注销导出*/
            if (synchType.contains(Constant.SYNCH_LOGOUT_SUPPLIER)) {
                outerSupplierService.selectLogoutSupplierOfExport(startTime, endTime);
            }

            /**内网公示专家导出*/
            if (synchType.contains(Constant.SYNCH_PUBLICITY_EXPERT)) {
                outerExpertService.selectExpByPublictyOfExport(startTime, endTime);
            }

            /**网上投诉信息导出*/
            if (synchType.contains(Constant.DATE_SYNCH_ONLINE_COMPLAINTS)) {
                complaintService.exportComplaintService(startTime, endTime, date);
            }

            /**供应商黑名单信息导出*/
            if (synchType.contains(Constant.DATE_SYNCH_SUPPLIER_BLACKLIST)) {
                supplierBlacklistService.exportSupplierBlacklist(startTime, endTime, date);
            }

	        /**网上投诉信息导出*/
	        if (synchType.contains(Constant.DATE_SYNCH_ONLINE_COMPLAINTS)) {
	          complaintService.exportComplaintService(startTime, endTime,date);
	        }
	        
	        /**供应商黑名单信息导出*/
	        if (synchType.contains(Constant.DATE_SYNCH_SUPPLIER_BLACKLIST)) {
	          supplierBlacklistService.exportSupplierBlacklist(startTime, endTime,date);
	        }
	        
	        /**专家黑名单信息导出*/
	        if (synchType.contains(Constant.DATE_SYNCH_EXPERT_BLACKLIST)) {
	          expertBlackListService.exportExpertBlacklist(startTime, endTime,date);
	        } 
	        
	        /** 服务热线信息导出*/
	        if (synchType.contains(Constant.DATE_SYNCH_HOT_LINE)) {
	        	serviceHotlineService.exportHotLine(startTime, endTime,date);
	        } 
	        bean.setSuccess(true);
	        return bean;
        }
        return new ResponseBean();
    }

    /**
     * 〈简述〉封装为带有类型名称的集合
     * 〈详细描述〉
     *
     * @param list 数据list
     * @return 组装后的list
     * @author myc
     */
    private List<SynchRecord> packageSynchRecord(List<SynchRecord> list) {
        List<SynchRecord> dataList = new ArrayList<>();
        for (SynchRecord synchRecord : list) {
            if (StringUtils.isNotBlank(synchRecord.getDataType())) {
                DictionaryData dd = DictionaryDataUtil.findById(synchRecord.getDataType());
                if (dd != null && StringUtils.isNotBlank(dd.getName())) {
                    synchRecord.setDataTypeName(dd.getName());
                }
            }
            dataList.add(synchRecord);
        }
        return dataList;
    }
}
