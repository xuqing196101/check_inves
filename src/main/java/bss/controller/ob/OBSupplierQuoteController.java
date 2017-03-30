package bss.controller.ob;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.util.NewBeanInstanceStrategy;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.User;
import ses.util.DictionaryDataUtil;
import bss.dao.ob.OBProductInfoMapper;
import bss.dao.ob.OBProjectResultMapper;
import bss.model.ob.ConfirmInfoVo;
import bss.model.ob.OBProductInfo;
import bss.model.ob.OBProject;
import bss.model.ob.OBProjectResult;
import bss.model.ob.OBProjectSupplier;
import bss.model.ob.OBResultInfoList;
import bss.model.ob.OBResultSubtabulation;
import bss.model.ob.OBResultsInfo;
import bss.model.ob.SupplierProductVo;
import bss.service.ob.OBProjectResultService;
import bss.service.ob.OBProjectServer;
import bss.service.ob.OBSupplierQuoteService;

import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.Constant;
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
	
	// 注入竞价商品详情Mapper
	@Autowired
	private OBProductInfoMapper obProductInfoMapper;
		
	@Autowired
	private OBProjectResultMapper OBProjectResultMapper;

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
		// 获取系统时间
		Date date = new Date();
		model.addAttribute("sysNowTime", date);
		return "bss/ob/supplier/list";
	}

	/**
	 * @throws ParseException 
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
	public String beginQuoteInfo(Model model, HttpServletRequest request) throws ParseException {
		// 获取报价截止时间
		String quotoEndTimeMillStr = request.getParameter("quotoEndTimeMill");
		Long quotoEndTimeMill = null;
		if(StringUtils.isNotEmpty(quotoEndTimeMillStr)){
			quotoEndTimeMill = Long.parseLong(quotoEndTimeMillStr);
		}
		// 获取标题id
		String titleId = request.getParameter("id");
		// 获取报价截止时间
		String quotoEndTimeStr = request.getParameter("quotoEndTime");
		DateFormat dateFormat = new SimpleDateFormat();
		if(StringUtils.isNotEmpty(quotoEndTimeStr)){
			Date date = dateFormat.parse(quotoEndTimeStr);
			System.out.println(date);
		}
		Map<String, Object> map = obSupplierQuoteService.findQuoteInfo(titleId);
		// 竞价信息
		OBProject obProject = (OBProject) map.get("obProject");
		// 竞价商品信息
		Object object = map.get("oBProductInfoList");
		// 获取采购机构名称
		String orgName = (String) map.get("orgName");
		String productIds = (String) map.get("productIds");
		// 获取采购机构名称
		String demandUnit = (String) map.get("demandUnit");
		String transportFees = (String) map.get("transportFees");
		List<UploadFile> uploadFiles = (List<UploadFile>) map.get("uploadFiles");
		List<OBProductInfo> oBProductInfo = null;
		if (object != null) {
			oBProductInfo = (List<OBProductInfo>) map.get("oBProductInfoList");
		}
		
		// 采购机构
		model.addAttribute("orgName", orgName);
		// 需求单位
		model.addAttribute("demandUnit", demandUnit);
		// 运杂费
		model.addAttribute("transportFees", transportFees);
		model.addAttribute("orgName", orgName);
		model.addAttribute("obProject", obProject);
		model.addAttribute("oBProductInfoList", oBProductInfo);
		model.addAttribute("productIds", productIds);
		model.addAttribute("uploadFiles", uploadFiles);
		// 封装文件下载项
		model.addAttribute("fileid", obProject.getAttachmentId());
		model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
		model.addAttribute("typeId",DictionaryDataUtil.getId("BIDD_INFO_MANAGE_ANNEX"));
		
		// 获取当前系统时间毫秒数
		long sysCurrentTime = new Date().getTime();
		// 获取报价倒计时毫秒值
		Long beginQuotoTime = quotoEndTimeMill - sysCurrentTime;
		model.addAttribute("beginQuotoTime", beginQuotoTime);
		
		return "bss/ob/supplier/supplierOffer";
	}

	/**
	 * @author Ma Mingwei
	 * @param model 
	 * @param supplierId 供应商id
	 * @description 点击确认结果
	 * @return string 视图页面
	 * @throws ParseException 
	 */
	@RequestMapping("/confirmResult")
	public String quoteConfirmResult(@CurrentUser User user, Model model, HttpServletRequest request,
			String supplierId, String projectId) throws ParseException {
		
		supplierId = user.getTypeId();
		String confirmStatus="";
		OBProjectResult oBProjectResult=new OBProjectResult();
		oBProjectResult.setProjectId(projectId);
		oBProjectResult.setSupplierId(supplierId);
		String status= OBProjectResultMapper.selectSupplierStatus(oBProjectResult);
		if(status.equals("1")){
			confirmStatus="2";
		}else if(status.equals("-1")){
			confirmStatus="1";
		}
		ConfirmInfoVo result=oBProjectResultService.selectSupplierDate(supplierId,projectId);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		model.addAttribute("sysCurrentTime", new Date());
	 	model.addAttribute("result", result);
	 	model.addAttribute("confirmStatus", 1);
	/*	supplierId = user.getTypeId();
		//把供应商id和标题id封装在oBProjectResult对象里
		OBProjectResult oBProjectResult = new OBProjectResult();
		oBProjectResult.setProjectId(projectId);
		oBProjectResult.setSupplierId(supplierId);
		//先查找一下符合当前竞标的供应商在 竞价结果表 中的status
		String confirmStatus = oBProjectResultService.selectSupplierStatus(oBProjectResult);
		
		
		//这是第一轮显示的数据，由于尽管是第二轮这个依然要显示bidProductList
		//状态为-1，也就是默认时，查找的第一轮的信息，第一、二轮都要显示的
		ConfirmInfoVo confirmInfoVo= new  ConfirmInfoVo();
		ConfirmInfoVo confirmInfoVo1 = oBProjectResultService.selectInfoByPSId(oBProjectResult,"-1");
		BeanUtils.copyProperties(confirmInfoVo1, confirmInfoVo);
		confirmInfoVo1 = null;
		//根据状态有选择的查询
		if("1".equals(confirmStatus)) {
			//第一轮接受，参加第二轮的操作
			confirmInfoVo1= oBProjectResultService.selectInfoByPSId(oBProjectResult,confirmStatus);
			model.addAttribute("secondConfirmInfoVo", confirmInfoVo1);
		}
		if("0".equals(confirmStatus)) {
			//第一轮放弃，参加第二轮时的操作	这个需求暂时无，不走这一步
			
		}
		//获取当前(在猫上运行，就是猫的)时间,（用此时间和当前标题的各个时间段比对）
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
		List<OBProjectResult> oBProjectResultList = oBProjectResultService.selectBySupplierId(supplierId);
		
		model.addAttribute("confirmStatus", confirmStatus);
		model.addAttribute("oBProjectResultList", oBProjectResultList);
		model.addAttribute("allProductPrice", allProductPrice);
		model.addAttribute("projectId", projectId);
		model.addAttribute("sysCurrentTime", sysCurrentTime);
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("confirmInfoVo", confirmInfoVo);*/

		return "bss/ob/supplier/confirmResult";
	}

	/**
	 * 
	* @Title: saveQuoteInfo 
	* @Description: 开始报价，保存报价信息
	* @author Easong
	* @param @param user
	* @param @param obResultsInfoExt
	* @param @param model
	* @param @param request
	* @param @return    设定文件 
	* @return JdcgResult    返回类型 
	* @throws
	 */
	@RequestMapping("/saveQuoteInfo")
	@ResponseBody
	public JdcgResult saveQuoteInfo(@CurrentUser User user,
			OBResultInfoList obResultsInfoExt, Model model,
			HttpServletRequest request) {
		// 获取竞价标题
		String titleId = request.getParameter("titleId");
		// 获取报价总金额，回显数据使用
		String showQuotoTotalPriceStr = request.getParameter("showQuotoTotalPrice");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("titleId", titleId);
		// 封装当前用户信息
		map.put("user", user);
		// 供应商报价信息
		map.put("obResultsInfoExtList", obResultsInfoExt);
		map.put("showQuotoTotalPriceStr", showQuotoTotalPriceStr);
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
	public String uptConfirmQuoteInfoAccept(@CurrentUser User user,@RequestBody List<OBResultSubtabulation> projectResultList,
			Model model,HttpServletRequest request) throws ParseException{
		String acceptNum = request.getParameter("acceptNum");
		String updateNum = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		//获取页面传过来的时间（这个时间点并不准确到实际操作，只是根据前面竞价开始时间加上规则计算出来的）
		String confirmStarttime = request.getParameter("confirmStarttime");//确认开始字符串
		Date cs = sdf.parse(confirmStarttime);//new Date(confirmStarttime)这个过时了
		String confirmOvertime = request.getParameter("confirmOvertime");//第一轮确认结束
		Date co = sdf.parse(confirmOvertime);
		String secondOvertime = request.getParameter("secondOvertime");//第二轮确认结束
		Date so = sdf.parse(secondOvertime);
		updateNum  = oBProjectResultService.updateResult(user,projectResultList,acceptNum);
		
		
		//获取当前的时间
		/*Date currentDate = new Date();
		if(currentDate.getTime() >= cs.getTime() && currentDate.getTime() < co.getTime()) {
			//在第一轮中间
			//调用service层的修改
			updateNum = oBProjectResultService.updateInfoBySPPIdList(user,projectResultList,"1");
		} else if(currentDate.getTime() >= co.getTime() && currentDate.getTime() < so.getTime()) {
			//在第二轮中间
			updateNum = oBProjectResultService.updateInfoBySPPIdList(user,projectResultList,"2");
		} else if(currentDate.getTime() >= so.getTime()) {
			//在第二轮之后(直接给页面一个反馈，不走后台流程)
			updateNum = -1;
		}*/
		
		String updateFlag = "no";
		/*if(updateNum > 0) {
			updateFlag = "yes";
		} else if(updateNum == -1) {
			updateFlag = "error";
		}*/
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
			String projectResultId,
			String roundNum,
			String supplierId,
			String confirmStatus,//当前正处于的未操作的状态
			HttpServletRequest request){
		int uptResult = 0;
		  if(user!=null){
			   if(StringUtils.isNotBlank(supplierId)){
				   if(supplierId.equals(user.getTypeId())){
					 uptResult = oBProjectResultService.updateBySupplierId(projectId,supplierId, confirmStatus,projectResultId);
					   
				   }
			   }else{
				   System.out.println("供应商不能为空");
			   }
		  }
		/*OBProjectResult oBProjectResult = new OBProjectResult();
		//把此供应商的状态都改为0，表示放弃
		oBProjectResult.setSupplierId(supplierId);
		oBProjectResult.setProjectId(projectId);
		
		//第一轮就选择放弃
		if("-1".equals(confirmStatus)) {
			oBProjectResult.setStatus(0);
			uptResult = oBProjectResultService.updateBySupplierId(oBProjectResult,"-1");
		}
		//第二轮选择放弃
		if("1".equals(confirmStatus)) {
			oBProjectResult.setStatus(1);
			uptResult = oBProjectResultService.updateBySupplierId(oBProjectResult,"1");
		}
		*/
		String resFlag = "fail";
		if(uptResult > 0) {
			resFlag = "success";
		}
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
		if(StringUtils.isNotBlank(projectId)){
		Map<String, Object> map = obSupplierQuoteService.findQuoteInfo(projectId);
		// 竞价信息
		OBProject obProject = (OBProject) map.get("obProject");
		// 竞价商品信息
		Object object = map.get("oBProductInfoList");
		// 获取采购机构名称
		String orgName = (String) map.get("orgName");
		String demandUnit = (String) map.get("demandUnit");
		String productIds = (String) map.get("productIds");
		String transportFees = (String) map.get("transportFees");
		List<OBProductInfo> oBProductInfo = null;
		if (object != null) {
			oBProductInfo = (List<OBProductInfo>) map.get("oBProductInfoList");
		}
		Double totalCountPriceBigDecimal = 0.00;
		NumberFormat currency = NumberFormat.getNumberInstance();
		currency.setMinimumIntegerDigits(2);//设置数的小数部分所允许的最小位数(如果不足后面补0) 
		/** 计算单个商品的总价以及合计金额 **/
		for (OBProductInfo productInfo : oBProductInfo) {
			if (productInfo != null) {
				BigDecimal signalCountInt = productInfo.getPurchaseCount();
				BigDecimal limitPrice = productInfo.getLimitedPrice();
				BigDecimal signalCount = null;
				if (signalCountInt != null && limitPrice != null) {
					/** 单个商品的总金额=现价 *采购数量 **/
					signalCount = signalCountInt;
					BigDecimal multiply = limitPrice.multiply(signalCount);
					productInfo.setTotalMoney(multiply);
					/**显示100000样式**/
					productInfo.setTotalMoney(multiply);
					/**显示￥100,000,00样式**/
					productInfo.setTotalMoneyStr(currency.format(multiply));
					/** 累加得到总计 **/
					totalCountPriceBigDecimal = multiply.add(
							new BigDecimal(Double
									.toString(totalCountPriceBigDecimal)))
							.doubleValue();
				}
			}
		}
		String totalCountPriceBigDecimalStr = currency.format(totalCountPriceBigDecimal);
		// 采购机构
		model.addAttribute("orgName", orgName);
		// 需求单位
		model.addAttribute("demandUnit", demandUnit);
		// 运杂费
		model.addAttribute("transportFees", transportFees);
		model.addAttribute("obProject", obProject);
		model.addAttribute("oBProductInfoList", oBProductInfo);
		model.addAttribute("productIds", productIds);
		model.addAttribute("totalCountPriceBigDecimal", totalCountPriceBigDecimalStr);
		
		// 封装文件下载项
		model.addAttribute("fileid", obProject.getAttachmentId());
		model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
		model.addAttribute("typeId",DictionaryDataUtil.getId("BIDD_INFO_MANAGE_ANNEX"));
		
		//查找 参与这个标题的供应商(里面封装有供应商所竞价的商品部分信息)
		List<OBProjectResult> resultList=OBProjectResultMapper.selectByPID(obProject.getId());
		List<OBProductInfo> plist=obProductInfoMapper.getProductName(obProject.getId());
		for(OBProjectResult s:resultList){
			s.setProductInfo(plist);
		}
		model.addAttribute("selectInfoByPID", resultList);
		model.addAttribute("plist", plist);
		}
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
	
	/**
	 * 
	* @Title: findQuotoIssueInfo 
	* @Description: 查询报价后的信息
	* @author Easong
	* @param @param model
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findQuotoIssueInfo")
	public String findQuotoIssueInfo(@CurrentUser User user, Model model, HttpServletRequest request){
		// 获取标题id
		String projectId = request.getParameter("id");
		Map<String, Object> mapInfo = new HashMap<String, Object>();
		if(projectId != null){
			mapInfo.put("supplierId", user.getTypeId());
			mapInfo.put("projectId", projectId);
			Map<String, Object> map = obSupplierQuoteService.selectQuotoInfo(mapInfo);
			// 竞价信息
			OBProject obProject = (OBProject) map.get("obProject");
			// 获取采购机构名称
			String orgName = (String) map.get("orgName");
			// 单位
			String demandUnit = (String) map.get("demandUnit");
			// 运杂费
			String transportFees = (String) map.get("transportFees");
			// 上传文件项
			List<UploadFile> uploadFiles = (List<UploadFile>) map
					.get("uploadFiles");
			
			// 报价产品信息
			List<OBResultsInfo> oBResultsInfo  = (List<OBResultsInfo>) map.get("oBResultsInfo");
			Double totalCountPriceBigDecimal = 0.00;
			// 保留两位小数
			//DecimalFormat df = new DecimalFormat("0.00");
			NumberFormat currency = NumberFormat.getNumberInstance();
			currency.setMinimumIntegerDigits(2);//设置数的小数部分所允许的最小位数(如果不足后面补0) 
			/** 计算单个商品的总价以及合计金额 **/
			for (OBResultsInfo obResultInfo : oBResultsInfo) {
				if (obResultInfo != null) {
					Integer signalCountInt = obResultInfo.getResultsNumber();
					BigDecimal myOfferMoney = obResultInfo.getMyOfferMoney();
					BigDecimal signalCount = null;
					if (signalCountInt != null && myOfferMoney != null) {
						/** 单个商品的总金额=报价 *采购数量 **/
						signalCount = new BigDecimal(signalCountInt);
						BigDecimal multiply = myOfferMoney.multiply(signalCount);
						/**显示100000样式**/
						obResultInfo.setDealMoney(multiply);
						/**显示￥100,000,00样式**/
						obResultInfo.setDealMoneyStr(currency.format(multiply));
						/** 累加得到总计 **/
						totalCountPriceBigDecimal = multiply.add(
								new BigDecimal(Double
										.toString(totalCountPriceBigDecimal)))
								.doubleValue();
					}
				}
			}
			//String totalCountPriceBigDecimalStr = df.format(totalCountPriceBigDecimal);
			String totalCountPriceBigDecimalStr = currency.format(totalCountPriceBigDecimal);
			// 采购机构
			model.addAttribute("orgName", orgName);
			// 需求单位
			model.addAttribute("demandUnit", demandUnit);
			// 运杂费
			model.addAttribute("transportFees", transportFees);
			model.addAttribute("obProject", obProject);
			model.addAttribute("uploadFiles", uploadFiles);
			model.addAttribute("oBResultsInfo", oBResultsInfo);
			model.addAttribute("totalCountPriceBigDecimal", totalCountPriceBigDecimalStr);
			model.addAttribute("fileid", obProject.getAttachmentId());
			model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
			model.addAttribute("typeId",DictionaryDataUtil.getId("BIDD_INFO_MANAGE_ANNEX"));
		}
		return "bss/ob/supplier/findQuotoIssueInfo";
	}
	
	/**
	 * 
	* @Title: findBiddingResult 
	* @Description: 查询竞价结果
	* @author Easong
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/findBiddingResult")
	public String findBiddingResult(Model model, HttpServletRequest request){
		// 获取标题id
		String projectId = request.getParameter("id");
		
		//查找 参与这个标题的供应商(里面封装有供应商所竞价的商品部分信息)
		List<OBProjectResult> resultList=OBProjectResultMapper.selectByPID(projectId);
		List<OBProductInfo> plist=obProductInfoMapper.getProductName(projectId);
		for(OBProjectResult s:resultList){
			s.setProductInfo(plist);
		}
		model.addAttribute("selectInfoByPID", resultList);
		model.addAttribute("plist", plist);
		return "bss/ob/biddingSpectacular/result";
	}
	
	

	/**
	 * 
	* @Title: findSupplierUnBidding 
	* @Description: 查询未中标的供应商
	* @author Easong
	* @param @return    设定文件 
	* @return JdcgResult    返回类型 
	* @throws
	 */
	@RequestMapping("/findSupplierUnBidding")
	@ResponseBody
	public JdcgResult findSupplierUnBidding(@CurrentUser User user, HttpServletRequest request){
		String projectId = request.getParameter("projectId");
		// 获取供应商的id
		if(user == null){
			return JdcgResult.ok("请先登录!");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplier_id", user.getTypeId());
		map.put("project_id", projectId);
		List<OBProjectResult> list = oBProjectResultService.findSupplierUnBidding(map);
		OBProjectResult obProjectResult = null;
		String remarkString = null;
		if(list != null && list.size() > 0){
			obProjectResult = list.get(0);
			remarkString = obProjectResult.getRemark();
			if(StringUtils.isEmpty(remarkString)){
				remarkString = "0";
			}
		}
		return JdcgResult.ok(remarkString);
	}
	
	/**
     * 
    * @Title: getSysTime 
    * @Description: 获取当前系统时间
    * @author Easong
    * @param @return    设定文件 
    * @return Long    返回类型 
    * @throws
     */
    @RequestMapping("/getSysTime")
    @ResponseBody()
    public Long getSysTime(){
    	// 获取当前系统时间  毫米值
    	Date date = new Date();
    	long sysDate = date.getTime();
    	return sysDate;
    }
}
