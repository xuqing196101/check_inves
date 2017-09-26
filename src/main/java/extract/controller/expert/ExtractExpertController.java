package extract.controller.expert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.dao.bms.EngCategoryMapper;
import ses.model.bms.AreaZtree;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.EngCategoryService;
import ses.util.DictionaryDataUtil;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.Project;
import bss.service.ppms.AdvancedProjectService;
import bss.service.ppms.ProjectService;

import com.alibaba.fastjson.JSON;
import common.annotation.CurrentUser;

import extract.model.expert.ExpertExtractCateInfo;
import extract.model.expert.ExpertExtractCondition;
import extract.model.expert.ExpertExtractProject;
import extract.model.expert.ExpertExtractResult;
import extract.service.expert.ExpertExtractConditionService;
import extract.service.expert.ExpertExtractProjectService;
import extract.service.expert.ExpertExtractResultService;


/**
 * 
 * Description: 专家抽取
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/extractExpert")
public class ExtractExpertController {

    /** 专家抽取项目信息 **/
    @Autowired
    private ExpertExtractProjectService expertExtractProjectService;

    /** 专家抽取条件信息 **/
    @Autowired
    private ExpertExtractConditionService expertExtractConditionService;

    /** 地区 **/
    @Autowired
    private AreaServiceI areaService;

    /** 品目 **/
    @Autowired
    private CategoryService categoryService;

    /** 工程专业 **/
    @Autowired
    private EngCategoryService engCategoryService;

    /** 工程专业品目 **/
    @Autowired
    private EngCategoryMapper engCategoryMapper;

    /** 字典 **/
    @Autowired
    private DictionaryDataServiceI dictionaryDataServiceI;
    
    /** 专家抽取结果 **/
    @Autowired
    private ExpertExtractResultService expertExtractResultService;
    
    /** 预研 **/
    @Autowired
    private AdvancedProjectService advancedProjectService;
    
    /** 项目 **/
    @Autowired
    private ProjectService projectService;

    /**
     * 
     * Description: 跳转专家人工抽取页面
     * 
     * @author zhang shubin
     * @data 2017年9月4日
     * @param 
     * @return
     */
    @RequestMapping("/toExpertExtract")
    public String toExpertExtract(@CurrentUser User user,Model model,String projectId,String projectInto,String packageId,String packageName){
        //权限验证  资源服务中心  采购机构  可以抽取
        String authType = null;
        if(null != user && ("4".equals(user.getTypeName()) || "1".equals(user.getTypeName()))){
            authType = user.getTypeName();
            //采购方式
            List<DictionaryData> purchaseWayList = DictionaryDataUtil.find(5);
            if(purchaseWayList != null && purchaseWayList.size() > 0){
                 for (DictionaryData dictionaryData : purchaseWayList) {
                     if("XJCG".equals(dictionaryData.getCode())){
                         dictionaryData.setName("询价");
                     }
                 }
            }
            model.addAttribute("purchaseWayList",purchaseWayList);
            //项目类型
            List<DictionaryData> projectTypeList = DictionaryDataUtil.find(6);
            model.addAttribute("projectTypeList",projectTypeList);
            //生成项目信息主键id
            String uuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
            model.addAttribute("projectId",uuid);
            //专家类型
            List<DictionaryData> expertTypeList = DictionaryDataUtil.find(12);
            model.addAttribute("expertTypeList",expertTypeList);
            //加载地区省
            List<AreaZtree> areaTree = areaService.getTreeList(null,null);
            model.addAttribute("areaTree",areaTree);
            model.addAttribute("authType",authType);
            //从项目实施进入  需要自动带入项目信息
            if(projectId != null && projectInto != null && packageId != null){
            	ExpertExtractProject expertExtractProject = new ExpertExtractProject();
            	expertExtractProject.setProjectId(projectId);
            	expertExtractProject.setPackageId(packageId);
            	if("advPro".equals(projectInto)){
        			//预研进入
        			AdvancedProject advancedProject = advancedProjectService.selectById(projectId);
        			expertExtractProject.setProjectName(advancedProject.getName());
        			expertExtractProject.setCode(advancedProject.getProjectNumber());
        			expertExtractProject.setProjectType(advancedProject.getPlanType());
        			expertExtractProject.setPurchaseWay(advancedProject.getPurchaseType());
            	}else if("relPro".equals(projectInto)){
            		//真实项目
            		Project project = projectService.selectById(projectId);
            		expertExtractProject.setProjectName(project.getName());
            		expertExtractProject.setCode(project.getProjectNumber());
            		expertExtractProject.setProjectType(project.getPlanType());
            		expertExtractProject.setPurchaseWay(project.getPurchaseType());
            	}else{
            		//随机抽取
            	}
        		model.addAttribute("expertExtractProject", expertExtractProject);
        		model.addAttribute("packageName", packageName);
            }
            return "ses/ems/exam/expert/extract/expertExtract";
        }
        return "redirect:/qualifyError.jsp";
    }
    
    /**
     * 
     * Description: 保存抽取信息
     * 
     * @author zhang shubin
     * @data 2017年9月5日
     * @param 
     * @return
     * @throws Exception 
     */
    @RequestMapping("/saveProjectInfo")
    @ResponseBody
    public String saveProjectInfo(@CurrentUser User user,ExpertExtractProject expertExtractProject,ExpertExtractCondition expertExtractCondition,ExpertExtractCateInfo expertExtractCateInfo) throws Exception{
        //保存项目基本信息
        expertExtractProjectService.save(expertExtractProject,user);
        //查询抽取结果信息
        Map<String, Object> result = expertExtractConditionService.findExpertByExtract(expertExtractProject,expertExtractCondition,expertExtractCateInfo);
        //保存抽取条件
        ExpertExtractCondition condition = expertExtractConditionService.save(expertExtractCondition,expertExtractCateInfo);
        result.put("conditionId", condition.getId());
        return JSON.toJSONString(result);
    }
    
    /**
     * 
     * Description: 根据项目类型加载专家类别
     * 
     * @author zhang shubin
     * @data 2017年9月6日
     * @param 
     * @return
     */
    @RequestMapping("/loadExpertKind")
    @ResponseBody
    public String loadExpertKind(String id){
        List<DictionaryData> expertKindList = new ArrayList<>();
        if(id != null){
            expertKindList = expertExtractProjectService.loadExpertKind(id);
        }
        return JSON.toJSONString(expertKindList);
    }
    
    /**
     * 
     * Description: 获取满足条件的人数
     * 
     * @author zhang shubin
     * @data 2017年9月7日
     * @param 
     * @return
     * @throws Exception 
     */
    @RequestMapping("/getCount")
    @ResponseBody
    public String getCount(ExpertExtractProject expertExtractProject,ExpertExtractCondition expertExtractCondition,ExpertExtractCateInfo expertExtractCateInfo) throws Exception{
        Map<String, Object> result = expertExtractConditionService.findExpertByExtract(expertExtractProject,expertExtractCondition,expertExtractCateInfo);
        return JSON.toJSONString(result);
    }
    
    /**
     * 
     * Description: 追加显示专家
     * 
     * @author zhang shubin
     * @data 2017年9月12日
     * @param 
     * @return
     * @throws Exception 
     */
    @RequestMapping("/getExpert")
    @ResponseBody
    public String getExpert(ExpertExtractProject expertExtractProject,ExpertExtractCondition expertExtractCondition,ExpertExtractCateInfo expertExtractCateInfo,String conId) throws Exception{
        expertExtractCondition.setId(conId);
        Map<String, Object> result = expertExtractConditionService.findExpertByExtract(expertExtractProject,expertExtractCondition,expertExtractCateInfo);
        return JSON.toJSONString(result);
    }
    
    /**
     * 
     * Description: 专家抽取结束
     * 
     * @author zhang shubin
     * @data 2017年9月20日
     * @param 
     * @return
     * @throws Exception 
     */
    @RequestMapping("/extractEnd")
    @ResponseBody
    public String extractEnd(ExpertExtractProject expertExtractProject,ExpertExtractCondition expertExtractCondition,ExpertExtractCateInfo expertExtractCateInfo,String conId) throws Exception{
        //判断是否保存候补专家
        expertExtractCondition.setId(conId);
        expertExtractCondition.setExpertKindId(expertExtractCondition.getExpertKindId());
        if(expertExtractCondition.getIsExtractAlternate() == 1){
            if(expertExtractCondition.getExpertKindId().indexOf(",") >= 0){
                //有两个专家类别
                for (String str : expertExtractCondition.getExpertKindId().split(",")) {
                    Map<String, Object> result22 = expertExtractConditionService.findExpertByExtract(expertExtractProject,expertExtractCondition,expertExtractCateInfo);
                    @SuppressWarnings("unchecked")
                    List<Expert> list = (List<Expert>)result22.get(str);
                    if(list != null && list.size() > 0){
                        ExpertExtractResult expertExtractResult = new ExpertExtractResult();
                        expertExtractResult.setIsAlternate((short)1);
                        expertExtractResult.setExpertId(list.get(0).getId());
                        expertExtractResult.setProjectId(expertExtractProject.getId());
                        expertExtractResult.setConditionId(conId);
                        expertExtractResult.setReviewTime(expertExtractProject.getReviewTime());
                        expertExtractResult.setIsJoin((short)1);
                        expertExtractResult.setExpertCode(str);
                        expertExtractResultService.save(expertExtractResult);
                    }
                }
            }else{
                //有一个专家类别
                for(int i=0; i<2; i++){
                    Map<String, Object> result22 = expertExtractConditionService.findExpertByExtract(expertExtractProject,expertExtractCondition,expertExtractCateInfo);
                    @SuppressWarnings("unchecked")
                    List<Expert> list = (List<Expert>)result22.get(expertExtractCondition.getExpertKindId());
                    if(list != null && list.size() > 0){
                        ExpertExtractResult expertExtractResult = new ExpertExtractResult();
                        expertExtractResult.setIsAlternate((short)1);
                        expertExtractResult.setExpertId(list.get(0).getId());
                        expertExtractResult.setProjectId(expertExtractProject.getId());
                        expertExtractResult.setConditionId(conId);
                        expertExtractResult.setReviewTime(expertExtractProject.getReviewTime());
                        expertExtractResult.setIsJoin((short)1);
                        expertExtractResult.setExpertCode(expertExtractCondition.getExpertKindId());
                        expertExtractResultService.save(expertExtractResult);
                    }
                }
            }
        }
        // 修改项目抽取状态
        expertExtractProjectService.updataStatus(expertExtractProject.getId());
        String jsonString = JSON.toJSONString("success");
        return jsonString;
    }
    
    /**
     * 
     * Description: 加载目录
     * 
     * @author zhang shubin
     * @data 2017年9月7日
     * @param 
     * @return
     */
    @RequestMapping("/addHeading")
    public String addHeading(Model model, String id, String type,String isSatisfy) {
        model.addAttribute("type", type);
        model.addAttribute("ids", id);
        model.addAttribute("isSatisfy", isSatisfy);
        return "ses/ems/exam/expert/extract/product";
    }
    
    /**
     * 
     * 〈简述〉获取品目树 〈详细描述〉
     * 
     * @author 
     * @return
     */
    @ResponseBody
    @RequestMapping("/getTree")
    public String getTree(String code,Category category,String ids) {
        if (code != null && code.equals("GOODS_PROJECT")) {
            code = "PROJECT";
        }
        if(code.equals("GOODS_SERVER")){
            return "";
        }
        String[] cheIds = ids.split(",");
        /*List<String> idList = new ArrayList<>();
        if(ids != null && !ids.equals("")){
            String[] split = ids.split(",");
            for (String str : split) {
                idList.add(str);
            }
        }*/
        String categoryId = DictionaryDataUtil.getId(code);
        if (code != null && code.indexOf("ENG_INFO_ID") > 0) {
            categoryId = DictionaryDataUtil.getId("ENG_INFO_ID");
            List < CategoryTree > allCategories = new ArrayList < CategoryTree > ();
            String typeIds = code.split(",")[0];
            if(category.getId() == null) {
                DictionaryData parent = dictionaryDataServiceI.getDictionaryData(categoryId);
                CategoryTree ct = new CategoryTree();
                ct.setName(parent.getName());
                ct.setId(parent.getId());
                ct.setIsParent("true");
                allCategories.add(ct);
            } else {
                List < Category > tempNodes = engCategoryService.findPublishTree(category.getId(), null);
                List < Category > childNodes = new ArrayList<Category>();
                int count = 0;
                if (typeIds != null && !typeIds.equals("")) {
                    String[] tIds = typeIds.split(",");
                    for (String typeId : tIds) {
                        if (typeId.equals("GOODS_PROJECT")) {
                            count++;
                            for (Category cate : tempNodes) {
                                if (cate.getExpertType() != null && cate.getExpertType().equals("0")) {
                                    childNodes.add(cate);
                                }
                            }
                        } else if (typeId.equals("PROJECT")) {
                            count++;
                            for (Category cate : tempNodes) {
                                if (cate.getExpertType() != null && cate.getExpertType().equals("1")) {
                                    childNodes.add(cate);
                                }
                            }
                        }
                    }
                }
                if (count == 2) {
                    childNodes = tempNodes;
                }
                if(childNodes != null && childNodes.size() > 0) {
                    for(Category ca: childNodes) {
                        CategoryTree ct = new CategoryTree();
                        ct.setName(ca.getName());
                        ct.setId(ca.getId());
                        ct.setParentId(ca.getParentId());
                        // 判断是否为父级节点
                        List < Category > nodesList = engCategoryService.findPublishTree(ca.getId(), null);
                        if(nodesList != null && nodesList.size() > 0) {
                            ct.setIsParent("true");
                        }
                        // 判断是否被选中
                        ct.setChecked(isChecked(cheIds,ca.getId()));
                        allCategories.add(ct);
                    }
                }
            }
            return JSON.toJSONString(allCategories);
        } else {
            List < CategoryTree > allCategories = new ArrayList < CategoryTree > ();
            if(category.getId() == null) {
                DictionaryData parent = dictionaryDataServiceI.getDictionaryData(categoryId);
                CategoryTree ct = new CategoryTree();
                ct.setName(parent.getName());
                ct.setId(parent.getId());
                ct.setIsParent("true");
                allCategories.add(ct);
            } else {
                List < Category > childNodes = categoryService.findPublishTree(category.getId(), null);
                if(childNodes != null && childNodes.size() > 0) {
                    for(Category ca: childNodes) {
                        CategoryTree ct = new CategoryTree();
                        ct.setName(ca.getName());
                        ct.setId(ca.getId());
                        ct.setParentId(ca.getParentId());
                        // 判断是否为父级节点
                        List < Category > nodesList = categoryService.findPublishTree(ca.getId(), null);
                        if(nodesList != null && nodesList.size() > 0) {
                            ct.setIsParent("true");
                        }
                     // 判断是否被选中
                        ct.setChecked(isChecked(cheIds,ca.getId()));
                        allCategories.add(ct);
                    }
                }
            }
            return JSON.toJSONString(allCategories);
        }
    }
    
    
    
    /**
     * 
     * Description: 验证项目编码唯一
     * 
     * @author zhang shubin
     * @data 2017年9月19日
     * @param 
     * @return
     */
    @ResponseBody
    @RequestMapping("/vaProjectCode")
    public String vaProjectCode(String code){
        return expertExtractProjectService.vaProjectCode(code);
    }
    
    /**
     * 
     * Description: 判断是否被选中
     * 
     * @author zhang shubin
     * @data 2017年9月22日
     * @param 
     * @return
     */
    public boolean isChecked(String[] allCategoryList,String typeCode) {
        if(allCategoryList != null && allCategoryList.length > 0){
            for (String s : allCategoryList) {
                if (s.equals(typeCode)){
                    return true;
                }
            }
        }
        return false;
    }

}
