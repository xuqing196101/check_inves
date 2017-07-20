package ses.controller.sys.bms;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.Templet;
import ses.model.bms.User;
import ses.service.bms.TempletService;

import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;


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
	public String getAll(@CurrentUser User user,Model model,Integer page){
		//声明标识是否是资源服务中心
        String authType = null;
        if(null != user && "4".equals(user.getTypeName())){
            //判断是否 是资源服务中心 
            authType = "4";
            model.addAttribute("authType", authType);
            List<Templet> templets = templetService.getAll(page==null?1:page);
            model.addAttribute("list",new PageInfo<Templet>(templets));
		}
		return "ses/bms/templet/list";
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
	public String add(@CurrentUser User user,HttpServletRequest request,Model model){
		if(null != user && "4".equals(user.getTypeName())){
			//判断是否 是资源服务中心 
			return "ses/bms/templet/add";
		}
		return "";
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
	public String save(@CurrentUser User user,HttpServletRequest request,@Valid Templet templet,BindingResult result,Model model){
		if(null != user && "4".equals(user.getTypeName())){
			//判断是否 是资源服务中心 
			Boolean flag = true;
			String url = "";
			if(templet.getTemType().equals("-请选择-")){
				flag = false;
				model.addAttribute("ERR_temType", "请选择模板类型");
			}
			if(result.hasErrors()){
				List<FieldError> errors = result.getFieldErrors();
				for(FieldError fieldError:errors){
					model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
				}
				flag = false;
			}
			if(flag == false){
				model.addAttribute("templet", templet);
				url="ses/bms/templet/add";
			}else{	
				templetService.save(templet);
				url="redirect:getAll.do";
			}
			return url;
		}
		return "";
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
	public String edit(@CurrentUser User user,Model model,String id){
		if(null != user && "4".equals(user.getTypeName())){
			//判断是否 是资源服务中心
			model.addAttribute("templet",templetService.get(id));
			return "ses/bms/templet/edit";
		}
		return "";
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
	public String update(HttpServletRequest request,@Valid Templet templet,BindingResult result,Model model){
		Boolean flag = true;
		String url = "";
		if(templet.getTemType().equals("-请选择-")){
			flag = false;
			model.addAttribute("ERR_temType", "请选择模板类型");
		}
		if(result.hasErrors()){
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fieldError:errors){
				model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
			flag = false;
		}
		if(flag == false){
			model.addAttribute("templet", templet);
			url="ses/bms/templet/edit";
		}else{
			templetService.update(templet);
			url="redirect:getAll.do";
		}
		return url;
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
		for (String str : id) {
			templetService.delete(str);
		}
		return "redirect:getAll.do";
	}
	
	/**
	 * 
	 * @Title: view
	 * @author Liyi 
	 * @date 2016-9-6 下午1:36:40  
	 * @Description:跳转查看页面
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/view")
	public String view(Model model,String id){
		model.addAttribute("templet",templetService.get(id));
		return "ses/bms/templet/view";
	}
	
	/**
	 * 
	 * @Title: search
	 * @author Liyi 
	 * @date 2016-10-9 下午5:27:51  
	 * @Description:条件查询
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/search")
	public String search(@CurrentUser User user,Model model,HttpServletRequest request,Templet templet,Integer page){
		//声明标识是否是资源服务中心
        String authType = null;
        if(null != user && "4".equals(user.getTypeName())){
            //判断是否 是资源服务中心 
            authType = "4";
			List<Templet> templets = templetService.search(page==null?1:page,templet);
			model.addAttribute("list",new PageInfo<Templet>(templets));
			model.addAttribute("templet",templet);
			model.addAttribute("authType",authType);
        }
		return "ses/bms/templet/list";
	}
	
	/**
	 * 
	 * @Title: search
	 * @author Liyi 
	 * @date 2016-10-9 下午5:27:51  
	 * @Description:根据类型查询
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/searchByTemtype")
	@ResponseBody
	public String searchByTemtype(Templet templet) throws Exception{
		Templet templets = templetService.searchByTemType(templet.getTemType());
		String content = templets.getContent();
		return content;
	}
}
