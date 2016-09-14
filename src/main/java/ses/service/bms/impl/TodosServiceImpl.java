/**
 * 
 */
package ses.service.bms.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.bms.TodosMapper;
import ses.model.bms.Todos;
import ses.service.bms.TodosService;

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
		mapper.insert(todos);
		
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
	public List<List<Todos>>  listTodos(Todos todos){
		List<String> listStr=listUndoType();
		List<List<Todos>> llTodos=new ArrayList<List<Todos>>();
		for (String str : listStr) {
			List<Todos> list=mapper.listTodos(new Todos(todos.getIsFinish(), new Short(str)));
			if(list!=null&&list.size()!=0){
				llTodos.add(list);
			}
		}
		return llTodos;
	}
	/**
 	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月12日 下午5:18:30  
	 * @param       
	 * @return void
	 */
	@Override
	public void updateIsFinish(String id){
		mapper.updateByPrimaryKey(new Todos(id));
	}

	/* (non-Javadoc)
	 * @see ses.service.bms.TodosService#listUndoType()
	 */
	@Override
	public List<String> listUndoType() {
		return mapper.listUndoType();
	}
}
