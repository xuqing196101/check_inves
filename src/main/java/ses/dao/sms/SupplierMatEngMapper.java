package ses.dao.sms;

import ses.model.sms.SupplierMatEng;

public interface SupplierMatEngMapper {
    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(SupplierMatEng record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierMatEng record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierMatEng selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierMatEng record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierMatEng record);
    
    SupplierMatEng getMatEngBySupplierId(String supplierId);
    
    /**
     * @Title: findSupplierIdById
     * @author XuQing 
     * @date 2017-4-26 下午3:31:45  
     * @Description:查询供应商id
     * @param @param id
     * @param @return      
     * @return String
     */
    String findSupplierIdByEngAptId(String id);
    /**
     * 查询供应商id
     * @param id
     * @return
     */
    String findSupplierIdByEngQuaId(String id);
    
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     * 根据供应商id删除
     * @param supplierId
     * @return
     */
	int deleteBySupplierId(String supplierId);
}