package bss.controller.ppms;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ses.model.bms.User;
import ses.model.oms.util.CommonConstant;
import ses.util.DictionaryDataUtil;

import common.annotation.CurrentUser;

import bss.controller.base.BaseController;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.AdvancedProject;
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
        if(user != null && user.getOrg().getId() != null){
            PageHelper.startPage(page.getPageNum(),CommonConstant.PAGE_SIZE);
            List<AdvancedProject> list = advancedProjectService.selectByList(advancedProject);
            model.addAttribute("list", new PageInfo<AdvancedProject>(list));
            model.addAttribute("project", advancedProject);
        }
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
    public String add(Model model, String name, String projectNumber, String planNo, PurchaseRequired purchaseRequired, Integer page, HttpServletRequest request){
        //查询状态为4的需求计划
        purchaseRequired.setStatus("4");
        purchaseRequired.setIsMaster(1);
        List<PurchaseRequired> list = purchaseRequiredService.query(purchaseRequired, page == null ? 1 : page);
        PageInfo<PurchaseRequired> info = new PageInfo<>(list);
        /*if(StringUtils.isNotBlank(id)){
            List<AdvancedDetail> lists = new ArrayList<>();
            String[] ids = id.split(",");
            for (int i = 0; i < ids.length; i++ ) {
                AdvancedDetail detail = detailService.selectById(ids[i]);
                lists.add(detail);
            }
            model.addAttribute("lists", lists);
            model.addAttribute("detailId", id);
            if(StringUtils.isNotBlank(name)){
                model.addAttribute("name", name);
            }
            if(StringUtils.isNotBlank(projectNumber)){
                model.addAttribute("projectNumber", projectNumber);
            }
        }*/
        if(StringUtils.isNotBlank(planNo)){
            HashMap<String,Object> detailMap=new HashMap<String,Object>();
            List<AdvancedDetail> details = new ArrayList<>();
            detailMap.put("status",  "2");
            detailMap.put("planNo",  planNo);
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
            }
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
    public String addDeatil(Model model, AdvancedProject project, String id){
        HashMap<String, Object> map = new HashMap<>();
        map.put("planNo", id);
        //查询计划明细
        List<PurchaseRequired> list = purchaseRequiredService.getByMap(map);
        model.addAttribute("list", list);
        model.addAttribute("project", project);
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        return "bss/ppms/advanced_project/addDetail";
    }
    
    @RequestMapping("/saveDetail")
    public String saveDetail(Model model, String id, String name, String projectNumber, HttpServletRequest request){
        String planNo="";
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
                        insertDeatil(required,xx);
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
                        planNo=purchaseRequired.getPlanNo();
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
        
        return "redirect:add.html?name="+name+"&projectNumber="+projectNumber+"&planNo="+planNo;
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
    public String create(@Valid AdvancedProject advancedProject, PurchaseRequiredFormBean list, Model model, BindingResult result, HttpServletRequest request){
        //验证
        if(result.hasErrors()){
            List<FieldError> errors = result.getFieldErrors();
            for(FieldError fieldError:errors){
                model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
            }
            return "bss/ppms/advanced_project/add";
        }
        //新增项目信息
        String projectId = null;
        if(advancedProject != null){
            advancedProject.setCreateAt(new Date());
            advancedProject.setStatus(3);
            if(list.getList().get(0).getGoodsUse() != null || list.getList().get(0).getUseUnit() != null){
                advancedProject.setIsImport(1);
            }else{
                advancedProject.setIsImport(0);
            }
            if(list.getList().get(0).getPlanType() != null){
                advancedProject.setPlanType(list.getList().get(0).getPlanType());
            }
            if(list.getList().get(0).getId() != null){
                advancedProject.setRequieredId(list.getList().get(0).getId());
            }
            advancedProject.setPurchaseType(list.getList().get(0).getPurchaseType());
            advancedProjectService.save(advancedProject);
            projectId = advancedProject.getId();
        }
        String detailId = request.getParameter("detailId");
        
        
        //进入分包页面
        if(projectId != null){
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", detailId);
            List<AdvancedDetail> list2 = detailService.selectByParentId(map);
            for (AdvancedDetail advancedDetail : list2) {
                advancedDetail.setAdvancedProject(new AdvancedProject(projectId));
                detailService.update(advancedDetail);
            }
            HashMap<String, Object> map1 = new HashMap<>();
            AdvancedProject project = advancedProjectService.selectById(projectId);
            map1.put("ids", project.getId());
            List<AdvancedDetail> details = detailService.selectByAll(map1);
            model.addAttribute("project", project);
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
    public String update(@Valid AdvancedProject project, BindingResult result, PurchaseRequiredFormBean lists, Model model){
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
            project.setPurchaseType(lists.getDetail().get(0).getPurchaseType());
            advancedProjectService.update(project);
        }
        //修改预研明细
        if(lists != null){
            if(lists.getDetail()!=null&&lists.getDetail().size()>0){
                for( AdvancedDetail detail:lists.getDetail()){
                    if( detail.getId()!=null){
                        detailService.update(detail);
                    }
                }
            }
        }
        return "redirect:list.html";
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
                map.put("advancedProject", projectId);
            }
            map.put("id", details.getRequiredId());
            List<AdvancedDetail> list = detailService.selectByParentId(map);
            String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        }else{
            map.put("advancedProject", projectId);
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
    
    
    @RequestMapping("/deleted")
    public String deleted(String id, String idss){
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
  
    
    
    public void insertDeatil(PurchaseRequired purchaseRequired,Integer positon){
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
