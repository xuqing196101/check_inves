/**
 * 
 */
package ses.service.ems;

import java.util.List;

import ses.model.bms.User;
import ses.model.ems.ProExtSupervise;
import ses.model.sms.SupplierExtUser;


/**
 * @Description:监督人员
 *	 
 * @author Wang Wenshuai
 * @version 2016年10月14日下午7:33:27
 * @since  JDK 1.7
 */
public interface ProjectSupervisorServicel {
    /**
     * @Description:集合
     *
     * @author Wang Wenshuai
     * @version 2016年10月14日 下午7:34:06  
     * @param @return      
     * @return List<ProjectSupervisor>
     */
    List<ProExtSupervise> list(ProExtSupervise extSupervise);

    /**
     * @Description:根据项目id删除监督信息
     *
     * @author Wang Wenshuai
     * @version 2016年10月15日 下午7:05:15  
     * @param       
     * @return void
     */
    void deleteProjectId(String prjectId);

    /**
     * 
     *〈简述〉插入
     *〈详细描述〉
     * @author Wang Wenshuai
     */
    void insert(ProExtSupervise record);

    /**
     * 批量插入
     *〈简述〉
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param list
     */
    void  listInsert(List<ProExtSupervise> list);
    
    /**
     * 修改
     *〈简述〉
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param extSupervise
     */
    void update(ProExtSupervise extSupervise);
}
