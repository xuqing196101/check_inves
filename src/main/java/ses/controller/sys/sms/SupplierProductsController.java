package ses.controller.sys.sms;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierProducts;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.SupplierProductsService;
import ses.service.sms.SupplierService;
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
	public String perfectProducts(HttpServletRequest request, Supplier supplier, String jsp) throws IOException {
		supplier = supplierService.get(supplier.getId());
		
		if ("procurement_dep".equals(jsp)) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (supplier.getAddress() != null && !"".equals(supplier.getAddress()) && !",".equals(supplier.getAddress())) {
				map.put("name", "%" + supplier.getAddress().split(",")[0] + "%");
			}
			List<Orgnization> listOrgnizations1 = orgnizationServiceI.findOrgnizationList(map);
			map.clear();
			if (supplier.getAddress() != null && !"".equals(supplier.getAddress()) && !",".equals(supplier.getAddress())) {
				map.put("notName", "%" + supplier.getAddress().split(",")[0] + "%");
			}
			List<Orgnization> listOrgnizations2 = orgnizationServiceI.findOrgnizationList(map);
			request.getSession().setAttribute("listOrgnizations1", listOrgnizations1);
			request.getSession().setAttribute("listOrgnizations2", listOrgnizations2);
		}

		// 页面跳转
		request.getSession().setAttribute("currSupplier", supplier);
		
		request.getSession().setAttribute("jump.page", jsp);
		
		return "redirect:../supplier/page_jump.html";
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
