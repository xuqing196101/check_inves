package bss.controller.ppms;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import ses.model.bms.Area;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.ems.ExpExtCondition;
import ses.model.ems.ExpExtractRecord;
import ses.model.ems.Expert;
import ses.model.ems.ProExtSupervise;
import ses.model.oms.Orgnization;
import ses.service.bms.AreaServiceI;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpExtConditionService;
import ses.service.ems.ExpExtractRecordService;
import ses.service.ems.ExpertService;
import ses.service.ems.ProjectSupervisorServicel;
import ses.service.oms.OrgnizationServiceI;
import ses.util.DictionaryDataUtil;

import common.annotation.CurrentUser;
import common.constant.StaticVariables;

import bss.controller.base.BaseController;
import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.AdvancedProject;
import bss.service.ppms.AdvancedPackageService;
import bss.service.ppms.AdvancedProjectService;
import bss.service.ppms.FlowMangeService;

@Controller
@Scope("prototype")
@RequestMapping("/adExpExtract")
public class AdExpExtractRecordController extends BaseController {
    
    @Autowired
    private ExpExtConditionService conditionService;
    
    @Autowired
    private AreaServiceI areaService;
    
    @Autowired
    private ExpExtractRecordService expExtractRecordService;
    
    @Autowired
    private AdvancedPackageService packageService;
    
    @Autowired
    private ProjectSupervisorServicel projectSupervisorService;
    
    @Autowired
    private AdvancedProjectService projectService;
    
    @Autowired
    private OrgnizationServiceI orgnizationService;
    
    @Autowired
    private UserServiceI userService;
    
    @Autowired
    private ExpertService expertService;
    
    @Autowired
    private FlowMangeService flowMangeService;
    
    @RequestMapping("/Extraction")
    public String listExtraction(@CurrentUser User user, Model model, String projectId, String page, String typeclassId, String packageId){
        if(StringUtils.isNotBlank(packageId)){
            String[] id = packageId.split(StaticVariables.COMMA_SPLLIT);
            if(id.length > 0){
                ExpExtCondition con = null;
                for (int i = 0; i < id.length; i++ ) {
                    con = new ExpExtCondition();
                    con.setProjectId(id[i]);
                    con.setStatus((short)2);
                    conditionService.update(con);
                }
            }
        }
        List<Area> listArea = areaService.findTreeByPid("0",null);
        model.addAttribute("listArea", listArea);
        model.addAttribute("typeclassId",typeclassId);
        
        if(StringUtils.isNotBlank(projectId)){
            //专家类型
            model.addAttribute("ddList", expExtractRecordService.ddList());
            //根据包获取抽取出的专家
            List<AdvancedPackages> listResultExpert = packageService.listProjectExtract(projectId);
            model.addAttribute("listResultExpert", listResultExpert);
            //专家抽取记录
            ExpExtractRecord record = new ExpExtractRecord();
            record.setProjectId(projectId);
            List<ExpExtractRecord> listSe = expExtractRecordService.listExtractRecord(record,0);
            if (listSe != null && listSe.size() > 0){
                //抽取地区
                model.addAttribute("extractionSites", listSe.get(0).getExtractionSites());
                //响应时间
                String[] atime = listSe.get(0).getResponseTime() != null ? listSe.get(0).getResponseTime().split(","):null;
                if (atime != null && atime.length >= 2){
                  model.addAttribute("minute", atime[0]);
                  model.addAttribute("hour", atime[1]);
                }
            }
            //获取监督人员
            List<ProExtSupervise> listUser = projectSupervisorService.list(new ProExtSupervise(projectId));
            if (listUser != null && listUser.size() != 0){
                String userName = null;
                String superviseId = null;
                for (ProExtSupervise proExtSupervise : listUser) {
                    userName += proExtSupervise.getRelName()+ ",";
                    superviseId += proExtSupervise.getId()+",";
                }
                if(StringUtils.isNotBlank(userName)){
                    model.addAttribute("userName", userName.substring(0, userName.length()-1));
                }
                if(StringUtils.isNotBlank(superviseId)){
                    model.addAttribute("superviseId", superviseId.substring(0, superviseId.length()-1));
                }
                model.addAttribute("listUser", listUser);
            }
            AdvancedProject project = projectService.selectById(projectId);
            if(project != null){
                if (project.getBidDate() != null) {
                    Long currentTime = System.currentTimeMillis();
                    Long currentBidDate = project.getBidDate().getTime()-(30*60*1000);
                    if (currentTime > currentBidDate){
                        model.addAttribute("typeId", 1);
                    }else{
                        model.addAttribute("typeId", 0);
                    }
                    
                    model.addAttribute("projectId", project.getId());
                    model.addAttribute("projectName", project.getName());
                    model.addAttribute("projectNumber", project.getProjectNumber());
                    model.addAttribute("bidDate", project.getBidDate());
                    
                    DictionaryData dictionaryData = DictionaryDataUtil.findById(project.getPurchaseType());
                    if(dictionaryData != null){
                        model.addAttribute("purchaseType", dictionaryData.getName());
                    }
                    
                }
            }
            
            //条件集合
            List<ExpExtCondition> listCon = conditionService.list(new ExpExtCondition(projectId), page == null ? 1 : Integer.valueOf(page));
            model.addAttribute("list", new PageInfo<ExpExtCondition>(listCon));
            List<DictionaryData> find = DictionaryDataUtil.find(12);
            model.addAttribute("stemFrom", find);
            String isCurment = "0";//是否为采购机构人员,默认:0-不是
            //根据当前用户获取机构信息
            if(null != user && null != user.getOrg()){
                Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(user.getOrg().getId());
                if(null != orgnization && null!=orgnization.getTypeName() && orgnization.getTypeName().equals("1")){
                    isCurment = "1";
                }
            }
            //如果不是本系统也就是菜单下进行抽取,则不作判断(设为1-是通过判断)
            if(!StringUtils.isEmpty(typeclassId)){
                isCurment = "1";
            }
            model.addAttribute("isCurment", isCurment);
        }
        return "bss/ppms/advanced_project/extract/condition_list";
    }
    
    @RequestMapping("/showTemporaryExpert")
    public String showTemporaryExpert(Model model, String packageId, String projectId, String flowDefineId){
        model.addAttribute("packageId", packageId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("expert", new Expert());
        model.addAttribute("flowDefineId", flowDefineId);
        //证件类型
        model.addAttribute("idType", DictionaryDataUtil.find(9));
        //专家类型
        model.addAttribute("ddList", expExtractRecordService.ddList());
        //获取专家
        HashMap<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        List<AdvancedPackages> selectByAll = packageService.selectByAll(map);
        if(selectByAll != null && selectByAll.size() > 0){
            model.addAttribute("packList", selectByAll);
        }
        return "bss/ppms/advanced_project/audit/temporary_expert_add";
    }
    
    @RequestMapping(value="/AddtemporaryExpert",produces = "text/html;charset=UTF-8")
    public Object addTemporaryExpert(@Valid Expert expert, BindingResult result, Model model, String projectId,String packageId,String packageName, String loginName, String loginPwd,String flowDefineId,HttpServletRequest sq) throws UnsupportedEncodingException{
        String mobile = sq.getParameter("mobile");
          //转码
        if (expert != null) {
            if(StringUtils.isNotBlank(expert.getRelName())){
                expert.setRelName(URLDecoder.decode(expert.getRelName(),"UTF-8"));
            }
            if(StringUtils.isNotBlank(expert.getIdCardNumber())){
                expert.setIdCardNumber(URLDecoder.decode(expert.getIdCardNumber(),"UTF-8"));
            }
            if(StringUtils.isNotBlank(expert.getAtDuty())){
                expert.setAtDuty(URLDecoder.decode(expert.getAtDuty(),"UTF-8"));
            }
            if(StringUtils.isNotBlank(expert.getMobile())){
                expert.setMobile(URLDecoder.decode(expert.getMobile(),"UTF-8"));
            }
            if(StringUtils.isNotBlank(expert.getRemarks())){
                expert.setRemarks(URLDecoder.decode(expert.getRemarks(),"UTF-8"));
            }
        }
        if (StringUtils.isNotBlank(loginName)) {
            loginName = URLDecoder.decode(loginName,"UTF-8");
        }
        if (StringUtils.isNotBlank(loginPwd)) {
            loginPwd = URLDecoder.decode(loginPwd,"UTF-8");
        }
        if (StringUtils.isNotBlank(packageName)) {
            packageName = URLDecoder.decode(packageName,"UTF-8");
        }
        Integer type = 0;
        //校验字段
        if (result.hasErrors()){
            type = 1;
        }
        if (StringUtils.isBlank(loginName)){
            model.addAttribute("loginNameError", "不能为空");
            type = 1;
        }else{
            //校验用户名是否存在
            List<User> users = userService.findByLoginName(loginName);
            if (users.size() > 0){
                type = 1;
                model.addAttribute("loginNameError", "用户名已存在");
            }
        }
        if(StringUtils.isBlank(mobile)){
            model.addAttribute("mobile", "不能为空");
        }else{
            Map<String, Object> map = new HashMap<>();
            map.put("mobile", mobile);
            List<Expert> list = expertService.yzCardNumber(map);
            if(list != null && list.size() != 0){
                model.addAttribute("mobile", "联系电话已存在");
            }
        }
        if (StringUtils.isBlank(loginPwd)) {
            model.addAttribute("loginPwdError", "不能为空");
            type = 1;
        }
        if (StringUtils.isBlank(packageId)) {
            model.addAttribute("packageIdError", "不能为空");
            type = 1;
        }

        if(StringUtils.isNotBlank(expert.getIdCardNumber())){
            Map<String, Object> map = new HashMap<>();
            map.put("idCardNumber", expert.getIdCardNumber());
            List<Expert> list = expertService.yzCardNumber(map);
            if(list != null && list.size() != 0){
                model.addAttribute("idCardNumberError", "已被占用");
                type = 1;
            }
        }


        if (type == 1){
            model.addAttribute("expert", expert);
            model.addAttribute("loginName", loginName);
            model.addAttribute("loginPwd", loginPwd);
            model.addAttribute("projectId", projectId);
            model.addAttribute("packageId", packageId);
            model.addAttribute("packageName", packageName);
            model.addAttribute("flowDefineId", flowDefineId);
            //专家类型
            model.addAttribute("ddList", expExtractRecordService.ddList());
            //证件类型
            model.addAttribute("idType", DictionaryDataUtil.find(9));
            return "bss/ppms/advanced_project/audit/temporary_expert_add";
        }


        expExtractRecordService.addTemporaryExpert(expert, projectId,packageId, loginName, loginPwd,sq);
        //修改状态
        flowMangeService.flowExe(sq, flowDefineId, projectId, 2);
        String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();  
        Random random = new Random();  
        for (int i = 0; i < 15; i++) {  
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));  
        }
        String randomCode = sb.toString();
        return "redirect:/adPackageExpert/assignedExpert.html?projectId=" + projectId + "&flowDefineId=" + flowDefineId + "&randomCode = " + randomCode;
    }
    
    @ResponseBody
    @RequestMapping("/getpackage")
    public String getPackage(String projectId){
        if(StringUtils.isNotBlank(projectId)){
            HashMap<String, Object> map = new HashMap<>();
            map.put("projectId", projectId);
            List<AdvancedPackages> selectByAll = packageService.selectByAll(map);
            if(selectByAll != null && selectByAll.size() > 0){
                return  JSON.toJSONString(selectByAll);
            }
        }
        return null;
    }
    
    /**
     * 
     *〈展示修改临时专家页面〉
     *〈详细描述〉
     * @author FengTian
     * @param model
     * @param packageId
     * @param projectId
     * @param flowDefineId
     * @param id
     * @return
     */
    @RequestMapping("/showEditTemporaryExpert")
    public  String showEditTemporaryExpert(Model model,String packageId,String projectId,String flowDefineId,String id){
        model.addAttribute("packageId", packageId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("flowDefineId", flowDefineId);
        //证件类型
        model.addAttribute("idType", DictionaryDataUtil.find(9));
        //专家类型
        model.addAttribute("ddList", expExtractRecordService.ddList());
        //获取专家
        HashMap<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        List<AdvancedPackages> selectByAll = packageService.selectByAll(map);
        if(selectByAll != null && selectByAll.size() > 0){
            model.addAttribute("packList", selectByAll);
        }
        Expert expert = expertService.selectByPrimaryKey(id);
        model.addAttribute("expert", expert);
        AdvancedPackages packages = packageService.selectById(packageId);
        if(packages != null && StringUtils.isNotBlank(packages.getName())){
            model.addAttribute("packageName", packages.getName());
        }
        List<User> userList = userService.selectByTypeId(id);
        if(userList != null && userList.size() > 0){
            model.addAttribute("loginName", userList.get(0).getLoginName());
        }
        return "bss/ppms/advanced_project/audit/temporary_expert_edit";
    }
    
    @RequestMapping(value = "/editTemporaryExpert", produces = "text/html;charset=UTF-8")
    public String editTemporaryExpert(@Valid Expert expert, BindingResult result, Model model, String projectId, String packageId, String packageName, String loginName, String loginPwd, String flowDefineId, HttpServletRequest sq,String oldPackageId)throws UnsupportedEncodingException {
        String mobile = sq.getParameter("mobile");
        // 转码
        if (expert != null) {
            if(StringUtils.isNotBlank(expert.getRelName())){
                expert.setRelName(URLDecoder.decode(expert.getRelName(),"UTF-8"));
            }
            if(StringUtils.isNotBlank(expert.getIdCardNumber())){
                expert.setIdCardNumber(URLDecoder.decode(expert.getIdCardNumber(),"UTF-8"));
            }
            if(StringUtils.isNotBlank(expert.getAtDuty())){
                expert.setAtDuty(URLDecoder.decode(expert.getAtDuty(),"UTF-8"));
            }
            if(StringUtils.isNotBlank(expert.getMobile())){
                expert.setMobile(URLDecoder.decode(expert.getMobile(),"UTF-8"));
            }
            if(StringUtils.isNotBlank(expert.getRemarks())){
                expert.setRemarks(URLDecoder.decode(expert.getRemarks(),"UTF-8"));
            }
        }
        if (StringUtils.isNotBlank(loginName)) {
            loginName = URLDecoder.decode(loginName,"UTF-8");
        }
        if (StringUtils.isNotBlank(packageName)) {
            packageName = URLDecoder.decode(packageName,"UTF-8");
        }
        Integer type = 0;
        // 校验字段
        if (result.hasErrors()) {
            type = 1;
        }
        if (StringUtils.isBlank(loginName)) {
            model.addAttribute("loginNameError", "不能为空");
            type = 1;
        } else {
            // 校验用户名是否存在
            Map<String, Object> umap = new HashMap<>();
            umap.put("loginName", loginName);
            umap.put("typeId", expert.getId());
            if (!userService.yzLoginName(umap)) {
                type = 1;
                model.addAttribute("loginNameError", "用户名已存在");
            }
        }
        if (StringUtils.isBlank(mobile)) {
            type = 1;
            model.addAttribute("mobile", "不能为空");
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("mobile", mobile);
            map.put("id", expert.getId());
            List<Expert> list = expertService.yzCardNumber(map);
            if (list != null && list.size() != 0) {
                model.addAttribute("mobile", "联系电话已存在");
                type = 1;
            }
        }
        if (StringUtils.isBlank(packageId)) {
            model.addAttribute("packageIdError", "不能为空");
            type = 1;
        }
        if (StringUtils.isNotBlank(expert.getIdCardNumber())) {
            Map<String, Object> map = new HashMap<>();
            map.put("idCardNumber", expert.getIdCardNumber());
            map.put("id", expert.getId());
            List<Expert> list = expertService.yzCardNumber(map);
            if (list != null && list.size() != 0) {
                model.addAttribute("idCardNumberError", "已被占用");
                type = 1;
            }
        }
        if (type == 1) {
            model.addAttribute("expert", expert);
            model.addAttribute("loginName", loginName);
            //model.addAttribute("loginPwd", loginPwd);
            model.addAttribute("projectId", projectId);
            model.addAttribute("packageId", packageId);
            model.addAttribute("packageName", packageName);
            model.addAttribute("flowDefineId", flowDefineId);
            // 专家类型
            model.addAttribute("ddList", expExtractRecordService.ddList());
            // 证件类型
            model.addAttribute("idType", DictionaryDataUtil.find(9));
            return "bss/prms/advanced_project/audit/temporary_expert_edit";
        }
        expExtractRecordService.editTemporaryExpert(expert, projectId,packageId, loginName, loginPwd, sq,oldPackageId);
        String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        String randomCode = sb.toString();
        return "redirect:/adPackageExpert/assignedExpert.html?projectId=" + projectId + "&flowDefineId=" + flowDefineId + "&randomCode = " + randomCode;
    }

}
