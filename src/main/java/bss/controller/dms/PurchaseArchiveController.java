/**
 * 
 */
package bss.controller.dms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import bss.service.dms.PurchaseArchiveServiceI;

import ses.controller.sys.sms.BaseSupplierController;

/**
 * @Title:PurchaseArchiveController
 * @Description: 
 * @author ZhaoBo
 * @date 2016-10-19上午9:08:12
 */
@Controller
@Scope("prototype")
@RequestMapping("/purchaseArchive")
public class PurchaseArchiveController extends BaseSupplierController{
	@Autowired
	private PurchaseArchiveServiceI purchaseArchiveService;
	
	@RequestMapping("/archiveList")
	public String archiveList(Model model){
		
		return "bss/dms/purchaseArchive/list";
	}
}
