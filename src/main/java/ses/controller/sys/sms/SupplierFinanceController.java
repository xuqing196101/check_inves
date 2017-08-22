package ses.controller.sys.sms;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;
import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierDictionaryData;
import ses.model.sms.SupplierFinance;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.TodosService;
import ses.service.sms.SupplierFinanceService;
import ses.service.sms.SupplierService;
import ses.util.PropUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@Scope("prototype")
@RequestMapping(value = "/supplier_finance")
public class SupplierFinanceController extends BaseSupplierController {
	
	@Autowired
	private SupplierFinanceService supplierFinanceService;// 供应商财务信息
	
	@Autowired
	private SupplierService supplierService;// 供应商基本信息
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
	private UploadService uploadService;
	
    /**
     * 发送待办服务层    
     */
    @Autowired
    private TodosService todosService;
	
	@RequestMapping(value = "add_finance")
	public String addCertEng(Model model, SupplierFinance supplierFinance) {
		model.addAttribute("supplierFinance", supplierFinance);
		model.addAttribute("uuid", UUID.randomUUID().toString().toUpperCase().replace("-", ""));
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
//		List<Integer> yearList=new ArrayList<Integer>();
//		Date date=new Date();
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//		String mont=sdf.format(date).split("-")[1];
//		Integer month=Integer.valueOf(mont);
//		Integer year = getYear();
//		 int year2=year-2;
//		 int year3=year-3;
//		if(month<6){
//			int yera4=year-4;
//			yearList.add(yera4);
//		}else{
//			int yera4=year-1;
//			yearList.add(yera4);
//		}
//		yearList.add(year2);
//		yearList.add(year3);
//		
//		model.addAttribute("yearList", yearList);
		return "ses/sms/supplier_register/add_finance";
	}
	
	@RequestMapping(value = "save_or_update_finance",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String saveOrUpdateCertEng( SupplierFinance supplierFinance,Model model) throws IOException {
	//	this.setFinanceUpload(request, supplierFinance);
		
//		Supplier supplier = supplierService.get(supplierId);
//		request.getSession().setAttribute("defaultPage", "tab-2");
//		request.getSession().setAttribute("currSupplier", supplier);
//		request.getSession().setAttribute("jump.page", "basic_info");
		Map<String, Object> map = validate(supplierFinance);
		boolean bool = (boolean) map.get("bool");
		if(bool==true){
			supplierFinanceService.saveOrUpdateFinance(supplierFinance);
			SupplierFinance finance = supplierFinanceService.queryById(supplierFinance.getId());
			map.put("fiance", finance);
			map.put("sysKey",  Constant.SUPPLIER_SYS_KEY);
		}
		return JSON.toJSONString(map);
	 
		
	}
	
	@RequestMapping(value = "back_to_basic_info")
	public String backToEngfessional(HttpServletRequest request, String supplierId) {
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-2");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "basic_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	@RequestMapping(value = "delete_finance")
	public String deleteCertEng(HttpServletRequest request, String financeIds, String supplierId) {
		supplierFinanceService.deleteFinance(financeIds);
		Supplier supplier = supplierService.get(supplierId);
//		request.getSession().setAttribute("defaultPage", "tab-2");
		request.getSession().setAttribute("currSupplier", supplier);
//		request.getSession().setAttribute("jump.page", "basic_info");
//		return "redirect:../supplier/page_jump.html";
		return "ses/sms/supplier_register/basic_info";
	}
	
	/**
	 *〈简述〉打开修改页面 财务审计报告
	 *〈详细描述〉
	 * @author Song Biaowei
	 * @param model
	 * @param supplierFinance
	 * @return String
	 */
	@RequestMapping(value = "edit")
    public String edit(Model model, SupplierFinance supplierFinance) {
        supplierFinance = supplierFinanceService.queryById(supplierFinance.getId());
        model.addAttribute("supplierFinance", supplierFinance);
        model.addAttribute("uuid", UUID.randomUUID().toString().toUpperCase().replace("-", ""));
        model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
        model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
        return "bss/supplier/finance/edit";
    }
	
	/**
	 *〈简述〉查看详情
	 *〈详细描述〉
	 * @author Song Biaowei
	 * @param model
	 * @param supplierFinance
	 * @return String
	 */
	@RequestMapping(value = "show")
    public String show(Model model, SupplierFinance supplierFinance) {
        supplierFinance = supplierFinanceService.queryById(supplierFinance.getId());
        model.addAttribute("supplierFinance", supplierFinance);
        model.addAttribute("uuid", UUID.randomUUID().toString().toUpperCase().replace("-", ""));
        model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
        model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
        return "bss/supplier/finance/show";
    }
	
	/**
	 *〈简述〉打开增加页面
	 *〈详细描述〉
	 * @author Song Biaowei
	 * @param user
	 * @param model
	 * @param supplierFinance
	 * @return String
	 */
	@RequestMapping(value = "add")
    public String add(@CurrentUser User user, Model model, SupplierFinance supplierFinance) {
        model.addAttribute("uuid", UUID.randomUUID().toString().toUpperCase().replace("-", ""));
        model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
        model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
        model.addAttribute("propertiesImg",PropUtil.getProperty("file.picture.type"));
        return "bss/supplier/finance/add";
    }
	
	/**
	 *〈简述〉保存
	 *〈详细描述〉
	 * @author Song Biaowei
	 * @param user
	 * @param model
	 * @param supplierFinance
	 * @return String
	 */
	@RequestMapping(value = "save")
    public String save(@CurrentUser User user, Model model, SupplierFinance supplierFinance) {
	    Map<String ,Object> map = validate(supplierFinance);
	    if (map.get("bool").equals(true)) {
	        supplierFinance.setSupplierId(user.getTypeId());
	        supplierFinance.setIsDeleted(0);
	        supplierFinance.setCreatedAt(new Timestamp(new Date().getTime()));
	        supplierFinanceService.save(supplierFinance);
	        Supplier supplier = supplierService.get(supplierFinance.getSupplierId());
	        Todos todo = new Todos();
	        todo.setSenderId(user.getId());
	        todo.setOrgId(supplier.getProcurementDepId());
	        todo.setPowerId(PropUtil.getProperty("gysedit"));
	        todo.setUndoType((short) 1);
	        todo.setName("财务审计报告审核");
	        todo.setIsDeleted((short) 0);
	        todo.setCreatedAt(new Date());
	        todo.setUrl("supplier_finance/audit.html?id=" + supplierFinance.getId());
	        todosService.insert(todo);
	        return "redirect:list.html";
	    } else {
	        model.addAttribute("uuid", supplierFinance.getId());
	        model.addAttribute("supplierFinance", supplierFinance);
	        model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
	        model.addAttribute("map", map);
	        model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
	        return "bss/supplier/finance/add";
	    }
    }
	
	/**
	 *〈简述〉打开审核页面
	 *〈详细描述〉
	 * @author Song Biaowei
	 * @param user
	 * @param model
	 * @param supplierFinance
	 * @return String
	 */
	@RequestMapping(value = "audit")
    public String audit(@CurrentUser User user, Model model, SupplierFinance supplierFinance) {
	    SupplierFinance sf = supplierFinanceService.queryById(supplierFinance.getId());
	    model.addAttribute("supplierFinance", sf);
	    model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
	    model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
        return "bss/supplier/finance/audit";
    }
	
	/**
	 *〈简述〉审核结束
	 *〈详细描述〉
	 * @author Song Biaowei
	 * @param user
	 * @param model
	 * @param supplierFinance
	 * @return String
	 */
	@RequestMapping(value = "auditEnd")
    public String auditEnd(@CurrentUser User user, Model model, SupplierFinance supplierFinance) {
	        todosService.updateIsFinish("supplier_finance/audit.html?id=" + supplierFinance.getId());
	        supplierFinanceService.update(supplierFinance);
	        return "redirect:/login/home.html";
    }
	
	/**
	 *〈简述〉更新
	 *〈详细描述〉
	 * @author Song Biaowei
	 * @param user
	 * @param model
	 * @param supplierFinance
	 * @return String
	 */
	@RequestMapping(value = "update")
    public String update(@CurrentUser User user, Model model, SupplierFinance supplierFinance) {
	    Map<String ,Object> map = validate(supplierFinance);
        if (map.get("bool").equals(true)) {
            supplierFinanceService.update(supplierFinance);
            return "redirect:list.html";
        } else {
            supplierFinance = supplierFinanceService.queryById(supplierFinance.getId());
            model.addAttribute("supplierFinance", supplierFinance);
            model.addAttribute("uuid", UUID.randomUUID().toString().toUpperCase().replace("-", ""));
            model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
            model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
            return "bss/supplier/finance/edit";
        }
    }
	
	/**
	 *〈简述〉提交
	 *〈详细描述〉
	 * @author Song Biaowei
	 * @param id
	 * @return boolean
	 */
	@RequestMapping(value = "tijiao")
	@ResponseBody
	public boolean tijiao(String id) {
	    boolean data = true;
	    SupplierFinance supplierFinance = supplierFinanceService.queryById(id);
	    if (supplierFinance.getStatus() == 2 || supplierFinance.getStatus() ==3 || supplierFinance.getStatus() ==5) {
	        data = false;
	    } else {
	        supplierFinance.setStatus(2);
	        supplierFinanceService.update(supplierFinance);
	    }
	    return data;
	}
	
	
	/**
	 *〈简述〉分页list
	 *〈详细描述〉
	 * @author Song Biaowei
	 * @param user
	 * @param model
	 * @param supplierFinance
	 * @param page
	 * @return String
	 */
    @RequestMapping(value = "/list")
    public String list(@CurrentUser User user, Model model, SupplierFinance supplierFinance, Integer page) {
        supplierFinance.setSupplierId(user.getTypeId());
        List<SupplierFinance> listSf = supplierFinanceService.selectFinanceBySupplierId(supplierFinance, page == null ? 0 : page);
        /*SupplierDictionaryData supplierDictionary = dictionaryDataServiceI.getSupplierDictionary();
        for (SupplierFinance sf : listSf) {
            //财务利润
            List<UploadFile> tlist1 = uploadService.getFilesOther(sf.getId(), supplierDictionary.getSupplierProfit(), Constant.SUPPLIER_SYS_KEY.toString());
            //审计报告意见
            List<UploadFile> tlist2 = uploadService.getFilesOther(sf.getId(), supplierDictionary.getSupplierAuditOpinion(), Constant.SUPPLIER_SYS_KEY.toString());
            //资产负债
            List<UploadFile> tlist3 = uploadService.getFilesOther(sf.getId(), supplierDictionary.getSupplierLiabilities(), Constant.SUPPLIER_SYS_KEY.toString());
            //现金流量表
            List<UploadFile> tlist4 = uploadService.getFilesOther(sf.getId(), supplierDictionary.getSupplierCashFlow(), Constant.SUPPLIER_SYS_KEY.toString());
            //权益变动表
            List<UploadFile> tlist5 = uploadService.getFilesOther(sf.getId(), supplierDictionary.getSupplierOwnerChange(), Constant.SUPPLIER_SYS_KEY.toString());
            if (tlist1 != null && tlist1.size() > 0) {
                sf.setProfitList(tlist1.get(0).getName());
            }
            if (tlist2 != null && tlist2.size() > 0) {
                sf.setAuditOpinion(tlist2.get(0).getName());
            }
            if (tlist3 != null && tlist3.size() > 0) {
                sf.setLiabilitiesList(tlist3.get(0).getName());
            }
            if (tlist4 != null && tlist4.size() > 0) {
                sf.setCashFlowStatement(tlist4.get(0).getName());
            }
            if (tlist5 != null && tlist5.size() > 0) {
                sf.setChangeList(tlist5.get(0).getName());
            }
        }*/
        model.addAttribute("listSf", new PageInfo<>(listSf));
        model.addAttribute("finance", supplierFinance);
        return "bss/supplier/finance/list";
    }
    
    /**
     *〈简述〉删除
     *〈详细描述〉
     * @author Song Biaowei
     * @param id
     * @return String
     */
    @RequestMapping(value = "delete")
    public String delete(String id) {
        supplierFinanceService.deleteFinance(id);
        return "redirect:list.html";
    }
	
	
	public  Map<String,Object>  validate(SupplierFinance supplierFinance){
		Map<String,Object> map=new HashMap<String,Object>();
		boolean bool=true;
		if(supplierFinance.getYear()==null){
			map.put("year", "不能为空");
			bool=false;
		}
		else{
			if(supplierFinance.getYear().length()!=4){
				map.put("year", "年度数据格式不正确！");
				bool=false;
			}
		}
		if(supplierFinance.getName()==null||supplierFinance.getName().length()>12){
			map.put("name", "不能为空");
			bool=false;
		}
		if(supplierFinance.getTelephone()==null||supplierFinance.getTelephone().length()>11||!supplierFinance.getTelephone().matches("^(1)[0-9]{10}$")){
			map.put("phone", "不能为空或者格式不正确");
			bool=false;
		}
		if(supplierFinance.getAuditors()==null||supplierFinance.getAuditors().length()>12){
			map.put("auditors", "不能为空");
			bool=false;
		}
	/*	if(supplierFinance.getQuota()==null||supplierFinance.getQuota().length()>30){
			map.put("quota", "不能为空");
			bool=false;
		}*/
		if(supplierFinance.getTotalAssets()==null){
			map.put("assets", "不能为空");
			bool=false;
		}
		if(supplierFinance.getTotalAssets()!=null&&!supplierFinance.getTotalAssets().toString().matches("^(([0-9]+//.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*//.[0-9]+)|([0-9]*[1-9][0-9]*))$")&&supplierFinance.getTotalAssets().toString().length()>11){
			map.put("assets", "不能为空");
			bool=false;
		}
		if(supplierFinance.getTotalLiabilities()==null){
			map.put("bilit", "不能为空");
			bool=false;
		}
		if(supplierFinance.getTotalLiabilities()!=null&&!supplierFinance.getTotalLiabilities().toString().matches("^(([0-9]+//.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*//.[0-9]+)|([0-9]*[1-9][0-9]*))$")&&supplierFinance.getTotalLiabilities().toString().length()>11){
			map.put("bilit", "金额错误");
			bool=false;
		}
		if(supplierFinance.getTotalNetAssets()==null){
			map.put("noAssets", "不能为空");
			bool=false;
		}
		if(supplierFinance.getTotalNetAssets()!=null&&!supplierFinance.getTotalNetAssets().toString().matches("^(([0-9]+//.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*//.[0-9]+)|([0-9]*[1-9][0-9]*))$")&&supplierFinance.getTotalNetAssets().toString().length()>11){
			map.put("noAssets", "金额错误");
			bool=false;
		}
		if(supplierFinance.getTaking()==null){
			map.put("taking", "不能为空");
			bool=false;
		}
		if(supplierFinance.getTaking()!=null&&!supplierFinance.getTaking().toString().matches("^(([0-9]+//.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*//.[0-9]+)|([0-9]*[1-9][0-9]*))$")&&supplierFinance.getTaking().toString().length()>11){
			map.put("taking", "金额格式错误");
			bool=false;
		}
		
		SupplierDictionaryData supplierDictionary = dictionaryDataServiceI.getSupplierDictionary();
		//* 财务审计报告意见
		List<UploadFile> tlist = uploadService.getFilesOther(supplierFinance.getId(), supplierDictionary.getSupplierAuditOpinion(), Constant.SUPPLIER_SYS_KEY.toString());
		if(tlist!=null&&tlist.size()<=0){
			map.put("err_taxCert", "请上传文件!");
			bool=false;
		}
		//* 资产负债表
		List<UploadFile> blist = uploadService.getFilesOther(supplierFinance.getId(), supplierDictionary.getSupplierLiabilities(), Constant.SUPPLIER_SYS_KEY.toString());
		if(blist!=null&&blist.size()<=0){
			bool=false;
			map.put("err_bil", "请上传文件!");
		}
		//利润表：
		List<UploadFile> slist = uploadService.getFilesOther(supplierFinance.getId(), supplierDictionary.getSupplierProfit(), Constant.SUPPLIER_SYS_KEY.toString());
		if(slist!=null&&slist.size()<=0){
			bool=false;
			map.put("err_security", "请上传文件!");
		}
		//现金流量表:
		List<UploadFile> bearlist = uploadService.getFilesOther(supplierFinance.getId(), supplierDictionary.getSupplierCashFlow(), Constant.SUPPLIER_SYS_KEY.toString());
		if(bearlist!=null&&bearlist.size()<=0){
			bool=false;
			map.put("err_bearch", "请上传文件!");
		}
		
		//所有者权益变动表：
//		List<UploadFile> list = uploadService.getFilesOther(supplierFinance.getId(), supplierDictionary.getSupplierOwnerChange(), Constant.SUPPLIER_SYS_KEY.toString());
//		if(list!=null&&list.size()<=0){
//			bool=false;
//			map.put("err_business", "请上传文件!");
//		}
		map.put("bool", bool);
		return map;
		
		
	}
	
	public   Integer getYear() {
		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		return year;
	}
	
	
}
