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
    List<List<Todos>>  listTodos(Todos todos);

    /**
     * @Description:获取已办list集合
     *
     * @author Wang Wenshuai
     * @date 2016年9月12日 下午5:15:53  
     * @param @param todos
     * @param @return      
     * @return List<Todos>
     */
    List<Todos>  listHaveTodo(Todos todos,Integer pageNum);
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

    /**
     * @Title: updateByUrl
     * @author Xu Qing
     * @date 2016-10-24 上午11:06:25  
     * @Description: 更新待办
     * @param @param todos      
     * @return void
     */
    void updateByUrl(Todos todos);

    /**
     * 
     *〈简述〉获取所有推送权限
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param userId 登录用户id
     * @return 权限id 集合
     */
    List<String> getPermisssion(String userId);
    
    /**
     * 
     *〈简述〉条件查询集合
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param todos
     * @return
     */
    List<Todos> listUrlTodo(Todos todos);
    
    List<Todos> listUrlTodoPage(Todos todos,Short types,Integer page);
    
    
}
