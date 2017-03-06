package ses.controller.sys.bms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.formbean.ResponseBean;
import ses.model.bms.CategoryTree;
import ses.service.bms.CategoryAuditService;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 *  参数审核
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Controller
@RequestMapping("/auditParams")
public class AuditParameterController {

    /** 参数审核service */
    @Autowired
    private CategoryAuditService auditService;
    
    
    /**
     * 
     *〈简述〉
     * 初始化tree
     *〈详细描述〉
     * @author myc
     * @return CategoryTree 集合
     */
    @ResponseBody
    @RequestMapping(value = "/initTree",produces= "application/json;charset=UTF-8")
    public List<CategoryTree> list(String id){
        
        return auditService.initTreeStatus(id);
    }
    
    /**
     * 
     *〈简述〉
     *  审核
     *〈详细描述〉
     * @author myc
     * @param id 品目Id
     * @param status 审核状态
     * @param advise 审核意见
     * @return ResponseBean 对象
     */
    @ResponseBody
    @RequestMapping(value="/audit", produces = "application/json;charset=UTF-8")
    public ResponseBean audit(String id, String status, String advise){
        
        return auditService.audit(id, status, advise);
    }
    
}
