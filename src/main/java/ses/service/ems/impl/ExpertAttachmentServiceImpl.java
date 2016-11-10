package ses.service.ems.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExpertAttachmentMapper;
import ses.model.ems.ExpertAttachment;
import ses.service.ems.ExpertAttachmentService;
import ses.util.FtpUtil;
@Service("expertAttachmentService")
public class ExpertAttachmentServiceImpl implements ExpertAttachmentService {
	@Autowired private ExpertAttachmentMapper mapper;
	
	 /**
     * 
      * @Title: selectListByExpertId
      * @author ShaoYangYang
      * @date 2016年9月29日 上午11:06:59  
      * @Description: TODO 根据条件查询附件集合
      * @param @param map
      * @param @return      
      * @return List<ExpertAttachment>
     */
	@Override
	public List<ExpertAttachment> selectListByMap(Map<String,Object> map) {
		// TODO Auto-generated method stub
		List<ExpertAttachment> list = mapper.selectListByMap(map);
		return list;
	}
	
}
