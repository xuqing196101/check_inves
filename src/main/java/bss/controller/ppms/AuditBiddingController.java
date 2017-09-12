package bss.controller.ppms;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.Constant;
import common.dao.FileUploadMapper;
import common.model.UploadFile;
import common.service.DownloadService;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.oms.PurchaseOrg;
import ses.service.bms.TodosService;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import bss.controller.base.BaseController;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.Reason;
import bss.service.ppms.AdvancedProjectService;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectService;
import bss.util.PropUtil;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>招标文件审核
 * <详细描述>
 * @author   Wang Wenshuai
 * @version  
 * @since
 * @see
 */
@Controller 
@RequestMapping("/Auditbidding")
public class AuditBiddingController extends BaseController {

  /** SCCUESS */
  private static final String SUCCESS = "SUCCESS";

  /**
   * 项目信息
   */
  @Autowired
  ProjectService projectService;

  @Autowired
  private FileUploadMapper fileDao;
  
  @Autowired
  private DownloadService downloadService;
  /**
   * 流程
   */
  @Autowired
  FlowMangeService flowMangeService;

  /**
   * 待办
   */
  @Autowired
  private TodosService todosService;

  
  @Autowired
  private PackageService packageService;
  
  @Autowired
  private AdvancedProjectService advancedProjectService;
  
  @Autowired
  private PurchaseOrgnizationServiceI purchaseOrgnizationService;
  
  /**
   * 
   *〈简述〉返回待审核的项目信息
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   */
  @RequestMapping("/list")
  public String list(@CurrentUser User user, Integer page, Model model,Project project){
	  if (user != null && user.getOrg() != null) {
		  String orgId = "";
		  List<PurchaseOrg> listOrg = purchaseOrgnizationService.getOrg(user.getOrg().getId());
		  for (PurchaseOrg purchaseOrg : listOrg) {
			  orgId += "'" + purchaseOrg.getPurchaseDepId() + "',";
		  }
		  
		  HashMap<String,Object> map = new HashMap<String,Object>();
          if (StringUtils.isNotBlank(project.getName())) {
              map.put("name", project.getName());
          }
          if (StringUtils.isNotBlank(project.getProjectNumber())) {
              map.put("projectNumber", project.getProjectNumber());
          }
          if (project.getConfirmFile() != null) {
        	  map.put("confirmFile", project.getConfirmFile());
          }
          if (project.getIsRehearse() != null) {
        	  map.put("IsRehearse", project.getIsRehearse());
          }
          if (orgId != null) {
        	  map.put("purchaseDepId", orgId.substring(0, orgId.length()-1));
          }
          if(page == null){
              page = 1;
          }
          PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
          List<AdvancedProject> selectByAudit = advancedProjectService.selectByAudit(map);
          if (selectByAudit != null && !selectByAudit.isEmpty()) {
        	  model.addAttribute("list", new PageInfo<AdvancedProject>(selectByAudit));
          }
          //只有采购管理部门才能操作
          if("2".equals(user.getTypeName())){
            model.addAttribute("auth", "show");
          }else {
            model.addAttribute("auth", "hidden");
          }
          model.addAttribute("project", project);
          model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
          model.addAttribute("status", DictionaryDataUtil.find(2));//获取数据字典数据
	  }
	  return "bss/ppms/audit_bidding/list";
  } 
  
  
  @ResponseBody
  @RequestMapping(value = "/auditProject",produces = "text/html;charset=UTF-8")
  public String auditProject(String projectId){
	  String audit = null;
	  if (StringUtils.isNotBlank(projectId)) {
		  Project project = projectService.selectById(projectId);
		  if (project != null) {
			  audit = "1";
		  } else {
			  AdvancedProject project2 = advancedProjectService.selectById(projectId);
			  if (project2 != null) {
				  audit = "2";
			  }
		  }
	  }
	  return audit;
  }

  /**
   * 
   *〈简述〉审核状态修改
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   * @throws UnsupportedEncodingException 
   */
  @ResponseBody
  @RequestMapping(value = "/updateAuditStatus",produces = "text/html;charset=UTF-8")
  public String updateAuditStatus(@CurrentUser User user, String projectId, String status, Reason reasons,HttpServletRequest request,String flowDefineId,String process) throws UnsupportedEncodingException{
    String  reasonStr = "";
    if (reasons != null) {
        JSONObject object = JSONObject.fromObject(reasons);
        reasonStr = object.toString();
    }
    
    Project project = new Project();
    project.setId(projectId);
    project.setAuditReason(reasonStr);
    //该环节设置为执行中状态
    flowMangeService.flowExe(request, flowDefineId, projectId, 2);
    //获取项目信息
    Project selectById = projectService.selectById(projectId);
    //获取包信息
    HashMap<String, Object> map = new HashMap<>();
    map.put("projectId", projectId);
    List<Packages> findById = packageService.findByID(map);
    if(findById != null && findById.size() > 0){
    	for (Packages packages : findById) {
    		if("4".equals(status)){
    			packages.setProjectStatus(DictionaryDataUtil.getId("ZBWJXGBB"));
    		} else if ("3".equals(status)) {
    			packages.setProjectStatus(DictionaryDataUtil.getId("ZBWJYTG"));
    		} else if ("2".equals(status)) {
    			packages.setProjectStatus(DictionaryDataUtil.getId("NZPFBZ"));
    		}
    		packageService.updateByPrimaryKeySelective(packages);
		}
    }
    //修改代办为已办
    todosService.updateIsFinish("open_bidding/bidFile.html?id=" + projectId + "&process=1");
    
  //修改报备 状态
    if("4".equals(status)){
    	project.setStatus(DictionaryDataUtil.getId("ZBWJXGBB"));
    	project.setConfirmFile(4);
    	project.setReplyTime(new Date());
    	 //推送待办
        Todos todos = new Todos();
        todos.setName(selectById.getName() + "招标文件修改报备");
        todos.setSenderId(user.getId());
        todos.setReceiverId(selectById.getPrincipal());
        todos.setUndoType((short)3);
        todos.setPowerId(PropUtil.getProperty("zbwjsh"));
        todos.setUrl("open_bidding/bidFile.html?id=" + projectId + "&process=1");
        todosService.insert(todos);
    }
    //通过 修改状态为一下状态
    if ("3".equals(status)) {
      project.setStatus(DictionaryDataUtil.getId("ZBWJYTG"));
      project.setConfirmFile(3);
      project.setReplyTime(new Date());
      //推送待办
      Todos todos = new Todos();
      todos.setName(selectById.getName() + "招标文件审核通过");
      todos.setSenderId(user.getId());
      todos.setReceiverId(selectById.getPrincipal());
      todos.setUndoType((short)3);
      todos.setPowerId(PropUtil.getProperty("zbwjsh"));
      todos.setUrl("open_bidding/bidFile.html?id=" + projectId + "&process=1");
      todosService.insert(todos);

    }
    //退回 修改状态为上一状态
    if ("2".equals(status)) {
      project.setStatus(DictionaryDataUtil.getId("NZPFBZ"));
      project.setConfirmFile(2);
      project.setReplyTime(new Date());
      //推送待办
      Todos todos = new Todos();
      todos.setName(selectById.getName() + "招标文件审核退回");
      todos.setSenderId(user.getId());
      todos.setReceiverId(selectById.getPrincipal());
      todos.setUndoType((short)3);
      todos.setPowerId(PropUtil.getProperty("zbwjsh"));
      todos.setUrl("open_bidding/bidFile.html?id=" + projectId + "&process=1");
      todosService.insert(todos);
    }
    projectService.update(project);
    return JSON.toJSONString(SUCCESS);

  }
  

  /**
   * 
   *〈简述〉生成正式的采购文件
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   * @throws UnsupportedEncodingException 
   */
  @RequestMapping("/purchaseFile")
  public void purchaseFile(HttpServletRequest request,  HttpServletResponse response, String projectId){
    //修改代办为已办
    todosService.updateIsFinish("open_bidding/bidFile.html?id=" + projectId + "&process=1");
    Integer systemKey = Integer.parseInt(Constant.TENDER_SYS_KEY+"");
    String tableName = Constant.fileSystem.get(systemKey);
    List<UploadFile> file = fileDao.findBybusinessId(projectId, tableName);
    if(file !=null && file.size()>0){
    //下载文件
    downloadService.downLoadFile(request, response, file.get(0).getPath());
    }
    
  }
  
    
    /**
     *〈简述〉查看招标文件审核意见
     *〈详细描述〉
     * @author Ye MaoLin
     * @param request
     * @param projectId
     * @param model
     * @param flowDefineId
     * @return
     */
    @RequestMapping("/viewAudit")
    public String viewAudit(HttpServletRequest request, String projectId, Model model, String type, String flowDefineId){
        Project project = projectService.selectById(projectId);
        model.addAttribute("project", project);
        model.addAttribute("flowDefineId", flowDefineId);
        model.addAttribute("reasons", JSON.parseObject(project.getAuditReason(), Reason.class));
        model.addAttribute("pStatus",DictionaryDataUtil.findById(project.getStatus()).getCode());
        model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
        model.addAttribute("type", type); //从监管系统跳转过来的
        //采购管理部门审核意见附件
        model.addAttribute("pcTypeId", DictionaryDataUtil.getId("PC_REASON"));
        //事业部门审核意见附件
        model.addAttribute("causeTypeId", DictionaryDataUtil.getId("CAUSE_REASON"));
        //财务部门审核意见附件
        model.addAttribute("financeTypeId", DictionaryDataUtil.getId("FINANCE_REASON"));
        return "bss/ppms/audit_bidding/audit_suggestion";
    }



}
