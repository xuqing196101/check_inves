package bss.controller.prms;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import common.constant.StaticVariables;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.ems.ProjectExtract;
import ses.model.sms.Quote;
import ses.model.sms.Supplier;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpExtractRecordService;
import ses.service.ems.ExpertService;
import ses.service.ems.ProjectExtractService;
import ses.service.sms.SupplierQuoteService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;

import bss.controller.base.BaseController;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.MarkTerm;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.SaleTender;
import bss.model.ppms.ScoreModel;
import bss.model.prms.ExpertScore;
import bss.model.prms.FirstAudit;
import bss.model.prms.PackageExpert;
import bss.model.prms.PackageFirstAudit;
import bss.model.prms.ReviewFirstAudit;
import bss.model.prms.ReviewProgress;
import bss.model.prms.SupplierRank;
import bss.model.prms.ext.ExpertSuppScore;
import bss.model.prms.ext.Extension;
import bss.model.prms.ext.PackExpertExt;
import bss.model.prms.ext.SupplierExt;
import bss.service.ppms.AdvancedDetailService;
import bss.service.ppms.AdvancedPackageService;
import bss.service.ppms.AdvancedProjectService;
import bss.service.ppms.BidMethodService;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.MarkTermService;
import bss.service.ppms.SaleTenderService;
import bss.service.ppms.ScoreModelService;
import bss.service.prms.ExpertScoreService;
import bss.service.prms.FirstAuditService;
import bss.service.prms.PackageExpertService;
import bss.service.prms.PackageFirstAuditService;
import bss.service.prms.ReviewFirstAuditService;
import bss.service.prms.ReviewProgressService;

@Controller
@Scope("prototype")
@RequestMapping("/adPackageExpert")
public class AdPackageExpertController extends BaseController {
    
    @Autowired
    private AdvancedProjectService projectService;
    
    @Autowired
    private ExpExtractRecordService expExtractRecordService;
    
    @Autowired
    private PackageExpertService packageExpertService;
    
    @Autowired
    private AdvancedPackageService packageService;
    
    @Autowired
    private ProjectExtractService projectExtractService;
    
    @Autowired
    private FlowMangeService flowMangeService;
    
    @Autowired
    private UserServiceI userService;
    
    @Autowired
    private ExpertService expertService;
    
    @Autowired
    private SupplierQuoteService supplierQuoteService;
    
    @Autowired
    private SaleTenderService saleTenderService;
    
    @Autowired
    private AdvancedDetailService detailService;
    
    @Autowired
    private FirstAuditService firstAuditService;
    
    @Autowired
    private ReviewFirstAuditService reviewFirstAuditService;
    
    @Autowired
    private PackageFirstAuditService packageFirstAuditService;
    
    @Autowired
    private SupplierService supplierService;
    
    @Autowired
    private ReviewProgressService reviewProgressService;
    
    @Autowired
    private ExpertScoreService expertScoreService;
    
    @Autowired
    private ScoreModelService scoreModelService;
    
    @Autowired
    private MarkTermService markTermService;
    
    @Autowired 
    private BidMethodService bidMethodService;
    
    private final static Short NUMBER_TWO = 2;
    
    private final static short ONE = 1;
    
    /**
     * 
     *〈组织专家评审〉
     *〈详细描述〉
     * @author FengTian
     * @param model
     * @param projectId
     * @param flowDefineId
     * @return
     */
    @RequestMapping("/auditManage")
    public String auditManage(Model model, String projectId, String flowDefineId){
        AdvancedProject project = projectService.selectById(projectId);
        if(project != null){
            DictionaryData findById = DictionaryDataUtil.findById(project.getPurchaseType());
            model.addAttribute("kind", findById.getCode());
            model.addAttribute("projectId", project.getId());
        }
        model.addAttribute("flowDefineId", flowDefineId);
        return "bss/ppms/advanced_project/audit/manage";
    }
    
    /**
     * 
     *〈专家名单〉
     *〈详细描述〉
     * @author FengTian
     * @param projectId
     * @param model
     * @param flowDefineId
     * @return
     */
    @RequestMapping("/assignedExpert")
    public String assignedExpert(String projectId, Model model, String flowDefineId) {
        //项目实体
        AdvancedProject project = projectService.selectById(projectId);
        model.addAttribute("project", project);
        model.addAttribute("flowDefineId", flowDefineId);
        
        //专家类型
        List<DictionaryData> data = expExtractRecordService.ddList();
        model.addAttribute("ddList", data);
        String ddJson = JSON.toJSONString(data);
        model.addAttribute("ddJson", ddJson);
        
        //评审类型
        List<DictionaryData> datas = DictionaryDataUtil.find(23);
        model.addAttribute("reviewTypes" , datas);
        
        //查询该项目下专家是否签到
        HashMap<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        List<PackageExpert> selectList = packageExpertService.selectList(map);
        if(selectList != null && selectList.size() > 0){
            List<AdvancedPackages> selectByAll = packageService.selectByAll(map);
            if(selectByAll != null && selectByAll.size() > 0){
                for (AdvancedPackages advancedPackages : selectByAll) {
                    DictionaryData dd = DictionaryDataUtil.findById(advancedPackages.getProjectStatus());
                    if(dd != null){
                        advancedPackages.setProjectStatus(dd.getCode());
                    }
                }
                model.addAttribute("packages", selectByAll);
                model.addAttribute("expertSigneds", selectList);
                model.addAttribute("isEndSigin", "1");
                return "bss/ppms/advanced_project/audit/expert_list_view";
            }
        } else {
            List<AdvancedPackages> selectByAll = packageService.listProjectExtract(projectId);
            if(selectByAll != null && selectByAll.size() > 0){
                for (AdvancedPackages advancedPackages : selectByAll) {
                    DictionaryData dd = DictionaryDataUtil.findById(advancedPackages.getProjectStatus());
                    if(dd != null){
                        advancedPackages.setProjectStatus(dd.getCode());
                    }
                }
                model.addAttribute("isEndSigin", "0");
                // 包信息
                model.addAttribute("packageList", selectByAll);
                return "bss/ppms/advanced_project/audit/expert_list";
            }    
        }
        return null;
    }
    
    @RequestMapping("/showExpert")
    public String showExpert(Model model, String packageId,String flowDefineId,String execute) {
        // 项目抽取的专家信息
        ProjectExtract projectExtract = new ProjectExtract();
        projectExtract.setProjectId(packageId);
        projectExtract.setIsProvisional((short)1);
        projectExtract.setReason("1");
        List<ProjectExtract> expertList = projectExtractService.list(projectExtract);
        model.addAttribute("expertList", expertList);
        model.addAttribute("packageId", packageId);
        model.addAttribute("flowDefineId", flowDefineId);
        //专家类型
        model.addAttribute("ddList", expExtractRecordService.ddList());
        model.addAttribute("execute", execute);
        return "bss/ppms/advanced_project/audit/list";
    }
    
    /**
     * 
     *〈添加专家,并设置组长〉
     *〈详细描述〉
     * @author FengTian
     * @param packageId
     * @param groupId
     * @param attr
     * @param sq
     * @param flowDefineId
     * @return
     */
    @RequestMapping("relate")
    public String relate(String packageId, String groupId, HttpServletRequest sq, String flowDefineId) {
        if(StringUtils.isNotBlank(packageId)){
            AdvancedPackages packages = packageService.selectById(packageId);
            if(packages != null){
                ProjectExtract projectExtract = new ProjectExtract();
                projectExtract.setProjectId(packageId);
                projectExtract.setIsProvisional((short)1);
                projectExtract.setReason("1");
                List<ProjectExtract> expertList = projectExtractService.list(projectExtract);
                if(expertList != null && expertList.size() > 0){
                    PackageExpert packageExpert = null;
                    for (ProjectExtract extract : expertList) {
                        packageExpert = new PackageExpert();
                        // 设置专家id
                        packageExpert.setExpertId(extract.getExpert().getId());
                        packageExpert.setPackageId(packageId);
                        packageExpert.setProjectId(packages.getProjectId());
                        // 评审状态 未评审
                        packageExpert.setIsAudit((short) 0);
                        // 初审是否汇总 未汇总
                        packageExpert.setIsGather((short) 0);
                        // 是否评分
                        packageExpert.setIsGrade((short) 0);
                        // 评分是否汇总
                        packageExpert.setIsGatherGather((short) 0);
                        // 判断组长id是否和选择的专家id一致，如果一致就设定为组长
                        if (groupId.equals(extract.getExpert().getId())) {
                            packageExpert.setIsGroupLeader((short) 1);
                        } else {
                            packageExpert.setIsGroupLeader((short) 0);
                        }

                        Map<String, Object> maps = new HashMap<String, Object>();
                        maps.put("expertId", extract.getExpert().getId());
                        maps.put("packageId", packageId);
                        List<PackageExpert> selectList2 = packageExpertService.selectList(maps);
                        if (selectList2 != null && selectList2.size() != 0 ){
                            //如果和本次相同就不进行修改
                            if (selectList2.get(0).getIsGroupLeader() != packageExpert.getIsGroupLeader()){
                                packageExpertService.updateByBean(packageExpert);
                            }  
                        } else {
                            packageExpertService.save(packageExpert);
                        }
                    }
                    //修改流程状态
                    flowMangeService.flowExe(sq, flowDefineId, packages.getProjectId(), 2);
                }
                return "redirect:/assignedExpert.html?projectId=" + packages.getProjectId() + "&flowDefineId=" + flowDefineId;
            }
        }
        return null;
    }
    
    @ResponseBody
    @RequestMapping("/executeFinish")
    public String executeFinish(String projectId, String flowDefineId ,HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        map.put("isGroupLeader", 1);
        //获取关联包的专家
        List<PackageExpert> selectList = packageExpertService.selectList(map);
        List<AdvancedPackages> selectByAll = packageService.selectByAll(map);
        Set<PackageExpert> set = new HashSet<PackageExpert>(selectList);
        selectList.clear();
        selectList.addAll(set);
        if (selectList.size() == selectByAll.size()){
            flowMangeService.flowExe(request, flowDefineId, projectId, 1);
        }else{
            return JSON.toJSONString("ERROR");
        }
        return JSON.toJSONString("SCCUESS");
    }
    
    @RequestMapping("/endSignIn")
    public void endSignIn(HttpServletResponse response, HttpServletRequest request, PurchaseRequiredFormBean packageExperts, String flowDefineId, String projectId) throws IOException {
        try {
            // 项目分包信息
            HashMap<String, Object> pack = new HashMap<String, Object>();
            pack.put("projectId", projectId);
            //pack.put("projectStatus", "close");
            List<AdvancedPackages> selectByAll = packageService.selectByAll(pack);
            if(selectByAll != null && selectByAll.size() > 0){
                String msg = "";
                int flag = 0;
                List<PackageExpert> packageExperts2 = packageExperts.getPackageExperts();
                for (AdvancedPackages packages : selectByAll) {
                    int count = 0;
                    if(packageExperts2 != null && packageExperts2.size() > 0){
                        for (int i = 1; i < packageExperts2.size(); i++) {
                            PackageExpert packageExpert = packageExperts2.get(i);
                            //校验每包组长数量
                            if (packages.getId().equals(packageExpert.getPackageId()) && packageExpert.getIsGroupLeader() == 1) {
                                count ++;
                            }
                            //校验组长必须签到
                            if (packages.getId().equals(packageExpert.getPackageId()) && packageExpert.getIsGroupLeader() == 1 && packageExpert.getIsSigin() == 0) {
                                msg += "【"+packages.getName()+"】请选择已到场的专家作为组长.";
                                flag = 1;
                            }
                            //临时专家字段校验
                            if (packages.getId().equals(packageExpert.getPackageId()) && packageExpert.getIsTempExpert() == 0) {
                                Expert expert = packageExpert.getExpert();
                                if (expert != null ) {
                                    if (StringUtils.isBlank(expert.getRelName()) || StringUtils.isBlank(expert.getIdNumber()) || StringUtils.isBlank(expert.getMobile()) || StringUtils.isBlank(expert.getAtDuty())) {
                                        msg += "【"+packages.getName()+"】临时专家填写项不能为空.";
                                        flag = 1;
                                    }
                                    List<User> users = userService.findByLoginName(expert.getMobile());
                                    if (users.size() > 0) {
                                        msg += "已存在【"+expert.getRelName()+"】手机号的用户名";
                                        flag = 1;
                                    }
                                }
                            }
                        }
                    }
                    
                    if (count == 0) {
                        if(packageExperts2 != null && packageExperts2.size() > 0){
                            msg += "【"+packages.getName()+"】请设置组长";
                            flag = 1;
                        } else {
                            msg += "【"+packages.getName()+"】请添加专家";
                            flag = 1;
                        }
                        
                    }
                    if (count > 1) {
                        msg += "【"+packages.getName()+"】请设置一个组长";
                        flag = 1;
                    }
                }
                if (flag == 1) {
                    response.setContentType("text/html;charset=utf-8");
                    response.getWriter().print("{\"success\": " + false + ", \"msg\": \"" + msg + "\"}");
                }
                if (flag == 0) {
                    for (int i = 1; i < packageExperts2.size(); i++) {
                        PackageExpert packageExpert = packageExperts2.get(i);
                        //保存到场签到的专家
                        if (packageExpert.getIsSigin() == 1 && packageExpert.getIsTempExpert() == 1) {
                            packageExpert.setIsAudit((short)0);
                            packageExpert.setIsGather((short)0);
                            packageExpert.setIsGrade((short)0);
                            packageExpert.setIsGatherGather((short)0);
                            String tempTypeId = packageExpert.getReviewTypeId();
                            DictionaryData tempdd = DictionaryDataUtil.findById(tempTypeId);
                            //技术类型
                            if ("GOODS".equals(tempdd.getCode()) || "PROJECT".equals(tempdd.getCode()) || "SERVICE".equals(tempdd.getCode())) {
                                packageExpert.setReviewTypeId(DictionaryDataUtil.getId("TECHNOLOGY"));
                            }
                            //经济类型
                            if ("GOODS_SERVER".equals(tempdd.getCode()) || "GOODS_PROJECT".equals(tempdd.getCode())) {
                                packageExpert.setReviewTypeId(DictionaryDataUtil.getId("ECONOMY"));
                            }
                            packageExpertService.save(packageExpert);
                        }
                    }
                    AdvancedProject project = projectService.selectById(projectId);
                    project.setStatus(DictionaryDataUtil.getId("ZJQDWC"));
                    projectService.update(project);
                    msg = "签到完成";
                    response.setContentType("text/html;charset=utf-8");
                    response.getWriter().print("{\"success\": " + true + ", \"msg\": \"" + msg + "\"}");
                }
                response.getWriter().flush();
            }
            //该环节设置为执行中
            flowMangeService.flowExe(request, flowDefineId, projectId, 2);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            response.getWriter().close();
        }
    }
    
    /**
     * 引用临时专家列表页面
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/gotoCiteExpertView")
    public String gotoCiteExpertView(Model model, HttpServletRequest request, HttpServletResponse response){
        String page = request.getParameter("page");
        String packageId = request.getParameter("packageId");
        String selectValue = request.getParameter("selectValue");
        String expertName = request.getParameter("expertName");
        String expertMobile = request.getParameter("expertMobile");
        String projectId = request.getParameter("projectId");
        Expert expert = new Expert();
        expert.setIsProvisional((short)1);//临时
        expert.setStatus("4");//待审核
        if(!StringUtils.isEmpty(expertName)){
            expert.setRelName(expertName);
        }
        if(!StringUtils.isEmpty(expertMobile)){
            expert.setMobile(expertMobile);
        }
        expert.setIsDelete((short)0);
        List<Expert> experts = expertService.findCiteExpertByCondition(expert, packageId, page == null || page.equals("") ? 1 : Integer.valueOf(page));
        //专家类型
        List<DictionaryData> dd1 = expExtractRecordService.ddList();
        model.addAttribute("list", new PageInfo<>(experts));
        model.addAttribute("packageId", packageId);
        model.addAttribute("ddList", dd1);
        model.addAttribute("selectValue", selectValue);
        model.addAttribute("projectId", projectId);
        if(null != expertName){
            model.addAttribute("expertName", expertName);
        }
        if(null != expertMobile){
            model.addAttribute("expertMobile", expertMobile);
        }
        return "bss/ppms/advanced_project/audit/expert_cite_list";
    }
    
    /**
     * 
     *〈供应商报价〉
     *〈详细描述〉
     * @author FengTian
     * @param req
     * @param projectId
     * @param model
     * @param flowDefineId
     * @return
     * @throws ParseException
     */
    @RequestMapping("/toSupplierQuote")
    public String toSupplierQuote(HttpServletRequest req, String projectId, Model model, String flowDefineId) throws ParseException {
        boolean flag = true;
        Quote condition = new Quote();
        condition.setProjectId(projectId);
        List<Quote> condition2 =  supplierQuoteService.getAllQuote(condition, 1);
        if (condition2 != null && condition2.size() > 0) {
           if (condition2.get(0).getQuotePrice() == null) {
               flag = false;
           }
        }
        AdvancedProject project = projectService.selectById(projectId);
        DictionaryData dictionaryData = null;
        if (project != null && StringUtils.isNotBlank(project.getPurchaseType())){
            dictionaryData = DictionaryDataUtil.findById(project.getPurchaseType());
        }
        if (flag) {
            Quote quote2 = new Quote();
            Quote quote1 = new Quote();
            Quote quote=new Quote();
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("projectId", projectId);
            SaleTender st = new SaleTender();
            st.setProjectId(projectId);
            StringBuilder sb = new StringBuilder("");
            List<SaleTender> saleTenderList = saleTenderService.find(st);
            for (SaleTender saleTender : saleTenderList) {
                sb.append(saleTender.getPackages());
            }
            List<AdvancedPackages> listPack = packageService.selectByAll(map);
            List<AdvancedPackages> listPackage = new ArrayList<AdvancedPackages>();
            for (AdvancedPackages packages : listPack) {
                if (sb.toString().indexOf(packages.getId()) != -1) {
                    listPackage.add(packages);
                }
            }
            //开始循环包
            for (AdvancedPackages pk:listPackage) {
                AdvancedPackages ps = packageService.selectById(pk.getId());
                if(ps != null && StringUtils.isNotBlank(ps.getProjectStatus())){
                    DictionaryData findById = DictionaryDataUtil.findById(ps.getProjectStatus());
                    pk.setProjectStatus(findById.getCode());
                }
                map.put("packageId", pk.getId());
                quote2.setProjectId(projectId);
                quote2.setPackageId(pk.getId());
                List<Date> listDate = supplierQuoteService.selectQuoteCount(quote2);
                List<Quote> listQuotebyPackage = new ArrayList<Quote>();
                if (listDate != null && listDate.size() > 1) {
                    if ("JZXTP".equals(dictionaryData.getCode()) || "DYLY".equals(dictionaryData.getCode())) {
                         quote1.setProjectId(projectId);
                         quote1.setPackageId(pk.getId());
                         quote1.setCreatedAt(new Timestamp(listDate.get(listDate.size()-1).getTime()));
                         listQuotebyPackage = supplierQuoteService.selectQuoteHistoryList(quote1);
                    } 
                }
                Collections.reverse(listDate);
                pk.setDataList(listDate);
                quote.setProjectId(projectId);
                if (listDate != null && listDate.size() > 0) {
                    quote.setCreatedAt(new Timestamp(listDate.get(0).getTime()));
                }
                quote.setPackageId(pk.getId());
                List<Quote> listQuote = supplierQuoteService.selectQuoteHistoryList(quote);
                List<Supplier> suList = setField(listQuote);
                //每个包有几个供应商
                pk.setSuppliers(suList);
                BigDecimal projectBudget = BigDecimal.ZERO;
                if (pk.getSuppliers() != null && pk.getSuppliers().size() > 0) {
                    for (Quote q : pk.getSuppliers().get(0).getQuoteList()) {
                        projectBudget = projectBudget.add(new BigDecimal(q.getProjectDetail().getBudget()));
                    }
                }
                //项目预算
                pk.setProjectBudget(projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP));
                setNewQuote(listQuote, listQuotebyPackage);
            }
            model.addAttribute("listPackage", listPackage);
            model.addAttribute("projectId", projectId);
        } else {
            List<AdvancedPackages> packList = packageService.getPackageId(projectId);
            //这里用这个是因为hashMap是无序的
            TreeMap<String ,List<SaleTender>> treeMap = new TreeMap<String ,List<SaleTender>>();
            SaleTender condition1 = new SaleTender();
            HashMap<String, Object> map = new HashMap<String, Object>();
            HashMap<String, Object> map1 = new HashMap<String, Object>();
            Quote quote2 = new Quote();
            Quote quote3 = new Quote();
            Map<String, String> mapPackageName=new HashMap<String, String>();
            if(packList != null && !packList.isEmpty()){
                for (AdvancedPackages pack : packList) {
                    AdvancedPackages packages = packageService.selectById(pack.getId());
                    if(packages != null && StringUtils.isNotBlank(packages.getProjectStatus())){
                        DictionaryData findById = DictionaryDataUtil.findById(packages.getProjectStatus());
                        mapPackageName.put(packages.getName(), findById.getCode());
                    }
                    condition1.setProjectId(projectId);
                    condition1.setPackages(pack.getId());
                    condition1.setStatusBid(NUMBER_TWO);
                    condition1.setStatusBond(NUMBER_TWO);
                    condition1.setIsTurnUp(0);
                    List<SaleTender> stList = saleTenderService.find(condition1);
                    model.addAttribute("mapPackageName", mapPackageName);
                    map1.put("packageId", pack.getId());
                    map1.put("advancedProject", projectId);
                    List<AdvancedDetail> selectByAll = detailService.selectByAll(map1);
                    if(selectByAll != null && selectByAll.size() > 0){
                        BigDecimal projectBudget = BigDecimal.ZERO;
                        for (AdvancedDetail detail : selectByAll) {
                            projectBudget = projectBudget.add(detail.getBudget());
                        }
                        quote3.setProjectId(projectId);
                        quote3.setPackageId(pack.getId());
                        List<Date> listDate1 = supplierQuoteService.selectQuoteCount(quote3);
                        List<Quote> listQuotebyPackage1 = new ArrayList<Quote>();
                        if (listDate1 != null && listDate1.size() > 1) {
                            //给第二次报价的数据查到
                            if ("JZXTP".equals(dictionaryData.getCode()) || "DYLY".equals(dictionaryData.getCode())) {
                                quote2.setProjectId(projectId);
                                quote2.setPackageId(pack.getId());
                                quote2.setCreatedAt(new Timestamp(listDate1.get(listDate1.size()-1).getTime()));
                                listQuotebyPackage1 = supplierQuoteService.selectQuoteHistoryList(quote2);
                            }
                        } 
                        Collections.reverse(listDate1);
                        for (SaleTender saleTender : stList) {
                            Quote quote = new Quote();
                            quote.setProjectId(projectId);
                            quote.setPackageId(pack.getId());
                            quote.setSupplierId(saleTender.getSupplierId());
                            if (listDate1 != null && listDate1.size() > 0) {
                                quote.setCreatedAt(new Timestamp(listDate1.get(0).getTime()));
                            }
                            List<Quote> allQuote = supplierQuoteService.getAllQuote(quote, 1);
                            if (allQuote != null && allQuote.size() > 0) {
                                for (Quote conditionQuote : allQuote) {
                                    if (conditionQuote.getSupplierId().equals(saleTender.getSuppliers().getId()) && conditionQuote.getProjectId().equals(saleTender.getProjectId()) && saleTender.getPackages().equals(conditionQuote.getPackageId())) {
                                          for (Quote qp : listQuotebyPackage1) {
                                              if (qp.getPackageId().equals(conditionQuote.getPackageId()) && qp.getSupplierId().equals(conditionQuote.getSupplierId())) {
                                                  conditionQuote.setTotal(qp.getTotal());
                                                  conditionQuote.setQuotePrice(qp.getQuotePrice());
                                                  conditionQuote.setRemark(qp.getRemark());
                                                  conditionQuote.setDeliveryTime(qp.getDeliveryTime());
                                              }
                                          }
                                        if (conditionQuote.getIsRemove() == null) {
                                            saleTender.setIsRemoved("正常");
                                        } else {
                                            saleTender.setIsRemoved("放弃报价");
                                        }
                                        saleTender.setRemovedReason(conditionQuote.getGiveUpReason());  
                                        saleTender.setTotal(conditionQuote.getTotal());
                                        saleTender.setDeliveryTime(conditionQuote.getDeliveryTime());
                                        saleTender.setIsTurnUp(conditionQuote.getIsTurnUp());
                                        saleTender.setQuoteId(conditionQuote.getId());
                                        saleTender.setDataList(listDate1);
                                    } else {
                                        saleTender.setDataList(listDate1);
                                    }
                                }
                            }
                        }
                        map.put("id", pack.getId());
                        if (stList != null && stList.size() > 0) {
                            treeMap.put(packages.getName()+"|"+projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP), stList);
                        }
                    }
                    model.addAttribute("treeMap", treeMap);
                }
                model.addAttribute("status", flag);
                model.addAttribute("project", project);
                model.addAttribute("flowDefineId", flowDefineId);
                model.addAttribute("dd", dictionaryData);
                return "bss/ppms/advanced_project/audit/quote_list";
            }
        }
        return null;
    }
    
    @RequestMapping("/supplierQuote")
    public String supplierQuote(String projectId, String supplierId, Model model, Quote quote, String timestamp) {
        try {
            AdvancedProject project = projectService.selectById(projectId);
            DictionaryData dd = DictionaryDataUtil.findById(project.getPurchaseType());
            model.addAttribute("purchaserType", dd.getCode());
            
            HashMap<String, Object> map = new HashMap<>();
            map.put("projectId", projectId);
            List<AdvancedPackages> listPackage = packageService.selectByAll(map);
            List<AdvancedPackages> listPackageEach = new ArrayList<AdvancedPackages>();
            SaleTender saleTender = new SaleTender();
            saleTender.setProjectId(projectId);
            saleTender.setSupplierId(supplierId);
            List<SaleTender> sts = saleTenderService.find(saleTender);
            if (sts != null && sts.size() > 0) {
                String packageStr = sts.get(0).getPackages();
                for (AdvancedPackages packages : listPackage) {
                    if (packageStr.indexOf(packages.getId()) != -1) {
                        listPackageEach.add(packages);
                    }
                }
            }
            List<List<Quote>> listQuote = new ArrayList<List<Quote>>();
            // 查询时间
            Quote quote2 = new Quote();
            quote2.setSupplierId(supplierId);
            quote.setProjectId(projectId);
            List<Date> listDate = supplierQuoteService.selectQuoteCount(quote2);
            for (AdvancedPackages pk : listPackageEach) {
                if (StringUtils.isNotEmpty(timestamp)) {
                    // 如果传递时间 就按照时间查询
                    quote.setCreatedAt(new Timestamp(new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss").parse(timestamp).getTime()));
                } else {
                    if (listDate != null && listDate.size() > 0) {
                        // 否则就查询最后一次报价
                        Date date = listDate.get(listDate.size() - 1);
                        Timestamp timestamp2 = new Timestamp(date.getTime());
                        quote.setCreatedAt(timestamp2);
                    }
                }
                quote.setPackageId(pk.getId());
                List<Quote> quoteList = supplierQuoteService.selectQuoteHistoryList(quote);
                listQuote.add(quoteList);
            }
            model.addAttribute("listPackage", listPackageEach);
            model.addAttribute("listQuote", listQuote);
            model.addAttribute("listDate", listDate);
            model.addAttribute("length", listDate.size());
            model.addAttribute("projectId", projectId);
            model.addAttribute("supplierId", supplierId);
            if (timestamp != null) {
                model.addAttribute("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "bss/ppms/advanced_project/audit/view_quote";
    }
    
    /**
     *〈简述〉跳转到初审页面
     *〈详细描述〉
     * @author FengTian
     * @param projectId 项目id
     * @param model
     * @param flowDefineId 流程环节id
     * @return
     */
    @RequestMapping("/toFirstAudit")
    public String toFirstAudit(String projectId, Model model, String flowDefineId){
        List<AdvancedPackages> resultExpert = packageService.resultExpert(projectId);
        if(resultExpert != null && !resultExpert.isEmpty()){
            model.addAttribute("packageList", resultExpert);
        }
        List<ReviewProgress> listResultExpert = packageService.listResultExpert(projectId);
        if(listResultExpert != null && !listResultExpert.isEmpty()){
            model.addAttribute("reviewProgressList", listResultExpert);
        }
        model.addAttribute("projectId", projectId);
        model.addAttribute("flowDefineId", flowDefineId);
        return "bss/ppms/advanced_project/first_audit/list";
    }
    
    /**
     *〈简述〉查看包的初审情况
     *〈详细描述〉
     * @author FengTian
     * @param packageId 包id 
     * @param projectId 项目id
     * @param model
     * @return
     */
    @RequestMapping("/firstAuditView")
    public String firstAuditView(String packageId, String projectId, Model model, String flowDefineId){
        //包与专家 关联表集合
        Map<String, Object> packageExpertmap = new HashMap<String, Object>();
        packageExpertmap.put("packageId", packageId);
        packageExpertmap.put("projectId", projectId);
        //查询专家
        List<PackageExpert> expertIdList = packageExpertService.selectList(packageExpertmap);
        if (expertIdList != null && expertIdList.size() > 0) {
            Short isEnd = expertIdList.get(0).getIsGather();
            model.addAttribute("isEnd", isEnd);
        }
        // 供应商信息
        List<SaleTender> supplierList = new ArrayList<SaleTender>();
        List<SaleTender> sl = saleTenderService.find(new SaleTender(projectId));
        for (SaleTender st : sl) {
            if (st.getPackages().indexOf(packageId) != -1 && st.getIsTurnUp() != null && st.getIsTurnUp() == 0) {
                supplierList.add(st);
            }
        }
        // 关联信息集合
        // 封装实体
        List<PackExpertExt> packExpertExtList = new ArrayList<>();
        // 供应商封装实体
        List<SupplierExt> supplierExtList = new ArrayList<>();
        PackExpertExt packExpertExt;
        for (PackageExpert packageExpert : expertIdList) {
            packExpertExt = new PackExpertExt();
            Expert expert = expertService.selectByPrimaryKey(packageExpert.getExpertId());
            packExpertExt.setExpert(expert);
            packExpertExt.setPackageId(packageExpert.getPackageId());
            packExpertExt.setProjectId(packageExpert.getProjectId());
            // 根据供应商id 和包id查询审核表 确定该供应商是否通过评审
            for (SaleTender saleTender : supplierList) {
                SupplierExt supplierExt = new SupplierExt();
                List<ReviewFirstAudit> selectList2 = new ArrayList<ReviewFirstAudit>();
                FirstAudit firstAudit = new FirstAudit();
                firstAudit.setProjectId(projectId);
                firstAudit.setPackageId(packageId);
                firstAudit.setIsConfirm((short)0);
                List<FirstAudit> firstAudits = firstAuditService.findBykind(firstAudit);
                List<String> firstAuditIds = new ArrayList<String>();
                for (FirstAudit firstAudit2 : firstAudits) {
                    firstAuditIds.add(firstAudit2.getId());
                }
                Map<String, Object> map2 = new HashMap<>();
                map2.put("supplierId", saleTender.getSuppliers().getId());
                map2.put("packageId", packageExpert.getPackageId());
                map2.put("expertId", packageExpert.getExpertId());
                map2.put("isBack", 0);
                selectList2 = reviewFirstAuditService.selectList(map2);
                if (selectList2 != null && selectList2.size() > 0) {
                    int count2 = 0;
                    for (ReviewFirstAudit reviewFirstAudit : selectList2) {
                        String firstAuditId = reviewFirstAudit.getFirstAuditId();
                        if (firstAuditIds.contains(firstAuditId) && reviewFirstAudit.getIsPass() == ONE) {
                            count2++;
                            break;
                        }
                    }
                    // 如果变量大于0 说明有不合格的数据
                    if (count2 > 0) {
                        supplierExt.setSupplierId(saleTender.getSuppliers().getId());
                        supplierExt.setExpertId(packageExpert.getExpertId());
                        supplierExt.setPackageId(packageExpert.getPackageId());
                        //判断专家是否提交
                        if (packageExpert.getIsAudit() != null && packageExpert.getIsAudit() == 1) {
                            //已提交的话显示评审结果
                            supplierExt.setSuppIsPass("0");
                        } else {
                            //未提交的话显示为未提交
                            supplierExt.setSuppIsPass("2");
                        }
                    } else {
                        supplierExt.setSupplierId(saleTender.getSuppliers().getId());
                        supplierExt.setExpertId(packageExpert.getExpertId());
                        supplierExt.setPackageId(packageExpert.getPackageId());
                        //判断专家是否提交
                        if (packageExpert.getIsAudit() != null && packageExpert.getIsAudit() == 1) {
                            //已提交的话显示评审结果
                            supplierExt.setSuppIsPass("1");
                        } else {
                            //未提交的话显示为未提交
                            supplierExt.setSuppIsPass("2");
                        }
                    }
                } else {
                    supplierExt
                    .setSupplierId(saleTender.getSuppliers().getId());
                    supplierExt.setPackageId(packageExpert.getPackageId());
                    supplierExt.setExpertId(packageExpert.getExpertId());
                    supplierExt.setSuppIsPass("2");
                }

                supplierExtList.add(supplierExt);
            }
            packExpertExtList.add(packExpertExt);
        }
        
        AdvancedPackages advancedPackages = packageService.selectById(packageId);
        if (advancedPackages != null) {
            model.addAttribute("pack", advancedPackages);
        }
        model.addAttribute("packageId", packageId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("flowDefineId", flowDefineId);
        model.addAttribute("supplierList", supplierList);
        model.addAttribute("supplierExtList", supplierExtList);
        model.addAttribute("packExpertExtList", packExpertExtList);
        AdvancedProject project = projectService.selectById(projectId);
        model.addAttribute("supplierNumber",project.getSupplierNumber());
        DictionaryData dd = DictionaryDataUtil.findById(project.getPurchaseType());
        if (dd != null) {
            model.addAttribute("purcahseCode", dd.getCode());
        }
        return "bss/ppms/advanced_project/first_audit/view";
    }
    
    @RequestMapping("/gather")
    @ResponseBody
    public void getPace(String projectId, String packageIds, HttpServletResponse response) throws IOException {
        try {
            String[] packageIdArr = packageIds.split(StaticVariables.COMMA_SPLLIT);
            String msg = "";
            if (packageIdArr != null && packageIdArr.length > 0) {
                PackageExpert record = new PackageExpert();
                for (String string : packageIdArr) {
                    AdvancedPackages packages = packageService.selectById(string);
                    if(packages != null){
                        Map<String, Object> map = new HashMap<>();
                        map.put("projectId", projectId);
                        map.put("packageId", packages.getId());
                        // 查询包关联专家实体
                        List<PackageExpert> selectList = packageExpertService.selectList(map);
                        if (selectList != null && selectList.size() > 0) {
                            int count2 = 0;
                            for (PackageExpert packageExpert : selectList) {
                                //判断为审核过的 和未汇总的 才执行汇总
                                if (packageExpert.getIsAudit() == ONE && packageExpert.getIsGather() != ONE) {
                                    count2 ++;
                                } else {
                                    break;
                                }
                            }
                            if (count2 == selectList.size()) {
                                record.setIsGather((short) 1);
                                record.setPackageId(packages.getId());
                                record.setProjectId(projectId);
                                packageExpertService.updateByBean(record);
                                String str = "【"+packages.getName()+"】";
                                msg += str;
                            }
                        }
                    }
                }
            }
            msg += "汇总完成";
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(msg);
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            response.getWriter().close();
        }
    }
    
    /**
     *〈简述〉查看专家对各供应商的初审明细
     *〈详细描述〉
     * @author Ye MaoLin
     * @param id 专家id
     * @param model
     * @param packageId 包id
     * @param projectId 项目id
     * @return
     */
    @RequestMapping("/viewByExpert")
    public String viewByExpert(String id, Model model, String packageId, String projectId, String flowDefineId){
        Expert expert = expertService.selectByPrimaryKey(id);
        //创建封装的实体
        Extension extension = new Extension();
        HashMap<String ,Object> map = new HashMap<String ,Object>();
        map.put("projectId", projectId);
        map.put("id", packageId);
        List<AdvancedPackages> selectByAll = packageService.selectByAll(map);
        if(selectByAll != null && !selectByAll.isEmpty()){
            //放入包信息
            extension.setPackageId(selectByAll.get(0).getId());
            extension.setPackageName(selectByAll.get(0).getName());
        }
        //查看项目
        AdvancedProject project = projectService.selectById(projectId);
        if(project != null){
            //放入项目信息
            extension.setProjectId(project.getId());
            extension.setProjectName(project.getName());
            extension.setProjectCode(project.getProjectNumber());
        }
        
        //查询改包下的初审项信息
        Map<String,Object> map2 = new HashMap<>();
        map2.put("projectId", projectId);
        map2.put("packageId", packageId);
        //查询出该包下的初审项id集合
        List<PackageFirstAudit> packageAuditList = packageFirstAuditService.selectList(map2);
        //创建初审项的集合
        List<FirstAudit> firstAuditList = new ArrayList<FirstAudit>();
        if(packageAuditList != null && packageAuditList.size()>0){
            for (PackageFirstAudit packageFirst : packageAuditList) {
                //根据初审项的id 查询出初审项的信息放入集合
                FirstAudit firstAudits = firstAuditService.get(packageFirst.getFirstAuditId());
                firstAuditList.add(firstAudits);
            }
        }
        //放入初审项集合
        extension.setFirstAuditList(firstAuditList);
        //查询供应商信息
        List<SaleTender> supplierList = saleTenderService.find(new SaleTender(projectId));
        extension.setSupplierList(supplierList);

        //查询审核过的信息用于回显
        Map<String, Object> reviewFirstAuditMap = new HashMap<String, Object>();
        reviewFirstAuditMap.put("projectId", projectId);
        reviewFirstAuditMap.put("packageId", packageId);
        reviewFirstAuditMap.put("expertId", expert.getId());
        List<ReviewFirstAudit> reviewFirstAuditList = reviewFirstAuditService.selectList(reviewFirstAuditMap);
        //回显信息放进去
        model.addAttribute("reviewFirstAuditList", reviewFirstAuditList);
        //把封装的实体放入域中
        model.addAttribute("extension", extension);
        model.addAttribute("expert", expert);
        model.addAttribute("packageId", packageId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("flowDefineId", flowDefineId);
        return "bss/ppms/advanced_project/first_audit/first_audit_expert_view";
    }
    
    @RequestMapping("/viewBySupplier")
    public String viewBySupplier(String supplierId, Model model, String packageId, String projectId, String flowDefineId){
        Supplier supplier = supplierService.selectById(supplierId);
        //创建封装的实体
        Extension extension = new Extension();
        HashMap<String ,Object> map = new HashMap<String ,Object>();
        map.put("projectId", projectId);
        map.put("id", packageId);
        //查询包信息
        List<AdvancedPackages> list = packageService.selectByAll(map);
        if(list != null && list.size()>0){
            //放入包信息
            extension.setPackageId(list.get(0).getId());
            extension.setPackageName(list.get(0).getName());
        }
        //查询项目信息
        AdvancedProject project = projectService.selectById(projectId);
        if(project != null){
            //放入项目信息
            extension.setProjectId(project.getId());
            extension.setProjectName(project.getName());
            extension.setProjectCode(project.getProjectNumber());
        }

        //查询改包下的初审项信息
        Map<String,Object> map2 = new HashMap<>();
        map2.put("projectId", projectId);
        map2.put("packageId", packageId);
        //查询出该包下的初审项id集合
        List<PackageFirstAudit> packageAuditList = packageFirstAuditService.selectList(map2);
        //创建初审项的集合
        List<FirstAudit> firstAuditList = new ArrayList<FirstAudit>();
        if(packageAuditList!=null && packageAuditList.size()>0){
            for (PackageFirstAudit packageFirst : packageAuditList) {
                //根据初审项的id 查询出初审项的信息放入集合
                FirstAudit firstAudits = firstAuditService.get(packageFirst.getFirstAuditId());
                firstAuditList.add(firstAudits);
            }
        }
        //放入初审项集合
        extension.setFirstAuditList(firstAuditList);
        //查询专家初审记录
        Map<String, Object> rfamap = new HashMap<>();
        map.put("projectId", projectId);
        map.put("packageId", packageId);
        map.put("supplierId", supplierId);
        List<ReviewFirstAudit> reviewFirstAuditList = reviewFirstAuditService.selectList(rfamap);
        List<Expert> experts = new ArrayList<Expert>();
        HashMap<String, Object> packageExpertMap = new HashMap<String, Object>();
        packageExpertMap.put("projectId", projectId);
        packageExpertMap.put("packageId", packageId);
        //获取包下所有专家
        List<PackageExpert> packageExperts = packageExpertService.selectList(packageExpertMap);
        for (PackageExpert packageExpert : packageExperts) {
            experts.add(packageExpert.getExpert());
        }
        //回显信息放进去
        model.addAttribute("reviewFirstAuditList", reviewFirstAuditList);
        //把封装的实体放入域中
        model.addAttribute("experts", experts);
        model.addAttribute("extension", extension);
        model.addAttribute("supplier", supplier);
        model.addAttribute("packageId", packageId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("flowDefineId", flowDefineId);
        return "bss/ppms/advanced_project/first_audit/first_audit_supplier_view";
    }
    
    /**
     *〈简述〉符合性审查结束提示
     *〈详细描述〉
     * @author Ye MaoLin
     * @param projectId
     * @param packageId
     * @param expertIds
     * @param response
     * @throws IOException
     */
    @RequestMapping("/isSendBack")
    public void isSendBack(String projectId, String packageId, String expertIds, HttpServletResponse response) throws IOException{
        try {
            String msg = "";
            int flag = 0;
            String[] expertIdArr = expertIds.split(StaticVariables.COMMA_SPLLIT);
            if (expertIdArr != null && expertIdArr.length > 0) {
                for (int i = 0; i < expertIdArr.length; i++) {
                    // 查询是否已评审
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("expertId", expertIdArr[i]);
                    map.put("packageId", packageId);
                    map.put("projectId", projectId);
                    List<PackageExpert> selectList = packageExpertService.selectList(map);
                    Expert expert = expertService.selectByPrimaryKey(expertIdArr[i]);
                    if (selectList != null && selectList.size() > 0) {
                        PackageExpert packageExpert = selectList.get(0);
                        // 必须是已评审
                        if (packageExpert.getIsAudit() != ONE) {
                            msg += "【"+expert.getRelName()+"】未提交评审内容.";
                            //操作失败提示
                            flag += 1;
                        }
                    }
                }
            }
            if (flag > 0) {
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().print("{\"success\": " + false + ", \"msg\": \"" + msg + "\"}");
            }  
            if (flag == 0 && expertIdArr != null && expertIdArr.length > 0){
                for (int i = 0; i < expertIdArr.length; i++) {
                    // 查询是否已评审
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("expertId", expertIdArr[i]);
                    map.put("packageId", packageId);
                    map.put("projectId", projectId);
                    List<PackageExpert> selectList = packageExpertService.selectList(map);
                    Expert expert = expertService.selectByPrimaryKey(expertIdArr[i]);
                    if (selectList != null && selectList.size() > 0) {
                        PackageExpert packageExpert = selectList.get(0);
                        if (packageExpert.getIsGrade() == ONE || packageExpert.getIsGrade() == NUMBER_TWO) {
                            msg += "【"+expert.getRelName()+"】经济技术评分内容将会清空.";
                        }
                    }
                }
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().print("{\"success\": " + true + ", \"msg\": \"" + msg + "\"}");
            
            }
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.getWriter().close();
        }
    }
    
    @ResponseBody
    @RequestMapping("/endPrice")
    public String endPrice(String packageId) {
        String result = "1";
        AdvancedPackages packages = new AdvancedPackages();
        packages.setId(packageId);
        packages.setIsEndPrice(1);
        packageService.update(packages);
        return result;
    }
    
    @RequestMapping("/printView")
    public String printView(String auditType, String projectId, String packageId, Model model, String expertId){
        Map<String, Object> pMap = new HashMap<String, Object>();
        pMap.put("packageId", packageId);
        pMap.put("projectId", projectId);
        pMap.put("expertId", expertId);
        List<PackageExpert> packageExperts = packageExpertService.selectList(pMap);
        String kind = null;
        if (packageExperts != null && packageExperts.size() > 0) {
            model.addAttribute("isSubmit", packageExperts.get(0).getIsAudit());
            kind = packageExperts.get(0).getReviewTypeId();
        }
        Expert expert = expertService.selectByPrimaryKey(expertId);
        model.addAttribute("expert", expert);
        //创建封装的实体
        Extension extension = new Extension();
        HashMap<String ,Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("id", packageId);
        //查询包信息
        List<AdvancedPackages> list = packageService.selectByAll(map);
        if(list != null && list.size() > 0){
            AdvancedPackages packages = list.get(0);
            model.addAttribute("pack", packages);
            //放入包信息
            extension.setPackageId(packages.getId());
            extension.setPackageName(packages.getName());
        }
        //查询项目信息
        AdvancedProject project = projectService.selectById(projectId);
        if(project!=null){
            model.addAttribute("project", project);
            //放入项目信息
            extension.setProjectId(project.getId());
            extension.setProjectName(project.getName());
            extension.setProjectCode(project.getProjectNumber());
        }
        //获取包下的评审项
        FirstAudit firstAudit = new FirstAudit();
        firstAudit.setPackageId(packageId);
        if ("1".equals(auditType)) {
            firstAudit.setIsConfirm((short)1);
            firstAudit.setKind(kind);
        } else {
            firstAudit.setIsConfirm((short)0);
        }
        List<FirstAudit> fas = firstAuditService.findBykind(firstAudit);
          //放入初审项集合
        extension.setFirstAuditList(fas);
        // 获取符合性审查通过且到场没被移除的供应商
        SaleTender saleTender = new SaleTender();
        saleTender.setPackages(packageId);
        if ("1".equals(auditType)) {
            saleTender.setIsFirstPass(1);
            saleTender.setIsRemoved("0");
        }
        saleTender.setIsTurnUp(0);
        List<SaleTender> supplierList = saleTenderService.findByCon(saleTender);
        extension.setSupplierList(supplierList);
       
        //查询审核过的信息用于回显
        Map<String, Object> reviewFirstAuditMap = new HashMap<>();
        reviewFirstAuditMap.put("projectId", projectId);
        reviewFirstAuditMap.put("packageId", packageId);
        reviewFirstAuditMap.put("expertId", expertId);
        List<ReviewFirstAudit> reviewFirstAuditList = reviewFirstAuditService.selectList(reviewFirstAuditMap);
        //回显信息放进去
        model.addAttribute("reviewFirstAuditList", reviewFirstAuditList);
        //把封装的实体放入域中
        model.addAttribute("extension", extension);
        List<DictionaryData> dds = new ArrayList<DictionaryData>();
        if ("1".equals(auditType)) {
            DictionaryData dd = DictionaryDataUtil.findById(kind);
            dds.add(dd); 
            model.addAttribute("dds", dds);
        } else {
            dds = DictionaryDataUtil.find(22);
            model.addAttribute("dds", dds);
        }
        model.addAttribute("expertId", expertId);
        model.addAttribute("auditType", auditType);
        return "bss/ppms/advanced_project/first_audit/print_view";
    }
    
    @RequestMapping("/print")
    public String print(String auditType, String projectId, String packageId, Model model, String expertId){
        Map<String, Object> pMap = new HashMap<String, Object>();
        pMap.put("packageId", packageId);
        pMap.put("projectId", projectId);
        pMap.put("expertId", expertId);
        List<PackageExpert> packageExperts = packageExpertService.selectList(pMap);
        String kind = null;
        if (packageExperts != null && packageExperts.size() > 0) {
            model.addAttribute("isSubmit", packageExperts.get(0).getIsAudit());
            kind = packageExperts.get(0).getReviewTypeId();
        }
        // 获取符合性审查通过且到场没被移除的供应商
        SaleTender saleTender = new SaleTender();
        saleTender.setPackages(packageId);
        if ("1".equals(auditType)) {
            saleTender.setIsFirstPass(1);
            saleTender.setIsRemoved("0");
        }
        saleTender.setIsTurnUp(0);
        Expert expert = expertService.selectByPrimaryKey(expertId);
        model.addAttribute("expert", expert);
        HashMap<String ,Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("id", packageId);
        //查询审核过的信息用于回显
        Map<String, Object> reviewFirstAuditMap = new HashMap<>();
        reviewFirstAuditMap.put("projectId", projectId);
        reviewFirstAuditMap.put("packageId", packageId);
        reviewFirstAuditMap.put("expertId", expertId);
        List<ReviewFirstAudit> reviewFirstAuditList = reviewFirstAuditService.selectList(reviewFirstAuditMap);
        //回显信息放进去
        model.addAttribute("reviewFirstAuditList", reviewFirstAuditList);
        //把封装的实体放入域中
        
        List<DictionaryData> dds = new ArrayList<DictionaryData>();
        if ("1".equals(auditType)) {
            DictionaryData dd = DictionaryDataUtil.findById(kind);
            dds.add(dd); 
            model.addAttribute("dds", dds);
        } else {
            dds = DictionaryDataUtil.find(22);
            model.addAttribute("dds", dds);
        }
        model.addAttribute("expertId", expertId);
        List<SaleTender> supplierList = saleTenderService.findByCon(saleTender);
        int supplierListSize=supplierList.size()%8==0?supplierList.size()/8:supplierList.size()/8+1;
        List<SaleTender> saleTenderListJ=null;
        List<Extension> extensions=new ArrayList<Extension>();
        for(int i=1;i<=supplierListSize;i++){
            Extension extensionJ = new Extension();
            saleTenderListJ=new ArrayList<SaleTender>();
            //查询包信息
            List<AdvancedPackages> list = packageService.selectByAll(map);
            if(list!=null && list.size()>0){
                AdvancedPackages packages = list.get(0);
                model.addAttribute("pack", packages);
                //放入包信息
                extensionJ.setPackageId(packages.getId());
                extensionJ.setPackageName(packages.getName());
            }
            //查询项目信息
            AdvancedProject project = projectService.selectById(projectId);
            if(project != null){
              model.addAttribute("project", project);
              //放入项目信息
              extensionJ.setProjectId(project.getId());
              extensionJ.setProjectName(project.getName());
              extensionJ.setProjectCode(project.getProjectNumber());
            }
            //获取包下的评审项
            FirstAudit firstAudit = new FirstAudit();
            firstAudit.setPackageId(packageId);
            if ("1".equals(auditType)) {
              firstAudit.setIsConfirm((short)1);
              firstAudit.setKind(kind);
            } else {
              firstAudit.setIsConfirm((short)0);
            }
            List<FirstAudit> fas = firstAuditService.findBykind(firstAudit);
            //放入初审项集合
            extensionJ.setFirstAuditList(fas);
            for(int j=(i-1)*8;j<i*8;j++){
                if(j==supplierList.size()){
                    break;
                }
                SaleTender saleTenderJ= supplierList.get(j);
                if(saleTenderJ!=null){
                    saleTenderListJ.add(saleTenderJ);
                }else{
                   break; 
                } 
            }
            extensionJ.setSupplierList(saleTenderListJ);
            extensions.add(extensionJ);  
        }
        model.addAttribute("extension", extensions);
        return "bss/ppms/advanced_project/first_audit/print";
    }
    
    /**
     *〈简述〉打印符合性审查汇总表
     *〈详细描述〉
     * @author Ye MaoLin
     * @param packageId 包id 
     * @param projectId 项目id
     * @param model
     * @return
     */
    @RequestMapping("/openPrint")
    public String openPrint(String packageId, String projectId, Model model, String flowDefineId){
        if(StringUtils.isNotBlank(projectId) && StringUtils.isNotBlank(packageId)){
            AdvancedProject project = projectService.selectById(projectId);
            HashMap<String ,Object> map = new HashMap<>();
            map.put("projectId", projectId);
            map.put("id", packageId);
            List<AdvancedPackages> selectByAll = packageService.selectByAll(map);
            if (selectByAll != null && selectByAll.size() > 0) {
                model.addAttribute("pack", selectByAll.get(0));
            }
            // 获取符合性审查通过且到场没被移除的供应商
            SaleTender saleTender = new SaleTender();
            saleTender.setPackages(packageId);
            saleTender.setIsTurnUp(0);
            //查询该包下参与的供应商
            List<SaleTender> sl = saleTenderService.findByCon(saleTender);
            List<ReviewFirstAudit> reviewFirstAudits = new ArrayList<ReviewFirstAudit>();
            //获取包下的评审项
            FirstAudit firstAudit = new FirstAudit();
            firstAudit.setPackageId(packageId);
            firstAudit.setIsConfirm((short)0);
            List<FirstAudit> list = firstAuditService.findBykind(firstAudit);
            for (SaleTender saleTender2 : sl) {
                //所有专家对各小项的评审结果少数服从多数
                for (FirstAudit firstAudit2 : list) {
                    Map<String, Object> auditMap = new HashMap<>();
                    if(saleTender2 != null && saleTender2.getSuppliers() != null){
                        auditMap.put("supplierId", saleTender2.getSuppliers().getId());
                    }
                    auditMap.put("packageId", packageId);
                    auditMap.put("projectId", projectId);
                    auditMap.put("firstAuditId", firstAudit2.getId());
                    auditMap.put("isBack", 0);
                    //获取所有专家对小项的评审结果
                    List<ReviewFirstAudit> selectList2 = reviewFirstAuditService.selectList(auditMap);
                    if (selectList2 != null && selectList2.size() > 0) {
                        int passNum = 0;
                        int noPassNum = 0;
                        for (ReviewFirstAudit reviewFirstAudit : selectList2) {
                          if (reviewFirstAudit.getIsPass() == 0) {
                            passNum += 1;
                          } 
                          if (reviewFirstAudit.getIsPass() == 1) {
                            noPassNum += 1;
                          }
                        }
                        //如果不通过的专家比通过的专家多，则该项判为不合格
                        if (noPassNum > passNum) {
                          ReviewFirstAudit reviewFirstAudit = selectList2.get(0);
                          reviewFirstAudit.setIsPass((short)1);
                          reviewFirstAudits.add(reviewFirstAudit);
                        }
                        if (noPassNum < passNum) {
                          ReviewFirstAudit reviewFirstAudit = selectList2.get(0);
                          reviewFirstAudit.setIsPass((short)0);
                          reviewFirstAudits.add(reviewFirstAudit);
                        }
                        if (noPassNum == passNum) {
                          ReviewFirstAudit reviewFirstAudit = selectList2.get(0);
                          reviewFirstAudit.setIsPass((short)2);
                          reviewFirstAudits.add(reviewFirstAudit);
                        }
                    }
                }
            }
            model.addAttribute("dds", DictionaryDataUtil.find(22));
            model.addAttribute("saleTenderList", sl);
            model.addAttribute("reviewFirstAudits", reviewFirstAudits);
            model.addAttribute("firstAudits", list);
            model.addAttribute("project", project);
        }
        return "bss/ppms/advanced_project/first_audit/print_total";
    }
    
    /**
     *〈简述〉导出word符合性审查汇总表
     *〈详细描述〉
     * @author li wan lin
     * @param packageId 包id 
     * @param projectId 项目id
     * @param model
     * @return
     */
    @RequestMapping("/printTotalWord")
    public String printTotalWord(String packageId, String projectId, Model model, String flowDefineId){
        AdvancedProject project = projectService.selectById(projectId);
        HashMap<String ,Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("id", packageId);
        List<AdvancedPackages> selectByAll = packageService.selectByAll(map);
        if (selectByAll != null && selectByAll.size() > 0) {
            model.addAttribute("pack", selectByAll.get(0));
        }
        // 获取符合性审查通过且到场没被移除的供应商
        SaleTender saleTender = new SaleTender();
        saleTender.setPackages(packageId);
        saleTender.setIsTurnUp(0);
        //查询该包下参与的供应商
        List<SaleTender> sl = saleTenderService.findByCon(saleTender);
        int supplierListSize=sl.size()%8==0?sl.size()/8:sl.size()/8+1;
        List<Extension> extensions=new ArrayList<Extension>();
        List<SaleTender> sleTenders=null;
        for(int i=1;i<=supplierListSize;i++){
            sleTenders=new ArrayList<SaleTender>();
            Extension extensionJ = new Extension();
            
            for(int j=(i-1)*8;j<i*8;j++){
                if(j==sl.size()){
                      break;
                  }
                SaleTender saleTender2=sl.get(j);
                if(saleTender2!=null){
                    sleTenders.add(saleTender2);
                }else{
                    break;
                }
              
            }
            extensionJ.setSupplierList(sleTenders);
            extensions.add(extensionJ);
            
        }
        
        List<ReviewFirstAudit> reviewFirstAudits = new ArrayList<ReviewFirstAudit>();
        //获取包下的评审项
        FirstAudit firstAudit = new FirstAudit();
        firstAudit.setPackageId(packageId);
        firstAudit.setIsConfirm((short)0);
        List<FirstAudit> list = firstAuditService.findBykind(firstAudit);
        for (SaleTender saleTender2 : sl) {
            //所有专家对各小项的评审结果少数服从多数
            for (FirstAudit firstAudit2 : list) {
                Map<String, Object> auditMap = new HashMap<>();
                auditMap.put("supplierId", saleTender2.getSuppliers().getId());
                auditMap.put("packageId", packageId);
                auditMap.put("projectId", projectId);
                auditMap.put("firstAuditId", firstAudit2.getId());
                auditMap.put("isBack", 0);
                //获取所有专家对小项的评审结果
                List<ReviewFirstAudit> selectList2 = reviewFirstAuditService.selectList(auditMap);
                if (selectList2 != null && selectList2.size() > 0) {
                    int passNum = 0;
                    int noPassNum = 0;
                    for (ReviewFirstAudit reviewFirstAudit : selectList2) {
                      if (reviewFirstAudit.getIsPass() == 0) {
                        passNum += 1;
                      } 
                      if (reviewFirstAudit.getIsPass() == 1) {
                        noPassNum += 1;
                      }
                    }
                    //如果不通过的专家比通过的专家多，则该项判为不合格
                    if (noPassNum > passNum) {
                      ReviewFirstAudit reviewFirstAudit = selectList2.get(0);
                      reviewFirstAudit.setIsPass((short)1);
                      reviewFirstAudits.add(reviewFirstAudit);
                    }
                    if (noPassNum < passNum) {
                      ReviewFirstAudit reviewFirstAudit = selectList2.get(0);
                      reviewFirstAudit.setIsPass((short)0);
                      reviewFirstAudits.add(reviewFirstAudit);
                    }
                    if (noPassNum == passNum) {
                      ReviewFirstAudit reviewFirstAudit = selectList2.get(0);
                      reviewFirstAudit.setIsPass((short)2);
                      reviewFirstAudits.add(reviewFirstAudit);
                    }
                }
            }
        }
        List<DictionaryData> dds = DictionaryDataUtil.find(22);
        model.addAttribute("dds", dds);
        model.addAttribute("saleTenderList", extensions);
        model.addAttribute("reviewFirstAudits", reviewFirstAudits);
        model.addAttribute("firstAudits", list);
        model.addAttribute("project", project);
        return "bss/prms/first_audit/print_total_word";
    }
    
    @RequestMapping("/openAllPrint")
    public String openAllPrint(String auditType, String projectId, String packageId, Model model){
        if(StringUtils.isNotBlank(packageId) && StringUtils.isNotBlank(projectId)){
            List<Extension> extensions = new ArrayList<Extension>();
            Map<String, Object> pMap = new HashMap<String, Object>();
            pMap.put("packageId", packageId);
            pMap.put("projectId", projectId);
            List<PackageExpert> packageExperts = packageExpertService.selectList(pMap);
            HashMap<String ,Object> map = new HashMap<>();
            map.put("projectId", projectId);
            map.put("id", packageId);
            List<AdvancedPackages> selectByAll = packageService.selectByAll(map);
            AdvancedPackages packages = null;
            if(selectByAll != null && selectByAll.size()>0){
                packages = selectByAll.get(0);
                model.addAttribute("pack", packages);
            }
            AdvancedProject project = projectService.selectById(projectId);
            model.addAttribute("project", project);
            for (PackageExpert packageExpert : packageExperts) {
                //创建封装的实体
                Extension extension = new Extension();
                List<DictionaryData> dds = new ArrayList<DictionaryData>();
                //获取包下的评审项
                FirstAudit firstAudit = new FirstAudit();
                firstAudit.setPackageId(packageId);
                if ("1".equals(auditType)) {
                    firstAudit.setIsConfirm((short)1);
                    firstAudit.setKind(packageExpert.getReviewTypeId());
                    DictionaryData dd =DictionaryDataUtil.findById(packageExpert.getReviewTypeId());
                    dds.add(dd);
                    extension.setDds(dds);
                } else {
                    firstAudit.setIsConfirm((short)0);
                    dds = DictionaryDataUtil.find(22);
                    extension.setDds(dds);
                }
                List<FirstAudit> fas = firstAuditService.findBykind(firstAudit);
                Expert expert = expertService.selectByPrimaryKey(packageExpert.getExpertId());
                extension.setExpert(expert);
                extension.setIsSubmit(packageExpert.getIsAudit());
                //放入包信息
                extension.setPackageId(packages.getId());
                extension.setPackageName(packages.getName());
                if(project != null){
                    //放入项目信息
                    extension.setProjectId(project.getId());
                    extension.setProjectName(project.getName());
                    extension.setProjectCode(project.getProjectNumber());
                }
                //放入初审项集合
                extension.setFirstAuditList(fas);
                // 获取符合性审查通过且到场没被移除的供应商
                SaleTender saleTender = new SaleTender();
                saleTender.setPackages(packageId);
                if ("1".equals(auditType)) {
                    saleTender.setIsFirstPass(1);
                    saleTender.setIsRemoved("0");
                }
                saleTender.setIsTurnUp(0);
                List<SaleTender> supplierList = saleTenderService.findByCon(saleTender);
                extension.setSupplierList(supplierList);
                extensions.add(extension);
            }
            //查询审核过的信息用于回显
            Map<String, Object> reviewFirstAuditMap = new HashMap<>();
            reviewFirstAuditMap.put("projectId", projectId);
            reviewFirstAuditMap.put("packageId", packageId);
            List<ReviewFirstAudit> reviewFirstAuditList = reviewFirstAuditService.selectList(reviewFirstAuditMap);
            //回显信息放进去
            model.addAttribute("reviewFirstAuditList", reviewFirstAuditList);
            model.addAttribute("auditType", auditType);
            model.addAttribute("extensions", extensions);
        }
        return "bss/ppms/advanced_project/first_audit/detail_print_view";
    }
    
    /**
     *〈简述〉导出word所有专家符合性审查
     *〈详细描述〉
     * @author li wan lin
     * @param packageId 包id 
     * @param projectId 项目id
     * @param model
     * @return
     */
    @RequestMapping("/openAllPrintWord")
    public String openAllPrintWord(String auditType, String projectId, String packageId, Model model){
        List<List<Extension>> extensionLists=new ArrayList<List<Extension>>();
        Map<String, Object> pMap = new HashMap<String, Object>();
        pMap.put("packageId", packageId);
        pMap.put("projectId", projectId);
        List<PackageExpert> packageExperts = packageExpertService.selectList(pMap);
        HashMap<String ,Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("id", packageId);
        //查询包信息
        AdvancedPackages packages = null;
        List<AdvancedPackages> selectByAll = packageService.selectByAll(map);
        if(selectByAll != null && selectByAll.size() > 0){
            packages = selectByAll.get(0);
            model.addAttribute("pack", packages);
        }
        //项目信息
        AdvancedProject project = projectService.selectById(projectId);
        if(project != null){
            model.addAttribute("project", project);
        }
        for (PackageExpert packageExpert : packageExperts) {
            List<Extension> extensions= new ArrayList<Extension>();
            // 获取符合性审查通过且到场没被移除的供应商
            SaleTender saleTender = new SaleTender();
            saleTender.setPackages(packageId);
            if ("1".equals(auditType)) {
                saleTender.setIsFirstPass(1);
                saleTender.setIsRemoved("0");
            }
            saleTender.setIsTurnUp(0);
            List<SaleTender> supplierList = saleTenderService.findByCon(saleTender);
            int supplierListSize=supplierList.size()%8==0?supplierList.size()/8:supplierList.size()/8+1;
            List<SaleTender> saleTenders=null;
            for(int i=1;i<=supplierListSize;i++){
                saleTenders=new ArrayList<SaleTender>();
                Extension extension = new Extension();
                List<DictionaryData> dds = new ArrayList<DictionaryData>();
                //获取包下的评审项
                FirstAudit firstAudit = new FirstAudit();
                firstAudit.setPackageId(packageId);
                if ("1".equals(auditType)) {
                    firstAudit.setIsConfirm((short)1);
                    firstAudit.setKind(packageExpert.getReviewTypeId());
                    DictionaryData dd =DictionaryDataUtil.findById(packageExpert.getReviewTypeId());
                    dds.add(dd);
                    extension.setDds(dds);
                } else {
                    firstAudit.setIsConfirm((short)0);
                    dds = DictionaryDataUtil.find(22);
                    extension.setDds(dds);
                }
                List<FirstAudit> fas = firstAuditService.findBykind(firstAudit);
                Expert expert = expertService.selectByPrimaryKey(packageExpert.getExpertId());
                extension.setExpert(expert);
                extension.setIsSubmit(packageExpert.getIsAudit());
                //放入包信息
                extension.setPackageId(packages.getId());
                extension.setPackageName(packages.getName());
                if(project != null){
                    //放入项目信息
                    extension.setProjectId(project.getId());
                    extension.setProjectName(project.getName());
                    extension.setProjectCode(project.getProjectNumber());
                }
                //放入初审项集合
                extension.setFirstAuditList(fas);
                
                for(int j=(i-1)*8;j<i*8;j++){
                    if(j==supplierList.size()){
                        break;
                    }
                    SaleTender saleTenderJ= supplierList.get(j);
                    if(saleTenderJ!=null){
                        saleTenders.add(saleTenderJ);
                    }else{
                        break; 
                    } 
                }
                extension.setSupplierList(saleTenders);
                extensions.add(extension);
            }
            extensionLists.add(extensions);
        }
        //查询审核过的信息用于回显
        Map<String, Object> reviewFirstAuditMap = new HashMap<>();
        reviewFirstAuditMap.put("projectId", projectId);
        reviewFirstAuditMap.put("packageId", packageId);
        List<ReviewFirstAudit> reviewFirstAuditList = reviewFirstAuditService.selectList(reviewFirstAuditMap);
        //回显信息放进去
        model.addAttribute("reviewFirstAuditList", reviewFirstAuditList);
        model.addAttribute("extensions", extensionLists);
        
        return "bss/ppms/advanced_project/first_audit/detail_print_view_word";
    }
    
    /**
     * 
     *〈确认供应商〉
     *〈详细描述〉
     * @author FengTian
     * @param projectId
     * @param model
     * @return
     */
    @RequestMapping("/confirmSupplier")
    public String confirmSupplier (String projectId, Model model) {
        SaleTender saleTender = new SaleTender();
        saleTender.setProjectId(projectId);
        saleTender.setIsTurnUp(0);
        List<SaleTender> list = saleTenderService.finds(saleTender);
        if(list != null && !list.isEmpty()){
            Map<String, Object> map = new HashMap<String, Object>();
            for (SaleTender sale : list) {
                AdvancedPackages packages = packageService.selectById(sale.getPackages());
                sale.setPackageNames(packages.getName());
                map.put("packageId", sale.getPackages());
                List<PackageExpert> packageExperts = packageExpertService.selectList(map);
                for (PackageExpert packageExpert : packageExperts) {
                    if (packageExpert.getIsGrade() == 1) {
                        sale.setIsFinish(1);
                        break;
                    }
                }
            }
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("projectId", projectId);
        List<AdvancedPackages> selectByAll = packageService.selectByAll(hashMap);
        model.addAttribute("supplierList", list);
        model.addAttribute("projectId", projectId);
        model.addAttribute("packages", selectByAll);
        return "bss/prms/rank/confirm_supplier";
    }
    
    @RequestMapping("/toScoreAudit")
    public String toScoreAudit(String projectId, Model model, String flowDefineId, HttpServletRequest request){
        List<PackageExpert> list = new ArrayList<PackageExpert>();
        List<ReviewProgress> reviewProgressList = new ArrayList<ReviewProgress>();
        List<AdvancedPackages> resultExpert = packageService.resultExpert(projectId);
        Map<String, Object> map = new HashMap<String, Object>();
        if(resultExpert != null && !resultExpert.isEmpty()){
            for (AdvancedPackages packages : resultExpert) {
                AdvancedPackages packages2 = packageService.selectById(packages.getId());
                if(packages2 != null && StringUtils.isNotBlank(packages2.getProjectStatus())){
                    DictionaryData findById = DictionaryDataUtil.findById(packages2.getProjectStatus());
                    packages.setProjectStatus(findById.getCode());
                }
                map.put("projectId", projectId);
                map.put("packageId", packages.getId());
                //查询该包有没有评审进度数据
                List<ReviewProgress> rplist = reviewProgressService.selectByMap(map);
                // 查询包关联专家实体
                List<PackageExpert> selectList = packageExpertService.selectList(map);
                if(selectList != null && selectList.size() > 0){
                    list.addAll(selectList);
                }
                map.put("isGrade", 1);
                // 查询包下提交评审的专家
                List<PackageExpert> selectList2 = packageExpertService.selectList(map);
                if (rplist == null || rplist.size() <= 0) {
                    ReviewProgress reviewProgress = new ReviewProgress();
                    reviewProgress.setAuditStatus("0");
                    reviewProgress.setFirstAuditProgress(0.00);
                    reviewProgress.setPackageId(packages2.getId());
                    reviewProgress.setPackageName(packages2.getName());
                    reviewProgress.setProjectId(projectId);
                    reviewProgress.setScoreProgress(0.00);
                    reviewProgress.setTotalProgress(0.00);
                    reviewProgress.setAuditStatus("0");
                    reviewProgress.setFinishNum(0);
                    reviewProgress.setNoFinishNum(selectList.size());
                    reviewProgressList.add(reviewProgress);
                } else {
                    ReviewProgress reviewProgress = rplist.get(0);
                    if (selectList2 != null && selectList2.size() > 0) {
                        reviewProgress.setFinishNum(selectList2.size());
                        reviewProgress.setNoFinishNum(selectList.size() - selectList2.size());
                    } else {
                        reviewProgress.setFinishNum(0);
                        reviewProgress.setNoFinishNum(selectList.size());
                    }
                    reviewProgressList.add(reviewProgress);
                }
            }
        }
        if(list != null && list.size() > 0){
            for (int i = 0; i < list.size(); i++ ) {
                if(list.get(i).getIsGatherGather() == 0){
                    break;
                } else if (i == list.size() - 1){
                    //该环节设置为执行完毕
                    flowMangeService.flowExe(request, flowDefineId, projectId, 1);
                }
            }
        }
        // 包信息
        model.addAttribute("packageList", resultExpert);
        model.addAttribute("projectId", projectId);
        // 进度
        model.addAttribute("reviewProgressList", reviewProgressList);
        model.addAttribute("flowDefineId", flowDefineId);
        return "bss/ppms/advanced_project/score_audit/list";
    }
    
    /**
     * 
     *〈专家详细评审〉
     *〈详细描述〉
     * @author FengTian
     * @param projectId
     * @param packageId
     * @param model
     * @return
     */
    @RequestMapping("detailedReview")
    public String detailedReview(String projectId, String packageId, Model model) {
        AdvancedPackages packages = packageService.selectById(packageId);
        if(packages != null){
            model.addAttribute("pack", packages);
        }
        // 项目分包信息
        HashMap<String, Object> pack = new HashMap<String, Object>();
        pack.put("projectId", projectId);
        List<AdvancedPackages> selectByAll = packageService.selectByAll(pack);
        if(selectByAll != null && !selectByAll.isEmpty()){
            Map<String, Object> list = new HashMap<String, Object>();
            // 进度集合
            List<ExpertSuppScore> expertScoreAll = new ArrayList<>();
            Map<String, Object> mapSearch = new HashMap<String, Object>();
            for (AdvancedPackages ps : selectByAll) {
                list.put("pack" + ps.getId(), ps);
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("packageId", ps.getId());
                List<AdvancedDetail> detailList = detailService.selectByAll(map);
                ps.setAdvancedDetails(detailList);
                // 设置查询条件
                mapSearch.put("projectId", projectId);
                mapSearch.put("packageId", ps.getId());
                List<ExpertSuppScore> expertList = expertScoreService.getScoreByMap(mapSearch);
                expertScoreAll.addAll(expertList);
            }
            // 供应商信息
            SaleTender record = new SaleTender();
            record.setPackages(packageId);
            record.setIsFirstPass(1);
            record.setIsRemoved("0");
            record.setIsTurnUp(0);
            List<SaleTender> supplierList = saleTenderService.getPackegeSuppliers(record);
            model.addAttribute("supplierList", supplierList);
         // 查询条件
            ProjectExtract projectExtract = new ProjectExtract();
            projectExtract.setProjectId(projectId);
            projectExtract.setReason("1");
            // 项目抽取的专家信息
            //List<ProjectExtract> expertList = projectExtractService.list(projectExtract);
            // 包中抽取的专家信息
            Map<String, Object> mapSearch1 = new HashMap<String, Object>(); 
            mapSearch1.put("projectId", projectId);
            mapSearch1.put("packageId", packageId);
            List<PackageExpert> expList = packageExpertService.selectList(mapSearch1);
            //判断是否结束评审
            if (expList != null && expList.size() > 0) {
                Short isEnd = expList.get(0).getIsGatherGather();
                model.addAttribute("isEnd", isEnd);
            }
            // 遍历进行排序   技术---经济---两者都有
            List<PackageExpert> expertList = new ArrayList<PackageExpert>();
            for (PackageExpert exp : expList) {
                DictionaryData data = DictionaryDataUtil.findById(exp.getReviewTypeId());
                if (data != null && "ECONOMY".equals(data.getCode())) {
                    expertList.add(exp);
                }
            }
            for (PackageExpert exp : expList) {
                DictionaryData data = DictionaryDataUtil.findById(exp.getReviewTypeId());
                if (data != null && "TECHNOLOGY".equals(data.getCode())) {
                    expertList.add(exp);
                }
            }
            model.addAttribute("expertList", expertList);
            // 包信息
            model.addAttribute("packageList", selectByAll);
            List<ExpertSuppScore> expertScoreList = new ArrayList<>();
            for (ExpertSuppScore score : expertScoreAll) {
                String expertId = score.getExpertId();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("expertId", expertId);
                map.put("packageId", packageId);
                List<PackageExpert> temp = packageExpertService.selectList(map);
                if (temp != null && temp.size() > 0 && temp.get(0) != null && temp.get(0).getIsGrade() == 1) {
                    expertScoreList.add(score);
                }
            }
            model.addAttribute("expertScoreList", expertScoreList);
        }
        AdvancedProject project = projectService.selectById(projectId);
        // 项目实体
        model.addAttribute("project", project);
        model.addAttribute("projectId", projectId);
        model.addAttribute("packageId", packageId);
        return "bss/ppms/advanced_project/score_audit/expert_detailed_review";
    }
    
    /**
     *〈简述〉
     * 退回分数
     *〈详细描述〉
     * @author WangHuijie
     * @param packageExpert
     * @param supplierId
     * @param model
     * @return 跳转到detailedReview.html
     */
    @RequestMapping("/backScore")
    @ResponseBody
    public String backScore(String projectId, String packageId, String expertId, Model model){
        // 将参数存储到model中以便redirect后取值
        model.addAttribute("projectId", projectId);
        model.addAttribute("packageId", packageId);
        // 封装参数到map中
        Map<String, Object> mapSearch = new HashMap<String, Object>();
        mapSearch.put("projectId", projectId);
        mapSearch.put("packageId", packageId);
        mapSearch.put("expertId", expertId);
        // 退回分数
        packageExpertService.backScore(mapSearch);
        // 跳转到showViewBySupplierId.html重新查询展示
        return "redirect:detailedReview.html";
    }
    
    /**
     *〈简述〉
     * 根据专家编号查看明细
     *〈详细描述〉
     * @author WangHuijie
     * @param packageId
     * @param expertId
     * @return
     */
    @RequestMapping("showViewByExpertId")
    public String showViewByExpertId(String packageId, String expertId, Model model, String projectId) {
        Expert expert = expertService.selectByPrimaryKey(expertId);
        model.addAttribute("expert", expert);
        model.addAttribute("expertId", expertId);
        
        AdvancedProject project = projectService.selectById(projectId);
        AdvancedPackages packages = packageService.selectById(packageId);
        if(packages != null){
            model.addAttribute("pack", packages);
        }
        //查询评分信息
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("packageId", packageId);
        // 专家可以打分的类型
        List<DictionaryData> markTermTypeList = new ArrayList<DictionaryData>();
        map.put("expertId", expertId);
        String typeId = packageExpertService.selectList(map).get(0).getReviewTypeId();
        markTermTypeList.add(DictionaryDataUtil.findById(typeId));
        model.addAttribute("markTermTypeList", markTermTypeList);
        // 查询所有的ScoreModel
        ScoreModel scoreModel = new ScoreModel();
        scoreModel.setPackageId(packageId);
        scoreModel.setProjectId(projectId);
        List<ScoreModel> scoreModelList = scoreModelService.findListByScoreModel(scoreModel);
        for (ScoreModel score : scoreModelList) {
            if (StringUtils.isBlank(score.getStandardScore())) {
                score.setStandardScore(score.getMaxScore());
            }
        }
        model.addAttribute("scoreModelList", scoreModelList);
        // 查出该包内所有的markTerm
        MarkTerm markTerm = new MarkTerm();
        markTerm.setProjectId(projectId);
        markTerm.setPackageId(packageId);
        List<MarkTerm> allMarkTerm = markTermService.findListByMarkTerm(markTerm);
        // 遍历去除pid is not null 的
        List<MarkTerm> markTermList = new ArrayList<MarkTerm>();
        for (MarkTerm mark : allMarkTerm) {
            if ("0".equals(mark.getPid()) && mark.getTypeName().equals(typeId)) {
                markTermList.add(mark);
            }
        }
        // 查询父节点的子节点个数
        for (int i = 0; i < markTermList.size(); i++) {
            int count = 0;
            for (ScoreModel score : scoreModelList) {
                if (markTermList.get(i).getId().equals(score.getMarkTerm().getPid())) {
                    count++;
                }
            }
            // 设置指定父节点的rowspan
            markTermList.get(i).setCount(count);
        }
        // 将count == 0 的移除
        List<MarkTerm> markTerms = new ArrayList<MarkTerm>();
        for (MarkTerm mark : markTermList) {
            if (mark.getCount() != 0) {
                markTerms.add(mark);
            }
        }
        if (markTerms.size() > 0 && scoreModelList.size() > 0) {
            for (int i = 0; i < markTerms.size(); i++) {
                int count = 0;
                for (int j = 0; j < scoreModelList.size(); j++) {
                    if (markTerms.get(i).getId().equals(scoreModelList.get(j).getMarkTerm().getPid())) {
                        if (count == 0) {
                            scoreModelList.get(j).setCount(markTerms.get(i).getCount());
                        } else {
                            scoreModelList.get(j).setCount(0); 
                        }
                        count++;
                    }
                }
            }
        }
        model.addAttribute("markTermList", markTerms);
        //查询供应商信息
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("packageId", packageId);
        hashMap.put("projectId", projectId);
        hashMap.put("isFirstPass", 1);
        hashMap.put("isRemoved", "0");
        hashMap.put("isTurnUp", 0);
        List<SaleTender> supplierList = saleTenderService.getAdPackegeSuppliers(hashMap);
        model.addAttribute("supplierList", supplierList);
        // 分数
        map.put("expertId", expertId);
        List<ExpertScore> scoresList = expertScoreService.selectInfoByMap(map);
        removeSame(scoresList);
        List<ExpertScore> scores = new ArrayList<ExpertScore>();
        // 判断如果该专家评分被退回就remove
        for (ExpertScore score : scoresList) {
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("packageId", score.getPackageId());
            map1.put("expertId", score.getExpertId());
            List<PackageExpert> temp = packageExpertService.selectList(map1);
            if (temp.get(0).getIsGrade() == 1) {
                scores.add(score);
            }
        }
        model.addAttribute("scores", scores);
        // 供应商经济总分,技术总分,总分
        List<SupplierRank> rankList = new ArrayList<SupplierRank>();
        for (SaleTender supp : supplierList) {
            SupplierRank rank = new SupplierRank();
            if(supp != null && supp.getSuppliers() != null){
                rank.setSupplierId(supp.getSuppliers().getId());
            }
            rank.setPackageId(supp.getPackages());
            BigDecimal es = supp.getEconomicScore();
            if (es == null) {
              rank.setEconScore(null);
            } else {
              rank.setEconScore(es);
            }
            BigDecimal ts = supp.getTechnologyScore();
            if (ts == null) {
              rank.setTechScore(null);
            } else {
              rank.setTechScore(ts);
            }
            if (es == null || ts == null) {
              rank.setSumScore(null);
            } else {
              rank.setSumScore(supp.getEconomicScore().add(supp.getTechnologyScore()));
            }
            rankList.add(rank);
        }
        // 循环遍历判断名次
        for (SupplierRank rank : rankList) {
            // 判断review_result是否不为空
            SaleTender saleTend = new SaleTender();
            saleTend.setPackages(rank.getPackageId());
            Supplier supplier = new Supplier();
            supplier.setId(rank.getSupplierId());
            saleTend.setSuppliers(supplier);
            String reviewResult = null;
            if(saleTenderService.findByCon(saleTend) != null && saleTenderService.findByCon(saleTend).size() > 0){
                reviewResult = saleTenderService.findByCon(saleTend).get(0).getReviewResult();
            }
            rank.setReviewResult(reviewResult);
        }
        model.addAttribute("rankList", rankList);
        // 新增参数
        double sum = 1;
        double length = (sum/supplierList.size())*100;
        model.addAttribute("length1", length + "%");
        model.addAttribute("length2", length/2 + "%");
        model.addAttribute("size", supplierList.size());
        model.addAttribute("project", project);
        model.addAttribute("projectId", projectId);
        model.addAttribute("packageId", packageId);
        return "bss/ppms/advanced_project/score_audit/view_expert_score";
    }
    
    /**
     *〈简述〉
     * 根据专家编号查看明细导出word
     *〈详细描述〉
     * @author liwanlin
     * @param packageId
     * @param expertId
     * @return
     */
    @RequestMapping("showViewByExpertIdWord")
    public String showViewByExpertIdWord(String packageId, String expertId, Model model, String projectId) {
        Expert expert = expertService.selectByPrimaryKey(expertId);
        model.addAttribute("expert", expert);
        model.addAttribute("expertId", expertId);
        AdvancedProject project = projectService.selectById(projectId);
        if(project != null){
            model.addAttribute("project", project);
        }
        AdvancedPackages packages = packageService.selectById(packageId);
        if(packages != null){
            model.addAttribute("pack", packages);
        }
        //查询评分信息
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("packageId", packageId);
        // 专家可以打分的类型
        List<DictionaryData> markTermTypeList = new ArrayList<DictionaryData>();
        map.put("expertId", expertId);
        String typeId = packageExpertService.selectList(map).get(0).getReviewTypeId();
        markTermTypeList.add(DictionaryDataUtil.findById(typeId));
        model.addAttribute("markTermTypeList", markTermTypeList);
        
        // 查询所有的ScoreModel
        ScoreModel scoreModel = new ScoreModel();
        scoreModel.setPackageId(packageId);
        scoreModel.setProjectId(projectId);
        List<ScoreModel> scoreModelList = scoreModelService.findListByScoreModel(scoreModel);
        for (ScoreModel score : scoreModelList) {
            if (StringUtils.isBlank(score.getStandardScore())) {
                score.setStandardScore(score.getMaxScore());
            }
        }
        model.addAttribute("scoreModelList", scoreModelList);
     // 查出该包内所有的markTerm
        MarkTerm markTerm = new MarkTerm();
        markTerm.setProjectId(projectId);
        markTerm.setPackageId(packageId);
        List<MarkTerm> allMarkTerm = markTermService.findListByMarkTerm(markTerm);
        // 遍历去除pid is not null 的
        List<MarkTerm> markTermList = new ArrayList<MarkTerm>();
        for (MarkTerm mark : allMarkTerm) {
            if ("0".equals(mark.getPid()) && mark.getTypeName().equals(typeId)) {
                markTermList.add(mark);
            }
        }
        // 查询父节点的子节点个数
        for (int i = 0; i < markTermList.size(); i++) {
            int count = 0;
            for (ScoreModel score : scoreModelList) {
                if (markTermList.get(i).getId().equals(score.getMarkTerm().getPid())) {
                    count++;
                }
            }
            // 设置指定父节点的rowspan
            markTermList.get(i).setCount(count);
        }
        // 将count == 0 的移除
        List<MarkTerm> markTerms = new ArrayList<MarkTerm>();
        for (MarkTerm mark : markTermList) {
            if (mark.getCount() != 0) {
                markTerms.add(mark);
            }
        }
        if (markTerms.size() > 0 && scoreModelList.size() > 0) {
            for (int i = 0; i < markTerms.size(); i++) {
                int count = 0;
                for (int j = 0; j < scoreModelList.size(); j++) {
                    if (markTerms.get(i).getId().equals(scoreModelList.get(j).getMarkTerm().getPid())) {
                        if (count == 0) {
                            scoreModelList.get(j).setCount(markTerms.get(i).getCount());
                        } else {
                            scoreModelList.get(j).setCount(0); 
                        }
                        count++;
                    }
                }
            }
        }
        model.addAttribute("markTermList", markTerms);
        //查询供应商信息
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("packageId", packageId);
        hashMap.put("projectId", projectId);
        hashMap.put("isFirstPass", 1);
        hashMap.put("isRemoved", "0");
        hashMap.put("isTurnUp", 0);
        List<SaleTender> supplierList = saleTenderService.getAdPackegeSuppliers(hashMap);
        int supplierListSize=supplierList.size()%8==0?supplierList.size()/8:supplierList.size()/8+1;
        List<Extension> extensionList=new ArrayList<Extension>();
        List<SaleTender> saleTenders=null;
        for(int i=1;i<=supplierListSize;i++){
           Extension extension=new Extension();
           saleTenders=new ArrayList<SaleTender>();
           for(int j=(i-1)*8;j<i*8;j++){
               if(j==supplierList.size()){
                 break;
              }
               SaleTender saleTender=supplierList.get(j);
               if(saleTender!=null){
                   saleTenders.add(saleTender);
               }else{
                   break;
               }
           }
           extension.setSupplierList(saleTenders);
           extensionList.add(extension);
        }
        model.addAttribute("supplierList", extensionList);
        // 分数
        map.put("expertId", expertId);
        List<ExpertScore> scoresList = expertScoreService.selectInfoByMap(map);
        removeSame(scoresList);
        List<ExpertScore> scores = new ArrayList<ExpertScore>();
        // 判断如果该专家评分被退回就remove
        for (ExpertScore score : scoresList) {
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("packageId", score.getPackageId());
            map1.put("expertId", score.getExpertId());
            List<PackageExpert> temp = packageExpertService.selectList(map1);
            if (temp.get(0).getIsGrade() == 1) {
                scores.add(score);
            }
        }
        model.addAttribute("scores", scores);
        // 供应商经济总分,技术总分,总分
        List<SupplierRank> rankList = new ArrayList<SupplierRank>();
        for (SaleTender supp : supplierList) {
            SupplierRank rank = new SupplierRank();
            rank.setSupplierId(supp.getSupplierId());
            rank.setPackageId(supp.getPackages());
            BigDecimal es = supp.getEconomicScore();
            if (es == null) {
              rank.setEconScore(null);
            } else {
              rank.setEconScore(es);
            }
            BigDecimal ts = supp.getTechnologyScore();
            if (ts == null) {
              rank.setTechScore(null);
            } else {
              rank.setTechScore(ts);
            }
            if (es == null || ts == null) {
              rank.setSumScore(null);
            } else {
              rank.setSumScore(supp.getEconomicScore().add(supp.getTechnologyScore()));
            }
            rankList.add(rank);
        }
        // 循环遍历判断名次
        for (SupplierRank rank : rankList) {
            // 判断review_result是否不为空
            SaleTender saleTend = new SaleTender();
            saleTend.setPackages(rank.getPackageId());
            Supplier supplier = new Supplier();
            supplier.setId(rank.getSupplierId());
            saleTend.setSuppliers(supplier);
            String reviewResult = saleTenderService.findByCon(saleTend).get(0).getReviewResult();
            rank.setReviewResult(reviewResult);
        }
        model.addAttribute("rankList", rankList);
        // 新增参数
        double sum = 1;
        double length = (sum/supplierList.size())*100;
        model.addAttribute("length1", length + "%");
        model.addAttribute("length2", length/2 + "%");
        model.addAttribute("size", supplierList.size());
        model.addAttribute("projectId", projectId);
        model.addAttribute("packageId", packageId);
        return "bss/ppms/advanced_project/score_audit/view_expert_score_word";
    }
    
    
    /**
     *〈简述〉
     * 根据供应商编号查看明细
     *〈详细描述〉
     * @author WangHuijie
     * @param packageId
     * @param supplierId
     * @return
     */
    @RequestMapping("showViewBySupplierId")
    public String showViewBySupplierId(String packageId, String supplierId, Model model, String projectId) {
        Supplier supplier = supplierService.get(supplierId);
        model.addAttribute("supplier", supplier);
        model.addAttribute("supplierId", supplierId);
        
        AdvancedProject project = projectService.selectById(projectId);
        if(project != null){
            model.addAttribute("project", project);
        }
        AdvancedPackages packages = packageService.selectById(packageId);
        if(packages != null){
            model.addAttribute("pack", packages);
        }
        //查询评分信息
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("packageId", packageId);
        // 查询所有的ScoreModel
        ScoreModel scoreModel = new ScoreModel();
        scoreModel.setPackageId(packageId);
        scoreModel.setProjectId(projectId);
        List<ScoreModel> scoreModelList = scoreModelService.findListByScoreModel(scoreModel);
        for (ScoreModel score : scoreModelList) {
            if (StringUtils.isBlank(score.getStandardScore())) {
                score.setStandardScore(score.getMaxScore());
            }
        }
        model.addAttribute("scoreModelList", scoreModelList);
     // 查出该包内所有的markTerm
        MarkTerm markTerm = new MarkTerm();
        markTerm.setProjectId(projectId);
        markTerm.setPackageId(packageId);
        List<MarkTerm> allMarkTerm = markTermService.findListByMarkTerm(markTerm);
        // 遍历去除pid is not null 的
        List<MarkTerm> markTermList = new ArrayList<MarkTerm>();
        for (MarkTerm mark : allMarkTerm) {
            if ("0".equals(mark.getPid())) {
                markTermList.add(mark);
            }
        }
        // 查询父节点的子节点个数
        for (int i = 0; i < markTermList.size(); i++) {
            int count = 0;
            for (ScoreModel score : scoreModelList) {
                if (markTermList.get(i).getId().equals(score.getMarkTerm().getPid())) {
                    count++;
                }
            }
            // 设置指定父节点的rowspan
            markTermList.get(i).setCount(count);
        }
        // 将count == 0 的移除
        List<MarkTerm> markTerms = new ArrayList<MarkTerm>();
        for (MarkTerm mark : markTermList) {
            if (mark.getCount() != 0) {
                markTerms.add(mark);
            }
        }
        if (markTerms.size() > 0 && scoreModelList.size() > 0) {
            for (int i = 0; i < markTerms.size(); i++) {
                int count = 0;
                for (int j = 0; j < scoreModelList.size(); j++) {
                    if (markTerms.get(i).getId().equals(scoreModelList.get(j).getMarkTerm().getPid())) {
                        if (count == 0) {
                            scoreModelList.get(j).setCount(markTerms.get(i).getCount());
                        } else {
                            scoreModelList.get(j).setCount(0); 
                        }
                        count++;
                    }
                }
            }
        }
        model.addAttribute("markTermList", markTerms);
        //查询专家信息
        List<PackageExpert> packExpertList = packageExpertService.selectList(map);
        List<Expert> expertList = new ArrayList<Expert>();
        for (PackageExpert packExpert : packExpertList) {
            expertList.add(expertService.selectByPrimaryKey(packExpert.getExpertId()));
        }
        model.addAttribute("expertList", expertList);
        // 分数
        map.put("supplierId", supplierId);
        List<ExpertScore> scoresList = expertScoreService.selectInfoByMap(map);
        removeSame(scoresList);
        List<ExpertScore> scores = new ArrayList<ExpertScore>();
        // 判断如果该专家评分被退回就remove
        for (ExpertScore score : scoresList) {
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("packageId", score.getPackageId());
            map1.put("expertId", score.getExpertId());
            List<PackageExpert> temp = packageExpertService.selectList(map1);
            if (temp.get(0).getIsGrade() == 1) {
                scores.add(score);
            }
        }
        model.addAttribute("scores", scores);
        // 新增参数
        double sum = 1;
        double length = (sum/expertList.size())*100;
        model.addAttribute("length1", length + "%");
        model.addAttribute("length2", length/2 + "%");
        model.addAttribute("size", expertList.size());
        model.addAttribute("projectId", projectId);
        model.addAttribute("packageId", packageId);
        model.addAttribute("type", 0);
        return "bss/prms/view_supplier_score";
    }
    
    @RequestMapping("/printRank")
    public String printRank(String packages, Model model) {
        AdvancedPackages packages2 = packageService.selectById(packages);
        if(packages2 != null){
            model.addAttribute("pack", packages2);
        }
        model.addAttribute("packages", packages);
        // 供应商信息
        SaleTender saleTender = new SaleTender();
        List<SaleTender> supplierList = new ArrayList<SaleTender>();
        saleTender.setPackages(packages2.getId());
        supplierList.addAll(saleTenderService.find(saleTender));
        List<SaleTender> suppList = new ArrayList<SaleTender>();
        for (SaleTender supp : supplierList) {
            if (supp.getIsFirstPass() != null && supp.getIsFirstPass() == 1 && !"1".equals(supp.getIsRemoved()) && supp.getIsTurnUp() != null && supp.getIsTurnUp() == 0) {
                suppList.add(supp);
            }
        }
        model.addAttribute("supplierList", suppList);
        List<SupplierRank> rankList = packageService.rankList(suppList);
        if(rankList != null && !rankList.isEmpty()){
            model.addAttribute("rankList", rankList);
        }
        List<PackageExpert> expList = packageService.expList(packages2.getId());
        if(expList != null && !expList.isEmpty()){
            model.addAttribute("expertList", expList);
        }
        // 专家给每个供应商打得分
        HashMap<String, Object> searchMap = new HashMap<>();
        searchMap.put("packageId", packages2.getId());
        List<ExpertSuppScore> scoreByMap = expertScoreService.getScoreByMap(searchMap);
        model.addAttribute("expertScoreList", scoreByMap);
        return "bss/ppms/advanced_project/rank/print_info";
    }
    
    /**
     *〈简述〉
     *〈详细描述〉
     * 汇总word
     * @author liwanlin
     * @param packages
     * @param model
     * @return
     */
    @RequestMapping("/printRankWord")
    public String printRankWord(String packages, Model model) {
        AdvancedPackages packages2 = packageService.selectById(packages);
        model.addAttribute("pack", packages2);
        model.addAttribute("packages", packages);
        // 供应商信息
        SaleTender saleTender = new SaleTender();
        List<SaleTender> supplierList = new ArrayList<SaleTender>();
        saleTender.setPackages(packages2.getId());
        supplierList.addAll(saleTenderService.find(saleTender));
        List<SaleTender> suppList = new ArrayList<SaleTender>();
        for (SaleTender supp : supplierList) {
            if (supp.getIsFirstPass() != null && supp.getIsFirstPass() == 1 && !"1".equals(supp.getIsRemoved()) && supp.getIsTurnUp() != null && supp.getIsTurnUp() == 0) {
                suppList.add(supp);
            }
        }
        int supplierListSize=suppList.size()%8==0?suppList.size()/8:suppList.size()/8+1;
        List<Extension> extensionList=new ArrayList<Extension>();
        List<SaleTender> saleTenders=null;
        for(int i=1;i<=supplierListSize;i++){
            Extension extension=new Extension();
            saleTenders=new ArrayList<SaleTender>();
            for(int j=(i-1)*8;j<i*8;j++){
                if(j==suppList.size()){
                  break;
               }
                SaleTender saleT=suppList.get(j);
                if(saleTender!=null){
                    saleTenders.add(saleT);
                }else{
                    break;
                }
            }
            extension.setSupplierList(saleTenders);
            extensionList.add(extension);
        }
        model.addAttribute("supplierList", extensionList);
        
        List<SupplierRank> rankList = packageService.rankList(suppList);
        if(rankList != null && !rankList.isEmpty()){
            model.addAttribute("rankList", rankList);
        }
        List<PackageExpert> expList = packageService.expList(packages2.getId());
        if(expList != null && !expList.isEmpty()){
            model.addAttribute("expertList", expList);
        }
        // 专家给每个供应商打得分
        HashMap<String, Object> searchMap = new HashMap<>();
        searchMap.put("packageId", packages2.getId());
        List<ExpertSuppScore> scoreByMap = expertScoreService.getScoreByMap(searchMap);
        model.addAttribute("expertScoreList", scoreByMap);
        return "bss/ppms/advanced_project/rank/print_info_word";
    }
    
    @RequestMapping("/checkAuditView")
    public String checkAuditView(String packageId, String projectId, Model model, String flowDefineId){
        //包与专家 关联表集合
        Map<String, Object> packageExpertmap = new HashMap<String, Object>();
        packageExpertmap.put("packageId", packageId);
        packageExpertmap.put("projectId", projectId);
        //查询专家
        List<PackageExpert> expertIdList = packageExpertService.selectList(packageExpertmap);
        if (expertIdList != null && expertIdList.size() > 0) {
            Short isEnd = expertIdList.get(0).getIsGatherGather();
            model.addAttribute("isEnd", isEnd);
            model.addAttribute("isSubmitCheck", expertIdList.get(0).getIsGatherGather());
        }
        // 获取符合性审查通过且到场没被移除的供应商
        SaleTender saleTender = new SaleTender();
        saleTender.setPackages(packageId);
        saleTender.setIsFirstPass(1);
        saleTender.setIsRemoved("0");
        saleTender.setIsTurnUp(0);
        List<SaleTender> supplierList = saleTenderService.findByCon(saleTender);
        if(supplierList != null && !supplierList.isEmpty()){
            model.addAttribute("supplierList", supplierList);
        }
        // 关联信息集合
        // 封装实体
        List<PackExpertExt> packExpertExtList = new ArrayList<>();
        PackExpertExt packExpertExt;
        for (PackageExpert packageExpert : expertIdList) {
            packExpertExt = new PackExpertExt();
            Expert expert = expertService.selectByPrimaryKey(packageExpert.getExpertId());
            packExpertExt.setExpert(expert);
            packExpertExt.setPackageId(packageExpert.getPackageId());
            packExpertExt.setProjectId(packageExpert.getProjectId());
            if (packageExpert.getIsGrade() == 0) {
                packExpertExt.setIsPass("暂无");
            }
            if (packageExpert.getIsGrade() == 1) {
                packExpertExt.setIsPass("已提交");
            }
            packExpertExtList.add(packExpertExt);
        }
        AdvancedPackages packages = packageService.selectById(packageId);
        if(packages != null){
            model.addAttribute("pack", packages);
        }
        model.addAttribute("packageId", packageId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("flowDefineId", flowDefineId);
        model.addAttribute("packExpertExtList", packExpertExtList);
        return "bss/ppms/advanced_project/score_audit/expert_detailed_check";
    }
    
    @RequestMapping("/expertConsult")
    public String expertConsult(String flag, String packageId, String projectId, Model model){
        AdvancedProject project = projectService.selectById(projectId);
        if(project != null){
            model.addAttribute("project", project);
        }
        HashMap<String ,Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("id", packageId);
        List<AdvancedPackages> selectByAll = packageService.selectByAll(map);
        if(selectByAll != null && !selectByAll.isEmpty()){
            model.addAttribute("pack", selectByAll.get(0));
        }
        // 获取符合性审查通过且到场没被移除的供应商
        SaleTender saleTender = new SaleTender();
        saleTender.setPackages(packageId);
        saleTender.setIsFirstPass(1);
        saleTender.setIsRemoved("0");
        saleTender.setIsTurnUp(0);
        List<SaleTender> sl = saleTenderService.findByCon(saleTender);
        //获取包下的评审项
        FirstAudit firstAudit = new FirstAudit();
        firstAudit.setPackageId(packageId);
        firstAudit.setIsConfirm((short)1);
        List<FirstAudit> list = firstAuditService.findBykind(firstAudit);
        Map<String, Object> auditMap = new HashMap<>();
        auditMap.put("packageId", packageId);
        auditMap.put("projectId", projectId);
        auditMap.put("isBack", 0);
        //获取所有专家对小项的评审结果
        List<ReviewFirstAudit> reviewFirstAudits = reviewFirstAuditService.selectList(auditMap);
        model.addAttribute("saleTenderList", sl);
        model.addAttribute("reviewFirstAudits", reviewFirstAudits);
        model.addAttribute("firstAudits", list);
        model.addAttribute("flag", flag);
        model.addAttribute("dds", DictionaryDataUtil.find(23));
        return "bss/ppms/advanced_project/audit/expert_consult_review";
    }
    
    @RequestMapping("/expertConsultWord")
    public String expertConsultWord(String flag, String packageId, String projectId, Model model){
        AdvancedProject project = projectService.selectById(projectId);
        if(project != null){
            model.addAttribute("project", project);
        }
        HashMap<String ,Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("id", packageId);
        List<AdvancedPackages> selectByAll = packageService.selectByAll(map);
        if(selectByAll != null && !selectByAll.isEmpty()){
            model.addAttribute("pack", selectByAll.get(0));
        }
        // 获取符合性审查通过且到场没被移除的供应商
        SaleTender saleTender = new SaleTender();
        saleTender.setPackages(packageId);
        saleTender.setIsFirstPass(1);
        saleTender.setIsRemoved("0");
        saleTender.setIsTurnUp(0);
        List<SaleTender> sl = saleTenderService.findByCon(saleTender);
        int supplierListSize=sl.size()%4==0?sl.size()/4:sl.size()/4+1;
        List<Extension> extensions=new ArrayList<Extension>();
        List<SaleTender> sleTenders=null;
        for(int i=1;i<=supplierListSize;i++){
            sleTenders=new ArrayList<SaleTender>();
            Extension extensionJ = new Extension();
            for(int j=(i-1)*4;j<i*4;j++){
                if(j==sl.size()){
                    break;
                }
                SaleTender saleTender2=sl.get(j);
                if(saleTender2 != null){
                    sleTenders.add(saleTender2);
                }else{
                    break;
                }
            }
            extensionJ.setSupplierList(sleTenders);
            extensions.add(extensionJ);
        }
        //获取包下的评审项
        FirstAudit firstAudit = new FirstAudit();
        firstAudit.setPackageId(packageId);
        firstAudit.setIsConfirm((short)1);
        List<FirstAudit> list = firstAuditService.findBykind(firstAudit);
        Map<String, Object> auditMap = new HashMap<>();
        auditMap.put("packageId", packageId);
        auditMap.put("projectId", projectId);
        auditMap.put("isBack", 0);
        //获取所有专家对小项的评审结果
        List<ReviewFirstAudit> reviewFirstAudits = reviewFirstAuditService.selectList(auditMap);
        model.addAttribute("dds", DictionaryDataUtil.find(23));
        model.addAttribute("saleTenderList", extensions);
        model.addAttribute("reviewFirstAudits", reviewFirstAudits);
        model.addAttribute("firstAudits", list);
        return "bss/prms/audit/expert_consult_review_word";
    }
    
    /**
     *〈简述〉
     *〈详细描述〉
     * 供应商总排名(展示分包信息)
     * @author WangHuijie
     * @param projectId
     * @param model
     * @return
     */
    @RequestMapping("supplierRank")
    public String supplierRank(String projectId, Model model, String flowDefineId){
        HashMap<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        List<AdvancedPackages> list = packageService.selectByAll(map);
        List<AdvancedPackages> packList = packageService.packList(projectId);
        if (packList.size() != list.size()) {
            // 判断有没有没显示出来的包
            model.addAttribute("flag", "1");
        }
        model.addAttribute("packagesList", packList);
        model.addAttribute("length", packList.size());
        
        List<SaleTender> suppList = packageService.suppList(packList, projectId);
        if(suppList != null && !suppList.isEmpty()){
            model.addAttribute("supplierList", suppList);
            
            List<SupplierRank> rankList = packageService.rankList(suppList);
            if(rankList != null && !rankList.isEmpty()){
                model.addAttribute("rankList", rankList);
            }
        }
        if(packList != null && !packList.isEmpty()){
            List<PackageExpert> packageExperts = new ArrayList<PackageExpert>();
            for (AdvancedPackages packages : packList) {
                List<PackageExpert> expList = packageService.expList(packages.getId());
                packageExperts.addAll(expList);
            }
            model.addAttribute("expertList", packageExperts);
        }
        // 专家给每个供应商打得分
        List<ExpertSuppScore> expertScoreList = new ArrayList<ExpertSuppScore>();
        HashMap<String, Object> searchMap = new HashMap<>();
        searchMap.put("projectId", projectId);
        for (AdvancedPackages pack : packList) {
            searchMap.put("packageId", pack.getId());
            expertScoreList.addAll(expertScoreService.getScoreByMap(searchMap));
        }
        model.addAttribute("expertScoreList", expertScoreList);
        // 跳转
        model.addAttribute("projectId", projectId); 
        model.addAttribute("flowDefineId", flowDefineId);  
        return "bss/ppms/advanced_project/rank/supplier_rank";
    }
    
    //给每个包的供应商，和物资明细赋值
    public List<Supplier> setField(List<Quote> listQuote) {
        List<Supplier> suList1 = new ArrayList<Supplier>();
        List<Supplier> suList2 = new ArrayList<Supplier>();
        for (Quote quoteSupplier : listQuote) {
            if (suList1.size() > 0) {
                suList1.add(quoteSupplier.getSupplier());
            } else {
                suList1.add(quoteSupplier.getSupplier());
            }
        }
        //去重
        for (int i = 0; i < suList1.size(); i++) {
            if (i == 0) {
                suList2.add(suList1.get(i));
            } else {
                if (!suList2.contains(suList1.get(i))) {
                    suList2.add(suList1.get(i));
                }
            }
        }
        
        for (Supplier sup : suList2) {
            List<Quote> quoteList = new ArrayList<Quote>();
            for (Quote quote1 : listQuote) {
                if (quote1.getSupplier().getId().equals(sup.getId())) {
                    if (quoteList.size() > 0) {
                        for (Quote quote2 : quoteList) {
                            if (quote2.getProjectDetail().getId().equals(quote1.getProjectDetail().getId())) {
                                continue;
                            } else {
                                quoteList.add(quote1);
                                break;
                            }
                        }
                    } else {
                        quoteList.add(quote1);
                    }
                }
            }
            //每个供应商的明细
            sup.setQuoteList(quoteList);
        }
        return suList2;
    }
    
    //给最新的报价赋值
    public void setNewQuote(List<Quote> listQuote, List<Quote> listQuotebyPackage) {
        for (Quote q : listQuote) {
            for (Quote qp : listQuotebyPackage) {
                if (qp.getPackageId().equals(q.getPackageId()) && qp.getSupplierId().equals(q.getSupplierId()) && qp.getProductId().equals(q.getProductId())) {
                    q.setTotal(qp.getTotal());
                    q.setQuotePrice(qp.getQuotePrice());
                    q.setRemark(qp.getRemark());
                    q.setDeliveryTime(qp.getDeliveryTime());
                }
            }
        }
    }
    
    /**
     *〈简述〉
     * 对List<ExpertScore>去重
     *〈详细描述〉
     * @author WangHuijie
     * @param list
     * @return
     */
    private List<ExpertScore> removeSame(List<ExpertScore> list){
        for (int i = 0; i < list.size(); i++) {
            for (int j = list.size() - 1 ; j > i; j--) {
                if (list.get(i).getScoreModelId().equals(list.get(j).getScoreModelId()) && list.get(i).getExpertId().equals(list.get(j).getExpertId()) && list.get(i).getSupplierId().equals(list.get(j).getSupplierId())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

}
