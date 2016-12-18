package bss.controller.prms;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ses.model.bms.DictionaryData;
import ses.model.ems.ExpExtPackage;
import ses.model.ems.Expert;
import ses.model.ems.ProjectExtract;
import ses.model.sms.Quote;
import ses.model.sms.Supplier;
import ses.service.ems.ExpExtPackageService;
import ses.service.ems.ExpExtractRecordService;
import ses.service.ems.ExpertService;
import ses.service.ems.ProjectExtractService;
import ses.service.sms.SupplierQuoteService;
import ses.service.sms.SupplierService;
import ses.util.CnUpperCaser;
import ses.util.DictionaryDataUtil;
import bss.model.ppms.FlowExecute;
import bss.model.ppms.Money;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.SaleTender;
import bss.model.prms.ExpertScore;
import bss.model.prms.FirstAudit;
import bss.model.prms.PackageExpert;
import bss.model.prms.PackageFirstAudit;
import bss.model.prms.ReviewFirstAudit;
import bss.model.prms.ReviewProgress;
import bss.model.prms.ext.AuditModelExt;
import bss.model.prms.ext.ExpertSuppScore;
import bss.model.prms.ext.Extension;
import bss.model.prms.ext.PackExpertExt;
import bss.model.prms.ext.SupplierExt;
import bss.service.ppms.AduitQuotaService;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SaleTenderService;
import bss.service.prms.ExpertScoreService;
import bss.service.prms.FirstAuditService;
import bss.service.prms.PackageExpertService;
import bss.service.prms.PackageFirstAuditService;
import bss.service.prms.ReviewFirstAuditService;
import bss.service.prms.ReviewProgressService;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("packageExpert")
public class PackageExpertController {
    private final static int ONE = 1;
    private final static short SONE = 1;
    @Autowired
    private PackageExpertService service;
    @Autowired
    private ProjectDetailService detailService;
    @Autowired
    private PackageService packageService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectExtractService projectExtractService;
    @Autowired
    private SaleTenderService saleTenderService;// 供应商查询
    @Autowired
    private ReviewProgressService reviewProgressService;// 进度
    @Autowired
    private ExpertService expertService;// 专家
    @Autowired
    private ReviewFirstAuditService reviewFirstAuditService;// 初审表
    @Autowired
    private PackageExpertService packageExpertService;// 专家项目包 关联表
    @Autowired
    private SupplierQuoteService supplierQuoteService;// 供应商报价
    @Autowired
    private ExpertScoreService expertScoreService;// 专家评分
    @Autowired
    private AduitQuotaService aduitQuotaService;// 评分
    @Autowired
    private PackageFirstAuditService packageFirstAuditService;//包关联初审项
    @Autowired
    private FirstAuditService firstAuditService;//初审项
    @Autowired
    private SupplierService supplierService;//初审项
    @Autowired
    private FlowMangeService flowMangeService;//环节
    @Autowired
    private ExpExtPackageService expExtPackageService;//项目包关联
    @Autowired
    private ExpExtractRecordService expExtractRecordService; //专家抽取记录表

    /**
     *〈简述〉跳转分配专家
     *〈详细描述〉
     * @author Ye MaoLin
     * @param projectId 项目id
     * @param model
     * @param flowDefineId 流程环节id
     * @return
     */
    @RequestMapping("/assignedExpert")
    public String assignedExpert(String projectId, Model model, String flowDefineId) {
        //获取组长信息
        FlowExecute execute=new FlowExecute();
        execute.setProjectId(projectId);
        execute.setIsDeleted(0);
        execute.setFlowDefineId(flowDefineId);
        List<FlowExecute> findFlowExecute = flowMangeService.findFlowExecute(execute);
        if (findFlowExecute.size() != 0 && findFlowExecute.get(0).getStatus() != null &&   findFlowExecute.get(0).getStatus() == 1){
            model.addAttribute("execute","SCCUESS" );
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        map.put("isGroupLeader", 1);
        List<PackageExpert> selectList = service.selectList(map);
        model.addAttribute("selectList", selectList);

        List<Packages> packages = packageService.listResultAllExpert(projectId);
        Project project = projectService.selectById(projectId);

        // 包信息
        model.addAttribute("packageList", packages);
        // 项目实体
        model.addAttribute("project", project);

        model.addAttribute("flowDefineId", flowDefineId);
        return "bss/prms/assign_expert/expert_list";
    }

    /**
     *〈简述〉展示专家列表
     *〈详细描述〉
     * @author Ye MaoLin
     * @param projectId 项目id
     * @param model
     * @param flowDefineId 流程环节id
     * @return  
     */
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
        return "bss/prms/assign_expert/list";
    }

    /**
     *〈简述〉
     * 专家后台评分汇总按钮点击事件
     *〈详细描述〉
     * 展示专家信息
     * @author WangHuijie
     * @param model
     * @param projectId
     * @param packageId
     * @return
     */
    @RequestMapping("toTotal")
    public String toTotal(Model model, String projectId, String packageId){
      //获取关联信息
        ExpExtPackage extPackag = new ExpExtPackage();
        extPackag.setProjectId(projectId);
        extPackag.setPackageId(packageId);
        List<ExpExtPackage> list = expExtPackageService.list(extPackag, "0");
        // 项目抽取的专家信息
        if (list != null && list.size() !=0 ){
            ProjectExtract projectExtract = new ProjectExtract();
            projectExtract.setProjectId(list.get(0).getId());
            projectExtract.setIsProvisional((short)1);
            projectExtract.setReason("1");
            List<ProjectExtract> expertList = projectExtractService.list(projectExtract);
            model.addAttribute("expertList", expertList);
            model.addAttribute("packageId", packageId);
            model.addAttribute("projectId", projectId);
        }
        return "bss/prms/audit/expert_list";
    }

    /**
     * 
     *〈简述〉执行完成
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param projectId 项目id 
     * @param flowDefineId 流程id 
     * @return
     */
    @ResponseBody
    @RequestMapping("/executeFinish")
    public String executeFinish(String  projectId, String flowDefineId,HttpServletRequest sq){
        //获取组长信息315B381C86D74703AF44CA675E126A55
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        map.put("isGroupLeader", 1);
        //获取关联包的专家
        List<PackageExpert> selectList = service.selectList(map);
        //获取项目下的所有包
        List<Packages> packageList = packageService.findPackageById(map);
        Set<PackageExpert> set = new HashSet<PackageExpert>(selectList);
        selectList.clear();
        selectList.addAll(set);
        if (selectList.size() == packageList.size()){
            //             //修改流程状态
            flowMangeService.flowExe(sq, flowDefineId, projectId, 1);
        }else{
            return JSON.toJSONString("ERROR");
        }
        return JSON.toJSONString("SCCUESS");

    }


    /**
     *〈简述〉跳转供应商报价
     *〈详细描述〉
     * @author Ye MaoLin
     * @param projectId 项目id
     * @param model
     * @param flowDefineId 
     * @return
     * @throws ParseException 处理异常
     */
    @RequestMapping("/toSupplierQuote")
    public String toSupplierQuote(HttpServletRequest req, String projectId, Model model, String flowDefineId) throws ParseException {
        // 供应商信息
        List<SaleTender> supplierList = saleTenderService.list(new SaleTender(projectId), 0);
        List<Packages> packages = packageService.listResultExpert(projectId);
        //
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        List<Packages> listPackage = supplierQuoteService.selectByPrimaryKey(map, null);
        java.text.DecimalFormat df=new java.text.DecimalFormat("#.00");
        for (SaleTender stender : supplierList) {
            Quote qt = new Quote();
            qt.setProjectId(projectId);
            qt.setSupplierId(stender.getSuppliers().getId());
            List<Date> listDate = supplierQuoteService.selectQuoteCount(qt);
            List<Packages> listPackageEach = new ArrayList<Packages>();
            SaleTender st = new SaleTender();
            st.setProjectId(projectId);
            st.setSupplierId(stender.getSuppliers().getId());
            List<SaleTender> stList = saleTenderService.find(st);
            if (stList != null && stList.size() > 0) {
                String packageStr = stList.get(0).getPackages();
                for (Packages packa : listPackage) {
                    if (packageStr.indexOf(packa.getId()) != -1) {
                        listPackageEach.add(packa);
                    }
                }
            }
            List<Money> listMoney = new ArrayList<Money>();
            for (Packages pk:listPackageEach) {
                Money money = new Money();
                Quote quote = new Quote();
                if(listDate != null && listDate.size() > 0){
                    quote.setCreatedAt(new Timestamp(listDate.get(listDate.size()-1).getTime()));
                    quote.setPackageId(pk.getId());
                    List<Quote> quoteList = supplierQuoteService.selectQuoteHistoryList(quote);
                    BigDecimal totalMoney = BigDecimal.ZERO;
                    for (Quote q : quoteList) {
                        totalMoney = totalMoney.add(q.getTotal());
                    }
                    money.setPackageName(pk.getName());
                    money.setTotalMoney(new BigDecimal(df.format(totalMoney)));
                    money.setUpperName(CnUpperCaser.getCnString(totalMoney.doubleValue()));
                }else{
                    money.setPackageName(pk.getName());
                    money.setTotalMoney(BigDecimal.ZERO);
                    money.setUpperName(CnUpperCaser.getCnString(0));
                }
                listMoney.add(money);
            }
            stender.setMoney(listMoney);
        }
        //----
        // 包信息
        model.addAttribute("packageList", packages);
        model.addAttribute("supplierList", supplierList);
        model.addAttribute("projectId", projectId);
        model.addAttribute("flowDefineId", flowDefineId);
        return "bss/prms/supplier_quote/quote_list";
    }

    /**
     *〈简述〉跳转查看评审进度
     *〈详细描述〉
     * @author Ye MaoLin
     * @param projectId 项目id
     * @param model 
     * @param flowDefineId 流程环节id
     * @return
     */
    @RequestMapping("/toAuditProgress")
    public String toAuditProgress(String projectId, Model model, String flowDefineId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        // 进度集合
        List<ReviewProgress> reviewProgressList = reviewProgressService.selectByMap(map);
        List<Packages> packages = packageService.listResultExpert(projectId);
        if (reviewProgressList.size() < packages.size()) {
            for (Packages pg : packages) {
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("projectId", projectId);
                map2.put("packageId", pg.getId());
                //查询该包有没有评审进度数据
                List<ReviewProgress> rplist = reviewProgressService.selectByMap(map2);
                if (rplist == null || rplist.size() <= 0) {
                    ReviewProgress reviewProgress = new ReviewProgress();
                    reviewProgress.setAuditStatus("0");
                    reviewProgress.setFirstAuditProgress(0.00);
                    reviewProgress.setPackageId(pg.getId());
                    reviewProgress.setPackageName(pg.getName());
                    reviewProgress.setProjectId(projectId);
                    reviewProgress.setScoreProgress(0.00);
                    reviewProgress.setTotalProgress(0.00);
                    reviewProgressList.add(reviewProgress);
                }
            }
        }
        // 包信息
        model.addAttribute("packageList", packages);
        // 进度
        model.addAttribute("reviewProgressList", reviewProgressList);
        model.addAttribute("flowDefineId", flowDefineId);
        return "bss/prms/bid_audit/audit_progress";
    }

    /**
     * 
     * @Title: toPackageExpert
     * @author ShaoYangYang
     * @date 2016年10月18日 下午3:05:52
     * @Description: TODO 跳转到组织专家评审页面
     * @param @param projectId
     * @param @return
     * @return String
     */
    @RequestMapping("toPackageExpert")
    public String toPackageExpert(String projectId, String flag, Model model) {
        // 项目分包信息
        //HashMap<String, Object> pack = new HashMap<String, Object>();
        //pack.put("projectId", projectId);
        //List<Packages> packages = packageService.findPackageById(pack);
        List<Packages> packages = packageService.listResultExpert(projectId);
        Map<String, Object> list = new HashMap<String, Object>();
        // 关联表集合
        List<PackageExpert> expertIdList = new ArrayList<>();
        // 进度集合
        List<ReviewProgress> reviewProgressList = new ArrayList<>();
        List<ExpertSuppScore> expertScoreAll = new ArrayList<>();
        List<AuditModelExt> auditModelListAll = new ArrayList<>();
        Map<String, Object> mapSearch = new HashMap<String, Object>();
        for (Packages ps : packages) {
            list.put("pack" + ps.getId(), ps);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("packageId", ps.getId());
            List<ProjectDetail> detailList = detailService.selectById(map);
            ps.setProjectDetails(detailList);
            // 设置查询条件
            mapSearch.put("projectId", projectId);
            mapSearch.put("packageId", ps.getId());
            // 查询出该项目下的包关联的信息集合
            List<PackageExpert> selectList = service.selectList(mapSearch);
            // 查询评审项
            List<AuditModelExt> auditModelExtList = aduitQuotaService
                .findAllByMap(mapSearch);
            auditModelListAll.addAll(auditModelExtList);
            // 查询评分信息
            //List<ExpertScore> expertList = expertScoreService.selectByMap(mapSearch);
            // 查询评分信息(由按项目查改为按供应商和包查)
            List<ExpertSuppScore> expertList = expertScoreService.getScoreByMap(mapSearch);
            for (ExpertSuppScore expertSuppScore : expertList) {
                System.out.println(expertSuppScore.getScore());
            }
            expertScoreAll.addAll(expertList);
            model.addAttribute("expertIdList", expertIdList);
            // 查询进度
            List<ReviewProgress> selectByMap = reviewProgressService
                .selectByMap(map); 
            expertIdList.addAll(selectList);
            reviewProgressList.addAll(selectByMap);
        }
        // 进度
        model.addAttribute("reviewProgressList", reviewProgressList);
        // 供应商信息
        List<SaleTender> supplierList = saleTenderService.list(new SaleTender(
            projectId), 0);
        model.addAttribute("supplierList", supplierList);
        // 查询条件
        ProjectExtract projectExtract = new ProjectExtract();
        projectExtract.setProjectId(projectId);
        projectExtract.setReason("1");
        // 项目抽取的专家信息
        List<ProjectExtract> expertList = projectExtractService
            .list(projectExtract);
        model.addAttribute("expertList", expertList);
        // 包信息
        model.addAttribute("packageList", packages);
        Project project = projectService.selectById(projectId);
        // 项目实体
        model.addAttribute("project", project);
        // 关联信息集合
        // 封装实体
        List<PackExpertExt> packExpertExtList = new ArrayList<>();
        // 供应商封装实体
        List<SupplierExt> supplierExtList = new ArrayList<>();
        PackExpertExt packExpertExt;
        for (PackageExpert packageExpert : expertIdList) {
            packExpertExt = new PackExpertExt();
            Expert expert = expertService.selectByPrimaryKey(packageExpert
                .getExpertId());
            packExpertExt.setExpert(expert);
            packExpertExt.setPackageId(packageExpert.getPackageId());
            packExpertExt.setProjectId(packageExpert.getProjectId());
            Map<String, Object> map = new HashMap<>();
            // 根据专家id和包id查询改包的这个专家是否评审完成
            map.put("expertId", packageExpert.getExpertId());
            map.put("packageId", packageExpert.getPackageId());
            // 根据专家id查询关联表 确定该专家是否都已评审
            List<PackageExpert> selectList = service.selectList(map);
            int count = 0;
            for (PackageExpert packageExpert2 : selectList) {
                if (packageExpert2.getIsAudit() == SONE) {
                    count++;
                }
            }
            if (count > 0) {
                packExpertExt.setIsPass("已评审");
            } else {
                packExpertExt.setIsPass("未评审");
            }
            // 根据供应商id 和包id查询审核表 确定该供应商是否通过评审
            for (SaleTender saleTender : supplierList) {
                SupplierExt supplierExt = new SupplierExt();
                Map<String, Object> map2 = new HashMap<>();
                map2.put("supplierId", saleTender.getSuppliers().getId());
                map2.put("packageId", packageExpert.getPackageId());
                map2.put("expertId", packageExpert.getExpertId());
                List<ReviewFirstAudit> selectList2 = reviewFirstAuditService
                    .selectList(map2);
                if (selectList2 != null && selectList2.size() > 0) {
                    int count2 = 0;
                    for (ReviewFirstAudit reviewFirstAudit : selectList2) {
                        if (reviewFirstAudit.getIsPass() == SONE) {
                            count2++;
                            break;
                        }
                    }
                    // 如果变量大于0 说明有不合格的数据
                    if (count2 > 0) {
                        supplierExt.setSupplierId(saleTender.getSuppliers()
                            .getId());
                        supplierExt.setExpertId(packageExpert.getExpertId());
                        supplierExt.setPackageId(packageExpert.getPackageId());
                        supplierExt.setSuppIsPass("不合格");
                    } else {
                        supplierExt.setSupplierId(saleTender.getSuppliers()
                            .getId());
                        supplierExt.setExpertId(packageExpert.getExpertId());
                        supplierExt.setPackageId(packageExpert.getPackageId());
                        supplierExt.setSuppIsPass("合格");
                    }
                } else {
                    supplierExt
                    .setSupplierId(saleTender.getSuppliers().getId());
                    supplierExt.setPackageId(packageExpert.getPackageId());
                    supplierExt.setExpertId(packageExpert.getExpertId());
                    supplierExt.setSuppIsPass("未评审");
                }

                supplierExtList.add(supplierExt);
            }
            packExpertExtList.add(packExpertExt);
        }
        // 评审项信息
        removeAuditModelExt(auditModelListAll);
        model.addAttribute("auditModelListAll", auditModelListAll);
        model.addAttribute("packExpertExtList", packExpertExtList);
        model.addAttribute("supplierExtList", supplierExtList);
        model.addAttribute("expertScoreList", expertScoreAll);
        // 成功标示
        model.addAttribute("flag", flag);
        return "bss/prms/package_expert";
    }

    /**
     * 
     * @Title: removeAuditModelExt
     * @author ShaoYangYang
     * @date 2016年11月18日 下午3:18:44
     * @Description: TODO 去重复
     * @param @param list
     * @return void
     */
    private static void removeAuditModelExt(List<AuditModelExt> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getScoreModelId()
                    .equals(list.get(i).getScoreModelId())) {
                    list.remove(j);
                }
            }
        }
    }


    /**
     * 
     *〈简述〉 添加专家。并设置组长
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param chkItem
     * @param packageExpert
     * @param groupId
     * @param attr
     * @return
     */
    @RequestMapping("relate")
    public String relate(String packageId, String groupId, RedirectAttributes attr,HttpServletRequest sq,String flowDefineId) {
        //获取关联信息
        Packages packages = new Packages();
        packages.setId(packageId);
        List<Packages> find = packageService.find(packages);
        String projectId = ""; 
        if (find != null && find.size() !=0 ){
            projectId=find.get(0).getProjectId();
        // 项目抽取的专家信息
            ProjectExtract projectExtract = new ProjectExtract();
            projectExtract.setProjectId(packageId);
            projectExtract.setIsProvisional((short)1);
            projectExtract.setReason("1");
            List<ProjectExtract> expertList = projectExtractService.list(projectExtract);
            for (ProjectExtract projectExtract2 : expertList) {

                PackageExpert packageExpert = new PackageExpert();
                // 设置专家id
                packageExpert.setExpertId(projectExtract2.getExpert().getId());
                packageExpert.setPackageId(packageId);
                packageExpert.setProjectId(projectId);
                // 评审状态 未评审
                packageExpert.setIsAudit((short) 0);
                // 初审是否汇总 未汇总
                packageExpert.setIsGather((short) 0);
                // 是否评分
                packageExpert.setIsGrade((short) 0);
                // 评分是否汇总
                packageExpert.setIsGatherGather((short) 0);
                // 判断组长id是否和选择的专家id一致，如果一致就设定为组长
                if (groupId.equals(projectExtract2.getExpert().getId())) {
                    packageExpert.setIsGroupLeader((short) 1);
                } else {
                    packageExpert.setIsGroupLeader((short) 0);
                }

                Map<String, Object> maps = new HashMap<String, Object>();
                maps.put("expertId", projectExtract2.getExpert().getId());
                maps.put("packageId", packageId);
                List<PackageExpert> selectList2 = service.selectList(maps);
                if (selectList2 != null && selectList2.size() != 0 ){
                    //如果和本次相同就不进行修改
                    if (selectList2.get(0).getIsGroupLeader() != packageExpert.getIsGroupLeader()){
                        service.updateByBean(packageExpert);
                    }  
                } else {
                    service.save(packageExpert);
                }

            }
            //修改流程状态
            flowMangeService.flowExe(sq, flowDefineId, projectId, 2);
        }
        return "redirect:/packageExpert/assignedExpert.html?projectId=" + projectId + "&&flowDefineId=" + flowDefineId;
    }

   
    /**
     * @Title: getPace
     * @author ShaoYangYang
     * @date 2016年10月27日 下午8:13:46
     * @Description: TODO 初审汇总
     * @param @param projectId
     * @param @param packageId
     * @param @param expertId
     * @return void
     * @throws IOException 
     */
    @RequestMapping("gather")
    @ResponseBody
    public void getPace(String projectId, String packageIds, HttpServletResponse response) throws IOException {
        try {
            PackageExpert record = new PackageExpert();
            String[] packageIdArr = packageIds.split(",");
            String msg = "";
            //遍历选中的包
            if (packageIdArr != null && packageIdArr.length > 0) {
                for (String packageId : packageIdArr) {
                    HashMap<String, Object> packmap = new HashMap<String, Object>();
                    packmap.put("id", packageId);
                    List<Packages> packages = packageService.findPackageById(packmap);
                    Packages pa = new Packages();
                    if (packages != null && packages.size() > 0) {
                        pa = packages.get(0);
                    }
                    Map<String, Object> map = new HashMap<>();
                    map.put("projectId", projectId);
                    map.put("packageId", packageId);
                    // 查询包关联专家实体
                    List<PackageExpert> selectList = packageExpertService.selectList(map);
                    if (selectList != null && selectList.size() > 0) {
                        int count2 = 0;
                        for (PackageExpert packageExpert : selectList) {
                            //判断为审核过的 和未汇总的 才执行汇总
                            if (packageExpert.getIsAudit() == SONE && packageExpert.getIsGather() != SONE) {
                                count2 ++;
                            } else {
                                break;
                            }
                        }
                        if (count2 == selectList.size()) {
                            record.setIsGather((short) 1);
                            record.setPackageId(packageId);
                            record.setProjectId(projectId);
                            service.updateByBean(record);
                            String str = "【"+pa.getName()+"】";
                            msg += str;
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
     * 
     * @Title: isBack
     * @author ShaoYangYang
     * @date 2016年10月27日 下午8:13:46
     * @Description: TODO 初审退回
     * @param @param projectId
     * @param @param packageId
     * @param @param expertId
     * @return void
     */
    @RequestMapping("isBack")
    @ResponseBody
    public void isBack(PackageExpert record, HttpServletResponse response) {
        try {
            // 查询是否已评审
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("expertId", record.getExpertId());
            map.put("packageId", record.getPackageId());
            map.put("projectId", record.getProjectId());
            List<PackageExpert> selectList = service.selectList(map);
            if (selectList != null && selectList.size() > 0) {
                PackageExpert packageExpert = selectList.get(0);
                // 必须是已评审 但未评分的数据才能退回
                if (packageExpert.getIsAudit() != SONE
                    || packageExpert.getIsGrade() == SONE) {

                    response.getWriter().print("0");
                } else {
                    // 初审结果集合
                    List<ReviewFirstAudit> reviewFIrstAuditList = reviewFirstAuditService
                        .selectList(map);
                    // 判断是否全部通过，如果全部通过则不允许退回
                    int count = 0;
                    for (ReviewFirstAudit reviewFirstAudit : reviewFIrstAuditList) {
                        // 为1 证明有不合格数据
                        if (reviewFirstAudit.getIsPass() == SONE) {
                            count++;
                        }
                    }
                    if (count == 0) {
                        response.getWriter().print("0");
                        return;
                    }
                    // 查询是否已评审
                    Map<String, Object> map2 = new HashMap<String, Object>();
                    map2.put("packageId", record.getPackageId());
                    map2.put("projectId", record.getProjectId());
                    // 该专家下的审核项目关联集合
                    List<PackageExpert> packageExpertList = packageExpertService
                        .selectList(map2);
                    // 判断是否为全部已评审状态
                    for (PackageExpert packageExpert2 : packageExpertList) {
                        if (packageExpert2.getIsAudit() == SONE) {
                            // 查询改项目的进度信息
                            List<ReviewProgress> reviewProgressList = reviewProgressService
                                .selectByMap(map2);
                            // 更新项目进度
                            if (reviewProgressList != null
                                && reviewProgressList.size() > 0) {
                                ReviewProgress progress = reviewProgressList
                                    .get(0);
                                // 修改进度
                                if (packageExpertList != null
                                    && packageExpertList.size() > 0) {
                                    // 计算当前专家占用的进度比重
                                    double first = 1 / (double) packageExpertList
                                        .size();
                                    BigDecimal b = new BigDecimal(first);
                                    double firstProgress = b.setScale(2,
                                        BigDecimal.ROUND_HALF_UP)
                                        .doubleValue();
                                    // 计算退回后的初审进度
                                    Double firstAuditProgress = progress
                                        .getFirstAuditProgress();
                                    // 最终进度
                                    double endFirst = firstAuditProgress
                                        - firstProgress;
                                    // 退回后的初审进度
                                    progress.setFirstAuditProgress(endFirst);
                                    // 初审退回 评分进度清空 重新评分
                                    // progress.setScoreProgress((double) 0.0);
                                    // 总进度比例
                                    double totalProgress = (firstProgress + progress
                                        .getScoreProgress()) / 2;
                                    // 当前总进度
                                    Double totalProgress2 = progress
                                        .getTotalProgress();
                                    // 计算退回之后的总进度
                                    progress.setTotalProgress(totalProgress2
                                        - totalProgress);

                                    // 修改
                                    reviewProgressService
                                    .updateByPrimaryKeySelective(progress);
                                }
                            }
                        }
                    }
                    Short flag = 0;
                    record.setIsGather(flag);
                    record.setIsAudit(flag);
                    service.updateByBean(record);
                    response.getWriter().print("1");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 
     * @Title: isBackScore
     * @author ShaoYangYang
     * @date 2016年10月27日 下午8:13:46
     * @Description: TODO 评分确认或退回
     * @param @param projectId
     * @param @param packageId
     * @param @param expertId
     * @return void
     */
    @RequestMapping("isBackScore")
    @ResponseBody
    public void isBackScore(String projectId, String packageId,
                            String supplierId, String scoreModelId, Integer flag,
                            HttpServletResponse response) {
        try {
            // 查询是否已评审
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("projectId", projectId);
            map.put("packageId", packageId);
            // 1为退回
            if (flag == ONE) {
                // 判断能不能退回
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("projectId", projectId);
                map2.put("packageId", packageId);
                map2.put("supplierId", supplierId);
                map2.put("scoreModelId", scoreModelId);
                List<ExpertScore> expertScoreList = expertScoreService
                    .selectByMap(map2);
                if (expertScoreList == null || expertScoreList.size() == 0) {
                    // 为空证明没有评分数据 则不能退回
                    response.getWriter().print("tuihui");
                    return;
                } else {
                    for (ExpertScore expertScore : expertScoreList) {
                        BigDecimal score = expertScore.getScore();
                        // 如果有没有得分数据的也证明没有评分
                        if (score == null) {
                            response.getWriter().print("tuihui");
                            return;
                        }
                    }
                }
                // 修改进度
                reviewProgressService.updateProgress(map);
                // 修改评分状态为 未评分
                packageExpertService.updateScore(map);
                // 修改AduitQuota的round状态为退回
                aduitQuotaService.updateStatus(projectId, packageId,
                    supplierId, scoreModelId, flag);
                response.getWriter().print("tuihuisuccess");
            } else {
                // 确认 保存最终得分 分数不一致则不执行保存
                String message = aduitQuotaService.updateStatus(projectId,
                    packageId, supplierId, scoreModelId, flag);
                response.getWriter().print(message);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 
     * @Title: supplierQuote
     * @author ShaoYangYang
     * @date 2016年11月11日 下午2:46:47
     * @Description: TODO 查看供应商报价
     * @param @param packageId
     * @param @param projectId
     * @param @return
     * @return String
     */
    @RequestMapping("supplierQuote")
    public String supplierQuote(String projectId, String supplierId,
                                Model model, Quote quote, String timestamp) {
        try {
            Project project = projectService.selectById(projectId);
            DictionaryData dd = DictionaryDataUtil.findById(project.getPurchaseType());
            model.addAttribute("purchaserType", dd.getCode());
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("projectId", projectId);
            List<Packages> listPackage = supplierQuoteService
                .selectByPrimaryKey(map, null);
            List<Packages> listPackageEach = new ArrayList<Packages>();
            SaleTender saleTender = new SaleTender();
            saleTender.setProjectId(projectId);
            saleTender.setSupplierId(supplierId);
            List<SaleTender> sts = saleTenderService.find(saleTender);
            if (sts != null && sts.size() > 0) {
                String packageStr = sts.get(0).getPackages();
                for (Packages packages : listPackage) {
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
            for (Packages pk : listPackageEach) {
                if (StringUtils.isNotEmpty(timestamp)) {
                    // 如果传递时间 就按照时间查询
                    quote.setCreatedAt(new Timestamp(new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss").parse(timestamp).getTime()));
                } else {
                    if (listDate != null && listDate.size() > 0) {
                        // 否则就查询最后一次报价
                        Date date = listDate.get(listDate.size() - 1);
                        // SimpleDateFormat sdf = new
                        // SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        // String formatDate = sdf.format(date);
                        Timestamp timestamp2 = new Timestamp(date.getTime());
                        quote.setCreatedAt(timestamp2);
                    }
                }
                quote.setPackageId(pk.getId());
                List<Quote> quoteList = supplierQuoteService
                    .selectQuoteHistoryList(quote);
                listQuote.add(quoteList);
            }
            model.addAttribute("listPackage", listPackageEach);
            model.addAttribute("listQuote", listQuote);
            model.addAttribute("listDate", listDate);
            model.addAttribute("length", listDate.size());
            model.addAttribute("projectId", projectId);
            model.addAttribute("supplierId", supplierId);
            if (timestamp != null) {
                model.addAttribute("timestamp", new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss").parse(timestamp));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "bss/prms/view_quote";
    }

    /**
     * 
     * 〈简述〉评分汇总 〈详细描述〉
     * 
     * @author WangHuijie
     * @param packageId
     * @param projectId
     * @return
     */
    @RequestMapping("/scoreTotal")
    @ResponseBody
    public void scoreTotal(String packageId, String projectId) {
        // 供应商信息
        List<SaleTender> allSupplierList = saleTenderService.list(new SaleTender(projectId), 0);
        List<SaleTender> supplierList = new ArrayList<SaleTender>();
        for (int i = 0; i < allSupplierList.size(); i++) {
            SaleTender sale = allSupplierList.get(i);
            if (sale.getPackages().contains(packageId)) {
                supplierList.add(sale);
            }
        }
        expertScoreService.gather(packageId, projectId, supplierList);
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
        //查询包信息
        List<Packages> list = packageService.findPackageById(map);
        if(list!=null && list.size()>0){
            Packages packages = list.get(0);
            //放入包信息
            extension.setPackageId(packages.getId());
            extension.setPackageName(packages.getName());
        }
        //查询项目信息
        Project project = projectService.selectById(projectId);
        if(project!=null){
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
        //查询供应商信息
        List<SaleTender> supplierList = saleTenderService.list(new SaleTender(projectId), 0);
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
        return "bss/prms/first_audit/first_audit_expert_view";
    }
    
    /**
     *〈简述〉查看所有专家对供应商的初审明细
     *〈详细描述〉
     * @author Ye MaoLin
     * @param supplierId 供应商id
     * @param model
     * @param packageId 包id
     * @param projectId 项目id
     * @return
     */
    @RequestMapping("/viewBySupplier")
    public String viewBySupplier(String supplierId, Model model, String packageId, String projectId, String flowDefineId){
        Supplier supplier = supplierService.selectById(supplierId);
        //创建封装的实体
        Extension extension = new Extension();
        HashMap<String ,Object> map = new HashMap<String ,Object>();
        map.put("projectId", projectId);
        map.put("id", packageId);
        //查询包信息
        List<Packages> list = packageService.findPackageById(map);
        if(list!=null && list.size()>0){
            Packages packages = list.get(0);
            //放入包信息
            extension.setPackageId(packages.getId());
            extension.setPackageName(packages.getName());
        }
        //查询项目信息
        Project project = projectService.selectById(projectId);
        if(project!=null){
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
        return "bss/prms/first_audit/first_audit_supplier_view";
    }

    /**
    *〈简述〉跳转到初审页面
    *〈详细描述〉
    * @author Ye MaoLin
    * @param projectId 项目id
    * @param model
    * @param flowDefineId 流程环节id
    * @return
    */
   @RequestMapping("/toFirstAudit")
   public String toFirstAudit(String projectId, Model model, String flowDefineId){
//       Map<String, Object> map = new HashMap<String, Object>();
//       map.put("projectId", projectId);
       // 进度集合
      // List<ReviewProgress> reviewProgressList = reviewProgressService.selectByMap(map);
       List<ReviewProgress> reviewProgressList = new ArrayList<ReviewProgress>();
       List<Packages> packages = packageService.listResultExpert(projectId);
       for (Packages pg : packages) {
           Map<String, Object> map2 = new HashMap<String, Object>();
           map2.put("projectId", projectId);
           map2.put("packageId", pg.getId());
           //查询该包有没有评审进度数据
           List<ReviewProgress> rplist = reviewProgressService.selectByMap(map2);
           Map<String, Object> pemap = new HashMap<>();
           pemap.put("projectId", projectId);
           pemap.put("packageId", pg.getId());
           // 查询包关联专家实体
           List<PackageExpert> selectList = packageExpertService.selectList(pemap);
           if (rplist == null || rplist.size() <= 0) {
               //如果该包进度不存在
               ReviewProgress reviewProgress = new ReviewProgress();
               reviewProgress.setAuditStatus("0");
               reviewProgress.setFirstAuditProgress(0.00);
               reviewProgress.setPackageId(pg.getId());
               reviewProgress.setPackageName(pg.getName());
               reviewProgress.setProjectId(projectId);
               reviewProgress.setScoreProgress(0.00);
               reviewProgress.setTotalProgress(0.00);
               reviewProgress.setIsGather(0);
               reviewProgressList.add(reviewProgress);
           } else {
               //是否汇总  0:未汇总 1：已汇总
               Integer isGather = 1;
               if (selectList != null && selectList.size() > 0) {
                   for (PackageExpert packageExpert : selectList) {
                       if (packageExpert.getIsGather() != SONE) {
                           isGather = 0;
                           break;
                       }
                   }
               }
               ReviewProgress reviewProgress = rplist.get(0);
               reviewProgress.setIsGather(isGather);
               reviewProgressList.add(reviewProgress);
           }
       }
       // 包信息
       model.addAttribute("packageList", packages);
       // 进度
       model.addAttribute("reviewProgressList", reviewProgressList);
       model.addAttribute("projectId", projectId);
       model.addAttribute("flowDefineId", flowDefineId);
       return "bss/prms/first_audit/list";
   }

    /**
     *〈简述〉查看包的初审情况
     *〈详细描述〉
     * @author Ye MaoLin
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
        List<PackageExpert> expertIdList = packageExpertService.selectList(packageExpertmap);
        // 供应商信息
        List<SaleTender> supplierList = new ArrayList<SaleTender>();
        List<SaleTender> sl = saleTenderService.list(new SaleTender(projectId), 0);
        for (SaleTender st : sl) {
            if (st.getPackages().indexOf(packageId) != -1) {
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
                Map<String, Object> map2 = new HashMap<>();
                map2.put("supplierId", saleTender.getSuppliers().getId());
                map2.put("packageId", packageExpert.getPackageId());
                map2.put("expertId", packageExpert.getExpertId());
                List<ReviewFirstAudit> selectList2 = reviewFirstAuditService.selectList(map2);
                if (selectList2 != null && selectList2.size() > 0) {
                    int count2 = 0;
                    for (ReviewFirstAudit reviewFirstAudit : selectList2) {
                        if (reviewFirstAudit.getIsPass() == SONE) {
                            count2++;
                            break;
                        }
                    }
                    // 如果变量大于0 说明有不合格的数据
                    if (count2 > 0) {
                        supplierExt.setSupplierId(saleTender.getSuppliers()
                            .getId());
                        supplierExt.setExpertId(packageExpert.getExpertId());
                        supplierExt.setPackageId(packageExpert.getPackageId());
                        supplierExt.setSuppIsPass("0");
                    } else {
                        supplierExt.setSupplierId(saleTender.getSuppliers()
                            .getId());
                        supplierExt.setExpertId(packageExpert.getExpertId());
                        supplierExt.setPackageId(packageExpert.getPackageId());
                        supplierExt.setSuppIsPass("1");
                    }
                } else {
                    supplierExt
                    .setSupplierId(saleTender.getSuppliers().getId());
                    supplierExt.setPackageId(packageExpert.getPackageId());
                    supplierExt.setExpertId(packageExpert.getExpertId());
                    supplierExt.setSuppIsPass("1");
                }

                supplierExtList.add(supplierExt);
            }
            packExpertExtList.add(packExpertExt);
        }

        HashMap<String, Object> packmap = new HashMap<String, Object>();
        packmap.put("id", packageId);
        List<Packages> packages = packageService.findPackageById(packmap);
        if (packages != null && packages.size() > 0 ) {
            model.addAttribute("pack", packages.get(0));
        }
        model.addAttribute("packageId", packageId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("flowDefineId", flowDefineId);
        model.addAttribute("supplierList", supplierList);
        model.addAttribute("supplierExtList", supplierExtList);
        model.addAttribute("packExpertExtList", packExpertExtList);
        return "bss/prms/first_audit/view";
    }

    /**
     *〈简述〉跳转到详细打分页面
     *〈详细描述〉
     * @author Ye MaoLin
     * @param projectId 项目id
     * @param model
     * @param flowDefineId 流程环节id
     * @return
     */
    @RequestMapping("/toScoreAudit")
    public String toScoreAudit(String projectId, Model model, String flowDefineId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        // 进度集合
        List<ReviewProgress> reviewProgressList = reviewProgressService.selectByMap(map);
        List<Packages> packages = packageService.listResultExpert(projectId);
        if (reviewProgressList.size() < packages.size()) {
            for (Packages pg : packages) {
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("projectId", projectId);
                map2.put("packageId", pg.getId());
                //查询该包有没有评审进度数据
                List<ReviewProgress> rplist = reviewProgressService.selectByMap(map2);
                if (rplist == null || rplist.size() <= 0) {
                    ReviewProgress reviewProgress = new ReviewProgress();
                    reviewProgress.setAuditStatus("0");
                    reviewProgress.setFirstAuditProgress(0.00);
                    reviewProgress.setPackageId(pg.getId());
                    reviewProgress.setPackageName(pg.getName());
                    reviewProgress.setProjectId(projectId);
                    reviewProgress.setScoreProgress(0.00);
                    reviewProgress.setTotalProgress(0.00);
                    reviewProgressList.add(reviewProgress);
                }
            }
        }
        // 包信息
        model.addAttribute("packageList", packages);
        model.addAttribute("projectId", projectId);
        // 进度
        model.addAttribute("reviewProgressList", reviewProgressList);
        model.addAttribute("flowDefineId", flowDefineId);
        return "bss/prms/score_audit/list";
    }
    /**
     *〈简述〉
     * 专家详细评审
     *〈详细描述〉
     * @author WangHuijie
     * @param packageId
     * @param expertId
     * @return
     */
    @RequestMapping("detailedReview")
    public String detailedReview(String projectId, String packageId, Model model) {
        // 项目分包信息
        HashMap<String, Object> pack = new HashMap<String, Object>();
        pack.put("projectId", projectId);
        List<Packages> packages = packageService.findPackageById(pack);
        //List<Packages> packages = packageService.listResultSupplier(projectId);
        Map<String, Object> list = new HashMap<String, Object>();
        // 关联表集合
        List<PackageExpert> expertIdList = new ArrayList<>();
        // 进度集合
        List<ReviewProgress> reviewProgressList = new ArrayList<>();
        List<ExpertSuppScore> expertScoreAll = new ArrayList<>();
        List<AuditModelExt> auditModelListAll = new ArrayList<>();
        Map<String, Object> mapSearch = new HashMap<String, Object>();
        for (Packages ps : packages) {
            list.put("pack" + ps.getId(), ps);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("packageId", ps.getId());
            List<ProjectDetail> detailList = detailService.selectById(map);
            ps.setProjectDetails(detailList);
            // 设置查询条件
            mapSearch.put("projectId", projectId);
            mapSearch.put("packageId", ps.getId());
            // 查询出该项目下的包关联的信息集合
            List<PackageExpert> selectList = service.selectList(mapSearch);
            // 查询评审项
            List<AuditModelExt> auditModelExtList = aduitQuotaService
                .findAllByMap(mapSearch);
            auditModelListAll.addAll(auditModelExtList);
            // 查询评分信息
            //List<ExpertScore> expertList = expertScoreService.selectByMap(mapSearch);
            // 查询评分信息(由按项目查改为按供应商和包查)
            List<ExpertSuppScore> expertList = expertScoreService.getScoreByMap(mapSearch);
            expertScoreAll.addAll(expertList);
            model.addAttribute("expertIdList", expertIdList);
            // 查询进度
            List<ReviewProgress> selectByMap = reviewProgressService
                .selectByMap(map); 
            expertIdList.addAll(selectList);
            reviewProgressList.addAll(selectByMap);
        }
        // 进度
        model.addAttribute("reviewProgressList", reviewProgressList);
        // 供应商信息
        List<SaleTender> supplierList = saleTenderService.list(new SaleTender(
            projectId), 0);
        model.addAttribute("supplierList", supplierList);
        // 查询条件
        ProjectExtract projectExtract = new ProjectExtract();
        projectExtract.setProjectId(projectId);
        projectExtract.setReason("1");
        // 项目抽取的专家信息
        List<ProjectExtract> expertList = projectExtractService
            .list(projectExtract);
        model.addAttribute("expertList", expertList);
        // 包信息
        model.addAttribute("packageList", packages);
        Project project = projectService.selectById(projectId);
        // 项目实体
        model.addAttribute("project", project);
        // 关联信息集合
        // 封装实体
        List<PackExpertExt> packExpertExtList = new ArrayList<>();
        // 供应商封装实体
        List<SupplierExt> supplierExtList = new ArrayList<>();
        PackExpertExt packExpertExt;
        for (PackageExpert packageExpert : expertIdList) {
            packExpertExt = new PackExpertExt();
            Expert expert = expertService.selectByPrimaryKey(packageExpert
                .getExpertId());
            packExpertExt.setExpert(expert);
            packExpertExt.setPackageId(packageExpert.getPackageId());
            packExpertExt.setProjectId(packageExpert.getProjectId());
            Map<String, Object> map = new HashMap<>();
            // 根据专家id和包id查询改包的这个专家是否评审完成
            map.put("expertId", packageExpert.getExpertId());
            map.put("packageId", packageExpert.getPackageId());
            // 根据供应商id 和包id查询审核表 确定该供应商是否通过评审
            for (SaleTender saleTender : supplierList) {
                SupplierExt supplierExt = new SupplierExt();
                Map<String, Object> map2 = new HashMap<>();
                map2.put("supplierId", saleTender.getSuppliers().getId());
                map2.put("packageId", packageExpert.getPackageId());
                map2.put("expertId", packageExpert.getExpertId());
                List<ReviewFirstAudit> selectList2 = reviewFirstAuditService
                    .selectList(map2);
                if (selectList2 != null && selectList2.size() > 0) {
                    int count2 = 0;
                    for (ReviewFirstAudit reviewFirstAudit : selectList2) {
                        if (reviewFirstAudit.getIsPass() == SONE) {
                            count2++;
                            break;
                        }
                    }
                    // 如果变量大于0 说明有不合格的数据
                    if (count2 > 0) {
                        supplierExt.setSupplierId(saleTender.getSuppliers()
                            .getId());
                        supplierExt.setExpertId(packageExpert.getExpertId());
                        supplierExt.setPackageId(packageExpert.getPackageId());
                        supplierExt.setSuppIsPass("不合格");
                    } else {
                        supplierExt.setSupplierId(saleTender.getSuppliers()
                            .getId());
                        supplierExt.setExpertId(packageExpert.getExpertId());
                        supplierExt.setPackageId(packageExpert.getPackageId());
                        supplierExt.setSuppIsPass("合格");
                    }
                } else {
                    supplierExt
                    .setSupplierId(saleTender.getSuppliers().getId());
                    supplierExt.setPackageId(packageExpert.getPackageId());
                    supplierExt.setExpertId(packageExpert.getExpertId());
                    supplierExt.setSuppIsPass("未评审");
                }

                supplierExtList.add(supplierExt);
            }
            packExpertExtList.add(packExpertExt);
        }
        // 评审项信息
        List<Map<String, Object>> typeNames = packageExpertService.findMarkTypeByProId(projectId);
        removeAuditModelExt(auditModelListAll);
        model.addAttribute("typeNames", typeNames);
        model.addAttribute("projectId", projectId);
        model.addAttribute("auditModelListAll", auditModelListAll);
        model.addAttribute("packExpertExtList", packExpertExtList);
        model.addAttribute("supplierExtList", supplierExtList);
        model.addAttribute("expertScoreList", expertScoreAll);
        // 成功标示
        model.addAttribute("packageId", packageId);
        return "bss/prms/expert_detailed_review";
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
        // 查询专家名称
        Expert expert = expertService.selectByPrimaryKey(expertId);
        model.addAttribute("expertName",expert.getRelName());
        model.addAttribute("expertId",expertId);
        // 查询该包内的所有供应商(一行一个)
        List<SaleTender> supplierList = saleTenderService.list(new SaleTender(
            projectId), 0);
        int count = 0;
        for (SaleTender sale : supplierList) {
            if (sale.getPackages().contains(packageId)) { 
                count++;
            }
        }
        model.addAttribute("listLength", count);
        model.addAttribute("supplierList", supplierList);
        // 查询供应商的审查项
        Map<String, Object> mapSearch = new HashMap<String, Object>();
        mapSearch.put("projectId", projectId);
        mapSearch.put("packageId", packageId);
        List<AuditModelExt> auditModelExtList = aduitQuotaService
            .findAllByMap(mapSearch);
        model.addAttribute("auditModelExtList", auditModelExtList);
        // 去重
        List<AuditModelExt> auditModelList = aduitQuotaService
            .findAllByMap(mapSearch);;
            for (int i = 0; i < auditModelList.size(); i++) {
                for (int j = auditModelList.size() - 1 ; j > i; j--) {
                    if (auditModelList.get(i).getMarkTermName().equals(auditModelList.get(j).getMarkTermName())) {
                        auditModelList.remove(j);
                    }
                }
            }
            // 查询专家给供应商每项所评分的成绩
            List<Map<String, Object>> scores = packageExpertService.findScoreByMap(mapSearch);
            model.addAttribute("scores", scores);
            model.addAttribute("auditModelList", auditModelList);
            model.addAttribute("packageId", packageId);
            model.addAttribute("projectId", projectId);
            return "bss/prms/view_expert_score";
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
    public String showViewBySupplierId(String packageId, String supplierId, Model model, String projectId, String expertIds) {
        // 查询供应商名称
        Supplier supplier = supplierService.selectById(supplierId);
        model.addAttribute("supplierName",supplier.getSupplierName());
        model.addAttribute("supplierId",supplierId);
        // 查询该包内的所有专家(一行一个)
        List<Expert> expertList = new ArrayList<Expert>();
        String[] ids = expertIds.split(",");
        for (String id : ids) {
            expertList.add(expertService.selectByPrimaryKey(id.replace("undefined", "")));
        }
        model.addAttribute("expertList", expertList);
        model.addAttribute("length", expertList.size());
        // 查询供应商的审查项
        Map<String, Object> mapSearch = new HashMap<String, Object>();
        mapSearch.put("projectId", projectId);
        mapSearch.put("packageId", packageId);
        List<AuditModelExt> auditModelExtList = aduitQuotaService
            .findAllByMap(mapSearch);
        model.addAttribute("auditModelExtList", auditModelExtList);
        // 去重
        List<AuditModelExt> auditModelList = aduitQuotaService
            .findAllByMap(mapSearch);;
            for (int i = 0; i < auditModelList.size(); i++) {
                for (int j = auditModelList.size() - 1 ; j > i; j--) {
                    if (auditModelList.get(i).getMarkTermName().equals(auditModelList.get(j).getMarkTermName())) {
                        auditModelList.remove(j);
                    }
                }
            }
            // 查询专家给供应商每项所评分的成绩
            List<Map<String, Object>> scores = packageExpertService.findScoreByMap(mapSearch);
            model.addAttribute("scores", scores);
            model.addAttribute("auditModelList", auditModelList);
            model.addAttribute("packageId", packageId);
            model.addAttribute("projectId", projectId);
            return "bss/prms/view_supplier_score";
    }

    /**
     *〈简述〉
     * 退回分数
     *〈详细描述〉
     * @author WangHuijie
     * @param packageExpert
     * @param supplierId
     * @param model
     * @return 跳转到
     */
    @RequestMapping("/backScore")
    public String backScore(String projectId, String packageId, String expertId, String expertIds, String supplierId, Model model){
        // 将参数存储到model中以便redirect后取值
        model.addAttribute("projectId", projectId);
        model.addAttribute("packageId", packageId);
        model.addAttribute("expertIds", expertIds);
        model.addAttribute("supplierId", supplierId);
        // 封装参数到map中
        Map<String, Object> mapSearch = new HashMap<String, Object>();
        mapSearch.put("projectId", projectId);
        mapSearch.put("supplierId", supplierId);
        mapSearch.put("packageId", packageId);
        mapSearch.put("expertId", expertId);
        // 退回分数
        packageExpertService.backScore(mapSearch);
        // 跳转到showViewBySupplierId.html重新查询展示
        return "redirect:showViewBySupplierId.html";
    }

    /**
     *〈简述〉
     * 判断所选择的包是否满足汇总条件
     *〈详细描述〉
     * @author WangHuijie
     * @param packageIds
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "isGather", produces = "text/html;charset=utf-8")
    public String isGather(String packageIds, String projectId){
        return service.isGather(packageIds, projectId);
    }
    
    @RequestMapping("/auditManage")
    public String auditManage(Model model, String projectId, String flowDefineId){
      
      model.addAttribute("projectId", projectId);
      model.addAttribute("flowDefineId", flowDefineId);
      return "bss/prms/audit_manage/manage";
    }
    
    /**
     *〈简述〉符合性审查汇总
     *〈详细描述〉
     * @author Ye MaoLin
     * @param packageId 包id
     * @param projectId 项目id
     * @return
     * @throws IOException 
     */
    @RequestMapping("/isFirstGather")
    public void isFirstGather(HttpServletResponse response, String projectId, String packageId) throws IOException{
      try {
        String msg = service.isFirstGather(projectId,packageId);
        if ("SUCCESS".equals(msg)) {
          response.setContentType("text/html;charset=utf-8");
          response.getWriter()
                  .print("{\"success\": " + true + ", \"msg\": \"" + msg+ "\"}");
        } else {
          response.setContentType("text/html;charset=utf-8");
          response.getWriter()
                  .print("{\"success\": " + false + ", \"msg\": \"" + msg+ "\"}");
        }
        response.getWriter().flush();
      } catch (Exception e) {
          e.printStackTrace();
      } finally{
          response.getWriter().close();
      }
    }
}
