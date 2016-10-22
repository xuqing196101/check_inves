/**
 * 
 */
package ses.service.bms.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.bms.TodosMapper;
import ses.model.bms.Todos;
import ses.service.bms.TodosService;
import ses.util.PropertiesUtil;

/**
 * @Description:
 *	 
 * @author Wang Wenshuai
 * @date 2016年9月12日下午5:07:11
 * @since  JDK 1.7
 */
@Service("TodosService")
public class TodosServiceImpl implements TodosService {

	@Autowired
	TodosMapper mapper; 
	/**
	 * @Description:插入待办事项
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月12日 下午5:15:26  
	 * @param @param todos      
	 * @return void
	 */
	@Override
	public void insert(Todos todos){
		mapper.insertSelective(todos);
	}

	/**
	 * @Description:获取待办list集合
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月12日 下午5:15:53  
	 * @param @param todos
	 * @param @return      
	 * @return List<Todos>
	 */
	@Override
	public List<List<Todos>>  listTodos(Todos todos,String orgId){
		Map<String, Object> map=new HashMap<String, Object>();
		List<List<Todos>> llTodos=new ArrayList<List<Todos>>();
		List<String> listStr=listUndoType();
		for (String str : listStr) {
			map.put("todos", new Todos(todos.getIsFinish(), new Short(str)));
			map.put("ids", orgId);
			List<Todos> list=mapper.listTodos(map);
			if(list!=null&&list.size()!=0){
				llTodos.add(list);
			}
		}
		return llTodos;
	}

	/**
	 * @Description:获取已办list集合
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月12日 下午5:15:53  
	 * @param @param todos
	 * @param @return      
	 * @return List<Todos>
	 */
	@Override
	public List<Todos>  listHaveTodo(Todos todos,String orgId,Integer pageNum){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("todos", todos);
		map.put("ids",orgId);
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		List<Todos> list=mapper.listTodos(map);
		return list;
	}
	/**
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月12日 下午5:18:30  
	 * @param       
	 * @return void
	 */
	@Override
	public void updateIsFinish(String url){
		mapper.updateIsFinish(new Todos(url));
	}
	/* (non-Javadoc)
	 * @see ses.service.bms.TodosService#listUndoType()
	 */
	@Override
	public List<String> listUndoType() {
		return mapper.listUndoType();
	}
}
