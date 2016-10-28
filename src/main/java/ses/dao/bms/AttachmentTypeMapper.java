package ses.dao.bms;

import java.util.List;

import ses.model.bms.AttachmentType;

public interface AttachmentTypeMapper {
    
    int delete(String id);

    int insert(AttachmentType attachmentType);

    List<AttachmentType> findList(AttachmentType attachmentType);

    int update(AttachmentType attachmentType);

}