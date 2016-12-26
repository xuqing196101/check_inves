package bss.controller.ppms;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.ems.ExpertCategory;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseInfo;
import ses.model.oms.util.CommonConstant;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpertService;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseServiceI;
import ses.util.ComparatorDetail;
import ses.util.ComparatorDetails;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;
import ses.util.WordUtil;

import common.annotation.CurrentUser;

import bss.controller.base.BaseController;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.FlowDefine;
import bss.model.ppms.FlowExecute;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.Task;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.AdvancedDetailService;
import bss.service.ppms.AdvancedPackageService;
import bss.service.ppms.AdvancedProjectService;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.ProjectTaskService;
import bss.service.ppms.TaskService;

@Controller
@Scope("prototype")
@RequestMapping("/advancedProject")
public class AdvancedProjectController extends BaseController {
    @Autowired
    private AdvancedProjectService advancedProjectService;
    
    @Autowired
    private PurchaseRequiredService purchaseRequiredService;
    
    @Autowired
    private AdvancedDetailService detailService;
    
    @Autowired
    private AdvancedPackageService packageService;
    
    @Autowired
    private PurchaseServiceI purchaseService;
    
    @Autowired
    private FlowMangeService flowMangeService;
    
    @Autowired
    private OrgnizationServiceI orgnizationService;
    
    @Autowired
    private UserServiceI userService;
    
    @Autowired
    private ExpertService service;// 专家管理
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private ProjectTaskService projectTaskService;
    
    
    /**
     * 
     *〈预研列表展示〉
     *〈详细描述〉
     * @author Administrator
     * @param user
     * @param model
     * @param advancedProject
     * @param page
     * @return
     */
    @RequestMapping("/list")
    public String list(@CurrentUser User user, Model model, AdvancedProject advancedProject, @ModelAttribute PageInfo<AdvancedProject> page){
        //if(user != null && user.getOrg().getId() != null){
            PageHelper.startPage(page.getPageNum(),CommonConstant.PAGE_SIZE);
            List<AdvancedProject> list = advancedProjectService.selectByList(advancedProject);
            model.addAttribute("info", new PageInfo<AdvancedProject>(list));
            model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
            model.addAttribute("project", advancedProject);
        //}
        return "bss/ppms/advanced_project/list";
    }
    
    
    /**
     * 
     *〈新增〉
     *〈详细描述〉
     * @author Administrator
     * @param model
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/add")
    public String add(Model model, String id, HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<>();
        map.put("planNo", id);
        List<PurchaseRequired> list = purchaseRequiredService.getByMap(map);
        for (PurchaseRequired purchaseRequired : list) {
            Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(purchaseRequired.getDepartment());
            model.addAttribute("orgnization", orgnization);
            Orgnization org = orgnizationService.getOrgByPrimaryKey(purchaseRequired.getOrganization());
            model.addAttribute("org", org);
        }
        model.addAttribute("lists", list);
        model.addAttribute("user", list.get(0).getUserId());
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        return "bss/ppms/advanced_project/add_advanced";
    }
    
    /**
     * 
     *〈预研通知书附件上传〉
     *〈详细描述〉
     * @author Administrator
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/attachment")
    public String attachment(Model model,String ids, String projectNumber, String proName, String department,String purchaseType, HttpServletRequest request){
        String planType = request.getParameter("planType");
        model.addAttribute("planType", planType);
        model.addAttribute("advancedAdvice", DictionaryDataUtil.getId("ADVANCED_ADVICE"));
        model.addAttribute("projectId", WfUtil.createUUID());
        model.addAttribute("projectNumber", projectNumber);
        model.addAttribute("proName", proName);
        model.addAttribute("department", department);
        model.addAttribute("purchaseType", purchaseType);
        model.addAttribute("ids", ids);
        return "bss/ppms/advanced_project/attachment";
    }
    
    @RequestMapping("/transmit")
    public String transmit(Model model, String ids, String projectNumber, String proName, String name, String documentNumber,String id, String department, String purchaseType, HttpServletRequest request){
        //立项 
        AdvancedProject project = new AdvancedProject();
        String planType = request.getParameter("planType");
        project.setId(id);
        project.setName(proName);
        project.setProjectNumber(projectNumber);
        project.setPurchaseType(purchaseType);
        project.setPlanType(planType);
        project.setStatus(0);
        advancedProjectService.save(project);
        
        //下达
        Task task = new Task();
        task.setId(WfUtil.createUUID());
        task.setName(name);
        task.setDocumentNumber(documentNumber);
        task.setPurchaseRequiredId(department);
        task.setStatus(0);
        task.setIsDeleted(0);
        task.setGiveTime(new Date());
        task.setProcurementMethod(purchaseType);
        task.setTaskNature(1);
        task.setNotDetail(0);
        taskService.add(task);
        
        //中间表
        ProjectTask projectTask = new ProjectTask();
        projectTask.setProjectId(id);
        projectTask.setTaskId(task.getId());
        projectTaskService.insertSelective(projectTask);
        
        //项目明细
        int j = 1;
        String[] idss = ids.split(",");
        for (int i = 0; i < idss.length; i++ ) {
            PurchaseRequired purchaseRequired = purchaseRequiredService.queryById(idss[i]);
            AdvancedDetail detail = new AdvancedDetail();
            detail.setRequiredId(purchaseRequired.getId());
            detail.setSerialNumber(purchaseRequired.getSeq());
            detail.setDepartment(purchaseRequired.getDepartment());
            detail.setGoodsName(purchaseRequired.getGoodsName());
            detail.setStand(purchaseRequired.getStand());
            detail.setQualitStand(purchaseRequired.getQualitStand());
            detail.setItem(purchaseRequired.getItem());
            detail.setCreatedAt(new Date());
            if (purchaseRequired.getPurchaseCount() != null) {
                detail.setPurchaseCount(purchaseRequired.getPurchaseCount().doubleValue());
            }
            if (id != null) {
                detail.setAdvancedProject(id);
            }
            if (purchaseRequired.getPrice() != null) {
                detail.setPrice(purchaseRequired.getPrice().doubleValue());
            }
            if (purchaseRequired.getPlanNo() != null) {
                detail.setPlanNo(purchaseRequired.getPlanNo());
            }
            if (purchaseRequired.getBudget() != null) {
                detail.setBudget(purchaseRequired.getBudget().doubleValue());
            }
            if (purchaseRequired.getDeliverDate() != null) {
                detail.setDeliverDate(purchaseRequired.getDeliverDate());
            }
            if (purchaseRequired.getPurchaseType() != null) {
                detail.setPurchaseType(purchaseRequired.getPurchaseType());
            }
            if (purchaseRequired.getSupplier() != null) {
                detail.setSupplier(purchaseRequired.getSupplier());
            }
            if (purchaseRequired.getIsFreeTax() != null) {
                detail.setIsFreeTax(purchaseRequired.getIsFreeTax());
            }
            if (purchaseRequired.getGoodsUse() != null) {
                detail.setGoodsUse(purchaseRequired.getGoodsUse());
                project.setIsImport(1);
                advancedProjectService.update(project);
            }else{
                project.setIsImport(0);
                advancedProjectService.update(project);
            }
            if (purchaseRequired.getUseUnit() != null) {
                detail.setUseUnit(purchaseRequired.getUseUnit());
            }
            if (purchaseRequired.getParentId() != null) {
                detail.setParentId(purchaseRequired.getParentId());
            }
            detail.setStatus("2");
            detail.setPosition(j);
            j++;
            detailService.save(detail);
        }
        return "redirect:/task/list.html";
    }
    
    /**
     * 
     *〈文件上传〉
     *〈详细描述〉
     * @author Administrator
     * @param users
     * @param proName
     * @param userId
     * @param seq
     * @param orgName
     * @param orgId
     * @param kindName
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("download")
    public ResponseEntity<byte[]> download(@CurrentUser User users,String proName, String userId,String seq, String orgName, String orgId, String kindName,
            HttpServletRequest request) throws Exception {
        User user = userService.getUserById(userId);
        String userNames = users.getRelName();
        String userphone = users.getMobile();
        Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(orgId);
        // 文件存储地址
        String filePath = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/upload_file/");
        // 文件名称
        String fileName = createWordMethod(user, proName,userNames,userphone, orgName,orgnization, kindName, seq, request);
        // 下载后的文件名
        String downFileName = new String("预研通知书.doc".getBytes("UTF-8"),
                "iso-8859-1");// 为了解决中文名称乱码问题
        return service.downloadFile(fileName, filePath, downFileName);
    }
    
    
    
    
    
    /**
     * 
     *〈新增〉
     *〈新增预研项目内容和明细〉
     * @author Administrator
     * @param advancedProject
     * @param list
     * @param model
     * @param result
     * @param request
     * @return
     */
    @RequestMapping("/save")
    public String create(@Valid AdvancedProject advancedProject, String projectIds, PurchaseRequiredFormBean list, Model model, BindingResult result, HttpServletRequest request){
        //验证
        if(result.hasErrors()){
            List<FieldError> errors = result.getFieldErrors();
            for(FieldError fieldError:errors){
                model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
            }
            return "bss/ppms/advanced_project/add";
        }
        //新增项目信息
        if(projectIds != null){
            AdvancedProject project = advancedProjectService.selectById(projectIds);
            project.setCreateAt(new Date());
            project.setStatus(3);
            project.setName(advancedProject.getName());
            project.setProjectNumber(advancedProject.getProjectNumber());
            if(list.getList().get(0).getOrganization() != null){
                project.setPurchaseDep(new PurchaseDep(list.getList().get(0).getOrganization()));
            }
            if(list.getList().get(0).getGoodsUse() != null || list.getList().get(0).getUseUnit() != null){
                project.setIsImport(1);
            }else{
                project.setIsImport(0);
            }
            if(list.getList().get(0).getPlanType() != null){
                project.setPlanType(list.getList().get(0).getPlanType());
            }
            if(list.getList().get(0).getId() != null){
                project.setRequieredId(list.getList().get(0).getId());
            }
            project.setPurchaseType(list.getList().get(0).getPurchaseType());
            advancedProjectService.update(project);
        }
        
        //进入分包页面
        if(projectIds != null){
            HashMap<String, Object> map1 = new HashMap<>();
            AdvancedProject project = advancedProjectService.selectById(projectIds);
            map1.put("advancedProject", project.getId());
            List<AdvancedDetail> details = detailService.selectByAll(map1);
            model.addAttribute("project", project);
            model.addAttribute("kind", DictionaryDataUtil.find(5));
            model.addAttribute("list", details);
        }
        return "bss/ppms/advanced_project/package";
    }
    
    /**
     * 
     *〈跳出修改页面〉
     *〈详细描述〉
     * @author Administrator
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Model model, String id){
        HashMap<String, Object> map = new HashMap<String, Object>();
        AdvancedProject project = advancedProjectService.selectById(id);
        map.put("advancedProject", id);
        List<AdvancedDetail> details = detailService.selectByAll(map);
        for (AdvancedDetail advancedDetail : details) {
            Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(advancedDetail.getDepartment());
            model.addAttribute("orgnization", orgnization);
        }
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        model.addAttribute("lists", details);
        model.addAttribute("project", project);
        return "bss/ppms/advanced_project/edit";
    }
    
    
    @ResponseBody
    @RequestMapping("/verify")
    public String verify(String projectNumber, Model model){
        AdvancedProject project = new AdvancedProject();
        project.setProjectNumber(projectNumber);
        Boolean flag = advancedProjectService.SameNameCheck(project);
        return JSON.toJSONString(flag);
    }
    
    /**
     * 
     *〈更新〉
     *〈详细描述〉
     * @author Administrator
     * @param project
     * @param result
     * @param lists
     * @param model
     * @return
     */
    @RequestMapping("/update")
    public String update(@Valid AdvancedProject project, BindingResult result, PurchaseRequiredFormBean detail, Model model){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("advancedProject", project.getId());
        List<AdvancedDetail> details = detailService.selectByAll(map);
        //验证
        if(result.hasErrors()){
            List<FieldError> error = result.getFieldErrors();
            for (FieldError fieldError : error) {
                model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("kind", DictionaryDataUtil.find(5));
            model.addAttribute("lists", details);
            model.addAttribute("project", project);
            return "bss/ppms/advanced_project/edit";
        }
        //保存项目信息
        if(project != null){
            project.setPurchaseType(detail.getDetail().get(0).getPurchaseType());
            advancedProjectService.update(project);
        }
        //修改预研明细
        if(detail != null){
            if(detail.getDetail()!=null&&detail.getDetail().size()>0){
                for( AdvancedDetail aa:detail.getDetail()){
                    if( aa.getId()!=null){
                        detailService.update(aa);
                    }
                }
            }
        }
        return "redirect:list.html";
    }
    
    
    @RequestMapping("/view")
    public String view(String id, String ids, Model model, Integer page, HttpServletRequest request) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("projectId", id);
            List<AdvancedPackages> packages = packageService.selectByAll(map);
            if(packages != null && packages.size()>0){
                for(AdvancedPackages ps:packages){
                    int serialN = 0;
                    HashMap<String,Object> packageId = new HashMap<>();
                    packageId.put("packageId", ps.getId());
                    List<AdvancedDetail> detailList = detailService.selectByAll(packageId);
                    List<String> parentId = new ArrayList<>();
                    List<AdvancedDetail> newDetails = new ArrayList<>();
                    for(int i=0;i<detailList.size();i++){
                        if(!parentId.contains(detailList.get(i).getParentId())){
                            parentId.add(detailList.get(i).getParentId());
                            HashMap<String,Object> parentMap = new HashMap<>();
                            parentMap.put("projectId", id);
                            parentMap.put("id", detailList.get(i).getRequiredId());
                            List<AdvancedDetail> pList = detailService.selectByParent(parentMap);
                            newDetails.addAll(pList);
                        }else{
                            newDetails.add(detailList.get(i));
                        }
                    }
                    ComparatorDetails comparator = new ComparatorDetails();
                    Collections.sort(newDetails, comparator);
                    List<String> newParentId = new ArrayList<>();
                    for(int i=0;i<newDetails.size();i++){
                        HashMap<String,Object> detailMap = new HashMap<>();
                        detailMap.put("id",newDetails.get(i).getRequiredId());
                        detailMap.put("projectId", id);
                        List<AdvancedDetail> dlist = detailService.selectByParentId(detailMap);
                        if(dlist.size()>1){
                            HashMap<String,Object> dMap = new HashMap<>();
                            dMap.put("projectId", id);
                            dMap.put("id", newDetails.get(i).getRequiredId());
                            dMap.put("packageId", ps.getId());
                            List<AdvancedDetail> packDetails = detailService.findHavePackageIdDetail(dMap);
                            int budget = 0;
                            for (AdvancedDetail projectDetail : packDetails) {
                                budget += projectDetail.getBudget().intValue();
                            }
                            double money = budget;
                            newDetails.get(i).setBudget(money);
                        }
                        if(dlist.size()==1){
                            if(!newParentId.contains(newDetails.get(i).getParentId())){
                                serialN = 0;
                                newParentId.add(newDetails.get(i).getParentId());
                            }
                            char serialNum = (char) (97 + serialN);
                            newDetails.get(i).setSerialNumber("（"+serialNum+"）");
                            serialN ++;
                        }
                    }
                    ps.setAdvancedDetails(newDetails);
                }
            }else{
                map.put("advancedProject", id);
                List<AdvancedDetail> detail = detailService.selectByAll(map);
                model.addAttribute("lists", detail);
            }
            model.addAttribute("kind", DictionaryDataUtil.find(5));
            model.addAttribute("packageList", packages);
            return "bss/ppms/advanced_project/view";

    }
    
    /**
     * 
     *〈递归〉
     *〈详细描述〉
     * @author Administrator
     * @param response
     * @param request
     * @throws IOException
     */
    @RequestMapping("/checkProjectDetail")
    public void checkProjectDeail(HttpServletResponse response, HttpServletRequest request) throws IOException{
        String projectId = request.getParameter("projectId");
        HashMap<String,Object> map = new HashMap<String,Object>();
        String id = request.getParameter("id");
        AdvancedDetail details = detailService.selectById(id);
        if("1".equals(details.getParentId())){
            if(StringUtils.isNotBlank(projectId)){
                map.put("projectId", projectId);
            }
            map.put("id", details.getRequiredId());
            List<AdvancedDetail> list = detailService.selectByParentId(map);
            String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        }else{
            map.put("projectId", projectId);
            map.put("id", details.getRequiredId());
            List<AdvancedDetail> list = detailService.selectByParent(map);
            String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
    
    /**
     * 〈递归选中〉 
     * 〈详细描述〉
     * @author FengTian
     * @param response 内置对象
     * @param id 需求明细id
     * @param model 内置对象
     * @throws IOException 抛出异常
     */
    @RequestMapping("/checkDetail")
    public void checkDetail(HttpServletResponse response, String id, Model model)
        throws IOException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PurchaseRequired purchaseRequired = purchaseRequiredService.queryById(id);
        if ("1".equals(purchaseRequired.getParentId())) {
            map.put("id", purchaseRequired.getId());
            List<PurchaseRequired> list = purchaseRequiredService.selectByParentId(map);
            String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        }
        map.put("id", purchaseRequired.getId());
        List<PurchaseRequired> list = purchaseRequiredService.selectByParent(map);
        String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }
    
    @RequestMapping("/viewIds")
    public void viewIds(HttpServletResponse response,String id,String projectId) throws IOException {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("projectId", projectId);
            map.put("id", id);
            List<AdvancedDetail> list = detailService.selectByParent(map);
            String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
    }
    
    /**
     * 
     *〈删除明细〉
     *〈详细描述〉
     * @author Administrator
     * @param id
     * @param idss
     * @return
     */
    @RequestMapping("/deleted")
    public String deleted(String id, String idss, String projectIds){
        if(id != null){
            String[] ids = id.split(",");
            for (int i = 0; i < ids.length; i++ ) {
                AdvancedDetail detail = detailService.selectById(ids[i]);
                PurchaseRequired required = purchaseRequiredService.queryById(detail.getRequiredId());
                required.setAdvancedStatus(0);
                purchaseRequiredService.updateByPrimaryKeySelective(required);
                detailService.deleteById(ids[i]);
            }
            return "redirect:add.html";
        }else{
            if(idss != null){
                advancedProjectService.deleteById(projectIds);
                String[] ids = idss.split(",");
                for (int i = 0; i < ids.length; i++ ) {
                    AdvancedDetail detail = detailService.selectById(ids[i]);
                    PurchaseRequired required = purchaseRequiredService.queryById(detail.getRequiredId());
                    required.setAdvancedStatus(0);
                    purchaseRequiredService.updateByPrimaryKeySelective(required);
                    detailService.deleteById(ids[i]);
                }
            }
            return "redirect:/collect/list.html";
        }
    }
    
    /**
     * 
     *〈分包〉
     *〈详细描述〉
     * @author Administrator
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/subPackage")
    public String subPackage(HttpServletRequest request,Model model){
        String id = request.getParameter("id");
        HashMap<String,Object> map = new HashMap<>();
        map.put("advancedProject", id);
        //拿到一个项目所有的明细
        List<AdvancedDetail> details = detailService.selectByAll(map);
        //拿到packageId不为null的底层明细
        List<AdvancedDetail> bottomDetails = new ArrayList<>();//底层的明细
        List<String> parentIds = new ArrayList<>();
        for(AdvancedDetail detail:details){
            HashMap<String,Object> detailMap = new HashMap<>();
            detailMap.put("id",detail.getRequiredId());
            detailMap.put("projectId", id);
            List<AdvancedDetail> dlist = detailService.selectByParentId(detailMap);
            if(dlist.size()==1){
                bottomDetails.add(detail);
            }
        }
        String str = "";
        List<AdvancedDetail> showDetails = new ArrayList<>();
        for(int i=0;i<bottomDetails.size();i++){
            if(bottomDetails.get(i).getPackageId()==null){
                if(!parentIds.contains(bottomDetails.get(i).getParentId())){
                    str = "无";
                    parentIds.add(bottomDetails.get(i).getParentId());
                    HashMap<String,Object> detailMap = new HashMap<>();
                    detailMap.put("id",bottomDetails.get(i).getRequiredId());
                    detailMap.put("projectId", id);
                    List<AdvancedDetail> dlist = detailService.selectByParent(detailMap);
                    for(int j=dlist.size()-1;j>=0;j--){
                       showDetails.add(dlist.get(j));
                    }
                }else{
                    if(showDetails.size()!=0){
                        for(int j=0;j<showDetails.size();j++){
                            if(showDetails.get(j).getParentId().equals(bottomDetails.get(i).getParentId())){
                                showDetails.add(bottomDetails.get(i));
                                break;
                            }
                        }
                    }
                }
            }
            if(i==bottomDetails.size()-1){
                if(str.equals("")){
                    model.addAttribute("list", null);
                }else{
                    for(int j=0;j<showDetails.size();j++){
                        HashMap<String,Object> detailMap = new HashMap<>();
                        detailMap.put("id",showDetails.get(j).getRequiredId());
                        detailMap.put("projectId", id);
                        List<AdvancedDetail> dlist = detailService.selectByParentId(detailMap);
                        if(dlist.size()>1){
                            HashMap<String,Object> dMap = new HashMap<>();
                            dMap.put("projectId", id);
                            dMap.put("id", showDetails.get(j).getRequiredId());
                            List<AdvancedDetail> packDetails = detailService.findNoPackageIdDetail(dMap);
                            int budget = 0;
                            for (AdvancedDetail projectDetail : packDetails) {
                                budget += projectDetail.getBudget().intValue();
                            }
                            double money = budget;
                            showDetails.get(j).setBudget(money);
                        }
                    }
                    model.addAttribute("list", showDetails);
                }
            }
        }
        HashMap<String,Object> pack = new HashMap<>();
        pack.put("projectId", id);
        List<AdvancedPackages> packages = packageService.selectByAll(pack);
        if(packages.size()!=0){
            for(AdvancedPackages ps:packages){
                int serialN = 0;
                HashMap<String,Object> packageId = new HashMap<>();
                packageId.put("packageId", ps.getId());
                List<AdvancedDetail> detailList = detailService.selectByAll(packageId);
                List<String> parentId = new ArrayList<>();
                List<AdvancedDetail> newDetails = new ArrayList<>();
                for(int i=0;i<detailList.size();i++){
                    if(!parentId.contains(detailList.get(i).getParentId())){
                        parentId.add(detailList.get(i).getParentId());
                        HashMap<String,Object> parentMap = new HashMap<>();
                        parentMap.put("projectId", id);
                        parentMap.put("id", detailList.get(i).getRequiredId());
                        List<AdvancedDetail> pList = detailService.selectByParent(parentMap);
                        newDetails.addAll(pList);
                    }else{
                        newDetails.add(detailList.get(i));
                    }
                }
                ComparatorDetails comparator = new ComparatorDetails();
                Collections.sort(newDetails, comparator);
                List<String> newParentId = new ArrayList<>();
                for(int i=0;i<newDetails.size();i++){
                    HashMap<String,Object> detailMap = new HashMap<>();
                    detailMap.put("id",newDetails.get(i).getRequiredId());
                    detailMap.put("projectId", id);
                    List<AdvancedDetail> dlist = detailService.selectByParentId(detailMap);
                    if(dlist.size()>1){
                        HashMap<String,Object> dMap = new HashMap<>();
                        dMap.put("projectId", id);
                        dMap.put("id", newDetails.get(i).getRequiredId());
                        dMap.put("packageId", ps.getId());
                        List<AdvancedDetail> packDetails = detailService.findHavePackageIdDetail(dMap);
                        int budget = 0;
                        for (AdvancedDetail projectDetail : packDetails) {
                            budget += projectDetail.getBudget().intValue();
                        }
                        double money = budget;
                        newDetails.get(i).setBudget(money);
                    }
                    if(dlist.size()==1){
                        if(!newParentId.contains(newDetails.get(i).getParentId())){
                            serialN = 0;
                            newParentId.add(newDetails.get(i).getParentId());
                        }
                        char serialNum = (char) (97 + serialN);
                        newDetails.get(i).setSerialNumber("（"+serialNum+"）");
                        serialN ++;
                    }
                }
                ps.setAdvancedDetails(newDetails);
            }
        }
        String num = request.getParameter("num");
        model.addAttribute("packageList", packages);
        model.addAttribute("num", num);
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        AdvancedProject project = advancedProjectService.selectById(id);
        model.addAttribute("project", project);
        return "bss/ppms/advanced_project/package";
    }
    
    /**
     * 
     *〈添加分包〉
     *〈详细描述〉
     * @author Administrator
     * @param request
     */
    @RequestMapping("/addPack")
    @ResponseBody
    public void addPack(HttpServletRequest request){
        String[] id = request.getParameter("id").split(",");
        String projectId = request.getParameter("projectId");
        AdvancedProject project = advancedProjectService.selectById(projectId);
        HashMap<String,Object> pack = new HashMap<String,Object>();
        pack.put("projectId",projectId);
        List<AdvancedPackages> packList = packageService.selectByAll(pack);
        AdvancedPackages pg = new AdvancedPackages();
        pg.setName("第"+(packList.size()+1)+"包");
        pg.setProject(new AdvancedProject(projectId));
        pg.setIsDeleted(0);
        if(project.getIsImport()==1){
            pg.setIsImport(1);
        }else{
            pg.setIsImport(0);
        }
        pg.setPurchaseType(project.getPurchaseType());
        pg.setCreatedAt(new Date());
        pg.setUpdatedAt(new Date());
        packageService.save(pg);
        List<AdvancedPackages> wantPackId = packageService.selectByAll(pack);
        for(int i=0;i<id.length;i++){
            AdvancedDetail pDetail = detailService.selectById(id[i]);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id", pDetail.getRequiredId());
            map.put("projectId", projectId);
            List<AdvancedDetail> list = detailService.selectByParentId(map);
            if(list.size()==1){
                AdvancedDetail projectDetail = new AdvancedDetail();
                projectDetail.setId(id[i]);
                projectDetail.setPackageId(wantPackId.get(wantPackId.size()-1).getId());
                projectDetail.setUpdateAt(new Date());
                detailService.update(projectDetail);
            }
        }
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("packageId", wantPackId.get(wantPackId.size()-1).getId());
        List<AdvancedDetail> details = detailService.selectByAll(map);
        AdvancedPackages p = new AdvancedPackages();
        p.setId(wantPackId.get(0).getId());
        if(details.get(0).getStatus().equals("1")){
            p.setStatus(1);
            packageService.update(p);
        }else{
            p.setStatus(0);
            packageService.update(p);
        }
    }
    
    @RequestMapping("/addDetailById")
    @ResponseBody
    public void addDetailById(HttpServletRequest request){
         String[] id = request.getParameter("id").split(",");
         String packageId = request.getParameter("packageId");
         String projectId = request.getParameter("projectId");
         for(int i=0;i<id.length;i++){
            AdvancedDetail pDetail = detailService.selectById(id[i]);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id", pDetail.getRequiredId());
            map.put("projectId", projectId);
            List<AdvancedDetail> list = detailService.selectByParentId(map);
            if(list.size()==1){
                AdvancedDetail projectDetail = new AdvancedDetail();
                projectDetail.setId(id[i]);
                projectDetail.setPackageId(packageId);
                projectDetail.setUpdateAt(new Date());
                detailService.update(projectDetail);
            }
         }
    }
    
    @RequestMapping("/start")
    public String start(String id, String principal, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        AdvancedProject project = advancedProjectService.selectById(id);
        map.put("purchaseDepName", principal);
        List<PurchaseInfo> purchaseInfo = purchaseService.findPurchaseList(map);
        if(purchaseInfo != null && purchaseInfo.size()>0){
            String mobile = purchaseInfo.get(0).getMobile();
            project.setPrincipal(principal);
            project.setIpone(mobile);
            project.setStatus(1);
            project.setStartTime(new Date());
            advancedProjectService.update(project);
        }
        return "redirect:excute.html?id=" + project.getId();
    }
    
    @RequestMapping("/startProject")
    public String startProject(String id, Model model) {
        AdvancedProject project = advancedProjectService.selectById(id);
        if (project != null){
           List<PurchaseInfo> purchaseInfo = purchaseService.findPurchaseUserList(project.getPurchaseDepId());
           model.addAttribute("purchaseInfo", purchaseInfo);
        }
        model.addAttribute("project", project);
        model.addAttribute("dataIds", DictionaryDataUtil.getId("PROJECT_APPROVAL_DOCUMENTS"));
        return "bss/ppms/advanced_project/upload";
    }
    
    @RequestMapping("/excute")
    public String execute(String id, Model model, Integer page) {
        AdvancedProject project = advancedProjectService.selectById(id);
        model.addAttribute("project", project);
        model.addAttribute("url", "advancedProject/mplement.html?projectId="+id);
        return "bss/ppms/advanced_project/main";
    }
    
    @RequestMapping("/mplement")
    public String starts(String projectId, Model model) {
        String number = "";
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        AdvancedProject project = advancedProjectService.selectById(projectId);
        Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(project.getPurchaseDepId());
        map.put("projectId", projectId);
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("advancedProject", projectId);
        List<AdvancedDetail> details = detailService.selectByAll(map1);
        List<AdvancedPackages> list = packageService.selectByAll(map);
        if(list != null && list.size()>0){
            for(AdvancedPackages ps:list){
                HashMap<String,Object> packageId = new HashMap<String,Object>();
                packageId.put("packageId", ps.getId());
                List<AdvancedDetail> detailList = detailService.selectByAll(packageId);
                ps.setAdvancedDetails(detailList);
            }
        }
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        model.addAttribute("packageList", list);
        model.addAttribute("project", project);
        model.addAttribute("orgnization", orgnization);
        model.addAttribute("budgetAmount", details.get(0).getBudget());
        model.addAttribute("dataId", DictionaryDataUtil.getId("PROJECT_IMPLEMENT"));
        model.addAttribute("dataIds", DictionaryDataUtil.getId("PROJECT_APPROVAL_DOCUMENTS"));
        return "bss/ppms/advanced_project/essential_information";
    }
    
    /**
     *〈简述〉根据采购方式获取流程环节list
     *〈详细描述〉
     * @author Ye MaoLin
     * @param code 采购方式编码
     * @return 流程环节
     */
    public Map<String, Object> getFlowDefine(String purchaseTypeId, String projectId){
        HashMap<String, Object> map = new HashMap<String, Object>();
        FlowDefine fd = new FlowDefine();
        fd.setPurchaseTypeId(purchaseTypeId);
        //该采购方式定义的流程环节
        List<FlowDefine> fds = flowMangeService.find(fd);
        //该项目已执行的流程环节
        FlowExecute flowExecute = new FlowExecute();
        flowExecute.setProjectId(projectId);
        List<FlowExecute> flowExecutes = flowMangeService.findFlowExecute(flowExecute);
        //如果项目已开始实施执行
        if (flowExecutes != null && flowExecutes.size() > 0) {
            for (FlowDefine flowDefine : fds) {
                //将要执行的步骤
                Integer willStep = flowExecutes.get(0).getStep()+1;
                flowExecute.setFlowDefineId(flowDefine.getId());
                //获取该项目该环节的执行情况
                List<FlowExecute> flowExecutes2 = flowMangeService.findFlowExecute(flowExecute);
                if (flowExecutes2 != null && flowExecutes2.size() > 0) {
                    Integer s = flowExecutes2.get(0).getStatus();
                    if (s == 1) {
                        //已执行状态
                        flowDefine.setStatus(1);
                    } else if (s == 2) {
                        //执行中状态
                        flowDefine.setStatus(2);
                    }
                } else {
                    if (flowDefine.getStep() == willStep) {
                        //将要执行状态
                        flowDefine.setStatus(4);
                        map.put("url", flowDefine.getUrl()+"?projectId="+projectId+"&flowDefineId="+flowDefine.getId());
                    } else {
                        //未执行状态
                        flowDefine.setStatus(3);
                    }
                }
            }
            if (flowExecutes.get(0).getStep() == fds.size()) {
                fds.get(fds.size()-1).setStatus(4);
                map.put("url", fds.get(fds.size()-1).getUrl()+"?projectId="+projectId+"&flowDefineId="+fds.get(fds.size()-1).getId());
            }
        } else {
            //默认第一个为将要执行状态
            fds.get(0).setStatus(4);
            map.put("url", fds.get(0).getUrl()+"?projectId="+projectId+"&flowDefineId="+fds.get(0).getId());
        }
        if (map.get("url") == null || "".equals(map.get("url"))) {
            fds.get(0).setStatus(4);
            map.put("url", fds.get(0).getUrl()+"?projectId="+projectId+"&flowDefineId="+fds.get(0).getId());
        }
        map.put("fds", fds);
        return map;
    }
    
    
    @RequestMapping("/judgeNext")
    @ResponseBody
    public String judgeNext(HttpServletRequest request){
        String id = request.getParameter("projectId");
        HashMap<String,Object> map = new HashMap<>();
        map.put("advancedProject", id);
        //拿到一个项目所有的明细
        List<AdvancedDetail> details = detailService.selectByAll(map);
        List<AdvancedDetail> bottomDetails = new ArrayList<>();//底层的明细
        for(AdvancedDetail detail:details){
            HashMap<String,Object> detailMap = new HashMap<>();
            detailMap.put("id",detail.getRequiredId());
            detailMap.put("projectId", id);
            List<AdvancedDetail> dlist = detailService.selectByParentId(detailMap);
            if(dlist.size()==1){
                bottomDetails.add(detail);
            }
        }
        String str = "";
        for(int i=0;i<bottomDetails.size();i++){
            if(bottomDetails.get(i).getPackageId()==null){
                str = "0";
                break;
            }else if(i==bottomDetails.size()-1){
                str = "1";
            }
        }
        return str;
    }
  
    /**
     * 
     *〈修改包名〉
     *〈详细描述〉
     * @author Administrator
     * @param request
     */
    @RequestMapping("/editPackName")
    @ResponseBody
    public void editPackName(HttpServletRequest request){
        String name = request.getParameter("name");
        String id = request.getParameter("id");
        AdvancedPackages packages = new AdvancedPackages();
        packages.setId(id);
        packages.setName(name);
        packages.setUpdatedAt(new Date());
        packageService.update(packages);
    }
    
    /**
     * 
     *〈删除包下面的明细〉
     *〈详细描述〉
     * @author Administrator
     * @param request
     */
    @RequestMapping("/deleteDetailById")
    @ResponseBody
    public void deleteDetailById(HttpServletRequest request){
        String id = request.getParameter("id");
        String[] dId = request.getParameter("dId").split(",");
        for(int i=0;i<dId.length;i++){
            AdvancedDetail detail = new AdvancedDetail();
            detail.setId(dId[i]);
            detail.setPackageId("");
            detailService.update(detail);
        }
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("packageId", id);
        List<AdvancedDetail> detail = detailService.selectByAll(map);
        if(detail.size() == 0){
            AdvancedPackages packages = new AdvancedPackages();
            packages.setId(id);
            packages.setIsDeleted(1);
            packageService.update(packages);
        }
    }
    
    
    
    /**
     * 
     * 
     * @Title: createWordMethod
     * @author: lkzx
     * @date: 2016-9-7 下午3:25:38
     * @Description: TODO 生成word文件提供下载
     * @param: @param expert
     * @return: String
     * @throws Exception
     */
    private String createWordMethod(User user, String proName, String userNames, String userphone,String seq, Orgnization orgnization, String kindName, String orgName,HttpServletRequest request) throws Exception {
        /** 用于组装word页面需要的数据 */
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("seq", orgName == null ? "" : orgName);
        dataMap.put("linkman", user.getRelName() == null ? "" : user.getRelName());
        Date time = new Date();
        dataMap.put("time",time == null ? "" : new SimpleDateFormat("yyyy-MM-dd").format(time));
        dataMap.put("projectName", proName == null ? "" : proName);
        dataMap.put("purchaseType", kindName == null ? "" : kindName);
        dataMap.put("mobile", user.getMobile() == null ? "" : user.getMobile());
        dataMap.put("purchase", orgnization.getName() == null ? "" : orgnization.getName());
        dataMap.put("phone", user.getTelephone() == null ? "" : user.getTelephone());
        dataMap.put("department", seq == null ? "" : seq);
        dataMap.put("agent", userNames == null ? "" : userNames);
        dataMap.put("Iphone", userphone == null ? "" : userphone);
        // 文件名称
        String fileName = new String(("预研通知书.doc").getBytes("UTF-8"),
                "UTF-8");
        /** 生成word 返回文件名 */
        String newFileName = WordUtil.createWord(dataMap, "advanced.ftl",
                fileName, request);
        return newFileName;
    }
}
