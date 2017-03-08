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
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import bss.echarts.Data;
import bss.model.ob.OBRule;
import bss.model.ob.OBSpecialDate;
import bss.service.ob.OBRuleService;
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
	public String ruleList(Model model, HttpServletRequest request, Integer page) {
		if (page == null) {
			page = 1;
		}
		// 竞价规则名称
		String name = (String) request.getParameter("name");
		if (name != null) {
			name = name.trim();
		}
		// 报价时间（分钟）
		String quoteTimeStr = request.getParameter("quoteTime");
		Integer quoteTime = null;
		if (StringUtils.isNotEmpty(quoteTimeStr)) {
			quoteTime = Integer.parseInt(quoteTimeStr);
		}
		Integer intervalWorkday = null;
		String intervalWorkdayStr = request.getParameter("intervalWorkday");
		if (StringUtils.isNotEmpty(intervalWorkdayStr)) {
			intervalWorkday = Integer.parseInt(intervalWorkdayStr);
		}
		// 间隔工作日（天）
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("quoteTime", quoteTime);
		map.put("intervalWorkday", intervalWorkday);
		map.put("page", page);
		List<OBRule> list = service.selectAllOBRules(map);
		PageInfo<OBRule> info = new PageInfo<OBRule>(list);

		model.addAttribute("info", info);
		// 查询条件回显
		model.addAttribute("name", name);
		model.addAttribute("quoteTime", quoteTime);
		model.addAttribute("intervalWorkday", intervalWorkday);
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
	public String addRuleUI() {
		return "bss/ob/biddingRules/create";
	}

	/**
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
	public JdcgResult addRule(OBRule obRule, HttpServletRequest request) {
		return service.addRule(obRule, request);
	}

	/**
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
	public JdcgResult delete(HttpServletRequest request) {
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		return service.delete(ids);
	}

	/**
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
	public JdcgResult setDefaultRule(HttpServletRequest request) {
		String id = request.getParameter("id");
		return service.setDefaultRule(id);
	}

	/**
	 * @throws ParseException
	 * 
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
	public String holidayList(Model model, HttpServletRequest request,
			Integer page) throws ParseException {
		if (page == null) {
			page = 1;
		}
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
	public String createSpecialdateUI() {
		return "bss/ob/biddingRules/createSpecialdate";
	}

	/**
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
	public JdcgResult addSpecialdate(OBSpecialDate obSpecialDate,
			HttpServletRequest request) {
		return service.addSpecialdate(obSpecialDate, request);
	}

	/**
	 * 
	* @Title: deleteSpecialDate 
	* @Description: 删除特殊假期
	* @param @param request
	* @param @return    设定文件 
	* @return JdcgResult    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/deleteSpecialDate", method = RequestMethod.POST)
	@ResponseBody
	public JdcgResult deleteSpecialDate(HttpServletRequest request){
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		return service.deleteSpecialDate(ids);
	}
}
