package yggc.service.iss.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yggc.dao.bms.ArticleAttachmentsMapper;
import yggc.dao.iss.IndexNewsMapper;
import yggc.model.bms.Article;
import yggc.model.bms.ArticleAttachments;
import yggc.service.iss.IndexNewsService;

/**
 * 
 *<p>Title:IndexNewsServiceImpl</p>
 *<p>Description:首页信息service实现类</p>
 *<p>Company:yggc</p>
 * @author Mrlovablee
 *@date 2016-8-29上午9:15:16
 */
@Service("IndexNewsService")
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
