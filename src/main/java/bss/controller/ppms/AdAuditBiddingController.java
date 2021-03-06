package bss.controller.ppms;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.Constant;
import common.dao.FileUploadMapper;
import common.model.UploadFile;
import common.service.DownloadService;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseOrg;
import ses.service.bms.TodosService;
import ses.service.bms.UserServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import bss.controller.base.BaseController;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.Project;
import bss.model.ppms.Reason;
import bss.service.ppms.AdvancedProjectService;
import bss.service.ppms.FlowMangeService;
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
@RequestMapping("/AdAuditbidding")
public class AdAuditBiddingController extends BaseController {

  /** SCCUESS */
  private static final String SUCCESS = "SUCCESS";

  /**
   * 项目信息
   */
  @Autowired
  private AdvancedProjectService projectService;

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
   * 用户
   */
  @Autowired
  private UserServiceI userService;

  /**
   * 待办
   */
  @Autowired
  private TodosService todosService;

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
  public String list(@CurrentUser User user,Integer page,Model model,AdvancedProject project){
  //采购机构信息
    PurchaseDep purchaseDep = purchaseOrgnizationService.selectByOrgId(user.getOrg().getId());
    project.setStatusArray(null);
    //拿到当前的采购机构获取到组织机构
    List<PurchaseOrg> listOrg = purchaseOrgnizationService.getOrg(purchaseDep.getOrgId());
    String org = "";
    for (PurchaseOrg purchaseOrg : listOrg) {
      org += "'"+purchaseOrg.getPurchaseDepId() + "',";
    }
    if(!"".equals(org)){
      project.setPurchaseDepId(org.substring(0, org.length()-1));
    }else{
      project.setPurchaseDepId("'123456'");
    }
    /*if (null == project.getConfirmFile()){
        project.setConfirmFile(1);
    }else if(project.getConfirmFile() == -1){
        project.setConfirmFile(null);
    }*/
    project.setPrincipal(user.getId());
    model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
    model.addAttribute("status", DictionaryDataUtil.find(2));//获取数据字典数据
    List<AdvancedProject> list = projectService.selectProjectByAll(page == null || "".equals(page) ? 1 : page, project);
    for (int i=0, k = list.size(); i < k; i++) {
      try {
        User contractor = userService.getUserById(list.get(i).getPrincipal());
        list.get(i).setProjectContractor(contractor.getRelName());
      } catch (Exception e) {
        list.get(i).setProjectContractor("");
      }
    }
    model.addAttribute("confirmFile", project.getConfirmFile());
    model.addAttribute("list", new PageInfo<AdvancedProject>(list));
    return "bss/ppms/advanced_project/advanced_bid_file/list";
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
      reasonStr = URLDecoder.decode(JSON.toJSONString(reasons),"UTF-8");
    }
    AdvancedProject project = new AdvancedProject();
    project.setId(projectId);
    project.setAuditReason(reasonStr);
    //该环节设置为执行中状态
    flowMangeService.flowExe(request, flowDefineId, projectId, 2);
    //获取项目信息
    AdvancedProject selectById = projectService.selectById(projectId);
    //修改代办为已办
    todosService.updateIsFinish("Adopen_bidding/bidFile.html?id=" + projectId + "&process=1");
    
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
        todos.setUrl("Adopen_bidding/bidFile.html?id=" + projectId + "&process=1");
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
      todos.setUrl("Adopen_bidding/bidFile.html?id=" + projectId + "&process=1");
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
      todos.setUrl("Adopen_bidding/bidFile.html?id=" + projectId + "&process=1");
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
    todosService.updateIsFinish("Adopen_bidding/bidFile.html?id=" + projectId + "&process=1");
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
  public String viewAudit(HttpServletRequest request, String projectId, String type, Model model, String flowDefineId){
      AdvancedProject project = projectService.selectById(projectId);
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
      //最终意见
      model.addAttribute("finalTypeId", DictionaryDataUtil.getId("FINAL_OPINION"));
      return "bss/ppms/advanced_project/advanced_bid_file/audit_suggestion";
  }

}
