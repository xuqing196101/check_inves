package bss.controller.ppms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	List<Packages> list1=new ArrayList<Packages>();
	List<Packages> packages = packageService.selectByProjectKey(map);
	for(Packages pc:packages){
	  Packages packages2=new Packages();
	  packages2.setId(pc.getId());
	  packages2.setName(pc.getName());
	  list1.add(packages2);
	}
	super.writeJson(response, list1);
  }
  @RequestMapping("/ter_package")
  public void terPackage(HttpServletResponse response, String packagesId,String projectId,String currFlowDefineId,String oldCurrFlowDefineId){
    terminationService.updateTermination(packagesId, projectId,currFlowDefineId,oldCurrFlowDefineId,null);
    super.writeJson(response, "ok");
  }
  @RequestMapping("/flowDefineId")
  public void flowDefineId(HttpServletResponse response, String packagesId,String projectId){
    List<FlowDefine> flowDefine = terminationService.selectFlowDefineTermination(projectId);
    super.writeJson(response, flowDefine);
  }
	
}
