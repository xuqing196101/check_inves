package ses.controller.sys.ppms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import org.apache.poi.ss.usermodel.Workbook;
import org.apache.solr.common.util.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.Category;
import ses.model.bms.CategoryAptitude;
import ses.model.bms.CategoryTree;
import ses.model.oms.Orgnization;
import ses.model.ppms.CategoryParam;
import ses.service.bms.CategoryAptitudeService;
import ses.service.bms.CategoryService;
import ses.service.oms.OrgnizationServiceI;
import ses.service.ppms.CategoryParamService;



import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;



@Controller
@Scope("prototype")
@RequestMapping("/categoryparam")
public class CategoryParamContrller extends BaseSupplierController{
	@Autowired
	private CategoryParamService categoryParamService;//品目参数
	@Autowired
	private CategoryService categoryService;//品目
	@Autowired
	private CategoryAptitudeService categoryAptitudeService;//品目资质
	
	@Autowired
	private OrgnizationServiceI orgnizationServiceI;
	
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
	public String getAll(Category category){
		if(category.getId()==null){
				category.setId("0");
		}	Gson gson = new Gson();
		    String list="";
			List<CategoryTree> jList=new ArrayList<CategoryTree>(); 
			List<Category> cateList=categoryService.findTreeByPid(category.getId());
			for(Category cate:cateList){
				List<Category> cList=categoryService.findTreeByPid(cate.getId());
				CategoryTree ct=new CategoryTree();
				if(!cList.isEmpty()){
					ct.setIsParent("true");
					cate.setIsEnd(1);
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
	* @Title: getAll
	* @author zhangxuefeng
	* @Description:进入参数页面
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
	public String save(HttpServletRequest request,CategoryParam categoryParam ,CategoryAptitude categoryAptitude){
		Category category=categoryService.selectByPrimaryKey(request.getParameter("categoryId"));
		category.setCreatedAt(new Date());
		categoryParam.setCategory(category);
		categoryAptitude.setCategory(category);
		category.setIsPublish(Integer.parseInt(request.getParameter("ispublish")));
		if (category.getIsPublish().equals("0")) {
		category.setIsPublish(0);
	    }else if (category.getIsPublish().equals("1")) {
		category.setIsPublish(1);
	    }
		String kinds=request.getParameter("kinds");
		
		category.setKind(kinds);
		category.setAcceptRange(request.getParameter("acceptRange"));
	    category.setParamStatus(0);
	    categoryService.updateByPrimaryKeySelective(category);
	    categoryParam.setName(request.getParameter("names"));
	    categoryParam.setValueType(request.getParameter("values"));
	    categoryParam.setCreatedAt(new Date());
		categoryParamService.insertSelective(categoryParam);
		String productNames= request.getParameter("products");
	
		categoryAptitude.setProductName(productNames);
		String saleNames = request.getParameter("sales");
		
	   	categoryAptitude.setCreatedAt(new Date());
	   	categoryAptitude.setSaleName(saleNames);
	   	categoryAptitudeService.insertSelective(categoryAptitude);
		return "ses/ppms/categoryparam/add";
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
	return "ses/ppms/categoryparam/add";
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
     
     /**
   	 * 
   	 * @Title: findOne
   	 * @author Zhang XueFeng/	
     * @Description:根据品目id查询参数信息
   	 * @param @return 	
   	 * @return void
     */ 
    @RequestMapping("/findOne")
    public String findOne(HttpServletResponse response,String id,Model model){
   CategoryParam cateParam=categoryParamService.selectByPrimaryKey(id);
   Category category=categoryService.selectByPrimaryKey(id);
  CategoryAptitude caAptitude=categoryAptitudeService.queryByCategoryId(id);
    model.addAttribute("cateParam",cateParam);
    model.addAttribute("category",category);
    model.addAttribute("caAptitude",caAptitude);
    return "ses/ppms/categoryparam/edit";
     }
    /**
   	 * 
   	 * @Title: edit
   	 * @author Zhang XueFeng/	
     * @Description：更新参数信息
   	 * @param @return 	
   	 * @return void
     */ 
    @RequestMapping("/edit")
    public String edit(CategoryParam cateparam, CategoryAptitude cateAptitude,  HttpServletRequest request){
    	Category category = categoryService.selectByPrimaryKey(request.getParameter("categoryId"));
    	category.setUpdatedAt(new Date());
    	cateparam.setCategory(category);
    	cateAptitude.setCategory(category);
    	category.setIsPublish(Integer.parseInt(request.getParameter("ispublish")));
		if (category.getIsPublish().equals("0")) {
		category.setIsPublish(0);
	    }else if (category.getIsPublish().equals("1")) {
		category.setIsPublish(1);
	    }
		String kinds=request.getParameter("kinds");
		
		category.setKind(kinds);
		category.setAcceptRange(request.getParameter("acceptRange"));
	    categoryService.updateByPrimaryKeySelective(category);
	    cateparam.setName(request.getParameter("names"));
	    cateparam.setValueType(request.getParameter("values"));
		cateparam.setUpdatedAt(new Date());
		categoryParamService.updateByPrimaryKeySelective(cateparam);
	
		String productNames= request.getParameter("products");
		
		cateAptitude.setProductName(productNames);
		String saleNames = request.getParameter("sales");

		cateAptitude.setUpdatedAt(new Date());
		cateAptitude.setSaleName(saleNames);
	   	categoryAptitudeService.updateByPrimaryKeySelective(cateAptitude);
		return "redirect:getAll.html";
    }
    
    @RequestMapping("/import")
    public void imports() throws FileNotFoundException{
   	/* Workbook workbook;
   	 InputStream is = new FileInputStream(new File("D:\\"));*/
    }
    /**********************************参数审核*************************************************************************/
    /**
     * 
     * @Title: change
     * @author Zhang XueFeng/	
     * @Description:进入审核列表页面
     * @param @return 	
     * @return void
     */ 
    @RequestMapping("/change")
    public String change(HttpServletResponse response,String id,Category category){
    	return "ses/ppms/categoryparam/audit";
    }
    
    /**
   	 * 
   	 * @Title: searchCategory
   	 * @author Zhang XueFeng/	
     * @Description:根据品目状态查询列表
   	 * @param 
   	 * @return String
     */ 
    @RequestMapping("/search_category")
    public String searchCategory(HttpServletResponse response,HttpServletRequest request,Model model,Integer page){
    	String status = request.getParameter("paramstatus");
    	Integer paramstatus = Integer.parseInt(status);
    	if(page==null){
			page=1;
		}
    	Map<String, Integer> map = new HashMap<String, Integer>();
    	map.put("paramStatus", paramstatus);
    	map.put("page", page);
    	List<Category> cate = categoryService.listByParamstatus(map);
    	model.addAttribute("list",new PageInfo<Category>(cate));
    	model.addAttribute("cate",cate);
    return "ses/ppms/categoryparam/audit";
     }
    /**
   	 * 
   	 * @Title: queryCategory
   	 * @author Zhang XueFeng/	
     * @Description:创建审核详情页面
   	 * @param 
   	 * @return String
     */
    @RequestMapping("/query_category")
    public String queryCategory(Model model,String id){
    	CategoryParam cateParam = categoryParamService.selectByPrimaryKey(id);
    	Category category = categoryService.selectByPrimaryKey(id);
    	CategoryAptitude caAptitude = categoryAptitudeService.queryByCategoryId(id);
    	model.addAttribute("cate",cateParam);
    	model.addAttribute("category",category);
    	model.addAttribute("caAptitude",caAptitude);
		return "ses/ppms/categoryparam/auditinfo";
    }
   
    /**
   	 * @Title: queryCategory
   	 * @author Zhang XueFeng/	
     * @Description:审核页面添加公示范围，改变参数状态
   	 * @param id
   	 * @return void
     */
    @RequestMapping("/audit_param")
    public void auditParam(HttpServletRequest request,Model model,String id,Category category){
    	category.setParamPublishRange(Integer.parseInt(request.getParameter("range")));
    	category.setParamStatus(Integer.parseInt(request.getParameter("storage")));
    	categoryService.updateByPrimaryKeySelective(category);
    }
    /**
     * @Title: publish
     * @author Zhang XueFeng/	
     * @Description:进入待发布列表页面
     * @param id
     * @return string
     */
    @RequestMapping("/publish")
    public String publish(HttpServletRequest request,Model model,Integer page){
    	if(page==null){
			page=1;
		}
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("page", page);
    	List<Category> cate = categoryService.findByStatus(map);
    	model.addAttribute("list",new PageInfo<Category>(cate));
    	model.addAttribute("page",page);
    	model.addAttribute("cate",cate);
    	return "ses/ppms/categoryparam/unrelease";
    	
    }
    /**
     * @Title: queryCategory
     * @author Zhang XueFeng/	
     * @Description:进入发布页面
     * @param id
     * @return string
     */
    @RequestMapping("/publish_category")
    public String publishCategory(HttpServletRequest request,Model model,String id){
    	CategoryParam cateParam = categoryParamService.selectByPrimaryKey(id);
    	Category category = categoryService.selectByPrimaryKey(id);
    	CategoryAptitude caAptitude = categoryAptitudeService.queryByCategoryId(id);
    	model.addAttribute("cate",cateParam);
    	model.addAttribute("category",category);
    	model.addAttribute("caAptitude",caAptitude);
    	return "ses/ppms/categoryparam/publish";
    	
    } 
    /***********************************************参数分配*******************************************************************/ 
    
    
    /**
     * @Title: query_orgnization
     * @author Zhang XueFeng/	
     * @Description:按照事业部门  负责人查询
     * @param id
     * @return string
     */
    @RequestMapping("/get_allocate")
    public String getAllocate(HttpServletRequest request){
        return "ses/ppms/categoryparam/allocate";    	
    }
    /**
     * @Title: query_orgnization
     * @author Zhang XueFeng/	
     * @Description:按照事业部门  负责人查询
     * @param id
     * @return string
     */
    @RequestMapping("/query_orgnization")
    public String queryOrgnization(HttpServletRequest request,Model model,Integer page){
    	/*String name = request.getParameter("name");
    	String princinpal = request.getParameter("princinpal");
    	if(page==null){
			page=1;
		}
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("name", name);
    	map.put("princinpal", princinpal);
    	map.put("page", page);
    	List<Orgnization>  cate = orgnizationServiceI.findByName(map);
    	model.addAttribute("list",new PageInfo<Orgnization>(cate));
    	model.addAttribute("cate",cate);*/
    	return "ses/ppms/categoryparam/allocate";
    }
    
   /**************************************************按产品查询和按目录查询************************************************************************/
    /**
     * @Title: getCategoryparam
     * @author Zhang XueFeng/	
     * @Description:进入查询页面
     * @param id
     * @return string
     */
    @RequestMapping("/get_categoryparam")
    public String getCategoryparam(){
    	return "ses/ppms/categoryparam/search";
    }
    /**
     * @Title: checkCategoryparam
     * @author Zhang XueFeng/	
     * @Description:进入按目录查询页面
     * @param id
     * @return string
     */ 
    @RequestMapping("/check_categoryparam")
    public String checkCategoryparam(){
	    return "ses/ppms/categoryparam/searchinfo";
 }  
    /**
     * @Title: searchOrgnization
     * @author Zhang XueFeng/	
     * @Description:根据产品名称进行模糊查
     * @param id
     * @return string
     */ 
    @RequestMapping("/search_orgnization")
    public String searchOrgnization(HttpServletRequest request,Model model,Integer page,Orgnization orgnization){
    	String name = request.getParameter("name");
    	if(page==null){
			page=1;
		}
    	/*Map<String, Object> map = new HashMap<String, Object>();
    	map.put("name", name);
    	map.put("page", page);
    	List<Category> cate = categoryService.listByKeyword(map);
    	model.addAttribute("list",new PageInfo<Category>(cate));
    	model.addAttribute("cate",cate);*/
		return "ses/ppms/categoryparam/search";
    }
     /**
      * @Title: listByCategoryId
      * @author: Wang Zhaohua
      * @date: 2016-10-10 下午8:23:29
      * @Description: 根据品目 ID 查找技术参数
      * @param: @param model
      * @param: @param categoryId
      * @param: @return
      * @return: String
      */
    @RequestMapping("/search_info")
    public void searchInfo(String id,HttpServletResponse response){
    	List<CategoryAptitude> categoryAptitudes = categoryAptitudeService.findProductByCategoryId(id);
    	List<CategoryParam>  categoryParams = categoryParamService.findListByCategoryId(id);
    	Category cate = categoryService.selectByPrimaryKey(id);	
    	cate.setCategoryParams(categoryParams);
    	cate.setCategoryAptitudes(categoryAptitudes);
    	/*CategoryAptitude caAptitude=categoryAptitudeService.queryByCategoryId(id);*/
    	 super.writeJson(response, cate);
    } 
 
 /***********************************************************************************************************************************/
     @RequestMapping(value = "list_by_category_id")
     public String listByCategoryId(Model model, String categoryId, String productsId, Integer sign) {
    	 Map<String, String> param = new HashMap<String, String>();
    	 param.put("categoryId", categoryId);
    	 if (sign == 1) {
    		 param.put("productsId", productsId);
    	 }
    	 List<CategoryParam> list = categoryParamService.findParamByCategoryIdAndProductsId(param, sign);
    	 model.addAttribute("list", list);
    	 model.addAttribute("categoryId", categoryId);
    	 model.addAttribute("productsId", productsId);
    	 return "ses/sms/supplier_register/add_param";
     }
}
