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
    page = page == null ? 1 : page;
    pageEx = pageEx == null ? 1 : pageEx;
    pageSupFormal = pageSupFormal == null ? 1 : pageSupFormal;
    pageExpFormal = pageExpFormal == null ? 1 : pageExpFormal;
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("page", page);
    hashMap.put("name", name);
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("pageEx", pageEx);
    map.put("nameEx", nameEx);
    HashMap<String, Object> supFormalmap = new HashMap<String, Object>();
    supFormalmap.put("pageSupFormal", pageSupFormal);
    supFormalmap.put("nameSupFormal", nameSupFormal);
    HashMap<String, Object> expFormalmap = new HashMap<String, Object>();
    expFormalmap.put("pageExpFormal", pageExpFormal);
    expFormalmap.put("nameExpFormal", nameExpFormal);
    List<Orgnization>  allOrg = orgnizationServiceI.findPurchaseOrgByPosition(null);
    request.setAttribute("allOrg", allOrg);
    List<supplierExport> selectSupplierNumber = supplierService
        .selectSupplierNumber(hashMap);
    List<supplierExport> selecteexNumber = supplierService
        .selectExpertNumber(map);
    List<supplierExport> supFormal = supplierService
        .selectSupplierNumberFormal(supFormalmap);
    List<supplierExport> expFormal = supplierService
        .selectExpertNumberFormal(expFormalmap);
    PageInfo<supplierExport> list = new PageInfo<supplierExport>(
        selectSupplierNumber);
    PageInfo<supplierExport> list1 = new PageInfo<supplierExport>(
        selecteexNumber);
    PageInfo<supplierExport> supFormallist = new PageInfo<supplierExport>(
        supFormal);
    PageInfo<supplierExport>  expFormallist= new PageInfo<supplierExport>(
        expFormal);
    model.addAttribute("list", list);
    model.addAttribute("listExpert", list1);
    model.addAttribute("supFormallist", supFormallist);
    model.addAttribute("expFormallist", expFormallist);
    model.addAttribute("name", name);
    model.addAttribute("nameEx", nameEx);
    model.addAttribute("nameSupFormal", nameSupFormal);
    model.addAttribute("nameExpFormal", nameExpFormal);
    model.addAttribute("type", type);
    return "ses/sms/supplierExport/list";
  }
}
