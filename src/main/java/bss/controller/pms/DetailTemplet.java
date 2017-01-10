package bss.controller.pms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/templet")
public class DetailTemplet {


	/**
	 * 
	* @Title: template
	* @Description: 明细模板
	* author: Li Xiaoxiao 
	* @param @return     
	* @return ModelAndView     
	* @throws
	 */
	@RequestMapping("/detail")
	public ModelAndView template(){
		
		ModelAndView moeldeAndView=new ModelAndView("/");
		
		return moeldeAndView;
	}
}
