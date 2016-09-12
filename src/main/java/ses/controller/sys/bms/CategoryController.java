package ses.controller.sys.bms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;



import ses.model.bms.Category;
import ses.model.bms.CategoryAttchment;
import ses.model.bms.CategoryTree;
import ses.service.bms.CategoryAttchmentService;
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
	private CategoryAttchmentService categoryAttchmentService;
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
	@RequestMapping("/findListByParent")
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
	@RequestMapping("/createtree")
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
	@RequestMapping("/get")
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
    @RequestMapping("/add")
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
  	* @return String
    @   */ 
   @RequestMapping("/save")
   public String save(@RequestParam("attaattach") MultipartFile[] attaattach,
	          HttpServletRequest request, HttpServletResponse response,Category category){
	  category.setName(request.getParameter("name"));
	  category.setOrderNum(Integer.parseInt(request.getParameter("orderNum")));
	  category.setStatus(1);
	  category.setCode(Integer.parseInt(request.getParameter("code")));
	  category.setCreatedAt(new Date());
	  category.setDescription(request.getParameter("description"));
	  category.setIsEnd(request.getParameter("isEnd"));
	  if (category.getIsEnd().equals("0")) {
		  category.setIsEnd("true");
	}else if (category.getIsEnd().equals("1")) {
		category.setIsEnd("false");
	}
	  categoryService.insertSelective(category);
	  upload(request,attaattach,category);
	return "category/list";
   }
   
   /**
	* @Title: 上传附件
	* @author Shen Zhenfei
	* @date 2016-9-1 下午2:00:40 
	* @Description: 保存
	* @param @return      
	* @return String
	 */
   
	public String upload(HttpServletRequest request,MultipartFile[] attaattach,Category category){
		
		if(attaattach!=null){
			for(int i=0;i<attaattach.length;i++){
		        String rootpath = (request.getSession().getServletContext().getRealPath("/")+"upload/").replace("\\", "/");
		        /** 创建文件夹 */
				File rootfile = new File(rootpath);
				if (!rootfile.exists()) {
					rootfile.mkdirs();
				}
		        String fileName = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() + "_" + attaattach[i].getOriginalFilename();
		        String filePath = rootpath+fileName;
		        File file = new File(filePath);
		        try {
					attaattach[i].transferTo(file);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				 CategoryAttchment attachment=new CategoryAttchment();
				attachment.setCategory(new Category(category.getId()));
				attachment.setFileName(fileName);
				attachment.setCreatedAt(new Date());
				attachment.setUpdatedAt(new Date());
				attachment.setContentType(attaattach[i].getContentType());
				attachment.setFileSize((float)attaattach[i].getSize());
				attachment.setAttchmentPath(filePath);
				categoryAttchmentService.insert(attachment);
			}
		}
		return "redirect:list.html";
	}
    
    /**
	 * 
	* @Title: update
	* @author zhangxuefeng
	* @Description:创建修改页面
	* @param @return 
	* @return String
     */  
   @RequestMapping("/update")
   public String update(Category category,Model model){
	   Category cate=categoryService.selectByPrimaryKey(category.getId());
	   
	   model.addAttribute("category",cate);
	return "category/edit";
   }
   /**
  	 * 
  	* @Title: edit
  	* @author Zhang XueFeng
  	* @Description:修改目录信息
  	* @param @return 
  	* @return String
       */  
   @RequestMapping("/edit")
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
		  category.setUpdatedAt(new Date());
		  category.setCode(Integer.parseInt(request.getParameter("code")));
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
   /**
 	 * 
 	* @Title: rename
 	* @author Zhang XueFeng
 	* @Description:修改目录名称
 	* @param @return 
 	* @return String
      */  
   @RequestMapping("/rename")
   public String updateName(HttpServletRequest request,Category category){
	   categoryService.updateByPrimaryKeySelective(category);
	return "category/list";
   }
   
   
   /**
	 * 
	* @Title: delete
	* @author Zhang XueFeng
	* @Description:删除目录节点
	* @param @return 
	* @return String
     */ 
   @RequestMapping("/del")
   public void delete(Category  category){
	
	   categoryService.deleteByPrimaryKey(category.getId());
	
   }
   
   /**
  	 * 
  	* @Title: ros
  	* @author Zhang XueFeng
  	* @Description:修改状态（激活休眠）
  	* @param @return 
  	* @return String
       */ 
   @RequestMapping("/ros")
   public String change(HttpServletRequest request,Category category){
	   String ids=request.getParameter("ids");
	   String[] cids=ids.split(",");
	   for (int i = 0; i < cids.length; i++) {
		Category cate=categoryService.selectByPrimaryKey(cids[i]);
		if (cate.getStatus()==0) {
			cate.setStatus(1);
		}else{
			cate.setStatus(0);
		}
		categoryService.updateByPrimaryKeySelective(cate);
	}
		
	return "category/list";
	   
   }
   
   /**
 	 * 
 	* @Title: 导入excel中的内容
 	* @author Zhang XueFeng
 	* @Description:
 	* @param @return 
 	* @return String
      */ 
	   
	   public String readExcel(String fileName,Category category){
		   //创建webbook  对应一个excel文件
		  HSSFWorkbook wb = new HSSFWorkbook();
		  HSSFSheet sheet = wb.createSheet();
		  HSSFRow row = sheet.createRow((int) 0);
		  HSSFCellStyle style = wb.createCellStyle();
		  style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设为居中格式
		  
	  HSSFCell cell = row.createCell((short) 0);
//		  cell.setCellValue(value);
		  
		  //写入实体数据  实际应用中这些数据从数据库得到
		//  List list =Create
		   //row = sheet.createRow(rownum);
		   //创建单元格 设置值
		   row.createCell((short) 0).setCellValue((String)category.getId());
		  // row.createCell(column);
		return fileName;
	   }
   
   

	}
	


