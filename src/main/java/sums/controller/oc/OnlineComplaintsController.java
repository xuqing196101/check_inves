package sums.controller.oc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import bss.model.ob.OBProduct;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ses.model.bms.User;
import ses.model.ems.ExamPaper;
import ses.model.ems.ExamPaperUser;
import ses.model.ems.ExamQuestion;
import ses.model.ems.ExamUserScore;
import ses.model.ems.Expert;
import ses.service.bms.UserServiceI;
import ses.util.PropertiesUtil;
import sums.model.oc.Complaint;
import sums.service.oc.ComplaintService;

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
	public String complaints(HttpServletRequest request) {
		Complaint complaint = new Complaint();
		complaint.setStatus(0);
		
		return "sums/oc/onlineComplaints/add";
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
	public String handling(Model model,HttpServletRequest request,Integer page) {
		//验证页面非空
		if (page == null) {
			page = 1;
		}
		List<Complaint> list = complaintService.selectAllComplaint(page);
		//封装的分页的类 把当前查询的对象传入进去
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
	public String recordQuery(HttpServletRequest request) {
        
		return "sums/oc/inquire/list";
	}

	/**
	 * 
	 * Description: 处理
	 * 
	 * @author zhang shubin
	 * @version 2017年3月13日
	 * @param @param request
	 * @param @return
	 * @return String
	 * @exception
	 */
	@RequestMapping("/dealWith")
	public String dealWith(Model model,HttpServletRequest request) {
		//非空验证
		
		//获取前台传过来的值
		String id =  request.getParameter("id");
		Complaint complaint = complaintService.selectByPrimaryKey(id);
		model.addAttribute("complaint",complaint);
		int status = complaint.getStatus();
		if(status ==0){
			return "sums/oc/complaintHandling/show";
		} else{
			return  "redirect:handling.do";
			}
		/*if(status == 1||status == 2){
			return "sums/oc/complaintHandling/show";			
		}else if(status == 3){			
			return "sums/oc/complaintHandling/show1"; 
		}
		
		else{
			//重定向 同一个controller直接redirect 那个方法 前端如何有后缀也加上
			//配置文件里边规定的 .do 就会被认为是返回一个页面 加上.do会被认为是 一个Controller
			return "redirect:handling.do";
		}*/
		
	}

	/**
	 * 
	 * Description: 公布
	 * 
	 * @author zhang shubin
	 * @version 2017年3月13日
	 * @param @param request
	 * @param @return
	 * @return String
	 * @exception
	 */
	@RequestMapping("/publish")
	public String publish(Model model,HttpServletRequest request) {
		String id =  request.getParameter("id");
		Complaint complaint=complaintService.selectByPrimaryKey(id);
		return "sums/oc/complaintHandling/show1";
	}
}
