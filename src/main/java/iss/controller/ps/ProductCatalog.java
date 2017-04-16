package iss.controller.ps;

import iss.bean.IssData;
import iss.model.ps.DataDownload;
import iss.service.ps.DataDownloadService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.Category;
import ses.model.bms.DictionaryData;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.constant.Constant;
import common.utils.JdcgResult;
/** 
*  
* @Description: 页面产品目录展示
* @author LiWanLin
* @date 2017-3-5上午9:30:53
 */
@Controller
@RequestMapping("/catalog")
public class ProductCatalog {
	@Autowired
	private DataDownloadService dataDownloadService;
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	@Autowired
	private CategoryService categoryService;
	@RequestMapping("/catalogList")
	public String catalogList(Integer page,Category category,HttpServletRequest request,Model model) {
		HashMap<String,Object> map = new HashMap<>();
		if(category!=null){
			if(category.getName()!=null && !category.getName().equals("")){
				map.put("name", category.getName());
			}
		}
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSizeArticle")));
		List<Category> Categorys = categoryService.findCategoryByName(map);
		Map<String, Object> indexMapper = new HashMap<String, Object>();
		IssData.topNews(indexMapper);
		model.addAttribute("indexMapper", indexMapper);
		model.addAttribute("list", new PageInfo<Category>(Categorys));
		return "iss/ps/catalog/catalogList";
	}
	@RequestMapping("/parameterList")
	public String parameterList(Integer page,Category category,HttpServletRequest request,Model model) {
		HashMap<String,Object> map = new HashMap<>();
		if(category!=null){
			if(category.getName()!=null && !category.getName().equals("")){
				map.put("name", category.getName());
			}
		}
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSizeArticle")));
		List<Category> Categorys = categoryService.findCategoryByNameOrClassify(map);
		model.addAttribute("list", new PageInfo<Category>(Categorys));
		Map<String, Object> indexMapper = new HashMap<String, Object>();
		IssData.topNews(indexMapper);
		model.addAttribute("indexMapper", indexMapper);
		return "iss/ps/catalog/parameterlist";
	}
	
	
}
