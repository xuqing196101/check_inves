package bss.controller.prms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.SaleTender;
import bss.model.prms.PackageExpert;
import bss.model.prms.ReviewFirstAudit;
import bss.model.prms.ReviewProgress;
import bss.model.prms.ext.PackExpertExt;
import bss.model.prms.ext.SupplierExt;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SaleTenderService;
import bss.service.prms.PackageExpertService;
import bss.service.prms.ReviewFirstAuditService;
import bss.service.prms.ReviewProgressService;
import ses.model.ems.Expert;
import ses.model.ems.ProjectExtract;
import ses.service.ems.ExpertService;
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
	@Autowired
	private ReviewProgressService reviewProgressService;//进度
	@Autowired
	private ExpertService expertService;//专家
	@Autowired
	private ReviewFirstAuditService reviewFirstAuditService;//初审表
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
		//进度集合
		List<ReviewProgress> reviewProgressList = new ArrayList<>();
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
			//查询进度
			List<ReviewProgress> selectByMap = reviewProgressService.selectByMap(map);
			expertIdList.addAll(selectList);
			reviewProgressList.addAll(selectByMap);
		}
		model.addAttribute("expertIdList", expertIdList);
		//进度
		model.addAttribute("reviewProgressList", reviewProgressList);
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
		//封装实体
		List<PackExpertExt> packExpertExtList = new ArrayList<>();
		//供应商封装实体
		List<SupplierExt> supplierExtList = new ArrayList<>();
		PackExpertExt packExpertExt;
		for(PackageExpert packageExpert:expertIdList){
			packExpertExt = new PackExpertExt();
			Expert expert = expertService.selectByPrimaryKey(packageExpert.getExpertId());
			packExpertExt.setExpert(expert);
			packExpertExt.setPackageId(packageExpert.getPackageId());
			packExpertExt.setProjectId(packageExpert.getProjectId());
			Map<String, Object> map = new HashMap<>();
			//根据专家id和包id查询改包的这个专家是否评审完成
			map.put("expertId", packageExpert.getExpertId());
			map.put("packageId", packageExpert.getPackageId());
			//根据专家id查询关联表 确定该专家是否都已评审
			List<PackageExpert> selectList = service.selectList(map );
				int count = 0;
				for (PackageExpert packageExpert2 : selectList) {
					if(packageExpert2.getIsAudit()==1){
						count++;
					}
				}
				if(count>0){
					packExpertExt.setIsPass("已评审");
				}else{
					packExpertExt.setIsPass("未评审");
				}
			//根据供应商id 和包id查询审核表  确定该供应商是否通过评审
				for(SaleTender saleTender : supplierList){
					SupplierExt supplierExt = new SupplierExt();
					Map<String,Object> map2 = new HashMap<>();
					map2.put("supplierId", saleTender.getSuppliers().getId());
					map2.put("packageId", packageExpert.getPackageId());
					map2.put("expertId", packageExpert.getExpertId());
					List<ReviewFirstAudit> selectList2 = reviewFirstAuditService.selectList(map2);
					if(selectList2!=null && selectList2.size()>0){
						int count2 = 0;
						for (ReviewFirstAudit reviewFirstAudit : selectList2) {
							if(reviewFirstAudit.getIsPass()==1){
								count2++;
								break;
							}
						}
						//如果变量大于0 说明有不合格的数据
						if(count2>0){
							supplierExt.setSupplierId(saleTender.getSuppliers().getId());
							supplierExt.setExpertId(packageExpert.getExpertId());
							supplierExt.setPackageId(packageExpert.getPackageId());
							supplierExt.setSuppIsPass("不合格");
						}else{
							supplierExt.setSupplierId(saleTender.getSuppliers().getId());
							supplierExt.setExpertId(packageExpert.getExpertId());
							supplierExt.setPackageId(packageExpert.getPackageId());
							supplierExt.setSuppIsPass("合格");
						}
					}else{
						supplierExt.setSupplierId(saleTender.getSuppliers().getId());
						supplierExt.setPackageId(packageExpert.getPackageId());
						supplierExt.setExpertId(packageExpert.getExpertId());
						supplierExt.setSuppIsPass("未评审");
					}
				
					supplierExtList.add(supplierExt);
				}
				packExpertExtList.add(packExpertExt);
		}
		model.addAttribute("packExpertExtList", packExpertExtList);
		model.addAttribute("supplierExtList", supplierExtList);
		//成功标示
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
			//评审状态 未评审
			packageExpert.setIsAudit((short) 0);
			//是否汇总 未汇总
			packageExpert.setIsGather((short) 0);
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
	/**
	 * 
	  * @Title: getPace
	  * @author ShaoYangYang
	  * @date 2016年10月27日 下午8:13:46  
	  * @Description: TODO 符合汇总
	  * @param @param projectId 
	  * @param @param packageId
	  * @param @param expertId      
	  * @return void
	 */
	@RequestMapping("gather")
	@ResponseBody
	public void getPace(PackageExpert record){
		record.setIsGather((short) 1);
		service.updateByBean(record );
	}
	/**
	 * 
	  * @Title: isBack
	  * @author ShaoYangYang
	  * @date 2016年10月27日 下午8:13:46  
	  * @Description: TODO 退回
	  * @param @param projectId 
	  * @param @param packageId
	  * @param @param expertId      
	  * @return void
	 */
	@RequestMapping("isBack")
	@ResponseBody
	public void isBack(PackageExpert record,HttpServletResponse response){
		//查询是否已评审
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("expertId", record.getExpertId());
		map.put("packageId", record.getPackageId());
		map.put("projectId", record.getProjectId());
		List<PackageExpert> selectList = service.selectList(map);
		if(selectList!= null && selectList.size()>0){
			
			PackageExpert packageExpert = selectList.get(0);
			if(packageExpert.getIsAudit()!=1){
				try {
					response.getWriter().print("error");
					return ;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		Short flag = 0;
		record.setIsGather(flag);
		record.setIsAudit(flag);
		service.updateByBean(record );
	}
}
