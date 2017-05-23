package bss.controller.ob;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ses.model.bms.Category;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.PathUtil;
import bss.model.ob.OBProduct;
import bss.model.ob.OBProductInfo;
import bss.model.ob.OBProject;
import bss.model.ob.OBProjectResult;
import bss.model.ob.OBProjectRule;
import bss.model.ob.OBProjectSupplier;
import bss.model.ob.OBResultSubtabulation;
import bss.model.ob.OBResultsInfo;
import bss.model.ob.OBRule;
import bss.model.ob.OBSupplier;
import bss.model.pms.PurchaseRequired;
import bss.service.ob.OBProductInfoServer;
import bss.service.ob.OBProductService;
import bss.service.ob.OBProjectResultService;
import bss.service.ob.OBProjectServer;
import bss.service.ob.OBResultSubtabulationService;
import bss.service.ob.OBRuleService;
import bss.service.ob.OBSupplierQuoteService;
import bss.service.ob.OBSupplierService;
import bss.util.BigDecimalUtils;
import bss.util.CheckUtil;
import bss.util.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;
import common.annotation.SystemControllerLog;
import common.annotation.SystemServiceLog;
import common.constant.Constant;
import common.constant.StaticVariables;
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
	private OBProductService oBProductService;
	
	@Autowired
	private OBSupplierService obSupplierService;

	@Autowired
	private OrgnizationServiceI orgnizationService;

	@Autowired
	private DictionaryDataServiceI dictionaryDataService;
	
	@Autowired
	private OBProjectResultService oBProjectResultService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private OBSupplierQuoteService obSupplierQuoteService;
	
	/**竞价 规则**/
	@Autowired
	private OBRuleService obRuleService;
	/**竞价 关联 产品**/
	@Autowired
	private OBProductInfoServer oBProductInfoService;
	@Autowired
	private OBProjectResultService obProjectResultService;
    
	/**用户**/
	@Autowired
	private UserServiceI userService;
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
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public String list(@CurrentUser User user, Model model,
			Integer page,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date startTime,
			String name) {
		    if(page == null){
			  page=1;
		    }
		    //定义 页面传值 判断 是否有权限 0：操作有效 2 无效
		    int orgId=2;
		    if (user != null) {
			//竞价信息管理，权限所属角色是：需求部门，查看范围是：本部门，操作范围是 ：本部门，权限属性是：操作。
		    if("0".equals(user.getTypeName())){
				orgId=0;
			//获取需求部门用户id 集合	
			List<String> userList=userService.findListByTypeId(user.getTypeName());
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("page", page);
			map.put("startTime", startTime);
			if(name != null){
				name=name.trim();
			 }
			map.put("name", name);
			map.put("createId", userList);
			List<OBProject> list = OBProjectServer.List(map);
			PageInfo<OBProject> info = new PageInfo<OBProject>(list);
			model.addAttribute("info", info);
			model.addAttribute("name",name);
			model.addAttribute("startTime",startTime);
			  }
		   }
		   model.addAttribute("orgId",orgId);
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
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public String supplierList(@CurrentUser User user, Model model,
			HttpServletRequest request,@RequestParam(defaultValue="1") Integer page,String obProjectId,
			String name,String status,String result) {
		if (user != null) {
			//根据参数获取供应商信息
			List<OBSupplier> lists = OBProjectServer.supplierList(page,obProjectId,
					 name, status,result);
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
						//组合采购目录数据
						List<Category> clist = categoryService.findCategoryByParentNode(map);
						String str = "";
						for (Category category : clist) {
							if(!obSupplier.getSmallPoints().getName().equals(category.getName())){
								str += category.getName() +"/";
							}
							
						}
						if(obSupplier.getSmallPoints() != null){
							str+=obSupplier.getSmallPoints().getName();
							obSupplier.setPointsName(str);
						}
					}
				}
			}
			model.addAttribute("info", new PageInfo<OBSupplier>(lists));
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
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
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
			String smallPointsId = null;
			if(obProjectId != null){
				List<OBProjectSupplier> listps = obSupplierQuoteService.selByProjectId(obProjectId);
				if(listps != null && listps.size() > 0){
					smallPointsId = listps.get(0).getSupplierPrimaryId();
				}
			}
			map1.put("smallPointsId", smallPointsId);
			List<OBSupplier> list = obSupplierService.selOfferSupplier(map1);
			model.addAttribute("info", new PageInfo<OBSupplier>(list));
			model.addAttribute("obProjectId",obProjectId);
			model.addAttribute("name",name);
			model.addAttribute("obProjectId",obProjectId);
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
						if(obSupplier.getSmallPoints() != null){
							str+=obSupplier.getSmallPoints().getName();
							obSupplier.setPointsName(str);
						}
					}
				}
		}
		return "bss/ob/biddingInformation/offerSupplier";
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
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public String addBidding(@CurrentUser User user, Model model,
			HttpServletRequest request) {
	    //定义 页面传值 判断 是否有权限 0：操作有效 2 无效
	   int authType=2;
       if(user!=null){
    	 //竞价信息管理，权限所属角色是：需求部门，查看范围是：本部门，操作范围是 ：本部门，权限属性是：操作。
		 if("0".equals(user.getTypeName())){
			 authType=0;
		// 获取当前 默认规则
		 OBRule obRule = obRuleService.selectByStatus();
		 if(obRule==null){
			 model.addAttribute("supplierCount",null);
		 }else{
			 model.addAttribute("supplierCount",obRule.getLeastSupplierNum());
		 }
		// 生成ID
		String uuid = UUID.randomUUID().toString().toUpperCase()
				.replace("-", "");
		model.addAttribute("ruleId",obRule.getId());
		model.addAttribute("fileid", uuid);
		model.addAttribute("userId", user.getId());
		model.addAttribute("obRule",obRule);
		model.addAttribute("sysKey", Constant.OB_PROJECT_SYS_KEY);
		// 标识 竞价附件
		model.addAttribute("typeId",DictionaryDataUtil.getId("BIDD_INFO_MANAGE_ANNEX"));
		   }
		}
		model.addAttribute("type", 2);
		model.addAttribute("authType", authType);
		return "bss/ob/biddingInformation/publish";
	}

	/**
	 * 获取可用的采购机构 信息 并返回页面
	 * 
	 * @author YangHongLiang
	 * @throws IOException
	 */
	@RequestMapping("mechanism")
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
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
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
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
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public String biddingInfoList(@CurrentUser User user, Model model,
			HttpServletRequest request,@RequestParam(defaultValue="1") Integer page,@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date startTimeStr,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date endTimeStr) throws ParseException {
		String authType=null;
		if(user!= null){
			//判断是否 是资源服务中心 
		if("4".equals(user.getTypeName())){
			authType=user.getTypeName();
		// 竞价标题
		String name = request.getParameter("name");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("startTime", startTimeStr);
		map.put("endTime", endTimeStr);
		map.put("page", page);
		List<OBProject> list = OBProjectServer.selectAllOBproject(map);
		// 封装分页信息
		PageInfo<OBProject> info = new PageInfo<OBProject>(list);
		// 将查询信息封装到model域中
		model.addAttribute("info", info);
		model.addAttribute("name", name);
		model.addAttribute("startTimeStr", startTimeStr);
		model.addAttribute("endTimeStr", endTimeStr);
		  }
		}
		model.addAttribute("authType", authType);
		return "bss/ob/biddingSpectacular/list";
	}
	/**
	 * 更新一个竞价信息的业务逻辑
	 * @param request
	 * @param projectId
	 *  #内外网判定 1外网 0内网
	 */
	@RequestMapping("/changeStatus")
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
    public void changeStatus( HttpServletRequest request,String projectId){
			 //获取是否内网标识 1外网 0内网
		// String ipAddressType= PropUtil.getProperty("ipAddressType");
			//if("1".equals(ipAddressType)){
			OBProjectServer.changeStatus(projectId);
		//}
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
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
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
			List<OBProjectResult> resultList=obProjectResultService.selectByPID(id);
			List<OBProductInfo> plist=oBProductInfoService.getProductName(id);
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
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public void getProduct(@CurrentUser User user, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			Map<String ,Object> map = new HashMap<String, Object>();
			String ids = request.getParameter("ids") == null ? "" : request.getParameter("ids").trim();
			String[] split = ids.split(",");
			List<String> list = new ArrayList<String>();
			if(split.length > 0){
				for (String string : split) {
					if(!("".equals(string)))
					list.add(string);
				}
			}
			int size = 0;
			if(list != null){
				size = list.size();
			}
			String smallPointsId = null;
			if(split.length > 0){
				OBProduct obProduct = oBProductService.selectByPrimaryKey(split[0]);
				if(obProduct != null){
					smallPointsId = obProduct.getSmallPointsId();
				}
			}
			map.put("list", list);
			map.put("size", size);
			map.put("smallPointsId", smallPointsId);
			String json = OBProjectServer.getProduct(map);
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
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public ResponseEntity<byte[]> download(HttpServletRequest request,
			String filename) throws IOException {
		String path = PathUtil.getWebRoot() + "excel/定型产品.xls";
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
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public String addProject(@CurrentUser User user, OBProject obProject,
			HttpServletRequest request, String fileid) {
		String msg = null;
		if (user != null) {
			 //竞价信息管理，权限所属角色是：需求部门，查看范围是：本部门，操作范围是 ：本部门，权限属性是：操作。
			if("0".equals(user.getTypeName())){
			msg = OBProjectServer.saveProject(obProject, user.getId(), fileid);
			}
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
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
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
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public String checkCatalog(HttpServletRequest request, HttpServletResponse response,@RequestBody List<String> productid){
	  return OBProjectServer.verifyCatalog(productid);
	}
	/** @Description: 编辑暂存的竞价信息
	* @param  OBProject
	* @return     
	* @return String     
    * @throws IOException 
	* @throws Exception
	*/
	@RequestMapping(value="/editOBProject", produces="text/html;charset=UTF-8" )
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public String editOBProject(@CurrentUser User user,Model model, HttpServletRequest request,String obProjectId,String status){
		if(user !=null){
			 //竞价信息管理，权限所属角色是：需求部门，查看范围是：本部门，操作范围是 ：本部门，权限属性是：操作。
			if("0".equals(user.getTypeName())){
			if(StringUtils.isNotBlank(obProjectId)){
			Map<String,Object> map=new HashMap<String, Object>();	
			map.put("id", obProjectId);
			//map.put("userId", user.getId());
			OBProject obProject=OBProjectServer.editOBProject(map);
			if(obProject !=null){
				 List<OBProductInfo> obProductInfo=oBProductInfoService.selectByProjectId(obProject.getId());
				 obProject.setObProductInfo(obProductInfo);
				 
				//竞价规则
				OBProjectRule oRule= obRuleService.selectByPrimaryKey(obProject.getId());
				model.addAttribute("obRule", oRule);
				// 生成ID
				model.addAttribute("ruleId", obProject.getRuleId());
				model.addAttribute("userId", user.getId());
				model.addAttribute("sysKey", Constant.OB_PROJECT_SYS_KEY);
				// 标识 竞价附件
				model.addAttribute("typeId",DictionaryDataUtil.getId("BIDD_INFO_MANAGE_ANNEX"));
				model.addAttribute("list", obProject);
				model.addAttribute("listinfo", obProject.getObProductInfo());
				model.addAttribute("fileid", obProject.getAttachmentId());
				if(obProject.getStatus()==3||obProject.getStatus()==4){
				//查找 参与这个标题的供应商(里面封装有供应商所竞价的商品部分信息)
				List<OBProjectResult> resultList=obProjectResultService.selectByPID(obProject.getId());
				List<OBProductInfo> plist=oBProductInfoService.getProductName(obProject.getId());
				for(OBProjectResult s:resultList){
					if(s.getStatus()==-1){
						Integer second= obProjectResultService.countByBidding(obProject.getId(), "1", null);
						String bidding=null;
						if(second>0){
							bidding="1";
						}else{
							bidding="2";
						}
				List<OBResultsInfo> infoList=obProjectResultService.getProductInfo(obProject.getId(),s.getSupplierId(),bidding);
						s.setOBResultsInfo(infoList);
					}else{
						s.setProductInfo(plist);
					}
				}
				model.addAttribute("selectInfoByPID", resultList);
				}
				//查询参与的供应商==============================================================================================
				if(obProjectId != null){
					List<String> biddingIdList = obProjectResultService.isSecondBidding(obProjectId);
					Boolean flag = true;
					if(biddingIdList != null && biddingIdList.size() > 0){
						for (String string : biddingIdList) {
							if("2".equals(string)){
								flag = false;
							}
						}
					}
					List<OBProjectResult> listss = oBProjectResultService.selResultByProjectId(obProjectId);
			    	Integer countProportion = 0;
			    	BigDecimal million = new BigDecimal(10000);
			    	if(listss != null && listss.size() > 0){
			    		for (OBProjectResult obProjectResult : listss) {
							if(obProjectResult != null){
								if(obProjectResult.getStatus() == 1 || obProjectResult.getStatus() == 2){
									obProjectResult.setStatus(1);
									List<OBProjectResult> prolist = oBProjectResultService.selProportion(obProjectId, obProjectResult.getSupplierId());
									if(prolist != null && prolist.size() == 1){
										obProjectResult.setFirstproportion(prolist.get(0).getProportion());
									}
									if(prolist != null && prolist.size() == 2){
										obProjectResult.setFirstproportion(prolist.get(0).getProportion());
										obProjectResult.setSecondproportion(prolist.get(1).getProportion());
									}
									List<OBResultSubtabulation> obResultSubtabulation = obResultSubtabulationService.selectByProjectIdAndSupplierId(obProjectId, obProjectResult.getSupplierId());
									if(obResultSubtabulation != null && obResultSubtabulation.size() > 0){
										for (OBResultSubtabulation obResultSubtabulation2 : obResultSubtabulation) {
											if(obResultSubtabulation2 != null){
												obResultSubtabulation2.setTotalMoney(obResultSubtabulation2.getTotalMoney().divide(million));
											}
										}
									}
									
									obProjectResult.setObResultSubtabulation(obResultSubtabulation);
									countProportion += Integer.parseInt(obProjectResult.getProportion());
									List<OBResultsInfo> listinf = oBProjectResultService.selectResult(obProjectId, obProjectResult.getSupplierId());
									obProjectResult.setOBResultsInfo(listinf);
								}else{
									List<OBResultsInfo> listinf = oBProjectResultService.selectResult(obProjectId, obProjectResult.getSupplierId());
									obProjectResult.setOBResultsInfo(listinf);
								}
							}
						}
			    	}
					OBProject obProjectww = OBProjectServer.selectByPrimaryKey(obProjectId);
			    	if(obProjectww != null){
			    		String projectName = obProjectww.getName();
			    		model.addAttribute("projectName",projectName);
			    	}
			    	model.addAttribute("flag", flag);
					model.addAttribute("listres", listss);
					model.addAttribute("countProportion",countProportion);
					model.addAttribute("size",listss.size());
				}
				if(StringUtils.isNotBlank(status)){
					model.addAttribute("type", "1");
					return "bss/ob/biddingInformation/editPublish";
				}else{
				if(obProject.getStatus()==0){
					model.addAttribute("orgId", 0);
					model.addAttribute("type", "2");
					return "bss/ob/biddingInformation/publish";
				}else{
					model.addAttribute("type", "1");
					return "bss/ob/biddingInformation/editPublish";
				 }
			   }
			 }
			}
			}
		}
		
		model.addAttribute("type", "1");
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
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public String delOBProject(@CurrentUser User user,Model model, HttpServletRequest request,String obProjectId,String status){
		if(user !=null){
		  //竞价信息管理，权限所属角色是：需求部门，查看范围是：本部门，操作范围是 ：本部门，权限属性是：操作。
		 if("0".equals(user.getTypeName())){
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
		}
		return list(user, model,  1, null, "");
	}
	
	
	/**
	 * @Description:根据 供应商数量 获取相对应的成交比例
	 * @author: YangHongLiang
	 * @param @return
	 * @return String
	 * @throws IOException 
	 */
	@RequestMapping("proportion")
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
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
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
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
	 * @throws UnsupportedEncodingException 
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
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	public String printResult(Model model, HttpServletRequest request,HttpServletResponse response,
			@RequestParam(defaultValue="1")Integer page) throws UnsupportedEncodingException {
		// 获取打印结果标识
		String print = request.getParameter("print");
		// 获取竞价标题的id
		String projectId = request.getParameter("id") == null ? "" : request
				.getParameter("id");
		
		Map<String, Object> map = OBProjectServer.findBiddingInfo(projectId);
		/*************************************竞价信息****************************************/
		OBProject obProject = (OBProject) map.get("obProject");
		// 获取采购机构名称
		String orgName = (String) map.get("orgName");
		String demandUnit = (String) map.get("demandUnit");
		String transportFees = (String) map.get("transportFees");
		//获取竞价产品信息
		List<OBProductInfo> obProductInfoList= oBProductInfoService.selectByProjectId(projectId);
		model.addAttribute("obProductInfoList", obProductInfoList);
		// 采购机构
		model.addAttribute("orgName", orgName);
		// 需求单位
		model.addAttribute("demandUnit", demandUnit);
		// 运杂费
		model.addAttribute("transportFees", transportFees);
		model.addAttribute("obProject", obProject);
		
		/*************************************竞价结果信息****************************************/
		BiddingResultCommon.getBiddingResultInfo(model, projectId, oBProjectResultService, obResultSubtabulationService);
		
		if (StringUtils.isNotEmpty(print)) {
			request.setAttribute("projectName",obProject.getName()+"_竞价结果信息表");
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
	@SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
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
		//NumberFormat currency = NumberFormat.getNumberInstance();
		//currency.setMinimumIntegerDigits(2);//设置数的小数部分所允许的最小位数(如果不足后面补0) 
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
					// 单位换算成万元
					BigDecimal moneyBigDecimal = BigDecimalUtils.getSignalDecimalScale4(multiply, million);
					/**显示100000样式**/
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
		// 保留两位小数
		// DecimalFormat df = new DecimalFormat("0.00");
		//String totalCountPriceBigDecimalStr = df.format(totalCountPriceBigDecimal);
//		String totalCountPriceBigDecimalStr = currency.format(totalCountPriceBigDecimal);
		BigDecimal bigDecimal = new BigDecimal(totalCountPriceBigDecimal);
		BigDecimal totalCountPriceBigDecimalAfter = BigDecimalUtils.getBigDecimalTOScale4(bigDecimal, million);
		
		
		//竞价规则
		OBProjectRule oRule= obRuleService.selectByPrimaryKey(obProject.getId());
		model.addAttribute("obRule", oRule);
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
		model.addAttribute("totalCountPriceBigDecimal", totalCountPriceBigDecimalAfter);
		
		// 封装文件下载项
		model.addAttribute("fileid", obProject.getAttachmentId());
		model.addAttribute("sysKey", Constant.OB_PROJECT_SYS_KEY);
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
    @SystemControllerLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OB_PROJECT_NAME,operType=StaticVariables.OB_PROJECT_NAME_SIGN)
    public String selInfo(Model model, HttpServletRequest request){
    	String projectId = request.getParameter("id") == null ? "" : request.getParameter("id");
    	// 调用获取竞价结果信息
    	BiddingResultCommon.getBiddingResultInfo(model, projectId,  oBProjectResultService, obResultSubtabulationService);
    	OBProject ob=OBProjectServer.selectByPrimaryKey(projectId);
    	if(ob!=null){
    		model.addAttribute("projectName", ob.getName());
    	}
    	return "bss/ob/biddingSpectacular/result";
    }
	
}
