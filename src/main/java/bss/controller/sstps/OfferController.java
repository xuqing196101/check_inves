
package bss.controller.sstps;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import bss.model.sstps.ProductInfo;
import bss.service.sstps.AppraisalContractService;
import bss.service.sstps.ContractProductService;
import bss.service.sstps.ProductInfoService;

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
public class OfferController {
	
	@Autowired
	private AppraisalContractService appraisalContractService;
	
	@Autowired
	private ContractProductService contractProductService;
	
	@Autowired
	private ProductInfoService productInfoService;
	
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
	public String list(Model model,Integer page,AppraisalContract appraisalContract){
		List<AppraisalContract> list = appraisalContractService.selectDistribution(appraisalContract,page==null?1:page);
		model.addAttribute("list", new PageInfo<AppraisalContract>(list));
		logger.info(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
		return "bss/sstps/offer/supplier/list";
	}
	
	/**
	* @Title: search
	* @author Shen Zhenfei 
	* @date 2016-10-27 下午1:10:42  
	* @Description: 条件查询搜索
	* @param @param model
	* @param @param page
	* @param @param appraisalContract
	* @param @return      
	* @return String
	 */
	@RequestMapping("/search")
	public String search(Model model,Integer page,AppraisalContract appraisalContract){
		AppraisalContract sib = new AppraisalContract();
		String name = appraisalContract.getName();
		String code = appraisalContract.getCode();
		String supplierName = appraisalContract.getSupplierName();
		sib.setName("%"+name+"%");
		sib.setCode("%"+code+"%");
		sib.setSupplierName("%"+supplierName+"%");
		List<AppraisalContract> list = appraisalContractService.selectDistribution(sib,page==null?1:page);
		model.addAttribute("list", new PageInfo<AppraisalContract>(list));
		model.addAttribute("name",name);
		model.addAttribute("code",code);
		model.addAttribute("supplierName",supplierName);
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
		List<AppraisalContract> list = appraisalContractService.selectAppraisal(null,page==null?1:page);
		model.addAttribute("list", new PageInfo<AppraisalContract>(list));
		logger.info(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
		return "bss/sstps/offer/checkAppraisal/list";
	}
	
	/**
	* @Title: appraisalList
	* @author Shen Zhenfei 
	* @date 2016-10-12 下午1:11:36  
	* @Description: 审价人员 
	* @param @param model
	* @param @param page
	* @param @return      
	* @return String
	 */
	@RequestMapping("/userAppraisalList")
	public String userAppraisalList(Model model,Integer page){
		List<AppraisalContract> list = appraisalContractService.selectDistribution(null,page==null?1:page);
		model.addAttribute("list", new PageInfo<AppraisalContract>(list));
		logger.info(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
		return "bss/sstps/offer/userAppraisal/list";
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
		map.put("appraisalContractId",contractId);
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<ContractProduct> list = contractProductService.select(map);
		model.addAttribute("list", new PageInfo<ContractProduct>(list));
		model.addAttribute("name", name);
		model.addAttribute("id", contractId);
		return "bss/sstps/offer/supplier/product_list";
	}
	
	
	/**
	* @Title: selectProductInfo
	* @author Shen Zhenfei 
	* @date 2016-10-13 上午9:41:57  
	* @Description: TODO 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/selectProductInfo")
	public String selectProductInfo(Model model,String productId,HttpServletRequest request){
		ContractProduct contractProduct = contractProductService.selectById(productId);
		model.addAttribute("contractProduct", contractProduct);
		String url;
		if(contractProduct.getOffer()==0){
			url="bss/sstps/offer/supplier/product_Info";
		}else{
			url="bss/sstps/offer/supplier/list/list";
		}
		ProductInfo productInfo = productInfoService.selectInfo(productId);
		model.addAttribute("productInfo", productInfo);
		return url;
	}
	

	/**
	 * 
	 * @Title: userSelectProduct
	 * @author Liyi 
	 * @date 2016-10-24 下午4:18:03  
	 * @Description:审价人员审价合同产品
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/userSelectProduct")
	public String userSelectProduct(Model model,String contractId,ContractProduct contractProduct,Integer page){
		AppraisalContract appraisalContract = new AppraisalContract();
		appraisalContract.setId(contractId);
		contractProduct.setAppraisalContract(appraisalContract);
		
		String name = contractProduct.getName();
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		if(name!=null && !name.equals("")){
			map.put("name", "%"+name+"%");
		}
		map.put("appraisalContractId",contractId);
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<ContractProduct> list = contractProductService.select(map); 
		model.addAttribute("list", new PageInfo<ContractProduct>(list));
		model.addAttribute("name", name);
		model.addAttribute("id", contractId);
		return "bss/sstps/offer/userAppraisal/product_list";
	}
	
	/**
	 * 
	 * @Title: userSelectProductInfo
	 * @author Liyi 
	 * @date 2016-10-24 下午6:10:23  
	 * @Description:装备技术审价
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/userSelectProductInfo")
	public String userSelectProductInfo(Model model,String productId,HttpServletRequest request){
		ContractProduct contractProduct = contractProductService.selectById(productId);
		model.addAttribute("contractProduct", contractProduct);
		
		String url="bss/sstps/offer/userAppraisal/list/list";
		
//		ProductInfo ProductI = new ProductInfo();
//		ProductI.setContractProduct(contractProduct);
	 	ProductInfo productInfo = productInfoService.selectInfo(productId);
		model.addAttribute("productInfo", productInfo);
		
		return url;
	}
	/**
	 * 
	 * @Title: userSelectProductCheck
	 * @author Liyi 
	 * @date 2016-10-24 下午4:18:03  
	 * @Description:审价人员复审合同产品
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/userSelectProductCheck")
	public String userSelectProductCheck(Model model,String contractId,ContractProduct contractProduct,Integer page){
		AppraisalContract appraisalContract = new AppraisalContract();
		appraisalContract.setId(contractId);
		contractProduct.setAppraisalContract(appraisalContract);
		
		String name = contractProduct.getName();
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		if(name!=null && !name.equals("")){
			map.put("name", "%"+name+"%");
		}
		map.put("appraisalContractId",contractId);
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<ContractProduct> list = contractProductService.select(map); 
		model.addAttribute("list", new PageInfo<ContractProduct>(list));
		model.addAttribute("name", name);
		model.addAttribute("id", contractId);
		return "bss/sstps/offer/checkAppraisal/product_list";
	}
	
	/**
	 * 
	 * @Title: userSelectProductInfo
	 * @author Liyi 
	 * @date 2016-10-24 下午6:10:23  
	 * @Description:装备技术审价
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/userSelectProductInfoCheck")
	public String userSelectProductInfoCheck(Model model,String productId,HttpServletRequest request){
		ContractProduct contractProduct = contractProductService.selectById(productId);
		model.addAttribute("contractProduct", contractProduct);
		
		String url="bss/sstps/offer/checkAppraisal/list/list";
		
//		ProductInfo ProductI = new ProductInfo();
//		ProductI.setContractProduct(contractProduct);
	 	ProductInfo productInfo = productInfoService.selectInfo(productId);
		model.addAttribute("productInfo", productInfo);
		
		return url;
	}
}
