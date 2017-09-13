package extract.service.expert.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import extract.dao.expert.ExpertExtractResultMapper;
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

    /**
     * 保存抽取结果信息
     */
    @Override
    public void save(ExpertExtractResult expertExtractResult) {
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
    }

}
