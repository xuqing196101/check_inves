package ses.controller.sys.sms;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.util.NewBeanInstanceStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.SupplierAgents;
import ses.service.sms.SupplierAgentsService;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageInfo;


@Controller
@Scope("prototype")
@RequestMapping("/supplierAgents")
public class SupplierAgentsController {
	@Autowired
	private SupplierAgentsService agentsService;

	/**
	 * 
	 * @Title: getAllSupplierAgents
	 * @author Ws
	 * @date 2016年9月5日 下午3:44:34  
	 * @Description:   首页展示信息
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/getAllSupplierAgents")
	public String showSupplierAgents(HttpServletRequest req){
		//代办事项
		List<SupplierAgents> getListSupplier=agentsService.getAllSupplierAgent(new SupplierAgents(new Short("0")), 1, 8);
		req.setAttribute("SupplierAgent", getListSupplier);
		//催办事项
		List<SupplierAgents> getListSupplierReminders=agentsService.getAllSupplierAgent(new SupplierAgents(new Short("1")), 1, 8);
		req.setAttribute("SupplierReminders", getListSupplierReminders);
		return "/backend";
	}
	/**
	 * 
	 * @Title: listSupplierAgents
	 * @author Wang Wenshuai
	 * @date 2016年9月7日 下午5:00:27  
	 * @Description:   列表获取代办事项|催办事项
	 * @param @param req
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/listSupplierAgents")
	public String listSupplierAgents(HttpServletRequest req,String type,String page,String id){
		PropertiesUtil config = new PropertiesUtil("config.properties");
		List<SupplierAgents> getListSupplier=agentsService.getAllSupplierAgent(new SupplierAgents(new Short(type)), page==null?1:Integer.parseInt(page), Integer.parseInt(config.getString("pageSize")) );
		req.setAttribute("getListSupplier",new PageInfo<SupplierAgents>(getListSupplier));
		req.setAttribute("type", type);
		req.setAttribute("id", id);
		return "sms/supplieragentlist";
	}
	/**
	 * @Description:   软删除代办事项
	 * 
	 * @author Wang Wenshuai
	 * @date 2016年9月7日 下午6:22:43  
	 * @param @param req
	 * @param @param ids
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/deleteSoftSupplierAgents")
	public String deleteSoftSupplierAgents (String ids,String type){
		String[] id=ids.split(",");
		for (String str : id) {
			agentsService.deleteSoftSupplierAgents(new SupplierAgents(str, new Short("1")));
		}
		return "redirect:listSupplierAgents.html?type="+type;
	}
	/**
	 * @Description: 修改代办为催办
	 * 
	 * @author Wang Wenshuai
	 * @date 2016年9月7日 下午6:22:43  
	 * @param @param req
	 * @param @param ids
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/updateIsReminders")
	public String updateIsReminders (String id,String type){
			agentsService.updateIsReminders(new SupplierAgents(id,new Date(),new Short("1")));
		return "redirect:listSupplierAgents.html?type="+type;
	}
}
