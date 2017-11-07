/**
 * 
 */
package ses.service.sms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ses.model.sms.SupplierExtUser;


/**
 * @Description: 监督人员
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
	List<SupplierExtUser> list(SupplierExtUser extSupervise);
	
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
    void insert(SupplierExtUser record);
    
    /**
     * 
     *〈简述〉批量插入
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param listInsert
     */
    void listInsert(List<SupplierExtUser> listInsert);
    
    /**
     * 
     *〈简述〉修改
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param extUser
     */
    void update(SupplierExtUser extUser);
    
    /**
     * 
     *〈简述〉生成模板
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param request
     * @param projectId
     * @return
     * @throws Exception
     * @param type:区分类型 0 只是生成 总模板 1 生成分包模板
     */
    String downLoadBiddingDoc(HttpServletRequest request,String projectId,int type,String suppliersID,Object...param) throws Exception;
    
    /**
     * 
     *〈预研生成模板〉
     *〈详细描述〉
     * @author FengTian
     * @param request
     * @param projectId
     * @param type
     * @param suppliersID
     * @return
     * @throws Exception
     */
    String downLoadBiddingDocs(HttpServletRequest request,String projectId,int type,String suppliersID) throws Exception;
    
}
