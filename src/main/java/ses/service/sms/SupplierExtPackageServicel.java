package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierExtPackage;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 根据包抽取
 * <详细描述>
 * @author   Wang Wenshuai
 * @version  
 * @since
 * @see
 */
public interface SupplierExtPackageServicel {
    
    /**
     * 
     *〈简述〉获取集合
     *〈详细描述〉
     * @author Wang Wenshuai
     */
    List<SupplierExtPackage> list(SupplierExtPackage sExtPackage, String page);

    /**
     * 
     *〈简述〉插入
     *〈详细描述〉
     * @author Wang Wenshuai
     */
    void update(SupplierExtPackage sExtPackage);
    
    /**
     * 
     *〈简述〉插入
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param sExtPackage 对象
     */
    void insert(SupplierExtPackage sExtPackage);
    
    /**
     * 
     *〈简述〉返回抽取记录集合
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param SupplierExtPackage
     */
    List<SupplierExtPackage> extractsList(SupplierExtPackage record);
    
}

