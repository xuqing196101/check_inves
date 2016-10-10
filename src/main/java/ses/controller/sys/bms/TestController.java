package ses.controller.sys.bms;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import bss.controller.base.BaseController;


@Controller
@RequestMapping("/test")
public class TestController extends BaseController{

	@RequestMapping("/list")
	public String getAll(Model model){
 
		model.addAttribute("test", new Test());
		return "bss/pms/statistic/test";
	}
	
	@RequestMapping("/add")
	public String getAll(@Valid Test test, BindingResult result){
	 if(result.hasErrors()){
//		List<ObjectError> list = result.getAllErrors();
//		for(ObjectError error : list){
//            System.out.println(error.getDefaultMessage());
//        }
//		 model.addAttribute("error", result);
		 return "bss/pms/statistic/test";
	
	}else{
		return "redirect:list.html";
	}
 
	 
	}
	
}
