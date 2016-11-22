package ses.controller.sys.sms;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;

import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierDictionaryData;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.sms.SupplierCertProService;
import ses.service.sms.SupplierService;
import ses.util.FtpUtil;
import ses.util.PropUtil;

@Controller
@Scope("prototype")
@RequestMapping(value = "/supplier_cert_pro")
public class SupplierCertProController extends BaseSupplierController {
	
	@Autowired
	private SupplierService supplierService;// 供应商基本信息
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
	private SupplierCertProService supplierCertProService;
	
	@Autowired
	private UploadService uploadService;
	
	@RequestMapping(value = "add_cert_pro")
	public String addCertPro(Model model, SupplierCertPro supplierCertPro, String supplierId,Integer sign) {
		model.addAttribute("supplierCertPro", supplierCertPro);
		model.addAttribute("uuid", UUID.randomUUID().toString().toUpperCase().replace("-", ""));
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("sign", sign);
		return "ses/sms/supplier_register/add_cert_pro";
	}
	
	@RequestMapping(value = "/save_or_update_cert_pro" ,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String saveOrUpdateCertPro(HttpServletRequest request, SupplierCertPro supplierCertPro, String supplierId,Model model) throws IOException {
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("currSupplier", supplier);
		Map<String, Object> map = valudatePro(supplierCertPro);
		boolean bool = (boolean) map.get("bool");
		if(bool==false){
			supplierCertProService.saveOrUpdateCertPro(supplierCertPro);
		} 
			return JSON.toJSONString(map);
 
	}
	
	@RequestMapping(value = "back_to_professional")
	public String backToProfessional(HttpServletRequest request, String supplierId) {
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-1");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	@RequestMapping(value = "delete_cert_pro")
	public String deleteCertPro(HttpServletRequest request, String certProIds, String supplierId) {
		supplierCertProService.deleteCertPro(certProIds);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-1");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	public void setCertProUpload(HttpServletRequest request, SupplierCertPro supplierCertPro) throws IOException {
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
					if (str.equals("attachFile")) {
						supplierCertPro.setAttach(newfileName);
					} 
				}
			}
		}
	}
	
	
	public  Map<String,Object> valudatePro(SupplierCertPro supplierCertPro){
		Map<String,Object> map=new HashMap<String,Object>();
		boolean bool=true;
		if(supplierCertPro.getName()==null){
			map.put("name", "不能为空");
			bool=false;
		}
		if(supplierCertPro.getLevelCert()==null){
			map.put("level", "不能为空");
			bool=false;
		}
		if(supplierCertPro.getLicenceAuthorith()==null){
			map.put("authorith", "不能为空");
			bool=false;
		}
		if(supplierCertPro.getExpStartDate()==null){
			map.put("sDate", "不能为空");
			bool=false;
		}
		if(supplierCertPro.getExpEndDate()==null){
			map.put("eDate", "不能为空");
			bool=false;
		}
		
		SupplierDictionaryData supplierDictionary = dictionaryDataServiceI.getSupplierDictionary();
		List<UploadFile> list = uploadService.getFilesOther(supplierCertPro.getId(), supplierDictionary.getSupplierProCert(), String.valueOf(Constant.SUPPLIER_SYS_KEY));
		if(list.size()<=0){
			map.put("file", "文件未上传");
			bool=false;
		}
		
		map.put("bool", bool);
		return map;
	}
}
