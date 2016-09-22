package ses.controller.sys.sms;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.SupplierTypeTree;
import ses.service.sms.SupplierTypeService;

@Controller
@Scope("prototype")
@RequestMapping(value = "supplier_type")
public class SupplierTypeController extends BaseSupplierController{
	
	@Autowired
	private SupplierTypeService supplierTypeService;// 供应商类型

	/**
	 * @Title: findSupplierType
	 * @author: Wang Zhaohua
	 * @date: 2016-9-19 下午2:11:08
	 * @Description: 查询供应商类型 Ajax
	 * @param: @param response
	 * @param: @param supplierId
	 * @param: @throws IOException
	 * @return: void
	 */
	@RequestMapping(value = "find_supplier_type")
	public void findSupplierType(HttpServletResponse response, String supplierId) throws IOException {
		List<SupplierTypeTree> listSupplierTypeTrees = supplierTypeService.findSupplierType(supplierId);
		super.writeJson(response, listSupplierTypeTrees);
	}
}
