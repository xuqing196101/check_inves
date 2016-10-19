/**
 * 
 */
package ses.service.sms;

import java.util.List;

import ses.model.bms.User;
import ses.model.ems.ProExtSupervise;
import ses.model.sms.SupplierExtUser;


/**
 * @Description:
 *	 
 * @author Wang Wenshuai
 * @version 2016年10月14日下午7:33:27
 * @since  JDK 1.7
 */
public interface SupplierExtUserServicel {
	/**
	 * @Description:集合
	 *
	 * @author Wang Wenshuai
	 * @version 2016年10月14日 下午7:34:06  
	 * @param @return      
	 * @return List<ProjectSupervisor>
	 */
	List<User> list(SupplierExtUser extSupervise);
}
