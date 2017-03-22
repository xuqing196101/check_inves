package bss.controller.ob;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
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
import ses.model.oms.PurchaseDep;
import ses.service.bms.CategoryService;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.util.PathUtil;
import bss.model.ob.OBProduct;
import bss.model.ob.OBSupplier;
import bss.model.pms.PurchaseRequired;
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
	public String list(OBProduct example, Model model, Integer page) {
		if (page == null) {
			page = 1;
		}
		List<OBProduct> list = oBProductService.selectByExample(example, page);
		PageInfo<OBProduct> info = new PageInfo<>(list);
		List<OBSupplier> numlist = oBSupplierService.selectSupplierNum();
		for (OBSupplier ob : numlist) {
			if(null != ob){
				if (null == ob.getnCount()) {
					ob.setnCount(0);
				}
			}
		}
		model.addAttribute("info", info);
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
		String smallPointsId = request.getParameter("smallPointsId") == null ? "" : request.getParameter("smallPointsId");
		List<OBSupplier> list = oBSupplierService.selectByProductId(null, page,
				0,supplierName,null,smallPointsId);
		PageInfo<OBSupplier> info = new PageInfo<>(list);
		model.addAttribute("info", info);
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
					obProduct.setIsDeleted(0);
					oBProductService.insertSelective(obProduct);
				}
			}
		}
		String jsonString = JSON.toJSONString(list);
		return jsonString;
	}


}