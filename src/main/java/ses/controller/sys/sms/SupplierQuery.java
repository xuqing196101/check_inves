package ses.controller.sys.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.Supplier;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierService;

@Controller
@Scope("prototype")
@RequestMapping("/supplierQuery")
public class SupplierQuery {
	
	@Autowired
	private SupplierAuditService supplierAuditService;

	/**
	 * @Title: highmaps
	 * @author Song Biaowei
	 * @date 2016-9-22 上午10:21:18  
	 * @Description: 供应商分布
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("highmaps")
	public String highmaps(Model model){
		StringBuffer sb = new StringBuffer("");
		Map<String,String> myMap=getMap();
		//调用供应商查询方法 List<Supplier>
		List<Supplier> listSupplier=supplierAuditService.supplierList(null, null);
		//开始循环 判断地址是否
		Map<String,Integer> map= new HashMap<String,Integer>(40);
		List<String> list=getAllProvince();
		for(Supplier supplier:listSupplier){
			for(String str:list){
				int count=1;
				if(supplier.getAddress().indexOf(str)!=-1){
					if(map.get(str)==null){
						map.put(myMap.get(str), count);
					}else{
						map.put(str,map.get(str)+1);
					}
				}
			}
		}
		for (Object o : map.keySet()) { 
			sb.append("{'hc-key':'").
			append(o).
			append("','value':").
			append(map.get(o)).
			append("},").append("\n");
			;
		}
		String highMapStr=null;
		if(sb.length()>0){
			highMapStr=sb.deleteCharAt(sb.length()-1).toString();
		}
		model.addAttribute("data", highMapStr);
		return "ses/sms/supplier_query/all_supplier";
	}
	
	/**
	 * @Title: findSupplierByPriovince
	 * @author Song Biaowei
	 * @date 2016-9-22 上午10:21:32  
	 * @Description: 每个省的供应商列表
	 * @param @param supplier
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("findSupplierByPriovince")
	public String findSupplierByPriovince(Supplier supplier,Model model){
		List<Supplier> listSupplier=supplierAuditService.supplierList(supplier, null);
		model.addAttribute("listSupplier", listSupplier);
		return "ses/sms/supplier_query/select_supplier_by_province";
	}
	
	/**
	 * @Title: showSupplierInfo
	 * @author Song Biaowei
	 * @date 2016-9-22 上午10:21:45  
	 * @Description: 供应商查询 
	 * @param @param supplier
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("showSupplierInfo")
	public String showSupplierInfo(Supplier supplier,Model model){
		supplier.setId("83398A4D748E43CCA1007FDCF5007009");
		List<Supplier> listSupplier=supplierAuditService.supplierList(supplier, 1);
		model.addAttribute("supplier", listSupplier.get(0));
		return "ses/sms/supplier_query/basic_info";
	}
	
	/**
	 * @Title: selectByCategory
	 * @author Song Biaowei
	 * @date 2016-9-27 上午11:19:28  
	 * @Description: 按照品目查询供应商 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("selectByCategory")
	public String selectByCategory(Supplier supplier,Model model){
		List<Supplier> listSupplier=supplierAuditService.supplierList(supplier, null);
		model.addAttribute("listSupplier", listSupplier);
		return "ses/sms/supplier_query/select_by_category";
	}
	
	public static List<String> getAllProvince(){
		List<String> list=new ArrayList<String>();
		list.add("吉林省");
		list.add("天津市");
		list.add("安徽省");
		list.add("山东省");
		list.add("山西省");
		list.add("新疆维吾尔自治区");
		list.add("河北省");
		list.add("河南省");
		list.add("湖南省");
		list.add("甘肃省");
		
		list.add("福建省");
		list.add("贵州省");
		list.add("重庆市");
		list.add("江苏省");
		list.add("湖北省");
		list.add("内蒙古自治区");
		list.add("广西壮族自治区");
		list.add("黑龙江省");
		list.add("云南省");
		list.add("辽宁省");
		
		list.add("香港特别行政区");
		list.add("浙江省");
		list.add("上海市");
		list.add("北京市");
		list.add("广东省");
		list.add("澳门特别行政区");
		list.add("西藏自治区");
		list.add("陕西省");
		list.add("四川省");
		list.add("海南省");
		
		list.add("宁夏回族自治区");
		list.add("青海省");
		list.add("江西省");
		list.add("台湾省");
		return list;
	}
	
	public static Map<String,String> getMap(){
		Map<String,String> myMap= new HashMap<String,String>(40);
		myMap.put("吉林省","cn-jl");        
		myMap.put("天津市","cn-tj");        
		myMap.put("安徽省","cn-ah");        
		myMap.put("山东省","cn-sd");        
		myMap.put("山西省","cn-sx");        
		myMap.put("新疆维吾尔自治区","cn-xj");
		myMap.put("河北省","cn-hb");        
		myMap.put("河南省","cn-he");        
		myMap.put("湖南省","cn-hn");        
		myMap.put("甘肃省","cn-gs");        
		myMap.put("福建省","cn-fj");        
		myMap.put("贵州省","cn-gz");        
		myMap.put("重庆市","cn-cq");        
		myMap.put("江苏省","cn-js");        
		myMap.put("湖北省","cn-hu");        
		myMap.put("内蒙古自治区","cn-nm");  
		myMap.put("广西壮族自治区","cn-gx"); 
		myMap.put("黑龙江省","cn-hl");      
		myMap.put("云南省","cn-yn");        
		myMap.put("辽宁省","cn-ln");        
		myMap.put("香港特别行政区","cn-6668"); 
		myMap.put("浙江省","cn-zj");        
		myMap.put("上海市","cn-sh");        
		myMap.put("北京市","cn-bj");        
		myMap.put("广东省","cn-gd");        
		myMap.put("澳门特别行政区","cn-3681"); 
		myMap.put("西藏自治区","cn-xz");    
		myMap.put("陕西省","cn-sa");        
		myMap.put("四川省","cn-sc");        
		myMap.put("海南省","cn-ha");        
		myMap.put("宁夏回族自治区","cn-nx"); 
		myMap.put("青海省","cn-qh");        
		myMap.put("江西省","cn-jx");        
		myMap.put("台湾省","tw-tw");    
		return myMap;
	}
}
