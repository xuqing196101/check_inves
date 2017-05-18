package bss.controller.ppms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.model.oms.util.AjaxJsonData;
import ses.model.oms.util.CommonConstant;
import ses.model.oms.util.Ztree;
import ses.util.DictionaryDataUtil;
import ses.util.FloatUtil;
import bss.controller.base.BaseController;
import bss.formbean.Jzjf;
import bss.model.ppms.BidMethod;
import bss.model.ppms.FlowExecute;
import bss.model.ppms.MarkTerm;
import bss.model.ppms.Packages;
import bss.model.ppms.ParamInterval;
import bss.model.ppms.Project;
import bss.model.ppms.ScoreModel;
import bss.model.ppms.SupplyMark;
import bss.model.prms.FirstAudit;
import bss.model.prms.FirstAuditTemitem;
import bss.model.prms.FirstAuditTemplat;
import bss.service.ppms.BidMethodService;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.MarkTermService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ParamIntervalService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.ScoreModelService;
import bss.service.prms.FirstAuditService;
import bss.service.prms.FirstAuditTemplatService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 
 * 版权：(C) 版权所有 
 * <简述>  八大评分模型
 * <详细描述>
 * @author   tiankf
 * @version  
 * @since
 * @see
 */
@Controller
@Scope("prototype")
@RequestMapping("/intelligentScore")
public class IntelligentScoringController extends BaseController{
    private AjaxJsonData ajaxJsonData = new AjaxJsonData();
	@Autowired
	private PackageService packageService;
	@Autowired
	private ScoreModelService scoreModelService;
	
	@Autowired
	private MarkTermService markTermService;
	
	@Autowired
	private ParamIntervalService paramIntervalService;
	
	@Autowired
	private BidMethodService bidMethodService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private FirstAuditService service;
	@Autowired
	private FirstAuditTemplatService firstAuditTemplatService;
	
	@Autowired
	private FlowMangeService flowMangeService;
	
	@RequestMapping(value = "checkScore")
	@ResponseBody
	public Integer checkScore(String standScore, String maxScore, String projectId, String packageId, String id, String checked){
	    List<DictionaryData> ddList = DictionaryDataUtil.find(23);
	    Double score = 0.0;
	    Integer result = 0;
	    int checkCount = 0;
        for (DictionaryData dictionaryData : ddList) {
            MarkTerm mt = new MarkTerm();
            mt.setTypeName(dictionaryData.getId());
            mt.setProjectId(projectId);
            mt.setPackageId(packageId);
            //默认顶级节点为0
            mt.setPid("0");
            List<MarkTerm> mtList = markTermService.findListByMarkTerm(mt);
            for (MarkTerm mtKey : mtList) {
                MarkTerm mt1 = new MarkTerm();
                mt1.setPid(mtKey.getId());
                mt1.setProjectId(projectId);
                mt1.setPackageId(packageId);
                List<MarkTerm> mtValue = markTermService.findListByMarkTerm(mt1);
                for (MarkTerm markTerm : mtValue) {
                    
                    if (id == null && "1".equals(markTerm.isChecked())) {
                        checkCount ++;
                    }
                    if (id != null && !markTerm.getSmId().equals(id) && "1".equals(markTerm.isChecked())) {
                        checkCount ++;
                    }
                    
                    if (markTerm.getSmId().equals(id)){
                        continue;
                    }
                    Double scscore = markTerm.getScscore();
                    if (scscore == null) {
                        scscore = 0.0;
                    }
                    score = score + scscore;
                }
            }
        }
	    if (standScore != null && !"".equals(standScore)) {
	        double resultScore = Double.parseDouble(standScore) + score;
	        if (resultScore <= 100){
	            result = 1;
	        }
	    } else {
	        if (maxScore != null && !"".equals(maxScore)){
                double resultScore = Double.parseDouble(maxScore) + score;
                if (resultScore <= 100){
                    result = 1;
                }
	        }  else {
	            result = 1; //新增的时候
	        }
	    }

        if (result == 1 && checkCount == 1 && "1".equals(checked)) {
            result = 2;//提示已存在
        }
	    return result;
	}
	
	@RequestMapping(value = "checkIsCheck")
    @ResponseBody
    public Integer checkIsCheck(String projectId, String packageId){
        List<DictionaryData> ddList = DictionaryDataUtil.find(23);
        Integer result = 0;
        int checkCount = 0;
        for (DictionaryData dictionaryData : ddList) {
            MarkTerm mt = new MarkTerm();
            mt.setTypeName(dictionaryData.getId());
            mt.setProjectId(projectId);
            mt.setPackageId(packageId);
            //默认顶级节点为0
            mt.setPid("0");
            List<MarkTerm> mtList = markTermService.findListByMarkTerm(mt);
            for (MarkTerm mtKey : mtList) {
                MarkTerm mt1 = new MarkTerm();
                mt1.setPid(mtKey.getId());
                mt1.setProjectId(projectId);
                mt1.setPackageId(packageId);
                List<MarkTerm> mtValue = markTermService.findListByMarkTerm(mt1);
                for (MarkTerm markTerm : mtValue) {
                    if ("1".equals(markTerm.isChecked())) {
                        checkCount ++;
                    }
                }
            }
        }
        if (checkCount == 0) {
            result = 1;
        }
        return result;
    }
	
	@RequestMapping(value = "addScoreMethod")
    public String addScoreMethod(Model model, String packageId, String projectId, String flowDefineId) {
	    List<DictionaryData> ddList = DictionaryDataUtil.find(27);
	    model.addAttribute("projectId", projectId);
	    model.addAttribute("packageId", packageId);
	    model.addAttribute("flowDefineId", flowDefineId);
	    model.addAttribute("ddList", ddList);
        return "bss/ppms/open_bidding/add_score_method";
    }
	
	@RequestMapping(value = "showScoreMethod")
    public String showScoreMethod(Model model, BidMethod bm, String packageId, String projectId, String flowDefineId) {
        List<BidMethod> bidMethod = bidMethodService.findScoreMethod(bm);
        if (bidMethod != null && bidMethod.size() > 0){
            model.addAttribute("bidMethod", bidMethod.get(0));
            model.addAttribute("packageId", packageId);
            model.addAttribute("projectId", projectId);
            model.addAttribute("flowDefineId", flowDefineId);
        }
        List<DictionaryData> ddList = DictionaryDataUtil.find(27);
        model.addAttribute("ddList", ddList);
        return "bss/ppms/open_bidding/show_score_method";
    }
	
	@RequestMapping(value = "updateScoreMethod")
    public String updateScoreMethod(BidMethod bm, String packageId, String projectId, String flowDefineId) {
	    bm.setProjectId(projectId);
	    bm.setPackageId(packageId);
	    bidMethodService.updateBidMethod(bm);
        return "redirect:packageList.html?flowDefineId=" + flowDefineId + "&projectId=" + projectId;
    }
	
	
	@RequestMapping(value = "saveScoreMethod")
	public String saveScoreMethod(BidMethod bm, String packageId, String projectId, String flowDefineId) {
	    bm.setProjectId(projectId);
	    bm.setPackageId(packageId);
	    bidMethodService.save(bm);
	    return "redirect:packageList.html?flowDefineId=" + flowDefineId + "&projectId=" + projectId;
	}
	
	@RequestMapping(value = "editScoreMethod")
    public void editScoreMethod(String packageId, String projectId) {
        
    }
	
	@RequestMapping("/loadTemplat")
	public void loadTemplat(HttpServletResponse response, String id, String projectId, String packageId) throws IOException{
	    try{
	        if (id != null && !"".equals(id) && packageId != null && !"".equals(packageId)) {
    	        //模板导入前首先给现有的东西删除掉所有的项目id都一样。所以按照项目id删除
    	        HashMap<String, Object> condition = new HashMap<String, Object>();
    	        condition.put("projectId", projectId);
    	        condition.put("packageId", packageId);
    	        bidMethodService.delBidMethodByMap(condition);
    	        markTermService.delMarkTermByMap(condition);
    	        scoreModelService.delScoreModelByMap(condition);
    	        paramIntervalService.delParamIntervalByMap(condition);
    	        //然后再来修改模板数据的projectid和packageid
    	        BidMethod bmCondition = new BidMethod();
    	        bmCondition.setProjectId(id);
    	        List<BidMethod> bmList = bidMethodService.findListByBidMethod(bmCondition);
    	        //模板中的数据
    	        MarkTerm mtCondition = new MarkTerm();
                mtCondition.setProjectId(id);
                List<MarkTerm> mtList = markTermService.findListByMarkTerm(mtCondition);
                //模板中数据
                ScoreModel smCondition = new ScoreModel();
                smCondition.setProjectId(id);
                List<ScoreModel> smList = scoreModelService.findListByScoreModel(smCondition);
                //模型七八的差额区间数据
                List<ParamInterval> piList = new ArrayList<ParamInterval>();
                //模型七八对应的差额区间查询条件
                ParamInterval paramInterval = new ParamInterval();
                long nowDate = new Date().getTime();
                long addTime = 1000l;//一秒
    	        for (BidMethod bidMethod : bmList) {
    	            //修改之前新增一条
    	            BidMethod bm = new BidMethod();
    	            bm.setId(null);
    	            bm.setName(bidMethod.getName());
    	            bm.setProjectId(id);
    	            bm.setIsDeleted("0");
    	            bm.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    	            bm.setRemainScore(bidMethod.getRemainScore());
    	            bm.setRemark(bidMethod.getRemark());
    	            //bm.setPackageId(packageId);
    	            bidMethodService.save(bm);
    	           
    	            for (MarkTerm markTerm : mtList) {
    	                if (markTerm.getBidMethodId() != null && markTerm.getBidMethodId().equals(bidMethod.getId())) {
    	                    MarkTerm mt = new MarkTerm();
    	                    mt.setId(null);
    	                    mt.setPid("0");
    	                    mt.setName(markTerm.getName());
    	                    mt.setIsDeleted(0);
    	                    mt.setCreatedAt(new Date());
    	                    mt.setMaxScore(markTerm.getMaxScore());
    	                    mt.setProjectId(id);
    	                    mt.setRemainScore(markTerm.getRemainScore());
    	                    mt.setTypeName(markTerm.getTypeName());
    	                    mt.setBidMethodId(bm.getId());
    	                    markTermService.save(mt);
    	                    
    	                    for (MarkTerm markTerm2 : mtList) {
                                if (markTerm.getId().equals(markTerm2.getPid())) {
                                    
                                    MarkTerm mtChildren = new MarkTerm();
                                    mtChildren.setId(null);
                                    mtChildren.setPid(mt.getId());
                                    mtChildren.setName(markTerm2.getName());
                                    mtChildren.setIsDeleted(0);
                                    //每条增加的时间都不一样
                                    addTime = addTime + 1000l;
                                    nowDate = nowDate + addTime;
                                    mtChildren.setCreatedAt(new Date(nowDate));
                                    mtChildren.setMaxScore(markTerm2.getMaxScore());
                                    mtChildren.setProjectId(id);
                                    mtChildren.setChecked(markTerm2.isChecked());
                                    mtChildren.setRemainScore(markTerm2.getRemainScore());
                                    mtChildren.setTypeName(markTerm2.getTypeName());
                                    markTermService.saveMarkTerm(mtChildren);
                                    
                                    for (ScoreModel scoreModel : smList) {
                                        if (scoreModel.getMarkTermId().equals(markTerm2.getId())) {
                                            ScoreModel sm = new ScoreModel();
                                            sm.setId(null);
                                            sm.setProjectId(id);
                                            sm.setName(scoreModel.getName());
                                            sm.setTypeName(scoreModel.getTypeName());
                                            sm.setEasyUnderstandContent(scoreModel.getEasyUnderstandContent());
                                            sm.setStandardScore(scoreModel.getStandardScore());
                                            sm.setStandExplain(scoreModel.getStandExplain());
                                            sm.setJudgeContent(scoreModel.getJudgeContent());
                                            sm.setReviewParam(scoreModel.getReviewParam());
                                            sm.setMarkTermId(mtChildren.getId());
                                            sm.setUnitScore(scoreModel.getUnitScore());
                                            sm.setMinScore(scoreModel.getMinScore());
                                            sm.setMaxScore(scoreModel.getMaxScore());
                                            sm.setReviewContent(scoreModel.getReviewContent());
                                            sm.setScore(scoreModel.getScore());
                                            sm.setUnit(scoreModel.getUnit());
                                            sm.setDeadlineNumber(scoreModel.getDeadlineNumber());
                                            sm.setReviewStandScore(scoreModel.getReviewStandScore());
                                            sm.setIntervalNumber(scoreModel.getIntervalNumber());
                                            sm.setIntervalTypeName(scoreModel.getIntervalTypeName());
                                            sm.setAddSubtractTypeName(scoreModel.getAddSubtractTypeName());
                                            sm.setIsHave(scoreModel.getIsHave());
                                            sm.setStandScores(scoreModel.getStandScores());
                                            sm.setRelation(scoreModel.getRelation());
                                            sm.setRelationScore(scoreModel.getRelationScore());
                                            scoreModelService.saveScoreModel(sm);
                                            paramInterval.setScoreModelId(scoreModel.getId());
                                            piList = paramIntervalService.findListByParamInterval(paramInterval);
                                            if (piList != null && piList.size() > 0) {
                                                for(int i = 0; i < piList.size(); i++){
                                                    ParamInterval p = new ParamInterval();
                                                    p.setScoreModelId(sm.getId());
                                                    p.setStartParam(piList.get(i).getStartParam());
                                                    p.setStartRelation(piList.get(i).getStartRelation());
                                                    p.setEndParam(piList.get(i).getEndParam());
                                                    p.setEndRelation(piList.get(i).getEndRelation());
                                                    p.setScore(piList.get(i).getScore());
                                                    p.setExplain(piList.get(i).getExplain());
                                                    p.setProjectId(scoreModel.getProjectId());
                                                    //模版里面不需要包id
                                                    paramIntervalService.saveParamInterval(p);
                                                }
                                                if (piList != null) {
                                                    for (ParamInterval pi : piList) {
                                                        pi.setProjectId(projectId);
                                                        pi.setPackageId(packageId);
                                                        paramIntervalService.updateParamInterval(pi);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
    	                }
    	            }
    	            bidMethod.setProjectId(projectId);
                    bidMethod.setPackageId(packageId);
                    bidMethodService.updateBidMethod(bidMethod);
                }
    	        
                for (MarkTerm markTerm : mtList) {
                    markTerm.setProjectId(projectId);
                    markTerm.setPackageId(packageId);
                    markTermService.updateMarkTerm(markTerm);
                }
    	        
    	        for (ScoreModel scoreModel : smList) {
    	            scoreModel.setProjectId(projectId);
    	            scoreModel.setPackageId(packageId);
    	            scoreModelService.updateScoreModel(scoreModel);
    	        }
	        }
	      String msg = "引入成功";
	      response.setContentType("text/html;charset=utf-8");
	      response.getWriter().print("{\"success\": " + true + ", \"msg\": \"" + msg+ "\"}");
	      response.getWriter().flush();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally{
	        response.getWriter().close();
	    }
	  }
	
	@RequestMapping(value = "deleteScoreModel")
	public String deleteScoreModel(String id, Integer deleteStatus, String projectId ,String packageId) {
	    //为2为顶级结点     1 为子节点
	    HashMap<String, Object> map = new HashMap<String, Object>();
	    HashMap<String, Object> conditionMap = new HashMap<String, Object>();
	    conditionMap.put("projectId", projectId);
        conditionMap.put("packageId", packageId);
	    if (deleteStatus == 1) {
            scoreModelService.deleteScoreModelByMtId(id);
            map.put("id", id);
            markTermService.delMarkTermByid(map);
            paramIntervalService.delParamIntervalByMap(conditionMap);
	    } else {
	        MarkTerm condition = new MarkTerm();
	        condition.setPid(id);
	        //顶级
	        List<MarkTerm> mtList = markTermService.findListByMarkTerm(condition);
	        for (MarkTerm markTerm : mtList) {
	            scoreModelService.deleteScoreModelByMtId(markTerm.getId());
	            map.put("id", markTerm.getId());
	            markTermService.delMarkTermByid(map);
	            paramIntervalService.delParamIntervalByMap(conditionMap);
	        }
	        MarkTerm mt = markTermService.findMarkTermById(id);
	        map.put("id", mt.getBidMethodId());
	        bidMethodService.delBidMethodByMap(map);
	        map.put("id", id);
	        markTermService.delMarkTermByid(map);
	    }
	    return "redirect:editPackageScore.html?projectId=" + projectId + "&packageId=" + packageId;
	}
	
	@RequestMapping(value = "saveScore")
	public void saveScore(HttpServletResponse response, BidMethod bm) throws IOException {
	    String reg="^\\d+$";
	    try {
	        int count = 0;
	        String msg = "";
	        if (bm.getName() == null || "".equals(bm.getName())) {
	          msg += "请输入评审项名称";
	          count ++;
	        }
	        if (bm.getRemainScore()== null) {
	          if (count > 0 ) {
	            msg += "、序号";
	          } else {
	            msg += "请输入排序号";
	          }
	          count ++;
	        } else {
	            boolean isFlag = true;
	            double paiMing = 0.0;
	            List<String> socreList = new ArrayList<String>();
	            
	            BidMethod condition = new BidMethod();
	            condition.setProjectId(bm.getProjectId());
	            condition.setPackageId(bm.getPackageId());
	            condition.setType(bm.getTypeName());
	            List<BidMethod> bdList = bidMethodService.findListByBidMethod(condition);
	            for (BidMethod bidMethod : bdList) {
	                if (bidMethod.getRemainScore() != null && Double.parseDouble(bidMethod.getRemainScore()) > paiMing) {
	                     paiMing = Double.parseDouble(bidMethod.getRemainScore()); 
	                }
	                socreList.add(bidMethod.getRemainScore());
	            }
                for (BidMethod bidMethod : bdList) {
                    if (bm.getId() != null && !"".equals(bm.getId()) && bm.getRemainScore().equals(bidMethod.getRemainScore()) && bm.getId().equals(bidMethod.getId())) {
                        
                    } else if (bm.getId() != null && !"".equals(bm.getId()) && !bm.getRemainScore().equals(bidMethod.getRemainScore()) && bm.getId().equals(bidMethod.getId())) {
                        if (socreList.contains(bm.getRemainScore())) {
                            msg += "排序号重复且排序号已用到了" + paiMing + ",请填写大于" +paiMing + "的排序号.";
                            count++;
                        }
                    } else {
                        if (bm.getId() == null && bm.getRemainScore().equals(bidMethod.getRemainScore())) {
                            isFlag = false;
                            break;
                        }
                }
                }
	            if (!isFlag) {
                    msg += "排序号重复";
                    count++;
                }
	            if (!bm.getRemainScore().matches(reg)) {
                    msg += "排序号为数字";
                    count++;
                }
	        }
	        /*if (bm.getRemark()== null || "".equals(bm.getRemark())) {
	          if (count > 0) {
	            msg += "和评审内容";
	          } else {
	            msg += "请输入评审内容";
	          }
	          count ++;
	        }*/
	        if (count > 0) {
	          response.setContentType("text/html;charset=utf-8");
	          response.getWriter()
	                  .print("{\"success\": " + false + ", \"msg\": \"" + msg+ "\"}");
	        }
	        if (count == 0) {
	          msg += "添加成功";
	          if (bm.getId() != null && !"".equals(bm.getId())) {
	              bm.setTypeName(null);
	              bidMethodService.updateBidMethod(bm);
	          } else {
	              bidMethodService.saveBidMethod(bm);
	          }
	          response.setContentType("text/html;charset=utf-8");
	          response.getWriter()
	                  .print("{\"success\": " + true + ", \"msg\": \"" + msg+ "\"}");
	        }
	        response.getWriter().flush();
	      } catch (Exception e) {
	          e.printStackTrace();
	      } finally{
	          response.getWriter().close();
	      }
	}
	
	@RequestMapping(value = "editScore")
	@ResponseBody
    public BidMethod editScore(HttpServletResponse response, BidMethod bm) throws IOException {
	  MarkTerm mt = markTermService.findMarkTermById(bm.getId());
	  bm.setId(mt.getBidMethodId());
      List<BidMethod> bmList = bidMethodService.findListByBidMethod(bm);
      if (bmList != null && bmList.size() >0 ){
          return bmList.get(0);
      } else {
          return bm;
      }
    }
	
	@RequestMapping("/editPackageScore")
	public String editPackageScore(HttpServletRequest request, String packageId, Model model, String projectId, String flowDefineId){    
	    //获取评分办法数据字典编码
	    String methodCode = bidMethodService.getMethod(projectId, packageId);
	    if (methodCode != null && !"".equals(methodCode)) {
  	      if ("PBFF_JZJF".equals(methodCode) || "PBFF_ZDJF".equals(methodCode)) {
    	        List<DictionaryData> dds = DictionaryDataUtil.find(23);
    	        //经济审查项
    	        FirstAudit firstAudit1 = new FirstAudit();
    	        firstAudit1.setKind(DictionaryDataUtil.getId("ECONOMY"));
    	        firstAudit1.setPackageId(packageId);
    	        firstAudit1.setIsConfirm((short)1);
    	        List<FirstAudit> items1 = service.findBykind(firstAudit1);
    	        //技术审查项
    	        FirstAudit firstAudit2 = new FirstAudit();
    	        firstAudit2.setKind(DictionaryDataUtil.getId("TECHNOLOGY"));
    	        firstAudit2.setPackageId(packageId);
    	        firstAudit2.setIsConfirm((short)1);
    	        List<FirstAudit> items2 = service.findBykind(firstAudit2);
    	        HashMap<String, Object> map = new HashMap<String, Object>();
    	        map.put("id", packageId);
    	        List<Packages> packages = packageService.findPackageById(map);
    	        if (packages != null) {
    	          model.addAttribute("packages", packages.get(0));
    	        }
    	        HashMap<String, Object> map2 = new HashMap<String, Object>();
    	        map2.put("kind", DictionaryDataUtil.getId("REVIEW_CHECK_ET"));
    	        //获取经济技术评审模版
    	        List<FirstAuditTemplat> firstAuditTemplats = firstAuditTemplatService.find(map2);
    	        model.addAttribute("dds", dds);
    	        model.addAttribute("items1", items1);
    	        model.addAttribute("items2", items2);
    	        model.addAttribute("packageId", packageId);
    	        model.addAttribute("projectId", projectId);
    	        model.addAttribute("firstAuditTemplats", firstAuditTemplats);
    	        model.addAttribute("flowDefineId", flowDefineId);
    	        Project project = projectService.selectById(projectId);
    	        model.addAttribute("flag", project.getConfirmFile());
    	        return "bss/prms/score/edit_package_check";
  	      } 
  	      if ("OPEN_ZHPFF".equals(methodCode)) {
    	        //显示经济技术 和子节点  子节点的子节点就是模型
    	        List<DictionaryData> ddList = DictionaryDataUtil.find(23);
    	        String str ="";
    	        for (DictionaryData dictionaryData : ddList) {
    	            str += getTable(request, dictionaryData.getId(), dictionaryData.getName(), projectId, packageId);
    	        }
    	        //页面需要显示包
    	        HashMap<String, Object> condition = new HashMap<String, Object>();
    	        condition.put("id", packageId);
    	        List<Packages> packages = packageService.findPackageById(condition);
    	        if (packages != null && packages.size() > 0) {
    	          model.addAttribute("packages", packages.get(0));
    	        }
    	        //获取经济技术审查模版
    	        HashMap<String, Object> map2 = new HashMap<String, Object>();
    	        map2.put("kind", DictionaryDataUtil.getId("REVIEW_ET"));
    	        //获取资格性和符合性审查模版
    	        List<FirstAuditTemplat> firstAuditTemplats = firstAuditTemplatService.find(map2);
    	        model.addAttribute("firstAuditTemplats", firstAuditTemplats);
    	        model.addAttribute("packageId", packageId);
    	        model.addAttribute("projectId", projectId);
    	        model.addAttribute("flowDefineId", flowDefineId);
    	        model.addAttribute("ddList", ddList);
    	        model.addAttribute("str", str);
    	        return "bss/prms/score/edit_package_qc";
  	      }
      }
	    return null;
	}
	
	  @RequestMapping("/viewModel")
    public String viewModel(String packageId, Model model, String projectId){    
  	    //获取该包的评分办法
        String methodCode = bidMethodService.getMethod(projectId, packageId);
        if (methodCode != null && !"".equals(methodCode)) {
            if ("PBFF_JZJF".equals(methodCode) || "PBFF_ZDJF".equals(methodCode)) {
              //基准价法和最低价法
              List<DictionaryData> dds = DictionaryDataUtil.find(23);
              //经济审查项
              FirstAudit firstAudit1 = new FirstAudit();
              firstAudit1.setKind(DictionaryDataUtil.getId("ECONOMY"));
              firstAudit1.setPackageId(packageId);
              firstAudit1.setIsConfirm((short)1);
              List<FirstAudit> items1 = service.findBykind(firstAudit1);
              //技术审查项
              FirstAudit firstAudit2 = new FirstAudit();
              firstAudit2.setKind(DictionaryDataUtil.getId("TECHNOLOGY"));
              firstAudit2.setPackageId(packageId);
              firstAudit2.setIsConfirm((short)1);
              List<FirstAudit> items2 = service.findBykind(firstAudit2);
              HashMap<String, Object> map = new HashMap<String, Object>();
              map.put("id", packageId);
              List<Packages> packages = packageService.findPackageById(map);
              if (packages != null) {
                model.addAttribute("packages", packages.get(0));
              }
              HashMap<String, Object> map2 = new HashMap<String, Object>();
              map2.put("kind", DictionaryDataUtil.getId("REVIEW_CHECK_ET"));
              //获取经济技术评审模版
              List<FirstAuditTemplat> firstAuditTemplats = firstAuditTemplatService.find(map2);
              model.addAttribute("dds", dds);
              model.addAttribute("items1", items1);
              model.addAttribute("items2", items2);
              model.addAttribute("packageId", packageId);
              model.addAttribute("projectId", projectId);
              model.addAttribute("firstAuditTemplats", firstAuditTemplats);
              Project project = projectService.selectById(projectId);
              model.addAttribute("flag", project.getConfirmFile());
              return "bss/prms/score/edit_package_check";
            }
            if ("OPEN_ZHPFF".equals(methodCode)) {
              //综合评分法 
              //显示经济技术 和子节点  子节点的子节点就是模型
              List<DictionaryData> ddList = DictionaryDataUtil.find(23);
              String str ="";
              for (DictionaryData dictionaryData : ddList) {
                str += getShowTable(dictionaryData.getId(), dictionaryData.getName(), projectId, packageId);
              }
              //页面需要显示包
              HashMap<String, Object> condition = new HashMap<String, Object>();
              condition.put("id", packageId);
              List<Packages> packages = packageService.findPackageById(condition);
              if (packages != null && packages.size() > 0) {
                model.addAttribute("packages", packages.get(0));
              }
              //获取经济技术审查模版
              HashMap<String, Object> map2 = new HashMap<String, Object>();
              map2.put("kind", DictionaryDataUtil.getId("REVIEW_ET"));
              //获取资格性和符合性审查模版
              List<FirstAuditTemplat> firstAuditTemplats = firstAuditTemplatService.find(map2);
              model.addAttribute("firstAuditTemplats", firstAuditTemplats);
              Project project = projectService.selectById(projectId);
              model.addAttribute("project", project);
              model.addAttribute("packageId", packageId);
              model.addAttribute("projectId", projectId);
              model.addAttribute("ddList", ddList);
              model.addAttribute("str", str);
              return "bss/prms/score/edit_package_qc";
            }
        }
        return null;
    }
	
	public String getTable(HttpServletRequest request, String id, String name ,String projectId, String packageId) {
	    TreeMap<MarkTerm, List<MarkTerm>> map = new TreeMap<MarkTerm, List<MarkTerm>>();
        MarkTerm mt = new MarkTerm();
        mt.setTypeName(id);
        mt.setProjectId(projectId);
        mt.setPackageId(packageId);
        //默认顶级节点为0
        mt.setPid("0");
        List<MarkTerm> mtList = markTermService.findListByMarkTerm(mt);
        Integer count3 = 0;
        for (MarkTerm mtKey : mtList) {
            MarkTerm mt1 = new MarkTerm();
            mt1.setPid(mtKey.getId());
            mt1.setProjectId(projectId);
            mt1.setPackageId(packageId);
            List<MarkTerm> mtValue = markTermService.findListByMarkTerm(mt1);
            if (mtValue != null && mtValue.size() == 0){
                count3 += 1;
            } else {
               //Collections.reverse(mtValue);
                count3 += mtValue.size();
            }
            mtKey.setJudge(mtKey.getPosition());
            map.put(mtKey, mtValue);
        }
        StringBuilder sb = new StringBuilder("");
        Integer count = 0;
        if (mtList != null && mtList.size() >0) {
            for (MarkTerm markKey : map.keySet()) {
                Integer count1 = 0;//游标
                if (count ==0) {
                    if (map.get(markKey) != null && map.get(markKey).size() > 0) {
                        for (MarkTerm markValue : map.get(markKey)) {
                            if(count1 == 0) {
                                sb.append("<tr><td class='w100' rowspan=" + count3 +"><span class='fl'>"+ name +"</span><a class='addItem item_size' onclick=addItem(this,'"+ id +"',1); ></a></td>");
                                sb.append("<td class='w150' rowspan="+map.get(markKey).size()+">");
                                sb.append("<span class='fl'>" + markKey.getName() + "</span><a class='addItem item_size' onclick=addModel('" + markValue.getName() + "','" + markKey.getId() + "',1); ></a>");
                                sb.append("<div class='fr'><a title='编辑' href='javascript:void(0);' onclick=editItem('" + markKey.getId() + "'); class='item_size editItem'></a>");
                                sb.append("<a title='删除' href='javascript:void(0);' onclick=delItem('" + markKey.getId() + "',2) class='item_size deleteItem'></a></div></td>");
                                String typeName = getTypeName(markValue.getSmtypename());
                                sb.append("<td class='tc w80'>" + markValue.getSmname() + "</td>");
                                sb.append("<td class='tc'>" + typeName + "</td>");
                                Double sscore = markValue.getScscore() ;
                                if (sscore == null){
                                    sscore = 0.0;
                                }
                                sb.append("<td>"+ markValue.getName());
                                sb.append("<div class='fr'><a href='javascript:void(0);' title='编辑' onclick=addModel('" + markValue.getName() + "','" + markValue.getId() + "',2); class='item_size editItem'></a>");
                                sb.append("<a href='javascript:void(0);' title='删除' onclick=delItem('" + markValue.getId() + "',1) class='item_size deleteItem'></a></div></td><td>"+sscore+"</td></tr>");
                            } else {
                                String typeName = getTypeName(markValue.getSmtypename());
                                sb.append("<tr><td class='tc w80'>" + markValue.getSmname() + "</td><td class='tc'><span>" + typeName + "</span></td>");
                                Double sscore = markValue.getScscore();
                                if (sscore == null){
                                    sscore = 0.0;
                                }
                                sb.append("<td>"+ markValue.getName());
                                sb.append("<div class='fr'><a href='javascript:void(0);' title='编辑' onclick=addModel('" + markValue.getName() + "','" + markValue.getId() + "',2); class='item_size editItem'></a>");
                                sb.append("<a href='javascript:void(0);' title='删除' onclick=delItem('" + markValue.getId() + "',1) class='item_size deleteItem'></a></div></td><td>"+sscore+"</td></tr>");
                            }
                            count1++;
                        }
                    } else {
                        sb.append("<tr><td rowspan=" + count3 +"><span class='fl'>"+ name +"</span><a class='addItem item_size' onclick=addItem(this,'"+ id +"',1); ></a></td>");
                        sb.append("<td class='w150'><span class='fl'>" + markKey.getName() + "</span><div class='fr'><a class='addItem item_size' onclick=addModel('" + markKey.getName() + "','" + markKey.getId() + "',1); ></a></div>");
                        sb.append("<div class='fr'><a title='编辑' href='javascript:void(0);' onclick=editItem('" + markKey.getId() + "'); class='item_size editItem'></a>");
                        sb.append("<a title='删除' href='javascript:void(0);' onclick=delItem('" + markKey.getId() + "',2) class='item_size deleteItem'></a></div></td>");
                        sb.append("<td></td><td></td><td></td><td></td></tr>");
                    }
                } else {
                    Integer count2 = 0;
                    if (map.get(markKey) != null && map.get(markKey).size() > 0) {
                        for (MarkTerm markValue : map.get(markKey)) {
                            if (count2 ==0) {
                                //sb.append("<tr><td rowspan=" + map.get(markKey).size() + ">"+markKey.getName()+"</td>");
                                sb.append("<tr><td rowspan="+map.get(markKey).size()+">");
                                sb.append("<span class='fl'>" + markKey.getName() + "</span><a class='addItem item_size' onclick=addModel('" + markValue.getName() + "','" + markKey.getId() + "',1); ></a>");
                                sb.append("<div class='fr'><a title='编辑' href='javascript:void(0);' onclick=editItem('" + markKey.getId() + "'); class='item_size editItem'></a>");
                                sb.append("<a title='删除' href='javascript:void(0);' onclick=delItem('" + markKey.getId() + "',2) class='item_size deleteItem'></a></div></td>");
                                
                                //sb.append("<td>" + markValue.getName() + "</td><td></td><td></td></tr>");
                                String typeName = getTypeName(markValue.getSmtypename());
                                sb.append("<td class='tc w80'>" + markValue.getSmname() + "</td><td class='tc'>" + typeName + "</td>");
                                Double sscore = markValue.getScscore();
                                if (sscore == null){
                                    sscore = 0.0;
                                }
                                sb.append("<td>"+ markValue.getName());
                                sb.append("<div class='fr'><a href='javascript:void(0);' title='编辑' onclick=addModel('" + markValue.getName() + "','" + markValue.getId() + "',2); class='item_size editItem'></a>");
                                sb.append("<a href='javascript:void(0);' title='删除' onclick=delItem('" + markValue.getId() + "',1) class='item_size deleteItem'></a></div></td><td>"+sscore+"</td></tr>");
                                
                            } else {
                                String typeName = getTypeName(markValue.getSmtypename());
                                sb.append("<tr><td class='tc'>" + markValue.getSmname() + "</td><td class='tc'>" + typeName + "</td>");
                                Double sscore = markValue.getScscore();
                                if (sscore == null){
                                    sscore = 0.0;
                                }
                                sb.append("<td>"+ markValue.getName());
                                sb.append("<div class='fr'><a href='javascript:void(0);' title='编辑' onclick=addModel('" + markValue.getName() + "','" + markValue.getId() + "',2); class='item_size editItem'></a>");
                                sb.append("<a href='javascript:void(0);' title='删除' onclick=delItem('" + markValue.getId() + "',1) class='item_size deleteItem'></a></div></td><td>"+sscore+"</td></tr>");
                                //sb.append("<tr><td>" + markValue.getName() + "</td><td></td><td></td></tr>");
                            }
                            count2++;
                        }
                    } else {
                        //sb.append("<tr><td>" + markKey.getName() + "</td><td></td><td></td><td></td></tr>");
                        sb.append("<tr><td>");
                        sb.append("<span class='fl'>" + markKey.getName() + "</span><a class='addItem item_size' onclick=addModel('" + markKey.getName() + "','" + markKey.getId() + "',1); ></a>");
                        sb.append("<div class='fr'><a title='编辑' href='javascript:void(0);' onclick=editItem('" + markKey.getId() + "'); class='item_size editItem'></a>");
                        sb.append("<a title='删除' href='javascript:void(0);' onclick=delItem('" + markKey.getId() + "',2) class='item_size deleteItem'></a></div></td>");
                        sb.append("<td></td><td></td><td></td><td></td></tr>");
                    }
                }
                count++;
            }
        } else {
            sb.append("<tr><td><span class='fl'>"+ name +"</span><div class='fr'><a class='addItem item_size' onclick=addItem(this,'"+ id +"',1); ></a></div></td>");
            sb.append("<td></td><td></td><td></td><td></td><td></td></tr>");
        }
        String str = sb.toString();
        return str;
	}
	
	public String getTypeName(Integer typename) {
	    String typeName = "";
        if (typename != null) {
            if (typename == 0) {
                typeName = "模型一A";
            }
            if (typename == 1) {
                typeName = "模型二";
            }
            if (typename == 2) {
                typeName = "模型三";
            }
            if (typename == 3) {
                typeName = "模型四A";
            }
            if (typename == 4) {
                typeName = "模型五";
            }
            if (typename == 5) {
                typeName = "模型六";
            }
            if (typename == 6) {
                typeName = "模型七";
            }
            if (typename == 7) {
                typeName = "模型八";
            }
            if (typename == 8) {
                typeName = "模型一B";
            }
            if (typename == 9) {
                typeName = "模型四B";
            }
        }
        return typeName;
	}
	
	
	@RequestMapping("packageList")
	public String packageList(@ModelAttribute Packages packages,Model model,HttpServletRequest request,String flowDefineId, String msg){
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("projectId", packages.getProjectId());
		Project project = projectService.selectById(packages.getProjectId());
		List<Packages> packagesList = packageService.findPackageAndBidMethodById(map);
		//增加一个字段判断又没有评分办法
		BidMethod bm = new BidMethod();
        for (Packages packages2 : packagesList) {
            BidMethod condition = new BidMethod();
            condition.setProjectId(packages.getProjectId());
            condition.setPackageId(packages2.getId());
            List<BidMethod> bmList = bidMethodService.findScoreMethod(condition);
            bm.setProjectId(packages.getProjectId());
            bm.setPackageId(packages2.getId());
            List<BidMethod> bl = bidMethodService.findListByBidMethod(bm);
            //  第一条是评分办法 大于1 说明有模型列表有值
            if (bl != null && bl.size() > 1) {
                packages2.setIsEditSecond(2);
            } else if (bl != null && bl.size() == 1){
                //获取该包的评分办法
                String methodCode = bidMethodService.getMethod(packages2.getProjectId(), packages2.getId());
                if (methodCode != null && !"".equals(methodCode)) {
                    if ("PBFF_JZJF".equals(methodCode) || "PBFF_ZDJF".equals(methodCode)) {
                      //基准价法和最低价法
                      FirstAudit firstAudit = new FirstAudit();
                      firstAudit.setPackageId(packages2.getId());
                      firstAudit.setIsConfirm((short)1);
                      List<FirstAudit> fas = service.findBykind(firstAudit);
                      if (fas != null && fas.size() > 0) {
                          //已维护经济技术评审数据
                          packages2.setIsEditSecond(2);
                      } else {
                          packages2.setIsEditSecond(1);
                      }
                    }
                    if ("OPEN_ZHPFF".equals(methodCode)) {
                      //综合评分法 
                      packages2.setIsEditSecond(1);
                    }
                }
            } else {
                packages2.setIsEditSecond(0);
            }
            if (bmList != null && bmList.size() > 0) {
                packages2.setIsHaveScoreMethod(1);
                packages2.setBidMethodTypeName(bmList.get(0).getTypeName());
            } else {
                packages2.setIsHaveScoreMethod(2);
            }
        }
        List<DictionaryData> ddList = DictionaryDataUtil.find(27);
        
      //查看是否环节结束，结束只能查看
        FlowExecute flowExecute = new FlowExecute();
        flowExecute.setFlowDefineId(flowDefineId);
        flowExecute.setProjectId(project.getId());
        List<FlowExecute> executes = flowMangeService.findFlowExecute(flowExecute);
        if(executes != null && executes.size() > 0){
            for (FlowExecute flowExecute2 : executes) {
                if(flowExecute2.getStatus() == 3){
                    project.setConfirmFile(1);
                    break;
                }
            }
        }
        model.addAttribute("project", project);
        model.addAttribute("ddList", ddList);
		model.addAttribute("packagesList", packagesList);
		model.addAttribute("projectId", packages.getProjectId());
		model.addAttribute("flowDefineId", flowDefineId);
		model.addAttribute("msg", msg);
		return "bss/ppms/open_bidding/scoring_rubric";
	}
	
	/**
	 *〈简述〉查看
	 *〈详细描述〉
	 * @author Ye MaoLin
	 * @param packages 分包对象
	 * @param model
	 * @param request
	 * @param flowDefineId 流程定义Id
	 * @return
	 */
	@RequestMapping("packageListView")
    public String packageListView(@ModelAttribute Packages packages,Model model,HttpServletRequest request,String flowDefineId){
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("projectId", packages.getProjectId());
        Project project = projectService.selectById(packages.getProjectId());
        model.addAttribute("project", project);
        List<Packages> packagesList = packageService.findPackageAndBidMethodById(map);
        model.addAttribute("packagesList", packagesList);
        model.addAttribute("projectId", packages.getProjectId());
        model.addAttribute("flowDefineId", flowDefineId);
        model.addAttribute("ope", "view");
        return "bss/ppms/open_bidding/scoring_rubric";
    }
	/**
	 * 
	 * @Title: list
	 * @author: Tian Kunfeng
	 * @date: 2016-10-17 上午11:24:25
	 * @Description: 八大模型列表页
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping("list")
	public String list(@ModelAttribute Packages packages,Model model,HttpServletRequest request){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", packages.getId());
		Packages packages2 = (packageService.findPackageAndBidMethodById(map)!=null && packageService.findPackageAndBidMethodById(map).size()>0)?packageService.findPackageAndBidMethodById(map).get(0):new Packages();
		model.addAttribute("packageId", packages.getId());
		model.addAttribute("bidMethodId", packages2.getBidMethodId());
		Project project = projectService.selectById(packages.getProjectId());
		model.addAttribute("projectId", request.getParameter("projectId"));
		model.addAttribute("project", project);
		BidMethod bidMethod = new BidMethod();
		if(packages2.getBidMethodId()!=null && !packages2.getBidMethodId().equals("")){
			bidMethod.setId(packages2.getBidMethodId());
		    bidMethod = (bidMethodService.findListByBidMethod(bidMethod)!=null && bidMethodService.findListByBidMethod(bidMethod).size()>0)?bidMethodService.findListByBidMethod(bidMethod).get(0):bidMethod;
		}
	    model.addAttribute("bidMethod", bidMethod);
		return "bss/ppms/open_bidding/scoring_standard";
	}
	
	@RequestMapping("operatorScoreModel")
	public String operatorScoreModel(@ModelAttribute ScoreModel scoreModel,HttpServletRequest request, String judgeModel){
		String[] startParam = request.getParameterValues("pi.startParam");
		String[] endParam = request.getParameterValues("pi.endParam");
		String[] score = request.getParameterValues("pi.score");
		String[] startRelation = request.getParameterValues("pi.startRelation");
        String[] endRelation = request.getParameterValues("pi.endRelation");
		 if (scoreModel.getReviewContent() != null && !"".equals(scoreModel.getReviewContent())) {
	            scoreModel.setReviewContent(scoreModel.getReviewContent().replaceAll("\\s*", ""));
	         }
		if(scoreModel.getId()!=null && !scoreModel.getId().equals("")){
            scoreModelService.updateScoreModel(scoreModel);
			MarkTerm condition = new MarkTerm();
			condition.setId(scoreModel.getMarkTermId());
			List<MarkTerm> mtList = markTermService.findListByMarkTerm(condition);
			if (mtList != null && mtList.size() > 0) {
			    MarkTerm markTerm = mtList.get(0);
			    markTerm.setName(scoreModel.getReviewContent());
			    markTerm.setChecked(scoreModel.getIscheck() + "");
			    markTermService.updateMarkTerm(markTerm);
			}
			HashMap<String, Object> map  = new HashMap<String,Object>();
			map.put("scoreModelId", scoreModel.getId());
			map.put("projectId", scoreModel.getProjectId());
            map.put("packageId", scoreModel.getPackageId());
			paramIntervalService.delParamIntervalByMap(map);
			int len = 0;
			if(startParam!=null){
				len = startParam.length;
			}
			if(startParam!=null && startParam.length>0 && endParam!=null && endParam.length>0 && score!=null && score.length>0){
				for(int i=0;i<len;i++){
					ParamInterval p = new ParamInterval();
					p.setScoreModelId(scoreModel.getId());
					p.setStartParam(startParam[i]);
					p.setStartRelation(startRelation[i]);
					p.setEndParam(endParam[i]);
					p.setEndRelation(endRelation[i]);
					p.setScore(score[i]);
					p.setProjectId(scoreModel.getProjectId());
					p.setPackageId(scoreModel.getPackageId());
					paramIntervalService.saveParamInterval(p);
				}
			}
			
		}else {
			
			MarkTerm mt = new MarkTerm();
			mt.setPid(scoreModel.getMarkTermId());
			mt.setName(scoreModel.getReviewContent());
			mt.setCreatedAt(new Date());
			mt.setPackageId(scoreModel.getPackageId());
			mt.setProjectId(scoreModel.getProjectId());
			mt.setMaxScore("0");
			mt.setChecked(scoreModel.getIscheck() + "");
			//mt.setTypeName();
			markTermService.saveMarkTerm(mt);
			scoreModel.setMarkTermId(mt.getId());
			scoreModelService.saveScoreModel(scoreModel);
			int len = 0;
			if(startParam!=null){
				len = startParam.length;
			}
			if(startParam!=null && startParam.length>0 && endParam!=null && endParam.length>0 && score!=null && score.length>0){
				for(int i=0;i<len;i++){
					ParamInterval p = new ParamInterval();
					p.setScoreModelId(scoreModel.getId());
					p.setStartParam(startParam[i]);
					p.setStartRelation(startRelation[i]);
					p.setEndParam(endParam[i]);
					p.setScore(score[i]);
					p.setEndRelation(endRelation[i]);
					p.setProjectId(scoreModel.getProjectId());
                    p.setPackageId(scoreModel.getPackageId());
					paramIntervalService.saveParamInterval(p);
				}
			}
		}
		return "redirect:editPackageScore.html?projectId="+scoreModel.getProjectId()+"&packageId="+scoreModel.getPackageId();
	}
	
	public String getShowTable(String id, String name ,String projectId, String packageId) {
        TreeMap<MarkTerm, List<MarkTerm>> map = new TreeMap<MarkTerm, List<MarkTerm>>();
        MarkTerm mt = new MarkTerm();
        mt.setTypeName(id);
        mt.setProjectId(projectId);
        mt.setPackageId(packageId);
        //默认顶级节点为0
        mt.setPid("0");
        List<MarkTerm> mtList = markTermService.findListByMarkTerm(mt);
        Integer count3 = 0;
        int judge = 0;
        for (MarkTerm mtKey : mtList) {
            MarkTerm mt1 = new MarkTerm();
            mt1.setPid(mtKey.getId());
            mt1.setProjectId(projectId);
            mt1.setPackageId(packageId);
            List<MarkTerm> mtValue = markTermService.findListByMarkTerm(mt1);
            if (mtValue != null && mtValue.size() == 0){
                count3 += 1;
            } else {
                count3 += mtValue.size();
            }
            mtKey.setJudge(judge++);
            map.put(mtKey, mtValue);
        }
        StringBuilder sb = new StringBuilder("");
        Integer count = 0;
        if (mtList != null && mtList.size() >0) {
            for (MarkTerm markKey : map.keySet()) {
                Integer count1 = 0;//游标
                if (count ==0) {
                    if (map.get(markKey) != null && map.get(markKey).size() > 0) {
                        for (MarkTerm markValue : map.get(markKey)) {
                            if(count1 == 0) {
                                sb.append("<tr><td class='w100' rowspan=" + count3 +"><span class='fl'>"+ name +"</span></td>");
                                sb.append("<td class='w150' rowspan="+map.get(markKey).size()+"><span class='fl'>" + markKey.getName() + "</span></td>");
                                String typeName = getTypeName(markValue.getSmtypename());
                                sb.append("<td class='tc w150'>" + markValue.getSmname() + "</td>");
                                sb.append("<td class='tc'>" + typeName + "</td>");
                                Double sscore = markValue.getScscore() ;
                                if (sscore == null){
                                    sscore = 0.0;
                                }
                                sb.append("<td>"+ markValue.getName());
                                sb.append("</td><td>"+sscore+"</td></tr>");
                            } else {
                                String typeName = getTypeName(markValue.getSmtypename());
                                sb.append("<tr><td class='tc w150'>" + markValue.getSmname() + "</td><td class='tc'><span>" + typeName + "</span></td>");
                                Double sscore = markValue.getScscore();
                                if (sscore == null){
                                    sscore = 0.0;
                                }
                                sb.append("<td>"+ markValue.getName());
                                sb.append("</td><td>"+sscore+"</td></tr>");
                            }
                            count1++;
                        }
                    } else {
                        sb.append("<tr><td rowspan=" + count3 +"><span class='fl'>"+ name +"</span></td>");
                        sb.append("<td class='w150'><span class='fl'>" + markKey.getName() + "</span>");
                        sb.append("</td><td></td><td></td><td></td><td></td></tr>");
                    }
                } else {
                    Integer count2 = 0;
                    if (map.get(markKey) != null && map.get(markKey).size() > 0) {
                        for (MarkTerm markValue : map.get(markKey)) {
                            if (count2 ==0) {
                                sb.append("<tr><td rowspan="+map.get(markKey).size()+">");
                                sb.append("<span class='fl'>" + markKey.getName() + "</span></td>");
                                String typeName = getTypeName(markValue.getSmtypename());
                                sb.append("<td class='tc w150'>" + markValue.getSmname() + "</td><td class='tc'>" + typeName + "</td>");
                                Double sscore = markValue.getScscore();
                                if (sscore == null){
                                    sscore = 0.0;
                                }
                                sb.append("<td>"+ markValue.getName());
                                sb.append("</td><td>"+sscore+"</td></tr>");
                                
                            } else {
                                String typeName = getTypeName(markValue.getSmtypename());
                                sb.append("<tr><td class='tc w150'>" + markValue.getSmname() + "</td><td class='tc'>" + typeName + "</td>");
                                Double sscore = markValue.getScscore();
                                if (sscore == null){
                                    sscore = 0.0;
                                }
                                sb.append("<td>"+ markValue.getName());
                                sb.append("</td><td>"+sscore+"</td></tr>");
                            }
                            count2++;
                        }
                    } else {
                        sb.append("<tr><td>");
                        sb.append("<span class='fl'>" + markKey.getName() + "</span>");
                        sb.append("</td><td></td><td></td><td></td><td></td></tr>");
                    }
                }
                count++;
            }
        } else {
            sb.append("<tr><td><span class='fl'>"+ name +"</span></td>");
            sb.append("<td></td><td></td><td></td><td></td><td></td></tr>");
        }
        String str = sb.toString();
        return str;
    }
	/**
	 * 
	 * @Title: add
	 * @author: Tian Kunfeng
	 * @date: 2016-10-17 上午11:24:20
	 * @Description: 新增页面
	 * @param: @param request
	 * @param: @return
	 * @return: String
	 */
	public String add(HttpServletRequest request){
		return "";
	}
	/**
	 * 
	 * @Title: create
	 * @author: Tian Kunfeng
	 * @date: 2016-10-17 上午11:24:15
	 * @Description: 保存
	 * @param: @return
	 * @return: String
	 */
	public String create(){
		return "redirect:list.do";
	}
	/**
	 * 
	 * @Title: edit
	 * @author: Tian Kunfeng
	 * @date: 2016-10-17 上午11:23:56
	 * @Description: 跳转编辑页面
	 * @param: @param request
	 * @param: @param id
	 * @param: @param model
	 * @param: @param scoreTemplate
	 * @param: @return
	 * @return: String
	 */
	public String edit(HttpServletRequest request,String id,Model model,@ModelAttribute ScoreModel scoreTemplate){
		model.addAttribute("scoreTemplate", scoreTemplate);
		return "";
	}
	/**
	 * 
	 * @Title: update
	 * @author: Tian Kunfeng
	 * @date: 2016-10-17 上午11:23:53
	 * @Description: 更新
	 * @param: @param request
	 * @param: @param id
	 * @param: @param model
	 * @param: @param scoreTemplate
	 * @param: @return
	 * @return: String
	 */
	public String update(HttpServletRequest request,String id,Model model,@ModelAttribute ScoreModel scoreTemplate){
		return "redirect:list.do";
	}
	
	/**
	 * 
	 * @Title: del
	 * @author: Tian Kunfeng
	 * @date: 2016-10-17 上午11:23:21
	 * @Description: 物理删除
	 * @param: @param ids
	 * @param: @return
	 * @return: AjaxJsonData
	 */
	public AjaxJsonData del(String ids){
		return ajaxJsonData;
	}
	/**
	 * 
	 * @Title: delSoft
	 * @author: Tian Kunfeng
	 * @date: 2016-10-17 上午11:23:41
	 * @Description: 软删除
	 * @param: @return
	 * @return: AjaxJsonData
	 */
	public AjaxJsonData delSoft(){
		return ajaxJsonData;
	}
	/**
	 * 
	 * @Title: getMarkTermTree
	 * @author: Tian Kunfeng
	 * @date: 2016-10-19 下午7:55:54
	 * @Description: 获取默认打分项
	 * @param: @param request
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "getMarkTermTree",produces={"application/json;charset=UTF-8"})
	@ResponseBody    
	public String getMarkTermTree(HttpServletRequest request){
		String packageId = request.getParameter("packageId");
		String bidMethodId = request.getParameter("bidMethodId");
		request.getSession().setAttribute("bidMethodId", bidMethodId);
		String projectId = request.getParameter("projectId");
		String id = request.getParameter("id");
		HashMap<String, Object> map = new HashMap<String, Object>();
		/*map.put("id", packageId);
		List<Packages> pList = packageService.findPackageById(map);
		Packages packages = new Packages();
		if(pList!=null && pList.size()>0){
			 packages = pList.get(0);
		}*/
		MarkTerm markTerm = new MarkTerm();
		//markTerm.setIsRoot("true");
		markTerm.setPackageId(packageId);
		if(id!=null && !id.equals("")){
			markTerm.setPid(id);
		}else {
			markTerm.setBidMethodId(bidMethodId);
		}
		List<MarkTerm> markTermList = markTermService.findListByMarkTerm(markTerm);
		List<Ztree> treeList = new ArrayList<Ztree>();  
		if(markTermList!=null && markTermList.size()>0){
			for(MarkTerm m : markTermList){
				Ztree z = new Ztree();
				z.setId(m.getId());
				String span = "<span style='color:blue'>"+"("+m.getMaxScore()+")"+"</span>";
				z.setName(m.getName()+m.getMaxScore());
				z.setpId(m.getPid() == null ? "-1":m.getPid());
				z.setCreatedAt(m.getCreatedAt());
				z.setBidMethodId(m.getBidMethodId());
				MarkTerm childMarkTerm = new MarkTerm();
				childMarkTerm.setPid(m.getId());
				List<MarkTerm> chiildList = markTermService.findListByMarkTerm(childMarkTerm);
				if(chiildList != null && chiildList.size() > 0){
					z.setIsParent("true");
				} else {
					z.setIsParent("false");
				}
				treeList.add(z);
			}
		}else {
			Project project = projectService.selectById(projectId);
			Ztree z = new Ztree();
			z.setName(project.getName()+"_评分细则");
			z.setpId("-1");
			z.setIsParent("false");
			z.setBidMethodId("");
			treeList.add(z);
		}
		JSONArray jObject = JSONArray.fromObject(treeList);
		return jObject.toString();
	
	}
	/**
	 * 
	 * @Title: operatorNode
	 * @author: Tian Kunfeng
	 * @date: 2016-10-20 下午1:40:12
	 * @Description: 操作节点
	 * @param: @param markTerm
	 * @param: @param request
	 * @param: @return
	 * @return: AjaxJsonData
	 */
	@RequestMapping("operatorNode")
	@ResponseBody
	public AjaxJsonData operatorNode(@ModelAttribute MarkTerm markTerm,HttpServletRequest request){
		String method = request.getParameter("method");
		String packageId = request.getParameter("packageId");
		if(method!=null && !method.equals("") && method.equals("addnode")){
			markTermService.saveMarkTerm(markTerm);
			ajaxJsonData.setSuccess(true);
			ajaxJsonData.setMessage(markTerm.getId());
			/*MarkTerm m = new MarkTerm() ;
			m.setId(markTerm.getPid());
			m = markTermService.findListByMarkTerm(m).get(0) ;
			TreeNode treeNode = new TreeNode();
			treeNode.setpTreeNode(markTerm);
			treeNode.setpTreeNode(m);
			ajaxJsonData.setObj(treeNode);*/
		}else if (method.equals("updatenode")) {
			markTermService.updateMarkTerm(markTerm);
		}else if(method.equals("delnode")){
			HashMap<String, Object> map = new HashMap<String,Object>();
			MarkTerm m = new MarkTerm();
			m.setId(markTerm.getId());
			markTerm = markTermService.findListByMarkTerm(m).get(0);
			if(markTerm.getBidMethodId()!=null && !markTerm.getBidMethodId().equals("")){
				//说明删除根节点   1级联删除    2删除bidmethod  3清空package
				map.put("id", markTerm.getId());
				markTermService.delMarkTermByid(map);
				map.clear();
				map.put("id", markTerm.getBidMethodId());
				bidMethodService.delBidMethodByMap(map);
				map.clear();
				Packages pack = new Packages();
				pack.setId(markTerm.getPackageId());
				pack.setBidMethodId("");
				packageService.updateByPrimaryKeySelective(pack);
			}
			map.put("id", markTerm.getId());
			//获取id跟新
			String id=(String)request.getSession().getAttribute("bidMethodId");
			BidMethod bm1 = new BidMethod();
			bm1.setId(id);
			BidMethod bm2 = bidMethodService.findListByBidMethod(bm1).get(0);
			bm2.setRemainScore(String.valueOf(Double.parseDouble(bm2.getRemainScore())+Double.parseDouble(markTerm.getRemainScore())));
			bidMethodService.updateBidMethod(bm2);
			markTermService.delMarkTermByMap(map);
			//给删掉的分数 加到markterm里面
            MarkTerm m1 = new MarkTerm();
            m1.setId(markTerm.getPid());
            MarkTerm markTerm1 = markTermService.findListByMarkTerm(m1).get(0);
            markTerm1.setRemainScore(String.valueOf(Double.parseDouble(markTerm1.getRemainScore())+Double.parseDouble(markTerm.getRemainScore())));
            markTermService.updateMarkTerm(markTerm1);
			updateScoreModelName(packageId,method,markTerm);
			request.getSession().removeAttribute("bidMethodId");
		}
		/*//增删改操作后  更新项目分包信息里面绑定的评分项树字符串   下次调用直接解析次字符串
		setPackageMarkTermTree(packageId,method,markTerm);
		
		//如果已经建立模型  更细打分项  关联更新模型表里面的name 
		updateScoreModelName(packageId,method,markTerm);*/
		return ajaxJsonData;
	}
	/**
	 * 
	 * @Title: gettreebody
	 * @author: Tian Kunfeng
	 * @date: 2016-10-20 下午1:40:44
	 * @Description: 右侧嵌套区   内涵八大模型表单
	 * @param: @param orgnization
	 * @param: @param model
	 * @param: @return
	 * @return: String
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("gettreebody")
	public String gettreebody(@ModelAttribute MarkTerm markTerm,Model model,HttpServletRequest request ,String addStatus) throws UnsupportedEncodingException {
		String packageId = request.getParameter("packageId");
		ScoreModel scoreModel = new ScoreModel();
		scoreModel.setName(URLDecoder.decode(markTerm.getName(), "UTF-8"));
		scoreModel.setMarkTermId(markTerm.getId()==null?"":markTerm.getId());
		List<ScoreModel> scoreModelList = scoreModelService.findListByScoreModel(scoreModel);
		if (scoreModelList != null && scoreModelList.size()==1) {
		    ParamInterval pi = new ParamInterval();
		    pi.setScoreModelId(scoreModelList.get(0).getId());
            pi.setProjectId(scoreModelList.get(0).getProjectId());
            pi.setPackageId(scoreModelList.get(0).getPackageId());
		    List<ParamInterval> piList = paramIntervalService.findListByParamInterval(pi);
		    StringBuilder sb = new StringBuilder("");
		    Integer count = 0;
		    for (ParamInterval paramInterval : piList) {
		      if (paramInterval.getScore() != null && paramInterval.getScore().startsWith(".")) {
            paramInterval.setScore(paramInterval.getScore().replace(".", "0."));
          }
          if (paramInterval.getStartParam() != null && paramInterval.getStartParam().startsWith(".")) {
            paramInterval.setStartParam(paramInterval.getStartParam().replace(".", "0."));
          }
          if (paramInterval.getEndParam() != null && paramInterval.getEndParam().startsWith(".")) {
            paramInterval.setEndParam(paramInterval.getEndParam().replace(".", "0."));
          }
		        count++;
                String startParam = paramInterval.getStartParam() == null ? "" : paramInterval.getStartParam();
                sb.append("<tr><td class=tc>" + count + "</td><td class=tc><input style='width:60px' onblur='checkNum()' type='text' value='" + startParam + "'name='pi.startParam'>");
                if ("0".equals(paramInterval.getStartRelation())) {
                    sb.append("</td><td class=tc><select onchange='checkNum()' name='pi.startRelation'><option value='0' selected='selected'><</option><option value='1'><=</option></select></td><td class='tc'>参数值</td>");
                } else {
                    sb.append("</td><td class=tc><select onchange='checkNum()' name='pi.startRelation'><option value='0'><</option><option value='1' selected='selected'><=</option></select></td><td class='tc'>参数值</td>");
                }
                if ("0".equals(paramInterval.getEndRelation())) {
                    sb.append("<td class=tc><select onchange='checkNum()' name='pi.endRelation'><option value='0'  selected='selected' ><</option><option value='1'><=</option></select></td>");
                } else {
                    sb.append("<td class=tc><select onchange='checkNum()' name='pi.endRelation'><option value='0' ><</option><option value='1'  selected='selected'><=</option></select></td>");
                }
                String endParam = paramInterval.getEndParam() == null ? "" : paramInterval.getEndParam();
                sb.append("<td class=tc><input style='width:60px' onblur='checkNum()' type='text' value='" + endParam + "'name='pi.endParam'></td>");
                String score = paramInterval.getScore() == null ? "" : paramInterval.getScore();
                sb.append("<td class=tc><input style='width:60px' onblur='checkNum()' type='text' value='" + score + "'name='pi.score'></td>");
                sb.append("<td></td>");
                sb.append("<td class=tc><a href=javascript:void(0); onclick=delTr(this)>删除</a></td></tr>");
            }
		    String scoreStr = sb.toString();
		    model.addAttribute("scoreStr", scoreStr);
        }
		model.addAttribute("scoreModelList", scoreModelList);
		model.addAttribute("packageId", packageId);
		model.addAttribute("markTermId", markTerm.getId());
		String markTermName ="";
		/*if(markTerm.getName()!=null && !markTerm.getName().equals("")){
			markTermName = URLDecoder.decode(markTerm.getName(), "UTF-8");
		}*/
		model.addAttribute("markTermName",markTermName );
		if(scoreModelList!=null && scoreModelList.size()>0){
		    if (scoreModelList.get(0).getJudgeContent() != null && !"".equals(scoreModelList.get(0).getJudgeContent())) {
                List<String> list = Arrays.asList(scoreModelList.get(0).getJudgeContent().split("\\|"));
                scoreModelList.get(0).setModel1BJudgeContent(list);
            }
		    if (scoreModelList.get(0).getUnitScore() != null && scoreModelList.get(0).getUnitScore().startsWith(".")) {
            scoreModelList.get(0).setUnitScore(scoreModelList.get(0).getUnitScore().replace(".", "0."));
        }
        if (scoreModelList.get(0).getMinScore() != null && scoreModelList.get(0).getMinScore().startsWith(".")) {
            scoreModelList.get(0).setMinScore(scoreModelList.get(0).getMinScore().replace(".", "0."));
        }
        if (scoreModelList.get(0).getMaxScore() != null && scoreModelList.get(0).getMaxScore().startsWith(".")) {
            scoreModelList.get(0).setMaxScore(scoreModelList.get(0).getMaxScore().replace(".", "0."));
        }
        if (scoreModelList.get(0).getStandardScore() != null && scoreModelList.get(0).getStandardScore().startsWith(".")) {
            scoreModelList.get(0).setStandardScore(scoreModelList.get(0).getStandardScore().replace(".", "0."));
        }
        if (scoreModelList.get(0).getIntervalNumber() != null && scoreModelList.get(0).getIntervalNumber().startsWith(".")) {
            scoreModelList.get(0).setIntervalNumber(scoreModelList.get(0).getIntervalNumber().replace(".", "0."));
        }
		    scoreModelList.get(0).setIscheck(scoreModelList.get(0).getMarkTerm().isChecked());
			model.addAttribute("scoreModel", scoreModelList.get(0));
		}
		model.addAttribute("projectId", markTerm.getProjectId());
		model.addAttribute("addStatus", addStatus);
		return "bss/ppms/open_bidding/treebody";
	}
	@RequestMapping("quantizateScore")
	@ResponseBody
	public String quantizateScore(@ModelAttribute Packages packages,HttpServletRequest request){
		
		return "";
	}
	
	/**
	 * @Title: packageListCn
	 * @author Song Biaowei
	 * @date 2016-10-25 下午7:57:09  
	 * @Description: TODO 
	 * @param @param packages
	 * @param @param model
	 * @param @param request
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("packageList_cn")
	public String packageListCn(@ModelAttribute Packages packages,Model model,HttpServletRequest request){
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("projectId", packages.getProjectId());
		List<Packages> packagesList = packageService.findPackageById(map);
		model.addAttribute("packagesList", packagesList);
		model.addAttribute("projectId", packages.getProjectId());
		return "bss/ppms/competitive_negotiation/scoring_rubric";
	}
	/**
	 * 
	 * @Title: operatorBidMethod
	 * @author: Tian Kunfeng
	 * @date: 2016-11-1 下午7:11:32
	 * @Description: 操作评标办法
	 * @param: @param bidMethod
	 * @param: @param request
	 * @param: @return
	 * @return: AjaxJsonData
	 */
	@RequestMapping("operatorBidMethod")
	@ResponseBody
	public AjaxJsonData operatorBidMethod(@ModelAttribute BidMethod bidMethod,HttpServletRequest request){
		String method = request.getParameter("method");
		if(bidMethod.getId()!=null && !bidMethod.getId().equals("")){
			bidMethodService.updateBidMethod(bidMethod);
			ajaxJsonData.setSuccess(true);
			ajaxJsonData.setMessage(bidMethod.getMaxScore());
		}else {
			bidMethodService.saveBidMethod(bidMethod);
			ajaxJsonData.setSuccess(true);
			ajaxJsonData.setMessage(bidMethod.getMaxScore());
		}
		if (method!=null &&! method.equals("") && method.equals("del")) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", bidMethod.getId());
			bidMethodService.delBidMethodByid(map);
		}
		return ajaxJsonData;
	}
	/**
	 * 
	 * @Title: getBidMethodById
	 * @author: Tian Kunfeng
	 * @date: 2016-11-2 下午2:45:32
	 * @Description: 唯一查询评标办法
	 * @param: @param bidMethod
	 * @param: @param request
	 * @param: @return
	 * @return: AjaxJsonData
	 */
	@RequestMapping("getBidMethodById")
	@ResponseBody
	public AjaxJsonData getBidMethodById(@ModelAttribute BidMethod bidMethod,HttpServletRequest request){
		bidMethod = (bidMethodService.findListByBidMethod(bidMethod)!=null && bidMethodService.findListByBidMethod(bidMethod).size()>0)?bidMethodService.findListByBidMethod(bidMethod).get(0):bidMethod;
		ajaxJsonData.setSuccess(true);
		ajaxJsonData.setObj(bidMethod);
		ajaxJsonData.setMessage("");
		return ajaxJsonData;
	}
	/**
	 * 
	 * @Title: operatorNode
	 * @author: Tian Kunfeng
	 * @date: 2016-11-1 下午7:12:30
	 * @Description: 操作节点页面
	 * @param: @param packages
	 * @param: @param model
	 * @param: @param request
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping("addNode")
	public String addNode(@ModelAttribute MarkTerm markTerm,Model model,HttpServletRequest request){
		//HashMap<String,Object> map = new HashMap<String,Object>();
		String method = request.getParameter("method");
		String pid = request.getParameter("pid");
		String packageId = request.getParameter("packageId");
		String projectId = request.getParameter("projectId");
		//String remainScore = request.getParameter("remainScore");
		String bidMethodId = request.getParameter("bidMethodId");
		
		if(markTerm.getId()!=null && !markTerm.getId().equals("")){
			markTerm = markTermService.findListByMarkTerm(markTerm).get(0);
		}
		if(markTerm.getPid()!=null && !markTerm.getPid().equals("")){
			MarkTerm m = new MarkTerm();
			m.setId(markTerm.getPid());
			m = markTermService.findListByMarkTerm(m).get(0);
			model.addAttribute("remainScore", m.getRemainScore());
		}else {
			model.addAttribute("remainScore", markTerm.getRemainScore());
		}
		model.addAttribute("markTerm", markTerm);
		model.addAttribute("method", method);
		model.addAttribute("pid", pid);
		model.addAttribute("packageId", packageId);
		model.addAttribute("projectId", projectId);
		model.addAttribute("bidMethodId", bidMethodId);
		return "bss/ppms/open_bidding/operator_node";
	}
	/**
	 * 
	 *〈简述〉
	 *〈详细描述〉
	 * @author tiankf
	 * @param model
	 * @param scoreModel
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("scoreModelList")
    public String scoreModelList(Model model,@ModelAttribute ScoreModel scoreModel,Integer page,HttpServletRequest request){
        //每页显示十条
        PageHelper.startPage(page == null ? 1 : page,CommonConstant.PAGE_SIZE);
        HashMap<String, Object> map = new HashMap<String, Object>();
        List<ScoreModel> scoreModelList = scoreModelService.findListByScoreModel(scoreModel);
        model.addAttribute("scoreModelList",scoreModelList);

        //分页标签
        model.addAttribute("list",new PageInfo<ScoreModel>(scoreModelList));
        model.addAttribute("scoreModel", scoreModel);
        model.addAttribute("projectId", request.getParameter("proid"));
        return "bss/ppms/open_bidding/show_score_model";
    }
	@RequestMapping("showScoreModel")
	public String showScoreModel(Model model,@ModelAttribute ScoreModel scoreModel,HttpServletRequest request){
	    scoreModel = scoreModelService.findScoreModelByScoreModel(scoreModel);
	    model.addAttribute("scoreModel", scoreModel);
	    return "bss/ppms/open_bidding/show_treebody";
	}
	//-----------------------------------方法封装-------------------------------------------------------------------------------
	public void setPackageMarkTermTree(String packageId,String method,MarkTerm markTerm){
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("id", packageId);
		Packages p = packageService.findPackageById(map).get(0);
		//判断当前package 是否填写过打分树    addnode updatenode   delnode
		if(p.getMarkTermTree()!=null &&! p.getMarkTermTree().equals("")){
			JSONArray array = JSONArray.fromObject(p.getMarkTermTree());
			List<Ztree> list = JSONArray.toList(array, Ztree.class);
			if(method.equals("addnode")){
				if(list!=null && list.size()>0){
					for(Ztree z:list){
						MarkTerm m = new MarkTerm();
						if(z.getpId()!=null &&! z.getpId().equals("")&&!z.getpId().equals("-1")){
							m.setId(z.getId());
							m.setPid(z.getpId());
							m.setName(z.getName());
							m.setCreatedAt(z.getCreatedAt());
							markTermService.insert(m);
						}else {
							continue;
						}
					}
				}
			}else if (method.equals("updatenode")) {
				for(Ztree z:list){
					MarkTerm m = new MarkTerm();
					if(z.getpId()!=null &&! z.getpId().equals("")&&!z.getpId().equals("-1")){
						if(z.getId().equals(markTerm.getId())){
							m.setId(z.getId());
							m.setPid(z.getpId());
							m.setName(markTerm.getName());
						}else {
							m.setId(z.getId());
							m.setPid(z.getpId());
							m.setName(z.getName());
						}
						m.setCreatedAt(z.getCreatedAt());
						markTermService.insert(m);
					}else {
						continue;
					}
				}
			}else if (method.equals("delnode")) {
				for(Ztree z:list){
					MarkTerm m = new MarkTerm();
					if(z.getpId()!=null &&! z.getpId().equals("")&&!z.getpId().equals("-1")){
						if(z.getId().equals(markTerm.getId())){
							continue;
						}else {
							m.setId(z.getId());
							m.setPid(z.getpId());
							m.setName(z.getName());
							m.setCreatedAt(z.getCreatedAt());
							markTermService.insert(m);
						}
					}else {
						continue;
					}
				}
			}
			
		}
		MarkTerm m = new MarkTerm();
		List<MarkTerm> markTermList = markTermService.findListByMarkTerm(m);
		p.setMarkTermTree(getMarkTermTreeStr(markTermList));
		packageService.updateByPrimaryKeySelective(p);
		map.clear();
		map.put("isRoot", "true");
		markTermService.delMarkTermByMap(map);
		
	}
	public void updateScoreModelName(String packageId,String method,MarkTerm markTerm){
		if(method.equals("addnode")){
			//nothing to do
		}else if (method.equals("updatenode")) {
			ScoreModel scoreModel = new ScoreModel();
			scoreModel.setName(markTerm.getName());
			scoreModel.setMarkTermId(markTerm.getId());
			scoreModelService.updateScoreModel(scoreModel);
			
		}else if (method.equals("delnode")) {
			HashMap<String, Object> map = new HashMap<String,Object>();
			map.put("markTermId", markTerm.getId());
			//del
			scoreModelService.delScoreModelByMap(map);
		}
	}
	public String getMarkTermTreeStr(List<MarkTerm> markTermList){
		List<Ztree> treeList = new ArrayList<Ztree>(); 
		if(markTermList!=null && markTermList.size()>0){
			for(MarkTerm m : markTermList){
				Ztree z = new Ztree();
				z.setId(m.getId());
				z.setName(m.getName());
				z.setpId(m.getPid() == null ? "-1":m.getPid());
				MarkTerm childMarkTerm = new MarkTerm();
				childMarkTerm.setPid(m.getId());
				List<MarkTerm> chiildList = markTermService.findListByMarkTerm(childMarkTerm);
				if(chiildList != null && chiildList.size() > 0){
					z.setIsParent("true");
				} else {
					z.setIsParent("false");
				}
				treeList.add(z);
			}
			JSONArray jObject = JSONArray.fromObject(treeList);
			return jObject.toString();
		}else {
			
			return "";
		}
		
	}
	//根据模型计算得分
	/**
	 * 
	 * @Title: getQuantizateScore
	 * @author: Tian Kunfeng
	 * @date: 2016-11-11 下午4:23:01
	 * @Description: 模型3-----模型6   直接调用对应方法   返回带有得分的集合  另行处理
	 * @param: @param scoreModel
	 * @param: @param number
	 * @param: @param flag
	 * @param: @return
	 * @return: double
	 */
	public double getQuantizateScore(ScoreModel scoreModel,Integer number,Integer flag){
		double score = 0 ;
		if(scoreModel.getTypeName()!=null && !scoreModel.getTypeName().equals("") && scoreModel.getTypeName().equals("0")){
		    score = getScoreByModelOne(scoreModel,flag);
		}else if (scoreModel.getTypeName().equals("1")) {
			score = getScoreByModelTwo(scoreModel,number);
		}else if (scoreModel.getTypeName().equals("2")){
			score = getScoreByModelTwo(scoreModel, number);
		}else if (scoreModel.getTypeName().equals("7")){
			score  = getScoreByModelSeven(scoreModel, number);
		}else if (scoreModel.getTypeName().equals("8")){
			score  = getScoreByModelEight(scoreModel, number);
		}
		return score;
	}
	/**
	 * 
	 * @Title: getQuantizateScore
	 * @author: Tian Kunfeng
	 * @date: 2016-11-7 下午5:09:27
	 * @Description: 是否判断类型
	 * @param: @param scoreModel
	 * @param: @param 
	 * @param: @param flag 0 不满足条件  0分   1 满足条件  的标准分值
	 * @param: @return
	 * @return: int
	 */
	public double getScoreByModelOne(ScoreModel scoreModel,Integer flag){
		double sc = 0 ;
		if(flag!=null && flag.equals(1)){
			sc = FloatUtil.round(Double.parseDouble(scoreModel.getStandardScore()), 4);
		}
		return sc;
	}
	/**
	 * 
	 * @Title: getScoreByModelTwo
	 * @author: Tian Kunfeng
	 * @date: 2016-11-8 下午3:28:13
	 * @Description: 模型2  按项加减分        计算公式   
	 * 0    加分类型    分值= 起始分值 + 满足项树 * 每项得分      最高不超过最高分    最低不低于最低分   
	 * 1 减分类型        分值  = 基准分值 -  满足项树 * 每项得分      最高不超过最高分    最低不低于最低分    
	 * @param: @param scoreModel 模型
	 * @param: @param number  满足项树
	 * @param: @return
	 * @return: double
	 */
	public double getScoreByModelTwo(ScoreModel scoreModel,Integer number){
		double sc = 0 ;
		double reviewStandScore = (scoreModel.getReviewStandScore()!=null && !scoreModel.getReviewStandScore().equals("") )? Double.parseDouble(scoreModel.getReviewStandScore()):0;
		double unitScore = Double.parseDouble(scoreModel.getUnitScore());
		//int maxScore  0加分      1减分
		if( scoreModel.getAddSubtractTypeName()!=null && scoreModel.getAddSubtractTypeName().equals("0")){
			if(number!=null && isNumber(number+"")){
				double score = FloatUtil.add(reviewStandScore, FloatUtil.mul(Double.parseDouble(number+""), unitScore)) ;
				score = getDeadlineScore(score, Double.parseDouble(scoreModel.getMaxScore()), 0);
			}
			
		}else if (scoreModel.getAddSubtractTypeName().equals("1")) {
			double score = FloatUtil.sub(reviewStandScore, FloatUtil.mul(Double.parseDouble(number+""), unitScore)) ;
			score = getDeadlineScore(score, Double.parseDouble(scoreModel.getMinScore()), 1);
		}
		return sc;
	}
	/**
	 * 
	 * @Title: getScoreByModelThree
	 * @author: Tian Kunfeng
	 * @date: 2016-11-8 下午3:28:55
	 * @Description: 模型三    评审数额最高递减 计算公式   
	 *              减分类型：    评审参数最高的最高分  依次递减每单位得分     最低不能低于最低分
	 * @param: @param scoreModel
	 * @param: @param number
	 * @param: @return
	 * @return: List<SupplyMark>
	 */
	public List<SupplyMark> getScoreByModelThree(ScoreModel scoreModel,ArrayList<SupplyMark> supplyMarkList){
		if(supplyMarkList!=null && supplyMarkList.size()>0){
			Collections.sort(supplyMarkList, new SortByParam());
			
			double maxScore = (scoreModel.getMaxScore()!=null &&!scoreModel.getMaxScore().equals("") ) ?Double.parseDouble(scoreModel.getMaxScore()) :0;
			maxScore = FloatUtil.round(maxScore, 4);
			int unit = 1;
			for(int i=0 ;i<supplyMarkList.size();i++){
				if(i==0){
					supplyMarkList.get(i).setScore(maxScore);
				}else {
					if(new Double(supplyMarkList.get(i).getPrarm()).compareTo(new Double(supplyMarkList.get(i-1).getPrarm())) ==0){
						supplyMarkList.get(i).setScore(supplyMarkList.get(i-1).getScore());
					}else {
						double subScore = FloatUtil.mul(Double.parseDouble(scoreModel.getUnitScore()), unit);
						//不能低于最低分     两个不等的得分始终差一个步长 
						double sc = getDeadlineScore(FloatUtil.sub(supplyMarkList.get(i-1).getScore(), subScore),Double.parseDouble(scoreModel.getMinScore()), 1);
						sc = FloatUtil.round(sc, 4);
						supplyMarkList.get(i).setScore(sc);
						//unit++;
					}
					//supplyMarkList.get(i).setScore(getDeadlineScore(FloatUtil.sub(maxScore, subScore), Double.parseDouble(scoreModel.getMinScore()), 1));
				}
			}
			return supplyMarkList;
		}
		return null;
		
	}
	/**
	 * 
	 * @Title: getScoreByModelThree
	 * @author: Tian Kunfeng
	 * @date: 2016-11-8 下午3:28:55
	 * @Description: 模型4    评审数额最低递增计算公式   
	 *              加分类型：    评审参数最高的最低分  依次递加每单位得分     最高不高于最高分
	 *              降序排列
	 *              排名相同  得分一样
	 * @param: @param scoreModel
	 * @param: @param number
	 * @param: @return
	 * @return: List<SupplyMark>
	 */
	@SuppressWarnings("unchecked")
	public List<SupplyMark> getScoreByModelFour(ScoreModel scoreModel,ArrayList<SupplyMark> supplyMarkList){
		if(supplyMarkList!=null && supplyMarkList.size()>0){
			Collections.sort(supplyMarkList, new SortByParam());
			double minScore = ( scoreModel.getMinScore()!=null && !scoreModel.getMinScore().equals("") ) ?Double.parseDouble(scoreModel.getMinScore()) :0;
			minScore = FloatUtil.round(minScore, 4);
			int unit = 1;
			for(int i=0 ;i<supplyMarkList.size();i++){
				if(i==0){
					supplyMarkList.get(i).setScore(minScore);
				}else {
					if(new Double(supplyMarkList.get(i).getPrarm()).compareTo(new Double(supplyMarkList.get(i-1).getPrarm())) ==0){
						supplyMarkList.get(i).setScore(supplyMarkList.get(i-1).getScore());
					}else {
						double addScore = FloatUtil.mul(Double.parseDouble(scoreModel.getUnitScore()), unit);
						//不能低于最低分     两个不等的得分始终差一个步长 
						double sc = getDeadlineScore(FloatUtil.add(supplyMarkList.get(i-1).getScore(), addScore),Double.parseDouble(scoreModel.getMaxScore()), 0);
						sc = FloatUtil.round(sc, 4);
						supplyMarkList.get(i).setScore(sc);
						//unit++;
					}
				}
				/*if(i==0){
					supplyMarkList.get(i).setScore(Double.parseDouble(scoreModel.getMaxScore()));
				}else {
					double addbScore = FloatUtil.mul(Double.parseDouble(scoreModel.getUnitScore()), i);
					supplyMarkList.get(i).setScore(getDeadlineScore(FloatUtil.add(minScore,addbScore), Double.parseDouble(scoreModel.getMaxScore()), 0));
				}*/
			}
			return supplyMarkList;
		}
		return null;
	}
	/**
	 * 
	 * @Title: getScoreByModelFive
	 * @author: Tian Kunfeng
	 * @date: 2016-11-8 下午7:07:07
	 * @Description: 模型5 评审数额高计算
	 *  计算公式    评审参数最高为基准数    得分= (评审参数/基准数 ) * 标准分值
	 * @param: @param scoreModel
	 * @param: @param supplyMarkList
	 * @param: @param sm   评审参数最高的
	 * @param: @return
	 * @return: List<SupplyMark>
	 */
	@SuppressWarnings("unchecked")
	public List<SupplyMark> getScoreByModelFive(ScoreModel scoreModel,ArrayList<SupplyMark> supplyMarkList,SupplyMark sm){
		double reviewScore = sm.getPrarm();// 基准分值 最高分
		reviewScore = FloatUtil.round(reviewScore, 4);
		int zero = new Double(reviewScore).compareTo(new Double(0));
		if(supplyMarkList!=null && supplyMarkList.size()>0 && zero>0){
			//Collections.sort(supplyMarkList, new SortByParam());//降序排列   第一个为最高分
			
			double standardScore = (scoreModel.getStandardScore()!=null && !scoreModel.getStandardScore().equals(""))? Double.parseDouble(scoreModel.getStandardScore()):0;
			
			for(int i=0 ;i<supplyMarkList.size();i++){
				if(new Double(supplyMarkList.get(i).getPrarm()).compareTo(new Double(reviewScore))==0){
					continue;
				}
				double score = FloatUtil.mul(FloatUtil.div(supplyMarkList.get(i).getPrarm(), reviewScore), standardScore) ;
				score = FloatUtil.round(score, 4);
				supplyMarkList.get(i).setScore(score);
			}
			//return supplyMarkList;
		}
		return supplyMarkList;
	}
	/**
	 * 
	 * @Title: getScoreByModelSix
	 * @author: Tian Kunfeng
	 * @date: 2016-11-8 下午8:11:16
	 * @Description:  模型6  评审数额低计算
	 * 计算公式    评审参数最低为基准数    得分= (基准数/评审参数 ) * 标准分值
	 * @param: @param scoreModel
	 * @param: @param supplyMarkList
	 * @param:  sm   评审参数最低的
	 * @param: @return
	 * @return: List<SupplyMark>
	 */
	@SuppressWarnings("unchecked")
	public List<SupplyMark> getScoreByModelSix(ScoreModel scoreModel,ArrayList<SupplyMark> supplyMarkList,SupplyMark sm){
		double reviewScore = sm.getPrarm();//最低为基准分值
		reviewScore = FloatUtil.round(reviewScore, 4);
		if(supplyMarkList!=null && supplyMarkList.size()>0){
			//Collections.sort(supplyMarkList, new SortByParam());//降序排列   第一个为最高分
			double standardScore = (scoreModel.getStandardScore()!=null && !scoreModel.getStandardScore().equals(""))? Double.parseDouble(scoreModel.getStandardScore()):0;
			for(int i=0 ;i<supplyMarkList.size();i++){
				double score = FloatUtil.mul(FloatUtil.div(supplyMarkList.get(i).getPrarm(), reviewScore), standardScore) ;
				score = FloatUtil.round(score, 4);
				supplyMarkList.get(i).setScore(score);
			}
			
		}
		return supplyMarkList;
	}
	/***
	 * 
	 * @Title: getScoreByModelSeven
	 * @author: Tian Kunfeng
	 * @date: 2016-11-9 上午10:59:21
	 * @Description: 模型7  高计算   左开右闭区间   第一个不包括          基准数值  < 评审截止数
	 * 等额   1 加分区间  区间内取值          低于评审基准数值为最低分0分，区间内  按计算规则递增(]  高于评审截止数   的最高分
	 *     2 减分区间   区间内取值      低于评审基准数的最高分，区间内  等额递减计算    高于评审截止数   得分最低分0分
                                                                       
	 * @param: @param scoreModel
	 * @param: @param number
	 * @param: @return
	 * @return: double
	 */
	public double getScoreByModelSeven(ScoreModel scoreModel,double number){
		double sc =0 ;
		//评审基准数
		double reviewStandScore = (scoreModel.getReviewStandScore()!=null &&! scoreModel.getReviewStandScore().equals(""))?Double.parseDouble(scoreModel.getReviewStandScore()):0;
		//0等额区间    1不等额区间
		String intervalTypeName = scoreModel.getIntervalTypeName();
		//加减分类型  0 加分 1减分
		String addSubtractTypeName = scoreModel.getAddSubtractTypeName();
		//每个区间之间的差额，用于等额区间模型
		double intervalNumber = (scoreModel.getIntervalNumber()!=null && !scoreModel.getIntervalNumber().equals(""))?Double.parseDouble(scoreModel.getIntervalNumber()) : 0 ;
		//加减分分值
		double score = (scoreModel.getScore()!=null &&! scoreModel.equals(""))?Double.parseDouble(scoreModel.getScore()):0;
		//如果加分，高于截止数为满分，如果减分，低于截止数为0分
		double deadlineNumber = (scoreModel.getDeadlineNumber()!=null &&!scoreModel.getDeadlineNumber().equals(""))?Double.parseDouble(scoreModel.getDeadlineNumber()): 0 ;
		double maxScore = (scoreModel.getMaxScore()!=null && !scoreModel.getMaxScore().equals(""))?Double.parseDouble(scoreModel.getMaxScore()):0;
		double minScore = (scoreModel.getMinScore()!=null && !scoreModel.getMinScore().equals(""))?Double.parseDouble(scoreModel.getMinScore()):0;
		if(intervalTypeName!=null && !intervalTypeName.equals("") && intervalTypeName.equals("0")){
			//jiafen
			if(addSubtractTypeName!=null &&! addSubtractTypeName.equals("") && addSubtractTypeName.equals("0")){
				if(new Double(number).compareTo(new Double(reviewStandScore)) <0){
					sc = FloatUtil.round(0, 4);
				}else if (new Double(number).compareTo(new Double(deadlineNumber)) >0) {
					sc = FloatUtil.round(maxScore, 4);
				}else {
					int floor = (int) ((deadlineNumber-reviewStandScore)/intervalNumber) +1;
					Double dNumber = new Double(number);
					Double dDeadlineNumber = new Double(deadlineNumber);
					Double dReviewStandScore = new Double(reviewStandScore);
					if( dNumber.compareTo(dReviewStandScore) >=0 && dNumber.compareTo(dDeadlineNumber)<=0){
						for(int i=0;i<floor;i++){
							if(dNumber.compareTo(new Double(FloatUtil.add(reviewStandScore, FloatUtil.mul(i+1, intervalNumber)))) <0){
								sc = FloatUtil.mul(i+1, score);
								sc = getDeadlineScore(sc, maxScore, 0);
								sc = FloatUtil.add(0, sc);
								break;
							}
						}
					}
				}
			}else if (addSubtractTypeName.equals("1")) {//jianfen
				if(new Double(number).compareTo(new Double(reviewStandScore)) <0){
					sc = FloatUtil.round(maxScore, 4);
				}else if (new Double(number).compareTo(new Double(deadlineNumber)) >0) {
					sc = FloatUtil.round(0, 4);
				}else {
					int floor = (int) ((deadlineNumber-reviewStandScore)/intervalNumber) +1;
					Double dNumber = new Double(number);
					Double dDeadlineNumber = new Double(deadlineNumber);
					Double dReviewStandScore = new Double(reviewStandScore);
					if( dNumber.compareTo(dReviewStandScore) >=0 && dNumber.compareTo(dDeadlineNumber)<=0){
						for(int i=0;i<floor;i++){
							if(dNumber.compareTo(new Double(FloatUtil.add(reviewStandScore, FloatUtil.mul(i+1, intervalNumber)))) <0){
								sc = FloatUtil.mul(i+1, score);
								sc = getDeadlineScore(sc, minScore, 1);
								sc = FloatUtil.sub(maxScore, sc);
								break;
							}
						}
					}
				}
			}
		}else if (intervalTypeName.equals("1")) {
			ParamInterval paramInterval = new ParamInterval();
			paramInterval.setScoreModelId(scoreModel.getId());
			List<ParamInterval> paramIntervalList = paramIntervalService.findListByParamInterval(paramInterval);
			Double num = new Double(number);
			for(ParamInterval p:paramIntervalList){
				Double  startParam = new Double(p.getStartParam());
				Double endParam = new Double(p.getEndParam());
				if(num.compareTo(startParam) >=0 && num.compareTo(endParam) <=0){
					sc = Double.parseDouble(p.getScore());
					break;
				}
			}
		}
		return sc;
	}
	/**
	 * 
	 * @Title: getScoreByModelEight
	 * @author: Tian Kunfeng
	 * @date: 2016-11-11 上午11:32:30
	 * @Description: 模型8  低计算   左开右闭区间   第一个不包括          基准数值  > 评审截止数
	 * 等额   1 加分区间  区间内取值          高于评审基准数值为最低分0分，区间内  按计算规则递增(]  低于评审截止数   的最高分
	 *     2 减分区间   区间内取值      高于评审基准数的最高分，区间内  等额递减计算    低于评审截止数   得分最低分0分
                
	 * @param: @param scoreModel
	 * @param: @param number
	 * @param: @return
	 * @return: double
	 */
	public double getScoreByModelEight(ScoreModel scoreModel,double number){

		double sc =0 ;
		//评审基准数
		double reviewStandScore = (scoreModel.getReviewStandScore()!=null &&! scoreModel.getReviewStandScore().equals(""))?Double.parseDouble(scoreModel.getReviewStandScore()):0;
		//0等额区间    1不等额区间
		String intervalTypeName = scoreModel.getIntervalTypeName();
		//加减分类型  0 加分 1减分
		String addSubtractTypeName = scoreModel.getAddSubtractTypeName();
		//每个区间之间的差额，用于等额区间模型
		double intervalNumber = (scoreModel.getIntervalNumber()!=null && !scoreModel.getIntervalNumber().equals(""))?Double.parseDouble(scoreModel.getIntervalNumber()) : 0 ;
		//加减分分值
		double score = (scoreModel.getScore()!=null &&! scoreModel.equals(""))?Double.parseDouble(scoreModel.getScore()):0;
		//如果加分，高于截止数为满分，如果减分，低于截止数为0分
		double deadlineNumber = (scoreModel.getDeadlineNumber()!=null &&!scoreModel.getDeadlineNumber().equals(""))?Double.parseDouble(scoreModel.getDeadlineNumber()): 0 ;
		double maxScore = (scoreModel.getMaxScore()!=null && !scoreModel.getMaxScore().equals(""))?Double.parseDouble(scoreModel.getMaxScore()):0;
		double minScore = (scoreModel.getMinScore()!=null && !scoreModel.getMinScore().equals(""))?Double.parseDouble(scoreModel.getMinScore()):0;
		if(intervalTypeName!=null && !intervalTypeName.equals("") && intervalTypeName.equals("0")){
			//jiafen
			if(addSubtractTypeName!=null &&! addSubtractTypeName.equals("") && addSubtractTypeName.equals("0")){
				if(new Double(number).compareTo(new Double(deadlineNumber)) <0){
					sc = FloatUtil.round(maxScore, 4);
				}else if (new Double(number).compareTo(new Double(reviewStandScore)) <0) {
					sc = FloatUtil.round(0, 4);
				}else {
					int floor = (int) ((deadlineNumber-reviewStandScore)/intervalNumber) +1;
					Double dNumber = new Double(number);
					Double dDeadlineNumber = new Double(deadlineNumber);
					Double dReviewStandScore = new Double(reviewStandScore);
					if( dNumber.compareTo(dDeadlineNumber) >=0 && dNumber.compareTo(dReviewStandScore)<=0){
						for(int i=0;i<floor;i++){
							if(dNumber.compareTo(new Double(FloatUtil.add(reviewStandScore, FloatUtil.mul(i+1, intervalNumber)))) <0){
								sc = FloatUtil.mul(i+1, score);
								sc = getDeadlineScore(sc, maxScore, 0);
								sc = FloatUtil.add(0, sc);
								break;
							}
						}
					}
				}
			}else if (addSubtractTypeName.equals("1")) {//jianfen
				if(new Double(number).compareTo(new Double(reviewStandScore)) <0){
					sc = FloatUtil.round(maxScore, 4);
				}else if (new Double(number).compareTo(new Double(deadlineNumber)) >0) {
					sc = FloatUtil.round(0, 4);
				}else {
					int floor = (int) ((deadlineNumber-reviewStandScore)/intervalNumber) +1;
					Double dNumber = new Double(number);
					Double dDeadlineNumber = new Double(deadlineNumber);
					Double dReviewStandScore = new Double(reviewStandScore);
					if( dNumber.compareTo(dDeadlineNumber) >=0 && dNumber.compareTo(dReviewStandScore)<=0){
						for(int i=0;i<floor;i++){
							if(dNumber.compareTo(new Double(FloatUtil.add(reviewStandScore, FloatUtil.mul(i+1, intervalNumber)))) <0){
								sc = FloatUtil.mul(i+1, score);
								sc = getDeadlineScore(sc, minScore, 1);
								sc = FloatUtil.sub(maxScore, sc);
								break;
							}
						}
					}
				}
			}
		}else if (intervalTypeName.equals("1")) {
			ParamInterval paramInterval = new ParamInterval();
			paramInterval.setScoreModelId(scoreModel.getId());
			List<ParamInterval> paramIntervalList = paramIntervalService.findListByParamInterval(paramInterval);
			Double num = new Double(number);
			for(ParamInterval p:paramIntervalList){
				Double  startParam = new Double(p.getStartParam());
				Double endParam = new Double(p.getEndParam());
				if(num.compareTo(startParam) >=0 && num.compareTo(endParam) <=0){
					sc = Double.parseDouble(p.getScore());
					break;
				}
			}
		}
		return sc;
	
	}
	/**
	 * 
	 * @Title: isNumber
	 * @author: Tian Kunfeng
	 * @date: 2016-11-7 下午6:16:41
	 * @Description: 判断是否是数字字符串
	 * @param: @param str
	 * @param: @return
	 * @return: boolean
	 */
	public boolean isNumber(String str) {
        java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("[0-9]*");
        java.util.regex.Matcher match=pattern.matcher(str);
        if(match.matches()==false)
        {
             return false;
        }
        else
        {
             return true;
        }
    }
	/**
	 * 
	 * @Title: getDeadlineScore
	 * @author: Tian Kunfeng
	 * @date: 2016-11-7 下午6:20:56
	 * @Description: 最高不可以高于最高分   最低不可低于最低分
	 * @param: @param cuurScore 当前得分
	 * @param: @param deadlineScoe  截止数
	 * @param: @param type 0 加分  1减分
	 * @param: @return
	 * @return: int
	 */
	public double getDeadlineScore(double cuurScore,double deadlineScoe,Integer type){
		double score=0;
		if(type!=null && type.equals(0)){
			if(FloatUtil.sub(cuurScore, deadlineScoe)>0){
				score = deadlineScoe;
			}
		}else if (type.equals(1)) {
			if (FloatUtil.sub(cuurScore, deadlineScoe)<0) {
				cuurScore = deadlineScoe;
			}
		}
		return score;
	}
	//---------------------------------基本get set 方法--------------------------------------------------------------------
	public AjaxJsonData getAjaxJsonData() {
		return ajaxJsonData;
	}
	public void setAjaxJsonData(AjaxJsonData ajaxJsonData) {
		this.ajaxJsonData = ajaxJsonData;
	}
	
	
}
/**
 * 
 * @Title: SortByParam
 * @Description: 内部类   集合排序
 * @author: Tian Kunfeng
 * @date: 2016-11-8下午4:33:53
 */
@SuppressWarnings("rawtypes")
class SortByParam implements Comparator {
	public int compare(Object o1, Object o2) {
		SupplyMark s1 = (SupplyMark) o1;
		SupplyMark s2 = (SupplyMark) o2;
		/*//升序排列
		if (new Double(s1.getPrarm()).compareTo(new Double(s2.getPrarm())) >0){
			return 1;
		}else if (new Double(s1.getPrarm()).compareTo(new Double(s2.getPrarm())) <0) {
			return -1;
		}*/
		//降序排列
		if (new Double(s1.getPrarm()).compareTo(new Double(s2.getPrarm())) >0){
			return -1;
		}else if (new Double(s1.getPrarm()).compareTo(new Double(s2.getPrarm())) <0) {
			return 1;
		}
		return 0;
	}
}