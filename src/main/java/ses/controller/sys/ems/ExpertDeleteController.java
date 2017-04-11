package ses.controller.sys.ems;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.ems.Expert;
import ses.service.ems.ExpertService;

import com.github.pagehelper.PageInfo;

/**
 * <p>Title:ExpertDeleteController </p>
 * <p>Description:专家注销 </p>
 * @author XuQing
 * @date 2017-4-11下午4:11:18
 */
@Controller
@Scope("prototype")
@RequestMapping("/expertDelete")
public class ExpertDeleteController {
	/**
     * 供应商注册服务层
     */
    @Autowired
    private ExpertService expertService;
	
	/**
     * @Title: cancellation
     * @author XuQing 
     * @date 2017-3-8 下午1:33:37  
     * @Description:专家注销
     * @param @param expertId      
     * @return void
     */
     @RequestMapping(value = "/cancellation")
     @ResponseBody
     public void cancellation(String expertId){
    	 expertService.deleteExpert(expertId);
     }
     
     /**
      * @Title: findLogoutList
      * @author XuQing 
      * @date 2017-4-11 下午3:08:59  
      * @Description:注销列表
      * @param @param supplier      
      * @return void
      */
     @RequestMapping(value = "/logoutList")
     public String logoutList(Expert expert, Integer page, Model model){
  	   List<Expert> logoutList = expertService.findLogoutList(expert, page);
	   PageInfo < Expert > pageInfo = new PageInfo < Expert > (logoutList);
	   model.addAttribute("result", pageInfo);
  	 return "ses/ems/expertDelete/list";
     }
}
