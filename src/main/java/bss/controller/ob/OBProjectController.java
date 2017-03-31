package bss.controller.ob;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ses.dao.oms.OrgnizationMapper;
import ses.model.bms.Category;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.PathUtil;
import ses.util.PropertiesUtil;
import bss.dao.ob.OBProductInfoMapper;
import bss.dao.ob.OBProjectResultMapper;
import bss.dao.ob.OBResultsInfoMapper;
import bss.dao.ob.OBRuleMapper;
import bss.model.ob.OBProduct;
import bss.model.ob.OBProductInfo;
import bss.model.ob.OBProductInfoExample;
import bss.model.ob.OBProductInfoExample.Criteria;
import bss.model.ob.OBProject;
import bss.model.ob.OBProjectResult;
import bss.model.ob.OBResultSubtabulation;
import bss.model.ob.OBResultsInfo;
import bss.model.ob.OBRule;
import bss.model.ob.OBSupplier;
import bss.model.pms.PurchaseRequired;
import bss.service.ob.OBProductInfoServer;
import bss.service.ob.OBProjectResultService;
import bss.service.ob.OBProjectServer;
import bss.service.ob.OBResultSubtabulationService;
import bss.service.ob.OBRuleService;
import bss.service.ob.OBSupplierQuoteService;
import bss.service.ob.OBSupplierService;
import bss.util.CheckUtil;
import bss.util.ExcelUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.Constant;
import common.model.UploadFile;
import common.utils.JdcgResult;

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
	private OBSupplierService obSupplierService;

	@Autowired
	private OrgnizationServiceI orgnizationService;
	@Autowired
	private OBRuleService OBRuleService;

	@Autowired
	private DictionaryDataServiceI dictionaryDataService;
	
	@Autowired
	private OBProjectResultService oBProjectResultService;

	@Autowired
	private OBProductInfoServer OBProductInfo;
	
	@Autowired
	private CategoryService categoryService;

	// 注入竞价商品详情Mapper
	@Autowired
	private OBProductInfoMapper obProductInfoMapper;
    
	@Autowired
	private OBResultsInfoMapper OBResultsInfoMapper;
	@Autowired
	private OBProjectResultMapper OBProjectResultMapper;
	@Autowired
	private OrgnizationMapper orgnizationMapper;

	@Autowired
	private OBSupplierQuoteService obSupplierQuoteService;
	
	@Autowired
	private OBRuleMapper OBRuleMapper;
	
	@Autowired
	private OBResultSubtabulationService obResultSubtabulationService;

	
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
			model.addAttribute("name",name);
			model.addAttribute("startTime",startTime);
		}
		return "bss/ob/biddingInformation/list";
	}
	/***
	 * 获取供应商信息跳转 list页
	 * 
	 * @author YangHongLiang
	 * @param model
	 * @param request
	 * @param status区分供应商 类型
	 * @return
	 */
	@RequestMapping(value = "/supplierList", produces = "text/html;charset=UTF-8")
	public String supplierList(@CurrentUser User user, Model model,
			HttpServletRequest request, Integer page,String obProjectId,
			String name,String status,String result) {
		if (user != null) {
			if (page == null) {
				page = 1;
			}
			String ss = request.getParameter("obProjectId");
			List<OBSupplier> lists = OBProjectServer.supplierList(page,obProjectId,
					 name, status,result);
			model.addAttribute("info", new PageInfo<OBSupplier>(lists));
			model.addAttribute("obProjectId",obProjectId);
			model.addAttribute("name",name);
			model.addAttribute("status",status);
			model.addAttribute("result",result);
			if(lists != null){
				for (OBSupplier obSupplier : lists) {
					String id = obSupplier.getSmallPointsId();
					if(id != null){
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("id", id);
						List<Category> clist = categoryService.findCategoryByParentNode(map);
						String str = "";
						for (Category category : clist) {
							if(!obSupplier.getSmallPoints().getName().equals(category.getName())){
								str += category.getName() +"/";
							}
							
						}
						str+=obSupplier.getSmallPoints().getName();
						obSupplier.setPointsName(str);
					}
				}
			}
		}
		return "bss/ob/biddingInformation/supplierlist";
	}
	
	/**
	 * 
	 * Description: 报价供应商列表
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月30日 
	 * @param  @param user
	 * @param  @param model
	 * @param  @param request
	 * @param  @param page
	 * @param  @param obProjectId
	 * @param  @param name
	 * @param  @param status
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping(value = "/offerSupplierList", produces = "text/html;charset=UTF-8")
	public String offerSupplierList(Model model,
			HttpServletRequest request, Integer page,String obProjectId,
			String name,Integer status) {
			if (page == null) {
				page = 1;
			}
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("page", page);
			map1.put("supplierName", name);
			map1.put("projectId", obProjectId);
			map1.put("status", status);
			List<OBSupplier> list = obSupplierService.selOfferSupplier(map1);
			model.addAttribute("info", new PageInfo<OBSupplier>(list));
			model.addAttribute("obProjectId",obProjectId);
			model.addAttribute("name",name);
			model.addAttribute("status",status);
			if(list != null){
				for (OBSupplier obSupplier : list) {
					String id = obSupplier.getSmallPointsId();
					if(id != null){
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("id", id);
						List<Category> clist = categoryService.findCategoryByParentNode(map);
						String str = "";
						for (Category category : clist) {
							if(!obSupplier.getSmallPoints().getName().equals(category.getName())){
								str += category.getName() +"/";
							}
							
						}
						str+=obSupplier.getSmallPoints().getName();
						obSupplier.setPointsName(str);
					}
				}
		}
		return "bss/ob/biddingInformation/supplierlist";
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
		model.addAttribute("ruleId",obRule.getId());
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
	 * 获取运杂费数据字典  信息 并返回页面
	 * 
	 * @author YangHongLiang
	 * @throws IOException
	 */
	@RequestMapping(value = "/transportFeesType", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getTransportFeesType(@CurrentUser User user, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		DictionaryData data=new DictionaryData();
		data.setKind(41);
		List<DictionaryData> list= dictionaryDataService.find(data);
		return JSON.toJSONString(list);
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
		model.addAttribute("startTimeStr", startTimeStr);
		model.addAttribute("endTimeStr", endTimeStr);
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
	@SuppressWarnings("unchecked")
	@RequestMapping("/findBiddingResult")
	public String findBiddingResult(Model model, HttpServletRequest request) {
		// 获取竞价标题的id
		String id = request.getParameter("id") == null ? "" : request.getParameter("id");
		OBProject obProject22 = OBProjectServer.selectByPrimaryKey(id);
		if(obProject22 != null){
			model.addAttribute("projectName", obProject22.getName());
		}
		if(StringUtils.isNotBlank(id)){
			Map<String, Object> map = obSupplierQuoteService.findQuoteInfo(id);
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
			/*model.addAttribute("fileid", obProject.getAttachmentId());
			model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
			model.addAttribute("typeId",DictionaryDataUtil.getId("BIDD_INFO_MANAGE_ANNEX"));*/
			
			//查找 参与这个标题的供应商(里面封装有供应商所竞价的商品部分信息)
			List<OBProjectResult> resultList=OBProjectResultMapper.selectByPID(id);
			List<OBProductInfo> plist=obProductInfoMapper.getProductName(id);
			for(OBProjectResult s:resultList){
				s.setProductInfo(plist);
			}
			model.addAttribute("selectInfoByPID", resultList);
			Integer totalProportion = 0;
			for (int i = 0; i < resultList.size(); i++) {
				if(resultList.get(i).getStatus() == 1 || resultList.get(i).getStatus() == 2){
					totalProportion += Integer.parseInt(resultList.get(i).getProportion());
				}
			}
			model.addAttribute("totalProportion", totalProportion);
			model.addAttribute("plist", plist);
			}
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
	@ResponseBody
	public JdcgResult unionSupplier(HttpServletRequest request, HttpServletResponse response,String productid){
			List<OBSupplier> getlist=null;
			JdcgResult jdcg=new JdcgResult();
			 if(productid!=null && productid!=""){
				 productid=productid.substring(0,productid.length()-1);
				 String plist[]=productid.split(",");
				 List<String> list=new ArrayList<>();
				 for(String item:plist){
					 list.add(item);
				 }
				 getlist= OBProjectServer.selecUniontSupplier(list);
				 if(getlist!=null && getlist.size()>0){
					 if(getlist.get(0).getSupplierId()=="" ||getlist.get(0).getSupplierId() ==null){
						 jdcg.setCount("0");
					 }else{
						 jdcg.setCount(getlist.get(0).getSupplierId());
					 }
					 if(getlist.get(0).getSupplierId()=="" ||getlist.get(0).getSupplierId() ==null){
						 jdcg.setSum("0");
					 }else{
						 jdcg.setSum(getlist.size()+"");
					 }
			}
				 }
			 return jdcg;
	}
	/**
	 * 动态验证 产品 是否在同一目录 下
	 * @author YangHongliang
	 * @param request
	 * @param response
	 * @param productid
	 * @return
	 */
	@RequestMapping("checkCatalog")
	@ResponseBody
	public String checkCatalog(HttpServletRequest request, HttpServletResponse response,@RequestBody List<String> productid){
	  return OBProjectServer.verifyCatalog(productid);
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
	public String editOBProject(@CurrentUser User user,Model model, HttpServletRequest request,String obProjectId,String status){
		if(user !=null){
			if(StringUtils.isNotBlank(obProjectId)){
			Map<String,Object> map=new HashMap<String, Object>();	
			map.put("id", obProjectId);
			map.put("userId", user.getId());
			OBProject obProject=OBProjectServer.editOBProject(map);
			if(obProject !=null){
				// 生成ID
				model.addAttribute("ruleId", obProject.getRuleId());
				model.addAttribute("userId", user.getId());
				model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
				// 标识 竞价附件
				model.addAttribute("typeId",DictionaryDataUtil.getId("BIDD_INFO_MANAGE_ANNEX"));
				model.addAttribute("list", obProject);
				model.addAttribute("listinfo", JSON.toJSONString(obProject.getObProductInfo()));
				model.addAttribute("fileid", obProject.getAttachmentId());
				if(obProject.getStatus()==3||obProject.getStatus()==4){
				//查找 参与这个标题的供应商(里面封装有供应商所竞价的商品部分信息)
				List<OBProjectResult> resultList=OBProjectResultMapper.selectByPID(obProject.getId());
				List<OBProductInfo> plist=obProductInfoMapper.getProductName(obProject.getId());
				for(OBProjectResult s:resultList){
					if(s.getStatus()==-1){
				List<OBResultsInfo> infoList=OBResultsInfoMapper.getProductInfo(obProject.getId(),s.getSupplierId());
						s.setOBResultsInfo(infoList);
					}else{
						s.setProductInfo(plist);
					}
				}
				model.addAttribute("selectInfoByPID", resultList);
				}
				
				if(StringUtils.isNotBlank(status)){
					return "bss/ob/biddingInformation/editPublish";
				}else{
				if(obProject.getStatus()==0){
					return "bss/ob/biddingInformation/publish";
				}else{
					return "bss/ob/biddingInformation/editPublish";
				 }
			   }
			 }
			}
		}
		return "bss/ob/biddingInformation/editPublish";
	}
	
	/** @Description: 更新删除 暂存的竞价信息
	* author: YangHongLiang
	* @param  OBProject
	* @return     
	* @return String     
    * @throws IOException 
	* @throws Exception
	*/
	@RequestMapping(value="/delOBProject", produces="text/html;charset=UTF-8" )
	public String delOBProject(@CurrentUser User user,Model model, HttpServletRequest request,String obProjectId,String status){
		if(user !=null){
			if(StringUtils.isNotBlank(obProjectId)){
			Map<String,Object> map=new HashMap<String, Object>();	
			map.put("id", obProjectId);
			map.put("userId", user.getId());
			OBProject obProject=OBProjectServer.editOBProject(map);
			if(obProject !=null){
				OBProject ob=new OBProject();
				ob.setId(obProjectId);
				ob.setIsDelete(1);
				 OBProjectServer.updateProject(ob);
			 } 
			}
		}
		return list(user, model, request, null, null, "");
	}
	
	
	/**
	 * @Description:根据 供应商数量 获取相对应的成交比例
	 * @author: YangHongLiang
	 * @param @return
	 * @return String
	 * @throws IOException 
	 */
	@RequestMapping("proportion")
	public void proportion(HttpServletRequest request, HttpServletResponse response, Integer supplierCount) throws IOException{
		String combination="";
		try {
			if(supplierCount !=null){
				switch (supplierCount) {
				case 1:
					combination=CheckUtil.combinationInteger(Constant.OB_PROJECT_ONE);
					break;
				case 2:
					combination=CheckUtil.combinationInteger(Constant.OB_PROJECT_TWO);
					break;
				case 3:
					combination=CheckUtil.combinationInteger(Constant.OB_PROJECT_THREE);
					break;
				case 4:
					combination=CheckUtil.combinationInteger(Constant.OB_PROJECT_FOUR);
					break;
				case 5:
					combination=CheckUtil.combinationInteger(Constant.OB_PROJECT_FIVE);
					break;
				case 6:
					combination=CheckUtil.combinationInteger(Constant.OB_PROJECT_SIX);
					break;
				default:
					break;
				}
			response.getWriter().print(combination);
			response.getWriter().flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close();
		}
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
		List<OBProductInfo> obProductInfoList = obProductInfoMapper.selectByExample(example);
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
		// 获取查看标识--为了区别不同角色查看的信息不同
		String flag = request.getParameter("flag");
		// 获取标题id
		String titleId = request.getParameter("id");
		Map<String, Object> map = obSupplierQuoteService.findQuoteInfo(titleId);
		// 竞价信息
		OBProject obProject = (OBProject) map.get("obProject");
		// 竞价商品信息
		Object object = map.get("oBProductInfoList");
		// 获取采购机构名称
		String orgName = (String) map.get("orgName");
		String demandUnit = (String) map.get("demandUnit");
		String productIds = (String) map.get("productIds");
		String transportFees = (String) map.get("transportFees");
		List<UploadFile> uploadFiles = (List<UploadFile>) map
				.get("uploadFiles");
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
		// 保留两位小数
		// DecimalFormat df = new DecimalFormat("0.00");
		//String totalCountPriceBigDecimalStr = df.format(totalCountPriceBigDecimal);
		
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
		model.addAttribute("uploadFiles", uploadFiles);
		model.addAttribute("totalCountPriceBigDecimal", totalCountPriceBigDecimalStr);
		
		// 封装文件下载项
		model.addAttribute("fileid", obProject.getAttachmentId());
		model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
		model.addAttribute("typeId",DictionaryDataUtil.getId("BIDD_INFO_MANAGE_ANNEX"));
		// 供应商查看竞价未开始、已流拍状态
		if(StringUtils.isNotEmpty(flag) && "1".equals(flag)){
			return "bss/ob/supplier/findBiddingIssueInfo";
		}
		return "bss/ob/biddingSpectacular/findBiddingIssueInfo";
	}
	
    /**
     * 
     * Description: 查询竞价信息结果
     * 
     * @author  zhang shubin
     * @version  2017年3月30日 
     * @param  @param model
     * @param  @param request
     * @param  @return 
     * @return String 
     * @exception
     */
    @RequestMapping("selInfo")
    public String selInfo(Model model, HttpServletRequest request){
    	String projectId = request.getParameter("id") == null ? "" : request.getParameter("id");
    	List<OBResultSubtabulation> list = obResultSubtabulationService.selectByProjectId(projectId);
    	Integer countProportion = 0;
    	if(list != null){
    		for (OBResultSubtabulation obr : list) {
    			if(obr != null){
    				OBProjectResult projectResult = oBProjectResultService.selectByPrimaryKey(obr.getProjectResultId());
    				if(projectResult != null){
    					countProportion += Integer.parseInt(projectResult.getProportion());
    					obr.setProportion(Integer.parseInt(projectResult.getProportion()));
    					obr.setStatus(projectResult.getStatus());
    					obr.setRanking(projectResult.getRanking());
    				}
    			}
        	}
    	}
    	String projectName = OBProjectServer.selectByPrimaryKey(projectId).getName();
    	Collections.sort(list);
    	model.addAttribute("list", list);
    	model.addAttribute("countProportion",countProportion);
    	model.addAttribute("projectName",projectName);
    	model.addAttribute("size",list.size());
    	return "bss/ob/biddingSpectacular/result";
    }
	
}
