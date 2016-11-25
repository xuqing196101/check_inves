package ses.dao.sms;

import java.util.List;

import ses.model.sms.ImportSupplier;
import ses.model.sms.ImportSupplierWithBLOBs;
/**
 * 版权：(C) 版权所有 
 * <简述>进口供应商持久层
 * <详细描述>
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
public interface ImportSupplierMapper {
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
    int insert(ImportSupplierWithBLOBs record);
    /**
     *〈简述〉新增数据某字段为空就不插入
     *〈详细描述〉
     * @author Song Biaowei
     * @param record 实体类
     * @return int
     */
    int insertSelective(ImportSupplierWithBLOBs record);
    /**
     *〈简述〉按住键查找
     *〈详细描述〉
     * @author Song Biaowei
     * @param id 主键
     * @return ImportRecommend
     */
    ImportSupplierWithBLOBs selectByPrimaryKey(String id);
    /**
     *〈简述〉修改数据某字段为空就不修改
     *〈详细描述〉
     * @author Song Biaowei
     * @param record 实体类
     * @return int
     */
    int updateByPrimaryKeySelective(ImportSupplierWithBLOBs record);
    /**
     *〈简述〉修改数据
     *〈详细描述〉
     * @author Song Biaowei
     * @param record 实体类
     * @return int
     */
    int updateByPrimaryKeyWithBLOBs(ImportSupplierWithBLOBs record);
    /**
     *〈简述〉修改数据
     *〈详细描述〉
     * @author Song Biaowei
     * @param record 实体类
     * @return int
     */
    int updateByPrimaryKey(ImportSupplier record);
    
    
    /**
     *〈简述〉查询总条数
     *〈详细描述〉
     * @author Song Biaowei
     * @param record 实体
     * @return int
     */
    int getCount(ImportSupplierWithBLOBs record);
    
    /**
     *〈简述〉条件查询返回list
     *〈详细描述〉
     * @author Song Biaowei
     * @param record 实体
     * @return List<ImportSupplierWithBLOBs>
     */
    List<ImportSupplierWithBLOBs> selectByFsInfo(ImportSupplierWithBLOBs record);
    
    /**
     *〈简述〉通过用户名查询ID
     *〈详细描述〉
     * @author Song Biaowei
     * @param record 实体
     * @return String
     */
    String selectIdByLoginName(ImportSupplierWithBLOBs record);
}