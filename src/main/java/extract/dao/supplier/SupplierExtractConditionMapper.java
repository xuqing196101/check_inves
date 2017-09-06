package extract.dao.supplier;

import java.util.List;
import java.util.Map;

import extract.model.supplier.SupplierConType;
import extract.model.supplier.SupplierCondition;

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
    int insert(SupplierCondition record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierCondition record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierCondition selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierCondition record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierCondition record);
    
    /**
     * @Description:获取集合信息
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 上午10:47:15  
     * @param @param record
     * @param @return      
     * @return List<SupplierCondition>
     */
    List<SupplierCondition> list(SupplierCondition record);
    
    
    /**
     * 
     *〈简述〉根据关联包id查询是否有未抽取的条件
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param packid 包id 
     * @return 数量
     */
    Integer getCount(String packId);

	void updateConditionByPrimaryKeySelective(SupplierCondition condition);

	List<SupplierConType> getAreaInfoByConditionId(String id);
	
	void deleteAreaInfoByConditionId(String id);
	
	void insertAreaCondition(SupplierCondition condition);
	
	List<SupplierConType> getTypeInfoByMap(Map<String, String> map);

	void deleteTypeInfoByMap(Map<String, String> map);

	void insertTypeInfo(SupplierCondition condition);


	
}