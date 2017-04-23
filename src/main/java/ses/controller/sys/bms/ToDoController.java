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

import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.oms.PurchaseDep;
import ses.service.bms.TodosService;
import ses.service.oms.PurchaseOrgnizationServiceI;

import com.github.pagehelper.PageInfo;

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
    
    @Autowired
    private PurchaseOrgnizationServiceI purchaseOrgnizationService;


    /**
     * 
     *〈简述〉待办
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return 地址
     */
    @RequestMapping("/todos")
    public String todos(HttpServletRequest req, String type, String id,Integer projectPage,Integer expertPage,Integer supplierPage){
        User user = (User) req.getSession().getAttribute("loginUser");
        if (user != null ){
            //代办事项
            Todos todos=new Todos();
            todos.setIsDeleted((short)0);
            todos.setIsFinish((short)0);
            todos.setReceiverId(user.getId());
            //机构id
            if (user.getOrg() != null && user.getOrg().getId() != null ){
                PurchaseDep dep = purchaseOrgnizationService.selectByOrgId(user.getOrg().getId());
                if (dep != null ) {
                  if( dep.getId() != null){
                    todos.setOrgId(dep.getId());
                  }else{
                    todos.setOrgId(dep.getOrgId());
                  }
                }
            }
            //角色id
            if (user.getRoles() != null && user.getRoles().size() != 0){
                todos.setRoleIdArray(user.getRoles());
            }
            //List<String> listUserPermission = getPermisssion(todos.getReceiverId());
            if(projectPage==null){
            	projectPage=1;
            }
            if(expertPage==null){
            	expertPage=1;
            }
            if(supplierPage==null){
            	supplierPage=1;
            }
            List<Todos> supplierlist = todosService.listUrlTodoPage(todos, (short)1,supplierPage);
            PageInfo<Todos> supplierTodos = new PageInfo<Todos>(supplierlist);
            List<Todos> expertlist = todosService.listUrlTodoPage(todos, (short)2,expertPage);
            PageInfo<Todos> expertTodos = new PageInfo<Todos>(expertlist);
            List<Todos> projectlist = todosService.listUrlTodoPage(todos, (short)3,projectPage);
            PageInfo<Todos> projectTodos = new PageInfo<Todos>(projectlist);
            req.setAttribute("supplierTodos", supplierTodos);
            req.setAttribute("expertTodos", expertTodos);
            req.setAttribute("projectTodos",projectTodos);
            req.setAttribute("type", type);
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
                PurchaseDep dep = purchaseOrgnizationService.selectByOrgId(user.getOrg().getId());
                if (dep != null && dep.getId() != null) {
                    todos.setOrgId(dep.getId());
                }else{
                  todos.setOrgId(dep.getOrgId());
                }
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
