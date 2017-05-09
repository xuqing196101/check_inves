package ses.controller.sys.bms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.annotation.CurrentUser;
import common.constant.StaticVariables;
import ses.formbean.ResponseBean;
import ses.model.bms.CategoryParameter;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.service.bms.CategoryParameterService;
import ses.service.bms.CategoryService;
import ses.util.PropUtil;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 *  产品参数管理
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Controller
@RequestMapping("/cateParam")
public class CategoryParameterController {
    
    /** 品目参数service */
    @Autowired
    private CategoryParameterService paramService;
    
    /** 品目service */
    @Autowired
    private CategoryService categoryService;
    
    /**
     * 
     *〈简述〉
     *  参数管理页面controller
     *〈详细描述〉
     * @author myc
     * @return
     */
    @RequestMapping("/list")
    public String list(@CurrentUser User user,HttpServletRequest request, Model model){
    	Boolean bool = PropUtil.getOutPageButton("config.properties");
        if (user != null && user.getOrg() != null) {
            model.addAttribute("orgId", user.getOrg().getId());
        }
        List<DictionaryData> list = paramService.initTypes();
        model.addAttribute("dictionary", list);
        model.addAttribute("buttonHidden", bool);
        
       return "/ses/ppms/categoryparam/cateParameter";
    }
    
    /**
     * 
     *〈简述〉
     *  初始化类型
     *〈详细描述〉myc
     * @return  类型集合
     */
    @ResponseBody
    @RequestMapping(value = "/initTypes" ,produces="application/json;charset=UTF-8")
    public List<DictionaryData> initTypes(){
        
        return paramService.initSmallTypes();
    }
    
    /**
     * 
     *〈简述〉
     * 初始化tree
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     * @return tree 对应的list
     */
    @ResponseBody
    @RequestMapping(value = "/initTree",produces = "application/json;charset=UTF-8")
    public List<CategoryTree> initTree(HttpServletRequest request){
        
        List<CategoryTree> list = paramService.initTree(request);
        return list;
    }
    
    /**
     * 
     *〈简述〉
     * 编辑
     *〈详细描述〉
     * @author myc
     * @param id 主键Id
     * @return
     */
    @ResponseBody
    @RequestMapping(value= "/edit",produces = "application/json;charset=UTF-8")
    public CategoryParameter findById(String id){
        
        return paramService.findById(id);
    }
    
    /**
     * 
     *〈简述〉
     *  保存品目参数
     *〈详细描述〉
     * @author myc
     * @param name   参数名称 {@link java.lang.String}
     * @param type   参数类型{@link java.lang.String}
     * @param orgId  所属机构Id {@link java.lang.String}
     * @param cateId 品目Id {@link java.lang.String}
     * @param id     主键Id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save" , produces = "application/json;charset=UTF-8")
    public ResponseBean save(String name, String type, String orgId , String cateId , String id,Integer paramRequired){
        
        return paramService.saveParameter(name, type ,orgId ,cateId ,id,paramRequired);
    }
    
    /**
     * 
     *〈简述〉
     * 删除参数
     *〈详细描述〉
     * @author myc
     * @param ids 需要删除的参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delParamters",produces="text/html;charset=UTF-8")
    public String delParamters(String ids){
        
        return  paramService.deleteParamters(ids);
    }
    
    /**
     * 
     *〈简述〉
     *  根据品目查询对应的参数
     *〈详细描述〉
     * @author myc
     * @param cateId 品目Id
     * @return 品目参数list
     */
    @ResponseBody
    @RequestMapping(value = "/params" ,produces = "application/json;charset=UTF-8")
    public List<CategoryParameter> findParamsByTreeId(String cateId){
       
        return  paramService.getParamsByCateId(cateId);
    }
    
    /**
     * 
     *〈简述〉
     *〈详细描述〉
     * @author myc
     * @param open 是否公开
     * @param classify 分类,只有物资才有次参数
     * @param cateId 品目Id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/submitParams", produces ="text/html;charset=UTF-8")
    public String submitParams(String open, String classify , String id){
        
        return  paramService.submit(open, classify, id);
    }
    
    /**
     * 
     *〈简述〉
     * 获取当前当前的状态
     *〈详细描述〉
     * @author myc
     * @param id 品目Id
     * @param opera 操作类型
     * @return 返回各个状态提示
     */
    @ResponseBody
    @RequestMapping(value = "/getStatus", produces = "text/html;charset=UTF-8")
    public String getStatus(String id, String opera){
        
        return  categoryService.estimate(id, opera,StaticVariables.CATEGORY_SUBMIT_MSG,StaticVariables.CATEGORY_SUBMIT_STATUS);
    }
}
