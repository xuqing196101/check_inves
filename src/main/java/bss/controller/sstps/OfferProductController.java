package bss.controller.sstps;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import bss.model.sstps.AccessoriesCon;
import bss.model.sstps.ComprehensiveCost;
import bss.model.sstps.ContractProduct;
import bss.model.sstps.ProductInfo;
import bss.service.sstps.AccessoriesConService;
import bss.service.sstps.ComprehensiveCostService;
import bss.service.sstps.ProductInfoService;


/**
* @Title:OfferProductController 
* @Description: 
* @author Shen Zhenfei
* @date 2016-10-13上午10:05:29
 */
@Controller
@Scope
@RequestMapping("/offerProduct")
public class OfferProductController {
	
	@Autowired
	private ProductInfoService productInfoService;
	
	@Autowired
	private AccessoriesConService accessoriesConService;
	
	@Autowired
	private ComprehensiveCostService comprehensiveCostService;
	
	
	/**
	* @Title: save
	* @author Shen Zhenfei 
	* @date 2016-10-22 上午9:38:49  
	* @Description: 保存
	* @param @param model
	* @param @param productInfo
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/save")
	public String save(Model model,ProductInfo productInfo,HttpServletRequest request,ComprehensiveCost comprehensiveCost){
		String id = request.getParameter("id");
		String proId = request.getParameter("contractProduct.id");
		productInfo.setUpdatedAt(new Date());
		if(id!=null && !id.equals("")){
			productInfoService.update(productInfo);
		}else{
			productInfo.setCreatedAt(new Date());
			productInfoService.insert(productInfo);
		}
		model.addAttribute("proId", proId);
		AccessoriesCon accessoriesCon = new AccessoriesCon();
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		accessoriesCon.setContractProduct(contractProduct);
		List<AccessoriesCon> list = accessoriesConService.selectProduct(accessoriesCon);
		model.addAttribute("list", list);
		
		comprehensiveCost.setContractProduct(contractProduct);
		List<ComprehensiveCost> lists = comprehensiveCostService.select(comprehensiveCost);
		if(lists.size()<1){
			String[] name1={"原辅材料","外购成件","外协部件","燃料动力","直接人工","专用费用","制造费用","合计"};
			for(int i =0;i<name1.length;i++){
				comprehensiveCost.setProjectName("专项试验费");
				comprehensiveCost.setSecondProject(name1[i]);
				comprehensiveCost.setStatus(0);
				comprehensiveCostService.insert(comprehensiveCost);
			}
			String[] name2={"管理费用","财务费用","销售费用","合计"};
			for(int i =0;i<name2.length;i++){
				comprehensiveCost.setProjectName("期间费用");
				comprehensiveCost.setSecondProject(name2[i]);
				comprehensiveCost.setStatus(1);
				comprehensiveCostService.insert(comprehensiveCost);
			}
			String[] name3={"成本","利润","税金","价格"};
			for(int i =0;i<name3.length;i++){
				comprehensiveCost.setProjectName("价格方案");
				comprehensiveCost.setSecondProject(name3[i]);
				comprehensiveCost.setStatus(2);
				comprehensiveCostService.insert(comprehensiveCost);
			}
			String[] name4={"本产品定额工时","工时分配率合计","直接人工","燃料动力","制造费用","期间费用"};
			for(int i =0;i<name4.length;i++){
				comprehensiveCost.setProjectName("工时及分配率");
				comprehensiveCost.setSecondProject(name4[i]);
				comprehensiveCost.setStatus(3);
				comprehensiveCostService.insert(comprehensiveCost);
			}
		}
		
		return "bss/sstps/offer/supplier/accessories/list";
	}
	
	/**
	 * 
	 * @Title: userSave
	 * @author Liyi 
	 * @date 2016-10-25 下午2:18:45  
	 * @Description:
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/userSave")
	public String userSave(Model model,ProductInfo productInfo,HttpServletRequest request){
		String id = request.getParameter("id");
		String proId = request.getParameter("proId");
		model.addAttribute("proId", proId);
		AccessoriesCon accessoriesCon = new AccessoriesCon();
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		accessoriesCon.setContractProduct(contractProduct);
		List<AccessoriesCon> list = accessoriesConService.selectProduct(accessoriesCon);
		model.addAttribute("list", list);
		return "bss/sstps/offer/userAppraisal/list/accessories_list";
	}
	
	/**
	* @Title: view
	* @author Shen Zhenfei 
	* @date 2016-10-22 上午9:38:57  
	* @Description: 查看
	* @param @return      
	* @return String
	 */
	@RequestMapping("view")
	public String view(Model model,HttpServletRequest request){
		String proId = request.getParameter("proId");
		model.addAttribute("proId", proId);
		
		AccessoriesCon accessoriesCon = new AccessoriesCon();
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		accessoriesCon.setContractProduct(contractProduct);
		List<AccessoriesCon> list = accessoriesConService.selectProduct(accessoriesCon);
		model.addAttribute("list", list);
		
		return "bss/sstps/offer/supplier/list/accessories_list";
	}
	
	/**
	 * 
	 * @Title: userSave
	 * @author Liyi 
	 * @date 2016-10-25 下午2:18:45  
	 * @Description:
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/userSaveCheck")
	public String userSaveCheck(Model model,ProductInfo productInfo,HttpServletRequest request){
		String id = request.getParameter("id");
		String proId = request.getParameter("proId");
		model.addAttribute("proId", proId);
		AccessoriesCon accessoriesCon = new AccessoriesCon();
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		accessoriesCon.setContractProduct(contractProduct);
		List<AccessoriesCon> list = accessoriesConService.selectProduct(accessoriesCon);
		model.addAttribute("list", list);
		return "bss/sstps/offer/checkAppraisal/list/accessories_list";
	}

}
