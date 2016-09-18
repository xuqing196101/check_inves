package iss.controller.ps;

import iss.model.ps.Article;
import iss.model.ps.DownloadUser;
import iss.service.ps.ArticleService;
import iss.service.ps.DownloadUserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ses.util.PropertiesUtil;

/**
 * 
 *@Title:DownloadUserController
 *@Description:下载人控制类
 *@author QuJie
 *@date 2016-9-12下午1:30:14
 */
@Controller
@Scope("prototype")
@RequestMapping("/downloadUser")
public class DownloadUserController {
	@Autowired
	private DownloadUserService downloadUserService;
	
	@Autowired
	private ArticleService articleService;
	
	/**
	 * 
	* @Title: selectDownloadUserByArticleId
	* @author QuJie 
	* @date 2016-9-12 下午1:36:41  
	* @Description: 根据文章id查询下载人信息 
	* @param @param model
	* @param @param article
	* @param @return
	* @param @throws Exception      
	* @return List<DownloadUser>
	 */
	@RequestMapping("/selectDownloadUserByArticleId")
	public String selectDownloadUserByArticleId(Model model,Article article,Integer page) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		PropertiesUtil config = new PropertiesUtil("config.properties");
		Integer pageSize = Integer.parseInt(config.getString("pageSize"));
		map.put("articleId",article.getId());
		map.put("pageSize",pageSize);
		map.put("page",page);
		List<DownloadUser> downloadUserList = downloadUserService.selectByArticleId(map);
//		PageHelper.startPage(page==null?1:page,Integer.parseInt(config.getString("pageSize")));
//		model.addAttribute("list", new PageInfo<DownloadUser>(downloadUserList));
		return "iss/ps/downloadUser/list";
	};
	
	/**
	 * 
	* @Title: deleteDownloadUser
	* @author QuJie 
	* @date 2016-9-12 下午4:48:00  
	* @Description: 删除下载人信息 
	* @param @param ids
	* @param @throws Exception      
	* @return void
	 */
	@RequestMapping("/deleteDownloadUser")
	public String deleteDownloadUser(String[] ids) throws Exception{
		DownloadUser downloadUser = downloadUserService.selectDownloadUserById(ids[0]);
		String id = downloadUser.getArticle().getId();
		for(int i=0;i<ids.length;i++){
			downloadUserService.deleteDownloadUserById(ids[i]);
		}
		Article article = downloadUser.getArticle();
		article.setDownloadCount(article.getDownloadCount()-1);
		articleService.update(article);
		return "redirect:selectDownloadUserByArticleId.html?id="+id;
	};
	
	/**
	 * 
	* @Title: selectDownloadUserByParam
	* @author QuJie 
	* @date 2016-9-14 上午11:11:55  
	* @Description: 根据条件查询下载人信息 
	* @param @param downloadUser
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/selectDownloadUserByParam")
	public String selectDownloadUserByParam(DownloadUser downloadUser,Model model) throws Exception{
		if(downloadUser.getArticle().getName()!=null){
			List<DownloadUser> list = downloadUserService.selectDownloadUserByParam(downloadUser);
			model.addAttribute("list", list);
		}
		return "iss/ps/downloadUser/list";
	};
}
