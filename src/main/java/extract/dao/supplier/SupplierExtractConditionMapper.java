package extract.dao.supplier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import ses.model.bms.Category;
import ses.model.bms.CategoryQua;
import ses.model.bms.DictionaryData;
import ses.model.sms.SupplierItemLevel;
import extract.model.supplier.Continent;
import extract.model.supplier.Qua;
import extract.model.supplier.SupplierExtractCondition;


public interface SupplierExtractConditionMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);


    /**
     * 
     * <简述> 动态添加一条数据
     *
     * @author Jia Chengxiang
     * @dateTime 2017-10-25下午8:33:10
     * @param record
     * @return
     */
    int insertSelective(SupplierExtractCondition condition);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierExtractCondition selectByPrimaryKey(String id);
    
	void updateConditionByPrimaryKeySelective(SupplierExtractCondition condition);

	List<DictionaryData> getEngAptitudeLevelByCategoryId(
			Map<String, String[]> map);

	List<Qua> getQuaByCid(HashMap<String, Object> hashMap);

	List<DictionaryData> getLevelByQid(String[] split);

	/**
	 * 按记录id 查询抽取条件
	 * @param id
	 * @return
	 */
	SupplierExtractCondition getByRid(String recordId);

	/**
	 * 查询抽取品目名称集合
	 * @param byMap2
	 * @return
	 */
	List<String> getCategoryByList(List<String> byMap2);

	/**
	 * 查询工程资质集合
	 * @param byMap
	 * @return
	 */
	List<String> getLevelByList(List<String> byMap);

	/**
	 * 
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2018-1-5下午2:55:32
	 * @param cid
	 * @return
	 */
	List<Category> checkParentCate(String cid);

	/**
	 * 查询品目子节点
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2018-1-5下午2:55:24
	 * @param split
	 * @return
	 */
	Set<String> selectChildCate(String[] split);
	
	/**
	 * 
	 * <简述>验证资质 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-9-27下午6:16:07
	 * @param quaId
	 * @return 
	 */
	List<CategoryQua> verifyQua(String quaId);


	/**
	 * 
	 * <简述>获取4级父节点供应商等级
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-11-7下午8:21:50
	 * @param map
	 * @return
	 */
	List<SupplierItemLevel> selectCateLevelToUp(Map<String, Object> map);

	/**
	 * 
	 * <简述>获取4级子节点供应商等级
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-11-7下午8:42:31
	 * @param map
	 * @return
	 */
	List<SupplierItemLevel> selectCateLevelToDown(Map<String, Object> map);


	/**
	 * 查询供应商工程资质等级
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-11-8下午2:53:30
	 * @param map
	 * @return 
	 */
	List<String> selectQuaLevelBySupplierIdAndQuaId(Map<String, Object> map);


	/**
	 * 查询与关系时等级是否满足
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2018-1-5下午2:54:42
	 * @param hashMap
	 * @return
	 */
	List<String> selectLevelOfLogicIsAnd(Map<String, Object> hashMap);

	/**
	 * 
	 * <简述>境外分支树（洲，国家） 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2018-1-5下午2:54:19
	 * @param cid
	 * @param cname
	 * @return
	 */
	List<Continent> selectContryTree(@Param("cid") String cid, @Param("cname")String cname);

	/**
	 * 工程与关系
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-12-25上午9:59:51
	 * @param id
	 * @param quaIds
	 * @param levelTypeIds
	 * @return
	 */
	String selectProjectLevelForAnd(@Param("sid")String sid, @Param("quaIds")String[] quaIds,
			@Param("levels")String[] levelTypeIds);


	/**
	 * 
	 * <简述> 查询抽取条件集合
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2018-1-5下午2:53:03
	 * @param hashMap
	 * @return
	 */
	List<SupplierExtractCondition> selectConditionListByMap(
			Map<String, Object> hashMap);
}