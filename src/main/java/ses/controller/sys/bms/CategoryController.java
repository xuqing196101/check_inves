package ses.controller.sys.bms;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.ArticleFile;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.service.bms.CategoryService;

import com.google.gson.Gson;


/**
 * 
* @Title:CategoryController
* @Description: 采购目录管理控制类
* @author zhangxuefeng
* @date 
 */
@Controller
@Scope("prototype")
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	private  Map<String, Object> listCategory=new  HashMap<String, Object>();
	
	public Map<String, Object> getListCategory() {
		return listCategory;
	}
	public void setListCategory(Map<String, Object> listCategory) {
		this.listCategory = listCategory;
	}
	/**
	 * 
	* @Title: selectAll
	* @author zhangxuefeng
	* @date 2016-7-18 下午4:27:01  
	* @Description:根据父 id生成列表 
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@ResponseBody
	@RequestMapping(value="findListByParent")
	public String selectAll(HttpServletRequest request,Category category){
		if (category.getId()==null) {
			category.setId("a");
		}
		Gson gson=new Gson();
		List<Category> cateList=categoryService.listByParent(category.getId());
		listCategory.put("cateList", cateList);
		listCategory.put("id", category.getId());
		return gson.toJson(listCategory);
	}
	/**
	 * 
	* @Title: getCategoryAll
	* @author zhangxuefeng
	* @date 2016-7-18 下午4:27:01  
	* @Description:查询采购目的所有信息转换成json
	* @param @return  
	* @return String
	 */
	@ResponseBody
	@RequestMapping(value="/createtree")
	public String getAll(Category category){
		if(category.getId()==null){
			category.setId("a");
		}
		List<CategoryTree> jList=new ArrayList<CategoryTree>(); 
		List<Category> cateList=categoryService.findTreeByPid(category.getId());
		Gson gson=new Gson();
		String list="";
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
			ct.setpId(cate.getAncestry());
			jList.add(ct);
			list = gson.toJson(jList);
	}
		return list;
	}
	/**
	 * 
	 *
	 * 
	 * */
	@RequestMapping(value="/get")
	public String get(HttpServletRequest request){
		return "category/list";
	}

	/**
	 * 
	* @Title: add
	* @author zhangxuefeng
	* @Description:创建新增页面
	* @param @return    
	* @return String
     */  
    @RequestMapping(value = "/add")
    public String addCategory(HttpServletRequest request,Model model,Category category){  
    	model.addAttribute("id",category.getId());
        return "category/add";  
		
		
	}
    /**
  	 * 
  	* @Title: 保存新增目录信息 
  	* @author zhangxuefeng
  	* @date 
  	* @Description:
  	* @param @return
  	* @param @throws Exception      
  	* @return String
    @   */ 
   @RequestMapping(value="save")
   public String save(HttpServletRequest request,Category category,Model model){
	  category.setName(request.getParameter("name"));
	  category.setOrderNum(Integer.parseInt(request.getParameter("orderNum")));
	 category.setStatus(Integer.parseInt(request.getParameter("status")));
	 if (category.getStatus().equals("0")) {
		category.setStatus(0);
	}else if (category.getStatus().equals("1")) {
		category.setStatus(1);
	}
	  category.setCode(Integer.parseInt(request.getParameter("code")));
	  category.setAttchment(request.getParameter("attchment"));
	  category.setDescription(request.getParameter("description"));
	  category.setIsEnd(request.getParameter("isEnd"));
	  if (category.getIsEnd().equals("0")) {
		  category.setIsEnd("true");
	}else if (category.getIsEnd().equals("1")) {
		category.setIsEnd("false");
	}
	  categoryService.insertSelective(category);
	return "category/list";
   }
    
    /**
	 * 
	* @Title: update
	* @author zhangxuefeng
	* @Description:创建修改页面
	* @param @return 
	* @return String
     */  
   @RequestMapping(value="/update")
   public String update(Category category,Model model){
	   Category cate=categoryService.selectByPrimaryKey(category.getId());
	   
	   model.addAttribute("category",cate);
	return "category/edit";
   }
   /**
  	 * 
  	* @Title: edit
  	* @author zhangxuefeng
  	* @Description:修改目录休息
  	* @param @return 
  	* @return String
       */  
   @RequestMapping(value="/edit")
   public String  edit(HttpServletRequest request,Category category){
	      category.setId(request.getParameter("id"));
	   	  category.setName(request.getParameter("name"));
		  category.setAncestry(request.getParameter("ancestry"));
		  if (category.getAncestry().equals("true")) {
			category.setAncestry("0");
		}else if (category.getAncestry().equals("false")) {
			category.setAncestry("1");
		}
		  category.setOrderNum(Integer.parseInt(request.getParameter("orderNum")));
		  category.setStatus(Integer.parseInt(request.getParameter("status")));
		  category.setCode(Integer.parseInt(request.getParameter("code")));
		  category.setAttchment(request.getParameter("attchment"));
		  category.setDescription(request.getParameter("description"));
		  category.setIsEnd(request.getParameter("isEnd"));
		  if (category.getIsEnd().equals("0")) {
			  category.setIsEnd("true");
		}else if (category.getIsEnd().equals("1")) {
			category.setIsEnd("false");
		}
	  categoryService.updateByPrimaryKey(category);
	return "category/list";
   }
   //图片信息
   private List<File> attach;
   private List<String> attchFileName;
   private List<String> attchContentType;
   
   //图片信息
   private File picattch;
   private String picattchFileName;
   private String picattchContentType;
   public List<File> getAttach() {
	return attach;
}
   public void setAttach(List<File> attach) {
	this.attach = attach;
}
   public List<String> getAttchFileName() {
	return attchFileName;
}
   public void setAttchFileName(List<String> attchFileName) {
	this.attchFileName = attchFileName;
   		}
   public List<String> getAttchContentType() {
	return attchContentType;
   		}
   public void setAttchContentType(List<String> attchContentType) {
	this.attchContentType = attchContentType;
   		}
   public File getPicattch() {
	return picattch;
   		}
   public void setPicattch(File picattch) {
	this.picattch = picattch;
   		}
   public String getPicattchFileName() {
	return picattchFileName;
   		}
   public void setPicattchFileName(String picattchFileName) {
	this.picattchFileName = picattchFileName;
   		}
   public String getPicattchContentType() {
	return picattchContentType;
   		}
   public void setPicattchContentType(String picattchContentType) {
	this.picattchContentType = picattchContentType;
   		}
/**
 * 附件上传
* @Title: upload
* @author zhangxuefeng
* @param @param newsService
* @param @return      
* @return List<Attachment>
 */
   
   public List<ArticleFile> upload(HttpServletRequest request){
	return null;
	   
	   
	   
   }
   

	}
	


