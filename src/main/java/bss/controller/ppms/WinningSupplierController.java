/**
 * 
 */
package bss.controller.ppms;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.User;

/**
 * @Description: 中标供应商
 *	 
 * @author Wang Wenshuai
 * @version 2016年10月11日下午2:51:13
 * @since  JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/winningSupplier")
public class WinningSupplierController {
	/**
	 * @Description:选择中标供应商
	 *
	 * @author Wang Wenshuai
	 * @version 2016年10月11日 下午2:53:31  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/selectSupplier")
	public String selectWinningSupplier(Model model){
		List<User> list=new ArrayList<User>();
		for (int i = 0; i < 10; i++) {
			User user=new User();
			user.setAddress("asds"+i);
			list.add(user);
		}
		model.addAttribute("list",list);
		return "bss/ppms/winning_supplier/list";
	}
	/**
	 * @Description:上传
	 *
	 * @author Wang Wenshuai
	 * @version 2016年10月11日 下午3:48:56  
	 * @param       
	 * @return void
	 */
	@RequestMapping("/upload")
	public String upload(){
		return "bss/ppms/winning_supplier/upload";
	}
	
	/**
	 * @Description:打开模板
	 *
	 * @author Wang Wenshuai
	 * @version 2016年10月11日 下午4:46:42  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/template")
	public String template(){
		return "bss/ppms/winning_supplier/template";
	}
}
