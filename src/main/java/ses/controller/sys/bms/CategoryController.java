package ses.controller.sys.bms;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import common.bean.ResBean;
import common.constant.Constant;
import common.constant.StaticVariables;
import net.sf.json.JSONSerializer;
import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.sms.SupplierTypeTree;
import ses.service.bms.CategoryAttachmentService;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.ems.ExpertService;
import ses.util.DictionaryDataUtil;
import ses.util.EncodingTool;
import ses.util.WfUtil;

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

    @Autowired
	private ExpertService service;

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
    @RequestMapping(value="/createtree", produces = "application/json;charset=utf-8")
    public String getAll(Category category,String param,Integer isCreate,String code){
       List<CategoryTree> jList=new ArrayList<CategoryTree>();
    	String name="";
    	if((param!=null&&!"".equals(param))||(code!=null&&!"".equals(code))||isCreate!=null){
			try {
				if(param!=null&&!"".equals(param)){
					name=java.net.URLDecoder.decode(param, "UTF-8");
				}	
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			//查询所有匹配的数据
			category.setId("0");
	        DictionaryData data=new DictionaryData();
	        data.setKind(6);
	        List<DictionaryData> listByPage = dictionaryDataServiceI.listByPage(data, 1);
	        for (DictionaryData dictionaryData : listByPage) {
	            CategoryTree ct=new CategoryTree();
	            ct.setId(dictionaryData.getId());
	            ct.setName(dictionaryData.getName());
	            ct.setIsParent("true");
	            ct.setClassify(dictionaryData.getCode());
	            jList.add(ct);
	        }
			List < Category > categoryList = categoryService.searchByNameAndCode(name.trim(),code,isCreate);
			List < Category > cateList = new ArrayList < Category > ();
			Set<Category> set=new HashSet<Category>();
			for(int i=0;i<categoryList.size();i++){
				Category catego = categoryList.get(i);
				List<Category> cList=categoryService.findTreeByPid(catego.getId());
				if(cList==null||cList.size()<=0){
					cateList.add(catego);
				}
			}
			for(int i=0;i<cateList.size();i++){
				HashMap<String,Object> map=new HashMap<String, Object>();
				map.put("id", cateList.get(i).getId());
				List<Category> catego = categoryService.findCategoryByParentNode(map);
				for(int j=0;j<catego.size();j++){
					set.add(catego.get(j));
				}
			}
			Iterator<Category> it = set.iterator();  
			while(it.hasNext()){
				Category cate = it.next();
				List<Category> cList=categoryService.findTreeByPid(cate.getId());
	            CategoryTree ct=new CategoryTree();
	            if(!cList.isEmpty()){
	                ct.setIsParent("true");
	            }else{
	                ct.setIsParent("false");
	            }
	            ct.setId(cate.getId());
	            ct.setName(cate.getName());
	            ct.setParentId(cate.getParentId());
	            ct.setKind(cate.getKind());
	            ct.setStatus(cate.getStatus());
	            jList.add(ct);
			}
	    	/*坑人的写法
	    	 * List < Category > categoryList = service.searchByName(name, null, null);
	    	List < Category > cateList = new ArrayList < Category > ();
	    	category.setId("0");
	        DictionaryData data=new DictionaryData();
	        data.setKind(6);
	        List<DictionaryData> listByPage = dictionaryDataServiceI.listByPage(data, 1);
	        for (DictionaryData dictionaryData : listByPage) {
	            CategoryTree ct=new CategoryTree();
	            ct.setId(dictionaryData.getId());
	            ct.setName(dictionaryData.getName());
	            ct.setIsParent("true");
	            ct.setClassify(dictionaryData.getCode());
	            jList.add(ct);
	            for(Category categorys: categoryList) {
	                String parentId = getParentId(categorys.getId());
	                if(parentId.equals(dictionaryData.getId())) {
	                    cateList.add(categorys);
	                }
	            }
	        }
	    	
	    	removeSame(cateList);
	    	List < Category > allCateList = new ArrayList < Category > ();
	        allCateList.addAll(cateList);
	        for(Category categoryds: cateList) {
	            List < Category > list = getParentNodeList(categoryds.getId());
	            allCateList.addAll(list);
	        }
	        removeSame(allCateList);
	        
	        for(Category categoryds: allCateList) {
	            CategoryTree treeNode = new CategoryTree();
	            treeNode.setId(categoryds.getId());
	            treeNode.setName(categoryds.getName());
	            treeNode.setParentId(categoryds.getParentId());
	            treeNode.setKind(categoryds.getKind());
	            treeNode.setStatus(categoryds.getStatus());
	            // 判断是否为父级节点
	            List < Category > nodesList = categoryService.findPublishTree(categoryds.getId(), null);
	            if(nodesList != null && nodesList.size() > 0) {
	                treeNode.setIsParent("true");
	            }
	            jList.add(treeNode);
	        }*/
	    	return JSON.toJSONString(jList);
    	}else{
    		 //获取字典表中的根数据
            if(category.getId()==null){
                category.setId("0");
                DictionaryData data=new DictionaryData();
                data.setKind(6);
                List<DictionaryData> listByPage = dictionaryDataServiceI.listByPage(data, 1);
                for (DictionaryData dictionaryData : listByPage) {
                    CategoryTree ct=new CategoryTree();
                    ct.setId(dictionaryData.getId());
                    ct.setName(dictionaryData.getName());
                    ct.setIsParent("true");
                    ct.setClassify(dictionaryData.getCode());
                    jList.add(ct);
                }
                
                return JSON.toJSONString(jList);
            }
            String list="";
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
    	            ct.setStatus(cate.getStatus());
    	            jList.add(ct);
    	        }

            list = JSON.toJSONString(jList);
            return list;
    	}
    	
    	
    }
    
    /**
     * 
     * Description: 根据id查询目录树
     * 
     * @author  zhang shubin
     * @version  2017年3月17日 
     * @param  @param category
     * @param  @return 
     * @return String 
     * @exception
     */
    @ResponseBody
    @RequestMapping(value="/createtreeById", produces = "application/json;charset=utf-8")
    public String createtreeById(Category category,String name){
    	List<CategoryTree> jList=new ArrayList<CategoryTree>();
    	
    	if((name!=null&&!"".equals(name))){
			try {
				if(name!=null&&!"".equals(name)){
					name=java.net.URLDecoder.decode(name, "UTF-8");
				}	
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			//查询所有匹配的数据
			category.setId("0");
	        DictionaryData data=new DictionaryData();
	        data.setKind(6);
	        List<DictionaryData> listByPage = dictionaryDataServiceI.listByPage(data, 1);
	        String id="";
	        for (DictionaryData dictionaryData : listByPage) {
	        	if(dictionaryData.getName().equals("物资")){
	            CategoryTree ct=new CategoryTree();
	            ct.setId(dictionaryData.getId());
	            ct.setName(dictionaryData.getName());
	            ct.setIsParent("true");
	            ct.setClassify(dictionaryData.getCode());
	            id=dictionaryData.getId();
	            jList.add(ct);
	        	}
	        }
	       HashMap<String, Object> maps=new HashMap<String, Object>();
	       maps.put("id", id);
	       maps.put("name", name.trim());
			List < Category > categoryList = categoryService.findCategoryByChildrenAndWuZi(maps);
			List < Category > cateList = new ArrayList < Category > ();
			Set<Category> set=new HashSet<Category>();
			for(int i=0;i<categoryList.size();i++){
				Category catego = categoryList.get(i);
				List<Category> cList=categoryService.findTreeByPidPublish(catego.getId());
				if(cList==null||cList.size()<=0){
					cateList.add(catego);
				}
				
			}
			for(int i=0;i<cateList.size();i++){
				HashMap<String,Object> map=new HashMap<String, Object>();
				map.put("id", cateList.get(i).getId());
				List<Category> catego = categoryService.findCategoryByParentNode(map);
				for(int j=0;j<catego.size();j++){
					set.add(catego.get(j));
				}
			}
			Iterator<Category> it = set.iterator();  
			while(it.hasNext()){
				Category cate = it.next();
				List<Category> cList=categoryService.findTreeByPidPublish(cate.getId());
	            CategoryTree ct=new CategoryTree();
	            if(!cList.isEmpty()){
	                ct.setIsParent("true");
	            }else{
	                ct.setIsParent("false");
	            }
	            ct.setId(cate.getId());
	            ct.setName(cate.getName());
	            ct.setParentId(cate.getParentId());
	            ct.setKind(cate.getKind());
	            ct.setStatus(cate.getStatus());
	            jList.add(ct);
			}
	    	
	    	return JSON.toJSONString(jList);
    	}else{
    		 //获取字典表中的根数据
            if(category.getId()==null){
                category.setId("0");
                DictionaryData data=new DictionaryData();
                data.setKind(6);
                List<DictionaryData> listByPage = dictionaryDataServiceI.listByPage(data, 1);
                for (DictionaryData dictionaryData : listByPage) {
                	if(dictionaryData.getName().equals("物资")){
                    CategoryTree ct=new CategoryTree();
                    ct.setId(dictionaryData.getId());
                    ct.setName(dictionaryData.getName());
                    ct.setIsParent("true");
                    ct.setClassify(dictionaryData.getCode());
                    jList.add(ct);
                	}
                    
                }
                
                return JSON.toJSONString(jList);
            }
            String list="";
            List<Category> cateList=categoryService.findTreeByPidPublish(category.getId());
    	        for(Category cate:cateList){
    	            List<Category> cList=categoryService.findTreeByPidPublish(cate.getId());
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
    	            ct.setStatus(cate.getStatus());
    	            jList.add(ct);
    	        }

            list = JSON.toJSONString(jList);
            return list;
    	 }
    	}
    	
    	
    
    public List < Category > getParentNodeList(String nodeId) {
    	 List < Category > parentNodeList = new ArrayList < Category > ();
	        Category category = categoryService.findById(nodeId);
	        if(category != null) {
	            String parentId = category.getParentId();
	            if(parentId != null && !"".equals(parentId)) {
	                Category cate = categoryService.findById(parentId);
	                if(cate != null) {
	                    parentNodeList.add(cate);
	                    List < Category > parentList = getParentNodeList(cate.getId());
	                    parentNodeList.addAll(parentList);
	                }
	            }
	        }
	        return parentNodeList;
	   
	}

    public void removeSame(List < Category > list) {
		for(int i = 0; i < list.size() - 1; i++) {
			for(int j = list.size() - 1; j > i; j--) {
				if(list.get(j).getId().equals(list.get(i).getId())) {
					list.remove(j);
				}
			}
		}
	}
    public String getParentId(String cateId) {
	  
	        Category cate = categoryService.selectByPrimaryKey(cateId);
	        if(cate != null) {
	            cateId = getParentId(cate.getParentId());
	        }
	        return cateId;
	    
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
        model.addAttribute("levelList", DictionaryDataUtil.find(31));
        return "ses/bms/category/list";
    }
    
    /**
     * 
     *〈简述〉
     * 添加页面初始化
     *〈详细描述〉
     * @author myc
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add")
    public String add(){
        String uuid = WfUtil.createUUID();
        return uuid;
    }

    /**
     * 
     * @Title: save
     * @author zhangxuefeng
     * @date
     * @Description:保存新增目录信息
     * @param @return
     * @return String 
     */
    @ResponseBody
    @RequestMapping(value = "/save", produces = "application/json;charset=UTF-8")
    public ResBean save(HttpServletRequest request) {
        
        ResBean resBean = categoryService.saveCategory(request);
        return resBean;
    }



    /**
     * 
     * @Title: update
     * @author Zhang XueFeng
     * @Description:获取需要修改的节点数据
     * @param @return
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "/update", produces = "application/json;charset=UTF-8")
    public Category update (String id) {
        Category cate = categoryService.getCategoryQuaById(id);
        return cate;
    }
    
    /**
     * 
     *〈简述〉
     *  判断是否可以删除
     *〈详细描述〉
     * @author myc
     * @param id 品目Id
     * @return 成功返回ok,否则返回错误信息
     */
    @ResponseBody
    @RequestMapping(value = "/calledStatus", produces = "text/html;charset=UTF-8")
    public String calledStatus(String id, String opera){
        
        return  categoryService.estimate(id, opera,StaticVariables.CATEGORY_ASSIGNED_MSG,StaticVariables.CATEGORY_ASSIGNED_STATUS);
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
     * @Title: ros
     * @author Zhang XueFeng
     * @Description:删除
     * @param @return 
     * @return String
     */ 
    @ResponseBody
    @RequestMapping("/deleted")
    public String change(HttpServletRequest request){
        String id = request.getParameter("id");
        Category category = categoryService.selectByPrimaryKey(id);
        if (category != null) {
            category.setIsDeleted(1);
            category.setUpdatedAt(new Date());
            categoryService.updateByPrimaryKeySelective(category);
            return "success";
        }
        return "failed";
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
    	List<String> list=new ArrayList<String>();
    	if(categoryIds!=null){
        	 list=Arrays.asList(categoryIds.split(","));
        }
        List<SupplierTypeTree> listSupplierTypeTrees = categoryService.queryCategory(category, list, 0);
        String json = JSON.toJSONStringWithDateFormat(listSupplierTypeTrees, "yyyy-MM-dd HH:mm:ss");
        json = json.replaceAll("parent", "isParent").replaceAll("isParentId", "parentId");
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }
    
    /**
     *〈简述〉供应商查询条件显示品目
     *〈详细描述〉
     * @author Song Biaowei
     * @param response response
     * @param category 品目实体
     * @param categoryIds 品目字符串
     * @throws IOException 异常处理
     */
    @RequestMapping(value = "query_category_select")
    public void queryCategorySelect(HttpServletResponse response, Category category, String categoryIds) throws IOException {
        List<String> list=new ArrayList<String>();
        if(categoryIds!=null){
             list=Arrays.asList(categoryIds.split(","));
        }
        List<SupplierTypeTree> listSupplierTypeTrees = categoryService.queryCategory(category, list,1);
        String json = JSON.toJSONStringWithDateFormat(listSupplierTypeTrees, "yyyy-MM-dd HH:mm:ss");
        json = json.replaceAll("parent", "isParent").replaceAll("isParentId", "parentId");
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }

    @RequestMapping("/createAdd")
    @ResponseBody
    public String createAdd(Model model,HttpServletRequest request){
        String articleuuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        DictionaryData da=new DictionaryData();
        da.setCode("GGWJ");
        List<DictionaryData> dlists = dictionaryDataServiceI.find(da);
        Map<String, Object> map = new HashMap<String, Object>();
        if(dlists.size()>0){
            map.put("artiAttachTypeId",  dlists.get(0).getId());
        }

        map.put("articleId", articleuuid);
        map.put("articleSysKey",Constant.TENDER_SYS_KEY);
        return JSONSerializer.toJSON(map).toString();
    }
}
