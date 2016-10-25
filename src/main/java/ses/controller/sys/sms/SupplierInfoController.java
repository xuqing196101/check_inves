package ses.controller.sys.sms;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSe;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSe;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierStockholder;
import ses.service.bms.UserServiceI;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierLevelService;
import ses.service.sms.SupplierService;
import ses.util.FtpUtil;
import ses.util.PropUtil;

import com.github.pagehelper.PageInfo;

@Controller
@Scope("prototype")
@RequestMapping("/supplierInfo")
public class SupplierInfoController extends BaseSupplierController{
	
	@Autowired
	private SupplierAuditService supplierAuditService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private SupplierLevelService supplierLevelService;
	public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	@Autowired
	private UserServiceI userService;
	
	
	@RequestMapping("/open_password")
	public String openPassword(HttpServletRequest request,Model model) {
		User user=(User)request.getSession().getAttribute("loginUser");
		model.addAttribute("loginName", user.getLoginName());
		return "ses/sms/supplier_info/update_password";
	}
	
	@RequestMapping("/udpate_password")
	@ResponseBody
	public String udpatePassword(HttpServletRequest request,String oldPassword,String newPassword1,String newPassword2,Model model) {
		String flag="1";
		User user=(User)request.getSession().getAttribute("loginUser");
		//生成15位随机码
		String randomCode = generateString(15);
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();     
        // false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true  表示：生成24位的Base64版     
        md5.setEncodeHashAsBase64(false);     
        String pwd = md5.encodePassword(oldPassword, user.getRandomCode());
		if(!user.getPassword().equals(pwd)){
			//提示密码错误
			flag="1";
			return flag;
		}else if(newPassword1.equals("")||newPassword2.equals("")){
			//提示输入密码不为空
			flag="4";
			return flag;
		}else if(!newPassword1.equals(newPassword2)){
			//提示两次输入密码不一致
			flag="2";
			return flag;
		}else{
			flag="3";
			user.setPassword(md5.encodePassword(newPassword1,randomCode));
			user.setRandomCode(randomCode);
			userService.update(user);
			return flag;
		}
	}
	
	/**
     * Description: 返回一个定长的随机字符串(只包含大小写字母、数字)
     * 
     * @author Ye MaoLin
     * @version 2016-9-14
     * @param length
     * @return String
     * @exception IOException
     */
    public String generateString(int length) {  
        StringBuffer sb = new StringBuffer();  
        Random random = new Random();  
        for (int i = 0; i < length; i++) {  
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));  
        }  
        return sb.toString();  
    }
	
	/**
	 * @Title: essentialInformation
	 * @author Song Biaowei
	 * @date 2016-9-29 上午11:36:49  
	 * @Description: 基本信息 
	 * @param @param request
	 * @param @param supplier
	 * @param @param supplierId
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/essential")
	public String essentialInformation(HttpServletRequest request,String ruku,Supplier supplier,Model model) {
		if(ruku.equals("1")){
			User user=(User)request.getSession().getAttribute("loginUser");
			supplier = supplierAuditService.supplierById(user.getTypeId());
			model.addAttribute("suppliers", supplier);
			return "ses/sms/supplier_info/ruku";
		}else{
			User user=(User)request.getSession().getAttribute("loginUser");
			supplier = supplierAuditService.supplierById(user.getTypeId());
			model.addAttribute("suppliers", supplier);
			return "ses/sms/supplier_info/essential";
		}
	}
	
	/**
	 * @Title: financialInformation
	 * @author Song Biaowei
	 * @date 2016-9-29 上午11:37:15  
	 * @Description: 财务信息 
	 * @param @param request
	 * @param @param supplierFinance
	 * @param @param supplier
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/financial")
	public String financialInformation(HttpServletRequest request,SupplierFinance supplierFinance,Supplier supplier) {
		String supplierId = supplierFinance.getSupplierId();
		List<SupplierFinance> list = supplierAuditService.supplierFinanceBySupplierId(supplierId);
		request.setAttribute("supplierId", supplierId);
		request.setAttribute("financial", list);

		return "ses/sms/supplier_info/financial";
	}

	/**
	 * @Title: shareholderInformation
	 * @author Song Biaowei
	 * @date 2016-9-29 上午11:37:50  
	 * @Description: 股东信息
	 * @param @param request
	 * @param @param supplierStockholder
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/shareholder")
	public String shareholderInformation(HttpServletRequest request,SupplierStockholder supplierStockholder) {
		String supplierId = supplierStockholder.getSupplierId();
		List<SupplierStockholder> list = supplierAuditService.ShareholderBySupplierId(supplierId);
		request.setAttribute("supplierId", supplierId);
		request.setAttribute("shareholder", list);
		return "ses/sms/supplier_info/shareholder";
	}
	
	/**
	 * @Title: materialProduction
	 * @author Song Biaowei
	 * @date 2016-9-29 上午11:38:07  
	 * @Description: 物资生产型专业信息  
	 * @param @param request
	 * @param @param supplierMatPro
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/materialProduction")
	public String materialProduction(HttpServletRequest request,SupplierMatPro supplierMatPro) {
		String supplierId = supplierMatPro.getSupplierId();
		/*List<SupplierCertPro> materialProduction = supplierService.get(supplierId).getSupplierMatPro().getListSupplierCertPros();*/
		//资质资格证书信息
		List<SupplierCertPro> materialProduction = supplierAuditService.findBySupplierId(supplierId);
		//供应商组织机构人员,产品研发能力,产品生产能里,质检测试登记信息
		/*supplierMatPro = supplierAuditService.findSupplierMatProBysupplierId(supplierId);*/
		supplierMatPro =supplierService.get(supplierId).getSupplierMatPro();
		
		request.setAttribute("supplierId", supplierId);	
		request.setAttribute("materialProduction",materialProduction);
		request.setAttribute("supplierMatPros", supplierMatPro);
		return "ses/sms/supplier_info/material_production";
	}
	
	/**
	 * @Title: materialSales
	 * @author Song Biaowei
	 * @date 2016-9-29 上午11:39:08  
	 * @Description: 物资销售专业信息 
	 * @param @param request
	 * @param @param supplierMatSell
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/materialSales")
	public String materialSales(HttpServletRequest request,SupplierMatSell supplierMatSell){
		String supplierId = supplierMatSell.getSupplierId();
		//资质资格证书
		List<SupplierCertSell> supplierCertSell=supplierAuditService.findCertSellBySupplierId(supplierId);
		//供应商组织机构和人员
		supplierMatSell = supplierService.get(supplierId).getSupplierMatSell();
		request.setAttribute("supplierCertSell", supplierCertSell);
		request.setAttribute("supplierMatSells", supplierMatSell);
		request.setAttribute("supplierId", supplierId);
		return "ses/sms/supplier_info/material_sales";
	}
	
	/**
	 * @Title: engineeringInformation
	 * @author Song Biaowei
	 * @date 2016-9-29 上午11:39:22  
	 * @Description: 工程专业信息  
	 * @param @param request
	 * @param @param supplierMatEng
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/engineering")
	public String engineeringInformation(HttpServletRequest request,SupplierMatEng supplierMatEng){
		String supplierId = supplierMatEng.getSupplierId();
		//资质资格证书信息
		List<SupplierCertEng> supplierCertEng= supplierAuditService.findCertEngBySupplierId(supplierId);
		//资质资格信息
		List<SupplierAptitute> supplierAptitute = supplierAuditService.findAptituteBySupplierId(supplierId);
		//组织结构和注册人人员
		supplierMatEng = supplierAuditService.findMatEngBySupplierId(supplierId);
		request.setAttribute("supplierCertEng", supplierCertEng);
		request.setAttribute("supplierAptitutes", supplierAptitute);
		request.setAttribute("supplierMatEngs",supplierMatEng);
		request.setAttribute("supplierId", supplierId);
		return "ses/sms/supplier_info/engineering";
	}
	
	/**
	 * @Title: serviceInformation
	 * @author Song Biaowei
	 * @date 2016-9-29 上午11:39:43  
	 * @Description: 服务专业信息  
	 * @param @param request
	 * @param @param supplierMatSe
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/serviceInformation")
	public String serviceInformation(HttpServletRequest request,SupplierMatSe supplierMatSe){
		String supplierId = supplierMatSe.getSupplierId();
		//资质证书信息
		List<SupplierCertSe> supplierCertSe = supplierAuditService.findCertSeBySupplierId(supplierId);
		//组织结构和人员
		supplierMatSe = supplierAuditService.findMatSeBySupplierId(supplierId);
		request.setAttribute("supplierCertSes", supplierCertSe);
		request.setAttribute("supplierMatSes", supplierMatSe);
		request.setAttribute("supplierId", supplierId);
		return "ses/sms/supplier_info/service_information";
	}
	
	/**
	 * @Title: list
	 * @author Song Biaowei
	 * @date 2016-10-8 下午5:41:13  
	 * @Description: 诚信信息
	 * @param @param model
	 * @param @param supplier
	 * @param @param page
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value = "list")
	public String list(Model model, Supplier supplier,String supplierId, Integer page) {
		supplier.setId(supplierId);
		List<Supplier> listSuppliers = supplierLevelService.findSupplier(supplier, page == null ? 1 : page);
		model.addAttribute("listSuppliers", new PageInfo<Supplier>(listSuppliers));
		model.addAttribute("supplierName", supplier.getSupplierName());
		model.addAttribute("supplierId", supplier.getId());
		return "ses/sms/supplier_info/cheng_xin";
	}
	
	/**
	 * @Title: item
	 * @author Song Biaowei
	 * @date 2016-10-8 下午3:11:30  
	 * @Description: 品目信息 
	 * @param @param supplierId
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/item")
	public String item(String supplierId,Model model){
		model.addAttribute("id", supplierId);
		return "ses/sms/supplier_query/supplierInfo/item";
	}	
	
	/**
	 * @Title: downLoadFile
	 * @author Song Biaowei
	 * @date 2016-10-6 上午11:23:53  
	 * @Description: 附件下载查看
	 * @param @param fileName
	 * @param @param request
	 * @param @return
	 * @param @throws UnsupportedEncodingException      
	 * @return ResponseEntity<byte[]>
	 */
	 @RequestMapping("/downLoadFile")
		public void download(HttpServletRequest request, HttpServletResponse response, String fileName) {
			String stashPath = super.getStashPath(request);
			FtpUtil.startDownFile(stashPath, PropUtil.getProperty("file.upload.path.supplier"), fileName);
			FtpUtil.closeFtp();
			if (fileName != null && !"".equals(fileName)) {
				super.download(request, response, fileName);
			} else {
				super.alert(request, response, "无附件下载 !",true);
			}
			super.removeStash(request, fileName);
		}
}
