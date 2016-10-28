package bss.controller.prms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.SaleTender;
import bss.model.prms.FirstAudit;
import bss.model.prms.PackageFirstAudit;
import bss.model.prms.ReviewFirstAudit;
import bss.model.prms.ext.Extension;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SaleTenderService;
import bss.service.prms.FirstAuditService;
import bss.service.prms.PackageFirstAuditService;
import bss.service.prms.ReviewFirstAuditService;
import ses.model.bms.User;
import ses.model.sms.Supplier;

@Controller
@RequestMapping("reviewFirstAudit")
public class ReviewFirstAuditController {

	@Autowired
	private ReviewFirstAuditService service;
	@Autowired
	private PackageService packageService;//包
	@Autowired
	private ProjectService projectService;//项目
	@Autowired
	private PackageFirstAuditService packageFirstAuditService;//包关联初审项
	@Autowired
	private FirstAuditService firstAuditService;//初审项
	@Autowired
	private SaleTenderService saleTenderService;//供应商查询

	/**
	 * 
	  * @Title: toAudit
	  * @author ShaoYangYang
	  * @date 2016年10月20日 下午7:03:22  
	  * @Description: TODO 去往评审页面
	  * @param @param projectId
	  * @param @param packageId
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("toAudit")
	public String toAudit(String projectId,String packageId,Model model,HttpSession session){
		//当前登录用户
		User user = (User)session.getAttribute("loginUser");
		//创建封装的实体
		Extension extension = new Extension();
		HashMap<String ,Object> map = new HashMap<>();
		map.put("projectId", projectId);
		map.put("id", packageId);
		//查询包信息
		List<Packages> list = packageService.findPackageById(map);
		if(list!=null && list.size()>0){
			Packages packages = list.get(0);
			//放入包信息
			extension.setPackageId(packages.getId());
			extension.setPackageName(packages.getName());
		}
		//查询项目信息
		Project project = projectService.selectById(projectId);
		if(project!=null){
			//放入项目信息
			extension.setProjectId(project.getId());
			extension.setProjectName(project.getName());
			extension.setProjectCode(project.getProjectNumber());
		}
		
		//查询改包下的初审项信息
		Map<String,Object> map2 = new HashMap<>();
		map2.put("projectId", projectId);
		map2.put("packageId", packageId);
		//查询出该包下的初审项id集合
		List<PackageFirstAudit> packageAuditList = packageFirstAuditService.selectList(map2);
		//创建初审项的集合
		List<FirstAudit> firstAuditList = new ArrayList<>();
		if(packageAuditList!=null && packageAuditList.size()>0){
			for (PackageFirstAudit packageFirst : packageAuditList) {
				//根据初审项的id 查询出初审项的信息放入集合
				FirstAudit firstAudit = firstAuditService.get(packageFirst.getFirstAuditId());
				firstAuditList.add(firstAudit);
			}
		}
	    //放入初审项集合
		extension.setFirstAuditList(firstAuditList);
		//查询供应商信息
		List<SaleTender> supplierList = saleTenderService.list(new SaleTender(projectId), 0);
		extension.setSupplierList(supplierList);
		
		//查询审核过的信息用于回显
		Map<String, Object> reviewFirstAuditMap = new HashMap<>();
		reviewFirstAuditMap.put("projectId", projectId);
		reviewFirstAuditMap.put("packageId", packageId);
		reviewFirstAuditMap.put("expertId", user.getTypeId());
		List<ReviewFirstAudit> reviewFirstAuditList = service.selectList(reviewFirstAuditMap);
		//回显信息放进去
		model.addAttribute("reviewFirstAuditList", reviewFirstAuditList);
		//把封装的实体放入域中
		model.addAttribute("extension", extension);
		return "bss/prms/audit/review_first_audit";
	}
	/**
	 * 
	  * @Title: add
	  * @author ShaoYangYang
	  * @date 2016年10月20日 下午7:03:09  
	  * @Description: TODO 新增
	  * @param @param reviewFirstAudit
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("add")
	@ResponseBody
	public void add(ReviewFirstAudit reviewFirstAudit,Model model,HttpSession session){
		//当前登录用户
		User user = (User)session.getAttribute("loginUser");
		Map<String, Object> map = new HashMap<>();
		map.put("projectId", reviewFirstAudit.getProjectId());
		map.put("packageId", reviewFirstAudit.getPackageId());
		map.put("firstAuditId", reviewFirstAudit.getFirstAuditId());
		map.put("supplierId", reviewFirstAudit.getSupplierId());
		map.put("expertId", user.getTypeId());
		service.delete(map);
		reviewFirstAudit.setExpertId(user.getTypeId());
		service.save(reviewFirstAudit);
	}
	/**
	 * 
	  * @Title: addAll
	  * @author ShaoYangYang
	  * @date 2016年10月21日 下午2:43:58  
	  * @Description: TODO 全部合格或不合格
	  * @param @param projectId 项目id
	  * @param @param packageId 包id
	  * @param @param supplierId 供应商id
	  * @return void
	 */
	@RequestMapping("addAll")
	@ResponseBody
	public void addAll(String projectId,String packageId,String supplierId,Short flag,String rejectReason,HttpSession session){
		//当前登录用户
		User user = (User)session.getAttribute("loginUser");
		//查询改包下的初审项信息
		Map<String,Object> map2 = new HashMap<>();
		map2.put("projectId", projectId);
		map2.put("packageId", packageId);
		//查询出该包下的初审项id集合
		List<PackageFirstAudit> firstAuditIdsList = packageFirstAuditService.selectList(map2);
		//创建保存对象
		ReviewFirstAudit reviewFirstAudit;
		    if(firstAuditIdsList!=null && firstAuditIdsList.size()>0){
		    	Map<String, Object> map = new HashMap<>();
		    	map.put("projectId", projectId);
		    	map.put("packageId", packageId);
		    	map.put("supplierId", supplierId);
		    	map.put("expertId", user.getTypeId());
				service.delete(map );
		      for (PackageFirstAudit packageFirstAudit : firstAuditIdsList) {
			    reviewFirstAudit = new ReviewFirstAudit();
			    reviewFirstAudit.setFirstAuditId(packageFirstAudit.getFirstAuditId());
			    reviewFirstAudit.setPackageId(packageId);
			    reviewFirstAudit.setProjectId(projectId);
			    reviewFirstAudit.setSupplierId(supplierId);
			    //reviewFirstAudit.setIsPass((short) 0);
			    reviewFirstAudit.setIsPass(flag);
			    reviewFirstAudit.setExpertId(user.getTypeId());
			    reviewFirstAudit.setRejectReason(rejectReason);
			    service.save(reviewFirstAudit);
		      }
		    }
	}
	/**
	 * 
	  * @Title: getReason
	  * @author ShaoYangYang
	  * @date 2016年10月21日 下午4:50:24  
	  * @Description: TODO 查询审核理由
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("getReason")
	@ResponseBody
	public ReviewFirstAudit getReason(String projectId,String packageId,String supplierId,String firstAuditId){
		Map<String, Object> map = new HashMap<>();
		map.put("projectId", projectId);
    	map.put("packageId", packageId);
    	map.put("supplierId", supplierId);
    	map.put("firstAuditId", firstAuditId);
		List<ReviewFirstAudit> list = service.selectList(map );
		if(list!=null && list.size()>0){
			ReviewFirstAudit reviewFirstAudit = list.get(0);
			return reviewFirstAudit;
		}else{
			return null;
		}
	}
}
