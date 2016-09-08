package iss.service.article.impl;

import iss.dao.article.IndexNewsMapper;
import iss.service.article.IndexNewsService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.ArticleAttachmentsMapper;
import ses.model.bms.Article;
import ses.model.bms.ArticleAttachments;


/**
 * 
 *<p>Title:IndexNewsServiceImpl</p>
 *<p>Description:首页信息service实现类</p>
 *<p>Company:ses</p>
 * @author Mrlovablee
 *@date 2016-8-29上午9:15:16
 */
@Service("indexNewsService")
public class IndexNewsServiceImpl implements IndexNewsService {
	
	@Autowired
	private IndexNewsMapper indeNewsMapper;
	
	@Autowired
	private ArticleAttachmentsMapper articleAttachmentsMapper;
	
	/**
	 * 首页查询所有信息方法
	 */
	@Override
	public List<Article> selectNewsByArticleTypeId(String id) {
		List<Article> indexNewsList = indeNewsMapper.selectNewsByArticleTypeId(id);
		if(indexNewsList.isEmpty()){
			return null;
		}else{
			return indexNewsList;
		}	
	}
}
