package sums.controller.oc;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ses.model.bms.User;
import sums.model.oc.Complaint;
import sums.service.oc.ComplaintService;

import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.annotation.SystemControllerLog;
import common.annotation.SystemServiceLog;
import common.constant.StaticVariables;

/**
 * 
 * Description： 网上投诉Controller
 * 
 * @author zhang shubin
 * @version
 * @since JDK1.7
 * @date 2017年3月13日 上午10:21:53
 * 
 */
@Controller
@RequestMapping("/onlineComplaints")
public class OnlineComplaintsController {

	@Autowired
	private ComplaintService complaintService;

	/**
	 * 
	 * Description: 网上投诉
	 * 
	 * @author zhang shubin
	 * @version 2017年3月13日
	 * @param @return
	 * @return String
	 * @exception
	 */
	@RequestMapping("/complaints")
	@SystemControllerLog(description=StaticVariables.OC_COMPLAINTS_NAME,operType=StaticVariables.OC_COMPLAINTS_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OC_COMPLAINTS_NAME,operType=StaticVariables.OC_COMPLAINTS_NAME_SIGN)
	public String complaints(@CurrentUser User user,@RequestParam(defaultValue="1")Integer page,Complaint complaint, Model model) {
		//声明标识是否是资源服务中心
		String authType = null;
		if(user != null){
			//判断是否 是资源服务中心 
			if("4".equals(user.getTypeName())){
				authType = "4";}}
		List<Complaint> list = complaintService.selectAllComplaint(complaint,page);
		PageInfo<Complaint> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("complaint", complaint);
		model.addAttribute("authType", authType);
		return "sums/oc/inquire/list";
	}
	
	/**
	 * 
	 * Description: 页面跳转
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月18日 
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/addoredit")
	@SystemControllerLog(description=StaticVariables.OC_COMPLAINTS_NAME,operType=StaticVariables.OC_COMPLAINTS_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OC_COMPLAINTS_NAME,operType=StaticVariables.OC_COMPLAINTS_NAME_SIGN)
	 public String addoredit(HttpServletRequest request,Model model){
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		Complaint complaint = new Complaint();
		complaint.setId(id);
		model.addAttribute("complaint",complaint);
		return "sums/oc/onlineComplaints/add";
	}
	
	/**
	 * 
	 * Description: 添加
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月18日 
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/add")
	@SystemControllerLog(description=StaticVariables.OC_COMPLAINTS_NAME,operType=StaticVariables.OC_COMPLAINTS_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OC_COMPLAINTS_NAME,operType=StaticVariables.OC_COMPLAINTS_NAME_SIGN)
	public String add(@CurrentUser User user,Model model,Complaint complaint){
		if(complaint != null){
			boolean flag = yzError(complaint,model);
			if(flag == false){
				model.addAttribute("complaint",complaint);
				return "sums/oc/onlineComplaints/add";
			}
			complaint.setIsDeleted(0);
			if(user != null){
				complaint.setCreaterId(user.getId());
			}
			complaint.setCreatedAt(new Date());
			complaintService.insertSelective(complaint);
		}
		model.addAttribute("complaintSuccess","投诉成功");
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		Complaint complaintId = new Complaint();
		complaintId.setId(id);
		model.addAttribute("complaint",complaintId);
		return "sums/oc/onlineComplaints/add";
	}
	
	/**
	 * 
	 * Description: 查看详情页面
	 * 
	 * @author  zhang shubin
	 * @version  2017年5月24日 
	 * @param  @param model
	 * @param  @param id
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/view")
	@SystemControllerLog(description=StaticVariables.OC_COMPLAINTS_NAME,operType=StaticVariables.OC_COMPLAINTS_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OC_COMPLAINTS_NAME,operType=StaticVariables.OC_COMPLAINTS_NAME_SIGN)
	public String view(Model model, String id){
		Complaint complaint = complaintService.selectByPrimaryKey(id);
		model.addAttribute("complaint", complaint);
		return "sums/oc/inquire/view";
	}
	
	/**
	 * 
	 * Description: 首页添加跳转
	 * 
	 * @author  zhang shubin
	 * @version  2017年5月24日 
	 * @param  @param user
	 * @param  @param model
	 * @param  @param complaint
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/index_add")
	@SystemControllerLog(description=StaticVariables.OC_COMPLAINTS_NAME,operType=StaticVariables.OC_COMPLAINTS_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OC_COMPLAINTS_NAME,operType=StaticVariables.OC_COMPLAINTS_NAME_SIGN)
	public String index_add(Model model){
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		Complaint complaint = new Complaint();
		complaint.setId(id);
		model.addAttribute("complaint",complaint);
		return "sums/oc/onlineComplaints/index_add";
	}
	
	/**
	 * 
	 * Description: 首页投诉
	 * 
	 * @author  zhang shubin
	 * @version  2017年5月24日 
	 * @param  @param user
	 * @param  @param model
	 * @param  @param complaint
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/indexadd")
	@SystemControllerLog(description=StaticVariables.OC_COMPLAINTS_NAME,operType=StaticVariables.OC_COMPLAINTS_NAME_SIGN)
	@SystemServiceLog(description=StaticVariables.OC_COMPLAINTS_NAME,operType=StaticVariables.OC_COMPLAINTS_NAME_SIGN)
	public String indexadd(@CurrentUser User user,Model model,Complaint complaint){
		if(complaint != null){
			boolean flag = yzError(complaint,model);
			if(flag == false){
				model.addAttribute("complaint",complaint);
				return "sums/oc/onlineComplaints/index_add";
			}
			complaint.setIsDeleted(0);
			if(user != null){
				complaint.setCreaterId(user.getId());
			}
			complaint.setCreatedAt(new Date());
			complaintService.insertSelective(complaint);
		}
		model.addAttribute("complaintSuccess","投诉成功");
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		Complaint complaintId = new Complaint();
		complaintId.setId(id);
		model.addAttribute("complaint",complaintId);
		return "sums/oc/onlineComplaints/index_add";
	}
	
	/**
	 * 
	 * Description: 验证获取到信息非空
	 * 
	 * @author  zhang shubin
	 * @version  2017年5月24日 
	 * @param  @param complaint
	 * @param  @param model
	 * @param  @return 
	 * @return boolean 
	 * @exception
	 */
	public boolean yzError(Complaint complaint,Model model){
		boolean flag = true;
		if(complaint.getTitle() == null || complaint.getTitle().equals("")){
			flag = false;
			model.addAttribute("error_title","标题不能为空");
		}else{
			if(complaint.getTitle().length() > 80){
				flag = false;
				model.addAttribute("error_title","不能超过80个字");
			}
		}
		if(complaint.getName() == null || complaint.getName().equals("")){
			flag = false;
			model.addAttribute("error_name","投诉人名称不能为空");
		}
		if(complaint.getTelephone() == null || complaint.getTelephone().equals("")){
			flag = false;
			model.addAttribute("error_telephone","投诉人联系电话不能为空");
		}/*else{
			Pattern p = Pattern.compile("[0-9-()（）]{7,18}");
			if (p.matcher(complaint.getTelephone()).matches() == false) {
				flag = false;
				model.addAttribute("error_telephone", "请输入正确的电话号码");
			}
		}*/
		if(complaint.getAdress() == null || complaint.getAdress().equals("")){
			flag = false;
			model.addAttribute("error_adress","投诉人联系地址不能为空");
		}
		if(complaint.getEmail() == null || complaint.getEmail().equals("")){
			flag = false;
			model.addAttribute("error_email","投诉人邮箱不能为空");
		}else{
			Pattern pp = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
			  if(pp.matcher(complaint.getEmail()).matches() == false){
				  flag = false;
				  model.addAttribute("error_email","请输入正确的邮箱");
			  }
		}
		if(complaint.getComplaintContent() == null || complaint.getComplaintContent().equals("")){
			flag = false;
			model.addAttribute("error_complaintContent","投诉内容不能为空");
		}else{
			if(complaint.getComplaintContent().length() > 1000){
				flag = false;
				model.addAttribute("error_complaintContent","不能超过1000个字");
			}
		}
		if(complaint.getId() != null){
			if(complaintService.yzsc(complaint.getId(), "47") < 1){
				flag = false;
				model.addAttribute("error_zs1","请上传投诉文件");
			}
		}
		return flag;
	}
}
