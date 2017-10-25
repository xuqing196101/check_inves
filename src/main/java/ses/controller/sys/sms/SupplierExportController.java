package ses.controller.sys.sms;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


















import bss.util.ExportExcel;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import ses.model.oms.Orgnization;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.SupplierService;
import ses.util.WordUtil;

@Controller
@RequestMapping("/supplierExport")
public class SupplierExportController extends BaseSupplierController {
  @Autowired
  private SupplierService supplierService; // 供应商基本信息
  @Autowired
  private OrgnizationServiceI orgnizationServiceI;

  @RequestMapping("/list")
  public String list(HttpServletRequest request, HttpServletResponse response,
      String name, String nameEx,String nameSupFormal,String nameExpFormal, Model model, Integer page, Integer pageEx,
      Integer pageSupFormal,Integer pageExpFormal, Integer type) {
    HashMap<String, Object> map=new HashMap<String, Object>();
    map.put("isAuditSupplier", 1);
    List<Orgnization>  allOrg = orgnizationServiceI.findPurchaseOrgByPosition(map);
    request.setAttribute("allOrg", allOrg);
    return "ses/sms/supplierExport/list";
  }
  @RequestMapping("/supplier_check")
  public String supplierCheck(HttpServletRequest request, HttpServletResponse response,
      String name, String nameEx,String nameSupFormal,String nameExpFormal, Model model, Integer page, Integer pageEx,
      Integer pageSupFormal,Integer pageExpFormal, Integer type) {
    String url="";
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("name", name);
    if(type==1){
      List<Map<String, Object>> selectSupplierCheckNumber = supplierService.selectSupplierCheckNumber(hashMap);
      model.addAttribute("list", selectSupplierCheckNumber);
      url="ses/sms/supplierExport/supplier_check";
    }else if(type==2){
      List<Map<String, Object>> selectSupplierTypeNumber = supplierService.selectSupplierTypeNumber(hashMap);
      model.addAttribute("list", selectSupplierTypeNumber);
      url="ses/sms/supplierExport/supplier_type";
    }else if(type==3){
      url="ses/sms/supplierExport/supplier_product_type";
    }else if(type==4){
      List<Map<String, Object>> selectExpertCheckNumber = supplierService.selectExpertCheckNumber(hashMap);
      model.addAttribute("list", selectExpertCheckNumber);
      url="ses/sms/supplierExport/expert_check";
    }else if(type==5){
      List<Map<String, Object>> selectExpertTypeNumber = supplierService.selectExpertTypeNumber(hashMap);
      model.addAttribute("list", selectExpertTypeNumber);
      url="ses/sms/supplierExport/expert_type";
    }else if(type==6){
      url="ses/sms/supplierExport/expert_product_type";
    }
    return url;
  }
  
/**
 *〈简述〉
 *〈详细描述〉
 * @author Ye Maolin
 * @param request
 * @param response
 * @param name
 * @param type
 */
@RequestMapping("/exportSupplier_check")
  public void exportSupplier_check(HttpServletRequest request, HttpServletResponse response, String name, Integer type) {
	  	List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
	  	HashMap<String, Object> hashMap = new HashMap<String, Object>();
	    hashMap.put("name", name);
	    String fileName = "";
	    String templateName = "";
	    if(type==1){
	    	resultList = supplierService.selectSupplierCheckNumber(hashMap);
	    	fileName = "军队供应商入库审核情况统计表.xls";
	    	templateName = "supplier_check.ftl";
	    }else if(type==2){
	    	resultList = supplierService.selectSupplierTypeNumber(hashMap);
	    	fileName = "军队入库供应商类型统计表.xls";
	    	templateName = "supplier_type.ftl";
	    }else if(type==3){
	    	fileName = "军队供应商增加产品类别统计表.xls";
	    	templateName = "supplier_product_type.ftl";
	    }else if(type==4){
	    	resultList = supplierService.selectExpertCheckNumber(hashMap);
	    	fileName = "军队采购评审专家入库审核情况统计表.xls";
	    	templateName = "expert_check.ftl";
	    }else if(type==5){
	    	resultList = supplierService.selectExpertTypeNumber(hashMap);
	    	fileName = "军队入库评审专家类型统计表.xls";
	    	templateName = "expert_type.ftl";
	    }else if(type==6){
	    	fileName = "军队采购评审专家增加参评类别统计表.xls";
	    	templateName = "expert_product_type.ftl";
	    }
	    Map<String, Object> dataMap = new HashMap<String, Object>();
	    if (resultList != null && resultList.size() > 0) {
	    	dataMap.put("size", String.valueOf(resultList.size()));  
	    	dataMap.put("resultList", resultList);  
		} else {
			dataMap.put("size", 0);  
	    	dataMap.put("resultList", resultList); 
		}
	    String tempPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");
	    ExportExcel.createExcel(dataMap, templateName, tempPath, fileName, response, request);
  }

}
