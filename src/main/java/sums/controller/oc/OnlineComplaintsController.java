package sums.controller.oc;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.User;
import sums.model.oc.Complaint;
import sums.service.oc.ComplaintService;

import com.github.pagehelper.PageInfo;

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
	public String complaints(HttpServletRequest request,Integer page,Complaint complaint, Model model) {
		if (page == null) {
			page = 1;
		}
		User user = (User) request.getSession().getAttribute("loginUser");
		if(user != null){
			complaint.setCreaterId(user.getId());
		}
		List<Complaint> list = complaintService.selectComplaintByUserId(complaint,page);
		PageInfo<Complaint> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("complaint", complaint);
		return "sums/oc/onlineComplaints/list";
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
	public String addoredit(HttpServletRequest request,Model model){
		int type = request.getParameter("type") == null ? 0 :Integer.parseInt(request.getParameter("type"));
		if(type == 1){
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			Complaint complaint = new Complaint();
			complaint.setId(id);
			model.addAttribute("complaint",complaint);
			return "sums/oc/onlineComplaints/add";
		}
		if(type == 2){
			String id = request.getParameter("id") == null ? "" : request.getParameter("id");
			Complaint complaint = complaintService.selectByPrimaryKey(id);
			model.addAttribute("complaint",complaint);
			return "sums/oc/onlineComplaints/edit";
		}
		return "redirect:/onlineComplaints/complaints.html";
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
	public String add(HttpServletRequest request,Model model,Complaint complaint){
		if(complaint != null){
			boolean flag = true;
			if(complaint.getType() == null || complaint.getType().equals("")){
				flag = false;
				model.addAttribute("error_type","投诉人类型不能为空");
			}
			if(complaint.getName() == null || complaint.getName().equals("")){
				flag = false;
				model.addAttribute("error_name","投诉人名称不能为空");
			}
			if(complaint.getComplaintObject() == null || complaint.getComplaintObject().equals("")){
				flag = false;
				model.addAttribute("error_complaintObject","投诉对象不能为空");
			}
			if(complaint.getComplaintMatter() != null){
				if(complaint.getComplaintMatter().length() > 1000){
					flag = false;
					model.addAttribute("error_complaintMatter","不能超过1000个字");
				}
			}
			if(complaint.getId() != null && complaint.getType() != null){
				if(complaint.getType() == 0){
					if(complaintService.yzsc(complaint.getId(), "47") < 1){
						flag = false;
						model.addAttribute("error_zs1","请上传投诉文件");
					}
				}else{
					if(complaintService.yzsc(complaint.getId(), "47") < 1){
						flag = false;
						model.addAttribute("error_zs1","请上传投诉文件");
					}
					if(complaintService.yzsc(complaint.getId(), "48") < 1){
						flag = false;
						model.addAttribute("error_zs2","请上传身份证照片");
					}
				}
			}
			if(flag == false){
				model.addAttribute("complaint",complaint);
				return "sums/oc/onlineComplaints/add";
			}
			complaint.setStatus(0);
			complaint.setIsDeleted(0);
			User user = (User) request.getSession().getAttribute("loginUser");
			if(user != null){
				complaint.setCreaterId(user.getId());
			}
			complaint.setCreatedAt(new Date());
			complaintService.insertSelective(complaint);
		}
		return "redirect:/onlineComplaints/complaints.html";
	}
	
	/**
	 * 
	 * Description: 修改
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月18日 
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/edit")
	public String edit(HttpServletRequest request,Model model,Complaint complaint){
		if(complaint != null){
			boolean flag = true;
			if(complaint.getType() == null || complaint.getType().equals("")){
				flag = false;
				model.addAttribute("error_type","投诉人类型不能为空");
			}
			if(complaint.getName() == null || complaint.getName().equals("")){
				flag = false;
				model.addAttribute("error_name","投诉人名称不能为空");
			}
			if(complaint.getComplaintObject() == null || complaint.getComplaintObject().equals("")){
				flag = false;
				model.addAttribute("error_complaintObject","投诉对象不能为空");
			}
			if(complaint.getComplaintMatter() != null){
				if(complaint.getComplaintMatter().length() > 1000){
					flag = false;
					model.addAttribute("error_complaintMatter","不能超过1000个字");
				}
			}
			if(complaint.getId() != null && complaint.getType() != null){
				if(complaint.getType() == 0){
					if(complaintService.yzsc(complaint.getId(), "47") < 1){
						flag = false;
						model.addAttribute("error_zs1","请上传投诉文件");
					}
				}else{
					if(complaintService.yzsc(complaint.getId(), "47") < 1){
						flag = false;
						model.addAttribute("error_zs1","请上传投诉文件");
					}
					if(complaintService.yzsc(complaint.getId(), "48") < 1){
						flag = false;
						model.addAttribute("error_zs2","请上传身份证照片");
					}
				}
			}
			if(flag == false){
				model.addAttribute("complaint",complaint);
				return "sums/oc/onlineComplaints/edit";
			}
			complaint.setUpdatedAt(new Date());
			complaintService.updateByPrimaryKeySelective(complaint);
		}
		return "redirect:/onlineComplaints/complaints.html";
	}
	
	/**
	 * 
	 * Description: 删除
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月18日 
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(HttpServletRequest request){
		String ids = request.getParameter("ids");
		String id = ids.trim();
		if (id.length() != 0) {
			String[] uniqueIds = id.split(",");
			for (String str : uniqueIds) {
				complaintService.updateIsDeleteByPrimaryKey(str);
			}

		}
	}

	/**
	 * 
	 * Description: 投诉处理
	 * 
	 * @author zhang shubin
	 * @version 2017年3月13日
	 * @param @param request
	 * @param @return
	 * @return String
	 * @exception
	 */
	@RequestMapping("/handling")
	public String handling(Model model, HttpServletRequest request, Integer page) {
		User user = (User) request.getSession().getAttribute("loginUser");
		String id = user.getId();
		// 验证页面非空
		if (page == null) {
			page = 1;
		}
		List<Complaint> list = complaintService.selectAllComplaint(page, id);
		// 封装的分页的类 把当前查询的对象传入进去
		PageInfo<Complaint> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		return "sums/oc/complaintHandling/list";
	}
	
	/**
	 * 
	 * Description: 投诉记录查询
	 * 
	 * @author zhang shubin
	 * @version 2017年3月13日
	 * @param @param request
	 * @param @return
	 * @return String
	 * @exception
	 */
	@RequestMapping("/recordQuery")
	public String recordQuery(HttpServletRequest request,Integer page,Complaint complaint, Model model) {
		if (page == null) {
			page = 1;
		}
		List<Complaint> list = complaintService.selectComplaintByUserId(complaint,page);
		PageInfo<Complaint> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("complaint", complaint);
		return "sums/oc/inquire/list";
	}

	/**
	 * 
	 * Description: 立项处理
	 * 
	 * @author zhang shubin
	 * @version 2017年3月13日
	 * @param @param request
	 * @param @return
	 * @return String
	 * @exception
	 */
	@RequestMapping("/dealWith")
	public String dealWith(Model model, HttpServletRequest request) {
		// 非空验证
		// 获取前台传过来的值
		String id = request.getParameter("id");
		Complaint complaint = complaintService.selectByPrimaryKey(id);
		// 获得 的值传入前
		model.addAttribute("complaint", complaint);
		int status = complaint.getStatus();
		request.setAttribute("ComplaintId", id);
		if (status == 0) {
			return "sums/oc/complaintHandling/show";			
		}else{
			return "redirect:handling.do";
		}
		/*
		 * if(status == 1||status == 2){ return
		 * "sums/oc/complaintHandling/show"; }else if(status == 3){ return
		 * "sums/oc/complaintHandling/show1"; }
		 * 
		 * else{ //重定向 同一个controller直接redirect 那个方法 前端如何有后缀也加上 //配置文件里边规定的 .do
		 * 就会被认为是返回一个页面 加上.do会被认为是 一个Controller return "redirect:handling.do"; }
		 */

	}

	/**
	 * 
	 * Description: 公布更改状态
	 * 
	 * @author zhang shubin
	 * @version 2017年3月13日
	 * @param @param request
	 * @param @return
	 * @return String
	 * @exception
	 */
	@RequestMapping("/publish")
	public String publish(Model model, HttpServletRequest request) {
		//获取前台传过来的值
		User user = (User) request.getSession().getAttribute("loginUser");
		String id = request.getParameter("id");
		String status = request.getParameter("status");
		//通过id查询
		Complaint complaint = complaintService.selectByPrimaryKey(id);
		complaint.setStatus(Integer.parseInt(status));
		// String Id = user.getId();
		complaint.setAuditId(user.getId());
		complaint.setCreaterId(user.getId());
		complaintService.updateByPrimaryKey(complaint);
		return "redirect:handling.do";
	}

	/**
	 * 对投诉进行处理的结果
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/update")
	public String update(Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("loginUser");
		String id = request.getParameter("Id");
		String state = request.getParameter("State");
		String msg = request.getParameter("Msg");
		//try {
			//msg = URLDecoder.decode(msg, "UTF-8");
		//} catch (UnsupportedEncodingException e) {
			//e.printStackTrace();
		//}
		Complaint complaint = complaintService.selectByPrimaryKey(id);
		complaint.setStatus(Integer.parseInt(state));
		complaint.setResion(msg);
		// String Id = user.getId();
		complaint.setAuditId(user.getId());
		complaint.setCreaterId(user.getId());
		complaintService.updateByPrimaryKey(complaint);
		return "redirect:handling.do";
	}
	/**
	 * 跳转公布页面
	 */

	@RequestMapping("/gongshi")
	public String gongbu(Model model, HttpServletRequest request) {
		String id = request.getParameter("id");
		Complaint complaint = complaintService.selectByPrimaryKey(id);
		// 获得 的值传入前
		model.addAttribute("complaint", complaint);
		int status = complaint.getStatus();
		request.setAttribute("ComplaintId", id);
		if (status == 1) {
			return "sums/oc/complaintHandling/show1";
		} else{
			return "redirect:handling.do";
		}
	}
	
	
	@RequestMapping("/ValidateState") 
	public @ResponseBody String ValidateState(Model model, HttpServletRequest request) {
		String id = request.getParameter("ComplaintId");
		String state = request.getParameter("State");
		Complaint complaint = complaintService.selectByPrimaryKey(id);
		String status = complaint.getStatus().toString();
		if (status.equals(state)) {
			return "success";
		} else{
			return "error";
		}
	}
	
}
