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
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.EngCategoryService;
import ses.service.ems.ExpertService;
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
import extract.service.expert.AutoExtractService;
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
    
    /** 专家管理 **/
    @Autowired
    private ExpertService service;
    
    /** 自动抽取 **/
    @Autowired
    private AutoExtractService autoExtractService;
    
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
        //权限验证  采购机构  可以抽取
        String authType = null;
        if(null != user && ("1".equals(user.getTypeName()))){
            authType = user.getTypeName();
            //采购方式
            List<DictionaryData> purchaseWayList = new ArrayList<DictionaryData>();
            //公开招标
            purchaseWayList.add(DictionaryDataUtil.get("GKZB"));
            //邀请招标
            purchaseWayList.add(DictionaryDataUtil.get("YQZB"));
            //竞争性谈判
            purchaseWayList.add(DictionaryDataUtil.get("JZXTP"));
            //询价
            DictionaryData dictionaryData = DictionaryDataUtil.get("XJCG");
            dictionaryData.setName("询价");
            purchaseWayList.add(dictionaryData);
            //单一来源
            purchaseWayList.add(DictionaryDataUtil.get("DYLY"));
            model.addAttribute("purchaseWayList",purchaseWayList);
            //项目类型
            List<DictionaryData> projectTypeList = DictionaryDataUtil.find(6);
            model.addAttribute("projectTypeList",projectTypeList);
            //生成项目信息主键id
            String uuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
            model.addAttribute("projectId",uuid);
            String uuid22 = UUID.randomUUID().toString().toUpperCase().replace("-", "");
            model.addAttribute("conditionId",uuid22);
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
    public String saveProjectInfo(@CurrentUser User user,String conId,ExpertExtractProject expertExtractProject,ExpertExtractCondition expertExtractCondition,ExpertExtractCateInfo expertExtractCateInfo) throws Exception{
        //保存项目基本信息
        expertExtractProjectService.save(expertExtractProject,user);
        expertExtractCondition.setId(conId);
        //查询抽取结果信息
        Map<String, Object> result = expertExtractConditionService.findExpertByExtract(expertExtractProject,expertExtractCondition,expertExtractCateInfo);
        //保存抽取条件
        ExpertExtractCondition condition = expertExtractConditionService.save(expertExtractProject.getId(),expertExtractCondition,expertExtractCateInfo);
        result.put("conditionId", condition.getId());
        Short isAuto = expertExtractProject.getIsAuto();
        if(isAuto == 0){
            //人工抽取
            return JSON.toJSONString(result);
        }else{
            //自动抽取
            //String extractResult = expertExtractProjectService.expertAutoExtract(condition, expertExtractProject,expertExtractCateInfo, result);
            //将保存的数据信息同步至外网    便于外网抽取使用
            expertExtractProjectService.exportExpertExtract(expertExtractProject.getId());
            //请求语音接口   （仅测试用）
            //autoExtractService.expertAutoExtract(expertExtractProject.getId());
            return JSON.toJSONString("OK");
        }
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
     * Description: 品目搜索
     * 
     * @author zhang shubin
     * @data 2017年10月9日
     * @param 
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/searchCate", produces = "application/json;charset=utf-8")
    public String searchCate(String code, String cateName,String ids,String codeName) throws Exception {
        if (code != null && code.equals("GOODS_PROJECT")) {
            code = "PROJECT";
        }
        if(code.equals("GOODS_SERVER")){
            return "";
        }
        String[] cheIds = ids.split(",");
        if(code.indexOf("ENG_INFO_ID") > 0){
            code = "ENG_INFO_ID";
        }
        DictionaryData typeData = DictionaryDataUtil.get(code);
        String typeId = DictionaryDataUtil.getId(code);
        if("".equals(cateName)){
        	cateName = null;
        }
        if("".equals(codeName)){
        	codeName = null;
        }
        if (typeData != null && typeData.getCode().equals("ENG_INFO_ID")) {
            // 查询出所有满足条件的品目
            List < Category > categoryList = service.searchByName(cateName, "ENG_INFO", codeName);
            // 循环判断是不是当前树的节点
            List < Category > cateList = new ArrayList < Category > ();
            for(Category category: categoryList) {
                String parentId = getParentId(category.getId(), "ENG_INFO");
                if(parentId.equals(typeId)) {
                    cateList.add(category);
                }
            }
            // 去重
            removeSame(cateList);
            // 获取被选中的节点的父节点
            List < Category > allCateList = new ArrayList < Category > ();
            allCateList.addAll(cateList);
            for(Category category: cateList) {
                List < Category > list = getParentNodeList(category.getId(), "ENG_INFO");
                allCateList.addAll(list);
            }
            // 去重
            removeSame(allCateList);
            // 最后加入根节点
            DictionaryData data = DictionaryDataUtil.findById(typeId);
            Category root = new Category();
            root.setId(data.getId());
            root.setName(data.getName());
            root.setCode(data.getCode());
            allCateList.add(root);
            // 将筛选完的List转换为CategoryTreeList
            List < CategoryTree > treeList = new ArrayList < CategoryTree > ();
            for(Category category: allCateList) {
                CategoryTree treeNode = new CategoryTree();
                treeNode.setId(category.getId());
                treeNode.setName(category.getName());
                treeNode.setParentId(category.getParentId());
                // 判断是否为父级节点
                List < Category > nodesList = engCategoryService.findPublishTree(category.getId(), null);
                if(nodesList != null && nodesList.size() > 0) {
                    treeNode.setIsParent("true");
                }
                treeList.add(treeNode);
            }
            // 判断是否被选中
            for(CategoryTree treeNode: treeList) {
                treeNode.setChecked(isChecked(cheIds,treeNode.getId()));
            }
            return JSON.toJSONString(treeList);
        } else {
            String type = typeId;
            // 查询出所有满足条件的品目
            List < Category > categoryList = service.searchByName(cateName, null, codeName);
            // 循环判断是不是当前树的节点
            List < Category > cateList = new ArrayList < Category > ();
            for(Category category: categoryList) {
                String parentId = getParentId(category.getId(), null);
                if(parentId.equals(typeId)) {
                    cateList.add(category);
                }
            }
            // 去重
            removeSame(cateList);
            // 获取被选中的节点的父节点
            List < Category > allCateList = new ArrayList < Category > ();
            allCateList.addAll(cateList);
            for(Category category: cateList) {
                List < Category > list = getParentNodeList(category.getId(), null);
                allCateList.addAll(list);
            }
            // 去重
            removeSame(allCateList);
            // 最后加入根节点
            DictionaryData data = DictionaryDataUtil.findById(typeId);
            Category root = new Category();
            root.setId(data.getId());
            if("PRODUCT".equals(type)) {
                data.setName(data.getName());
            } else if("SALES".equals(type)) {
                data.setName(data.getName());
            }
            root.setName(data.getName());
            root.setCode(data.getCode());
            allCateList.add(root);
            // 将筛选完的List转换为CategoryTreeList
            List < CategoryTree > treeList = new ArrayList < CategoryTree > ();
            for(Category category: allCateList) {
                if(category.getLevel() != null && category.getLevel() >= 5){
                    continue;
                }
                CategoryTree treeNode = new CategoryTree();
                treeNode.setId(category.getId());
                treeNode.setName(category.getName());
                treeNode.setParentId(category.getParentId());
                treeNode.setCode(category.getCode());
                // 判断是否为父级节点
                List < Category > nodesList = categoryService.findPublishTree(category.getId(), null);
                if(nodesList != null && nodesList.size() > 0) {
                    treeNode.setIsParent("true");
                }
                if(category.getLevel() != null && category.getLevel() == 4){
                	treeNode.setIsParent("false");
                }
                treeList.add(treeNode);
            }
            // 判断是否被选中
            for(CategoryTree treeNode: treeList) {
                treeNode.setChecked(isChecked(cheIds,treeNode.getId()));
            }
            return JSON.toJSONString(treeList);
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
    public String vaProjectCode(String code,String xmProjectId){
        return expertExtractProjectService.vaProjectCode(code,xmProjectId);
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
    
    /**
     * 
     * Description: 品目去重复
     * 
     * @author zhang shubin
     * @data 2017年10月9日
     * @param 
     * @return
     */
    public void removeSame(List < Category > list) {
        for(int i = 0; i < list.size() - 1; i++) {
            for(int j = list.size() - 1; j > i; j--) {
                if(list.get(j).getId().equals(list.get(i).getId())) {
                    list.remove(j);
                }
            }
        }
    }
    
    /**
     * 
     * Description: 得到父节点
     * 
     * @author zhang shubin
     * @data 2017年10月9日
     * @param 
     * @return
     */
    public String getParentId(String cateId, String flag) {
        if (flag == null) {
            Category cate = categoryService.selectByPrimaryKey(cateId);
            if(cate != null) {
                cateId = getParentId(cate.getParentId(), null);
            }
            return cateId;
        } else {
            Category cate = engCategoryService.selectByPrimaryKey(cateId);
            if(cate != null) {
                cateId = getParentId(cate.getParentId(), "ENG_INFO");
            }
            return cateId;
        }
    }
    
    /**
     * 
     * Description: 去重父级节点,只保留子节点
     * 
     * @author zhang shubin
     * @data 2017年10月9日
     * @param 
     * @return
     */
    public void removeParentNodes(List < Category > list, String flag) {
        Category cate = null;
        List < Category > childrenList = new ArrayList < Category > ();
        for(int i = 0; i < list.size(); i++) {
            cate = list.get(i);
            if (flag == null) {
                childrenList = categoryService.findPublishTree(cate.getId(), null);
            } else {
                childrenList = engCategoryService.findPublishTree(cate.getId(), null);
            }
            if(childrenList.size() > 0) {
                list.remove(i);
            }
        }
    }

    /**
     * 
     * Description: 获取当前节点的所有父级节点
     * 
     * @author zhang shubin
     * @data 2017年10月9日
     * @param 
     * @return
     */
    public List < Category > getParentNodeList(String nodeId, String flag) {
        if (flag == null) {
            List < Category > parentNodeList = new ArrayList < Category > ();
            Category category = categoryService.findById(nodeId);
            if(category != null) {
                String parentId = category.getParentId();
                if(parentId != null && !"".equals(parentId)) {
                    Category cate = categoryService.findById(parentId);
                    if(cate != null) {
                        parentNodeList.add(cate);
                        List < Category > parentList = getParentNodeList(cate.getId(), null);
                        parentNodeList.addAll(parentList);
                    }
                }
            }
            return parentNodeList;
        } else {
            List < Category > parentNodeList = new ArrayList < Category > ();
            Category category = engCategoryService.findById(nodeId);
            if(category != null) {
                String parentId = category.getParentId();
                if(parentId != null && !"".equals(parentId)) {
                    Category cate = engCategoryService.findById(parentId);
                    if(cate != null) {
                        parentNodeList.add(cate);
                        List < Category > parentList = getParentNodeList(cate.getId(), "ENG_INFO");
                        parentNodeList.addAll(parentList);
                    }
                }
            }
            return parentNodeList;
        }
    }
}
