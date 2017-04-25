package synchro.controller;

import java.io.File;
import java.util.ArrayList;
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
import ses.service.sms.SMSProductLibService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import synchro.inner.read.expert.InnerExpertService;
import synchro.inner.read.supplier.InnerSupplierService;
import synchro.model.SynchRecord;
import synchro.outer.read.att.OuterAttachService;
import synchro.outer.read.infos.OuterInfoImportService;
import synchro.service.SynchService;
import synchro.util.Constant;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;
import bss.service.ob.OBProductService;
import bss.service.ob.OBProjectServer;
import bss.service.ob.OBSupplierService;

import com.github.pagehelper.PageInfo;

import common.bean.ResponseBean;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>数据导入
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Controller
@RequestMapping("/synchImport")
public class SynchImportController {
    
    /** 同步控制层service **/
    @Autowired
    private SynchService synchService;
    
    /** 同步信息数据service **/
    @Autowired
    private OuterInfoImportService infoService;
    
    @Autowired
    /** 附件导入 **/
    private OuterAttachService attachService;
    /**同步 竞价定型产品**/
    @Autowired
    private OBProductService OBProductService;
    /**竞价供应商**/
    @Autowired
    private OBSupplierService OBSupplierService;
    /**竞价信息**/
    @Autowired
    private OBProjectServer OBProjectServer;
    /** 同步供应商数据service **/
    @Autowired
    private InnerSupplierService innerSupplierService;
    /**产品库**/
    @Autowired
    private SMSProductLibService smsProductLibService;
   
    @Autowired
    private InnerExpertService innerExpertService;
    /** 设置数据类型 **/
    private static final Integer DATA_TYPE_KIND = 29;
    
    
    /**
     * 
     *〈简述〉初始化导入
     *〈详细描述〉
     * @author myc
     * @param model
     * @param request {@link HttpServletRequest}
     * @return
     */
    @RequestMapping("/initImport")
    public String initImport(Model model, HttpServletRequest request){
       model.addAttribute("operType", Constant.OPER_TYPE_IMPORT);
       List<DictionaryData> list =DictionaryDataUtil.find(DATA_TYPE_KIND);
       //获取是否内网标识 1外网 0内网
       String ipAddressType= PropUtil.getProperty("ipAddressType");
       //过滤附件类型
       Iterator<DictionaryData> iter= list.iterator();
       while (iter.hasNext()) {
		DictionaryData dd = (DictionaryData) iter.next();
		if (dd.getCode().equals(Constant.DATA_TYPE_ATTACH_CODE)){
			iter.remove();
            continue;
        }
 	   //内网时
        if(ipAddressType.equals("0")){
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
        }
          //外网时   
        if(ipAddressType.equals("1")){
            if(dd.getCode().equals(Constant.DATA_TYPE_BIDDING_RESULT_CODE)){
            	/**竞价结果导出  只能是外网导出内网**/
            	iter.remove();
                continue;
            }
          }
	  }
       model.addAttribute("dataTypeList", list);
       return "/synch/import";
    }
    
    /**
     * 
     *〈简述〉数据同步
     *〈详细描述〉
     * @author myc
     * @param operType 操作类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/list", produces="application/json;charset=UTF-8")
    public ResponseBean list(Integer operType,Integer page , String searchType , String startTime, String endTime){
        
        ResponseBean bean = new ResponseBean();
        if (operType != null){
            bean.setSuccess(true);
            List<SynchRecord> list = synchService.getList(operType,page,searchType, startTime, endTime);
            PageInfo<SynchRecord> pageInfo = new PageInfo<SynchRecord> (list);
            if (pageInfo.getList() != null && pageInfo.getList().size() > 0){
                pageInfo.setList(packageSynchRecord(pageInfo.getList()));
            }
            bean.setObj(pageInfo);
        } else {
            bean.setSuccess(false);
        }
        return bean;
    }
    
    
    /**
     * 
     *〈简述〉数据导入
     *〈详细描述〉
     * @author myc
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/dataImport",produces="application/json;charset=UTF-8")
    public ResponseBean dataImport(String startTime, String endTime, String synchType){
        ResponseBean bean = new ResponseBean();
        if (StringUtils.isEmpty(synchType)){
            bean.setSuccess(false);
            return bean;
        }
        //获取是否内网标识 1外网 0内网
        String ipAddressType= PropUtil.getProperty("ipAddressType");
        File file = FileUtils.getImportFile();
        if(synchType.contains(Constant.DATA_TYPE_INFOS_CODE)){
            if (file != null && file.exists()){
                File [] files = file.listFiles();
                for (File f : files){
                    if (f.getName().contains(FileUtils.C_INFOS_FILENAME)){
                        infoService.importInfos(f);
                    }
                    if (f.getName().contains(FileUtils.C_ATTACH_FILENAME)){
                        attachService.importAttach(f);
                    }
                    if (f.isDirectory()){
                        if (f.getName().equals(Constant.ATTACH_FILE_TENDER)){
                            OperAttachment.moveFolder(f);
                        }
                    }
                }
            }
        }
        if(synchType.contains(Constant.DATA_TYPE_SUPPLIER_CODE)){
        	if (file != null && file.exists()){
                File [] files = file.listFiles();
                if(files.length<1){
                	bean.setSuccess(false);
                    return bean;
                }
                for (File f : files){
                    if (f.getName().contains(FileUtils.C_SUPPLIER_FILENAME)){
                    	innerSupplierService.importSupplierInfo(f);
                    	
                    }
                    if (f.getName().contains(FileUtils.C_ATTACH_FILENAME)){
                        attachService.importSupplierAttach(f);
                    }
                    if (f.isDirectory()){
                        if (f.getName().equals(Constant.ATTACH_FILE_SUPPLIER)){
                            OperAttachment.moveFolder(f);
                        }
                    }
                }
            } 
        }
        if(synchType.contains(Constant.DATA_TYPE_EXPERT_CODE)){
        	if (file != null && file.exists()){
                File [] files = file.listFiles();
                if(files.length<1){
                	bean.setSuccess(false);
                    return bean;
                }
                for (File f : files){
                    if (f.getName().contains(FileUtils.C_EXPERT_FILENAME)){
                    	innerExpertService.readNewExpertInfo(f);
                    }
                    if (f.getName().contains(FileUtils.C_EXPERT_FILENAME)){
                        attachService.importExpertAttach(f);
                    }
                    if (f.isDirectory()){
                        if (f.getName().equals(Constant.ATTCH_FILE_EXPERT)){
                            OperAttachment.moveFolder(f);
                        }
                    }
                }
            } 
        }
        if(synchType.contains(Constant.DATE_SYNCH_BIDDING_PRODUCT)){
        	/**竞价定型产品导入  只能是外网导入内网**/
        	 if (file != null && file.exists()){
                 File [] files = file.listFiles();
                 for (File f : files){
                	 if (f.isDirectory()){
                		 if (f.getName().equals(Constant.PRODUCT_FILE_EXPERT)){
                	      for (File file2 : f.listFiles()) {
                		   //判断文件名是否是 竞价产品 创建 数据名称
                      	 //判断是否有竞价产品 更新数据名称
                           if (file2.getName().contains(FileUtils.C_OB_PRODUCT_FILENAME)||file2.getName().contains(FileUtils.M_OB_PRODUCT_FILENAME)){
                          	 OBProductService.importProduct(file2);
                           }
                		   
                	      } 
					     }
                	   }
                     if (f.isDirectory()){
                         if (f.getName().equals(Constant.PRODUCT_FILE_EXPERT)){
                             OperAttachment.moveFolder(f);
                         }
                     }
                 }
             }
        }
        /*if(synchType.contains(Constant.DATE_SYNCH_BIDDING_SPECIAL_DATE)){
        	/**竞价特殊日期导出  只能是外网导入内网**//*
        	 if (file != null && file.exists()){
                 File [] files = file.listFiles();
                 if(files.length<1){
                 	bean.setSuccess(false);
                     return bean;
                 }
                 for (File f : files){
                	 //判断文件名是否是 竞价特殊日期 创建 数据名称
                	 //判断是否有竞价特殊日期 更新数据名称
                     if (f.getName().contains(FileUtils.C_OB_SPECIAL_DATE_FILENAME)||f.getName().contains(FileUtils.M_OB_SPECIAL_DATE_FILENAME)){
                    	 OBSpecialDateServer.importSpecialDate(f);
                     }
                     if (f.isDirectory()){
                         if (f.getName().equals(Constant.SPECIAL_DATE_FILE_EXPERT)){
                             OperAttachment.moveFolder(f);
                         }
                     }
                 }
             }
        }*/
        if(synchType.contains(Constant.DATE_SYNCH_BIDDING_SUPPLIER)){
        	/**竞价供应商导出  只能是外网导入内网**/
        	 if (file != null && file.exists()){
                 File [] files = file.listFiles();
                 for (File f : files){
                	 if (f.isDirectory()){
                         if (f.getName().equals(Constant.SUPPLIER_FILE_EXPERT)){
                        	 for (File file3 : f.listFiles()) {
                        		 if (file3.getName().contains(FileUtils.C_OB_SUPPLIER_FILENAME)||file3.getName().contains(FileUtils.M_OB_SUPPLIER_FILENAME)){
                                	 OBSupplierService.importSupplier(file3);
                                 }
							}
                         }
                       }
                    
                     if (f.isDirectory()){
                         if (f.getName().equals(Constant.SUPPLIER_FILE_EXPERT)){
                           OperAttachment.moveFolder(f);
                         }
                     }
                 }
             }
        }
        if(synchType.contains(Constant.DATA_TYPE_BIDDING_CODE)){
        	/**竞价信息导入  只能是外网导入内网**/
        	 if (file != null && file.exists()){
                 File [] files = file.listFiles();
                 for (File f : files){
                	 //如果文件存在 那么删除
                     if (f.isDirectory()){
                    	 if (f.getName().equals(Constant.PROJECT_EXPERT)){
                    		 for (File file2 : f.listFiles()) {
                    			 //判断文件名是否是 竞价信息  数据名称
                    			 if (file2.getName().contains(FileUtils.C_OB_PROJECT_STATUS_FILENAME)){
                    				 OBProjectServer.importProject(file2);
                    			 }
                    			 //判断文件是否是竞价信息 附件文件
                        		 if (file2.getName().contains(FileUtils.C_OB_PROJECT_FILE_FILENAME)){
                        			 OBProjectServer.importFile(file2);
                        		 }
                    		 }
                    	 }
                     if(f.getName().equals(Constant.PROJECT_FILE_EXPERT)){
                    	 for (File file2 : f.listFiles()) {
                    		 if (f.isDirectory()){
                                 OperAttachment.moveToPathFolder(file2,FileUtils.BASE_ATTCH_PATH+FileUtils.TENDER_ATTFILE_PATH);
                    		 }
                    	 }
                     	}
                    
                     }
                 }
             }
        }
        if(synchType.contains(Constant.DATA_TYPE_BIDDING_RESULT_CODE)){
        	/**竞价结果导出  只能是外网导入内网**/
        	if (file != null && file.exists()){
                 File [] files = file.listFiles();
                 for (File f : files){
                	  if (f.isDirectory()){
                          if (f.getName().equals(Constant.RESULT_FILE_EXPERT)){
                        	  for (File file2 : f.listFiles()) {
                        		  if (file2.getName().contains(FileUtils.C_OB_PROJECT_RESULT_FILENAME)){
                        			  OBProjectServer.importProjectResult(file2);
                        		  }
                        	  }
                          }
                	  }
                     if (f.isDirectory()){
                         if (f.getName().equals(Constant.RESULT_FILE_EXPERT)){
                             OperAttachment.moveFolder(f);
                         }
                     }
                 }
             }
        }
        if(synchType.contains(Constant.SYNCH_PRODUCT_LIBRARY)){
        	/**产品库 **/
        	 if (file != null && file.exists()){
                 File [] files = file.listFiles();
                 for (File f : files){
                	 //如果文件存在 那么删除
                     if (f.isDirectory()){
                    	 /**产品库管理导入   1外网 0内网**/
                     	if(ipAddressType.equals("1")){
                     	// 外网 只能导入  内网导出的 产品审核过的数据
                     		if (f.getName().equals(Constant.INNER_PRODUCT_LIBRARY_EXPERT)){
                       		 for (File file2 : f.listFiles()) {
                       			 //判断文件名是否是  数据名称
                       			 if (file2.getName().contains(FileUtils.C_SYNCH_INNER_PRODUCT_LIBRARY)){
                       				 smsProductLibService.importCheckProjectData(file2);
                       			 }
                       		 }
                       	 }
                     	}else{
                     	// 内网 只能 导入 外网 导出的 产品录入需要审核 的数据
                     		if (f.getName().equals(Constant.OUTER_PRODUCT_LIBRARY_EXPERT)){
                       		 for (File file2 : f.listFiles()) {
                       			 //判断文件名是否是  数据名称
                       			 if (file2.getName().contains(FileUtils.C_SYNCH_OUTER_PRODUCT_LIBRARY)){
                       				smsProductLibService.importAddProjectData(file2);
                       			 }
                       			 //判断文件是否是  附件文件
                           		 if (file2.getName().contains(FileUtils.C_SYNCH_OUTER_FILE_PRODUCT_LIBRARY)){
                           			 OBProjectServer.importFile(file2);
                           		 }
                       		 }
                       	 }
                     	}
                     if(f.getName().equals(Constant.OUTER_FILE_PRODUCT_LIBRARY_EXPERT)){
                    	 for (File file2 : f.listFiles()) {
                    		 if (f.isDirectory()){
                                 OperAttachment.moveToPathFolder(file2,FileUtils.BASE_ATTCH_PATH+FileUtils.TENDER_ATTFILE_PATH);
                    		 }
                    	  }
                     	}
                     }
                 }
             }
        }
        bean.setSuccess(true);
        return bean;
    }
    
    /**
     * 
     *〈简述〉封装为带有类型名称的集合
     *〈详细描述〉
     * @author myc
     * @param list 数据list
     * @return 组装后的list
     */
    private List<SynchRecord> packageSynchRecord(List<SynchRecord> list){
        List<SynchRecord> dataList = new ArrayList<>();
        for (SynchRecord synchRecord : list){
            if (StringUtils.isNotBlank(synchRecord.getDataType())){
                DictionaryData dd = DictionaryDataUtil.findById(synchRecord.getDataType());
                if (dd != null && StringUtils.isNotBlank(dd.getName())){
                    synchRecord.setDataTypeName(dd.getName());
                }
            }
            dataList.add(synchRecord);
        }
        return dataList;
    }
}
