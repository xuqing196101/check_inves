package extract.controller.common;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.User;

import com.alibaba.fastjson.JSON;
import common.annotation.CurrentUser;

import extract.model.common.Supervise;
import extract.service.common.SuperviseService;

@Controller
@RequestMapping("/supervise")
public class SuperviseController {

	
	@Autowired
	private SuperviseService superviseService;
	
	 /**
     * 引用历史人员 跳转页面
     * @param packageId
     * @return
     */
     @RequestMapping("/toPeronList")
    public String toPeronList(Model model,String personType,Supervise suser,@CurrentUser User user2){
    	 if("supervise".equals(personType) && null !=user2){
       		String orgId = user2.getOrg().getId();
       		if(StringUtils.isNotBlank(orgId)){
       			suser.setOrgId(orgId);
       			model.addAttribute("personType", personType);
       			model.addAttribute("personList", superviseService.getList(suser));
       		}
   		}
        return "ses/extract/person_list";
    }
     
     /**
      * 引用历史人员
      * @param packageId
      * @return
      */
     @RequestMapping("/getPeronList")
     @ResponseBody
     public String getPeronList(Model model,String personType,Supervise user,@CurrentUser User user2){
    	  if (personType != null && !"".equals(personType)) {
          	if("supervise".equals(personType) && null !=user2){
          		String orgId = user2.getOrg().getId();
          		if(StringUtils.isNotBlank(orgId)){
          			user.setOrgId(orgId);
          			return JSON.toJSONString(superviseService.getList(user));
          		}
      		}
          }
		return "";
     }
     
     @RequestMapping("/addPerson")
     @ResponseBody
     public String addPerson(@Valid Supervise user,BindingResult result,@CurrentUser User user2){
    	 
    	 if(result.hasErrors()){
    		 HashMap<String, String> err = new HashMap<>();
    		 List<FieldError> fieldErrors = result.getFieldErrors();
    		 for (FieldError fieldError : fieldErrors) {
				err.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
    		 return JSON.toJSONString(err);
    	 }
    	 HashMap<String, String> addPerson = null;
    	if(null!= user2){
    		String orgId = user2.getOrg().getId();
    		if(StringUtils.isNotBlank(orgId)){
    			user.setOrgId(orgId);
    			addPerson = superviseService.addPerson(user);
    		}
    	} 
		return JSON.toJSONString(addPerson);
     }
	
	
}
