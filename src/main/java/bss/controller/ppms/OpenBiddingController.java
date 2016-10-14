package bss.controller.ppms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import bss.model.ppms.Project;
import bss.service.ppms.ProjectService;


/**
 * Description: 公开招标
 *
 * @author Ye MaoLin
 * @version 2016-10-9
 * @since JDK1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/open_bidding")
public class OpenBiddingController {
	
	@Autowired
	private ProjectService projectService;
	
	/**
	 * Description: 进入招标文件页面
	 * 
	 * @author Ye MaoLin
	 * @version 2016-10-14
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/bidFile")
	public String bidFile(String id, Model model){
		Project project = projectService.selectById(id);
		model.addAttribute("project", project);
		return "bss/ppms/open_bidding/bid_file";
	}
	
	/**
	 * Description: 招标公告
	 * 
	 * @author Ye MaoLin
	 * @version 2016-10-14
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/bidNotice")
	public String bidNotice(){
		
		return "bss/ppms/open_bidding/bid_notice";
	}
	
}
