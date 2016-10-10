package ses.service.ems;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import ses.model.ems.ExpertAttachment;

public interface ExpertAttachmentService {
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
	    int deleteByPrimaryKey(String id);
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
	    int save(ExpertAttachment record);
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
	    int saveNotNull(ExpertAttachment record);
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
	    ExpertAttachment findById(String id);
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
	    int updateNotNull(ExpertAttachment record);
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
	    int updateAll(ExpertAttachment record);
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
	    /**
	     * 
	      * @Title: downloadFile
	      * @author ShaoYangYang
	      * @date 2016年9月29日 下午1:46:29  
	      * @Description: TODO 下载专家附件
	      * @param @param categoryId
	      * @param @return      
	      * @return ResponseEntity<byte[]>
	     */
	    ResponseEntity<byte[]> downloadFile(String attachmentId);
	    /**
	     * 
	      * @Title: ftpDownLoadFile
	      * @author ShaoYangYang
	      * @date 2016年10月10日 上午9:41:13  
	      * @Description: TODO ftp文件下载
	      * @param @param dir
	      * @param @param fileName      
	      * @return void
	     */
	    void ftpDownLoadFile(String attachmentId,HttpServletResponse response);
}
