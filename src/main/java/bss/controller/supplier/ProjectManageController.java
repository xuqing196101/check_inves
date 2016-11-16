package bss.controller.supplier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

import ses.model.sms.Quote;
import ses.model.sms.Supplier;
import ses.service.sms.SupplierQuoteService;
import ses.util.DictionaryDataUtil;

import common.constant.Constant;
import common.model.UploadFile;
import common.service.DownloadService;
import common.service.UploadService;

import bss.model.ppms.MarkTerm;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.SaleTender;
import bss.model.ppms.ScoreModel;
import bss.model.prms.FirstAudit;
import bss.model.prms.PackageFirstAudit;
import bss.service.ppms.MarkTermService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SaleTenderService;
import bss.service.ppms.ScoreModelService;
import bss.service.prms.FirstAuditService;
import bss.service.prms.PackageFirstAuditService;

/**
 * 版权：(C) 版权所有 
 * 供应商项目实施过程管理
 * @author   Ye MaoLin
 * @version  
 * @since
 * @see
 */
@Controller
@RequestMapping("/supplierProject")
public class ProjectManageController {
    /**
     * @Fields projectService : 引用项目业务实现接口
     */
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private UploadService uploadService;
    
    @Autowired
    private SaleTenderService saleTenderService;
    
    @Autowired
    private DownloadService downloadService;
    
    @Autowired
    private SupplierQuoteService supplierQuoteService;
    
    @Autowired
    private ProjectDetailService detailService;
    
    @Autowired
    private ScoreModelService scoreModelService;
    
    @Autowired
    private FirstAuditService firstAuditService;
    
    @Autowired
    private PackageFirstAuditService packageFirstAuditService;
    
    @Autowired
    private MarkTermService markTermService;
    
    /**
     *〈简述〉投标管理进入
     *〈详细描述〉
     * @author Ye MaoLin
     * @param request
     * @param projectId项目id
     * @param model
     * @return 进入页面
     */
    @RequestMapping("/bidIndex")
    public String bidIndex(HttpServletRequest request, String projectId, Model model){
        Project project = projectService.selectById(projectId);
        model.addAttribute("project", project);
        //供应商与项目的关联的关联作为投标文件的业务id
        SaleTender saleTender = new SaleTender();
        saleTender.setProjectId(projectId);
        //当前登录供应商
        Supplier supplier = (Supplier)request.getSession().getAttribute("loginSupplier");
        model.addAttribute("supplier", supplier);
        saleTender.setSupplierId(supplier.getId());
        List<SaleTender> sts = saleTenderService.find(saleTender);
        if (sts != null && sts.size() > 0) {
            SaleTender std = sts.get(0);
            model.addAttribute("std", std);
            String businessId = std.getId();
            if (std.getBidFinish() == 0) {
                model.addAttribute("fileId", "0");
                //未保存标书，进入编辑标书页面
                return "bss/supplier/bid/add_file";
            } else if (std.getBidFinish() == 1) {
                //标书制作完成，进入绑定指标页面
                return "bss/supplier/bid/binding_index";
            } else if (std.getBidFinish() == 2) {
                //指标绑定完成，进入报价页面
                return null;
            } else if (std.getBidFinish() == 3) {
                //报价完成，进入完成页面
                return "bss/supplier/bid/result";
            } else if (std.getBidFinish() == 4) {
                //投标完成，进入结果查看页面
                return "bss/supplier/bid/result";
            }
        }
        return "bss/supplier/bid/add_file";
    }
    
    /**
     * 跳转到编制投标文件页面
     * @author Ye MaoLin
     * @param proejctId
     * @param model
     * @return 页面名称
     */
    @RequestMapping("/bidDocument")
    public String bidDocument(HttpServletRequest request, String projectId, Model model){
        Project project = projectService.selectById(projectId);
        model.addAttribute("project", project);
        //供应商与项目的关联的关联作为投标文件的业务id
        SaleTender saleTender = new SaleTender();
        saleTender.setProjectId(projectId);
        //当前登录供应商
        Supplier supplier = (Supplier)request.getSession().getAttribute("loginSupplier");
        model.addAttribute("supplier", supplier);
        saleTender.setSupplierId(supplier.getId());
        List<SaleTender> sts = saleTenderService.find(saleTender);
        if (sts != null && sts.size() > 0) {
            SaleTender std = sts.get(0);
            String businessId = std.getId();
            //判断是否上传投标文件
            String typeId = DictionaryDataUtil.getId("tbwj");
            List<UploadFile> files = uploadService.getFilesOther(businessId, typeId, Constant.TENDER_SYS_KEY+"");
            if (std.getBidFinish() == 0) {
                if (files != null && files.size() > 0){
                    model.addAttribute("fileId", std.getId());
                } else {
                    model.addAttribute("fileId", "0");
                }
                //编辑状态
                model.addAttribute("status", 0);
            } else {
                //进入到查看状态
                model.addAttribute("status", 1);
            }
        } else {
            model.addAttribute("fileId", "0");
        }
        return "bss/supplier/bid/add_file";
    }
    
   /**
     *〈简述〉下载文件
     *〈详细描述〉
     * @author Ye MaoLin
     * @param request
     * @param fileId
     * @param response
     */
   @RequestMapping("/loadFile")
   public void loadFile(HttpServletRequest request, String fileId, HttpServletResponse response){
       downloadService.downloadOther(request, response, fileId, Constant.TENDER_SYS_KEY+"");
   }
    
    /**
     *〈简述〉跳转到绑定投标文件中的各项指标
     *〈详细描述〉
     * @author Ye MaoLin
     * @param projectId
     * @param model
     * @return
     */
    @RequestMapping("/toBindingIndex")
    public String toBindingIndex(HttpServletRequest request, String projectId, Model model){
        //供应商与项目的关联的关联作为投标文件的业务id
        SaleTender saleTender = new SaleTender();
        saleTender.setProjectId(projectId);
        //当前登录供应商
        Supplier supplier = (Supplier)request.getSession().getAttribute("loginSupplier");
        model.addAttribute("supplier", supplier);
        saleTender.setSupplierId(supplier.getId());
        List<SaleTender> sts = saleTenderService.find(saleTender);
        if (sts != null && sts.size() > 0) {
            model.addAttribute("std", sts.get(0));
        }
        //初审项
        List<FirstAudit> firstAudits = new ArrayList<FirstAudit>();
        Map<String, Object> map =new HashMap<String, Object>();
        //map.put("packageId", packageId);
        map.put("projectId", projectId);
        List<PackageFirstAudit> packageFirstAudits = packageFirstAuditService.selectList(map);
        for (PackageFirstAudit packageFirstAudit : packageFirstAudits) {
            FirstAudit firstAudit = firstAuditService.get(packageFirstAudit.getFirstAuditId());
            firstAudits.add(firstAudit);
        }
        //评审模型关联
        ScoreModel scoreModel = new ScoreModel();
        scoreModel.setProjectId(projectId);
      //  scoreModel.setPackageId(packageId);
        List<ScoreModel> scoreModels = scoreModelService.findListByScoreModel(scoreModel);
        for (ScoreModel scoreModel2 : scoreModels) {
            MarkTerm markTerm = new MarkTerm();
            markTerm.setId(scoreModel2.getMarkTermId());
            List<MarkTerm> markTerms = markTermService.findListByMarkTerm(markTerm);
            if (markTerms != null && markTerms.size() > 0) {
                scoreModel2.setMarkTermName(markTerms.get(0).getName());
            }
        }
        Project project = projectService.selectById(projectId);
        model.addAttribute("project", project);
        model.addAttribute("scoreModels", scoreModels);
        model.addAttribute("firstAudits", firstAudits);
        return "bss/supplier/bid/binding_index";
    }
    
    /**
     *〈简述〉保存供应商投标文件到服务器
     *〈详细描述〉
     * @author Ye MaoLin
     * @param req
     * @param projectId 项目id
     * @throws IOException
     */
    @RequestMapping("/saveBidFile")
    public void saveBidFile(HttpServletRequest req, String projectId, Model model) throws IOException{
        String result = "保存失败";
        //供应商与项目的关联的关联作为投标文件的业务id
        SaleTender saleTender = new SaleTender();
        saleTender.setProjectId(projectId);
        Supplier supplier = (Supplier)req.getSession().getAttribute("loginSupplier");
        saleTender.setSupplierId(supplier.getId());
        List<SaleTender> sts = saleTenderService.find(saleTender);
        if (sts != null && sts.size() > 0) {
            SaleTender std = sts.get(0);
            String businessId = std.getId();
            //判断该项目是否上传过招标文件
            String typeId = DictionaryDataUtil.getId("tbwj");
            List<UploadFile> files = uploadService.getFilesOther(businessId, typeId, Constant.TENDER_SYS_KEY+"");
            if (files != null && files.size() > 0){
                //删除 ,表中数据假删除
                uploadService.updateFileOther(files.get(0).getId(), Constant.TENDER_SYS_KEY+"");
                result = uploadService.saveOnlineFile(req, businessId, typeId, Constant.TENDER_SYS_KEY+"");
            } else {
                result = uploadService.saveOnlineFile(req, businessId, typeId, Constant.TENDER_SYS_KEY+"");
                //设置投标状态 表：T_BSS_PPMS_SALE_TENDER 1：投标文件保存服务器完成
                std.setBidFinish((short)1);
                saleTenderService.update(std);
            }
        }
        System.out.println(result);
    }
    
    /**
     *〈简述〉查看是否上传投标文件
     *〈详细描述〉
     * @author Ye MaoLin
     * @param response
     * @param projectId 项目id
     * @throws IOException 
     */
    @RequestMapping("/isExistFile")
    @ResponseBody
    public void isExistFile(HttpServletResponse response, HttpServletRequest req, String projectId) throws IOException{
        try {
            String isExist = "1";
            //供应商与项目的关联的关联作为投标文件的业务id
            SaleTender saleTender = new SaleTender();
            saleTender.setProjectId(projectId);
            Supplier supplier = (Supplier)req.getSession().getAttribute("loginSupplier");
            saleTender.setSupplierId(supplier.getId());
            List<SaleTender> sts = saleTenderService.find(saleTender);
            if (sts != null && sts.size() > 0) {
                SaleTender std = sts.get(0);
                String businessId = std.getId();
                //判断该项目是否上传过招标文件
                String typeId = DictionaryDataUtil.getId("tbwj");
                List<UploadFile> files = uploadService.getFilesOther(businessId, typeId, Constant.TENDER_SYS_KEY+"");
                //如果该项目没有上传过招标文件
                if (files == null || files.size() <= 0){
                    isExist = "0";
                }
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter()
                    .print("{\"success\": " + true + ",  \"isExist\": \"" + isExist
                            + "\"}");
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            response.getWriter().close();
        }
    }
    
}
