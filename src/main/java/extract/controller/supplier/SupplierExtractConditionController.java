/**
 * 
 */
package extract.controller.supplier;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;

import com.alibaba.fastjson.JSON;

import extract.model.supplier.SupplierConType;
import extract.model.supplier.SupplierExtractCondition;
import extract.service.supplier.SupplierExtractConditionService;



/**
 * @Description:查询条件控制
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月28日上午10:58:03
 * @since  JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/SupplierCondition")
public class SupplierExtractConditionController {
    @Autowired
    private SupplierExtractConditionService conditionService;

    
    
    /**
     * 
     *〈简述〉返回满足条件的供应商个数
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param condition
     * @param conType
     * @param sq
     * @param typeclassId
     * @return
     */
    @ResponseBody
    @RequestMapping("selectLikeSupplierCount")
    public String selectLikeSupplierCount(SupplierExtractCondition condition,SupplierConType conType,HttpServletRequest sq,String province){
        Map<String,Object> count = conditionService.selectLikeSupplierCount(condition,conType);
        return JSON.toJSONString(count);
    }
    
    /**
     * 
     *〈简述〉返回满足条件的供应商
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param condition
     * @param conType
     * @param sq
     * @param typeclassId
     * @return
     */
    @ResponseBody
    @RequestMapping("selectLikeSupplier")
    public String selectLikeSupplier(SupplierExtractCondition condition,SupplierConType conType,HttpServletRequest sq,String province){
    	Map<String, Map<String, Object>> supplierList = conditionService.selectLikeSupplier(condition,conType,1);
    	return JSON.toJSONString(supplierList);
    }

    /**
    *
    *〈简述〉获取品目树
    *〈详细描述〉
    * @author Wang Wenshuai
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
    * @author Jia chegnxiang
    */
   @RequestMapping("/getEngLevelByCid")
   @ResponseBody
   public List<DictionaryData> getEngLevelByCid(String categoryId){
   	return conditionService.getEngAptitudeLevelByCategoryId(categoryId);
   }
   
   
   /**
    * 根据工程品目ID 查询工程资质
    * @author Jia chegnxiang
    */
   @RequestMapping("/getQuaByCid")
   @ResponseBody
   public List<DictionaryData> getQuaByCid(String categoryId){
   	return conditionService.getQuaByCid(categoryId);
   }

}
