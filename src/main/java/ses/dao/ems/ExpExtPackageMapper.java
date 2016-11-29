package ses.dao.ems;

import java.util.List;

import ses.model.ems.ExpExtPackage;
import ses.model.sms.SupplierExtPackage;

public interface ExpExtPackageMapper {
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
    int insert(ExpExtPackage record);

    /**
     *
     * @param record
     */
    int insertSelective(ExpExtPackage record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    ExpExtPackage selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(ExpExtPackage record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(ExpExtPackage record);
    
    /**
     * 
     *〈简述〉批量插入
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param extPackages
     */
    void insertList(List<ExpExtPackage> extPackages);
    
    /**
     * 
     *〈简述〉list
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param record
     * @return
     */
    List<ExpExtPackage> list(ExpExtPackage record);
    
    /**
     * 
     *〈简述〉抽取记录
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param record
     * @return 集合
     */
    List<ExpExtPackage> extractsList(ExpExtPackage record);
}