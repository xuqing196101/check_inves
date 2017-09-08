package extract.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String toPeronList(Model model,String personType,Supervise suser){
        model.addAttribute("personType", personType);
        model.addAttribute("personList", superviseService.getList(suser));
        return "ses/extract/person_list";
    }
     
     @RequestMapping("/addPerson")
     @ResponseBody
     public void addPerson(Supervise user){
    	 superviseService.addPerson(user);
     }
	
	
}
