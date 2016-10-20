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



import org.apache.http.protocol.HTTP;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.Category;
import ses.model.bms.CategoryAptitude;
import ses.model.bms.CategoryTree;
import ses.model.ppms.CategoryParam;
import ses.service.bms.CategoryAptitudeService;
import ses.service.bms.CategoryService;
import ses.service.ppms.CategoryParamService;

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
		/*String kind = "";
		for (int i = 0; i < kinds.length; i++) {
			kind+= kinds[i]+",";
		}*/
		category.setKind(kinds);
		category.setAcceptRange(request.getParameter("acceptRange"));
	    category.setParamStatus(0);
	    categoryService.updateByPrimaryKeySelective(category);
	    categoryParam.setName(request.getParameter("names"));
	    categoryParam.setValueType(request.getParameter("values"));
	    categoryParam.setCreatedAt(new Date());
		categoryParamService.insertSelective(categoryParam);
		String productNames= request.getParameter("products");
	/*	String productName="";
		for (int i = 0; i < productNames.length; i++) {
			productName+=productNames[i]+",";
		}*/
		categoryAptitude.setProductName(productNames);
		String saleNames = request.getParameter("sales");
		/*String saleName="";
	   	for (int i = 0; i < saleNames.length; i++) {
	   		saleName+=saleNames[i]+"";
		}*/
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
		/*String kind = "";
		for (int i = 0; i < kinds.length; i++) {
			kind+= kinds[i]+",";
		}*/
		category.setKind(kinds);
		category.setAcceptRange(request.getParameter("acceptRange"));
	    categoryService.updateByPrimaryKeySelective(category);
	    cateparam.setName(request.getParameter("names"));
	    cateparam.setValueType(request.getParameter("values"));
		cateparam.setUpdatedAt(new Date());
		categoryParamService.updateByPrimaryKeySelective(cateparam);
	
		String productNames= request.getParameter("products");
		/*String productName="";
		for (int i = 0; i < productNames.length; i++) {
			productName+=productNames[i]+",";
		}*/
		cateAptitude.setProductName(productNames);
		String saleNames = request.getParameter("sales");
	/*	String saleName="";
	   	for (int i = 0; i < saleNames.length; i++) {
	   		saleName+=saleNames[i]+"";
		}*/
		cateAptitude.setUpdatedAt(new Date());
		cateAptitude.setSaleName(saleNames);
	   	categoryAptitudeService.updateByPrimaryKeySelective(cateAptitude);
		return "redirect:getAll.html";
    }
    
    /**
  	* 
  	* @Title: 进入审核菜单页面
  	* @author Zhang XueFeng
  	* @Description:
  	* @param get1 
  	* @return String
    */
    @RequestMapping("/audit")
    public String get1(HttpServletRequest request,Model model){
    	List<Category> cate=categoryService.selectAll();
    	model.addAttribute("cate",cate);
    	return "ses/ppms/categoryparam/audit";
    	
    }
    /**
  	* 
  	* @Title: 进入查询页面
  	* @author Zhang XueFeng
  	* @Description:
  	* @param getAll 
  	* @return String
    */
    @RequestMapping("/look")
    public String getAll(HttpServletRequest request,Model model){
    	return "ses/ppms/categoryparam/search";
    	
    }
    /**
   	 * 
   	 * @Title: select
   	 * @author Zhang XueFeng/	
     * @Description:根据品目id查询参数信息进入审核页面
   	 * @param @return 	
   	 * @return void
     */ 
    @RequestMapping("/selectOne")
    public String select(HttpServletResponse response,String id,Model model){
   CategoryParam cateParam=categoryParamService.selectByPrimaryKey(id);
   Category category=categoryService.selectByPrimaryKey(id);
  CategoryAptitude caAptitude=categoryAptitudeService.queryByCategoryId(id);
    model.addAttribute("cateParam",cateParam);
    model.addAttribute("category",category);
    model.addAttribute("caAptitude",caAptitude);
    return "ses/ppms/categoryparam/auditinfo";
     }
    /**
   	 * 
   	 * @Title: select
   	 * @author Zhang XueFeng/	
     * @Description:根据品目id查询参数信息进入审核页面
   	 * @param @return 	
   	 * @return void
     */ 
    @RequestMapping("/change")
    public String change(HttpServletResponse response,String id,Category category){
         category.setParamStatus(1);
         categoryService.updateByPrimaryKey(category);
  
    return "ses/ppms/categoryparam/auditinfo";
     }
   /**
  	* 
  	* @Title: 导入excel中的内容
  	* @author Zhang XueFeng
  	* @Description:
  	* @param @return 
  	* @return String
    * @throws IOException 
    * @throws FileNotFoundException 
    */
     /*  @RequestMapping("/read")
 	public void read(Integer length) throws IOException {
 		   Workbook workbook;
 		   InputStream is = new FileInputStream(new File("D:\\add\\基础数据字典.xlsx"));
 			try {
 				workbook = new XSSFWorkbook(is);
 			} catch (FileNotFoundException e) {
 				workbook = new HSSFWorkbook(is);
 			}
 			Sheet sheet = workbook.getSheetAt(4);
 			for(int i=0;i<sheet.getPhysicalNumberOfRows();i++){
 				Row row = sheet.getRow(i);
 				if(row==null){
 					continue;
 				}
 				
 				Cell queType = row.getCell(0);
 				Cell name = row.getCell(1);
 				if(length==null){
 					length=1;
 				}
 				if(queType.toString().length()==length){
 					if(length!=1){
 						Category category = new Category();
 					List<Category> list=categoryService.selectAll();  
 					for(int k=0;k<list.size();k++){
 						//这个数据库的数据和queType的length-2的截取字符串对比 //查询语句lenngth-2;select  from category by
 						if((list.get(k).getCode()).equals(queType.toString().substring(0, length-2))){
 								category.setParentId(list.get(k).getId());
 								category.setName(name.toString());
 								category.setCode(queType.toString());
 								categoryService.insertSelective(category);
 						}
 					}
 			}else{
 				    Category category = new Category();
 				    category.setCode(queType.toString());
 				    category.setName(name.toString());
 				    category.setParentId("a");
 					categoryService.insertSelective(category);//插入语句
 					}
 				}
 			}
      read(length+=2);
     
     	
 	   }*/
     @RequestMapping("/import")
     public void imports() throws FileNotFoundException{
    	 Workbook workbook;
    	 InputStream is = new FileInputStream(new File("D:\\"));
	
    	 
    	 
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
