package ses.controller.sys.bms;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.Category;
import ses.model.bms.CategoryAttachment;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.sms.SupplierTypeTree;
import ses.service.bms.CategoryAttachmentService;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.util.PathUtil;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import common.constant.Constant;

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

	private Map<String, Object> listCategory = new HashMap<String, Object>();

    @Autowired
	private CategoryAttachmentService categoryAttachmentService;
    
    @Autowired
    private DictionaryDataServiceI dictionaryDataServiceI;
	

	public Map<String, Object> getListCategory() {
		return listCategory;
	}

	public void setListCategory(Map<String, Object> listCategory) {
		this.listCategory = listCategory;
	}
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
			
				
			}else{
				ct.setIsParent("false");
			}
			ct.setId(cate.getId());
			ct.setName(cate.getName());
			ct.setpId(cate.getParentId());
			ct.setKind(cate.getKind());
			ct.setIsEnd(cate.getIsEnd());
			ct.setStatus(cate.getStatus());
			jList.add(ct);
			list = gson.toJson(jList);
		}
		return list;
	}
	/**
	 * @Title: get
	 * @author zhangxuefeng
	 * @Description:进入采购页面
	 * @param @return
	 * @return String
	 */
	@RequestMapping("/get")
	public String get(HttpServletRequest request,Model model) {
		model.addAttribute("cate",new Category());
		return "ses/bms/category/lista";
	}
	
	/**
	 * @Title: search
	 * @author zhangxuefeng
	 * @Description:根据关键字查找内容
	 * @param @return
	 * @return String
	 */
	/*@ResponseBody
	@RequestMapping("/search")
	public void search(HttpServletRequest request, HttpServletResponse response,String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		//List<Category> nodeList = categoryService.listByKeyword(name);
		String list ="";
		return list = JSON.toJSONString(nodeList);
		super.writeJson(response, nodeList);
		
	}*/

	/**
	 * 
	 * @Title: save
	 * @author zhangxuefeng
	 * @date
	 * @Description:保存新增目录信息
	 * @param @return
	 * @return String 
	 */
	@RequestMapping("/save")
	public String save(Category category,HttpServletRequest request,Model model) {
		/*String categoryuuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
		model.addAttribute("categoryId", categoryuuid);
		DictionaryData dd=new DictionaryData();
		dd.setCode("CATEGORY_ATTACHMENT");
		List<DictionaryData> lists = dictionaryDataServiceI.find(dd);
		request.getSession().setAttribute("categorySysKey", Constant.TENDER_SYS_KEY);
		if(lists.size()>0){
			model.addAttribute("attachTypeId", lists.get(0).getId());
		}
		*/
		Boolean flag = true;
		String name = request.getParameter("name");
		List<Category> cate = categoryService.selectAll();
		String catename="";
		String pos = "";
		String code ="";
		for(int i=0;i<cate.size();i++){
		catename+=cate.get(i).getName()+",";
		pos+= cate.get(i).getPosition()+",";
		code+= cate.get(i).getCode()+",";
		}
		String[] catenames = catename.split(",");
		for (int i = 0; i < catenames.length; i++) {
			if (name.equals(catenames[i])) {
				flag = false;
				model.addAttribute("name","目录不能重复");
			}
		}
		if (name==null||name.equals("")) {
			flag= false;
			model.addAttribute("name", "目录不能为空");
		}
		Integer position = Integer.parseInt(request.getParameter("position"));
		String[] poses = pos.split(",");
		for (int i = 0; i < poses.length; i++) {
			if (position.equals(poses[i])) {
				flag = false;
				model.addAttribute("position", "排序号不能重复");
			}
		}
		if (position==null || position.equals("")) {
			flag = false;
			model.addAttribute("position", "排序号不能为空");
		}
		if (flag == false) {
			model.addAttribute("", attributeValue)
		}
		
		
		category.setPosition(Integer.parseInt(request.getParameter("position")));
		category.setKind(request.getParameter("kind"));
		category.setStatus(1);
		category.setCode(request.getParameter("code"));
		category.setDescription(request.getParameter("description"));
		category.setCreatedAt(new Date());
		categoryService.insertSelective(category);
		
		
		return "redirect:get.html";
		}
		
		
	
	
	/**
	 * @Title: 上传附件
	 * @author Zhang Xuefeng
	 * @date 2016-9-1 下午2:00:40
	 * @Description: 保存
	 * @param @return
	 * @return String
	 */
	public String upload(HttpServletRequest request, MultipartFile file, Category category) {
		if (file.getOriginalFilename() != null && file.getOriginalFilename().equals("")) {
				String rootpath = (PathUtil.getWebRoot() + "picupload/").replace("\\", "/");
				/** 创建文件夹 */
		        String fileName = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() + "_" + file.getOriginalFilename();		        
		        String filePath = rootpath+fileName;
		        File files = new File(filePath);
		        try {
					file.transferTo(files);
				} catch (IllegalStateException e) {
					e.printStackTrace();				
					} catch (IOException e) {
				e.printStackTrace();
			}
				 CategoryAttachment attachment=new CategoryAttachment();
         		attachment.setCategory(new Category(category.getId()));
				attachment.setFileName(fileName);
				attachment.setCreatedAt(new Date());
				attachment.setContentType(file.getContentType());
				attachment.setFileSize((float)file.getSize());
				//路径==相对路径
				attachment.setAttchmentPath("picupload/"+fileName);
				categoryAttachmentService.insertSelective(attachment);
			}
		return "redirect:get.html";
	}
	/**
	 * 
	 * @Title: update
	 * @author Zhang XueFeng
	 * @Description:获取需要修改的节点数据
	 * @param @return
	 * @return String
	 */
	@RequestMapping("/update")
	public void update(HttpServletResponse response, String id,Model model) {
		Category cate = categoryService.selectByPrimaryKey(id);
		CategoryAttachment attchment = categoryAttachmentService.selectByCategoryId(cate.getId());
		cate.setCategoryAttchment(attchment);
		super.writeJson(response, cate);
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
  	* @Title: edit
  	* @author Zhang XueFeng
  	* @Description:修改目录信息
  	* @param @return 
  	* @return String
     */  
 
   @RequestMapping("/edit")
   public String  edit (@RequestParam("attaattach") MultipartFile attaattach,Category category,
	          HttpServletRequest request, HttpServletResponse response,Model model){
	      /*Boolean flag = true;
	      String url = "";
	      if(result.hasErrors()){
				List<FieldError> errors = result.getFieldErrors();
				for(FieldError fieldError:errors){
					model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
				}
				flag = false;
	      }
	      String name = request.getParameter("name");
	      if (name.equals(category.getName())) {
	    	  flag = false;
			model.addAttribute("ERR_name","品目名不能重复");
		}
	      Integer position = Integer.parseInt(request.getParameter("position"));
	      if (position.equals(category.getPosition())) {
			flag = false;
			model.addAttribute("ERR_position","排序号不能重复");
		}
	      String code = request.getParameter("code");
	      if (code.equals(category.getCode())) {
			flag = false;
			model.addAttribute("ERR_code","品目编码不能重复");
		}
	      if (flag == false) {
		     url = "ses/bms/category/list";
		}else{
			 category.setName(name);
			 category.setPosition(position);
			 category.setCode(code);
			 category.setDescription(request.getParameter("description"));
			 categoryService.updateByPrimaryKeySelective(category);
			 if (attaattach.getOriginalFilename() != null && !attaattach.getOriginalFilename().equals("")) {
					String rootpath = (PathUtil.getWebRoot() + "picupload/").replace("\\", "/");
					*//** 创建文件夹 *//*
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
					categoryAttachmentService.updateByPrimaryKeySelective(attachment);
					url = "ses/bms/category/list";
				}
		
		}
				return url;*/
	     /* String name = request.getParameter("name");
	      String position = request.getParameter("position");
	      Integer Position = Integer.parseInt(position);
	      String code = request.getParameter("code");*/
  	      category.setName(request.getParameter("name"));
  	      category.setPosition(Integer.parseInt(request.getParameter("position")));
		  category.setCode(request.getParameter("code"));
		  category.setDescription(request.getParameter("description"));
		  
		  category.setUpdatedAt(new Date());
		  categoryAttachmentService.deleteByPrimaryKey(category.getId());
	      categoryService.updateByPrimaryKeySelective(category);
	      upload(request,attaattach,category);
		return "redirect:get.html";
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
    public void change(HttpServletRequest request,String ids){
	   String[] cids=ids.split(",");
	   for (int i = 0; i < cids.length; i++) {
		Category cate=categoryService.selectByPrimaryKey(cids[i]);
		if (cate.getStatus()==0) {
			cate.setStatus(1);
		}else if (cate.getStatus()==1) {
			cate.setStatus(0);
		}
		categoryService.updateByPrimaryKeySelective(cate);
	}
	  
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

	
	/**
	 * @Title: findCategoryAndDisabled
	 * @author Song Biaowei
	 * @date 2016-10-12 下午5:09:31  
	 * @Description: 展示品目，不可编辑
	 * @param @param response
	 * @param @param category
	 * @param @param supplierId
	 * @param @throws IOException      
	 * @return void
	 */
	@RequestMapping(value = "find_category_and_disabled")
	public void findCategoryAndDisabled(HttpServletResponse response, Category category, String supplierId) throws IOException {
		List<SupplierTypeTree> listSupplierTypeTrees = categoryService.findCategoryByTypeAndDisabled(category, supplierId);
		String json = JSON.toJSONStringWithDateFormat(listSupplierTypeTrees, "yyyy-MM-dd HH:mm:ss");
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
		response.getWriter().flush();
		response.getWriter().close();
	}

	

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
}
