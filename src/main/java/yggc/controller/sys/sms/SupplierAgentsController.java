package yggc.controller.sys.sms;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.druid.stat.TableStat.Mode;

import yggc.model.sms.SupplierAgents;
import yggc.service.sms.SupplierAgentsService;

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
	 * @Description: TODO  首页展示信息
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/getAllSupplierAgents")
	public String showSupplierAgents(HttpServletRequest req){
		//代办事项
		List<SupplierAgents> getListSupplier=agentsService.getAllSupplierAgent(new SupplierAgents(new Short("0")));
		req.setAttribute("SupplierAgent", getListSupplier);
		//催办事项
		List<SupplierAgents> getListSupplierReminders=agentsService.getAllSupplierAgent(new SupplierAgents(new Short("1")));
		req.setAttribute("SupplierReminders", getListSupplierReminders);
		return "/backend";
	}
	/**
	 * 
	 * @Title: listSupplierAgents
	 * @author Wang Wenshuai
	 * @date 2016年9月7日 下午5:00:27  
	 * @Description: TODO  列表获取代办事项|催办事项
	 * @param @param req
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/listSupplierAgents")
	public String listSupplierAgents(HttpServletRequest req,String type){
		List<SupplierAgents> getListSupplier=agentsService.getAllSupplierAgent(new SupplierAgents(new Short(type)) );
		req.setAttribute("getListSupplier", getListSupplier);
		return "sms/supplieragentlist";
	}
	
	/**
	 * 软删除代办事项
	 * @Title: deleteSoftSupplierAgents
	 * @author Wang Wenshuai
	 * @date 2016年9月7日 下午6:22:43  
	 * @Description: TODO 
	 * @param @param req
	 * @param @param ids
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/deleteSoftSupplierAgents")
	public String deleteSoftSupplierAgents (HttpServletRequest req,String ids){
		String[] id=ids.split(",");
		for (String str : id) {
			agentsService.deleteSoftSupplierAgents(str);
		}
		return "redirect:getAllSupplierAgents.html";
	}
}
