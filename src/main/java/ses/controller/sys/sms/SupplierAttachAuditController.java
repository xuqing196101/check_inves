package ses.controller.sys.sms;

import java.util.Iterator;
import java.util.List;

import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import common.constant.Constant;
import common.utils.ListSortUtil;
import ses.model.bms.Area;
import ses.model.sms.SupplierAddress;
import ses.model.sms.SupplierFinance;
import ses.service.bms.AreaServiceI;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.sms.SupplierAddressService;
import ses.service.sms.SupplierService;

/**
 * 供应商附件审核
 */
@Controller
@Scope("prototype")
@RequestMapping("/supplierAttachAudit")
public class SupplierAttachAuditController {

	@Autowired
	private AreaServiceI areaService; //地区
	
	@Autowired
	private SupplierAddressService supplierAddressService; //生产经营地址
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
	private SupplierService supplierService;
	
	/**
	 * 生产经营地址
	 */
	@RequestMapping(value = "/address")
	public String address(String supplierId, Model model){
		List < SupplierAddress > supplierAddress = supplierAddressService.queryBySupplierId(supplierId);
		List < Area > province = areaService.findRootArea();
		if(!supplierAddress.isEmpty() && supplierAddress.size() > 0 ){
			for(Area a: province) {
				for(SupplierAddress s: supplierAddress) {
					if(a.getId().equals(s.getParentId())) {
						s.setParentName(a.getName());
					}
				}
			}
		}
		model.addAttribute("supplierAddress", supplierAddress);
		
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);

		return "ses/sms/supplier_attach/address";
	}
	
	/**
	 * 只要近三年财务
	 */
	@RequestMapping(value = "/finance")
	public String finance (String supplierId, Model model){
		List < SupplierFinance > supplierFinance = supplierService.get(supplierId, 1).getListSupplierFinances();
		if(supplierFinance != null && supplierFinance.size() > 0){
			// 排序
			ListSortUtil<SupplierFinance> sortList = new ListSortUtil<SupplierFinance>();
			sortList.sort(supplierFinance, "year", "asc");
			// 如果近三年财务信息超过三年，则取最近三年
			if(supplierFinance.size() > 3){
				Iterator<SupplierFinance> it = supplierFinance.iterator();
				int i = supplierFinance.size();
				while(it.hasNext()){
					it.next();
					if(i > 3){
						it.remove();
					}
					i--;
				}
			}
		}
		model.addAttribute("finance", supplierFinance);
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		return "ses/sms/supplier_attach/finance";
	}
}
