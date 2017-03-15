package bss.controller.ob;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.service.sms.SupplierService;
import bss.model.ob.OBProduct;
import bss.model.ob.OBSupplier;
import bss.service.ob.OBProductService;
import bss.service.ob.OBSupplierService;

import com.github.pagehelper.PageInfo;

/**
 * 
 * Description：
 * 
 * @author zhang shubin
 * @version
 * @since JDK1.7
 * @date 2017年3月7日 下午4:19:54
 * 
 */
@Controller
@RequestMapping("/obSupplier")
public class OBSupplierController {

	@Autowired
	private OBSupplierService oBSupplierService;

	@Autowired
	private OBProductService oBProductService;
	
	@Autowired
	private SupplierService supplierService;

	/**
	 * 
	 * Description: 查询定型产品列表信息
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
			if (ob.getnCount() == null) {
				ob.setnCount(0);
			}
		}
		model.addAttribute("numlist", numlist);
		model.addAttribute("supplierinfo", info);
		model.addAttribute("supplierproductExample", example);
		return "bss/ob/addSupplier/addSupplierlist";
	}

	/**
	 * 
	 * Description: 跳转添加供应商页面
	 * 
	 * @author zhang shubin
	 * @version 2017年3月8日
	 * @param @param request
	 * @param @return
	 * @return String
	 * @exception
	 */
	@RequestMapping("/addSupplieri")
	public String addSupplieri(HttpServletRequest request,Model model) {
		String productid = request.getParameter("proid") == null ? "" : request.getParameter("proid");
		String supid = UUID.randomUUID().toString().replaceAll("-", "");
		OBSupplier obSupplier = new OBSupplier();
		obSupplier.setId(supid);
		obSupplier.setProductId(productid);
		model.addAttribute("obSupplier",obSupplier);
		return "bss/ob/addSupplier/addSupplier";
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
		int status = request.getParameter("status") == null ? 0 : Integer
				.parseInt(request.getParameter("status"));
		List<OBSupplier> list = oBSupplierService.selectByProductId(null, page,
				status);
		PageInfo<OBSupplier> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("status", status);
		return "bss/ob/addSupplier/supplierlist";
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
		String ids = request.getParameter("ids");
		String productId = ids.trim();
		if (productId.length() != 0) {
			String[] uniqueIds = productId.split(",");
			for (String str : uniqueIds) {
				oBSupplierService.deleteByPrimaryKey(str);
			}

		}
	}

	/**
	 * 
	 * Description: 查询供应商信息
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月9日 
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/findAllSupplier")
	@ResponseBody
	public List<Supplier> findAllSupplier(){
		List<Supplier> list = supplierService.findAllUsefulSupplier();
		return list;
	}
	
	/**
	 * 
	 * Description: 根据供应商ID查询统一社会信用代码
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月9日 
	 * @param  @param request
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/findUsccById")
	@ResponseBody
	public String findUsccById(HttpServletRequest request){
		String id = request.getParameter("option") == null ? "" :request.getParameter("option");
		Supplier supplier = supplierService.selectById(id);
		String creditCode = "";
		if(supplier != null){
			creditCode = supplier.getCreditCode();
		}
		return creditCode;
	}
	
	/**
	 * 
	 * Description: 添加供应商
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月9日 
	 * @param  @param model
	 * @param  @param request
	 * @param  @param obSupplier
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/add")
	public String add(Model model,HttpServletRequest request,OBSupplier obSupplier){
		boolean flag = true;
		if(obSupplier.getSupplierId() == null || obSupplier.getSupplierId() == ""){
			flag = false;
			model.addAttribute("errorName","供应商名称不能为空");
		}
		if(obSupplier.getCertValidPeriod() == null){
			flag = false;
			model.addAttribute("errorCertValidPeriod","证书有效期不能为空");
		}
		if(obSupplier.getQualityInspectionDep() == null || obSupplier.getQualityInspectionDep() == ""){
			flag = false;
			model.addAttribute("errorQualityInspectionDep","质检机构不能为空");
		}
		if(obSupplier.getContactName() == null || obSupplier.getContactName() == ""){
			flag = false;
			model.addAttribute("errorContactName","联系人姓名不能为空");
		}
		if(obSupplier.getContactTel() == null || obSupplier.getContactTel() == ""){
			flag = false;
			model.addAttribute("errorContactTel","联系人电话不能为空");
		}else {
			if(isMobileNO(obSupplier.getContactTel()) == false){
				flag = false;
				model.addAttribute("errorContactTel","请输入正确手机号码");
			}
		}
		if(obSupplier.getCertCode() == null || obSupplier.getCertCode() == ""){
			flag = false;
			model.addAttribute("errorCertCode","资质证书编号不能为空");
		}
		if(obSupplier.getUscc() == null || obSupplier.getUscc() == ""){
			flag = false;
			model.addAttribute("errorUscc","统一社会信用代码不能为空");
		}
		
		if(flag == true){
			HttpSession session = request.getSession();
			obSupplier.setIsDeleted(0);
			User user = (User) session.getAttribute("loginUser");
			String userId = "";
			if(user != null){
				userId = user.getId();
			}
			obSupplier.setCreaterId(userId);
			obSupplier.setCreatedAt(new Date());
			oBSupplierService.insertSelective(obSupplier);
			return "redirect:/obSupplier/list.html";
		}else{
			model.addAttribute("obSupplier", obSupplier);
			return "bss/ob/addSupplier/addSupplier";
		}
		
	}
	
	
	@RequestMapping("/toedit")
	public String toedit(Model model,HttpServletRequest request){
		String id = request.getParameter("suppid") == null ? "" : request.getParameter("suppid");
		OBSupplier obSupplier = oBSupplierService.selectByPrimaryKey(id);
		model.addAttribute("obSupplier", obSupplier);
		return "bss/ob/addSupplier/editSupplier";
	}
	
	/**
	 * 
	 * Description: 修改
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月9日 
	 * @param  @param model
	 * @param  @param request
	 * @param  @param obSupplier
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/edit")
	public String edit(Model model,HttpServletRequest request,OBSupplier obSupplier){
		boolean flag = true;
		if(obSupplier.getSupplierId() == null || obSupplier.getSupplierId() == ""){
			flag = false;
			model.addAttribute("errorName","供应商名称不能为空");
		}
		if(obSupplier.getCertValidPeriod() == null){
			flag = false;
			model.addAttribute("errorCertValidPeriod","证书有效期不能为空");
		}
		if(obSupplier.getQualityInspectionDep() == null || obSupplier.getQualityInspectionDep() == ""){
			flag = false;
			model.addAttribute("errorQualityInspectionDep","质检机构不能为空");
		}
		if(obSupplier.getContactName() == null || obSupplier.getContactName() == ""){
			flag = false;
			model.addAttribute("errorContactName","联系人姓名不能为空");
		}
		if(obSupplier.getContactTel() == null || obSupplier.getContactTel() == ""){
			flag = false;
			model.addAttribute("errorContactTel","联系人电话不能为空");
		}
		if(obSupplier.getContactTel() == null || obSupplier.getContactTel() == ""){
			flag = false;
			model.addAttribute("errorContactTel","联系人电话不能为空");
		}else {
			if(obSupplier.getContactTel().matches("[0-9]+") == false){
				flag = false;
				model.addAttribute("errorContactTel","请输入正确电话号码");
			}
		}
		if(obSupplier.getCertCode() == null || obSupplier.getCertCode() == ""){
			flag = false;
			model.addAttribute("errorCertCode","资质证书编号不能为空");
		}
		if(obSupplier.getUscc() == null || obSupplier.getUscc() == ""){
			flag = false;
			model.addAttribute("errorUscc","统一社会信用代码不能为空");
		}
		if(flag == true){
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("loginUser");
			String userId = "";
			if(user != null){
				userId = user.getId();
			}
			obSupplier.setCreaterId(userId);
			obSupplier.setUpdatedAt(new Date());
			oBSupplierService.updateByPrimaryKeySelective(obSupplier);
			return "redirect:/obSupplier/supplier.html";
		}else{
			model.addAttribute("obSupplier", obSupplier);
			return "bss/ob/addSupplier/editSupplier";
		}
		
	}
	
	/**
	 * 
	 * Description: 手机号验证
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月10日 
	 * @param  @param mobiles
	 * @param  @return 
	 * @return boolean 
	 * @exception
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("[0-9]{1,}");
		return p.matcher(mobiles).matches();
	}
}
