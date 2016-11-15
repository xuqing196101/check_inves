package ses.controller.sys.sms;

import java.io.IOException;
import java.util.ArrayList;
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
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierTypeRelate;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.UserServiceI;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierLevelService;
import ses.service.sms.SupplierService;
import ses.util.FtpUtil;
import ses.util.PropUtil;

import com.github.pagehelper.PageInfo;
import common.constant.Constant;

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
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	
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
		if(ruku!=null&&ruku.equals("1")){
			User user=(User)request.getSession().getAttribute("loginUser");
			supplier = supplierAuditService.supplierById(user.getTypeId());
			model.addAttribute("suppliers", supplier);
			List<SupplierTypeRelate> listSupplierTypeRelates = supplier.getListSupplierTypeRelates();
			String supplierType="";
			if(listSupplierTypeRelates.size()>0){
				for(SupplierTypeRelate str:listSupplierTypeRelates){
					supplierType+=str.getSupplierTypeName()+" ";
				}
			}
			model.addAttribute("supplierType", supplierType);
			return "ses/sms/supplier_info/ruku";
		}else{
			User user=(User)request.getSession().getAttribute("loginUser");
			supplier = supplierAuditService.supplierById(user.getTypeId());
			getSupplierType(supplier);
			request.getSession().setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
			request.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
			model.addAttribute("suppliers", supplier);
			return "ses/sms/supplier_info/essential";
		}
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
	 
	 public void getSupplierType(Supplier supplier){
			List<SupplierTypeRelate> listSupplierTypeRelates = supplierService.get(supplier.getId()).getListSupplierTypeRelates();
			String supplierType="";
			if(listSupplierTypeRelates.size()>0){
				for(SupplierTypeRelate str:listSupplierTypeRelates){
					supplierType+=str.getSupplierTypeName()+" ";
				}
				supplier.setSupplierType(supplierType);
			}
	}
}
