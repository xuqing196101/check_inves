package ses.controller.sys.sms;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import ses.model.bms.Area;
import ses.model.bms.DictionaryData;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierProducts;
import ses.service.bms.AreaServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.sms.SupplierProductsService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.FtpUtil;
import ses.util.PropUtil;

@Controller
@Scope("prototype")
@RequestMapping(value = "/supplier_products")
public class SupplierProductsController extends BaseSupplierController {

	@Autowired
	private SupplierService supplierService;// 供应商基本信息

	@Autowired
	private SupplierProductsService supplierProductsService;// 供应商产品
	
	@Autowired
	private OrgnizationServiceI orgnizationServiceI;// 机构
	
	@Autowired
	private AreaServiceI  areaService;
	
	@Autowired
	private PurchaseOrgnizationServiceI purchaseOrgnizationService;
	
	
	/**
	 * @Title: addCertEng
	 * @author: Wang Zhaohua
	 * @date: 2016-11-2 下午5:32:52
	 * @Description: 添加产品页面
	 * @param: @param model
	 * @param: @param supplierProducts
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "add_products")
	public String addCertEng(Model model, SupplierProducts supplierProducts) {
		model.addAttribute("supplierProducts", supplierProducts);
		return "ses/sms/supplier_register/add_products";
	}
	
	/**
	 * @Title: backToEngfessional
	 * @author: Wang Zhaohua
	 * @date: 2016-11-2 下午5:34:27
	 * @Description: 返回产品页面
	 * @param: @param request
	 * @param: @param supplierId
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "back_to_products")
	public String backToEngfessional(HttpServletRequest request, String supplierId) {
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "products");
		return "redirect:../supplier/page_jump.html";
	}
	
	/**
	 * @Title: saveOrUpdateCertEng
	 * @author: Wang Zhaohua
	 * @date: 2016-11-2 下午3:47:33
	 * @Description: 保存或更新产品信息
	 * @param: @param request
	 * @param: @param supplierProducts
	 * @param: @return
	 * @param: @throws IOException
	 * @return: String
	 */
	@RequestMapping(value = "save_or_update_products")
	public String saveOrUpdateCertEng(HttpServletRequest request, SupplierProducts supplierProducts) throws IOException {
		this.setUpload(request, supplierProducts);
		supplierProductsService.saveOrUpdateProducts(supplierProducts);
		Supplier supplier = supplierService.get(supplierProducts.getSupplierId());
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "products");
		return "redirect:../supplier/page_jump.html";
	}
	
	/**
	 * @Title: delete
	 * @author: Wang Zhaohua
	 * @date: 2016-11-2 下午3:47:47
	 * @Description: 删除产品信息
	 * @param: @param request
	 * @param: @param supplierId
	 * @param: @param proIds
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "delete")
	public String delete(HttpServletRequest request, String supplierId, String proIds) {
		supplierProductsService.deleteProducts(proIds);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "products");
		return "redirect:../supplier/page_jump.html";
	}
	
	/**
	 * @Title: perfectProducts
	 * @author: Wang Zhaohua
	 * @date: 2016-11-2 下午3:52:11
	 * @Description: perfectProducts
	 * @param: @param request
	 * @param: @param supplier
	 * @param: @param jsp
	 * @param: @return
	 * @param: @throws IOException
	 * @return: String
	 */
	@RequestMapping(value = "perfect_products")
	public String perfectProducts(HttpServletRequest request, Supplier supplier, String jsp,String flag,Model model) throws IOException {
		  supplier = supplierService.get(supplier.getId());
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
			List<DictionaryData> wlist =DictionaryDataUtil.find(8);
			model.addAttribute("wlist", wlist);
			model.addAttribute("supplieType", list);
			
			 
		// 页面跳转
		model.addAttribute("currSupplier", supplier);
		
		if(flag.equals("prev")){
			return "ses/sms/supplier_register/supplier_type";	
		}else if(flag.equals("store")){
			return "ses/sms/supplier_register/products";	
		}else{
		    HashMap<String, Object> map1 = new HashMap<String, Object>();
	        map1.put("typeName", "1");
	        List<PurchaseDep> list1 = purchaseOrgnizationService
	                .findPurchaseDepList(map1);  
	        model.addAttribute("allPurList", list1);
			return "ses/sms/supplier_register/procurement_dep";	
		}
		
		
		
	}
	
	public void setUpload(HttpServletRequest request, SupplierProducts supplierProducts) throws IOException {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {// 检查form中是否有enctype="multipart/form-data"
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;// 将request变成多部分request
			Iterator<String> its = multiRequest.getFileNames();// 获取multiRequest 中所有的文件名
			while (its.hasNext()) {// 循环遍历
				String str = its.next();
				MultipartFile file = multiRequest.getFile(str);
				String fileName = file.getOriginalFilename();
				if (file != null && file.getSize() > 0) {
					String path = super.getStashPath(request) + fileName;// 获取暂存路径
					file.transferTo(new File(path));// 暂存
					FtpUtil.connectFtp(PropUtil.getProperty("file.upload.path.supplier"));// 连接 ftp 服务器
					String newfileName = FtpUtil.upload(new File(path));// 上传到 ftp 服务器, 获取新的文件名
					FtpUtil.closeFtp();// 关闭 ftp
					super.removeStash(request, fileName);// 移除暂存

					// 上面代码固定, 下面封装名字到对象
					if (str.equals("productPicFile")) {
						supplierProducts.setProductPic(newfileName);
					} else if (str.equals("qrCodeFile")) {
						supplierProducts.setQrCode(newfileName);
					}
				}
			}
		}
	}
}
