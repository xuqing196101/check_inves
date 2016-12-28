package ses.controller.sys.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import bss.controller.base.BaseController;
import ses.formbean.QualificationBean;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierDictionaryData;
import ses.model.sms.SupplierItem;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
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
	
	@Autowired
	private PurchaseOrgnizationServiceI purchaseOrgnizationService;
	
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
	public String saveOrUpdate(HttpServletRequest request, SupplierItem supplierItem, String flag, Model model,String supplierTypeIds) {
		
		
		
		if(flag.equals("4")){
			supplierItemService.saveOrUpdate(supplierItem);
		}
		
		
		Supplier supplier = supplierService.get(supplierItem.getSupplierId());
			HashMap<String, Object> map = new HashMap<String, Object>();
			if(supplier.getProcurementDepId()!=null){
			       map.put("id", supplier.getProcurementDepId());
                   map.put("typeName", "1");
                   // 采购机构
                   List<PurchaseDep> depList = purchaseOrgnizationService .findPurchaseDepList(map);
				if (depList != null && depList.size() >0){
				    Orgnization orgnization = depList.get(0);
	                List<Area> city = areaService.findAreaByParentId(orgnization.getProvinceId());
	                model.addAttribute("orgnization", orgnization);
	                model.addAttribute("city", city);
	                model.addAttribute("listOrgnizations1", depList);
				}
				
	
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
		model.addAttribute("supplierTypeIds", supplierTypeIds);
		if(flag.equals("3")){
			return "ses/sms/supplier_register/supplier_type";
		}
		
		
		if(flag.equals("2")){
			return "ses/sms/supplier_register/items";	
		} 
	 
		//采购机构页面
		if(flag.equals("5")){
			return "ses/sms/supplier_register/procurement_dep";	
		}
		//查询所有的三级品目
		List<Category> list2 = getSupplier(supplier.getId(),supplierTypeIds);
		
	 
		//根据品目id查询所有的证书信息
 
	   List<QualificationBean> list3 = supplierService.queryCategoyrId(list2);
 
	   List<Qualification> qaList=new ArrayList<Qualification>();
	   for(QualificationBean qb:list3){
		   qaList.addAll(qb.getList());
	   }
	   StringBuffer sbUp=new StringBuffer("");
	   StringBuffer sbShow=new StringBuffer("");
	   int len=qaList.size()+1;
	   for(int i=1;i<len;i++){
		   sbUp.append("pUp"+i+",");
			sbShow.append("pShow"+i+",");
			if(len==i){
				sbUp.append("pUp"+i);
				sbShow.append("pShow"+i);
			}
	   }
		model.addAttribute("sbUp", sbUp);
		model.addAttribute("sbShow", sbShow);
		model.addAttribute("cateList", list3);
//		model.addAttribute("len", len);
		return "ses/sms/supplier_register/aptitude"; 
	 
		
		
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
	
	
	/**
	 * 
	* @Title: echoTree
	* @Description: 供应商资质文件维护，点击上一步
	* author: Li Xiaoxiao 
	* @param @param supplierId
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping(value="/category_tree")
	public String echoTree(String supplierId,Model model){
		Supplier supplier = supplierService.get(supplierId);
		// 页面跳转
		model.addAttribute("currSupplier", supplier);
		List<Category> category = supplierItemService.getCategory(supplierId);
		model.addAttribute("category", category);
		return "ses/sms/supplier_register/items";	
	}
	/**
	 * 
	* @Title: getCategory
	* @Description: 查询供应商勾选的三级品目
	* author: Li Xiaoxiao 
	* @param @param code
	* @param @param supplierId
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping(value="/category_type" ,produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getCategory(String code,String supplierId){
		List<CategoryTree> categoryList=new ArrayList<CategoryTree>();
		String   categoryId="";
		   if (code != null ) {
			     DictionaryData type = DictionaryDataUtil.get(code);
               if(code.equals("PRODUCT") || code.equals("SALES")){
                  
                   categoryId = DictionaryDataUtil.getId("GOODS");
               } else {
            	   categoryId = DictionaryDataUtil.getId(code);
               }
               //查询子节点
               CategoryTree ct = new CategoryTree();
               ct.setName(type.getName());
               ct.setId(categoryId);
               ct.setChecked(true);
               ct.setIsParent("true");
               categoryList.add(ct);
               //查询三级节点
    		   List<SupplierItem> category = supplierItemService.getCategory(supplierId, categoryId);
    		     for(SupplierItem c:category){
    		    	 Category cate= categoryService.selectByPrimaryKey(c.getCategoryId());
    	                CategoryTree ct1 = new CategoryTree();
    	                ct1.setName(cate.getName());
    	                ct1.setParentId(cate.getParentId());
    	                ct1.setId(c.getCategoryId());
    	                ct1.setChecked(true);
    	                List<Category> cList = categoryService.findTreeByPid(c.getCategoryId());
    	                if (cList != null && cList.size() > 0){
    	                    ct1.setIsParent("true");
    	                } else {
    	                    ct1.setIsParent("false");
    	                }
    	              
    	                categoryList.add(ct1);
    	            }
           }
	 return JSON.toJSONString(categoryList);
	}
	
	@RequestMapping(value="/getSupplierCate" ,produces = "text/html;charset=UTF-8")
    @ResponseBody
	public String getCategory(String supplierId){
		List<Category> list = supplierItemService.getCategory(supplierId);
		if(list.size()>0){
			return "1";
		}else{
			return "0";
		}
	}
	
	public List<Category> getSupplier(String supplierId,String code){
		List<Category> categoryList=new ArrayList<Category>();
		String[] types = code.split(",");
		for(String s:types){
			String   categoryId="";
			   if (s != null ) {
	               if(s.equals("PRODUCT") || s.equals("SALES")){
	                  
	                   categoryId = DictionaryDataUtil.getId("GOODS");
	               } else {
	            	   categoryId = DictionaryDataUtil.getId(s);
	               }
	    		   List<SupplierItem> category = supplierItemService.getCategory(supplierId, categoryId);
	    		     for(SupplierItem c:category){
	    		    	 Category cate= categoryService.selectByPrimaryKey(c.getCategoryId());
	    		    	 categoryList.add(cate);
	    		 
	    	            }
	           }
		}
	
		  return  categoryList;
	}
	
	
}
