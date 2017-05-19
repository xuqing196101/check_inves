/**
 * 
 */
package ses.controller.sys.bms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.oms.PurchaseDep;
import ses.service.bms.TodosService;
import ses.service.oms.PurchaseOrgnizationServiceI;

import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;

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
        return "ses/bms/todo/todos";
    }
    @RequestMapping("/supplier")
    @ResponseBody
    public String supplier(@CurrentUser User user,HttpServletRequest req, Integer type, String id,Integer page,Integer expertPage,Integer supplierPage){
    	JSONObject jsonObj = new JSONObject();
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
            if(page==null||"null".equals(page)){
            	page=1;
            }
            PageInfo<Todos> pageInfo=null;
            //供应商待办
            if(type==0){
            	 List<Todos> supplierlist = todosService.listUrlTodoPage(todos, (short)1,page);
            	 pageInfo= new PageInfo<Todos>(supplierlist);
            }else if(type==1){//专家待办
            	List<Todos> expertlist = todosService.listUrlTodoPage(todos, (short)2,page);
            	pageInfo= new PageInfo<Todos>(expertlist);
            }else if(type==2){//项目待办
            	List<Todos> projectlist = todosService.listUrlTodoPage(todos, (short)3,page);
            	pageInfo = new PageInfo<Todos>(projectlist);
            }
            /*for(Todos to:pageInfo.getList()){
            	to.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(to.getCreatedAt()));
            }
            
            */
            
            jsonObj.put("pages", pageInfo.getPages());
            jsonObj.put("data", pageInfo.getList());
            jsonObj.put("pageNum", pageInfo.getPageNum());
            jsonObj.put("pageSize", pageInfo.getPageSize());
            jsonObj.put("total", pageInfo.getTotal());
            
        
            return jsonObj.toString();
    }
    /**
     * 
     *〈简述〉已办
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return 地址
     */
    @RequestMapping("/havetodo")
    public String havetodo(@CurrentUser User user,HttpServletRequest req,String type,Todos todos, String pages, String id){
            //已办事项
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
        req.setAttribute("todos",todos);

        return "ses/bms/todo/havetodo";
    }
}
