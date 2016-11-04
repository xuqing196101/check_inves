/**
 * 
 */
package ses.controller.sys.bms;

import java.util.List;











import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ctc.wstx.dtd.StarModel;
import com.github.pagehelper.PageInfo;

import ses.model.bms.StationMessage;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.service.bms.StationMessageService;
import ses.service.bms.TodosService;

/**
 * @Description: 站内消息
 *	 
 * @author Wang Wenshuai
 * @date 2016年9月8日下午4:56:52
 * @since JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/StationMessage")
public class StationMessageController {

    @Autowired
    private StationMessageService stationMessageService;
    
    @Autowired
    private TodosService todosService;


    /**
     * @Description:插入站内消息
     *
     * @author Wang Wenshuai
     * @date 2016年9月8日 下午5:14:04  
     * @param @param stationMessage     
     * @return void
     */
    @RequestMapping("/insertStationMessage")
    public void insertStationMessage(HttpServletRequest request,StationMessage stationMessage1) {
        //发送通知
        StationMessage stationMessage =new StationMessage();
        //发送用户id 必填  
        stationMessage.setSenderId("");
        //标题 必填
        stationMessage.setName("");
        //接收用户id 选填
        stationMessage.setReceiverId("");
        //权限id 选填
        stationMessage.setPowerId("");
        //机构id 选填
        stationMessage.setOrgId("");
        //插入通知表
        stationMessageService.insertStationMessage(stationMessage);
        
        //发送待办
        Todos todos=new Todos();
        //发送用户id 必填  
        todos.setSenderId("");
        //标题 必填
        todos.setName("");
        //接收用户id 选填
        todos.setReceiverId("");
        //权限id 选填
        todos.setPowerId("");
        //机构id 选填
        todos.setOrgId("");
        todosService.insert(todos);
    }


    /**
     * @Description:分页获取集合
     *
     * @author Wang Wenshuai
     * @date 2016年9月8日 下午5:17:51  
     * @param @param stationMessage
     * @param @return      
     * @return List<StationMessage>
     */
    @RequestMapping("/listStationMessage")
    public String listStationMessage(HttpServletRequest req,Model model, StationMessage stationMessage,String page) {
        //第几页
        User user = (User) req.getSession().getAttribute("loginUser");
        if (user != null){
            stationMessage.setReceiverId(user.getId());
            stationMessage.setOrgId(user.getOrg().getId());
            List<StationMessage> listStationMessage = stationMessageService.listStationMessage(stationMessage,page==null||"".equals(page)?1:Integer.valueOf(page));
            model.addAttribute("listStationMessage", new PageInfo<StationMessage>(listStationMessage));
            model.addAttribute("stationMessage",stationMessage);
        }

        return "ses/bms/station/list";
    }


}
