package bss.controller.prms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bss.model.prms.PackageFirstAudit;
import bss.service.prms.PackageFirstAuditService;

/**
 * 
  * <p>Title:PackageFirstAuditController </p>
  * <p>Description: </p>包关联初审项
  * <p>Company: yggc </p> 
  * @author ShaoYangYang
  * @date 2016年10月17日下午4:05:21
 */
@Controller
@RequestMapping("packageFirstAudit")
public class PackageFirstAuditController {

	@Autowired
	private PackageFirstAuditService service;
	/**
	 * 
	  * @Title: relate
	  * @author ShaoYangYang
	  * @date 2016年10月17日 下午4:07:47  
	  * @Description: TODO 执行关联
	  * @param       
	  * @return void
	 */
	@RequestMapping("relate")
	public String relate(String chkItem,PackageFirstAudit packageFirstAudit,RedirectAttributes attr){
		
		service.delete(packageFirstAudit.getPackageId());
		//获取选中的初审项id
		String[] firstAuditIds = chkItem.split(",");
		//循环插入
		for (int i = 0; i < firstAuditIds.length; i++) {
			packageFirstAudit.setFirstAuditId(firstAuditIds[i]);
			service.save(packageFirstAudit);
		}
	
		attr.addAttribute("projectId", packageFirstAudit.getProjectId());
		attr.addAttribute("flag", "success");
		return "redirect:/firstAudit/toPackageFirstAudit.html";
		
	}
	
}
