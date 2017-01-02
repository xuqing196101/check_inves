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
	public String saveOrUpdate(HttpServletRequest request, SupplierItem supplierItem, String flag, Model model,String supplierTypeIds, String clickFlag) {
	    
	    // 判断是否是取消选中
		if ("0".equals(clickFlag) && flag.equals("4")) {
		    supplierItemService.deleteItems(supplierItem);
		} else if(flag.equals("4")){
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
		    HashMap<String, Object> map1 = new HashMap<String, Object>();
	        map1.put("typeName", "1");
	        List<PurchaseDep> list1 = purchaseOrgnizationService
	                .findPurchaseDepList(map1);  
	        model.addAttribute("allPurList", list1);
			return "ses/sms/supplier_register/procurement_dep";	
		}
		//查询所有的三级品目生产
		List<Category> list2 = getSupplier(supplier.getId(),supplierTypeIds);
		//根据品目id查询所有的证书信息
	   List<QualificationBean> list3 = supplierService.queryCategoyrId(list2, 2);
 
		//查询所有的三级品目销售
		List<Category> listSlae = getSale(supplier.getId(),supplierTypeIds);
		//根据品目id查询所有的证书信息
	   List<QualificationBean> saleQua = supplierService.queryCategoyrId(listSlae, 3);
	   
	   
		//查询所有的三级目录工程
		List<Category> listProject = getSale(supplier.getId(),supplierTypeIds);
		//根据品目id查询所有的工证书
	   List<QualificationBean> projectQua= supplierService.queryCategoyrId(listProject, 1);
	   
		//查询所有的三级品目服务
		List<Category> listService = getSale(supplier.getId(),supplierTypeIds);
		//根据品目id查询所有的服务证书信息
	   List<QualificationBean> serviceQua= supplierService.queryCategoyrId(listService, 1);
	   
	   //生产证书
	   List<Qualification> qaList=new ArrayList<Qualification>();
	   List<Qualification> saleList=new ArrayList<Qualification>();
	   List<Qualification> projectList=new ArrayList<Qualification>();
	   List<Qualification> serviceList=new ArrayList<Qualification>();
	   
	   if(list3!=null&&list3.size()>0){
		   for(QualificationBean qb:list3){
			   qaList.addAll(qb.getList());
		   }
	   }
	   //销售
	   if(saleQua!=null&&saleQua.size()>0){
		   for(QualificationBean qb:saleQua){
			   saleList.addAll(qb.getList());
		   }
	   } 
	   //工程
	   if(projectQua!=null&&projectQua.size()>0){
		   for(QualificationBean qb:projectQua){
			   projectList.addAll(qb.getList());
		   }
	   } 
	   //服务
	   if(serviceQua!=null&&serviceQua.size()>0){
		   for(QualificationBean qb:serviceQua){
			   serviceList.addAll(qb.getList());
		   }
	   } 
	   
	  //生产
	   StringBuffer sbUp=new StringBuffer("");
	   StringBuffer sbShow=new StringBuffer("");
	   int len=qaList.size()+1;
	   for(int i=1;i<len;i++){
		   sbUp.append("pUp"+i+",");
			sbShow.append("pShow"+i+",");
		 
	   }
	/*   StringBuffer saleUp=new StringBuffer("");
	   StringBuffer saleShow=new StringBuffer("");*/
	   //销售
	   int slaelen=saleList.size()+1;
	   for(int i=1;i<slaelen;i++){
		   sbUp.append("saleUp"+i+",");
		   sbShow.append("saleShow"+i+",");
		 
	   }
	   if(projectList!=null&&projectList.size()>0){
		   int projectlen=projectList.size()+1;
		   for(int i=1;i<projectlen;i++){
			   sbUp.append("projectUp"+i+",");
			   sbShow.append("projectShow"+i+",");
			 
		   } 
	   }
	
	   if(serviceList!=null&&serviceList.size()>0){
		   int serverlen=serviceList.size()+1;
		   for(int i=1;i<serverlen;i++){
			   sbUp.append("serverUp"+i+",");
			   sbShow.append("serverShow"+i+",");
			 
		   } 
	   }
	   
	   
	   /*saleUp.append(sbUp);
	   saleShow.append(sbShow);*/
	   model.addAttribute("saleUp", sbUp.toString());
	   model.addAttribute("saleShow", sbShow.toString());
	/*	model.addAttribute("sbUp", sbUp);
		model.addAttribute("sbShow", sbShow);*/
		model.addAttribute("cateList",  list3);
		model.addAttribute("saleQua", saleQua);
		model.addAttribute("projectQua", projectQua);
		model.addAttribute("serviceQua", serviceQua);
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
//		List<Category> category = supplierItemService.getCategory(supplierId);
//		model.addAttribute("category", category);
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
    public String getCategory(String code,String supplierId,String stype){
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
    		   List<SupplierItem> category = supplierItemService.getCategory(supplierId, categoryId,stype);
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
//		List<Category> list = supplierItemService.getCategory(supplierId);
//		if(list.size()>0){
//			return "1";
//		}else{
			return "0";
//		}
	}
	
	//生产所有的三级目录
	public List<Category> getSupplier(String supplierId,String code){
		List<Category> categoryList=new ArrayList<Category>();
		String[] types = code.split(",");
		for(String s:types){
			String   categoryId="";
			   if (s != null ) {
	               if(s.equals("PRODUCT")){
	                   categoryId = DictionaryDataUtil.getId("GOODS");
	                   List<SupplierItem> category = supplierItemService.getCategory(supplierId, categoryId,s);
	    		       for(SupplierItem c:category){
	    		    	 Category cate= categoryService.selectByPrimaryKey(c.getCategoryId());
	    		    	 cate.setId(c.getId());
	    		    	 categoryList.add(cate);
	    	             }
	                 }
		       }
		}
		  return  categoryList;
	}
	//销售
	public List<Category>  getSale(String supplierId,String code){
		List<Category> categoryList=new ArrayList<Category>();
		
		String[] types = code.split(",");
		for(String s:types){
			String   categoryId="";
			   if (s != null ) {
	               if(s.equals("SALES")){
	                   categoryId = DictionaryDataUtil.getId("GOODS");
	    		       List<SupplierItem> category = supplierItemService.getCategory(supplierId, categoryId,s);
	    		       for(SupplierItem c:category){
	    		    	 Category cate= categoryService.selectByPrimaryKey(c.getCategoryId());
	    		    	 categoryList.add(cate);
	    	            }
	              }
		      }
		}
		
		return categoryList;
	}
	
	
	//工程
	public List<Category>  getProject(String supplierId,String code){
		List<Category> categoryList=new ArrayList<Category>();
		
		String[] types = code.split(",");
		for(String s:types){
			String   categoryId="";
			   if (s != null ) {
	               if(s.equals("PROJECT") ){
	                   categoryId = DictionaryDataUtil.getId("PROJECT");
	                   List<SupplierItem> category = supplierItemService.getCategory(supplierId, categoryId,s);
	    		      for(SupplierItem c:category){
	    		    	 Category cate= categoryService.selectByPrimaryKey(c.getCategoryId());
	    		    	 categoryList.add(cate);
	    		 
	    	            }
	             }
		    }
		
		}
		
		return categoryList;
	}
	
	//服务
	public List<Category>  getServer(String supplierId,String code){
		List<Category> categoryList=new ArrayList<Category>();
		
		String[] types = code.split(",");
		for(String s:types){
			String   categoryId="";
			   if (s != null ) {
	               if(s.equals("SERVICE")){
	    		   List<SupplierItem> category = supplierItemService.getCategory(supplierId, categoryId,s);
	    		     for(SupplierItem c:category){
	    		    	 Category cate= categoryService.selectByPrimaryKey(c.getCategoryId());
	    		    	 categoryList.add(cate);
	    	            }
	               }
			 }
		}
		return categoryList;
	}
	
	
	
}
