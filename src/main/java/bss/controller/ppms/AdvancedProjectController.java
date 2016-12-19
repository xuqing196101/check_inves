package bss.controller.ppms;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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

import ses.model.bms.User;
import ses.model.oms.util.CommonConstant;
import ses.util.ComparatorDetail;
import ses.util.ComparatorDetails;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;

import common.annotation.CurrentUser;

import bss.controller.base.BaseController;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.AdvancedDetailService;
import bss.service.ppms.AdvancedPackageService;
import bss.service.ppms.AdvancedProjectService;

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
     *〈跳转新增页面〉
     *〈详细描述〉
     * @author Administrator
     * @param model
     * @return
     */
    @RequestMapping("/add")
    public String add(Model model, String name, String projectNumber, String projectId, PurchaseRequired purchaseRequired, Integer page, HttpServletRequest request){
        //查询状态为4的需求计划
        purchaseRequired.setStatus("4");
        purchaseRequired.setIsMaster(1);
        List<PurchaseRequired> list = purchaseRequiredService.query(purchaseRequired, page == null ? 1 : page);
        PageInfo<PurchaseRequired> info = new PageInfo<>(list);
        if(StringUtils.isNotBlank(projectId)){
            HashMap<String,Object> detailMap=new HashMap<String,Object>();
            List<AdvancedDetail> details = new ArrayList<>();
            detailMap.put("status",  "2");
            detailMap.put("advancedProject",  projectId);
            List<AdvancedDetail> advance = detailService.selectByAll(detailMap);
            if(advance != null && advance.size() > 0){
                int bud = 0;
                for (AdvancedDetail advancedDetail : advance) {
                    detailMap.put("id", advancedDetail.getRequiredId());
                    List<AdvancedDetail> lists = detailService.selectByParentId(detailMap);
                    if(lists.size() == 1){//查询最底层明细的金额
                        for (AdvancedDetail detail : lists) {
                            bud+=detail.getBudget().intValue();
                        }
                    }
                    details.add(advancedDetail);
                }
                List<AdvancedDetail> list1 = new ArrayList<>();
                for (AdvancedDetail advancedDetail : details) {
                    detailMap.put("id", advancedDetail.getRequiredId());
                    List<AdvancedDetail> lists = detailService.selectByParentId(detailMap);
                    if(lists.size() > 1){
                        advancedDetail.setBudget(Double.valueOf(bud));
                        detailService.update(advancedDetail);
                    }
                    list1.add(advancedDetail);
                }
                model.addAttribute("lists", list1);
                model.addAttribute("projectIds", projectId);
            }
        }else{
            String projectIds = WfUtil.createUUID();
            AdvancedProject project = new AdvancedProject();
            project.setId(projectIds);
            advancedProjectService.save(project);
            model.addAttribute("projectId", projectIds);
        }
        model.addAttribute("info", info);
        model.addAttribute("dic", DictionaryDataUtil.findById("6"));
        return "bss/ppms/advanced_project/add";
    }
    
    /**
     * 
     *〈跳转新增明细页面〉
     *〈详细描述〉
     * @author Administrator
     * @param model
     * @param project
     * @param id
     * @return
     */
    @RequestMapping("/addDetail")
    public String addDeatil(Model model, AdvancedProject project, String projectId, String id){
        HashMap<String, Object> map = new HashMap<>();
        map.put("planNo", id);
        //查询计划明细
        List<PurchaseRequired> list = purchaseRequiredService.getByMap(map);
        model.addAttribute("list", list);
        model.addAttribute("project", project);
        model.addAttribute("projectId", projectId);
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        return "bss/ppms/advanced_project/addDetail";
    }
    
    @RequestMapping("/saveDetail")
    public String saveDetail(Model model, String id, String name, String projectNumber, String projectId, HttpServletRequest request){
        if(id.trim().length()!=0){
            String[] detailIds = id.split(",");
            Map<String,Object> detailMap=new HashMap<String,Object>();
            detailMap.put("id",  detailIds[0]);
            List<AdvancedDetail> advance = detailService.selectByParentId(detailMap);
            //取到同一个父节点下面的子节点
            String parId=null ;
            if(advance.size() > 0){
                for (int i = 0; i < detailIds.length; i++ ) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    PurchaseRequired purchaseRequired = purchaseRequiredService.queryById(detailIds[i]);
                    AdvancedDetail detail = detailService.selectByRequiredId(detailIds[i]);
                    map.put("id", purchaseRequired.getId());
                    List<PurchaseRequired> lists = purchaseRequiredService.selectByParentId(map);
                    if(lists.size() == 1){//查询最底层明细的节点
                        for (PurchaseRequired purchaseRequired2 : lists) {
                            if(detail != null){
                                if(!detail.getRequiredId().equals(purchaseRequired2.getId())){
                                    parId = purchaseRequired2.getParentId();
                                }
                            }else{
                                parId = purchaseRequired2.getParentId();
                            }
                            
                            
                            
                        }
                    }
                }
            }
            //第二次添加
            
            if(advance.size()>0){
               int xx= advance.size()+1;
                for(String pid:detailIds){
                    PurchaseRequired required = purchaseRequiredService.queryById(pid);
                    required.setAdvancedStatus(1);
                    purchaseRequiredService.updateByPrimaryKeySelective(required);
                    if(required.getParentId().equals(parId)){
                        insertDeatil(required,xx,projectId);
                    } 
                } 
            }else{
                //第一次添加
                HashMap<String, Object> map = new HashMap<String, Object>();
                List<PurchaseRequired> list  = new ArrayList<>();
               
                String[] ids = id.split(",");
                int bud = 0;
                for (int i = 0; i < ids.length; i++ ) {
                    PurchaseRequired purchaseRequired = purchaseRequiredService.queryById(ids[i]);
                    map.put("id", purchaseRequired.getId());
                    List<PurchaseRequired> lists = purchaseRequiredService.selectByParentId(map);
                    if(lists.size() == 1){//查询最底层明细的金额
                        for (PurchaseRequired purchaseRequired2 : lists) {
                            bud+=purchaseRequired2.getBudget().intValue();
                            purchaseRequired2.setAdvancedStatus(1);
                            purchaseRequiredService.updateByPrimaryKeySelective(purchaseRequired2);
                        }
                    }
                    list.add(purchaseRequired);
                }
                List<PurchaseRequired> list1 = new ArrayList<>();
                for (PurchaseRequired purchaseRequired2 : list) {
                    map.put("id", purchaseRequired2.getId());
                    List<PurchaseRequired> lists = purchaseRequiredService.selectByParentId(map);
                    if(lists.size() > 1){
                        purchaseRequired2.setBudget(new BigDecimal(bud));
                    }
                    list1.add(purchaseRequired2);
                }
                //新增项目明细
                int i=1;
                if(list1 != null && list1.size() > 0){
                    for (PurchaseRequired purchaseRequired : list1) {
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
                        if (projectId != null) {
                            detail.setAdvancedProject(projectId);
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
                        }
                        if (purchaseRequired.getUseUnit() != null) {
                            detail.setUseUnit(purchaseRequired.getUseUnit());
                        }
                        if (purchaseRequired.getParentId() != null) {
                            detail.setParentId(purchaseRequired.getParentId());
                        }
                        detail.setStatus("2");
                        detail.setPosition(i);
                        i++;
                        detailService.save(detail);
                    }
                }
            }
            
        }
        
        return "redirect:add.html?name="+name+"&projectNumber="+projectNumber+"&projectId="+projectId;
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
    public void viewIds(HttpServletResponse response,String id) throws IOException {
            HashMap<String, Object> map = new HashMap<String, Object>();
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
        model.addAttribute("packageList", packages);
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
        AdvancedPackages packages = new AdvancedPackages();
        packages.setName("第"+(packList.size()+1)+"包");
        packages.setProject(new AdvancedProject(projectId));
        packages.setIsDeleted(0);
        if(project.getIsImport()==1){
            packages.setIsImport(1);
        }else{
            packages.setIsImport(0);
        }
        packages.setPurchaseType(project.getPurchaseType());
        packages.setCreatedAt(new Date());
        packages.setUpdatedAt(new Date());
        packageService.save(packages);
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
                projectDetail.setPackageId(wantPackId.get(0).getId());
                projectDetail.setUpdateAt(new Date());
                detailService.update(projectDetail);
            }
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
    
    public void insertDeatil(PurchaseRequired purchaseRequired,Integer positon,String projectId){
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
        if (purchaseRequired.getPrice() != null) {
            detail.setPrice(purchaseRequired.getPrice().doubleValue());
        }
        if (projectId != null) {
            detail.setAdvancedProject(projectId);
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
        }
        if (purchaseRequired.getUseUnit() != null) {
            detail.setUseUnit(purchaseRequired.getUseUnit());
        }
        if (purchaseRequired.getParentId() != null) {
            detail.setParentId(purchaseRequired.getParentId());
        }
        detail.setStatus("2");
        detail.setPosition(positon);
        detailService.save(detail);
        
    }
}
