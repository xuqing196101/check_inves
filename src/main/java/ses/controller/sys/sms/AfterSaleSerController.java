package ses.controller.sys.sms;

import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;



import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bss.model.cs.PurchaseContract;
import bss.model.pqims.PqInfo;
import bss.model.pqims.SupplierPqrecord;
import bss.service.cs.PurchaseContractService;
import bss.service.pqims.SupplierPqrecordService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.constant.Constant;
import ses.dao.sms.AfterSaleSerMapper;
import ses.model.bms.DictionaryData;
import ses.model.sms.AfterSaleSer;
import ses.model.sms.Supplier;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.sms.AfterSaleSerService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;

@Controller
@Scope("prototype")
@RequestMapping("/after_sale_ser")
public class AfterSaleSerController extends BaseSupplierController{
	
	
	
	@Autowired
	private AfterSaleSerService  afterSaleSerService; //售后服务
	
	@Resource
	private PurchaseContractService purchaseContractService;
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
    private SupplierService supplierService;
	
	@Autowired
    private SupplierPqrecordService supplierPqrecordService;
	
	

	/**
     * 
     *〈简述〉
     *〈详细描述〉
     * @author Administrator
     * @param model
     * @param code 合同编号
     * @param type 类型 1：从质检那边进来的只能查看不能有别的操作
     * @param page
     * @return
     */
    @RequestMapping(value="/list")
    public String getAll(Model model, String code, String type, Integer page){
        HashMap<String, Object> map = new HashMap<>();
        if(StringUtils.isNotBlank(code)){
            PurchaseContract purchaseCon = purchaseContractService.selectByCode(code);
            if(purchaseCon != null){
                map.put("purchaseCon", purchaseCon);
            }
            if(page == null){
                page = 1;
            }
            PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
            List<AfterSaleSer> selectByAll = afterSaleSerService.selectByAll(map);
            if(selectByAll != null && selectByAll.size() > 0){
                model.addAttribute("list", new PageInfo<AfterSaleSer>(selectByAll));
            }
        }
        /*List<AfterSaleSer> AfterSaleSers = afterSaleSerService.getAll(page==null?1:page);
        model.addAttribute("list",new PageInfo<AfterSaleSer>(AfterSaleSers));*/
        model.addAttribute("type", type);
        return "ses/sms/after_sale_ser/list";
    }
	
	/**
	 * 
	 * @Title: add
	 * @author LiChenHao  
	 * @Description:新增售后服务信息
	 * @param:     
	 * @return:
	 *//*
	@RequestMapping(value = "/add")
	public String add(Model model, AfterSaleSer afterSaleSer) {
		String id = afterSaleSer.getId();
		if (id != null && !"".equals(id)) {
			afterSaleSer = afterSaleSerService.get(id);
			model.addAttribute("AfterSaleSer",afterSaleSer);
		}
		return "ses/sms/after_sales_ser/add";
	}*/
	/**
	 * 
	 * @Title: add
	 * @author LiChenHao 
	 * @Description:跳转至新增编辑页面
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request,Model model){
		String afterSaleSeruid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
		model.addAttribute("afterSaleSerId", afterSaleSeruid);
		DictionaryData dd=new DictionaryData();
		dd.setCode("CONTRACT_APPROVE_ATTACH");
		List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
		request.getSession().setAttribute("sysKey", Constant.TENDER_SYS_KEY);
		if(datas.size()>0){
			model.addAttribute("attachTypeId", datas.get(0).getId());
		}
		return "ses/sms/after_sale_ser/add";
	}
	@RequestMapping(value="/getContract",produces = "text/html; charset=utf-8")
	@ResponseBody
	public void createAllCommonContract(HttpServletRequest request,HttpServletResponse response, String supplierId) throws Exception{
		HashMap<String, Object> map=new HashMap<String, Object>();
		/*map.put("supplierDepName", value)*/
		List<PurchaseContract> selectDraftContract = purchaseContractService.selectAllContract();
		super.writeJson(response, selectDraftContract);

	}
	
	/**
	 * 
	 * @Title: save
	 * @author LiChenHao 
	 * @Description:保存新增信息
	 * @param:     
	 * @return:
	 */
	@SuppressWarnings("null")
	@RequestMapping("/save")
	public String save(HttpServletRequest request,@RequestParam("dateString") String dateString,
			@Valid AfterSaleSer afterSaleSer,BindingResult result,Model model){
		
		Boolean flag = true;
		String url = "";
			
		if(afterSaleSer.getContract().getCode()==null || afterSaleSer.getContract().getCode().equals("")){
			flag = false;
			model.addAttribute("ERR_contract_code","请输入合同编号");
		}else{
			PurchaseContract pc=purchaseContractService.selectByCode(afterSaleSer.getContract().getCode());
			if (pc==null) {
				flag = false;
				model.addAttribute("ERR_contract_code","合同编号不存在");
			}else{
				afterSaleSer.setContract(pc);
			}
		}
		/*if(afterSaleSer.getAddress() == null) {
			flag = false;
			model.addAttribute("ERR_address", "产品名称不能为空!");
		}*/
		if(afterSaleSer.getAddress() == null) {
			flag = false;
			model.addAttribute("ERR_address", "全国售后地址不能为空!");
		}
		if(afterSaleSer.getContactName() == null) {
			flag = false;
			model.addAttribute("ERR_contactName", "联系人不能为空!");
		}
		if(afterSaleSer.getTechnicalParameters() == null) {
			flag = false;
			model.addAttribute("ERR_technicalParameters", "技术参数不能为空!");
		}
		if(afterSaleSer.getMobile() == null) {
			flag = false;
			model.addAttribute("ERR_mobile", "联系电话不能为空!");
		}
		if(flag == false){
			
			url="ses/sms/after_sale_ser/add";
		}else{
			
		}
		return url;
	}
	/**
	 * 
	 * @Title: edit
	 * @author LiChenHao 
	 * @Description:跳转修改编辑页面
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/edit")
	public String edit(HttpServletRequest request,Model model,String id){
		
		model.addAttribute("afterSaleSer",afterSaleSerService.get(id));
		model.addAttribute("afterSaleSerID",id);
		DictionaryData dd=new DictionaryData();
		dd.setCode("CONTRACT_APPROVE_ATTACH");
		List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
		request.getSession().setAttribute("afterSaleSerKey", Constant.TENDER_SYS_KEY);
		if(datas.size()>0){
			model.addAttribute("attachtypeId", datas.get(0).getId());
		}
		return "ses/sms/after_sale_ser/edit";
	}
	
	/**
	 * 
	 * @Title: update
	 * @author LiChenHao 
	 * @Description:更新修改信息
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/update")
	public String update(HttpServletRequest request,@RequestParam("date") String dateString,
			@Valid AfterSaleSer afterSaleSer,BindingResult result,Model model){
		Boolean flag = true;
		String url = "";
			
		if(afterSaleSer.getContract().getCode()==null || afterSaleSer.getContract().getCode().equals("")){
			flag = false;
			model.addAttribute("ERR_contract_code","请输入合同编号");
		}else{
			PurchaseContract pc=purchaseContractService.selectByCode(afterSaleSer.getContract().getCode());
			if (pc==null) {
				flag = false;
				model.addAttribute("ERR_contract_code","合同编号不存在");
			}else{
				afterSaleSer.setContract(pc);
			}
		}
		/*if(afterSaleSer.getAddress() == null) {
			flag = false;
			model.addAttribute("ERR_address", "产品名称不能为空!");
		}*/
		if(afterSaleSer.getAddress() == null) {
			flag = false;
			model.addAttribute("ERR_address", "全国售后地址不能为空!");
		}
		if(afterSaleSer.getContactName() == null) {
			flag = false;
			model.addAttribute("ERR_contactName", "联系人不能为空!");
		}
		if(afterSaleSer.getTechnicalParameters() == null) {
			flag = false;
			model.addAttribute("ERR_technicalParameters", "技术参数不能为空!");
		}
		if(afterSaleSer.getMobile() == null) {
			flag = false;
			model.addAttribute("ERR_mobile", "联系电话不能为空!");
		}
		if(flag == false){
			
			url="ses/sms/after_sale_ser/edit";
		}else{
			
		}
		return url;
	}
	
	/**
	 * 
	 * @Title: view
	 * @author LiChenHao 
	 * @Description:跳转查看页面
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/view")
	public String view(Model model,String id){
		AfterSaleSer afterSaleSer = afterSaleSerService.get(id);
		afterSaleSer.getContract().setPurchaseType(DictionaryDataUtil.findById(afterSaleSer.getContract().getPurchaseType()).getName());
		model.addAttribute("afterSaleSer",afterSaleSer);
		return "ses/sms/after_sale_ser/show";
	}
	
	/**
	 * 
	 * @Title: delete
	 * @author LiChenHao 
	 * @Description:批量删除质检信息
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/delete")
	public String delete(String ids){
		String[] id=ids.split(",");
		for (String str : id) {
			AfterSaleSer afterSaleSer = afterSaleSerService.get(str);
			String supplierId = afterSaleSerService.get(str).getContract().getSupplier().getId();
			String supplierName = afterSaleSerService.get(str).getContract().getSupplier().getSupplierName();
			afterSaleSerService.delete(str);
			int count = afterSaleSerService.queryByConut(supplierId);
			if (count == 0) {
				SupplierPqrecord sPqrecord = supplierPqrecordService.selectByName(supplierName);
				supplierPqrecordService.delete(sPqrecord.getId());
			}else {
			     SupplierPqrecord supplierPqrecord = supplierPqrecordService.selectByName(supplierName);
			     if (supplierPqrecord==null) {
			    	 Supplier supplier = afterSaleSer.getContract().getSupplier();
			    	 supplier.setSupplierName(supplierName);
					 supplierPqrecord.setSupplier(supplier);
			    	 supplierPqrecordService.add(supplierPqrecord);
			     }
			     supplierPqrecord = supplierPqrecordService.selectByName(supplierName);
			     supplierPqrecordService.update(supplierPqrecord);
			}
		}
		return "redirect:getAll.html";
	}
	@RequestMapping(value = "update_status")
	public String updateAfterSaleSer(AfterSaleSer AfterSaleSer) {
		afterSaleSerService.saveOrUpdateAfterSaleSer(AfterSaleSer);
		return "redirect:list.html";
	}

}
