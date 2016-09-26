package ses.controller.sys.sms;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
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

import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.sms.ApplyEdit;
import ses.model.sms.Supplier;
import ses.service.bms.TodosService;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierUpdateService;
import ses.util.PropUtil;
/**
 * @Title: SupplierUpdateController
 * @Description:供应商变更 
 * @author: Song Biaowei
 * @date: 2016-9-20下午7:20:43
 */

@Controller
@Scope("prototype")
@RequestMapping("/supplierUpdate")
public class SupplierUpdateController {
	@Autowired
	private SupplierUpdateService supplierUpdateService;
	@Autowired
	private TodosService todosService;
	@Autowired
	private SupplierService supplierService;
	
	@RequestMapping("shenqing")
	public String shenqing(Model model){
		return "ses/sms/supplier_apply_edit/add";
	}
	
	/**
	 * @Title: save
	 * @author Song Biaowei
	 * @date 2016-9-21 下午2:24:38  
	 * @Description: 保存供应商变更申请信息
	 * @param @param ae
	 * @param @param model
	 * @param @param req
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("save")
	public String save(ApplyEdit ae,Model model,HttpServletRequest req){
		User user=(User) req.getSession().getAttribute("loginUser");
		Supplier supplier=supplierService.get(user.getTypeId());
		ae.setSupplierId(user.getTypeId());
		ae.setCreatedAt(new Timestamp(new Date().getTime()));
		supplierUpdateService.insertSelective(ae);
		Todos todo=new Todos();
		//自己的id
		todo.setSenderId(user.getTypeId());
		//代办人id
		todo.setReceiverId(supplier.getProcurementDepId());
		//待办类型0 未审核 1 已审核 2 审核中
		todo.setUndoType((short)0);
		//标题
		todo.setName("供应商信息变更申请表");
		//逻辑删除 0未删除 1已删除
		todo.setIsDeleted((short)0);
		todo.setCreatedAt(new Date());
		todo.setUrl("supplierUpdate/show.html?id="+user.getTypeId());
		todosService.insert(todo);
		return "redirect:../login/index.html";
	}
	
	/**
	 * @Title: show
	 * @author Song Biaowei
	 * @date 2016-9-21 下午2:24:58  
	 * @Description: 查找供应商变更申请信息
	 * @param @param id
	 * @param @param model
	 * @param @param req
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("show")
	public String show(String id,Model model,HttpServletRequest req){
		ApplyEdit ae=supplierUpdateService.selectByPrimaryKey(id);
		model.addAttribute("ae", ae);
		return "ses/sms/supplier_apply_edit/audit";
	}
	
	/**
	 * @Title: audit
	 * @author Song Biaowei
	 * @date 2016-9-21 下午2:25:10  
	 * @Description: 审核 
	 * @param @param ae
	 * @param @param model
	 * @param @param req
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("audit")
	public String audit(ApplyEdit ae,Model model,HttpServletRequest req){
		//通过的话就跳转到变更 页面
		if(ae.getAuditStatus()==1){
			supplierUpdateService.updateByPrimaryKey(ae);
			todosService.updateIsFinish(ae.getId());
			return "redirect:edit.html";
		}else{
			supplierUpdateService.updateByPrimaryKey(ae);
			return "redirect:../login/index.html";
		}
		
	}
	
	/**
	 * @Title: edit
	 * @author Song Biaowei
	 * @date 2016-9-23 下午6:18:17  
	 * @Description:供应商变更跳转到编辑页面
	 * @param @param id
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("edit")
	public String edit(String id,Model model){
		Supplier supplier=supplierService.get("83398A4D748E43CCA1007FDCF5007009");
		model.addAttribute("supplier", supplier);
		return "ses/sms/supplier_apply_edit/editSupplier";
	}
	
	/**
	 * @Title: saveSupplier
	 * @author Song Biaowei
	 * @date 2016-9-23 下午6:17:29  
	 * @Description: 保存变更信息
	 * @param @param supplier
	 * @param @param model
	 * @param @return      
	 * @return String
	 * @throws IOException 
	 */
	@RequestMapping("saveSupplier")
	public String saveSupplier(Supplier supplier,Model model,HttpServletRequest request) throws IOException{
		this.setSupplierUpload(request, supplier);
		supplierService.perfectBasic(supplier);
		return "redirect:../login/index.html";
	}
	
	public void setSupplierUpload(HttpServletRequest request, Supplier supplier) throws IOException {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator<String> its = multiRequest.getFileNames();
			while (its.hasNext()) {
				String str = its.next();
				MultipartFile file = multiRequest.getFile(str);
				if (file != null && file.getSize() > 0) {
					String path = getRootPath(request) + file.getOriginalFilename();
					file.transferTo(new File(path));
					if (str.equals("taxCertFile")) {
						supplier.setTaxCert(path);
					} else if (str.equals("billCertFile")) {
						supplier.setBillCert(path);
					} else if (str.equals("securityCertFile")) {
						supplier.setSecurityCert(path);
					} else if (str.equals("breachCertFile")) {
						supplier.setBreachCert(path);
					}
				}
			}
		}
	}
	public static String getRootPath(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath("/").split("\\\\")[0] + "/" + PropUtil.getProperty("file.upload.path.supplier");
	}
}

