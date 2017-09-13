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
import ses.model.ems.ExtConType;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.EngCategoryService;
import ses.util.DictionaryDataUtil;

import com.alibaba.fastjson.JSON;

import extract.model.expert.ExpertExtractCateInfo;
import extract.model.expert.ExpertExtractCondition;
import extract.model.expert.ExpertExtractProject;
import extract.service.expert.ExpertExtractConditionService;
import extract.service.expert.ExpertExtractProjectService;


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

    /**
     * 
     * Description: 跳转专家人共抽取页面
     * 
     * @author zhang shubin
     * @data 2017年9月4日
     * @param 
     * @return
     */
    @RequestMapping("/toExpertExtract")
    public String toExpertExtract(Model model){
        //采购方式
        List<DictionaryData> purchaseWayList = new ArrayList<>();
        purchaseWayList.add(DictionaryDataUtil.get("JZXTP"));
        purchaseWayList.add(DictionaryDataUtil.get("XJCG"));
        purchaseWayList.add(DictionaryDataUtil.get("YQZB"));
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
        return "ses/ems/exam/expert/extract/condition_list";
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
    public String saveProjectInfo(ExpertExtractProject expertExtractProject,ExpertExtractCondition expertExtractCondition,ExpertExtractCateInfo expertExtractCateInfo) throws Exception{
        //保存项目基本信息
        expertExtractProjectService.save(expertExtractProject);
         //查询抽取结果信息
        Map<String, Object> result = expertExtractProjectService.findExpertByExtract(expertExtractCondition,expertExtractCateInfo);
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
    public String getCount(ExpertExtractCondition expertExtractCondition,ExpertExtractCateInfo expertExtractCateInfo) throws Exception{
        Map<String, Object> result = expertExtractProjectService.findExpertByExtract(expertExtractCondition,expertExtractCateInfo);
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
    public String getExpert(ExpertExtractCondition expertExtractCondition,ExpertExtractCateInfo expertExtractCateInfo,String conId) throws Exception{
        expertExtractCondition.setId(conId);
        Map<String, Object> result = expertExtractProjectService.findExpertByExtract(expertExtractCondition,expertExtractCateInfo);
        return JSON.toJSONString(result);
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
    public String addHeading(Model model, String[] id, String type) {
        ExtConType extConType = null;
        if (id != null && id.length != 0) {
            extConType = new ExtConType();
            extConType.setCategoryId(id[0]);
            extConType.setExpertsCount(Integer.parseInt(id[2]));
            extConType.setExpertsQualification(id[3]);
        }
        model.addAttribute("type", type);
        model.addAttribute("extConType", extConType);
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
    public String getTree(String code,Category category) {
        if (code != null && code.equals("GOODS_PROJECT")) {
            code = "PROJECT";
        }
        if(code.equals("GOODS_SERVER")){
            return "";
        }
        String categoryId = DictionaryDataUtil.getId(code);
        if (code != null && code.equals("ENG_INFO_ID")) {
            List < CategoryTree > allCategories = new ArrayList < CategoryTree > ();
            if(category.getId() == null) {
                DictionaryData parent = dictionaryDataServiceI.getDictionaryData(categoryId);
                CategoryTree ct = new CategoryTree();
                ct.setName(parent.getName());
                ct.setId(parent.getId());
                ct.setIsParent("true");
                allCategories.add(ct);
            } else {
                List < Category > tempNodes = engCategoryService.findPublishTree(category.getId(), null);
                if(tempNodes != null && tempNodes.size() > 0) {
                    for(Category ca: tempNodes) {
                        CategoryTree ct = new CategoryTree();
                        ct.setName(ca.getName());
                        ct.setId(ca.getId());
                        ct.setParentId(ca.getParentId());
                        // 判断是否为父级节点
                        List < Category > nodesList = engCategoryService.findPublishTree(ca.getId(), null);
                        if(nodesList != null && nodesList.size() > 0) {
                            ct.setIsParent("true");
                        }
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
                        allCategories.add(ct);
                    }
                }
            }
            return JSON.toJSONString(allCategories);
        }
    }
}
