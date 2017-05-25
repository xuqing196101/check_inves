package iss.controller.hl;

import java.util.Date;
import java.util.List;

import iss.model.hl.ServiceHotline;
import iss.service.hl.ServiceHotlineService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import ses.model.bms.User;
import common.annotation.CurrentUser;
import common.annotation.SystemControllerLog;
import common.annotation.SystemServiceLog;
import common.constant.StaticVariables;


/**
 * 
 * Description： 服务热线Controller
 * 
 * @author  zhang shubin
 * @version  
 * @since JDK1.7
 * @date 2017年5月25日 上午9:53:20 
 *
 */
@Controller
@RequestMapping("/serviceHotline")
public class ServiceHotlineController {
	
	@Autowired
	private ServiceHotlineService serviceHotlineService;

	/**
	 * 
	 * Description: 查询所有信息
	 * 
	 * @author  zhang shubin
	 * @version  2017年5月25日 
	 * @param  @param user
	 * @param  @param page
	 * @param  @param model
	 * @param  @param serviceHotline
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/list")
	@SystemControllerLog(description=StaticVariables.HL_SERVICEHOTLINE_NAME,operType=StaticVariables.HL_SERVICEHOTLINE_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.HL_SERVICEHOTLINE_NAME,operType=StaticVariables.HL_SERVICEHOTLINE_NAME_SIGN)
	public String list(@CurrentUser User user,@RequestParam(defaultValue="1")Integer page, Model model,ServiceHotline serviceHotline){
		//声明标识是否是资源服务中心
		String authType = null;
		if(user != null){
			//判断是否 是资源服务中心 
			if("4".equals(user.getTypeName())){
				authType = "4";
			}
		}
		List<ServiceHotline> list = serviceHotlineService.selectAll(serviceHotline,page);
		PageInfo<ServiceHotline> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("serviceHotline", serviceHotline);
		model.addAttribute("authType", authType);
		return "iss/hl/list";
	}
	
	/**
	 * 
	 * Description: 页面跳转
	 * 
	 * @author  zhang shubin
	 * @version  2017年5月25日 
	 * @param  @param request
	 * @param  @param model
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/addoredit")
	@SystemControllerLog(description=StaticVariables.HL_SERVICEHOTLINE_NAME,operType=StaticVariables.HL_SERVICEHOTLINE_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.HL_SERVICEHOTLINE_NAME,operType=StaticVariables.HL_SERVICEHOTLINE_NAME_SIGN)
	public String addoredit(HttpServletRequest request,Model model){
		String type = request.getParameter("type") == null ? "" : request.getParameter("type");
		if(type.equals("1")){
			return "iss/hl/add";
		}else if(type.equals("2")){
			String id = request.getParameter("id") == null ? "" : request.getParameter("id");
			ServiceHotline serviceHotline = serviceHotlineService.selectByPrimaryKey(id);
			model.addAttribute("serviceHotline", serviceHotline);
			return "iss/hl/edit";
		}else{
			return "redirect:list.do";
		}
	}
	
	/**
	 * 
	 * Description: 添加
	 * 
	 * @author  zhang shubin
	 * @version  2017年5月25日 
	 * @param  @param model
	 * @param  @param user
	 * @param  @param serviceHotline
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/add")
	@SystemControllerLog(description=StaticVariables.HL_SERVICEHOTLINE_NAME,operType=StaticVariables.HL_SERVICEHOTLINE_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.HL_SERVICEHOTLINE_NAME,operType=StaticVariables.HL_SERVICEHOTLINE_NAME_SIGN)
	public String add(Model model,@CurrentUser User user,ServiceHotline serviceHotline){
		if(serviceHotline != null){
			boolean flag = true;
			if(serviceHotline.getServicecontent() == null || serviceHotline.getServicecontent().equals("")){
				flag = false;
				model.addAttribute("error_content","服务内容不能为空");
			}
			if(serviceHotline.getContactphonenumber() == null || serviceHotline.getContactphonenumber().equals("")){
				flag = false;
				model.addAttribute("error_phone","联系电话不能为空");
			}
			if(flag == false){
				model.addAttribute("serviceHotline", serviceHotline);
				return "iss/hl/add";
			}
			serviceHotline.setIsDeleted(0);
			if(user != null){
				serviceHotline.setCreaterId(user.getId());
			}
			serviceHotline.setCreatedAt(new Date());
			serviceHotlineService.insertSelective(serviceHotline);
		}
		return "redirect:list.do";
	}
	
	/**
	 * 
	 * Description: 修改
	 * 
	 * @author  zhang shubin
	 * @version  2017年5月25日 
	 * @param  @param model
	 * @param  @param serviceHotline
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/edit")
	@SystemControllerLog(description=StaticVariables.HL_SERVICEHOTLINE_NAME,operType=StaticVariables.HL_SERVICEHOTLINE_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.HL_SERVICEHOTLINE_NAME,operType=StaticVariables.HL_SERVICEHOTLINE_NAME_SIGN)
	public String edit(Model model,ServiceHotline serviceHotline){
		if(serviceHotline != null){
			boolean flag = true;
			if(serviceHotline.getServicecontent() == null || serviceHotline.getServicecontent().equals("")){
				flag = false;
				model.addAttribute("error_content","服务内容不能为空");
			}
			if(serviceHotline.getContactphonenumber() == null || serviceHotline.getContactphonenumber().equals("")){
				flag = false;
				model.addAttribute("error_phone","联系电话不能为空");
			}
			if(flag == false){
				model.addAttribute("serviceHotline", serviceHotline);
				return "iss/hl/edit";
			}
			serviceHotline.setUpdatedAt(new Date());
			serviceHotlineService.updateByPrimaryKeySelective(serviceHotline);
		}
		return "redirect:list.do";
	}
	
	/**
	 * 
	 * Description: 删除
	 * 
	 * @author  zhang shubin
	 * @version  2017年5月25日 
	 * @param  @param request 
	 * @return void 
	 * @exception
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@SystemControllerLog(description=StaticVariables.HL_SERVICEHOTLINE_NAME,operType=StaticVariables.HL_SERVICEHOTLINE_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.HL_SERVICEHOTLINE_NAME,operType=StaticVariables.HL_SERVICEHOTLINE_NAME_SIGN)
	public void delete(HttpServletRequest request){
		String ids = request.getParameter("ids");
		String id = ids.trim();
		if (id.length() != 0) {
			String[] uniqueIds = id.split(",");
			for (String str : uniqueIds) {
				serviceHotlineService.deleteByPrimaryKey(str);
			}
		}
	}
	
	/**
	 * 
	 * Description: 首页服务热线
	 * 
	 * @author  zhang shubin
	 * @version  2017年5月25日 
	 * @param  @param page
	 * @param  @param model
	 * @param  @param serviceHotline
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/index_list")
	@SystemControllerLog(description=StaticVariables.HL_SERVICEHOTLINE_NAME,operType=StaticVariables.HL_SERVICEHOTLINE_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.HL_SERVICEHOTLINE_NAME,operType=StaticVariables.HL_SERVICEHOTLINE_NAME_SIGN)
	public String index_list(@RequestParam(defaultValue="1")Integer page, Model model,String servicecontent){
		ServiceHotline serviceHotline = new ServiceHotline();
		if(servicecontent != null){
			serviceHotline.setServicecontent(servicecontent);
		}
		List<ServiceHotline> list = serviceHotlineService.selectAll(serviceHotline,page);
		PageInfo<ServiceHotline> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("serviceHotline", serviceHotline);
		return "iss/hl/index_list";
	}
	
}
