package bss.dao.ppms;

import java.util.List;

import bss.model.ppms.FirstAuditQuota;

/**
 * 版权：(C) 版权所有 
 * <简述> 供应商投标初审项值
 * <详细描述>
 * @author   Ye MaoLin
 * @version  
 * @since
 * @see
 */
public interface FirstAuditQuotaMapper {
    
    /**
     *〈简述〉根据id删除数据
     *〈详细描述〉
     * @author Ye MaoLin
     * @param id
     */
    void delete(String id);

    /**
     *〈简述〉插入数据
     *〈详细描述〉
     * @author Ye MaoLin
     * @param record
     */
    void insert(FirstAuditQuota record);

    /**
     *〈简述〉根据id查询供应商投标初审项值对象
     *〈详细描述〉
     * @author Ye MaoLin
     * @param id
     * @return
     */
    FirstAuditQuota selectByPrimaryKey(String id);

    /**
     *〈简述〉更新供应商投标初审项值
     *〈详细描述〉
     * @author Ye MaoLin
     * @param record
     */
    void update(FirstAuditQuota record);
    
    /**
     *〈简述〉根据条件查询
     *〈详细描述〉
     * @author Ye MaoLin
     * @param record
     * @return
     */
    List<FirstAuditQuota> findList(FirstAuditQuota record);

}