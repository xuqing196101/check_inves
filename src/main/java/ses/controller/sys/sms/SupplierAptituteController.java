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
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierDictionaryData;
import ses.service.bms.AreaServiceI;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.QualificationService;
import ses.service.sms.SupplierAptituteService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
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
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
	private UploadService uploadService;
    
    @Autowired
    private QualificationService qualificationService;
    
    @Autowired
    private AreaServiceI areaService;
	
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
	@RequestMapping(value = "/add_aptitute")
	public String addAptitute(Model model, String matEngId, String supplierId) {
		model.addAttribute("matEngId", matEngId);
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("uuid", UUID.randomUUID().toString().toUpperCase().replace("-", ""));
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
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
	@RequestMapping(value = "/save_or_update_aptitute",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String saveOrUpdateAptitute(HttpServletRequest request, SupplierAptitute supplierAptitute, String supplierId,Model model) throws IOException {
		// this.setAptituteUpload(request, supplierAptitute);
		
//		Supplier supplier = supplierService.get(supplierId);
//		request.getSession().setAttribute("currSupplier", supplier);
		 Map<String, Object> map = validateAptitute(supplierAptitute);
		boolean bool = (boolean) map.get("bool");
		if(bool==true){
			supplierAptituteService.saveOrUpdateAptitute(supplierAptitute);
			SupplierAptitute aptitute = supplierAptituteService.queryById(supplierAptitute.getId());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String adate = sdf.format(aptitute.getAptituteDate());
			String change = sdf.format(aptitute.getAptituteChangeAt());
			map.put("adate", adate);
			map.put("aptitute", aptitute);
			map.put("change", change);
		} 
		String json = JSON.toJSONString(map);
		return json;
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
	public String deleteAptitute(Model model, String aptituteIds, String supplierId) {
		supplierAptituteService.deleteAptitute(aptituteIds);
		Supplier supplier = supplierService.get(supplierId);
//		request.getSession().setAttribute("defaultPage", "tab-3");
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
	
	/**
	 * 
	* @Title: validateAptitute
	* @Description: 校验数据是否正确
	* author: Li Xiaoxiao 
	* @param @param aptitute
	* @param @return     
	* @return Map<String,Object>     
	* @throws
	 */
	public Map<String,Object> validateAptitute(  SupplierAptitute aptitute){
		Map<String,Object> map=new HashMap<String,Object>();
		boolean bool=true;
		if(aptitute.getCertType()==null||aptitute.getCertType().length()>12){
			map.put("type", "不能为空");
			bool=false;
		}
		if(aptitute.getAptituteCode()==null||aptitute.getAptituteCode().length()>30||!aptitute.getAptituteCode().matches("^[0-9a-zA-Z]*$")){
			map.put("code", "不能为空或不允许输入汉字");
			bool=false;
		}
		if(aptitute.getAptituteSequence()==null||aptitute.getAptituteCode().length()>200||!aptitute.getAptituteCode().matches("^[0-9a-zA-Z]*$")){
			map.put("sequence", "不能为空");
			bool=false;
		}
		if(aptitute.getProfessType()==null||aptitute.getProfessType().length()>12){
			map.put("proType", "不能为空或者字符串过长");
			bool=false;
		}
		if(aptitute.getAptituteLevel()==null||aptitute.getAptituteLevel().length()>12){
			map.put("level", "不能为空");
			bool=false;
		}
		if(aptitute.getAptituteContent()==null||aptitute.getAptituteContent().length()>80){
			map.put("content", "不能为空或者字符串过长");
			bool=false;
		}
		if(aptitute.getAptituteCode()==null||aptitute.getAptituteContent().length()>80){
			map.put("aptituteCode", "不能为空");
			bool=false;
		}
		if(aptitute.getAptituteDate()==null){
			map.put("aDate", "不能为空");
			bool=false;
		}
		if(aptitute.getAptituteWay()==null){
			map.put("way", "不能为空");
			bool=false;
		}
		if(aptitute.getAptituteChangeAt()==null){
			map.put("changeAt", "不能为空");
			bool=false;
		}
		if(aptitute.getAptituteChangeReason()==null||aptitute.getAptituteContent().length()>80){
			map.put("reason", "不能为空或者字符串过长");
			bool=false;
		}
		SupplierDictionaryData supplierDictionary = dictionaryDataServiceI.getSupplierDictionary();
		List<UploadFile> list = uploadService.getFilesOther(aptitute.getId(), supplierDictionary.getSupplierEngCertFile(), String.valueOf(Constant.SUPPLIER_SYS_KEY));
		if(list.size()<=0){
			map.put("file", "文件未上传");
			bool=false;
		}
		
		 map.put("bool", bool);
		 return map;
		
	}
}
