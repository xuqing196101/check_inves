package extract.service.expert;

import extract.model.expert.ExpertExtractResult;

/**
 * 
 * Description: 专家抽取结果
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public interface ExpertExtractResultService {

    /**
     * 
     * Description: 保存抽取结果信息
     * 
     * @author zhang shubin
     * @data 2017年9月12日
     * @param 
     * @return
     */
    void save(ExpertExtractResult expertExtractResult);
}
