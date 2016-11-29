package ses.service.ems;

import java.util.List;

import ses.model.ems.ExpExtPackage;
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
public interface ExpExtPackageService {
    
    /**
     * 
     *〈简述〉获取集合
     *〈详细描述〉
     * @author Wang Wenshuai
     */
    List<ExpExtPackage> list(ExpExtPackage sExtPackage, String page);

    /**
     * 
     *〈简述〉插入
     *〈详细描述〉
     * @author Wang Wenshuai
     */
    void update(ExpExtPackage sExtPackage);
    
    /**
     * 
     *〈简述〉插入
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param sExtPackage 对象
     */
    void insert(ExpExtPackage sExtPackage);
    
    /**
     * 
     *〈简述〉返回抽取记录集合
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param SupplierExtPackage
     */
    List<ExpExtPackage> extractsList(ExpExtPackage record);
    
    /**
     * 
     *〈简述〉 getbyid
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param id id
     * @return 对象
     */
    ExpExtPackage getById(String  id);
}

