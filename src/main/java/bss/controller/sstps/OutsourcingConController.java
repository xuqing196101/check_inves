package bss.controller.sstps;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.util.ValidateUtils;

import bss.model.sstps.ComprehensiveCost;
import bss.model.sstps.ContractProduct;
import bss.model.sstps.OutsourcingCon;
import bss.service.sstps.ComprehensiveCostService;
import bss.model.sstps.OutsourcingConList;
import bss.service.sstps.OutsourcingConService;

@Controller
@Scope
@RequestMapping("/outsourcingCon")
public class OutsourcingConController {
	
	@Autowired
	private OutsourcingConService outsourcingConService;
	
	@Autowired
	private ComprehensiveCostService comprehensiveCostService;
	
	/**
	* @Title: select
	* @author Shen Zhenfei 
	* @date 2016-10-14 下午2:23:31  
	* @Description: 列表
	* @param @param model
	* @param @param proId
	* @param @param outsourcingCon
	* @param @return      
	* @return String
	 */
	@RequestMapping("/select")
	public String select(Model model,String proId,OutsourcingCon outsourcingCon,Integer total){
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		outsourcingCon.setContractProduct(contractProduct);
		List<OutsourcingCon> list = outsourcingConService.selectProduct(outsourcingCon);
		model.addAttribute("list", list);
		model.addAttribute("proId", proId);
		if(total!=null){
			ComprehensiveCost comprehensiveCost = new ComprehensiveCost();
			comprehensiveCost.setContractProduct(contractProduct);
			comprehensiveCost.setSingleOffer(total);
			comprehensiveCost.setProjectName("专项试验费");
			comprehensiveCost.setSecondProject("外购成件");
			comprehensiveCostService.updateInfo(comprehensiveCost);
		}
		return "bss/sstps/offer/supplier/outsourcing/list";
	}
	
	/**
	* @Title: view
	* @author Shen Zhenfei 
	* @date 2016-10-22 上午10:45:16  
	* @Description: 查看
	* @param @return      
	* @return String
	 */
	@RequestMapping("/view")
	public String view(Model model,String proId,OutsourcingCon outsourcingCon){
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		outsourcingCon.setContractProduct(contractProduct);
		List<OutsourcingCon> list = outsourcingConService.selectProduct(outsourcingCon);
		model.addAttribute("list", list);
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/list/outsourcing_list";
	}
	
	/**
	* @Title: add
	* @author Shen Zhenfei 
	* @date 2016-10-14 下午2:23:48  
	* @Description: 新增页面
	* @param @param model
	* @param @param proId
	* @param @return      
	* @return String
	 */
	@RequestMapping("/add")
	public String add(Model model,String proId){
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/outsourcing/add";
	}
	
	/**
	* @Title: edit
	* @author Shen Zhenfei 
	* @date 2016-10-14 下午2:23:57  
	* @Description: 修改页面
	* @param @param model
	* @param @param proId
	* @param @param id
	* @param @return      
	* @return String
	 */
	@RequestMapping("/edit")
	public String edit(Model model,String proId,String id){
		OutsourcingCon outsourcingCon = outsourcingConService.selectById(id);
		model.addAttribute("out", outsourcingCon);
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/outsourcing/edit";
	}
	
	/**
	* @Title: save
	* @author Shen Zhenfei 
	* @date 2016-10-14 下午2:24:15  
	* @Description: 保存
	* @param @param model
	* @param @param outsourcingCon
	* @param @return      
	* @return String
	 */
	@RequestMapping("/save")
	public String save(Model model,@Valid OutsourcingCon outsourcingCon,BindingResult result,HttpServletRequest request){
		String proId = outsourcingCon.getContractProduct().getId();
		model.addAttribute("proId",proId);
		String url = "";
		boolean flag = true;
		if(ValidateUtils.isNull(outsourcingCon.getOutsourcingName())){
			flag = false;
			model.addAttribute("ERR_outsourcingName", "外协加工件名称不能为空");
		}
		if(ValidateUtils.isNull(outsourcingCon.getNorm())){
			flag = false;
			model.addAttribute("ERR_norm", "材料名称不能为空");
		}
		if(ValidateUtils.isNull(outsourcingCon.getPaperCode())){
			flag = false;
			model.addAttribute("ERR_paperCode", "图纸位置号(代号)不能为空");
		}
		if(flag==false){
			model.addAttribute("out", outsourcingCon);
			url = "bss/sstps/offer/supplier/outsourcing/add";
		}else{
			outsourcingCon.setCreatedAt(new Date());
			outsourcingCon.setUpdatedAt(new Date());
			outsourcingConService.insert(outsourcingCon);
			List<OutsourcingCon> list = outsourcingConService.selectProduct(outsourcingCon);
			model.addAttribute("list", list);
			url = "bss/sstps/offer/supplier/outsourcing/list";
		}
		return url;
	}
	
	/**
	* @Title: update
	* @author Shen Zhenfei 
	* @date 2016-10-14 下午2:24:21  
	* @Description: 修改 
	* @param @param model
	* @param @param outsourcingCon
	* @param @return      
	* @return String
	 */
	@RequestMapping("/update")
	public String update(Model model,@Valid OutsourcingCon outsourcingCon,BindingResult result,HttpServletRequest request,String id){
		String proId = outsourcingCon.getContractProduct().getId();
		model.addAttribute("proId",proId);
		String url = "";
		boolean flag = true;
		if(ValidateUtils.isNull(outsourcingCon.getOutsourcingName())){
			flag = false;
			model.addAttribute("ERR_outsourcingName", "外协加工件名称不能为空");
		}
		if(ValidateUtils.isNull(outsourcingCon.getNorm())){
			flag = false;
			model.addAttribute("ERR_norm", "材料名称不能为空");
		}
		if(ValidateUtils.isNull(outsourcingCon.getPaperCode())){
			flag = false;
			model.addAttribute("ERR_paperCode", "图纸位置号(代号)不能为空");
		}
		if(flag==false){
			model.addAttribute("out", outsourcingCon);
			url = "bss/sstps/offer/supplier/outsourcing/edit";
			
		}else{
			outsourcingCon.setUpdatedAt(new Date());
			outsourcingConService.update(outsourcingCon);
			List<OutsourcingCon> list = outsourcingConService.selectProduct(outsourcingCon);
			model.addAttribute("list", list);
			url = "bss/sstps/offer/supplier/outsourcing/list";
		}
		return url;
	}
	
	/**
	* @Title: delete
	* @author Shen Zhenfei 
	* @date 2016-10-14 下午2:24:28  
	* @Description: 删除
	* @param @param model
	* @param @param proId
	* @param @param ids
	* @param @return      
	* @return String
	 */
	@RequestMapping("/delete")
	public String delete(Model model,String proId,String ids){
		String[] id=ids.split(",");
		for(String str : id){
			outsourcingConService.delete(str);
		}
		OutsourcingCon outsourcingCon = new OutsourcingCon();
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		outsourcingCon.setContractProduct(contractProduct);
		List<OutsourcingCon> list = outsourcingConService.selectProduct(outsourcingCon);
		model.addAttribute("list", list);
		model.addAttribute("proId",proId);
		return "bss/sstps/offer/supplier/outsourcing/list";
	}
	
	@RequestMapping("/userGetAll")
	public String userGetAll(Model model,HttpServletRequest request,String productId){ 
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		OutsourcingCon outsourcingCon = new OutsourcingCon();
		outsourcingCon.setContractProduct(contractProduct);
		List<OutsourcingCon> list = outsourcingConService.selectProduct(outsourcingCon);
		model.addAttribute("list", list);
		model.addAttribute("proId", productId);
		
		return "bss/sstps/offer/userAppraisal/list/outsourcing_list";
	}
	
	@RequestMapping("/userUpdate")
	public String userUpdate(Model model,OutsourcingConList OutsourcingConList,HttpServletRequest request){
		String proID = request.getParameter("productId");
		List<OutsourcingCon> outsourcingCons = OutsourcingConList.getOutsourcingCons();
		for (OutsourcingCon outsourcingCon : outsourcingCons) {
			outsourcingCon.setUpdatedAt(new Date());
			outsourcingConService.update(outsourcingCon);
		}
		model.addAttribute("proId",proID);
		return "redirect:/specialCost/userGetAll.html?productId="+proID;
	}
}
