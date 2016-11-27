package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierEdit;
/**
 * 版权：(C) 版权所有 
 * <简述>供应商变更持久层
 * <详细描述>
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
public interface SupplierEditMapper {
    /**
     *〈简述〉按照主键删除
     *〈详细描述〉
     * @author Song Biaowei
     * @param id 主键
     * @return int
     */
    int deleteByPrimaryKey(String id);
    /**
     *〈简述〉新增数据
     *〈详细描述〉
     * @author Song Biaowei
     * @param record 实体类
     * @return int
     */
    int insert(SupplierEdit record);
    /**
     *〈简述〉新增数据某字段为空就不插入
     *〈详细描述〉
     * @author Song Biaowei
     * @param record 实体类
     * @return int
     */
    int insertSelective(SupplierEdit record);
    /**
     *〈简述〉按住键查找
     *〈详细描述〉
     * @author Song Biaowei
     * @param id 主键
     * @return SupplierEdit
     */
    SupplierEdit selectByPrimaryKey(String id);
    /**
     *〈简述〉修改数据某字段为空就不修改
     *〈详细描述〉
     * @author Song Biaowei
     * @param record 实体类
     * @return int
     */
    int updateByPrimaryKeySelective(SupplierEdit record);
    /**
     *〈简述〉修改数据
     *〈详细描述〉
     * @author Song Biaowei
     * @param record 实体类
     * @return int
     */
    int updateByPrimaryKey(SupplierEdit record);
    /**
     *〈简述〉条件查询
     *〈详细描述〉
     * @author Song Biaowei
     * @param se 实体类
     * @return List<SupplierEdit>
     */
    List<SupplierEdit> getAll(SupplierEdit se);
    /**
     *〈简述〉条件查询
     *〈详细描述〉
     * @author Song Biaowei
     * @param se 实体类
     * @return List<SupplierEdit>
     */
    List<SupplierEdit> getAllbySupplierId(SupplierEdit se);
    /**
     *〈简述〉条件查询
     *〈详细描述〉
     * @author Song Biaowei
     * @param se 实体类
     * @return List<SupplierEdit>
     */    
    List<SupplierEdit> getAllRecord(SupplierEdit se);
    
}