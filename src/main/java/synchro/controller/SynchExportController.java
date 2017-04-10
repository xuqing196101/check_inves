package synchro.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;
import synchro.inner.back.service.infos.InnerInfoExportService;
import synchro.model.SynchRecord;
import synchro.outer.back.service.expert.OuterExpertService;
import synchro.outer.back.service.supplier.OuterSupplierService;
import synchro.service.SynchRecordService;
import synchro.service.SynchService;
import synchro.util.Constant;

import bss.model.ob.OBSupplier;
import bss.service.ob.OBProductService;
import bss.service.ob.OBProjectServer;
import bss.service.ob.OBSpecialDateServer;
import bss.service.ob.OBSupplierService;

import com.github.pagehelper.PageInfo;
import common.bean.ResponseBean;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>数据导出
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Controller
@RequestMapping("/synchExport")
public class SynchExportController {
    
    /** 同步控制层service **/
    @Autowired
    private SynchService synchService;
    
    /** 同步信息数据service **/
    @Autowired
    private InnerInfoExportService infoService;
    
    /** 同步供应商数据service **/
    @Autowired
    private OuterSupplierService outerSupplierService;
    
    /** 记录service  **/
    @Autowired
    private SynchRecordService  recordService;
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
    @Autowired
    private OuterExpertService outerExpertService;
    
    
    /** 设置数据类型 **/
    private static final Integer DATA_TYPE_KIND = 29;
    
    /**
     * 
     *〈简述〉初始化导出
     *〈详细描述〉
     * @author myc
     * @param model 
     * @param request {@link HttpServletRequest}
     * @return
     */
    @RequestMapping("/initExport")
    public String initExport(Model model, HttpServletRequest request){
       model.addAttribute("operType", Constant.OPER_TYPE_EXPORT);
       List<DictionaryData> templist = DictionaryDataUtil.find(DATA_TYPE_KIND);
       List<DictionaryData> list = new ArrayList<DictionaryData>();
       
       //过滤附件类型
       for (int i = 0; i < templist.size(); i++){
           DictionaryData dd = templist.get(i);
           if (!dd.getCode().equals(Constant.DATA_TYPE_ATTACH_CODE)){
               list.add(dd);
           }
       }
       
       model.addAttribute("dataTypeList", list);
       
       //获取最近一次同步时间,作为手动同步的开始时间的默认值
       DictionaryData dd = DictionaryDataUtil.get(Constant.DATA_TYPE_INFOS_CODE);
       if (dd != null && StringUtils.isNotBlank(dd.getId())){
           String startTime = recordService.getSynchTime(Constant.OPER_TYPE_EXPORT, dd.getId());
           model.addAttribute("startTime", startTime);
       }
       
       return "/synch/export";
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
    public ResponseBean list(Integer operType,Integer page, String searchType , String startTime, String endTime){
        
        ResponseBean bean = new ResponseBean();
        if (operType != null){
            bean.setSuccess(true);
            List<SynchRecord> list = synchService.getList(operType, page , searchType, startTime, endTime);
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
     *〈简述〉数据导出
     *〈详细描述〉
     * @author myc
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/dataExport")
    public ResponseBean dataExport(String startTime, String endTime, String synchType){
        ResponseBean bean = new ResponseBean();
        if (StringUtils.isEmpty(synchType)){
            bean.setSuccess(false);
            return bean;
        }
        Date date=new Date();
        if (synchType.contains(Constant.DATA_TYPE_INFOS_CODE)){
            infoService.backUpInfos(startTime, endTime, date);
        } 
        if (synchType.contains(Constant.DATA_TYPE_SUPPLIER_CODE)) {
            outerSupplierService.exportCommitSupplier(startTime, endTime, date);
        }
        if (synchType.contains(Constant.DATA_TYPE_EXPERT_CODE)) {
        	outerExpertService.backupCreated(startTime, endTime);
        }
        if(synchType.contains(Constant.DATE_SYNCH_BIDDING_PRODUCT)){
        	/**竞价定型产品导出  只能是内网导出外网**/
        	OBProductService.exportProduct(startTime, endTime, date);
        }
       /* if(synchType.contains(Constant.DATE_SYNCH_BIDDING_SPECIAL_DATE)){
        	/**竞价特殊日期导出  只能是内网导出外网**//*
        	OBSpecialDateServer.exportSpecialDate(startTime, endTime, date);
        }*/
        if(synchType.contains(Constant.DATE_SYNCH_BIDDING_SUPPLIER)){
        	/**竞价供应商导出  只能是内网导出外网**/
        	OBSupplierService.exportSupplier(startTime, endTime, date);
        }
        if(synchType.contains(Constant.DATA_TYPE_BIDDING_CODE)){
        	/**竞价信息导出  只能是内网导出外网**/
        	OBProjectServer.exportProject(startTime, endTime, date);
        }
        if(synchType.contains(Constant.DATA_TYPE_BIDDING_RESULT_CODE)){
        	/**竞价结果导出  只能是外网导出内网**/
        	OBProjectServer.exportProjectResult(startTime, endTime, date);
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
