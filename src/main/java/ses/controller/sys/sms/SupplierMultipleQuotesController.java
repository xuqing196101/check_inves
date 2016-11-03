package ses.controller.sys.sms;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.User;
import ses.model.sms.Quote;
import ses.service.sms.SupplierQuoteService;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.SaleTender;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SaleTenderService;

import com.github.pagehelper.PageInfo;
/**
 * @Title: SupplierMultipleQuotesController
 * @Description: 供应商报价控制层
 * @author: Song Biaowei
 * @date: 2016-10-28上午10:21:33
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/mulQuo")
public class SupplierMultipleQuotesController extends BaseSupplierController {
	
	@Autowired
	private SaleTenderService saleTenderService;
	
	@Autowired
	private SupplierQuoteService supplierQuoteService;
	
    @Autowired
    private ProjectDetailService detailService;
    
	
    @Autowired
    private ProjectService projectService;
	
    /**
     * @Title: list
     * @author Song Biaowei
     * @date 2016-10-28 上午10:21:46  
     * @Description: 供应商报价的项目列表
     * @param @param req
     * @param @param response
     * @param @param saleTender
     * @param @param page
     * @param @param model
     * @param @return      
     * @return String
     */
	@RequestMapping(value="/list")
	public String list(HttpServletRequest req,HttpServletResponse response,SaleTender saleTender,Integer page,Model model){
			HashMap<String, Object> map = new HashMap<String, Object>();
		    List<ProjectDetail> pdList = detailService.selectByCondition(map,page==null?0:page);
		    model.addAttribute("pdList", new PageInfo<>(pdList));
			return "ses/sms/multiple_quotes/list";
	}
	
	/**
	 * @Title: baojia
	 * @author Song Biaowei
	 * @date 2016-10-28 上午10:22:00  
	 * @Description: 点击项目进行报价
	 * @param @param req
	 * @param @param id
	 * @param @param packageName
	 * @param @param packageId
	 * @param @param model
	 * @param @return
	 * @param @throws UnsupportedEncodingException      
	 * @return String
	 */
	@RequestMapping(value="/baojia")
	public String baojia(HttpServletRequest req,String id,String packageName,HttpServletResponse response,String packageId,Integer page,Model model) throws UnsupportedEncodingException{
		//判断有没有缴纳保证金，标书费
		User user=(User)req.getSession().getAttribute("loginUser");
		SaleTender saleTender=new SaleTender();
		saleTender.setStatusBid((short)2);
		saleTender.setStatusBond((short)2);
		saleTender.setSupplierId(user.getTypeId());
		saleTender.setProjectId(id);
		int size=saleTenderService.list(saleTender, 0).size();
		if(size>0){
			HashMap<String, Object> map = new HashMap<String, Object>();
	        map.put("id",id );
	        List<ProjectDetail> detailList = detailService.selectByCondition(map,page);
	        List<ProjectDetail> list=new ArrayList<ProjectDetail>();
	        if(detailList.size()>0){
	        	for(ProjectDetail pd:detailList){
	        		if(pd.getPackages().getName().equals(URLDecoder.decode(packageName,"UTF-8"))){
	        			list.add(pd);
	        		}
	        	}
	        }
			model.addAttribute("list", list);
			model.addAttribute("id", id);
			model.addAttribute("packageId", packageId);
			return "ses/sms/multiple_quotes/baojia";
		}else{
			super.alert(req, response, "缴纳保证金和标书费后才可以报价", false);
			return null;
		}
	}
	
	/**
	 * @Title: save
	 * @author Song Biaowei
	 * @date 2016-10-28 上午10:22:14  
	 * @Description: 保存报价
	 * @param @param req
	 * @param @param quote
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value="/save")
	public String save(HttpServletRequest req,Quote quote,Model model) {
		User user=(User)req.getSession().getAttribute("loginUser");
		quote.setSupplierId(user.getTypeId());
		quote.setCreatedAt(new Timestamp(new Date().getTime()));
		supplierQuoteService.insert(quote);
		return "redirect:list.html";
	}
	
	/**
	 * @Title: quoteHistory
	 * @author Song Biaowei
	 * @date 2016-10-28 上午10:22:25  
	 * @Description: 查看报价历史
	 * @param @param req
	 * @param @param quote
	 * @param @param page
	 * @param @param packageName
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value="/quoteHistory")
	public String quoteHistory(HttpServletRequest req,Quote quote,Integer page,String packageName,Model model) {
		User user=(User)req.getSession().getAttribute("loginUser");
		quote.setSupplierId(user.getTypeId());
		model.addAttribute("quoteList", new PageInfo<>(supplierQuoteService.getAllQuote(quote,page==null?0:page)));
		return "ses/sms/multiple_quotes/view";
	}
	
	/**
	 * @Title: listProject
	 * @author Song Biaowei
	 * @date 2016-10-31 下午5:20:43  
	 * @Description: 正参与招标项目页面和参与项目结束页面 
	 * @param @param req
	 * @param @param response
	 * @param @param status2
	 * @param @param project
	 * @param @param page
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value="/listProject")
	public String listProject(HttpServletRequest req,HttpServletResponse response,Project project,Integer page,Model model){
		    HashMap<String, Object> map = new HashMap<String, Object>();
		    List<ProjectDetail> pdList = supplierQuoteService.selectByCondition(map,page==null?0:page);
		    model.addAttribute("info", new PageInfo<>(pdList));
		    model.addAttribute("project", project);
			return "ses/sms/multiple_quotes/project_list/list";
	}
	
}
