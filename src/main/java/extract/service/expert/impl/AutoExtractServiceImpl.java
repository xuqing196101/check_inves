package extract.service.expert.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ses.dao.bms.AreaMapper;
import ses.model.bms.Area;
import ses.model.ems.Expert;
import ses.model.ems.ProjectExtract;
import ses.util.DictionaryDataUtil;

import com.alibaba.fastjson.JSON;

import extract.autoVoiceExtract.Epoint005WebService;
import extract.autoVoiceExtract.PeopleYytz;
import extract.autoVoiceExtract.ProjectYytz;
import extract.dao.expert.ExpertExtractConditionMapper;
import extract.dao.expert.ExpertExtractProjectMapper;
import extract.dao.expert.ExpertExtractResultMapper;
import extract.dao.expert.ExpertExtractTypeInfoMapper;
import extract.dao.expert.ExtractCategoryMapper;
import extract.model.expert.ExpertExtractCateInfo;
import extract.model.expert.ExpertExtractCondition;
import extract.model.expert.ExpertExtractProject;
import extract.model.expert.ExpertExtractResult;
import extract.model.expert.ExpertExtractTypeInfo;
import extract.model.expert.ExpertResult;
import extract.model.expert.ProjectVoiceResult;
import extract.service.expert.AutoExtractService;
import extract.service.expert.ExpertExtractConditionService;
import extract.util.DateUtils;
import extract.util.WebServiceUtil;

/**
 * 
 * Description: 专家自动抽取
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
@Service("autoExtractService")
public class AutoExtractServiceImpl implements AutoExtractService {

    @Autowired
    private ExpertExtractProjectMapper expertExtractProjectMapper;

    @Autowired
    private ExpertExtractResultMapper expertExtractResultMapper;
    
    @Autowired
    private ExpertExtractConditionMapper expertExtractConditionMapper;
    
    @Autowired
    private ExpertExtractTypeInfoMapper expertExtractTypeInfoMapper;
    
    @Autowired
    private ExtractCategoryMapper extractCategoryMapper;
    
    @Autowired
    private ExpertExtractConditionService expertExtractConditionService;

    //地区
    @Autowired
    private AreaMapper areaMapper;

    @Override
    public String expertResultUpload(String result) throws Exception {
        Map<Object, Class<ExpertResult>> cMap = new HashMap<Object, Class<ExpertResult>>();
        cMap.put("expertList", ExpertResult.class);
        ProjectVoiceResult projectVoiceResult = (ProjectVoiceResult)JSONObject.toBean(JSONObject.fromObject(result),ProjectVoiceResult.class,cMap);
        List<ExpertResult> expertList = projectVoiceResult.getExpertResult();
        //查询抽取的项目信息 
        ExpertExtractProject expertExtractProject = expertExtractProjectMapper.selectByPrimaryKey(projectVoiceResult.getRecordeId());
        for (ExpertResult expertResult : expertList) {
            //唯一标识  电话
            String mobile = expertResult.getExpertId();
            List<String> expertIdList = expertExtractProjectMapper.selExppertIdByMobile(mobile);
            String expertId = "";
            if(expertIdList != null && expertIdList.size() > 0){
                expertId = expertIdList.get(0);
            }
            ExpertExtractResult expertExtractResult = new ExpertExtractResult();
            expertExtractResult.setProjectId(projectVoiceResult.getRecordeId());
            expertExtractResult.setExpertId(expertId);
            expertExtractResult.setUpdatedAt(new Date());
            //接口的状态码跟本地不同  所以要先转换一下
            if(expertResult.getJoin() == 0){
                //待定
                expertExtractResult.setIsJoin((short)2);
                expertExtractResultMapper.updateByProjectIdandExpertId(expertExtractResult);
            }else if(expertResult.getJoin() == 1){
                //确认参加
                expertExtractResult.setIsJoin((short)1);
                expertExtractResultMapper.updateByProjectIdandExpertId(expertExtractResult);
            }else if(expertResult.getJoin() == 2){
                //拒绝参加
                expertExtractResult.setIsJoin((short)3);
                expertExtractResultMapper.updateByProjectIdandExpertId(expertExtractResult);
            }else if(expertResult.getJoin() == 8){
                //请假   如果专家请假   删除这个专家
                expertExtractResult.setIsJoin((short)3);
                expertExtractResult.setIsDeleted((short)1);
                expertExtractResultMapper.updateByProjectIdandExpertId(expertExtractResult);
            }
            //标识是否是从项目实施进入的抽取
            if(expertExtractProject != null && expertExtractProject.getPackageId() != null){
            	String[] packageIds = expertExtractProject.getPackageId().split(",");
        	    ProjectExtract projectExtract = new ProjectExtract();
        	    for (String packageId : packageIds) {
        	    	projectExtract.setProjectId(packageId);
        	    	projectExtract.setExpertId(expertId);
        	    	projectExtract.setUpdatedAt(new Date());
        	    	projectExtract.setOperatingType(expertExtractResult.getIsJoin());
        	    	projectExtract.setIsDeleted(expertExtractResult.getIsDeleted());
        	    	expertExtractResultMapper.updateProjectByEId(projectExtract);
        	    }
            }
        }
        //查询抽取条件
        List<ExpertExtractCondition> conditionList = expertExtractConditionMapper.selByProjectId(expertExtractProject.getId());
        ExpertExtractCondition expertExtractCondition = new ExpertExtractCondition();
        if(conditionList != null && conditionList.size() > 0){
            expertExtractCondition = conditionList.get(0);
        }
        //记录每个类别还需要抽取的正式专家人数
        Map<String, Object> countMap = new HashMap<>();
        //记录每个类别还需要抽取的候补专家人数
        Map<String, Object> hbCountMap = new HashMap<>();
        ExpertExtractCateInfo expertExtractCateInfo = new ExpertExtractCateInfo();
        if(expertExtractCondition.getExpertKindId() != null){
            //记录候补专家个数
            int num = 0;
            String[] kindIds = expertExtractCondition.getExpertKindId().split(",");
            if(expertExtractCondition.getIsExtractAlternate() != null && expertExtractCondition.getIsExtractAlternate() == 1){
                if(kindIds.length == 2){
                    num = 1;
                }else if(kindIds.length == 1){
                    num = 2;
                }
            }
            //查询不同类型的抽取品目条件
            for (String str : kindIds) {
                String code = DictionaryDataUtil.findById(str) == null ? "" : DictionaryDataUtil.findById(str).getCode();
                ExpertExtractTypeInfo expertExtractTypeInfo22 = new ExpertExtractTypeInfo();
                expertExtractTypeInfo22.setConditionId(expertExtractCondition.getId());
                expertExtractTypeInfo22.setExpertTypeCode(code);
                ExpertExtractTypeInfo expertExtractTypeInfo = new ExpertExtractTypeInfo();
                List<ExpertExtractTypeInfo> expertExtractTypeInfoList= expertExtractTypeInfoMapper.selectByTypeInfo(expertExtractTypeInfo22);
                if(expertExtractTypeInfoList != null && expertExtractTypeInfoList.size() > 0){
                    expertExtractTypeInfo = expertExtractTypeInfoList.get(0);
                }
                if(code.equals("GOODS")){
                    //物资
                    expertExtractCateInfo.setGoods_i_count(expertExtractTypeInfo.getCountPerson().toString());
                    //产品类别
                    Map<String, Object> caMap = new HashMap<>();
                    caMap.put("conditionId", expertExtractCondition.getId());
                    caMap.put("typeId", str);
                    caMap.put("isEng", 0);
                    List<String> caList = extractCategoryMapper.selByMap(caMap);
                    String categoryIds = StringUtils.collectionToDelimitedString(caList, ",");
                    expertExtractCateInfo.setGoods_type(categoryIds == null ? "" : categoryIds);
                    expertExtractCateInfo.setGoods_technical(expertExtractTypeInfo.getTechnicalTitle() == null ? "" : expertExtractTypeInfo.getTechnicalTitle());
                    expertExtractCateInfo.setGoods_isSatisfy(expertExtractTypeInfo.getIsSatisfy() == null ? "" : expertExtractTypeInfo.getIsSatisfy().toString());
                }
                if(code.equals("GOODS_SERVER")){
                    //物资服务经济
                    expertExtractCateInfo.setGoods_server_i_count(expertExtractTypeInfo.getCountPerson().toString());
                    expertExtractCateInfo.setGoods_server_technical(expertExtractTypeInfo.getTechnicalTitle() == null ? "" : expertExtractTypeInfo.getTechnicalTitle());
                    expertExtractCateInfo.setGoods_server_isSatisfy(expertExtractTypeInfo.getIsSatisfy() == null ? "" : expertExtractTypeInfo.getIsSatisfy().toString());
                }
                if(code.equals("SERVICE")){
                    //服务
                    expertExtractCateInfo.setService_i_count(expertExtractTypeInfo.getCountPerson().toString());
                    //产品类别
                    Map<String, Object> caMap = new HashMap<>();
                    caMap.put("conditionId", expertExtractCondition.getId());
                    caMap.put("typeId", str);
                    caMap.put("isEng", 0);
                    List<String> caList = extractCategoryMapper.selByMap(caMap);
                    String categoryIds = StringUtils.collectionToDelimitedString(caList, ",");
                    expertExtractCateInfo.setService_type(categoryIds == null ? "" : categoryIds);
                    expertExtractCateInfo.setService_technical(expertExtractTypeInfo.getTechnicalTitle() == null ? "" : expertExtractTypeInfo.getTechnicalTitle());
                    expertExtractCateInfo.setService_isSatisfy(expertExtractTypeInfo.getIsSatisfy() == null ? "" : expertExtractTypeInfo.getIsSatisfy().toString());
                }
                if(code.equals("PROJECT")){
                    //工程
                    expertExtractCateInfo.setProject_i_count(expertExtractTypeInfo.getCountPerson().toString());
                    //产品类别
                    Map<String, Object> caMap = new HashMap<>();
                    caMap.put("conditionId", expertExtractCondition.getId());
                    caMap.put("typeId", str);
                    caMap.put("isEng", 0);
                    List<String> caList = extractCategoryMapper.selByMap(caMap);
                    String categoryIds = StringUtils.collectionToDelimitedString(caList, ",");
                    expertExtractCateInfo.setProject_type(categoryIds == null ? "" : categoryIds);
                    expertExtractCateInfo.setProject_technical(expertExtractTypeInfo.getTechnicalTitle() == null ? "" : expertExtractTypeInfo.getTechnicalTitle());
                    expertExtractCateInfo.setProject_isSatisfy(expertExtractTypeInfo.getIsSatisfy() == null ? "" : expertExtractTypeInfo.getIsSatisfy().toString());
                    //工程专业信息
                    Map<String, Object> engMap = new HashMap<>();
                    caMap.put("conditionId", expertExtractCondition.getId());
                    caMap.put("typeId", str);
                    caMap.put("isEng", 1);
                    List<String> engList = extractCategoryMapper.selByMap(engMap);
                    String engCategoryIds = StringUtils.collectionToDelimitedString(engList, ",");
                    expertExtractCateInfo.setProject_eng_info(engCategoryIds == null ? "" : engCategoryIds);
                    //是否同时满足
                    expertExtractCateInfo.setProject_eng_isSatisfy(expertExtractTypeInfo.getEngIsSatisfy() == null ? "" : expertExtractTypeInfo.getEngIsSatisfy().toString());
                    //工程执业资格
                    expertExtractCateInfo.setProject_qualification(expertExtractTypeInfo.getEngQualification() == null ? "" : expertExtractTypeInfo.getEngQualification());
                }
                if(code.equals("GOODS_PROJECT")){
                    //工程经济
                    expertExtractCateInfo.setGoods_project_i_count(expertExtractTypeInfo.getCountPerson().toString());
                    //产品类别
                    Map<String, Object> caMap = new HashMap<>();
                    caMap.put("conditionId", expertExtractCondition.getId());
                    caMap.put("typeId", str);
                    caMap.put("isEng", 0);
                    List<String> caList = extractCategoryMapper.selByMap(caMap);
                    String categoryIds = StringUtils.collectionToDelimitedString(caList, ",");
                    expertExtractCateInfo.setGoods_project_type(categoryIds == null ? "" : categoryIds);
                    expertExtractCateInfo.setGoods_project_technical(expertExtractTypeInfo.getTechnicalTitle() == null ? "" : expertExtractTypeInfo.getTechnicalTitle());
                    expertExtractCateInfo.setGoods_project_isSatisfy(expertExtractTypeInfo.getIsSatisfy() == null ? "" : expertExtractTypeInfo.getIsSatisfy().toString());
                    //工程专业信息
                    Map<String, Object> engMap = new HashMap<>();
                    caMap.put("conditionId", expertExtractCondition.getId());
                    caMap.put("typeId", str);
                    caMap.put("isEng", 1);
                    List<String> engList = extractCategoryMapper.selByMap(engMap);
                    String engCategoryIds = StringUtils.collectionToDelimitedString(engList, ",");
                    expertExtractCateInfo.setGoods_project_eng_info(engCategoryIds == null ? "" : engCategoryIds);
                    //是否同时满足
                    expertExtractCateInfo.setGoods_project_eng_isSatisfy(expertExtractTypeInfo.getEngIsSatisfy() == null ? "" : expertExtractTypeInfo.getEngIsSatisfy().toString());
                    //工程执业资格
                    expertExtractCateInfo.setGoods_project_qualification(expertExtractTypeInfo.getEngQualification() == null ? "" : expertExtractTypeInfo.getEngQualification());
                }
                Map<String, Object> conMap = new HashMap<>();
                conMap.put("conditionId", expertExtractCondition.getId());
                conMap.put("expertCode",code);
                countMap.put(code, expertExtractTypeInfo.getCountPerson().intValue() - expertExtractResultMapper.selNumByConditionId(conMap));
                if(num != 0){
                    conMap.put("isAlternate",1);
                    hbCountMap.put(code+"_hb", num - expertExtractResultMapper.selNumByConditionId(conMap));
                }
            }
        }
        //判断人数是否足够  是否需要继续抽取
        boolean flag = true;
        for (Object v : countMap.values()) {
            if((Integer)v > 0){
                flag = false;
                break;
            }
        }
        if(flag){
            for (Object v : hbCountMap.values()) {
                if((Integer)v > 0){
                    flag = false;
                    break;
                }
            }
        }
        if(flag){
            //不需要继续抽取  将项目状态改成抽取完成
            Map<String, Object> projectMap = new HashMap<>();
            projectMap.put("status", 2);
            projectMap.put("projectId", expertExtractProject.getId());
            projectMap.put("updatedAt", new Date());
            expertExtractProjectMapper.updataStatus(projectMap);
            return "OK";
        }else{
            //需要继续抽取
            String str = autoVoice(expertExtractProject, expertExtractCondition, expertExtractCateInfo, countMap, hbCountMap);
            return str;
        }
    }

    /**
     * 专家自动抽取 调用语音接口
     */
    @Override
    public String expertAutoExtract(String projectId) {
        //记录每个类别还需要抽取的正式专家人数
        Map<String, Object> countMap = new HashMap<>();
        //记录每个类别还需要抽取的候补专家人数
        Map<String, Object> hbCountMap = new HashMap<>();
        //查询抽取的项目信息 
        ExpertExtractProject expertExtractProject = expertExtractProjectMapper.selectByPrimaryKey(projectId);
        //查询抽取条件
        List<ExpertExtractCondition> conditionList = expertExtractConditionMapper.selByProjectId(expertExtractProject.getId());
        ExpertExtractCondition expertExtractCondition = new ExpertExtractCondition();
        if(conditionList != null && conditionList.size() > 0){
            expertExtractCondition = conditionList.get(0);
        }
        ExpertExtractCateInfo expertExtractCateInfo = new ExpertExtractCateInfo();
        if(expertExtractCondition.getExpertKindId() != null){
            String[] kindIds = expertExtractCondition.getExpertKindId().split(",");
            //查询不同类型的抽取品目条件
            for (String str : kindIds) {
                String code = DictionaryDataUtil.findById(str) == null ? "" : DictionaryDataUtil.findById(str).getCode();
                if(expertExtractCondition.getIsExtractAlternate() != null && expertExtractCondition.getIsExtractAlternate() == 1){
                    if(kindIds.length == 2){
                        hbCountMap.put(code+"_hb", 1);
                    }else if(kindIds.length == 1){
                        hbCountMap.put(code+"_hb", 2);
                    }else{
                        hbCountMap.put(code+"_hb", 0);
                    }
                }
                ExpertExtractTypeInfo expertExtractTypeInfo22 = new ExpertExtractTypeInfo();
                expertExtractTypeInfo22.setConditionId(expertExtractCondition.getId());
                expertExtractTypeInfo22.setExpertTypeCode(code);
                ExpertExtractTypeInfo expertExtractTypeInfo = new ExpertExtractTypeInfo();
                List<ExpertExtractTypeInfo> expertExtractTypeInfoList= expertExtractTypeInfoMapper.selectByTypeInfo(expertExtractTypeInfo22);
                if(expertExtractTypeInfoList != null && expertExtractTypeInfoList.size() > 0){
                    expertExtractTypeInfo = expertExtractTypeInfoList.get(0);
                }
                countMap.put(code, expertExtractTypeInfo.getCountPerson().intValue());
                if(code.equals("GOODS")){
                    //物资
                    expertExtractCateInfo.setGoods_i_count(expertExtractTypeInfo.getCountPerson().toString());
                    //产品类别
                    Map<String, Object> caMap = new HashMap<>();
                    caMap.put("conditionId", expertExtractCondition.getId());
                    caMap.put("typeId", str);
                    caMap.put("isEng", 0);
                    List<String> caList = extractCategoryMapper.selByMap(caMap);
                    String categoryIds = StringUtils.collectionToDelimitedString(caList, ",");
                    expertExtractCateInfo.setGoods_type(categoryIds == null ? "" : categoryIds);
                    expertExtractCateInfo.setGoods_technical(expertExtractTypeInfo.getTechnicalTitle() == null ? "" : expertExtractTypeInfo.getTechnicalTitle());
                    expertExtractCateInfo.setGoods_isSatisfy(expertExtractTypeInfo.getIsSatisfy() == null ? "" : expertExtractTypeInfo.getIsSatisfy().toString());
                }
                if(code.equals("GOODS_SERVER")){
                    //物资服务经济
                    expertExtractCateInfo.setGoods_server_i_count(expertExtractTypeInfo.getCountPerson().toString());
                    expertExtractCateInfo.setGoods_server_technical(expertExtractTypeInfo.getTechnicalTitle() == null ? "" : expertExtractTypeInfo.getTechnicalTitle());
                    expertExtractCateInfo.setGoods_server_isSatisfy(expertExtractTypeInfo.getIsSatisfy() == null ? "" : expertExtractTypeInfo.getIsSatisfy().toString());
                }
                if(code.equals("SERVICE")){
                    //服务
                    expertExtractCateInfo.setService_i_count(expertExtractTypeInfo.getCountPerson().toString());
                    //产品类别
                    Map<String, Object> caMap = new HashMap<>();
                    caMap.put("conditionId", expertExtractCondition.getId());
                    caMap.put("typeId", str);
                    caMap.put("isEng", 0);
                    List<String> caList = extractCategoryMapper.selByMap(caMap);
                    String categoryIds = StringUtils.collectionToDelimitedString(caList, ",");
                    expertExtractCateInfo.setService_type(categoryIds == null ? "" : categoryIds);
                    expertExtractCateInfo.setService_technical(expertExtractTypeInfo.getTechnicalTitle() == null ? "" : expertExtractTypeInfo.getTechnicalTitle());
                    expertExtractCateInfo.setService_isSatisfy(expertExtractTypeInfo.getIsSatisfy() == null ? "" : expertExtractTypeInfo.getIsSatisfy().toString());
                }
                if(code.equals("PROJECT")){
                    //工程
                    expertExtractCateInfo.setProject_i_count(expertExtractTypeInfo.getCountPerson().toString());
                    //产品类别
                    Map<String, Object> caMap = new HashMap<>();
                    caMap.put("conditionId", expertExtractCondition.getId());
                    caMap.put("typeId", str);
                    caMap.put("isEng", 0);
                    List<String> caList = extractCategoryMapper.selByMap(caMap);
                    String categoryIds = StringUtils.collectionToDelimitedString(caList, ",");
                    expertExtractCateInfo.setProject_type(categoryIds == null ? "" : categoryIds);
                    expertExtractCateInfo.setProject_technical(expertExtractTypeInfo.getTechnicalTitle() == null ? "" : expertExtractTypeInfo.getTechnicalTitle());
                    expertExtractCateInfo.setProject_isSatisfy(expertExtractTypeInfo.getIsSatisfy() == null ? "" : expertExtractTypeInfo.getIsSatisfy().toString());
                    //工程专业信息
                    Map<String, Object> engMap = new HashMap<>();
                    caMap.put("conditionId", expertExtractCondition.getId());
                    caMap.put("typeId", str);
                    caMap.put("isEng", 1);
                    List<String> engList = extractCategoryMapper.selByMap(engMap);
                    String engCategoryIds = StringUtils.collectionToDelimitedString(engList, ",");
                    expertExtractCateInfo.setProject_eng_info(engCategoryIds == null ? "" : engCategoryIds);
                    //是否同时满足
                    expertExtractCateInfo.setProject_eng_isSatisfy(expertExtractTypeInfo.getEngIsSatisfy() == null ? "" : expertExtractTypeInfo.getEngIsSatisfy().toString());
                    //工程执业资格
                    expertExtractCateInfo.setProject_qualification(expertExtractTypeInfo.getEngQualification() == null ? "" : expertExtractTypeInfo.getEngQualification());
                }
                if(code.equals("GOODS_PROJECT")){
                    //工程经济
                    expertExtractCateInfo.setGoods_project_i_count(expertExtractTypeInfo.getCountPerson().toString());
                    //产品类别
                    Map<String, Object> caMap = new HashMap<>();
                    caMap.put("conditionId", expertExtractCondition.getId());
                    caMap.put("typeId", str);
                    caMap.put("isEng", 0);
                    List<String> caList = extractCategoryMapper.selByMap(caMap);
                    String categoryIds = StringUtils.collectionToDelimitedString(caList, ",");
                    expertExtractCateInfo.setGoods_project_type(categoryIds == null ? "" : categoryIds);
                    expertExtractCateInfo.setGoods_project_technical(expertExtractTypeInfo.getTechnicalTitle() == null ? "" : expertExtractTypeInfo.getTechnicalTitle());
                    expertExtractCateInfo.setGoods_project_isSatisfy(expertExtractTypeInfo.getIsSatisfy() == null ? "" : expertExtractTypeInfo.getIsSatisfy().toString());
                    //工程专业信息
                    Map<String, Object> engMap = new HashMap<>();
                    caMap.put("conditionId", expertExtractCondition.getId());
                    caMap.put("typeId", str);
                    caMap.put("isEng", 1);
                    List<String> engList = extractCategoryMapper.selByMap(engMap);
                    String engCategoryIds = StringUtils.collectionToDelimitedString(engList, ",");
                    expertExtractCateInfo.setGoods_project_eng_info(engCategoryIds == null ? "" : engCategoryIds);
                    //是否同时满足
                    expertExtractCateInfo.setGoods_project_eng_isSatisfy(expertExtractTypeInfo.getEngIsSatisfy() == null ? "" : expertExtractTypeInfo.getEngIsSatisfy().toString());
                    //工程执业资格
                    expertExtractCateInfo.setGoods_project_qualification(expertExtractTypeInfo.getEngQualification() == null ? "" : expertExtractTypeInfo.getEngQualification());
                }
            }
        }
        String str = "";
        try {
            str = autoVoice(expertExtractProject, expertExtractCondition, expertExtractCateInfo, countMap, hbCountMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(str);
    }

    /**
     * 
     * Description: 调用语音接口进行抽取
     * 
     * @author zhang shubin
     * @data 2017年10月19日
     * @param 
     * @return
     */
    public String autoVoice(ExpertExtractProject expertExtractProject,ExpertExtractCondition expertExtractCondition,ExpertExtractCateInfo expertExtractCateInfo,Map<String, Object> countMap,Map<String, Object> hbCountMap) throws Exception{
        Map<String, Object> resultMap = expertExtractConditionService.findExpertByExtract(expertExtractProject,expertExtractCondition,expertExtractCateInfo);
        Epoint005WebService service = WebServiceUtil.getService();
        Client proxy = ClientProxy.getClient(service);
        HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
        HTTPClientPolicy policy = new HTTPClientPolicy();
        policy.setConnectionTimeout(30000); // 连接超时时间
        policy.setReceiveTimeout(180000);// 请求超时时间
        conduit.setClient(policy);
        List<PeopleYytz> peoplelist = new ArrayList<PeopleYytz>();
        //标识是否是从项目实施进入的抽取
        boolean xmssFlag = false;
        if(expertExtractProject.getPackageId() != null){
            xmssFlag = true;
        }
        if(expertExtractCondition.getExpertKindId() != null){
            String[] kindIds = expertExtractCondition.getExpertKindId().split(",");
            for (String typeCode : kindIds) {
                String code = DictionaryDataUtil.findById(typeCode) == null ? "" : DictionaryDataUtil.findById(typeCode).getCode();
                @SuppressWarnings("unchecked")
                List<Expert> reList = (List<Expert>) resultMap.get(code);
                //正式专家
                int zs = (int)countMap.get(code);
                if(zs > 0){
                    PeopleYytz peopleYytz = new PeopleYytz();
                    for (int i = 0; i < zs; i++) {
                        Expert expert = reList.get(i);
                        peopleYytz.setMobile(expert.getMobile());
                        peopleYytz.setUsername(expert.getRelName());
                        peopleYytz.setIscandidate("0");
                        peoplelist.add(peopleYytz);
                        //保存抽取到的专家信息
                        ExpertExtractResult expertExtractResult = new ExpertExtractResult();
                        String uuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
                        expertExtractResult.setId(uuid);
                        expertExtractResult.setProjectId(expertExtractProject != null ? expertExtractProject.getId() : "");
                        expertExtractResult.setExpertId(expert.getId());
                        expertExtractResult.setConditionId(expertExtractCondition != null ? expertExtractCondition.getId() : "");
                        expertExtractResult.setReviewTime(expertExtractProject != null ? expertExtractProject.getReviewTime() : null);
                        expertExtractResult.setIsAlternate(null);
                        expertExtractResult.setExpertCode(code);
                        expertExtractResult.setIsDeleted((short) 0);
                        expertExtractResult.setCreatedAt(new Date());
                        expertExtractResult.setUpdatedAt(new Date());
                        expertExtractResultMapper.insertSelective(expertExtractResult);
                        //从项目实施进入的
                        if(xmssFlag){
                            String[] packageIds = expertExtractProject.getPackageId().split(",");
                            for (String packageId : packageIds) {
                                Map<String, Object> proMap = new HashMap<>();
                                proMap.put("packageId", packageId);
                                proMap.put("expertId", expertExtractResult.getExpertId());
                                List<ProjectExtract> proList = expertExtractResultMapper.findByPackageId(proMap);
                                ProjectExtract projectExtract = new ProjectExtract();
                                if(proList != null && proList.size() > 0){
                                    //修改
                                    projectExtract = proList.get(0);
                                    projectExtract.setUpdatedAt(new Date());
                                    projectExtract.setProjectId(packageId);
                                    projectExtract.setExpertId(expertExtractResult.getExpertId());
                                    projectExtract.setReason(expertExtractResult.getReason());
                                    projectExtract.setReviewType(DictionaryDataUtil.getId(expertExtractResult.getExpertCode() == null ? "" : expertExtractResult.getExpertCode()));
                                    projectExtract.setOperatingType(expertExtractResult.getIsJoin());
                                    projectExtract.setIsProvisional(expertExtractResult.getIsAlternate());
                                    projectExtract.setExpertConditionId(expertExtractResult.getConditionId());
                                    expertExtractResultMapper.updateProject(projectExtract);
                                }else{
                                    //新增
                                    String uuuuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
                                    projectExtract.setId(uuuuid);
                                    projectExtract.setProjectId(packageId);
                                    projectExtract.setIsDeleted((short) 0);
                                    projectExtract.setCreatedAt(new Date());
                                    projectExtract.setUpdatedAt(new Date());
                                    projectExtract.setExpertId(expertExtractResult.getExpertId());
                                    projectExtract.setReason(expertExtractResult.getReason());
                                    projectExtract.setReviewType(DictionaryDataUtil.getId(expertExtractResult.getExpertCode() == null ? "" : expertExtractResult.getExpertCode()));
                                    projectExtract.setOperatingType(expertExtractResult.getIsJoin());
                                    projectExtract.setIsProvisional(expertExtractResult.getIsAlternate());
                                    projectExtract.setExpertConditionId(expertExtractResult.getConditionId());
                                    expertExtractResultMapper.insertProject(projectExtract);
                                }
                            }
                        }
                    }
                }
                //候补专家
                int hb = hbCountMap.get(code+"_hb") == null ? 0 : (int)hbCountMap.get(code+"_hb");
                if(hb > 0){
                    PeopleYytz peopleYytz = new PeopleYytz();
                    for (int i = zs; i < zs+hb; i++) {
                        Expert expert = reList.get(i);
                        peopleYytz.setMobile(expert.getMobile());
                        peopleYytz.setUsername(expert.getRelName());
                        peopleYytz.setIscandidate("1");
                        peoplelist.add(peopleYytz);
                        //保存抽取到的专家信息
                        ExpertExtractResult expertExtractResult = new ExpertExtractResult();
                        String uuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
                        expertExtractResult.setId(uuid);
                        expertExtractResult.setProjectId(expertExtractProject != null ? expertExtractProject.getId() : "");
                        expertExtractResult.setExpertId(expert.getId());
                        expertExtractResult.setConditionId(expertExtractCondition != null ? expertExtractCondition.getId() : "");
                        expertExtractResult.setReviewTime(expertExtractProject != null ? expertExtractProject.getReviewTime() : null);
                        expertExtractResult.setIsAlternate((short)1);
                        expertExtractResult.setExpertCode(code);
                        expertExtractResult.setIsDeleted((short) 0);
                        expertExtractResult.setCreatedAt(new Date());
                        expertExtractResult.setUpdatedAt(new Date());
                        expertExtractResultMapper.insertSelective(expertExtractResult);
                        //从项目实施进入的
                        if(xmssFlag){
                            String[] packageIds = expertExtractProject.getPackageId().split(",");
                            for (String packageId : packageIds) {
                                Map<String, Object> proMap = new HashMap<>();
                                proMap.put("packageId", packageId);
                                proMap.put("expertId", expertExtractResult.getExpertId());
                                List<ProjectExtract> proList = expertExtractResultMapper.findByPackageId(proMap);
                                ProjectExtract projectExtract = new ProjectExtract();
                                if(proList != null && proList.size() > 0){
                                    //修改
                                    projectExtract = proList.get(0);
                                    projectExtract.setUpdatedAt(new Date());
                                    projectExtract.setProjectId(packageId);
                                    projectExtract.setExpertId(expertExtractResult.getExpertId());
                                    projectExtract.setReason(expertExtractResult.getReason());
                                    projectExtract.setReviewType(DictionaryDataUtil.getId(expertExtractResult.getExpertCode() == null ? "" : expertExtractResult.getExpertCode()));
                                    projectExtract.setOperatingType(expertExtractResult.getIsJoin());
                                    projectExtract.setIsProvisional(expertExtractResult.getIsAlternate());
                                    projectExtract.setExpertConditionId(expertExtractResult.getConditionId());
                                    expertExtractResultMapper.updateProject(projectExtract);
                                }else{
                                    //新增
                                    String uuuuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
                                    projectExtract.setId(uuuuid);
                                    projectExtract.setProjectId(packageId);
                                    projectExtract.setIsDeleted((short) 0);
                                    projectExtract.setCreatedAt(new Date());
                                    projectExtract.setUpdatedAt(new Date());
                                    projectExtract.setExpertId(expertExtractResult.getExpertId());
                                    projectExtract.setReason(expertExtractResult.getReason());
                                    projectExtract.setReviewType(DictionaryDataUtil.getId(expertExtractResult.getExpertCode() == null ? "" : expertExtractResult.getExpertCode()));
                                    projectExtract.setOperatingType(expertExtractResult.getIsJoin());
                                    projectExtract.setIsProvisional(expertExtractResult.getIsAlternate());
                                    projectExtract.setExpertConditionId(expertExtractResult.getConditionId());
                                    expertExtractResultMapper.insertProject(projectExtract);
                                }
                            }
                        }
                    }
                }
            }
        }
        ProjectYytz projectYytz = new ProjectYytz();
        Area area1 = areaMapper.selectById(expertExtractProject != null ? expertExtractProject.getReviewProvince() : "");
        Area area2 = areaMapper.selectById(expertExtractProject != null ? expertExtractProject.getReviewAddress() : "");
        projectYytz.setAddress(area2 == null ? "" : area2.getName());
        projectYytz.setContactnum(expertExtractProject != null ? expertExtractProject.getContactNum() : "");
        projectYytz.setContactperson(expertExtractProject != null ? expertExtractProject.getContactPerson() : "");
        projectYytz.setProjectid(expertExtractProject != null ? expertExtractProject.getProjectId() : "");
        projectYytz.setProjectname(expertExtractProject != null ? expertExtractProject.getProjectName() : "");
        projectYytz.setProvince(area1 == null ? "" : area1.getName());
        projectYytz.setRecordid(expertExtractProject != null ? expertExtractProject.getId() : "");
        projectYytz.setReviewdays(expertExtractProject != null ? Integer.parseInt(expertExtractProject.getReviewDays() == null ? "0" : expertExtractProject.getReviewDays() ) : 0);
        projectYytz.setSite(expertExtractProject != null ? expertExtractProject.getReviewSite() : "");
        projectYytz.setStarttime(expertExtractProject != null ? DateUtils.dateToXmlDate(expertExtractProject.getReviewTime()) : null);
        projectYytz.getPeoplelist().addAll(peoplelist);
        // 标识""  代表专家
        String str = service.putObject(projectYytz, "E");
        return str;
    }
}
