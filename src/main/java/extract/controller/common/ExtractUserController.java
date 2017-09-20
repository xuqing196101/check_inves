package extract.controller.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import extract.model.common.ExtractUser;
import extract.service.common.ExtractUserService;

@Controller
@RequestMapping("/extractUser")
public class ExtractUserController {

	@Autowired
	private ExtractUserService extractUserService;
	
	 /**
     * 引用历史人员 跳转页面
     * @param packageId
     * @return
     */
     @RequestMapping("/toPeronList")
    public String toPeronList(Model model,String personType,ExtractUser user,@CurrentUser User user2){
    	 if (personType != null && !"".equals(personType)) {
           	if("extractUser".equals(personType) && null !=user2){
           		String orgId = user2.getOrg().getId();
           		if(StringUtils.isNotBlank(orgId)){
           			user.setOrgId(orgId);
           			model.addAttribute("personType", personType);
           			model.addAttribute("personList", extractUserService.getList(user));
           		}
       		}
           }
        return "/ses/extract/person_list";
    }
     
     
     /**
      * 引用历史人员
      * @param packageId
      * @return
      */
     @RequestMapping("/getPeronList")
     @ResponseBody
     public String getPeronList(Model model,String personType,ExtractUser user, @CurrentUser User user2){
    	  if (personType != null && !"".equals(personType)) {
          	if("extractUser".equals(personType) && null !=user2){
          		String orgId = user2.getOrg().getId();
          		if(StringUtils.isNotBlank(orgId)){
          			user.setOrgId(orgId);
          			return JSON.toJSONString(extractUserService.getList(user));
          		}
      		}
          }
		return "";
     }
     
     @RequestMapping("/addPerson")
     @ResponseBody
     public String addPerson( @Valid ExtractUser user,BindingResult result,@CurrentUser User user2){
    	 
    	 if(result.hasErrors()){
    		 HashMap<String, String> hashMap = new HashMap<>();
    		 List<FieldError> fieldErrors = result.getFieldErrors();
    		 for (FieldError fieldError : fieldErrors) {
				hashMap.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
    		 return JSON.toJSONString(hashMap);
    	 }
    	 
    	Map<String, String> error = null;;
    	
    	if(null !=user2){
    		String orgId = user2.getOrg().getId();
    		if(StringUtils.isNotBlank(orgId)){
    			user.setOrgId(orgId);
    			error = extractUserService.addPerson(user);
    		}
    	}
    	
    	
    	
		return JSON.toJSONString(error);
     }
}
