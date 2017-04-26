
package bss.controller.sstps;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.controller.sys.bms.LoginController;
import ses.model.bms.User;
import ses.model.ppms.CategoryParam;
import ses.model.sms.Supplier;
import ses.service.sms.SupplierService;
import ses.util.PropertiesUtil;
import bss.model.cs.ContractRequired;
import bss.model.sstps.AppraisalContract;
import bss.model.sstps.ContractProduct;
import bss.model.sstps.ProductInfo;
import bss.service.cs.ContractRequiredService;
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
	private ContractRequiredService contractRequiredService;
	
	@Autowired
	private ProductInfoService productInfoService;
	
	@Autowired
	private SupplierService supplierService;
	
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
		if(list!=null&&list.size()>0){
			for(AppraisalContract appCont:list){
				Supplier supplier = supplierService.selectById(appCont.getSupplierName());
				if(supplier!=null){
					appCont.setSupplierName(supplier.getSupplierName());
				}else{
					appCont.setSupplierName("");
				}
			}
		}
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
	public String userAppraisalList(AppraisalContract appraisalContract,HttpServletRequest request, Model model,Integer page){
		User user = (User) request.getSession().getAttribute("loginUser");
		appraisalContract.setUser(user);
		appraisalContract.setSupplier(new Supplier());
		if(appraisalContract.getSupplier() != null ){
			appraisalContract.getSupplier().setSupplierName(appraisalContract.getSupplierName());
		}
		List<AppraisalContract> list = appraisalContractService.selectByOffer(appraisalContract,page==null?1:page);
		for (AppraisalContract ac : list) {
            //这个supplierName里面存的是id
           ac.setMoney(ac.getMoney().setScale(4, BigDecimal.ROUND_HALF_UP));
        }
		model.addAttribute("list", new PageInfo<AppraisalContract>(list));
		model.addAttribute("ap", appraisalContract);
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
		for(ContractProduct cp:list){
			ContractRequired contractRequired = contractRequiredService.selectConRequByPrimaryKey(cp.getRequirdeId());
			cp.setContractRequired(contractRequired);
		}
		model.addAttribute("list", new PageInfo<ContractProduct>(list));
		model.addAttribute("name", name);
		model.addAttribute("id", contractId);
		return "bss/sstps/offer/supplier/product_list";
	}
	
	/**
	 * 
	 * @Title: selectProductUser
	 * @author Liyi 
	 * @date 2016-11-27 下午3:34:20  
	 * @Description:审价人员审价查询条目
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/selectProductUser")
	public String selectProductUser(Model model,String contractId,ContractProduct contractProduct,Integer page){
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
		for(ContractProduct cp:list){
			ContractRequired contractRequired = contractRequiredService.selectConRequByPrimaryKey(cp.getRequirdeId());
			cp.setContractRequired(contractRequired);
		}
		model.addAttribute("list", new PageInfo<ContractProduct>(list));
		model.addAttribute("name", name);
		model.addAttribute("id", contractId);
		return "bss/sstps/offer/userAppraisal/product_list";
	}
	
	/**
	 * 
	 * @Title: selectProductCheck
	 * @author Liyi 
	 * @date 2016-11-27 下午3:34:43  
	 * @Description:审价人员复审查询条目
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/selectProductCheck")
	public String selectProductCheck(Model model,String contractId,ContractProduct contractProduct,Integer page){
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
	* @Title: selectProductInfo
	* @author Li WanLin
	* @date 2017-04-06 上午9:41:57  
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
		for(ContractProduct cp:list){
			ContractRequired contractRequired = contractRequiredService.selectConRequByPrimaryKey(cp.getRequirdeId());
			cp.setContractRequired(contractRequired);
		}
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
		for(ContractProduct cp:list){
			ContractRequired contractRequired = contractRequiredService.selectConRequByPrimaryKey(cp.getRequirdeId());
			cp.setContractRequired(contractRequired);
		}
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
	
	/**
	 * 
	 * @Title: userSearch
	 * @author Liyi 
	 * @date 2016-11-27 下午2:25:45  
	 * @Description:审价人员审价查询
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/userSearch")
	public String userSearch(Model model,Integer page,AppraisalContract appraisalContract){
		AppraisalContract sib = new AppraisalContract();
		String name = appraisalContract.getName();
		String code = appraisalContract.getCode();
		String supplierName = appraisalContract.getSupplierName();
		sib.setName("%"+name+"%");
		sib.setCode("%"+code+"%");
		sib.setSupplierName("%"+supplierName+"%");
		List<AppraisalContract> list = appraisalContractService.selectDistributionUser(sib,page==null?1:page);
		for (AppraisalContract ac : list) {
            //这个supplierName里面存的是id
            Supplier supplier = supplierService.selectOne(ac.getSupplierName());
            if (supplier != null) {
                ac.setSupplierName(supplier.getSupplierName());
            } else {
                ac.setSupplierName("");
            }
            ac.setMoney(ac.getMoney().setScale(4, BigDecimal.ROUND_HALF_UP));
        }
		model.addAttribute("list", new PageInfo<AppraisalContract>(list));
		model.addAttribute("name",name);
		model.addAttribute("code",code);
		model.addAttribute("supplierName",supplierName);
		return "bss/sstps/offer/userAppraisal/list";
	}
	
	/**
	 * 
	 * @Title: userSearchCheck
	 * @author Liyi 
	 * @date 2016-11-27 下午2:26:04  
	 * @Description:审价人员复审查询
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/userSearchCheck")
	public String userSearchCheck(Model model,Integer page,AppraisalContract appraisalContract){
		AppraisalContract sib = new AppraisalContract();
		String name = appraisalContract.getName();
		String code = appraisalContract.getCode();
		String supplierName = appraisalContract.getSupplierName();
		sib.setName("%"+name+"%");
		sib.setCode("%"+code+"%");
		sib.setSupplierName("%"+supplierName+"%");
		List<AppraisalContract> list = appraisalContractService.selectDistributionCheck(sib,page==null?1:page);
		model.addAttribute("list", new PageInfo<AppraisalContract>(list));
		model.addAttribute("name",name);
		model.addAttribute("code",code);
		model.addAttribute("supplierName",supplierName);
		return "bss/sstps/offer/checkAppraisal/list";
	}
	
	
	/**
	 * 
	 * Description: 导出
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月23日 
	 * @param  @param id
	 * @param  @param session
	 * @param  @param request
	 * @param  @param response
	 * @param  @throws IOException 
	 * @return void 
	 * @exception
	 */
    /*@RequestMapping("/exports")
    public void export(String id,HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException{
        String filename ="审价合同信息";
        response.setContentType("application/vnd.ms-excel; charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename="+filename+".xlsx");
        response.setCharacterEncoding("utf-8");
        OutputStream os=response.getOutputStream();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("审价合同信息");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("合同名称");
        HSSFCell cell1 = row.createCell(1);
        cell1.setCellValue("合同编号");
        HSSFCell cell2 = row.createCell(2);
        cell2.setCellValue("合同金额");
        HSSFCell cell3 = row.createCell(3);
        cell3.setCellValue("供应商名称");
        AppraisalContract contract = appraisalContractService.selectContractInfo(id);
        row = sheet.createRow(1);
        row.createCell(0).setCellValue(contract.getName());
        row.createCell(1).setCellValue(contract.getCode());
        row.createCell(2).setCellValue(contract.getMoney().toString());
        row.createCell(3).setCellValue(contract.getSupplier().getSupplierName());
        try {
            os.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
