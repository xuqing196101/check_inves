package bss.controller.pms;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import common.annotation.CurrentUser;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.PurchaseRequiredService;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;

@Controller
@RequestMapping("/templet")
public class DetailTemplet {

	@Autowired
	private PurchaseOrgnizationServiceI purchserOrgnaztionService;

	@Autowired
	private PurchaseRequiredService purchaseRequiredService;

	@Autowired
	private OrgnizationServiceI orgnizationServiceI;

	/**
	 * 
	 * @Title: template @Description: 明细模板 author: Li
	 * Xiaoxiao @param @return @return ModelAndView @throws
	 */
	@RequestMapping("/detail")
	@ResponseBody
	public ModelAndView template(@CurrentUser User user, Integer index, Model model, String type) {

		if (user.getOrg() != null) {
			Orgnization orgnization = orgnizationServiceI.getOrgByPrimaryKey(user.getOrg().getId());

			model.addAttribute("orgName", orgnization.getShortName());
			model.addAttribute("orgType", user.getOrg().getTypeName());
		}
		ModelAndView moeldeAndView = new ModelAndView("/bss/pms/purchaserequird/detail");
		if ("edit".equals(type)) {
			moeldeAndView = new ModelAndView("/bss/pms/purchaserequird/edit_detail");
		}
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		model.addAttribute("id", id);
		model.addAttribute("index", index);
		// 附件id
		String attId = DictionaryDataUtil.getId("PURCHASE_DETAIL");
		moeldeAndView.addObject("attId", attId);

		// 采购机构
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("typeName", "1");
		List<PurchaseDep> list2 = purchserOrgnaztionService.findPurchaseDepList(map);
		// model.addAttribute("requires", list2);
		// 采购方式
		List<DictionaryData> list = DictionaryDataUtil.find(5);
		model.addAttribute("list2", list);
		//
		// List<Supplier> suppliers = purchaseRequiredService.queryAllSupplier();
		// model.addAttribute("suppliers", suppliers);
		model.addAttribute("uuId", WfUtil.createUUID());
		return moeldeAndView;
	}

	@RequestMapping("/uploaddetail")
	@ResponseBody
	public ModelAndView upload(@CurrentUser User user, Integer index, Model model, String prList, String type) {
		JSONArray json1 = JSONArray.fromObject(prList);
		List<PurchaseRequired> lists = (List<PurchaseRequired>) JSONArray.toCollection(json1, PurchaseRequired.class);

		ModelAndView moeldeAndView = new ModelAndView("/bss/pms/purchaserequird/uploaddetail");
		if ("edit".equals(type)) {
			moeldeAndView = new ModelAndView("/bss/pms/purchaserequird/edit_uploaddetail");
		}
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		model.addAttribute("id", id);
		model.addAttribute("index", index);
		// 附件id
		String attId = DictionaryDataUtil.getId("PURCHASE_DETAIL");
		moeldeAndView.addObject("attId", attId);

		List<DictionaryData> list = DictionaryDataUtil.find(5);
		model.addAttribute("list2", list);
		model.addAttribute("lists", lists);

		Orgnization org = orgnizationServiceI.getOrgByPrimaryKey(user.getOrg().getId());
		model.addAttribute("shortName", org.getShortName());

		return moeldeAndView;
	}

}
