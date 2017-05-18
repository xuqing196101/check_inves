package common.dao;

import java.util.List;
import java.util.Map;

import ses.model.bms.Analyze;
import common.model.RegisterAnalyze;
/**
 * 
* @ClassName: LoginAnalyzeMapper 
* @Description: 注册统计Mapper
* @author Easong
* @date 2017年5月11日 下午5:21:45 
*
 */
public interface RegisterAnalyzeMapper {
    int deleteByPrimaryKey(String id);

    int insert(RegisterAnalyze record);
    /**
     * 
    * @Title: insertSelective 
    * @Description: 保存注册统计
    * @author Easong
    * @param @param record
    * @param @return    设定文件 
    * @return int    返回类型 
    * @throws
     */
    int insertSelective(RegisterAnalyze record);

    RegisterAnalyze selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RegisterAnalyze record);

    int updateByPrimaryKey(RegisterAnalyze record);
    
    /**
     * 
    * @Title: analyzeRegisterCountByDay 
    * @Description: 注册按天统计
    * @author Easong
    * @param @param map
    * @param @return    设定文件 
    * @return List<Analyze>    返回类型 
    * @throws
     */
    public List<Analyze> analyzeRegisterCountByDay(Map<String, Object> map);
    
    /**
     * 
    * @Title: analyzeRegisterCountByWeek 
    * @Description: 注册按周统计
    * @author Easong
    * @param @param map
    * @param @return    设定文件 
    * @return List<Analyze>    返回类型 
    * @throws
     */
    public List<Analyze> analyzeRegisterCountByWeek(Map<String, Object> map);
    
    /**
     * 
    * @Title: analyzeRegisterCountByMonth 
    * @Description: 注册按月统计
    * @author Easong
    * @param @param map
    * @param @return    设定文件 
    * @return List<Analyze>    返回类型 
    * @throws
     */
    public List<Analyze> analyzeRegisterCountByMonth(Map<String, Object> map);
}