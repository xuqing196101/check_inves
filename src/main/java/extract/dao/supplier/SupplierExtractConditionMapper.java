package extract.dao.supplier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ses.model.bms.DictionaryData;
import extract.model.supplier.SupplierConType;
import extract.model.supplier.SupplierExtractCondition;

public interface SupplierExtractConditionMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(SupplierExtractCondition record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierExtractCondition record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierExtractCondition selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierExtractCondition record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierExtractCondition record);
    
    /**
     * @Description:获取集合信息
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 上午10:47:15  
     * @param @param record
     * @param @return      
     * @return List<SupplierCondition>
     */
    List<SupplierExtractCondition> list(SupplierExtractCondition record);
    
    
    /**
     * 
     *〈简述〉根据关联包id查询是否有未抽取的条件
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param packid 包id 
     * @return 数量
     */
    Integer getCount(String packId);

	void updateConditionByPrimaryKeySelective(SupplierExtractCondition condition);

	List<SupplierConType> getAreaInfoByConditionId(String id);
	
	void deleteAreaInfoByConditionId(String id);
	
	void insertAreaCondition(SupplierExtractCondition condition);
	
	List<SupplierConType> getTypeInfoByMap(Map<String, String> map);

	void deleteTypeInfoByMap(Map<String, String> map);

	void insertTypeInfo(SupplierExtractCondition condition);

	List<DictionaryData> getEngAptitudeLevelByCategoryId(
			Map<String, String[]> map);

	List<DictionaryData> getQuaByCid(HashMap<String, String[]> hashMap);

	List<DictionaryData> getLevelByQid(String[] split);

	/**
	 * 按记录id 查询抽取条件
	 * @param id
	 * @return
	 */
	SupplierExtractCondition getByRid(String id);

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


	
}