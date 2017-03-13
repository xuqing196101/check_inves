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
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.User;
import bss.model.ob.OBProductInfo;
import bss.model.ob.OBProject;
import bss.model.ob.OBProjectResult;
import bss.model.ob.OBResultInfoList;
import bss.service.ob.OBProjectResultService;
import bss.service.ob.OBProjectServer;
import bss.service.ob.OBSupplierQuoteService;

import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.utils.JdcgResult;

@RequestMapping("/supplierQuote")
@Controller
public class OBSupplierQuoteController {

	@Autowired
	private OBProjectServer obProjectServer;

	@Autowired
	private OBSupplierQuoteService obSupplierQuoteService;

	@Autowired
	private OBProjectResultService oBProjectResultService;

	/**
	 * @throws ParseException
	 * 
	 * @Title: list
	 * @Description: 供应商报价列表查询
	 * @author Easong
	 * @param @param model
	 * @param @param request
	 * @param @param page
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/list")
	public String list(Model model, HttpServletRequest request, Integer page)
			throws ParseException {
		if (page == null) {
			page = 1;
		}
		// 竞价标题
		String name = request.getParameter("name");
		// 竞价发布时间
		String createTimeStr = request.getParameter("createTime");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date createTime = null;
		if (StringUtils.isNotBlank(createTimeStr)) {
			createTime = dateFormat.parse(createTimeStr);
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("name", name);
		map.put("startTime", createTime);
		List<OBProject> list = obProjectServer.selectAllOBproject(map);
		PageInfo<OBProject> info = new PageInfo<OBProject>(list);
		// 将查询出的数据存入到model域中
		model.addAttribute("info", info);
		// 竞价发布时间回显
		model.addAttribute("name", name);
		model.addAttribute("createTimeStr", createTimeStr);
		return "bss/ob/supplier/list";
	}

	/**
	 * 
	 * @Title: beginQuoteInfo
	 * @Description: 供应商开始竞价
	 * @author Easong
	 * @param @param model
	 * @param @param request
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/beginQuoteInfo")
	public String beginQuoteInfo(Model model, HttpServletRequest request) {
		// 获取标题id
		String titleId = request.getParameter("id");
		Map<String, Object> map = obSupplierQuoteService.findQuoteInfo(titleId);
		// 竞价信息
		OBProject obProject = (OBProject) map.get("obProject");
		// 竞价商品信息
		Object object = map.get("oBProductInfoList");
		// 获取采购机构名称
		String orgName = (String) map.get("orgName");
		String productIds = (String) map.get("productIds");
		List<OBProductInfo> oBProductInfo = null;
		if (object != null) {
			oBProductInfo = (List<OBProductInfo>) map.get("oBProductInfoList");
		}
		model.addAttribute("orgName", orgName);
		model.addAttribute("obProject", obProject);
		model.addAttribute("oBProductInfoList", oBProductInfo);
		model.addAttribute("productIds", productIds);

		return "bss/ob/supplier/supplierOffer";
	}

	/**
	 * @author Ma Mingwei
	 * @param model 
	 * @param supplierId 供应商id
	 * @description 点击确认结果
	 * @return string 视图页面
	 */
	@RequestMapping("/confirmResult")
	public String quoteConfirmResult(Model model, HttpServletRequest request,
			String supplierId) {
		if (supplierId == null || "".equals(supplierId)) {
			// 这个目前做测试用
			supplierId = "5b214591d1ba471ebcbda346408f6545";
		}
		// 根据供应商查询到的竞价结果信息
		List<OBProjectResult> oBProjectResultList = oBProjectResultService
				.selectBySupplierId(supplierId);
		model.addAttribute("oBProjectResultList", oBProjectResultList);

		return "bss/ob/supplier/confirmResult";
	}

	@RequestMapping("/saveQuoteInfo")
	@ResponseBody
	public JdcgResult saveQuoteInfo(@CurrentUser User user,
			OBResultInfoList obResultsInfoExt, Model model,
			HttpServletRequest request) {
		// 获取竞价标题
		String titleId = request.getParameter("titleId");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("titleId", titleId);
		// 封装当前用户信息
		map.put("user", user);
		// 供应商报价信息
		map.put("obResultsInfoExtList", obResultsInfoExt);
		return obSupplierQuoteService.saveQuoteInfo(map);
	}
	
	/**
	 * 
	 * @param model
	 * @param request
	 * @return string 视图页面
	 */
	@RequestMapping("saveConfirmQuoteInfo")
	public String saveConfirmQuoteInfo(Model model, HttpServletRequest request){
		
		return "";
	}
}
