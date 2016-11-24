package bss.controller.ppms;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


import ses.model.oms.util.AjaxJsonData;
import ses.model.oms.util.CommonConstant;
import ses.model.oms.util.Ztree;
import ses.util.FloatUtil;

import bss.model.ppms.BidMethod;
import bss.model.ppms.MarkTerm;
import bss.model.ppms.Packages;
import bss.model.ppms.ParamInterval;
import bss.model.ppms.Project;
import bss.model.ppms.ScoreModel;
import bss.model.ppms.SupplyMark;
import bss.service.ppms.BidMethodService;
import bss.service.ppms.MarkTermService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ParamIntervalService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.ScoreModelService;
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
public class IntelligentScoringController {
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
	
	@RequestMapping("packageList")
	public String packageList(@ModelAttribute Packages packages,Model model,HttpServletRequest request,String flowDefineId){
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("projectId", packages.getProjectId());
		Project project = projectService.selectById(packages.getProjectId());
		model.addAttribute("project", project);
		List<Packages> packagesList = packageService.findPackageAndBidMethodById(map);
		model.addAttribute("packagesList", packagesList);
		model.addAttribute("projectId", packages.getProjectId());
		model.addAttribute("flowDefineId", flowDefineId);
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
	public String operatorScoreModel(@ModelAttribute ScoreModel scoreModel,HttpServletRequest request){
		String packageId = request.getParameter("id");
		String[] startParam = request.getParameterValues("pi.startParam");
		String[] endParam = request.getParameterValues("pi.endParam");
		String[] score = request.getParameterValues("pi.score");
		String[] explain = request.getParameterValues("pi.explain");
		
		if(scoreModel.getId()!=null && !scoreModel.getId().equals("")){
			scoreModelService.updateScoreModel(scoreModel);
			HashMap<String, Object> map  = new HashMap<String,Object>();
			map.put("scoreModelId", scoreModel.getId());
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
					p.setEndParam(endParam[i]);
					p.setScore(score[i]);
					p.setExplain(explain[i]);
					paramIntervalService.saveParamInterval(p);
				}
			}
			
		}else {
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
					p.setEndParam(endParam[i]);
					p.setScore(score[i]);
					p.setExplain(explain[i]);
					paramIntervalService.saveParamInterval(p);
				}
			}
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
		String bidMethodId = request.getParameter("bidMethodId");
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
			markTermService.delMarkTermByMap(map);
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
		model.addAttribute("projectId", markTerm.getProjectId());
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