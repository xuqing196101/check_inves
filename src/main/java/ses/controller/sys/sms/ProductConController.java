package ses.controller.sys.sms;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.ProductCon;
import ses.service.sms.ProductConService;

import com.github.pagehelper.PageInfo;

public class ProductConController {
	
	@Resource
	private ProductConService ProductConService;
	
	/**
	 * 
	 * @Title: getAll
	 * @author Li ChenHao
	 * @date 2017-03-01  
	 * @Description:获取产品列表
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/getAll")
	public String getAll(Model model,Integer page){
		List<ProductCon> ProductCon = ProductConService.getAll(page==null?1:page);
		model.addAttribute("list",new PageInfo<ProductCon>(ProductCon));
		return "";
	}

}
