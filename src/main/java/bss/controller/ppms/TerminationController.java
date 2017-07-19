package bss.controller.ppms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.controller.sys.sms.BaseSupplierController;
import bss.model.ppms.FlowDefine;
import bss.model.ppms.Packages;
import bss.service.ppms.PackageService;
import bss.service.ppms.TerminationService;

/**
 * 终止包
 * @author lwl
 */
@Controller
@RequestMapping("/termination")
public class TerminationController extends BaseSupplierController {
  @Autowired
  private TerminationService terminationService;
  @Autowired
  private PackageService packageService;
  
  
  
  @RequestMapping("/package")
  public void getPackage(HttpServletResponse response, String projectId){
    HashMap<String, Object> map=new HashMap<String, Object>();
	map.put("projectId", projectId);
	map.put("projectStatus", "1");
	List<Packages> packages = packageService.selectByProjectKey(map);
	super.writeJson(response, packages);
  }
  @RequestMapping("/ter_package")
  public void terPackage(HttpServletResponse response, String packagesId,String projectId,String currFlowDefineId,String oldCurrFlowDefineId){
    terminationService.updateTermination(packagesId, projectId,currFlowDefineId,oldCurrFlowDefineId,null);
    super.writeJson(response, "ok");
  }
  @RequestMapping("/flowDefineId")
  public void flowDefineId(HttpServletResponse response, String packagesId,String currFlowDefineId){
    List<FlowDefine> flowDefine = terminationService.selectFlowDefineTermination(currFlowDefineId);
    super.writeJson(response, flowDefine);
  }
	
}
