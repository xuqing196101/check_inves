package yggc.controller.sys.ems;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import yggc.model.ems.Expert;
import yggc.service.ems.ExpertService;
import yggc.util.Encrypt;

@Controller
@RequestMapping("/expert")
public class ExpertController {

	@Autowired
	private ExpertService service;
	/**
	 * 
	  * @Title: toExpert
	  * @author lkzx 
	  * @date 2016年8月31日 下午7:04:16  
	  * @Description: TODO 跳转到评审专家注册页面
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/toExpert")
	public String toExpert(){
		
		return "ems/expert/expertRegister";
	}
	
	
	/**
	 * 
	  * @Title: add
	  * @author lkzx 
	  * @date 2016年8月31日 下午6:36:19  
	  * @Description: TODO 注册评审专家用户
	  * @param @param expert
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/register")
	public String register(Expert expert,HttpSession session, Model model,HttpServletRequest request,@RequestParam String token2){
		Object tokenValue = session.getAttribute("tokenSession");
		if (tokenValue != null && tokenValue.equals(token2)) {
			// 正常提交
			session.removeAttribute("tokenSession");
		expert.setId(UUID.randomUUID().toString());
		//密码加密
		String md5AndSha = Encrypt.md5AndSha(expert.getPassword());
		expert.setPassword(md5AndSha);
		request.setAttribute("uuid", expert.getId());
		service.insertSelective(expert);
		return "ems/expert/basicInfo";
		} else{
			return "ems/expert/basicInfo";
		}
	}
	/**
	 * 
	  * @Title: toBasicInfo
	  * @author lkzx 
	  * @date 2016年9月1日 上午11:12:55  
	  * @Description: TODO 跳转到填写 、修改个人信息
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/toBasicInfo")
	public String toBasicInfo(HttpServletRequest request,HttpServletResponse response,  Model model){
		
		return "ems/expert/basicInfo";
	}
	/**
	 * 
	  * @Title: edit
	  * @author lkzx 
	  * @date 2016年9月1日 上午11:14:38  
	  * @Description: TODO 修改、填写个人信息
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/edit")
	public String edit(Expert expert ,Model model,HttpSession session,@RequestParam String token2 ){
		Object tokenValue = session.getAttribute("tokenSession");
		if (tokenValue != null && tokenValue.equals(token2)) {
			// 正常提交
			session.removeAttribute("tokenSession");
		service.updateByPrimaryKeySelective(expert);
		return "index";
		}else{
		return "index";
		}
	}
  /**
   * 
    * @Title: findAllLoginName
    * @author lkzx 
    * @date 2016年9月1日 下午5:35:45  
    * @Description: TODO 用户名ajax校验
    * @param @param model
    * @param @return      
    * @return List<String>
   */
	@RequestMapping("/findAllLoginName")
	@ResponseBody
	public List<Expert> findAllLoginName(@RequestParam("loginName")String loginName, Model model){
		List<Expert> selectLoginNameList = service.selectLoginNameList(loginName);
		System.out.println(selectLoginNameList);
		return selectLoginNameList;
	}
}
