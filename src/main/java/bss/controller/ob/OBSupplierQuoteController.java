package bss.controller.ob;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.User;
import ses.util.DictionaryDataUtil;
import bss.dao.ob.OBProductInfoMapper;
import bss.dao.ob.OBProjectResultMapper;
import bss.dao.ob.OBProjectSupplierMapper;
import bss.dao.ob.OBResultsInfoMapper;
import bss.model.ob.ConfirmInfoVo;
import bss.model.ob.OBProduct;
import bss.model.ob.OBProductInfo;
import bss.model.ob.OBProject;
import bss.model.ob.OBProjectResult;
import bss.model.ob.OBProjectSupplier;
import bss.model.ob.OBResultInfoList;
import bss.model.ob.OBResultSubtabulation;
import bss.model.ob.OBResultsInfo;
import bss.model.ob.OBRuleTimeInterval;
import bss.service.ob.OBProjectResultService;
import bss.service.ob.OBProjectServer;
import bss.service.ob.OBResultSubtabulationService;
import bss.service.ob.OBSupplierQuoteService;
import bss.util.BiddingStateUtil;
import bss.util.BigDecimalUtils;

import com.alibaba.fastjson.JSON;
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
	@Autowired
	private OBProjectSupplierMapper mapper;
	
	// 出入结果Service
	@Autowired
	private OBResultSubtabulationService obResultSubtabulationService;
	// 注入竞价项目Service
	@Autowired
	private OBProjectServer OBProjectServer;
	
	@Autowired
	private OBResultsInfoMapper obResultsInfoMapper;
	
	// 第一轮结果确认
	private static final String FIRST_CONFIRM = "firstConfirm";
	// 第二轮结果确认
	private static final String SECOND_CONFIRM = "secondConfirm";
	
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
	@SuppressWarnings("unchecked")
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
		// 调用Service方法
		Map<String, Object> selectSupplierOBproject = obProjectServer.selectSupplierOBproject(map);
		List<OBProjectSupplier> oBProjectSupplier = null;
		List<OBRuleTimeInterval> timeList = null;
		PageInfo<OBProjectSupplier> info = null;
		if(selectSupplierOBproject != null){
			// // 存储报价列表信息
			oBProjectSupplier = (List<OBProjectSupplier>) selectSupplierOBproject.get("obProjectList");
			info = new PageInfo<OBProjectSupplier>(oBProjectSupplier);
			// 封装竞价各段时间的信息
			timeList = (List<OBRuleTimeInterval>) selectSupplierOBproject.get("timeList");
		}
		// 将竞价信息转成json
		String timeListObject = JSON.toJSONString(timeList);
		model.addAttribute("timeListObject", timeListObject);
		
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
	 * @description 验证
	 * @return string 视图页面
	 * @throws ParseException 
	 */
	@RequestMapping(value="/checkConfirmResult")
	@ResponseBody
	public JdcgResult checkConfirmResult(@CurrentUser User user, Model model, HttpServletRequest request,
			String supplierId, String projectId) throws ParseException {
		supplierId = user.getTypeId();
		String confirmStatus="";
		OBProjectResult oBProjectResult=new OBProjectResult();
		oBProjectResult.setProjectId(projectId);
		oBProjectResult.setSupplierId(supplierId);
		OBProject project=obProjectServer.selectByPrimaryKey(projectId);
		int ranking=0;
		 if(project!=null){
			 //竞价结束时间 和当前时间比较
		  if(project.getEndTime().getTime()<new Date().getTime()){
			  confirmStatus="4";
		  }else{
		 List<OBProjectResult>	getList= OBProjectResultMapper.selectSupplierStatus(oBProjectResult);
		 if(getList!=null && getList.size()==1){
			 //必须一条数据 状态是-1 表示第一轮
		    if(getList.get(0).getStatus()==-1 && getList.get(0).getRemark().equals("1")){
		    	if(getList.get(0).getProportion().equals("0")){
		    		//未中标
					confirmStatus="5";
		    	}else{
		    		//第一轮
		    		confirmStatus="1";
		    	}
		     }else if(getList.get(0).getStatus()==1){
		    	 //标识第一轮 接受 如果竞价未完成 100% 可参加 第二轮
		    	 confirmStatus="2";
		     }
		  }else if(getList!=null && getList.size()==2){
			  //已经操作过第二轮
			  confirmStatus="3";
		 }
		  if(confirmStatus=="2"){
			  //获取 可以进行第一轮 供应商
			  List< OBProjectResult> obresultsList = OBProjectResultMapper.getSecond(projectId);
			  if(obresultsList!=null){
				  OBProjectResult result=obresultsList.get(0);
				  if(result.getSupplierId().equals(supplierId)){
					  confirmStatus="2";
				  }else{
					  //顺推 前一名第二轮未确定
						 ranking=result.getRanking();
						 confirmStatus="8";
				}
		     }else{
		    	 //第二轮 已经结束
		    	 confirmStatus="7";
		     }
		    }
		  }
		 }else{
			 confirmStatus="4";
		 }
		 
		 JdcgResult jdcg=new JdcgResult();
		 if(confirmStatus=="1"){
			 jdcg.setStatus(1);
			 jdcg.setMsg("第一轮");
		 }else   if(confirmStatus=="2"){
			 jdcg.setStatus(2);
			 jdcg.setMsg("第二轮");
		 }else  if(confirmStatus=="3"){
			 jdcg.setStatus(3);
			 jdcg.setMsg("第二轮已操作");
		 }else if(confirmStatus=="4"){
			 jdcg.setStatus(4);
			 jdcg.setMsg("时间已结束");
		 }else if(confirmStatus=="5") {
			 jdcg.setStatus(5);
			 jdcg.setMsg("第一轮未中标");
		 }else if(confirmStatus=="7"){
			 jdcg.setStatus(7);
			 jdcg.setMsg("竞价已完成");
		 }else if(confirmStatus=="8"){
			 jdcg.setStatus(8);
			 //jdcg.setMsg("您是第"+ranking+"名,第"+(ranking-1)+"名第二轮未确定,请耐心等候");
			 jdcg.setMsg("您的前一名第二轮未确定,请耐心等候");
		 }else{
			 jdcg.setStatus(0);
			 jdcg.setMsg("错误");
		 }
		return jdcg;
	}
	
	
	/**
	 * @author Ma Mingwei
	 * @param model 
	 * @param supplierId 供应商id
	 * @description 点击确认结果
	 * @return string 视图页面
	 * @throws ParseException 
	 */
	@RequestMapping(value="/confirmResult",produces = "text/html;charset=UTF-8")
	public String quoteConfirmResult(@CurrentUser User user, Model model, HttpServletRequest request,
			String supplierId, String projectId) throws ParseException {
		supplierId = user.getTypeId();
		String confirmStatus="";
		OBProjectResult oBProjectResult=new OBProjectResult();
		oBProjectResult.setProjectId(projectId);
		oBProjectResult.setSupplierId(supplierId);
		OBProject project=obProjectServer.selectByPrimaryKey(projectId);
		int ranking=0;
		 if(project!=null){
			 //竞价结束时间 和当前时间比较
		  if(project.getEndTime().getTime()<new Date().getTime()){
			  confirmStatus="4";
		  }else{
		 List<OBProjectResult>	getList= OBProjectResultMapper.selectSupplierStatus(oBProjectResult);
		 if(getList!=null && getList.size()==1){
			 //必须一条数据 状态是-1 表示第一轮
		    if(getList.get(0).getStatus()==-1 && getList.get(0).getRemark().equals("1")){
		    	if(getList.get(0).getProportion().equals("0")){
		    		//未中标
					confirmStatus="5";
		    	}else{
		    		//第一轮
		    		confirmStatus="1";
		    	}
		     }else if(getList.get(0).getStatus()==1){
		    	 //标识第一轮 接受 如果竞价未完成 100% 可参加 第二轮
		    	 confirmStatus="2";
		     }
		  }else if(getList!=null && getList.size()==2){
			  //已经操作过第二轮
			  confirmStatus="3";
		 }
		  if(confirmStatus=="2"){
			  //获取 可以进行第一轮 供应商
			  List< OBProjectResult> obresultsList = OBProjectResultMapper.getSecond(projectId);
			  if(obresultsList!=null){
				  OBProjectResult result=obresultsList.get(0);
				  if(result.getSupplierId().equals(supplierId)){
					  confirmStatus="2";
				  }else{
					  //顺推 前一名第二轮未确定
						 ranking=result.getRanking();
						 confirmStatus="8";
				}
		     }else{
		    	 //第二轮 已经结束
		    	 confirmStatus="7";
		     }
		    }
		  }
		 }else{
			 confirmStatus="4";
		 }
		
		
		
		 if(confirmStatus=="1"||confirmStatus=="2"){
             BigDecimal million = new BigDecimal(10000);
		  ConfirmInfoVo result=oBProjectResultService.selectSupplierDate(supplierId,projectId,confirmStatus);
             if(result != null){
                 List<OBResultsInfo> obResultsInfos = result.getOBResultsInfo();
                 for (OBResultsInfo obResultsInfo : obResultsInfos){
                     // 成交价
                     BigDecimal dealMoney = obResultsInfo.getDealMoney();
                     // 成交数量
                     Integer resultsNumber = obResultsInfo.getResultsNumber();
                     BigDecimal resultsNumbers = new BigDecimal(resultsNumber);
                     // 成交总价
                     BigDecimal multiply = dealMoney.multiply(resultsNumbers);
                     multiply.setScale(4);
                     BigDecimal moneyBigDecimal = multiply.divide(million, 4, BigDecimal.ROUND_HALF_UP);
                     obResultsInfo.setDealTotalMoney(moneyBigDecimal);
                 }

             }
			model.addAttribute("sysCurrentTime", new Date());
		 	model.addAttribute("result", result);
		 	model.addAttribute("confirmStatus", confirmStatus);
		  }
		 return "/bss/ob/supplier/confirmResult";
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
	@RequestMapping(value="/uptConfirmAccept")
	@ResponseBody
	public JdcgResult uptConfirmQuoteInfoAccept(@CurrentUser User user,@RequestBody List<OBResultSubtabulation> projectResultList,
			Model model,HttpServletRequest request) throws ParseException{
		String acceptNum = request.getParameter("acceptNum");
		int type = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		//获取页面传过来的时间（这个时间点并不准确到实际操作，只是根据前面竞价开始时间加上规则计算出来的）
		String confirmStarttime = request.getParameter("confirmStarttime");//确认开始字符串
		Date cs = sdf.parse(confirmStarttime);//new Date(confirmStarttime)这个过时了
		String confirmOvertime = request.getParameter("confirmOvertime");//第一轮确认结束
		Date co = sdf.parse(confirmOvertime);
		String secondOvertime = request.getParameter("secondOvertime");//第二轮确认结束
		Date so = sdf.parse(secondOvertime);
		//获取当前的时间
		Date currentDate = new Date();
		if(currentDate.getTime() >= cs.getTime() && currentDate.getTime() < co.getTime()) {
			//在第一轮中间
			type=1;
		} else if(currentDate.getTime() >= co.getTime() && currentDate.getTime() < so.getTime()) {
			//在第二轮中间
			type=2;
		} else if(currentDate.getTime() >= so.getTime()) {
			//在第二轮之后(直接给页面一个反馈，不走后台流程)
			type = -1;
		}
		if(type==-1){
			return JdcgResult.build(0, "确定时间超出,不能确定");
		}else{
			oBProjectResultService.updateResult(user,projectResultList,acceptNum);
			if(type==1){
				return JdcgResult.build(1, "第一轮确定成功");
			}else if(type==2){
				return JdcgResult.build(1, "第二轮确定成功");
			}else{
				return JdcgResult.build(0, "确定错误");
			}
		}
	}
	/**
	 * @description 确认结果页面   点击放弃,后台针对传过来的信息执行修改
	 * @param model
	 * @param request
	 * @return string ajax返回执行状态
	 * @author Ma Mingwei
	 * @throws ParseException 
	 */
	@RequestMapping("uptConfirmDrop")
	@ResponseBody
	public JdcgResult uptConfirmQuoteInfoDrop(@CurrentUser User user,
			String projectId,Model model,
			String projectResultId,String roundNum,
			String supplierId,String confirmStatus,//当前正处于的未操作的状态
			HttpServletRequest request) throws ParseException{
		int uptResult = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		//获取页面传过来的时间（这个时间点并不准确到实际操作，只是根据前面竞价开始时间加上规则计算出来的）
				String confirmStarttime = request.getParameter("confirmStarttime");//确认开始字符串
				Date cs = sdf.parse(confirmStarttime);//new Date(confirmStarttime)这个过时了
				String confirmOvertime = request.getParameter("confirmOvertime");//第一轮确认结束
				Date co = sdf.parse(confirmOvertime);
				String secondOvertime = request.getParameter("secondOvertime");//第二轮确认结束
				Date so = sdf.parse(secondOvertime);
				//获取当前的时间
				Date currentDate = new Date();
				if(currentDate.getTime() >= cs.getTime() && currentDate.getTime() < co.getTime()) {
					//在第一轮中间
					uptResult=1;
				} else if(currentDate.getTime() >= co.getTime() && currentDate.getTime() < so.getTime()) {
					//在第二轮中间
					uptResult=2;
				} else if(currentDate.getTime() >= so.getTime()) {
					//在第二轮之后(直接给页面一个反馈，不走后台流程)
					uptResult = -1;
				}
				JdcgResult jdcgResult=new JdcgResult();
		    if(user!=null){
			   if(StringUtils.isNotBlank(supplierId)){
				   if(supplierId.equals(user.getTypeId())){
						if(uptResult==-1){
							jdcgResult.setStatus(0);
							jdcgResult.setMsg("确定时间超出,不能放弃");
						}else{
							 boolean boo = oBProjectResultService.updateBySupplierId(projectId,supplierId, confirmStatus,projectResultId);
							 if(uptResult==1){
								jdcgResult.setStatus(boo==false?1:2);
								jdcgResult.setMsg("第一轮放弃成功");
							}else if(uptResult==2){
								jdcgResult.setStatus(boo==false?1:2);
								jdcgResult.setMsg("第二轮放弃成功");
							}else{
								jdcgResult.setStatus(0);
								jdcgResult.setMsg("放弃错误");
							}
						}
				   }
			   }else{
				   jdcgResult.setStatus(0);
				jdcgResult.setMsg("供应商数据为空");
			   }
		  }
		return jdcgResult;
	}
	
	/**
	 * @author Ma Mingwei
	 * @description 竞价结果查询
	 * @param model
	 * @param request 	projectId--竞价标题id
	 * @return string 视图页面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("queryBiddingResult")
	public String queryBiddingResult(Model model,
			@CurrentUser User user,
			HttpServletRequest request,
			String projectId){
		if(StringUtils.isNotBlank(projectId)){
		Map<String, Object> map = obSupplierQuoteService.findQuoteInfo(projectId);
		
		/**********************竞价信息****************************/
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
		BigDecimal million = new BigDecimal(10000);
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
					BigDecimal moneyBigDecimal = multiply.divide(million);
					productInfo.setTotalMoney(moneyBigDecimal);
					/**显示￥100,000,00样式**/
//					productInfo.setTotalMoneyStr(currency.format(multiply));
					/** 累加得到总计 **/
					totalCountPriceBigDecimal = multiply.add(
							new BigDecimal(Double
									.toString(totalCountPriceBigDecimal)))
							.doubleValue();
				}
			}
		}
		//String totalCountPriceBigDecimalStr = currency.format(totalCountPriceBigDecimal);
		// 采购机构
		model.addAttribute("orgName", orgName);
		// 需求单位
		model.addAttribute("demandUnit", demandUnit);
		// 运杂费
		model.addAttribute("transportFees", transportFees);
		model.addAttribute("obProject", obProject);
		model.addAttribute("oBProductInfoList", oBProductInfo);
		model.addAttribute("productIds", productIds);
		model.addAttribute("totalCountPriceBigDecimal", totalCountPriceBigDecimal / 10000);
		
		// 封装文件下载项
		model.addAttribute("fileid", obProject.getAttachmentId());
		model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
		model.addAttribute("typeId",DictionaryDataUtil.getId("BIDD_INFO_MANAGE_ANNEX"));
		
		/**********************竞价信息结束****************************/
		//查找 参与这个标题的供应商(里面封装有供应商所竞价的商品部分信息)
		/*List<OBProjectResult> resultList=OBProjectResultMapper.selectByPID(obProject.getId());
		List<OBProductInfo> plist=obProductInfoMapper.getProductName(obProject.getId());
		for(OBProjectResult s:resultList){
			s.setProductInfo(plist);
		}*/
		
		
		/**************************页面底层供应商信息*************************/
		// 页面底层供应商信息
		//查询参与的供应商==============================================================================================
		if(projectId != null){
			List<OBProjectResult> listss = oBProjectResultService.selResultByProjectId(projectId);
	    	Integer countProportion = 0;
	    	if(listss != null && listss.size() > 0){
	    		for (OBProjectResult obProjectResult : listss) {
					if(obProjectResult != null){
						if(obProjectResult.getStatus() == 1){
							List<OBProjectResult> prolist = oBProjectResultService.selProportion(projectId, obProjectResult.getSupplierId());
							if(prolist != null && prolist.size() == 1){
								obProjectResult.setFirstproportion(prolist.get(0).getProportion());
							}
							if(prolist != null && prolist.size() == 2){
								obProjectResult.setFirstproportion(prolist.get(0).getProportion());
								obProjectResult.setSecondproportion(prolist.get(1).getProportion());
							}
							List<OBResultSubtabulation> obResultSubtabulation = obResultSubtabulationService.selectByProjectIdAndSupplierId(projectId, obProjectResult.getSupplierId());
							if(obResultSubtabulation != null && obResultSubtabulation.size() > 0){
								for (OBResultSubtabulation obResultSubtabulation2 : obResultSubtabulation) {
									if(obResultSubtabulation2 != null){
										obResultSubtabulation2.setTotalMoney(obResultSubtabulation2.getTotalMoney().divide(million));
									}
								}
							}
							
							obProjectResult.setObResultSubtabulation(obResultSubtabulation);
							countProportion += Integer.parseInt(obProjectResult.getProportion());
						}else{
							List<OBResultsInfo> listinf = obResultsInfoMapper.selectResult(projectId, obProjectResult.getSupplierId());
							obProjectResult.setOBResultsInfo(listinf);
						}
					}
				}
	    	}
			OBProject obProjectww = OBProjectServer.selectByPrimaryKey(projectId);
	    	if(obProjectww != null){
	    		String projectName = obProjectww.getName();
	    		model.addAttribute("projectName",projectName);
	    	}
			model.addAttribute("listres", listss);
			model.addAttribute("countProportion",countProportion);
			model.addAttribute("size",listss.size());
		}
    	
    	/**************************页面底层供应商信息结束*************************/
		
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
		
		// 获取第一轮确认结果标识
		String confirmFlag = request.getParameter("flag");
		
		Map<String, Object> mapInfo = new HashMap<String, Object>();
		
		// 获取供应商ID
		String typeIdString = null;
		if(user != null){
			typeIdString = user.getTypeId();
		}
		if(projectId != null){
			mapInfo.put("supplierId", typeIdString);
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
			//NumberFormat currency = NumberFormat.getNumberInstance();
			//currency.setMinimumIntegerDigits(2);//设置数的小数部分所允许的最小位数(如果不足后面补0) 
			/** 计算单个商品的总价以及合计金额 **/
			BigDecimal million = new BigDecimal(10000);
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
						BigDecimal moneyBigDecimal = BigDecimalUtils.getSignalDecimalScale4(multiply, million);
						obResultInfo.setDealMoney(moneyBigDecimal);
						/**显示￥100,000,00样式**/
						//obResultInfo.setDealMoneyStr(currency.format(multiply));
						/** 累加得到总计 **/
						totalCountPriceBigDecimal = multiply.add(
								new BigDecimal(Double
										.toString(totalCountPriceBigDecimal)))
								.doubleValue();
					}
				}
			}
			//String totalCountPriceBigDecimalStr = df.format(totalCountPriceBigDecimal);
			//String totalCountPriceBigDecimalStr = currency.format(totalCountPriceBigDecimal);
			// 采购机构
			model.addAttribute("orgName", orgName);
			// 需求单位
			model.addAttribute("demandUnit", demandUnit);
			// 运杂费
			model.addAttribute("transportFees", transportFees);
			model.addAttribute("obProject", obProject);
			model.addAttribute("uploadFiles", uploadFiles);
			model.addAttribute("oBResultsInfo", oBResultsInfo);

			// 保留四位小数
			BigDecimal totalCountPriceBigDecimalAfter = new BigDecimal(totalCountPriceBigDecimal);
			// 计算总价钱
			BigDecimal totalCountPriceBigDecimalShow = BigDecimalUtils.getBigDecimalTOScale4(totalCountPriceBigDecimalAfter, million);
			model.addAttribute("totalCountPriceBigDecimal", totalCountPriceBigDecimalShow);

			if(obProject != null){
				model.addAttribute("fileid", obProject.getAttachmentId());
			}
			model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
			model.addAttribute("typeId",DictionaryDataUtil.getId("BIDD_INFO_MANAGE_ANNEX"));
			
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("projectId", projectId);
			resultMap.put("supplierId", typeIdString);
			// 定义
			/***********************************第一轮结果确认信息****************************/
			if(FIRST_CONFIRM.equals(confirmFlag) || SECOND_CONFIRM.equals(confirmFlag)){
				/** 获取第一轮、第二轮的排序标识 
				 * 	：第一轮确认结果获取的是按时间正序排序的第一条记录
				 * 	：第二轮确认结果是按时间倒序排序的第一条记录	
				 * **/
				OBProjectResult findConfirmResult = null;
				OBProjectResult findConfirmResultSecond = null;
				// 第一轮确认金额总计
				Double confirmFirstTotalFigureStr = 0.00;
				// 第二轮确认金额总计
				Double confirmSecondTotalFigureStr = 0.00;
				/**
				 * 第一轮确认的结果查询
				 */
				if(FIRST_CONFIRM.equals(confirmFlag)){
					// 第一轮结果确认信息
					resultMap.put("orderWay", "ASC");
					findConfirmResult = oBProjectResultService.findConfirmResult(resultMap);
					List<OBResultSubtabulation> subtabulationList = findConfirmResult.getObResultSubtabulation();
					calculateSignalResultTotalPrice(subtabulationList);
					confirmFirstTotalFigureStr = BigDecimalUtils.getTotalFigure(findConfirmResult);
				}
				
				/**
				 * 第二轮确认的结果查询
				 */
				if(SECOND_CONFIRM.equals(confirmFlag)){
					// 第二轮结果确认信息--既显示第一轮有显示第二轮
					resultMap.put("orderWay", "ASC");
					// 第一轮
					findConfirmResult = oBProjectResultService.findConfirmResult(resultMap);
					List<OBResultSubtabulation> subtabulationList = findConfirmResult.getObResultSubtabulation();
					calculateSignalResultTotalPrice(subtabulationList);
					confirmFirstTotalFigureStr = BigDecimalUtils.getTotalFigure(findConfirmResult);
					// 第二轮
					resultMap.put("orderWay", "DESC");
					findConfirmResultSecond = oBProjectResultService.findConfirmResult(resultMap);
					List<OBResultSubtabulation> subtabulationSecondList = findConfirmResultSecond.getObResultSubtabulation();
					calculateSignalResultTotalPrice(subtabulationSecondList);
					confirmSecondTotalFigureStr = BigDecimalUtils.getTotalFigure(findConfirmResultSecond);
					// 封装数据
					BigDecimal bigDecimal = new BigDecimal(confirmSecondTotalFigureStr);
					// 单位换算
					BigDecimal confirmSecondTotalFigureBigDecimal = bigDecimal.setScale(4, BigDecimal.ROUND_HALF_UP);
					model.addAttribute("confirmResultSecond", findConfirmResultSecond);
					model.addAttribute("confirmSecondTotalFigureStr", confirmSecondTotalFigureBigDecimal);
				}
				
				BigDecimal bigDecimal = new BigDecimal(confirmFirstTotalFigureStr);
				BigDecimal confirmFirstTotalFigureBigDecimal = bigDecimal.setScale(4, BigDecimal.ROUND_HALF_UP);
				
				model.addAttribute("confirmResult", findConfirmResult);
				
				model.addAttribute("confirmFirstTotalFigureStr", confirmFirstTotalFigureBigDecimal);
				model.addAttribute("confirmFlag", confirmFlag);
			}
			
			/***********************************第一轮结果确认信息结束****************************/
			
		}
		return "bss/ob/supplier/findQuotoIssueInfo";
	}
	
	
	/**
	 * 
	* @Title: calculateSignalResultTotalPrice 
	* @Description: 计算确认结果单个商品总价以万元的方式显示
	* @author Easong
	* @param @param subtabulationList    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void calculateSignalResultTotalPrice(List<OBResultSubtabulation> subtabulationList){
		// 将产品的价格按照万元的方式输出
		BigDecimal millionTotal = new BigDecimal(10000);
		for (OBResultSubtabulation obResultSubtabulation : subtabulationList) {
			if(obResultSubtabulation != null){
				// BigDecimal的除法
				BigDecimal totalMoney = obResultSubtabulation.getTotalMoney();
				BigDecimal moneyBigDecimal = BigDecimalUtils.getSignalDecimalScale4(totalMoney, millionTotal);
				obResultSubtabulation.setTotalMoney(moneyBigDecimal);
			}
		}
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
		// 调用Service方法
		List<OBProjectResult> list = oBProjectResultService.findSupplierUnBidding(map);
		OBProjectResult obProjectResult = null;
		String proportion = null;
		if(list != null && list.size() > 0){
			obProjectResult = list.get(0);
			proportion = obProjectResult.getProportion();
			if(StringUtils.isEmpty(proportion)){
				proportion = "0";
			}
		}
		return JdcgResult.ok(proportion);
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
