package iss.controller.ps;

import iss.model.ps.ArticleType;
import iss.service.ps.ArticleService;
import iss.service.ps.ArticleTypeService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.User;

import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;



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
	public String getArticleTypeList(@CurrentUser User user,Model model,Integer page) {
		//声明标识是否是资源服务中心
        String authType = null;
        if(null != user && "4".equals(user.getTypeName())){
            //判断是否 是资源服务中心 
            authType = "4";
            model.addAttribute("authType", authType);
            List<ArticleType> articletypes = articleTypeService.selectAllArticleType(page==null?1:page);
            model.addAttribute("list", new PageInfo<ArticleType>(articletypes));
        }
        return "iss/ps/articletype/list";
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
	public String view(@CurrentUser User user,Model model, String id) {
		if(null != user && "4".equals(user.getTypeName())){
			ArticleType articletype = articleTypeService.selectTypeByPrimaryKey(id);
			model.addAttribute("articletype", articletype);
			return "iss/ps/articletype/view";
		}
		return "";
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
	public String edit(@CurrentUser User user,Model model, String id) {
		if(null != user && "4".equals(user.getTypeName())){
            //判断是否 是资源服务中心 
			ArticleType articletype = articleTypeService.selectTypeByPrimaryKey(id);
			List<ArticleType> articletypes = articleTypeService.getAll();
			model.addAttribute("articletype", articletype);
			model.addAttribute("list", articletypes);
			return "iss/ps/articletype/edit";
		}
		return "";
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
	public String update(@Valid ArticleType articleType,BindingResult result,HttpServletRequest request,Model model) {
		List<ArticleType> articletypes = articleTypeService.getAll();
		String id = request.getParameter("articletypeId");
		Boolean flag = true;
		String url = "";
		for(int i=0;i<articletypes.size();i++){
			if(articletypes.get(i).getId().equals(id)){
				articletypes.remove(i);
			}
		}
		for(ArticleType ar:articletypes){
			if(ar.getName().equals(articleType.getName())){
				flag = false;
				model.addAttribute("ERR_name", "栏目名称不能重复");
			}
		}
		if(articleType.getName()==null || articleType.getName().equals("")){
			flag = false;
			model.addAttribute("ERR_name", "栏目名称不能为空");
		}
		if(flag == false){
			model.addAttribute("articletype", articleType);
			url = "iss/ps/articletype/edit";
		}else{
			Timestamp ts = new Timestamp(new Date().getTime());
			articleType.setUpdatedAt(ts);
			articleType.setId(id);
			articleTypeService.updateByPrimaryKey(articleType);	
			url = "redirect:getAll.html";
		}
		return url;
	}
}
