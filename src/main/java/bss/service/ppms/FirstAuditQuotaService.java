package bss.service.ppms;

import java.util.List;

import bss.model.ppms.FirstAuditQuota;

/**
 * 版权：(C) 版权所有 
 * <简述>供应商投标初审项值投标指标值业务处理接口
 * <详细描述>
 * @author   Ye MaoLin
 * @version  
 * @since
 * @see
 */
public interface FirstAuditQuotaService {

    /**
     *〈简述〉查询list
     *〈详细描述〉
     * @author Ye MaoLin
     * @param faq 初审项投标值对象
     * @return  初审项投标值对象集合
     */
    List<FirstAuditQuota> find(FirstAuditQuota faq);
    
    /**
     *〈简述〉根据主键id查询 初审项投标值对象
     *〈详细描述〉
     * @author Ye MaoLin
     * @param id主键id
     * @return  初审项投标值对象
     */
    FirstAuditQuota get(String id);
    
    /**
     *〈简述〉更新数据
     *〈详细描述〉
     * @author Ye MaoLin
     * @param faq  初审项投标值对象
     */
    void update(FirstAuditQuota faq);

    /**
     *〈简述〉保存数据
     *〈详细描述〉
     * @author Ye MaoLin
     * @param aq  初审项投标值对象
     */
    void save(FirstAuditQuota faq);
    
    /**
     *〈简述〉删除数据
     *〈详细描述〉
     * @author Ye MaoLin
     * @param id 主键id
     */
    void delete(String id);
}
