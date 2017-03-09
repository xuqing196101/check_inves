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

import bss.model.ob.OBProductInfo;
import bss.model.ob.OBProject;
import bss.service.ob.OBProjectServer;
import bss.service.ob.OBSupplierQuoteService;

import com.github.pagehelper.PageInfo;

@RequestMapping("/supplierQuote")
@Controller
public class OBSupplierQuoteController {

	@Autowired
	private OBProjectServer obProjectServer;

	@Autowired
	private OBSupplierQuoteService obSupplierQuoteService;

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

	@RequestMapping("/beginQuoteInfo")
	public String beginQuoteInfo(Model model, HttpServletRequest request){
		// 获取标题id
		String titleId = request.getParameter("id");
		Map<String, Object> map = obSupplierQuoteService.findQuoteInfo(titleId);
		// 竞价信息
		OBProject obProject = (OBProject) map.get("obProject");
		// 竞价商品信息
		OBProductInfo oBProductInfo = (OBProductInfo) map.get("oBProductInfoList");
		model.addAttribute("obProject", obProject);
		model.addAttribute("oBProductInfo", oBProductInfo);
		return "bss/ob/supplier/supplierOffer";
	}
	
	
	
	/**
	 * @author Ma Mingwei
	 * @param model
	 * @return
	 */
	@RequestMapping("/confirmResult")
	public String quoteConfirmResult(Model model){
		// 
		return "bss/ob/supplier/confirmResult";
	}
}
