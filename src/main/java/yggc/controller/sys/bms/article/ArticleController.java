package yggc.controller.sys.bms.article;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSON;

import yggc.controller.sys.bms.LoginController;
import yggc.model.bms.Article;
import yggc.model.bms.User;
import yggc.model.iss.ArticleType;
import yggc.service.bms.ArticleService;
import yggc.service.bms.UserServiceI;

/**
 * 
 *<p>Title:ArticleController</p>
 *<p>Description:信息管理接口类</p>
 *<p>Company:yggc</p>
 * @author Mrlovablee
 *@date 2016-8-10上午10:35:15
 */
@Controller
@Scope("prototype")
@RequestMapping("/article")
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	private Logger logger = Logger.getLogger(LoginController.class); 
	
	@RequestMapping("/getAll")
	public String getAll(Model model){
		List<Article> list = articleService.selectAllArticle();
		model.addAttribute("list", list);
		logger.info(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
		return "article/list";
	}
}
