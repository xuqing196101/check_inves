package bss.controller.prms;

import java.math.BigDecimal;
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

import com.alibaba.fastjson.JSON;

import bss.model.ppms.AduitQuota;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.SaleTender;
import bss.model.ppms.ScoreModel;
import bss.model.ppms.SupplyMark;
import bss.model.prms.ExpertScore;
import bss.model.prms.FirstAudit;
import bss.model.prms.PackageFirstAudit;
import bss.model.prms.ReviewFirstAudit;
import bss.model.prms.ext.AuditModelExt;
import bss.model.prms.ext.Extension;
import bss.service.ppms.AduitQuotaService;
import bss.service.ppms.MarkTermService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SaleTenderService;
import bss.service.ppms.ScoreModelService;
import bss.service.prms.ExpertScoreService;
import bss.service.prms.FirstAuditService;
import bss.service.prms.PackageFirstAuditService;
import bss.service.prms.ReviewFirstAuditService;
import bss.util.ScoreModelUtil;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.sms.Supplier;
import ses.service.ems.ExpertService;
import ses.service.sms.SupplierService;

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
	@Autowired
	private AduitQuotaService aduitQuotaService;//评分查询
	@Autowired
	private ScoreModelService scoreModelService;//模型关联查询
	@Autowired
	private MarkTermService markTermService;//评分项查询
	@Autowired
	private ExpertService expertService;//评分项查询
	@Autowired
	private SupplierService supplierService;//供应商查询
	@Autowired
	private ExpertScoreService expertScoreService;//供应商查询

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
			    Expert expert = expertService.selectByPrimaryKey(user.getTypeId());
			    FirstAudit firstAudit = new FirstAudit();
			    firstAudit.setId(packageFirst.getFirstAuditId());
			    if ("1".equals(expert.getExpertsTypeId())) {
			        firstAudit.setKind("技术");
                }
			    if ("2".equals(expert.getExpertsTypeId())) {
                    firstAudit.setKind("法律");
                }
			    if ("3".equals(expert.getExpertsTypeId())) {
                    firstAudit.setKind("商务");
                }
				List<FirstAudit> firstAudits = firstAuditService.findBykind(firstAudit);
				if (firstAudits != null && firstAudits.size() > 0) {
				    firstAuditList.add(firstAudits.get(0));
                }
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
	  * @Title: toGrade
	  * @author ShaoYangYang
	  * @date 2016年10月20日 下午7:03:22  
	  * @Description: TODO 去往评分页面
	  * @param @param projectId
	  * @param @param packageId
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("toGrade")
	public String toGrade(String projectId,String packageId,Model model,HttpSession session){
		//当前登录用户
		User user = (User)session.getAttribute("loginUser");
		String expertId = user.getTypeId();
		Expert expert = expertService.selectByPrimaryKey(expertId);
		//查询项目信息
		Project project = projectService.selectById(projectId);
		HashMap<String, Object> map2 = new HashMap<>();
		map2.put("id", packageId);
		//查询包信息
		List<Packages> packages = packageService.findPackageById(map2 );
		if(packages!=null && packages.size()>0){
			model.addAttribute("pack", packages.get(0));
		}
		Map<String, Object> map = new HashMap<>();
		map.put("projectId", projectId);
		map.put("packageId", packageId);
		if(expert.getExpertsTypeId().equals("1"))
		map.put("typeName",expert.getExpertsTypeId());
		if(expert.getExpertsTypeId().equals("3"))
			map.put("typeName",0);
		
		//查询评分信息
		List<AuditModelExt> findAllByMap = aduitQuotaService.findAllByMap(map);
		removeAuditModelExt(findAllByMap);
		model.addAttribute("list", findAllByMap);
		//查询供应商信息
		List<Supplier> supplierList = new ArrayList<>();
		Map<String,Object> supplierMap = new HashMap<>();
		supplierMap.put("projectId", projectId);
		supplierMap.put("packageId", packageId);
		if(findAllByMap!=null && findAllByMap.size()>0){
			for (AuditModelExt auditModelExt : findAllByMap) {
				supplierMap.put("supplierId", auditModelExt.getSupplierId());
				List<ReviewFirstAudit> list = service.selectList(supplierMap);
				if(list!=null && list.size()>0){
					//如果有一项不合格 那么就不参加评分
					int flag = 0;
					for (ReviewFirstAudit reviewFirstAudit : list) {
						if(reviewFirstAudit.getIsPass()==1){
							//证明有不合格的数据
							flag=1;
							break;
						}
					}
					if(flag==0){
						Supplier supplier = supplierService.selectById(auditModelExt.getSupplierId());
						supplierList.add(supplier);
					}
				}
			}
		}	
		//去重复
		removeSupplier(supplierList);
		model.addAttribute("supplierList", supplierList);
		model.addAttribute("project", project);
		model.addAttribute("projectId", projectId);
		model.addAttribute("packageId", packageId);
		return "bss/prms/audit/review_first_grade";
	}
	/**
	 * 
	  * @Title: removeDuplicate
	  * @author ShaoYangYang
	  * @date 2016年11月16日 下午3:31:52  
	  * @Description: TODO 去重复
	  * @param @param list      
	  * @return void
	 */
	private static void removeSupplier(List<Supplier> list)   { 
	   
	   for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )   { 
	    for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )   { 
	      if  (list.get(j).getId(). equals(list.get(i).getId()))   { 
	        list.remove(j); 
	      } 
	    } 
	  } 
	} 
	private static void removeAuditModelExt(List<AuditModelExt> list)   { 
		for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )   { 
			for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )   { 
				if  (list.get(j).getScoreModelId(). equals(list.get(i).getScoreModelId()))   { 
					list.remove(j); 
				} 
			} 
		} 
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
	/**
	 * 
	  * @Title: caseGrade
	  * @author ShaoYangYang
	  * @date 2016年11月17日 下午3:02:55  
	  * @Description: TODO 3456模型算法
	  * @param @param scoreModelId
	  * @param @param supplierIds
	  * @param @param expertValues
	  * @param @return      
	  * @return List<SupplyMark>
	 */
	@RequestMapping("caseGrade")
	@ResponseBody
	public String caseGrade(String markTermId,String supplierIds,String expertValues,String scoreModelId,String typeName,String quotaId,String projectId,String packageId,HttpSession session){
		//当前登录用户
		User user = (User)session.getAttribute("loginUser");
		String expertId = user.getTypeId();
		ArrayList<SupplyMark> smList = new ArrayList<>();
		String[] ids = supplierIds.split(",");
		String[] eids = expertValues.split(",");
		SupplyMark sm ;
		for (int i = 0; i < ids.length; i++) {
			sm = new SupplyMark();
			sm.setSupplierId(ids[i]);
			sm.setPrarm(Double.valueOf(eids[i]));
			sm.setMarkTermId(markTermId);
			smList.add(sm);
		}
		
		ScoreModel scoreModel = new ScoreModel();
		scoreModel.setId(scoreModelId);
		ScoreModel scoreModel2 = scoreModelService.findScoreModelByScoreModel(scoreModel );
		List<SupplyMark> list = null ;
		if(typeName=="3" || typeName.equals("3")) {
			list = ScoreModelUtil.getScoreByModelThree(scoreModel2, smList);
		}
		if(typeName=="4" || typeName.equals("4")) {
			 list = ScoreModelUtil.getScoreByModelFour(scoreModel2, smList);
		}
		if(typeName=="5" || typeName.equals("5")) {
			 list = ScoreModelUtil.getScoreByModelFive(scoreModel2, smList, null);
		}
		if(typeName=="6" || typeName.equals("6")) {
			 list = ScoreModelUtil.getScoreByModelSix(scoreModel2, smList,null);
		}
		
		
		ExpertScore expertScore = new ExpertScore();
		expertScore.setExpertId(expertId);
		expertScore.setProjectId(projectId);
		expertScore.setPackageId(packageId);
		expertScore.setScoreModelId(scoreModelId);
		expertScoreService.saveScore(expertScore, list,scoreModelId);
		
		
			
		return JSON.toJSONString(list);
	}
	/**
	 * 
	  * @Title: caseGrade
	  * @author ShaoYangYang
	  * @date 2016年11月17日 下午3:02:55  
	  * @Description: TODO 1278模型算法
	  * @param @param scoreModelId
	  * @param @param supplierIds
	  * @param @param expertValues
	  * @param @return      
	  * @return List<SupplyMark>
	 */
	@RequestMapping("caseGradeTwo")
	@ResponseBody
	public String caseGradeTwo(HttpSession session,String supplierIds,Integer expertValues,String scoreModelId,String typeName,String quotaId,String projectId,String packageId){
		//当前登录用户
		User user = (User)session.getAttribute("loginUser");
		String expertId = user.getTypeId();
		//算分
		ScoreModel scoreModel = new ScoreModel();
		scoreModel.setId(scoreModelId);
		ScoreModel scoreModel2 = scoreModelService.findScoreModelByScoreModel(scoreModel );
		double score = ScoreModelUtil.getQuantizateScore(scoreModel2, expertValues, expertValues);
		//保存结果 修改数据
		ExpertScore expertScore = new ExpertScore();
		expertScore.setExpertId(expertId);
		expertScore.setProjectId(projectId);
		expertScore.setPackageId(packageId);
		expertScore.setScoreModelId(scoreModelId);
		expertScore.setSupplierId(supplierIds);
		BigDecimal expertValue = new BigDecimal(expertValues);
		BigDecimal score2= new BigDecimal(score);
		expertScore.setScore(score2);
		expertScore.setExpertValue(expertValue);
		expertScoreService.saveScore(expertScore, null,scoreModelId);
		return JSON.toJSONString(score);
	}
}
