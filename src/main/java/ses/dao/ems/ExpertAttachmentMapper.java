package ses.dao.ems;

import java.util.List;

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
      * @Title: selectListByExpertId
      * @author ShaoYangYang
      * @date 2016年9月29日 上午11:06:59  
      * @Description: TODO 根据专家id查询附件集合
      * @param @param expertId
      * @param @return      
      * @return List<ExpertAttachment>
     */
    List<ExpertAttachment> selectListByExpertId(String expertId);
}