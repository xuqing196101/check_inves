package iss.controller.ps;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import iss.model.ps.ArticleType;
import iss.service.ps.ArticleService;
import iss.service.ps.ArticleTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;



/** 
* @Title:ArticleTypeController 
* @Description: 栏目类型管理控制器
* @author QuJie
* @date 2016-9-8下午5:30:53
 */
@Controller
@Scope("prototype")
@RequestMapping("/articletype")
public class ArticleTypeController {	
	@Autowired
	private ArticleTypeService articleTypeService;
	@Autowired
	private ArticleService articleService;
	
	/**
	 * @Title: getArticleTypeList
	 * @author QuJie
	 * @date 2016-8-10 下午19:47:32
	 * @Description: 获取栏目列表跳转到后台管理
	 * @param @param model
	 * @return String
	 */
	@RequestMapping("/getAll")
	public String getArticleTypeList(Model model,Integer page) {
		List<ArticleType> articletypes = articleTypeService.selectAllArticleType(page==null?1:page);
		model.addAttribute("list", new PageInfo<ArticleType>(articletypes));
		return "iss/articletype/list";
	}
	
	/**
	 * @Title: view
	 * @author QuJie
	 * @date 2016-8-10 下午19:47:32
	 * @Description: 跳转栏目详情页
	 * @param @param model
	 * @return String
	 */
	@RequestMapping("/view")
	public String view(Model model, String id) {
		ArticleType articletype = articleTypeService.selectByPrimaryKey(id);
		model.addAttribute("articletype", articletype);
		return "iss/articletype/view";
	}
	/**
	 * @Title: view
	 * @author QuJie
	 * @date 2016-8-10 下午19:47:32
	 * @Description: 跳转栏目详情页
	 * @param @param model
	 * @return String
	 */
	@RequestMapping("/edit")
	public String edit(Model model, String id) {
		ArticleType articletype = articleTypeService.selectByPrimaryKey(id);
		model.addAttribute("articletype", articletype);
		return "iss/articletype/edit";
	}
	
	/**
	 * @Title: view
	 * @author QuJie
	 * @date 2016-8-10 下午19:47:32
	 * @Description: 跳转栏目详情页
	 * @param @param model
	 * @return String
	 */
	@RequestMapping("/update")
	public String update(HttpServletRequest request, ArticleType articleType) {
		Timestamp ts = new Timestamp(new Date().getTime());
		articleType.setUpdatedAt(ts);
		String id = request.getParameter("articletypeId");
		articleType.setId(id);
		System.out.println(articleType);
		articleTypeService.updateByPrimaryKey(articleType);	
		return "redirect:getAll.html";
	}
	
}
