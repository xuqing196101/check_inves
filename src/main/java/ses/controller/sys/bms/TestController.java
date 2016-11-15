package ses.controller.sys.bms;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

import bss.controller.base.BaseController;
 


@Controller
@RequestMapping("/test")
public class TestController extends BaseController{

	@RequestMapping("/list")
	public String getAll(Model model){
 
		model.addAttribute("test", new Test());
	 
//		String json = JSON.toJSONString(line());
//		model.addAttribute("json", json);
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
	
	
	
	/*public List<Line> line(){
		
		List<Line> list=new LinkedList<Line>();
		Line l=new Line();
		List<Double> data=new LinkedList<Double>();
		data.add(1.0);
		data.add(2.0);
		data.add(3.0);
		data.add(4.0);
		data.add(5.0);
		data.add(6.0);
		data.add(7.0);
		l.setData(data);
		l.setName("测试");
		l.setType("line");
		l.setStack("总量");
		list.add(l);
		return list;
	}*/
	
	
}
