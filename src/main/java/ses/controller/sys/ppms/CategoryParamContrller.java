package ses.controller.sys.ppms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;

import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.service.bms.CategoryService;
import ses.service.ppms.CategoryParamService;

@Controller
@Scope("prototype")
@RequestMapping("/categoryparam")
public class CategoryParamContrller {
	@Autowired
	private CategoryParamService categoryParamService;
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * 
	* @Title: createtree
	* @author zhangxuefeng
	* @date 2016-7-18 下午4:27:01  
	* @Description:查询采购目的所有信息转换成json
	* @param @return  
	* @return String
	 */
	@RequestMapping("/createtree")
	public String createtree(Category category){
		if(category.getId()==null){
			category.setId("a");
	}	Gson gson = new Gson();
	    String list="";
		List<CategoryTree> jList=new ArrayList<CategoryTree>(); 
		List<Category> cateList=categoryService.findTreeByPid(category.getId());
		for(Category cate:cateList){
			List<Category> cList=categoryService.findTreeByPid(cate.getId());
			CategoryTree ct=new CategoryTree();
			if(!cList.isEmpty()){
				ct.setIsParent("true");
			}else{
				ct.setIsParent("false");
			}
			ct.setId(cate.getId());
			ct.setName(cate.getName());
			ct.setpId(cate.getParentId());
			jList.add(ct);
			list=gson.toJson(jList);
	}
		return list;
		
	}
	/**
	* @Title: get
	* @author zhangxuefeng
	* @Description:创建新增页面
	* @param @return    
	* @return String
     */  
	@RequestMapping("/get")
	public String get(HttpServletRequest request){
		return "ses/ppms/categoryparam/add";
	}
}
