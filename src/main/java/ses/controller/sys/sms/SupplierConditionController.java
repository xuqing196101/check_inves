/**
 * 
 */
package ses.controller.sys.sms;

import java.util.ArrayList;
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






import bss.service.ppms.ProjectService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;

import ses.dao.sms.SupplierExtRelateMapper;
import ses.dao.sms.SupplierExtUserMapper;
import ses.dao.sms.SupplierExtractsMapper;
import ses.model.bms.Area;
import ses.model.bms.User;


import ses.model.sms.SupplierConType;
import ses.model.sms.SupplierCondition;
import ses.model.sms.SupplierExtRelate;
import ses.model.sms.SupplierExtUser;
import ses.model.sms.SupplierExtracts;
import ses.service.bms.AreaServiceI;
import ses.service.sms.SupplierConTypeService;
import ses.service.sms.SupplierConditionService;
import ses.service.sms.SupplierExtPackageServicel;
import ses.service.sms.SupplierExtRelateService;
import ses.service.sms.SupplierExtUserServicel;
import ses.util.ValidateUtils;

/**
 * @Description:查询条件控制
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月28日上午10:58:03
 * @since  JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/SupplierCondition")
public class SupplierConditionController {
    @Autowired
    private SupplierConditionService conditionService;
    @Autowired 
    private SupplierConTypeService conTypeService;
    @Autowired
    private AreaServiceI areaService;
    @Autowired
    private SupplierExtRelateMapper extRelateMapper; //关联表
    @Autowired
    private SupplierExtractsMapper supplierExtractsMapper;//记录
    @Autowired
    private SupplierExtUserMapper userServicl;
    @Autowired
    private SupplierExtUserServicel extUserServicl;
    @Autowired
    private ProjectService projectService;//项目
    @Autowired
    private SupplierExtPackageServicel  supplierExtPackageServicel;
    @Autowired
    private SupplierExtRelateService extRelateService; //关联表
    /**
     * @Description:保存查询条件
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 上午10:56:45  
     * @param @return      
     * @return String 
     */
    @ResponseBody
    @RequestMapping("/saveSupplierCondition")
    public String saveSupplierCondition(SupplierCondition condition,SupplierConType conType,HttpServletRequest sq,String typeclassId){
        
        Map<String, Object> map = new HashMap<String, Object>();
        if(conType.getSupplierCount() == null || conType.getSupplierCount() == 0 ){
            map.put("count", "不能为空");
           return JSON.toJSONString(map);
        }

        //已抽取
//        conditionService.update(new SupplierCondition(condition.getProjectId(),(short)2));
        //插入信息
        condition.setProjectId(condition.getProjectId());
        conditionService.insert(condition);

        //如果有id就修改没有就新增
        conType.setConditionId(condition.getId());
        conTypeService.insert(conType); 
        
        
        

        map.put("conId",condition.getId());
        map.put("sccuess", "sccuess");
        List<Area> listArea = areaService.findTreeByPid("1",null);
        sq.setAttribute("listArea", listArea);
        sq.setAttribute("typeclassId", typeclassId);


        Map<String, Integer> mapcount = new HashMap<String, Integer>();
        User user = (User) sq.getSession().getAttribute("loginUser");

        List<SupplierExtRelate> list = extRelateService.list(new SupplierExtRelate(condition.getId()), "");
        if (list == null || list.size() == 0){
            extRelateService.insert(condition.getId(), user != null && !"".equals(user.getId()) ? user.getId() : "");
            list = extRelateService.list(new SupplierExtRelate(condition.getId()),"");
        }
        //已操作的
        List<SupplierExtRelate> projectExtractListYes = new ArrayList<SupplierExtRelate>();
        //未操作的
        List<SupplierExtRelate> projectExtractListNo = new ArrayList<SupplierExtRelate>();
        for (SupplierExtRelate projectExtract : list) {
            if (projectExtract.getOperatingType() != null && (projectExtract.getOperatingType() == 1 || projectExtract.getOperatingType() == 2)){
                projectExtractListYes.add(projectExtract);
                Integer conTypeId = mapcount.get(projectExtract.getConTypeId());
                if (conTypeId != null && conTypeId != 0){
                    mapcount.put(projectExtract.getConTypeId(), conTypeId += 1);
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
        List<SupplierCondition> listCondition = conditionService.list(new SupplierCondition(condition.getId(), ""), 0);
        List<SupplierConType> conTypes = null;
        if (listCondition != null && listCondition.size() !=0 ){
            conTypes = listCondition.get(0).getConTypes();
        }
        if (conTypes !=null && conTypes.size() !=0 ){
            for (SupplierConType extConType : conTypes) {
                extConType.setAlreadyCount(mapcount.get(extConType.getId()) == null ? 0 : mapcount.get(extConType.getId()));
            }
            map.put("extConType", conTypes);
        }

        if (projectExtractListNo.size() != 0){
            projectExtractListYes.add(projectExtractListNo.get(0));
            projectExtractListNo.remove(0);
        } else {
            //已抽取
            conditionService.update(new SupplierCondition(condition.getId(), (short)2));
        }
        map.put("extRelateListYes", projectExtractListYes);
        map.put("extRelateListNo", projectExtractListNo);
        //删除查询不出的查询结果
        if (projectExtractListNo.size() == 0 && projectExtractListYes.size() == 0){
            conditionService.delById(listCondition.get(0).getId());
            conTypeService.delete(listCondition.get(0).getConTypes().get(0).getId());
        }

        return JSON.toJSONString(map);
    }


    /**
     * 
     *〈简述〉返回满足条件的供应商个数
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param condition
     * @param conType
     * @param sq
     * @param typeclassId
     * @return
     */
    @ResponseBody
    @RequestMapping("selectLikeSupplier")
    public String selectLikeSupplier(SupplierCondition condition,SupplierConType conType,HttpServletRequest sq){
        Integer count = conditionService.selectLikeSupplier(condition,conType);
        return JSON.toJSONString(count);
    }



    /**
     * @Description:查询单个
     *
     * @author Wang Wenshuai
     * @version 2016年9月30日 下午1:59:22  
     * @param @return      
     * @return String
     */
    @RequestMapping("/showSupplierCondition")
    public String showSupplierCondition(SupplierCondition condition,Model model,String cId,String typeclassId){
        List<Area> listArea = areaService.findTreeByPid("0",null);
        model.addAttribute("listArea", listArea);
        model.addAttribute("typeclassId",typeclassId);
        List<SupplierCondition> list = conditionService.list(condition,0);
        if(list!=null&&list.size()!=0){
            model.addAttribute("ExpExtCondition", list.get(0));
            model.addAttribute("projectId", list.get(0).getProjectId());
            //供应商抽取地址
            SupplierExtracts record = new SupplierExtracts();
            record.setProjectId(list.get(0).getProjectId());
            PageHelper.startPage(1, 1);
            List<SupplierExtracts> listSe = supplierExtractsMapper.list(record);
            if (listSe != null && listSe.size() != 0){
                model.addAttribute("extractionSites", listSe.get(0).getExtractionSites());
            }

        }

        //获取监督人员
        List<User>  listUser = extUserServicl.list(new SupplierExtUser(list.get(0).getProjectId()));
        model.addAttribute("listUser", listUser);
        String userName="";
        String userId="";
        if (listUser != null && listUser.size() != 0){
            for (User user : listUser) {
                userName += user.getRelName() + ",";
                userId += user.getId() + ",";
            }
        }

        model.addAttribute("userName", userName);
        model.addAttribute("userId", userId);
        return "ses/sms/supplier_extracts/add_condition";
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
    public String dels(@RequestParam(value="delids",required = false)String delids){
        String[] id = delids.split(",");
        for (String str : id) {
            conTypeService.delete(str);
        }
        return "sccuess";
    }
}
