package ses.dao.ems;

import java.util.List;
import java.util.Map;

import ses.model.ems.ExpertAttachment;

public interface ExpertAttachmentMapper {
    int deleteByPrimaryKey(String id);

    int insert(ExpertAttachment record);

    int insertSelective(ExpertAttachment record);

    ExpertAttachment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ExpertAttachment record);

    int updateByPrimaryKey(ExpertAttachment record);
    /**
     * 
      * @Title: selectListByMap
      * @author ShaoYangYang
      * @date 2016年11月8日 下午5:22:57  
      * @Description: TODO 条件查询
      * @param @param map
      * @param @return      
      * @return List<ExpertAttachment>
     */
    List<ExpertAttachment> selectListByMap(Map<String,Object> map);
}