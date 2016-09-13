package iss.controller.ps;

import iss.model.ps.Article;
import iss.model.ps.ArticleType;
import iss.model.ps.DownloadUser;
import iss.service.ps.DownloadUserService;

import java.util.List;

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
		List<DownloadUser> downloadUserList = downloadUserService.selectByArticleId(article.getId());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page==null?1:page,Integer.parseInt(config.getString("pageSize")));
		model.addAttribute("list", new PageInfo<DownloadUser>(downloadUserList));
		return "downloadUser/list";
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
	public void deleteDownloadUser(String[] ids) throws Exception{
		System.out.println(ids);
	};
}
