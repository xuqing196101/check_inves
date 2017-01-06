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

import com.github.pagehelper.PageInfo;

import common.bean.ResponseBean;
import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;
import synchro.inner.backup.service.infos.InnerInfoExportService;
import synchro.model.SynchRecord;
import synchro.service.SynchRecordService;
import synchro.service.SynchService;
import synchro.util.Constant;

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
    
    /** 记录service  **/
    @Autowired
    private SynchRecordService  recordService;
    
    
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
       List<DictionaryData> list = DictionaryDataUtil.find(DATA_TYPE_KIND);
       
       //过滤附件类型
       for (int i = 0; i < list.size(); i++){
           DictionaryData dd = list.get(i);
           if (dd.getCode().equals(Constant.DATA_TYPE_ATTACH_CODE)){
               list.remove(i);
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
    public ResponseBean list(Integer operType,Integer page){
        
        ResponseBean bean = new ResponseBean();
        if (operType != null){
            bean.setSuccess(true);
            List<SynchRecord> list = synchService.getList(operType,page);
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
        if (synchType.equals(Constant.DATA_TYPE_INFOS_CODE)){
            infoService.backUpInfos(startTime, endTime, new Date());
            bean.setSuccess(true);
        }
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
