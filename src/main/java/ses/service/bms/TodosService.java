/**
 * 
 */
package ses.service.bms;

import java.util.List;
import java.util.Map;

import ses.model.bms.Todos;

/**
 * @Description: 待办事项
 *	 
 * @author Wang Wenshuai
 * @date 2016年9月12日下午5:06:51
 * @since  JDK 1.7
 */
public interface TodosService {

	/**
	 * @Description:插入待办事项
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月12日 下午5:15:26  
	 * @param @param todos      
	 * @return void
	 */
	void insert(Todos todos);

	/**
	 * @Description:获取待办list集合
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月12日 下午5:15:53  
	 * @param @param todos
	 * @param @return      
	 * @return List<Todos>
	 */
	List<List<Todos>>  listTodos(Todos todos,String orgId);

	/**
	 * @Description:获取已办list集合
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月12日 下午5:15:53  
	 * @param @param todos
	 * @param @return      
	 * @return List<Todos>
	 */
	List<Todos>  listHaveTodo(Todos todos,String orgId,Integer pageNum);
	/**
	 * @Description:是否完成
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月12日 下午5:18:30  
	 * @param       
	 * @return void
	 */
	void updateIsFinish(String url);

	/**
	 * @Description:获取任务类型
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月14日 下午1:58:02  
	 * @param @return      
	 * @return List<String>
	 */
	List<String> listUndoType();
}
