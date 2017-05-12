package bss.controller.ob;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.User;

import com.github.pagehelper.PageInfo;

import bss.model.ob.OBRule;
import bss.model.ob.OBSpecialDate;
import bss.service.ob.OBRuleService;
import common.annotation.CurrentUser;
import common.annotation.SystemControllerLog;
import common.annotation.SystemServiceLog;
import common.constant.StaticVariables;
import common.utils.JdcgResult;

/**
 * 竞价规则Controller
 * 
 * @ClassName: OBRuleController
 * @Description:
 * @author Easong
 * @date 2017年3月6日 下午4:43:57
 * 
 */
@Controller
@RequestMapping("/obrule")
public class OBRuleController {

	@Autowired
	private OBRuleService service;

	/**
	 * 
	 * @Title: ruleList
	 * @Description: 竞价规则列表查询
	 * @param @param model
	 * @param @param request
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/ruleList")
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public String ruleList(@CurrentUser User user, Model model,
			HttpServletRequest request,@RequestParam(defaultValue="1") Integer page) {
		String authType=null;
		if(user!= null){
			//判断是否 是资源服务中心 
			if("4".equals(user.getTypeName())){
				authType=user.getTypeName();
		// 竞价规则名称
		String name = (String) request.getParameter("name");
		if (name != null) {
			name = name.trim();
		}
		// 报价时间（分钟）
		String quoteTimeStr = request.getParameter("quoteTime");
		Integer quoteTime = null;
		if (StringUtils.isNotEmpty(quoteTimeStr)) {
			if (quoteTimeStr.matches("^[0-9]+$")) {
				quoteTime = Integer.parseInt(quoteTimeStr);
			}
			quoteTime = Integer.parseInt(quoteTimeStr);
		}
		Integer intervalWorkday = null;
		String intervalWorkdayStr = request.getParameter("intervalWorkday");
		if (StringUtils.isNotEmpty(intervalWorkdayStr)) {
			if (intervalWorkdayStr.matches("^[0-9]+$")) {
				intervalWorkday = Integer.parseInt(intervalWorkdayStr);
			}
		}
		// 间隔工作日（天）
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("quoteTime", quoteTime);
		map.put("intervalWorkday", intervalWorkday);
		/*if (user != null) {
			map.put("userId", user.getId());
		}*/
		map.put("page", page);
		List<OBRule> list = service.selectAllOBRules(map);
		PageInfo<OBRule> info = new PageInfo<OBRule>(list);

		model.addAttribute("info", info);
		// 查询条件回显
		model.addAttribute("name", name);
		model.addAttribute("quoteTime", quoteTimeStr);
		model.addAttribute("intervalWorkday", intervalWorkdayStr);
			}
		}
		model.addAttribute("authType",authType);
		return "bss/ob/biddingRules/list";
	}

	/**
	 * 
	 * @Title: addRuleUI
	 * @Description: 添加规则页面的回显
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/addRuleUI")
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public String addRuleUI(@CurrentUser User user,Model model) {
		if(user!= null){
			//判断是否 是资源服务中心 
			if("4".equals(user.getTypeName())){
				model.addAttribute("authType", user.getTypeName());
			}
		}
		return "bss/ob/biddingRules/create";
	}

	/**
	 * @throws Exception 
	 * 
	 * @Title: addRule
	 * @Description: 添加规则
	 * @param @param obRule
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	@RequestMapping(value = "/addRule", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public JdcgResult addRule(OBRule obRule, @CurrentUser User user,Model model) throws Exception {
		if(user!= null){
			//判断是否 是资源服务中心 
			if("4".equals(user.getTypeName())){
				model.addAttribute("authType", user.getTypeName());
				return service.addRule(obRule, user);
			}
		}
		return JdcgResult.build(500, StaticVariables.OB_PROJECT_SHOW);
	}

	/**
	 * @throws Exception 
	 * 
	 * @Title: delete
	 * @Description: 刪除规则
	 * @param @param request
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public JdcgResult delete(@CurrentUser User user,HttpServletRequest request,Model model) throws Exception {
		if(user!= null){
			//判断是否 是资源服务中心 
			if("4".equals(user.getTypeName())){
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		model.addAttribute("authType", user.getTypeName());
		return service.delete(ids);
			}
		}
		return JdcgResult.build(500, StaticVariables.OB_PROJECT_SHOW);
	}

	/**
	 * @throws Exception 
	 * 
	 * @Title: setDefaultRule
	 * @Description: 默认规则设置
	 * @param @param request
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	@RequestMapping(value = "/setDefaultRule", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public JdcgResult setDefaultRule(@CurrentUser User user,HttpServletRequest request,Model model) throws Exception {
		if(user!= null){
			//判断是否 是资源服务中心 
			if("4".equals(user.getTypeName())){
		String id = request.getParameter("id");
		model.addAttribute("authType", user.getTypeName());
		return service.updateDefaultRule(id);
			}
		}
		return JdcgResult.build(500, StaticVariables.OB_PROJECT_SHOW);
	}

	/**
	 * @throws ParseException
	 * @Title: holidayList
	 * @Description: 节假日管理列表页面
	 * @param @param model
	 * @param @param request
	 * @param @param page
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/holidayList")
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public String holidayList(@CurrentUser User user, Model model,
			HttpServletRequest request,@RequestParam(defaultValue="1") Integer page) throws ParseException {
		String authType=null;
		if(user!= null){
			//判断是否 是资源服务中心 
			if("4".equals(user.getTypeName())){
				authType=user.getTypeName();
		// 设置日期
		String specialDateStr = request.getParameter("specialDate");
		// 将字符串类型转换成日期类型
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date specialDate = null;
		if (StringUtils.isNotEmpty(specialDateStr)) {
			specialDate = dateFormat.parse(specialDateStr);
		}
		// 类型
		String dateType = request.getParameter("dateType");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("specialDate", specialDate);
		map.put("dateType", dateType);
		map.put("page", page);
		List<OBSpecialDate> list = service.selectAllOBSpecialDate(map);
		PageInfo<OBSpecialDate> info = new PageInfo<OBSpecialDate>(list);

		// 将查询的所有信息存放到model域中
		model.addAttribute("info", info);
		// 查询数据回显
		model.addAttribute("specialDateStr", specialDateStr);
		model.addAttribute("dateType", dateType);
			}
		}
		model.addAttribute("authType", authType);
		return "bss/ob/biddingRules/holiday";
	}

	/**
	 * 
	 * @Title: createSpecialdateUI
	 * @Description: 创建特殊日期UI回显
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("createSpecialdateUI")
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public String createSpecialdateUI(@CurrentUser User user,Model model) {
		String authType=null;
		if(user!= null){
			//判断是否 是资源服务中心 
			if("4".equals(user.getTypeName())){
				authType=user.getTypeName();
			}
		}
		model.addAttribute("authType", authType);
		return "bss/ob/biddingRules/createSpecialdate";
	}

	/**
	 * @throws Exception 
	 * 
	 * @Title: addSpecialdate
	 * @Description: 创建特殊日期
	 * @param @param obSpecialDate
	 * @param @param request
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	@RequestMapping(value = "addSpecialdate", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public JdcgResult addSpecialdate(@CurrentUser User user,
			OBSpecialDate obSpecialDate, HttpServletRequest request) throws Exception {
		if(user!= null){
			//判断是否 是资源服务中心 
			if("4".equals(user.getTypeName())){
		return service.addSpecialdate(obSpecialDate, request, user);
			}
		}
		return JdcgResult.build(500, StaticVariables.OB_PROJECT_SHOW);
	}

	/**
	 * @throws Exception 
	 * 
	 * @Title: deleteSpecialDate
	 * @Description: 删除特殊假期
	 * @param @param request
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	@RequestMapping(value = "/deleteSpecialDate", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public JdcgResult deleteSpecialDate(@CurrentUser User user,HttpServletRequest request) throws Exception {
		if(user!= null){
			//判断是否 是资源服务中心 
			if("4".equals(user.getTypeName())){
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		return service.deleteSpecialDate(ids);
			}
		}
		return JdcgResult.build(500, StaticVariables.OB_PROJECT_SHOW);
	}

	/**
	 * 
	* @Title: editobRule 
	* @Description: 修改竞价规则数据回显
	* @author Easong
	* @param @param model
	* @param @param id
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/editobRule")
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public String editobRule(@CurrentUser User user,Model model, String id) {
		String authType=null;
		if(user!= null){
			//判断是否 是资源服务中心 
			if("4".equals(user.getTypeName())){
				authType=user.getTypeName();
			if (StringUtils.isEmpty(id)) {
			}else{
			OBRule obRule = service.editObRule(id);
			model.addAttribute("obRule", obRule);
			}
		//return "bss/ob/biddingRules/editRule";
			}
		}
			model.addAttribute("authType", authType);
			return "bss/ob/biddingRules/editRule";
	}
	
	/**
	 * @throws Exception 
	 * 
	* @Title: updateobRule 
	* @Description: 修改规则
	* @author Easong
	* @param @param obRule
	* @param @param request
	* @param @return    设定文件 
	* @return JdcgResult    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/updateobRule", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public JdcgResult updateobRule(@CurrentUser User user,OBRule obRule) throws Exception {
		if(user!= null){
			//判断是否 是资源服务中心 
			if("4".equals(user.getTypeName())){
		     return service.updateobRule(obRule);
			}
		}
		return JdcgResult.build(500,StaticVariables.OB_PROJECT_SHOW);
	}
	
	/**
	 * 
	* @Title: editSpecialdate 
	* @Description: 修改特殊节假日数据回显
	* @author Easong
	* @param @param model
	* @param @param id
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/editSpecialdate")
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public String editSpecialdate(@CurrentUser User user,Model model, String id){
		String authType=null;
		if(user!= null){
			//判断是否 是资源服务中心 
			if("4".equals(user.getTypeName())){
				authType=user.getTypeName();
				if (StringUtils.isEmpty(id)) {
				}else{
				 OBSpecialDate specialDate = service.editSpecialdate(id);
				 model.addAttribute("specialDate", specialDate);
				}
			}
		}
		model.addAttribute("authType", authType);
		return "bss/ob/biddingRules/editSpecialdate";
	}
	
	/**
	 * @throws Exception 
	 * 
	* @Title: updateSpecialdate 
	* @Description: 修改特殊日期
	* @author Easong
	* @param @param obSpecialDate
	* @param @return    设定文件 
	* @return JdcgResult    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/updateSpecialdate", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public JdcgResult updateSpecialdate(@CurrentUser User user,OBSpecialDate obSpecialDate) throws Exception{
		if(user!= null){
			//判断是否 是资源服务中心 
			if("4".equals(user.getTypeName())){
		    return service.updateobSpecialDate(obSpecialDate);
			}
		}
		return JdcgResult.build(500, StaticVariables.OB_PROJECT_SHOW);
	}
	
	/**
	 * 
	* @Title: checkNameUnique 
	* @Description: 校验竞价规则名称是否唯一
	* @author Easong
	* @param @param name
	* @param @return    设定文件 
	* @return JdcgResult    返回类型 
	* @throws
	 */
	@RequestMapping("/checkNameUnique")
	@ResponseBody
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public JdcgResult checkNameUnique(@CurrentUser User user,String name){
		if(user!= null){
			//判断是否 是资源服务中心 
			if("4".equals(user.getTypeName())){
		return service.checkNameUnique(name);
			}
		}
		return JdcgResult.build(500, StaticVariables.OB_PROJECT_SHOW);
	}
}
