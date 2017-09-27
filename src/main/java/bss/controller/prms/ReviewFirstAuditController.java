
package bss.controller.prms;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.sms.Quote;
import ses.model.sms.Supplier;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.ems.ExpertService;
import ses.service.sms.SupplierExtRelateService;
import ses.service.sms.SupplierPorjectQuaService;
import ses.service.sms.SupplierQuoteService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.ScoreModelUtil;
import ses.util.WfUtil;
import bss.controller.base.BaseController;
import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.MarkTerm;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.SaleTender;
import bss.model.ppms.ScoreModel;
import bss.model.ppms.SupplierCheckPass;
import bss.model.ppms.SupplyMark;
import bss.model.prms.ExpertScore;
import bss.model.prms.FirstAudit;
import bss.model.prms.PackageExpert;
import bss.model.prms.PackageFirstAudit;
import bss.model.prms.ReviewFirstAudit;
import bss.model.prms.SupplierRank;
import bss.model.prms.ext.Extension;
import bss.service.ppms.AduitQuotaService;
import bss.service.ppms.AdvancedPackageService;
import bss.service.ppms.AdvancedProjectService;
import bss.service.ppms.MarkTermService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SaleTenderService;
import bss.service.ppms.ScoreModelService;
import bss.service.ppms.SupplierCheckPassService;
import bss.service.prms.ExpertScoreService;
import bss.service.prms.FirstAuditService;
import bss.service.prms.PackageExpertService;
import bss.service.prms.PackageFirstAuditService;
import bss.service.prms.ReviewFirstAuditService;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("reviewFirstAudit")
public class ReviewFirstAuditController extends BaseSupplierController {

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
	private ExpertScoreService expertScoreService;//专家成绩查询
	@Autowired
    private DictionaryDataServiceI dictionaryDataServiceI;//数据字典查询
    @Autowired
    private SupplierExtRelateService supplierExtRelateService;//供应商分包查询
    @Autowired
    private PackageExpertService packageExpertService;//专家分包查询
    @Autowired
    private AdvancedPackageService advancedPackageService;
    @Autowired
    private AdvancedProjectService advancedProjectService;
    @Autowired
    private SupplierQuoteService supplierQuoteService;
    @Autowired
    private SupplierCheckPassService checkPassService;
    /**
     *〈简述〉
     * 项目评审list页面中的查看详情
     *〈详细描述〉
     * @author WangHuijie
     * @param packageId
     * @param model
     * @return
     */
    @RequestMapping("showPackView")
    public String showPackView (String packageId, Model model) {
        Packages pack = new Packages();
        pack.setId(packageId);
        List<Packages> list = packageService.find(pack);
        Packages packages = null;
        if (list != null && !list.isEmpty()) {
            packages = list.get(0);
        }
        model.addAttribute("packages", packages);
        // 查询包内供应商list
        SaleTender saleTender = new SaleTender();
        saleTender.setPackages(packageId);
        List<SaleTender> supplierList = saleTenderService.find(saleTender);
        model.addAttribute("supplierList", supplierList);
        return "bss/prms/audit/packages_view";
    }
    
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
			
			if(packages != null && packages.getQualificationTime() == null){
	            packages.setQualificationTime(new Date());
	            packageService.updateByPrimaryKeySelective(packages);
	        }
		} else {
		    List<AdvancedPackages> selectByAll = advancedPackageService.selectByAll(map);
		    if(selectByAll != null && !selectByAll.isEmpty()){
		        AdvancedPackages packages = selectByAll.get(0);
		      //放入包信息
	            extension.setPackageId(packages.getId());
	            extension.setPackageName(packages.getName());
	            
	            if(packages != null && packages.getQualificationTime() == null){
	                packages.setQualificationTime(new Date());
	                advancedPackageService.update(packages);
	            }
		    }
		}
		//查询项目信息
		Project project = projectService.selectById(projectId);
		if(project!=null){
			//放入项目信息
			extension.setProjectId(project.getId());
			extension.setProjectName(project.getName());
			extension.setProjectCode(project.getProjectNumber());
		} else {
		    AdvancedProject project2 = advancedProjectService.selectById(projectId);
		    if(project2 != null){
		        //放入项目信息
	            extension.setProjectId(project2.getId());
	            extension.setProjectName(project2.getName());
	            extension.setProjectCode(project2.getProjectNumber());
		    }
		}
		
		//查询改包下的初审项信息
		FirstAudit firstAudit = new FirstAudit();
		firstAudit.setPackageId(packageId);
		firstAudit.setProjectId(projectId);
		firstAudit.setIsConfirm((short)0);
		List<FirstAudit> firstAuditList = firstAuditService.findBykind(firstAudit);
	    //放入初审项集合
		extension.setFirstAuditList(firstAuditList);
		//查询供应商信息
		List<SaleTender> supplierList = new ArrayList<SaleTender>();
		List<SaleTender> sl = saleTenderService.find(new SaleTender(projectId));
		for (SaleTender st : sl) {
       if (st.getPackages().indexOf(packageId) != -1 && st.getIsTurnUp() == 0) {
           supplierList.add(st);
       }
		}
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
		List<DictionaryData> dds = DictionaryDataUtil.find(22);
		model.addAttribute("dds", dds);
		return "bss/prms/audit/review_first_audit";
	}
	
	/**
	 *〈简述〉去往专家经济技术评审页面
	 *〈详细描述〉
	 * @author WangHuijie 
	 * @param projectId 项目Id
	 * @param packageId 包Id
	 * @param model 
	 * @param session
	 * @return
	 */
	@RequestMapping("toGrade")
	public String toGrade(String projectId,String packageId,Model model,HttpSession session){
		//当前登录用户
		User user = (User)session.getAttribute("loginUser");
 		String expertId = user.getTypeId();
		Expert expert = expertService.selectByPrimaryKey(expertId);
		model.addAttribute("expert", expert);
		model.addAttribute("expertId", expertId);
		//查询项目信息
		Project project = projectService.selectById(projectId);
		if(project != null){
		    model.addAttribute("project", project);
		} else {
		    AdvancedProject project2 = advancedProjectService.selectById(projectId);
		    if(project2 != null){
		        model.addAttribute("project", project2);
		    }
		}
		HashMap<String, Object> map2 = new HashMap<>();
		map2.put("id", packageId);
		//查询包信息
		List<Packages> packages = packageService.findPackageById(map2);
		if(packages!=null && packages.size()>0){
	        if( packages.get(0).getTechniqueTime() == null){
	            packages.get(0).setTechniqueTime(new Date());
	            packageService.updateByPrimaryKeySelective(packages.get(0));
	        }
			model.addAttribute("pack", packages.get(0));
		} else {
		    AdvancedPackages packages2 = advancedPackageService.selectById(packageId);
		    if(packages2 != null){
		        if( packages2.getTechniqueTime() == null){
	                packages2.setTechniqueTime(new Date());
	                advancedPackageService.update(packages2);
	            }
	            model.addAttribute("pack", packages2);
		    }
		}
		//查询评分信息
		Map<String, Object> map = new HashMap<>();
		map.put("projectId", projectId);
		map.put("packageId", packageId);
		// 专家类别
		//String[] typeIds = expert.getExpertsTypeId().split(",");
		// 查询出所有的评审项类型
		//List<DictionaryData> AllMarkTermType = dictionaryDataServiceI.findByKind("23");
		//根据专家的类别判断显示哪些类型的评分项
        /*for (String id : typeIds) {
            int kind = dictionaryDataServiceI.getDictionaryData(id).getKind();
            if (kind == 6) {
                // kind值为6代表技术类型
                loop:for (DictionaryData data : AllMarkTermType) {
                    if ("TECHNOLOGY".equals(data.getCode())) {
                        markTermTypeList.add(data);
                        continue loop;
                    }
                }
            } else if (kind == 19) {
                // kind值为19表示为经济类型
                loop:for (DictionaryData data : AllMarkTermType) {
                    if ("ECONOMY".equals(data.getCode())) {
                        markTermTypeList.add(data);
                        continue loop;
                    }
                }
            }
        }
        removeDictionaryData(markTermTypeList);*/
		// 专家可以打分的类型
		List<DictionaryData> markTermTypeList = new ArrayList<DictionaryData>();
		map.put("expertId", expertId);
		String typeId = packageExpertService.selectList(map).get(0).getReviewTypeId();
		markTermTypeList.add(dictionaryDataServiceI.getDictionaryData(typeId));
		
		model.addAttribute("markTermTypeList", markTermTypeList);
		// 查询所有的ScoreModel
        ScoreModel scoreModel = new ScoreModel();
        scoreModel.setPackageId(packageId);
        scoreModel.setProjectId(projectId);
        List<ScoreModel> scoreModelList = scoreModelService.findListByScoreModelByTime(scoreModel);
        for (ScoreModel score : scoreModelList) {
            if (score.getStandardScore() == null || "".equals(score.getStandardScore())) {
                score.setStandardScore(score.getMaxScore());
            }
            if (score.getTypeName() != null && "8".equals(score.getTypeName()) && score.getJudgeContent() != null && !"".equals(score.getJudgeContent())) {
              List<String> list = Arrays.asList(score.getJudgeContent().split("\\|"));
              score.setModel1BJudgeContent(list);
            }
        }
        model.addAttribute("scoreModelList", scoreModelList);
		// 查出该包内所有的markTerm
		/*MarkTerm markTerm = new MarkTerm();
		markTerm.setProjectId(projectId);
		markTerm.setPackageId(packageId);
		List<MarkTerm> allMarkTerm = markTermService.findListByMarkTerm(markTerm);
		// 遍历去除pid is not null 的
		List<MarkTerm> markTermList = new ArrayList<MarkTerm>();
		for (MarkTerm mark : allMarkTerm) {
            if ("0".equals(mark.getPid()) && mark.getTypeName().equals(typeId)) {
                markTermList.add(mark);
            }
        }*/
		
		MarkTerm mt = new MarkTerm();
    mt.setTypeName(typeId);
    mt.setProjectId(projectId);
    mt.setPackageId(packageId);
    //默认顶级节点为0
    mt.setPid("0");
    List<MarkTerm> markTermList = markTermService.findListByMarkTerm(mt);
		// 查询父节点的子节点个数
		for (int i = 0; i < markTermList.size(); i++) {
		    int count = 0;
		    for (ScoreModel score : scoreModelList) {
		        if (markTermList.get(i).getId().equals(score.getMarkTerm().getPid())) {
		            count++;
		        }
		    }
		    // 设置指定父节点的rowspan
		    markTermList.get(i).setCount(count);
		}
		// 将count == 0 的移除
		List<MarkTerm> markTerms = new ArrayList<MarkTerm>();
		for (MarkTerm mark : markTermList) {
		    if (mark.getCount() != 0) {
		      MarkTerm mt1 = new MarkTerm();
          mt1.setPid(mark.getId());
          mt1.setProjectId(projectId);
          mt1.setPackageId(packageId);
          List<MarkTerm> mtValue = markTermService.findListByMarkTerm(mt1);
          for (MarkTerm markTerm : mtValue) {
              if ("1".equals(markTerm.isChecked())) {
                mark.setCheckedPrice(markTerm.isChecked());
              }
          }
		        markTerms.add(mark);
		    }
		}
		if (markTerms.size() > 0 && scoreModelList.size() > 0) {
		    for (int i = 0; i < markTerms.size(); i++) {
		        int count = 0;
		        for (int j = 0; j < scoreModelList.size(); j++) {
		            if (markTerms.get(i).getId().equals(scoreModelList.get(j).getMarkTerm().getPid())) {
		                if (count == 0) {
		                    scoreModelList.get(j).setCount(markTerms.get(i).getCount());
		                } else {
		                    scoreModelList.get(j).setCount(0); 
		                }
		                count++;
		            }
		        }
		    }
		}
		model.addAttribute("markTermList", markTerms);
		//查询供应商信息
		HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("packageId", packageId);
        hashMap.put("projectId", projectId);
        hashMap.put("isFirstPass", 1);
        hashMap.put("isRemoved", "0");
        hashMap.put("isTurnUp", 0);
        List<SaleTender> supplierList = saleTenderService.getAdPackegeSuppliers(hashMap);
		model.addAttribute("supplierList", supplierList);
		// 回显
		map.put("expertId", expertId);
		List<ExpertScore> scores = expertScoreService.selectInfoByMap(map);
		model.addAttribute("scores", scores);
		// 新增参数
        model.addAttribute("size", supplierList.size());
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
	private static void removeDictionaryData(List<DictionaryData> list)   { 
		for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )   { 
			for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )   { 
				if  (list.get(j).getId(). equals(list.get(i).getId()))   { 
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
	public void addAll(String auditType, String kind, String projectId, String packageId, String supplierId, Short flag, String rejectReason, HttpSession session){
  		//当前登录用户
  		User user = (User)session.getAttribute("loginUser");
  		//查询改包下的初审项信息
  		FirstAudit firstAudit = new FirstAudit();
  		firstAudit.setPackageId(packageId);
  		if ("1".equals(auditType)) {
  		  firstAudit.setIsConfirm((short)1);
  		  firstAudit.setKind(kind);
      } else {
        firstAudit.setIsConfirm((short)0);
      }
  		List<FirstAudit> firstAudits = firstAuditService.findBykind(firstAudit);
  		//创建保存对象
  		ReviewFirstAudit reviewFirstAudit;
      for (FirstAudit fa : firstAudits) {
	        Map<String, Object> map = new HashMap<>();
	        map.put("projectId", projectId);
	        map.put("packageId", packageId);
	        map.put("supplierId", supplierId);
	        map.put("expertId", user.getTypeId());
	        map.put("firstAuditId", fa.getId());
	        service.delete(map);
			    reviewFirstAudit = new ReviewFirstAudit();
			    reviewFirstAudit.setFirstAuditId(fa.getId());
			    reviewFirstAudit.setPackageId(packageId);
			    reviewFirstAudit.setProjectId(projectId);
			    reviewFirstAudit.setSupplierId(supplierId);
			    reviewFirstAudit.setIsPass(flag);
			    reviewFirstAudit.setExpertId(user.getTypeId());
			    reviewFirstAudit.setRejectReason(rejectReason);
			    service.save(reviewFirstAudit);
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
	public ReviewFirstAudit getReason(String projectId, String packageId, String supplierId, String firstAuditId, String expertId){
		Map<String, Object> map = new HashMap<>();
		map.put("projectId", projectId);
    	map.put("packageId", packageId);
    	map.put("supplierId", supplierId);
    	map.put("firstAuditId", firstAuditId);
    	map.put("expertId", expertId);
		List<ReviewFirstAudit> list = service.selectList(map);
		if(list!=null && list.size()>0){
			ReviewFirstAudit reviewFirstAudit = list.get(0);
			return reviewFirstAudit;
		}else{
			return null;
		}
	}
	@RequestMapping("save_score")
	public void saveScore(String packId,String projectId,HttpServletRequest request,HttpServletResponse response) throws ParseException{
	  List<DictionaryData> ddList = DictionaryDataUtil.find(23);
	  String scoreId="";
    for (DictionaryData dictionaryData : ddList) {
        MarkTerm mt = new MarkTerm();
        mt.setTypeName(dictionaryData.getId());
        mt.setProjectId(projectId);
        mt.setPackageId(packId);
        //默认顶级节点为0
        mt.setPid("0");
        List<MarkTerm> mtList = markTermService.findListByMarkTerm(mt);
        atrh:
        for (MarkTerm mtKey : mtList) {
            MarkTerm mt1 = new MarkTerm();
            mt1.setPid(mtKey.getId());
            mt1.setProjectId(projectId);
            mt1.setPackageId(packId);
            List<MarkTerm> mtValue = markTermService.findListByMarkTerm(mt1);
            for (MarkTerm markTerm : mtValue) {
                if ("1".equals(markTerm.isChecked())) {
                    scoreId=markTerm.getId();
                    break atrh;
                }
            }
        }
    }
    ScoreModel model=new ScoreModel();
    model.setPackageId(packId);
    model.setProjectId(projectId);
    model.setMarkTermId(scoreId);
	  ScoreModel smodel = scoreModelService.findScoreModelByScoreModel(model);
	  
	  Quote quote=new Quote(); 
	  quote.setPackageId(packId);
	  quote.setProjectId(projectId);
/*	  List<Date> selectQuoteCount = supplierQuoteService.selectQuoteCount(quote);
	  quote.setCreatedAt(selectQuoteCount.get(selectQuoteCount.size()-1));*/
	  List<Quote> quotes = supplierQuoteService.get(quote);
	  Double param = quotes.get(0).getTotal().doubleValue();
	  ArrayList<SupplyMark> smList = new ArrayList<>();
	  SupplyMark sm;
    for (Quote qt : quotes) {
        sm = new SupplyMark();
        sm.setSupplierId(qt.getSupplierId());
        sm.setPrarm(qt.getTotal().doubleValue());
        sm.setMarkTermId(scoreId);
        smList.add(sm);
        if (param > qt.getTotal().doubleValue()) {
            param = qt.getTotal().doubleValue();
        }
    }
    ScoreModel scoreModel = new ScoreModel();
    scoreModel.setId(smodel.getId());
    ScoreModel scoreModel2 = scoreModelService.findScoreModelByScoreModel(scoreModel );
    SupplyMark smCondition = new SupplyMark();
    smCondition.setPrarm(param);
    List<SupplyMark> list = ScoreModelUtil.getScoreByModelSix(scoreModel2, smList,smCondition);
    ExpertScore expertScore = new ExpertScore();
    expertScore.setProjectId(projectId);
    expertScore.setPackageId(packId);
    expertScore.setScoreModelId(smodel.getId());
    expertScoreService.saveScore(expertScore, list,smodel.getId());
    List<ExpertScore> es = expertScoreService.selectByScore(expertScore);
    StringBuffer buffer=new StringBuffer();
    buffer.append("{\"spriceScore\":[");
    for(ExpertScore ets:es){
      buffer.append("{\"id\":\""+ets.getSupplierId()+"_"+ets.getPackageId()+"\",");
      buffer.append("\"pScore\":\""+ets.getScore()+"\"},");
      HashMap<String, Object> ranMap = new HashMap<String, Object>();
      ranMap.put("supplierId", ets.getSupplierId());
      ranMap.put("packageId", ets.getPackageId());
      ranMap.put("priceScore", ets.getScore());
      saleTenderService.editSumScore(ranMap);
    }
    buffer=buffer.replace(buffer.lastIndexOf(","), buffer.lastIndexOf(",")+1, "],");
    System.out.println(buffer.toString());
 // 供应商信息
    List<SaleTender> allSupplierList = saleTenderService.find(new SaleTender(projectId));
    List<SaleTender> supplierList = new ArrayList<SaleTender>();
    for (int i = 0; i < allSupplierList.size(); i++) {
        SaleTender sale = allSupplierList.get(i);
        if (sale.getPackages().contains(packId) && sale.getIsFirstPass() == 1 && "0".equals(sale.getIsRemoved()) && sale.getIsTurnUp() != null && sale.getIsTurnUp() == 0) {
            supplierList.add(sale);
        }
    }
    // 将供应商的经济技术总分存入SaleTender表中
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("packageId", packId);
    
    //根据评分办法筛选后的供应商
    List<SaleTender> finalSupplier = new ArrayList<SaleTender>();
    //封装合格和不合格的供应商
    HashMap<String, Object> rejectByPriceMap = new HashMap<String, Object>();
    //根据有效平均报价剔除不合格供应商
    rejectByPriceMap = packageExpertService.rejectByPrice(packId, projectId, supplierList);
    
    //根据有效经济技术平均分剔除不合格供应商
    finalSupplier = packageExpertService.rejectByScore(packId, projectId, rejectByPriceMap);
   
    //往saleTener插入最终供应商排名
    packageExpertService.rank(packId, projectId, finalSupplier);
    for (int i = 0; i < finalSupplier.size(); i++) {
      SaleTender st = finalSupplier.get(i);
      String reviewResult = st.getReviewResult();
      int rank = i+1;
      reviewResult += rank;
      //插入排名
      HashMap<String, Object> ranMap = new HashMap<String, Object>();
      ranMap.put("reviewResult", reviewResult);
      ranMap.put("supplierId", st.getSuppliers().getId());
      ranMap.put("packageId", st.getPackages());
      ranMap.put("economicScore", st.getEconomicScore());
      ranMap.put("technologyScore", st.getTechnologyScore());
      saleTenderService.editSumScore(ranMap);
      //向SUPPLIER_CHECK_PASS表中插入预中标供应商
      BigDecimal totalSupplier = new BigDecimal(0);
      totalSupplier= totalSupplier.add(st.getEconomicScore());
      totalSupplier= totalSupplier.add(st.getTechnologyScore());
      SupplierCheckPass record = new SupplierCheckPass();
      record.setId(WfUtil.createUUID());
      record.setPackageId(packId);
      record.setProjectId(projectId);
      record.setSupplierId(st.getSuppliers().getId());
      record.setTotalScore(totalSupplier);
      record.setTotalPrice(st.getTotalPrice());
      record.setRanking(i+1);
      SupplierCheckPass checkPass = new SupplierCheckPass();
      checkPass.setPackageId(packId);
      checkPass.setSupplierId(st.getSuppliers().getId());
      //判断是否有旧数据
      List<SupplierCheckPass> oldList= checkPassService.listCheckPass(checkPass);
      if (oldList != null && oldList.size() > 0) {
        for (SupplierCheckPass supplierCheckPass : oldList) {
          //删除原数据
          checkPassService.delete(supplierCheckPass.getId());
        }
      }
      checkPassService.insert(record);
    }
    StringBuffer ranks=new StringBuffer();
    ranks.append("\"ranks\":[");
    StringBuffer score=new StringBuffer();
    score.append("\"scores\":[");
    for(ExpertScore ets:es){
      SaleTender st=new SaleTender();
      st.setSupplierId(ets.getSupplierId());
      st.setPackages(packId);
      List<SaleTender> sts = saleTenderService.getPackegeSuppliers(st);
      ranks.append("{\"id\":\""+sts.get(0).getSuppliers().getId()+"_"+sts.get(0).getPackages()+"\",");
      ranks.append("\"rank\":\""+sts.get(0).getReviewResult().substring(sts.get(0).getReviewResult().lastIndexOf("_")+1,sts.get(0).getReviewResult().length())+"\"},");
      score.append("{\"id\":\""+sts.get(0).getSuppliers().getId()+"_"+sts.get(0).getPackages()+"\",");
      score.append("\"score\":\""+sts.get(0).getPriceScore()+"(价格)+"+sts.get(0).getEconomicScore()+"(经济)+"+sts.get(0).getTechnologyScore()+"(技术)="+sts.get(0).getPriceScore().add(sts.get(0).getEconomicScore()).add(sts.get(0).getTechnologyScore())+"\"},");
    }
    ranks=ranks.replace(ranks.lastIndexOf(","), ranks.lastIndexOf(",")+1, "],");
    score=score.replace(score.lastIndexOf(","), score.lastIndexOf(",")+1, "]}");
    buffer.append(ranks);
    buffer.append(score);
    System.out.println(buffer.toString());
	  super.writeJson(response, buffer.toString());
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
		if(typeName=="2" || typeName.equals("2")) {
			list = ScoreModelUtil.getScoreByModelThree(scoreModel2, smList);
		}
		if(typeName=="9" || typeName.equals("9")) {
      list = ScoreModelUtil.getScoreByModelFourB(scoreModel2, smList);
    }
		if(typeName=="3" || typeName.equals("3")) {
			 list = ScoreModelUtil.getScoreByModelFour(scoreModel2, smList);
		}
		if(typeName=="4" || typeName.equals("4")) {
		    double param = 0;
		    for (SupplyMark supplyMark : smList) {
                if (param < supplyMark.getPrarm()) {
                    param = supplyMark.getPrarm();
                }
            }
		    SupplyMark smCondition = new SupplyMark();
		    smCondition.setPrarm(param);
			list = ScoreModelUtil.getScoreByModelFive(scoreModel2, smList, smCondition);
		}
		if(typeName=="5" || typeName.equals("5")) {
		    double param = smList.get(0).getPrarm();
            for (SupplyMark supplyMark : smList) {
                if (param > supplyMark.getPrarm()) {
                    param = supplyMark.getPrarm();
                }
            }
            SupplyMark smCondition = new SupplyMark();
            smCondition.setPrarm(param);
			list = ScoreModelUtil.getScoreByModelSix(scoreModel2, smList,smCondition);
		}
		
		// 
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
	public String caseGradeTwo(HttpSession session,String supplierIds,Double expertValues,String scoreModelId,String typeName,String quotaId,String projectId,String packageId){
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

	/**
	 *〈简述〉获取当前专家给供应商的合计得分
	 *〈详细描述〉
	 * @author Ye MaoLin
	 * @param supplierId
	 * @param projectId
	 * @param packageId
	 * @param session
	 * @return
	 */
	@RequestMapping("supplierTotal")
	@ResponseBody
	public String supplierTotal(String supplierIds, String projectId, String packageId, HttpSession session){
	    //当前登录用户
      User user = (User)session.getAttribute("loginUser");
      String expertId = user.getTypeId();
      HashMap<String, Object> map = new HashMap<String, Object>();
      map.put("expertId", expertId);
      map.put("projectId", projectId);
      map.put("packageId", packageId);
      map.put("supplierId", supplierIds);
      BigDecimal score = expertScoreService.selectSumByMap(map);
	    return JSON.toJSONString(score);
	}
	  
	/**
	 *〈简述〉
	 * 专家后台判断是否可以进行查看
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param packageId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("isShowView")
	public String isShowView (String packageId) {
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("packageId", packageId);
	    List<PackageExpert> list = packageExpertService.selectList(map);
	    String isShowView = "0";
	    if (list != null && list.size() > 0) {
	        PackageExpert pack = list.get(0);
	        if (pack.getIsGatherGather() == 1) {
	            isShowView = "1";
	        }
	    }
	    return isShowView;
	}
	
	/**
	 *〈简述〉
	 * 专家后台经济技术评审页面的暂存功能
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param expertId
	 * @param packageId
	 * @param projectId
	 */
	@ResponseBody
	@RequestMapping("/zanCun")
	public void zanCun (PackageExpert record) {
	    record.setIsGrade((short) 2);
	    packageExpertService.updateByBean(record);
	}
	
	/**
   *〈简述〉
   * 专家后台经济技术非模型评审页面
   *〈详细描述〉
   * @author Ye Maolin
	 * @param projectId
	 * @param packageId
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("toCheck")
  public String toCheck(String projectId, String packageId, Model model, HttpSession session){
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
      
      if(packages.getTechniqueTime() != null){
          packages.setTechniqueTime(new Date());
          packageService.updateByPrimaryKeySelective(packages);
      }
    } else {
        List<AdvancedPackages> selectByAll = advancedPackageService.selectByAll(map);
        if(selectByAll != null && !selectByAll.isEmpty()){
            AdvancedPackages packages = selectByAll.get(0);
            //放入包信息
            extension.setPackageId(packages.getId());
            extension.setPackageName(packages.getName());
            
            if(packages.getTechniqueTime() != null){
                packages.setTechniqueTime(new Date());
                advancedPackageService.update(packages);
            }
        }
    }
    //查询项目信息
    Project project = projectService.selectById(projectId);
    if(project!=null){
      //放入项目信息
      extension.setProjectId(project.getId());
      extension.setProjectName(project.getName());
      extension.setProjectCode(project.getProjectNumber());
    } else {
        AdvancedProject project2 = advancedProjectService.selectById(projectId);
        if(project2 != null){
            extension.setProjectId(project2.getId());
            extension.setProjectName(project2.getName());
            extension.setProjectCode(project2.getProjectNumber());
        }
    }
    // 专家可以评审的类型
    Map<String, Object> map2 = new HashMap<>();
    map2.put("projectId", projectId);
    map2.put("packageId", packageId);
    map2.put("expertId", user.getTypeId());
    List<PackageExpert> pes = packageExpertService.selectList(map2);
    String kind = null;
    if (pes != null && pes.size() > 0) {
      kind = pes.get(0).getReviewTypeId();
    }
    //查询改包下的经济技术评审项信息
    FirstAudit firstAudit = new FirstAudit();
    firstAudit.setPackageId(packageId);
    firstAudit.setProjectId(projectId);
    firstAudit.setIsConfirm((short)1);
    firstAudit.setKind(kind);
    List<FirstAudit> firstAuditList = firstAuditService.findBykind(firstAudit);
    extension.setFirstAuditList(firstAuditList);
    
    // 获取符合性审查通过且到场没被移除的供应商
    SaleTender saleTender = new SaleTender();
    saleTender.setPackages(packageId);
    saleTender.setIsFirstPass(1);
    saleTender.setIsRemoved("0");
    saleTender.setIsTurnUp(0);
    List<SaleTender> supplierList = saleTenderService.findByCon(saleTender);
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
    model.addAttribute("dd", DictionaryDataUtil.findById(kind));
    return "bss/prms/audit/review_check_audit";
  }
	
	
	@RequestMapping("/showView")
	public String showView(Model model, String packageId, String expertId){
	    if(StringUtils.isNotBlank(expertId) && StringUtils.isNotBlank(packageId)){
	        //专家信息
            Expert expert = expertService.selectByPrimaryKey(expertId);
            if(expert != null){
                HashMap<String, Object> map = new HashMap<>();
                map.put("packageId", packageId);
                map.put("expertId", expert.getId());
                List<PackageExpert> packageExperts = packageExpertService.selectList(map);
                if(packageExperts != null && packageExperts.size() > 0){
                    model.addAttribute("isSubmit", packageExperts.get(0).getIsAudit());
                }
                Packages packages = packageService.selectByPrimaryKeyId(packageId);
                String packName = "";
                String projectId = "";
                if(packages != null){
                    packName = packages.getName();
                    projectId = packages.getProjectId();
                } else {
                    AdvancedPackages packages2 = advancedPackageService.selectById(packageId);
                    if(packages2 != null){
                        packName = packages2.getName();
                        projectId = packages2.getProjectId();
                    }
                }
                //创建封装的实体
                Extension extension = new Extension();
                extension.setPackageId(packageId);
                extension.setPackageName(packName);
                Project project = projectService.selectById(projectId);
                if(project != null){
                    extension.setProjectId(project.getId());
                    extension.setProjectName(project.getName());
                    extension.setProjectCode(project.getProjectNumber());
                    model.addAttribute("project", project);
                } else {
                    AdvancedProject project2 = advancedProjectService.selectById(projectId);
                    if(project2 != null){
                        extension.setProjectId(project2.getId());
                        extension.setProjectName(project2.getName());
                        extension.setProjectCode(project2.getProjectNumber());
                        model.addAttribute("project", project2);
                    }
                }
                //获取包下的评审项
                FirstAudit firstAudit = new FirstAudit();
                firstAudit.setPackageId(packageId);
                firstAudit.setIsConfirm((short)0);
                List<FirstAudit> fas = firstAuditService.findBykind(firstAudit);
                if(fas != null && fas.size() > 0){
                    //放入初审项集合
                    extension.setFirstAuditList(fas);
                    // 获取符合性审查通过且到场没被移除的供应商
                    SaleTender saleTender = new SaleTender();
                    saleTender.setPackages(packageId);
                    saleTender.setIsTurnUp(0);
                    List<SaleTender> supplierList = saleTenderService.findByCon(saleTender);
                    if(supplierList != null && supplierList.size() > 0){
                        extension.setSupplierList(supplierList);
                    }
                }
                
                //查询审核过的信息用于回显
                Map<String, Object> reviewFirstAuditMap = new HashMap<>();
                reviewFirstAuditMap.put("projectId", projectId);
                reviewFirstAuditMap.put("packageId", packageId);
                reviewFirstAuditMap.put("expertId", expertId);
                List<ReviewFirstAudit> reviewFirstAuditList = service.selectList(reviewFirstAuditMap);
                
                
                //查询评分信息
                Map<String, Object> maps = new HashMap<>();
                maps.put("projectId", projectId);
                maps.put("packageId", packageId);
                // 专家可以打分的类型
                List<DictionaryData> markTermTypeList = new ArrayList<DictionaryData>();
                maps.put("expertId", expertId);
                String typeId = packageExpertService.selectList(maps).get(0).getReviewTypeId();
                markTermTypeList.add(dictionaryDataServiceI.getDictionaryData(typeId));
                model.addAttribute("markTermTypeList", markTermTypeList);
                
                
             // 查询所有的ScoreModel
                ScoreModel scoreModel = new ScoreModel();
                scoreModel.setPackageId(packageId);
                scoreModel.setProjectId(projectId);
                List<ScoreModel> scoreModelList = scoreModelService.findListByScoreModel(scoreModel);
                for (ScoreModel score : scoreModelList) {
                    if (score.getStandardScore() == null || "".equals(score.getStandardScore())) {
                        score.setStandardScore(score.getMaxScore());
                    }
                }
                model.addAttribute("scoreModelList", scoreModelList);
                // 查出该包内所有的markTerm
                MarkTerm markTerm = new MarkTerm();
                markTerm.setProjectId(projectId);
                markTerm.setPackageId(packageId);
                List<MarkTerm> allMarkTerm = markTermService.findListByMarkTerm(markTerm);
                // 遍历去除pid is not null 的
                List<MarkTerm> markTermList = new ArrayList<MarkTerm>();
                for (MarkTerm mark : allMarkTerm) {
                    if ("0".equals(mark.getPid()) && mark.getTypeName().equals(typeId)) {
                        markTermList.add(mark);
                    }
                }
                // 查询父节点的子节点个数
                for (int i = 0; i < markTermList.size(); i++) {
                    int count = 0;
                    for (ScoreModel score : scoreModelList) {
                        if (markTermList.get(i).getId().equals(score.getMarkTerm().getPid())) {
                            count++;
                        }
                    }
                    // 设置指定父节点的rowspan
                    markTermList.get(i).setCount(count);
                }
                // 将count == 0 的移除
                List<MarkTerm> markTerms = new ArrayList<MarkTerm>();
                for (MarkTerm mark : markTermList) {
                  MarkTerm mt1 = new MarkTerm();
                  mt1.setPid(mark.getId());
                  mt1.setProjectId(projectId);
                  mt1.setPackageId(packageId);
                  List<MarkTerm> mtValue = markTermService.findListByMarkTerm(mt1);
                  for (MarkTerm mt : mtValue) {
                      if ("1".equals(mt.isChecked())) {
                        mark.setCheckedPrice(mt.isChecked());
                      }
                  }
                    if (mark.getCount() != 0) {
                        markTerms.add(mark);
                    }
                }
                if (markTerms.size() > 0 && scoreModelList.size() > 0) {
                    for (int i = 0; i < markTerms.size(); i++) {
                        int count = 0;
                        for (int j = 0; j < scoreModelList.size(); j++) {
                            if (markTerms.get(i).getId().equals(scoreModelList.get(j).getMarkTerm().getPid())) {
                                if (count == 0) {
                                    scoreModelList.get(j).setCount(markTerms.get(i).getCount());
                                } else {
                                    scoreModelList.get(j).setCount(0); 
                                }
                                count++;
                            }
                        }
                    }
                }
                if(markTerms != null && markTerms.size() > 0){
                    model.addAttribute("markTermList", markTerms);
                }
                
                
                //查询供应商信息
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("packageId", packageId);
                hashMap.put("projectId", projectId);
                hashMap.put("isFirstPass", 1);
                hashMap.put("isRemoved", "0");
                hashMap.put("isTurnUp", 0);
                
                List<SaleTender> supplierList = saleTenderService.getAdPackegeSuppliers(hashMap);
                model.addAttribute("supplierList", supplierList);
                
                List<ExpertScore> scoresList = expertScoreService.selectInfoByMap(maps);
                removeSame(scoresList);
                List<ExpertScore> scores = new ArrayList<ExpertScore>();
                // 判断如果该专家评分被退回就remove
                for (ExpertScore score : scoresList) {
                    Map<String, Object> map1 = new HashMap<String, Object>();
                    map1.put("packageId", score.getPackageId());
                    map1.put("expertId", score.getExpertId());
                    List<PackageExpert> temp = packageExpertService.selectList(map1);
                    if (temp.get(0).getIsGrade() == 1) {
                        scores.add(score);
                    }
                }
                model.addAttribute("scores", scores);
                // 供应商经济总分,技术总分,总分
                List<SupplierRank> rankList = new ArrayList<SupplierRank>();
                for (SaleTender supp : supplierList) {
                    SupplierRank rank = new SupplierRank();
                    if(supp.getSuppliers() != null && StringUtils.isNotBlank(supp.getSuppliers().getId())){
                        rank.setSupplierId(supp.getSuppliers().getId());
                    } else if (StringUtils.isNotBlank(supp.getSupplierId())){
                        rank.setSupplierId(supp.getSupplierId());
                    }
                    rank.setPackageId(supp.getPackages());
                    BigDecimal es = supp.getEconomicScore();
                    if (es == null) {
                      rank.setEconScore(null);
                    } else {
                      rank.setEconScore(es);
                    }
                    BigDecimal ts = supp.getTechnologyScore();
                    if (ts == null) {
                      rank.setTechScore(null);
                    } else {
                      rank.setTechScore(ts);
                    }
                    if (es == null || ts == null) {
                      rank.setSumScore(null);
                    } else {
                      rank.setSumScore(supp.getEconomicScore().add(supp.getTechnologyScore()));
                    }
                    rankList.add(rank);
                }
                // 循环遍历判断名次
                for (SupplierRank rank : rankList) {
                    // 判断review_result是否不为空
                    SaleTender saleTend = new SaleTender();
                    saleTend.setPackages(rank.getPackageId());
                    Supplier supplier = new Supplier();
                    supplier.setId(rank.getSupplierId());
                    saleTend.setSuppliers(supplier);
                    String reviewResult = saleTenderService.findByCon(saleTend).get(0).getReviewResult();
                    rank.setReviewResult(reviewResult);
                }
                model.addAttribute("rankList", rankList);
                // 新增参数
                double sum = 1;
                double length = (sum/supplierList.size())*100;
                model.addAttribute("length1", length + "%");
                model.addAttribute("length2", length/2 + "%");
                model.addAttribute("size", supplierList.size());
                
                List<DictionaryData> find = DictionaryDataUtil.find(22);
                
                model.addAttribute("dds", find);
                model.addAttribute("reviewFirstAuditList", reviewFirstAuditList);
                model.addAttribute("extension", extension);
                model.addAttribute("expert", expert);
                model.addAttribute("pack", packages);
                model.addAttribute("expertId", expertId);
                model.addAttribute("projectId", projectId);
                model.addAttribute("packageId", packageId);
            }
	    }
	    return "bss/prms/audit/showView";
	}
	
	
	private List<ExpertScore> removeSame(List<ExpertScore> list){
        for (int i = 0; i < list.size(); i++) {
            for (int j = list.size() - 1 ; j > i; j--) {
                if (list.get(i).getScoreModelId().equals(list.get(j).getScoreModelId()) && list.get(i).getExpertId().equals(list.get(j).getExpertId()) && list.get(i).getSupplierId().equals(list.get(j).getSupplierId())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }
}
