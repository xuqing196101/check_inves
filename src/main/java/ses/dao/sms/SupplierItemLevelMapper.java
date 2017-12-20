package ses.dao.sms;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import ses.model.bms.DictionaryData;
import ses.model.sms.SupplierItemLevel;

public interface SupplierItemLevelMapper {
	/**
	 * 
	 * Description:查询数据是否存在
	 * 
	 * @author YangHongLiang
	 * @version 2017-6-22
	 * @return
	 */
	int countItemLevel(String supplierId,String supplierTypeId );
	/**
	 * 
	 * Description:清空数据
	 * 
	 * @author YangHongLiang
	 * @version 2017-6-22
	 * @param id
	 * @return
	 */
    int deleteByCategoryIdType(@Param("categoryId")String categoryId,@Param("supplierTypeId")String supplierTypeId);

    int insert(SupplierItemLevel record);

    int insertSelective(SupplierItemLevel record);
    /**
     * 
     * Description:查询数据
     * 
     * @author YangHongLiang
     * @version 2017-6-22
     * @param supplierLevel 
     * @param id
     * @return
     */
    List<SupplierItemLevel> selectByCategoryId(@Param("categoryId")String categoryId, @Param("supplierTypeRelateId")String supplierTypeRelateId , @Param("armyBusinessName")String armyBusinessName, @Param("supplierName")String supplierName, @Param("supplierLevel")String supplierLevel, @Param("auditType")String auditType);
    /**
     * 
     * Description:根据条件更新数据
     * 
     * @author YangHongLiang
     * @version 2017-6-22
     * @param record
     * @return
     */
    int updateByPrimaryKeyBySupplierIdTypeID(SupplierItemLevel record);

    int updateByPrimaryKey(SupplierItemLevel record);
    
    /**
     * 全部供应商查询，等级查询 
     * @param supplierItemLevel
     * @return
     */
    SupplierItemLevel selectLevelByItem(SupplierItemLevel supplierItemLevel);
    
	/**
	 * 获取工程四级品目下供应商等级
	 * @param supplierId
	 * @param categoryLevel 
	 * @param categoryIds
	 */
	List<String> getProjectLevel(@Param("supplierId")String supplierId, @Param("categoryId")String categoryId);
	
	/**
     * Description:根据 工程品目 查询其所有等级
     * 
     * 
     * @author Ye MaoLin
     * @version 2017-10-18
     * @param categoryIds
     * @return
     */
	List<DictionaryData> ajaxProjectCategoryLevels(String categoryId);
	
	
	/**
	 * Description:根据 工程品目 查询其下所有供应商以及等级
     * 
     * @author Ye MaoLin
     * @version 2017-10-18
	 * @param categoryIds
	 * @param supplierType
	 * @param armyBusinessName
	 * @param supplierName
	 * @param supplierLevel
	 * @return
	 */
	List<SupplierItemLevel> selectProjectSupplierByCategory(@Param("categoryId")String categoryId, @Param("supplierTypeRelateId")String supplierTypeRelateId , @Param("armyBusinessName")String armyBusinessName, @Param("supplierName")String supplierName, @Param("supplierLevel")String supplierLevel, @Param("auditType")String auditType);
	
	
	/**
	 * 
	 * <简述>根据map查询 要导出的信息
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-11-3下午4:58:49
	 * @param map
	 * @return
	 */
	List<SupplierItemLevel> selectByMapForExport(Map<String, Object> map);

	/**
	 *〈简述〉//如果是大于或等于五级的品目，就查其父级四级目录的等级
	 *〈详细描述〉
	 * @author Ye Maolin
	 * @param categoryIds
	 * @param supplierType
	 * @param armyBusinessName
	 * @param supplierName
	 * @param supplierLevelName
	 * @param clickCategoryId
	 * @return
	 */
	List<SupplierItemLevel> selectFourCategoryLevelOutfour(@Param("categoryId")String categoryIds,
			@Param("supplierTypeRelateId")String supplierType, @Param("armyBusinessName")String armyBusinessName, @Param("supplierName")String supplierName,
			@Param("supplierLevel")String supplierLevelName, @Param("clickCategoryId")String clickCategoryId, @Param("auditType")String auditType);
	
	/**
	 * 
	 * <简述> 按id查询数据
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-11-10上午11:45:07
	 * @param id
	 * @return
	 */
	SupplierItemLevel selectById(String id);
	
	/**
	 * 
	 * <简述>删除前一天导入的供应商等级 
	 *
	 * @author Jia Chengxiang
	 * @param date 
	 * @dateTime 2017-11-23下午2:46:35
	 */
	void deleteSupplierItemLevelByDateOfYestoday(@Param("yestoday") Date date);
}