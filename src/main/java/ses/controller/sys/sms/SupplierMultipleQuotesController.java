package ses.controller.sys.sms;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
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
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.SaleTender;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.SaleTenderService;

import com.github.pagehelper.PageInfo;
import com.sun.net.httpserver.spi.HttpServerProvider;

@Controller
@Scope("prototype")
@RequestMapping(value = "/mulQuo")
public class SupplierMultipleQuotesController extends BaseSupplierController {
	
	@Autowired
	private SaleTenderService saleTenderService;
	
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
		    List<ProjectDetail> stList = detailService.selectByCondition(map);
			model.addAttribute("stList", new PageInfo<>(stList));
			return "ses/sms/multiple_quotes/list";
		}else{
			super.alert(req, response, "缴纳保证金和标书费后才可以报价", false);
			return null;
		}
		
	}
	
	
	@RequestMapping(value="/baojia")
	public String baojia(HttpServletRequest req,String id,String packageName,Model model) throws UnsupportedEncodingException{
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
		return "ses/sms/multiple_quotes/baojia";
	}
}
