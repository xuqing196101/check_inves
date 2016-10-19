/**
 * 
 */
package bss.controller.dms;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import bss.service.dms.PurchaseArchiveServiceI;
import ses.controller.sys.sms.BaseSupplierController;
import ses.model.oms.PurchaseInfo;
import ses.service.oms.PurchaseServiceI;
import ses.util.PropertiesUtil;

/**
 * @Title:PurchaseArchiveController
 * @Description: 
 * @author ZhaoBo
 * @date 2016-10-19上午9:08:12
 */
@Controller
@Scope("prototype")
@RequestMapping("/purchaseArchive")
public class PurchaseArchiveController extends BaseSupplierController{
	@Autowired
	private PurchaseArchiveServiceI purchaseArchiveService;
	@Autowired
	private PurchaseServiceI purchaseService;
	
	/**
	 * 
	* @Title: archiveList
	* @author ZhaoBo
	* @date 2016-10-19 下午2:42:46  
	* @Description: 采购档案列表页面 
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/archiveList")
	public String archiveList(Model model){
		
		return "bss/dms/purchaseArchive/list";
	}
	
	/**
	 * 
	* @Title: queryArchive
	* @author ZhaoBo
	* @date 2016-10-19 下午2:42:49  
	* @Description: 采购档案查询页面 
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/queryArchive")
	public String queryArchive(Model model){
		
		
		
		return "bss/dms/purchaseArchive/query_archive";
	}
	
	/**
	 * 
	* @Title: archiveAuthorize
	* @author ZhaoBo
	* @date 2016-10-19 下午3:05:27  
	* @Description: 采购档案授权 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/archiveAuthorize")
	public String archiveAuthorize(Integer page,HttpServletRequest request,Model model){
		HashMap<String,Object> map = new HashMap<String,Object>();
		String name = request.getParameter("name");
		String depName = request.getParameter("depName");
		if(name!=null&&name!=""){
			map.put("relName",name);
		}
		if(depName!=null&&depName!=""){
			map.put("purchaseDepName",depName);
		}
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<PurchaseInfo> purList = purchaseService.findPurchaseList(map);
		model.addAttribute("name", name);
		model.addAttribute("depName", depName);
		model.addAttribute("purchaseList", new PageInfo<PurchaseInfo>(purList));
		return "bss/dms/purchaseArchive/authorize";
	}
}
