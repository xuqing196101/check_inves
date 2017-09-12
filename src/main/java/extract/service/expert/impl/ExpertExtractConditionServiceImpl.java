package extract.service.expert.impl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.DictionaryDataUtil;
import extract.dao.expert.ExpertExtractConditionMapper;
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
    
	/**
     * 保存抽取条件
     * @throws ClassNotFoundException 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    @Override
    public void save(ExpertExtractCondition expertExtractCondition,ExpertExtractCateInfo expertExtractCateInfo) throws Exception {
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
            	Short isSatisfy = new Short((String)field3.get(expertExtractCateInfo));
            	expertExtractTypeInfo.setIsSatisfy(isSatisfy);
            }
            //专家类别为工程
            if(typeCode.indexOf("PROJECT") >= 0){
            	//工程执业资格
            	Field field5 = c.getDeclaredField(typeCode.toLowerCase()+"_qualification");
                field5.setAccessible(true); //设置些属性是可以访问的  
                String qualification = (String)field5.get(expertExtractCateInfo);
                expertExtractTypeInfo.setEngQualification(qualification);
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
    }
}
