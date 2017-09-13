package extract.service.expert;

import java.util.List;
import java.util.Map;

import ses.model.bms.DictionaryData;
import extract.model.expert.ExpertExtractCateInfo;
import extract.model.expert.ExpertExtractCondition;
import extract.model.expert.ExpertExtractProject;

/**
 * 
 * Description: 专家抽取项目信息管理
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public interface ExpertExtractProjectService {

    /**
     * 
     * Description: 保存项目信息
     * 
     * @author zhang shubin
     * @data 2017年9月5日
     * @param 
     * @return
     */
    int save(ExpertExtractProject expertExtractProject);
    
    /**
     * 
     * Description: 根据项目类型加载专家类别
     * 
     * @author zhang shubin
     * @data 2017年9月6日
     * @param 
     * @return
     */
    List<DictionaryData> loadExpertKind(String id);
    
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
    Map<String, Object> findExpertByExtract(ExpertExtractCondition expertExtractCondition,ExpertExtractCateInfo expertExtractCateInfo) throws Exception;
}
