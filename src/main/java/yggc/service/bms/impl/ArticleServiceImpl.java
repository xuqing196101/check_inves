package yggc.service.bms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yggc.dao.bms.ArticlesMapper;
import yggc.model.bms.Article;
import yggc.service.bms.ArticleService;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
	
	@Autowired
	private ArticlesMapper articleMapper;
	
	/**
	 * 新增信息
	 */
	@Override
	public void addArticle(Article article) {
		articleMapper.insert(article);
	}
	
	/**
	 * 查询所有信息列表
	 */
	@Override
	public List<Article> selectAllArticle() {
		return articleMapper.selectAllArticle();
	}
	
	/**
	 * 修改信息
	 */
	@Override
	public void editArticle(Article article) {
		articleMapper.updateByPrimaryKeyWithBLOBs(article);
	}

	@Override
	public void delArticleById(Long id) {
		articleMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 根据id查询信息
	 */
	@Override
	public Article selectArticleById(Long id) {
		return articleMapper.selectByPrimaryKey(id);
	}
}
