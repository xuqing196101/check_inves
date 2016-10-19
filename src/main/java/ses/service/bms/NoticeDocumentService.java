/**
 * 
 */
package ses.service.bms;

import java.util.List;
import ses.model.bms.NoticeDocument;
/**
 * @Title:NoticeDocumentService 
 * @Description: 
 * @author Liyi
 * @date 2016-10-18下午3:48:09
 *
 */
public interface NoticeDocumentService {
	
	/*
	 * 1.获取所有须知文档
	 */
	List<NoticeDocument> getAll(Integer pageNum);
	
	/*
	 * 2.添加须知文档
	 */
	void save(NoticeDocument noticeDocument);
	
	/*
	 * 3.更新须知文档
	 */
	void update(NoticeDocument noticeDocument);
	
	/*
	 * 4.根据主键查询须知文档
	 */
	NoticeDocument get(String id);
	
	/*
	 * 5.根据主键删除须知文档
	 */
	void delete(String id);
	
	/*
	 * 6.条件搜索
	 */
	List<NoticeDocument> search(Integer pageNum,NoticeDocument noticeDocument);
}
