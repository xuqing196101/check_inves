package synchro.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.service.sms.SupplierService;
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
import bss.service.ob.OBSpecialDateServer;
import bss.service.ob.OBSupplierService;
import bss.util.FileUtil;

import com.github.pagehelper.PageInfo;

import common.bean.ResponseBean;
import common.utils.UploadUtil;

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
    /**竞价特殊日期**/
    @Autowired
    private OBSpecialDateServer OBSpecialDateServer;
    /**竞价供应商**/
    @Autowired
    private OBSupplierService OBSupplierService;
    /**竞价信息**/
    @Autowired
    private OBProjectServer OBProjectServer;
    /** 同步供应商数据service **/
    @Autowired
    private InnerSupplierService innerSupplierService;
    
    @Autowired
    private SupplierService supplierService;
   
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
       List<DictionaryData> list =new LinkedList<DictionaryData>();
       List<DictionaryData> templist = DictionaryDataUtil.find(DATA_TYPE_KIND); 
       //过滤附件类型
       for(DictionaryData dd:templist){
    	   if (!dd.getCode().equals(Constant.DATA_TYPE_ATTACH_CODE)){
               list.add(dd);
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
                        			 OBProjectServer.importProjectFile(file2);
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
        
       /* if(synchType.equals(Constant.DATA_TYPE_INFOS_CODE)){
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
        }else  if(synchType.equals(Constant.DATA_TYPE_SUPPLIER_CODE)){
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
        }else  if(synchType.equals(Constant.DATA_TYPE_EXPERT_CODE)){
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
        }*/
       
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
