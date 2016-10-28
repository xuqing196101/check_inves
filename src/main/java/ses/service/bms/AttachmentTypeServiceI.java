package ses.service.bms;

import java.util.List;

import ses.model.bms.AttachmentType;

public interface AttachmentTypeServiceI {
    
    List<AttachmentType> find(AttachmentType attachmentType);
    
    void delete(String id);
    
    void save(AttachmentType attachmentType);
    
    void update(AttachmentType attachmentType);

    List<AttachmentType> listByPage(AttachmentType attachmentType, int i);

}
