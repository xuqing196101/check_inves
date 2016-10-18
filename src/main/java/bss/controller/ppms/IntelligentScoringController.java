package bss.controller.ppms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


import ses.model.oms.util.AjaxJsonData;

import bss.model.ppms.ScoreModel;
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
	
	
	
	@RequestMapping("packageList")
	public String packageList(){
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
	public String list(){
		return "";
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
	//-----------------------------------方法封装-------------------------------------------------------------------------------
	//---------------------------------基本get set 方法--------------------------------------------------------------------
	public AjaxJsonData getAjaxJsonData() {
		return ajaxJsonData;
	}
	public void setAjaxJsonData(AjaxJsonData ajaxJsonData) {
		this.ajaxJsonData = ajaxJsonData;
	}
	
}
