/**
 * 
 */
package ses.controller.sys.ems;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
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

import bss.controller.base.BaseController;
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
import ses.model.ems.ExpExtractRecord;
import ses.model.ems.ExtConType;
import ses.model.ems.ProjectExtract;
import ses.service.bms.AreaServiceI;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.ems.ExpExtConditionService;
import ses.service.ems.ExtConTypeService;
import ses.service.ems.ProjectExtractService;
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
public class ExpExtConditionController extends BaseController {
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
  @Autowired
  ProjectExtractService extractService; //关联表
  /** 字典**/
  @Autowired
  private DictionaryDataServiceI dictionaryDataServiceI; 
  @Autowired
  private AreaServiceI areaServiceI; 
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
  @RequestMapping(value="saveExtCondition",produces = "text/html;charset=UTF-8")
  public String saveExtCondition(ExpExtCondition condition,String hour,String minute,
                                 ExtConType extConType,HttpServletRequest sq,Model model,String typeclassId,String province) throws NoSuchFieldException, SecurityException, UnsupportedEncodingException{
    //获取类型
    String[] expertsTypeSplit = extConType.getExpertsTypeSplit();
    String[] projectId = condition.getProjectId().split(",");
    String conditionId = "";
    //保存单个conid
    String conId = "";
    if (condition.getProjectId() != null && !"".equals(condition.getProjectId())){
      //已抽取
      conditionService.update(new ExpExtCondition(condition.getProjectId(),(short)2));
    }
    List<Area> listArea = areaService.findTreeByPid("1",null);
    model.addAttribute("listArea", listArea);
    model.addAttribute("typeclassId", typeclassId);
    Map<String, Object> map = new HashMap<>();
    Integer verification = verification(condition, hour, minute, model,extConType,map);
    if (verification == 0){
      //循环多包插入条件 
      if (condition.getProjectId() != null && condition.getProjectId().length() > 0){
        String[] split = condition.getProjectId().split(",");
        for (String proid : split) {
          condition.setResponseTime(hour + "," + minute);
          condition.setProjectId(proid);
          if(condition.getAddressId() == null || "".equals(condition.getAddressId())){
            if(province != null && !"".equals(province)){

              List<Area> findAreaByParentId = areaService.findAreaByParentId(province);
              String address = "";
              for (int i = 0; i < findAreaByParentId.size(); i++ ) {
               address += findAreaByParentId.get(i).getId() + ",";
              }
              condition.setAddressId(address);


            }
          }

          //插入信息
          conditionService.insert(condition);
          conditionId += condition.getId()+",";
          if("".equals(conId)){
            conId = condition.getId();
          }
          //插入条件表
          extConType.setConditionId(condition.getId());
          if(expertsTypeSplit != null && expertsTypeSplit.length != 0){
            for (String id : expertsTypeSplit) {
              DictionaryData findById = DictionaryDataUtil.findById(id);
              if("GOODS".equals(findById.getCode())){
                extConType.setExpertsTypeId(findById.getId());
                String goodsCount = sq.getParameter("goodsCount");
                extConType.setExpertsCount(Integer.parseInt(goodsCount));
                conTypeService.insert(extConType); 
              }
              if("PROJECT".equals(findById.getCode())){
                String projectCount = sq.getParameter("projectCount");
                extConType.setExpertsTypeId(findById.getId());
                extConType.setExpertsCount(Integer.parseInt(projectCount));
                conTypeService.insert(extConType);
              }
              if("SERVICE".equals(findById.getCode())){
                String serviceCount = sq.getParameter("serviceCount");    
                extConType.setExpertsTypeId(findById.getId());
                extConType.setExpertsCount(Integer.parseInt(serviceCount));
                conTypeService.insert(extConType);
              }
              if("GOODS_SERVER".equals(findById.getCode())){
                String goodsServerCount = sq.getParameter("goodsServerCount");
                extConType.setExpertsTypeId(findById.getId());
                extConType.setExpertsCount(Integer.parseInt(goodsServerCount));
                conTypeService.insert(extConType);
              }
              if("GOODS_PROJECT".equals(findById.getCode())){
                String goodsProjectCount = sq.getParameter("goodsProjectCount");
                extConType.setExpertsTypeId(findById.getId());
                extConType.setExpertsCount(Integer.parseInt(goodsProjectCount));
                conTypeService.insert(extConType);
              }
            }
          }else{
            conTypeService.insert(extConType);  
          }

        }
      }
      map.put("sccuess", "sccuess");

      //获取查询条件类型
      Map<String, Integer> mapcount = new HashMap<String, Integer>();
      User user=(User) sq.getSession().getAttribute("loginUser");
      Integer sum = conTypeService.getSum(conId);
      PageHelper.startPage(1, sum*2);
      List<ProjectExtract> list = extractService.list(new ProjectExtract(conId));
      if (list == null || list.size() == 0){
        extractService.insert(conId, user != null && !"".equals(user.getId()) ? user.getId() : "",projectId,conditionId);
        PageHelper.startPage(1, sum*2);
        list = extractService.list(new ProjectExtract(conId));
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
      List<ExpExtCondition> listCondition = conditionService.list(new ExpExtCondition(conId, ""), 0);
      List<ExtConType> conTypes = listCondition.get(0).getConTypes();
      map.put("extConType", conTypes);

      if (projectExtractListNo.size() != 0) {
        Collections.shuffle(projectExtractListNo);
        projectExtractListYes.add(projectExtractListNo.get(0));
        projectExtractListNo.remove(0);
      }else{
        //已抽取
        conditionService.update(new ExpExtCondition(conId, (short)2));
      }
      map.put("extRelateListYes", projectExtractListYes);
      map.put("extRelateListNo", projectExtractListNo);
      //删除查询不出的查询结果
      if (projectExtractListNo.size() == 0 && projectExtractListYes.size() == 0){
        conditionService.delById(listCondition.get(0).getId());
        conTypeService.delete(listCondition.get(0).getConTypes().get(0).getId());
      }

    }
    return JSON.toJSONString(map);
  }


  /**
   * 
   *〈简述〉
   *〈详细描述〉返回满足条件的人数
   * @author Wang Wenshuai
   * @param condition
   * @param extConType
   * @return
   */
  @ResponseBody
  @RequestMapping("selectLikeExpertCount")
  public String selectLikeExpert(ExpExtCondition condition,ExtConType extConType,String province){
    Integer count = conditionService.selectLikeExpert(condition, extConType,province);
    return JSON.toJSONString(count);
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
                               Model model,ExtConType extConType,Map<String, Object> map) {
    model.addAttribute("ExpExtCondition", condition);
    Integer count = 0;
    //        if (condition.getAgeMax() == null || "".equals(condition.getAgeMax()) || condition.getAgeMin() == null || "".equals(condition.getAgeMax())){
    //            map.put("age", "不能为空");
    //            count = 1;
    //        }   
    //        if (sids == null || sids.length == 0){
    //            map.put("supervise", "监督人员不能为空");
    //            count = 1;
    //        }

    if (extConType.getExpertsCount() == null  || extConType.getExpertsCount() == 0){
      map.put("expertsCountError", "不能为空");

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
      //            List<User>  listUser = projectSupervisorServicel.list(new ProExtSupervise(list.get(0).getProjectId()));
      //            model.addAttribute("listUser", listUser);
      //            String userName = "";
      //            String userId = "";
      //            if (listUser != null && listUser.size() != 0){
      //                for (User user : listUser) {
      //                    if (user != null && user.getId() != null){
      //                        userName += user.getRelName() + ",";
      //                        userId += user.getId() + ",";
      //                    }
      //
      //                }
      //            }

      List<DictionaryData> find = DictionaryDataUtil.find(12);
      model.addAttribute("find", find);

      //专家抽取地址
      ExpExtractRecord er = new ExpExtractRecord();
      er.setProjectId(list.get(0).getProjectId());
      List<ExpExtractRecord> listRe = expExtractRecordMapper.list(er);
      if (listRe != null && listRe.size() != 0){
        model.addAttribute("extractionSites", listRe.get(0).getExtractionSites());
      }

      //            model.addAttribute("userName", userName);
      //            model.addAttribute("userId", userId);
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
