package bss.controller.ob;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.service.oms.OrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.Constant;
import bss.model.ob.OBProject;
import bss.model.ppms.Project;
import bss.service.ob.OBProjectServer;

/**
 * 竞价信息管理控制
 * 
 * @author YangHongliang
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/ob_project")
public class OBProjectController {
	@Autowired
	private OBProjectServer OBProjectServer;

	@Autowired
	private OrgnizationServiceI orgnizationService;

	/***
	 * 获取竞价信息跳转 list页
	 * 
	 * @author YangHongLiang
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model, HttpServletRequest request, Integer page) {
		OBProject op = new OBProject();
		op.setName("");
		op.setStartTime(new Date());
		List<OBProject> list = OBProjectServer.list(op);
		if (page == null) {
			page = 1;
		}
		PageHelper.startPage(page,
				Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
		model.addAttribute("listInfo", new PageInfo<OBProject>(list));

		return "bss/ob/biddingInformation/list";
	}

	/**
	 * 发布竞价信息跳转 add页
	 * 
	 * @author YangHongLiang
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/add")
	public String addBidding(@CurrentUser User user, Model model,
			HttpServletRequest request) {

		model.addAttribute("userId", user.getId());
		model.addAttribute("sysKey", Constant.PROJECT_SYS_KEY);
		model.addAttribute("typeId", DictionaryDataUtil.getId("BID_FILE_AUDIT"));
		return "bss/ob/biddingInformation/publish";
	}

	/**
	 * 获取可用的采购机构 信息 并返回页面
	 * 
	 * @author YangHongLiang
	 * @throws IOException
	 */
	@RequestMapping("mechanism")
	public void getMechanism(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			String json = orgnizationService.getMechanism();
			response.getWriter().print(json.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close();
		}
	}

	/** ------------竞价看板------------- **/

	/**
	 * 
	 * @Title: biddingInfoList
	 * @Description: 竞价信息列表显示
	 * @author Easong
	 * @param @param model
	 * @param @param request
	 * @param @param page
	 * @param @return
	 * @param @throws ParseException 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/biddingInfoList")
	public String biddingInfoList(Model model, HttpServletRequest request,
			Integer page) throws ParseException {
		if (page == null) {
			page = 1;
		}

		// 竞价标题
		String name = request.getParameter("name");
		// 竞价开始时间
		String startTimeStr = request.getParameter("startTime");
		// 竞价结束时间
		String endTimeStr = request.getParameter("endTime");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date startTime = null;
		if (StringUtils.isNotEmpty(startTimeStr)) {
			startTime = dateFormat.parse(startTimeStr);
		}
		Date endTime = null;
		if (StringUtils.isNotEmpty(endTimeStr)) {
			endTime = dateFormat.parse(endTimeStr);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("page", page);
		List<OBProject> list = OBProjectServer.selectAllOBproject(map);
		// 封装分页信息
		PageInfo<OBProject> info = new PageInfo<OBProject>(list);
		// 将查询信息封装到model域中
		model.addAttribute("info", info);
		model.addAttribute("name", name);
		model.addAttribute("startTime", startTimeStr);
		model.addAttribute("endTime", endTimeStr);
		return "bss/ob/biddingSpectacular/list";

	}

	/**
	 * 
	* @Title: findBiddingResult 
	* @Description: 竞价结果查询
	* @author Easong
	* @param @param model
	* @param @param request
	* @param @param page
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/findBiddingResult")
	public String findBiddingResult(Model model, HttpServletRequest request,
			Integer page) {
		// 获取竞价标题的id
		String id = request.getParameter("id");
		
		return "bss/ob/biddingSpectacular/result";
	}

}
