package bss.controller.sstps;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.controller.sys.bms.LoginController;
import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgLocaleService;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.impl.OrgnizationServiceImpl;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.ValidateUtils;
import bss.model.cs.ContractRequired;
import bss.model.cs.PurchaseContract;
import bss.model.sstps.AppraisalContract;
import bss.model.sstps.ContractProduct;
import bss.model.sstps.Select;
import bss.service.cs.ContractRequiredService;
import bss.service.cs.PurchaseContractService;
import bss.service.sstps.AppraisalContractService;
import bss.service.sstps.ContractProductService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;

/**
 * 
* @Title:AppraisalContractController 
* @Description: 审价合同
* @author Shen Zhenfei
* @date 2016-10-10上午11:02:20
 */
@Controller
@Scope
@RequestMapping("/appraisalContract")
public class AppraisalContractController extends BaseSupplierController{

	@Autowired
	private AppraisalContractService appraisalContractService;
	
	@Autowired
	private UserServiceI userService;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private ContractProductService contractProductService;
	
	@Autowired
	private PurchaseContractService purchaseContractService;
	
	@Autowired
	private ContractRequiredService contractRequiredService;
	
    @Autowired
    private OrgnizationServiceI oService;
    
	private Logger logger = Logger.getLogger(LoginController.class); 
	
	
	/**
	* @Title: selectContract
	* @author Shen Zhenfei 
	* @date 2016-10-3 下午4:16:31  
	* @Description: 获取采购合同列表
	* @param @param response
	* @param @param type      
	* @return void
	 * @throws Exception 
	 */
	@RequestMapping(value="/selectContract",produces="application/json;charest=utf-8")
	public void selectContract(HttpServletResponse response,HttpServletRequest request) throws Exception{
		String purchaseType = request.getParameter("purchaseType");
	//	purchaseType = new String(purchaseType.getBytes("ISO-8859-1") , "UTF-8");
		List<Select> list = appraisalContractService.selectChose(purchaseType);
		super.writeJson(response, list);
	}
	
	
	//根据Id查询采购合同
	@RequestMapping("/selectContractId")
	public void selectContractId(HttpServletResponse response,String id){
		if(id!=null){
			PurchaseContract purchaseContract = purchaseContractService.selectById(id);
			super.writeJson(response, purchaseContract);
		}
	}
	
	
	/**
	* @Title: select
	* @author Shen Zhenfei 
	* @date 2016-9-19 上午9:30:15  
	* @Description: 查询审价列表
	* @param @param model
	* @param @param page
	* @param @return      
	* @return String
	 */
    @RequestMapping("/select")
    public String select(Model model,Integer page, HttpServletRequest req) {
        AppraisalContract sib = new AppraisalContract();
        User judge = (User)req.getSession().getAttribute("loginUser");
        List<AppraisalContract> list = new ArrayList<AppraisalContract>();
        if ("1".equals(judge.getOrg().getTypeName())) {
            list = appraisalContractService.selectByObjectLike(sib, page==null?1:page);
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
            logger.info(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
            return "bss/sstps/appraisal/list";
        } else {
            sib.setAppraisal(1);
            list = appraisalContractService.selectByObjectLike(sib, page==null?1:page);
            for (AppraisalContract ac : list) {
                Supplier supplier = supplierService.selectOne(ac.getSupplierName());
                if (supplier != null) {
                    ac.setSupplierName(supplier.getSupplierName());
                } else {
                    ac.setSupplierName("");
                }
                ac.setMoney(ac.getMoney().setScale(4, BigDecimal.ROUND_HALF_UP));
            }
            model.addAttribute("list", new PageInfo<AppraisalContract>(list));
            return "bss/sstps/distribution/list";
        }
    }
  
    
	
	/**
	* @Title: add
	* @author Shen Zhenfei 
	* @date 2016-9-19 上午9:58:24  
	* @Description: 新增合同列表
	* @param @param model
	* @param @param page
	* @param @return      
	* @return String
	 */
	@RequestMapping("/add")
    public String add(Model model,Integer page){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", 2);
        map.put("page", 1);
        List<PurchaseContract> pcList = purchaseContractService.selectAllContractByStatus(map);
        for (PurchaseContract contracts : pcList) {
            if (contracts != null && contracts.getSupplierDepName() != null && !"".equals(contracts.getSupplierDepName())) {
                Supplier supplier = supplierService.selectOne(contracts.getSupplierDepName());
                if (supplier != null) {
                    contracts.setSupplierDepName(supplier.getSupplierName());
                } else {
                    contracts.setSupplierDepName("");
                }
            }
            if (contracts != null && contracts.getPurchaseDepName() != null && !"".equals(contracts.getPurchaseDepName())) {
                Orgnization org = oService.findByCategoryId(contracts.getPurchaseDepName());
                if (org != null) {
                    contracts.setPurchaseDepName(org.getName());
                } else {
                    contracts.setPurchaseDepName("");
                }
            }
            
            if (contracts != null && contracts.getPurchaseType() != null && !"".equals(contracts.getPurchaseType())){
                DictionaryData dictionaryData = DictionaryDataUtil.findById(contracts.getPurchaseType());
                if (dictionaryData != null) {
                    contracts.setPurchaseType(dictionaryData.getName());
                } else {
                    contracts.setPurchaseType("");
                }
            }
            if (contracts.getMoney() != null && !"".equals(contracts.getMoney())) {
                contracts.setMoney(contracts.getMoney().setScale(4, BigDecimal.ROUND_HALF_UP));
            }
        }
        model.addAttribute("pcList", pcList);
        return "bss/sstps/appraisal/add";
    }
	
	//模拟合同信息
    @RequestMapping("/selectContractInfo")
    public String selectContractInfo(Model model,String id){
        AppraisalContract contracts = appraisalContractService.selectContractInfo(id);
        if (contracts != null && contracts.getSupplierName() != null && !"".equals(contracts.getSupplierName())) {
            Supplier supplier = supplierService.selectOne(contracts.getSupplierName());
            if (supplier != null) {
                contracts.setSupplierName(supplier.getSupplierName());
            } else {
                contracts.setSupplierName("");
            }
        }
        if (contracts != null && contracts.getPurchaseDepName() != null && !"".equals(contracts.getPurchaseDepName())) {
            Orgnization org = oService.findByCategoryId(contracts.getPurchaseDepName());
            if (org != null) {
                contracts.setPurchaseDepName(org.getName());
            } else {
                contracts.setPurchaseDepName("");
            }
        }
        
        if (contracts != null && contracts.getPurchaseType() != null && !"".equals(contracts.getPurchaseType())){
            DictionaryData dictionaryData = DictionaryDataUtil.findById(contracts.getPurchaseType());
            if (dictionaryData != null) {
                contracts.setPurchaseType(dictionaryData.getName());
            } else {
                contracts.setPurchaseType("");
            }
        }
        if (contracts.getMoney() != null && !"".equals(contracts.getMoney())) {
            contracts.setMoney(contracts.getMoney().setScale(4, BigDecimal.ROUND_HALF_UP));
        }
		model.addAttribute("contracts",contracts);
		return "bss/sstps/appraisal/contract";
	}
	
	
	
	/**
	* @Title: save
	* @author Song BiaoWei
	* @date 2016-9-19 上午9:36:38  
	* @Description: 保存新增合同
	* @param @return      
	* @return String
	 */
    @RequestMapping("/save")
    public String save(AppraisalContract appraisalContract,String contractId,Model model){
        String url = "";
        if (contractId == null || "".equals(contractId)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("status", 2);
            map.put("page", 1);
            List<PurchaseContract> pcList = purchaseContractService.selectAllContractByStatus(map);
            writeName(pcList);
            model.addAttribute("pcList", pcList);
            model.addAttribute("ERR_name", "合同名称不能为空");
            model.addAttribute("appraisalContract", appraisalContract);
            url = "bss/sstps/appraisal/add";
        } else {
            AppraisalContract ac = new AppraisalContract();
            PurchaseContract pcCondition = new PurchaseContract();
            pcCondition.setId(contractId);
            ac.setPurchaseContract(pcCondition);
            AppraisalContract appraisal = appraisalContractService.selectContractId(ac);
            if (appraisal != null) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("status", 2);
                map.put("page", 1);
                List<PurchaseContract> pcList = purchaseContractService.selectAllContractByStatus(map);
                writeName(pcList);
                model.addAttribute("pcList", pcList);
                model.addAttribute("ERR_name", "此合同已经存在于审价列表");
                model.addAttribute("appraisalContract", appraisalContract);
                url = "bss/sstps/appraisal/add";
            } else {
                PurchaseContract pc = purchaseContractService.selectById(contractId);
                appraisalContract.setAppraisal(0);
                appraisalContract.setDistribution(0);
                PurchaseContract purchaseContract = new PurchaseContract();
                purchaseContract.setId(contractId);
                appraisalContract.setPurchaseContract(purchaseContract);
                appraisalContract.setName(pc.getName());
                appraisalContract.setCode(pc.getCode());
                appraisalContract.setMoney(pc.getMoney());
                appraisalContract.setSupplierName(pc.getSupplierDepName());
                appraisalContract.setCreatedAt(new Date());
                appraisalContract.setUpdatedAt(new Date());
                appraisalContract.setPurchaseDepName(pc.getPurchaseDepName());
                appraisalContract.setPurchaseType(pc.getPurchaseType());
                appraisalContractService.insert(appraisalContract);
                url = "redirect:select.html";
            }
            /*
            //审价产品
            ContractProduct contractProduct = new ContractProduct();
            //根据合同编号，获取审价ID
            AppraisalContract app = new AppraisalContract();
            app.setId(contractId);
            app.setPurchaseContract(purchaseContract);
            AppraisalContract appc = appraisalContractService.selectContractId(app);
            List<ContractRequired> list = contractRequiredService.selectConRequeByContractId(contractId);
            for (int i=0; i < list.size(); i++) {
                //关联审价编号
                contractProduct.setAppraisalContract(appc);
                //获取合同产品
                contractProduct.setName(list.get(i).getGoodsName());
                contractProduct.setCreatedAt(new Date());
                contractProduct.setUpdatedAt(new Date());
                contractProduct.setOffer(0);
                contractProduct.setAuditOffer(0);
                contractProductService.insert(contractProduct);
            }*/
        }
        return url;
    }
    
    public void writeName(List<PurchaseContract> pcList) {
        for (PurchaseContract contracts : pcList) {
            if (contracts != null && contracts.getSupplierDepName() != null && !"".equals(contracts.getSupplierDepName())) {
                Supplier supplier = supplierService.selectOne(contracts.getSupplierDepName());
                if (supplier != null) {
                    contracts.setSupplierDepName(supplier.getSupplierName());
                } else {
                    contracts.setSupplierDepName("");
                }
            }
            if (contracts != null && contracts.getPurchaseDepName() != null && !"".equals(contracts.getPurchaseDepName())) {
                Orgnization org = oService.findByCategoryId(contracts.getPurchaseDepName());
                if (org != null) {
                    contracts.setPurchaseDepName(org.getName());
                } else {
                    contracts.setPurchaseDepName("");
                }
            }
            
            if (contracts != null && contracts.getPurchaseType() != null && !"".equals(contracts.getPurchaseType())){
                DictionaryData dictionaryData = DictionaryDataUtil.findById(contracts.getPurchaseType());
                if (dictionaryData != null) {
                    contracts.setPurchaseType(dictionaryData.getName());
                } else {
                    contracts.setPurchaseType("");
                }
            }
            if (contracts.getMoney() != null && !"".equals(contracts.getMoney())) {
                contracts.setMoney(contracts.getMoney().setScale(4, BigDecimal.ROUND_HALF_UP));
            }
        }
    }
	
	/**
	* @Title: update
	* @author Shen Zhenfei 
	* @date 2016-9-19 下午5:12:15  
	* @Description: 审价提交 
	* @param @return      
	* @return String
	 * @throws Exception 
	 */
	@RequestMapping("/update")
	public String update(AppraisalContract appraisalContract){
		appraisalContract.setAppraisal(1);
		appraisalContract.setUpdatedAt(new Date());
		appraisalContractService.update(appraisalContract);
		return "redirect:select.html";
	}
	
	
	/**
	* @Title: selectDistribution
	* @author Shen Zhenfei 
	* @date 2016-9-20 下午2:09:10  
	* @Description: 分配列表
	* @param @return      
	* @return String
	 */
	@RequestMapping("/selectDistribution")
	public String selectDistribution(Model model,Integer page){
		List<AppraisalContract> list = appraisalContractService.selectDistribution(null,page==null?1:page);
		for (AppraisalContract ac : list) {
            Supplier supplier = supplierService.selectOne(ac.getSupplierName());
            if (supplier != null) {
                ac.setSupplierName(supplier.getSupplierName());
            } else {
                ac.setSupplierName("");
            }
        }
		model.addAttribute("list", new PageInfo<AppraisalContract>(list));
		logger.info(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
		return "bss/sstps/distribution/list";
	}
	
	/**
	* @Title: distributionUser
	* @author Shen Zhenfei 
	* @date 2016-9-20 下午3:01:40  
	* @Description: 跳转分配人页面
	* @param @return      
	* @return String
	 */
	@RequestMapping("/distributionUser")
	public String distributionUser(Model model,String sbid){
		model.addAttribute("id", sbid);
		return "bss/sstps/distribution/distribute_user";
	}
	
	/**
	* @Title: selectUser
	* @author Shen Zhenfei 
	* @date 2016-11-16 上午9:28:54  
	* @Description:获取监管人员的信息
	* @param       
	* @return void
	 */
	@RequestMapping("/selectUser")
	public void selectUser(HttpServletResponse response,HttpServletRequest request){
		HashMap<String,Object> map = new HashMap<>();
		map.put("code", "SUPERVISER_R");
		List<User> user = userService.findByRole(map);
		super.writeJson(response, user);
	}
	
	
	/**
	* @Title: updateTow
	* @author Shen Zhenfei 
	* @date 2016-9-20 下午1:58:39  
	* @Description: 分配任务
	* @param @param AppraisalContract
	* @param @return      
	* @return String
	 * @throws Exception 
	 * @throws Exception 
	 */
	@RequestMapping("/updateDistribution")
	public void updateDistribution(HttpServletResponse response,AppraisalContract appraisalContract) throws Exception{
		try {
			appraisalContract.setDistribution(1);
			appraisalContract.setUpdatedAt(new Date());
			appraisalContractService.update(appraisalContract);
		
			String msg = "分配成功";
			response.setContentType("text/html;charset=utf-8");
			response.getWriter()
					.print("{\"success\": " + true + ", \"msg\": \"" + msg
							+ "\"}");
			
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			response.getWriter().close();
		}
	}
	
	/**
	* @Title: serch
	* @author Shen Zhenfei 
	* @date 2016-10-8 上午11:05:15  
	* @Description: 根据条件模糊查询
	* @param @param AppraisalContract
	* @param @param like
	* @param @param model
	* @param @param page
	* @param @return      
	* @return String
	 */
	@RequestMapping("/serch")
	public String serch(AppraisalContract appraisalContract,Model model,Integer page, HttpServletRequest req){
		AppraisalContract sib = new AppraisalContract();
		String name = appraisalContract.getName();
		String code = appraisalContract.getCode();
		String supplierName = appraisalContract.getSupplierName();
		if(name!=null){
			sib.setName("%"+name+"%");
		}
		if(code!=null){
			sib.setCode("%"+code+"%");
		}
		if(supplierName!=null){
			sib.setSupplierName("%"+supplierName+"%");
		}
		
		User judge = (User)req.getSession().getAttribute("loginUser");
        List<AppraisalContract> list = new ArrayList<AppraisalContract>();
        if ("1".equals(judge.getOrg().getTypeName())) {
            list = appraisalContractService.selectByObjectLike(sib, page==null?1:page);
            for (AppraisalContract ac : list) {
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
            logger.info(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
            return "bss/sstps/appraisal/list";
        } else {
            sib.setAppraisal(1);
            list = appraisalContractService.selectByObjectLike(sib, page==null?1:page);
            for (AppraisalContract ac : list) {
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
            return "bss/sstps/distribution/list";
        }
	}

}
