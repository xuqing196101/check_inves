package extract.dao.supplier;

import java.util.List;

import extract.model.supplier.SupplierExtUser;

public interface SupplierExtractUserMapper {
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
    int insert(SupplierExtUser record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierExtUser record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierExtUser selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierExtUser record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierExtUser record);
    
    /**
     * @Description:集合
     *
     * @author Wang Wenshuai
     * @version 2016年10月14日 下午7:39:19  
     * @param @param record
     * @param @return      
     * @return List<ProExtSupervise>
     */
    List<SupplierExtUser> list(SupplierExtUser record);
    
    /**
     * @Description:根据项目id删除监督信息
     *
     * @author Wang Wenshuai
     * @version 2016年10月15日 下午7:05:15  
     * @param       
     * @return void
     */
    void deleteProjectId(String prjectId);
    
    
    /**
     * 
     *〈简述〉批量插入
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param listInsert
     */
    void listInsert(List<SupplierExtUser> listInsert);

	String getName(String recordId);
}