package common.dao;

import java.util.List;
import java.util.Map;

import ses.model.bms.Analyze;
import common.model.LoginAnalyze;
/**
 * 
* @ClassName: LoginAnalyzeMapper 
* @Description: 登录统计Mapper
* @author Easong
* @date 2017年5月11日 下午5:21:45 
*
 */
public interface LoginAnalyzeMapper {
    int deleteByPrimaryKey(String id);

    int insert(LoginAnalyze record);

    /**
     * 
    * @Title: insertSelective 
    * @Description: 保存登录统计
    * @author Easong
    * @param @param record
    * @param @return    设定文件 
    * @return int    返回类型 
    * @throws
     */
    int insertSelective(LoginAnalyze record);

    LoginAnalyze selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LoginAnalyze record);

    int updateByPrimaryKey(LoginAnalyze record);
    
    /**
     * 
    * @Title: analyzeLoginCountByDay 
    * @Description: 按日统计
    * @author Easong
    * @param @param map
    * @param @return    设定文件 
    * @return List<Analyze>    返回类型 
    * @throws
     */
    public List<Analyze> analyzeLoginCountByDay(Map<String, Object> map);
    
    /**
     * 
    * @Title: analyzeLoginCountByWeek 
    * @Description: 按周统计
    * @author Easong
    * @param @param map
    * @param @return    设定文件 
    * @return List<Analyze>    返回类型 
    * @throws
     */
    public List<Analyze> analyzeLoginCountByWeek(Map<String, Object> map);
    
    /**
     * 
    * @Title: analyzeLoginCountByMonth 
    * @Description: 按月统计
    * @author Easong
    * @param @param map
    * @param @return    设定文件 
    * @return List<Analyze>    返回类型 
    * @throws
     */
    public List<Analyze> analyzeLoginCountByMonth(Map<String, Object> map);
    
}