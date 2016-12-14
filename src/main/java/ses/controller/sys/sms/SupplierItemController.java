package ses.controller.sys.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import bss.controller.base.BaseController;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.DictionaryData;
import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierDictionaryData;
import ses.model.sms.SupplierItem;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;

@Controller
@Scope("prototype")
@RequestMapping(value = "/supplier_item")
public class SupplierItemController extends BaseController{
	
	@Autowired
	private SupplierItemService supplierItemService;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private OrgnizationServiceI orgnizationServiceI;
	
	@Autowired
	private AreaServiceI areaService;
	
	@Autowired
	private UploadService uploadService;
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
	private CategoryService categoryService;
	/**
	 * @Title: saveOrUpdate
	 * @author: Wang Zhaohua
	 * @date: 2016-11-2 下午3:27:32
	 * @Description: 保存或者更新品目
	 * @param: @param request
	 * @param: @param supplierItem
	 * @param: @param jsp
	 * @param: @param defaultPage
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "save_or_update")
	public String saveOrUpdate(HttpServletRequest request, SupplierItem supplierItem, String flag, Model model) {
		
		
		
		
		supplierItemService.saveOrUpdate(supplierItem);
		
		Supplier supplier = supplierService.get(supplierItem.getSupplierId());
			HashMap<String, Object> map = new HashMap<String, Object>();
			if(supplier.getProcurementDepId()!=null){
				map.put("id", supplier.getProcurementDepId());
				List<Orgnization> listOrgnizations1 = orgnizationServiceI.findOrgnizationList(map);
				Orgnization orgnization = listOrgnizations1.get(0);
				List<Area> city = areaService.findAreaByParentId(orgnization.getProvinceId());
				model.addAttribute("orgnization", orgnization);
				model.addAttribute("city", city);
				model.addAttribute("listOrgnizations1", listOrgnizations1);
	
			}
			List<Area> privnce = areaService.findRootArea();
			
			model.addAttribute("privnce", privnce);
			Map<String, Object> maps = supplierService.getCategory(supplier.getId());
			model.addAttribute("server", maps.get("server"));
			model.addAttribute("product", maps.get("product"));
			model.addAttribute("sale", maps.get("sale"));
			model.addAttribute("project", maps.get("project"));
			
			List<DictionaryData> list = DictionaryDataUtil.find(6);
			for(int i=0;i<list.size();i++){
				 String code = list.get(i).getCode();
				 if(code.equals("GOODS")){
					 list.remove(list.get(i));
				 }
			}
			
			List<DictionaryData> wlist =DictionaryDataUtil.find(8);
			model.addAttribute("wlist", wlist);
			model.addAttribute("supplieType", list);
			
			 
		// 页面跳转
		model.addAttribute("currSupplier", supplier);
		
		
//		supplierService.get(id)
/*		Supplier supplier = supplierService.get(supplierItem.getSupplierId());
		
		if ("items".equals(jsp))
			request.getSession().setAttribute("defaultPage", defaultPage);
		else
			request.getSession().removeAttribute("defaultPage");
		
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", jsp);*/
//		return  "redirect:../supplier/page_jump.html";
		if(flag.equals("3")){
			return "ses/sms/supplier_register/supplier_type";
		}
		
		
		if(flag.equals("2")){
			return "ses/sms/supplier_register/items";	
		}
	
		boolean bool = validataItem(supplierItem);
		if(bool==false){
			model.addAttribute("err_item", "请上传产品目录近对应的近三年文件");
			return "ses/sms/supplier_register/items";	
		}
		 
			return "ses/sms/supplier_register/procurement_dep";	
	 
		
		
	}
	
	@RequestMapping(value = "getCategory")
	public String getCategory(String categoryId,Model model){
		String id = DictionaryDataUtil.getId("SUPPLIER_CATEGORY");
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("sysKey",  Constant.SUPPLIER_SYS_KEY);
		model.addAttribute("typeId", id);
		return "ses/sms/supplier_register/category_uploadfile";	
	}
	
	public boolean validataItem(SupplierItem supplierItem){
		boolean bool=true;
		if(supplierItem.getCategoryId()!=null){
			String ids[] = supplierItem.getCategoryId().split(",");
//			SupplierDictionaryData supplierDictionary = dictionaryDataServiceI.getSupplierDictionary();
			for(String s:ids){
				List<Category> category = categoryService.findTreeByPid(s);
				if(category.size()<1){
					List<UploadFile> list = uploadService.getFilesOther(s, null,"1");
					if(list.size()<1){
						bool=false;
					}
				}
			}
		}
	
		return bool;
	}
	
	
}
