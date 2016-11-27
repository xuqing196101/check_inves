/**
 * 
 */
package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierExtUserMapper;
import ses.model.bms.User;
import ses.model.sms.SupplierExtUser;
import ses.service.sms.SupplierExtUserServicel;

/**
 * @Description:监督人员
 *	 
 * @author Wang Wenshuai
 * @version 2016年10月17日下午3:52:16
 * @since  JDK 1.7
 */
@Service
public class SupplierExtUserServicelmpl implements SupplierExtUserServicel {
    @Autowired
    SupplierExtUserMapper supplierExtUserMapper;

    /**
     * @Description:集合
     *
     * @author Wang Wenshuai
     * @version 2016年10月14日 下午7:34:06  
     * @param @return      
     * @return List<ProjectSupervisor>
     */
    @Override
    public List<User> list(SupplierExtUser extSupervise) {
        return supplierExtUserMapper.list(extSupervise);
    }

    /**
     * @Description:根据项目id删除监督信息
     *
     * @author Wang Wenshuai
     * @version 2016年10月15日 下午7:05:15  
     * @param       
     * @return void
     */
    @Override
    public void deleteProjectId(String prjectId) {
         supplierExtUserMapper.deleteProjectId(prjectId);
    }

    @Override
    public void insert(SupplierExtUser record) {
        supplierExtUserMapper.insertSelective(record);
    }

}
