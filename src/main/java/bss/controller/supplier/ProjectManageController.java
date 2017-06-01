package bss.controller.supplier;

import java.io.IOException;
import java.math.BigDecimal;
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


import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;

import common.annotation.CurrentUser;
import common.constant.Constant;
import common.model.UploadFile;
import common.service.DownloadService;
import common.service.UploadService;

import bss.model.ppms.AduitQuota;
import bss.model.ppms.FirstAuditQuota;
import bss.model.ppms.MarkTerm;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.SaleTender;
import bss.model.ppms.ScoreModel;
import bss.model.prms.FirstAudit;
import bss.model.prms.PackageFirstAudit;
import bss.service.ppms.AduitQuotaService;
import bss.service.ppms.FirstAuditQuotaService;
import bss.service.ppms.MarkTermService;
import bss.service.ppms.PackageService;
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
    private ScoreModelService scoreModelService;
    
    @Autowired
    private FirstAuditService firstAuditService;
    
    @Autowired
    private PackageFirstAuditService packageFirstAuditService;
    
    @Autowired
    private MarkTermService markTermService;
    
    @Autowired
    private PackageService packageService;
    
    @Autowired
    private AduitQuotaService aduitQuotaService;
    
    @Autowired
    private FirstAuditQuotaService firstAuditQuotaService;
    
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
    public String bidIndex(@CurrentUser User user,String projectId){
     // 单一来源：在线？未答复
     // 资格预审公告：1编制标书2.绑定指标3.完成生成文档      暂时未作
        if(user != null && user.getTypeId() != null){
           if(user.getTypeId() != null){
             List<String> getList= saleTenderService.getBidFinish(projectId,user.getTypeId());
              if(getList != null && getList.size()>0){
                String bidFinish=getList.get(0);
                //根据状态判断进入不同的业务
              	 if ("0".equals(bidFinish)) {
                       //进入开标一览表
                       return "redirect:/mulQuo/openBid.html?projectId="+projectId;
                   } else if ("1".equals(bidFinish)) {
                       //进入价格构成表
                       return "redirect:/mulQuo/priceBuild.html?projectId="+projectId;
                   } else if ("2".equals(bidFinish)) {
                       //进入明细表
                       return "redirect:/mulQuo/priceView.html?projectId="+projectId;
                   } else if ("3".equals(bidFinish)) {
                       //进入编制标书
                       return "redirect:toBindingIndex.html?projectId="+projectId;
                   } else if ("4".equals(bidFinish)) {
                       //进入绑定指标页面
                     return "redirect:toBindingIndex.html?projectId="+projectId;
                   }else if("5".equals(bidFinish)){
                     //完成
                     return "redirect:result.html?projectId="+projectId;
                   }
                 /* if (std.getBidFinish() == 0) {
                      //未保存标书，进入编辑标书页面
                      return "redirect:bidDocument.html?projectId="+projectId;
                  } else if (std.getBidFinish() == 1) {
                      //标书制作完成，进入绑定指标页面
                      return "redirect:toBindingIndex.html?projectId="+projectId;
                  } else if (std.getBidFinish() == 2) {
                      //指标绑定完成，进入报价页面
                      return "redirect:/mulQuo/list.html?projectId="+projectId;
                  } else if (std.getBidFinish() == 3) {
                      //报价完成，进入完成页面
                      return "redirect:result.html?projectId="+projectId;
                  } else if (std.getBidFinish() == 4) {
                      //投标完成，进入结果查看页面
                      return "redirect:result.html?projectId="+projectId;
                  }*/
              }        
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
        SaleTender std = getProSupplier(request, projectId, model);
        if (std != null) {
            String businessId = std.getId();
            model.addAttribute("std", std);
            //判断是否上传投标文件
            String typeId = DictionaryDataUtil.getId("SUPPLIER_BID");
            List<UploadFile> files = uploadService.getFilesOther(businessId, typeId, Constant.TENDER_SYS_KEY+"");
            if (files != null && files.size() > 0){
                model.addAttribute("fileId", files.get(0).getId());
            } else {
                model.addAttribute("fileId", "0");
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
     * @throws Exception 
     */
    @RequestMapping("/toBindingIndex")
    public String toBindingIndex(HttpServletRequest request, String projectId, Model model) throws Exception{
        SaleTender std = getProSupplier(request, projectId, model);
        if (std != null) {
            model.addAttribute("std", std);
        }
        String typeId = DictionaryDataUtil.getId("SUPPLIER_BID");
        List<UploadFile> files = uploadService.getFilesOther(std.getId(), typeId, Constant.TENDER_SYS_KEY+"");
        if (files != null && files.size() > 0){
            model.addAttribute("fileId", files.get(0).getId());
        } else {
            model.addAttribute("fileId", "0");
        }
        //获取评审项
        getBinding(request, std, projectId, model);
        Project project = projectService.selectById(projectId);
        model.addAttribute("project", project);
        return "bss/supplier/bid/binding_index";
    }
    
    /**
     *〈简述〉获取初审项以及评审细则
     *〈详细描述〉
     * @author Ye MaoLin
     * @param saleTender 供应商与项目关联对象
     * @param projectId 项目id
     * @param model
     * @throws Exception 
     */
    private void getBinding(HttpServletRequest req, SaleTender saleTender, String projectId, Model model) throws Exception {
        //初审项
        List<FirstAudit> firstAudits = firstAuditService.getListByProjectId(projectId);
        for (FirstAudit firstAudit : firstAudits) {
            //set每个初审项的page，用于控制绑定页面的回显
            FirstAuditQuota faq = new FirstAuditQuota();
            faq.setProjectId(projectId);
            faq.setPackFirstId(firstAudit.getId());
            Supplier supplier = (Supplier)req.getSession().getAttribute("loginSupplier");
            faq.setSupplierId(supplier.getId());
            List<FirstAuditQuota> faqs = firstAuditQuotaService.find(faq);
            if (faqs != null && faqs.size() > 0) {
                firstAudit.setPage(faqs.get(0).getPage());
            }
        }
        String[] packageIds = saleTender.getPackages().split(",");
        Map<String, Object> map =new HashMap<String, Object>();
        map.put("packageIds", packageIds);
        map.put("projectId", projectId);
        List<PackageFirstAudit> packageFirstAudits = packageFirstAuditService.findByProAndPackage(map);
        for (PackageFirstAudit packageFirstAudit : packageFirstAudits) {
            String firstAuditId = packageFirstAudit.getFirstAuditId();
            FirstAudit firstAudit = firstAuditService.get(firstAuditId);
            packageFirstAudit.setFirstAuditName(firstAudit.getName());
            packageFirstAudit.setFirstAuditKind(firstAudit.getKind());
            //回显该供应商当前项目包下初审项的值
            FirstAuditQuota faq = new FirstAuditQuota();
            faq.setPackageId(packageFirstAudit.getPackageId());
            faq.setProjectId(projectId);
            faq.setPackFirstId(packageFirstAudit.getFirstAuditId());
            Supplier supplier = (Supplier)req.getSession().getAttribute("loginSupplier");
            faq.setSupplierId(supplier.getId());
            List<FirstAuditQuota> faqs = firstAuditQuotaService.find(faq);
            if (faqs != null && faqs.size() > 0) {
                
                packageFirstAudit.setIs_pass(faqs.get(0).getValue());
            }
        }
        //评审模型关联
        List<ScoreModel> scoreModels = new ArrayList<ScoreModel>();
        ScoreModel scoreModel = new ScoreModel();
        scoreModel.setProjectId(projectId);
        for (String packageId : packageIds) {
            scoreModel.setPackageId(packageId);
            List<ScoreModel> sms = scoreModelService.findListByScoreModel(scoreModel);
            scoreModels.addAll(sms);
        }
        for (ScoreModel scoreModel2 : scoreModels) {
            MarkTerm markTerm = new MarkTerm();
            markTerm.setId(scoreModel2.getMarkTermId());
            List<MarkTerm> markTerms = markTermService.findListByMarkTerm(markTerm);
            if (markTerms != null && markTerms.size() > 0) {
                scoreModel2.setMarkTerm(markTerms.get(0));
            }
            //用于回显供应商在当前包下详细评审项的值
            AduitQuota aq = new AduitQuota();
            aq.setPackageId(scoreModel2.getPackageId());
            aq.setProjectId(projectId);
            aq.setScoreModelId(scoreModel2.getId());
            Supplier supplier = (Supplier)req.getSession().getAttribute("loginSupplier");
            aq.setSupplierId(supplier.getId());
            List<AduitQuota> aqs = aduitQuotaService.find(aq);
            if (aqs != null && aqs.size() > 0) {
                scoreModel2.setValue(aqs.get(0).getSupplierValue());
                scoreModel2.setPage(aqs.get(0).getPage());
            }
        }
        List<Packages> packages = new ArrayList<Packages>();
        for (String packageId : packageIds) {
            HashMap<String, Object> paMap = new HashMap<String, Object>();
            paMap.put("id", packageId);
            List<Packages> pg = packageService.findPackageById(paMap);
            if (pg != null && pg.size() > 0) {
                packages.add(pg.get(0));
            }
        }
        //判断供应商是否保存指标值
        AduitQuota aq = new AduitQuota();
        aq.setProjectId(projectId);
        Supplier supplier = (Supplier)req.getSession().getAttribute("loginSupplier");
        aq.setSupplierId(supplier.getId());
        List<AduitQuota> aqs = aduitQuotaService.find(aq);
        if (aqs != null && aqs.size() > 0) {
            model.addAttribute("saveFirst", "ok");
        } else {
            model.addAttribute("saveFirst", "no");
        }
        
        model.addAttribute("packages", packages);
        model.addAttribute("scoreModels", scoreModels);
        model.addAttribute("packageFirstAudits", packageFirstAudits);
        model.addAttribute("firstAudits", firstAudits);
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
    public void saveBidFile(HttpServletRequest req, String projectId, Model model, String kind) throws IOException{
        String result = "保存失败";
        SaleTender std = getProSupplier(req, projectId, model);
        if (std != null) {
            String businessId = std.getId();
            //判断该项目是否上传过招标文件
            String typeId = DictionaryDataUtil.getId("SUPPLIER_BID");
            List<UploadFile> files = uploadService.getFilesOther(businessId, typeId, Constant.TENDER_SYS_KEY+"");
            if (files != null && files.size() > 0){
                //删除 ,表中数据假删除
                uploadService.updateFileOther(files.get(0).getId(), Constant.TENDER_SYS_KEY+"");
                result = uploadService.saveOnlineFile(req, businessId, typeId, Constant.TENDER_SYS_KEY+"");
                if (kind == null || "".equals(kind)) {
                    //设置投标状态 表：T_BSS_PPMS_SALE_TENDER 1：投标文件保存服务器完成
                    std.setBidFinish((short)1);
                }
                if ("1".equals(kind)) {
                    //设置投标状态 表：T_BSS_PPMS_SALE_TENDER 2：投标文件绑定指标完成
                    std.setBidFinish((short)2);
                }
                saleTenderService.update(std);
            } else {
                result = uploadService.saveOnlineFile(req, businessId, typeId, Constant.TENDER_SYS_KEY+"");
                if (kind == null || "".equals(kind)) {
                    //设置投标状态 表：T_BSS_PPMS_SALE_TENDER 1：投标文件保存服务器完成
                    std.setBidFinish((short)1);
                }
                if ("1".equals(kind)) {
                    //设置投标状态 表：T_BSS_PPMS_SALE_TENDER 2：投标文件绑定指标完成
                    std.setBidFinish((short)2);
                }
                saleTenderService.update(std);
            }
            System.out.println(result);
        }
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
    public void isExistFile(HttpServletResponse response, HttpServletRequest req, String projectId, Model model) throws IOException{
        try {
            String isExist = "1";
            SaleTender std = getProSupplier(req, projectId, model);
            if (std != null) {
                String businessId = std.getId();
                //判断该项目是否上传过招标文件
                String typeId = DictionaryDataUtil.getId("SUPPLIER_BID");
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
    
    /**
     *〈简述〉保存供应商填写的初审项、详细评审项的值
     *〈详细描述〉
     * @author Ye MaoLin
     * @param data1
     * @param data2
     * @param response
     * @param req
     * @throws IOException
     */
    @RequestMapping("/saveIndex")
    @ResponseBody
    public void saveIndex(String data1, String data2, HttpServletResponse response, HttpServletRequest req) throws IOException{
        try {
            if (data1 != null && !"".equals(data1)) {
                //解析data
                String[] dataArr1 = data1.split(",");
                for (String values : dataArr1) {
                    String value = values.substring(1, values.length()-1);
                    String[] v = value.split("_");
                    FirstAuditQuota faq = new FirstAuditQuota();
                    faq.setCreatedAt(new Date());
                    faq.setId(WfUtil.createUUID());
                    faq.setIsDeleted((short)0);
                    faq.setPackageId(v[2]);
                    faq.setProjectId(v[0]);
                    faq.setPackFirstId(v[1]);
                    Supplier supplier = (Supplier)req.getSession().getAttribute("loginSupplier");
                    faq.setSupplierId(supplier.getId());
                    faq.setValue(Integer.parseInt(v[3]));
                    firstAuditQuotaService.save(faq);
                }
            }
            if (data2 != null && !"".equals(data2)) {
                String[] dataArr2 = data2.split(",");
                for (String values : dataArr2) {
                    String value = values.substring(1, values.length()-1);
                    String[] v = value.split("_");
                    AduitQuota aq = new AduitQuota();
                    aq.setCreatedAt(new Date());
                    aq.setId(WfUtil.createUUID());
                    aq.setIsDeleted((short)0);
                    aq.setPackageId(v[2]);
                    aq.setProjectId(v[0]);
                    aq.setRound(0);
                    aq.setScoreModelId(v[1]);
                    Supplier supplier = (Supplier)req.getSession().getAttribute("loginSupplier");
                    aq.setSupplierId(supplier.getId());
                    if ("null".equals(v[3])) {
                        aq.setSupplierValue(null);
                    } else {
                        BigDecimal bd = new BigDecimal(v[3]);
                        aq.setSupplierValue(bd);
                    }
                    aduitQuotaService.save(aq);
                }
            }
            String msg = "保存成功";
            response.setContentType("text/html;charset=utf-8");
            response.getWriter()
                    .print("{\"success\": " + true + ",  \"msg\": \"" + msg
                            + "\"}");
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            response.getWriter().close();
        }
        
    }
    
    /**
     *〈简述〉保存供应商绑定的指标页码
     *〈详细描述〉
     * @author Ye MaoLin
     * @param request
     * @param response
     * @param firstAuditId  初审项id
     * @param smId 详细评审项与模型关联id
     * @param projecId 项目id
     * @param page 指标页码
     * @throws IOException 
     */
    @RequestMapping("/saveBindingIndex")
    @ResponseBody
    public void saveBindingIndex(HttpServletRequest request, HttpServletResponse response, String firstAuditId, String smId, String projectId, Integer page) throws IOException{
        try{ 
            Supplier supplier = (Supplier)request.getSession().getAttribute("loginSupplier");
            if (firstAuditId != null && !"".equals(firstAuditId)) {
                FirstAuditQuota faq = new FirstAuditQuota();
                faq.setProjectId(projectId);
                faq.setPackFirstId(firstAuditId);
                faq.setSupplierId(supplier.getId());
                List<FirstAuditQuota> faqs = firstAuditQuotaService.find(faq);
                //更新该供应商下该项目下该初审项在标书的页码
                for (FirstAuditQuota firstAuditQuota : faqs) {
                    firstAuditQuota.setPage(page);
                    firstAuditQuota.setUpdatedAt(new Date());
                    firstAuditQuotaService.update(firstAuditQuota);
                }
            }
            if (smId != null && !"".equals(smId)) {
                AduitQuota aq = new AduitQuota();
                aq.setProjectId(projectId);
                aq.setScoreModelId(smId);
                aq.setSupplierId(supplier.getId());
                List<AduitQuota> aqs = aduitQuotaService.find(aq);
                //更新该供应商下该项目下该详细评审项在标书的页码
                for (AduitQuota aduitQuota : aqs) {
                    aduitQuota.setPage(page);
                    aduitQuota.setUpdatedAt(new Date());
                    aduitQuotaService.update(aduitQuota);
                }
            }
            String msg = "保存成功";
            response.setContentType("text/html;charset=utf-8");
            response.getWriter()
                    .print("{\"success\": " + true + ",  \"msg\": \"" + msg
                            + "\"}");
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            response.getWriter().close();
        }
    }
    
    /**
     *〈简述〉
     *〈详细描述〉
     * @author Ye MaoLin
     * @param request
     * @param response
     * @param firstAuditId  初审项id
     * @param smId 详细评审项与模型关联id
     * @param projecId 项目id
     * @throws IOException
     */
    @RequestMapping("/deletedBindingIndex")
    @ResponseBody
    public void deletedBindingIndex(HttpServletRequest request, HttpServletResponse response, String firstAuditId, String smId, String projectId) throws IOException{
        try{ 
            Supplier supplier = (Supplier)request.getSession().getAttribute("loginSupplier");
            if (firstAuditId != null && !"".equals(firstAuditId)) {
                FirstAuditQuota faq = new FirstAuditQuota();
                faq.setProjectId(projectId);
                faq.setPackFirstId(firstAuditId);
                faq.setSupplierId(supplier.getId());
                List<FirstAuditQuota> faqs = firstAuditQuotaService.find(faq);
                //更新该供应商下该项目下该初审项在标书的页码为空
                for (FirstAuditQuota firstAuditQuota : faqs) {
                    firstAuditQuota.setPage(null);
                    firstAuditQuota.setUpdatedAt(new Date());
                    firstAuditQuotaService.update(firstAuditQuota);
                }
            }
            if (smId != null && !"".equals(smId)) {
                AduitQuota aq = new AduitQuota();
                aq.setProjectId(projectId);
                aq.setScoreModelId(smId);
                aq.setSupplierId(supplier.getId());
                List<AduitQuota> aqs = aduitQuotaService.find(aq);
                //更新该供应商下该项目下该详细评审项在标书的页码为空
                for (AduitQuota aduitQuota : aqs) {
                    aduitQuota.setPage(null);
                    aduitQuota.setUpdatedAt(new Date());
                    aduitQuotaService.update(aduitQuota);
                }
            }
            String msg = "删除成功";
            response.setContentType("text/html;charset=utf-8");
            response.getWriter()
                    .print("{\"success\": " + true + ",  \"msg\": \"" + msg
                            + "\"}");
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            response.getWriter().close();
        }
    }
    
    /**
     *〈简述〉结果页面
     *〈详细描述〉
     * @author Ye MaoLin
     * @param req
     * @param projectId项目id
     * @param model
     * @return
     */
    @RequestMapping("/result")
    public String result(HttpServletRequest req, String projectId, Model model){
        SaleTender std = getProSupplier(req, projectId, model);
        if (std != null) {
            model.addAttribute("std", std);
        }
        Project project = projectService.selectById(projectId);
        model.addAttribute("project", project);
        return "bss/supplier/bid/result";
    }
    
    /**
     *〈简述〉获取供应商与项目的关联对象
     *〈详细描述〉
     * @author Ye MaoLin
     * @param req
     * @param projectId 项目id
     * @return 供应商与项目的关联对象
     */
    public SaleTender getProSupplier(HttpServletRequest req, String projectId, Model model){
        //供应商与项目的关联的关联作为投标文件的业务id
        SaleTender saleTender = new SaleTender();
        saleTender.setProjectId(projectId);
        Supplier supplier = (Supplier)req.getSession().getAttribute("loginSupplier");
        model.addAttribute("supplier", supplier);
        saleTender.setSupplierId(supplier.getId());
        List<SaleTender> sts = saleTenderService.find(saleTender);
        if (sts != null && sts.size() > 0) {
            SaleTender std = sts.get(0);
            return std;
        } else {
            return null;
        }
    }
    
}
