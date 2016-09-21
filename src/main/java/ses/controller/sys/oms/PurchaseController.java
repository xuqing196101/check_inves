package ses.controller.sys.oms;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseInfo;
import ses.model.oms.util.CommUtils;
import ses.model.oms.util.CommonConstant;
import ses.service.oms.PurchaseServiceI;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title: PurchaseController
 * @Description: 
 * @author: Tian Kunfeng
 * @date: 2016-9-20上午11:46:15
 */
@Controller
@Scope("prototype")
@RequestMapping("/purchase")
public class PurchaseController {
	@Autowired
	private PurchaseServiceI purchaseServiceI;
	@RequestMapping("list")
	public String list(Model model,@ModelAttribute PageInfo page,@ModelAttribute PurchaseInfo purchaseInfo){
		//每页显示十条
		PageHelper.startPage(page.getPageNum(),CommonConstant.PAGE_SIZE);
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(purchaseInfo.getRelName()!=null&&!purchaseInfo.equals("")){
			map.put("relName", purchaseInfo.getRelName());
		}
		List<PurchaseInfo> purchaseList = purchaseServiceI.findPurchaseList(map);
		page = new PageInfo(purchaseList);
		model.addAttribute("purchaseList",purchaseList);

		//分页标签
		String pagesales = CommUtils.getTranslation(page,"purchase/list.do");
		model.addAttribute("pagesql", pagesales);
		model.addAttribute("purchaseInfo", purchaseInfo);
		return "ses/oms/purchase/list";
	}
	
	
}
