package extract.service.expert.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.DictionaryDataMapper;
import ses.dao.ems.ExpertCategoryMapper;
import ses.dao.ems.ExpertMapper;
import ses.model.bms.DictionaryData;
import ses.model.ems.Expert;
import ses.util.DictionaryDataUtil;
import extract.dao.expert.ExpertExtractProjectMapper;
import extract.dao.expert.ExpertExtractResultMapper;
import extract.model.expert.ExpertExtractCateInfo;
import extract.model.expert.ExpertExtractCondition;
import extract.model.expert.ExpertExtractProject;
import extract.service.expert.ExpertExtractProjectService;

/**
 * 
 * Description: 专家抽取项目信息管理
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
@Service("expertExtractProjectService")
public class ExpertExtractProjectServiceImpl implements ExpertExtractProjectService {

    //专家抽取项目信息
    @Autowired
    private ExpertExtractProjectMapper expertExtractProjectMapper;

    //专家抽取结果
    @Autowired
    private ExpertExtractResultMapper expertExtractResultMapper;
    
    //数据字典
    @Autowired
    private DictionaryDataMapper dictionaryDataMapper;

    //专家查询
    @Autowired
    private ExpertMapper expertMapper;

    //专家产品信息
    @Autowired
    private ExpertCategoryMapper expertCategoryMapper;

    /**
     * 保存信息
     */
    @Override
    public int save(ExpertExtractProject expertExtractProject) {
        expertExtractProject.setCreatedAt(new Date());
        expertExtractProject.setUpdatedAt(new Date());
        expertExtractProject.setIsDeleted((short) 0);
        return expertExtractProjectMapper.insertSelective(expertExtractProject);
    }


    /**
     * 根据项目类型加载专家类别
     */
    @Override
    public List<DictionaryData> loadExpertKind(String id) {
        DictionaryData dictionaryData = DictionaryDataUtil.findById(id);
        String code = "";
        if(dictionaryData != null){
            code = dictionaryData.getCode();
        }
        List<DictionaryData> expertKindList = new ArrayList<>();
        if(code.endsWith("GOODS")){
            //物资    物资服务经济
            DictionaryData project = DictionaryDataUtil.get("GOODS");
            project.setName(project.getName() + "技术");
            expertKindList.add(project);
            expertKindList.add(DictionaryDataUtil.get("GOODS_SERVER"));
        }else if(code.endsWith("PROJECT")){
            //工程技术    工程经济
            DictionaryData project = DictionaryDataUtil.get("PROJECT");
            project.setName(project.getName() + "技术");
            expertKindList.add(project);
            expertKindList.add(DictionaryDataUtil.get("GOODS_PROJECT"));
        }else if(code.endsWith("SERVICE")){
            //服务
            DictionaryData project = DictionaryDataUtil.get("SERVICE");
            project.setName(project.getName() + "技术");
            expertKindList.add(project);
        }
        return expertKindList;
    }


    /**
     * 专家抽取查询专家
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    @Override
    @SuppressWarnings("rawtypes")
    public Map<String, Object> findExpertByExtract(ExpertExtractCondition expertExtractCondition,ExpertExtractCateInfo expertExtractCateInfo) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> resultMap = new HashMap<>();
        Class c = Class.forName("extract.model.expert.ExpertExtractCateInfo");
        //Integer count = 0;
        //判断专家类型 地方  军队
        if(expertExtractCondition.getExpertTypeId() != null && !("0").equals(expertExtractCondition.getExpertTypeId())){
            map.put("expertsFrom", expertExtractCondition.getExpertTypeId());
        }
        //区域要求
        if(expertExtractCondition.getAreaName() != null && !("0").equals(expertExtractCondition.getAreaName()) && !("").equals(expertExtractCondition.getAreaName())){
            String[] areaNames = expertExtractCondition.getAreaName().split(",");
            map.put("areaNames", areaNames);
        }
        //专家类别
        if(expertExtractCondition.getExpertKindId() != null && expertExtractCondition.getExpertKindId().indexOf(",") >= 0){
            String[] typeCodes = expertExtractCondition.getExpertKindId().split(",");
            for (String typeCode : typeCodes) {
                if(DictionaryDataUtil.get(typeCode) != null){
                    map.put("expertsTypeId", DictionaryDataUtil.get(typeCode).getId());
                }
                //附加产品目录
                Set<String> expertIds = new HashSet<>();
                Field field = c.getDeclaredField(typeCode.toLowerCase()+"_type");
                field.setAccessible(true); //设置些属性是可以访问的  
                String categoryIds = (String)field.get(expertExtractCateInfo);
                if(categoryIds != null && !categoryIds.equals("")){
                    //1  满足某一条件  2同时满足多个产品目录条件
                    Field field2 = c.getDeclaredField(typeCode.toLowerCase()+"_isSatisfy");
                    field2.setAccessible(true); //设置些属性是可以访问的  
                    String isSatisfy = (String)field2.get(expertExtractCateInfo);
                    if(isSatisfy != null && isSatisfy.equals("1")){
                        String[] categoryId = categoryIds.split(",");
                        for (String str : categoryId) {
                            Map<String, Object> cateMap = new HashMap<>();
                            cateMap.put("categoryId", str);
                            cateMap.put("typeId", DictionaryDataUtil.getId(typeCode));
                            List<String> expertIdList = expertCategoryMapper.selExpertByCategory(cateMap);
                            expertIds.addAll(expertIdList);
                        }
                    }
                }
                map.put("expertIds",expertIds);
                map.put("size",expertIds.size());
                //筛选 去掉已经被抽过的专家
                List<String> notExpertIds = new ArrayList<String>();
                if(expertExtractCondition.getId() != null){
                	List<String> findByConditionId = expertExtractResultMapper.findByConditionId(expertExtractCondition.getId());
                	notExpertIds.addAll(findByConditionId);
                }
                map.put("notExpertIds",notExpertIds);
                map.put("notSize",notExpertIds.size());
                List<Expert> expertList = expertMapper.findExpertByExtract(map);
                expertList = getExpertTypes(expertList);
                /*if(expertList != null){
                    count = expertList.size();
                }*/
                resultMap.put(typeCode, expertList);
            }
        }else{
            String typeCode = expertExtractCondition.getExpertKindId();
            if(DictionaryDataUtil.get(typeCode) != null){
                map.put("expertsTypeId", DictionaryDataUtil.get(typeCode).getId());
            }
            //附加产品目录
            Set<String> expertIds = new HashSet<>();
            Field field = c.getDeclaredField(typeCode.toLowerCase()+"_type");
            field.setAccessible(true); //设置些属性是可以访问的  
            String categoryIds = (String)field.get(expertExtractCateInfo);
            if(categoryIds != null && !categoryIds.equals("")){
                //1  满足某一条件  2同时满足多个产品目录条件
                Field field2 = c.getDeclaredField(typeCode.toLowerCase()+"_isSatisfy");
                field2.setAccessible(true); //设置些属性是可以访问的  
                String isSatisfy = (String)field2.get(expertExtractCateInfo);
                if(isSatisfy != null && isSatisfy.equals("1")){
                    String[] categoryId = categoryIds.split(",");
                    for (String str : categoryId) {
                        Map<String, Object> cateMap = new HashMap<>();
                        cateMap.put("categoryId", str);
                        cateMap.put("typeId", DictionaryDataUtil.getId(typeCode));
                        List<String> expertIdList = expertCategoryMapper.selExpertByCategory(cateMap);
                        expertIds.addAll(expertIdList);
                    }
                }
            }
            map.put("expertIds",expertIds);
            map.put("size",expertIds.size());
            //筛选 去掉已经被抽过的专家
            List<String> notExpertIds = new ArrayList<String>();
            if(expertExtractCondition.getId() != null){
            	List<String> findByConditionId = expertExtractResultMapper.findByConditionId(expertExtractCondition.getId());
            	notExpertIds.addAll(findByConditionId);
            }
            map.put("notExpertIds",notExpertIds);
            map.put("notSize",notExpertIds.size());
            List<Expert> expertList = expertMapper.findExpertByExtract(map);
            expertList = getExpertTypes(expertList);
            /*if(expertList != null){
                count = expertList.size();
            }*/
            resultMap.put(typeCode, expertList);
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 转换专家类型
     * 
     * @author zhang shubin
     * @data 2017年9月10日
     * @param 
     * @return
     */
    public List<Expert> getExpertTypes(List<Expert> expertList){
        for(Expert exp: expertList) {
            StringBuffer expertType = new StringBuffer();
            if(exp.getExpertsTypeId() != null) {
                for(String typeId: exp.getExpertsTypeId().split(",")) {
                    DictionaryData data = DictionaryDataUtil.findById(typeId);
                    if(data != null){
                        if(6 == data.getKind()) {
                            expertType.append(data.getName() + "技术、");
                        } else {
                            expertType.append(data.getName() + "、");
                        }
                    }
                }
                if(expertType.length() > 0){
                    String expertsType = expertType.toString().substring(0, expertType.length() - 1);
                     exp.setExpertsTypeId(expertsType);
                }
            } else {
                exp.setExpertsTypeId("");
            }
        }
        return expertList;
    }
}
