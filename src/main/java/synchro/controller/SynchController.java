package synchro.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import common.bean.ResponseBean;
import synchro.inner.backup.service.att.InnerAttachmentService;
import synchro.inner.backup.service.infos.InnerInfoService;
import synchro.model.SynchRecord;
import synchro.service.SynchService;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>数据同步控制层
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Controller
@RequestMapping("/synch")
public class SynchController {
    
    /** 同步控制层service **/
    @Autowired
    private SynchService synchService;
    
    /** 同步信息数据service **/
    @Autowired
    private InnerInfoService infoService;
    
    /** 附件同步 **/
    @Autowired
    private InnerAttachmentService attachService;
    
    /**
     * 
     *〈简述〉初始化
     *〈详细描述〉
     * @author myc
     * @param model 
     * @param request {@link HttpServletRequest}
     * @return
     */
    @RequestMapping("/init")
    public String init(Model model, HttpServletRequest request){
       String type =  request.getParameter("type");
       model.addAttribute("operType", type);
       return "/synch/list";
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
            bean.setObj(pageInfo);
        } else {
            bean.setSuccess(false);
        }
        return bean;
    }
    
    /**
     * 
     *〈简述〉
     *〈详细描述〉
     * @author myc
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/synched")
    public ResponseBean synch(String startTime, String endTime){
        ResponseBean bean = new ResponseBean();
        infoService.backUpInfos(startTime, endTime, new Date());
        attachService.backAttachment();
        bean.setSuccess(true);
        return bean;
    }
}
