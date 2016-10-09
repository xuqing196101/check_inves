package ses.controller.sys.bms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.Category;
import ses.model.bms.CategoryAttachment;
import ses.model.bms.CategoryTree;
<<<<<<< Updated upstream
import ses.model.sms.SupplierTypeTree;
import ses.service.bms.CategoryAttchmentService;
=======
import ses.service.bms.CategoryAttachmentService;
>>>>>>> Stashed changes
import ses.service.bms.CategoryService;
import ses.util.PathUtil;

import com.alibaba.fastjson.JSON;
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
public class CategoryController extends BaseSupplierController {
	@Autowired
	private CategoryService categoryService;

	@Autowired
<<<<<<< Updated upstream
	private CategoryAttchmentService categoryAttchmentService;

	private Map<String, Object> listCategory = new HashMap<String, Object>();

=======
	private CategoryAttachmentService categoryAttachmentService;
	
	private  Map<String, Object> listCategory=new  HashMap<String, Object>();
	
>>>>>>> Stashed changes
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
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping("/findListByParent") public String selectAll(HttpServletRequest request,Category category){ if (category.getId()==null) { category.setId("a"); } List<Category> cateList=categoryService.listByParent(category.getId()); listCategory.put("cateList", cateList); listCategory.put("id", category.getId()); return JSON.toJSONString(listCategory); }
	 */

	/**
	 * 
	 * @Title: createtree
	 * @author zhangxuefeng
	 * @date 2016-7-18 下午4:27:01
	 * @Description:查询采购目的所有信息转换成json
	 * @param @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping("/createtree")
<<<<<<< Updated upstream
	public String getAll(Category category) {
		if (category.getId() == null) {
			category.setId("a");
		}
		Gson gson = new Gson();
		String list = "";
		List<CategoryTree> jList = new ArrayList<CategoryTree>();
		List<Category> cateList = categoryService.findTreeByPid(category.getId());
		for (Category cate : cateList) {
			List<Category> cList = categoryService.findTreeByPid(cate.getId());
			CategoryTree ct = new CategoryTree();
			if (!cList.isEmpty()) {
				ct.setIsParent("true");
			} else {
=======
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
>>>>>>> Stashed changes
				ct.setIsParent("false");
				
			}
			ct.setId(cate.getId());
			ct.setName(cate.getName());
			ct.setpId(cate.getParentId());
			jList.add(ct);
			list = gson.toJson(jList);
		}
		return list;
	}
<<<<<<< Updated upstream

=======
	 
>>>>>>> Stashed changes
	/**
	 * @Title: get
	 * @author zhangxuefeng
	 * @Description:创建新增页面
	 * @param @return
	 * @return String
	 */
	@RequestMapping("/get")
	public String get(HttpServletRequest request) {
		return "ses/bms/category/list";
	}

	/**
	 * 
	 * @Title: add
	 * @author zhangxuefeng
	 * @Description:创建新增页面
	 * @param @return
	 * @return String
	 */
	/*
	 * @RequestMapping("/add") public String addCategory(HttpServletRequest request,Model model,Category category){ model.addAttribute("id",category.getId()); return "ses/bms/category/add";
	 * 
	 * 
	 * }
	 */

	/**
	 * 
	 * @Title: search
	 * @author zhangxuefeng
	 * @Description:根据关键字展开树
	 * @param @return
	 * @return String
	 */
	@RequestMapping("/search")
	public void search(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String name = request.getParameter("name");
		map.put("name", name);
		List<Category> nodeList = categoryService.listByKeyword(map);
		super.writeJson(response, nodeList);
	}
<<<<<<< Updated upstream

	/**
	 * 
	 * @Title: save
	 * @author zhangxuefeng
	 * @date
	 * @Description:保存新增目录信息
	 * @param @return
	 * @return String @
	 */
	@RequestMapping("/save")
	public String save(@RequestParam("attaattach") MultipartFile[] attaattach, HttpServletRequest request, HttpServletResponse response, Category category) {
		category.setName(request.getParameter("name"));
		category.setPosition(Integer.parseInt(request.getParameter("position")));
		category.setParentId(request.getParameter("parentId"));
		category.setStatus(1);
		category.setCode(request.getParameter("code"));
		category.setDescription(request.getParameter("description"));
		category.setCreatedAt(new Date());

		// category.setIsEnd(request.getParameter("isEnd"));
		// if (category.getIsEnd().equals("0")) {
		// category.setIsEnd("true");
		// }else if (category.getIsEnd().equals("1")) {
		// category.setIsEnd("false");
		// }
		categoryService.insertSelective(category);
		upload(request, attaattach, category);
		return "redirect:get.html";
	}

	/**
	 * @Title: 上传附件
	 * @author Shen Zhenfei
	 * @date 2016-9-1 下午2:00:40
	 * @Description: 保存
	 * @param @return
	 * @return String
	 */

	public String upload(HttpServletRequest request, MultipartFile[] attaattach, Category category) {

		if (attaattach != null) {
			for (int i = 0; i < attaattach.length; i++) {
				String rootpath = (PathUtil.getWebRoot() + "picupload/").replace("\\", "/");
				/** 创建文件夹 */
=======
    /**
  	 * 
  	* @Title: save
  	* @author zhangxuefeng
  	* @date 
  	* @Description:保存新增目录信息 
  	* @param @return    
  	* @return String
    @   */ 
   @RequestMapping("/save")
   public String save(@RequestParam("attaattach") MultipartFile attaattach,
	          HttpServletRequest request, HttpServletResponse response,Category category){
	 
	  category.setName(request.getParameter("name"));
	  category.setPosition(Integer.parseInt(request.getParameter("position")));
	  category.setParentId(request.getParameter("parentId"));
	  category.setStatus(1);
	  category.setIsEnd(0);
	  category.setCode(request.getParameter("code"));
	  category.setDescription(request.getParameter("description"));
	  category.setCreatedAt(new Date());
	  
//	  category.setIsEnd(request.getParameter("isEnd"));
//	  if (category.getIsEnd().equals("0")) {
//		  category.setIsEnd("true");
//	}else if (category.getIsEnd().equals("1")) {
//		category.setIsEnd("false");
//	}
	  categoryService.insertSelective(category);
	  upload(request,attaattach,category);
	return "redirect:get.html";
   }
   
   /**
	* @Title: 上传附件
	* @author Shen Zhenfei
	* @date 2016-9-1 下午2:00:40 
	* @Description: 保存
	* @param @return      
	* @return String
	 */
   
	public void upload(HttpServletRequest request,MultipartFile attaattach,Category category){
		if(attaattach.getOriginalFilename() != null && !attaattach.getOriginalFilename().equals("")){
			System.out.println(111111);
		        String rootpath = (PathUtil.getWebRoot()+"picupload/").replace("\\", "/");
		        /** 创建文件夹 */
>>>>>>> Stashed changes
				File rootfile = new File(rootpath);
				if (!rootfile.exists()) {
					rootfile.mkdirs();
				}
<<<<<<< Updated upstream
				String fileName = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() + "_" + attaattach[i].getOriginalFilename();
				String filePath = rootpath + fileName;
				File file = new File(filePath);
				try {
					attaattach[i].transferTo(file);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				CategoryAttchment attachment = new CategoryAttchment();
				attachment.setCategory(new Category(category.getId()));
				attachment.setFileName(fileName);
				attachment.setCreatedAt(new Date());
				attachment.setContentType(attaattach[i].getContentType());
				attachment.setFileSize((float) attaattach[i].getSize());
				attachment.setAttchmentPath(filePath);

				categoryAttchmentService.insertSelective(attachment);
			}
		}
		return "redirect:get.html";

=======
		        String fileName = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() + "_" + attaattach.getOriginalFilename();		        
		        String filePath = rootpath+fileName;
		        File file = new File(filePath);
		        try {
					attaattach.transferTo(file);
				} catch (IllegalStateException e) {
					e.printStackTrace();				
					} catch (IOException e) {
				e.printStackTrace();
			}
				 CategoryAttachment attachment=new CategoryAttachment();
         		attachment.setCategory(new Category(category.getId()));
				attachment.setFileName(fileName);
				attachment.setCreatedAt(new Date());
				attachment.setContentType(attaattach.getContentType());
				attachment.setFileSize((float)attaattach.getSize());
				//路径==相对路径
				attachment.setAttchmentPath("picupload/"+fileName);
				categoryAttachmentService.insertSelective(attachment);
			}
>>>>>>> Stashed changes
	}

	/**
	 * 
<<<<<<< Updated upstream
	 * @Title: update
	 * @author zhangxuefeng
	 * @Description:获取需要修改的节点数据
	 * @param @return
	 * @return String
	 */

	@RequestMapping("/update")
	public void update(HttpServletResponse response, String id) {
		Category cate = categoryService.selectByPrimaryKey(id);
		CategoryAttchment attchment = categoryAttchmentService.selectByCategoryId(cate.getId());
		cate.setCategoryAttchment(attchment);
		super.writeJson(response, cate);
	}

	/**
	 * 
	 * @Title: edit
	 * @author Zhang XueFeng
	 * @Description:修改目录信息
	 * @param @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping("/edit")
	public String edit(@RequestParam("attaattach") MultipartFile[] attaattach, HttpServletRequest request, HttpServletResponse response, Category category) {
		category.setName(request.getParameter("name"));
		category.setParentId(request.getParameter("parentId"));
		category.setPosition(Integer.parseInt(request.getParameter("position")));
		category.setCode(request.getParameter("code"));
		category.setDescription(request.getParameter("description"));
		category.setUpdatedAt(new Date());
		/* category.setIsEnd(request.getParameter("isEnd")); */
		/*
		 * if (category.getIsEnd().equals("0")) { category.setIsEnd("true"); }else if (category.getIsEnd().equals("1")) { category.setIsEnd("false"); }
		 */
		categoryService.updateByPrimaryKeySelective(category);
		upload(request, attaattach, category);
		return "redirect:get.html";
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
	public String updateName(HttpServletRequest request, Category category) {
		categoryService.updateByPrimaryKeySelective(category);
		return "ses/bms/category/list";
	}

	/**
	 * 
	 * @Title: delete
	 * @author Zhang XueFeng/
	 * @Description:删除目录节点
	 * @param @return * @return String
	 */
	@RequestMapping("/del")
	public void delete(Category category) {
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
	public String change(HttpServletRequest request, Category category) {
		String ids = request.getParameter("ids");
		String[] cids = ids.split(",");
		for (int i = 0; i < cids.length; i++) {
			Category cate = categoryService.selectByPrimaryKey(cids[i]);
			if (cate.getStatus() == 0) {
				cate.setStatus(1);
			} else {
				cate.setStatus(0);
			}
			categoryService.updateByPrimaryKeySelective(cate);
=======
	* @Title: update
	* @author zhangxuefeng	
	* @Description:获取需要修改的节点数据
	* @param @return 
	* @return String
     */
   @RequestMapping("/update")
   public void update(HttpServletResponse response ,String id){
	   Category cate=categoryService.selectByPrimaryKey(id);
	   CategoryAttachment attchment=categoryAttachmentService.selectByCategoryId(cate.getId());
	   cate.setCategoryAttchment(attchment);
	   super.writeJson(response, cate);
   }
   /**
  	 * 
  	* @Title: edit
  	* @author Zhang XueFeng
  	* @Description:修改目录信息
  	* @param @return 
  	* @return String
       */  
   @ResponseBody
   @RequestMapping("/edit")
   public String  edit(@RequestParam("attaattach") MultipartFile attaattach,
	          HttpServletRequest request, HttpServletResponse response,Category category){
  	    category.setName(request.getParameter("name"));
		  category.setParentId(request.getParameter("parentId"));
		  category.setPosition(Integer.parseInt(request.getParameter("position")));
		  category.setCode(request.getParameter("code"));
		  category.setDescription(request.getParameter("description"));
		  category.setUpdatedAt(new Date());
		/*category.setIsEnd(request.getParameter("isEnd"));*/
		  /*if (category.getIsEnd().equals("0")) {
			  category.setIsEnd("true");
		}else if (category.getIsEnd().equals("1")) {
			category.setIsEnd("false");
		}*/
		
	  categoryService.updateByPrimaryKeySelective(category);
	  upload(request,attaattach,category);
	return "redirect:get.html";
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
	return "ses/bms/category/list";
   }
   /**
	 * 
	 * @Title: deleteById
	 * @author Zhang XueFeng/	
     * @Description:删除目录节点
	 * @param @return 	
	 * @return void
     */ 
   @RequestMapping("/del")
   public void deleteById(Category  category){
	   categoryService.deleteByPrimaryKey(category.getId());
   }
   /**
	 * 
	 * @Title: delete
	 * @author Zhang XueFeng/	
     * @Description:删除附件
	 * @param @return 	
	 * @return void
    */ 
   @RequestMapping("/deleted")
   public void delete(Category category){
	   categoryAttachmentService.deleteByPrimaryKey(category.getId());
	   
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
>>>>>>> Stashed changes
		}

		return "ses/bms/category/list";

	}
	
	/**
	 * @Title: findCategoryByType
	 * @author: Wang Zhaohua
	 * @date: 2016-10-3 下午4:40:39
	 * @Description: 查询品目树
	 * @param: @param response
	 * @param: @param category
	 * @param: @param supplierId
	 * @param: @throws IOException
	 * @return: void
	 */
	@RequestMapping(value = "find_category")
	public void findCategory(HttpServletResponse response, Category category, String supplierId) throws IOException {
		List<SupplierTypeTree> listSupplierTypeTrees = categoryService.findCategoryByType(category, supplierId);
		String json = JSON.toJSONStringWithDateFormat(listSupplierTypeTrees, "yyyy-MM-dd HH:mm:ss");
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
<<<<<<< Updated upstream
	
	/**
	 * @Title: queryCategory
	 * @author Song Biaowei
	 * @date 2016-10-8 下午2:24:48  
	 * @Description: 按照品目查询供应商 
	 * @param @param response
	 * @param @param category
	 * @param @param categoryIds
	 * @param @throws IOException      
	 * @return void
	 */
	@RequestMapping(value = "query_category")
	public void queryCategory(HttpServletResponse response, Category category, String categoryIds) throws IOException {
		List<String> list=Arrays.asList(categoryIds.split(","));
		List<SupplierTypeTree> listSupplierTypeTrees = categoryService.queryCategory(category, list);
		String json = JSON.toJSONStringWithDateFormat(listSupplierTypeTrees, "yyyy-MM-dd HH:mm:ss");
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
		response.getWriter().flush();
		response.getWriter().close();
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
	@RequestMapping("/read")
=======
	return "ses/bms/category/list";
	   
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
>>>>>>> Stashed changes
	public void read(Integer length) throws IOException {
		Workbook workbook;
		InputStream is = new FileInputStream(new File("D:\\add\\基础数据字典.xlsx"));
		try {
			workbook = new XSSFWorkbook(is);
		} catch (FileNotFoundException e) {
			workbook = new HSSFWorkbook(is);
		}
		Sheet sheet = workbook.getSheetAt(4);
		for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				continue;
			}

			Cell queType = row.getCell(0);
			Cell name = row.getCell(1);
			if (length == null) {
				length = 1;
			}
			if (queType.toString().length() == length) {
				if (length != 1) {
					Category category = new Category();
					List<Category> list = categoryService.selectAll();
					for (int k = 0; k < list.size(); k++) {
						// 这个数据库的数据和queType的length-2的截取字符串对比 //查询语句lenngth-2;select from category by
						if ((list.get(k).getCode()).equals(queType.toString().substring(0, length - 2))) {
							category.setParentId(list.get(k).getId());
							category.setName(name.toString());
							category.setCode(queType.toString());
							categoryService.insertSelective(category);
						}
					}
				} else {
					Category category = new Category();
					category.setCode(queType.toString());
					category.setName(name.toString());
					category.setParentId("a");
					categoryService.insertSelective(category);// 插入语句
				}
			}
<<<<<<< Updated upstream
		}
		read(length += 2);

	}

	/**
	 * *
	 * 
	 * @Title: 导出数据库中的内容
	 * @author Zhang XueFeng
	 * @Description:
	 * @param @return
	 * @return String
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@RequestMapping("/write")
	public void writeExcel() throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("采购目录表");
		HSSFRow row = sheet.createRow(0);
		// 创建单元格，并设置表头，且居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("编码");
		cell.setCellStyle(style);
		cell.setCellValue("目录名称 ");
		cell.setCellStyle(style);
		// 写入实体数据，从数据库得到
		List<Category> list = categoryService.selectAll();
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(i + 1);
			Category cate = list.get(i);
=======
     read(length+=2);
    
    	
	   }*/

    /** 
    * * 
    * @Title: 导出数据库中的内容
    * @author Zhang XueFeng
    * @Description:
    * @param @return 
    * @return String
    * @throws IOException 
    * @throws FileNotFoundException 
     */ 
   /* @RequestMapping("/write")
    public void writeExcel() throws IOException{
    	HSSFWorkbook wb = new HSSFWorkbook();
    	HSSFSheet sheet = wb.createSheet("采购目录表");
    	HSSFRow  row = sheet.createRow(0);
    	//创建单元格，并设置表头，且居中
    	HSSFCellStyle  style = wb.createCellStyle();
    	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    	HSSFCell cell = row.createCell(0);
    	cell.setCellValue("编码");
    	cell.setCellStyle(style);
    	cell.setCellValue("目录名称 ");
    	cell.setCellStyle(style);
    	//写入实体数据，从数据库得到
    	List<Category> list = categoryService.selectAll();
    	for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(i+1);
			Category cate =  list.get(i);
>>>>>>> Stashed changes
			row.createCell(0).setCellValue(cate.getCode());
			row.createCell(1).setCellValue(cate.getName());
		}
		try {
			FileOutputStream fout = new FileOutputStream("F:\\category\\xls");
			wb.write(fout);
			fout.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
<<<<<<< Updated upstream

=======
	
    	
    	
    	
    }*/
>>>>>>> Stashed changes
	}
}
