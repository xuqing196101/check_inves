package ses.dao.sms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
     * @param id
     * @return
     */
    List<SupplierItemLevel> selectByCategoryId(@Param("categoryId")String categoryId ,@Param("armyBusinessName")String armyBusinessName,@Param("supplierName")String supplierName);
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
}