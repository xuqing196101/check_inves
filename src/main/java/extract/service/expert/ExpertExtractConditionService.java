package extract.service.expert;

import java.util.HashMap;
import java.util.Map;

import extract.model.expert.ExpertExtractCateInfo;
import extract.model.expert.ExpertExtractCondition;
import extract.model.expert.ExpertExtractProject;

/**
 * 
 * Description: 专家抽取条件
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public interface ExpertExtractConditionService {

    /**
     * 
     * Description: 添加非空数据 
     * 
     * @author zhang shubin
     * @data 2017年9月11日
     * @param 
     * @return
     * @throws ClassNotFoundException 
     * @throws Exception 
     */
    ExpertExtractCondition save(String proId,ExpertExtractCondition expertExtractCondition,ExpertExtractCateInfo expertExtractCateInfo) throws Exception;

    /**
     * 
     * Description: 专家抽取查询专家
     * 
     * @author zhang shubin
     * @data 2017年9月8日
     * @param 
     * @return
     * @throws Exception 
     */
    Map<String, Object> findExpertByExtract(ExpertExtractProject expertExtractProject,ExpertExtractCondition expertExtractCondition,ExpertExtractCateInfo expertExtractCateInfo) throws Exception;

    /**
     * 条件查询
     * @param cmap
     * @return
     */
    ExpertExtractCondition getByMap(HashMap<Object, Object> cmap);
}
