package sums.controller.ss;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;

import common.annotation.CurrentUser;

import bss.model.ppms.Project;
import bss.service.ppms.ProjectService;

/**
 * 
 * 版权：(C) 版权所有 
 * <采购项目监督>
 * <详细描述>
 * @author   FengTian
 * @version  
 * @since
 * @see
 */
@Controller
@Scope("prototype")
@RequestMapping("/projectSupervision")
public class ProjectSupervisionController {
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private OrgnizationServiceI orgnizationService;
    
    @Autowired
    private UserServiceI userService;
    
    /**
     * 
     *〈列表〉
     *〈详细描述〉
     * @author FengTian
     * @param model
     * @param user
     * @param project
     * @param page
     * @return
     */
    @RequestMapping(value="/list",produces = "text/html;charset=UTF-8")
    public String list(Model model, @CurrentUser User user,Project project,Integer page){
        if(user != null && user.getOrg() != null){
            HashMap<String,Object> map = new HashMap<String,Object>();
            if(StringUtils.isNotBlank(project.getName())){
                map.put("name", project.getName());
            }
            if(StringUtils.isNotBlank(project.getProjectNumber())){
                map.put("projectNumber", project.getProjectNumber());
            }
            if(StringUtils.isNotBlank(project.getStatus())){
                map.put("status", project.getStatus());
            }
            if(StringUtils.isNotBlank(project.getPurchaseType())){
                map.put("purchaseType", project.getPurchaseType());
            }
            map.put("purchaseDepId", user.getOrg().getId());
            if(page==null){
                page = 1;
            }
            PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
            List<Project> list = projectService.selectProjectsByConition(map);
            for (int i = 0; i < list.size(); i++ ) {
                try {
                    Orgnization org = orgnizationService.getOrgByPrimaryKey(list.get(i).getPurchaseDepId());
                    User users = userService.getUserById(list.get(i).getAppointMan());
                    list.get(i).setPurchaseDepId(org.getName());
                    list.get(i).setAppointMan(users.getRelName());
                } catch (Exception e) {
                    list.get(i).setPurchaseDepId("");
                    list.get(i).setAppointMan("");
                }
            }
            model.addAttribute("info", new PageInfo<Project>(list));
            model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
            model.addAttribute("status", DictionaryDataUtil.find(2));//获取数据字典数据
            model.addAttribute("project", project);
        }
        return "sums/ss/projectSupervision/list";
    }
}
