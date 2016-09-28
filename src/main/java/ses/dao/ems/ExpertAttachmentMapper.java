package ses.dao.ems;

import ses.model.ems.ExpertAttachment;

public interface ExpertAttachmentMapper {
    int deleteByPrimaryKey(String id);

    int insert(ExpertAttachment record);

    int insertSelective(ExpertAttachment record);

    ExpertAttachment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ExpertAttachment record);

    int updateByPrimaryKey(ExpertAttachment record);
}