/**
 * 
 */
package ses.service.bms;

import java.util.List;
import java.util.Map;

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
	
	/**
	 * @Title: findSupplierDoc
	 * @author: Wang Zhaohua
	 * @date: 2016-11-10 下午3:43:22
	 * @Description: 查找须知文档
	 * @param: @return
	 * @return: List<String>
	 */
	String findDocByMap(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 查询须知文档名称
	 * 
	 * @author  zhang shubin
	 * @version  2017年4月10日 
	 * @param  @param map
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	String findDocNameByMap(Map<String, Object> map);
}
