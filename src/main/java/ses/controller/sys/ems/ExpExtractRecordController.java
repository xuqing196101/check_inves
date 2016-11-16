/**
 * 
 */
package ses.controller.sys.ems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;




















import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.Area;
import ses.model.bms.User;
import ses.model.ems.ExpExtCondition;
import ses.model.ems.ExpExtractRecord;
import ses.model.ems.Expert;
import ses.model.ems.ExtConType;
import ses.model.ems.ProExtSupervise;
import ses.model.ems.ProjectExtract;
import ses.service.bms.AreaServiceI;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpExtConditionService;
import ses.service.ems.ExpExtractRecordService;
import ses.service.ems.ExpertService;
import ses.service.ems.ExtConTypeService;
import ses.service.ems.ProjectExtractService;
import ses.service.ems.ProjectSupervisorServicel;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import bss.controller.base.BaseController;
import bss.model.ppms.Project;
import bss.service.ppms.ProjectService;

/**
 * @Description:专家抽取
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月27日下午4:34:37
 * @since  JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/ExpExtract")
public class ExpExtractRecordController extends BaseController {
    @Autowired
    ProjectService projectService;
    @Autowired
    AreaServiceI areaService;
    @Autowired
    ExpExtConditionService conditionService;
    @Autowired
    ExtConTypeService conTypeService;
    @Autowired
    ExpertService ExpertService;//专家管理
    @Autowired
    ProjectExtractService extractService; //关联表
    @Autowired
    ExpExtractRecordService expExtractRecordService;
    @Autowired
    ProjectSupervisorServicel projectSupervisorServicel;
    @Autowired
    UserServiceI userServiceI;//用户管理
    /**
     * @Description:	获取项目集合
     *
     * @author Wang Wenshuai
     * @version 2016年9月27日 下午4:38:31  
     * @param @param page
     * @param @param model
     * @param @param project
     * @param @return      
     * @return String
     */
    @RequestMapping("/projectlist")
    public String list(Integer page,Model model,Project project){
        List<Project> list = projectService.list(page==null?1:page, project);
        PageInfo<Project> info = new PageInfo<>(list);
        model.addAttribute("info", info);
        return "ses/ems/exam/expert/extract/project_list";
    }
    /**
     * @Description:条件查询集合
     *
     * @author Wang Wenshuai
     * @version 2016年9月27日 下午6:03:40  
     * @param @param id
     * @param @return      
     * @return String
     */
    @RequestMapping("/Extraction")	
    public String listExtraction(Model model,String projectId,String page,String typeclassId){
        model.addAttribute("typeclassId",typeclassId);
        if (projectId != null && !"".equals(projectId)){
            List<ExpExtCondition> list = conditionService.list(new ExpExtCondition(projectId), page == null || "".equals(page)  ? 1 : Integer.valueOf(page));
            model.addAttribute("list", new PageInfo<>(list));
            Project selectById = projectService.selectById(projectId); 
            model.addAttribute("projectId", projectId);
            model.addAttribute("projectName", selectById.getName());
            model.addAttribute("projectNumber", selectById.getProjectNumber());
            model.addAttribute("expExtCondition", new ExpExtCondition());
        }
        return "ses/ems/exam/expert/extract/condition_list";
    }
    /**
     * @Description:添加查询条件
     *
     * @author Wang Wenshuai
     * @version 2016年9月27日 下午6:04:26  
     * @param @param id
     * @param @return      
     * @return String
     */
    @RequestMapping("/addExtraction")
    public String addExtraction(Model model,String projectId,String projectName, String projectNumber,String typeclassId){
        model.addAttribute("typeclassId", typeclassId);
        List<Area> listArea = areaService.findTreeByPid("1",null);
        model.addAttribute("listArea", listArea);
        model.addAttribute("projectId",projectId);
        if(projectId != null && !"".equals(projectId)){
        //获取监督人员
        List<User>  listUser = projectSupervisorServicel.list(new ProExtSupervise(projectId));
        model.addAttribute("listUser", listUser);
        String userName="";
        String userId="";
        if(listUser!=null &&listUser.size()!=0){
            for (User user : listUser) {
                if(user!=null&&user.getId()!=null){
                    userName += user.getLoginName()+",";
                    userId += user.getId()+",";
                }
            }
        }
        
        //专家抽取地址
        ExpExtractRecord er = new ExpExtractRecord();
        er.setProjectId(projectId);
        List<ExpExtractRecord> listRe = expExtractRecordService.listExtractRecord(er,0);
        if (listRe != null && listRe.size() != 0){
            model.addAttribute("extractionSites", listRe.get(0).getExtractionSites());
        }
        model.addAttribute("userName", userName);
        model.addAttribute("userId", userId);
        }else{
            //后台数据校验
            int count=0;
            if(projectName == null || "".equals(projectName)){
                model.addAttribute("projectNameError", "项目名称不能为空");
                count=1;
            }else{
                model.addAttribute("projectName", projectName);
                model.addAttribute("projectNameError", "");
            }
            if(projectNumber == null || "".equals(projectNumber)){
                model.addAttribute("projectNumberError", "项目编号不能为空");
                count=1;
            }else{
                model.addAttribute("projectNumber", projectNumber);
                model.addAttribute("projectNameError", "");
            }
            if(count==1){
                return "ses/ems/exam/expert/extract/condition_list";
            }else{
//                创建一个临时项目
                Project project = new Project();
                project.setProjectNumber(projectNumber);
                project.setName(projectName);
                project.setIsProvisional(1);
                projectService.add(project);
                model.addAttribute("projectId", project.getId());
            }
        
        }
        return "ses/ems/exam/expert/extract/add_condition";
    }
    /**
     * @Description:选择品目
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 上午9:48:28  
     * @param @return      
     * @return String
     */
    @RequestMapping("/addHeading")
    public String addHeading(Model model, String[] id){
        ExtConType extConType=null;
        if(id!=null&&id.length!=0){
            extConType=new ExtConType();
            extConType.setCategoryId(id[0]);
            extConType.setExpertsTypeId(new Short(id[1]));
            extConType.setExpertsCount(Integer.parseInt(id[2]));
            extConType.setExpertsQualification(id[3]);
        }
        model.addAttribute("extConType", extConType);
        return "ses/ems/exam/expert/extract/product";
    }
    /**
     * @Description: 条件抽取专家
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午3:12:34  
     * @param @return      
     * @return String
     */
    @RequestMapping("/extractCondition")
    public String extractCondition(HttpServletRequest sq, Model model,String cId){
        Map<String, Integer> mapcount = new HashMap<String, Integer>();
        User user=(User) sq.getSession().getAttribute("loginUser");
        List<ProjectExtract> list = extractService.list(new ProjectExtract(cId));
        if (list == null || list.size() == 0){
            extractService.insert(cId, user != null && !"".equals(user.getId()) ? user.getId() : "");
            conditionService.update(new ExpExtCondition(cId, (short)2));
            list = extractService.list(new ProjectExtract(cId));
        }
        //已操作的
        List<ProjectExtract> projectExtractListYes = new ArrayList<ProjectExtract>();
        //未操作的
        List<ProjectExtract> projectExtractListNo = new ArrayList<ProjectExtract>();
        for (ProjectExtract projectExtract : list) {
            if (projectExtract.getOperatingType() != null && (projectExtract.getOperatingType() ==1 || projectExtract.getOperatingType() == 2)){
                projectExtractListYes.add(projectExtract);
                Integer conTypeId = mapcount.get(projectExtract.getConTypeId());
                if (conTypeId != null && conTypeId != 0){
                    mapcount.put(projectExtract.getConTypeId(), conTypeId+=1);
                } else {
                    mapcount.put(projectExtract.getConTypeId(), 1);
                }
            } else if (projectExtract.getOperatingType() != null && projectExtract.getOperatingType() == 3){
                projectExtractListYes.add(projectExtract);
            } else {
                projectExtractListNo.add(projectExtract);
            }
        }
        //获取查询条件类型
        List<ExpExtCondition> listCondition = conditionService.list(new ExpExtCondition(cId, ""),null);
        List<ExtConType> conTypes = listCondition.get(0).getConTypes();
        for (ExtConType extConType : conTypes) {
            extConType.setAlreadyCount(mapcount.get(extConType.getId()) == null ? 0 : mapcount.get(extConType.getId()));
        }
        model.addAttribute("extConType", conTypes);

        if (projectExtractListNo.size() != 0){
            projectExtractListYes.add(projectExtractListNo.get(0));
            projectExtractListNo.remove(0);
        }
        model.addAttribute("extRelateListYes", projectExtractListYes);
        model.addAttribute("extRelateListNo", projectExtractListNo);
        return "ses/ems/exam/expert/extract/resultlist";
    }

    /**
     * @Description:返回结果
     *
     * @author Wang Wenshuai
     * @date 2016年9月19日 下午2:31:46  
     * @param @param model
     * @param @return      
     * @return String
     */
    @ResponseBody
    @RequestMapping("/resultextract")
    public Object resultextract(Model model,String id,String reason,HttpServletRequest sq){
        //		修改状态
        String[] ids = id.split(",");
        if (reason != null && !"".equals(reason)){
            extractService.update(new ProjectExtract(ids[0], new Short(ids[2]), reason));
        } else {
            extractService.update(new ProjectExtract(ids[0], new Short(ids[2])));
        }

        List<ProjectExtract> projectExtractListYes = resultProjectExtract(sq, ids);

        return projectExtractListYes;
    }

    /**
     *〈简述〉返回专家抽取方法
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param sq HttpServletRequest
     * @param ids 集合  [1]关联表id [2]条件表id [3]参与不参与
     * @return List<ProjectExtract>
     */
    private List<ProjectExtract> resultProjectExtract(HttpServletRequest sq, String[] ids) {
        //存放已抽取的数量
        Map<String, Integer> mapcount = new HashMap<String, Integer>();
        //存放已操作
        List<ProjectExtract> projectExtractListYes = new ArrayList<ProjectExtract>();
        //未操作
        List<ProjectExtract> projectExtractListNo = new ArrayList<ProjectExtract>();
        //循环出抽取未抽取的
        forExtract(mapcount, ids[1], projectExtractListYes, projectExtractListNo, 0);
        //拿出数量和session中存放的数字进行对比
        ProjectExtract pe = new ProjectExtract();
        pe.setId(ids[0]);
        List<ProjectExtract> list2 = extractService.list(pe);
        ExtConType extConType = conTypeService.getExtConType(list2.get(0).getConTypeId());
        Integer count = mapcount.get(extConType.getId());
        if (count != null && count != 0){
            if (count >= extConType.getExpertsCount()){
                extractService.updateStatusCount("1",extConType.getId());
                forExtract(mapcount, ids[1], projectExtractListYes, projectExtractListNo,1);
            }else{
                forExtract(mapcount, ids[1], projectExtractListYes, projectExtractListNo,1);
            }
        }
        //获取查询条件类型
        List<ExpExtCondition> listCondition = conditionService.list(new ExpExtCondition(ids[1], ""),null);
        List<ExtConType> conTypes = listCondition.get(0).getConTypes();
        for (ExtConType extConType1 : conTypes) {
            extConType1.setAlreadyCount(mapcount.get(extConType1.getId()) == null ? 0 : mapcount.get(extConType1.getId()));
        }
        projectExtractListYes.get(0).setConType(conTypes);
        if (projectExtractListNo.size() != 0){
            projectExtractListYes.add(projectExtractListNo.get(0));
        }
        return projectExtractListYes;
    }
    /**
     * 
     *〈简述〉循环出抽取未抽取的
     *〈详细描述〉
     * @author yggc
     * @param mapcount
     * @param expertConditionId
     * @param projectExtractListYes
     * @param projectExtractListNo
     */
    private void forExtract(Map<String, Integer> mapcount, String expertConditionId,
                            List<ProjectExtract> projectExtractListYes,
                            List<ProjectExtract> projectExtractListNo,Integer type) {
        //每次进入方法清除数据
        projectExtractListYes.clear();
        projectExtractListNo.clear();
        //查询数据
        List<ProjectExtract> list = extractService.list(new ProjectExtract(expertConditionId));
        for (ProjectExtract projectExtract : list) {
            if (projectExtract.getOperatingType() != null && (projectExtract.getOperatingType()==1 || projectExtract.getOperatingType() == 2)){
                projectExtractListYes.add(projectExtract);
                if (type == 0){
                    Integer conTypeId = mapcount.get(projectExtract.getConTypeId());
                    if (conTypeId != null && conTypeId != 0){
                        mapcount.put(projectExtract.getConTypeId(), conTypeId += 1);
                    } else {
                        mapcount.put(projectExtract.getConTypeId(), 1);
                    }
                }
            } else if (projectExtract != null && projectExtract.getOperatingType() == 3){
                //不参与
                projectExtractListYes.add(projectExtract);
                if (type == 0){
                    ExtConType extConType = conTypeService.getExtConType(projectExtract.getConTypeId());
                    extractService.updateStatusCount("0",extConType.getId());
                }
            } else {
                projectExtractListNo.add(projectExtract);
            }
        }
    }	

    /**
     * @Description:专家抽取记录集合
     *
     * @author Wang Wenshuai
     * @version 2016年9月29日 下午2:11:25  
     * @param @param model
     * @param @return      
     * @return String
     */
    @RequestMapping("/resuleRecordlist")
    public String resuleRecord(Model model,ExpExtractRecord expExtractRecord,String page){
        List<ExpExtractRecord> listExtractRecord = expExtractRecordService.listExtractRecord(expExtractRecord,page!=null&&!page.equals("")?Integer.parseInt(page):1);
        model.addAttribute("listExtractRecord", new PageInfo<ExpExtractRecord>(listExtractRecord));
        model.addAttribute("expExtractRecord", expExtractRecord);
        return "ses/ems/exam/expert/extract/recordlist";
    }

    /**
     * @Description:抽取记录
     *
     * @author Wang Wenshuai
     * @version 2016年10月14日 下午7:29:36  
     * @param @param model
     * @param @param id
     * @param @return      
     * @return String
     */
    @RequestMapping("/showRecord")
    public String showRecord(Model model,String id){
        //获取抽取记录
        ExpExtractRecord showExpExtractRecord = expExtractRecordService.listExtractRecord(new ExpExtractRecord(id),0).get(0);
        model.addAttribute("ExpExtractRecord", showExpExtractRecord);
        //抽取条件
        List<ExpExtCondition> conditionList = conditionService.list(new ExpExtCondition(showExpExtractRecord.getProjectId()),null);

        model.addAttribute("conditionList", conditionList);
        List<List<ProjectExtract>> listEp=new ArrayList<List<ProjectExtract>>();
        //获取专家人数
        for (ExpExtCondition expExtCondition : conditionList) {
            ProjectExtract pExtract = new ProjectExtract();
            pExtract.setProjectId(showExpExtractRecord.getProjectId());
            pExtract.setExpertConditionId(expExtCondition.getId());
            //占用字段保存状态类型
            pExtract.setReason("1,2,3");
            List<ProjectExtract> projectExtract = extractService.list(pExtract); 
            listEp.add(projectExtract);
        }
        model.addAttribute("ProjectExtract", listEp);
        //获取监督人员
        if (conditionList != null && conditionList.size() != 0){
            List<User>  listUser = projectSupervisorServicel.list(new ProExtSupervise(conditionList.get(0).getProjectId()));
            model.addAttribute("listUser", listUser);  
        }
        return "ses/ems/exam/expert/extract/show_info";
    }

    /**
     * @Description:重置密码
     *
     * @author Wang Wenshuai
     * @version 2016年10月14日 下午7:29:36  
     * @param model  实体
     * @param  id 专家id
     */
    @ResponseBody
    @RequestMapping("/resetPwd")
    public String resetPwd(Model model, String eid){
        User user = new User();
        user.setTypeId(eid);
        user.setTypeName(5);
        List<User> queryByList = userServiceI.queryByList(user);
        if (queryByList != null && queryByList.size() != 0){
            user = queryByList.get(0);
            //根据随机码+密码加密
            Md5PasswordEncoder md5 = new Md5PasswordEncoder();     
            // false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true  表示：生成24位的Base64版     
            md5.setEncodeHashAsBase64(false);     
            String pwd = md5.encodePassword("123456", user.getRandomCode());
            String userId = user.getId();
            user = new User();
            user.setPassword(pwd);
            user.setId(userId);
            userServiceI.update(user);
            return JSON.toJSONString("sccuess");
        }else{
            return JSON.toJSONString("error");
        }
    }
    /**
     * @Description:展示添加临时专家页面
     *
     * @author Wang Wenshuai
     * @version 2016年10月14日 下午7:29:36  
     * @param model  实体
     * @param  id 专家id
     */
    @RequestMapping("/showTemporaryExpert")
    public  String showTemporaryExpert(Model model,String projectId){
        model.addAttribute("projectId", projectId);
        return "bss/prms/temporary_expert_add";
    }

    /**
     * @Description:添加临时专家
     *
     * @author Wang Wenshuai
     * @version 2016年10月14日 下午7:29:36  
     * @param model  实体
     * @param  id 专家id
     */
    @RequestMapping("/AddtemporaryExpert")
    public  Object addTemporaryExpert(Model model, Expert expert, String projectId, String loginName, String loginPwd){
        expExtractRecordService.addTemporaryExpert(expert, projectId, loginName, loginPwd);
        return "redirect:/packageExpert/toPackageExpert.html?projectId=" + projectId;
    }
}
