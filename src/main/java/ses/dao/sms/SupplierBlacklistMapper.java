package ses.dao.sms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.SupplierBlacklist;
import ses.model.sms.SupplierBlacklistVO;

public interface SupplierBlacklistMapper {
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
    int insert(SupplierBlacklist record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierBlacklist record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierBlacklist selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierBlacklist record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierBlacklist record);
    
    List<SupplierBlacklist> findSupplierBlacklist(SupplierBlacklist supplierBlacklist);
    /**
     * 获取 不等于 status 状态的 供应商id集合
     * @param status
     * @return
     */
    List<String> findByStatus(@Param("status")String status);
    
    int updateStatusById(SupplierBlacklist supplierBlacklist);

    /**
     * 查询供应商黑名单列表
     * @param supplierBlacklist
     * @param supplierTypeIds
     * @return
     */
	List<SupplierBlacklistVO> selectSupplierBlacklist(
		@Param("supplierBlacklist")SupplierBlacklist supplierBlacklist, 
		@Param("supplierTypeIds")String[] supplierTypeIds);
}