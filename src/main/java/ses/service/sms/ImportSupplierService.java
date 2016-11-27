package ses.service.sms;

import java.util.List;

import ses.model.sms.ImportSupplierWithBLOBs;



/**
 * 版权：(C) 版权所有 
 * <简述>进口供应商服务层
 * <详细描述>
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
public interface ImportSupplierService {
    /**
     *〈简述〉保存供应商信息
     *〈详细描述〉
     * @author Song Biaowei
     * @param is 实体类
     */
    void register(ImportSupplierWithBLOBs is);
    
    /**
     *〈简述〉修改进口供应商信息
     *〈详细描述〉
     * @author Song Biaowei
     * @param is 实体类
     */
    void updateRegisterInfo(ImportSupplierWithBLOBs is);

    /**
     *〈简述〉按照ID查找
     *〈详细描述〉
     * @author Song Biaowei
     * @param id 主键
     * @return ImportSupplierWithBLOBs
     */
    ImportSupplierWithBLOBs findById(String id);

    /**
     *〈简述〉获取次数
     *〈详细描述〉
     * @author Song Biaowei
     * @param is 实体类
     * @return int
     */
    int getCount(ImportSupplierWithBLOBs is);

    /**
     *〈简述〉条件查询
     *〈详细描述〉
     * @author Song Biaowei
     * @param is 实体类
     * @param page 当前页
     * @return List<ImportSupplierWithBLOBs>
     */
    List<ImportSupplierWithBLOBs> selectByFsInfo(ImportSupplierWithBLOBs is, Integer page);

    /**
     *〈简述〉主键查询
     *〈详细描述〉
     * @author Song Biaowei
     * @param is 实体类
     * @return ImportSupplierWithBLOBs
     */
    ImportSupplierWithBLOBs selectByPrimaryKey(ImportSupplierWithBLOBs is);

    /**
     *〈简述〉按照名称查找
     *〈详细描述〉
     * @author Song Biaowei
     * @param is 实体类
     * @return String
     */
    String selectIdByLoginName(ImportSupplierWithBLOBs is);

    /**
     *〈简述〉删除
     *〈详细描述〉
     * @author Song Biaowei
     * @param id 主键
     */
    void delete(String id);
}
