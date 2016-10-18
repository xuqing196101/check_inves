package ses.controller.sys.bms;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
	public String getAll(@Valid Test test, BindingResult result,Model model){
	 if(result.hasErrors()){
	 
		 ObjectError error = new ObjectError("name","已经存在");	
		 result.addError(error);
		 model.addAttribute("test", test);
		 return "bss/pms/statistic/test";
	
	}else{
		return "redirect:list.html";
	}
 
	 
	}
	
}
