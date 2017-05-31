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

	// 注入竞价规则Service
	@Autowired
	private OBRuleService service;

	/**
	 * 
	 * Description:竞价规则列表查询
	 * 
	 * @author Easong
	 * @version 2017年5月22日
	 * @param user
	 * @param model
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/ruleList")
	@SystemControllerLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	public String ruleList(@CurrentUser User user, Model model, HttpServletRequest request, @RequestParam(defaultValue = "1") Integer page) {
		String authType = null;
		if (user != null && "4".equals(user.getTypeName())) {
			// 判断是否 是资源服务中心
				authType = user.getTypeName();
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
				/*
				 * if (user != null) { map.put("userId", user.getId()); }
				 */
				map.put("page", page);
				List<OBRule> list = service.selectAllOBRules(map);
				PageInfo<OBRule> info = new PageInfo<OBRule>(list);

				model.addAttribute("info", info);
				// 查询条件回显
				model.addAttribute("name", name);
				model.addAttribute("quoteTime", quoteTimeStr);
				model.addAttribute("intervalWorkday", intervalWorkdayStr);
		}
		model.addAttribute("authType", authType);
		return "bss/ob/biddingRules/list";
	}

	/**
	 * 
	 * Description:添加规则页面的回显
	 * 
	 * @author Easong
	 * @version 2017年5月22日
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("/addRuleUI")
	@SystemControllerLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	public String addRuleUI(@CurrentUser User user, Model model) {
		if (user != null && "4".equals(user.getTypeName())) {
			// 判断是否 是资源服务中心
				model.addAttribute("authType", user.getTypeName());
		}
		return "bss/ob/biddingRules/create";
	}

	/**
	 * 
	 * Description:添加规则
	 * 
	 * @author Easong
	 * @version 2017年5月22日
	 * @param obRule
	 * @param user
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addRule", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	public JdcgResult addRule(OBRule obRule, @CurrentUser User user, Model model) throws Exception {
		if (user != null && "4".equals(user.getTypeName())) {
			// 判断是否 是资源服务中心
				model.addAttribute("authType", user.getTypeName());
				return service.addRule(obRule, user);
		}
		return JdcgResult.build(500, StaticVariables.OB_PROJECT_SHOW);
	}

	/**
	 * 
	 * Description:刪除规则
	 * 
	 * @author Easong
	 * @version 2017年5月22日
	 * @param user
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	public JdcgResult delete(@CurrentUser User user, HttpServletRequest request, Model model) throws Exception {
		if (user != null && "4".equals(user.getTypeName())) {
			// 判断是否 是资源服务中心
				String id = request.getParameter("id");
				String[] ids = id.split(",");
				model.addAttribute("authType", user.getTypeName());
				return service.delete(ids);
		}
		return JdcgResult.build(500, StaticVariables.OB_PROJECT_SHOW);
	}

	/**
	 * 
	 * Description:默认规则设置
	 * 
	 * @author Easong
	 * @version 2017年5月22日
	 * @param user
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setDefaultRule", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	public JdcgResult setDefaultRule(@CurrentUser User user, HttpServletRequest request, Model model) throws Exception {
		if (user != null && "4".equals(user.getTypeName())) {
			// 判断是否 是资源服务中心
				String id = request.getParameter("id");
				model.addAttribute("authType", user.getTypeName());
				return service.updateDefaultRule(id);
		}
		return JdcgResult.build(500, StaticVariables.OB_PROJECT_SHOW);
	}

	/**
	 * 
	 * Description:节假日管理列表页面
	 * 
	 * @author Easong
	 * @version 2017年5月22日
	 * @param user
	 * @param model
	 * @param request
	 * @param page
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/holidayList")
	@SystemControllerLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	public String holidayList(@CurrentUser User user, Model model,
			HttpServletRequest request,
			@RequestParam(defaultValue = "1") Integer page)
			throws ParseException {
		String authType = null;
		if (user != null && "4".equals(user.getTypeName())) {
			// 判断是否 是资源服务中心
				authType = user.getTypeName();
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
				Map<String, Object> map = new HashMap<>();
				map.put("specialDate", specialDate);
				map.put("dateType", dateType);
				map.put("page", page);
				List<OBSpecialDate> list = service.selectAllOBSpecialDate(map);
				PageInfo<OBSpecialDate> info = new PageInfo<>(list);

				// 将查询的所有信息存放到model域中
				model.addAttribute("info", info);
				// 查询数据回显
				model.addAttribute("specialDateStr", specialDateStr);
				model.addAttribute("dateType", dateType);
		}
		model.addAttribute("authType", authType);
		return "bss/ob/biddingRules/holiday";
	}

	/**
	 * 
	 * Description:创建特殊日期UI回显
	 * 
	 * @author Easong
	 * @version 2017年5月22日
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("createSpecialdateUI")
	@SystemControllerLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	public String createSpecialdateUI(@CurrentUser User user, Model model) {
		String authType = null;
		if (user != null&& "4".equals(user.getTypeName())) {
			// 判断是否 是资源服务中心
				authType = user.getTypeName();
		}
		model.addAttribute("authType", authType);
		return "bss/ob/biddingRules/createSpecialdate";
	}

	/**
	 * 
	 * Description:创建特殊日期
	 * 
	 * @author Easong
	 * @version 2017年5月22日
	 * @param user
	 * @param obSpecialDate
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "addSpecialdate", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	public JdcgResult addSpecialdate(@CurrentUser User user,OBSpecialDate obSpecialDate, HttpServletRequest request) throws Exception {
		if (user != null && "4".equals(user.getTypeName())) {
			// 判断是否 是资源服务中心
				return service.addSpecialdate(obSpecialDate, request, user);
		}
		return JdcgResult.build(500, StaticVariables.OB_PROJECT_SHOW);
	}

	/**
	 * 
	 * Description:删除特殊假期
	 * 
	 * @author Easong
	 * @version 2017年5月22日
	 * @param user
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSpecialDate", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	public JdcgResult deleteSpecialDate(@CurrentUser User user, HttpServletRequest request) throws Exception {
		if (user != null && "4".equals(user.getTypeName())) {
			// 判断是否 是资源服务中心
				String id = request.getParameter("id");
				String[] ids = id.split(",");
				return service.deleteSpecialDate(ids);
		}
		return JdcgResult.build(500, StaticVariables.OB_PROJECT_SHOW);
	}

	/**
	 * 
	 * Description:修改竞价规则数据回显
	 * 
	 * @author Easong
	 * @version 2017年5月22日
	 * @param user
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/editobRule")
	@SystemControllerLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	public String editobRule(@CurrentUser User user, Model model, String id) {
		String authType = null;
		if (user != null && "4".equals(user.getTypeName())) {
			// 判断是否 是资源服务中心
				authType = user.getTypeName();
				if (StringUtils.isEmpty(id)) {
				} else {
					OBRule obRule = service.editObRule(id);
					model.addAttribute("obRule", obRule);
				}
		}
		model.addAttribute("authType", authType);
		return "bss/ob/biddingRules/editRule";
	}

	/**
	 * 
	 * Description:修改规则
	 * 
	 * @author Easong
	 * @version 2017年5月22日
	 * @param user
	 * @param obRule
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateobRule", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	public JdcgResult updateobRule(@CurrentUser User user, OBRule obRule)
			throws Exception {
		if (user != null && "4".equals(user.getTypeName())) {
			// 判断是否 是资源服务中心
				return service.updateobRule(obRule);
		}
		return JdcgResult.build(500, StaticVariables.OB_PROJECT_SHOW);
	}

	/**
	 * 
	 * Description:修改特殊节假日数据回显
	 * 
	 * @author Easong
	 * @version 2017年5月22日
	 * @param user
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/editSpecialdate")
	@SystemControllerLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	public String editSpecialdate(@CurrentUser User user, Model model, String id) {
		String authType = null;
		if (user != null && "4".equals(user.getTypeName())) {
			// 判断是否 是资源服务中心
				authType = user.getTypeName();
				if (StringUtils.isEmpty(id)) {
				} else {
					OBSpecialDate specialDate = service.editSpecialdate(id);
					model.addAttribute("specialDate", specialDate);
			}
		}
		model.addAttribute("authType", authType);
		return "bss/ob/biddingRules/editSpecialdate";
	}

	/**
	 * 
	 * Description:修改特殊日期
	 * 
	 * @author Easong
	 * @version 2017年5月22日
	 * @param user
	 * @param obSpecialDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSpecialdate", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	public JdcgResult updateSpecialdate(@CurrentUser User user,
			OBSpecialDate obSpecialDate) throws Exception {
		if (user != null && "4".equals(user.getTypeName())) {
			// 判断是否 是资源服务中心
				return service.updateobSpecialDate(obSpecialDate);
		}
		return JdcgResult.build(500, StaticVariables.OB_PROJECT_SHOW);
	}

	/**
	 * 
	 * Description:校验竞价规则名称是否唯一
	 * 
	 * @author Easong
	 * @version 2017年5月22日
	 * @param user
	 * @param name
	 * @return
	 */
	@RequestMapping("/checkNameUnique")
	@ResponseBody
	@SystemControllerLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description = StaticVariables.OB_PROJECT_NAME, operType = StaticVariables.OB_PROJECT_NAME_SIGN)
	public JdcgResult checkNameUnique(@CurrentUser User user, String name) {
		if (user != null && "4".equals(user.getTypeName())) { 
			// 判断是否 是资源服务中心
				return service.checkNameUnique(name);
		}
		return JdcgResult.build(500, StaticVariables.OB_PROJECT_SHOW);
	}
}
