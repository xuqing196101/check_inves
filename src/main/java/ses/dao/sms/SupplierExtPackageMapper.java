package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierExtPackage;

public interface SupplierExtPackageMapper {
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
    int insert(SupplierExtPackage record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierExtPackage record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierExtPackage selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierExtPackage record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierExtPackage record);
    
    /**
     * 
     *〈简述〉批量插入
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param extPackages
     */
    void insertList(List<SupplierExtPackage> extPackages);
    
    /**
     * 
     *〈简述〉list
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param record
     * @return
     */
    List<SupplierExtPackage> list(SupplierExtPackage record);
}