package bss.controller.ppms;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import ses.model.bms.Area;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.ems.ExpExtCondition;
import ses.model.ems.ExpExtractRecord;
import ses.model.ems.ProExtSupervise;
import ses.model.oms.Orgnization;
import ses.service.bms.AreaServiceI;
import ses.service.ems.ExpExtConditionService;
import ses.service.ems.ExpExtractRecordService;
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

}
