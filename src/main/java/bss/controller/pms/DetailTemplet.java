package bss.controller.pms;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import bss.service.pms.PurchaseRequiredService;
import ses.model.bms.DictionaryData;
import ses.model.oms.PurchaseDep;
import ses.model.sms.Supplier;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.util.DictionaryDataUtil;

@Controller
@RequestMapping("/templet")
public class DetailTemplet {

	
	@Autowired
	private PurchaseOrgnizationServiceI purchserOrgnaztionService;
	
	@Autowired
	private PurchaseRequiredService purchaseRequiredService;
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
	@ResponseBody
	public ModelAndView template(Integer index, Model model){
		
		
		ModelAndView moeldeAndView=new ModelAndView("/bss/pms/purchaserequird/detail");
		 String id = UUID.randomUUID().toString().replaceAll("-", "");
		 model.addAttribute("id", id);
		 model.addAttribute("index", index);
		 //附件id
		 String attId = DictionaryDataUtil.getId("PURCHASE_DETAIL");
		 moeldeAndView.addObject("attId", attId);
		 
		 //采购机构
		 HashMap<String,Object> map=new HashMap<String,Object>();
		 map.put("typeName", "1");
		 List<PurchaseDep> list2 = purchserOrgnaztionService.findPurchaseDepList(map);
//		 model.addAttribute("requires", list2);
		 //采购方式
		 List<DictionaryData> list = DictionaryDataUtil.find(5);
		 model.addAttribute("list2", list);
		 
		 List<Supplier> suppliers = purchaseRequiredService.queryAllSupplier();
		 model.addAttribute("suppliers", suppliers);
		 return moeldeAndView;
	}
}
