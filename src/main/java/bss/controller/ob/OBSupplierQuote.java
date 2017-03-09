package bss.controller.ob;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import bss.model.ob.OBProject;
import bss.service.ob.OBProjectServer;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;

/**
 * @description 供应商竞价
 * @author Ma Mingwei
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/obSQuote")
public class OBSupplierQuote {

	@Autowired
	private OBProjectServer OBProjectServer;
	
	/**
	 * 
	 * @Title: ruleList
	 * @Description: 供应商报价列表查询
	 * @param @param model
	 * @param @param request
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/quoteList")
	public String quoteList(Model model, HttpServletRequest request,Integer page) {
		
		OBProject op =new OBProject();
    	//op.setName("");
    	//op.setStartTime(new Date());
    	List<OBProject> list=OBProjectServer.list(op);
    	
    	if(page==null){
    		page=1;
    	}
    	PropertiesUtil config = new PropertiesUtil("config.properties");
    	PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
    	model.addAttribute("listInfo", new PageInfo<OBProject>(list));
		
		return "bss/ob/supplier/list";
	}
}
