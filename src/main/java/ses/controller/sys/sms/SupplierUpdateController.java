package ses.controller.sys.sms;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.sms.ApplyEdit;
import ses.model.sms.Supplier;
import ses.service.bms.TodosService;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierUpdateService;
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
		Supplier supplier=supplierService.login(user.getTypeId());
		ae.setSupplierId(user.getTypeId());
		ae.setCreatedAt(new Timestamp(new Date().getTime()));
		supplierUpdateService.insertSelective(ae);
		Todos todo=new Todos();
		//自己的id
		todo.setSenderId(user.getTypeId());
		//代办人id
		todo.setReceiverId(supplier.getProcurementId());
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
	 * @Description: 查找 
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
			return "";
		}else{
			supplierUpdateService.updateByPrimaryKey(ae);
			return "redirect:../login/index.html";
		}
		
	}
	
}

