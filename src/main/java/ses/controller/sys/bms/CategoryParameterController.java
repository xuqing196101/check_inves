package ses.controller.sys.bms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.CategoryTree;
import ses.service.bms.CategoryParameterService;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
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
    
    /**
     * 
     *〈简述〉
     *  参数管理页面controller
     *〈详细描述〉
     * @author myc
     * @return
     */
    @RequestMapping("/list")
    public String list(){
        
       return "/ses/ppms/categoryparam/cateParameter";
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
    @RequestMapping("/initTree")
    public List<CategoryTree> initTree(HttpServletRequest request){
        List<CategoryTree> list = paramService.initTree(request);
        return list;
    }
}
