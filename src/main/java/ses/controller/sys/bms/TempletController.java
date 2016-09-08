package ses.controller.sys.bms;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.Templet;
import ses.service.bms.TempletService;
import ses.service.bms.impl.TempletServiceImbl;


/**
 * 
 * @Title:TempletController
 * @Description:模版管理控制类
 * @author Liyi
 * @date 2016-9-1下午1:58:27
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/templet")
public class TempletController {
	@Resource
	private TempletService templetService;
	
	/**
	 * 
	 * @Title: getAll
	 * @author Liyi 
	 * @date 2016-9-1 下午1:58:18  
	 * @Description:获取模版列表
	 * @param: model
	 * @return: String
	 */
	@RequestMapping("/getAll")
	public String getAll(Model model){
		List<Templet> templets = templetService.getAll();
		model.addAttribute("list",templets);
		return "bms/templet/list";
	}
	
	/**
	 * 
	 * @Title: add
	 * @author Liyi 
	 * @date 2016-9-1 下午2:32:08  
	 * @Description:跳转新增编辑页面
	 * @param: request model    
	 * @return: String
	 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request,Model model){	
		return "bms/templet/add";
	}
	
	/**
	 * 
	 * @Title: save
	 * @author Liyi 
	 * @date 2016-9-1 下午2:37:08  
	 * @Description:保存新增信息
	 * @param: request id   
	 * @return:String
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request,Templet templet){
		templetService.save(templet);
		return "redirect:getAll.do";
	}
	
	/**
	 * 
	 * @Title: edit
	 * @author Liyi 
	 * @date 2016-9-1 下午2:37:08  
	 * @Description:跳转修改编辑页面
	 * @param: request id   
	 * @return:String
	 */
	@RequestMapping("/edit")
	public String edit(Model model,String id){
		model.addAttribute("templet",templetService.get(id));
		return "bms/templet/edit";
	}
	
	/**
	 * 
	 * @Title: update
	 * @author Liyi 
	 * @date 2016-9-1 下午3:40:34  
	 * @Description:更新修改信息
	 * @param: request    
	 * @param: id  
	 * @return: String
	 */
	@RequestMapping("/update")
	public String update(HttpServletRequest request,String id){
		return "redirect:getAll.do";
	}
	
	/**
	 * 
	 * @Title: delete
	 * @author Liyi 
	 * @date 2016-9-1 下午3:41:56  
	 * @Description:批量删除模板信息，逻辑删除
	 * @param: ids    
	 * @return: String
	 */
	@RequestMapping("/delete")
	public String delete(String ids){
		String[] id=ids.split(",");
		return "redirect:getAll.do";
	}
	
	/**
	 * 
	 * @Title: view
	 * @author Liyi 
	 * @date 2016-9-6 下午1:36:40  
	 * @Description:
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/view")
	public String view(Model model){
		return "bms/templet/view";
	}
}
