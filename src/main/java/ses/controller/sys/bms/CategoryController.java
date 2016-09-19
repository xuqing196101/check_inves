package ses.controller.sys.bms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jsqlparser.statement.create.index.CreateIndex;

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
	@Autowired
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
		Gson gson = new Gson();
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
			ct.setpId(cate.getParentId());
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
    @RequestMapping("/add")
    public String addCategory(HttpServletRequest request,Model model,Category category){  
    	model.addAttribute("id",category.getId());
        return "ses/bms/category/add";  
		
		
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
	  category.setPosition(Integer.parseInt(request.getParameter("position")));
	  category.setStatus(1);
	  category.setCode(request.getParameter("code"));
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
	return "ses/bms/category/list";
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
		        String fileName = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() + "_" + attaattach[i].getOriginalFilename();		        String filePath = rootpath+fileName;
		        File file = new File(filePath);
		        try {
					attaattach[i].transferTo(file);
				} catch (IllegalStateException e) {
					e.printStackTrace();				} catch (IOException e) {
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
	return "ses/bms/category/edit";
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
		  category.setParentId(request.getParameter("parentId"));
		  category.setPosition(Integer.parseInt(request.getParameter("position")));
		  category.setUpdatedAt(new Date());
		  category.setCode(request.getParameter("code"));
		  category.setDescription(request.getParameter("description"));
		  category.setIsEnd(request.getParameter("isEnd"));
		  if (category.getIsEnd().equals("0")) {
			  category.setIsEnd("true");
		}else if (category.getIsEnd().equals("1")) {
			category.setIsEnd("false");
		}
	  categoryService.updateByPrimaryKey(category);
	return "ses/bms/category/list";
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
	 * @Title: delete
	 * @author Zhang XueFeng/	
	 * 
     * @Description:删除目录节点
	 * @param @return 	* @return String
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
	
    @RequestMapping("/read")
	public void read(Integer length) throws IOException {
		   Workbook workbook;
		   InputStream is = new FileInputStream(new File("D:\\add\\基础数据字典.xlsx"));
			try {
				workbook = new XSSFWorkbook(is);
			} catch (FileNotFoundException e) {
				workbook = new HSSFWorkbook(is);
			}
			Sheet sheet = workbook.getSheetAt(5);
			for(int i=0;i<sheet.getPhysicalNumberOfRows();i++){
				Row row = sheet.getRow(i);
				if(row==null){
					continue;
				}
				Category category = new Category();
				Cell queType = row.getCell(0);
				if(length==null){
					length=1;
				}
				if(queType.toString().length()==length){
					if(length!=1){
					List<Category> list=categoryService.selectAll();  
					for(int k=0;k<list.size();k++){
						if(list.get(k).equals(list.toString().substring(0, length-2))){//这个数据库的数据和queType的length-2的截取字符串对比 //查询语句lenngth-2;select  from category by 
								category.setParentId(list.get(k).getId());
						}
					}
			}else{
				    category.setCode(queType.toString());
					category.setParentId("0");
					categoryService.insertSelective(category);//插入语句
					}
				}
			}
     read(length+2);
	   }

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
			row.createCell(0).setCellValue(cate.getCode());
			row.createCell(1).setCellValue(cate.getName());
		}
    	try {
			FileOutputStream fout = new FileOutputStream("F:/category/xls");
			wb.write(fout);
			fout.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	
    	
    	
    	
    }
	}
