package iss.controller.ps;

import iss.bean.IssData;
import iss.model.ps.DataDownload;
import iss.service.ps.DataDownloadService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.util.PropertiesUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.constant.Constant;
import common.utils.JdcgResult;
/** 
*  
* @Description: 页面产品目录展示
* @date 2017-3-5上午9:30:53
 */
@Controller
@RequestMapping("/categorys")
public class ProductCatalog {
	@Autowired
	private DataDownloadService dataDownloadService;
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	@Autowired
	private CategoryService categoryService;
	@RequestMapping("/categoryList")
	public String catalogList(Integer page,Category category,HttpServletRequest request,Model model) {
		return "iss/ps/catalog/catalogList";
	}
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
			List < Category > categoryList = categoryService.searchByNameAndCode(name.trim(),code,0);
			List < Category > cateList = new ArrayList < Category > ();
			Set<Category> set=new HashSet<Category>();
			for(int i=0;i<categoryList.size();i++){
				Category catego = categoryList.get(i);
				List<Category> cList=categoryService.findTreeByPidIsPublish(catego.getId());
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
				List<Category> cList=categoryService.findTreeByPidIsPublish(cate.getId());
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
            List<Category> cateList=categoryService.findTreeByPidIsPublish(category.getId());
    	        for(Category cate:cateList){
    	            List<Category> cList=categoryService.findTreeByPidIsPublish(cate.getId());
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
	@RequestMapping("/parameterList")
	public String parameterList(Integer page,Category category,HttpServletRequest request,Model model) {
		/*HashMap<String,Object> map = new HashMap<>();
		if(category!=null){
			if(category.getName()!=null && !category.getName().equals("")){
				map.put("name", category.getName());
			}
		}
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSizeArticle")));
		List<Category> Categorys = categoryService.findCategoryByNameOrClassify(map);
		model.addAttribute("list", new PageInfo<Category>(Categorys));
		Map<String, Object> indexMapper = new HashMap<String, Object>();
		IssData.topNews(indexMapper);
		model.addAttribute("indexMapper", indexMapper);*/
		return "iss/ps/catalog/parameterlist";
	}
	
	
}
