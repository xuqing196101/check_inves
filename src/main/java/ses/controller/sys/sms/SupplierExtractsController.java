
package ses.controller.sys.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bss.controller.base.BaseController;
import bss.model.ppms.Project;
import bss.model.ppms.SaleTender;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SaleTenderService;

import com.github.pagehelper.PageInfo;

import ses.model.bms.Area;
import ses.model.bms.User;
import ses.model.ems.ExpExtCondition;
import ses.model.ems.ExtConType;
import ses.model.ems.ProjectExtract;
import ses.model.sms.SupplierConType;
import ses.model.sms.SupplierCondition;
import ses.model.sms.SupplierExtRelate;
import ses.model.sms.SupplierExtUser;
import ses.model.sms.SupplierExtracts;
import ses.service.bms.AreaServiceI;
import ses.service.bms.UserServiceI;
import ses.service.sms.SupplierConTypeService;
import ses.service.sms.SupplierConditionService;
import ses.service.sms.SupplierExtRelateService;
import ses.service.sms.SupplierExtUserServicel;
import ses.service.sms.SupplierExtractsService;

/**
 * @Description:供应商抽取记录
 *
 * @author Wang Wenshuai
 * @date 2016年9月18日下午3:29:12
 * @since  JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/SupplierExtracts")
public class SupplierExtractsController extends BaseController {
    /**
     * 项目
     */
    @Autowired
    private ProjectService projectService; 
    /**
     * 地区
     */
    @Autowired
    private AreaServiceI areaService; 
    @Autowired
    private SupplierConditionService conditionService; //条件
    @Autowired
    private SupplierConTypeService conTypeService; //条件
    @Autowired
    private SupplierExtRelateService extRelateService; //关联表
    @Autowired
    private SupplierExtractsService expExtractRecordService; //记录
    @Autowired
    private SupplierExtUserServicel extUserServicl;
    @Autowired
    private UserServiceI userServicl;
    @Autowired
    private SaleTenderService saleTenderService; //发售标书

    /**
     * @Description:获取项目集合
     *
     * @author Wang Wenshuai
     * @version 2016年9月27日 下午4:38:31  
     * @param  page
     * @param  model
     * @param  project 
     * @return String
     */
    @RequestMapping("/projectList")
    public String list(Integer page, Model model, Project project){
        List<Project> list = projectService.list(page==null?1:page, project);
        PageInfo<Project> info = new PageInfo<>(list);
        model.addAttribute("info", info);
        return "ses/sms/supplier_extracts/project_list";
    }
    /**
     * @Description:条件查询集合
     *
     * @author Wang Wenshuai
     * @version 2016年9月27日 下午6:03:40  
     * @param  id
     * @return String
     */
    @RequestMapping("/Extraction")	
    public String listExtraction(Model model,String id,String pages,String typeclassId){
        model.addAttribute("typeclassId",typeclassId);
        if (id != null && !"".equals(id)){
            List<SupplierCondition> list= conditionService.list(new SupplierCondition(id),pages==null?1:Integer.valueOf(pages));
            model.addAttribute("list", new PageInfo<SupplierCondition>(list));
            Project selectById = projectService.selectById(id);
            if (selectById != null){
                model.addAttribute("projectId", selectById.getId());
                model.addAttribute("projectName", selectById.getName());
                model.addAttribute("projectNumber", selectById.getProjectNumber());
            }
        }
        return "ses/sms/supplier_extracts/condition_list";
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
        List<Area> listArea = areaService.findTreeByPid("1",null);
        model.addAttribute("listArea", listArea);
       model.addAttribute("typeclassId", typeclassId);
        if(projectId != null && !"".equals(projectId)){
            //获取监督人员
            List<User>  listUser=extUserServicl.list(new SupplierExtUser(projectId));
            model.addAttribute("listUser", listUser);
            String userName="";
            String userId="";
            if(listUser!=null&&listUser.size()!=0){
                for (User user : listUser) {
                    userName+=user.getLoginName()+",";
                    userId+=user.getId()+",";
                }
                model.addAttribute("userName", userName.substring(0, userName.length()-1));
                model.addAttribute("userId", userId.substring(0, userId.length()-1));
            }
            model.addAttribute("projectId",projectId);
        }else{
            //后台数据校验
            int count=0;
            if (projectName == null || "".equals(projectName)){
                model.addAttribute("projectNameError", "项目名称不能为空");
                count=1;
            }else{
                model.addAttribute("projectName", projectName);
                model.addAttribute("projectNameError", "");
            }
            if (projectNumber == null || "".equals(projectNumber)){
                model.addAttribute("projectNumberError", "项目编号不能为空");
                count=1;
            }else{
                model.addAttribute("projectNumber", projectNumber);
                model.addAttribute("projectNameError", "");
            }
            if (count == 1){
                return "ses/sms/supplier_extracts/condition_list";
            } else{
//                创建一个临时项目
                Project project = new Project();
                project.setProjectNumber(projectNumber);
                project.setName(projectName);
                project.setIsProvisional(1);
                projectService.add(project);
                model.addAttribute("projectId", project.getId());
            }
          
        }
        return "ses/sms/supplier_extracts/add_condition";
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
        return "ses/sms/supplier_extracts/product";
    }
    /**
     * @Description: 条件抽取供应商
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午3:12:34  
     * @param @return      
     * @return String
     */
    @RequestMapping("/extractCondition")
    public String extractCondition(HttpServletRequest sq, Model model,String cId){
        Map<String, Integer> mapcount = new HashMap<String, Integer>();
        User user = (User) sq.getSession().getAttribute("loginUser");
        List<SupplierExtRelate> list = extRelateService.list(new SupplierExtRelate(cId), "");
        if (list == null || list.size() == 0){
            extRelateService.insert(cId,user != null && !"".equals(user.getId()) ? user.getId() : "");
            conditionService.update(new SupplierCondition(cId,(short)2));
            list = extRelateService.list(new SupplierExtRelate(cId),"");
        }
        //已操作的
        List<SupplierExtRelate> projectExtractListYes=new ArrayList<SupplierExtRelate>();
        //未操作的
        List<SupplierExtRelate> projectExtractListNo=new ArrayList<SupplierExtRelate>();
        for (SupplierExtRelate projectExtract : list) {
            if (projectExtract.getOperatingType() != null && (projectExtract.getOperatingType() ==1 || projectExtract.getOperatingType() == 2)){
                projectExtractListYes.add(projectExtract);
                Integer conTypeId = mapcount.get(projectExtract.getConTypeId());
                if (conTypeId != null && conTypeId != 0){
                    mapcount.put(projectExtract.getConTypeId(), conTypeId+=1);
                } else {
                    mapcount.put(projectExtract.getConTypeId(), 1);
                }
            } else if (projectExtract.getOperatingType() != null&&projectExtract.getOperatingType() == 3){
                projectExtractListYes.add(projectExtract);
            } else {
                projectExtractListNo.add(projectExtract);
            }
        }
        //获取查询条件类型
        List<SupplierCondition> listCondition = conditionService.list(new SupplierCondition(cId, ""),0);
        List<SupplierConType> conTypes = listCondition.get(0).getConTypes();
        for (SupplierConType extConType : conTypes) {
            extConType.setAlreadyCount(mapcount.get(extConType.getId()) == null ? 0 : mapcount.get(extConType.getId()));
        }
        model.addAttribute("extConType", conTypes);

        if (projectExtractListNo.size()!=0){
            projectExtractListYes.add(projectExtractListNo.get(0));
            projectExtractListNo.remove(0);
        }
        model.addAttribute("extRelateListYes",projectExtractListYes);
        model.addAttribute("extRelateListNo", projectExtractListNo);
        return "ses/sms/supplier_extracts/resultlist";
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
        String ids[]=id.split(",");

        if (reason != null && !"".equals(reason)){
            extRelateService.update(new SupplierExtRelate(ids[0],new Short(ids[2]),reason));
        }else{

            extRelateService.update(new SupplierExtRelate(ids[0],new Short(ids[2])));
        }
        if("1".equals(ids[2])){
            SupplierExtRelate supplierExtRelate = extRelateService.getSupplierExtRelate(ids[0]);
            SaleTender saleTender=new SaleTender();
            saleTender.setProjectId(supplierExtRelate.getProjectId());
            saleTender.setSupplierId(supplierExtRelate.getSupplier().getId());
            saleTenderService.insert(saleTender);
        }
        List<SupplierExtRelate> projectExtractListYes = resultProjectExtract(sq, ids);  

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
    private List<SupplierExtRelate> resultProjectExtract(HttpServletRequest sq, String[] ids) {
        //存放已抽取的数量
        Map<String, Integer> mapcount = new HashMap<String, Integer>();
        //存放已操作
        List<SupplierExtRelate> projectExtractListYes=new ArrayList<SupplierExtRelate>();
        //未操作
        List<SupplierExtRelate> projectExtractListNo=new ArrayList<SupplierExtRelate>();
        //循环出抽取未抽取的
        forExtract(mapcount, ids[1], projectExtractListYes, projectExtractListNo, 0);
        //拿出数量和session中存放的数字进行对比
        SupplierExtRelate pe = new SupplierExtRelate();
        pe.setId(ids[0]);
        List<SupplierExtRelate> list2 = extRelateService.list(pe,null);
        SupplierConType extConType = conTypeService.getExtConType(list2.get(0).getConTypeId());
        Integer count = mapcount.get(extConType.getId());
        if (count != null && count != 0){
            if (count >= extConType.getSupplieCount()){
                extRelateService.updateStatusCount("1",extConType.getId());
                forExtract(mapcount, ids[1], projectExtractListYes, projectExtractListNo,1);
            }else{
                forExtract(mapcount, ids[1], projectExtractListYes, projectExtractListNo,1);
            }
        }

        //获取查询条件类型
        List<SupplierCondition> listCondition = conditionService.list(new SupplierCondition(ids[1],""),0);
        List<SupplierConType> conTypes = listCondition.get(0).getConTypes();
        for (SupplierConType extConType1 : conTypes) {
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
                            List<SupplierExtRelate> projectExtractListYes,
                            List<SupplierExtRelate> projectExtractListNo,Integer type) {
        //每次进入方法清除数据
        projectExtractListYes.clear();
        projectExtractListNo.clear();
        //查询数据
        List<SupplierExtRelate> list = extRelateService.list(new SupplierExtRelate(expertConditionId),null);
        for (SupplierExtRelate projectExtract : list) {
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
            } else if (projectExtract.getOperatingType() != null && projectExtract.getOperatingType() == 3){
                //不参与
                projectExtractListYes.add(projectExtract);
                if (type == 0){
                    SupplierConType extConType = conTypeService.getExtConType(projectExtract.getConTypeId());
                    extRelateService.updateStatusCount("0",extConType.getId());
                }
            } else {
                projectExtractListNo.add(projectExtract);
            }
        }
    }



    /**
     * @Description:供应商抽取记录集合
     *
     * @author Wang Wenshuai
     * @version 2016年9月29日 下午2:11:25  
     * @param @param model
     * @param @return      
     * @return String
     */
    @RequestMapping("/resuleRecordlist")
    public String resuleRecord(Model model,SupplierExtracts se,String page){
        List<SupplierExtracts> listExtractRecord = expExtractRecordService.listExtractRecord(se,page!=null&&!page.equals("")?Integer.parseInt(page):1);
        model.addAttribute("extractslist", new PageInfo<SupplierExtracts>(listExtractRecord));
        model.addAttribute("se", se);
        return "ses/sms/supplier_extracts/recordlist";
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
        SupplierExtracts showExpExtractRecord = expExtractRecordService.listExtractRecord(new SupplierExtracts(id),0).get(0);
        model.addAttribute("ExpExtractRecord", showExpExtractRecord);
        //抽取条件
        List<SupplierCondition> conditionList = conditionService.list(new SupplierCondition(showExpExtractRecord.getProjectId()),0);
        model.addAttribute("conditionList", conditionList);
        List<List<SupplierExtRelate>> listEp = new ArrayList<List<SupplierExtRelate>>();
        //获取专家人数
        for (SupplierCondition expExtCondition : conditionList) {
            SupplierExtRelate pExtract = new SupplierExtRelate();
            pExtract.setProjectId(showExpExtractRecord.getProjectId());
            pExtract.setSupplierConditionId(expExtCondition.getId());
            //占用字段保存状态类型
            pExtract.setReason("1,2,3");
            List<SupplierExtRelate> projectExtract = extRelateService.list(pExtract,""); 
            listEp.add(projectExtract);
        }
        model.addAttribute("ProjectExtract", listEp);
        //获取监督人员
        List<User>  listUser = extUserServicl.list(new SupplierExtUser(conditionList.get(0).getProjectId()));
        model.addAttribute("listUser", listUser);
        return "ses/sms/supplier_extracts/show_info";
    }

    /**
     * @Description: 获取市
     *
     * @author Wang Wenshuai
     * @date 2016年9月18日 下午4:16:35  
     * @param @return      
     * @return String
     */
    @ResponseBody
    @RequestMapping("/city")
    public Object city(Model model,String area){

        List<Area> listArea = areaService.findTreeByPid(area==null?"1":area,null);

        return listArea;
    }

    /**
     * @Description:显示监督人员
     *
     * @author Wang Wenshuai
     * @version 2016年9月25日 09:49:56 
     * @return String
     */
    @RequestMapping("/showSupervise")
    public String showSupervise(Model model, Integer page){
        User user = new User();
        //8监督人员
        user.setTypeName(7);
        List<User> users = userServicl.selectUser(user, page == null ? 1 : page);
        model.addAttribute("list", new PageInfo<User>(users));
        return "ses/sms/supplier_extracts/supervise_list";
    }
}
