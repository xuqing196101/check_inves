package extract.service.expert.impl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExpertBlackListMapper;
import ses.dao.ems.ExpertCategoryMapper;
import ses.dao.ems.ExpertMapper;
import ses.model.bms.DictionaryData;
import ses.model.ems.Expert;
import ses.model.ems.ExpertBlackList;
import ses.util.DictionaryDataUtil;
import extract.dao.expert.ExpertExtractConditionMapper;
import extract.dao.expert.ExpertExtractResultMapper;
import extract.dao.expert.ExpertExtractTypeInfoMapper;
import extract.dao.expert.ExtractCategoryMapper;
import extract.model.expert.ExpertExtractCateInfo;
import extract.model.expert.ExpertExtractCondition;
import extract.model.expert.ExpertExtractTypeInfo;
import extract.model.expert.ExtractCategory;
import extract.service.expert.ExpertExtractConditionService;

/**
 * 
 * Description: 专家抽取条件
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
@Service("expertExtractConditionService")
public class ExpertExtractConditionServiceImpl implements ExpertExtractConditionService {

    //专家抽取条件
    @Autowired
    private ExpertExtractConditionMapper expertExtractConditionMapper;

    //抽取专家类型条件信息
    @Autowired
    private ExpertExtractTypeInfoMapper expertExtractTypeInfoMapper;

    //专家抽取产品类别信息
    @Autowired
    private ExtractCategoryMapper extractCategoryMapper;

    //专家产品信息
    @Autowired
    private ExpertCategoryMapper expertCategoryMapper;

    //专家抽取结果
    @Autowired
    private ExpertExtractResultMapper expertExtractResultMapper;

    //专家黑名单
    @Autowired
    private ExpertBlackListMapper expertBlackListMapper;

    //专家查询
    @Autowired
    private ExpertMapper expertMapper;

    /**
     * 保存抽取条件
     * @throws ClassNotFoundException 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    @Override
    public ExpertExtractCondition save(ExpertExtractCondition expertExtractCondition,ExpertExtractCateInfo expertExtractCateInfo) throws Exception {
        String uuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        expertExtractCondition.setProjectId(expertExtractCondition.getId());
        expertExtractCondition.setId(uuid);
        String kind = expertExtractCondition.getExpertKindId();
        if(kind != null && kind.indexOf(",") >= 0){
            String[] typeCodes = kind.split(",");
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < typeCodes.length; i++) {
                if(i != 0){
                    sb.append(","+DictionaryDataUtil.getId(typeCodes[i]));
                }else{
                    sb.append(DictionaryDataUtil.getId(typeCodes[i]));
                }
            }
            expertExtractCondition.setExpertKindId(sb.toString());
        }else{
            expertExtractCondition.setExpertKindId(DictionaryDataUtil.getId(kind));
        }
        expertExtractCondition.setCreatedAt(new Date());
        expertExtractCondition.setUpdatedAt(new Date());
        expertExtractCondition.setIsDeleted((short) 0);
        expertExtractConditionMapper.insertSelective(expertExtractCondition);
        //插入品目条件信息
        @SuppressWarnings("rawtypes")
        Class c = Class.forName("extract.model.expert.ExpertExtractCateInfo");
        String[] typeCodes = kind.split(",");
        for (String typeCode : typeCodes) {
            ExpertExtractTypeInfo expertExtractTypeInfo = new ExpertExtractTypeInfo();
            String id = UUID.randomUUID().toString().toUpperCase().replace("-", "");
            expertExtractTypeInfo.setId(id);
            //获取总人数
            Field field1 = c.getDeclaredField(typeCode.toLowerCase()+"_i_count");
            field1.setAccessible(true); //设置些属性是可以访问的  
            String sCount = (String)field1.get(expertExtractCateInfo);
            if(sCount != null && !sCount.equals("")){
                BigDecimal countPerson = new BigDecimal(sCount);
                expertExtractTypeInfo.setCountPerson(countPerson);
            }
            //获取技术职称 
            Field field2 = c.getDeclaredField(typeCode.toLowerCase()+"_technical");
            field2.setAccessible(true); //设置些属性是可以访问的  
            String technicalTitle = (String)field2.get(expertExtractCateInfo);
            expertExtractTypeInfo.setTechnicalTitle(technicalTitle);
            expertExtractTypeInfo.setConditionId(uuid);
            expertExtractTypeInfo.setExpertTypeCode(typeCode);
            //获取是否同时满足
            Field field3 = c.getDeclaredField(typeCode.toLowerCase()+"_isSatisfy");
            field3.setAccessible(true); //设置些属性是可以访问的  
            String isS = (String)field3.get(expertExtractCateInfo);
            if(isS != null && !isS.equals("")){
                Short isSatisfy = new Short(isS);
                expertExtractTypeInfo.setIsSatisfy(isSatisfy);
            }
            //专家类别为工程
            if(typeCode.indexOf("PROJECT") >= 0){
                //工程执业资格
                Field field5 = c.getDeclaredField(typeCode.toLowerCase()+"_qualification");
                field5.setAccessible(true); //设置些属性是可以访问的  
                String qualification = (String)field5.get(expertExtractCateInfo);
                expertExtractTypeInfo.setEngQualification(qualification);
                //是否同时满足工程专业信息
                Field field6 = c.getDeclaredField(typeCode.toLowerCase()+"_eng_isSatisfy");
                field6.setAccessible(true); //设置些属性是可以访问的  
                String engIs = (String)field6.get(expertExtractCateInfo);
                if(engIs != null && !engIs.equals("")){
                    Short engIsSatisfy = new Short(engIs);
                    expertExtractTypeInfo.setEngIsSatisfy(engIsSatisfy);
                }
            }
            expertExtractTypeInfo.setCreatedAt(new Date());
            expertExtractTypeInfo.setUpdatedAt(new Date());
            expertExtractTypeInfo.setIsDeleted((short) 0);
            expertExtractTypeInfoMapper.insertSelective(expertExtractTypeInfo);
            //附加产品目录
            Field field4 = c.getDeclaredField(typeCode.toLowerCase()+"_type");
            field4.setAccessible(true); //设置些属性是可以访问的  
            String categoryIds = (String)field4.get(expertExtractCateInfo);
            if(categoryIds != null && !categoryIds.equals("")){
                String[] categoryId = categoryIds.split(",");
                for (String str : categoryId) {
                    ExtractCategory extractCategory = new ExtractCategory();
                    String cid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
                    extractCategory.setId(cid);
                    extractCategory.setConditionId(uuid);
                    extractCategory.setCategoryId(str);
                    extractCategory.setTypeId(DictionaryDataUtil.getId(typeCode));
                    extractCategory.setIsDeleted((short) 0);
                    extractCategoryMapper.insertSelective(extractCategory);
                }
            }
            //专家类别为工程  工程特有的工程专业信息
            if(typeCode.indexOf("PROJECT") >= 0){
                //工程执业资格
                Field field6 = c.getDeclaredField(typeCode.toLowerCase()+"_eng_info");
                field6.setAccessible(true); //设置些属性是可以访问的  
                String engInfo = (String)field6.get(expertExtractCateInfo);
                if(!"".equals(engInfo)){
                    String[] engInfoCategory = engInfo.split(",");
                    for (String str : engInfoCategory) {
                        ExtractCategory extractCategory = new ExtractCategory();
                        String cid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
                        extractCategory.setId(cid);
                        extractCategory.setConditionId(uuid);
                        extractCategory.setCategoryId(str);
                        extractCategory.setTypeId(DictionaryDataUtil.getId(typeCode));
                        extractCategory.setIsDeleted((short) 0);
                        extractCategory.setIsEng((short) 1);
                        extractCategoryMapper.insertSelective(extractCategory);
                    }
                }
            }
        }
        return expertExtractCondition;
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
        if(expertExtractCondition.getExpertKindId() != null){
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
                //工程特有
                if(typeCode.indexOf("PROJECT") >= 0){
                	Set<String> expertEngIds = new HashSet<>();
                    //工程执业资格
                    Field field2 = c.getDeclaredField(typeCode.toLowerCase()+"_qualification");
                    field2.setAccessible(true); //设置些属性是可以访问的  
                    String qualification = (String)field2.get(expertExtractCateInfo);
                    map.put("qualification",qualification);
                    //工程专业信息
                    Field field3 = c.getDeclaredField(typeCode.toLowerCase()+"_eng_info");
                    field3.setAccessible(true); //设置些属性是可以访问的  
                    String engCategoryIds = (String)field3.get(expertExtractCateInfo);
                    if(!"".equals(engCategoryIds)){
                    	Field field4 = c.getDeclaredField(typeCode.toLowerCase()+"_eng_isSatisfy");
                        field4.setAccessible(true); //设置些属性是可以访问的  
                        String engIsSatisfy = (String)field4.get(expertExtractCateInfo);
                        if(engIsSatisfy != null && engIsSatisfy.equals("1")){
                            String[] categoryId = engCategoryIds.split(",");
                            for (String str : categoryId) {
                                Map<String, Object> cateMap = new HashMap<>();
                                cateMap.put("categoryId", str);
                                cateMap.put("typeId", DictionaryDataUtil.getId("ENG_INFO_ID"));
                                List<String> expertIdList = expertCategoryMapper.selExpertByCategory(cateMap);
                                expertEngIds.addAll(expertIdList);
                            }
                        }
                    }
                    map.put("expertEngIds",expertEngIds);
                    map.put("engSize",expertEngIds.size());
                }
                //筛选 去掉已经被抽过的专家
                List<String> notExpertIds = new ArrayList<String>();
                if(expertExtractCondition.getId() != null){
                    List<String> findByConditionId = expertExtractResultMapper.findByConditionId(expertExtractCondition.getId());
                    notExpertIds.addAll(findByConditionId);
                }
                //筛选专家黑名单中的专家
                List<ExpertBlackList> blackList = expertBlackListMapper.findAllBlackListExpert(0);
                if(blackList != null && blackList.size() > 0){
                    for (ExpertBlackList ebl : blackList) {
                        if( !"".equals(ebl.getExpertId())){
                            notExpertIds.add(ebl.getExpertId());
                        }
                    }
                }
                map.put("notExpertIds",notExpertIds);
                map.put("notSize",notExpertIds.size());
                //技术职称
                Field field2 = c.getDeclaredField(typeCode.toLowerCase()+"_technical");
                field2.setAccessible(true); //设置些属性是可以访问的  
                String technical = (String)field2.get(expertExtractCateInfo);
                map.put("technical",technical);
                List<Expert> expertList = expertMapper.findExpertByExtract(map);
                expertList = getExpertTypes(expertList);
                resultMap.put(typeCode, expertList);
            }
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
