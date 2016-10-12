package bss.controller.sstps;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.controller.sys.bms.LoginController;
import ses.util.PropertiesUtil;

import bss.model.sstps.AppraisalContract;
import bss.model.sstps.ContractProduct;
import bss.service.sstps.AppraisalContractService;
import bss.service.sstps.ContractProductService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
* @Title:OfferContriller 
* @Description: 报价
* @author Shen Zhenfei
* @date 2016-10-10下午5:08:44
 */
@Controller
@Scope
@RequestMapping("/offer")
public class OfferContriller {
	
	@Autowired
	private AppraisalContractService appraisalContractService;
	
	@Autowired
	private ContractProductService contractProductService;
	
	private Logger logger = Logger.getLogger(LoginController.class); 
	
	/**
	* @Title: list
	* @author Shen Zhenfei 
	* @date 2016-10-12 下午1:10:41  
	* @Description: 报价列表
	* @param @param model
	* @param @param page
	* @param @return      
	* @return String
	 */
	@RequestMapping("/list")
	public String list(Model model,Integer page){
		List<AppraisalContract> list = appraisalContractService.selectDistribution(null,page==null?1:page);
		model.addAttribute("list", new PageInfo<AppraisalContract>(list));
		logger.info(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
		return "bss/sstps/offer/supplier/list";
	}
	
	/**
	* @Title: checkList
	* @author Shen Zhenfei 
	* @date 2016-10-12 下午1:10:56  
	* @Description: 复审报价
	* @param @param model
	* @param @param page
	* @param @return      
	* @return String
	 */
	@RequestMapping("/checkList")
	public String checkList(Model model,Integer page){
		List<AppraisalContract> list = appraisalContractService.selectDistribution(null,page==null?1:page);
		model.addAttribute("list", new PageInfo<AppraisalContract>(list));
		logger.info(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
		return "bss/sstps/offer/supplier/list";
	}
	
	/**
	* @Title: appraisalList
	* @author Shen Zhenfei 
	* @date 2016-10-12 下午1:11:36  
	* @Description: 审价
	* @param @param model
	* @param @param page
	* @param @return      
	* @return String
	 */
	@RequestMapping("/appraisalList")
	public String appraisalList(Model model,Integer page){
		List<AppraisalContract> list = appraisalContractService.selectDistribution(null,page==null?1:page);
		model.addAttribute("list", new PageInfo<AppraisalContract>(list));
		logger.info(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
		return "bss/sstps/offer/supplier/list";
	}
	
	/**
	* @Title: selectProduct
	* @author Shen Zhenfei 
	* @date 2016-10-12 下午1:13:13  
	* @Description: 审价合同产品
	* @param @return      
	* @return String
	 */
	@RequestMapping("/selectProduct")
	public String selectProduct(Model model,String contractId,ContractProduct contractProduct,Integer page){
		AppraisalContract appraisalContract = new AppraisalContract();
		appraisalContract.setId(contractId);
		contractProduct.setAppraisalContract(appraisalContract);
		
		String name = contractProduct.getName();
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		if(name!=null && !name.equals("")){
			map.put("name", "%"+name+"%");
		}
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<ContractProduct> list = contractProductService.select(contractProduct); 
		model.addAttribute("list", new PageInfo<ContractProduct>(list));
		model.addAttribute("name", name);
		model.addAttribute("id", contractId);
		return "bss/sstps/offer/supplier/product_list";
	}
	

}
