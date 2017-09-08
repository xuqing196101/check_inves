package extract.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

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
    public String toPeronList(Model model,String personType,ExtractUser user){
        model.addAttribute("personType", personType);
        model.addAttribute("personList", extractUserService.getList(user));
        return "/ses/extract/person_list";
    }
     
     
     /**
      * 引用历史人员
      * @param packageId
      * @return
      */
     @RequestMapping("/getPeronList")
     @ResponseBody
     public String getPeronList(Model model,String personType,ExtractUser user){
    	  if (personType != null && !"".equals(personType)) {
          	if("extractUser".equals(personType)){
      			return JSON.toJSONString(extractUserService.getList(user));
      		}
          }
		return "";
     }
     
     @RequestMapping("/addPerson")
     @ResponseBody
     public void addPerson(ExtractUser user){
    	 extractUserService.addPerson(user);
     }
}
