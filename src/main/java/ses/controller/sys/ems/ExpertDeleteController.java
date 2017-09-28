package ses.controller.sys.ems;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpertService;

import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;

/**
 * <p>Title:ExpertDeleteController </p>
 * <p>Description:专家注销 </p>
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
    
    @Autowired
    private UserServiceI UserServiceI;
	
	/**
     * @Title: cancellation
     * @date 2017-3-8 下午1:33:37  
     * @Description:专家注销
     * @param @param expertId      
     * @return void
     */
     @RequestMapping(value = "/cancellation")
     @ResponseBody
     public void cancellation(String expertId){
    	 /*if(sign == 1){
    		 expertService.updateById(expertId);
    		 UserServiceI.updateByTypeId(expertId);
    	 }else{
    		 expertService.deleteExpert(expertId);
    	 }*/
    	 
         Expert expert = expertService.selectByPrimaryKey(expertId);
         String status = expert.getStatus();
         //Short isProvisional = expert.getIsProvisional();
         if(status !=null ){
        	 if(status.equals("3") || status.equals("0")){
        		 expertService.updateById(expertId);
        		 UserServiceI.updateByTypeId(expertId);
        	 } 
         }	 
     }
     
     /**
      * @Title: findLogoutList
      * @date 2017-4-11 下午3:08:59  
      * @Description:注销列表
      * @param @param supplier      
      * @return void
      */
     @RequestMapping(value = "/logoutList")
     public String logoutList(@CurrentUser User user,Expert expert, Integer page, Model model){
       if(null != user && "4".equals(user.getTypeName())){
    	   List<Expert> logoutList = expertService.findLogoutList(expert, page);
    	   PageInfo < Expert > pageInfo = new PageInfo < Expert > (logoutList);
    	   model.addAttribute("result", pageInfo);
       }else{
    	   model.addAttribute("result", new PageInfo < Expert > ());
       }
       
  	 return "ses/ems/expertDelete/list";
     }
}
