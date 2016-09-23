package bss.controller.pms;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpertService;
import bss.model.pms.AuditPerson;
import bss.model.pms.UpdateFiled;
import bss.service.pms.AuditPersonService;
import bss.service.pms.UpdateFiledService;

import com.github.pagehelper.PageInfo;

/***
 * 
 * @Title: AuditSetController
 * @Description:  审核设置控制类
 * @author Li Xiaoxiao
 * @date  2016年9月21日,下午5:24:21
 *
 */
@Controller
@RequestMapping("/set")
public class AuditSetController {

	
	@Autowired
	private UpdateFiledService updateFiledService;
	
	@Autowired
	private ExpertService expertService;
	
	@Autowired
	private UserServiceI userServiceI;
	
	@Autowired
	private AuditPersonService auditPersonService;
	
	/**
	 * 
	* @Title: set
	* @Description: 采购计划审核设置页面 
	* author: Li Xiaoxiao 
	* @param @param model
	* @param @param page
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/list")
	public String set(Model model,Integer page,String id){
		List<UpdateFiled> list = updateFiledService.getAll();
		List<UpdateFiled> listy=new LinkedList<UpdateFiled>();
		List<UpdateFiled> listn=new LinkedList<UpdateFiled>();
		for(UpdateFiled u:list){
			if(u.getIsUpdate().equals(1)){
				listy.add(u);
			}else{
				listn.add(u);
			}
		}
		
		List<AuditPerson> listAudit = auditPersonService.query(new AuditPerson(), page==null?1:page);
		PageInfo<AuditPerson> info = new PageInfo<>(listAudit);
		model.addAttribute("info", info);
		
		model.addAttribute("listy", listy);
		model.addAttribute("listn", listn);
		model.addAttribute("id", id);
		return "bss/pms/collect/auditset";
	}
	/**
	 * 
	* @Title: save
	* @Description: 设置字段是否允许修改
	* author: Li Xiaoxiao 
	* @param @param updateFiled
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/update")
	public String save(String val1, String val2){
		String[] field1 = val1.split(",");
		List<String> list=new LinkedList<String>();
		
		for(int i=0;i<field1.length;i++){
			list.add(field1[i]);
		}
		updateFiledService.update(1, list);
		List<String> list2=new LinkedList<String>();
		String[] field2 = val2.split(",");
		for(int i=0;i<field2.length;i++){
			list2.add(field2[i]);
		}
		updateFiledService.update(2, list2);
		return "redirect:list.html";
	}
	
	/**
	 * 
	* @Title: getExpert
	* @Description: 查询所有专家
	* author: Li Xiaoxiao 
	* @param @param page
	* @param @param expert
	* @param @param model
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/expert")
	public String getExpert(Integer page,Expert expert,Model model){
		List<Expert> list = expertService.selectAllExpert(page==null?1:page, expert);
		PageInfo<Expert> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		return "bss/pms/collect/expertlist";
	}
	/**
	 * 
	* @Title: getUser
	* @Description: 查询所有用户
	* author: Li Xiaoxiao 
	* @param @param page
	* @param @param suer
	* @param @param model
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/user")
	public String getUser(Integer page,User user,Model model){
		List<User> list = userServiceI.selectUser(user, page==null?1:page);
		PageInfo<User> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		return "bss/pms/collect/userlist";
	}
	/**
	 * 
	* @Title: add
	* @Description: 添加审核人员
	* author: Li Xiaoxiao 
	* @param @param auditPerson
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/add")
	@ResponseBody
	public String add(AuditPerson auditPerson,String id){
		 if(auditPerson.getType()==1){
			 Expert expert = expertService.selectByPrimaryKey(id);
			 auditPerson.setName(expert.getRelName());
			 auditPerson.setMobile(expert.getMobile());
			 auditPerson.setIdNumber(expert.getIdNumber());
			 auditPersonService.add(auditPerson);
		 }else if(auditPerson.getType()==2){
			 User user = userServiceI.getUserById(id);
			 auditPerson.setName(user.getRelName());
			 auditPerson.setMobile(user.getMobile());
			 auditPersonService.add(auditPerson);
		 }else{
			 auditPersonService.add(auditPerson);
		 }
		
		return null;
	}
	
}
