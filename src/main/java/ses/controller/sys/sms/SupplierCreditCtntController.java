package ses.controller.sys.sms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.sms.SupplierCreditCtnt;
import ses.service.sms.SupplierCreditCtntService;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import common.utils.JdcgResult;

import javax.servlet.http.HttpServletRequest;

@Controller
@Scope("prototype")
@RequestMapping("/supplier_credit_ctnt")
public class SupplierCreditCtntController {

	@Autowired
	private SupplierCreditCtntService supplierCreditCtntService;
	
	
	@RequestMapping(value = "list_by_credit_id")
	public String listByCreditId(Model model, SupplierCreditCtnt supplierCreditCtnt, Integer page) {
		List<SupplierCreditCtnt> listSupplierCreditCtnts = supplierCreditCtntService.findCreditCtntByCreditId(supplierCreditCtnt,  page == null ? 1 : page);
		model.addAttribute("listSupplierCreditCtnts", new PageInfo<SupplierCreditCtnt>(listSupplierCreditCtnts));
		model.addAttribute("supplierCreditId", supplierCreditCtnt.getSupplierCreditId());
		return "ses/sms/supplier_credit_ctnt/list";
	}
	
	@RequestMapping(value = "add_credit_ctnt")
	public String addCreditCtnt(Model model, SupplierCreditCtnt supplierCreditCtnt, HttpServletRequest request) throws Exception {
		// 解决中文乱码
		String encoding = request.getCharacterEncoding();
		if(supplierCreditCtnt.getName() != null){
			if ("UTF-8".equals(encoding)){
				supplierCreditCtnt.setName(supplierCreditCtnt.getName());
			}
			if ("ISO8859-1".equals(encoding)){
				supplierCreditCtnt.setName(new String(supplierCreditCtnt.getName().getBytes("ISO8859-1"), "UTF-8"));
			}
		}
		model.addAttribute("supplierCreditCtnt", supplierCreditCtnt);

		return "ses/sms/supplier_credit_ctnt/add_credit_ctnt";
	}

	@RequestMapping(value = "save_or_update_supplier_credit_ctnt")
	@ResponseBody
	public JdcgResult saveOrUpdateSupplierCredit(SupplierCreditCtnt supplierCreditCtnt) {
		if(StringUtils.isEmpty(supplierCreditCtnt.getName())){
			return JdcgResult.build(500, "请填写诚信内容名称");
		}
		if(supplierCreditCtnt.getScore() == null || "".equals(supplierCreditCtnt.getScore())){
			return JdcgResult.build(500, "请填写诚信内容分数"); 
		}
		supplierCreditCtntService.saveOrUpdateSupplierCreditCtnt(supplierCreditCtnt);
		//return "redirect:list_by_credit_id.html?supplierCreditId=" + supplierCreditCtnt.getSupplierCreditId();
		return JdcgResult.ok(supplierCreditCtnt.getSupplierCreditId());
	}
	
	@RequestMapping(value = "delete")
	public String delete(String ids, String supplierCreditId) {
		supplierCreditCtntService.delete(ids);
		return "redirect:list_by_credit_id.html?supplierCreditId=" + supplierCreditId;
	}
	
}
