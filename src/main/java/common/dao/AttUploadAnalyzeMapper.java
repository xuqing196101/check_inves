package common.dao;

import java.util.List;
import java.util.Map;

import ses.model.bms.Analyze;
import common.model.AttUploadAnalyze;
/**
 * 
* @ClassName: LoginAnalyzeMapper 
* @Description: 文件上传统计Mapper
* @author Easong
* @date 2017年5月11日 下午5:21:45 
*
 */
public interface AttUploadAnalyzeMapper {
    int deleteByPrimaryKey(String id);

    int insert(AttUploadAnalyze record);

    /**
     * 
    * @Title: insertSelective 
    * @Description: 保存文件上传统计
    * @author Easong
    * @param @param record
    * @param @return    设定文件 
    * @return int    返回类型 
    * @throws
     */
    int insertSelective(AttUploadAnalyze record);

    AttUploadAnalyze selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AttUploadAnalyze record);

    int updateByPrimaryKey(AttUploadAnalyze record);
    
    /**
     * 
    * @Title: analyzeAttUploadCountByDay 
    * @Description: 注册按天统计
    * @author Easong
    * @param @param map
    * @param @return    设定文件 
    * @return List<Analyze>    返回类型 
    * @throws
     */
    public List<Analyze> analyzeAttUploadCountByDay(Map<String, Object> map);
    
    /**
     * 
    * @Title: analyzeAttUploadCountByWeek 
    * @Description: 注册按周统计
    * @author Easong
    * @param @param map
    * @param @return    设定文件 
    * @return List<Analyze>    返回类型 
    * @throws
     */
    public List<Analyze> analyzeAttUploadCountByWeek(Map<String, Object> map);
    
    /**
     * 
    * @Title: analyzeAttUploadCountByMonth 
    * @Description: 注册按月统计
    * @author Easong
    * @param @param map
    * @param @return    设定文件 
    * @return List<Analyze>    返回类型 
    * @throws
     */
    public List<Analyze> analyzeAttUploadCountByMonth(Map<String, Object> map);
}