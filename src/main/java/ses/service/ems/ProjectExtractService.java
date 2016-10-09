/**
 * 
 */
package ses.service.ems;

import java.util.List;

import net.sf.jsqlparser.statement.update.Update;
import ses.model.ems.ProjectExtract;

/**
 * @Description:抽取记录关联专家
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月28日下午4:10:47
 * @since  JDK 1.7
 */
public interface ProjectExtractService {
	/**
	 * @Description:insert
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 下午4:12:09  
	 * @param       
	 * @return void
	 */
	String insert(String  cId,String userId);
	
	/**
	 * @Description:集合展示
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 下午6:07:39  
	 * @param @param projectExtract      
	 * @return void
	 */
	List<ProjectExtract> list(ProjectExtract projectExtract);
	
	/**
	 * @Description:修改操作状态
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 下午8:02:39  
	 * @param @param projectExtract      
	 * @return void
	 */
	void update(ProjectExtract projectExtract);
}
