package ses.controller.sys.sms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.sms.Supplier;
import ses.service.bms.UserServiceI;
import ses.service.sms.SupplierService;

import com.github.pagehelper.PageInfo;
/**
 * <p>Title:SupplierDeleteController </p>
 * <p>Description:供应商注销 </p>
 * @date 2017-4-11下午4:00:55
 */
@Controller
@Scope("prototype")
@RequestMapping("/suppliertDelete")
public class SupplierDeleteController {
	
	/**
     * 供应商注册服务层
     */
    @Autowired
    private SupplierService supplierService;
    
    
    @Autowired
    private UserServiceI UserServiceI;
	
	/**
     * @Title: cancellation
     * @date 2017-3-8 下午1:33:37  
     * @Description:供应商注销
     * @param @param supplierIds      
     * @return void
     */
     @RequestMapping(value = "/cancellation")
     @ResponseBody
     public void cancellation(String supplierId){
       /*if(sign == 1){*/
    	   UserServiceI.updateByTypeId(supplierId);
    	   supplierService.updateById(supplierId);
       /*}else{
    	   supplierService.deleteSupplier(supplierId);
       }*/
     }
     
     /**
      * @Title: findLogoutList
      * @date 2017-4-11 下午3:08:59  
      * @Description:注销列表
      * @param @param supplier      
      * @return void
      */
     @RequestMapping(value = "/logoutList")
     public String logoutList(Supplier supplier, Integer page, Model model){
  	   List<Supplier> logoutList = supplierService.findLogoutList(supplier, page);
	   PageInfo < Supplier > pageInfo = new PageInfo < Supplier > (logoutList);
	   model.addAttribute("result", pageInfo);
  	 return "ses/sms/supplier_delete/list";
     }
}
