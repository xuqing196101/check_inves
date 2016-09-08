package ses.controller.sys.sms;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.SupplierFsInfoWithBLOBs;
import ses.service.sms.SupplierFsInfoService;


/**
 * 
 * @Title: SupplierFsInfoController
 * @Description: 进口供应商注册审核控制层
 * @author: Song Biaowei
 * @date: 2016-9-7下午6:09:03
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
	@RequestMapping("registerStep1.html")
	public String registerStep1(){
		return "fsInfo/register/step1";
	}
	
	/**
	* @Title: registerStep2
	* @author Song Biaowei
	* @date 2016-9-7 下午5:49:53  
	* @Description: 第二个页面
	* @param @param sfi
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("registerStep2.html")
	public String registerStep2(SupplierFsInfoWithBLOBs sfi, Model model){
		if(sfi.getId()!=null){
			SupplierFsInfoWithBLOBs sfiStep2=supplierFsInfoService.findById(sfi.getId());
			model.addAttribute("loginName", sfiStep2.getLoginName());
		}
		return "fsInfo/register/step2";
	}
	
	/**
	* @Title: registerStep3
	* @author Song Biaowei
	* @date 2016-9-7 下午5:50:07  
	* @Description: 第三个页面
	* @param @param sfi
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("registerStep3.html")
	public String registerStep3(SupplierFsInfoWithBLOBs sfi, Model model){
		supplierFsInfoService.register(sfi);
		String id=supplierFsInfoService.selectIdByLoginName(sfi);
		System.out.println(id);
		model.addAttribute("id", id);
		return "fsInfo/register/step3";
	}
	
	/**
	* @Title: registerStep4
	* @author Song Biaowei
	* @date 2016-9-7 下午5:50:17  
	* @Description: 第四个页面 
	* @param @param sfi
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("registerStep4.html")
	public String registerStep4(SupplierFsInfoWithBLOBs sfi, Model model){
		SupplierFsInfoWithBLOBs sfiStep2=supplierFsInfoService.findById(sfi.getId());
		System.out.println(sfi.getId());
		sfi.setLoginName(sfiStep2.getLoginName());
		sfi.setPassword(sfiStep2.getPassword());
		sfi.setMobile(sfiStep2.getMobile());
		supplierFsInfoService.updateRegisterInfo(sfi);
		model.addAttribute("id", sfiStep2.getId());
		return "fsInfo/register/step4";
	}
	
	/**
	* @Title: registerStep5
	* @author Song Biaowei
	* @date 2016-9-7 下午5:50:26  
	* @Description:第五个也页面 
	* @param @param sfi
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("registerStep5.html")
	public String registerStep5(SupplierFsInfoWithBLOBs sfi, Model model){
		SupplierFsInfoWithBLOBs sfiStep2=supplierFsInfoService.findById(sfi.getId());
		sfiStep2.setOrgmanId(sfi.getOrgmanId());
		supplierFsInfoService.updateRegisterInfo(sfiStep2);
		model.addAttribute("id", sfiStep2.getId());
		return "fsInfo/register/step5";
	}
	
	/**
	* @Title: registerStep6
	* @author Song Biaowei
	* @date 2016-9-7 下午5:50:48  
	* @Description: 第六个页面 
	* @param @param sfi
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("registerStep6.html")
	public String registerStep6(SupplierFsInfoWithBLOBs sfi, Model model){
		model.addAttribute("id", sfi.getId());
		return "fsInfo/register/step6";
	}
	
	/**
	* @Title: registerStep7
	* @author Song Biaowei
	* @date 2016-9-7 下午5:50:58  
	* @Description:第七个页面 
	* @param @param sfi
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("registerStep7.html")
	public String registerStep7(SupplierFsInfoWithBLOBs sfi, Model model){
		SupplierFsInfoWithBLOBs sfiStep2=supplierFsInfoService.findById(sfi.getId());
		sfiStep2.setOrgmanId(sfi.getSupplierRegList());
		sfiStep2.setCreatedAt(new Date());
		sfiStep2.setStatus((short)0);
		supplierFsInfoService.updateRegisterInfo(sfiStep2);
		return "fsInfo/register/step7";
	}

	
	/**
	* @Title: daiban
	* @author Song Biaowei
	* @date 2016-9-6 上午11:34:12  
	* @Description: 供应商审核点击我的待办
	* @param @param sfi
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("daiban.html")
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
		model.addAttribute("yishenhe", yishenhe);
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
	@RequestMapping("auditList.html")
	public String daibanList(SupplierFsInfoWithBLOBs sfi,Model model){
		List<SupplierFsInfoWithBLOBs> sfiList=supplierFsInfoService.selectByFsInfo(sfi);
		model.addAttribute("sfiList", sfiList);
		return "fsInfo/auditList";
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
	@RequestMapping("audit.html")
	public String auditShow(SupplierFsInfoWithBLOBs sfi,Model model){
		SupplierFsInfoWithBLOBs supplierFsInfoWithBLOBs = supplierFsInfoService.selectByPrimaryKey(sfi);
		model.addAttribute("sfi", supplierFsInfoWithBLOBs);
		return "fsInfo/firstAudit";
	}
	
}
