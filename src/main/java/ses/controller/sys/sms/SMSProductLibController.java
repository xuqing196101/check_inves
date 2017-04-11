package ses.controller.sys.sms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import common.utils.JdcgResult;

/**
 * 
* @ClassName: ProductLibController 
* @Description: 产品库管理Controller
* @author Easong
* @date 2017年4月10日 下午5:42:32 
*
 */
@Controller
@RequestMapping("/product_lib")
public class SMSProductLibController {
	
	
	/**
	 * 
	* @Title: findAllProductLibInfo 
	* @Description: 供应商后台查询所有已添加的产品信息
	* @author Easong
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/findAllProductLibInfo")
	public String findAllSMSProductLibInfo(Model model, HttpServletRequest req){
		
		return "ses/sms/supplier_product_lib/list";
	}
	
	/**
	 * 
	* @Title: addSMSProductUI 
	* @Description: 供应商后台录入产品信息页面回显
	* @author Easong
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/addSMSProductUI")
	public String addSMSProductUI(){
		return "ses/sms/supplier_product_lib/add";
	}
	
	/**
	 * 
	* @Title: addProductLibInfo 
	* @Description: 供应商后台录入产品信息
	* @author Easong
	* @param @return    设定文件 
	* @return JdcgResult    返回类型 
	* @throws
	 */
	@RequestMapping("/addProductLibInfo")
	public JdcgResult addProductLibInfo(){
		return JdcgResult.ok();
	}
	
	
	/**
	 * 
	* @Title: deleteProductLibInfo 
	* @Description: 供应商后台删除暂存的产品信息
	* @author Easong
	* @param @return    设定文件 
	* @return JdcgResult    返回类型 
	* @throws
	 */
	@RequestMapping("/deleteProductLibInfo")
	public JdcgResult deleteProductLibInfo(){
		return JdcgResult.ok();
	}
}
