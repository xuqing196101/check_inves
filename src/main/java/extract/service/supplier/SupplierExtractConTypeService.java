/**
 * 
 */
package extract.service.supplier;

import extract.model.supplier.SupplierConType;

/**
 * @Description:条件关联表
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月29日下午7:23:58
 * @since  JDK 1.7
 */

public interface SupplierExtractConTypeService {
    /**
     * @Description:插入
     *
     * @author Wang Wenshuai
     * @version 2016年9月29日 下午7:26:30  
     * @param       
     * @return void
     */
    void insert(SupplierConType record);

    /**
     * @Description:删除
     *
     * @author Wang Wenshuai
     * @version 2016年9月30日 下午2:30:05  
     * @param       
     * @return void
     */
    void delete(String id);

    /**
     * @Description:修改
     *
     * @author Wang Wenshuai
     * @version 2016年10月12日 下午3:33:22  
     * @param @param conType      
     * @return void
     */
    void update(SupplierConType conType);

    /**
     * @Description:获取一个对象
     *
     * @author Wang Wenshuai
     * @version 2016年10月12日 下午3:33:22  
     * @param @param conType      
     * @return void
     */
    SupplierConType getExtConType(String id);
    
    /**
     * 根据供应商类型返回抽取数量
     *〈简述〉
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    Integer getSupplierTypeById(String conId,String supplierTypeId);
    
    /**
     * 
     *〈简述〉获取总和
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param conId
     * @return
     */
    Integer getSum(String conId);

}
