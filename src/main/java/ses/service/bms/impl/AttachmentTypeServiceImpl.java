package ses.service.bms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.bms.AttachmentTypeMapper;
import ses.model.bms.AttachmentType;
import ses.service.bms.AttachmentTypeServiceI;
import ses.util.PropertiesUtil;

@Service("attachmentTypeService")
public class AttachmentTypeServiceImpl implements AttachmentTypeServiceI {

	@Autowired
	private AttachmentTypeMapper attachmentTypeMapper;

    @Override
    public List<AttachmentType> find(AttachmentType attachmentType) {
        List<AttachmentType> attachmentTypes = attachmentTypeMapper.findList(attachmentType);
        return attachmentTypes;
    }

    @Override
    public void delete(String id) {
        attachmentTypeMapper.delete(id);
    }

    @Override
    public void save(AttachmentType attachmentType) {
        attachmentTypeMapper.insert(attachmentType);
    }

    @Override
    public void update(AttachmentType attachmentType) {
        attachmentTypeMapper.update(attachmentType);
    }

    @Override
    public List<AttachmentType> listByPage(AttachmentType attachmentType, int page) {
        PropertiesUtil config = new PropertiesUtil("config.properties");
        PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
        List<AttachmentType> attachmentTypes = attachmentTypeMapper.findList(attachmentType);
        return attachmentTypes;
    }

}
