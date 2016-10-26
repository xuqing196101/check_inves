package bss.controller.prms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.SaleTender;
import bss.model.prms.PackageExpert;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SaleTenderService;
import bss.service.prms.PackageExpertService;
import ses.model.ems.ProjectExtract;
import ses.service.ems.ProjectExtractService;

@Controller
@RequestMapping("packageExpert")
public class PackageExpertController {

	@Autowired
	private PackageExpertService service;
	@Autowired
	private ProjectDetailService detailService;
	@Autowired
	private PackageService packageService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProjectExtractService projectExtractService;
	@Autowired
	private SaleTenderService saleTenderService;//供应商查询
	/**
	 * 
	  * @Title: toPackageExpert
	  * @author ShaoYangYang
	  * @date 2016年10月18日 下午3:05:52  
	  * @Description: TODO 跳转到关联专家页面
	  * @param @param projectId
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("toPackageExpert")
	public String toPackageExpert(String projectId,String flag,Model model){
		//项目分包信息
		HashMap<String,Object> pack = new HashMap<String,Object>();
		pack.put("projectId", projectId);
		List<Packages> packList = packageService.findPackageById(pack);
		if(packList.size()==0){
			Packages pg = new Packages();
			pg.setName("第一包");
			pg.setProjectId(projectId);
			packageService.insertSelective(pg);
		}
		List<Packages> packages = packageService.findPackageById(pack);
		Map<String,Object> list = new HashMap<String,Object>();
		//关联表集合
		List<PackageExpert> expertIdList = new ArrayList<>();
		Map<String,Object> mapSearch = new HashMap<String,Object>(); 
		for(Packages ps:packages){
			list.put("pack"+ps.getId(),ps);
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("packageId", ps.getId());
			List<ProjectDetail> detailList = detailService.selectById(map);
			ps.setProjectDetails(detailList);
			//设置查询条件
			mapSearch.put("projectId", projectId);
			mapSearch.put("packageId", ps.getId());
			//查询出该项目下的包关联的信息集合
			List<PackageExpert> selectList = service.selectList(mapSearch);
			expertIdList.addAll(selectList);
		}
		//供应商信息
		  List<SaleTender> supplierList = saleTenderService.list(new SaleTender(projectId), 0);
		  model.addAttribute("supplierList", supplierList);
		//查询条件
		ProjectExtract projectExtract = new ProjectExtract();
		projectExtract.setProjectId(projectId);
		projectExtract.setReason("1");
		//项目抽取的专家信息
		List<ProjectExtract> expertList = projectExtractService.list(projectExtract );
		model.addAttribute("expertList", expertList);
		//包信息
		model.addAttribute("packageList", packages);
		Project project = projectService.selectById(projectId);
		//项目实体
		model.addAttribute("project", project);
		//关联信息集合
		model.addAttribute("expertIdList", expertIdList);
		//成功表示
		model.addAttribute("flag", flag);
		return "bss/prms/package_expert";
	}
	/**
	 * 
	  * @Title: relate
	  * @author ShaoYangYang
	  * @date 2016年10月18日 下午2:30:52  
	  * @Description: TODO 
	  * @param @param chkItem 专家id ，拼接的
	  * @param @param packageExpert 关联实体
	  * @param @param groupId 组长专家id
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("relate")
	public String relate(String chkItem,PackageExpert packageExpert,String groupId,RedirectAttributes attr){
		service.deleteByPackageId(packageExpert.getPackageId());
		//设置变量判断是否操作错误
		int count = 0 ;
		if(StringUtils.isNotEmpty(chkItem) && StringUtils.isNotEmpty(groupId)){
		String[] expertIds = chkItem.split(",");
		Set<String> set = new HashSet<>();
		//去除重复 的专家id
		for (int i = 0; i < expertIds.length; i++) {
			set.add(expertIds[i]);
		}
		//迭代set集合
		for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
			String expertId = (String) iterator.next();
			//设置专家id
			packageExpert.setExpertId(expertId);
			//判断组长id是否和选择的专家id一致，如果一致就设定为组长
			if(groupId.equals(expertId)){
				packageExpert.setIsGroupLeader((short) 1);
				count++;
			}else{
				packageExpert.setIsGroupLeader((short) 0);
			}
			service.save(packageExpert);
		}
		    //判断如果count等于0 说明选择的组长不在选择的专家中不对 ，为错误操作
		    if(count==0){
			  attr.addAttribute("flag", "error");
			  service.deleteByPackageId(packageExpert.getPackageId());
		    }else{
		      attr.addAttribute("flag", "success");
		    }
		    
		}else {
			attr.addAttribute("flag", "error");
		}
		attr.addAttribute("projectId", packageExpert.getProjectId());
		return "redirect:toPackageExpert.html";
	}
}
