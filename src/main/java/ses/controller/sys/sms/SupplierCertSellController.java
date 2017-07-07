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
import ses.model.bms.Area;
import ses.model.bms.DictionaryData;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierDictionaryData;
import ses.service.bms.AreaServiceI;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.QualificationService;
import ses.service.sms.SupplierCertSellService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.FtpUtil;
import ses.util.PropUtil;

@Controller
@Scope("prototype")
@RequestMapping(value = "/supplier_cert_sell")
public class SupplierCertSellController extends BaseSupplierController {

	@Autowired
	private SupplierService supplierService;// 供应商基本信息

	@Autowired
	private SupplierCertSellService supplierCertSellService;
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;

	@Autowired
	private UploadService uploadService;
    
    @Autowired
    private QualificationService qualificationService;
    
    @Autowired
    private AreaServiceI areaService;
	
	@RequestMapping(value = "add_cert_sell")
	public String addCertSell(Model model, String matSellId, String supplierId) {
		model.addAttribute("matSellId", matSellId);
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		model.addAttribute("uuid", UUID.randomUUID().toString().toUpperCase().replace("-", ""));
		return "ses/sms/supplier_register/add_cert_sell";
	}

	@RequestMapping(value = "save_or_update_cert_sell" ,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String saveOrUpdateCertSell(HttpServletRequest request, SupplierCertSell supplierCertSell, String supplierId) throws IOException {
		
		Map<String, Object> map = validateSale(supplierCertSell);
		boolean bool=(boolean) map.get("bool");
		if(bool==true){
			supplierCertSellService.saveOrUpdateCertSell(supplierCertSell);
			SupplierCertSell sell = supplierCertSellService.queryById(supplierCertSell.getId());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String sdate = sdf.format(sell.getExpStartDate());
			String edate = sdf.format(sell.getExpEndDate());
		    map.put("sdate", sdate);
		    map.put("edate", edate);
		    map.put("sell", sell);
		}
		return  JSON.toJSONString(map);
	}

	@RequestMapping(value = "back_to_professional")
	public String backToSellfessional(HttpServletRequest request, String supplierId) {
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-2");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}

/*	@RequestMapping(value = "delete_cert_sell")
	public String deleteCertSell(Model model, String certSellIds, String supplierId) {
		supplierCertSellService.deleteCertSell(certSellIds);
		Supplier supplier = supplierService.get(supplierId);
//		request.getSession().setAttribute("defaultPage", "tab-2");
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
        model.addAttribute("typeList", qualificationService.findList(null, Integer.MAX_VALUE,null, 4));
		return "ses/sms/supplier_register/supplier_type";	
	}*/
	
	/**
	 * 异步删除物资销售资质证书
	 * @param certSellIds
	 * @return
	 */
	@RequestMapping(value = "delete_cert_sell")
	@ResponseBody
	public String deleteCertSell(String certSellIds) {
		supplierCertSellService.deleteCertSell(certSellIds);
		return "ok";	
	}
	
	public void setCertSellUpload(HttpServletRequest request, SupplierCertSell supplierCertSell) throws IOException {
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
						supplierCertSell.setAttach(newfileName);
					} 
				}
			}
		}
	}
	
	public Map<String,Object> validateSale( SupplierCertSell supplierCertSell){
		Map<String,Object> map=new HashMap<String,Object>();
		boolean bool=true;
		if(supplierCertSell.getName()==null||supplierCertSell.getName().length()>12){
			map.put("name", "不能为空");
			bool=false;
		}
		if(supplierCertSell.getLevelCert()==null||supplierCertSell.getLevelCert().length()>12){
			map.put("lever", "不能为空");
			bool=false;
		}
		if(supplierCertSell.getLicenceAuthorith()==null||supplierCertSell.getLicenceAuthorith().length()>12){
			map.put("authorith", "不能为空");
			bool=false;
		}
		if(supplierCertSell.getExpStartDate()==null){
			map.put("sDate", "不能为空");
			bool=false;
		}
		
		if(supplierCertSell.getExpEndDate()==null){
			map.put("eDate", "不能为空");
			bool=false;
		}
		SupplierDictionaryData supplierDictionary = dictionaryDataServiceI.getSupplierDictionary();
		List<UploadFile> list = uploadService.getFilesOther(supplierCertSell.getId(), supplierDictionary.getSupplierSellCert(), String.valueOf(Constant.SUPPLIER_SYS_KEY));
		if(list.size()<=0){
			map.put("file", "文件未上传");
			bool=false;
		}
		map.put("bool", bool);
		
		
		return map;
		
	}
}
