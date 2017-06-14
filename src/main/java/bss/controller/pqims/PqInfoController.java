
package bss.controller.pqims;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import ses.util.WfUtil;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.Constant;
import common.constant.StaticVariables;
import common.model.UploadFile;
import common.service.UploadService;

import bss.model.cs.PurchaseContract;
import bss.model.ppms.Project;
import bss.model.pqims.PqInfo;
import bss.model.pqims.SupplierPqrecord;
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
    private OrgnizationServiceI orgnizationService;
	
	@Autowired
	private UploadService uploadService;
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
	public String getAll(Model model, @CurrentUser User user, Integer page, PqInfo pqInfo){
	    if(user != null && user.getOrg() != null){
            Orgnization org = orgnizationService.getOrgByPrimaryKey(user.getOrg().getId());
            if(org != null && "1".equals(org.getTypeName())){
                HashMap<String, Object> map = new HashMap<>();
                if(pqInfo.getContract() != null && StringUtils.isNotBlank(pqInfo.getContract().getName())){
                    map.put("name", pqInfo.getContract().getName());
                }
                if(pqInfo.getContract() != null && StringUtils.isNotBlank(pqInfo.getContract().getCode())){
                    map.put("code", pqInfo.getContract().getCode());
                }
                if(StringUtils.isNotBlank(pqInfo.getType())){
                    map.put("type", pqInfo.getType());
                }
                if(StringUtils.isNotBlank(pqInfo.getConclusion())){
                    map.put("conclusion", pqInfo.getConclusion());
                }
                List<PqInfo> pqInfos = pqInfoService.getAll(page==null?1:page,map);
                if(pqInfos != null && pqInfos.size() > 0){
                    for (PqInfo pqInfo2 : pqInfos) {
                        if(StringUtils.isNotBlank(pqInfo2.getContract().getPurchaseDepName())){
                            Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(pqInfo2.getContract().getPurchaseDepName());
                            if(orgnization != null){
                                pqInfo2.getContract().setPurchaseDepName(orgnization.getShortName());
                            } else {
                                pqInfo2.getContract().setPurchaseDepName(null);
                            }
                            List<UploadFile> uploadFiles = uploadService.getFilesOther(pqInfo2.getId(), DictionaryDataUtil.getId("CONTRACT_APPROVE_ATTACH"), "2");
                            if(uploadFiles != null && uploadFiles.size() > 0){
                                pqInfo2.setReport(uploadFiles.get(0).getTypeId());
                            }else{
                                pqInfo2.setReport("0");
                            }
                        }
                    }
                }
                model.addAttribute("info",new PageInfo<PqInfo>(pqInfos));
                model.addAttribute("pqInfo", pqInfo);
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
	public String add(Model model){
	    model.addAttribute("id", WfUtil.createUUID());
		model.addAttribute("attachTypeId", DictionaryDataUtil.getId("CONTRACT_APPROVE_ATTACH"));
		model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
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
	@RequestMapping("/save")
	public String save(@Valid PqInfo pqInfo,BindingResult result,Model model){
		if(result.hasErrors()){
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fieldError:errors){
				model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
			model.addAttribute("pqinfo", pqInfo);
			return "bss/pqims/pqinfo/add";
		}
		pqInfoService.add(pqInfo);
		return "redirect:getAll.html";
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
	public String edit(Model model,String id){
	    if(StringUtils.isNotBlank(id)){
	        PqInfo pqInfo = pqInfoService.get(id);
	        if(pqInfo != null){
	            model.addAttribute("pqinfo",pqInfo);
	        }
	    }
		model.addAttribute("pqinfoKey", Constant.TENDER_SYS_KEY);
		model.addAttribute("attachtypeId", DictionaryDataUtil.getId("CONTRACT_APPROVE_ATTACH"));
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
	public String update(@Valid PqInfo pqInfo,BindingResult result,Model model){
		if(result.hasErrors()){
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fieldError:errors){
				model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
			model.addAttribute("pqinfo", pqInfo);
			 return "bss/pqims/pqinfo/edit";
		}
	    pqInfoService.update(pqInfo);
	    return "redirect:getAll.html";
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
	    if(StringUtils.isNotBlank(ids)){
	        String[] id=ids.split(StaticVariables.COMMA_SPLLIT);
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
	    if(StringUtils.isNotBlank(id)){
	        PqInfo pqInfo = pqInfoService.get(id);
	        pqInfo.getContract().setPurchaseType(DictionaryDataUtil.findById(pqInfo.getContract().getPurchaseType()).getName());
	        model.addAttribute("pqinfo",pqInfo);
	    }
		model.addAttribute("type",type);
		return "bss/pqims/pqinfo/view";
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
	public String getAllResult(@CurrentUser User user, Model model,Integer page, PqInfo pqInfo){
	    if(user != null && user.getOrg() != null){
	        Orgnization org = orgnizationService.getOrgByPrimaryKey(user.getOrg().getId());
	        if(org != null && "1".equals(org.getTypeName())){
	            HashMap<String, Object> map = new HashMap<>();
	            if(pqInfo.getContract() != null && StringUtils.isNotBlank(pqInfo.getContract().getName())){
	                map.put("name", pqInfo.getContract().getName());
	            }
	            if(pqInfo.getContract() != null && StringUtils.isNotBlank(pqInfo.getContract().getCode())){
	                map.put("code", pqInfo.getContract().getCode());
	            }
	            if(StringUtils.isNotBlank(pqInfo.getType())){
	                map.put("type", pqInfo.getType());
	            }
	            if(StringUtils.isNotBlank(pqInfo.getConclusion())){
	                map.put("conclusion", pqInfo.getConclusion());
	            }
	            List<PqInfo> pqInfos = pqInfoService.getAll(page==null?1:page,map);
	            if(pqInfos != null && pqInfos.size() > 0){
	                for (PqInfo pqInfo2 : pqInfos) {
	                    if(StringUtils.isNotBlank(pqInfo2.getContract().getPurchaseDepName())){
	                        Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(pqInfo2.getContract().getPurchaseDepName());
	                        if(orgnization != null){
	                            pqInfo2.getContract().setPurchaseDepName(orgnization.getShortName());
	                        } else {
	                            pqInfo2.getContract().setPurchaseDepName(null);
	                        }
	                        List<UploadFile> uploadFiles = uploadService.getFilesOther(pqInfo2.getId(), DictionaryDataUtil.getId("CONTRACT_APPROVE_ATTACH"), "2");
	                        if(uploadFiles != null && uploadFiles.size() > 0){
	                            pqInfo2.setReport(uploadFiles.get(0).getTypeId());
	                        }else{
	                            pqInfo2.setReport("0");
	                        }
	                    }
                    }
	            }
	            model.addAttribute("info",new PageInfo<PqInfo>(pqInfos));
	            model.addAttribute("pqInfo", pqInfo);
	        }
	    }
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
	public String getAllSupplierPqInfo(@CurrentUser User user, Model model,Integer page, SupplierPqrecord supplierPqrecord, HttpServletRequest request){
	    if(user != null && user.getOrg() != null){
	        Orgnization org = orgnizationService.getOrgByPrimaryKey(user.getOrg().getId());
	        if(org != null && "1".equals(org.getTypeName())){
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
	        }
	    }
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
	        double fen = y / z;  
	        // NumberFormat nf = NumberFormat.getPercentInstance(); 注释掉的也是一种方法  
	        // nf.setMinimumFractionDigits( 2 ); 保留到小数点后几位  
	        DecimalFormat df1 = new DecimalFormat("##.00%"); // ##.00%  
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
			super.writeJson(response, list);
		}
}
