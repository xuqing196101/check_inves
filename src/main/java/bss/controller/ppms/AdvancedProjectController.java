package bss.controller.ppms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ses.model.bms.User;
import ses.model.oms.util.CommonConstant;

import common.annotation.CurrentUser;

import bss.controller.base.BaseController;
import bss.model.ppms.AdvancedProject;
import bss.service.ppms.AdvancedProjectService;

@Controller
@Scope("prototype")
@RequestMapping("/advancedProject")
public class AdvancedProjectController extends BaseController {
    @Autowired
    private AdvancedProjectService advancedProjectService;
    
    @RequestMapping("/list")
    public String list(@CurrentUser User user, Model model, AdvancedProject advancedProject, @ModelAttribute PageInfo<AdvancedProject> page){
        //if(user != null && user.getOrg().getId() != null){
            PageHelper.startPage(page.getPageNum(),CommonConstant.PAGE_SIZE);
            List<AdvancedProject> list = advancedProjectService.list(advancedProject);
            model.addAttribute("list", new PageInfo<AdvancedProject>(list));
            model.addAttribute("project", advancedProject);
        //}
        return "bss/ppms/advanced_project/list";
    }
    
}
