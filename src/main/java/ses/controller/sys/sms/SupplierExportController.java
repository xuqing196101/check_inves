package ses.controller.sys.sms;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



import com.github.pagehelper.PageInfo;

import ses.model.sms.supplierExport;
import ses.service.sms.SupplierService;

@Controller
@RequestMapping("/supplierExport")
public class SupplierExportController extends BaseSupplierController {
	@Autowired
	private SupplierService supplierService; // 供应商基本信息
	@RequestMapping("/list")
	public String list(HttpServletRequest request,HttpServletResponse response,String name,String nameEx,Model model,Integer page,Integer pageEx,Integer type){
		page=page==null?1:page;
		pageEx=pageEx==null?1:pageEx;
		HashMap<String, Object> hashMap=new HashMap<String, Object>();
		hashMap.put("page", page);
		hashMap.put("name", name);
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("pageEx", pageEx);
		map.put("nameEx", nameEx);
		List<supplierExport> selectSupplierNumber = supplierService.selectSupplierNumber(hashMap);
		List<supplierExport> selecteexNumber = supplierService.selectExpertNumber(map);
		PageInfo<supplierExport> list = new PageInfo<supplierExport>(selectSupplierNumber);
		PageInfo<supplierExport> list1 = new PageInfo<supplierExport>(selecteexNumber);
		model.addAttribute("list", list);
		model.addAttribute("listExpert", list1);
		model.addAttribute("name", name);
		model.addAttribute("nameEx", nameEx);
		model.addAttribute("type", type);
		return "ses/sms/supplierExport/list";
	}
}
