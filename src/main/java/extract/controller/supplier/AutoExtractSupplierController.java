package extract.controller.supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import extract.model.supplier.SupplierConType;
import extract.model.supplier.SupplierExtractCondition;
import extract.service.supplier.AutoExtractSupplierService;


@Controller
@RequestMapping("/autoExtract")
public class AutoExtractSupplierController {

	@Autowired
	private AutoExtractSupplierService autoExtract;
	
	/**
	 * 内网导出抽取条件
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-20下午2:55:05
	 * @return
	 */
	@RequestMapping("exportExtractInfo")
	public void exportExtractInfo(SupplierExtractCondition condition,SupplierConType conType,String projectInto) {
		
		//处理详细抽取条件
		autoExtract.exportExtractInfo(condition,conType,projectInto);
		
	}
  
	
	
}
