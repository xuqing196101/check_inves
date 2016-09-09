package ses.controller.sys.sms;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ses.model.bms.User;
import ses.model.sms.SupplierFsInfoWithBLOBs;
import ses.service.bms.UserServiceI;
import ses.service.sms.SupplierFsInfoService;
import ses.util.Encrypt;
import ses.util.WfUtil;

/**
 * @Title: SupplierFsInfoController
 * @Description: 进口供应商注册审核控制层
 * @author: Song Biaowei
 * @date: 2016-9-7下午6:09:03
 */
@Controller
@Scope("prototype")
@RequestMapping("/supplierFsInfo")
public class SupplierFsInfoController {
	@Autowired
	private SupplierFsInfoService supplierFsInfoService;
	@Autowired
	private UserServiceI userService;
	
	/**
	* @Title: beforeRegister
	* @author Song Biaowei
	* @date 2016-9-6 上午11:31:17  
	* @Description:点击进口供应商注册 
	* @param @return      
	* @return String
	 */
	@RequestMapping("registerStart")
	public String registerStart(){
		return "fsInfo/register";
	}
	
	/**
	 * @Title: registerEnd
	 * @author Song Biaowei
	 * @date 2016-9-8 上午10:25:06  
	 * @Description: 注册完成
	 * @param @param sfi
	 * @param @return      
	 * @return String
	 * @throws IOException 
	 */
	@RequestMapping("registerEnd")
	public String registerEnd(SupplierFsInfoWithBLOBs sfi,@RequestParam("files") MultipartFile[] files,HttpServletRequest request) throws IOException{
		if(files!=null && files.length>0){
			 for(MultipartFile myfile : files){  
		            if(myfile.isEmpty()){  
		            	
		            }else{  
		                String filename = myfile.getOriginalFilename();
		                String uuid = WfUtil.createUUID();
		                filename=uuid+filename;
		                String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");  
		                FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, filename));  
		            }  
		        }  
			}
		supplierFsInfoService.register(sfi);
		User user=new User();
		String psw=Encrypt.md5AndSha(sfi.getLoginName()+sfi.getPassword());
		user.setPassword(psw);
		userService.save(user);
		return "redirect:daiban.html";
	}

	/**
	 * @Title: checkLoginName
	 * @author Song Biaowei
	 * @date 2016-9-9 上午9:04:40  
	 * @Description: 验证用户名
	 * @param @param sfi
	 * @param @return
	 * @param @throws Exception      
	 * @return boolean
	 */
	@RequestMapping("checkLoginName")
	@ResponseBody
	public boolean checkLoginName(SupplierFsInfoWithBLOBs sfi) throws Exception {
		List<SupplierFsInfoWithBLOBs> sfiList=supplierFsInfoService.selectByFsInfo(sfi);
		boolean flag=false;
		if(sfiList!=null&&sfiList.size()==0){
			flag =true;
		}
		return flag;
	}
	
	/**
	 * @Title: checkMobile
	 * @author Song Biaowei
	 * @date 2016-9-9 上午9:04:53  
	 * @Description: 验证电话号码 
	 * @param @param sfi
	 * @param @return
	 * @param @throws Exception      
	 * @return boolean
	 */
	@RequestMapping("checkMobile")
	@ResponseBody
	public boolean checkMobile(SupplierFsInfoWithBLOBs sfi) throws Exception {
		List<SupplierFsInfoWithBLOBs> sfiList=supplierFsInfoService.selectByFsInfo(sfi);
		boolean flag=false;
		if(sfiList!=null&&sfiList.size()==0){
			flag =true;
		}
		return flag;
	}
	
	/**
	* @Title: daiban
	* @author Song Biaowei
	* @date 2016-9-6 上午11:34:12  
	* @Description: 供应商审核点击我的待办
	* @param @param sfi
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("daiban.html")
	public String daiban(SupplierFsInfoWithBLOBs sfi,Model model){
		//未审核 就等于初审
		sfi.setStatus((short)0);
		int weishenhe=supplierFsInfoService.getCount(sfi);
		//审核中 就等于 复审
		sfi.setStatus((short)1);
		int fushen=supplierFsInfoService.getCount(sfi);
		//审核通过
		sfi.setStatus((short)2);
		int yishenhe=supplierFsInfoService.getCount(sfi);
		model.addAttribute("weishenhe", weishenhe);
		model.addAttribute("shenhezhong",fushen);
		model.addAttribute("yishenhe", yishenhe);
		return "fsInfo/daiban";
	}
	
	/**
	* @Title: daibanList
	* @author Song Biaowei
	* @date 2016-9-6 上午11:35:35  
	* @Description: 点击我的待办里面的选项进入的页面
	* @param @param sfi
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("auditList.html")
	public String daibanList(SupplierFsInfoWithBLOBs sfi,Model model){
		List<SupplierFsInfoWithBLOBs> sfiList=supplierFsInfoService.selectByFsInfo(sfi);
		model.addAttribute("sfiList", sfiList);
		return "fsInfo/auditList";
	}
	
	/**
	* @Title: auditShow
	* @author Song Biaowei
	* @date 2016-9-6 上午11:37:14  
	* @Description: 点击初审 复审进入的方法 
	* @param @param sfi
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("audit.html")
	public String auditShow(SupplierFsInfoWithBLOBs sfi,Model model){
		SupplierFsInfoWithBLOBs supplierFsInfoWithBLOBs = supplierFsInfoService.selectByPrimaryKey(sfi);
		model.addAttribute("sfi", supplierFsInfoWithBLOBs);
		return "fsInfo/firstAudit";
	}
	
}
