/**
 * 
 */
package ses.controller.sys.bms;

import java.util.List;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.github.pagehelper.PageInfo;

import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.service.bms.TodosService;

/**
 * @Description: 通知
 *	 
 * @author Wang Wenshuai
 * @date 2016年9月8日下午4:56:52
 * @since JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/todo")
public class ToDoController {

    @Autowired
    private TodosService todosService;


    /**
     * 
     *〈简述〉待办
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return 地址
     */
    @RequestMapping("/todos")
    public String todos(HttpServletRequest req, String type, String id){
        User user = (User) req.getSession().getAttribute("loginUser");
        if (user != null ){
            //代办事项
            Todos todos=new Todos();
            todos.setIsDeleted((short)0);
            todos.setIsFinish((short)0);
            todos.setReceiverId(user.getId());
            //机构id
            if (user.getOrg() != null && user.getOrg().getId() != null ){
                todos.setOrgId(user.getOrg().getId());
            }
            //角色id
            if (user.getRoles() != null && user.getRoles().size() != 0){
                todos.setRoleIdArray(user.getRoles());
            }
            req.setAttribute("listTodos", todosService.listTodos(todos));
        }
        return "ses/bms/todo/todos";
    }

    /**
     * 
     *〈简述〉已办
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return 地址
     */
    @RequestMapping("/havetodo")
    public String havetodo(HttpServletRequest req,String type,Todos todos, String pages, String id){
        User user = (User) req.getSession().getAttribute("loginUser");
        if (user != null ){
            //          //已办事项
            todos.setIsFinish(new Short("1"));
            todos.setReceiverId(user.getId());
            if(user.getOrg() != null && user.getOrg().getId() != null && !"".equals(user.getOrg().getId())){
                todos.setOrgId(user.getOrg().getId());
            }
            if (user.getRoles() != null && user.getRoles().size() !=0){
                todos.setRoleIdArray(user.getRoles());
            }
            List<Todos> listHaveTodo = todosService.listHaveTodo(todos, pages == null || "".equals(pages) ? 1 : Integer.valueOf(pages));
            req.setAttribute("list", new PageInfo<Todos>(listHaveTodo));
        }
        req.setAttribute("todos",todos);

        return "ses/bms/todo/havetodo";
    }
}
