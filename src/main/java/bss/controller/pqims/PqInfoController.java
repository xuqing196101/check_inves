
package bss.controller.pqims;


import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.service.oms.OrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;


import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.Constant;
import common.constant.StaticVariables;
import common.model.UploadFile;
import common.service.UploadService;

import bss.model.pqims.PqInfo;
import bss.model.pqims.SupplierPqrecord;
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
	
	@Autowired
    private OrgnizationServiceI orgnizationService;
	
	@Autowired
	private UploadService uploadService;
	
	@Autowired
	private SupplierPqrecordService pqrecordService;
	
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
                map.put("purchaseDepId", user.getOrg().getId());
                List<PqInfo> list = pqInfoService.getAll(page==null?1:page,map);
                if(list != null && list.size() > 0){
                    for (PqInfo pqInfo2 : list) {
                        List<UploadFile> uploadFiles = uploadService.getFilesOther(pqInfo2.getId(), DictionaryDataUtil.getId("CONTRACT_APPROVE_ATTACH"), "2");
                        if(uploadFiles != null && uploadFiles.size() > 0){
                            pqInfo2.setReport(uploadFiles.get(0).getTypeId());
                        }else{
                            pqInfo2.setReport("0");
                        }
                    }
                }
                model.addAttribute("info",new PageInfo<PqInfo>(list));
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
	public String save(@CurrentUser User user, @Valid PqInfo pqInfo,BindingResult result,Model model){
		if(result.hasErrors()){
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fieldError:errors){
				model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
			model.addAttribute("pqinfo", pqInfo);
			return "bss/pqims/pqinfo/add";
		}
		if(user != null && user.getOrg() != null){
		    pqInfo.setPurchaseDepId(user.getOrg().getId());
	        pqInfoService.add(pqInfo);
		}
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
	            pqInfoService.delete(pqInfo.getId());
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
	public String view(Model model,String id, String type, String status){
	    if(StringUtils.isNotBlank(id)){
	        PqInfo pqInfo = pqInfoService.get(id);
	        pqInfo.getContract().setPurchaseType(DictionaryDataUtil.findById(pqInfo.getContract().getPurchaseType()).getName());
	        model.addAttribute("pqinfo",pqInfo);
	    }
		model.addAttribute("type",type);
		model.addAttribute("status",status);
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
	            map.put("purchaseDepId", user.getOrg().getId());
	            List<PqInfo> pqInfos = pqInfoService.getAll(page==null?1:page,map);
	            if(pqInfos != null && pqInfos.size() > 0){
	                for (PqInfo pqInfo2 : pqInfos) {
	                    if(StringUtils.isNotBlank(pqInfo2.getContract().getPurchaseDepName()) ){
	                        Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(pqInfo2.getContract().getPurchaseDepName());
	                        if(orgnization != null ){
	                            pqInfo2.getContract().setPurchaseDepName(orgnization.getName());
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
	 *〈简述〉
	 *〈详细描述〉
	 * @author FengTian
	 * @param user
	 * @param model
	 * @param page
	 * @param supplierName
	 * @return
	 */
	@RequestMapping("/getAllSupplierPqInfo")
	public String getAllSupplierPqInfo(@CurrentUser User user, Model model,Integer page, String supplierName){
	    if(user != null && user.getOrg() != null && "1".equals(user.getTypeName()) &&  "1".equals(user.getOrg().getTypeName())){
			HashMap<String, Object> map = new HashMap<>();
			map.put("purchaseDepId", user.getOrg().getId());
			if(StringUtils.isNotBlank(supplierName)){
		    map.put("supplierName", supplierName);
	    }
	        List<SupplierPqrecord> list = pqrecordService.getByAll(page==null?1:page, map);
	        model.addAttribute("info",new PageInfo<SupplierPqrecord>(list));
	        model.addAttribute("supplierName", supplierName);
	    }
		return "bss/pqims/pqinfo/supplier_pqinfo_list";
	}
	  
}
