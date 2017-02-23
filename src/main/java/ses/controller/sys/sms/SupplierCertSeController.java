package ses.controller.sys.sms;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import ses.model.bms.Area;
import ses.model.bms.DictionaryData;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierDictionaryData;
import ses.service.bms.AreaServiceI;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.QualificationService;
import ses.service.sms.SupplierCertSeService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.FtpUtil;
import ses.util.PropUtil;

import com.alibaba.fastjson.JSON;
import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;

@Controller
@Scope("prototype")
@RequestMapping(value = "/supplier_cert_se")
public class SupplierCertSeController extends BaseSupplierController {
	
	@Autowired
	private SupplierService supplierService;// 供应商基本信息

	@Autowired
	private SupplierCertSeService supplierCertSeService;
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
	private UploadService uploadService;
	
	@Autowired
	private QualificationService qualificationService;
	
	@Autowired
	private AreaServiceI areaService;
	
	
	@RequestMapping(value = "/add_cert_se")
	public String addCertSell(Model model, String matSeId, String supplierId) {
		model.addAttribute("matSeId", matSeId);
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("uuid", UUID.randomUUID().toString().toUpperCase().replace("-", ""));
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		
		return "ses/sms/supplier_register/add_cert_se";
	}
	
	@RequestMapping(value = "/save_or_update_cert_se" ,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String saveOrUpdateCertSell(HttpServletRequest request, SupplierCertServe supplierCertSe, String supplierId,HttpServletResponse response) throws IOException {
		// this.setCertSeUpload(request, supplierCertSe);
	
//		Supplier supplier = supplierService.get(supplierId);
//		request.getSession().setAttribute("defaultPage", "tab-4");
//		request.getSession().setAttribute("currSupplier", supplier);
//		request.getSession().setAttribute("jump.page", "professional_info");
//		return "redirect:../supplier/page_jump.html";
//		return "ses/sms/supplier_register/supplier_type";
		
		Map<String, Object> map = valudateServer(supplierCertSe);
		boolean bool = (boolean) map.get("bool");
		if(bool==true){
			supplierCertSeService.saveOrUpdateCertSe(supplierCertSe);
			SupplierCertServe server = supplierCertSeService.queryById(supplierCertSe.getId());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String sdate = sdf.format(server.getExpStartDate());
			String edate = sdf.format(server.getExpEndDate());
		    map.put("sdate", sdate);
		    map.put("edate", edate);
			map.put("server", server);
		} 
//		response.getWriter().print(map);
		String json = JSON.toJSONString(map);
		return json;
	}
	
	@RequestMapping(value = "back_to_professional")
	public String backToSellfessional(HttpServletRequest request, String supplierId) {
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-4");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	@RequestMapping(value = "delete_cert_se")
	public String deleteCertSell(Model model, String certSeIds, String supplierId) {
		supplierCertSeService.deleteCertSe(certSeIds);
		Supplier supplier = supplierService.get(supplierId);
//		request.getSession().setAttribute("defaultPage", "tab-4");
		model.addAttribute("currSupplier", supplier);
//		request.getSession().setAttribute("jump.page", "professional_info");
//		return "redirect:../supplier/page_jump.html";
        List<DictionaryData> list = DictionaryDataUtil.find(6);
        for(int i=0;i<list.size();i++){
            String code = list.get(i).getCode();
            if(code.equals("GOODS")){
                list.remove(list.get(i));
            }
        }
        model.addAttribute("supplieType", list);
        List<DictionaryData> wlist = DictionaryDataUtil.find(8);
        model.addAttribute("wlist", wlist);
        //初始化供应商注册附件类型
        model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
        model.addAttribute("sysKey",  Constant.SUPPLIER_SYS_KEY);
        List<Area> areaList = areaService.findRootArea();
        model.addAttribute("rootArea", areaList);
        model.addAttribute("typeList", qualificationService.findList(null, null, 4));
		return "ses/sms/supplier_register/supplier_type";	
	}
	
	public void setCertSeUpload(HttpServletRequest request, SupplierCertServe supplierCertSe) throws IOException {
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
						supplierCertSe.setAttach(newfileName);
					} 
				}
			}
		}
	}
	
	public Map<String,Object> valudateServer(SupplierCertServe supplierCertSe){
		Map<String,Object> map=new HashMap<String,Object>();
		boolean bool=true;

		if(supplierCertSe.getName()==null||supplierCertSe.getName().length()>12){
			map.put("name", "不能为空");
			bool=false;
		}
		if(supplierCertSe.getLevelCert()==null||supplierCertSe.getLevelCert().length()>12){
			map.put("level", "不能为空");
			bool=false;
		}
		if(supplierCertSe.getLicenceAuthorith()==null||supplierCertSe.getLicenceAuthorith().length()>12){
			map.put("authorith", "不能为空");
			bool=false;
		}
		if(supplierCertSe.getExpStartDate()==null){
			map.put("sDate", "不能为空");
			bool=false;
		}
		if(supplierCertSe.getExpEndDate()==null){
			map.put("eDate", "不能为空");
			bool=false;
		}
		SupplierDictionaryData supplierDictionary = dictionaryDataServiceI.getSupplierDictionary();
		List<UploadFile> list = uploadService.getFilesOther(supplierCertSe.getId(), supplierDictionary.getSupplierServeCert(), String.valueOf(Constant.SUPPLIER_SYS_KEY));
		if(list.size()<=0){
			map.put("file", "文件未上传");
			bool=false;
		}
		map.put("bool", bool);
		return map;
		
	}
}
