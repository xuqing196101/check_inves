package bss.controller.ob;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.User;
import bss.model.ob.ConfirmInfoVo;
import bss.model.ob.OBProductInfo;
import bss.model.ob.OBProject;
import bss.model.ob.OBProjectResult;
import bss.model.ob.OBProjectSupplier;
import bss.model.ob.OBResultInfoList;
import bss.model.ob.SupplierProductVo;
import bss.service.ob.OBProjectResultService;
import bss.service.ob.OBProjectServer;
import bss.service.ob.OBSupplierQuoteService;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.model.UploadFile;
import common.utils.JdcgResult;
/**
 * 
* @ClassName: OBSupplierQuoteController 
* @Description: 供应商后台管理Controller
* @author Easong
* @date 2017年3月20日 下午1:19:19 
*
 */
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
	public String list(@CurrentUser User user, Model model, HttpServletRequest request, Integer page)
			throws ParseException {
		if (page == null) {
			page = 1;
		}
		// 状态标识
		String remark = request.getParameter("remark");
		if(StringUtils.isEmpty(remark)){
			remark = "0";
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
		map.put("user", user);
		map.put("name", name);
		map.put("createTime", createTime);
		map.put("remark", remark);
		List<OBProjectSupplier> oBProjectSupplier = obProjectServer.selectSupplierOBproject(map);
		PageInfo<OBProjectSupplier> info = new PageInfo<OBProjectSupplier>(oBProjectSupplier);
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
		List<UploadFile> uploadFiles = (List<UploadFile>) map.get("uploadFiles");
		List<OBProductInfo> oBProductInfo = null;
		if (object != null) {
			oBProductInfo = (List<OBProductInfo>) map.get("oBProductInfoList");
		}
		model.addAttribute("orgName", orgName);
		model.addAttribute("obProject", obProject);
		model.addAttribute("oBProductInfoList", oBProductInfo);
		model.addAttribute("productIds", productIds);
		model.addAttribute("uploadFiles", uploadFiles);
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
	public String quoteConfirmResult(@CurrentUser User user, Model model, HttpServletRequest request,
			String supplierId, String projectId) {
		supplierId = user.getTypeId();
		//把供应商id和标题id封装在oBProjectResult对象里
		OBProjectResult oBProjectResult = new OBProjectResult();
		oBProjectResult.setProjectId(projectId);
		oBProjectResult.setSupplierId(supplierId);
		//先查找一下符合当前竞标的供应商在 竞价结果表 中的status
		String confirmStatus = oBProjectResultService.selectSupplierStatus(oBProjectResult);
		
		//这是第一轮显示的数据，由于尽管是第二轮这个依然要显示
		//状态为-1，也就是默认时，查找的第一轮的信息，第一、二轮都要显示的
		ConfirmInfoVo confirmInfoVo = oBProjectResultService.selectInfoByPSId(oBProjectResult,"-1");
		//根据状态有选择的查询
		if("1".equals(confirmStatus)) {
			//第一轮接受，参加第二轮的操作
			ConfirmInfoVo secondConfirmInfoVo = oBProjectResultService.selectInfoByPSId(oBProjectResult,confirmStatus);
			model.addAttribute("secondConfirmInfoVo", secondConfirmInfoVo);
		}
		if("0".equals(confirmStatus)) {
			//第一轮放弃，参加第二轮时的操作	这个需求暂时不够确定，先不走这一步
			
		}
		//获取当前(在猫上运行，就是猫的)时间
		Date sysCurrentTime = new Date();
		
		double allProductPrice = 0;
		if(confirmInfoVo != null) {
			for(int i = 0;i < confirmInfoVo.getBidProductList().size();i++) {
				Double dealMoney = confirmInfoVo.getBidProductList().get(i).getMyOfferMoney().doubleValue() * confirmInfoVo.getBidProductList().get(i).getProductNum().doubleValue();
				confirmInfoVo.getBidProductList().get(i).setDealMoney(new BigDecimal(dealMoney));
				allProductPrice += dealMoney;
			}
		}
		
		// 根据供应商查询到的竞价结果信息
		List<OBProjectResult> oBProjectResultList = oBProjectResultService
				.selectBySupplierId(supplierId);
		
		model.addAttribute("confirmStatus", confirmStatus);
		model.addAttribute("oBProjectResultList", oBProjectResultList);
		model.addAttribute("allProductPrice", allProductPrice);
		model.addAttribute("projectId", projectId);
		model.addAttribute("sysCurrentTime", sysCurrentTime);
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("confirmInfoVo", confirmInfoVo);

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
	 * @description 确认结果页面   点击接受,后台针对传过来的信息执行修改
	 * @param model
	 * @param request
	 * @return string ajax返回执行状态
	 * @author Ma Mingwei	//,method=RequestMethod.POST
	 * @throws ParseException 
	 */
	@RequestMapping(value="uptConfirmAccept")
	@ResponseBody
	public String uptConfirmQuoteInfoAccept(@CurrentUser User user,
			@RequestBody List<OBProjectResult> projectResultList,
			Model model,
			HttpServletRequest request) throws ParseException{
		String acceptNum = request.getParameter("acceptNum");
		int updateNum = 0;//定义接受的数字
		SimpleDateFormat sdf = new SimpleDateFormat();
		//获取页面传过来的时间（这个时间点并不准确到实际操作，只是根据前面竞价开始时间加上规则计算出来的）
		String confirmStarttime = request.getParameter("confirmStarttime");//确认开始字符串
		Date cs = sdf.parse(confirmStarttime);//new Date(confirmStarttime)这个过时了
		String confirmOvertime = request.getParameter("confirmOvertime");//第一轮确认结束
		Date co = sdf.parse(confirmOvertime);
		String secondOvertime = request.getParameter("secondOvertime");//第二轮确认结束
		Date so = sdf.parse(secondOvertime);
		//获取当前的时间
		Date currentDate = new Date();
		if(currentDate.getTime() >= cs.getTime() && currentDate.getTime() > co.getTime()) {
			//在第一轮中间
			//调用service层的修改
			updateNum = oBProjectResultService.updateInfoBySPPIdList(projectResultList);
		} else if(currentDate.getTime() >= co.getTime() && currentDate.getTime() > so.getTime()) {
			//在第二轮中间
		} else if(currentDate.getTime() >= so.getTime()) {
			//在第二轮之后(直接给页面一个反馈，不走后台流程)
			updateNum = -1;
		}
		
		String updateFlag = "no";
		if(updateNum > 0) {
			updateFlag = "yes";
		} else if(updateNum == -1) {
			updateFlag = "error";
		}
		return updateFlag;
	}
	
	/**
	 * @description 确认结果页面   点击放弃,后台针对传过来的信息执行修改
	 * @param model
	 * @param request
	 * @return string ajax返回执行状态
	 * @author Ma Mingwei
	 */
	@RequestMapping("uptConfirmDrop")
	@ResponseBody
	public String uptConfirmQuoteInfoDrop(@CurrentUser User user,
			String projectId,
			Model model,
			String roundNum,
			HttpServletRequest request){
		String supplierId = "2E7A7EAC566343379640DDAB5A35123F";//user.getId();
		
		OBProjectResult oBProjectResult = new OBProjectResult();
		//把此供应商的状态都改为0，表示放弃
		oBProjectResult.setSupplierId(supplierId);
		oBProjectResult.setSupplierId(projectId);
		oBProjectResult.setStatus(0);
		int uptResult = oBProjectResultService.updateBySupplierId(oBProjectResult);
		String resFlag = "fail";
		if(uptResult > 0) {
			resFlag = "success";
		}
		System.out.println(projectId + "cnjewfn" + uptResult);
		return resFlag;
	}
	
	/**
	 * @author Ma Mingwei
	 * @description 竞价结果查询
	 * @param model
	 * @param request 	projectId--竞价标题id
	 * @return string 视图页面
	 */
	@RequestMapping("queryBiddingResult")
	public String queryBiddingResult(Model model,
			@CurrentUser User user,
			HttpServletRequest request,
			String projectId){
		if (projectId == null || "".equals(projectId)) {
			// 这个目前做测试用
			projectId = "DDE523291D694F5B8CC084EC2DFDBFF9";
		}
		String supplierId = user.getTypeId();
		//查找这个标题id的标题信息
		OBProject obProject = obProjectServer.selectByPrimaryKey(projectId);
		
		//查找 参与这个标题的供应商(里面封装有供应商所竞价的商品部分信息)
		List<SupplierProductVo> selectInfoByPID = oBProjectResultService.selectInfoByPID(projectId, supplierId);
		
		model.addAttribute("selectInfoByPID", selectInfoByPID);
		model.addAttribute("obProject", obProject);
		
		return "bss/ob/supplier/queryBiddingResults";
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
