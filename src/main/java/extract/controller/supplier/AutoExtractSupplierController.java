package extract.controller.supplier;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import extract.model.supplier.SupplierExtractCondition;
import extract.service.supplier.AutoExtractSupplierService;


@Controller
@RequestMapping("/autoExtract")
public class AutoExtractSupplierController {

	@Autowired
	private AutoExtractSupplierService autoExtractSupplierService;
	
	/**
	 * 内网导出抽取条件
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-20下午2:55:05
	 * @return
	 */
	@RequestMapping("exportExtractInfo")
	@ResponseBody
	public String exportExtractInfo(SupplierExtractCondition condition,String projectInto) {
		
		//处理详细抽取条件
		String exportExtractInfo = autoExtractSupplierService.exportExtractInfo(condition,projectInto);
		return JSON.toJSONString(exportExtractInfo);
		
	}
  
	@RequestMapping("autoExtractTest")
	public void selectAutoExtractProject(){
		autoExtractSupplierService.selectAutoExtractProject(new Date(),new Date());
	}
}
