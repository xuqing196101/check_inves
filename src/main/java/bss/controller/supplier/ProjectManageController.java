package bss.controller.supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import bss.model.ppms.Project;
import bss.service.ppms.ProjectService;

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
    
    
    /**
     * 跳转到编制投标文件页面
     * @author Ye MaoLin
     * @param proejctId
     * @param model
     * @return 页面名称
     */
    @RequestMapping("/bidDocument")
    public String bidDocument(String projectId, Model model){
        Project project = projectService.selectById(projectId);
        model.addAttribute("project", project);
        return "bss/supplier/bid/add_file";
    }
    
    /**
     * 保存投标文件到服务器
     * @author Ye MaoLin
     * @return
     */
    public String saveBidDocument(){
        
        return "bss/supplier/bid/add_file_view";
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
}
