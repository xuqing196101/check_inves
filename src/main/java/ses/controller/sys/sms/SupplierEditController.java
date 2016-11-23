package ses.controller.sys.sms;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

import ses.model.bms.Area;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierEdit;
import ses.model.sms.SupplierReason;
import ses.service.bms.AreaServiceI;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.TodosService;
import ses.service.sms.SupplierAudReasonService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierEditService;
import ses.service.sms.SupplierService;
import ses.util.FtpUtil;
import ses.util.PropUtil;

import com.github.pagehelper.PageInfo;
import common.constant.Constant;

@Controller
@Scope("prototype")
@RequestMapping("/supplier_edit")
public class SupplierEditController extends BaseSupplierController{
	
	@Autowired
	private SupplierEditService supplierEditService;
	
	@Autowired
	private TodosService todosService;
	
	@Autowired
	private SupplierAudReasonService supplierAudReasonService; 
	
	@Autowired
	private SupplierAuditService supplierAuditService;
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private AreaServiceI areaService;
	
	/**
	 * @Title: registerStart
	 * @author Song Biaowei
	 * @date 2016-10-17 下午6:38:59  
	 * @Description: list展示修改记录 
	 * @param @param se
	 * @param @param request
	 * @param @param page
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value="list")
	public String registerStart(SupplierEdit se,HttpServletRequest request,Integer page,Model model){
		User user1=(User) request.getSession().getAttribute("loginUser");
		se.setRecordId(user1.getTypeId());
		List<SupplierEdit> seList=supplierEditService.findAll(se,page==null?1:page);
		request.setAttribute("seList", new PageInfo<>(seList));
		model.addAttribute("id", user1.getTypeId());
		return "ses/sms/supplier_apply_edit/list";
	}
	
	/**
	 * @Title: register
	 * @author Song Biaowei
	 * @date 2016-10-17 下午6:39:58  
	 * @Description: 增加一条记录
	 * @param @param id
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value="add")
	public String register(HttpServletRequest request,String id,Model model){
		Supplier supplier=supplierAuditService.supplierById(id);
		supplier.setAddress(getAddressName(supplier.getAddress()));
		request.getSession().setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		request.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		model.addAttribute("suppliers", supplier);
		return "ses/sms/supplier_apply_edit/add";
	}
	
	/**
	 * @Title: registerEnd
	 * @author Song Biaowei
	 * @date 2016-10-17 下午6:42:02  
	 * @Description: 保存
	 * @param @param se
	 * @param @param request
	 * @param @return
	 * @param @throws IOException      
	 * @return String
	 */
	@RequestMapping(value="save")
	public String registerEnd(SupplierEdit se,HttpServletRequest request) throws IOException{
		User user1=(User) request.getSession().getAttribute("loginUser");
		se.setRecordId(se.getId());
		se.setId(null);
		se.setCreateDate(new Timestamp(new Date().getTime()));
		//现在不用了
		//this.setSupplierUpload(request, se);
		se.setStatus((short)0);
		supplierEditService.insertSelective(se);
		Todos todo=new Todos();
		//自己的id
		todo.setSenderId(user1.getId());
		//代办机构id
		if(user1.getOrg()!=null){
			todo.setOrgId(user1.getOrg().getId());
		}
		//权限Id
		todo.setPowerId(PropUtil.getProperty("gysdb"));
		//待办类型 供应商
		todo.setUndoType((short)1);
		//标题
		todo.setName("供应商变更审核");
		//逻辑删除 0未删除 1已删除
		todo.setIsDeleted((short)0);
		todo.setCreatedAt(new Date());
		todo.setUrl("supplier_edit/audit.html?id="+se.getId());
	    todosService.insert(todo);
		return "redirect:list.html";
	}
	
	/**
	 * @Title: aduit
	 * @author Song Biaowei
	 * @date 2016-10-17 下午6:40:32  
	 * @Description: 审核
	 * @param @param id
	 * @param @param model
	 * @param @param req
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value="audit")
	public String aduit(String id,Model model,HttpServletRequest req){
		//修改后的
		SupplierEdit se=supplierEditService.selectByPrimaryKey(id);
		//修改前的
		Supplier supplier=supplierAuditService.supplierById(se.getRecordId());
		supplier.setAddress(getAddressName(supplier.getAddress()));
		Supplier result=supplierEditService.getResult(se, supplier);
		req.getSession().setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		req.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		req.getSession().setAttribute("supplierId_edit", se.getId());
		req.getSession().setAttribute("result",result);
		model.addAttribute("currSupplier", se);
		return "ses/sms/supplier_apply_edit/audit";
	}
	
	/**
	 * @Title: auditEnd
	 * @author Song Biaowei
	 * @date 2016-10-17 下午6:40:50  
	 * @Description: 审核完成
	 * @param @param id
	 * @param @param auditStatus
	 * @param @param model
	 * @param @param req
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value="auditEnd")
	public String auditEnd(String id,Short auditStatus,Model model,HttpServletRequest req){
		SupplierEdit se=new SupplierEdit();
		se.setId(id);
		se.setStatus(auditStatus);
		if(auditStatus==1){
			this.copyToSupplier(se.getId());
			supplierEditService.updateByPrimaryKey(se);
		}else{
			supplierEditService.updateByPrimaryKey(se);
		}
		todosService.updateIsFinish("supplier_edit/audit.html?id="+id);
		return "redirect:/login/index.html";
	}
	
	/**
	 * @Title: view
	 * @author Song Biaowei
	 * @date 2016-10-17 下午6:42:35  
	 * @Description: 查看
	 * @param @param id
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value="view")
	public String view(String id,Model model,HttpServletRequest request){
		SupplierEdit se=supplierEditService.selectByPrimaryKey(id);
		model.addAttribute("suppliers",se );
		request.getSession().setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		request.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		return "ses/sms/supplier_apply_edit/view";
	}
	
	/**
	 * @Title: reasonList
	 * @author Song Biaowei
	 * @date 2016-10-17 下午6:41:12  
	 * @Description: 查看问题汇总
	 * @param @param sr
	 * @param @param request
	 * @param @param page
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value="reasonList")
	public String reasonList(SupplierReason sr,HttpServletRequest request,Integer page,Model model){
		List<SupplierReason> srList=supplierAudReasonService.findAll(sr);
		model.addAttribute("srList", srList);
		return "ses/sms/supplier_apply_edit/audits";
	}
	
	/**
	 * @Title: saveReason
	 * @author Song Biaowei
	 * @date 2016-10-17 下午6:41:35  
	 * @Description: ajax保存理由 
	 * @param @param sr
	 * @param @param request
	 * @param @throws IOException      
	 * @return void
	 */
	@RequestMapping(value="saveReason")
	@ResponseBody
	public void saveReason(SupplierReason sr,HttpServletRequest request) throws IOException{
		supplierAudReasonService.insertSelective(sr);
	}
	
	public void setSupplierUpload(HttpServletRequest request, SupplierEdit supplier) throws IOException {
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
					if (str.equals("taxCertFile")) {
						supplier.setTaxCert(newfileName);
					} else if (str.equals("billCertFile")) {
						supplier.setBillCert(newfileName);
					} else if (str.equals("securityCertFile")) {
						supplier.setSecurityCert(newfileName);
					} else if (str.equals("breachCertFile")) {
						supplier.setBreachCert(newfileName);
					} else if(str.equals("businessCertFile")){
						supplier.setBusinessCert(newfileName);
					}
				}
			}
		}
	}
	public static String getRootPath(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath("/").split("\\\\")[0] + "/" + PropUtil.getProperty("file.upload.path.supplier");
	}
	
	public void copyToSupplier(String seId){
		SupplierEdit supplierEdit=supplierEditService.selectByPrimaryKey(seId);
		Supplier supplier=supplierAuditService.supplierById(supplierEdit.getRecordId());
		//第一次保存的时候要给原始数据保存一条，用来后面做对比
		SupplierEdit supplierEdit1=supplierEditService.setToSupplierEdit(supplier);
	    //开始改变供应商的值
		Supplier supplier1=supplierEditService.setToSupplier(supplierEdit);
		Area area = new Area();
		area.setName(supplier1.getAddress().split(",")[1]);
		List<Area> listArea = areaService.listByArea(area);
		supplier1.setAddress(listArea.get(0).getId());
		supplier1.setId(supplier.getId());
	    SupplierEdit supplierEdit2=new SupplierEdit();
	    supplierEdit2.setRecordId(supplier.getId());
	    supplierEdit2.setStatus((short)4);
	    //如果是第一次审核通过的时候要给原始数据保存下来（没有保存状态）
		if(supplierEditService.getAllbySupplierId(supplierEdit2).size()==0){
			supplierEdit1.setStatus((short)4);
			supplierEdit1.setCreateDate(new Timestamp(new Date().getTime()));
			supplierEditService.insertSelective(supplierEdit1);
		}
		supplierService.perfectBasic(supplier1);
	}
	
	/**
	 * @Title: getAddressName
	 * @author Song Biaowei
	 * @date 2016-11-23 下午4:22:56  
	 * @Description: 根据地址ID获取中文名称
	 * @param @param str
	 * @param @return      
	 * @return String
	 */
	public String getAddressName(String str){
		String provinceName="";
		String cityName="";
		Area area=areaService.listById(str);
		if(area!=null){
			cityName=area.getName();
			Area area1=areaService.listById(area.getParentId());
			if(area1!=null){
				provinceName=area1.getName();
			}
		}
		return provinceName+","+cityName;
	}
}
