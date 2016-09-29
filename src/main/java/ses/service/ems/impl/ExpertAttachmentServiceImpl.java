package ses.service.ems.impl;

import java.io.File;
import java.util.List;

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
@Service("expertAttachmentService")
public class ExpertAttachmentServiceImpl implements ExpertAttachmentService {
	@Autowired private ExpertAttachmentMapper mapper;
	/**
	 * 
	  * @Title: deleteByPrimaryKey
	  * @author ShaoYangYang
	  * @date 2016年9月29日 上午11:12:02  
	  * @Description: TODO 根据id删除
	  * @param @param id
	  * @param @return      
	  * @return int
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id);
	}
	 /**
     * 
      * @Title: insert
      * @author ShaoYangYang
      * @date 2016年9月29日 上午11:12:12  
      * @Description: TODO 新增
      * @param @param record
      * @param @return      
      * @return int
     */
	@Override
	public int save(ExpertAttachment record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}
	 /**
     * 
      * @Title: insertSelective
      * @author ShaoYangYang
      * @date 2016年9月29日 上午11:12:26  
      * @Description: TODO 新增不为空的
      * @param @param record
      * @param @return      
      * @return int
     */
	@Override
	public int saveNotNull(ExpertAttachment record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}
	 /**
     * 
      * @Title: selectByPrimaryKey
      * @author ShaoYangYang
      * @date 2016年9月29日 上午11:12:38  
      * @Description: TODO 根据id查询
      * @param @param id
      * @param @return      
      * @return ExpertAttachment
     */
	@Override
	public ExpertAttachment findById(String id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}
	/**
     * 
      * @Title: updateByPrimaryKeySelective
      * @author ShaoYangYang
      * @date 2016年9月29日 上午11:13:00  
      * @Description: TODO 更新不为空数据
      * @param @param record
      * @param @return      
      * @return int
     */
	@Override
	public int updateNotNull(ExpertAttachment record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKeySelective(record);
	}
	 /**
     * 
      * @Title: updateByPrimaryKey
      * @author ShaoYangYang
      * @date 2016年9月29日 上午11:13:34  
      * @Description: TODO 更新全部
      * @param @param record
      * @param @return      
      * @return int
     */
	@Override
	public int updateAll(ExpertAttachment record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}
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
	@Override
	public List<ExpertAttachment> selectListByExpertId(String expertId) {
		// TODO Auto-generated method stub
		List<ExpertAttachment> list = mapper.selectListByExpertId(expertId);
		
		return list;
	}
	/**
     * 
      * @Title: downloadFile
      * @author ShaoYangYang
      * @date 2016年9月29日 下午1:46:29  
      * @Description: TODO 根据附件id下载专家附件
      * @param @param categoryId
      * @param @return      
      * @return ResponseEntity<byte[]>
     */
	@Override
	
	public ResponseEntity<byte[]> downloadFile(String attachmentId){
    	 try {
    		 ExpertAttachment attachment = mapper.selectByPrimaryKey(attachmentId);
 			File file=new File(attachment.getFilePath()+"/"+attachment.getFileName());  
 			    HttpHeaders headers = new HttpHeaders(); 
 			   String downFileName=new String(attachment.getFileName().getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题  
 			    headers.setContentDispositionFormData("attachment",downFileName );   
 			    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
 			    ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED); 
 			    return entity;
 		} catch (Exception e) {
 			e.printStackTrace();
 			return null;
 		}
    }
}
