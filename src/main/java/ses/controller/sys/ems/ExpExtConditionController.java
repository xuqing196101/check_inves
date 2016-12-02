/**
 * 
 */
package ses.controller.sys.ems;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bss.model.ppms.Project;
import bss.service.ppms.ProjectService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;

import ses.dao.ems.ExpExtPackageMapper;
import ses.dao.ems.ExpExtractRecordMapper;
import ses.dao.ems.ProExtSuperviseMapper;
import ses.model.bms.Area;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.ems.ExpExtCondition;
import ses.model.ems.ExpExtPackage;
import ses.model.ems.ExpExtractRecord;
import ses.model.ems.ExtConType;
import ses.model.ems.ExtConTypeArray;
import ses.model.ems.ProExtSupervise;
import ses.service.bms.AreaServiceI;
import ses.service.ems.ExpExtConditionService;
import ses.service.ems.ExtConTypeService;
import ses.service.ems.ProjectSupervisorServicel;
import ses.util.DictionaryDataUtil;

/**
 * @Description:查询条件控制
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月28日上午10:58:03
 * @since  JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/ExtCondition")
public class ExpExtConditionController {
    @Autowired
    ExpExtConditionService conditionService;
    @Autowired 
    ExtConTypeService conTypeService;
    @Autowired
    private AreaServiceI areaService;
    @Autowired
    private ProExtSuperviseMapper extSuperviseMapper;
    @Autowired
    private ExpExtractRecordMapper expExtractRecordMapper;
    @Autowired
    ProjectSupervisorServicel projectSupervisorServicel;
    @Autowired
    ProjectService projectService;
    @Autowired
    ExpExtPackageMapper extPackageMapper;
    
    /**
     * @Description:保存查询条件
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 上午10:56:45  
     * @param @return      
     * @return String 
     * @throws UnsupportedEncodingException 
     */
    @ResponseBody
    @RequestMapping(value="/saveExtCondition",produces = "text/html;charset=UTF-8")
    public String saveExtCondition(ExpExtCondition condition,String hour,String minute,
                                   ExtConTypeArray extConTypeArray,String[] sids,HttpServletRequest sq,Model model,String typeclassId,String extAddress) throws NoSuchFieldException, SecurityException, UnsupportedEncodingException{
        List<Area> listArea = areaService.findTreeByPid("1",null);
        model.addAttribute("listArea", listArea);
        model.addAttribute("typeclassId", typeclassId);
        Map<String, String> map = new HashMap<>();
        Integer verification = verification(condition, hour, minute, sids, model,extConTypeArray,map);
      
        if (verification == 0){
            map.put("sccuess", "sccuess");
            condition.setResponseTime(hour + "," + minute);
            //给专家记录表set信息并且插入到记录表(查看是否已存在记录)
            ExpExtractRecord record = new ExpExtractRecord();
            
            //根据包id获取
            ExpExtPackage byId = extPackageMapper.selectByPrimaryKey(condition.getProjectId());
            
            record.setProjectId(byId.getProjectId());
            //查询是否已有记录
            PageHelper.startPage(1, 1);
            List<ExpExtractRecord> list = expExtractRecordMapper.list(record);
            if (condition.getId() != null && !"".equals(condition.getId())){
                conditionService.update(condition);	
                //删除关联数据重新添加
                conTypeService.delete(condition.getId());
            }else{
                //插入信息
                conditionService.insert(condition);
                //添加一条记录
                ExpExtPackage extP = new  ExpExtPackage();
                extP.setId(condition.getProjectId());
                List<ExpExtPackage> listExtPackage = extPackageMapper.list(extP);
                if (listExtPackage != null && listExtPackage.size() != 0){
                    extP.setCount(listExtPackage.get(0).getCount() == 0 ? 1 : listExtPackage.get(0).getCount()+ 1);
                    extPackageMapper.updateByPrimaryKeySelective(extP);
                }
                
                
                if (list != null && list.size() != 0){
                
                }else{
                    ExpExtractRecord expExtractRecord = new ExpExtractRecord();
                    expExtractRecord.setExtractionTime(new Date());
                    Project selectById = projectService.selectById(condition.getProjectId());
                    if (selectById != null){
                        expExtractRecord.setProjectId(selectById.getId());
                        expExtractRecord.setProjectName(selectById.getName());
                    }
                    User user = (User) sq.getSession().getAttribute("loginUser");
                    expExtractRecord.setExtractsPeople(user.getId());
                    expExtractRecord.setExtractTheWay((short)1);
                    expExtractRecord.setExtractionSites(extAddress);
                    expExtractRecordMapper.insertSelective(expExtractRecord);
                }
               
            }
            
            //插入条件表
            ExtConType conType = null;
            if(extConTypeArray != null && extConTypeArray.getExpertsTypeId() != null){
                for (int i = 0; i < extConTypeArray.getExpertsTypeId().length; i++) {
                    conType=new ExtConType();

                    conType.setExpertsCount(Integer.parseInt(extConTypeArray.getExtCount()[i]));

                    conType.setExpertsTypeId(new Short(extConTypeArray.getExpertsTypeId()[i]));
                    if (extConTypeArray.getExtCategoryId().length != 0){
                        conType.setCategoryId(extConTypeArray.getExtCategoryId()[i]);
                        conType.setCategoryName(extConTypeArray.getExtCategoryName()[i]);
                    }
//                    if (extConTypeArray.getExtQualifications().length != 0){
//                        conType.setExpertsQualification(extConTypeArray.getExtQualifications()[i]);
//                    }
                    conType.setConditionId(condition.getId());
//                    conType.setIsMulticondition(new Short(extConTypeArray.getIsSatisfy()[i]));
                    //如果有id就修改没有就新增
                    conTypeService.insert(conType);	
                }
            }
           
         
        }
        return JSON.toJSONString(map);
    }
    /**
     * 
     *〈简述〉 验证消息
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param condition
     * @param hour
     * @param minute
     * @param sids
     * @param model
     * @return
     */
    private Integer verification(ExpExtCondition condition, String hour, String minute,
                                 String[] sids, Model model,ExtConTypeArray extConTypeArray,Map<String, String> map) {
        model.addAttribute("ExpExtCondition", condition);
        Integer count = 0;
        if (condition.getAgeMax() == null || "".equals(condition.getAgeMax()) || condition.getAgeMin() == null || "".equals(condition.getAgeMax())){
            map.put("age", "不能为空");
            count = 1;
        }   
//        if (sids == null || sids.length == 0){
//            map.put("supervise", "监督人员不能为空");
//            count = 1;
//        }

        if (extConTypeArray == null || extConTypeArray.getExtCount() == null){
            map.put("typeArray", "请添加供应商抽取数量，产品类型等条件");
            count = 1;
        }
        return count;
    }
    /**
     * @Description:查询单个
     *
     * @author Wang Wenshuai
     * @version 2016年9月30日 下午1:59:22  
     * @param @return      
     * @return String
     */
    @RequestMapping("/showExtCondition")
    public String showExtCondition(ExpExtCondition condition,Model model,String cId,String typeclassId){
        List<Area> listArea = areaService.findTreeByPid("0",null);
        model.addAttribute("listArea", listArea);
        model.addAttribute("typeclassId", typeclassId);
        List<ExpExtCondition> list = conditionService.list(condition,null);
        //响应时间
        if (list != null && list.size() != 0){
//            String[] atime = list.get(0).getResponseTime() != null?list.get(0).getResponseTime().split(","):null;
//            if (atime != null && atime.length >= 2){
//                model.addAttribute("minute", atime[0]);
//                model.addAttribute("hour", atime[1]);
//            }
            model.addAttribute("ExpExtCondition", list.get(0));
            model.addAttribute("projectId", list.get(0).getProjectId());
            //获取监督人员
            List<User>  listUser = projectSupervisorServicel.list(new ProExtSupervise(list.get(0).getProjectId()));
            model.addAttribute("listUser", listUser);
            String userName = "";
            String userId = "";
            if (listUser != null && listUser.size() != 0){
                for (User user : listUser) {
                    if (user != null && user.getId() != null){
                        userName += user.getLoginName() + ",";
                        userId += user.getId() + ",";
                    }

                }
            }
            
            List<DictionaryData> find = DictionaryDataUtil.find(12);
            model.addAttribute("find", find);
            
            //专家抽取地址
            ExpExtractRecord er = new ExpExtractRecord();
            er.setProjectId(list.get(0).getProjectId());
            List<ExpExtractRecord> listRe = expExtractRecordMapper.list(er);
            if (listRe != null && listRe.size() != 0){
                model.addAttribute("extractionSites", listRe.get(0).getExtractionSites());
            }
            
            model.addAttribute("userName", userName);
            model.addAttribute("userId", userId);
        }

        return "ses/ems/exam/expert/extract/add_condition";
    }

    /**
     * @Description:修改
     *
     * @author Wang Wenshuai
     * @version 2016年9月30日 下午1:47:48  
     * @param @return      
     * @return String
     */
    @RequestMapping("/updateCondition")
    public String updateCondition(){

        return null;
    }
    /**
     * @Description:删除
     *
     * @author Wang Wenshuai
     * @version 2016年9月30日 下午3:09:44  
     * @param @param delids
     * @param @return      
     * @return Object
     */

    @RequestMapping("/dels")	
    public String dels(@RequestParam(value="delids",required=false)String delids){
        String[] id = delids.split(",");
        for (String str : id) {
            conTypeService.delete(str);
        }
        return "sccuess";
    }
}
