package sums.controller.oc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * Description： 网上投诉Controller
 * 
 * @author  zhang shubin
 * @version  
 * @since JDK1.7
 * @date 2017年3月13日 上午10:21:53 
 *
 */
@Controller
@RequestMapping("/onlineComplaints")
public class OnlineComplaints {

	/**
	 * 
	 * Description: 网上投诉
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月13日 
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/complaints")
	public String complaints(HttpServletRequest request){
		
		return "sums/oc/onlineComplaints/add";
	}
	
	/**
	 * 
	 * Description: 投诉处理
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月13日 
	 * @param  @param request
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/handling")
	public String handling(HttpServletRequest request){
		
		return "sums/oc/complaintHandling/list";
	}
	
	/**
	 * 
	 * Description: 投诉记录查询
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月13日 
	 * @param  @param request
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/recordQuery")
	public String recordQuery(HttpServletRequest request){
		
		return "sums/oc/inquire/list";
	}
	
	/**
	 * 
	 * Description: 处理
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月13日 
	 * @param  @param request
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/dealWith")
	public String dealWith(HttpServletRequest request){
		
		return "sums/oc/complaintHandling/show";
	}
	
	/**
	 * 
	 * Description: 公布
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月13日 
	 * @param  @param request
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/publish")
	public String publish(HttpServletRequest request){

		return "sums/oc/complaintHandling/show1";
	}
}
