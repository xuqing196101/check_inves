package ses.controller.sys.bms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.User;


@Controller
@RequestMapping("/test")
public class TestController {

	@RequestMapping("/add")
	public String getAll(){
 
		
		return "user/test";
	}
	
	@RequestMapping("/get")
	public String getAll(User user){
		System.out.println("------------------------------------");
		System.out.println(user.getCreatedAt());
		
		return "view";
	}
	
}
