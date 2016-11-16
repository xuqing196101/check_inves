package bss.controller.supplier;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.util.DictionaryDataUtil;

import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;

import bss.model.ppms.Project;
import bss.model.ppms.SaleTender;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SaleTenderService;

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
    
    /**
     * 跳转到编制投标文件页面
     * @author Ye MaoLin
     * @param proejctId
     * @param model
     * @return 页面名称
     */
    @RequestMapping("/bidDocument")
    public String bidDocument(HttpServletRequest request, String projectId, Model model){
        //供应商与项目的关联的关联作为投标文件的业务id
        SaleTender saleTender = new SaleTender();
        saleTender.setProjectId(projectId);
        //当前登录供应商
        Supplier supplier = (Supplier)request.getSession().getAttribute("loginSupplier");
        saleTender.setSupplierId(supplier.getId());
        List<SaleTender> sts = saleTenderService.find(saleTender);
        if (sts != null && sts.size() > 0) {
            String businessId = sts.get(0).getId();
            //判断是否上传投标文件
            String typeId = DictionaryDataUtil.getId("tbwj");
            List<UploadFile> files = uploadService.getFilesOther(businessId, typeId, Constant.TENDER_SYS_KEY+"");
            if (files != null && files.size() > 0){
                model.addAttribute("fileId", files.get(0).getId());
            } else {
                model.addAttribute("fileId", "0");
            }
        } else {
            model.addAttribute("fileId", "0");
        }
        Project project = projectService.selectById(projectId);
        model.addAttribute("project", project);
        return "bss/supplier/bid/add_file";
    }
    
    /**
     * 绑定投标文件中的各项指标
     * @author Ye MaoLin
     * @return
     */
    @RequestMapping("/toBindingIndex")
    public String toBindingIndex(String projectId, Model model){
        Project project = projectService.selectById(projectId);
        model.addAttribute("project", project);
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
            String businessId = sts.get(0).getId();
            //判断该项目是否上传过招标文件
            String typeId = DictionaryDataUtil.getId("tbwj");
            List<UploadFile> files = uploadService.getFilesOther(businessId, typeId, Constant.TENDER_SYS_KEY+"");
            if (files != null && files.size() > 0){
                //删除 ,表中数据假删除
                uploadService.updateFileOther(files.get(0).getId(), Constant.TENDER_SYS_KEY+"");
                result = uploadService.saveOnlineFile(req, businessId, typeId, Constant.TENDER_SYS_KEY+"");
            } else {
                result = uploadService.saveOnlineFile(req, businessId, typeId, Constant.TENDER_SYS_KEY+"");
            }
        }
        System.out.println(result);
    }
}
