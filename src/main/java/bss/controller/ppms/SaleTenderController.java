/**
 * 
 */
package bss.controller.ppms;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: 发售标书
 *	 
 * @author Wang Wenshuai
 * @version 2016年10月19日下午2:27:04
 * @since  JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/saleTender")
public class SaleTenderController {

	/**
	 * @Description:展示发售标书列表
	 *
	 * @author Wang Wenshuai
	 * @version 2016年10月19日 下午2:39:16  
	 * @param @param prjectId      
	 * @return void
	 */
	@RequestMapping("/list")
	public String  list(String prjectId){
		return prjectId;

	}
	
	
	/**
	 * @Description:展示供应商列表
	 *
	 * @author Wang Wenshuai
	 * @version 2016年10月19日 下午2:41:09  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/showSupplier")
	public  String showSupplier(){
		return null;
	}
	
	/**
	 * 
	 * @Description:
	 *
	 * @author Wang Wenshuai
	 * @version 2016年10月19日 下午2:43:04  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/uploadDeposit")
	public String uploadDeposit(){
		return null;
	}
}
