package bss.controller.pms;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ses.dao.bms.DictionaryDataMapper;
import ses.dao.oms.OrgnizationMapper;
import ses.model.bms.Category;
import ses.model.bms.DictionaryData;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseOrg;
import ses.model.sms.Supplier;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.PathUtil;
import ses.util.PropUtil;
import bss.controller.base.BaseController;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.PurchaseManagement;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.PurchaseManagementService;
import bss.service.pms.PurchaseRequiredService;
import bss.util.ExcelUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.annotation.SystemControllerLog;
import common.constant.StaticVariables;
import common.model.UploadFile;
import common.service.UpdateHistoryService;
import common.service.UploadService;

/**
 * 
 * @Title: PurcharseRequiredController
 * @Description: 采购需求计划类
 * @author Li Xiaoxiao
 * @date 2016年9月12日,下午1:54:34
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/purchaser")
public class PurchaseRequiredController extends BaseController {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PurchaseRequiredService purchaseRequiredService;

	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;

	@Autowired
	private OrgnizationMapper oargnizationMapper;

	@Autowired
	private DictionaryDataMapper dictionaryDataMapper;

	@Autowired
	private PurchaseOrgnizationServiceI purchserOrgnaztionService;

	@Autowired
	private OrgnizationServiceI orgnizationServiceI;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private PurchaseManagementService purchaseManagementService;

	@Autowired
	private UpdateHistoryService updateHistoryService;

	@Autowired
	private UploadService uploadService;

	/**
	 * 
	 * @Title: queryPlan @Description: 条件查询分页 author: Li Xiaoxiao @param @param
	 * purchaseRequired @param @return @return String @throws
	 */
	@RequestMapping("/list")
	public String queryPlan(@CurrentUser User user, PurchaseRequired purchaseRequired, Integer page, Model model) {
		purchaseRequired.setIsMaster(1);

		if (purchaseRequired.getStatus() == null) {
			// purchaseRequired.setStatus("1");
			purchaseRequired.setStatus("total");
		}

		else if (purchaseRequired.getStatus().equals("5")) {
			purchaseRequired.setSign("5");
		}
		if (purchaseRequired.getStatus().equals("total")) {
			purchaseRequired.setStatus(null);
		}
		if (page == null) {
			page = StaticVariables.DEFAULT_PAGE;
		}
		List<Role> roles = user.getRoles();
		boolean bool = false;
		if (roles != null && roles.size() > 0) {
			for (Role r : roles) {
				if (r.getCode().equals("NEED_M")) {
					bool = true;
				}
			}
		}
		if (bool != true) {
			purchaseRequired.setUserId(user.getId());
		}
		List<PurchaseRequired> list = purchaseRequiredService.query(purchaseRequired, page);
		model.addAttribute("info", new PageInfo<PurchaseRequired>(list));
		model.addAttribute("inf", purchaseRequired);

		Map<String, Object> map = new HashMap<String, Object>();
		List<Orgnization> requires = oargnizationMapper.findOrgPartByParam(map);
		model.addAttribute("requires", requires);

		// HashMap<String, Object> maps = new HashMap<String, Object>();
		// maps.put("typeName", StaticVariables.ORG_TYPE_MANAGE);
		// List<Orgnization> manages =
		// orgnizationServiceI.findOrgnizationList(maps);
		List<PurchaseOrg> manages = purchserOrgnaztionService.get(user.getOrg().getId());
		// model.addAttribute("manages", manages);
		model.addAttribute("manages", manages.size());
		return "bss/pms/purchaserequird/list";
	}

	/**
	 * 
	 * @Title: getById @Description: 根据计划编号查询明细 author: Li
	 * Xiaoxiao @param @return @return String @throws
	 */
	@RequestMapping("/queryByNo")
	public String getById(@CurrentUser User user, String planNo, Model model, String type) {
		PurchaseRequired p = new PurchaseRequired();
		p.setUniqueId(planNo.trim());
		List<PurchaseRequired> list = purchaseRequiredService.queryUnique(p);
		model.addAttribute("kind", DictionaryDataUtil.find(5));// 获取数据字典数据
		model.addAttribute("list", list);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("typeName", "1");
		List<PurchaseDep> list2 = purchserOrgnaztionService.findPurchaseDepList(map);
		model.addAttribute("requires", list2);
		model.addAttribute("types", DictionaryDataUtil.find(6));
		String fileId = list.get(0).getFileId();
		String typeId = DictionaryDataUtil.getId("PURCHASE_FILE");
		model.addAttribute("typeId", typeId);
		model.addAttribute("fileId", fileId);
		model.addAttribute("planNo", planNo);
		model.addAttribute("detailId", DictionaryDataUtil.getId("PURCHASE_DETAIL"));
		model.addAttribute("org_advice", type);
		model.addAttribute("uniqueId", planNo.trim());
		if (type.equals("1") || type.equals("2")) {
			return "bss/pms/purchaserequird/view";
		} else {
			model.addAttribute("listSize", list.size());
			return "bss/pms/purchaserequird/edit";
		}

	}

	/**
	 * @throws IOException
	 * 
	 * @Title: updateById @Description: 根据id修改 author: Li
	 * Xiaoxiao @param @return @return String @throws
	 */
	@RequestMapping("/update")
	public String updateById(PurchaseRequiredFormBean list, String planName, String planNo, String referenceNo,
			String planType, String mobile, String history, Integer enterPort, HttpServletRequest request)
			throws IOException {

		if (list != null) {
			if (list.getList() != null && list.getList().size() > 0) {
				for (PurchaseRequired p : list.getList()) {
					if (p.getId() != null) {
						p.setPlanType(planType);
						p.setPlanNo(planNo);
						p.setPlanName(planName);
						p.setReferenceNo(referenceNo);
						p.setRecorderMobile(mobile);
						p.setEnterPort(enterPort);
						purchaseRequiredService.updateByPrimaryKeySelective(p);
					}

				}
			}
		}

		String[] ids = history.split(",");
		Set<String> set = new HashSet<String>();
		for (String i : ids) {
			if (i.trim().length() != 0) {
				set.add(i);
			}
		}
		for (String str : set) {
			PurchaseRequired obj = purchaseRequiredService.queryById(str);
			updateHistoryService.add(str, obj);
		}
		return "redirect:list.html";
	}

	/**
	 * 
	 * 
	 * @Title: add @Description: 添加跳转页面 author: Li Xiaoxiao @param @return @return
	 * String @throws
	 */
	@RequestMapping("/add")
	public String add(@CurrentUser User user, Model model, String type) {
		model.addAttribute("user", user);
		model.addAttribute("list", DictionaryDataUtil.find(6));
		model.addAttribute("list2", DictionaryDataUtil.find(5));
		// Map<String,Object> map=new HashMap<String,Object>();
		// List<Orgnization> requires = oargnizationMapper.findOrgPartByParam(map);
		// model.addAttribute("requires",requires);
		if (user.getOrg() != null) {
			Orgnization orgnization = orgnizationServiceI.getOrgByPrimaryKey(user.getOrg().getId());

			model.addAttribute("orgName", orgnization.getShortName());
			model.addAttribute("orgType", user.getOrg().getTypeName());
		}

		String typeId = DictionaryDataUtil.getId("PURCHASE_DETAIL");

		String fileId = UUID.randomUUID().toString().replaceAll("-", "");
		model.addAttribute("fileId", fileId);
		model.addAttribute("typeId", typeId);
		model.addAttribute("planNo", randomPlano());
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		model.addAttribute("id", id);

		String uid = UUID.randomUUID().toString().replaceAll("-", "");
		model.addAttribute("uid", uid);
		/*
		 * List<Supplier> suppliers = purchaseRequiredService.queryAllSupplier();
		 * model.addAttribute("suppliers", suppliers);
		 */
		return "bss/pms/purchaserequird/add";
	}

	@RequestMapping("/fileUpload")
	public String uploadPage() {
		return "/bss/pms/purchaserequird/fileUpload";
	}

	/**
	 * @Title: uploadFile
	 * @Description: 导入excel表格数据 author: Li Xiaoxiao
	 * @param @return
	 * @return String
	 * @throws IOException
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/upload", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String uploadFile(@CurrentUser User user, String planDepName, MultipartFile file, String type, String planName,
			String planNo, Model model) throws Exception {
		String fileName = file.getOriginalFilename();
		if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
			return "1";
		}
		String mark = "";

		try {
			WorkbookFactory.create(file.getInputStream());
		} catch (Exception e) {
			mark = e.getMessage();
		}

		if (!StringUtils.isBlank(mark)) {
			return "0";
		}
		List<PurchaseRequired> list = new ArrayList<PurchaseRequired>();
		if (planNo != null) {
			PurchaseRequired purchaseRequired = new PurchaseRequired();
			purchaseRequired.setUniqueId(planNo.trim());
			list = purchaseRequiredService.queryUnique(purchaseRequired);
		}

		// ExcelUtil util=new ExcelUtil();
		Map<String, Object> maps = (Map<String, Object>) ExcelUtil.readExcel(file);
		list.addAll((List<PurchaseRequired>) maps.get("list"));

		String errMsg = (String) maps.get("errMsg");

		if (errMsg != null) {
			String jsonString = JSON.toJSONString(errMsg);
			return jsonString;
		}
		if (list.size() < 2) {
			return "2";
		}

		if (!list.get(0).getSeq().matches("[\u4E00-\u9FA5]")) {
			return "3";
		}
		if (list.get(0).getSeq().matches("[\u4E00-\u9FA5]") && list.get(0).getPurchaseCount() != null) {
			return "4";
		}
		// if(!list.get(0).getSeq().matches("[\u4E00-\u9FA5]")){
		// return "3";
		// }

		/*
		 * if(!user.getOrg().getName().equals(list.get(0).getDepartment())){ return
		 * "2"; }
		 */

		String id = UUID.randomUUID().toString().replaceAll("-", "");
		String pid = UUID.randomUUID().toString().replaceAll("-", "");
		String cid = UUID.randomUUID().toString().replaceAll("-", "");
		String ccid = UUID.randomUUID().toString().replaceAll("-", "");
		String cccid = UUID.randomUUID().toString().replaceAll("-", "");
		String ccccid = UUID.randomUUID().toString().replaceAll("-", "");
		String plano = randomPlano();

		int len = list.size() - 1;
		StringBuffer sbUp = new StringBuffer("");
		StringBuffer sbShow = new StringBuffer("");
		int count = 1;
		// BigDecimal budget=BigDecimal.ZERO;

		for (int i = 0; i < list.size(); i++) {
			// String id = UUID.randomUUID().toString().replaceAll("-", "");

			PurchaseRequired p = list.get(i);
			p.setPlanNo(plano);
			if (p.getPlanName() != null) {

			} else {
				p.setPlanName(planName);
			}
			p.setPlanType(type);
			p.setHistoryStatus("0");
			p.setIsDelete(0);
			p.setIsMaster(count);
			p.setCreatedAt(new Date());
			p.setUserId(user.getId());
			/*
			 * if(p.getPurchaseType()!=null){ DictionaryData data =
			 * dictionaryDataMapper.queryByName(p.getPurchaseType());
			 * p.setPurchaseType(data.getId()); }
			 */

			// p.setOrganization(user.getOrg().getName());
			p.setDetailStatus(1);

			// if(p.getBudget()!=null){
			// budget=budget.add(p.getBudget());
			// }
			// 顶级节点
			if (p.getSeq().matches("[\u4E00-\u9FA5]") && !p.getSeq().contains("（")) {
				p.setSeq("一");
				count = 1;
				p.setIsMaster(count);

				p.setParentId("1");// 注释
				plano = randomPlano();
				p.setPlanNo(plano);
				if (p.getId() != null) {
					id = p.getId();
				} else {
					id = UUID.randomUUID().toString().replaceAll("-", "");// 重新给顶级id赋值
				}
				p.setId(id);// 注释
				count++;
				PurchaseRequired pr = list.get(i + 1);
				if (pr != null) {
					if (!isContainChinese(pr.getSeq())) {
						errMsg = String.valueOf(i + 4) + "行，节点错误";
						break;
					}
				}
				continue;
			}
			// 判断是否是二级节点(一)
			if (isContainChinese(p.getSeq())) {

				p.setParentId(id);
				if (p.getId() != null) {
					pid = p.getId();
				} else {
					pid = UUID.randomUUID().toString().replaceAll("-", "");// 重新给顶级id赋值
				}
				p.setId(pid);
				count++;
				PurchaseRequired pr = list.get(i + 1);
				if (pr != null) {
					if (!pr.getSeq().equals("1") && !isContainChinese(p.getSeq())) {
						errMsg = String.valueOf(i + 4) + "行，节点错误";
						break;
					}
				}
				continue;
			}

			// 判断是否是三级节点1,2,3
			else if (isInteger(p.getSeq())) {

				p.setParentId(pid);
				if (p.getId() != null) {
					cid = p.getId();
				} else {
					cid = UUID.randomUUID().toString().replaceAll("-", "");// 重新给顶级id赋值
				}
				p.setId(cid);
				count++;
				continue;
			}

			// 判断是否四级节点(1),(2)
			else if (isContainIntger(p.getSeq())) {

				p.setParentId(cid);
				if (p.getId() != null) {
					ccid = p.getId();
				} else {
					ccid = UUID.randomUUID().toString().replaceAll("-", "");// 重新给顶级id赋值
				}
				p.setId(ccid);
				count++;
				continue;
			}
			// 五级节点
			else if (isEng(p.getSeq())) {
				p.setId(cccid);
				if (p.getId() != null) {
					cccid = p.getId();
				} else {
					cccid = UUID.randomUUID().toString().replaceAll("-", "");// 重新给顶级id赋值
				}
				p.setParentId(ccid);
				count++;
				continue;
			} else {
				p.setId(ccccid);
				ccccid = UUID.randomUUID().toString().replaceAll("-", "");// 重新给顶级cid赋值
				p.setParentId(cccid);
				count++;
			}

			// count++;

			sbUp.append("pUp" + i + ",");
			sbShow.append("pShow" + i + ",");
			if (len == i) {
				sbUp.append("pUp" + i);
				sbShow.append("pShow" + i);
			}
		}
		if (errMsg != null) {
			String jsonString = JSON.toJSONString(errMsg);
			return jsonString;
		}
		String jsonString = JSON.toJSONString(list);
		// purchaseRequiredService.batchAdd(list);

		return jsonString;
	}

	/**
	 * @throws IOException
	 * 
	 * @Title: addReq @Description: 添加数据 author: Li Xiaoxiao @param @param
	 * purchaseRequired @param @return @return String @throws
	 */
	@RequestMapping("/adddetail")
	@ResponseBody
	public String addReq(PurchaseRequiredFormBean list, String planType, String planNo, String planName,
			String recorderMobile, HttpServletRequest request, String referenceNo, String fileId, String prList,
			Integer enterPort) throws IOException {
		List<PurchaseRequired> plist = get(prList);

		User user = (User) request.getSession().getAttribute("loginUser");
		// List<PurchaseRequired> plist = list.getList();

		String id = UUID.randomUUID().toString().replaceAll("-", "");
		String pid = UUID.randomUUID().toString().replaceAll("-", "");
		String cid = UUID.randomUUID().toString().replaceAll("-", "");
		String ccid = UUID.randomUUID().toString().replaceAll("-", "");
		String cccid = UUID.randomUUID().toString().replaceAll("-", "");
		String ccccid = UUID.randomUUID().toString().replaceAll("-", "");
		String unqueId = UUID.randomUUID().toString().replaceAll("-", "");
		int count = 1;
		if (list != null) {
			if (plist != null && plist.size() > 0) {
				for (int i = 0; i < plist.size(); i++) {

					PurchaseRequired p = plist.get(i);
					p.setPlanNo(planNo);
					/*
					 * if(p.getPlanName()!=null){
					 * 
					 * }else{
					 */
					p.setPlanName(planName);
					// }
					p.setPlanType(planType);
					p.setHistoryStatus("0");
					p.setIsDelete(0);
					p.setIsMaster(count);
					p.setCreatedAt(new Date());
					p.setUserId(user.getId());
					p.setRecorderMobile(recorderMobile);
					p.setProjectStatus(0);
					p.setAdvancedStatus(0);
					p.setIsDelete(0);
					p.setReferenceNo(referenceNo);
					p.setDetailStatus(0);
					p.setStatus("1");
					p.setFileId(fileId);
					p.setEnterPort(enterPort);
					if (p.getSeq() != null) {

						if (p.getPurchaseType() != null && p.getPurchaseType().trim().length() != 0) {
							DictionaryData data = dictionaryDataMapper.queryByName(p.getPurchaseType());
							p.setPurchaseType(data.getId());
						}

						if (p.getSeq().matches("[\u4E00-\u9FA5]") && !p.getSeq().contains("（")) {
							p.setSeq("一");
							count = 1;
							p.setIsMaster(count);
							p.setParentId("1");// 注释
							// id = UUID.randomUUID().toString().replaceAll("-",
							// "");//重新给顶级id赋值
							// p.setId(id);//注释

							unqueId = UUID.randomUUID().toString().replaceAll("-", "");
							p.setUniqueId(unqueId);
							count++;
							continue;
						}
						// 判断是否是二级节点(一)
						if (isContainChinese(p.getSeq())) {
							// p.setParentId(id);
							// pid = UUID.randomUUID().toString().replaceAll("-",
							// "");//重新给顶级pid赋值
							// p.setId(pid);
							p.setUniqueId(unqueId);
							count++;
							continue;
						}

						// 判断是否是三级节点1,2,3
						else if (isInteger(p.getSeq())) {

							// p.setParentId(pid);
							// cid = UUID.randomUUID().toString().replaceAll("-",
							// "");//重新给顶级cid赋值
							// p.setId(cid);
							p.setUniqueId(unqueId);
							count++;
							continue;
						}

						// 判断是否四级节点(1),(2)
						else if (isContainIntger(p.getSeq())) {

							// p.setParentId(cid);
							// ccid = UUID.randomUUID().toString().replaceAll("-",
							// "");//重新给顶级cid赋值
							// p.setId(ccid);
							p.setUniqueId(unqueId);
							count++;
							continue;
						}
						// 五级节点
						else if (isEng(p.getSeq())) {
							// p.setId(cccid);
							//
							// cccid = UUID.randomUUID().toString().replaceAll("-",
							// "");//重新给顶级cid赋值
							// p.setParentId(ccid);
							p.setUniqueId(unqueId);
							count++;
							continue;
						} else {
							// p.setId(ccccid);
							// ccccid = UUID.randomUUID().toString().replaceAll("-",
							// "");//重新给顶级cid赋值
							// p.setParentId(cccid);
							p.setUniqueId(unqueId);
							count++;
						}
					}

				}
			}
		}
		purchaseRequiredService.batchAdd(plist);
		// return "redirect:list.html";
		return "";
	}

	/**
	 * @throws Exception
	 * 
	 * @Title: excel @Description: 根据计划编号导出excel表格 author: Li
	 * Xiaoxiao @param @param planNo @param @return @return String @throws
	 */
	@RequestMapping("/exports")
	@ResponseBody
	public String excel(HttpServletResponse response, String planNo) throws Exception {

		List<PurchaseRequired> list = purchaseRequiredService.getUnique(planNo);

		String filedisplay = "明细.xls";
		response.addHeader("Content-Disposition",
				"attachment;filename=" + new String(filedisplay.getBytes("gb2312"), "iso8859-1"));
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("1");
		HSSFCellStyle style = workbook.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_HAIR);
		style.setBorderLeft(HSSFCellStyle.BORDER_HAIR);
		style.setBorderTop(HSSFCellStyle.BORDER_HAIR);
		style.setBorderRight(HSSFCellStyle.BORDER_HAIR);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));

		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 3200);
		sheet.setColumnWidth(5, 1200);
		sheet.setColumnWidth(6, 2300);
		sheet.setColumnWidth(7, 2300);
		sheet.setColumnWidth(8, 2300);
		sheet.setColumnWidth(9, 2300);
		sheet.setColumnWidth(10, 2500);
		sheet.setColumnWidth(11, 3000);
		sheet.setColumnWidth(12, 3000);

		// 表头第一行
		HSSFRow row = sheet.createRow(0);
		//
		HSSFCell cell = row.createCell(0);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		String planName = list.get(0).getPlanName();
		generateName(workbook, sheet, planName);

		generateHeader(workbook, sheet);

		int count = 2;
		for (PurchaseRequired p : list) {
			row = sheet.createRow(count);
			cell = row.createCell(0);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cell.setCellStyle(style);
			cell.setCellValue(p.getSeq());
			cell = row.createCell(1);
			style.setWrapText(true);
			style.setAlignment(CellStyle.ALIGN_LEFT);
			cell.setCellStyle(style);
			if (p.getPurchaseCount() == null) {
				cell.setCellValue(p.getDepartment());
			}

			cell = row.createCell(2);
			style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			style.setWrapText(true);
			cell.setCellStyle(style);
			cell.setCellValue(p.getGoodsName());
			cell = row.createCell(3);
			style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			style.setWrapText(true);
			cell.setCellStyle(style);
			cell.setCellValue(p.getStand());
			cell = row.createCell(4);
			style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			style.setWrapText(true);
			cell.setCellStyle(style);
			cell.setCellValue(p.getQualitStand());
			cell = row.createCell(5);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setWrapText(true);
			cell.setCellStyle(style);
			cell.setCellValue(p.getItem());
			cell = row.createCell(6);
			style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			cell.setCellStyle(style);
			if (p.getPurchaseCount() != null) {
				double d = p.getPurchaseCount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

				cell.setCellValue(d);
			}

			cell = row.createCell(7);
			style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			cell.setCellStyle(style);
			if (p.getPrice() != null) {
				double price = p.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				cell.setCellValue(price);
			}

			cell = row.createCell(8);
			style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			cell.setCellStyle(style);
			if (p.getBudget() != null) {
				double budget = p.getBudget().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

				cell.setCellValue(budget);
			}

			cell = row.createCell(9);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setWrapText(true);
			cell.setCellStyle(style);
			cell.setCellValue(p.getDeliverDate());

			cell = row.createCell(10);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setWrapText(true);
			cell.setCellStyle(style);
			if (p.getPurchaseCount() != null) {
				DictionaryData dicType = DictionaryDataUtil.findById(p.getPurchaseType());
				if (dicType != null) {
					cell.setCellValue(dicType.getName());
				}
			}

			//
			// cell = row.createCell(11);
			// style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// style.setWrapText(true);
			// cell.setCellStyle(style);
			// if(p.getPurchaseCount()!=null){
			// if(p.getOrganization()!=null){
			// Orgnization orgnization =
			// purchaseRequiredService.queryPur(p.getOrganization());
			// if(orgnization!=null){
			// cell.setCellValue(orgnization.getName());
			// }
			// }
			//
			//
			// }

			//
			cell = row.createCell(11);
			style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			style.setWrapText(true);
			cell.setCellStyle(style);
			cell.setCellValue(p.getSupplier());

			cell = row.createCell(12);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setWrapText(true);
			cell.setCellStyle(style);
			cell.setCellValue(p.getMemo());

			count++;
		}

		ServletOutputStream fileOut = null;
		try {
			filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
			fileOut = response.getOutputStream();
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {

		}
		return "下载成功";
	}

	/**
	 * 
	 * @Title: delete @Description: 逻辑删除数据 author: Li Xiaoxiao @param @param
	 * planNo @param @return @return String @throws
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(HttpServletRequest request) {
		String planNo = request.getParameter("planNo");

		String uniqueId = planNo.trim();
		if (uniqueId.length() != 0) {
			String[] uniqueIds = uniqueId.split(",");
			for (String str : uniqueIds) {
				purchaseRequiredService.updateByUniqueId(str);
			}

		}
		/*
		 * PurchaseRequired p=new PurchaseRequired(); p.setUniqueId(planNo.trim());
		 * List<PurchaseRequired> list = purchaseRequiredService.queryUnique(p);
		 * for(PurchaseRequired pr:list){
		 * purchaseRequiredService.delete(pr.getId()); }
		 */

	}

	/**
	 * 
	 * @Title: downFile @Description: 下载excel表格模板 author: Li
	 * Xiaoxiao @param @param path @param @return @return String @throws
	 */
	@RequestMapping("download")
	public ResponseEntity<byte[]> download(HttpServletRequest request, String filename) throws IOException {
		// filename = new String(filename.getBytes("iso8859-1"),"UTF-8");
		String path = PathUtil.getWebRoot() + "excel/采购需求模板示例.xls";
		File file = new File(path);

		HttpHeaders headers = new HttpHeaders();
		String fileName = new String("模板.xls".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
	}

	/**
	 * 
	 * @Title: submit @Description: 提交 author: Li Xiaoxiao @param @return @return
	 * String @throws
	 */
	@RequestMapping("/submit")
	public String updateSubmit(@CurrentUser User user, String planNo, Model model, Integer page, String name) {
		if (page == null) {
			page = 1;
		}
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("page", page);
		if (name != null && !"".equals(name)) {
			hashMap.put("orgName", name);
		}
		hashMap.put("orgId", user.getOrg().getId());
		List<PurchaseOrg> manages = purchserOrgnaztionService.selectByOrgId(hashMap);
		for (PurchaseOrg po : manages) {
			Orgnization orgnization = orgnizationServiceI.getOrgByPrimaryKey(po.getPurchaseDepId());
			po.setOrgnization(orgnization);
		}
		PageInfo<PurchaseOrg> list = new PageInfo<PurchaseOrg>(manages);
		model.addAttribute("list", list);
		model.addAttribute("uniqueId", planNo);
		return "bss/pms/purchaserequird/add_purchase_org";
	}

	@RequestMapping("/ztree")
	public String ztree(String type, String planNo, String planName, Model model) {
		model.addAttribute("type", type);
		model.addAttribute("planNo", planNo);
		model.addAttribute("planName", planName);
		return "bss/pms/purchaserequird/ztreeadd";
	}

	/**
	 * 
	 * @Title: id @Description: 生成id author: Li Xiaoxiao @param @return @return
	 * String @throws
	 */
	@RequestMapping("/getId")
	@ResponseBody
	public String id() {
		String id = UUID.randomUUID().toString().replaceAll("-", "");

		return id;
	}

	@RequestMapping("/submanage")
	@SystemControllerLog(description = "提交管理部门", operType = 3)
	public String submanage(String uniqueId, String managementId) {
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		PurchaseManagement pm = new PurchaseManagement();
		pm.setId(id);
		pm.setManagementId(managementId);
		pm.setPurchaseId(uniqueId);
		pm.setCreateAt(new Date());
		pm.setStatus(1);
		List<PurchaseManagement> pms = purchaseManagementService.queryByMidAndPid(pm.getPurchaseId(), pm.getManagementId());
		if (pms == null || pms.size() < 1) {
			purchaseManagementService.add(pm);
		}
		if (pms.size() > 0) {
			purchaseManagementService.updateStatus(uniqueId, 1);
		}

		PurchaseRequired p = new PurchaseRequired();
		p.setUniqueId(uniqueId);
		p.setStatus("2");
		p.setDetailStatus(0);
		p.setAuditDate(new Date());
		purchaseRequiredService.updateStatus(p);

		return "redirect:list.html";
	}

	/**
	 * 
	 * @Title: listName
	 * @author ZhaoBo
	 * @date 2016-12-14 下午1:55:01
	 * @Description: 检索物种名称
	 * @param @param
	 *          request
	 * @param @return
	 * @return String
	 */
	@RequestMapping("/listName")
	@ResponseBody
	public List<Category> listName(HttpServletRequest request) {
		List<Category> list = new ArrayList<>();
		String name = request.getParameter("name");
		if (name.trim().equals("") || name.trim() == null) {
			list = null;
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", name);
			list = categoryService.listByKeyname(map);
		}
		return list;
	}

	/**
	 * 
	 * @Title: viewIds
	 * @author ZhaoBo
	 * @date 2016-12-19 下午5:04:54
	 * @Description: 关联计算
	 * @param @param
	 *          response
	 * @param @param
	 *          id
	 * @param @throws
	 *          IOException
	 * @return void
	 */
	@RequestMapping("/viewIds")
	public void viewIds(HttpServletResponse response, String id) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		List<PurchaseRequired> list = purchaseRequiredService.selectByParent(map);
		String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
		response.getWriter().flush();
		response.getWriter().close();
	}

	@RequestMapping(value = "/queryNo")
	@ResponseBody
	public String line(String no) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planNo", no);
		List<PurchaseRequired> list = purchaseRequiredService.getByMap(map);
		if (list.size() > 0) {
			return "1";
		}
		return "0";
	}

	/**
	 * 
	 * @Title: isContainChinese @Description: 判断是否是含有中文 author: Li
	 * Xiaoxiao @param @param str @param @return @return boolean @throws
	 */
	public boolean isContainChinese(String str) {
		boolean bool = true;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find() == true && str.contains("（")) {
			bool = true;
		} else {
			bool = false;
		}
		return bool;
	}

	/**
	 * 
	 * @Title: isInteger @Description: 判断是否是数字 author: Li Xiaoxiao @param @param
	 * str @param @return @return boolean @throws
	 */
	public boolean isInteger(String str) {
		boolean bool = true;
		String regex = "^\\d+$";
		if (str.matches(regex)) {
			bool = true;
		} else {
			bool = false;
		}
		return bool;
	}

	/**
	 * 
	 * @Title: isContain @Description: 是否包含数字 author: Li Xiaoxiao @param @param
	 * str @param @return @return boolean @throws
	 */
	public boolean isContainIntger(String str) {
		boolean bool = true;
		Pattern p = Pattern.compile(".*\\d+.*");
		Matcher m = p.matcher(str);
		if (m.matches()) {
			bool = true;
		}
		return bool;
	}

	/**
	 * 
	 * @Title: isEng @Description:是否是英文 author: Li Xiaoxiao @param @param
	 * str @param @return @return boolean @throws
	 */
	public boolean isEng(String str) {
		boolean bool = true;
		String eng = "abcdefghijklmnopqrstuvwxyz";
		if (eng.contains(str)) {
			bool = true;
		} else {
			bool = false;
		}
		return bool;
	}

	public String randomPlano() {
		String str[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String year = sdf.format(date).split("-")[0];

		String res = "XQ-" + year + "-";
		for (int i = 0; i < 5; i++) {
			int id = (int) Math.ceil(Math.random() * 35);
			res += str[id];
		}

		return res;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// 设置List的最大长度
		binder.setAutoGrowCollectionLimit(30000);
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	public List<PurchaseRequired> get(String json) {

		JSONArray json1 = JSONArray.fromObject(json);
		List<PurchaseRequired> list = (List<PurchaseRequired>) JSONArray.toCollection(json1, PurchaseRequired.class);
		//
		//
		// Map<String,Object> map=new HashMap<String,Object>();
		//
		// JSONArray array=JSONArray.fromObject(json);
		//
		// JSONObject obj = new JSONObject();
		// PurchaseRequired pr=new PurchaseRequired();
		// List<PurchaseRequired> list=new ArrayList<PurchaseRequired>();
		// String planName="";
		// for (int i = 0; i < array.size(); i++) {
		// pr.setId((String)obj.get("id"));
		// list.add(pr);
		// planName=(String)obj.get("planName");
		// }
		// map.put("", list);
		// map.put("", planName);
		return list;
	}

	public void generateHeader(HSSFWorkbook workbook, HSSFSheet sheet) {

		HSSFRow row = sheet.createRow(1);
		HSSFCell cell = row.createCell(0);
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 3200);
		sheet.setColumnWidth(5, 1200);
		sheet.setColumnWidth(6, 2300);
		sheet.setColumnWidth(7, 2300);
		sheet.setColumnWidth(8, 2300);
		sheet.setColumnWidth(9, 2300);
		sheet.setColumnWidth(10, 2500);
		sheet.setColumnWidth(11, 3000);
		sheet.setColumnWidth(12, 3000);

		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();

		style.setBorderBottom(HSSFCellStyle.BORDER_HAIR);
		style.setBorderLeft(HSSFCellStyle.BORDER_HAIR);
		style.setBorderTop(HSSFCellStyle.BORDER_HAIR);
		style.setBorderRight(HSSFCellStyle.BORDER_HAIR);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		font.setFontHeightInPoints((short) 9);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(font);
		cell.setCellStyle(style);
		cell.setCellValue("序号");
		cell = row.createCell(1);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cell.setCellStyle(style);
		cell.setCellValue("需求部门");
		cell = row.createCell(2);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setWrapText(true);
		cell.setCellStyle(style);
		cell.setCellValue("物资类别及名称");
		cell = row.createCell(3);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setWrapText(true);
		cell.setCellStyle(style);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setWrapText(true);
		cell.setCellStyle(style);
		cell.setCellValue("规格型号");
		cell = row.createCell(4);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setWrapText(true);
		cell.setCellStyle(style);
		cell.setCellValue("质量技术标准");
		cell = row.createCell(5);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setWrapText(true);
		cell.setCellStyle(style);
		cell.setCellValue("计量单位");
		cell = row.createCell(6);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setWrapText(true);
		cell.setCellStyle(style);
		cell.setCellValue("采购数量");

		cell = row.createCell(7);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setWrapText(true);
		cell.setCellStyle(style);
		cell.setCellValue("单价（元）");

		cell = row.createCell(8);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setWrapText(true);
		cell.setCellStyle(style);
		cell.setCellValue("预算金额（万元）");

		cell = row.createCell(9);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setWrapText(true);
		cell.setCellStyle(style);
		cell.setCellValue("交货期限");

		cell = row.createCell(10);
		cell.setCellStyle(style);
		cell.setCellValue("采购方式");

		// cell = row.createCell(11);
		// cell.setCellStyle(style);
		// cell.setCellValue("采购机构");

		cell = row.createCell(11);
		cell.setCellStyle(style);
		cell.setCellValue("供应商名称");

		cell = row.createCell(12);
		cell.setCellStyle(style);
		cell.setCellValue("备注");
	}

	public void generateName(HSSFWorkbook workbook, HSSFSheet sheet, String planName) {
		// 表头第一行
		HSSFRow row = sheet.createRow(0);
		//
		HSSFCell cell = row.createCell(0);
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 22);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(font);
		cell.setCellStyle(style);
		row.setHeight((short) 800);
		cell.setCellValue(planName);
		sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) 12));

	}

	@RequestMapping("/getfile")
	@ResponseBody
	public String fileIds(String id) {
		String ids = "";
		List<UploadFile> list = uploadService.findBybusinessId(id, 2);
		for (UploadFile up : list) {
			ids += up.getId() + ",";
		}
		return ids;
	}

	/**
	 * @throws IOException
	 * 
	 * @Title: addReq @Description: 修改功能 添加数据 author: ShiShuai @param @param
	 * purchaseRequired @param @return @return String @throws
	 */
	@RequestMapping("/editdetail")
	@ResponseBody
	public String editReq(PurchaseRequiredFormBean list, String planType, String planNo, String planName,
			String recorderMobile, HttpServletRequest request, String referenceNo, String fileId, String prList,
			Integer enterPort, String unqueId) throws IOException {
		List<PurchaseRequired> plist = get(prList);
		User user = (User) request.getSession().getAttribute("loginUser");
		// int count=1;
		if (list != null) {
			if (plist != null && plist.size() > 0) {
				for (int i = 0; i < plist.size(); i++) {
					PurchaseRequired p = plist.get(i);
					p.setPlanNo(planNo);
					p.setPlanName(planName);
					p.setPlanType(planType);
					p.setHistoryStatus("0");
					p.setIsDelete(0);
					// p.setIsMaster(count);
					p.setCreatedAt(new Date());
					p.setUserId(user.getId());
					p.setRecorderMobile(recorderMobile);
					p.setProjectStatus(0);
					p.setAdvancedStatus(0);
					p.setIsDelete(0);
					p.setReferenceNo(referenceNo);
					p.setDetailStatus(0);
					p.setStatus("1");
					p.setFileId(fileId);
					p.setEnterPort(enterPort);
					if (p.getSeq() != null) {

						if (p.getPurchaseType() != null && p.getPurchaseType().trim().length() != 0) {
							DictionaryData data = dictionaryDataMapper.queryByName(p.getPurchaseType());
							p.setPurchaseType(data.getId());
						}

						if (p.getSeq().matches("[\u4E00-\u9FA5]") && !p.getSeq().contains("（")) {
							p.setSeq("一");
							// count=1;
							p.setIsMaster(1);
							p.setParentId("1");// 注释
							// unqueId= UUID.randomUUID().toString().replaceAll("-", "");
							p.setUniqueId(unqueId);
							// count++;
							continue;
						}
						// 判断是否是二级节点(一)
						if (isContainChinese(p.getSeq())) {
							p.setUniqueId(unqueId);
							// count++;
							continue;
						}
						// 判断是否是三级节点1,2,3
						else if (isInteger(p.getSeq())) {
							p.setUniqueId(unqueId);
							// count++;
							continue;
						}

						// 判断是否四级节点(1),(2)
						else if (isContainIntger(p.getSeq())) {
							p.setUniqueId(unqueId);
							// count++;
							continue;
						}
						// 五级节点
						else if (isEng(p.getSeq())) {
							p.setUniqueId(unqueId);
							// count++;
							continue;
						} else {
							p.setUniqueId(unqueId);
							// count++;
						}
					}

				}
			}
		}
		purchaseRequiredService.batchAdd(plist);
		return "";
	}
	/**
	 * Description: 校验供应商名称
	 * 
	 * @author ShiShuai
	 * @version 2017-5-24
	 * @param request
	 * @param response
	 * @param model
	 * @param r
	 * @exception
	 */
	@RequestMapping("checkSupplierName")
	@ResponseBody
	public boolean checkSupplierName(String name){
		List<Supplier> list = supplierService.selByName(name);
		if(list!=null&&list.size()>0){
			return false;
		}
		return true;
	}
	
	
	@RequestMapping("/viewDetamd")
	public String viewDetamd(PurchaseRequired purchaseRequired, String date, Integer page, String orgId, Model model){
	    HashMap<String, Object> map = new HashMap<>(); 
        map.put("isMaster", StaticVariables.DEFAULT_PAGE);
        if(StringUtils.isNotBlank(purchaseRequired.getStatus())){
            map.put("status", purchaseRequired.getStatus());
        }
        if(StringUtils.isNotBlank(purchaseRequired.getPlanName())){
            map.put("planName", purchaseRequired.getPlanName());
        }
        if(StringUtils.isNotBlank(purchaseRequired.getReferenceNo())){
            map.put("referenceNo", purchaseRequired.getReferenceNo());
        }
        if(StringUtils.isNotBlank(purchaseRequired.getPlanType())){
            map.put("planType", purchaseRequired.getPlanType());
        }
        if(StringUtils.isNotBlank(orgId)){
            map.put("orgId", orgId);
        }
        if (page == null) {
            page = StaticVariables.DEFAULT_PAGE;
        }
        if(StringUtils.isNotBlank(date)){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
            try {
                Date date2 = sdf.parse(date);
                map.put("createdAt", date2);
                purchaseRequired.setCreatedAt(date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
        List<PurchaseRequired> list = purchaseRequiredService.selectByAll(map);
        Orgnization orgnization = orgnizationServiceI.getOrgByPrimaryKey(orgId);
        if(orgnization != null){
            model.addAttribute("shortName", orgnization.getShortName());
        }
        model.addAttribute("info", new PageInfo<PurchaseRequired>(list));
        model.addAttribute("purchaseRequired", purchaseRequired);
        model.addAttribute("data", DictionaryDataUtil.find(6));
        model.addAttribute("orgId", orgId);
        return "dss/rids/list/view_required";
	}
}
