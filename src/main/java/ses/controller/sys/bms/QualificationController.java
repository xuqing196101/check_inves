package ses.controller.sys.bms;

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
import ses.model.bms.Qualification;
import ses.service.bms.QualificationService;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>资质管理
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Controller
@RequestMapping("/qualification")
public class QualificationController {
    
    /** service **/
    @Autowired
    private QualificationService quaService;
    
    /**
     * 
     *〈简述〉初始化页面
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
        return   "/ses/bms/qualification/list";
    }
    
    /**
     * 
     *〈简述〉查询
     *〈详细描述〉
     * @author myc
     * @param page 当前页
     * @param name 查询的名称
     * @param type 类型
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public ResponseBean list(Integer page, String name, String  type){
        
        ResponseBean res = new ResponseBean();
        if (StringUtils.isNotBlank(type)){
            Integer typeInteger = Integer.parseInt(type);
            List<Qualification> list = quaService.findList(page, name, typeInteger);
            PageInfo<Qualification> pageInfo = new PageInfo<Qualification> (list);
            res.setSuccess(true);
            res.setObj(pageInfo);
        }
       return res;
    }
    
    /**
     * 
     *〈简述〉保存
     *〈详细描述〉
     * @author myc
     * @param type 类型
     * @param name 名称
     * @return  ResponseBean
     */
    @ResponseBody
    @RequestMapping(value = "/save", produces="application/json;chaset=UTF-8")
    public ResponseBean save(String type, String name, String operaType, String id){
        
        ResponseBean bean = new ResponseBean();
        
        if (!StringUtils.isNotBlank(name)){
            bean.setSuccess(false);
            bean.setObj("名称不能为空");
            return bean;
        }
        if (!StringUtils.isNotBlank(type)){
            bean.setSuccess(false);
            bean.setObj("类型值出现错误");
            return bean;
        }
        
        Qualification qualification = quaService.save(type, name, operaType, id);
        bean.setSuccess(true);
        bean.setObj(qualification);
        return bean;
    }
    
    /**
     * 
     *〈简述〉根据Id查询对象
     *〈详细描述〉
     * @author myc
     * @param id 主键
     * @return 
     */
    @ResponseBody
    @RequestMapping(value ="/getQualification", produces="application/json")
    public ResponseBean getQualification(String id){
        
        ResponseBean res = new ResponseBean();
        Qualification qualification = quaService.getQualification(id);
        if (qualification != null){
            res.setSuccess(true);
            res.setObj(qualification);
        }
        return res;
    }
    
    /**
     * 
     *〈简述〉根据id删除
     *〈详细描述〉
     * @author myc
     * @param id 主键id集合
     * @return 成功返回 ok
     */
    @ResponseBody
    @RequestMapping(value ="/del", produces="html/text")
    public String del(String id){
        
        return  quaService.del(id);
    }
    
    /**
     * 
     *〈简述〉
     *〈详细描述〉
     * @author myc
     * @param model {@link Model} 
     * @param type {@link type} 类型
     * @return 
     */
    @RequestMapping("/initLayer")
    public String initOpenLayer(Model model, String type, String ids){
        model.addAttribute("type", type);
        model.addAttribute("ids", ids);
        return "/ses/bms/qualification/quaLayer";
    }
    
}
