package yggc.controller.sys.sms;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import yggc.model.sms.SupplierFsInfoWithBLOBs;
import yggc.service.sms.SupplierFsInfoService;

/**
* <p>Title:SupplierFsInfoController </p>
* <p>Description:进口供应商注册审核控制层 </p>
* <p>Company: yggc </p> 
* @author Song Biaowei
* @date 2016-9-6下午1:31:21
 */

@Controller
@Scope("prototype")
@RequestMapping("/supplierFsInfo")
public class SupplierFsInfoController {
	@Autowired
	private SupplierFsInfoService supplierFsInfoService;
	
	/**
	* @Title: beforeRegister
	* @author Song Biaowei
	* @date 2016-9-6 上午11:31:17  
	* @Description:点击进口供应商注册 
	* @param @return      
	* @return String
	 */
	@RequestMapping("beforeRegister.html")
	public String registerStep1(){
		return "fsInfo/register/step1";
	}
	
	@RequestMapping("registerStep2.html")
	public String registerStep2(){
		return "fsInfo/register/step2";
	}
	
	@RequestMapping("registerStep3.html")
	public String registerStep3(){
		return "fsInfo/register/step3";
	}
	
	@RequestMapping("registerStep4.html")
	public String registerStep4(){
		return "fsInfo/register/step4";
	}
	
	@RequestMapping("registerStep5.html")
	public String registerStep5(){
		return "fsInfo/register/step5";
	}
	@RequestMapping("registerStep6.html")
	public String registerStep6(){
		return "fsInfo/register/step6";
	}
	@RequestMapping("registerStep7.html")
	public String registerStep7(){
		return "fsInfo/register/step7";
	}

	
	/**
	* @Title: register
	* @author Song Biaowei
	* @date 2016-9-6 上午11:32:49  
	* @Description: 注册第四步选择机构后点击保存 
	* @param @param sfi
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("register.do")
	public String register(SupplierFsInfoWithBLOBs sfi, Model model) {
		sfi.setCreatedAt(new Date());
		sfi.setStatus((short)0);
		sfi.setOrgmanId("fasdfdas");
		supplierFsInfoService.register(sfi);
		List<SupplierFsInfoWithBLOBs> sfiList=supplierFsInfoService.selectByFsInfo(sfi);
		return "fsInfo/finished";
	}
	
	/**
	* @Title: daiban
	* @author Song Biaowei
	* @date 2016-9-6 上午11:34:12  
	* @Description: 点击我的待办
	* @param @param sfi
	* @param @param model
	* @param @return      
	* @return String
	 */
	public String daiban(SupplierFsInfoWithBLOBs sfi,Model model){
		//未审核 就等于初审
		sfi.setStatus((short)0);
		int weishenhe=supplierFsInfoService.getCount(sfi);
		//审核中 就等于 复审
		sfi.setStatus((short)1);
		int fushen=supplierFsInfoService.getCount(sfi);
		//审核通过
		sfi.setStatus((short)2);
		int yishenhe=supplierFsInfoService.getCount(sfi);
		model.addAttribute("weishenhe", weishenhe);
		model.addAttribute("shenhezhong",fushen);
		model.addAttribute("weishenhe", yishenhe);
		return "fsInfo/daiban";
	}
	
	/**
	* @Title: daibanList
	* @author Song Biaowei
	* @date 2016-9-6 上午11:35:35  
	* @Description: 点击我的待办里面的选项进入的页面
	* @param @param sfi
	* @param @param model
	* @param @return      
	* @return String
	 */
	public String daibanList(SupplierFsInfoWithBLOBs sfi,Model model){
		List<SupplierFsInfoWithBLOBs> sfiList=supplierFsInfoService.selectByFsInfo(sfi);
		model.addAttribute("sfiList0", sfiList);
		return "fsInfo/daiban";
	}
	
	/**
	* @Title: auditShow
	* @author Song Biaowei
	* @date 2016-9-6 上午11:37:14  
	* @Description: 点击初审 复审进入的方法 
	* @param @param sfi
	* @param @param model
	* @param @return      
	* @return String
	 */
	public String auditShow(SupplierFsInfoWithBLOBs sfi,Model model){
		SupplierFsInfoWithBLOBs supplierFsInfoWithBLOBs = supplierFsInfoService.selectByPrimaryKey(sfi);
		model.addAttribute("supplierFsInfoWithBLOBs", supplierFsInfoWithBLOBs);
		return "fsInfo/daiban";
	}
	
}
