package ses.controller.sys.bms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.CategoryTree;
import ses.service.bms.CategoryPublishService;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 *  发布控制层
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Controller
@RequestMapping("/publish")
public class CategoryPublishController {
    
    /** 发布service */
    @Autowired
    private CategoryPublishService publishService;
    
    /**
     * 
     *〈简述〉
     * 初始化参数发布
     *〈详细描述〉
     * @author myc
     * @return
     */
    @RequestMapping("/init")
    public String initPublish(){
        
        return "/ses/ppms/categoryparam/publish";
    }
    
    /**
     * 
     *〈简述〉
     * 初始化tree
     *〈详细描述〉
     * @author myc
     * @param id treeId
     * @return CategoryTree 集合
     */
    @ResponseBody
    @RequestMapping(value = "/initTree",produces="application/json;charset=UTF-8")
    public List<CategoryTree> initTree(String id){
        
        return publishService.initTree(id);
    }
    
    /**
     * 
    * @Title: initTreeIndex 
    * @Description: 门户技术参数显示
    * @author Easong
    * @param @param id
    * @param @return    设定文件 
    * @return List<CategoryTree>    返回类型 
    * @throws
     */
    @ResponseBody
    @RequestMapping(value = "/initTreeIndex",produces="application/json;charset=UTF-8")
    public List<CategoryTree> initTreeIndex(String id){
    	return publishService.initTreeIndex(id);
    }
    
    /**
     * 
     *〈简述〉
     * 发布
     *〈详细描述〉
     * @author myc
     * @return 成功返回ok,失败返回失败信息
     */
    @ResponseBody
    @RequestMapping(value = "/published", produces="text/html;charset=UTF-8")
    public String publish(String ids){
        String msg  = publishService.publish(ids);
        return msg;
    }
}
