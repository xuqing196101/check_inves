package bss.controller.ob;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ses.dao.oms.OrgnizationMapper;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.service.oms.OrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.PathUtil;
import ses.util.PropertiesUtil;
import bss.dao.ob.OBProductInfoMapper;
import bss.dao.ob.OBRuleMapper;
import bss.model.ob.OBProduct;
import bss.model.ob.OBProductInfo;
import bss.model.ob.OBProductInfoExample;
import bss.model.ob.OBProductInfoExample.Criteria;
import bss.model.ob.OBProject;
import bss.model.ob.OBProjectResult;
import bss.model.ob.OBRule;
import bss.model.ob.OBSupplier;
import bss.model.pms.PurchaseRequired;
import bss.service.ob.OBProductInfoServer;
import bss.service.ob.OBProjectResultService;
import bss.service.ob.OBProjectServer;
import bss.service.ob.OBRuleService;
import bss.service.ob.OBSupplierQuoteService;
import bss.util.ExcelUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;

import common.annotation.CurrentUser;
import common.constant.Constant;
import common.model.UploadFile;

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
	Logger log = LoggerFactory.getLogger(OBProjectController.class);

	@Autowired
	private OBProjectServer OBProjectServer;

	@Autowired
	private OrgnizationServiceI orgnizationService;
	@Autowired
	private OBRuleService OBRuleService;

	@Autowired
	private OBProjectResultService oBProjectResultService;

	@Autowired
	private OBProductInfoServer OBProductInfo;

	// 注入竞价商品详情Mapper
	@Autowired
	private OBProductInfoMapper obProductInfoMapper;

	@Autowired
	private OrgnizationMapper orgnizationMapper;

	@Autowired
	private OBSupplierQuoteService obSupplierQuoteService;
	
	@Autowired
	private OBRuleMapper OBRuleMapper;

	
	/***
	 * 获取竞价信息跳转 list页
	 * 
	 * @author YangHongLiang
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", produces = "text/html;charset=UTF-8")
	public String list(@CurrentUser User user, Model model,
			HttpServletRequest request, Integer page, Date startTime,
			String name) {
		if (user != null) {
			if (page == null) {
				page = 1;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("page", page);
			map.put("uid", user.getId());
			map.put("startTime", startTime);
			map.put("name", name);
			PropertiesUtil config = new PropertiesUtil("config.properties");
			PageHelper.startPage((Integer) (map.get("page")),
					Integer.parseInt(config.getString("pageSize")));
			List<OBProject> list = OBProjectServer.List(map);
			PageInfo<OBProject> info = new PageInfo<OBProject>(list);
			model.addAttribute("info", info);
		}
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
		// 获取当前 默认规则
		 OBRule obRule = OBRuleMapper.selectByStatus();
		// 生成ID
		String uuid = UUID.randomUUID().toString().toUpperCase()
				.replace("-", "");
		model.addAttribute("supplierCount",obRule.getLeastSupplierNum());
		model.addAttribute("fileid", uuid);
		model.addAttribute("userId", user.getId());
		model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
		// 标识 竞价附件
		model.addAttribute("typeId",
				DictionaryDataUtil.getId("BIDD_INFO_MANAGE_ANNEX"));
		return "bss/ob/biddingInformation/publish";
	}

	/**
	 * 获取可用的采购机构 信息 并返回页面
	 * 
	 * @author YangHongLiang
	 * @throws IOException
	 */
	@RequestMapping("mechanism")
	public void getMechanism(@CurrentUser User user, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
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
	public String biddingInfoList(@CurrentUser User user, Model model,
			HttpServletRequest request, Integer page) throws ParseException {
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
		if (user != null) {
			map.put("userId", user.getId());
		}
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
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/findBiddingResult")
	public String findBiddingResult(Model model, HttpServletRequest request,
			Integer page) {
		if (page == null) {
			page = 1;
		}
		// 获取竞价标题的id
		String id = request.getParameter("id") == null ? "" : request
				.getParameter("id");
		List<OBProjectResult> list = oBProjectResultService.selectByProjectId(
				id, page);
		PageInfo<OBProjectResult> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		OBProject obProject = OBProjectServer.selectByPrimaryKey(id);
		Integer countOfferPricebyOne = 0;
		if (list != null && list.size() > 0) {
			OBProjectResult obProjectResult = list.get(0);
			countOfferPricebyOne = obProjectResult.getCountOfferPrice();
		}
		if(obProject != null){
			model.addAttribute("projectName", obProject.getName());
		}
		model.addAttribute("countOfferPricebyOne", countOfferPricebyOne);
		int count = OBProductInfo.selectCount(id);
		int chengjiao = 0;
		for (OBProjectResult obProjectResult : list) {
			chengjiao += obProjectResult.getCountresultCount();
		}
		model.addAttribute("count", count);
		model.addAttribute("chengjiao", chengjiao);
		// 竞价标题id
		model.addAttribute("titleId", id);
		return "bss/ob/biddingSpectacular/result";
	}

	/**
	 * 获取可用的产品相关信息 并返回页面
	 * 
	 * @author YangHongLiang
	 * @throws IOException
	 */
	@RequestMapping("product")
	public void getProduct(@CurrentUser User user, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			String json = OBProjectServer.getProduct();
			response.getWriter().print(json.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close();
		}
	}

	/**
	 * 
	 * @Title: downFile
	 * @Description: 下载excel表格模板 author: YangHongLiang
	 * @param @param path
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("download")
	public ResponseEntity<byte[]> download(HttpServletRequest request,
			String filename) throws IOException {
		String path = PathUtil.getWebRoot() + "excel/定型产品.xls";
		;
		File file = new File(path);
		HttpHeaders headers = new HttpHeaders();
		String fileName = new String("定型产品模板.xls".getBytes("UTF-8"),
				"iso-8859-1");// 为了解决中文名称乱码问题
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
				headers, HttpStatus.OK);
	}

	/**
	 * @Description: 竞价管理保存 author: YangHongLiang
	 * @param 接收页面返回数据
	 * @return
	 * @return String
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping(value = "/addProject", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addProject(@CurrentUser User user, OBProject obProject,
			HttpServletRequest request, String fileid) {
		String msg = "";
		if (user != null) {
			msg = OBProjectServer.saveProject(obProject, user.getId(), fileid);
		}
		return msg;

	}
	/***
	 * @Description: 查询供应商并集
	 * @author: YangHongLiang
	 * @param 接收页面返回数据
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping("unionSupplier")
	public void unionSupplier(HttpServletRequest request, HttpServletResponse response,List<String> productid) throws IOException{
		try {
			System.out.println(productid.size());
			if(productid!=null&&productid.size()>0){
			List<OBSupplier> list = OBProjectServer.selecUniontSupplier(productid);
			response.getWriter().print(JSON.toJSONString(list).toString());
			response.getWriter().flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close();
		}
		
	}
	
	/** @Description: 竞价管理更新
	* author: YangHongLiang
	* @param 接收页面返回数据
	* @return     
	* @return String     
    * @throws IOException 
	* @throws Exception
	*/
	@RequestMapping(value="/edit", produces="text/html;charset=UTF-8" )
	@ResponseBody
	public String edit(@CurrentUser User user,OBProject obProject, HttpServletRequest request,
			String fileid){
		String msg="";
		if(user !=null){
			msg=OBProjectServer.saveProject(obProject,user.getId(),fileid);
		}
		return msg;
		
	}
	
	/** @Description: 编辑暂存的竞价信息
	* author: YangHongLiang
	* @param  OBProject
	* @return     
	* @return String     
    * @throws IOException 
	* @throws Exception
	*/
	@RequestMapping(value="/editOBProject", produces="text/html;charset=UTF-8" )
	public String editOBProject(@CurrentUser User user,Model model, HttpServletRequest request,String obProjectId){
		if(user !=null){
			if(StringUtils.isNotBlank(obProjectId)){
			Map<String,Object> map=new HashMap<String, Object>();	
			map.put("id", obProjectId);
			map.put("userId", user.getId());
			OBProject obProject=OBProjectServer.editOBProject(map);
			System.out.println(obProject.toString());
			if(obProject !=null){
				//默认规则
				OBRule obr=OBRuleService.selectByStatus();
				// 生成ID
				model.addAttribute("rule", obr);
				model.addAttribute("userId", user.getId());
				model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
				// 标识 竞价附件
				model.addAttribute("typeId",DictionaryDataUtil.getId("BIDD_INFO_MANAGE_ANNEX"));
				model.addAttribute("list", obProject);
				model.addAttribute("listinfo", JSON.toJSONString(obProject.getObProductInfo()));
			 }
			}
		}
		return "bss/ob/biddingInformation/publish";
		
	}
	/**
	 * @Title: uploadFile
	 * @Description: 导入excel表格数据 author: YangHongLiang
	 * @param @return
	 * @return String
	 * @throws IOException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/upload", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String uploadFile(@CurrentUser User user, String planDepName,
			MultipartFile file, String type, String planName, String planNo,
			Model model) throws Exception {
		
		String fileName = file.getOriginalFilename();
		if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
			return "1";
		}

		List<PurchaseRequired> list = new ArrayList<PurchaseRequired>();
		Map<String, Object> maps = (Map<String, Object>) ExcelUtil
				.readOBExcel(file);
		list = (List<PurchaseRequired>) maps.get("list");

		String errMsg = (String) maps.get("errMsg");

		if (errMsg != null) {
			String jsonString = JSON.toJSONString(errMsg);
			return jsonString;
		}
		String jsonString = JSON.toJSONString(list);
		return jsonString;
	}

	/**
	 * 
	 * @Title: printResult
	 * @Description: 打印竞价结果
	 * @author Easong
	 * @param @param model
	 * @param @param request
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/printResult")
	public String printResult(Model model, HttpServletRequest request,
			Integer page) {
		if (page == null) {
			page = 1;
		}
		// 获取打印结果表示
		String print = request.getParameter("print");
		// 获取竞价标题的id
		String id = request.getParameter("id") == null ? "" : request
				.getParameter("id");
		List<OBProjectResult> list = oBProjectResultService.selectByProjectId(
				id, page);
		PageInfo<OBProjectResult> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		OBProject obProject = OBProjectServer.selectByPrimaryKey(id);

		// 根据标题id查询该标题下发布的产品信息
		OBProductInfoExample example = new OBProductInfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andProjectIdEqualTo(id);
		// 根据标题的id查询标题下所有的商品信息
		List<OBProductInfo> obProductInfoList = obProductInfoMapper
				.selectByExample(example);
		HashMap<String, Object> selectMap = new HashMap<String, Object>();
		model.addAttribute("obProductInfoList", obProductInfoList);
		// 根据采购机构id查询采购机构
		selectMap.put("id", obProject.getOrgId());
		List<Orgnization> orgnizationMapperList = orgnizationMapper
				.selectByPrimaryKey(selectMap);
		Integer countOfferPricebyOne = 0;
		if (list != null && list.size() > 0) {
			Orgnization orgnization = orgnizationMapperList.get(0);
			model.addAttribute("orgName", orgnization.getName());
			countOfferPricebyOne = list.get(0).getCountOfferPrice();
		}
		model.addAttribute("obProject", obProject);
		model.addAttribute("countOfferPricebyOne", countOfferPricebyOne);
		int count = OBProductInfo.selectCount(id);
		int chengjiao = 0;
		for (OBProjectResult obProjectResult : list) {
			chengjiao += obProjectResult.getCountresultCount();
		}
		model.addAttribute("count", count);
		model.addAttribute("chengjiao", chengjiao);
		if (StringUtils.isNotEmpty(print)) {
			// 打印结果页面
			return "bss/ob/biddingSpectacular/expert_word_print";
		}
		return "bss/ob/biddingSpectacular/print";
	}

	/**
	 * 
	 * @Title: findBiddingInfo
	 * @Description: 查看竞价发布信息
	 * @author Easong
	 * @param @param model
	 * @param @param request
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findBiddingIssueInfo")
	public String findBiddingInfo(Model model, HttpServletRequest request)
			throws Exception {
		// 获取标题id
		String titleId = request.getParameter("id");
		// 获取成交合格供应商数
		String bargainCountStr = request.getParameter("bargainCount");
		Integer bargainCount = 0;
		if (StringUtils.isNotEmpty(bargainCountStr)) {
			bargainCount = Integer.parseInt(bargainCountStr);
		}
		Map<String, Object> map = obSupplierQuoteService.findQuoteInfo(titleId);
		// 竞价信息
		OBProject obProject = (OBProject) map.get("obProject");
		obProject.setQualifiedSupplier(bargainCount);
		// 竞价商品信息
		Object object = map.get("oBProductInfoList");
		// 获取采购机构名称
		String orgName = (String) map.get("orgName");
		String productIds = (String) map.get("productIds");
		List<UploadFile> uploadFiles = (List<UploadFile>) map
				.get("uploadFiles");
		List<OBProductInfo> oBProductInfo = null;
		if (object != null) {
			oBProductInfo = (List<OBProductInfo>) map.get("oBProductInfoList");
		}
		Double totalCountPriceBigDecimal = 0.00;
		/** 计算单个商品的总价以及合计金额 **/
		for (OBProductInfo productInfo : oBProductInfo) {
			if (productInfo != null) {
				Integer signalCountInt = productInfo.getPurchaseCount();
				BigDecimal limitPrice = productInfo.getLimitedPrice();
				BigDecimal signalCount = null;
				if (signalCountInt != null && limitPrice != null) {
					/** 单个商品的总金额=现价 *采购数量 **/
					signalCount = new BigDecimal(signalCountInt);
					BigDecimal multiply = limitPrice.multiply(signalCount);
					productInfo.setTotalMoney(multiply);
					/** 累加得到总计 **/
					totalCountPriceBigDecimal = multiply.add(
							new BigDecimal(Double
									.toString(totalCountPriceBigDecimal)))
							.doubleValue();
				}
			}
		}
		BigDecimal bigDecimal = new BigDecimal(totalCountPriceBigDecimal);
		bigDecimal.setScale(2);
		model.addAttribute("orgName", orgName);
		model.addAttribute("obProject", obProject);
		model.addAttribute("oBProductInfoList", oBProductInfo);
		model.addAttribute("productIds", productIds);
		model.addAttribute("uploadFiles", uploadFiles);
		model.addAttribute("totalCountPriceBigDecimal", bigDecimal.toString());
		return "bss/ob/biddingSpectacular/findBiddingIssueInfo";
	}

}
