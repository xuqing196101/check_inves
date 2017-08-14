package bss.controller.ppms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.Constant;
import common.constant.StaticVariables;
import common.model.UploadFile;
import common.service.UploadService;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierExtUserServicel;
import ses.util.DictionaryDataUtil;
import bss.controller.base.BaseController;
import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.SaleTender;
import bss.service.ppms.AdvancedPackageService;
import bss.service.ppms.AdvancedProjectService;
import bss.service.ppms.SaleTenderService;

@Controller
@Scope("prototype")
@RequestMapping("/adSaleTender")
public class AdSaleTenderController extends BaseController {
    
    @Autowired
    private SaleTenderService saleTenderService; //关联表
    
    @Autowired
    private SupplierAuditService auditService;//查询所有供应商
    
    @Autowired
    private SupplierExtUserServicel extUserServicel; //模板引入
    
    @Autowired
    private UploadService uploadService;
    
    @Autowired
    private AdvancedProjectService projectService;
    
    @Autowired
    private AdvancedPackageService packageService;
    
    /**
     * 
     *〈发售标书〉
     *〈详细描述〉
     * @author FengTian
     * @param model
     * @param projectId
     * @param flowDefineId
     * @return
     */
    @RequestMapping("/manage")
    public String manage(Model model, String projectId, String flowDefineId){
        AdvancedProject project = projectService.selectById(projectId);
        DictionaryData findById = DictionaryDataUtil.findById(project.getPurchaseType());
        model.addAttribute("kind", findById.getCode());
        model.addAttribute("projectId", projectId);
        model.addAttribute("flowDefineId", flowDefineId);
        return "bss/ppms/advanced_project/sall_tender/manage";
    }
    
    /**
     * 
     *〈登记供应商〉
     *〈详细描述〉
     * @author FengTian
     * @param projectId
     * @param model
     * @param supplier
     * @param flowDefineId
     * @param ix
     * @return
     */
    @RequestMapping("/view")
    public String view(String projectId, Model model, Supplier supplier, String flowDefineId, String ix) {
        if(StringUtils.isNotBlank(projectId)){
            AdvancedProject project = projectService.selectById(projectId);
            if(project != null){
                List<AdvancedPackages> lists = new ArrayList<AdvancedPackages>();
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("projectId",project.getId());
                List<AdvancedPackages> selectByAll = packageService.selectByAll(map);
                if(selectByAll != null && selectByAll.size() > 0){
                    for (AdvancedPackages packages : selectByAll) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("projectId", projectId);
                        hashMap.put("packageId", packages.getId());
                        if(StringUtils.isNotBlank(supplier.getSupplierName())){
                            hashMap.put("supplierName", supplier.getSupplierName());
                        }
                        if(StringUtils.isNotBlank(supplier.getArmyBuinessTelephone())){
                            hashMap.put("armyBuinessTelephone", supplier.getArmyBuinessTelephone());
                        }
                        List<SaleTender> saleTenderList = saleTenderService.getAdPackegeSuppliers(hashMap);
                        if(saleTenderList != null && saleTenderList.size() > 0){
                            packages.setSaleTenderList(saleTenderList);
                        }
                        lists.add(packages);
                    }
                }
                model.addAttribute("packageList", lists);
            }
            DictionaryData findById = DictionaryDataUtil.findById(project.getPurchaseType());
            model.addAttribute("status", findById.getCode());
            model.addAttribute("project", project);
        }
        model.addAttribute("supplier",supplier);
        model.addAttribute("flowDefineId", flowDefineId);
        model.addAttribute("ix",ix);
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        return "bss/ppms/advanced_project/sall_tender/view";
    }
    
    /**
     * 
     *〈单一来源查询供应商〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @return
     */
    @RequestMapping("/viewsupplier")
    @ResponseBody
    public String startTask(String id){
        SaleTender saleTender=new SaleTender();
        saleTender.setPackages(id);
        List<SaleTender> list = saleTenderService.getPackegeSuppliers(saleTender);
        if(list != null && list.size() >0){
            return "1";
        }else{
            return "0";
        }
    }
    
    /**
     * 
     *〈添加供应商〉
     *〈详细描述〉
     * @author Administrator
     * @param model
     * @param flowDefineId
     * @param projectId
     * @param page
     * @param packageId
     * @param supplier
     * @param ix
     * @param selectValue
     * @return
     */
    @RequestMapping("/showAllSuppliers")
    public String showAllSuppliers(Model model, String flowDefineId, String projectId,Integer page,String packageId,Supplier supplier,String ix,String selectValue){
        HashMap<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("packageId", packageId);
        map.put("isRemoved", "0");
        List<SaleTender> saleTenders = saleTenderService.getAdPackegeSuppliers(map);
        if(saleTenders != null && saleTenders.size() > 0){
            List<String> stsupplierIds = new ArrayList<String>();
            for (SaleTender tender : saleTenders) {
                if (StringUtils.isNotBlank(tender.getSupplierId())) {
                    stsupplierIds.add(tender.getSupplierId());
                }
            }
            if (stsupplierIds != null && stsupplierIds.size() > 0) {
                supplier.setStsupplierIds(stsupplierIds);
            }
        }
        AdvancedProject project = projectService.selectById(projectId);
        DictionaryData findById = DictionaryDataUtil.findById(project.getPurchaseType());
        model.addAttribute("kind", findById.getCode());
        List<Supplier> list = auditService.selectSaleTenderSupplier(supplier, page == null ? 1 : Integer.valueOf(page));
        model.addAttribute("list",new PageInfo<Supplier>(list));
        model.addAttribute("packageId", packageId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("supplierName", supplier.getSupplierName());
        model.addAttribute("ix", ix);
        model.addAttribute("selectValue", selectValue);
        return "bss/ppms/advanced_project/sall_tender/suppliers_list";
    }
    
    /**
     * 
     *〈公开招标添加供应商〉
     *〈详细描述〉
     * @author FengTian
     * @param user
     * @param ids
     * @param packages
     * @param projectId
     * @param ix
     * @return
     */
    @RequestMapping("/saveSupplier")
    public String saveSupplier(@CurrentUser User user, String ids, String packages, String projectId, String ix){
        if(StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(projectId) && StringUtils.isNotBlank(packages)){
            String[] id = ids.split(StaticVariables.COMMA_SPLLIT);
            for (int i = 0; i < id.length; i++ ) {
                saleTenderService.insert(new SaleTender(projectId, (short)2, id[i], (short)2, user.getId(),packages));
            }
            AdvancedProject project = projectService.selectById(projectId);
            if(project != null){
                project.setStatus(DictionaryDataUtil.getId("FSBSZ"));
                projectService.update(project);
            }
        }
        return "redirect:view.html?projectId="+projectId+"&ix="+ix;
    }
    
    @RequestMapping("/downList")
    public String downList(Model model, String projectId, String flowDefineId){
        AdvancedProject project = projectService.selectById(projectId);
        if(project != null){
            HashMap<String, Object> map = new HashMap<>();
            map.put("projectId", project.getId());
            map.put("statusBid", "2");
            List<SaleTender> saleTenders = saleTenderService.getAdPackegeSuppliers(map);
            if(saleTenders != null && saleTenders.size() > 0){
                removeSaleTender(saleTenders);
                for (SaleTender saleTender : saleTenders) {
                    String packageName = "";
                    map.put("supplierId", saleTender.getSupplierId());
                    //该供应商参与的包
                    List<SaleTender> tenders = saleTenderService.getAdPackegeSuppliers(map);
                    if (tenders != null && tenders.size() > 0) {
                        for (int i = 0; i < tenders.size(); i++) {
                            AdvancedPackages packages = packageService.selectById(tenders.get(i).getPackages());
                            if (i == 0) {
                                packageName = packages.getName();
                            } else {
                                packageName += "," + packages.getName();
                            }
                        }
                    }
                    saleTender.setPackageNames(packageName);
                }
                model.addAttribute("list", saleTenders);
            }
            model.addAttribute("projectId", project.getId());
        }
        return "bss/ppms/advanced_project/sall_tender/download_list";
    }
    
    /**
     * 
     *〈根据供应商参与包 处理相关文件〉
     *〈详细描述〉
     * @author FengTian
     * @param model
     * @param request
     * @param projectId
     * @param suppliersId
     * @return
     * @throws Exception
     */
    @RequestMapping("/processingDocuments")
    public String processingDocuments(Model model, HttpServletRequest request, String projectId,String suppliersId) throws Exception {
        //判断是否上传招标文件
        String typeId = DictionaryDataUtil.getId("PROJECT_BID");
        AdvancedProject project = projectService.selectById(projectId);
        if(project != null){
            List<UploadFile> files = uploadService.getFilesOther(project.getId(), typeId, Constant.TENDER_SYS_KEY+"");
            if(files != null && files.size() > 0){
                //调用生成word模板传人 标识0 表示 只是生成 拆包部分模板
                String filePath = extUserServicel.downLoadBiddingDoc(request,projectId,1,suppliersId);
                if(StringUtils.isNotBlank(filePath)){
                    model.addAttribute("filePath", filePath);
                }
                model.addAttribute("fileId", files.get(0).getId());
                model.addAttribute("project", project);
                return  "bss/ppms/sall_tender/before_download";
            }
        }
        return "error不能下载";
    }
    
    /**
     * 
     *〈去重〉
     *〈详细描述〉
     * @author FengTian
     * @param list
     */
    public void removeSaleTender(List<SaleTender> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getSupplierId().equals(list.get(i).getSupplierId())) {
                    list.remove(j);
                }
            }
        }
    }
}
