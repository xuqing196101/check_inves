/**
 * 
 */
package ses.service.bms.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ses.dao.bms.NoticeDocumentMapper;
import ses.model.bms.NoticeDocument;
import ses.service.bms.NoticeDocumentService;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

/**
 * @Title:NoticeDocumentServiceImpl 
 * @Description: 须知文档管理实现类
 * @author Liyi
 * @date 2016-10-18下午3:58:01
 *
 */
@Service("noticeDocumentService")
public class NoticeDocumentServiceImpl implements NoticeDocumentService{

	@Resource
	private NoticeDocumentMapper noticeDocumentMapper;
	
	@Override
	public List<NoticeDocument> getAll(Integer pageNum) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		return noticeDocumentMapper.queryByList();
	}

	
	@Override
	public void save(NoticeDocument noticeDocument) {
		noticeDocument.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		noticeDocumentMapper.insertSelective(noticeDocument);
	}

	@Override
	public void update(NoticeDocument noticeDocument) {
		noticeDocument.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		noticeDocumentMapper.updateByPrimaryKeySelective(noticeDocument);
	}

	@Override
	public NoticeDocument get(String id) {
		
		return noticeDocumentMapper.selectByPrimaryKey(id);
	}

	
	@Override
	public void delete(String id) {
		noticeDocumentMapper.deleteByPrimaryKey(id);
		
	}

	
	@Override
	public List<NoticeDocument> search(Integer pageNum,
			NoticeDocument noticeDocument) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		List<NoticeDocument> list = noticeDocumentMapper.selectByType(noticeDocument);
		return list;
	}

	
	/**
	 * @Title: findSupplierDoc
	 * @author: Wang Zhaohua
	 * @date: 2016-11-10 下午3:43:22
	 * @Description: 查找供应商须知文档
	 * @param: @return
	 * @return: List<String>
	 */
	@Override
	public String findDocByMap(Map<String, Object> map) {
		List<String> list = noticeDocumentMapper.findByMap(map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 查询须知文档名称
	 */
	@Override
	public String findDocNameByMap(Map<String, Object> map) {
		List<String> list = noticeDocumentMapper.findDocNameByMap(map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
