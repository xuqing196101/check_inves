package ses.controller.sys.sms;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierAptitute;
import ses.service.sms.SupplierAptituteService;
import ses.service.sms.SupplierService;
import ses.util.FtpUtil;
import ses.util.PropUtil;

@Controller
@Scope("prototype")
@RequestMapping(value = "/supplier_aptitute")
public class SupplierAptituteController extends BaseSupplierController {
	@Autowired
	private SupplierService supplierService;// 供应商基本信息

	@Autowired
	private SupplierAptituteService supplierAptituteService;// 供应商资质资格
	
	/**
	 * @Title: addAptitute
	 * @author: Wang Zhaohua
	 * @date: 2016-10-13 下午3:56:20
	 * @Description: 添加资质资格
	 * @param: @param model
	 * @param: @param matEngId
	 * @param: @param supplierId
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "add_aptitute")
	public String addAptitute(Model model, String matEngId, String supplierId) {
		model.addAttribute("matEngId", matEngId);
		model.addAttribute("supplierId", supplierId);
		return "ses/sms/supplier_register/add_aptitute";
	}
	
	/**
	 * @Title: saveOrUpdateAptitute
	 * @author: Wang Zhaohua
	 * @date: 2016-10-13 下午3:56:35
	 * @Description: 修改或更新
	 * @param: @param request
	 * @param: @param supplierAptitute
	 * @param: @param supplierId
	 * @param: @return
	 * @param: @throws IOException
	 * @return: String
	 */
	@RequestMapping(value = "save_or_update_aptitute")
	public String saveOrUpdateAptitute(HttpServletRequest request, SupplierAptitute supplierAptitute, String supplierId) throws IOException {
		// this.setAptituteUpload(request, supplierAptitute);
		supplierAptituteService.saveOrUpdateAptitute(supplierAptitute);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-3");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	/**
	 * @Title: backToSellfessional
	 * @author: Wang Zhaohua
	 * @date: 2016-10-13 下午3:57:09
	 * @Description: 返回专业信息页面
	 * @param: @param request
	 * @param: @param supplierId
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "back_to_professional")
	public String backToSellfessional(HttpServletRequest request, String supplierId) {
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-3");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	/**
	 * @Title: deleteAptitute
	 * @author: Wang Zhaohua
	 * @date: 2016-10-13 下午3:57:34
	 * @Description: 删除
	 * @param: @param request
	 * @param: @param aptituteIds
	 * @param: @param supplierId
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "delete_aptitute")
	public String deleteAptitute(HttpServletRequest request, String aptituteIds, String supplierId) {
		supplierAptituteService.deleteAptitute(aptituteIds);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-3");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	/**
	 * @Title: setAptituteUpload
	 * @author: Wang Zhaohua
	 * @date: 2016-10-13 下午3:57:45
	 * @Description: 设置文件上传
	 * @param: @param request
	 * @param: @param supplierAptitute
	 * @param: @throws IOException
	 * @return: void
	 */
	public void setAptituteUpload(HttpServletRequest request, SupplierAptitute supplierAptitute) throws IOException {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {// 检查form中是否有enctype="multipart/form-data"
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;// 将request变成多部分request
			Iterator<String> its = multiRequest.getFileNames();// 获取multiRequest 中所有的文件名
			while (its.hasNext()) {// 下面循环遍历
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
					if (str.equals("attachCertFile")) {
						supplierAptitute.setAttachCert(newfileName);
					} 
				}
			}
		}
	}
}
