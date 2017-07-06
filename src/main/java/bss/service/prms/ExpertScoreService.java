package bss.service.prms;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bss.model.ppms.SaleTender;
import bss.model.ppms.SupplyMark;
import bss.model.prms.ExpertScore;
import bss.model.prms.ext.ExpertSuppScore;

public interface ExpertScoreService {
	/**
	 * 
	  * @Title: deleteByPrimaryKey
	  * @author ShaoYangYang
	  * @date 2016年11月18日 上午11:28:41  
	  * @Description: TODO 删除
	  * @param @param id      
	  * @return void
	 */
	    void deleteByPrimaryKey(String id);
/**
 * 
  * @Title: insert
  * @author ShaoYangYang
  * @date 2016年11月18日 上午11:28:49  
  * @Description: TODO 新增所有
  * @param @param record      
  * @return void
 */
	    void insert(ExpertScore record);
/**
 * 
  * @Title: insertSelective
  * @author ShaoYangYang
  * @date 2016年11月18日 上午11:29:02  
  * @Description: TODO 新增不为空
  * @param @param record      
  * @return void
 */
	    void insertSelective(ExpertScore record);
/**
 * 
  * @Title: selectByPrimaryKey
  * @author ShaoYangYang
  * @date 2016年11月18日 上午11:29:10  
  * @Description: TODO 主键查询
  * @param @param id
  * @param @return      
  * @return ExpertScore
 */
	    ExpertScore selectByPrimaryKey(String id);
/**
 * 
  * @Title: updateByPrimaryKeySelective
  * @author ShaoYangYang
  * @date 2016年11月18日 上午11:29:19  
  * @Description: TODO 修改不为空
  * @param @param record      
  * @return void
 */
	    void updateByPrimaryKeySelective(ExpertScore record);
/**
 * 
  * @Title: updateByPrimaryKey
  * @author ShaoYangYang
  * @date 2016年11月18日 上午11:29:30  
  * @Description: TODO 修改全部
  * @param @param record      
  * @return void
 */
	    void updateByPrimaryKey(ExpertScore record);
/**
 * 
  * @Title: selectByMap
  * @author ShaoYangYang
  * @date 2016年11月18日 下午1:39:44  
  * @Description: TODO 条件查询
  * @param @param map
  * @param @return      
  * @return List<ExpertScore>
 */
	    List<ExpertScore> selectByMap(Map<String,Object> map);
	    
	    /**
	     *〈简述〉
	     * 分数被退回后的回显
	     *〈详细描述〉
	     * @author WangHuijie
	     * @param map
	     * @return
	     */
	    List<ExpertScore> selectInfoByMap(Map<String,Object> map);
	    /**
	     * 
	      * @Title: saveScore
	      * @author ShaoYangYang
	      * @date 2016年11月18日 下午1:48:28  
	      * @Description: TODO 保存
	      * @param @param map
	      * @param @param supplyMarkList      
	      * @return void
	     */
	    void saveScore(ExpertScore expertScore,List<SupplyMark> supplyMarkList,String scoreModelId);
	    /**
	     * 
	     *〈简述〉
	     *〈详细描述〉评分汇总
	     * @author ShaoYangYang
	     * @param packageId
	     * @param projectId
	     * @param expertId 
	     * @return
	     */
        void gather(String packageId, String projectId, List<SaleTender> supplierList);
        
        /**
         * 
         *〈简述〉
         *〈详细描述〉专家详细审核界面的分值展示
         * @author WangHuijie
         * @param map
         * @return
         */
        List<ExpertSuppScore> getScoreByMap(Map<String, Object> map);
        /**
         *〈简述〉计算供应商总得分
         *〈详细描述〉
         * @author Ye MaoLin
         * @param map
         */
        BigDecimal selectSumByMap(HashMap<String, Object> map);
}
