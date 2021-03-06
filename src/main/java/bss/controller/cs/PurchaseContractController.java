package bss.controller.cs;


import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.DictionaryData;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.sms.AfterSaleSer;
import ses.model.sms.Supplier;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurChaseDepOrgService;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.sms.AfterSaleSerService;
import ses.service.sms.SupplierService;
import ses.util.ValidateUtils;
import bss.model.cs.ContractAdvice;
import bss.model.cs.ContractRequired;
import bss.model.cs.PurchaseContract;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.SupplierCheckPass;
import bss.model.ppms.Task;
import bss.model.ppms.theSubject;
import bss.service.cs.ContractAdviceService;
import bss.service.cs.ContractRequiredService;
import bss.service.cs.PurchaseContractService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.ProjectTaskService;
import bss.service.ppms.SupplierCheckPassService;
import bss.service.ppms.TaskService;
import bss.service.ppms.theSubjectService;
import bss.service.sstps.AppraisalContractService;
import bss.util.ExportExcel;

import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.Constant;
import common.constant.StaticVariables;
import common.model.UploadFile;
import common.service.DownloadService;
import common.service.UploadService;
import dss.model.rids.PurchaseContractAnalyzeVo;


/*
 * @Title:PurchaseContractController
 * @Description:采购合同控制类
 * @author QuJie
 * @date 2016-9-23下午1:34:27
 */
@Controller
@Scope("prototype")
@RequestMapping("/purchaseContract")
public class PurchaseContractController extends BaseSupplierController {
    @Autowired
    private PurchaseContractService purchaseContractService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectDetailService projectDetailService;

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private ContractRequiredService contractRequiredService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private PackageService packageService;

    @Autowired
    private SupplierCheckPassService supplierCheckPassService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private DictionaryDataServiceI dictionaryDataServiceI;

    @Autowired
    private AppraisalContractService appraisalContractService;

    @Autowired
    private OrgnizationServiceI orgnizationServiceI;

    @Autowired
    private PurchaseOrgnizationServiceI purchaseOrgnizationServiceI;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private DownloadService downloadService;

    @Autowired
    private ProjectDetailService detailService;

    @Autowired
    private PurChaseDepOrgService chaseDepOrgService;

    @Autowired
    private AfterSaleSerService saleSerService;

    @Autowired
    private theSubjectService theSubjectService;
    
    @Autowired
    private ContractAdviceService contractAdviceService;
    
    @Autowired
    private UserServiceI userService;

    /**
     * 
     *〈成交项目列表〉
     *〈详细描述〉
     * @author FengTian
     * @param user
     * @param projects
     * @param isCreate
     * @param model
     * @param page
     * @return
     */
    @RequestMapping("/selectAllPuCon")
    public String selectAllPurchaseContract(@CurrentUser User user, Project projects, String isCreate, Model model, Integer page) {
    	if (user != null && StringUtils.isNotBlank(user.getTypeName()) && user.getOrg() != null) {
    		HashMap<String, Object> hashMap = new HashMap<String, Object>();
    		if(projects != null){
                if(StringUtils.isNotBlank(projects.getName())){
                    hashMap.put("projectName", projects.getName());
                }
                if(StringUtils.isNotBlank(projects.getProjectNumber())){
                    hashMap.put("projectCode", projects.getProjectNumber());
                }
            }
    		if(StringUtils.isNotBlank(isCreate)){
                hashMap.put("isCreateContract", isCreate);
            }
    		if (page == null) {
                page = 1;
            }
    		hashMap.put("page", page);
    		if (StringUtils.equals("1", user.getTypeName())) {
    			hashMap.put("purchaseDepId", user.getOrg().getId());
    			List<SupplierCheckPass> list = supplierCheckPassService.selectByAll(hashMap);
    			if (list != null && !list.isEmpty()) {
					for (SupplierCheckPass supplierCheckPass : list) {
						//标的信息
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("supplierId", supplierCheckPass.getSupplierId());
                        map.put("packageId", supplierCheckPass.getPackageId());
                        List<theSubject> theSubject = theSubjectService.selectBysupplierIdAndPackagesId(map);
                        BigDecimal sum = new BigDecimal(0);
                        if (theSubject != null && theSubject.size() > 0) {
                            for (theSubject ts : theSubject) {
                                if (ts.getDetailId() != null) {
                                    BigDecimal count = new BigDecimal(ts.getPurchaseCount());
                                    BigDecimal price = count.multiply(ts.getUnitPrice());
                                    sum = sum.add(price);
                                }
                            }
                        }
                        sum = sum.divide(new BigDecimal(10000));
                        supplierCheckPass.setWonPrice(sum);
					}
				}
    			PageInfo<SupplierCheckPass> info = new PageInfo<SupplierCheckPass>(list);
                model.addAttribute("list", info);
                model.addAttribute("projects", projects);
                model.addAttribute("authType", user.getTypeName());
                model.addAttribute("isCreate", isCreate);
			}
		}
    	return "bss/cs/purchaseContract/list"; 
    }
    /*public String selectAllPurchaseContract(@CurrentUser User user, Project projects, String isCreate, Model model, Integer page) {
        if(user != null && StringUtils.isNotBlank(user.getTypeName()) && user.getOrg() != null){
            if (page == null) {
                page = 1;
            }
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("isWonBid", (short)1);
            hashMap.put("page", page);
            if(projects != null){
                if(StringUtils.isNotBlank(projects.getName())){
                    hashMap.put("projectName", projects.getName());
                }
                if(StringUtils.isNotBlank(projects.getProjectNumber())){
                    hashMap.put("projectCode", projects.getProjectNumber());
                }
            }
            if(StringUtils.isNotBlank(isCreate)){
                hashMap.put("isCreateContract", isCreate);
            }
            //采购机构
            if("1".equals(user.getTypeName())){
                hashMap.put("purchaseDepId", user.getOrg().getId());
                List<SupplierCheckPass> listsupplier = supplierCheckPassService.listsupplier(hashMap);
                if(listsupplier != null && !listsupplier.isEmpty()){
                    for (SupplierCheckPass supplierCheckPass : listsupplier) {
                        if(StringUtils.isNotBlank(supplierCheckPass.getProjectId())){
                            //项目信息
                            Project project = projectService.selectById(supplierCheckPass.getProjectId());
                            supplierCheckPass.setProject(project);
                            //机构信息
                            Orgnization orgnization = orgnizationServiceI.getOrgByPrimaryKey(project.getPurchaseDepId());
                            supplierCheckPass.setPurchaseDep(orgnization.getShortName());
                        }
                        if(StringUtils.isNotBlank(supplierCheckPass.getPackageId()) && StringUtils.isNotBlank(supplierCheckPass.getSupplierId())){
                            //分包信息
                            Packages packages = packageService.selectByPrimaryKeyId(supplierCheckPass.getPackageId());
                            
                            //供应商信息
                            Supplier supplier = supplierService.selectById(supplierCheckPass.getSupplierId());
                            supplierCheckPass.setSupplier(supplier);
                            
                            //标的信息
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("supplierId", supplierCheckPass.getSupplierId());
                            map.put("packageId", supplierCheckPass.getPackageId());
                            List<theSubject> theSubject = theSubjectService.selectBysupplierIdAndPackagesId(map);
                            BigDecimal sum = new BigDecimal(0);
                            if (theSubject != null && theSubject.size() > 0) {
                                for (theSubject ts : theSubject) {
                                    if (ts.getDetailId() != null) {
                                        BigDecimal count = new BigDecimal(
                                            ts.getPurchaseCount());
                                        BigDecimal price = count.multiply(ts.getUnitPrice());
                                        sum = sum.add(price);
                                    }
                                }
                            }
                            sum = sum.divide(new BigDecimal(10000));
                            packages.setWonPrice(sum);
                            supplierCheckPass.setPackages(packages);
                        }
                        if(StringUtils.isNotBlank(supplierCheckPass.getContractId())){
                            //合同信息
                            PurchaseContract contract = purchaseContractService.selectById(supplierCheckPass.getContractId());
                            supplierCheckPass.setPc(contract);
                        }
                    }
                }
                PageInfo<SupplierCheckPass> list = new PageInfo<SupplierCheckPass>(listsupplier);
                model.addAttribute("list", list);
                model.addAttribute("projects", projects);
                model.addAttribute("authType", user.getTypeName());
            }
        }
        return "bss/cs/purchaseContract/list";
    }*/


    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午2:50:43
     * @Description: 打印正式合同
     * @param @param request
     * @param @param model
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/printFormalContract")
    public String printFormalContract(HttpServletRequest request, Model model)
        throws Exception {
        String ids = request.getParameter("ids");
        PurchaseContract purCon = purchaseContractService.selectById(ids);
        List<ContractRequired> requList = contractRequiredService.selectConRequeByContractId(ids);
        model.addAttribute("requList", requList);
        model.addAttribute("purCon", purCon);
        return "bss/cs/purchaseContract/printModel";
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午2:51:00
     * @Description: 合并生成合同
     * @param @param request
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping(value = "/createAllCommonContract", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String createAllCommonContract(HttpServletRequest request)
        throws Exception {
        String id = request.getParameter("ids");
        String suppliers = request.getParameter("suppliers");
        String[] ids = id.split(",");
        String[] supplierId = suppliers.split(",");
        String flag = "true";
        String news = "";
        List<Project> proList = new ArrayList<Project>();
        for (int i = 0; i < ids.length; i++ ) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", ids[i]);
            Packages pack = packageService.findPackageById(map).get(0);
            Project pro = projectService.selectById(pack.getProjectId());
            proList.add(pro);
        }
        // SupplierCheckPass suchp = new SupplierCheckPass();
        // suchp.setPackageId(pack.getId());
        // suchp.setIsWonBid((short)1);
        // List<SupplierCheckPass> chList = supplierCheckPassService.listCheckPass(suchp);
        // // if(chList.size()>1){
        // // flag="false";
        // // news = "";
        // // news+="有多个供应商，无法合并";
        // // break;
        // // }else{
        // supIdList.add(chList.get(0).getSupplier().getId());
        // // }
        // }

        if (flag.equals("true")) {
            out: for (int i = 0; i < proList.size() - 1; i++ ) {
                for (int j = i + 1; j < proList.size(); j++ ) {
                    if (!proList.get(i).getId().equals(proList.get(j).getId())) {
                        flag = "false";
                        news = "";
                        news += "不是同一个项目";
                        break out;
                    }
                }
            }
        }

        if (flag.equals("true")) {
            for (int j = 0; j < supplierId.length - 1; j++ ) {
                for (int k = j + 1; k < supplierId.length; k++ ) {
                    if (!supplierId[j].equals(supplierId[k])) {
                        flag = "false";
                        news = "";
                        news += "供应商不一致";
                    }
                }
            }
        }

        if (flag.equals("true")) {
            return "true=";
        } else {
            return "false=" + news;
        }
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午2:51:25
     * @Description: 查询选择的项目的成交供应商
     * @param @param request
     * @param @return
     * @return String
     */
    @RequestMapping(value = "/selectSuppliers", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String selectSuppliers(HttpServletRequest request) {
        String packageId = request.getParameter("packageId");
        String[] packIds = packageId.split(",");
        SupplierCheckPass suppliercheckpass = new SupplierCheckPass();
        suppliercheckpass.setPackageId(packIds[0]);
        suppliercheckpass.setIsWonBid((short)1);
        List<SupplierCheckPass> chePList = supplierCheckPassService.listCheckPass(suppliercheckpass);
        String options = "";
        for (SupplierCheckPass su : chePList) {
            String option = "<option value='" + su.getSupplier().getId() + "'>"
                            + su.getSupplier().getSupplierName() + "</option>";
            options += option;
        }
        return options;
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午2:51:58
     * @Description: 通过包id查询成交供应商
     * @param @param request
     * @param @return
     * @return String
     */
    @RequestMapping(value = "/selectSupplierByPId", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String selectSupplierByPId(HttpServletRequest request) {
        String packageId = request.getParameter("packageId");
        String[] packIds = packageId.split(",");
        SupplierCheckPass suppliercheckpass = new SupplierCheckPass();
        suppliercheckpass.setPackageId(packIds[0]);
        suppliercheckpass.setIsWonBid((short)1);
        List<SupplierCheckPass> chePList = supplierCheckPassService.listCheckPass(suppliercheckpass);
        String supid = "";
        for (SupplierCheckPass su : chePList) {
            supid += su.getSupplier().getId();
        }
        return supid;
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午2:52:32
     * @Description: 打印草稿合同
     * @param @param purCon 合同实体类
     * @param @param proList 前台传的list
     * @param @param result
     * @param @param request
     * @param @param model
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/printDraftContract")
    public String printDraftContract(PurchaseContract purCon, ProList proList,
                                     BindingResult result, HttpServletRequest request, Model model)
        throws Exception {
        Map<String, Object> map = valid(model, purCon);
        model = (Model)map.get("model");
        Boolean flag = (boolean)map.get("flag");
        String url = "";
        if (flag == false) {
            String ids = request.getParameter("ids");
            List<ContractRequired> requList = proList.getProList();
            model.addAttribute("draftCon", purCon);
            model.addAttribute("requList", requList);
            model.addAttribute("ids", ids);
            return "bss/cs/purchaseContract/draftContract";
        } else {
            List<ContractRequired> requList = proList.getProList();
            model.addAttribute("requList", requList);
            model.addAttribute("purCon", purCon);
            url = "bss/cs/purchaseContract/printModel";
        }
        return url;
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午2:53:44
     * @Description: 根据合同编号查合同
     * @param @param request
     * @param @return
     * @param @throws Exception
     * @return PurchaseContract
     */
    @ResponseBody
    @RequestMapping("/selectByCode")
    public PurchaseContract selectByCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            PurchaseContract contract = purchaseContractService.selectByCode(code);
            if (contract != null && contract.getStatus() == 2) {
                contract.setPurchaseType(DictionaryDataUtil.findById(contract.getPurchaseType()).getName());
            } else if (contract != null
                       && (contract.getStatus() == 0 || contract.getStatus() == 1)) {
                contract = new PurchaseContract();
                contract.setCode("ErrCode1");
            } else {
                contract = new PurchaseContract();
                contract.setCode("ErrCode");
            }
            return contract;
        }
        return null;
    }

    /**
     * 〈根据合同编号查询售后〉 〈详细描述〉
     * 
     * @author FengTian
     * @param code
     * @return
     */
    @RequestMapping("/viewAfter")
    @ResponseBody
    public String viewAfter(String code) {
        if (StringUtils.isNotBlank(code)) {
            PurchaseContract purchaseCon = purchaseContractService.selectByCode(code);
            if (purchaseCon != null && StringUtils.isNotBlank(purchaseCon.getId())) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("contractId", purchaseCon.getId());
                List<AfterSaleSer> selectByAll = saleSerService.selectByAll(map);
                if (selectByAll != null && selectByAll.size() > 0) {
                    return "1";
                } else {
                    return "0";
                }
            }
        }
        return "2";
    }
    
    @RequestMapping("/createcContract")
    public String createcContract(Model model, String packId, String projectId, String supplierId, String supcheckId, String wonPrice){
    	//UUID
        String contractuuid = WfUtil.createUUID();
        model.addAttribute("attachuuid", contractuuid);
        
        //草案批复意见
        String attachtypeId = DictionaryDataUtil.getId("DRAFT_REVIEWED");
        model.addAttribute("attachtypeId", attachtypeId);
        model.addAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
        
        //授权书 
        String bookattachtypeId = DictionaryDataUtil.getId("CONTRACT_WARRANT");
        model.addAttribute("bookattachtypeId", bookattachtypeId);
        model.addAttribute("bookattachsysKey", Constant.TENDER_SYS_KEY);
        
        //采购方式
        model.addAttribute("kinds", DictionaryDataUtil.find(5));
        
        //合同总金额
        BigDecimal totalPrice = new BigDecimal(wonPrice);
        model.addAttribute("totalPrice", totalPrice);
        
        //标的信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("supplierId", supplierId);
        map.put("packageId", packId);
        List<theSubject> subjectList = theSubjectService.selectByTheSubject(map);
        if (subjectList != null && !subjectList.isEmpty()) {
			model.addAttribute("subjectList", subjectList);
		}
        
        if (StringUtils.isNotBlank(projectId)) {
        	//项目信息
			Project project = projectService.selectById(projectId);
			model.addAttribute("project", project);
			//任务文号
			List<Task> selectByProjectTask = taskService.selectByProjectTask(projectId);
			if (selectByProjectTask != null && !selectByProjectTask.isEmpty()) {
				model.addAttribute("documentNumber", selectByProjectTask.get(0).getDocumentNumber());
			}
		}
        
        //获取包的预算价格
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("packageId", packId);
        map1.put("projectId", projectId);
        BigDecimal budgets = projectDetailService.selectByBudget(map1);
        BigDecimal budget = budgets.setScale(4, BigDecimal.ROUND_HALF_UP);
        model.addAttribute("budget", budget);
        return "bss/cs/purchaseContract/newContract";
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午2:54:10
     * @Description: 创建合同基本信息页面
     * @param @param request
     * @param @param model
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/createCommonContract")
    public String createCommonContract(@CurrentUser User user, HttpServletRequest request, Model model)
        throws Exception {
        String supcheckid = request.getParameter("supcheckid");
        String transactionAmount = request.getParameter("transactionAmount");
        String[] transactionAmounts = transactionAmount.split(",");
        BigDecimal amounts = new BigDecimal(0);
        if (!transactionAmount.isEmpty() && transactionAmounts.length > 0) {
            for (String tranAmount : transactionAmounts) {
                amounts = amounts.add(new BigDecimal(tranAmount));
            }
        }
        String contractuuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        model.addAttribute("attachuuid", contractuuid);
        DictionaryData dd = new DictionaryData();
        dd.setCode("DRAFT_REVIEWED");
        List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
        request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
        if (datas.size() > 0) {
            model.addAttribute("attachtypeId", datas.get(0).getId());
        }
        /* 授权书 */
        DictionaryData ddbook = new DictionaryData();
        ddbook.setCode("CONTRACT_WARRANT");
        List<DictionaryData> bookdata = dictionaryDataServiceI.find(ddbook);
        request.getSession().setAttribute("bookattachsysKey", Constant.TENDER_SYS_KEY);
        if (bookdata.size() > 0) {
            model.addAttribute("bookattachtypeId", bookdata.get(0).getId());
        }

        model.addAttribute("kinds", DictionaryDataUtil.find(5));
        String id = request.getParameter("id");
        String[] ids = id.split(",");
        String supid = request.getParameter("supid");
        String[] supids = supid.split(",");
        List<ProjectDetail> allList = new ArrayList<ProjectDetail>();
        for (int i = 0; i < ids.length; i++ ) {
            HashMap<String, Object> requMap = new HashMap<String, Object>();
            requMap.put("packageId", ids[i]);
            // List<ProjectDetail> requList = projectDetailService.selectById(requMap);
            List<ProjectDetail> requList = projectDetailService.selectTheSubjectBySupplierId(
                requMap, supids[0]);
            allList.addAll(requList);
        }
        model.addAttribute("requList", allList);
        Supplier supplier = supplierService.selectById(supids[0]);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", ids[0]);
        String planNos = "";

        Packages pack = packageService.findPackageById(map).get(0);
        Project project = projectService.selectById(pack.getProjectId());
        // 获取计划号
        String[] productIds = {pack.getProjectId()};
        HashMap<String, Object> taskMap = new HashMap<String, Object>();
        taskMap.put("idArray", productIds);
        List<ProjectTask> taskList = projectTaskService.queryByProjectNos(taskMap);
        for (ProjectTask pur : taskList) {
            Task task = taskService.selectById(pur.getTaskId());
            planNos = task.getDocumentNumber();
        }
        // 获取包的预算价格
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("packageId", pack.getId());
        map1.put("projectId", pack.getProjectId());
        List<ProjectDetail> detailList = detailService.selectByCondition(map1, null);
        BigDecimal projectBudget = BigDecimal.ZERO;
        String department = "";
        for (ProjectDetail projectDetail : detailList) {
            projectBudget = projectBudget.add(new BigDecimal(projectDetail.getBudget()));
            department = projectDetail.getDepartment();
        }
        BigDecimal projectBud = projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP);

        // 所有甲方机构
        // List<Orgnization> orgs = orgnizationServiceI.initOrgByType("1");
        project.setDealSupplier(supplier);

        /*
         * Orgnization org = orgnizationServiceI.getOrgByPrimaryKey(project.getSectorOfDemand());
         * project.setOrgnization(org);
         */

        /* PurchaseDep purchaseDep = chaseDepOrgService.findByOrgId(project.getSectorOfDemand()); */
        model.addAttribute("project", project);
        model.addAttribute("department", department);
        model.addAttribute("transactionAmount", amounts);
        model.addAttribute("id", contractuuid);
        model.addAttribute("supcheckid", supcheckid);
        model.addAttribute("projectBud", projectBud);
        model.addAttribute("planNos", planNos);
        model.addAttribute("user", user);
        // model.addAttribute("orgs",orgs);
        // model.addAttribute("purchaseDep",purchaseDep);
        return "bss/cs/purchaseContract/newContract";
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author liwanlin
     * @date 2016-11-11 下午2:54:10
     * @Description: 手动创建合同基本信息页面
     * @param @param request
     * @param @param model
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/manualCreateContract")
    public String manualCreateContract(HttpServletRequest request, Model model, @CurrentUser
    User user)
        throws Exception {

        DictionaryData dd = new DictionaryData();
        dd.setCode("DRAFT_REVIEWED");
        List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
        request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
        if (datas.size() > 0) {
            model.addAttribute("attachtypeId", datas.get(0).getId());
        }
        /* 授权书 */
        DictionaryData ddbook = new DictionaryData();
        ddbook.setCode("CONTRACT_WARRANT");
        List<DictionaryData> bookdata = dictionaryDataServiceI.find(ddbook);
        request.getSession().setAttribute("bookattachsysKey", Constant.TENDER_SYS_KEY);
        if (bookdata.size() > 0) {
            model.addAttribute("bookattachtypeId", bookdata.get(0).getId());
        }
        PurchaseDep purchaseDep = chaseDepOrgService.findByOrgId(user.getOrg().getId());
        String contractuuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        model.addAttribute("id", contractuuid);
        model.addAttribute("attachuuid", contractuuid);
        model.addAttribute("kinds", DictionaryDataUtil.find(5));
        model.addAttribute("user", user);
        model.addAttribute("purchaseDep", purchaseDep);
        return "bss/cs/purchaseContract/manualNewContract";
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午2:55:57
     * @Description: 创建合同明细信息
     * @param @param request
     * @param @param model
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/createDetailContract")
    public String createDetailContract(HttpServletRequest request, Model model)
        throws Exception {
        String id = request.getParameter("id");
        String[] ids = id.split(",");
        String supid = request.getParameter("supid");
        List<ProjectDetail> allList = new ArrayList<ProjectDetail>();
        for (int i = 0; i < ids.length; i++ ) {
            HashMap<String, Object> requMap = new HashMap<String, Object>();
            requMap.put("packageId", ids[i]);
            List<ProjectDetail> requList = projectDetailService.selectById(requMap);
            allList.addAll(requList);
        }
        model.addAttribute("requList", allList);
        model.addAttribute("id", id);
        model.addAttribute("supid", supid);
        return "bss/cs/purchaseContract/detailContract";
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午2:56:21
     * @Description: 创建合同文本信息
     * @param @param request
     * @param @param model
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/createTextContract")
    public String createTextContract(HttpServletRequest request, Model model)
        throws Exception {
        String id = request.getParameter("id");
        String supid = request.getParameter("supid");
        Supplier supplier = supplierService.get(supid);
        String[] ids = id.split(",");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", ids[0]);
        Packages pack = packageService.findPackageById(map).get(0);
        Project project = projectService.selectById(pack.getProjectId());
        project.setDealSupplier(supplier);
        List<ProjectDetail> allList = new ArrayList<ProjectDetail>();
        for (int i = 0; i < ids.length; i++ ) {
            HashMap<String, Object> requMap = new HashMap<String, Object>();
            requMap.put("packageId", ids[i]);
            List<ProjectDetail> requList = projectDetailService.selectById(requMap);
            allList.addAll(requList);
        }
        model.addAttribute("requList", allList);
        // HashMap<String, Object> requMainMap = new HashMap<String, Object>();
        // requMainMap.put("id", project.getId());
        // List<ProjectDetail> requMainList = projectDetailService.selectById(requMainMap);
        String planNos = "";
        HashMap<String, Object> taskMap = new HashMap<String, Object>();
        taskMap.put("idArray", ids);
        List<ProjectTask> taskList = projectTaskService.queryByProjectNos(taskMap);
        for (ProjectTask pur : taskList) {
            Task task = taskService.selectById(pur.getTaskId());
            planNos += task.getDocumentNumber() + ",";
        }
        model.addAttribute("project", project);
        model.addAttribute("planNos", planNos);
        model.addAttribute("ids", id);
        return "bss/cs/purchaseContract/textContract";
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午2:56:42
     * @Description: 生成合同草稿
     * @param @param purCon 合同实体类
     * @param @param proList 明细list
     * @param @param result
     * @param @param request
     * @param @param model
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/addPurchaseContract")
    public String addPurchaseContract(PurchaseContract purCon, ProList proList,
                                      BindingResult result, HttpServletRequest request,
                                      Model model, @CurrentUser
                                      User user)
        throws Exception {
        String ids = request.getParameter("ids");
        String dga = request.getParameter("dga");
        String dra = request.getParameter("dra");
        /* String manual = request.getParameter("manual"); */
        String supcheckid = request.getParameter("supcheckid");
        String[] supcheckids = supcheckid.split(",");
        Date draftGitAt = null;
        Date draftReAt = null;
        if (!dga.equals("")) {
            draftGitAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dga);
        }
        if (!dga.equals("")) {
            draftReAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dra);
        }
        purCon.setId(ids);
        Map<String, Object> map = valid(model, purCon);
        model = (Model)map.get("model");
        Boolean flag = (boolean)map.get("flag");
        String url = "";
        if (flag == false) {
            List<ContractRequired> requList = proList.getProList();
            List<ContractRequired> requListNew=new ArrayList<ContractRequired>();
            if(requList!=null){
              for (int i = 0; i < requList.size(); i++ ) {
                if(requList.get(i)!=null){
                   if(requList.get(i).getTransportFees()!=null&&requList.get(i).getTransportFees()==1){
                     requListNew.add(requList.get(i));
                   }else{
                     if (requList.get(i).getGoodsName() != null) {
                       requListNew.add(requList.get(i));
                   }
                   }
                }
             }
            }
            /* 授权书 */
            DictionaryData ddbook = new DictionaryData();
            ddbook.setCode("CONTRACT_WARRANT");
            List<DictionaryData> bookdata = dictionaryDataServiceI.find(ddbook);
            request.getSession().setAttribute("bookattachsysKey", Constant.TENDER_SYS_KEY);
            if (bookdata.size() > 0) {
                model.addAttribute("bookattachtypeId", bookdata.get(0).getId());
            }
            PurchaseDep purchaseDep = chaseDepOrgService.findByOrgId(purCon.getPurchaseDepName());
            model.addAttribute("attachuuid", ids);
            model.addAttribute("supcheckid", supcheckid);
            model.addAttribute("kinds", DictionaryDataUtil.find(5));
            model.addAttribute("purCon", purCon);
            model.addAttribute("requList", requListNew);
            model.addAttribute("planNos", purCon.getDocumentNumber());
            model.addAttribute("id", ids);
            model.addAttribute("user", user);
            model.addAttribute("purchaseDep", purchaseDep);
            /* model.addAttribute("manual", manual); */
            url = "bss/cs/purchaseContract/errContract";
        } else {
            if (draftGitAt != null) {
                purCon.setDraftGitAt(draftGitAt);
            }
            if (draftReAt != null) {
                purCon.setDraftReviewedAt(draftReAt);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
            purCon.setYear(new BigDecimal(sdf.format(new Date())));
            purCon.setCreatedAt(new Date());
            purCon.setUpdatedAt(new Date());
            purCon.setMoney(new BigDecimal(purCon.getMoney_string()));
            purCon.setBudget(new BigDecimal(purCon.getBudget_string()));
            purCon.setSupplierBankAccount(purCon.getSupplierBankAccount_string());
            purCon.setPurchaseBankAccount(purCon.getPurchaseBankAccount_string());
            PurchaseContract pur = purchaseContractService.selectById(purCon.getId());
            if (pur == null) {
                //
                purchaseContractService.insertSelective(purCon);
            } else {
                purchaseContractService.updateByPrimaryKeySelective(purCon);
            }
            String id = purCon.getId();
            List<ContractRequired> requList = proList.getProList();
            List<ContractRequired> conRequList = contractRequiredService.selectConRequeByContractId(purCon.getId());
            if (requList != null) {
                if (conRequList.size() > 0) {
                    contractRequiredService.deleteByContractId(purCon.getId());
                }
                List<ContractRequired> requListNew=new ArrayList<ContractRequired>();
                  for (int i = 0; i < requList.size(); i++ ) {
                    if(requList.get(i)!=null){
                       if(requList.get(i).getTransportFees()!=null&&requList.get(i).getTransportFees()==1){
                         requListNew.add(requList.get(i));
                       }else{
                         if (requList.get(i).getGoodsName() != null) {
                           requListNew.add(requList.get(i));
                       }
                       }
                    }
                 }
                for (ContractRequired conRequ : requListNew) {
                    conRequ.setContractId(id);
                    contractRequiredService.insertSelective(conRequ);
                }
            }
            if (purCon.getManualType() != 1) {
                if (pur != null) {
                    String supchid = pur.getSupplierCheckIds();
                    if (supchid != null && !"".equals(supchid)) {
                        String[] supchids = supchid.split(",");
                        for (String supid : supchids) {
                            SupplierCheckPass sup = new SupplierCheckPass();
                            sup.setId(supid);
                            sup.setIsCreateContract(1);
                            sup.setContractId(purCon.getId());
                            supplierCheckPassService.update(sup);
                        }
                    }

                } else {
                    for (String supchid : supcheckids) {
                        SupplierCheckPass sup = new SupplierCheckPass();
                        sup.setId(supchid);
                        sup.setIsCreateContract(1);
                        sup.setContractId(purCon.getId());
                        supplierCheckPassService.update(sup);
                    }
                }

            }

            /* if(manual!=null){ */
            url = "redirect:selectDraftContract.html";
            /*
             * }else{ url = "redirect:selectAllPuCon.html"; }
             */
        }
        return url;
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午2:56:42
     * @Description: 生成合同草稿
     * @param @param purCon 合同实体类
     * @param @param proList 明细list
     * @param @param result
     * @param @param request
     * @param @param model
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/addzancun")
    public String addzancun(PurchaseContract purCon, ProList proList, BindingResult result,
                            HttpServletRequest request, Model model)
        throws Exception {
        String supcheckid = request.getParameter("supcheckid");
        String ids = request.getParameter("ids");
        String[] supcheckids = supcheckid.split(",");
        purCon.setId(ids);
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
        purCon.setYear(new BigDecimal(sdf.format(new Date())));
        purCon.setCreatedAt(new Date());
        purCon.setUpdatedAt(new Date());
        if (purCon.getMoney_string() != "" && purCon.getMoney_string() != null) {
            purCon.setMoney(new BigDecimal(purCon.getMoney_string()));
        }
        if (purCon.getBudget_string() != "" && purCon.getBudget_string() != null) {
            purCon.setBudget(new BigDecimal(purCon.getBudget_string()));
        }
        purCon.setSupplierCheckIds(supcheckid);
        PurchaseContract pur = purchaseContractService.selectById(purCon.getId());
        purCon.setSupplierBankAccount(purCon.getSupplierBankAccount_string());
        purCon.setPurchaseBankAccount(purCon.getPurchaseBankAccount_string());
        if (pur == null) {
            purchaseContractService.insertSelective(purCon);
        } else {
            purchaseContractService.updateByPrimaryKeySelective(purCon);
        }
        String id = purCon.getId();
        List<ContractRequired> requList = proList.getProList();
        List<ContractRequired> conRequList = contractRequiredService.selectConRequeByContractId(purCon.getId());
        List<ContractRequired> requListNew=new ArrayList<ContractRequired>();
        if (requList != null) {
          for (int i = 0; i < requList.size(); i++ ) {
            if(requList.get(i)!=null){
               if(requList.get(i).getTransportFees()!=null&&requList.get(i).getTransportFees()==1){
                 requListNew.add(requList.get(i));
               }else{
                 if (requList.get(i).getGoodsName() != null) {
                   requListNew.add(requList.get(i));
               }
               }
            }
         }
            if (conRequList.size() > 0) {
                contractRequiredService.deleteByContractId(purCon.getId());
            }
            for (ContractRequired conRequ : requListNew) {
                conRequ.setContractId(id);
                contractRequiredService.insertSelective(conRequ);
            }
        }
        if (supcheckid != null) {
            if (purCon.getManualType() != 1) {
                List<SupplierCheckPass> byContractId = supplierCheckPassService.getByContractId(id);
                if (byContractId != null && byContractId.size() > 0) {
                    for (SupplierCheckPass pass : byContractId) {
                        pass.setId(pass.getId());
                        pass.setIsCreateContract(1);
                        pass.setContractId(id);
                        supplierCheckPassService.update(pass);
                    }
                } else {
                    SupplierCheckPass sup = new SupplierCheckPass();
                    for (String pass : supcheckids) {
                        sup.setId(pass);
                        sup.setIsCreateContract(1);
                        sup.setContractId(id);
                        supplierCheckPassService.update(sup);
                    }
                }

            }
        }

        return "redirect:selectDraftContract.html";
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午2:56:42
     * @Description: 生成合同草稿
     * @param @param purCon 合同实体类
     * @param @param proList 明细list
     * @param @param result
     * @param @param request
     * @param @param model
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/updateZanCun")
    public String updateZanCun(HttpServletRequest request, Model model, String id, @CurrentUser
    User user)
        throws Exception {
        PurchaseContract purCon = purchaseContractService.selectById(id);
        String supcheckid = request.getParameter("supcheckid");
        /*
         * SupplierCheckPass supChkPa = supplierCheckPassService.findByPrimaryKey(supcheckid);
         * String contractuuid = supChkPa.getContractId();
         */
        model.addAttribute("attachuuid", id);
        DictionaryData dd = new DictionaryData();
        dd.setCode("DRAFT_REVIEWED");
        List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
        request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
        if (datas.size() > 0) {
            model.addAttribute("attachtypeId", datas.get(0).getId());
        }

        /* 授权书 */
        DictionaryData ddbook = new DictionaryData();
        ddbook.setCode("CONTRACT_WARRANT");
        List<DictionaryData> bookdata = dictionaryDataServiceI.find(ddbook);
        request.getSession().setAttribute("bookattachsysKey", Constant.TENDER_SYS_KEY);
        if (bookdata.size() > 0) {
            model.addAttribute("bookattachtypeId", bookdata.get(0).getId());
        }
        model.addAttribute("kinds", DictionaryDataUtil.find(5));
        model.addAttribute("id", id);

        if (purCon.getMoney() != null) {
            purCon.setMoney_string(purCon.getMoney().toString());
        }
        if (purCon.getBudget() != null) {
            purCon.setBudget_string(purCon.getBudget().toString());
        }
        if (purCon.getSupplierBankAccount() != null) {
            purCon.setSupplierBankAccount_string(purCon.getSupplierBankAccount().toString());
        }
        if (purCon.getPurchaseBankAccount() != null) {
            purCon.setPurchaseBankAccount_string(purCon.getPurchaseBankAccount().toString());
        }
        purCon.setMoney_string(purCon.getMoney() == null ? "" : purCon.getMoney().toString());
        purCon.setBudget_string(purCon.getBudget() == null ? "" : purCon.getBudget().toString());
        purCon.setSupplierBankAccount_string(purCon.getSupplierBankAccount() == null ? "" : purCon.getSupplierBankAccount().toString());
        purCon.setPurchaseBankAccount_string(purCon.getPurchaseBankAccount() == null ? "" : purCon.getPurchaseBankAccount().toString());
        purCon.setBudget(new BigDecimal(purCon.getBudget_string().equals("") ? "0" : purCon.getBudget_string().toString()));
        purCon.setSupplierBankAccount(purCon.getSupplierBankAccount_string());
        purCon.setPurchaseBankAccount(purCon.getPurchaseBankAccount_string());
        List<ContractRequired> resultList = contractRequiredService.selectConRequeByContractId(purCon.getId());
        PurchaseDep purchaseDep = chaseDepOrgService.findByOrgId(purCon.getPurchaseDepName());
        model.addAttribute("requList", resultList);
        model.addAttribute("purCon", purCon);
        model.addAttribute("supcheckid", supcheckid);
        model.addAttribute("user", user);
        model.addAttribute("purchaseDep", purchaseDep);
        return "bss/cs/purchaseContract/errContract";
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午2:56:42
     * @Description: 生成合同草稿
     * @param @param purCon 合同实体类
     * @param @param proList 明细list
     * @param @param result
     * @param @param request
     * @param @param model
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/addStraightContract")
    public String addStraightContract(PurchaseContract purCon, ProList proList,
                                      BindingResult result, HttpServletRequest request, Model model)
        throws Exception {
        String dga = request.getParameter("dga");
        String dra = request.getParameter("dra");
        String fga = request.getParameter("fga");
        String fra = request.getParameter("fra");
        Map<String, Object> map = valid(model, purCon);
        Boolean flag = (boolean)map.get("flag");
        model = (Model)map.get("model");
        if (purCon.getDraftGitAt() == null) {
            flag = false;
            model.addAttribute("ERR_draftGitAt", "草案上报时间不可为空");
        }
        if (purCon.getDraftReviewedAt() == null) {
            flag = false;
            model.addAttribute("ERR_draftReviewedAt", "草案报批时间不可为空");
        }
        if (purCon.getFormalGitAt() == null) {
            flag = false;
            model.addAttribute("ERR_formalGitAt", "正式合同上报时间不可为空");
        }
        if (purCon.getFormalReviewedAt() == null) {
            flag = false;
            model.addAttribute("ERR_formalReviewedAt", "正式合同报批时间不可为空");
        }
        if (ValidateUtils.isNull(purCon.getApprovalNumber())) {
            flag = false;
            model.addAttribute("ERR_approvalNumber", "合同批准文号不可为空");
        }
        if (ValidateUtils.isNull(purCon.getQuaCode())) {
            flag = false;
            model.addAttribute("ERR_quaCode", "资格证号不能为空");
        }
        if (ValidateUtils.isNull(purCon.getSupplierPurId())) {
            flag = false;
            model.addAttribute("ERR_supplierPurId", "组织机构代码不能为空");
        }
        if (purCon.getFormalGitAt() != null && purCon.getFormalReviewedAt() != null
            && purCon.getDraftGitAt() != null && purCon.getDraftReviewedAt() != null) {
            Date draftGitAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dga);
            Date draftRAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dra);
            Date formalGitAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fga);
            Date formalRAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fra);
            purCon.setDraftGitAt(draftGitAt);
            purCon.setDraftReviewedAt(draftRAt);
            purCon.setFormalGitAt(formalGitAt);
            purCon.setFormalReviewedAt(formalRAt);
            if (draftGitAt.getTime() > draftRAt.getTime()
                || draftGitAt.getTime() > draftRAt.getTime()
                || draftRAt.getTime() > formalRAt.getTime()
                || draftRAt.getTime() > formalGitAt.getTime()) {
                flag = false;
                model.addAttribute("ERR_draftGitAt", "正式合同时间不可早于草稿合同时间");
                model.addAttribute("ERR_draftReviewedAt", "正式合同时间不可早于草稿合同时间");
                model.addAttribute("ERR_formalGitAt", "正式合同时间不可早于草稿合同时间");
                model.addAttribute("ERR_formalReviewedAt", "正式合同时间不可早于草稿合同时间");
            } else if (purCon.getDraftGitAt().getTime() > purCon.getDraftReviewedAt().getTime()) {
                flag = false;
                model.addAttribute("ERR_draftGitAt", "草案报批时间应晚于提报时间");
                model.addAttribute("ERR_draftReviewedAt", "草案报批时间应晚于提报时间");
            } else if (purCon.getFormalGitAt().getTime() > purCon.getFormalReviewedAt().getTime()) {
                flag = false;
                model.addAttribute("ERR_formalGitAt", "正式合同报批时间应晚于提报时间");
                model.addAttribute("ERR_formalReviewedAt", "正式合同报批时间应晚于提报时间");
            }
        }
        String url = "";
        List<ContractRequired> requList = proList.getProList();
        if (flag == false) {
            model.addAttribute("purCon", purCon);
            model.addAttribute("requList", requList);
            if (requList != null) {
                for (int i = 0; i < requList.size(); i++ ) {
                    if (requList.get(i).getPlanNo() == null) {
                        requList.remove(i);
                    }
                }
            }
            model.addAttribute("attachuuid", purCon.getId());
            DictionaryData dd = new DictionaryData();
            dd.setCode("CONTRACT_APPROVE_ATTACH");
            List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
            request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
            if (datas.size() > 0) {
                model.addAttribute("attachtypeId", datas.get(0).getId());
            }
            model.addAttribute("kinds", DictionaryDataUtil.find(5));
            url = "bss/cs/purchaseContract/straightContract";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
            purCon.setYear(new BigDecimal(sdf.format(new Date())));
            purCon.setCreatedAt(new Date());
            purCon.setUpdatedAt(new Date());
            purCon.setMoney(new BigDecimal(purCon.getMoney_string()));
            purCon.setBudget(new BigDecimal(purCon.getBudget_string()));
            purCon.setSupplierBankAccount(purCon.getSupplierBankAccount_string());
            purCon.setPurchaseBankAccount(purCon.getPurchaseBankAccount_string());
            purchaseContractService.insertSelectiveById(purCon);
            purchaseContractService.createWord(purCon, requList, request);
            appraisalContractService.insertPurchaseContract(purCon);
            String id = purCon.getId();
            if (requList != null) {
                for (int i = 0; i < requList.size(); i++ ) {
                    if (requList.get(i).getPlanNo() == null) {
                        requList.remove(i);
                    }
                }
                for (ContractRequired conRequ : requList) {
                    conRequ.setContractId(id);
                    contractRequiredService.insertSelective(conRequ);
                }
            }
            url = "redirect:selectAllPuCon.html";
        }
        return url;
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午2:56:42
     * @Description: 生成合同草稿
     * @param @param purCon 合同实体类
     * @param @param proList 明细list
     * @param @param result
     * @param @param request
     * @param @param model
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/toValidRoughContract")
    public void toValidRoughContract(PurchaseContract purCon, HttpServletResponse response)
        throws Exception {
        Boolean flag = true;
        Map<String, Object> map = new HashMap<String, Object>();
        if (purCon.getDraftGitAt() == null) {
            flag = false;
            map.put("gitAt", "提报时间不能为空");
        }
        if (purCon.getDraftReviewedAt() == null) {
            flag = false;
            map.put("reviewAt", "报批时间不能为空");
        }
        if (flag && purCon.getDraftGitAt().getTime() > purCon.getDraftReviewedAt().getTime()) {
            flag = false;
            map.put("gitAt", "报批时间不能早于提报时间");
            map.put("reviewAt", "报批时间不能早于提报时间");
        }
        if (flag) {
            super.writeJson(response, 1);
        } else {
            super.writeJson(response, JSONSerializer.toJSON(map).toString());
        }
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午2:56:42
     * @Description: 生成合同草稿案
     * @param @param purCon 合同实体类
     * @param @param proList 明细list
     * @param @param result
     * @param @param request
     * @param @param model
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/toRoughContract")
    public String toRoughContract(PurchaseContract purCon)
        throws Exception {
        purCon.setUpdatedAt(new Date());
        purchaseContractService.updateByPrimaryKeySelective(purCon);
        return "redirect:selectDraftContract.html";
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午3:02:44
     * @Description: 修改合同草案
     * @param @param agrfile
     * @param @param request
     * @param @param purCon
     * @param @param result
     * @param @param proList
     * @param @param model
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/updateDraftContract")
    public String updateDraftContract(PurchaseContract purCon, ProList proList,
                                      HttpServletRequest request, Model model)
        throws Exception {
        Map<String, Object> map = valid(model, purCon);
        model = (Model)map.get("model");
        Boolean flag = (boolean)map.get("flag");
        String url = "";
        if (flag == false) {
            List<ContractRequired> requList = proList.getProList();
            List<ContractRequired> requListNew=new ArrayList<ContractRequired>();
            model.addAttribute("purCon", purCon);
            if(requList!=null){
              for (int i = 0; i < requList.size(); i++ ) {
                if(requList.get(i)!=null){
                   if(requList.get(i).getTransportFees()!=null&&requList.get(i).getTransportFees()==1){
                     requListNew.add(requList.get(i));
                   }else{
                     if (requList.get(i).getGoodsName() != null) {
                       requListNew.add(requList.get(i));
                   }
                   }
                }
             }
            }
            Orgnization orgnization = orgnizationServiceI.getOrgByPrimaryKey(purCon.getPurchaseDepName());
            /* purCon.setContractReList(requList); */
            /* model.addAttribute("purCon", purCon); */
            DictionaryData ddbook = new DictionaryData();
            ddbook.setCode("CONTRACT_WARRANT");
            List<DictionaryData> bookdata = dictionaryDataServiceI.find(ddbook);
            request.getSession().setAttribute("bookattachsysKey", Constant.TENDER_SYS_KEY);
            if (bookdata.size() > 0) {
                model.addAttribute("bookattachtypeId", bookdata.get(0).getId());
            }
            PurchaseDep purchaseDep = chaseDepOrgService.findByOrgId(purCon.getPurchaseDepName());
            model.addAttribute("attachuuid", purCon.getId());
            model.addAttribute("requList", requListNew);
            model.addAttribute("kinds", DictionaryDataUtil.find(5));
            model.addAttribute("org", orgnization);
            model.addAttribute("purchaseDep", purchaseDep);
            url = "bss/cs/purchaseContract/updateErrContract";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
            purCon.setYear(new BigDecimal(sdf.format(new Date())));
            purCon.setUpdatedAt(new Date());
            purCon.setMoney(new BigDecimal(purCon.getMoney_string()));
            purCon.setBudget(new BigDecimal(purCon.getBudget_string()));
            purCon.setSupplierBankAccount(purCon.getSupplierBankAccount_string());
            purCon.setPurchaseBankAccount(purCon.getPurchaseBankAccount_string());
            purchaseContractService.updateByPrimaryKeySelective(purCon);
            String id = purCon.getId();
            contractRequiredService.deleteByContractId(id);
            List<ContractRequired> requList = proList.getProList();
            List<ContractRequired> requListNew=new ArrayList<ContractRequired>();
            if (requList != null) {
              for (int i = 0; i < requList.size(); i++ ) {
                if(requList.get(i)!=null){
                   if(requList.get(i).getTransportFees()!=null&&requList.get(i).getTransportFees()==1){
                     requListNew.add(requList.get(i));
                   }else{
                     if (requList.get(i).getGoodsName() != null) {
                       requListNew.add(requList.get(i));
                   }
                   }
                }
             }
                for (ContractRequired conRequ : requListNew) {
                    if (conRequ.getId() == null) {
                        conRequ.setContractId(id);
                        contractRequiredService.insertSelective(conRequ);
                    } else {
                        contractRequiredService.updateByPrimaryKeySelective(conRequ);
                    }
                }
            }
            url = "redirect:selectDraftContract.html";
        }
        return url;
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午2:57:23
     * @Description: 校验公共方法
     * @param @param model
     * @param @param purCon 合同实体类
     * @param @return
     * @return Map
     */
    public Map<String, Object> valid(Model model, PurchaseContract purCon) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = true;
        if (ValidateUtils.isNull(purCon.getSupplierBankAccount_string())) {
            flag = false;
            model.addAttribute("ERR_supplierBankAccount", "乙方账号不能为空");
        } else if (!ValidateUtils.Number(purCon.getSupplierBankAccount_string())) {
            flag = false;
            model.addAttribute("ERR_supplierBankAccount", "请输入正确的乙方账号");
        }/*
          * else if(!ValidateUtils.BANK_ACCOUNT(purCon.getSupplierBankAccount_string())){ flag =
          * false; model.addAttribute("ERR_supplierBankAccount", "请输入正确的乙方账号"); }
          */
        if (ValidateUtils.isNull(purCon.getName())) {
            flag = false;
            model.addAttribute("ERR_name", "合同名称不能为空");
        }
        if (ValidateUtils.isNull(purCon.getPurchaseType())) {
            flag = false;
            model.addAttribute("ERR_purchaseType", "采购方式不能为空");
        }
        if (ValidateUtils.isNull(purCon.getProjectCode())) {
            flag = false;
            model.addAttribute("ERR_proCode", "项目编号不能为空");
        }
        if (ValidateUtils.isNull(purCon.getProjectName())) {
            flag = false;
            model.addAttribute("ERR_projectName", "项目名称不能为空");
        }
        // if(ValidateUtils.isNull(purCon.getDemandSector())){
        // flag = false;
        // model.addAttribute("ERR_demandSector", "需求部门不能为空");
        // }
        if (ValidateUtils.isNull(purCon.getCode())) {
            flag = false;
            model.addAttribute("ERR_code", "合同编号不能为空");
        } else {
            List<PurchaseContract> contractList = purchaseContractService.selectContractByCode();
            for (int i = 0; i < contractList.size(); i++ ) {
                if (purCon.getId().equals(contractList.get(i).getId())) {
                    contractList.remove(i);
                }
            }
            for (PurchaseContract con : contractList) {
                if (con.getCode() != null) {
                    if (con.getCode().equals(purCon.getCode())) {
                        flag = false;
                        model.addAttribute("ERR_code", "合同编号不可重复");
                    }
                }
            }
        }
        if (ValidateUtils.isNull(purCon.getDocumentNumber())) {
            flag = false;
            model.addAttribute("ERR_documentNumber", "计划任务文号不能为空");
        }
        /*
         * if(ValidateUtils.isNull(purCon.getQuaCode())){ flag = false;
         * model.addAttribute("ERR_quaCode", "采购机构文号不能为空"); }
         */
        if (ValidateUtils.isNull(purCon.getPurchaseDepName())) {
            flag = false;
            model.addAttribute("ERR_purchaseDepName", "甲方单位不能为空");
        } else {
            Orgnization org = orgnizationServiceI.getOrgByPrimaryKey(purCon.getPurchaseDepName());
            if (ValidateUtils.isNull(org.getName())) {
                flag = false;
                model.addAttribute("ERR_purchaseDepName", "甲方单位不能为空");
            }
        }
        if (ValidateUtils.isNull(purCon.getPrepaidRatio()+"")&&ValidateUtils.isNull(purCon.getWarrantyRatio()+"")) {
          flag = false;
          model.addAttribute("ERR_prepaidRatio", "预付比例和质保比例必须填写一个");
          model.addAttribute("ERR_warrantyRatio", "预付比例和质保比例必须填写一个");
        } 
        if (!ValidateUtils.isNull(purCon.getPrepaidRatio()+"")) {
          if(ValidateUtils.isNull(purCon.getPrepaymentAmount()+"")){
            flag = false;
            model.addAttribute("ERR_prepaymentAmount", "预付金额不能为空");
          }
        }
        if (!ValidateUtils.isNull(purCon.getWarrantyRatio()+"")) {
          if(ValidateUtils.isNull(purCon.getWarrantyAmount()+"")){
            flag = false;
            model.addAttribute("ERR_warrantyAmount", "质保金额不能为空");
          }
        }
        /*
         * if(ValidateUtils.isNull(purCon.getBingDepName())){ flag = false;
         * model.addAttribute("ERR_bingDepName", "需求部门不能为空"); }else{ PurchaseDep purDep =
         * purchaseOrgnizationServiceI.selectPurchaseById(purCon.getBingDepName());
         * if(ValidateUtils.isNull(purDep.getDepName())){ flag = false;
         * model.addAttribute("ERR_bingDepName", "需求部门不能为空"); } }
         */
        /*
         * if(ValidateUtils.isNull(purCon.getPurchaseLegal())){ flag = false;
         * model.addAttribute("ERR_purchaseLegal", "甲方法人不能为空"); }
         */
        /*
         * if(ValidateUtils.isNull(purCon.getPurchaseAgent())){ flag = false;
         * model.addAttribute("ERR_purchaseAgent", "甲方委托代理人不能为空"); }
         */
        if (ValidateUtils.isNull(purCon.getPurchaseContact())) {
            flag = false;
            model.addAttribute("ERR_purchaseContact", "甲方联系人不能为空");
        }
        if (ValidateUtils.isNull(purCon.getPurchaseContactTelephone())) {
            flag = false;
            model.addAttribute("ERR_purchaseContactTelephone", "甲方联系电话不能为空");
        }/*
          * else if(!ValidateUtils.Tele(purCon.getPurchaseContactTelephone())){ flag = false;
          * model.addAttribute("ERR_purchaseContactTelephone", "请输入正确的联系电话"); }
          */
        if (ValidateUtils.isNull(purCon.getPurchaseContactAddress())) {
            flag = false;
            model.addAttribute("ERR_purchaseContactAddress", "甲方地址不能为空");
        }
        if (ValidateUtils.isNull(purCon.getPurchaseUnitpostCode())) {
            flag = false;
            model.addAttribute("ERR_purchaseUnitpostCode", "甲方邮编不能为空");
        } else if (!ValidateUtils.Zipcode(purCon.getPurchaseUnitpostCode())) {
            flag = false;
            model.addAttribute("ERR_purchaseUnitpostCode", "请输入正确的邮编");
        }
        /*
         * if(ValidateUtils.isNull(purCon.getPurchasePayDep())){ flag = false;
         * model.addAttribute("ERR_purchasePayDep", "甲方付款单位不能为空"); }
         */
        if (ValidateUtils.isNull(purCon.getPurchaseBank())) {
            flag = false;
            model.addAttribute("ERR_purchaseBank", "甲方开户银行不能为空");
        }
        if (purCon.getContractType() == null) {
            flag = false;
            model.addAttribute("ERR_contractType", "合同类型不能为空");
        }
        if (ValidateUtils.isNull(purCon.getPurchaseBankAccount_string())) {
            flag = false;
            model.addAttribute("ERR_purchaseBankAccount", "甲方账号不能为空");
        } else if (!ValidateUtils.Number(purCon.getPurchaseBankAccount_string())) {
            flag = false;
            model.addAttribute("ERR_purchaseBankAccount", "请输入正确的甲方账号");
        }/*
          * else if(!ValidateUtils.BANK_ACCOUNT(purCon.getPurchaseBankAccount_string())){ flag =
          * false; model.addAttribute("ERR_purchaseBankAccount", "请输入正确的甲方账号"); }
          */
        if (ValidateUtils.isNull(purCon.getMoney_string())) {
            flag = false;
            model.addAttribute("ERR_money", "合同金额不能为空");
        } else if (!ValidateUtils.Money(purCon.getMoney_string())) {
            flag = false;
            model.addAttribute("ERR_money", "请输入正确金额");
        }
        if (ValidateUtils.isNull(purCon.getBudget_string())) {
            flag = false;
            model.addAttribute("ERR_budget", "合同预算不能为空");
        } else if (!ValidateUtils.Money(purCon.getBudget_string())) {
            flag = false;
            model.addAttribute("ERR_budget", "请输入正确金额");
        }
        if (ValidateUtils.isNull(purCon.getSupplierDepName())) {
            flag = false;
            model.addAttribute("ERR_supplierDepName", "乙方单位不能为空");
        }/*
          * else{ Supplier su = supplierService.selectOne(purCon.getSupplierDepName());
          * if(ValidateUtils.isNull(su.getSupplierName())){ flag = false;
          * model.addAttribute("ERR_supplierDepName", "乙方单位不能为空"); } }
          */
        /*
         * if(ValidateUtils.isNull(purCon.getSupplierLegal())){ flag = false;
         * model.addAttribute("ERR_supplierLegal", "乙方法人不能为空"); }
         */
        /*
         * if(ValidateUtils.isNull(purCon.getSupplierAgent())){ flag = false;
         * model.addAttribute("ERR_supplierAgent", "乙方委托代理人不能为空"); }
         */
        if (ValidateUtils.isNull(purCon.getSupplierContact())) {
            flag = false;
            model.addAttribute("ERR_supplierContact", "乙方联系人不能为空");
        }
        if (ValidateUtils.isNull(purCon.getSupplierContactTelephone())) {
            flag = false;
            model.addAttribute("ERR_supplierContactTelephone", "乙方联系电话不能为空");
        }/*
          * else if(!ValidateUtils.Tele(purCon.getSupplierContactTelephone())){ flag = false;
          * model.addAttribute("ERR_supplierContactTelephone", "请输入正确的联系电话"); }
          */
        if (ValidateUtils.isNull(purCon.getSupplierContactAddress())) {
            flag = false;
            model.addAttribute("ERR_supplierContactAddress", "乙方地址不能为空");
        }
        if (ValidateUtils.isNull(purCon.getSupplierUnitpostCode())) {
            flag = false;
            model.addAttribute("ERR_supplierUnitpostCode", "乙方邮编不能为空");
        } else if (!ValidateUtils.Zipcode(purCon.getSupplierUnitpostCode())) {
            flag = false;
            model.addAttribute("ERR_supplierUnitpostCode", "请输入正确的邮编");
        }
        if (ValidateUtils.isNull(purCon.getSupplierBank())) {
            flag = false;
            model.addAttribute("ERR_supplierBank", "乙方开户银行不能为空");
        }
        if (ValidateUtils.isNull(purCon.getSupplierBankName())) {
            flag = false;
            model.addAttribute("ERR_supplierBankName", "乙方开户名称不能为空");
        }
        // if(ValidateUtils.isNull(purCon.getBingContact())){
        // flag = false;
        // model.addAttribute("ERR_bingContact", "丙方联系人不能为空");
        // }
        // if(ValidateUtils.isNull(purCon.getBingContactTelephone())){
        // flag = false;
        // model.addAttribute("ERR_bingContactTelephone", "丙方联系电话不能为空");
        // }else if(!ValidateUtils.Tele(purCon.getBingContactTelephone())){
        // flag = false;
        // model.addAttribute("ERR_bingContactTelephone", "请输入正确的联系电话");
        // }
        // if(ValidateUtils.isNull(purCon.getBingContactAddress())){
        // flag = false;
        // model.addAttribute("ERR_bingContactAddress", "丙方地址不能为空");
        // }
        // if(ValidateUtils.isNull(purCon.getBingUnitpostCode())){
        // flag = false;
        // model.addAttribute("ERR_bingUnitpostCode", "丙方邮编不能为空");
        // }else if(!ValidateUtils.Zipcode(purCon.getBingUnitpostCode())){
        // flag = false;
        // model.addAttribute("ERR_bingUnitpostCode", "请输入正确的邮编");
        // }
        map.put("flag", flag);
        map.put("model", model);
        return map;
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午2:58:05
     * @Description: 查询草案合同列表
     * @param @param request
     * @param @param page 分页
     * @param @param model
     * @param @param purCon 合同实体类
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/selectDraftContract")
    public String selectDraftContract(@CurrentUser User user, Integer page, Model model, PurchaseContract contract)  {
    	if(page==null){
            page=1;
        }
		HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("page", page);
        if(StringUtils.isNotBlank(contract.getProjectName())){
            map.put("projectName", contract.getProjectName());
        }
        if(StringUtils.isNotBlank(contract.getCode())){
            map.put("code", contract.getCode());
        }
        if(StringUtils.isNotBlank(contract.getSupplierDepName())){
            map.put("supplierName", contract.getSupplierDepName());
        }
        if(StringUtils.isNotBlank(contract.getPurchaseDepName())){
            map.put("purchaseDepName", contract.getPurchaseDepName());
        }
        if(StringUtils.isNotBlank(contract.getDemandSector())){
            map.put("demandSector", contract.getDemandSector());
        }
        if(StringUtils.isNotBlank(contract.getDocumentNumber())){
            map.put("documentNumber", contract.getDocumentNumber());
        }
        if(StringUtils.isNotBlank(contract.getYear_string())){
            if(ValidateUtils.Integer(contract.getYear_string())){
                map.put("year", new BigDecimal(contract.getYear_string()));
            }
        }
        if(StringUtils.isNotBlank(contract.getBudgetSubjectItem())){
            map.put("budgetSubjectItem", contract.getBudgetSubjectItem());
        }
        if (contract.getStatus() != null) {
        	map.put("status", contract.getStatus());
		}
        map.put("purchaseDepName", user.getOrg().getId());
        List<PurchaseContract> list = purchaseContractService.contractSupervisionList(map);
        BigDecimal contractSum = BigDecimal.ZERO;
        for (PurchaseContract purchaseContract : list) {
        	 if (purchaseContract.getMoney() != null) {
                 contractSum = contractSum.add(purchaseContract.getMoney());
             }
		}
        PageInfo<PurchaseContract> info = new PageInfo<PurchaseContract>(list);
        model.addAttribute("info", info);
        model.addAttribute("contract", contract);
        model.addAttribute("authType", user.getTypeName());
        model.addAttribute("contractSum", contractSum);
    	return "bss/cs/purchaseContract/draftlist";
    }
    /*public String selectDraftContract(@CurrentUser
    User user, HttpServletRequest request, Integer page, Model model, PurchaseContract purCon) throws Exception {
        if (page == null) {
            page = 1;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        if (purCon.getProjectName() != null) {
            map.put("projectName", purCon.getProjectName());
        }
        if (purCon.getCode() != null) {
            map.put("code", purCon.getCode());
        }
        if (purCon.getSupplierDepName() != null) {
            map.put("supplierDepName", purCon.getSupplierDepName());
        }
        if (purCon.getPurchaseDepName() != null) {
            map.put("purchaseDepName", purCon.getPurchaseDepName());
        }
        if (purCon.getDemandSector() != null) {
            map.put("demandSector", purCon.getDemandSector());
        }
        if (purCon.getDocumentNumber() != null) {
            map.put("documentNumber", purCon.getDocumentNumber());
        }
        if (purCon.getYear_string() != null) {
            if (ValidateUtils.Integer(purCon.getYear_string())) {
                map.put("year", new BigDecimal(purCon.getYear_string()));
            } else {
                map.put("year", 1234);
            }
        }
        if (purCon.getBudgetSubjectItem() != null) {
            map.put("budgetSubjectItem", purCon.getBudgetSubjectItem());
        }
        if (purCon.getStatus() != null) {
            map.put("status", purCon.getStatus());
        }
        List<PurchaseContract> draftConList = new ArrayList<PurchaseContract>();
        // 采购机构
        Orgnization orgnization = orgnizationService.findByCategoryId(user.getOrg().getId());
        boolean roleflag = true;
        String typeName = user.getTypeName();
        if ("1".equals(typeName)) {
            map.put("purchaseDepName", user.getOrg().getId());
            if (purCon.getStatus() != null) {
                draftConList = purchaseContractService.selectAllContractByStatus(map);
            } else {
                draftConList = purchaseContractService.selectAllContractByCode(map);
            }

        }
        
         * 判断如果是管理部门
         
        if ("2".equals(typeName)) {

            List<PurchaseOrg> list = purchaseOrgnizationServiceI.get(user.getOrg().getId());
            if (list != null && list.size() > 0) {
                List<PurchaseContract> draftConLists = new ArrayList<PurchaseContract>();
                for (int i = 0; i < list.size(); i++ ) {
                    map.put("purchaseDepName", list.get(i).getPurchaseDepId());
                    if (purCon.getStatus() != null) {
                        draftConLists = purchaseContractService.selectAllContractByStatus(map);
                    } else {
                        draftConLists = purchaseContractService.selectAllContractByCode(map);
                    }
                    draftConList.addAll(draftConLists);
                }
            }
        }
        // 判断如果是需求部门
        if ("0".equals(typeName)) {
            String name = orgnization.getName();
            map.put("deptname", name);
            if (purCon.getStatus() != null) {
                draftConList = purchaseContractService.selectAllContractByStatus(map);
            } else {
                draftConList = purchaseContractService.selectAllContractByCode(map);
            }

        }

        BigDecimal contractSum = new BigDecimal(0);
        if (roleflag) {
            // List<Role> roleList = user.getRoles();
            // boolean isRole = false;
            // for(Role r:roleList){
            // if(r.getCode().equals("PURCHASE_ORG_R")||r.getCode().equals("ADMIN_R")){
            // isRole = true;
            // }
            // }
            // if(isRole){

            for (PurchaseContract pur : draftConList) {
                Supplier su = null;
                Orgnization org = null;
                PurchaseDep purchaseDep = null;
                if (pur.getSupplierDepName() != null) {
                    su = supplierService.selectOne(pur.getSupplierDepName());
                }
                // PurchaseDep purdep =
                // purchaseOrgnizationServiceI.selectPurchaseById(pur.getBingDepName());
                if (pur.getPurchaseDepName() != null) {
                    purchaseDep = chaseDepOrgService.findByOrgId(pur.getPurchaseDepName());
                }
                if (purchaseDep != null) {
                    if (purchaseDep.getDepName() == null) {
                        pur.setShowDemandSector("");
                    } else {
                        pur.setShowDemandSector(purchaseDep.getDepName());
                    }
                }
                if (su != null) {
                    if (su.getSupplierName() != null) {
                        pur.setShowSupplierDepName(su.getSupplierName());
                    } else {
                        pur.setShowSupplierDepName("");
                    }
                }
                // if(purdep.getDepName()!=null){
                // pur.setShowPurchaseDepName(purdep.getDepName());
                // }else{
                // pur.setShowPurchaseDepName("");
                // }
            }
            // }
            if (draftConList.size() > 0) {
                for (int i = 0; i < draftConList.size(); i++ ) {
                    if (draftConList.get(i) != null) {
                        if (draftConList.get(i).getMoney() != null) {
                            contractSum = contractSum.add(draftConList.get(i).getMoney());
                        }
                    }
                }
            }
        }
        PageInfo<PurchaseContract> list = new PageInfo<PurchaseContract>(draftConList);
        model.addAttribute("list", list);
        model.addAttribute("draftConList", draftConList);
        model.addAttribute("contractSum", contractSum);
        model.addAttribute("purCon", purCon);
        model.addAttribute("authType", user.getTypeName());
        return "bss/cs/purchaseContract/draftlist";
    }*/

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午2:58:05
     * @Description: 打印上传附件页面
     * @param @param request
     * @param @param page 分页
     * @param @param model
     * @param @param purCon 合同实体类
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/filePage")
    public String filePage(HttpServletRequest request, Model model)
        throws Exception {
        String id = request.getParameter("id");
        String status = request.getParameter("status");
        DictionaryData dd = new DictionaryData();
        dd.setCode("DRAFT_REVIEWED");
        List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
        request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
        if (datas.size() > 0) {
            model.addAttribute("attachtypeId", datas.get(0).getId());
        }
        model.addAttribute("attachuuid", id);
        model.addAttribute("status", status);
        return "bss/cs/purchaseContract/filePage";
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午2:58:05
     * @Description: 查询草稿合同列表
     * @param @param request
     * @param @param page 分页
     * @param @param model
     * @param @param purCon 合同实体类
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/selectRoughContract")
    public String selectRoughContract(HttpServletRequest request, Integer page, Model model,
                                      PurchaseContract purCon)
        throws Exception {
        if (page == null) {
            page = 1;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        if (purCon.getProjectName() != null) {
            map.put("projectName", purCon.getProjectName());
        }
        if (purCon.getCode() != null) {
            map.put("code", purCon.getCode());
        }
        if (purCon.getSupplierDepName() != null) {
            map.put("supplierDepName", purCon.getSupplierDepName());
        }
        if (purCon.getPurchaseDepName() != null) {
            map.put("purchaseDepName", purCon.getPurchaseDepName());
        }
        if (purCon.getDemandSector() != null) {
            map.put("demandSector", purCon.getDemandSector());
        }
        if (purCon.getDocumentNumber() != null) {
            map.put("documentNumber", purCon.getDocumentNumber());
        }
        if (purCon.getYear_string() != null) {
            if (ValidateUtils.Integer(purCon.getYear_string())) {
                map.put("year", new BigDecimal(purCon.getYear_string()));
            } else {
                map.put("year", 1234);
            }
        }
        if (purCon.getBudgetSubjectItem() != null) {
            map.put("budgetSubjectItem", purCon.getBudgetSubjectItem());
        }
        List<PurchaseContract> roughConList = new ArrayList<PurchaseContract>();
        User user = (User)request.getSession().getAttribute("loginUser");
        List<Role> roleList = user.getRoles();
        boolean isRole = false;
        for (Role r : roleList) {
            if (r.getCode().equals("PURCHASE_ORG_R") || r.getCode().equals("ADMIN_R")) {
                isRole = true;
            }
        }
        if (isRole) {
            // roughConList = purchaseContractService.selectRoughContract(map);
            roughConList = purchaseContractService.selectAllContractByStatus(map);
        }
        BigDecimal contractSum = new BigDecimal(0);
        if (roughConList.size() > 0) {
            for (int i = 0; i < roughConList.size(); i++ ) {
                if (roughConList.get(i) != null) {
                    if (roughConList.get(i).getMoney() != null) {
                        contractSum = contractSum.add(roughConList.get(i).getMoney());
                    }
                }
            }
        }
        model.addAttribute("list", new PageInfo<PurchaseContract>(roughConList));
        model.addAttribute("roughConList", roughConList);
        model.addAttribute("contractSum", contractSum);
        model.addAttribute("purCon", purCon);
        return "bss/cs/purchaseContract/roughlist";
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午2:58:38
     * @Description: 创建草稿合同页面
     * @param @param request
     * @param @param model
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/createDraftContract")
    public String createDraftContract(HttpServletRequest request, Model model)
        throws Exception {
        String id = request.getParameter("ids");
        PurchaseContract draftCon = purchaseContractService.selectDraftById(id);
        List<ContractRequired> conRequList = contractRequiredService.selectConRequeByContractId(draftCon.getId());
        draftCon.setContractReList(conRequList);

        //
        // String uuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        // model.addAttribute("attachuuid", uuid);
        // DictionaryData dd=new DictionaryData();
        // dd.setCode("CONTRACT_APPROVE_ATTACH");
        // List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
        // request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
        // if(datas.size()>0){
        // model.addAttribute("attachtypeId", datas.get(0).getId());
        // }
        if (draftCon.getMoney() != null) {
            draftCon.setMoney_string(draftCon.getMoney().toString());
        }
        if (draftCon.getBudget() != null) {
            draftCon.setBudget_string(draftCon.getBudget().toString());
        }
        if (draftCon.getSupplierBankAccount() != null) {
            draftCon.setSupplierBankAccount_string(draftCon.getSupplierBankAccount().toString());
        }
        if (draftCon.getPurchaseBankAccount() != null) {
            draftCon.setPurchaseBankAccount_string(draftCon.getPurchaseBankAccount().toString());
        }
        DictionaryData ddbook = new DictionaryData();
        ddbook.setCode("CONTRACT_WARRANT");
        List<DictionaryData> bookdata = dictionaryDataServiceI.find(ddbook);
        request.getSession().setAttribute("bookattachsysKey", Constant.TENDER_SYS_KEY);
        if (bookdata.size() > 0) {
            model.addAttribute("bookattachtypeId", bookdata.get(0).getId());
        }
        model.addAttribute("attachuuid", draftCon.getId());
        Orgnization orgnization = orgnizationServiceI.getOrgByPrimaryKey(draftCon.getPurchaseDepName());
        PurchaseDep purchaseDep = chaseDepOrgService.findByOrgId(draftCon.getPurchaseDepName());
        HashMap<String, Object> map = new HashMap<>();
        map.put("contractId", id);
        List<ContractAdvice> find = contractAdviceService.find(map);
        if (find != null && !find.isEmpty()) {
        	User user = userService.getUserById(find.get(0).getUserId());
        	find.get(0).setUserId(user.getRelName());
			model.addAttribute("contractAdvice", find.get(0));
			model.addAttribute("auditId", DictionaryDataUtil.getId("CONTRACT_AUDIT"));
		}
        model.addAttribute("purCon", draftCon);
        model.addAttribute("kinds", DictionaryDataUtil.find(5));
        model.addAttribute("id", id);
        model.addAttribute("requList", conRequList);
        model.addAttribute("org", orgnization);
        model.addAttribute("purchaseDep", purchaseDep);
        return "bss/cs/purchaseContract/updateContract";
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午2:58:38
     * @Description: 创建草稿合同页面
     * @param @param request
     * @param @param model
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/createRoughContract")
    public String createRoughContract(HttpServletRequest request, Model model)
        throws Exception {
        String ids = request.getParameter("ids");
        PurchaseContract roughCon = purchaseContractService.selectRoughById(ids);
        List<ContractRequired> conRequList = contractRequiredService.selectConRequeByContractId(roughCon.getId());
        roughCon.setContractReList(conRequList);
        //
        // String uuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        // model.addAttribute("attachuuid", uuid);
        // DictionaryData dd=new DictionaryData();
        // dd.setCode("CONTRACT_APPROVE_ATTACH");
        // List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
        // request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
        // if(datas.size()>0){
        // model.addAttribute("attachtypeId", datas.get(0).getId());
        // }
        if (roughCon.getMoney() != null) {
            roughCon.setMoney_string(roughCon.getMoney().toString());
        }
        if (roughCon.getBudget() != null) {
            roughCon.setBudget_string(roughCon.getBudget().toString());
        }
        if (roughCon.getSupplierBankAccount() != null) {
            roughCon.setSupplierBankAccount_string(roughCon.getSupplierBankAccount().toString());
        }
        if (roughCon.getPurchaseBankAccount() != null) {
            roughCon.setPurchaseBankAccount_string(roughCon.getPurchaseBankAccount().toString());
        }
        model.addAttribute("draftCon", roughCon);
        model.addAttribute("ids", ids);
        return "bss/cs/purchaseContract/roughContract";
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午2:59:07
     * @Description: 展示合同草稿
     * @param @param request
     * @param @param model
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/showDraftContract")
    public String showDraftContract(HttpServletRequest request, Model model)
        throws Exception {
        String ids = request.getParameter("ids");
        String url = "";
        PurchaseContract draftCon = purchaseContractService.selectDraftById(ids);
        List<ContractRequired> conRequList = contractRequiredService.selectConRequeByContractId(draftCon.getId());
        draftCon.setContractReList(conRequList);
        Orgnization org = orgnizationServiceI.getOrgByPrimaryKey(draftCon.getPurchaseDepName());
        draftCon.setShowDemandSector(org.getName());
        if (draftCon.getManualType() != 1) {
            Supplier su = supplierService.selectOne(draftCon.getSupplierDepName());
            draftCon.setShowSupplierDepName(su.getSupplierName());
        } else {
            draftCon.setShowSupplierDepName(draftCon.getSupplierDepName());
        }
        model.addAttribute("draftCon", draftCon);
        model.addAttribute("attachuuid", ids);
        DictionaryData dd = new DictionaryData();
        dd.setCode("DRAFT_REVIEWED");
        List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
        request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
        if (datas.size() > 0) {
            model.addAttribute("attachtypeId", datas.get(0).getId());
        }

        /* 授权书 */
        DictionaryData ddbook = new DictionaryData();
        ddbook.setCode("CONTRACT_WARRANT");
        List<DictionaryData> bookdata = dictionaryDataServiceI.find(ddbook);
        request.getSession().setAttribute("bookattachsysKey", Constant.TENDER_SYS_KEY);
        if (bookdata.size() > 0) {
            model.addAttribute("bookattachtypeId", bookdata.get(0).getId());
        }

        DictionaryData dds = new DictionaryData();
        dds.setCode("CONTRACT_APPROVE_ATTACH");
        List<DictionaryData> datass = dictionaryDataServiceI.find(dds);
        request.getSession().setAttribute("contractattachsysKey", Constant.TENDER_SYS_KEY);
        if (datas.size() > 0) {
            model.addAttribute("contractattachId", datass.get(0).getId());
        }
        if (draftCon.getStatus() == 0) {
            url = "bss/cs/purchaseContract/showRoughContract";
        } else if (draftCon.getStatus() == 1) {
            url = "bss/cs/purchaseContract/showDraftContract";
        } else if (draftCon.getStatus() == 2) {
            url = "bss/cs/purchaseContract/showFormalContract";
        }
        return url;
    }

    @RequestMapping("/showDraftContracts")
    public String showDraftContracts(HttpServletRequest request, Model model)
        throws Exception {
        String ids = request.getParameter("ids");
        String status = request.getParameter("status");
        String url = "";
        PurchaseContract draftCon = purchaseContractService.selectDraftById(ids);
        List<ContractRequired> conRequList = contractRequiredService.selectConRequeByContractId(draftCon.getId());
        draftCon.setContractReList(conRequList);
        Supplier su = supplierService.selectOne(draftCon.getSupplierDepName());
        // PurchaseDep purdep =
        // purchaseOrgnizationServiceI.selectPurchaseById(draftCon.getBingDepName());
        Orgnization org = orgnizationServiceI.getOrgByPrimaryKey(draftCon.getPurchaseDepName());
        draftCon.setShowDemandSector(org.getName());
        draftCon.setShowSupplierDepName(su.getSupplierName());
        // draftCon.setShowPurchaseDepName(purdep.getDepName());
        model.addAttribute("draftCon", draftCon);
        model.addAttribute("attachuuid", ids);
        DictionaryData dd = new DictionaryData();
        dd.setCode("DRAFT_REVIEWED");
        List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
        request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
        if (datas.size() > 0) {
            model.addAttribute("attachtypeId", datas.get(0).getId());
        }

        /* 授权书 */
        DictionaryData ddbook = new DictionaryData();
        ddbook.setCode("CONTRACT_WARRANT");
        List<DictionaryData> bookdata = dictionaryDataServiceI.find(ddbook);
        request.getSession().setAttribute("bookattachsysKey", Constant.TENDER_SYS_KEY);
        if (bookdata.size() > 0) {
            model.addAttribute("bookattachtypeId", bookdata.get(0).getId());
        }

        DictionaryData dds = new DictionaryData();
        dds.setCode("CONTRACT_APPROVE_ATTACH");
        List<DictionaryData> datass = dictionaryDataServiceI.find(dds);
        request.getSession().setAttribute("contractattachsysKey", Constant.TENDER_SYS_KEY);
        if (datas.size() > 0) {
            model.addAttribute("contractattachId", datass.get(0).getId());
        }
        if (status.equals("0")) {
            url = "bss/cs/purchaseContract/showRoughContract";
        } else if (status.equals("1")) {
            url = "bss/cs/purchaseContract/showDraftContract";
        } else if (status.equals("2")) {
            url = "bss/cs/purchaseContract/showFormalContracts";
        }
        return url;
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午2:59:07
     * @Description: 展示合同草稿
     * @param @param request
     * @param @param model
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/showRoughContract")
    public String showRoughContract(HttpServletRequest request, Model model)
        throws Exception {
        String ids = request.getParameter("ids");
        PurchaseContract draftCon = purchaseContractService.selectRoughById(ids);
        List<ContractRequired> conRequList = contractRequiredService.selectConRequeByContractId(draftCon.getId());
        draftCon.setContractReList(conRequList);
        model.addAttribute("draftCon", draftCon);
        return "bss/cs/purchaseContract/showDraftContract";
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午3:01:37
     * @Description: 展示正式合同
     * @param @param request
     * @param @param model
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/showFormalContract")
    public String showFormalContract(HttpServletRequest request, Model model)
        throws Exception {
        String ids = request.getParameter("ids");
        model.addAttribute("attachuuid", ids);
        DictionaryData dd = new DictionaryData();
        dd.setCode("CONTRACT_APPROVE_ATTACH");
        List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
        request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
        if (datas.size() > 0) {
            model.addAttribute("attachtypeId", datas.get(0).getId());
        }
        PurchaseContract draftCon = purchaseContractService.selectFormalById(ids);
        List<ContractRequired> conRequList = contractRequiredService.selectConRequeByContractId(draftCon.getId());
        draftCon.setContractReList(conRequList);
        model.addAttribute("draftCon", draftCon);
        return "bss/cs/purchaseContract/showFormalContract";
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午3:03:56
     * @Description: 生成正式合同
     * @param @param agrfile
     * @param @param purCon
     * @param @param request
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/updateDraftById")
    public String updateDraftById(PurchaseContract purCon, HttpServletRequest request, Model model)
        throws Exception {
        Boolean flag = true;
        String url = "";
        String fga = request.getParameter("fga");
        String fra = request.getParameter("fra");
        if (purCon.getApprovalNumber() == null || purCon.getApprovalNumber().equals("")) {
            flag = false;
            model.addAttribute("ERR_approvalNumber", "合同批准文号不可为空");
        }
        if (purCon.getFormalGitAt() == null) {
            flag = false;
            model.addAttribute("ERR_formalGitAt", "正式合同上报时间不可为空");
        }
        if (purCon.getFormalReviewedAt() == null) {
            flag = false;
            model.addAttribute("ERR_formalReviewedAt", "正式合同报批时间不可为空");
        }
        if (purCon.getFormalGitAt() != null && purCon.getFormalReviewedAt() != null) {
            Date formalGitAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fga);
            Date formalRAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fra);
            purCon.setFormalGitAt(formalGitAt);
            purCon.setFormalReviewedAt(formalRAt);
            if (formalGitAt.getTime() > formalRAt.getTime()) {
                flag = false;
                model.addAttribute("ERR_formalGitAt", "报批时间不能早于提报时间");
                model.addAttribute("ERR_formalReviewedAt", "报批时间不能早于提报时间");
            }
        }
        if (flag) {
            purCon.setUpdatedAt(new Date());
            purCon.setFormalAt(new Date());
            //List<ContractRequired> requList = contractRequiredService.selectConRequeByContractId(purCon.getId());
            PurchaseContract pur = purchaseContractService.selectById(purCon.getId());
            purchaseContractService.updateByPrimaryKeySelective(purCon);
            // purchaseContractService.createWord(pur, requList,request);
            appraisalContractService.insertPurchaseContract(pur);
            url = "redirect:selectAllPuCon.html";
        } else {
            model.addAttribute("attachuuid", purCon.getId());
            DictionaryData dd = new DictionaryData();
            dd.setCode("CONTRACT_APPROVE_ATTACH");
            List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
            request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
            if (datas.size() > 0) {
                model.addAttribute("attachtypeId", datas.get(0).getId());
            }
            model.addAttribute("purCon", purCon);
            url = "bss/cs/purchaseContract/transFormaTional";
        }
        return url;
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午3:03:56
     * @Description: 生成正式合同
     * @param @param agrfile
     * @param @param purCon
     * @param @param request
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/toCreateFormalContract")
    public String toCreateFormalContract(PurchaseContract purCon, Model model) throws Exception {
        Boolean flag = true;
        String url = "";
        if (purCon.getApprovalNumber() == null || purCon.getApprovalNumber().equals("")) {
            flag = false;
            model.addAttribute("ERR_approvalNumber", "合同批准文号不可为空");
        }
        if (purCon.getFormalGitAt() == null) {
            flag = false;
            model.addAttribute("ERR_formalGitAt", "正式合同上报时间不可为空");
        }
        if (purCon.getFormalReviewedAt() == null) {
            flag = false;
            model.addAttribute("ERR_formalReviewedAt", "正式合同报批时间不可为空");
        }
        if (purCon.getFormalGitAt() != null && purCon.getFormalReviewedAt() != null) {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        	String fga = sdf.format(purCon.getFormalGitAt());
        	String fra = sdf.format(purCon.getFormalReviewedAt());
            Date formalGitAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fga);
            Date formalRAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fra);
            purCon.setFormalGitAt(formalGitAt);
            purCon.setFormalReviewedAt(formalRAt);
            if (formalGitAt.getTime() > formalRAt.getTime()) {
                flag = false;
                model.addAttribute("ERR_formalGitAt", "报批时间不能早于提报时间");
                model.addAttribute("ERR_formalReviewedAt", "报批时间不能早于提报时间");
            }
        }
        List<UploadFile> upLoad = uploadService.getFilesOther(purCon.getId(), DictionaryDataUtil.getId("CONTRACT_APPROVE_ATTACH"), Constant.TENDER_SYS_KEY+"");
        if (upLoad.isEmpty()) {
        	flag = false;
            model.addAttribute("ERR_formalUpload", "附件不能为空");
		}
        if (flag) {
            purCon.setUpdatedAt(new Date());
            purCon.setFormalAt(new Date());
            PurchaseContract pur = purchaseContractService.selectById(purCon.getId());
            purchaseContractService.updateByPrimaryKeySelective(purCon);
            appraisalContractService.insertPurchaseContract(pur);
            url = "redirect:selectDraftContract.html";
        } else {
            model.addAttribute("id", purCon.getId());
            model.addAttribute("attachtypeId",  DictionaryDataUtil.getId("CONTRACT_APPROVE_ATTACH"));
            model.addAttribute("purCon", purCon);
            url = "bss/cs/purchaseContract/toFormalContract";
        }
        return url;
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午3:05:57
     * @Description: 删除合同草稿
     * @param @param request
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/deleteDraft")
    public String deleteDraft(HttpServletRequest request)
        throws Exception {
        String id = request.getParameter("ids");
        String[] ids = id.split(",");
        for (int i = 0; i < ids.length; i++ ) {
            purchaseContractService.deleteDraftByPrimaryKey(ids[i]);
        }
        return "redirect:selectDraftContract.html";
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午3:05:57
     * @Description: 删除合同草稿
     * @param @param request
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/deleteRoughDraft")
    public String deleteRoughDraft(HttpServletRequest request)
        throws Exception {
        String id = request.getParameter("ids");
        String[] ids = id.split(",");
        for (int i = 0; i < ids.length; i++ ) {
            purchaseContractService.deleteRoughByPrimaryKey(ids[i]);
        }
        return "redirect:selectRoughContract.html";
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-11 下午3:06:13
     * @Description: 查询正式合同
     * @param @param request
     * @param @param page 分页
     * @param @param model
     * @param @param purCon 合同实体类
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/selectFormalContract")
    public String selectFormalContract(HttpServletRequest request, Integer page, Model model,
                                       PurchaseContract purCon)
        throws Exception {
        if (page == null) {
            page = 1;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        if (purCon.getProjectName() != null) {
            map.put("projectName", purCon.getProjectName());
        }
        if (purCon.getCode() != null) {
            map.put("code", purCon.getCode());
        }
        if (purCon.getSupplierDepName() != null) {
            map.put("supplierDepName", purCon.getSupplierDepName());
        }
        if (purCon.getPurchaseDepName() != null) {
            map.put("purchaseDepName", purCon.getPurchaseDepName());
        }
        if (purCon.getDemandSector() != null) {
            map.put("demandSector", purCon.getDemandSector());
        }
        if (purCon.getDocumentNumber() != null) {
            map.put("documentNumber", purCon.getDocumentNumber());
        }
        if (purCon.getYear_string() != null) {
            if (ValidateUtils.Integer(purCon.getYear_string())) {
                map.put("year", new BigDecimal(purCon.getYear_string()));
            } else {
                map.put("year", 1234);
            }
        }
        if (purCon.getBudgetSubjectItem() != null) {
            map.put("budgetSubjectItem", purCon.getBudgetSubjectItem());
        }
        List<PurchaseContract> formalConList = new ArrayList<PurchaseContract>();
        User user = (User)request.getSession().getAttribute("loginUser");
        List<Role> roleList = user.getRoles();
        boolean isRole = false;
        for (Role r : roleList) {
            if (r.getCode().equals("PURCHASE_ORG_R") || r.getCode().equals("ADMIN_R")) {
                isRole = true;
            }
        }
        if (isRole) {
            formalConList = purchaseContractService.selectFormalContract(map);
        }
        PageInfo<PurchaseContract> list = new PageInfo<PurchaseContract>(formalConList);
        model.addAttribute("list", list);
        BigDecimal contractSum = new BigDecimal(0);
        if (formalConList.size() > 0) {
            for (int i = 0; i < formalConList.size(); i++ ) {
                if (formalConList.get(i) != null) {
                    if (formalConList.get(i).getMoney() != null) {
                        contractSum = contractSum.add(formalConList.get(i).getMoney());
                    }
                }
            }
        }
        model.addAttribute("formalConList", formalConList);
        model.addAttribute("contractSum", contractSum);
        model.addAttribute("purCon", purCon);
        return "bss/cs/purchaseContract/formallist";
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-15 下午2:54:43
     * @Description: 跳转生成正式合同页面
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/createTransFormal")
    public String createTransFormal(PurchaseContract purCon, ProList proList,
                                    BindingResult result, HttpServletRequest request, Model model,
                                    @CurrentUser
                                    User user)
        throws Exception {
        String id = request.getParameter("ids");
        String supcheckid = request.getParameter("supcheckid");
        String[] supcheckids = supcheckid.split(",");
        purCon.setId(id);
        Map<String, Object> map = valid(model, purCon);
        model = (Model)map.get("model");
        Boolean flag = (boolean)map.get("flag");
        String url = "";
        if (flag == false) {
            List<ContractRequired> requList = proList.getProList();
            model.addAttribute("purCon", purCon);
            model.addAttribute("kinds", DictionaryDataUtil.find(5));

            /* 授权书 */
            DictionaryData ddbook = new DictionaryData();
            ddbook.setCode("CONTRACT_WARRANT");
            List<DictionaryData> bookdata = dictionaryDataServiceI.find(ddbook);
            request.getSession().setAttribute("bookattachsysKey", Constant.TENDER_SYS_KEY);
            if (bookdata.size() > 0) {
                model.addAttribute("bookattachtypeId", bookdata.get(0).getId());
            }

            if (requList != null) {
              for (int i = 0; i < requList.size(); i++ ) {
                if(requList.get(i)!=null){
                   if(requList.get(i).getTransportFees()!=null&&requList.get(i).getTransportFees()==1){
                     continue;
                   }else{
                     if (requList.get(i).getGoodsName() == null) {
                       requList.remove(i);
                   }
                   }
                }else{
                  requList.remove(i);
                }
             }
            }
            PurchaseDep purchaseDep = chaseDepOrgService.findByOrgId(purCon.getPurchaseDepName());
            model.addAttribute("requList", requList);
            model.addAttribute("planNos", purCon.getDocumentNumber());
            model.addAttribute("id", purCon.getId());
            model.addAttribute("attachuuid", purCon.getId());
            model.addAttribute("supcheckid", supcheckid);
            model.addAttribute("user", user);
            model.addAttribute("purchaseDep", purchaseDep);
            url = "bss/cs/purchaseContract/errContract";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
            purCon.setYear(new BigDecimal(sdf.format(new Date())));
            purCon.setCreatedAt(new Date());
            purCon.setUpdatedAt(new Date());
            purCon.setMoney(new BigDecimal(purCon.getMoney_string()));
            purCon.setBudget(new BigDecimal(purCon.getBudget_string()));
            purCon.setSupplierBankAccount(purCon.getSupplierBankAccount_string());
            purCon.setPurchaseBankAccount(purCon.getPurchaseBankAccount_string());
            purCon.setSupplierCheckIds(supcheckid);
            PurchaseContract pur = purchaseContractService.selectById(purCon.getId());
            if (pur == null) {
                purchaseContractService.insertSelective(purCon);
            } else {
                purchaseContractService.updateByPrimaryKeySelective(purCon);
            }
            List<ContractRequired> requList = proList.getProList();
            if (requList != null) {
                for (int i = 0; i < requList.size(); i++ ) {
                    if (requList.get(i).getPlanNo() == null) {
                        requList.remove(i);
                    }
                }
                for (ContractRequired conRequ : requList) {
                    conRequ.setContractId(purCon.getId());
                    contractRequiredService.insertSelective(conRequ);
                }
            }
            model.addAttribute("attachuuid", id);
            DictionaryData dd = new DictionaryData();
            dd.setCode("CONTRACT_APPROVE_ATTACH");
            List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
            request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
            if (datas.size() > 0) {
                model.addAttribute("attachtypeId", datas.get(0).getId());
            }
            if (purCon.getManualType() != 1) {
                for (String supchid : supcheckids) {
                    SupplierCheckPass sup = new SupplierCheckPass();
                    sup.setId(supchid);
                    sup.setIsCreateContract(1);
                    supplierCheckPassService.update(sup);
                }
            }

            model.addAttribute("id", id);
            model.addAttribute("supckid", supcheckid);
            url = "bss/cs/purchaseContract/transFormaTional";
            // model.addAttribute("id", id);
            //
            // model.addAttribute("attachuuid", id);
            // DictionaryData dd=new DictionaryData();
            // dd.setCode("CONTRACT_APPROVE_ATTACH");
            // List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
            // request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
            // if(datas.size()>0){
            // model.addAttribute("attachtypeId", datas.get(0).getId());
            // }
            // List<ContractRequired> requList = proList.getProList();
            // if(requList!=null){
            // for(int i=0;i<requList.size();i++){
            // if(requList.get(i).getPlanNo()==null){
            // requList.remove(i);
            // }
            // }
            // }
            // return "bss/cs/purchaseContract/transFormaTional";
        }
        return url;
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-15 下午2:54:43
     * @Description: 跳转生成正式合同页面
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/toFormalContract")
    public String toFormalContract(String id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("attachtypeId", DictionaryDataUtil.getId("CONTRACT_APPROVE_ATTACH"));
        return "bss/cs/purchaseContract/toFormalContract";
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-15 下午2:54:43
     * @Description: 跳转生成正式合同页面
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/createerrContractPage")
    public String createerrContractPage(HttpServletRequest request, Model model, @CurrentUser
    User user)
        throws Exception {
        String id = request.getParameter("ids");
        String supckid = request.getParameter("supckid");
        String[] supcheckids = supckid.split(",");
        PurchaseContract purCon = purchaseContractService.selectById(id);
        purCon.setBudget_string(purCon.getBudget().toString());
        purCon.setMoney_string(purCon.getMoney().toString());
        purCon.setPurchaseBankAccount_string(purCon.getPurchaseBankAccount().toString());
        purCon.setSupplierBankAccount_string(purCon.getSupplierBankAccount().toString());
        List<ContractRequired> requList = contractRequiredService.selectConRequeByContractId(id);
        model.addAttribute("purCon", purCon);
        model.addAttribute("id", id);
        model.addAttribute("kinds", DictionaryDataUtil.find(5));
        model.addAttribute("requList", requList);
        model.addAttribute("planNos", purCon.getDocumentNumber());
        purchaseContractService.deleteRoughByPrimaryKey(id);
        for (String supchid : supcheckids) {
            SupplierCheckPass sup = new SupplierCheckPass();
            sup.setId(supchid);
            sup.setIsCreateContract(0);
            supplierCheckPassService.update(sup);
        }
        /* 授权书 */
        DictionaryData ddbook = new DictionaryData();
        ddbook.setCode("CONTRACT_WARRANT");
        List<DictionaryData> bookdata = dictionaryDataServiceI.find(ddbook);
        request.getSession().setAttribute("bookattachsysKey", Constant.TENDER_SYS_KEY);
        if (bookdata.size() > 0) {
            model.addAttribute("bookattachtypeId", bookdata.get(0).getId());
        }
        model.addAttribute("attachuuid", id);
        model.addAttribute("supcheckid", supckid);
        model.addAttribute("user", user);
        return "bss/cs/purchaseContract/errContract";
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-15 下午2:54:43
     * @Description: 跳转生成正式合同页面
     * @param @return
     * @param @throws Exception
     * @return String
     */
    @RequestMapping("/createStraightContract")
    public String createStraightContract(HttpServletRequest request, Model model)
        throws Exception {
        String contractuuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        model.addAttribute("attachuuid", contractuuid);
        DictionaryData dd = new DictionaryData();
        dd.setCode("CONTRACT_APPROVE_ATTACH");
        List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
        request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
        if (datas.size() > 0) {
            model.addAttribute("attachtypeId", datas.get(0).getId());
        }
        model.addAttribute("kinds", DictionaryDataUtil.find(5));
        return "bss/cs/purchaseContract/straightContract";
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-20 上午9:05:30
     * @Description: 新增明细验证
     * @param @param conRe
     * @param @param response
     * @param @throws Exception
     * @return void
     */
    @RequestMapping("/validAddRe")
    public void validAddRe(ContractRequired conRe, HttpServletResponse response)
        throws Exception {
        boolean flag = true;
        Map<String, Object> map = new HashMap<String, Object>();
        if (ValidateUtils.isNull(conRe.getGoodsName())) {
            flag = false;
            map.put("wzmc", "产品名称不能为空");
        }
        if (ValidateUtils.isNull(conRe.getPlanNo())) {
            flag = false;
            map.put("bh", "编号不能为空");
        }
        if (ValidateUtils.isNull(conRe.getDeliverDate())) {
            flag = false;
            map.put("jfsj", "交付时间不能为空");
        }
        if (ValidateUtils.isNull(conRe.getBrand())) {
            flag = false;
            map.put("ppsb", "品牌商标不能为空");
        }
        if (ValidateUtils.isNull(conRe.getStand())) {
            flag = false;
            map.put("ggxh", "规格型号不能为空");
        }
        if (ValidateUtils.isNull(conRe.getItem())) {
            flag = false;
            map.put("jldw", "计量单位不能为空");
        }
        if (ValidateUtils.isNull(conRe.getPurchaseCount_string())) {
            flag = false;
            map.put("sl", "数量不能为空");
        } else if (!ValidateUtils.Z_index(conRe.getPurchaseCount_string())) {
            flag = false;
            map.put("sl", "请输入正整数");
        }
        if (ValidateUtils.isNull(conRe.getPrice_string())) {
            flag = false;
            map.put("dj", "单价不能为空");
        } else if (!ValidateUtils.Z_index(conRe.getPurchaseCount_string())) {
            flag = false;
            map.put("dj", "请输入正整数");
        }
        if (flag) {
            super.writeJson(response, 1);
        } else {
            super.writeJson(response, JSONSerializer.toJSON(map).toString());
        }
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-20 上午9:06:41
     * @Description: 验证选择的供应商
     * @param @throws Exception
     * @return void
     */
    @RequestMapping("/isChoiceSupplier")
    public void isChoiceSupplier(String delSupplier, HttpServletResponse response)
        throws Exception {
        Boolean flag = true;
        Map<String, Object> map = new HashMap<String, Object>();
        if (ValidateUtils.isNull(delSupplier)) {
            flag = false;
            map.put("delsuerr", "请先选择供应商");
        }
        if (flag) {
            super.writeJson(response, 1);
        } else {
            super.writeJson(response, JSONSerializer.toJSON(map).toString());
        }
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-20 上午9:06:41
     * @Description: 查询所有可用需求部门
     * @param @throws Exception
     * @return void
     */

    @RequestMapping(value = "/findAllUsefulOrg", produces = "application/json;charest=utf-8")
    public void findAllUsefulOrg(HttpServletResponse response, HttpServletRequest request)
        throws Exception {
        // List<Orgnization> list = purchaseContractService.findAllUsefulOrg();
        List<Orgnization> list = orgnizationServiceI.initOrgByType("1");
        super.writeJson(response, list);
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-20 上午9:06:41
     * @Description: 查询所有可用供应商
     * @param @throws Exception
     * @return void
     */
    @RequestMapping(value = "/findAllUsefulSupplier", produces = "application/json;charest=utf-8")
    public void findAllUsefulSupplier(HttpServletResponse response, HttpServletRequest request)
        throws Exception {
        List<Supplier> list = supplierService.findAllUsefulSupplier();
        super.writeJson(response, list);
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-20 上午9:06:41
     * @Description: 查询所有可用采购部门
     * @param @throws Exception
     * @return void
     */
    @RequestMapping(value = "/findAllUsefulPurDep", produces = "application/json;charest=utf-8")
    public void findAllUsefulPurDep(HttpServletResponse response, HttpServletRequest request)
        throws Exception {
        List<PurchaseDep> list = purchaseOrgnizationServiceI.findAllUsefulPurchaseDep();
        super.writeJson(response, list);
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-20 上午9:06:41
     * @Description: 选择需求部门后回显
     * @param @throws Exception
     * @return void
     */
    @RequestMapping(value = "/changeXuqiu", produces = "application/json;charest=utf-8")
    public void changeXuqiu(String id, HttpServletResponse response, HttpServletRequest request)
        throws Exception {
        PurchaseDep purchaseDep = chaseDepOrgService.findByOrgId(id);
        super.writeJson(response, purchaseDep);
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-20 上午9:06:41
     * @Description: 选择供应商后回显
     * @param @throws Exception
     * @return void
     */
    @RequestMapping(value = "/changeSupplierDep", produces = "application/json;charest=utf-8")
    public void changeSupplierDep(String id, HttpServletResponse response,
                                  HttpServletRequest request)
        throws Exception {
        Supplier su = supplierService.selectById(id);
        super.writeJson(response, su);
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-20 上午9:06:41
     * @Description: 选择供应商后回显
     * @param @throws Exception
     * @return void
     */
    @RequestMapping(value = "/changePurDep", produces = "application/json;charest=utf-8")
    public void changePurDep(String id, HttpServletResponse response, HttpServletRequest request)
        throws Exception {
        PurchaseDep purDep = purchaseOrgnizationServiceI.selectPurchaseById(id);
        super.writeJson(response, purDep);
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-21 上午10:58:44
     * @Description: 验证草稿提报时间
     * @param @param purCon
     * @param @param response
     * @param @throws Exception
     * @return void
     */
    @RequestMapping("/addDraftGit")
    public void addDraftGit(HttpServletResponse response, HttpServletRequest request)
        throws Exception {
        Boolean flag = true;
        String draftGitAt = request.getParameter("draftGitAt");
        String draftReviewedAt = request.getParameter("draftReviewedAt");
        Date dga = null;
        Date dra = null;
        if (!draftGitAt.equals("")) {
            dga = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(draftGitAt);
        }
        if (!draftReviewedAt.equals("")) {
            dra = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(draftReviewedAt);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        if (dga == null) {
            flag = false;
            map.put("gitAt", "提报时间不能为空");
        }
        if (dra == null) {
            flag = false;
            map.put("reviewAt", "报批时间不能为空");
        }
        if (flag && dga.getTime() > dra.getTime()) {
            flag = false;
            map.put("gitAt", "报批时间不能早于提报时间");
            map.put("reviewAt", "报批时间不能早于提报时间");
        }
        String gitStr = "";
        String reviewedStr = "";
        if (dga != null) {
            gitStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dga);
        }
        if (dra != null) {
            reviewedStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dra);
        }
        map.put("gitStr", gitStr);
        map.put("reviewedStr", reviewedStr);
        if (flag) {
            super.writeJson(response, 1);
        } else {
            super.writeJson(response, JSONSerializer.toJSON(map).toString());
        }
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author QuJie
     * @date 2016-11-21 上午10:58:44
     * @Description: 跳转至打印页面
     * @param @param purCon
     * @param @param response
     * @param @throws Exception
     * @return void
     */
    @RequestMapping("/createPrintPage")
    @ResponseBody
    public void createPrintPage(PurchaseContract purCon, ProList proList, BindingResult result,
                                HttpServletResponse response, HttpServletRequest request)
        throws Exception {
        /* String typeId = DictionaryDataUtil.getId("CONTRACT_FILE"); */
        Map<String, Object> map = null;
        /*
         * List<UploadFile> files = uploadService.getFilesOther(purCon.getId(), typeId,
         * Constant.TENDER_SYS_KEY+"");
         */

        /*
         * if (files != null && files.size() > 0){ map=new HashMap<String, Object>();
         * map.put("filePath", files.get(0).getPath()) ; map.put("fileName",
         * files.get(0).getName()); JSONObject object=JSONObject.fromObject(map); String json =
         * object.toString(); response.setContentType("text/html;charset=utf-8");
         * response.getWriter().write(json); response.getWriter().flush();
         * response.getWriter().close();
         */
        /* }else{ */
        map = purchaseContractService.createWord(purCon, proList.getProList(), request);
        super.writeJson(response, JSONSerializer.toJSON(map).toString());
        /* } */

    }

    /**
     * 〈简述〉保存招标文件到服务器 〈详细描述〉
     * 
     * @author Qu Jie
     * @param req
     * @param projectId 项目id
     * @throws IOException
     * @throws FileUploadException
     */
    @RequestMapping("/saveContractFile")
    public void saveContractFile(HttpServletResponse response, HttpServletRequest req,
                                 String projectId, Model model, String flowDefineId, String flag)
        throws IOException, FileUploadException {
        String result = "保存失败";
        // 判断该项目是否上传过招标文件
        String typeId = DictionaryDataUtil.getId("CONTRACT_FILE");
        List<UploadFile> files = uploadService.getFilesOther(projectId, typeId,
            Constant.TENDER_SYS_KEY + "");
        if (files != null && files.size() > 0) {
            // 删除 ,表中数据假删除
            uploadService.updateFileOther(files.get(0).getId(), Constant.TENDER_SYS_KEY + "");
            result = uploadService.saveOnlineFile(req, projectId, typeId, Constant.TENDER_SYS_KEY
                                                                          + "");
        } else {
            result = uploadService.saveOnlineFile(req, projectId, typeId, Constant.TENDER_SYS_KEY
                                                                          + "");
        }
        System.out.println(result);
    }

    /**
     * 〈简述〉跳转到打印页面 〈详细描述〉
     * 
     * @author Qu Jie
     * @param req
     * @param projectId 项目id
     * @throws IOException
     */
    @RequestMapping("/printContract")
    public String printContract(HttpServletRequest req, Model model)
        throws IOException {
        String id = req.getParameter("id");
        String status = req.getParameter("status");
        String url = "";
        PurchaseContract pur = purchaseContractService.selectById(id);
        //List<ContractRequired> requList = contractRequiredService.selectConRequeByContractId(pur.getId());
        /* Map<String, Object> map = purchaseContractService.createWord(pur, requList, req); */
        model.addAttribute("id", id);
        model.addAttribute("pur", pur);
        /* model.addAttribute("filePath", map.get("filePath")); */
        if (status.equals("0")) {
            url = "bss/cs/purchaseContract/printDraft";
        } else if (status.equals("1")) {
            url = "bss/cs/purchaseContract/printDraft";
        } else if (status.equals("2")) {
            url = "bss/cs/purchaseContract/printformal";
        }
        return url;
    }

    /**
     * 〈简述〉下载附件 〈详细描述〉
     * 
     * @author Qu Jie
     * @param request
     * @param fileId 附件id
     * @param response
     */
    @RequestMapping("/loadFile")
    public void loadFile(HttpServletRequest request, HttpServletResponse response,
                         String filePath, String id) {
        String typeId = DictionaryDataUtil.getId("CONTRACT_FILE");
        List<UploadFile> files = uploadService.getFilesOther(id, typeId, Constant.TENDER_SYS_KEY
                                                                         + "");
        if (files != null && files.size() > 0) {
            downloadService.downLoadFile(request, response, files.get(0).getPath());
        } else {
            downloadService.downLoadFile(request, response, filePath);
        }

    }

    @RequestMapping("/getProjectName")
    @ResponseBody
    public Project getProjectName(HttpServletRequest request, HttpServletResponse response,
                                  String code, String name) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectNumber", code);
        map.put("name", name);
        List<Project> projects = projectService.selectProjectByCode(map);
        Project project = new Project();
        if (projects != null && projects.size() > 0) {
            project.setName(projects.get(0).getName());
        }
        return project;
    }

    @RequestMapping("/projectContract")
    @ResponseBody
    public String projectContract(HttpServletRequest request, HttpServletResponse response,
                                  String projectId, @CurrentUser
                                  User user) {
        String result = "";
        Project projects = projectService.selectById(projectId);
        if (projects != null) {
            List<PurchaseContract> pcs = purchaseContractService.selectByProjectCode(projects.getProjectNumber());
            if (pcs.size() > 0) {
                result = "no";
            } else {
                String pid = UUID.randomUUID().toString().replaceAll("-", "");
                PurchaseContract purCon = new PurchaseContract();
                purCon.setCreatedAt(new Date());
                purCon.setId(pid);
                purCon.setProjectId(projects.getId());
                purCon.setProjectCode(projects.getProjectNumber());
                purCon.setProjectName(projects.getName());
                purCon.setPurchaseType(projects.getPurchaseType());
                purCon.setManualType(1);
                purCon.setStatus(0);
                purCon.setPurchaseDepName(user.getOrg().getId());
                purchaseContractService.insertSelective(purCon);
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("projectId", projects.getId());
                List<Packages> pas = packageService.selectByProjectKey(map);
                ContractRequired required = null;
                if (pas != null && pas.size() > 0) {
                    for (Packages p : pas) {
                        List<ProjectDetail> details = projectDetailService.selectByPackageId(p.getId());
                        if (details != null && details.size() > 0) {
                            for (ProjectDetail det : details) {
                                required = new ContractRequired();
                                required.setGoodsName(det.getGoodsName());
                                required.setBrand(det.getBrand());
                                required.setStand(det.getStand());
                                required.setItem(det.getItem());
                                BigDecimal purchaseCount = new BigDecimal(0);
                                if (det.getPurchaseCount() != null) {
                                    purchaseCount = new BigDecimal(
                                        det.getPurchaseCount().toString());
                                }
                                required.setPurchaseCount(purchaseCount);
                                BigDecimal price = new BigDecimal(0);
                                if (det.getPrice() != null) {
                                    price = new BigDecimal(det.getPrice().toString());
                                }
                                required.setPrice(price);
                                BigDecimal budget = new BigDecimal(0);
                                if (det.getBudget() != null) {
                                    budget = new BigDecimal(det.getBudget().toString());
                                }
                                required.setAmount(budget);
                                required.setDeliverDate(det.getDeliverDate());
                                required.setDetailId(det.getId());
                                required.setContractId(pid);
                                contractRequiredService.insertSelective(required);
                            }
                        }
                    }
                }
                result = "ok";
            }

        } else {
            result = "no";
        }
        return result;
    }

    /**
     * Description: 已完成采购合同只读列表
     * 
     * @author Easong
     * @version 2017年6月12日
     * @param user
     * @param request
     * @param page
     * @param model
     * @param purCon
     * @return
     * @throws Exception
     */
    @RequestMapping("/readOnlyList")
    public String readOnlyList(@CurrentUser
    User user, HttpServletRequest request, Integer page, Model model, PurchaseContract purCon,
                               PurchaseContractAnalyzeVo purchaseContractAnalyzeVo)
        throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if (page == null) {
            page = 1;
        }
        map.put("page", page);
        if (purCon.getProjectName() != null) {
            map.put("projectName", purCon.getProjectName());
        }
        if (purCon.getCode() != null) {
            map.put("code", purCon.getCode());
        }
        if (purCon.getSupplierDepName() != null) {
            map.put("supplierDepName", purCon.getSupplierDepName());
        }
        if (purCon.getPurchaseDepName() != null) {
            map.put("purchaseDepName", purCon.getPurchaseDepName());
        }
        if (purCon.getDemandSector() != null) {
            map.put("demandSector", purCon.getDemandSector());
        }
        if (purCon.getDocumentNumber() != null) {
            map.put("documentNumber", purCon.getDocumentNumber());
        }
        if (purCon.getYear_string() != null) {
            if (ValidateUtils.Integer(purCon.getYear_string())) {
                map.put("year", new BigDecimal(purCon.getYear_string()));
            } else {
                map.put("year", 1234);
            }
        }
        if (purCon.getBudgetSubjectItem() != null) {
            map.put("budgetSubjectItem", purCon.getBudgetSubjectItem());
        }
        if (purCon.getStatus() != null) {
            map.put("status", purCon.getStatus());
        }
        List<PurchaseContract> draftConList = new ArrayList<>();
        draftConList = purchaseContractService.selectAllContractByStatus(map);
        boolean roleflag = true;
        BigDecimal contractSum = new BigDecimal(0);
        if (roleflag) {
            for (PurchaseContract pur : draftConList) {
                Supplier su = null;
                Orgnization org = null;
                if (pur.getSupplierDepName() != null) {
                    su = supplierService.selectOne(pur.getSupplierDepName());
                }
                if (pur.getPurchaseDepName() != null) {
                    org = orgnizationServiceI.getOrgByPrimaryKey(pur.getPurchaseDepName());
                }
                if (org != null) {
                    if (org.getName() == null) {
                        pur.setShowDemandSector("");
                    } else {
                        pur.setShowDemandSector(org.getName());
                    }
                }
                if (su != null) {
                    if (su.getSupplierName() != null) {
                        pur.setShowSupplierDepName(su.getSupplierName());
                    } else {
                        pur.setShowSupplierDepName("");
                    }
                }
            }
            if (draftConList.size() > 0) {
                for (int i = 0; i < draftConList.size(); i++ ) {
                    if (draftConList.get(i) != null) {
                        if (draftConList.get(i).getMoney() != null) {
                            contractSum = contractSum.add(draftConList.get(i).getMoney());
                        }
                    }
                }
            }
        }
        PageInfo<PurchaseContract> list = new PageInfo<PurchaseContract>(draftConList);
        model.addAttribute("list", list);
        model.addAttribute("draftConList", draftConList);
        model.addAttribute("contractSum", contractSum);
        model.addAttribute("purCon", purCon);
        return "dss/rids/list/purchaseContractList";
    }

    @RequestMapping("/downdetail")
    public void downdetail(HttpServletResponse rs) {
        List<String> headers = new ArrayList<String>();
        headers.add("序号");
        headers.add("编号");
        headers.add("产品名称");
        headers.add("品牌商标");
        headers.add("规格型号");
        headers.add("计量单位");
        headers.add("数量");
        headers.add("单价(元)");
        headers.add("合计金额(元)");
        headers.add("交付时间");
        headers.add("备注");
        ExportExcel.ExpExs("合同标的模板表", null, headers, null, rs);
    }

    @RequestMapping(value = "/upload", produces = "text/html;charset=UTF-8")
    public void upload(MultipartFile file, HttpServletResponse response)
        throws Exception {
        List<ContractRequired> list = new ArrayList<ContractRequired>();
        ContractRequired purchaseRequired = null;
        //String fileName = file.getOriginalFilename();
        WorkbookFactory.create(file.getInputStream());
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            if (row.getRowNum() > 0) {
                purchaseRequired = new ContractRequired();
                for (Cell cell : row) {
                    extracted(purchaseRequired, cell);
                }
                list.add(purchaseRequired);
            }
        }
        super.writeJson(response, list);

    }

    private void extracted(ContractRequired purchaseRequired, Cell cell) {
        if (cell.getColumnIndex() == 1) {
            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                purchaseRequired.setPlanNo(String.valueOf(cell.getNumericCellValue()));
            } else {
                purchaseRequired.setPlanNo(cell.getStringCellValue());
            }
        }
        if (cell.getColumnIndex() == 2) {
            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                purchaseRequired.setGoodsName(String.valueOf(cell.getNumericCellValue()));
            } else {
                purchaseRequired.setGoodsName(cell.getStringCellValue());
            }
        }
        if (cell.getColumnIndex() == 3) {
            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                purchaseRequired.setBrand(String.valueOf(cell.getNumericCellValue()));
            } else {
                purchaseRequired.setBrand(cell.getStringCellValue());
            }
        }
        if (cell.getColumnIndex() == 4) {
            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                purchaseRequired.setStand(String.valueOf(cell.getNumericCellValue()));
            } else {
                purchaseRequired.setStand(cell.getStringCellValue());
            }
        }
        if (cell.getColumnIndex() == 5) {
            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                purchaseRequired.setItem(String.valueOf(cell.getNumericCellValue()));
            } else {
                purchaseRequired.setItem(cell.getStringCellValue());
            }
        }
        if (cell.getColumnIndex() == 6) {
            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                purchaseRequired.setPurchaseCount(new BigDecimal(
                    String.valueOf(cell.getNumericCellValue())));
            }
        }
        if (cell.getColumnIndex() == 7) {
            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                purchaseRequired.setPrice(new BigDecimal(
                    String.valueOf(cell.getNumericCellValue())));
            }
        }
        if (cell.getColumnIndex() == 8) {
            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                purchaseRequired.setAmount(new BigDecimal(
                    String.valueOf(cell.getNumericCellValue())));
            }
        }
        if (cell.getColumnIndex() == 9) {
            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                purchaseRequired.setDeliverDate(String.valueOf(cell.getNumericCellValue()));
            } else {
                purchaseRequired.setDeliverDate(cell.getStringCellValue());
            }
        }
        if (cell.getColumnIndex() == 10) {
            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                purchaseRequired.setMemo(String.valueOf(cell.getNumericCellValue()));
            } else {
                purchaseRequired.setMemo(cell.getStringCellValue());
            }
        }
    }
    
    @RequestMapping("/audit")
    @ResponseBody
    public String audit(@CurrentUser User user, String id, String projectId) {
		Boolean flag = contractAdviceService.saveContractAdvice(id, projectId, user.getId());
		if (flag) {
			return StaticVariables.SUCCESS;
		} else {
			return StaticVariables.FAILED;
		}
    }
}