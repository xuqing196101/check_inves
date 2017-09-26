package extract.service.expert.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.model.ems.ProjectExtract;
import extract.dao.expert.ExpertExtractProjectMapper;
import extract.dao.expert.ExpertExtractResultMapper;
import extract.model.expert.ExpertExtractProject;
import extract.model.expert.ExpertExtractResult;
import extract.service.expert.ExpertExtractResultService;

/**
 * 
 * Description: 专家抽取结果
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
@Service("expertExtractResultService")
public class ExpertExtractResultServiceImpl implements ExpertExtractResultService {

    /** 抽取结果 **/
    @Autowired
    private ExpertExtractResultMapper expertExtractResultMapper;
    
    /** 专家抽取项目信息 **/
    @Autowired
    private ExpertExtractProjectMapper expertExtractProjectMapper;
    
    /**
     * 保存抽取结果信息
     */
    @Override
    public void save(ExpertExtractResult expertExtractResult) {
    	//专家抽取结果保存
        Map<String, Object> map = new HashMap<>();
        map.put("conditionId", expertExtractResult.getConditionId());
        map.put("expertId", expertExtractResult.getExpertId());
        List<ExpertExtractResult> list = expertExtractResultMapper.findByConditionIdExpertId(map);
        if(list != null && list.size() > 0){
            expertExtractResult.setId(list.get(0).getId());
            expertExtractResult.setUpdatedAt(new Date());
            expertExtractResultMapper.updateByPrimaryKeySelective(expertExtractResult);
        }else{
            String uuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
            expertExtractResult.setId(uuid);
            expertExtractResult.setIsDeleted((short) 0);
            expertExtractResult.setCreatedAt(new Date());
            expertExtractResult.setUpdatedAt(new Date());
            expertExtractResultMapper.insertSelective(expertExtractResult);
        }
        //项目实施部分结果保存
        ExpertExtractProject expertExtractProject = expertExtractProjectMapper.selectByPrimaryKey(expertExtractResult.getProjectId());
        if(expertExtractProject != null && expertExtractProject.getPackageId() != null){
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
                	projectExtract.setReviewType(expertExtractResult.getExpertCode());
                	projectExtract.setOperatingType(expertExtractResult.getIsJoin());
                	projectExtract.setIsProvisional(expertExtractResult.getIsAlternate());
                	projectExtract.setExpertConditionId(expertExtractResult.getConditionId());
                	expertExtractResultMapper.updateProject(projectExtract);
                }else{
                	//新增
                	String uuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
                	projectExtract.setId(uuid);
                	projectExtract.setProjectId(packageId);
                	projectExtract.setIsDeleted((short) 0);
                	projectExtract.setCreatedAt(new Date());
                	projectExtract.setUpdatedAt(new Date());
                	projectExtract.setExpertId(expertExtractResult.getExpertId());
                	projectExtract.setReason(expertExtractResult.getReason());
                	projectExtract.setReviewType(expertExtractResult.getExpertCode());
                	projectExtract.setOperatingType(expertExtractResult.getIsJoin());
                	projectExtract.setIsProvisional(expertExtractResult.getIsAlternate());
                	projectExtract.setExpertConditionId(expertExtractResult.getConditionId());
                	expertExtractResultMapper.insertProject(projectExtract);
                }
            }
        }
    }

}
