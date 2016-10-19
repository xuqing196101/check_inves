package ses.dao.ems;

import java.util.List;
import java.util.Map;

import ses.model.ems.ExpExtCondition;
import ses.model.ems.Expert;


public interface ExpertMapper {
    int deleteByPrimaryKey(String id);

    int insert(Expert record);

    int insertSelective(Expert record);

    Expert selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Expert record);

    int updateByPrimaryKey(Expert record);
    
   // List<Expert> selectLoginNameList(String loginName);
    /**
     * 
      * @Title: getCount
      * @author ShaoYangYang
      * @date 2016年9月12日 下午3:58:35  
      * @Description: TODO 查询专家待审核数量
      * @param @param expert
      * @param @return      
      * @return Integer
     */
    Integer getCount(Expert expert);
    /**
     * 
      * @Title: selectAllExpert
      * @author lkzx 
      * @date 2016年9月2日 下午5:42:05  
      * @Description: TODO 查询所有专家
      * @param @return      
      * @return List<Expert>
     */
    List<Expert> selectAllExpert(Map paramMap);
    
    List<Expert> findExpertList();
    
    /**
     * @Title: findExpertAll
     * @author Xu Qing
     * @date 2016-10-12 下午7:42:52  
     * @Description: 查询专家 
     * @param @return      
     * @return List<Expert>
     */
    List<Expert> findExpertAll(Expert expert);
    
    /**
     * @Description: 抽取供应商
     *
     * @author Wang Wenshuai
     * @version 2016年10月14日 下午2:55:31  
     * @param @return      
     * @return List<Expert>
     */
    List<Expert> listExtractionExpert(ExpExtCondition con);

}