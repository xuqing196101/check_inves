package system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import common.bean.ResponseBean;
import common.model.SystemLog;
import common.service.SystemLogService;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 系统日志controller
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Controller
@RequestMapping("/systemLog")
public class SystemLogController {
    
    
    /** 系统日志service **/
    @Autowired
    private SystemLogService systemLogService;
    
    /**
     * 
     *〈简述〉初始化
     *〈详细描述〉
     * @author myc
     * @param model {@link Model}
     * @param request {@link HttpServletRequest}
     * @return
     */
    @RequestMapping("/init")
    public String init(Model model, HttpServletRequest request){
        String type = request.getParameter("type");
        model.addAttribute("type", type);
        return "/system/log/list";
    }
    
    /**
     * 
     *〈简述〉根据参数查询
     *〈详细描述〉
     * @author myc
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public ResponseBean list(HttpServletRequest request,Integer page){
        ResponseBean res = new ResponseBean();
        List<SystemLog> list = systemLogService.getListByParam(request,page);
        PageInfo<SystemLog> pageInfo = new PageInfo<SystemLog> (list);
        res.setSuccess(true);
        res.setObj(pageInfo);
        return res;
    }
    
    /**
     * 
     *〈简述〉查询详情
     *〈详细描述〉
     * @author myc
     * @param id 主键Id
     * @return 
     */
    @RequestMapping("/detail")
    public String detail(String id, Model model){
        SystemLog systemLog = systemLogService.findById(id);
        model.addAttribute("systemLog", systemLog);
        return "/system/log/detail";
    }
}
