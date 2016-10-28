/**
 * 
 */
package ses.service.sms;

import ses.model.ems.ExtConType;
import ses.model.sms.SupplierConType;


/**
 * @Description:条件关联表
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月29日下午7:23:58
 * @since  JDK 1.7
 */

public interface SupplierConTypeService {
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

}
