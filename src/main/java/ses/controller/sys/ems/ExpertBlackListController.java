package ses.controller.sys.ems;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.ems.ExpertBlackList;
import ses.service.ems.ExpertBlackListService;

/**
 * <p>Title:ExpertBlackListController </p>
 * <p>Description: 专家黑名单控制器</p>
 * @author Xu Qing
 * @date 2016-9-8下午2:51:05
 */
@Controller
@RequestMapping("/expert")
public class ExpertBlackListController {
	@Autowired
	private ExpertBlackListService service;
	/**
	 * @Title: add
	 * @author Xu Qing
	 * @date 2016-9-8 下午5:13:31  
	 * @Description: 添加页面 
	 * @param @param expertBlackList
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/addBlacklist")
	public String add(ExpertBlackList expertBlackList){
		return "ems/expertBlackList/add";
	}
	/**
	 * @Title: save
	 * @author Xu Qing
	 * @date 2016-9-8 下午5:13:53  
	 * @Description: 保存信息 
	 * @param @param expertBlackList
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/saveBlacklist")
	public String save(ExpertBlackList expertBlackList){
		expertBlackList.setCreatedAt(new Date());
		service.insert(expertBlackList);
		return "redirect:blacklist.html";
	}
	/**
	 * @Title: fnidList
	 * @author Xu Qing
	 * @date 2016-9-8 下午5:14:12  
	 * @Description: 列表页 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/blacklist")
	public String fnidAll(Model model){
		List<ExpertBlackList> expertList = service.findAll();
		model.addAttribute("expertList", expertList);
		return "ems/expertBlackList/list";
	}
	/**
	 * @Title: edit
	 * @author Xu Qing
	 * @date 2016-9-9 下午1:52:05  
	 * @Description: 修改页面 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/editBlacklist")
	public String edit(HttpServletRequest request, Model model){
		String id = request.getParameter("id");
		ExpertBlackList expertBlackList = service.findById(id);
		model.addAttribute("expert", expertBlackList);
		return "ems/expertBlackList/edit";
	}
	/**
	 * @Title: update
	 * @author Xu Qing
	 * @date 2016-9-9 下午3:12:04  
	 * @Description: 更新数据 
	 * @param @param expertBlackList
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/updateBlacklist")
	public String update(ExpertBlackList expertBlackList){
		expertBlackList.setCreatedAt(new Date());
		service.update(expertBlackList);
		return "redirect:blacklist.html";
	}
	/**
	 * @Title: delete
	 * @author Xu Qing
	 * @date 2016-9-9 下午4:54:50  
	 * @Description: 根据id批量删除信息 
	 * @param @param id
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/deleteBlacklist")
	public String delete(HttpServletRequest request){
		String[] ids = request.getParameter("ids").split(",");
		for(int i = 0;i<ids.length;i++){
			service.delete(ids[i]);
		}
		return "redirect:blacklist.html";
	}
	/**
	 * @Title: delete
	 * @author Xu Qing
	 * @date 2016-9-12 下午3:30:37  
	 * @Description: 条件查询 
	 * @param @param request
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/queryBlacklist")
	public String query(HttpServletRequest request,ExpertBlackList expert,HttpServletResponse response){
		List<ExpertBlackList> expertList = service.query(expert);
		request.setAttribute("expertList", expertList);
		return "ems/expertBlackList/list";
	}
	
	
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

}
