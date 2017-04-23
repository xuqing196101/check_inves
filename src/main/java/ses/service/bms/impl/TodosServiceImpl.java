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
import ses.model.bms.PreMenu;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.service.bms.PreMenuServiceI;
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

  @Autowired
  PreMenuServiceI preMenuServiceI;
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
  public List<List<Todos>>  listTodos(Todos todos){

    List<String> listUserPermission = getPermisssion(todos.getReceiverId());

    //拼装条件。接收用户id，接收机构id， 接收权限id。满足单个条件即可查询（ OR） 
    Map<String, Object> map = new HashMap<String, Object>();
    List<List<Todos>> llTodos = new ArrayList<List<Todos>>();
    List<String> listStr = listUndoType();
    for (String str : listStr) {
      todos.setUndoType(new Short(str));
      map = new HashMap<String, Object>();
      map.put("todos", todos);
      map.put("permission", listUserPermission);
      List<Todos> list = mapper.listTodos(map);
      if (list != null && list.size() != 0){
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
   * @param  todos 待办对象
   * @param pageNum   当前页数  
   * @return List<Todos>
   */
  @Override
  public List<Todos>  listHaveTodo(Todos todos, Integer pageNum){
    //读取配置文件中的id查询出当前用户是否有权限,返回权限id集合
    List<String> listUserPermission = getPermisssion(todos.getReceiverId());
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("todos", todos);
    map.put("permission", listUserPermission);
    PropertiesUtil config = new PropertiesUtil("config.properties");
    PageHelper.startPage(pageNum, Integer.parseInt(config.getString("pageSize")));
    List<Todos> list = mapper.listTodos(map);
    return list;
  }

  /**
   * 
   *〈简述〉获取所有推送权限
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param userId 登录用户id
   * @return 权限id 集合
   */
  @Override
  public  List<String> getPermisssion(String userId) {
    PropertiesUtil config = new PropertiesUtil("config.properties");
    String gyscs = config.getString("gyscs");
    String gysfs = config.getString("gysfs");
    String gysjk = config.getString("gysjk");
    String zjdb = config.getString("zjdb");
    String gysedit = config.getString("gysedit");
    String zbwjsh = config.getString("zbwjsh");
    Map<String, Object> pMap = new HashMap<String, Object>();
    List<String> listUserPermission = new ArrayList<String>();
    if (gyscs != null && zjdb != null && gysfs != null && gysedit != null){
      String[] db = {gyscs, gysfs, zjdb,gysjk,gysedit,zbwjsh};
      pMap.put("id", userId);
      pMap.put("db", db);
      User user = new User();
      user.setId(userId);
      List<PreMenu> menu = preMenuServiceI.getMenu(user);
      if (menu != null && menu.size() != 0) {
        for (String str : db) {
          for (PreMenu preMenu : menu) {
            if(preMenu != null){
              if(str.equals(preMenu.getId())){
                listUserPermission.add(str);
              }
            }

          }
        }
      }

    }

    if (listUserPermission != null && listUserPermission.size() != 0){
      return listUserPermission;
    }else{
      return  null;  
    }
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

  /**
   * 更新待办
   */
  @Override
  public void updateByUrl(Todos todos) {
    mapper.updateByUrl(todos);

  }

  @Override
  public List<Todos> listUrlTodo(Todos todos) {
    // TODO Auto-generated method stub
    return mapper.listUrlTodo(todos);
  }

@Override
public List<Todos> listUrlTodoPage(Todos todos,Short types,Integer page) {
	
	List<String> listUserPermission = getPermisssion(todos.getReceiverId());
      Map<String, Object> map = new HashMap<String, Object>();
      todos.setUndoType(types);
      map = new HashMap<String, Object>();
      map.put("todos", todos);
      map.put("permission", listUserPermission);
      PropertiesUtil config = new PropertiesUtil("config.properties");
	  PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
      List<Todos> list = mapper.listTodos(map);
      return list;
     }
}
