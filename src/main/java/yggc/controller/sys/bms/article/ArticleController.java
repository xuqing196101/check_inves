package yggc.controller.sys.bms.article;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

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

}
