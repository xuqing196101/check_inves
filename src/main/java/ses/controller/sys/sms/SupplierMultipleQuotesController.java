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
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.SaleTender;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.SaleTenderService;

import com.github.pagehelper.PageInfo;

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
	
	@RequestMapping(value="/list")
	public String list(HttpServletRequest req,HttpServletResponse response,SaleTender saleTender,Integer page,Model model){
		User user=(User)req.getSession().getAttribute("loginUser");
		saleTender.setStatusBid((short)2);
		saleTender.setStatusBond((short)2);
		saleTender.setSupplierId(user.getTypeId());
		int size=saleTenderService.list(saleTender, page==null?0:page).size();
		if(size>0){
			HashMap<String, Object> map = new HashMap<String, Object>();
		    List<ProjectDetail> pdList = detailService.selectByCondition(map);
			model.addAttribute("pdList", new PageInfo<>(pdList));
			return "ses/sms/multiple_quotes/list";
		}else{
			super.alert(req, response, "缴纳保证金和标书费后才可以报价", false);
			return null;
		}
	}
	
	
	@RequestMapping(value="/baojia")
	public String baojia(HttpServletRequest req,String id,String packageName,String packageId,Model model) throws UnsupportedEncodingException{
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id",id );
        List<ProjectDetail> detailList = detailService.selectByCondition(map);
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
	}
	
	@RequestMapping(value="/save")
	public String save(HttpServletRequest req,Quote quote,Model model) {
		User user=(User)req.getSession().getAttribute("loginUser");
		quote.setSupplierId(user.getTypeId());
		quote.setCreatedAt(new Timestamp(new Date().getTime()));
		supplierQuoteService.insert(quote);
		return "redirect:list.html";
	}
	
	@RequestMapping(value="/quoteHistory")
	public String quoteHistory(HttpServletRequest req,Quote quote,Integer page,String packageName,Model model) {
		User user=(User)req.getSession().getAttribute("loginUser");
		quote.setSupplierId(user.getTypeId());
		model.addAttribute("quoteList", new PageInfo<>(supplierQuoteService.getAllQuote(quote,page==null?0:page)));
		return "ses/sms/multiple_quotes/view";
	}
	
}
