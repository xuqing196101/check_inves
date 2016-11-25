/**
 * 
 */
package ses.controller.sys.sms;

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

import ses.dao.sms.SupplierExtRelateMapper;
import ses.dao.sms.SupplierExtUserMapper;
import ses.dao.sms.SupplierExtractsMapper;
import ses.model.bms.Area;
import ses.model.bms.User;
import ses.model.ems.ExpExtractRecord;
import ses.model.ems.ExtConTypeArray;
import ses.model.sms.SupplierConType;
import ses.model.sms.SupplierCondition;
import ses.model.sms.SupplierExtPackage;
import ses.model.sms.SupplierExtUser;
import ses.model.sms.SupplierExtracts;
import ses.service.bms.AreaServiceI;
import ses.service.sms.SupplierConTypeService;
import ses.service.sms.SupplierConditionService;
import ses.service.sms.SupplierExtPackageServicel;
import ses.service.sms.SupplierExtUserServicel;

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
    public String saveSupplierCondition(SupplierCondition condition,ExtConTypeArray extConTypeArray,String[] sids,HttpServletRequest sq,String typeclassId,String extAddress ){
        List<Area> listArea = areaService.findTreeByPid("1",null);
        sq.setAttribute("listArea", listArea);
        sq.setAttribute("typeclassId", typeclassId);
        Map<String, String> map = new HashMap<String, String>();
        Integer count=0;
//        if (sids==null || sids.length==0 || "".equals(sids)){
//            map.put("supervise", "请选择监督人员");
//            count=1;
//        }
        if(extConTypeArray == null || extConTypeArray.getExtCount() == null || extConTypeArray.getExtCategoryId()==null){
            map.put("array", "请添加供应商抽取数量，产品类型等条件");
            count=1;
        }
        if(count==1){
            return JSON.toJSONString(map);
        }

        //给专家记录表set信息并且插入到记录表(查询是否存在)
        SupplierExtracts record=new SupplierExtracts();
        record.setProjectId(condition.getProjectId());
        PageHelper.startPage(1, 1);
        List<SupplierExtracts> list = supplierExtractsMapper.list(record);


        if (condition.getId() != null && !"".equals(condition.getId())){
            conditionService.update(condition);	
            //删除关联数据重新添加
            conTypeService.delete(condition.getId());
        }else{
            //插入信息
            conditionService.insert(condition);
            if (list != null && list.size() != 0){

            }else{
                //给供应商记录表set信息并且插入到记录表
                SupplierExtracts expExtractRecord=new SupplierExtracts();
                expExtractRecord.setExtractionTime(new Date());
                Project selectById = projectService.selectById(condition.getProjectId());
                if (selectById != null){
                    expExtractRecord.setProjectId(selectById.getId());
                    expExtractRecord.setProjectName(selectById.getName());
                }
                User user = (User) sq.getSession().getAttribute("loginUser");
                expExtractRecord.setExtractsPeople(user.getId());
                expExtractRecord.setExtractTheWay((short)1);
                expExtractRecord.setExtractionSites(condition.getExtractAddress());
                supplierExtractsMapper.insertSelective(expExtractRecord);
            }
        }

        //抽取地址
        if (extAddress != null && !"".equals(extAddress)){
            SupplierExtracts supplierExtracts = new SupplierExtracts();
            supplierExtracts.setId(list.get(0).getId());
            supplierExtracts.setExtractionSites(extAddress);
            supplierExtractsMapper.updateByPrimaryKeySelective(supplierExtracts);
        }  

        SupplierConType conType=null;

        if(extConTypeArray!=null&&extConTypeArray.getExtCount()!=null&&extConTypeArray.getExtCount().length!=0){
            for (int i = 0; i < extConTypeArray.getExtCount().length; i++) {
                conType=new SupplierConType();
                conType.setSupplieCount(Integer.parseInt(extConTypeArray.getExtCount()[i]));
                if(extConTypeArray.getExpertsTypeId().length != 0){
                    conType.setSupplieTypeId(extConTypeArray.getExpertsTypeId()[i]);
                }
                if (extConTypeArray.getExtCategoryId().length != 0){
                    conType.setCategoryName(extConTypeArray.getExtCategoryName()[i]);
                    conType.setCategoryId(extConTypeArray.getExtCategoryId()[i]);
                }
                conType.setConditionId(condition.getId());
                if(extConTypeArray.getIsSatisfy().length!=0){
                    conType.setIsMulticondition(new Short(extConTypeArray.getIsSatisfy()[i]));
                }
                //如果有id就修改没有就新增
                conTypeService.insert(conType);	
            }
        }

        map.put("sccuess", "sccuess");
        //插入数据
        SupplierExtPackage byId = supplierExtPackageServicel.getById(condition.getProjectId());
        SupplierExtPackage extPackage = new SupplierExtPackage();
        extPackage.setId(byId.getId());
        if (byId.getCount() == null || byId.getCount() == 0){
            extPackage.setCount(new Long(1));
        } else {
            extPackage.setCount(byId.getCount() + 1);
        }
        supplierExtPackageServicel.update(extPackage);
        return JSON.toJSONString(map);
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
        List<Area> listArea = areaService.findTreeByPid("1",null);
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
        List<User>  listUser=extUserServicl.list(new SupplierExtUser(list.get(0).getProjectId()));
        model.addAttribute("listUser", listUser);
        String userName="";
        String userId="";
        if (listUser != null && listUser.size() != 0){
            for (User user : listUser) {
                userName+=user.getLoginName()+",";
                userId+=user.getId()+",";
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
        String[] id=delids.split(",");
        for (String str : id) {
            conTypeService.delete(str);
        }
        return "sccuess";
    }
}
