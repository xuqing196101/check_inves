package bss.controller.prms;

import iss.service.ps.ArticleService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import common.annotation.CurrentUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.service.bms.CategoryService;
import ses.util.DictionaryDataUtil;
import bss.controller.base.BaseController;
import bss.model.ppms.MarkTerm;
import bss.model.ppms.ParamInterval;
import bss.model.ppms.ScoreModel;
import bss.model.prms.FirstAuditTemitem;
import bss.model.prms.FirstAuditTemplat;
import bss.service.ppms.BidMethodService;
import bss.service.ppms.MarkTermService;
import bss.service.ppms.ParamIntervalService;
import bss.service.ppms.ScoreModelService;
import bss.service.prms.FirstAuditTemitemService;
import bss.service.prms.FirstAuditTemplatService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/auditTemplat")
public class FirstAuditTemplatController extends BaseController{

	@Autowired
	private FirstAuditTemplatService service;
	
	@Autowired
	private MarkTermService markTermService;
	
	@Autowired
	private FirstAuditTemitemService temService;
	
	@Autowired
	private BidMethodService bidMethodService;
	
	@Autowired
	private ScoreModelService scoreModelService;
	    
	@Autowired
	private ParamIntervalService paramIntervalService;
	
	@Autowired
  private ArticleService articleService;
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value = "deleteScoreModel")
    public String deleteScoreModel(String id, Integer deleteStatus, String projectId ,String packageId) {
        //为2为顶级结点     1 为子节点
        HashMap<String, Object> map = new HashMap<String, Object>();
        HashMap<String, Object> conditionMap = new HashMap<String, Object>();
        if (deleteStatus == 1) {
            scoreModelService.deleteScoreModelByMtId(id);
            map.put("id", id);
            markTermService.delMarkTermByid(map);
            conditionMap.put("projectId", projectId);
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
                conditionMap.put("projectId", projectId);
                paramIntervalService.delParamIntervalByMap(conditionMap);
            }
            MarkTerm mt = markTermService.findMarkTermById(id);
            map.put("id", mt.getBidMethodId());
            bidMethodService.delBidMethodByMap(map);
            map.put("id", id);
            markTermService.delMarkTermByid(map);
        }
        return "redirect:editTemplat.html?templetId="+projectId+"&templetKind=1";
    }
	
	/**
	 * 
	  * @Title: list
	  * @author ShaoYangYang
	  * @date 2016年10月12日 上午10:17:21  
	  * @Description: TODO 列表
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/list")
	public String list(String name, String kind, String categoryId, Integer page, Model model, @CurrentUser User user){
        Map<String, Object> map = new HashMap<String, Object>();
        //判读是否是资源服务中心  支撑环境-后台管理-评审模板管理，权限所属角色是：资源服务中心，查看范围是：所有，操作范围是：所有，权限属性是：操作
        String typeName = user.getTypeName();
        if (StringUtils.isNotBlank(typeName)&&"4".equalsIgnoreCase(typeName )) {
            map.put("name", name);
            map.put("kind", kind);
            map.put("categoryId", categoryId);
            List<FirstAuditTemplat> list = service.selectAll(map, page == null ? 1 : page);
            for (FirstAuditTemplat firstAuditTemplat : list) {
              if (firstAuditTemplat.getCategoryId() != null && !"".equals(firstAuditTemplat.getCategoryId())) {
                Category category = categoryService.findById(firstAuditTemplat.getCategoryId());
                if (category != null) {
                  firstAuditTemplat.setCategoryName(category.getName());
                }
              }
            }
            List<DictionaryData> kinds = DictionaryDataUtil.find(20);
            model.addAttribute("list", new PageInfo<>(list));
            model.addAttribute("categoryId", categoryId);
            if (categoryId != null) {
              Category category = categoryService.findById(categoryId);
              if (category != null) {
                model.addAttribute("categoryName", category.getName());
              }
            }
            model.addAttribute("kinds", kinds);
            model.addAttribute("kind", kind);
            model.addAttribute("name", name);
        }
        return "bss/prms/templat/list";
	}
	
	/**
	 * 
	  * @Title: toAdd
	  * @author ShaoYangYang
	  * @date 2016年10月11日 下午4:03:10  
	  * @Description: TODO 跳转到新增页面
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/toAdd")
	public String toAdd(Model model){
	  List<DictionaryData> kinds = DictionaryDataUtil.find(20);
    model.addAttribute("kinds", kinds);
		return "bss/prms/templat/add_templat";
	}
	/**
	 * 
	  * @Title: add
	  * @author ShaoYangYang
	  * @date 2016年10月11日 下午4:03:35  
	  * @Description: TODO 新增
	  * @param @param templat
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/add")
	public String add(@Valid FirstAuditTemplat templat, BindingResult result, Model model){
	  if(result.hasErrors()){
      List<DictionaryData> kinds = DictionaryDataUtil.find(20);
      model.addAttribute("kinds", kinds);
      model.addAttribute("templat", templat);
      if (templat != null && templat.getCategoryId() != null) {
        Category category = categoryService.findById(templat.getCategoryId());
        if (category != null) {
          model.addAttribute("categoryName", category.getName());
        }
      }
      return "bss/prms/templat/add_templat";
    }
		service.save(templat);
		return "redirect:list.html";
	}
	/**
	 * 
	  * @Title: edit
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午1:51:00  
	  * @Description: TODO 修改
	  * @param @param templat
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/edit")
	public String edit(@Valid FirstAuditTemplat templat, BindingResult result, Model model){
	  if(result.hasErrors()){
	    List<DictionaryData> kinds = DictionaryDataUtil.find(20);
	    model.addAttribute("kinds", kinds);
	    model.addAttribute("templat", templat);
	    if (templat != null && templat.getCategoryId() != null) {
        Category category = categoryService.findById(templat.getCategoryId());
        if (category != null) {
          model.addAttribute("categoryName", category.getName());
        }
      }
	    return "bss/prms/templat/edit";
	  }
		service.update(templat);
		return "redirect:list.html";
	}
	/**
	 * 
	  * @Title: toEdit
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午1:53:26  
	  * @Description: TODO 去往修改页面
	  * @param @param id
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/toEdit")
	public String toEdit(String id , Model model){
		FirstAuditTemplat templat = service.getById(id);
		List<DictionaryData> kinds = DictionaryDataUtil.find(20);
    model.addAttribute("kinds", kinds);
		model.addAttribute("templat", templat);
		if (templat != null && templat.getCategoryId() != null) {
		  Category category = categoryService.findById(templat.getCategoryId());
		  if (category != null) {
		    model.addAttribute("categoryName", category.getName());
		  }
		}
		return "bss/prms/templat/edit";
	}
	
	/**
	 *〈简述〉编辑模板的评审项
	 *〈详细描述〉
	 * @author Ye MaoLin
	 * @param templatKind 模板类型id
	 * @return
	 */
	@RequestMapping("/editTemplat")
	public String editTemplat(String templetKind, Model model, String templetId){
	  DictionaryData kind = DictionaryDataUtil.findById(templetKind);
	  List<FirstAuditTemitem> items = temService.selectByTemplatId(templetId);
	  if (kind != null && kind.getCode().equals("REVIEW_QC")) {
	    List<DictionaryData> dds = DictionaryDataUtil.find(22);
	    //符合性审查项
	    FirstAuditTemitem record = new FirstAuditTemitem();
	    record.setKind(DictionaryDataUtil.getId("COMPLIANCE"));
	    record.setTemplatId(templetId);
	    List<FirstAuditTemitem> items1 = temService.find(record);
	    //资格性审查项
      FirstAuditTemitem record2 = new FirstAuditTemitem();
      record2.setKind(DictionaryDataUtil.getId("QUALIFICATION"));
      record2.setTemplatId(templetId);
      List<FirstAuditTemitem> items2 = temService.find(record2);
	    //符合性资格性审查项类型
	    model.addAttribute("dds", dds);
	    model.addAttribute("kind", kind);
	    model.addAttribute("items1", items1);
	    model.addAttribute("items2", items2);
	    model.addAttribute("templetKind", templetKind);
	    model.addAttribute("templetId", templetId);
	    //符合性资格性审查项编辑
	    return "bss/prms/templat/qc_item_templet";
    } 
	  if ((kind != null && kind.getCode().equals("REVIEW_ET") || "1".equals(templetKind))) {
	    //显示经济技术 和子节点  子节点的子节点就是模型
        List<DictionaryData> ddList = DictionaryDataUtil.find(23);
        String str ="";
        for (DictionaryData dictionaryData : ddList) {
            str += getTable(dictionaryData.getId(), dictionaryData.getName(), templetId, "");
        }
        model.addAttribute("ddList", ddList);
        model.addAttribute("str", str);
        model.addAttribute("templetId", templetId);
        model.addAttribute("templetKind", templetKind);
        return "bss/prms/templat/edit_package_qc";
    } 
	  if (kind != null && kind.getCode().equals("REVIEW_CHECK_ET")) {
      List<DictionaryData> dds = DictionaryDataUtil.find(23);
      //经济审查项
      FirstAuditTemitem record = new FirstAuditTemitem();
      record.setKind(DictionaryDataUtil.getId("ECONOMY"));
      record.setTemplatId(templetId);
      List<FirstAuditTemitem> items1 = temService.find(record);
      //技术审查项
      FirstAuditTemitem record2 = new FirstAuditTemitem();
      record2.setKind(DictionaryDataUtil.getId("TECHNOLOGY"));
      record2.setTemplatId(templetId);
      List<FirstAuditTemitem> items2 = temService.find(record2);
      model.addAttribute("dds", dds);
      model.addAttribute("kind", kind);
      model.addAttribute("items1", items1);
      model.addAttribute("items2", items2);
      model.addAttribute("templetKind", templetKind);
      model.addAttribute("templetId", templetId);
      return "bss/prms/templat/check_et_item";
    } 
	  return null;
	}
	
	@RequestMapping("gettreebody")
    public String gettreebody(@ModelAttribute MarkTerm markTerm,Model model,HttpServletRequest request ,String addStatus) throws UnsupportedEncodingException {
        ScoreModel scoreModel = new ScoreModel();
        scoreModel.setName(URLDecoder.decode(markTerm.getName(), "UTF-8"));
        scoreModel.setMarkTermId(markTerm.getId()==null?"":markTerm.getId());
        List<ScoreModel> scoreModelList = scoreModelService.findListByScoreModel(scoreModel);
        if (scoreModelList != null && scoreModelList.size()==1) {
            ParamInterval pi = new ParamInterval();
            pi.setScoreModelId(scoreModelList.get(0).getId());
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
        return "bss/prms/templat/treebody";
    }
	
	public String getTable(String id, String name, String projectId, String packageId) {
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
            //强转为int也是越来越大 所以不会有bug 做法不太好
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
                                sb.append("<div class='fr'><a title='编辑' href='javascript:void(0);' onclick=editItem('" + markKey.getId() + "');  class='item_size editItem'></a>");
                                sb.append("<a title='删除' href='javascript:void(0);' onclick=delItem('" + markKey.getId() + "',2)  class='item_size deleteItem'></a></div></td>");
                                String typeName = getTypeName(markValue.getSmtypename());
                                sb.append("<td class='tc'>" + markValue.getSmname() + "</td><td class='tc'>" + typeName + "</td>");
                                Double sscore = markValue.getScscore() ;
                                if (sscore == null){
                                    sscore = 0.0;
                                }
                                sb.append("<td>"+ markValue.getName());
                                sb.append("<div class='fr'><a href='javascript:void(0);' title='编辑' onclick=addModel('" + markValue.getName() + "','" + markValue.getId() + "',2);  class='item_size editItem'></a>");
                                sb.append("<a href='javascript:void(0);' title='删除' onclick=delItem('" + markValue.getId() + "',1)  class='item_size deleteItem'></a></div></td><td>"+sscore+"</td></tr>");
                            } else {
                                String typeName = getTypeName(markValue.getSmtypename());
                                sb.append("<tr><td class='tc'>" + markValue.getSmname() + "</td><td class='tc'><span>" + typeName + "</span></td>");
                                Double sscore = markValue.getScscore();
                                if (sscore == null){
                                    sscore = 0.0;
                                }
                                sb.append("<td>"+ markValue.getName());
                                sb.append("<div class='fr'><a href='javascript:void(0);' title='编辑' onclick=addModel('" + markValue.getName() + "','" + markValue.getId() + "',2);  class='item_size editItem'></a>");
                                sb.append("<a href='javascript:void(0);' title='删除' onclick=delItem('" + markValue.getId() + "',1)  class='item_size deleteItem'></a></div></td><td>"+sscore+"</td></tr>");
                            }
                            count1++;
                        }
                    } else {
                        sb.append("<tr><td class='w100' rowspan=" + count3 +"><span class='fl'>"+ name +"</span><a class='addItem item_size' onclick=addItem(this,'"+ id +"',1); ></a></td>");
                        sb.append("<td class='w150'><span class='fl'>" + markKey.getName() + "</span><a class='addItem item_size' onclick=addModel('" + markKey.getName() + "','" + markKey.getId() + "',1); ></a>");
                        sb.append("<div class='fr'><a title='编辑' href='javascript:void(0);' onclick=editItem('" + markKey.getId() + "');  class='item_size editItem'></a>");
                        sb.append("<a title='删除' href='javascript:void(0);' onclick=delItem('" + markKey.getId() + "',2)  class='item_size deleteItem'></a></div></td>");
                        sb.append("<td></td><td></td><td></td><td></td></tr>");
                    }
                } else {
                    Integer count2 = 0;
                    if (map.get(markKey) != null && map.get(markKey).size() > 0) {
                        for (MarkTerm markValue : map.get(markKey)) {
                            if (count2 ==0) {
                                //sb.append("<tr><td rowspan=" + map.get(markKey).size() + ">"+markKey.getName()+"</td>");
                                sb.append("<tr><td class='w100' rowspan="+map.get(markKey).size()+">");
                                sb.append("<span class='fl'>" + markKey.getName() + "</span><a class='addItem item_size' onclick=addModel('" + markValue.getName() + "','" + markKey.getId() + "',1); ></a>");
                                sb.append("<div class='fr'><a title='编辑' href='javascript:void(0);' onclick=editItem('" + markKey.getId() + "');  class='item_size editItem'></a>");
                                sb.append("<a title='删除' href='javascript:void(0);' onclick=delItem('" + markKey.getId() + "',2)  class='item_size deleteItem'></a></div></td>");
                                
                                //sb.append("<td>" + markValue.getName() + "</td><td></td><td></td></tr>");
                                String typeName = getTypeName(markValue.getSmtypename());
                                sb.append("<td class='tc'>" + markValue.getSmname() + "</td><td class='tc'>" + typeName + "</td>");
                                Double sscore = markValue.getScscore();
                                if (sscore == null){
                                    sscore = 0.0;
                                }
                                sb.append("<td>"+ markValue.getName());
                                sb.append("<div class='fr'><a href='javascript:void(0);' title='编辑' onclick=addModel('" + markValue.getName() + "','" + markValue.getId() + "',2);  class='item_size editItem'></a>");
                                sb.append("<a href='javascript:void(0);' title='删除' onclick=delItem('" + markValue.getId() + "',1)  class='item_size deleteItem'></a></div></td><td>"+sscore+"</td></tr>");
                                
                            } else {
                                String typeName = getTypeName(markValue.getSmtypename());
                                sb.append("<tr><td class='tc'>" + markValue.getSmname() + "</td><td class='tc'>" + typeName + "</td>");
                                Double sscore = markValue.getScscore();
                                if (sscore == null){
                                    sscore = 0.0;
                                }
                                sb.append("<td>"+ markValue.getName());
                                sb.append("<div class='fr'><a href='javascript:void(0);' title='编辑' onclick=addModel('" + markValue.getName() + "','" + markValue.getId() + "',2);  class='item_size editItem'></a>");
                                sb.append("<a href='javascript:void(0);' title='删除' onclick=delItem('" + markValue.getId() + "',1)  class='item_size deleteItem'></a></div></td><td>"+sscore+"</td></tr>");
                                //sb.append("<tr><td>" + markValue.getName() + "</td><td></td><td></td></tr>");
                            }
                            count2++;
                        }
                    } else {
                        //sb.append("<tr><td>" + markKey.getName() + "</td><td></td><td></td><td></td></tr>");
                        sb.append("<tr><td>");
                        sb.append("<span class='fl'>" + markKey.getName() + "</span><a class='addItem item_size' onclick=addModel('" + markKey.getName() + "','" + markKey.getId() + "',1); ></a>");
                        sb.append("<div class='fr'><a title='编辑' href='javascript:void(0);' onclick=editItem('" + markKey.getId() + "');  class='item_size editItem'></a>");
                        sb.append("<a title='删除' href='javascript:void(0);' onclick=delItem('" + markKey.getId() + "',2)  class='item_size deleteItem'></a></div></td>");
                        sb.append("<td></td><td></td><td></td><td></td></tr>");
                    }
                }
                count++;
            }
        } else {
            sb.append("<tr><td class='w100'><span class='fl'>"+ name +"</span><a class='addItem item_size' onclick=addItem(this,'"+ id +"',1); ></a></td>");
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
                    p.setStartRelation(startRelation[i]);
                    p.setStartParam(startParam[i]);
                    p.setEndParam(endParam[i]);
                    p.setScore(score[i]);
                    p.setEndRelation(endRelation[i]);
                    p.setProjectId(scoreModel.getProjectId());
                    paramIntervalService.saveParamInterval(p);
                }
            }
        }
        return "redirect:editTemplat.html?templetId="+scoreModel.getProjectId()+"&templetKind=1";
    }
	
	/**
	 * 
	  * @Title: toAddFirstAudit
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午2:08:24  
	  * @Description: TODO 去往添加初审项定义页面
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("toAddFirstAudit")
	public String toAddFirstAudit(String id,Model model){
		FirstAuditTemplat templat = service.getById(id);
		List<FirstAuditTemitem> list = temService.selectByTemplatId(id);
		model.addAttribute("templat", templat);
		model.addAttribute("list", list);
		return "bss/prms/templat/add_first_audit";
	}
	/**
	 * 
	  * @Title: saveFirstAudit
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午2:52:48  
	  * @Description: TODO 保存初审项定义
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("saveFirstAudit")
	public String saveFirstAudit(FirstAuditTemitem temitem ,RedirectAttributes attr){
		temService.save(temitem);
		attr.addAttribute("id", temitem.getTemplatId());
		return "redirect:toAddFirstAudit.html";
	}
	/**
	 * 
	  * @Title: toEditFirstAudit
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午5:32:58  
	  * @Description: TODO 去往修改初审项窗口
	  * @param @param id
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("toEditFirstAudit")
	public String toEditFirstAudit(String id,Model model){
		FirstAuditTemitem temitem = temService.getById(id);
		model.addAttribute("temitem", temitem);
		return  "bss/prms/templat/edit_first_audit";
	}
	/**
	 * 
	  * @Title: editFirstAudit
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午3:01:11  
	  * @Description: TODO 修改初审项定义
	  * @param @param temitem
	  * @param @param attr
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("editFirstAudit")
	public String editFirstAudit(FirstAuditTemitem temitem,RedirectAttributes attr){
		temService.update(temitem);
		attr.addAttribute("id", temitem.getTemplatId());
		return "redirect:toAddFirstAudit.html";
	}
	/**
	 * 
	  * @Title: delete
	  * @author ShaoYangYang
	  * @date 2016年10月12日 上午11:19:13  
	  * @Description: TODO 删除初审项定义
	  * @param       
	  * @return void
	 */
	@RequestMapping("deleteFirstAudit")
	@ResponseBody
	public void deleteFirstAudit(String ids){
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			temService.deleteById(id[i]);
		}
	}
	/**
	 * 
	  * @Title: delete 
	  * @author ShaoYangYang
	  * @date 2016年10月12日 上午11:19:13  
	  * @Description: TODO 删除模板
	  * @param       
	  * @return void
	 */
	@RequestMapping("delete")
	public String delete(String ids){
		String[] id = ids.split(",");
		//循环删除选中的数据
		for (String string : id) {
			service.deleteById(string);
			List<FirstAuditTemitem> temitems = temService.selectByTemplatId(string);
      for (FirstAuditTemitem firstAuditTemitem : temitems) {
        temService.deleteById(firstAuditTemitem.getId());
      }
		}
		return "redirect:list.html";
	}
	
	/**
	 *〈简述〉保存符合性评审项
	 *〈详细描述〉
	 * @author Ye MaoLin
	 * @param response
	 * @param auditTemitem
	 * @throws IOException
	 */
	@RequestMapping("/saveItem")
	public void saveItem(HttpServletResponse response, FirstAuditTemitem auditTemitem) throws IOException{
	  try {
      int count = 0;
      String msg = "";
      if (auditTemitem.getName() == null || "".equals(auditTemitem.getName())) {
        msg += "请输入评审项名称";
        count ++;
      }
      if (auditTemitem.getPosition()== null) {
        if (count > 0) {
          msg += "、序号";
        } else {
          msg += "请输入排序号";
        }
        count ++;
      }
      if (auditTemitem.getContent()== null || "".equals(auditTemitem.getContent())) {
        if (count > 0) {
          msg += "和评审内容";
        } else {
          msg += "请输入评审内容";
        }
        count ++;
      }
      if (count > 0) {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter()
                .print("{\"success\": " + false + ", \"msg\": \"" + msg+ "\"}");
      }
      if (count == 0) {
        msg += "添加成功";
        temService.save(auditTemitem);
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
	
	/**
	 *〈简述〉弹出编辑符合性评审项页面
	 *〈详细描述〉
	 * @author Ye MaoLin
	 * @return
	 */
	@RequestMapping("/editItem")
	public String editItem(String id, Model model){
	  FirstAuditTemitem firstAuditTemitem = temService.getById(id);
	  FirstAuditTemplat firstAuditTemplat = service.getById(firstAuditTemitem.getTemplatId());
	  model.addAttribute("item", firstAuditTemitem);
	  model.addAttribute("templat", firstAuditTemplat);
	  return "bss/prms/templat/qc_edit_item";
	}
	
	/**
	 *〈简述〉更新符合性评审项
	 *〈详细描述〉
	 * @author Ye MaoLin
	 * @param response
	 * @param firstAuditTemitem
	 * @throws IOException
	 */
	@RequestMapping("/updateItem")
	public void updateItem(HttpServletResponse response, FirstAuditTemitem firstAuditTemitem) throws IOException{
	  try {
	    int count = 0;
      String msg = "";
      if (firstAuditTemitem.getName() == null || "".equals(firstAuditTemitem.getName())) {
        msg += "请输入评审项名称";
        count ++;
      }
      if (firstAuditTemitem.getPosition()== null) {
        if (count > 0) {
          msg += "、序号";
        } else {
          msg += "请输入排序号";
        }
        count ++;
      }
      if (firstAuditTemitem.getContent()== null || "".equals(firstAuditTemitem.getContent())) {
        if (count > 0) {
          msg += "和评审内容";
        } else {
          msg += "请输入评审内容";
        }
        count ++;
      }
      if (count > 0) {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter()
                .print("{\"success\": " + false + ", \"msg\": \"" + msg+ "\"}");
      }
      if (count == 0) {
        msg += "更新成功";
        temService.update(firstAuditTemitem);
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
	
	/**
	 *〈简述〉删除符合性评审项
	 *〈详细描述〉
	 * @author Ye MaoLin
	 * @param response
	 * @param id 
	 * @throws IOException
	 */
	@RequestMapping("/delItem")
	public void delItem(HttpServletResponse response, String id) throws IOException{
	  try {
	    FirstAuditTemitem firstAuditTemitem = temService.getById(id);
	    temService.deleteById(firstAuditTemitem.getId());
	    String msg = "删除成功";
	    response.setContentType("text/html;charset=utf-8");
      response.getWriter()
              .print("{\"success\": " + true + ", \"msg\": \"" + msg+ "\"}");
	    response.getWriter().flush();
    } catch (Exception e) {
        e.printStackTrace();
    } finally{
        response.getWriter().close();
    }
	}
	
	/**
	 *〈简述〉
	 *〈详细描述〉查询产品目录树
	 * @author Ye MaoLin
	 * @param tempId
	 * @param id
	 * @param backCategoryIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/categoryTree", produces = "application/json;charset=UTF-8")
  public String categoryTree(String tempId, String id, String backCategoryIds){
	  List < CategoryTree > allCategories = new ArrayList < CategoryTree > ();
	  if (id == null) {
      List<DictionaryData> dictionaryDatas = DictionaryDataUtil.find(6);
      for (DictionaryData dictionaryData : dictionaryDatas) {
        CategoryTree ct=new CategoryTree();
        ct.setId(dictionaryData.getId());
        ct.setName(dictionaryData.getName());
        ct.setIsParent("true");
        ct.setClassify(dictionaryData.getCode());
        allCategories.add(ct);
      }
    } else {
      List < Category > tempNodes = articleService.getCategoryIsPublish(id);
      for (Category category : tempNodes) {
        CategoryTree ct = new CategoryTree();
        ct.setName(category.getName());
        ct.setId(category.getId());
        ct.setParentId(id);
        // 判断是否为父级节点
        List < Category > nodesList = articleService.getCategoryIsPublish(category.getId());
        if(nodesList != null && nodesList.size() > 0) {
          ct.setIsParent("true");
        }
        // 判断是否被选中
        HashMap<String, Object> map =  new HashMap<String, Object>();
        map.put("id", tempId);
        map.put("categoryId", category.getId());
        List<FirstAuditTemplat> auditTemplats = service.find(map);
        if (auditTemplats != null && auditTemplats.size() > 0) {
          ct.setChecked(true);
        }else {
          ct.setChecked(false);
        }
        allCategories.add(ct);
      }
    }    
	  return JSON.toJSONString(allCategories);
	}
	      
	 @ResponseBody
   @RequestMapping(value = "/searchCategory")
   public String searchCategory(String name, String rootCode) throws UnsupportedEncodingException{
       List<CategoryTree> jList = new ArrayList<CategoryTree>();
       name = URLDecoder.decode(name,"UTF-8");
       articleService.searchCategory(jList, name, rootCode);
       return JSON.toJSONString(jList);
   }
}
