package bss.controller.prms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.pagehelper.PageInfo;

import bss.model.prms.FirstAuditTemitem;
import bss.model.prms.FirstAuditTemplat;
import bss.service.prms.FirstAuditTemitemService;
import bss.service.prms.FirstAuditTemplatService;
@RequestMapping("auditTemplat")
@Controller
public class FirstAuditTemplatController {

	@Autowired
	private FirstAuditTemplatService service;
	@Autowired
	private FirstAuditTemitemService temService;
	
	/**
	 * 
	  * @Title: list
	  * @author ShaoYangYang
	  * @date 2016年10月12日 上午10:17:21  
	  * @Description: TODO 列表
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("list")
	public String list(String name,Integer page,Model model){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", name);
		List<FirstAuditTemplat> list = service.selectAll(map,page==null?1:page);
		model.addAttribute("list", new PageInfo<>(list));
		model.addAttribute("name", name);
		return "bss/prms/templat/list";
	}
	
	/**
	 * 
	  * @Title: toAdd
	  * @author ShaoYangYang
	  * @date 2016年10月11日 下午4:03:10  
	  * @Description: TODO 跳转到新增页面
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("toAdd")
	public String toAdd(){
		return "bss/prms/templat/add_templat";
	}
	/**
	 * 
	  * @Title: add
	  * @author ShaoYangYang
	  * @date 2016年10月11日 下午4:03:35  
	  * @Description: TODO 新增
	  * @param @param templat
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("add")
	public String add(FirstAuditTemplat templat){
		service.save(templat);
		return "redirect:list.html";
	}
	/**
	 * 
	  * @Title: edit
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午1:51:00  
	  * @Description: TODO 修改
	  * @param @param templat
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("edit")
	public String edit(FirstAuditTemplat templat){
		service.update(templat);
		return "redirect:list.html";
	}
	/**
	 * 
	  * @Title: toEdit
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午1:53:26  
	  * @Description: TODO 去往修改页面
	  * @param @param id
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("toEdit")
	public String toEdit(String id , Model model){
		FirstAuditTemplat templat = service.getById(id);
		model.addAttribute("templat", templat);
		return "bss/prms/templat/edit";
	}
	/**
	 * 
	  * @Title: toAddFirstAudit
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午2:08:24  
	  * @Description: TODO 去往添加初审项定义页面
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("toAddFirstAudit")
	public String toAddFirstAudit(String id,Model model){
		FirstAuditTemplat templat = service.getById(id);
		List<FirstAuditTemitem> list = temService.selectByTemplatId(id);
		model.addAttribute("templat", templat);
		model.addAttribute("list", list);
		return "bss/prms/templat/add_first_audit";
	}
	/**
	 * 
	  * @Title: saveFirstAudit
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午2:52:48  
	  * @Description: TODO 保存初审项定义
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("saveFirstAudit")
	public String saveFirstAudit(FirstAuditTemitem temitem ,RedirectAttributes attr){
		temService.save(temitem);
		attr.addAttribute("id", temitem.getTemplatId());
		return "redirect:toAddFirstAudit.html";
	}
	/**
	 * 
	  * @Title: toEditFirstAudit
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午5:32:58  
	  * @Description: TODO 去往修改初审项窗口
	  * @param @param id
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("toEditFirstAudit")
	public String toEditFirstAudit(String id,Model model){
		FirstAuditTemitem temitem = temService.getById(id);
		model.addAttribute("temitem", temitem);
		return  "bss/prms/templat/edit_first_audit";
	}
	/**
	 * 
	  * @Title: editFirstAudit
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午3:01:11  
	  * @Description: TODO 修改初审项定义
	  * @param @param temitem
	  * @param @param attr
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("editFirstAudit")
	public String editFirstAudit(FirstAuditTemitem temitem,RedirectAttributes attr){
		temService.update(temitem);
		attr.addAttribute("id", temitem.getTemplatId());
		return "redirect:toAddFirstAudit.html";
	}
	/**
	 * 
	  * @Title: delete
	  * @author ShaoYangYang
	  * @date 2016年10月12日 上午11:19:13  
	  * @Description: TODO 删除初审项定义
	  * @param       
	  * @return void
	 */
	@RequestMapping("deleteFirstAudit")
	@ResponseBody
	public void deleteFirstAudit(String ids){
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			temService.deleteById(id[i]);
		}
	}
	/**
	 * 
	  * @Title: delete
	  * @author ShaoYangYang
	  * @date 2016年10月12日 上午11:19:13  
	  * @Description: TODO 删除模板
	  * @param       
	  * @return void
	 */
	@RequestMapping("delete")
	public String delete(String ids){
		String[] id = ids.split(",");
		//循环删除选中的数据
		for (String string : id) {
			service.deleteById(string);
		}
		return "redirect:list.html";
	}
}
