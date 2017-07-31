package bss.controller.prms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import bss.model.ppms.FlowExecute;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.prms.FirstAudit;
import bss.model.prms.FirstAuditTemitem;
import bss.model.prms.FirstAuditTemplat;
import bss.model.prms.PackageFirstAudit;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.prms.FirstAuditService;
import bss.service.prms.FirstAuditTemitemService;
import bss.service.prms.FirstAuditTemplatService;
import bss.service.prms.PackageFirstAuditService;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.util.DictionaryDataUtil;

@Controller
@RequestMapping("/firstAudit")
public class FirstAuditController {
	@Autowired
	private FirstAuditService service;
	@Autowired
	private ProjectDetailService detailService;
	@Autowired
	private PackageService packageService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private FirstAuditTemplatService templatService;
	@Autowired
	private PackageFirstAuditService packageFirstAuditService;
	@Autowired
    private FirstAuditTemplatService firstAuditTemplatService;//符合性审查模板
    @Autowired
    private FirstAuditTemitemService firstAuditTemitemService;//符合性审查模板评审项
    @Autowired
    private FlowMangeService flowMangeService;
	/**
	 * 
	  * @Title: toAdd
	  * @author ShaoYangYang
	  * @date 2016年10月9日 上午11:10:20  
	  * @Description: TODO 跳转到初审项定义页面
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/toAdd")
	public String toAdd(String projectId, Model model, String flowDefineId, String msg){
		try {
		    Project project = projectService.selectById(projectId);
		    HashMap<String, Object> map = new HashMap<String, Object>();
		    map.put("projectId", projectId);
            List<Packages> packages = packageService.findPackageById(map);
            for (Packages packages2 : packages) {
              FirstAudit firstAudit = new FirstAudit();
              firstAudit.setPackageId(packages2.getId());
              firstAudit.setIsConfirm((short)0);
              List<FirstAudit> fas = service.findBykind(firstAudit);
              //是否维护符合性审查项
              if (fas == null || fas.size() <= 0) {
                packages2.setIsEditFirst(0);
              } else {
                packages2.setIsEditFirst(1);
              }
            }
            //查询项目下所有的符合性审查项
            List<FirstAudit> firstAudits = service.getListByProjectId(projectId);
            model.addAttribute("packages", packages);
            List<DictionaryData> dds = DictionaryDataUtil.find(22);
            //符合性资格性审查项类型
            model.addAttribute("dds", dds);
            List<DictionaryData> purchaseTypes = DictionaryDataUtil.find(5);
            //查看是否环节结束，结束只能查看
            FlowExecute flowExecute = new FlowExecute();
            flowExecute.setFlowDefineId(flowDefineId);
            flowExecute.setProjectId(project.getId());
            List<FlowExecute> executes = flowMangeService.findFlowExecute(flowExecute);
            if(executes != null && executes.size() > 0){
                for (FlowExecute flowExecute2 : executes) {
                    if(flowExecute2.getStatus() == 3){
                        project.setConfirmFile(1);
                        break;
                    }
                }
            }
            model.addAttribute("purchaseTypes", purchaseTypes);
            model.addAttribute("firstAudits", firstAudits);
			model.addAttribute("projectId", projectId);
			model.addAttribute("flowDefineId", flowDefineId);
			model.addAttribute("project", project);
			model.addAttribute("msg", msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bss/ppms/open_bidding/bid_file";
	}
	
	/**
	 * 
	  * @Title: toPackageFirstAudit
	  * @author ShaoYangYang
	  * @date 2016年10月14日 下午6:13:08  
	  * @Description: TODO 去往分包选择页面
	  * @param @param projectId
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/toPackageFirstAudit")
	public String toPackageFirstAudit(String projectId, String flag, Model model, String flowDefineId){
		try {
			//项目分包信息
			HashMap<String,Object> pack = new HashMap<String,Object>();
			pack.put("projectId", projectId);
			/*List<Packages> packList = packageService.findPackageById(pack);
			if(packList.size()==0){
				Packages pg = new Packages();
				pg.setName("第一包"); 
				pg.setProjectId(projectId);
				packageService.insertSelective(pg);*/
				/*List<ProjectDetail> list = new ArrayList<ProjectDetail>();
				List<Packages> pk = packageService.findPackageById(pack);
				for(int i=0;i<list.size();i++){
					ProjectDetail pd = new ProjectDetail();
					pd.setId(list.get(i).getId());
					pd.setPackageId(pk.get(0).getId());
					detailService.update(pd);
				}*/
			//}
			List<Packages> packages = packageService.findPackageById(pack);
			Map<String,Object> list = new HashMap<String,Object>();
			//关联表集合
			List<PackageFirstAudit> idList = new ArrayList<>();
			Map<String,Object> mapSearch = new HashMap<String,Object>(); 
			for(Packages ps:packages){
				list.put("pack"+ps.getId(),ps);
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("packageId", ps.getId());
				List<ProjectDetail> detailList = detailService.selectById(map);
				ps.setProjectDetails(detailList);
				//设置查询条件
				mapSearch.put("projectId", projectId);
				mapSearch.put("packageId", ps.getId());
				List<PackageFirstAudit> selectList = packageFirstAuditService.selectList(mapSearch);
				idList.addAll(selectList);
			}
			model.addAttribute("idList",idList);
			model.addAttribute("packageList", packages);
			Project project = projectService.selectById(projectId);
			model.addAttribute("project", project);
			//初审项信息
			List<FirstAudit> list2 = service.getListByProjectId(projectId);
			model.addAttribute("list", list2);
			model.addAttribute("projectId", projectId);
			model.addAttribute("flag", flag);
			model.addAttribute("flowDefineId", flowDefineId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bss/ppms/open_bidding/package_first_audit";
	}
	
	/**
	 * 
	  * @Title: add
	  * @author ShaoYangYang
	  * @date 2016年10月9日 下午1:49:47  
	  * @Description: TODO 新增初审项定义
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("add")
	public String add(FirstAudit firstAudit,Model model,RedirectAttributes attr){
		service.add(firstAudit);
		attr.addAttribute("projectId", firstAudit.getProjectId());
		return "redirect:toAdd.html";
	}
	/**
	 * 
	  * @Title: remove
	  * @author ShaoYangYang
	  * @date 2016年10月9日 下午4:38:47  
	  * @Description: TODO 删除
	  * @param @param id
	  * @param @param attr
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("remove")
	@ResponseBody
	public void remove(String id,RedirectAttributes attr){
		String[] ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			service.delete(ids[i]);
		}
	}
	/**
	 * 
	  * @Title: toEdit
	  * @author ShaoYangYang
	  * @date 2016年10月9日 下午4:56:24  
	  * @Description: TODO 跳转到修改页面
	  * @param @param id
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("toEdit")
	public String toEdit(String id,Model model){
		FirstAudit firstAudit = service.get(id);
		model.addAttribute("firstAudit", firstAudit);
		return "bss/prms/edit";
	}
	/**
	 * 
	  * @Title: edit
	  * @author ShaoYangYang
	  * @date 2016年10月9日 下午4:59:39  
	  * @Description: TODO 修改
	  * @param @param firstAudit
	  * @param @param attr
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("edit")
	public String edit(FirstAudit firstAudit,RedirectAttributes attr){
		service.update(firstAudit);
		attr.addAttribute("projectId", firstAudit.getProjectId());
		return "redirect:toAdd.html";
	}
	/**
	 * 
	  * @Title: list
	  * @author ShaoYangYang
	  * @date 2016年10月12日 上午10:17:21  
	  * @Description: TODO 打开模板列表
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("toTemplatList")
	public String toTemplatList(String name,Integer page,Model model,HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("loginUser");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", name);
		map.put("userId", user.getId());
		List<FirstAuditTemplat> list = templatService.selectAllTemplat(map,page==null?1:page);
		model.addAttribute("list", new PageInfo<>(list));
		model.addAttribute("name", name);
		return "bss/prms/templat/window_list";
	}
	/**
	 * 
	  * @Title: relate
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午5:32:26  
	  * @Description: TODO 关联模板
	  * @param       
	  * @return void
	 */
	@RequestMapping("relate")
	@ResponseBody
	public void relate(String id,String projectId){
		templatService.relate(id, projectId);
	}
	
	
	/**
	 *〈简述〉编辑包的符合性审查项
	 *〈详细描述〉
	 * @author Ye MaoLin
	 * @param packageId 包id
	 * @param model
	 * @return
	 */
	@RequestMapping("/editPackageFirstAudit")
	public String editPackageFirstAudit(String packageId, Model model, String projectId, String flag, String flowDefineId){	  
	  List<DictionaryData> dds = DictionaryDataUtil.find(22);
    //符合性审查项
	  FirstAudit firstAudit1 = new FirstAudit();
	  firstAudit1.setKind(DictionaryDataUtil.getId("COMPLIANCE"));
	  firstAudit1.setPackageId(packageId);
	  firstAudit1.setIsConfirm((short)0);
	  List<FirstAudit> items1 = service.findBykind(firstAudit1);
    //资格性审查项
	  FirstAudit firstAudit2 = new FirstAudit();
    firstAudit2.setKind(DictionaryDataUtil.getId("QUALIFICATION"));
    firstAudit2.setPackageId(packageId);
    firstAudit2.setIsConfirm((short)0);
    List<FirstAudit> items2 = service.findBykind(firstAudit2);
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("id", packageId);
    List<Packages> packages = packageService.findPackageById(map);
    if (packages != null) {
      model.addAttribute("packages", packages.get(0));
    }
    /*HashMap<String, Object> map2 = new HashMap<String, Object>();
    map2.put("kind", DictionaryDataUtil.getId("REVIEW_QC"));
    //获取资格性和符合性审查模版
    List<FirstAuditTemplat> firstAuditTemplats = firstAuditTemplatService.find(map2);*/
    model.addAttribute("dds", dds);
    model.addAttribute("items1", items1);
    model.addAttribute("items2", items2);
    model.addAttribute("packageId", packageId);
    model.addAttribute("projectId", projectId);
    //model.addAttribute("firstAuditTemplats", firstAuditTemplats);
    model.addAttribute("flowDefineId", flowDefineId);
    model.addAttribute("flag", flag);
	  return "bss/prms/first_audit/edit_package_qc";
	}
	
	/**
	 *〈简述〉保存符合性审查项
	 *〈详细描述〉
	 * @author Ye MaoLin
	 * @param response
	 * @param firstAudit 符合性审查项封装实体
	 * @throws IOException
	 */
	@RequestMapping("/savePackageFirstAudit")
    public void savePackageFirstAudit(HttpServletResponse response, FirstAudit firstAudit) throws IOException{
      try {
      int count = 0;
      String msg = "";
      if (firstAudit.getName() == null || "".equals(firstAudit.getName())) {
        msg += "请输入评审项名称";
        count ++;
      }
      if (firstAudit.getPosition()== null) {
        if (count > 0) {
          msg += "、序号";
        } else {
          msg += "请输入排序号";
        }
        count ++;
      }
      if (firstAudit.getContent()== null || "".equals(firstAudit.getContent())) {
        if (count > 0) {
          msg += "和评审内容";
        } else {
          msg += "请输入评审内容";
        }
        count ++;
      }
      if (count > 0) {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter()
                .print("{\"success\": " + false + ", \"msg\": \"" + msg+ "\"}");
      }
      if (count == 0) {
        msg += "添加成功";
        service.add(firstAudit);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter()
                .print("{\"success\": " + true + ", \"msg\": \"" + msg+ "\"}");
      }
      response.getWriter().flush();
    } catch (Exception e) {
        e.printStackTrace();
    } finally{
        response.getWriter().close();
    }
      
    }
	
	/**
   *〈简述〉弹出编辑符合性评审项页面
   *〈详细描述〉
   * @author Ye MaoLin
   * @return
   */
  @RequestMapping("/editItem")
  public String editItem(String id, Model model, Short isConfirm, String flowDefineId){
    FirstAudit firstAudit = service.get(id);
    model.addAttribute("item", firstAudit);
    model.addAttribute("isConfirm", isConfirm);
    model.addAttribute("flowDefineId", flowDefineId);
    return "bss/prms/first_audit/qc_edit_item";
  }
  
  /**
   *〈简述〉更新符合性评审项
   *〈详细描述〉
   * @author Ye MaoLin
   * @param response
   * @param firstAuditTemitem
   * @throws IOException
   */
  @RequestMapping("/updateItem")
  public void updateItem(HttpServletResponse response, FirstAudit firstAudit) throws IOException{
    try {
      int count = 0;
      String msg = "";
      if (firstAudit.getName() == null || "".equals(firstAudit.getName())) {
        msg += "请输入评审项名称";
        count ++;
      }
      if (firstAudit.getPosition()== null) {
        if (count > 0) {
          msg += "、序号";
        } else {
          msg += "请输入排序号";
        }
        count ++;
      }
      if (firstAudit.getContent()== null || "".equals(firstAudit.getContent())|| "".equals(firstAudit.getContent().trim())) {
        if (count > 0) {
          msg += "和评审内容";
        } else {
          msg += "请输入评审内容";
        }
        count ++;
      }
      if (count > 0) {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter()
                .print("{\"success\": " + false + ", \"msg\": \"" + msg+ "\"}");
      }
      if (count == 0) {
        msg += "更新成功";
        service.update(firstAudit);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter()
                .print("{\"success\": " + true + ", \"msg\": \"" + msg+ "\"}");
        
      }
      response.getWriter().flush();
    } catch (Exception e) {
        e.printStackTrace();
    } finally{
        response.getWriter().close();
    }
  }
  
  /**
   *〈简述〉删除符合性评审项
   *〈详细描述〉
   * @author Ye MaoLin
   * @param response
   * @param id 
   * @throws IOException
   */
  @RequestMapping("/delItem")
  public void delItem(HttpServletResponse response, String id) throws IOException{
    try {
      service.delete(id);
      String msg = "删除成功";
      response.setContentType("text/html;charset=utf-8");
      response.getWriter()
              .print("{\"success\": " + true + ", \"msg\": \"" + msg+ "\"}");
      response.getWriter().flush();
    } catch (Exception e) {
        e.printStackTrace();
    } finally{
        response.getWriter().close();
    }
  }
	
  /**
   *〈简述〉引入模板数据
   *〈详细描述〉
   * @author Ye MaoLin
   * @param response
   * @param id 模板id
   * @param projectId 项目id
   * @param packageId 包id
   * @throws IOException 
   */
  @RequestMapping("/loadTemplat")
  public void loadTemplat(HttpServletResponse response, String id, String projectId, String packageId, Short isConfirm) throws IOException{
    try{
      FirstAudit record = new FirstAudit();
      if (packageId != null && !"".equals(packageId)) {
          record.setPackageId(packageId);
          record.setProjectId(projectId);
          record.setIsConfirm(isConfirm);
          List<FirstAudit> firstAudits = service.findBykind(record);
          //先删除数据
          for (FirstAudit firstAudit : firstAudits) {
              service.delete(firstAudit.getId());
          }
      }
      List<FirstAuditTemitem> items = firstAuditTemitemService.selectByTemplatId(id);
      for (FirstAuditTemitem firstAuditTemitem : items) {
        FirstAudit firstAudit = new FirstAudit();
        firstAudit.setContent(firstAuditTemitem.getContent());
        firstAudit.setKind(firstAuditTemitem.getKind());
        firstAudit.setName(firstAuditTemitem.getName());
        firstAudit.setPackageId(packageId);
        firstAudit.setPosition(firstAuditTemitem.getPosition());
        firstAudit.setProjectId(projectId);
        firstAudit.setIsConfirm(isConfirm);
        //保存导入模板数据
        service.add(firstAudit);
      }
      String msg = "引入成功";
      response.setContentType("text/html;charset=utf-8");
      response.getWriter()
              .print("{\"success\": " + true + ", \"msg\": \"" + msg+ "\"}");
      response.getWriter().flush();
    } catch (Exception e) {
        e.printStackTrace();
    } finally{
        response.getWriter().close();
    }
  }
  
  
  /**
   *〈简述〉加载其他项目包的评审项
   *〈详细描述〉
   * @author Ye MaoLin
   * @param model
   * @param page页码
   * @param packages
   * @return
   */
  @RequestMapping("/loadOtherPackage")
  public String loadOtherPackage(Model model, Integer page,String projectName, String packageName, String oldPackageId, String oldProjectId, String flowDefineId){
      HashMap<String, Object> map = new HashMap<String, Object>();
      map.put("projectName", projectName);
      map.put("packageName", packageName);
      List<Packages> list = packageService.findPackage(map, page == null ? 1 : page);
      model.addAttribute("list", new PageInfo<Packages>(list));
      model.addAttribute("projectName", projectName);
      model.addAttribute("packageName", packageName);
      model.addAttribute("oldPackageId", oldPackageId);
      model.addAttribute("oldProjectId", oldProjectId);
      model.addAttribute("flowDefineId", flowDefineId);
      return "bss/prms/first_audit/load_other";
  }
  
  @RequestMapping("/saveLoadPackage")
  public void saveLoadPackage(HttpServletResponse response, String id, String packageId, String projectId) throws IOException{
    try{
      FirstAudit record = new FirstAudit();
      record.setPackageId(packageId);
      record.setProjectId(projectId);
      record.setIsConfirm((short)0);
      List<FirstAudit> firstAudits = service.findBykind(record);
      //先删除数据
      for (FirstAudit firstAudit : firstAudits) {
        service.delete(firstAudit.getId());
      }
      FirstAudit record2 = new FirstAudit();
      record2.setPackageId(id);
      record2.setIsConfirm((short)0);
      //获取引入数据评审项
      List<FirstAudit> firstAudits2 = service.findBykind(record2);
      for (FirstAudit fa : firstAudits2) {
        FirstAudit firstAudit = new FirstAudit();
        firstAudit.setContent(fa.getContent());
        firstAudit.setKind(fa.getKind());
        firstAudit.setName(fa.getName());
        firstAudit.setPackageId(packageId);
        firstAudit.setPosition(fa.getPosition());
        firstAudit.setProjectId(projectId);
        firstAudit.setIsConfirm((short)0);
        //保存导入模板数据
        service.add(firstAudit);
      }
      String msg = "引入成功";
      response.setContentType("text/html;charset=utf-8");
      response.getWriter()
              .print("{\"success\": " + true + ", \"msg\": \"" + msg+ "\"}");
      response.getWriter().flush();
    } catch (Exception e) {
        e.printStackTrace();
    } finally{
        response.getWriter().close();
    }
    
  }
  
  /**
   *〈简述〉查询模板
   *〈详细描述〉
   * @author Ye MaoLin
   * @param response
   * @param categoryId
   * @param type
   * @throws IOException
   */
  @RequestMapping("/find")
  public void find(HttpServletResponse response, String categoryId, String type) throws IOException{
    try {
      String data = "";
      HashMap<String, Object> map2 = new HashMap<String, Object>();;
      map2.put("kind", DictionaryDataUtil.getId(type));
      if (categoryId != null && !"".equals(categoryId)) {
        map2.put("categoryId", categoryId);
      }
      List<FirstAuditTemplat> firstAuditTemplats = firstAuditTemplatService.find(map2);
      if (firstAuditTemplats != null && firstAuditTemplats.size() > 0) {
        data = JSON.toJSONString(firstAuditTemplats);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter()
        .print(data);
      }else {
        data += "没有该产品目录的模板";
        response.setContentType("text/html;charset=utf-8");
        response.getWriter()
                .print("{\"success\": " + false + ", \"msg\": \"" + data+ "\"}");
      }
      response.getWriter().flush();
    } catch (Exception e) {
        e.printStackTrace();
    } finally{
        response.getWriter().close();
    }
    
    
  }
}
