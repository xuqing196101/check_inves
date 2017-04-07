package bss.controller.ob;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
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

import ses.model.bms.Category;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.service.bms.CategoryService;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.util.PathUtil;
import bss.model.ob.OBProduct;
import bss.model.ob.OBSupplier;
import bss.service.ob.OBProductService;
import bss.service.ob.OBSupplierService;
import bss.util.ExcelUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.utils.JdcgResult;

/**
 * 
 * Description： 定型产品管理 Controller
 * 
 * @author zhang shubin
 * @version
 * @since JDK1.7
 * @date 2017年3月6日 下午5:33:43
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/product")
public class OBProductController {

	@Autowired
	private OBProductService oBProductService;

	@Autowired
	private OBSupplierService oBSupplierService;

	@Autowired
	private PurchaseOrgnizationServiceI purchaseOrgnizationServiceI;

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private OrgnizationServiceI orgnizationService;
	
	/**
	 * 
	 * Description: 列表查询
	 * 
	 * @author zhang shubin
	 * @version 2017年3月7日
	 * @param @param example
	 * @param @param model
	 * @param @param page
	 * @param @return
	 * @return String
	 * @exception
	 */
	@RequestMapping("/list")
	public String list(@CurrentUser User user,OBProduct example, Model model, Integer page) {
		if (page == null) {
			page = 1;
		}
		String orgTyp = null;
		if(user != null){
			if(user.getOrg().getTypeName().equals("2")){
				orgTyp = user.getOrg().getTypeName();
			}
		}
		List<OBProduct> list = oBProductService.selectByExample(example, page);
		if(list != null){
			for (OBProduct oBProduct : list) {
				String id = oBProduct.getSmallPointsId();
				if(id != null){
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("id", id);
					List<Category> clist = categoryService.findCategoryByParentNode(map);
					String str = "";
					for (Category category : clist) {
						if(!oBProduct.getSmallPoints().getName().equals(category.getName())){
							str += category.getName() +"/";
						}
					}
					if(oBProduct.getSmallPoints() != null){
						str+=oBProduct.getSmallPoints().getName();
						oBProduct.setPointsName(str);
					}
				}
			}
		}
		PageInfo<OBProduct> info = new PageInfo<>(list);
		List<OBSupplier> numlist = oBSupplierService.selectSupplierNum();
		for (OBSupplier ob : numlist) {
			if(null != ob){
				if (null == ob.getnCount()) {
					ob.setnCount(0);
				}
			}
		}
		if(user != null){
			model.addAttribute("userii", user);
		}
		model.addAttribute("info", info);
		model.addAttribute("orgTyp", orgTyp);
		model.addAttribute("productExample", example);
		model.addAttribute("numlist", numlist);
		return "bss/ob/finalize_DesignProduct/list";
	}

	/**
	 * 
	 * Description: 删除
	 * 
	 * @author zhang shubin
	 * @version 2017年3月7日
	 * @param @param request
	 * @return void
	 * @exception
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(HttpServletRequest request) {
		String oBProductids = request.getParameter("oBProductids");
		String productId = oBProductids.trim();
		if (productId.length() != 0) {
			String[] uniqueIds = productId.split(",");
			for (String str : uniqueIds) {
				oBProductService.deleteByPrimaryKey(str);
			}
		}
	}
	/**
	 * 
	 * Description: 获取产品 类型 和型号
	 * 
	 * @author Yanghongliang
	 * @param @param request
	 * @return void
	 * @exception
	 */
	@RequestMapping("/productType")
	@ResponseBody
	public OBProduct productType(HttpServletRequest request,String productId,Model model) {
		OBProduct obp=null;
		if (StringUtils.isNotBlank(productId)) {
			obp=  oBProductService.selectByPrimaryKey(productId);
		}
		return obp;
	}
	/**
	 * 
	 * Description: 发布定型产品(改变发布状态)
	 * 
	 * @author zhang shubin
	 * @version 2017年3月7日
	 * @param @param request
	 * @return void
	 * @exception
	 */
	@RequestMapping("/fab")
	@ResponseBody
	public void fab(HttpServletRequest request,Model model) {
		String id = request.getParameter("id") == null ? "" : request.getParameter("id");
			oBProductService.fab(id);
	}
	
	/**
	 * 
	 * Description: 撤回发布
	 * 
	 * @author  zhang shubin
	 * @version  2017年4月1日 
	 * @param  @param request
	 * @param  @param model 
	 * @return void 
	 * @exception
	 */
	@RequestMapping("/chfab")
	@ResponseBody
	public void chfab(HttpServletRequest request,Model model) {
		String id = request.getParameter("id") == null ? "" : request.getParameter("id");
			oBProductService.chfab(id);
	}

	
	
	/**
	 * 
	 * Description: 页面跳转
	 * 
	 * @author zhang shubin
	 * @version 2017年3月7日
	 * @param @param model
	 * @param @param request
	 * @param @return
	 * @return String
	 * @exception
	 */
	@RequestMapping("/tiaozhuan")
	public String tiaozhuan(Model model, HttpServletRequest request) {
		String id = request.getParameter("proid") == null ? "" : request
				.getParameter("proid");
		int type = Integer.parseInt(request.getParameter("type"));
		if (type == 1) {
			Category parentCategory = new Category();
			OBProduct obProduct = oBProductService.selectByPrimaryKey(id);
			if(obProduct != null && obProduct.getProductCategoryLevel() != null){
				if(obProduct.getProductCategoryLevel() == 2){
					parentCategory = categoryService.findById(obProduct.getCategoryBigId());
				}else if(obProduct.getProductCategoryLevel() == 3){
					parentCategory = categoryService.findById(obProduct.getCategoryMiddleId());
				}else if(obProduct.getProductCategoryLevel() == 4){
					parentCategory = categoryService.findById(obProduct.getCategoryId());
				}else if(obProduct.getProductCategoryLevel() == 5){
					parentCategory = categoryService.findById(obProduct.getProductCategoryId());
				}else{
					if(obProduct.getProductCategoryId() != null){
						parentCategory = categoryService.findById(obProduct.getProductCategoryId());
						obProduct.setProductCategoryLevel(5);
					}else{
						if(obProduct.getCategoryId() != null){
							parentCategory = categoryService.findById(obProduct.getCategoryId());
							obProduct.setProductCategoryLevel(4);
						}else{
							if(obProduct.getCategoryMiddleId() != null){
								parentCategory = categoryService.findById(obProduct.getCategoryMiddleId());
								obProduct.setProductCategoryLevel(3);
							}else{
								if(obProduct.getCategoryBigId() != null){
									parentCategory = categoryService.findById(obProduct.getCategoryBigId());
									obProduct.setProductCategoryLevel(2);
								}
							}
						}
					}
				}
			}
			if(parentCategory != null){
				model.addAttribute("categoryName", parentCategory.getName());
				model.addAttribute("cId",parentCategory.getId() );
			}
			model.addAttribute("obProduct", obProduct);
			return "bss/ob/finalize_DesignProduct/edit";
		} else {
			return "bss/ob/finalize_DesignProduct/publish";
		}
	}

	/**
	 * 
	 * Description: 查看供应商列表
	 * 
	 * @author zhang shubin
	 * @version 2017年3月7日
	 * @param @param model
	 * @param @param request
	 * @param @return
	 * @return String
	 * @exception
	 */
	@RequestMapping("/supplier")
	public String supplier(Model model, HttpServletRequest request, Integer page) {
		if (page == null) {
			page = 1;
		}
		String supplierName = request.getParameter("supplierName") == null ? "" : request.getParameter("supplierName");
		Integer status = request.getParameter("status") == null ? 0 : Integer.parseInt(request.getParameter("status"));
		String smallPointsId = request.getParameter("smallPointsId") == null ? "" : request.getParameter("smallPointsId");
		List<OBSupplier> list = oBSupplierService.selectByProductId(null, page,
				status,supplierName,null,smallPointsId);
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
		PageInfo<OBSupplier> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("status", status);
		model.addAttribute("supplierName", supplierName);
		model.addAttribute("smallPointsId", smallPointsId);
		return "bss/ob/finalize_DesignProduct/qualifiedSupplierlist";
	}

	/**
	 * 
	 * Description: 根据采购机构查询名称
	 * 
	 * @author zhang shubin
	 * @version 2017年3月8日
	 * @param @param request
	 * @param @return
	 * @return String
	 * @exception
	 */
	@RequestMapping("/selPurchaseDepbyId")
	@ResponseBody
	public JdcgResult selPurchaseDepbyId(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		PurchaseDep purchaseDep = purchaseOrgnizationServiceI
				.selectPurchaseById(id);
		String name = purchaseDep.getDepName();
		response.setContentType("text/html; charset=utf-8");
		return JdcgResult.ok(name);
	}

	/**
	 * 
	 * Description: 发布定型产品
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月8日 
	 * @param  @param example
	 * @param  @param model
	 * @param  @param request
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/add")
	public String add(Model model, HttpServletRequest request) {
		boolean flag = true;
		HttpSession session = request.getSession();
		String code = request.getParameter("code") == null ? "" : request.getParameter("code");
		String name = request.getParameter("name") == null ? "" :request.getParameter("name");
		String procurementId = request.getParameter("procurementId") == null ? "" :request.getParameter("procurementId");
		String categoryId = request.getParameter("category") == null ? "" :request.getParameter("category");
		int categoryLevel = request.getParameter("categoryLevel") == null ? 0 : Integer.parseInt(request.getParameter("categoryLevel"));
		String standardModel = request.getParameter("standardModel") == null ? "" :request.getParameter("standardModel");
		String qualityTechnicalStandard = request.getParameter("qualityTechnicalStandard") == null ? "" :request.getParameter("qualityTechnicalStandard");
		int i = Integer.parseInt(request.getParameter("i"));
		if(procurementId.equals("")){
			flag = false;
			model.addAttribute("error_org","采购机构不能为空");
		}
		OBProduct obProduct = new OBProduct();
		obProduct.setCode(code);
		obProduct.setName(name);
		obProduct.setProcurementId(procurementId);
		obProduct.setQualityTechnicalStandard(qualityTechnicalStandard);
		obProduct.setStandardModel(standardModel);
		obProduct.setSmallPointsId(categoryId);
		if(categoryLevel == 2){
			//大类
			obProduct.setCategoryBigId(categoryId);
		}
		if(categoryLevel == 3){
			//中类
			Category parentCategory = categoryService.findById(categoryId);
			obProduct.setCategoryMiddleId(categoryId);
			if(parentCategory != null){
				obProduct.setCategoryBigId(parentCategory.getParentId());
			}
		}
		if(categoryLevel == 4){
			//小类
			Category parentCategory = categoryService.findById(categoryId);
			obProduct.setCategoryId(categoryId);
			if(parentCategory != null){
				obProduct.setCategoryMiddleId(parentCategory.getParentId());
				Category parentCategory1 = categoryService.findById(parentCategory.getParentId());
				if(parentCategory1 != null){
					obProduct.setCategoryBigId(parentCategory1.getParentId());
				}
			}
		}
		if(categoryLevel == 5){
			//产品类别
			Category parentCategory = categoryService.findById(categoryId);
			obProduct.setProductCategoryId(categoryId); //产品类别id
			if(parentCategory != null){
				obProduct.setCategoryId(parentCategory.getParentId());//小类
				Category parentCategory1 = categoryService.findById(parentCategory.getParentId());
				if(parentCategory1 != null){
					obProduct.setCategoryMiddleId(parentCategory1.getParentId());//中类
					Category parentCategory2 = categoryService.findById(parentCategory1.getParentId());
					if(parentCategory1 != null){
						obProduct.setCategoryBigId(parentCategory2.getParentId());//大类
					}
				}
			}
		}
		obProduct.setProductCategoryLevel(categoryLevel);
		obProduct.setCreatedAt(new Date());
		User user = (User) session.getAttribute("loginUser");
		String userId = "";
		if(user != null){
			userId = user.getId();
		}
		obProduct.setCreaterId(userId);
		obProduct.setStatus(i);
		obProduct.setIsDeleted(0);
		if(oBProductService.yzProductCode(code,null) > 0){
			flag = false;
			model.addAttribute("error_code","产品代码不能重复");
		}
		if(oBProductService.yzProductName(name,null) > 0){
			flag = false;
			model.addAttribute("error_name","产品名称不能重复");
		}
		if(standardModel.length() > 1000){
			flag = false;
			model.addAttribute("error_standardModel","不能超过1000个字");
		}
		if(qualityTechnicalStandard.length() > 1000){
			flag = false;
			model.addAttribute("error_quality","不能超过1000个字");
		}
		if(categoryId.equals("")){
			model.addAttribute("error_category", "产品目录不能为空");
			flag = false;
		}else{
			String orgId = oBProductService.selOrgByCategory(categoryId,null);
			if(orgId != null){
				if(! orgId.equals(procurementId)){
					model.addAttribute("error_org", "该目录已有采购机构");
					flag = false;
				}
			}
		}
		if(flag == false){
			model.addAttribute("obProduct",obProduct);
			Category parentCategory = categoryService.findById(categoryId);
			model.addAttribute("categoryName", parentCategory.getName());
			model.addAttribute("cId",categoryId );
			return "bss/ob/finalize_DesignProduct/publish";
		}else{
			oBProductService.insertSelective(obProduct);
			return "redirect:/product/list.html";
		}
	}
	
	/**
	 * 
	 * Description: 定型产品修改
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月8日 
	 * @param  @param model
	 * @param  @param request
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/edit")
	public String edit(Model model, HttpServletRequest request){
		boolean flag = true;
		String id = request.getParameter("id") == null ? "" : request.getParameter("id");
		String code = request.getParameter("code") == null ? "" : request.getParameter("code");
		String name = request.getParameter("name") == null ? "" :request.getParameter("name");
		String procurementId = request.getParameter("procurementId") == null ? "" :request.getParameter("procurementId");
		String categoryId = request.getParameter("category") == null ? "" :request.getParameter("category");
		int categoryLevel = request.getParameter("categoryLevel") == null ? 0 : Integer.parseInt(request.getParameter("categoryLevel"));
		String standardModel = request.getParameter("standardModel") == null ? "" :request.getParameter("standardModel");
		String qualityTechnicalStandard = request.getParameter("qualityTechnicalStandard") == null ? "" :request.getParameter("qualityTechnicalStandard");
		int i = Integer.parseInt(request.getParameter("i"));
		if(code.equals("")){
			model.addAttribute("errorCode", "产品代码不能为空");
			flag = false;
		}else{
			if(oBProductService.yzProductCode(code,id) > 0){
				model.addAttribute("errorCode", "产品代码不能重复");
				flag = false;
			}
		}
		if(name.equals("")){
			model.addAttribute("errorName", "产品名称不能为空");
			flag = false;
		}else{
			if(oBProductService.yzProductName(name,id) > 0){
				model.addAttribute("errorName", "产品名称不能重复");
				flag = false;
			}
		}
		if(procurementId.equals("")){
			model.addAttribute("errorProcurement", "采购机构不能为空");
			flag = false;
		}
		if(standardModel.length() > 1000){
			flag = false;
			model.addAttribute("error_standardModel","不能超过1000个字");
		}
		if(qualityTechnicalStandard.length() > 1000){
			flag = false;
			model.addAttribute("error_quality","不能超过1000个字");
		}
		if(categoryId.equals("")){
			model.addAttribute("error_category", "产品目录不能为空");
			flag = false;
		}else{
			String orgId = oBProductService.selOrgByCategory(categoryId,id);
			if(orgId != null){
				if(! orgId.equals(procurementId)){
					model.addAttribute("errorProcurement", "该目录已有采购机构");
					flag = false;
				}
			}
		}
		OBProduct obProduct = new OBProduct();
		obProduct.setId(id);
		obProduct.setCode(code);
		obProduct.setName(name);
		obProduct.setProcurementId(procurementId);
		obProduct.setSmallPointsId(categoryId);
		if(categoryLevel == 2){
			//大类
			obProduct.setCategoryBigId(categoryId);
		}
		if(categoryLevel == 3){
			//中类
			Category parentCategory = categoryService.findById(categoryId);
			obProduct.setCategoryMiddleId(categoryId);
			if(parentCategory != null){
				obProduct.setCategoryBigId(parentCategory.getParentId());
			}
		}
		if(categoryLevel == 4){
			//小类
			Category parentCategory = categoryService.findById(categoryId);
			obProduct.setCategoryId(categoryId);
			if(parentCategory != null){
				obProduct.setCategoryMiddleId(parentCategory.getParentId());
				Category parentCategory1 = categoryService.findById(parentCategory.getParentId());
				if(parentCategory1 != null){
					obProduct.setCategoryBigId(parentCategory1.getParentId());
				}
			}
		}
		if(categoryLevel == 5){
			//产品类别
			Category parentCategory = categoryService.findById(categoryId);
			obProduct.setProductCategoryId(categoryId); //产品类别id
			if(parentCategory != null){
				obProduct.setCategoryId(parentCategory.getParentId());//小类
				Category parentCategory1 = categoryService.findById(parentCategory.getParentId());
				if(parentCategory1 != null){
					obProduct.setCategoryMiddleId(parentCategory1.getParentId());//中类
					Category parentCategory2 = categoryService.findById(parentCategory1.getParentId());
					if(parentCategory1 != null){
						obProduct.setCategoryBigId(parentCategory2.getParentId());//大类
					}
				}
			}
		}
		obProduct.setProductCategoryLevel(categoryLevel);
		obProduct.setStandardModel(standardModel);
		obProduct.setQualityTechnicalStandard(qualityTechnicalStandard);
		obProduct.setUpdatedAt(new Date());
		obProduct.setStatus(i);
		obProduct.setIsDeleted(0);
		if(flag == true){
			oBProductService.updateByPrimaryKeySelective(obProduct);
			return "redirect:/product/list.html";
		}else{
			Category ccategory = categoryService.findById(categoryId);
			model.addAttribute("obProduct", obProduct);
			model.addAttribute("cId",categoryId );
			if(ccategory != null){
				model.addAttribute("categoryName", ccategory.getName());
			}
			return "bss/ob/finalize_DesignProduct/edit";
		}
	}
	
	/**
	 * 
	 * Description: 模板下载
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月16日 
	 * @param  @param request
	 * @param  @param filename
	 * @param  @return
	 * @param  @throws IOException 
	 * @return ResponseEntity<byte[]> 
	 * @exception
	 */
	@RequestMapping("/download")
	public ResponseEntity<byte[]> download(HttpServletRequest request,
			String filename) throws IOException {
		String path = PathUtil.getWebRoot() + "excel/定型产品上传模板.xlsx";
		File file = new File(path);
		HttpHeaders headers = new HttpHeaders();
		String fileName = new String("定型产品上传模板.xlsx".getBytes("UTF-8"),
				"iso-8859-1");// 为了解决中文名称乱码问题
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
				headers, HttpStatus.OK);
	}
	
	/**
	 * 
	 * Description: 产品目录下载
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月28日 
	 * @param  @param request
	 * @param  @param filename
	 * @param  @return
	 * @param  @throws IOException 
	 * @return ResponseEntity<byte[]> 
	 * @exception
	 */
	@RequestMapping("/downloadCategory")
	public ResponseEntity<byte[]> downloadCategory(HttpServletRequest request,
			String filename) throws IOException {
		String path = PathUtil.getWebRoot() + "excel/产品分类目录下载.xlsx";
		File file = new File(path);
		HttpHeaders headers = new HttpHeaders();
		String fileName = new String("产品分类目录下载.xlsx".getBytes("UTF-8"),
				"iso-8859-1");// 为了解决中文名称乱码问题
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
				headers, HttpStatus.OK);
	}
	
	/**
	 * 
	 * Description: 导入excel
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月16日 
	 * @param  @param user
	 * @param  @param planDepName
	 * @param  @param file
	 * @param  @param type
	 * @param  @param planName
	 * @param  @param planNo
	 * @param  @param model
	 * @param  @return
	 * @param  @throws Exception 
	 * @return String 
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/upload", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String uploadFile(@CurrentUser User user,MultipartFile file) throws Exception {
		String fileName = file.getOriginalFilename();
		if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
			return "1";
		}

		List<OBProduct> list = new ArrayList<OBProduct>();
		Map<String, Object> maps = (Map<String, Object>) ExcelUtil
				.readOBProductExcel(file);
		list = (List<OBProduct>) maps.get("list");
		
		String errMsg = (String) maps.get("errMsg");

		if (errMsg != null) {
			String jsonString = JSON.toJSONString(errMsg);
			return jsonString;
		}else{
			if(list != null){
				for (OBProduct obProduct : list) {
					int i = 2;
					String smallPointsId = obProduct.getSmallPointsId();
					Category category = categoryService.findById(smallPointsId);
					Category category1 = categoryService.findById(category.getParentId());//上一级目录
					Category category2 = null;
					Category category3 = null;
					if(category1 != null){//计算机设备
						i++;//3
						category2 = categoryService.findById(category1.getParentId());//上两级目录
						if(category2 != null){ //通用设备
							i++;
							category3 = categoryService.findById(category2.getParentId());//上三级目录
							if(category3 != null){
								i++;
							}
						}
					}
					if(i == 2){
						obProduct.setProductCategoryLevel(i);
						obProduct.setCategoryBigId(smallPointsId);
					}
					if(i == 3){
						obProduct.setProductCategoryLevel(i);
						obProduct.setCategoryMiddleId(smallPointsId);
						obProduct.setCategoryBigId(category1.getId());
					}
					if(i == 4){
						obProduct.setProductCategoryLevel(i);
						obProduct.setCategoryId(smallPointsId);
						obProduct.setCategoryMiddleId(category1.getId());
						obProduct.setCategoryBigId(category2.getId());
					}
					if(i == 5){
						obProduct.setProductCategoryLevel(i);
						obProduct.setProductCategoryId(smallPointsId);
						obProduct.setCategoryId(category1.getId());
						obProduct.setCategoryMiddleId(category2.getId());
						obProduct.setCategoryBigId(category3.getId());
						
					}
					obProduct.setIsDeleted(0);
					obProduct.setStatus(2);
					obProduct.setCreatedAt(new Date());
					oBProductService.insertSelective(obProduct);
				}
			}
		}
		String jsonString = JSON.toJSONString(list);
		return jsonString;
	}

	/**
	 * 
	 * Description: 查看产品信息
	 * 
	 * @author  zhang shubin
	 * @version  2017年4月1日 
	 * @param  @param model
	 * @param  @param request
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/view")
	public String view(Model model, HttpServletRequest request){
		String id = request.getParameter("productId") == null ? "" : request
				.getParameter("productId");
		OBProduct obProduct = oBProductService.selectByPrimaryKey(id);
		if(obProduct != null){
			Category category = categoryService.findById(obProduct.getSmallPointsId());
			model.addAttribute("orgName", orgnizationService.selectById(obProduct.getProcurementId()));
			if(category != null){
				String name = category.getName();
				model.addAttribute("categoryName", name);
			}
			String sid = obProduct.getSmallPointsId();
				if(sid != null){
					HashMap<String, Object> map1 = new HashMap<String, Object>();
					map1.put("id", sid);
					List<Category> clist = categoryService.findCategoryByParentNode(map1);
					String str = "";
					for (Category categorys : clist) {
						if(!obProduct.getSmallPoints().getName().equals(categorys.getName())){
							str += categorys.getName() +"/";
						}
					}
					if(obProduct.getSmallPoints() != null){
						str+=obProduct.getSmallPoints().getName();
						obProduct.setPointsName(str);
					}
				}
			}
		model.addAttribute("obProduct", obProduct);
		return "bss/ob/finalize_DesignProduct/view";
	}
	
	/**
	 * 
	 * Description: 根据目录获取采购机构
	 * 
	 * @author  zhang shubin
	 * @version  2017年4月2日 
	 * @param  @param request
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("selOrgByCategory")
	@ResponseBody
	public String selOrgByCategory(HttpServletRequest request,HttpServletResponse response){
		String smallPointsId = request.getParameter("smallPointsId") == null ? "" : request.getParameter("smallPointsId");
		String orgId = oBProductService.selOrgByCategory(smallPointsId,null);
		return orgId;
	}
	
	/**
	 * 
	 * Description: 列表查询
	 * 
	 * @author zhang shubin
	 * @version 2017年3月7日
	 * @param @param example
	 * @param @param model
	 * @param @param page
	 * @param @return
	 * @return String
	 * @exception
	 */
	@RequestMapping("/index_list")
	public String headlist(HttpServletRequest request,Model model, Integer page) {
		if (page == null) {
			page = 1;
		}
		OBProduct example = new OBProduct();
		String name = request.getParameter("name") == null ? "" : request.getParameter("name");
		String code = request.getParameter("code") == null ? "" : request.getParameter("code");
		String smallPointsId = request.getParameter("smallPointsId") == null ? "" : request.getParameter("smallPointsId");
		example.setName(name);
		example.setCode(code);
		example.setSmallPointsId(smallPointsId);
		List<OBProduct> list = oBProductService.selectPublishProduct(example, page);
		if(list != null){
			for (OBProduct oBProduct : list) {
				String id = oBProduct.getSmallPointsId();
				if(id != null){
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("id", id);
					List<Category> clist = categoryService.findCategoryByParentNode(map);
					String str = "";
					for (Category category : clist) {
						if(!oBProduct.getSmallPoints().getName().equals(category.getName())){
							str += category.getName() +"/";
						}
					}
					if(oBProduct.getSmallPoints() != null){
						str+=oBProduct.getSmallPoints().getName();
						oBProduct.setPointsName(str);
					}
				}
			}
		}
		Category cl = categoryService.findById(smallPointsId);
		if(cl != null){
			model.addAttribute("catName", cl.getName());
		}
		PageInfo<OBProduct> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("product", example);
		return "bss/ob/finalize_DesignProduct/index_list";
	}


}