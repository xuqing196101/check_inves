package bss.controller.ppms;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.util.AjaxJsonData;
import ses.model.oms.util.Ztree;

import bss.model.ppms.MarkTerm;
import bss.model.ppms.Packages;
import bss.model.ppms.ScoreModel;
import bss.service.ppms.PackageService;
import bss.service.ppms.ScoreModelService;
/**
 * 
 * @Title: ScoreTemplate
 * @Description: 八大评分模型
 * @author: Tian Kunfeng
 * @date: 2016-10-17上午11:17:08
 */
@Controller
@Scope("prototype")
@RequestMapping("/intelligentScore")
public class IntelligentScoringController {
	
	private AjaxJsonData ajaxJsonData = new AjaxJsonData();
	@Autowired
	private PackageService packageService;
	@Autowired
	private ScoreModelService scoreModelService;
	
	@RequestMapping("packageList")
	public String packageList(@ModelAttribute Packages packages,Model model,HttpServletRequest request){
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("projectId", packages.getProjectId());
		List<Packages> packagesList = packageService.findPackageById(map);
		model.addAttribute("packagesList", packagesList);
		model.addAttribute("projectId", packages.getProjectId());
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
		
		model.addAttribute("packageId", packages.getId());
		model.addAttribute("projectId", request.getParameter("projectId"));
		return "bss/ppms/open_bidding/scoring_standard";
	}
	@RequestMapping("operatorScoreModel")
	
	public String operatorScoreModel(@ModelAttribute ScoreModel scoreModel,HttpServletRequest request){
		String packageId = request.getParameter("id");
		if(scoreModel.getId()!=null && !scoreModel.getId().equals("")){
			scoreModelService.updateScoreModel(scoreModel);
		}else {
			scoreModelService.saveScoreModel(scoreModel);
		}
		return "redirect:gettreebody.do";
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
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", packageId);
		List<Packages> pList = packageService.findPackageById(map);
		Packages packages = new Packages();
		if(pList!=null && pList.size()>0){
			 packages = pList.get(0);
		}
		if(packages.getMarkTermTree()!=null &&! packages.getMarkTermTree().equals("")){
			return packages.getMarkTermTree();
		}else{
			MarkTerm markTerm = new MarkTerm();
			markTerm.setIsRoot("true");
			List<MarkTerm> markTermList = scoreModelService.findListByMarkTerm(markTerm);
			List<Ztree> treeList = new ArrayList<Ztree>();  
			for(MarkTerm m : markTermList){
				Ztree z = new Ztree();
				z.setId(m.getId());
				z.setName(m.getName());
				z.setpId(m.getPid() == null ? "-1":m.getPid());
				z.setCreatedAt(m.getCreatedAt());
				MarkTerm childMarkTerm = new MarkTerm();
				childMarkTerm.setPid(m.getId());
				List<MarkTerm> chiildList = scoreModelService.findListByMarkTerm(childMarkTerm);
				if(chiildList != null && chiildList.size() > 0){
					z.setIsParent("true");
				} else {
					z.setIsParent("false");
				}
				treeList.add(z);
			}
			JSONArray jObject = JSONArray.fromObject(treeList);
			return jObject.toString();
		}
		
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
			scoreModelService.saveMarkTerm(markTerm);
			ajaxJsonData.setSuccess(true);
			ajaxJsonData.setMessage(markTerm.getId());
		}else if (method.equals("updatenode")) {
			scoreModelService.updateMarkTerm(markTerm);
		}else if(method.equals("delnode")){
			HashMap<String, Object> map = new HashMap<String,Object>();
			map.put("id", markTerm.getId());
			scoreModelService.delMarkTermByMap(map);
		}
		//增删改操作后  更新项目分包信息里面绑定的评分项树字符串   下次调用直接解析次字符串
		setPackageMarkTermTree(packageId,method,markTerm);
		
		//如果已经建立模型  更细打分项  关联更新模型表里面的name 
		updateScoreModelName(packageId,method,markTerm);
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
	public String gettreebody(@ModelAttribute MarkTerm markTerm,Model model,HttpServletRequest request) throws UnsupportedEncodingException {
		String packageId = request.getParameter("packageId");
		ScoreModel scoreModel = new ScoreModel();
		scoreModel.setMarkTermId(markTerm.getId()==null?"":markTerm.getId());
		List<ScoreModel> scoreModelList = scoreModelService.findListByScoreModel(scoreModel);
		model.addAttribute("scoreModelList", scoreModelList);
		model.addAttribute("packageId", packageId);
		model.addAttribute("markTermId", markTerm.getId());
		String markTermName ="";
		if(markTerm.getName()!=null && !markTerm.getName().equals("")){
			markTermName = URLDecoder.decode(markTerm.getName(), "UTF-8");
		}
		model.addAttribute("markTermName",markTermName );
		if(scoreModelList!=null && scoreModelList.size()>0){
			
			model.addAttribute("scoreModel", scoreModelList.get(0));
		}
		return "bss/ppms/open_bidding/treebody";
	}
	@RequestMapping("quantizateScore")
	@ResponseBody
	public String quantizateScore(@ModelAttribute Packages packages,HttpServletRequest request){
		
		return "3";
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
							scoreModelService.insert(m);
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
						scoreModelService.insert(m);
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
							scoreModelService.insert(m);
						}
					}else {
						continue;
					}
				}
			}
			
		}
		MarkTerm m = new MarkTerm();
		List<MarkTerm> markTermList = scoreModelService.findListByMarkTerm(m);
		p.setMarkTermTree(getMarkTermTreeStr(markTermList));
		packageService.updateByPrimaryKeySelective(p);
		map.clear();
		map.put("isRoot", "true");
		scoreModelService.delMarkTermByMap(map);
		
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
				List<MarkTerm> chiildList = scoreModelService.findListByMarkTerm(childMarkTerm);
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
	public String getQuantizateScore(ScoreModel scoreModel){
		return "";
	}
	//---------------------------------基本get set 方法--------------------------------------------------------------------
	public AjaxJsonData getAjaxJsonData() {
		return ajaxJsonData;
	}
	public void setAjaxJsonData(AjaxJsonData ajaxJsonData) {
		this.ajaxJsonData = ajaxJsonData;
	}
	
}
