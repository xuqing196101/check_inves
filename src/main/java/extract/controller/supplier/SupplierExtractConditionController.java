/**
 * 
 */
package extract.controller.supplier;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;
import ses.service.bms.CategoryService;

import com.alibaba.fastjson.JSON;
import common.bean.ResponseBean;

import extract.model.supplier.Qua;
import extract.model.supplier.SupplierConType;
import extract.model.supplier.SupplierExtractCondition;
import extract.service.supplier.AutoExtractSupplierService;
import extract.service.supplier.SupplierExtractConditionService;


	
	/**
	 * @Description:查询条件控制
	 *	 
	 * @author jia chengxiang
	 * @version 2016年9月28日上午10:58:03
	 * @since  JDK 1.7
	 */
	@Controller
	@Scope("prototype")
	@RequestMapping("/SupplierCondition_new")
	public class SupplierExtractConditionController {
    @Autowired
    private SupplierExtractConditionService conditionService;
    
    @Autowired
    private AutoExtractSupplierService autoExtractSupplierService;

    @Autowired
    private CategoryService categoryService;
    
    
    
    /**
     * 
     *〈简述〉返回满足条件的供应商个数
     *〈详细描述〉
     * @author Jia Chengxaing
     * @param condition
     * @param conType
     * @param sq
     * @param typeclassId
     * @return
     */
    @ResponseBody
    @RequestMapping("selectLikeSupplierCount")
    public String selectLikeSupplierCount(SupplierExtractCondition condition,SupplierConType conType){
        Map<String,Object> count = conditionService.selectLikeSupplierCount(condition,conType);
        return JSON.toJSONString(count);
    }
    
    /**
     * 
     *〈简述〉返回满足条件的供应商
     *〈详细描述〉
     * @author Jia Chengxaing
     * @param condition
     * @param conType
     * @param sq
     * @param typeclassId
     * @return
     */
    @ResponseBody
    @RequestMapping("selectLikeSupplier")
    public String selectLikeSupplier(SupplierExtractCondition condition,SupplierConType conType){
    	//Map<String, Object> supplierList = conditionService.selectLikeSupplier(condition,conType,1);
    	//原抽取供应商方法
    	//Map<String, Object> supplierList = conditionService.selectLikeSupplier2(condition,1);
    	
    	//需求变更为每个品目对应不同的等级进行抽取
    	Map<String, Object> supplierList = conditionService.selectSupplier(condition,1);
    	return JSON.toJSONString(supplierList);
    }
    
    
    /**
     * 
     *〈简述〉返回满足条件的供应商(自动抽取)
     *〈详细描述〉
     * @author Jia Chengxaing
     * @param condition
     * @param conType
     * @param sq
     * @param typeclassId
     * @return
     */
    @ResponseBody
    @RequestMapping("autoExtract")
    public String autoExtract(SupplierExtractCondition condition,SupplierConType conType,String projectInfo){
    	Map<String, Object> supplierList = autoExtractSupplierService.autoExtract(condition,projectInfo);
    	return JSON.toJSONString(supplierList);
    }

    /**
    *
    *〈简述〉获取品目树
    *〈详细描述〉
    * @author Jia Chengxiang
    * @return
    */
   @ResponseBody
   @RequestMapping("/getTree")
   public String getTree(Category category,String projectId,String supplierTypeCode){
  	 //获取字典表中的根数据
   	List<CategoryTree> jList = 	conditionService.getTreeForExt(category,supplierTypeCode);
       return JSON.toJSONString(jList);
   }
   
   /**
    * 根据工程品目ID 查询资质等级 
    * @author Jia Chengxaing
    */
   @RequestMapping("/getEngLevelByCid")
   @ResponseBody
   public List<DictionaryData> getEngLevelByCid(String categoryId){
   	return conditionService.getEngAptitudeLevelByCategoryId(categoryId);
   }
   
   
   /**
    * 根据工程品目ID 查询资质信息
    * @author Jia Chengxiang
    */
   @RequestMapping("/getQuaByCid")
   @ResponseBody
   public List<Qua> getQuaByCid(String categoryId,String supplierTypeCode,String parentId){
   	return conditionService.getQuaByCid(categoryId,supplierTypeCode,parentId);
   }
   
   /**
    * 按资质查询资质等级
    * @param qid
    * @return
    */
   @RequestMapping("/getLevelByQid")
   @ResponseBody
   public String getLevelByQid(String qid){
   	List<DictionaryData> list =  conditionService.getLevelByQid(qid);
		return JSON.toJSONString(list);
   }
   
   /**
    * 
    * <简述> 模糊查询资质
    *
    * @author Jia Chengxiang
    * @dateTime 2017-9-23下午12:32:38
    * @param page
    * @param name
    * @param type
    * @param ids
    * @return
    */
   @ResponseBody
   @RequestMapping("/qualificationList")
   public ResponseBean list(String name){
       
       ResponseBean res = new ResponseBean();
           List<Qualification> list = conditionService.qualificationList(name);
           res.setSuccess(true);
           res.setObj(list);
      return res;
   }
   
   
   /**
    * 
    * <简述> 存储抽取条件
    *
    * @author Jia Chengxiang
    * @dateTime 2017-9-23下午12:32:38
    * @param page
    * @param name
    * @param type
    * @param ids
    * @return
    */
   @ResponseBody
   @RequestMapping("/saveCondition")
   public String saveCondition( SupplierExtractCondition condition, SupplierConType conType){
	   int status = conditionService.saveOrUpdateCondition(condition,conType);
	   return JSON.toJSONString(status);
   }

   @ResponseBody
   @RequestMapping("/searchCate")
   public String searchCate(String typeId,String cateName,String cateCode){
	return JSON.toJSONString(categoryService.selectCategoryByName(typeId, cateName,cateCode));
   }
   
}
