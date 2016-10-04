package ses.controller.sys.sms;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.Decoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSe;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSe;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierStockholder;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierService;

@Controller
@Scope("prototype")
@RequestMapping("/supplierQuery")
public class SupplierQueryController extends BaseSupplierController{
	
	@Autowired
	private SupplierAuditService supplierAuditService;
	
	@Autowired
	private SupplierService supplierService;

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
	public String highmaps(Supplier sup,Model model,Integer status){
		StringBuffer sb = new StringBuffer("");
		//调用供应商查询方法 List<Supplier>
		if(status!=null){
			sup.setStatus(status);
		}
		List<Supplier> listSupplier=supplierAuditService.supplierList(sup, null);
		//开始循环 判断地址是否
		Map<String,Integer> map= new HashMap<String,Integer>(40);
		List<String> list=getAllProvince();
		for(Supplier supplier:listSupplier){
			for(String str:list){
				int count=1;
				if(supplier.getAddress().indexOf(str)!=-1){
					if(map.get(str)==null){
						map.put(str, count);
					}else{
						map.put(str,map.get(str)+1);
					}
				}else{
					map.put(str, 0);
				}
			}
		}
		for (Object o : map.keySet()) { 
			sb.append(o).append(map.get(o));
		}
		String highMapStr=null;
		if(sb.length()>0){
			highMapStr=sb.toString();
		}
		model.addAttribute("data", highMapStr);
		if(status!=null){
			return "ses/sms/supplier_query/all_ruku_supplier";
		}else{
			return "ses/sms/supplier_query/all_supplier";
		}
		
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
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("findSupplierByPriovince")
	public String findSupplierByPriovince(Supplier supplier,Integer page,Model model) throws UnsupportedEncodingException{
		supplier.setAddress(URLDecoder.decode(supplier.getAddress(),"UTF-8"));
		List<Supplier> listSupplier=supplierAuditService.supplierList(supplier, page==null?1:page);
		for(Supplier sup:listSupplier){
			List<SupplierAudit> listAudit=supplierAuditService.selectByPrimaryKey(sup.getId());
			for(SupplierAudit sa:listAudit){
				if(sa.getStatus()==3){
					sup.setPassDate(sa.getCreatedAt());
				}
			}
		}
		model.addAttribute("address", supplier.getAddress());
		model.addAttribute("listSupplier", new PageInfo<>(listSupplier));
		return "ses/sms/supplier_query/select_supplier_by_province";
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
	public String selectByCategory(Supplier supplier,Integer page,Model model){
		List<Supplier> listSupplier=supplierAuditService.supplierList(supplier, page==null?1:page);
		model.addAttribute("listSupplier", new PageInfo<>(listSupplier));
		return "ses/sms/supplier_query/select_by_category";
	}
	
	@RequestMapping("selectByCategoryByAjax")
	@ResponseBody
	public PageInfo<Supplier>  selectByCategoryByAjax(Supplier supplier,Integer page,Model model){
		List<Supplier> listSupplier=supplierAuditService.supplierList(supplier, page==null?1:page);
		PageInfo<Supplier>  pager=new PageInfo<>(listSupplier);
		return pager;
	}
	
	/**
	 * @Title: essentialInformation
	 * @author Song Biaowei
	 * @date 2016-9-29 上午11:36:49  
	 * @Description: 基本信息 
	 * @param @param request
	 * @param @param supplier
	 * @param @param supplierId
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("essential")
	public String essentialInformation(HttpServletRequest request,Supplier supplier,String supplierId) {
		supplier = supplierAuditService.supplierById(supplierId);
		request.setAttribute("suppliers", supplier);
		return "ses/sms/supplier_query/supplierInfo/essential";
	}
	
	/**
	 * @Title: financialInformation
	 * @author Song Biaowei
	 * @date 2016-9-29 上午11:37:15  
	 * @Description: 财务信息 
	 * @param @param request
	 * @param @param supplierFinance
	 * @param @param supplier
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("financial")
	public String financialInformation(HttpServletRequest request,SupplierFinance supplierFinance,Supplier supplier) {
		String supplierId = supplierFinance.getSupplierId();
		List<SupplierFinance> list = supplierAuditService.supplierFinanceBySupplierId(supplierId);
		request.setAttribute("supplierId", supplierId);
		request.setAttribute("financial", list);

		return "ses/sms/supplier_query/supplierInfo/financial";
	}

	/**
	 * @Title: shareholderInformation
	 * @author Song Biaowei
	 * @date 2016-9-29 上午11:37:50  
	 * @Description: 股东信息
	 * @param @param request
	 * @param @param supplierStockholder
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("shareholder")
	public String shareholderInformation(HttpServletRequest request,SupplierStockholder supplierStockholder) {
		String supplierId = supplierStockholder.getSupplierId();
		List<SupplierStockholder> list = supplierAuditService.ShareholderBySupplierId(supplierId);
		request.setAttribute("supplierId", supplierId);
		request.setAttribute("shareholder", list);
		return "ses/sms/supplier_query/supplierInfo/shareholder";
	}
	
	/**
	 * @Title: materialProduction
	 * @author Song Biaowei
	 * @date 2016-9-29 上午11:38:07  
	 * @Description: 物资生产型专业信息  
	 * @param @param request
	 * @param @param supplierMatPro
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("materialProduction")
	public String materialProduction(HttpServletRequest request,SupplierMatPro supplierMatPro) {
		String supplierId = supplierMatPro.getSupplierId();
		/*List<SupplierCertPro> materialProduction = supplierService.get(supplierId).getSupplierMatPro().getListSupplierCertPros();*/
		//资质资格证书信息
		List<SupplierCertPro> materialProduction = supplierAuditService.findBySupplierId(supplierId);
		//供应商组织机构人员,产品研发能力,产品生产能里,质检测试登记信息
		/*supplierMatPro = supplierAuditService.findSupplierMatProBysupplierId(supplierId);*/
		supplierMatPro =supplierService.get(supplierId).getSupplierMatPro();
		
		request.setAttribute("supplierId", supplierId);	
		request.setAttribute("materialProduction",materialProduction);
		request.setAttribute("supplierMatPros", supplierMatPro);
		return "ses/sms/supplier_query/supplierInfo/material_production";
	}
	
	/**
	 * @Title: materialSales
	 * @author Song Biaowei
	 * @date 2016-9-29 上午11:39:08  
	 * @Description: 物资销售专业信息 
	 * @param @param request
	 * @param @param supplierMatSell
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("materialSales")
	public String materialSales(HttpServletRequest request,SupplierMatSell supplierMatSell){
		String supplierId = supplierMatSell.getSupplierId();
		//资质资格证书
		List<SupplierCertSell> supplierCertSell=supplierAuditService.findCertSellBySupplierId(supplierId);
		//供应商组织机构和人员
		supplierMatSell = supplierService.get(supplierId).getSupplierMatSell();
		request.setAttribute("supplierCertSell", supplierCertSell);
		request.setAttribute("supplierMatSells", supplierMatSell);
		request.setAttribute("supplierId", supplierId);
		return "ses/sms/supplier_query/supplierInfo/material_sales";
	}
	
	/**
	 * @Title: engineeringInformation
	 * @author Song Biaowei
	 * @date 2016-9-29 上午11:39:22  
	 * @Description: 工程专业信息  
	 * @param @param request
	 * @param @param supplierMatEng
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("engineering")
	public String engineeringInformation(HttpServletRequest request,SupplierMatEng supplierMatEng){
		String supplierId = supplierMatEng.getSupplierId();
		//资质资格证书信息
		List<SupplierCertEng> supplierCertEng= supplierAuditService.findCertEngBySupplierId(supplierId);
		//资质资格信息
		List<SupplierAptitute> supplierAptitute = supplierAuditService.findAptituteBySupplierId(supplierId);
		//组织结构和注册人人员
		supplierMatEng = supplierAuditService.findMatEngBySupplierId(supplierId);
		request.setAttribute("supplierCertEng", supplierCertEng);
		request.setAttribute("supplierAptitutes", supplierAptitute);
		request.setAttribute("supplierMatEngs",supplierMatEng);
		request.setAttribute("supplierId", supplierId);
		return "ses/sms/supplier_query/supplierInfo/engineering";
	}
	
	/**
	 * @Title: serviceInformation
	 * @author Song Biaowei
	 * @date 2016-9-29 上午11:39:43  
	 * @Description: 服务专业信息  
	 * @param @param request
	 * @param @param supplierMatSe
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("serviceInformation")
	public String serviceInformation(HttpServletRequest request,SupplierMatSe supplierMatSe){
		String supplierId = supplierMatSe.getSupplierId();
		//资质证书信息
		List<SupplierCertSe> supplierCertSe = supplierAuditService.findCertSeBySupplierId(supplierId);
		//组织结构和人员
		supplierMatSe = supplierAuditService.findMatSeBySupplierId(supplierId);
		request.setAttribute("supplierCertSes", supplierCertSe);
		request.setAttribute("supplierMatSes", supplierMatSe);
		request.setAttribute("supplierId", supplierId);
		return "ses/sms/supplier_query/supplierInfo/service_information";
	}
	
	 @RequestMapping("/downLoadFile")
	  public ResponseEntity<byte[]> downLoadFile(String fileName,HttpServletRequest request) throws UnsupportedEncodingException{
		  fileName=URLDecoder.decode(fileName,"UTF-8");
		 String filePath=getFilePath(request);
		 return  supplierAuditService.downloadFile(filePath,fileName);
	  }
	  
	public static List<String> getAllProvince(){
		List<String> list=new ArrayList<String>();
		list.add("吉林");
		list.add("天津");
		list.add("安徽");
		list.add("山东");
		list.add("山西");
		list.add("新疆");
		list.add("河北");
		list.add("河南");
		list.add("湖南");
		list.add("甘肃");
		
		list.add("福建");
		list.add("贵州");
		list.add("重庆");
		list.add("江苏");
		
		list.add("内蒙古");
		list.add("广西");
		list.add("黑龙江");
		list.add("云南");
		list.add("辽宁");
		
		list.add("香港");
		list.add("浙江");
		list.add("上海");
		list.add("北京");
		list.add("广东");
		list.add("澳门");
		list.add("西藏");
		list.add("陕西");
		list.add("四川");
		list.add("海南");
		
		list.add("宁夏");
		list.add("青海");
		list.add("江西");
		list.add("台湾");
		list.add("湖北");
		return list;
	}
}
