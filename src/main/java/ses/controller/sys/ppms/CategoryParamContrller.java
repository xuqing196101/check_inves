package ses.controller.sys.ppms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.print.DocFlavor.STRING;
import javax.servlet.http.HttpServletRequest;

import org.apache.zookeeper.server.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;

import ses.model.bms.Category;
import ses.model.bms.CategoryAptitude;
import ses.model.bms.CategoryTree;
import ses.model.ppms.CategoryParam;
import ses.service.bms.CategoryAptitudeService;
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
	@Autowired
	private CategoryAptitudeService categoryAptitudeService;
	
	private List<CategoryParam> list;
	
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
	@RequestMapping("/getAll")
	public String get(HttpServletRequest request){
		return "ses/ppms/categoryparam/add";
	}
	/**
	 * 
	* @Title: save
	* @author zhangxuefeng
	* @date 2016-7-18 下午4:27:01  
	* @Description:添加参数
	* @param @return  
	* @return String
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request,Category category,CategoryParam categoryParam,CategoryAptitude categoryAptitude){
		category.setId(request.getParameter("id"));
		category.setIsPublish(Integer.parseInt(request.getParameter("ispublish")));
		if (category.getIsPublish().equals("a")) {
		category.setIsPublish(0);
	}else if (category.getIsPublish().equals("b")) {
		category.setIsPublish(1);
	}
		
		categoryService.insertSelective(category);
		category.setAcceptRange(request.getParameter("acceptRange"));
	category.setParamStatus(0);
		/*categoryParam.setName(request.getParameter("name"));
		categoryParam.setValueType(Integer.parseInt(request.getParameter("valueType")));*/
		StringBuffer sb = new StringBuffer();
		String[] name=request.getParameterValues("name");
		for (int i = 0; i < name.length; i++) {
			sb.append(name[i]);
		}
		categoryParam.setName(sb.toString());
	
		String[] valueType=(request.getParameterValues("valueType"));
		for (int i = 0; i < valueType.length; i++) {
			if (valueType[i].equals("a")) {
			categoryParam.setValueType(0);
		}else if (valueType[i].equals("b")) {
				categoryParam.setValueType(1);
			}else if (valueType[i].equals("c")) {
				categoryParam.setValueType(2);
			}
		}
		/*String[] productName= request.getParameterValues("productName");
		for (int i = 0; i < productName.length; i++) {
			sb.append(productName[i]);
		}
		categoryAptitude.setProductName(sb.toString());
		String[] saleName = request.getParameterValues("saleName");
		for (int i = 0; i < saleName.length; i++) {
			sb.append(saleName[i]);
			
		}
	    categoryAptitude.setSaleName(sb.toString());
	     String[] type=request.getParameterValues("type");
	     for (int i = 0; i < type.length; i++) {
		    if (type[i].equals("a")) {		
		category.setKind(0);
			}else if (type[i].equals("b")) {
				category.setKind(1);
		}else if (type[i].equals("c")) {
				category.setKind(2);
			}else if (type[i].equals("d")) {
				category.setKind(3);			}
		}	*/
		String proName=request.getParameter("productName");
		return "redirect:getAll.html";
		
	}
	 /**
 	 * 
 	* @Title: rename
 	* @author Zhang XueFeng
 	* @Description:修改品目名称
 	* @param @return 
 	* @return String
      */  
   @RequestMapping("/rename")
   public String updateName(HttpServletRequest request,Category category){
	   categoryService.updateByPrimaryKeySelective(category);
	return "ses/ppms/categoryparam/list";
   }
   
   /**
  	 * 
  	 * @Title: delete
  	 * @author Zhang XueFeng/	
     * @Description:删除目录节点
  	 * @param @return 	
  	 * @return void
       */ 
     @RequestMapping("/del")
     public void delete(Category  category){
  	   categoryService.deleteByPrimaryKey(category.getId());
  	
     }
}
