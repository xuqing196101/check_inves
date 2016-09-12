package ses.dao.ems;

import java.util.List;
import java.util.Map;

import ses.model.ems.Expert;


public interface ExpertMapper {
    int deleteByPrimaryKey(String id);

    int insert(Expert record);

    int insertSelective(Expert record);

    Expert selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Expert record);

    int updateByPrimaryKey(Expert record);
    
    List<Expert> selectLoginNameList(String loginName);
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
}