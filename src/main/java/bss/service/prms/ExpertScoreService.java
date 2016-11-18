package bss.service.prms;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import bss.model.ppms.SupplyMark;
import bss.model.prms.ExpertScore;

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
}
