package bss.controller.prms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.prms.FirstAudit;
import bss.model.prms.PackageFirstAudit;
import bss.model.prms.ReviewFirstAudit;
import bss.model.prms.ext.Extension;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectService;
import bss.service.prms.FirstAuditService;
import bss.service.prms.PackageFirstAuditService;
import bss.service.prms.ReviewFirstAuditService;
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
	public String toAudit(String projectId,String packageId,Model model){
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
		List<Supplier> supplierList = new ArrayList<>();
		Supplier s = new Supplier();
		s.setId("111");
		s.setSupplierName("第一个");
		Supplier s2 = new Supplier();
		s2.setId("111");
		s2.setSupplierName("第二个");
		supplierList.add(s);
		supplierList.add(s2);
		extension.setSupplierList(supplierList);
		/**
		 * 还差供应商集合
		 * */
		//把封装的实体放入域中
		model.addAttribute("extension", extension);
		return "bss/prms/review_first_audit";
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
	public String add(ReviewFirstAudit reviewFirstAudit,Model model){
		
		return "";
	}
}
