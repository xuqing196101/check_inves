package ses.controller.sys.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import ses.model.oms.Orgnization;
import ses.model.sms.supplierExport;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.SupplierService;

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
}
