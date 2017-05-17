
package bss.controller.pqims;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;

import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.Constant;

import bss.model.cs.PurchaseContract;
import bss.model.ppms.Project;
import bss.model.ppms.Task;
import bss.model.pqims.PqInfo;
import bss.model.pqims.SupplierPqrecord;
import bss.model.pqims.Supplier_pqinfo;
import bss.model.sstps.Select;
import bss.service.cs.PurchaseContractService;
import bss.service.ppms.ProjectService;
import bss.service.pqims.PqInfoService;
import bss.service.pqims.SupplierPqrecordService;

/**
 * @Title:PqInfoController 
 * @Description:质检信息管理控制类 
 * @author Liyi
 * @date 2016-9-19上午9:50:25
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/pqinfo")
public class PqInfoController extends BaseSupplierController{
	@Resource
	private PqInfoService pqInfoService;
	
	@Resource
	private PurchaseContractService purchaseContractService;
	
    @Autowired
    private SupplierService supplierService;
	
    @Autowired
    private SupplierPqrecordService supplierPqrecordService;
    
    @Autowired
    private ProjectService projectService;
    
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
    private OrgnizationServiceI orgnizationService;
	/**
	 * 
	 * @Title: getAll
	 * @author Liyi 
	 * @date 2016-9-19 上午9:54:15  
	 * @Description:获取质检信息列表
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/getAll")
	public String getAll(Model model, @CurrentUser User user, Integer page){
	    if(user != null && user.getOrg().getId() != null){
	        Orgnization orgnization = orgnizationService.findByCategoryId(user.getOrg().getId());
	        if("1".equals(orgnization.getTypeName())){
	            List<PqInfo> pqInfos = pqInfoService.getAll(page==null?1:page);
	            model.addAttribute("list",new PageInfo<PqInfo>(pqInfos));
	        }
	    }
		return "bss/pqims/pqinfo/list";
	}
	
	/**
	 * 
	 * @Title: add
	 * @author Liyi 
	 * @date 2016-9-19 上午9:55:09  
	 * @Description:跳转至新增编辑页面
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request,Model model){
		String pqinfouuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
		model.addAttribute("pqinfoId", pqinfouuid);
		DictionaryData dd=new DictionaryData();
		dd.setCode("CONTRACT_APPROVE_ATTACH");
		List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
		request.getSession().setAttribute("sysKey", Constant.TENDER_SYS_KEY);
		if(datas.size()>0){
			model.addAttribute("attachTypeId", datas.get(0).getId());
		}
		return "bss/pqims/pqinfo/add";
	}
	
	/**
	 * 
	 * @Title: save
	 * @author Liyi 
	 * @date 2016-9-19 上午9:56:44  
	 * @Description:保存新增信息
	 * @param:     
	 * @return:
	 */
	@SuppressWarnings("null")
	@RequestMapping("/save")
	public String save(HttpServletRequest request,@RequestParam("dateString") String dateString,
			@Valid PqInfo pqInfo,BindingResult result,Model model){
		
		Boolean flag = true;
		String url = "";
		//设置质检日期
		if(dateString!=null && !dateString.equals("")){			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			ParsePosition pos = new ParsePosition(0);
			Date date = formatter.parse(dateString, pos);
			pqInfo.setDate(date);
		}else{
			flag = false;
			model.addAttribute("ERR_pqdate", "请选择质检日期");
		}
			
		if(pqInfo.getContract().getCode()==null || pqInfo.getContract().getCode().equals("")){
			flag = false;
			model.addAttribute("ERR_contract_code","请输入合同编号");
		}else{
			PurchaseContract pc=purchaseContractService.selectByCode(pqInfo.getContract().getCode());
			if (pc==null) {
				flag = false;
				model.addAttribute("ERR_contract_code","合同编号不存在");
			}else{
				pqInfo.setContract(pc);
			}
		}
		if(pqInfo.getProjectType()==null||pqInfo.getProjectType().equals("-请选择-")){
			flag = false;
			model.addAttribute("ERR_projectType", "请选择项目类型");
		}
		if(pqInfo.getType()==null||pqInfo.getType().equals("-请选择-")){
			flag = false;
			model.addAttribute("ERR_type", "请选择质检类型");
		}
		if(pqInfo.getConclusion()==null||pqInfo.getConclusion().equals("-请选择-")){
			flag = false;
			model.addAttribute("ERR_conclusion", "请选择质检结论");
		}
		if(result.hasErrors()){
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fieldError:errors){
				model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
			flag = false;
		}
		if(flag == false){
			String id = pqInfo.getContract().getSupplierDepName();
			if(id!=null){
				Supplier supplier = supplierService.selectOne(id);
				PurchaseContract pc = pqInfo.getContract();
				pc.setSupplierDepName(supplier.getSupplierName());
				pqInfo.setContract(pc);
			}
			model.addAttribute("pqinfo", pqInfo);
			url="bss/pqims/pqinfo/add";
		}else{
			 String report=pqInfoService.queryPath(pqInfo.getId());
			 if(report!=null && report!=""){
				 pqInfo.setReport(report);
			 }
		     //封装质检信息实体类
		     pqInfoService.add(pqInfo);
		     
		     Supplier supplier = pqInfo.getContract().getSupplier();
		     String id = supplier.getId();
		     String supplierName = supplier.getSupplierName();
		     SupplierPqrecord supplierPqrecord = supplierPqrecordService.selectByName(supplierName);
		     if (supplierPqrecord==null) {
		    	 supplierPqrecordService.insert(id);
		     }
		     supplierPqrecord = supplierPqrecordService.selectByName(supplier.getSupplierName());
		     BigDecimal countSuccess = pqInfoService.queryByCountSuccess(supplierPqrecord.getSupplier().getId());
		     if (countSuccess==null) {
		    	 countSuccess=new BigDecimal(0);
		     }
		     BigDecimal countFail =pqInfoService.queryByCountFail(supplierPqrecord.getSupplier().getId());
		     if (countFail==null) {
		    	 countFail=new BigDecimal(0);
		     }
		     supplierPqrecord.setSuccessedCount(countSuccess.intValue());
		     supplierPqrecord.setFailedCount(countFail.intValue());
		     supplierPqrecord.setSuccessedAvg(myPercent(countSuccess.doubleValue(),(countSuccess.doubleValue()+countFail.doubleValue())));
		     supplierPqrecordService.update(supplierPqrecord);
		     url = "redirect:getAll.html";
		}
		return url;
	}
	
	/**
	 * 
	 * @Title: edit
	 * @author Liyi 
	 * @date 2016-9-19 上午9:57:51  
	 * @Description:跳转修改编辑页面
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/edit")
	public String edit(HttpServletRequest request,Model model,String id){
		
		model.addAttribute("pqinfo",pqInfoService.get(id));
		model.addAttribute("pqinfoID",id);
		DictionaryData dd=new DictionaryData();
		dd.setCode("CONTRACT_APPROVE_ATTACH");
		List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
		request.getSession().setAttribute("pqinfoKey", Constant.TENDER_SYS_KEY);
		if(datas.size()>0){
			model.addAttribute("attachtypeId", datas.get(0).getId());
		}
		return "bss/pqims/pqinfo/edit";
	}
	
	/**
	 * 
	 * @Title: update
	 * @author Liyi 
	 * @date 2016-9-19 上午9:58:25  
	 * @Description:更新修改信息
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/update")
	public String update(HttpServletRequest request,@RequestParam("date") String dateString,
			@Valid PqInfo pqInfo,BindingResult result,Model model){
		Boolean flag = true;
		String url = "";
		//设置质检日期
		if(dateString!=null && !dateString.equals("")){			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			ParsePosition pos = new ParsePosition(0);
			Date date = formatter.parse(dateString, pos);
			pqInfo.setDate(date);
		}else{
			flag = false;
			model.addAttribute("ERR_pqdate", "请选择质检日期");
		}
		if(pqInfo.getContract().getCode()==null || pqInfo.getContract().getCode().equals("")){
			flag = false;
			model.addAttribute("ERR_contract_code","请输入合同编号");
		}else{
			PurchaseContract pc=purchaseContractService.selectByCode(pqInfo.getContract().getCode());
			if (pc==null) {
				flag = false;
				model.addAttribute("ERR_contract_code","合同编号不存在");
			}else{
				pqInfo.setContract(pc);
			}
		}
		if(pqInfo.getProjectType()==null||pqInfo.getProjectType().equals("")){
			flag = false;
			model.addAttribute("ERR_projectType", "请选择项目类型");
		}
		if(pqInfo.getType()==null||pqInfo.getType().equals("-请选择-")){
			flag = false;
			model.addAttribute("ERR_type", "请选择质检类型");
		}
		if(pqInfo.getConclusion()==null||pqInfo.getConclusion().equals("-请选择-")){
			flag = false;
			model.addAttribute("ERR_conclusion", "请选择质检结论");
		}
		if(result.hasErrors()){
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fieldError:errors){
				model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
			flag = false;
		}
		if(flag == false){
			String id = pqInfo.getContract().getSupplierDepName();
			Supplier supplier = supplierService.selectOne(id);
			PurchaseContract pc = pqInfo.getContract();
			pc.setSupplierDepName(supplier.getSupplierName());
			pqInfo.setContract(pc);
			model.addAttribute("pqinfo", pqInfo);
			url="bss/pqims/pqinfo/edit";
		}else{
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        ParsePosition pos = new ParsePosition(0);
	        Date date = formatter.parse(dateString, pos);
	        pqInfo.setDate(date);
			String report=pqInfoService.queryPath(pqInfo.getId());
			if(report!=null && report!=""){
				 pqInfo.setReport(report);
			}
	        pqInfoService.update(pqInfo);
	        Supplier supplier = pqInfo.getContract().getSupplier();
		     String id = supplier.getId();
		     String supplierName = supplier.getSupplierName();
		     SupplierPqrecord supplierPqrecord = supplierPqrecordService.selectByName(supplierName);
		     if (supplierPqrecord==null) {
		    	 supplierPqrecordService.insert(id);
		     }
		     supplierPqrecord = supplierPqrecordService.selectByName(supplier.getSupplierName());
		     BigDecimal countSuccess = pqInfoService.queryByCountSuccess(supplierPqrecord.getSupplier().getId());
		     if (countSuccess==null) {
		    	 countSuccess=new BigDecimal(0);
		     }
		     BigDecimal countFail =pqInfoService.queryByCountFail(supplierPqrecord.getSupplier().getId());
		     if (countFail==null) {
		    	 countFail=new BigDecimal(0);
		     }
		     supplierPqrecord.setSuccessedCount(countSuccess.intValue());
		     supplierPqrecord.setFailedCount(countFail.intValue());
		     supplierPqrecord.setSuccessedAvg(myPercent(countSuccess.doubleValue(),(countSuccess.doubleValue()+countFail.doubleValue())));
		     supplierPqrecordService.update(supplierPqrecord);
	        url="redirect:getAll.html";
		}
		return url;
	}
	
	
	
	/**
	 * 
	 * @Title: delete
	 * @author Liyi 
	 * @date 2016-9-19 上午10:00:06  
	 * @Description:批量删除质检信息
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/delete")
	public String delete(String ids){
		String[] id=ids.split(",");
		for (String str : id) {
			PqInfo pqInfo = pqInfoService.get(str);
			String supplierId = pqInfoService.get(str).getContract().getSupplier().getId();
			String supplierName = pqInfoService.get(str).getContract().getSupplier().getSupplierName();
			pqInfoService.delete(str);
			int count = pqInfoService.queryByConut(supplierId);
			if (count == 0) {
				SupplierPqrecord sPqrecord = supplierPqrecordService.selectByName(supplierName);
				supplierPqrecordService.delete(sPqrecord.getId());
			}else {
			     SupplierPqrecord supplierPqrecord = supplierPqrecordService.selectByName(supplierName);
			     if (supplierPqrecord==null) {
			    	 Supplier supplier = pqInfo.getContract().getSupplier();
			    	 supplier.setSupplierName(supplierName);
					 supplierPqrecord.setSupplier(supplier);
			    	 supplierPqrecordService.add(supplierPqrecord);
			     }
			     supplierPqrecord = supplierPqrecordService.selectByName(supplierName);
			     BigDecimal countSuccess = pqInfoService.queryByCountSuccess(supplierPqrecord.getSupplier().getId());
			     if (countSuccess==null) {
			    	 countSuccess=new BigDecimal(0);
			     }
			     BigDecimal countFail =pqInfoService.queryByCountFail(supplierPqrecord.getSupplier().getId());
			     if (countFail==null) {
			    	 countFail=new BigDecimal(0);
			     }
			     supplierPqrecord.setSuccessedCount(countSuccess.intValue());
			     supplierPqrecord.setFailedCount(countFail.intValue());
			     supplierPqrecord.setSuccessedAvg(myPercent(countSuccess.doubleValue(),(countSuccess.doubleValue()+countFail.doubleValue())));
			     supplierPqrecordService.update(supplierPqrecord);
			}
		}
		return "redirect:getAll.html";
	}
	
	/**
	 * 
	 * @Title: view
	 * @author Liyi 
	 * @date 2016-9-19 上午10:12:36  
	 * @Description:跳转查看页面
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/view")
	public String view(Model model,String id,String type){
		PqInfo pqInfo = pqInfoService.get(id);
		pqInfo.getContract().setPurchaseType(DictionaryDataUtil.findById(pqInfo.getContract().getPurchaseType()).getName());
		model.addAttribute("pqinfo",pqInfo);
		model.addAttribute("type",type);
		return "bss/pqims/pqinfo/view";
	}
	
	/**
	 * 
	 * @Title: search
	 * @author Liyi 
	 * @date 2016-9-29 下午1:33:07  
	 * @Description:
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/search")
	public String search(Model model,HttpServletRequest request,PqInfo pqInfo,Integer page){
		if(pqInfo!=null){
			List<PqInfo> pqInfos = pqInfoService.selectByCondition(pqInfo,page==null?1:page);
			model.addAttribute("list",new PageInfo<PqInfo>(pqInfos));
		}else{
			List<PqInfo> pqInfos = pqInfoService.getAll(page==null?1:page);
			model.addAttribute("list",new PageInfo<PqInfo>(pqInfos));
		}
		model.addAttribute("pqinfo",pqInfo);
		return "bss/pqims/pqinfo/list";
	}
	
	/**
	 * 
	 * @Title: getAllInfo
	 * @author Liyi 
	 * @date 2016-9-29 下午1:33:12  
	 * @Description:
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/getAllReasult")
	public String getAllResult(Model model,Integer page){
		List<PqInfo> pqInfos = pqInfoService.getAll(page==null?1:page);
		int i = 0;
		for (PqInfo pqInfo : pqInfos) {
		    if(pqInfo.getContract() != null && StringUtils.isNotBlank(pqInfo.getContract().getProjectId())){
		        String projectId = pqInfo.getContract().getProjectId();
	            Project project = projectService.selectById(projectId);
	            if(project!=null){
	                String purchaseDepName = project.getPurchaseDepId();
	                HashMap<String, Object> map = new HashMap<>();
	                map.put("typeName", "1");
	                List<Orgnization> orgnizations = orgnizationService.findOrgnizationList(map);
	                for(int j=0;j<orgnizations.size();j++){
	                    if(purchaseDepName.equals(orgnizations.get(j).getId())){
	                        pqInfo.getContract().setPurchaseDepName(orgnizations.get(j).getName());
	                        pqInfos.set(i, pqInfo);
	                        i++;
	                        break;
	                    }
	                }
	            }
		    }
		}
		model.addAttribute("list",new PageInfo<PqInfo>(pqInfos));
		return "bss/pqims/pqinfo/resultList";
	}
	
	/**
	 * 
	 * @Title: searchResult
	 * @author Liyi 
	 * @date 2016-9-29 下午1:33:07  
	 * @Description:
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/searchReasult")
	public String searchResult(Model model,HttpServletRequest request,PqInfo pqInfo,Integer page){
		if(pqInfo!=null){
			List<PqInfo> pqInfos = pqInfoService.selectByCondition(pqInfo,page==null?1:page);
			int i = 0;
			for (PqInfo pqInfo1 : pqInfos) {
				String projectId = pqInfo1.getContract().getProjectId();
				String purchaseDepName = projectService.selectById(projectId).getPurchaseDepName();
				pqInfo1.getContract().setPurchaseDepName(purchaseDepName);
				pqInfos.set(i, pqInfo1);
				i++;
			}
			model.addAttribute("list",new PageInfo<PqInfo>(pqInfos));
		}else{
			List<PqInfo> pqInfos = pqInfoService.getAll(page==null?1:page);
			int i = 0;
			for (PqInfo pqInfo1 : pqInfos) {
				String projectId = pqInfo1.getContract().getProjectId();
				String purchaseDepName = projectService.selectById(projectId).getPurchaseDepName();
				pqInfo1.getContract().setPurchaseDepName(purchaseDepName);
				pqInfos.set(i, pqInfo1);
				i++;
			}
			model.addAttribute("list",new PageInfo<PqInfo>(pqInfos));
		}
		model.addAttribute("pqinfo",pqInfo);
		return "bss/pqims/pqinfo/resultList";
	}
	
	/**
	 * 
	 * @Title: getAllSupplierPqInfo
	 * @author Liyi 
	 * @date 2016-9-29 下午2:19:08  
	 * @Description:
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/getAllSupplierPqInfo")
	public String getAllSupplierPqInfo(Model model,Integer page, SupplierPqrecord supplierPqrecord, HttpServletRequest request){
	    HashMap<String, Object> map = new HashMap<>();
	    if(supplierPqrecord.getSupplier() != null && StringUtils.isNotBlank(supplierPqrecord.getSupplier().getSupplierName())){
	        map.put("supplier", supplierPqrecord.getSupplier());
	    }
	    if(page==null){
            page = 1;
        }
        map.put("page", page.toString());
        PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
		List<SupplierPqrecord> list = supplierPqrecordService.getAll(map);
		model.addAttribute("info",new PageInfo<SupplierPqrecord>(list));
		model.addAttribute("supplierPqrecord",supplierPqrecord);
		return "bss/pqims/pqinfo/supplier_pqinfo_list";
	}
	
	@RequestMapping("/searchSupplier")
	public String searchSupplier(Model model,HttpServletRequest request,SupplierPqrecord supplierPqrecord,Integer page){
		if (supplierPqrecord!=null) {
			List<SupplierPqrecord> supplier_pqinfos = supplierPqrecordService.queryByName(supplierPqrecord.getSupplier().getSupplierName(), page==null?1:page);
			model.addAttribute("list",new PageInfo<SupplierPqrecord>(supplier_pqinfos));
			model.addAttribute("supplierPqrecord",supplierPqrecord);
			return "bss/pqims/pqinfo/supplier_pqinfo_list";
		}else{
			return "redirect:getAllSupplierPqInfo.html";
		}
		
	}
	
	  public static String myPercent(double y, double z) {  
	        String baifenbi = "";// 接受百分比的值  
	      /*  double baiy = y * 1.0;  
	        double baiz = z * 1.0;  */
	        double fen = y / z;  
	        // NumberFormat nf = NumberFormat.getPercentInstance(); 注释掉的也是一种方法  
	        // nf.setMinimumFractionDigits( 2 ); 保留到小数点后几位  
	        DecimalFormat df1 = new DecimalFormat("##.00%"); // ##.00%  
	                                                            // 百分比格式，后面不足2位的用0补齐  
	        // baifenbi=nf.format(fen);  
	        baifenbi = df1.format(fen);   
	        return baifenbi;  
	    }  
	  
	  	/**
	  	 * 
	  	 * @Title: selectContract
	  	 * @author Liyi 
	  	 * @date 2016-11-15 上午11:04:01  
	  	 * @Description:select2获取列表
	  	 * @param:     
	  	 * @return:
	  	 */
		@RequestMapping(value="/selectContract",produces="application/json;charest=utf-8")
		public void selectContract(HttpServletResponse response,HttpServletRequest request) throws Exception{
			String purchaseType = request.getParameter("purchaseType");
			List<Select> list = pqInfoService.selectChose(purchaseType);
			System.out.println("list:"+list);
			super.writeJson(response, list);
		}
}
