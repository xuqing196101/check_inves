package ses.controller.sys.sms;

import bss.model.cs.ContractRequired;
import bss.model.cs.PurchaseContract;
import bss.service.cs.ContractRequiredService;
import bss.service.cs.PurchaseContractService;
import bss.service.pqims.SupplierPqrecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;
import common.constant.Constant;
import common.service.UploadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.sms.AfterSaleSer;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.AfterSaleSerService;
import ses.service.sms.SupplierService;
import ses.util.PropUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
	
	@Autowired
    private ContractRequiredService contractRequiredService;
    @Autowired
    private OrgnizationServiceI orgnizationServiceI;

    @Autowired
    private UploadService uploadService;

	/**ContractRequiredService
     * 
     *〈简述〉
     *〈详细描述〉
     * @author Administrator
     * @param model
     * @param code 合同编号
     * @param page
     * @return
     */
    @RequestMapping(value="/list")
    public String getAll(@CurrentUser User user, Model model, String code, String goodsName,String name, Integer page){
    	if(page==null){
            page=1;
        }
        if(user!=  null){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("page", page);
			 List<AfterSaleSer> AfterSaleSers = afterSaleSerService.queryBySupplierIdList(user.getTypeId(),goodsName,code,name,map);
			/* if(null!=AfterSaleSers && !AfterSaleSers.isEmpty()){
				 for(AfterSaleSer after:AfterSaleSers){
					 ContractRequired selectConRequByPrimaryKey = contractRequiredService.selectConRequByPrimaryKey(after.getRequiredId());
					 if(selectConRequByPrimaryKey==null){
						 continue;
					 }
					 PurchaseContract selectById = purchaseContractService.selectById(selectConRequByPrimaryKey.getContractId());
					 after.setContractCode(selectById.getCode());
					 after.setMoney(selectById.getMoney());
					 after.setRequiredId(selectConRequByPrimaryKey.getGoodsName());
				 }
			 }*/
			 PageInfo<AfterSaleSer> list = new PageInfo<AfterSaleSer>(AfterSaleSers);
			 model.addAttribute("list", list);
			model.addAttribute("goodsName", goodsName);
			model.addAttribute("code", code);
			model.addAttribute("name", name);
		}
        return "ses/sms/after_sale_ser/list";
    }
	
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
		model.addAttribute("propertiesImg",PropUtil.getProperty("file.picture.type"));
		return "ses/sms/after_sale_ser/add";
	}
	@RequestMapping(value="/getContract",produces = "text/html; charset=utf-8")
	@ResponseBody
	public void createAllCommonContract(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response, String supplierId) throws Exception{
		System.out.println(user);
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("supplierDepName", user.getTypeId());
		List<PurchaseContract> selectDraftContract = purchaseContractService.selectAllContractBySupplier(map);
		/*for(){
			
		}*/
		super.writeJson(response, selectDraftContract);

	}
	@RequestMapping(value="/getProduct",produces = "text/html; charset=utf-8")
	@ResponseBody
	public void createAllCommonProduct(HttpServletRequest request,HttpServletResponse response, String id) throws Exception{
		HashMap<String, Object> map=new HashMap<String, Object>();
		List<ContractRequired> selectConRequeByContractId = contractRequiredService.selectConRequeByContractId(id);
		super.writeJson(response, selectConRequeByContractId);

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
	@RequestMapping(value="/save",produces = "text/html; charset=utf-8")
	public String save(@CurrentUser User user, AfterSaleSer afterSaleSer,Model model,String contractCode){
		Boolean flag = true;
		String url = "";
		if(contractCode==null || "".equals(contractCode)){
			flag = false;
			model.addAttribute("ERR_contract_code","请输入合同编号");
			model.addAttribute("contractCode",contractCode);
		}else{
			PurchaseContract pc=purchaseContractService.selectByCode(contractCode);
			if (pc==null) {
				flag = false;
				model.addAttribute("ERR_contract_code","合同编号不存在");
			}
			model.addAttribute("contractCode",contractCode);
		}
		if(StringUtils.isBlank(afterSaleSer.getAfterSaleSerId())){
			flag = false;
			model.addAttribute("ERR_img","产品使用说明或用户操作手册非法");
		}else{
			long sum=uploadService.countFileByBusinessId(afterSaleSer.getAfterSaleSerId(), "70", Constant.TENDER_SYS_KEY);
			if(sum==0){
				flag = false;
				model.addAttribute("ERR_img","产品使用说明或用户操作手册非法不能为空");
			}
		}
		if(afterSaleSer.getRequiredId() == null) {
			flag = false;
			model.addAttribute("ERR_requiredId", "产品名称不能为空!");
		}
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
			model.addAttribute("afterSaleSerId", afterSaleSer.getAfterSaleSerId());
			model.addAttribute("propertiesImg",PropUtil.getProperty("file.picture.type"));
			url="ses/sms/after_sale_ser/add";
		}else{
			if(user !=null){
				afterSaleSer.setId(afterSaleSer.getAfterSaleSerId());
				afterSaleSer.setSupplierId(user.getTypeId());
				afterSaleSerService.add(afterSaleSer);
				url="redirect:list.html";
			}
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
		AfterSaleSer afterSaleSer = afterSaleSerService.get(id);
		 ContractRequired selectConRequByPrimaryKey = contractRequiredService.selectConRequByPrimaryKey(afterSaleSer.getRequiredId());
		 PurchaseContract selectById = purchaseContractService.selectById(selectConRequByPrimaryKey.getContractId());
		 afterSaleSer.setContractCode(selectById.getCode());
		 afterSaleSer.setMoney(selectById.getMoney());
		model.addAttribute("after", afterSaleSer);
		 model.addAttribute("contractCode", selectById.getCode());
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
		public String update(HttpServletRequest request, AfterSaleSer afterSaleSer,Model model,String contractCode){
		Boolean flag = true;
		String url = "";
			
		if(contractCode==null || "".equals(contractCode)){
			flag = false;
			model.addAttribute("ERR_contract_code","请输入合同编号");
			model.addAttribute("contractCode",contractCode);
		}else{
			PurchaseContract pc=purchaseContractService.selectByCode(contractCode);
			if (pc==null) {
				flag = false;
				model.addAttribute("ERR_contract_code","合同编号不存在");
			}
			model.addAttribute("contractCode",contractCode);
		}
		if(afterSaleSer.getRequiredId() == null) {
			flag = false;
			model.addAttribute("ERR_requiredId", "产品名称不能为空!");
		}
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
			afterSaleSerService.update(afterSaleSer);
			
			url="redirect:list.html";
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
		//afterSaleSer.getContract().setPurchaseType(DictionaryDataUtil.findById(afterSaleSer.getContract().getPurchaseType()).getName());
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
		for(int i=0;i<id.length;i++){
			afterSaleSerService.delete(id[i]);
		}
		return "redirect:list.html";
	}
	@RequestMapping(value = "update_status")
	public String updateAfterSaleSer(AfterSaleSer AfterSaleSer) {
		afterSaleSerService.saveOrUpdateAfterSaleSer(AfterSaleSer);
		return "redirect:list.html";
	}
	
	/**
	 * 
	 *〈根据合同编号查看售后〉
	 *〈详细描述〉
	 * @author FengTian
	 * @param model
	 * @param code
	 * @param type
	 * @param page
	 * @return
	 */
	@RequestMapping("/viewAfter")
	public String viewAfter(Model model, String code, String type, Integer page){
	    if(StringUtils.isNotBlank(type) && StringUtils.isNotBlank(code)){
	        PurchaseContract contract = purchaseContractService.selectByCode(code);
	        if(contract != null && StringUtils.isNotBlank(contract.getId())){
	            if(page == null){
	                page = 1;
	            }
	            PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
	            HashMap<String, Object> map = new HashMap<>();
                map.put("contractId", contract.getId());
                List<AfterSaleSer> selectByAll = afterSaleSerService.selectByAll(map);
                if(selectByAll != null && selectByAll.size() > 0){
                    for(AfterSaleSer after:selectByAll){
                        ContractRequired selectConRequByPrimaryKey = contractRequiredService.selectConRequByPrimaryKey(after.getRequiredId());
                        PurchaseContract selectById = purchaseContractService.selectById(selectConRequByPrimaryKey.getContractId());
                        after.setContractCode(selectById.getCode());
                        after.setMoney(selectById.getMoney());
                        after.setRequiredId(selectConRequByPrimaryKey.getGoodsName());
                    }
                    PageInfo<AfterSaleSer> list = new PageInfo<AfterSaleSer>(selectByAll);
                    model.addAttribute("list", list);
                }
	        }
	        model.addAttribute("type", type);
	    }
	    return "ses/sms/after_sale_ser/list";
	}

}
