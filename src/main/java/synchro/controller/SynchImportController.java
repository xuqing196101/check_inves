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
import bss.util.FileUtil;

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
        if(synchType.equals(Constant.DATA_TYPE_INFOS_CODE)){
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
                        if (f.getName().equals(Constant.ATTACH_FILE_TENDER)){
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
                        if (f.getName().equals(Constant.ATTACH_FILE_TENDER)){
                            OperAttachment.moveFolder(f);
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
