package ses.service.bms.impl;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.ArticleAttachmentsMapper;
import ses.model.bms.ArticleAttachments;
import ses.service.bms.ArticleAttachmentsService;


/*
 *@Title:ArticleAttachmentsServiceImpl
 *@Description:信息附件service层实现类
<<<<<<< Updated upstream:src/main/java/ses/service/bms/impl/ArticleAttachmentsServiceImpl.java
 *<p>Company:ses</p>
=======
>>>>>>> Stashed changes:src/main/java/yggc/service/bms/impl/ArticleAttachmentsServiceImpl.java
 *@author QuJie
 *@date 2016-9-7上午10:10:58
 */
@Service("articleAttachmentsService")
public class ArticleAttachmentsServiceImpl implements ArticleAttachmentsService {
	
	@Autowired
	private ArticleAttachmentsMapper articleAttachmentsMapper;
	
	/**
	 * 新增一个附件
	 */
	@Override
	public int insert(ArticleAttachments record) {
		return articleAttachmentsMapper.insertSelective(record);
	}
	
	/**
	 * 根据articleId查询附件
	 */
	@Override
	public List<ArticleAttachments> selectAllArticleAttachments(String id) {
		return articleAttachmentsMapper.selectAllArticleAttachments(id);
	}
	
	/**
	 * 根据id查询信息附件
	 */
	@Override
	public ArticleAttachments selectArticleAttaById(String id) {
		return articleAttachmentsMapper.selectByPrimaryKey(id);
	}
}
