package bss.controller.sstps;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import bss.model.sstps.AccessoriesCon;
import bss.model.sstps.ContractProduct;
import bss.model.sstps.ProductInfo;
import bss.service.sstps.AccessoriesConService;
import bss.service.sstps.ProductInfoService;


/**
* @Title:OfferProductController 
* @Description: 
* @author Shen Zhenfei
* @date 2016-10-13上午10:05:29
 */
@Controller
@Scope
@RequestMapping("/offerProduct")
public class OfferProductController {
	
	@Autowired
	private ProductInfoService productInfoService;
	
	@Autowired
	private AccessoriesConService accessoriesConService;
	
	
	@RequestMapping("/save")
	public String save(Model model,ProductInfo productInfo,HttpServletRequest request){
		String id = request.getParameter("id");
		String proId = request.getParameter("contractProduct.id");
		productInfo.setUpdatedAt(new Date());
		if(id!=null && !id.equals("")){
			productInfoService.update(productInfo);
		}else{
			productInfo.setCreatedAt(new Date());
			productInfoService.insert(productInfo);
		}
		model.addAttribute("proId", proId);
		
		AccessoriesCon accessoriesCon = new AccessoriesCon();
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		accessoriesCon.setContractProduct(contractProduct);
		List<AccessoriesCon> list = accessoriesConService.selectProduct(accessoriesCon);
		model.addAttribute("list", list);
		return "bss/sstps/offer/supplier/accessories/list";
	}
	
	

}
