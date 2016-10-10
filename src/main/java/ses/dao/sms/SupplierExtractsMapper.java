package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierExtracts;

public interface SupplierExtractsMapper {
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
    int insert(SupplierExtracts record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierExtracts record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierExtracts selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierExtracts record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierExtracts record);
    
    /**
     * @Description:查询抽取集合
     *
     * @author Wang Wenshuai
     * @version 2016年9月21日 上午10:18:05  
     * @param @return      
     * @return List<SupplierExtracts>
     */
    List<SupplierExtracts> listExtracts(SupplierExtracts record);
    
    /**
     * @Description: 分页获取记录
     *
     * @author Wang Wenshuai
     * @version 2016年10月9日 下午3:23:32  
     * @param @param record
     * @param @return      
     * @return List<SupplierExtracts>
     */
    List<SupplierExtracts> pageExtracts(SupplierExtracts record);
    
}